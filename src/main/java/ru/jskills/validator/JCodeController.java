package ru.jskills.validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import ru.jskills.vo.MsgCode;


import java.security.Principal;

import static ru.jskills.vo.MsgCode.*;

/**
 * Created by safin.v on 17.10.2016.
 */
@Controller
public class JCodeController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    private ValidatorsService validatorsService;
//    private static Map<String, ValidatorTask> map;
//    private static ExecutorService executor;
//    static{
//        map = new HashMap<>();
//        executor = Executors.newFixedThreadPool(1);
//    }

    private Logger log = LoggerFactory.getLogger(JCodeController.class);
    private static String moduleName = "Lesson";


//    @MessageMapping("/jcode")
//    public void getTextCode(MsgCode msgCode)throws Exception {
//
//        switch (msgCode.getCommandId()){
//            case CODE_MSG:
//                try {//для мавен зависимостей
//                    List<URL> urlsCollection = MavenClassLoader.usingCentralRepo().getArtifactUrlsCollection("com.github.igor-suhorukov:phantomjs-runner:1.1", null);
//
//                    SimpleClassPathCompiler simpleCompiler = new SimpleClassPathCompiler(urlsCollection);
//                    simpleCompiler.setCompilerOptions("-8");
//                    simpleCompiler.setDebuggingInformation(true, true, true);
//
//                    //String src = IOUtil.toString(getClass().getResourceAsStream(modulePath + moduleName));
//                    simpleCompiler.cook(moduleName + ".java", msgCode.getMsg());
//
//                    Class<?> clazz = simpleCompiler.getClassLoader().loadClass("Lesson");
//                    Method main = clazz.getMethod("main", String[].class);
//
//                    PrintStream sysOut = System.out;
////                    InputStream sysIn = System.in;
//
//                    ByteArrayOutputStream baOut = new ByteArrayOutputStream();
//                    PrintStream myOut = new PrintStream(baOut);
//
//                    System.setOut(myOut);
//                    System.setErr(myOut);
//
//
//                    main.invoke(null, (Object) null);
//
//                    String s = new String(baOut.toByteArray());
//                    simpMessagingTemplate.convertAndSend("/topic/out_print" + msgCode.getSessionId(), s);
//
//                    System.setOut(sysOut);
//                    System.setErr(sysOut);
//
//
//
//
//
//                } catch (final Exception err) {
//                    simpMessagingTemplate.convertAndSend("/topic/out_print" + msgCode.getSessionId(), err.getLocalizedMessage());
//                }
//                break;
//            case CMD_MSG:
//                try {
//                    c.writer().append(msgCode.getMsg());
//                    //System.setIn(new ByteArrayInputStream(msgCode.getMsg().getBytes()));
//
//                } catch (final Exception err) {
//                    simpMessagingTemplate.convertAndSend("/topic/out_print" + msgCode.getSessionId(), err.getLocalizedMessage());
//                }
//        }
//    }
@MessageMapping("/ws")
public void getTextCode(Principal principal, MsgCode msgCode)throws Exception {
    switch (msgCode.getCommandId()){

        case OK_MSG:
//            validator = map.get(principal.getName());
//            if (validator != null)
//                validator.setRedy(true);
            //System.out.println(msgCode.getMsg());
            validatorsService.setRedy(principal.getName() );
            break;

        case CODE_MSG:
            try {
//                validator = new ValidatorTask(principal.getName(), msgCode.getMsg()){
//                    @Override
//                    public void onOutPrint(String s) {
//                        super.onOutPrint(s);
//                        //System.out.println(s);
//                        simpMessagingTemplate.convertAndSendToUser(principal.getName(),"/queue/out_print", s);
//                    }
//                };
//                map.put(principal.getName(), validator);
//
//                executor.submit(validator);
//                System.out.println("");

                validatorsService.execCode(principal.getName(), msgCode.getMsg());

            } catch (final Exception err) {
                simpMessagingTemplate.convertAndSendToUser(principal.getName(),"/queue/out_print", err.getLocalizedMessage());
            }
            break;

        case CMD_MSG:
            try {
//                validator = map.get(principal.getName());
//                if (validator != null)
//                    validator.println(msgCode.getMsg());
                validatorsService.setMsg(principal.getName(), msgCode.getMsg());
            } catch (final Exception err) {
                simpMessagingTemplate.convertAndSendToUser(principal.getName(),"/queue/out_print", err.getLocalizedMessage());
            }
    }
}


}
