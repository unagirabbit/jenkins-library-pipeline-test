package groovy

import static org.junit.Assert.assertEquals
import com.lesfurets.jenkins.unit.BasePipelineTest
import org.junit.Before
import org.junit.Test

/**
* slack.groovyのテスト
*/
class TestSlack extends BasePipelineTest {

    final String scriptFile = 'slack.groovy'

    @Override
    @Before
    void setUp() throws Exception {
        scriptRoots += 'src/main/groovy/vars'
        scriptExtension = 'groovy'
        super.setUp()
    }

    @Test
    void post_slack_message1() throws Exception {
        Script script = runScript(scriptFile)
        String result = script.postSlackMessage(credentialsId: '', postChannel: '', text: '')
        assertEquals('', result)
        printCallStack()
    }

    @Test
    void post_slack_message2() throws Exception {
        Script script = runScript(scriptFile)
        String result = script.postSlackMessage(credentialsId: '', postChannel: '', text: '', color: 'danger')
        assertEquals('', result)
        printCallStack()
    }

    @Test
    void post_slack_message3() throws Exception {
        Script script = runScript(scriptFile)
        String result = script.postSlackMessage(credentialsId: '', postChannel: '', text: '', attachments: [])
        assertEquals('', result)
        printCallStack()
    }

}
