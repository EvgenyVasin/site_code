package ru.jskills.controllers;
import org.codehaus.commons.compiler.jdk.SimpleClassPathCompiler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import ru.jskills.vo.MsgCode;
import com.github.igorsuhorukov.smreed.dropship.MavenClassLoader;


import java.io.*;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.List;

import static ru.jskills.vo.MsgCode.*;

/**
 * Created by safin.v on 17.10.2016.
 */
@Controller
public class JCodeController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;


    private Logger log = LoggerFactory.getLogger(JCodeController.class);
    private static String moduleName = "Lesson";


    @MessageMapping("/jcode")
    public void getTextCode(MsgCode msgCode)throws Exception {
        switch (msgCode.getCommandId()){
            case CODE_MSG:
                try {//для мавен зависимостей
                    List<URL> urlsCollection = MavenClassLoader.usingCentralRepo().getArtifactUrlsCollection("com.github.igor-suhorukov:phantomjs-runner:1.1", null);

                    SimpleClassPathCompiler simpleCompiler = new SimpleClassPathCompiler(urlsCollection);
                    simpleCompiler.setCompilerOptions("-8");
                    simpleCompiler.setDebuggingInformation(true, true, true);

                    //String src = IOUtil.toString(getClass().getResourceAsStream(modulePath + moduleName));
                    simpleCompiler.cook(moduleName + ".java", msgCode.getMsg());

                    Class<?> clazz = simpleCompiler.getClassLoader().loadClass("Lesson");
                    Method main = clazz.getMethod("main", String[].class);

                    PrintStream sysOut = System.out;


                    ByteArrayOutputStream baOut = new ByteArrayOutputStream();
                    PrintStream myOut = new PrintStream(baOut);

                    System.setOut(myOut);
                    System.setErr(myOut);

                    main.invoke(null, (Object) null);


                    String s = new String(baOut.toByteArray());
                    simpMessagingTemplate.convertAndSend("/topic/out_print" + msgCode.getSessionId(), s);

                    System.setOut(sysOut);
                    System.setErr(sysOut);





                } catch (final Exception err) {
                    simpMessagingTemplate.convertAndSend("/topic/out_print" + msgCode.getSessionId(), err.getLocalizedMessage());
                }
                break;
            case CMD_MSG:
                try {
                    //System.setIn(new ByteArrayInputStream(msgCode.getMsg().getBytes()));

                } catch (final Exception err) {
                    simpMessagingTemplate.convertAndSend("/topic/out_print" + msgCode.getSessionId(), err.getLocalizedMessage());
                }
        }
    }
}
