package com.app.util.message.validator;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.app.util.message.validator.core.grok.GrokCompilerProvider;
import com.app.util.message.validator.manage.JSONOrderSerialzer;
import com.app.util.message.validator.pojo.ResponseCode;
import com.app.util.message.validator.pojo.Validate;

import local.io.krakens.grok.api.Grok;
import local.io.krakens.grok.api.GrokCompiler;
import local.io.krakens.grok.api.Match;


@Component
public class GrokStandAloneExecuter extends AbstractPatternExecuter {
    

    public void validate(Validate validate) {
        
        final GrokCompiler grokCompiler = GrokCompilerProvider.getGrokCompiler(standAloneRepository);
        
        grokCompiler.register("TEST", validate.getTestPattern());
        
        final Grok compile = grokCompiler.compile("%{TEST:test}");
        final String text = validate.getTestMessage();
        final Map<String, Object> capture =  compile.matchAndCapture(text, true);
        // final Map<String, List<Object>> capture = compile.groupCapture(match);//match.capture();
        
        if (capture.isEmpty()) {
            validate.setResponseCode(ResponseCode.NO_MATCH.getCode());
            validate.setResponseMsg(ResponseCode.NO_MATCH.getMessage());
        }
        final String json =  JSONOrderSerialzer.toJson(capture);
        responseFormat(validate, json);

    }
}
