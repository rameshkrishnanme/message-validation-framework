package smartvalidator;

import java.io.IOException;

import org.junit.Test;

import com.app.util.message.validator.GrokMessagePatternExecuter;
import com.app.util.message.validator.core.grok.GrokCompilerProvider;
import com.app.util.message.validator.manage.ApplicationLogger;
import com.app.util.message.validator.manage.UtilFile;
import com.app.util.message.validator.pattern.pojo.MessagePattern;
import com.app.util.message.validator.pojo.Validate;

import local.io.krakens.grok.api.GrokCompiler;

public class GrokMessagePatternExecuterTest {

    public static GrokCompiler testGrokComplier() {
        GrokCompiler grokCompiler = GrokCompiler.newInstance();
        grokCompiler.registerDefaultPatterns();
        try {
            grokCompiler.register(UtilFile.readAsInputStream("/standalone.pattern"));
        }
        catch (IOException e) {
            // Ignore
        }
        return grokCompiler;
    }

    @Test
    public void testPILMessage() {
        ApplicationLogger.log("Starting test");

        GrokMessagePatternExecuter grokMessagePatternExecuter = testGrokMessagePatternExecuter();
        String testMessage = UtilFile.readFileContents("/PIL.message");
        Validate validate = new Validate();
        validate.setTestMessage(testMessage);
        validate = grokMessagePatternExecuter.validate(validate, "PIL");
        System.out.println("MessageValidResponse  : " + validate.getTestResults());

    }

    public static GrokMessagePatternExecuter testGrokMessagePatternExecuter() {
        GrokMessagePatternExecuter grokMessagePatternExecuter = new GrokMessagePatternExecuter() {

            public MessagePattern loadMessagePattern(String uniqueMessageIdentifier) {
                return MessagePattern.buildMessagePattern(UtilFile.readFileContents("/PIL.pattern"));
            }

            @Override
            public GrokCompiler getGrokComplier() {
                return testGrokComplier();
            }

               
            

        };
        return grokMessagePatternExecuter;
    }

}
