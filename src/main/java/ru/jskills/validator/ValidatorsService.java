package ru.jskills.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by safin.v on 14.11.2016.
 */
@Service
public class ValidatorsService {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    private  Map<String, Validator> map;

    private ExecutorService executor = Executors.newFixedThreadPool(1000);


    public ValidatorsService() {
        map = new HashMap<>();
    }

    public void execCode(String user, String code) throws Exception {
        Validator validator = map.get(user);
        if(validator != null) {
            validator.closeValidator();
        }

        validator = new Validator(user, code, "lesson1") {
            @Override
            public void onOutPrint(String s) {
                super.onOutPrint(s);
                simpMessagingTemplate.convertAndSendToUser(user, "/queue/out_print", s);
            }
        };
        map.put(user, validator);


        executor.execute(validator);



    }

    public void setMsg(String user, String msg){
        Validator validator;

        validator = map.get(user);
                if (validator != null)
                    validator.println(msg);
    }

    public void setRedy(String user){
        Validator validator;

        validator = map.get(user);
        if (validator != null)
            validator.setRedy(true);
    }


}
