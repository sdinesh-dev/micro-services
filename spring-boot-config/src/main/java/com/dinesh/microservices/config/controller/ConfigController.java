package com.dinesh.microservices.config.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Map;

@RestController
@RefreshScope
public class ConfigController {

    @Value("${my.greeting}")
    private String message;

    @Value("${app.description}")
    private String appDesc;

    @Value("${static.msg: Static Messages}") //Setting Default Values
    private String staticMsg;

    @Value("${list.of.numbers}")
    private List<String> stringList;

    @Value("#{${dbValues}}")
    private Map<String, String> mapValues;

    @Autowired
    private DbSettings dbSettings;

    @Autowired
    private Environment env;

    @GetMapping("/greeting")
    public String getGreeting(){
        return message;
    }

    @GetMapping("/desc")
    public String getDescription(){
        return appDesc;
    }

    @GetMapping("/static")
    public String getStaticMsg(){
        return staticMsg;
    }

    @GetMapping("/list")
    public List<String> getStringList(){
        return stringList;
    }

    @GetMapping("/map")
    public Map<String,String> getMapValues(){
        return mapValues;
    }

    @GetMapping("/setting")
    public String getDbSettings(){
        return dbSettings.getConnection() + dbSettings.getHost() + dbSettings.getPort();
    }

    @GetMapping("/envdetails")
    public String getEnvDetails(){
        return env.toString();
    }

}
