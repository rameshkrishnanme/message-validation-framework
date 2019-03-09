package com.app.util.message.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.util.message.validator.GrokMessagePatternExecuter;
import com.app.util.message.validator.GrokStandAloneExecuter;
import com.app.util.message.validator.core.grok.GrokCompilerProvider;
import com.app.util.message.validator.pojo.Validate;

@RestController
public class ApplicationController {

    @Autowired
    GrokStandAloneExecuter grokStandAloneExecuter;
    
    @Autowired
    GrokMessagePatternExecuter grokMessagePatternExecuter;
    
    
    @RequestMapping("/validate")
    public Validate messageValidate(@RequestBody Validate validate) {
        
        if (validate.isBase64()) {
            validate.setTestMessage(new String(Base64Utils.decodeFromString(validate.getTestMessage())));
        }
        
        if (StringUtils.isBlank(validate.getUniqueIdentifier())) {
            return grokMessagePatternExecuter.validate(validate);
        }
        else {
            return grokMessagePatternExecuter.validate(validate, validate.getUniqueIdentifier());
        }
    }

    @RequestMapping("/savalidate")
    public Validate standAloneValidate(@RequestBody Validate validate) {
        grokStandAloneExecuter.validate(validate);
        return validate;
    }
    
    @RequestMapping("/clearcache")
    public void clear() {
       GrokCompilerProvider.clear();
    }

}