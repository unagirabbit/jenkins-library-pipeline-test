package pipeline

import static com.lesfurets.jenkins.unit.global.lib.LibraryConfiguration.library
import static com.lesfurets.jenkins.unit.global.lib.ProjectSource.projectSource

import com.lesfurets.jenkins.unit.declarative.DeclarativePipelineTest

import org.junit.Before
import org.junit.Test

class TestSample extends DeclarativePipelineTest {

    @Override
    @Before
    void setUp() throws Exception {
        scriptRoots += 'jenkinsfiles'
        scriptExtension = 'Jenkinsfile'
        super.setUp()

        binding.setVariable('manager', [addShortText: { return 'expected result' }])
        helper.registerAllowedMethod('lock', [String])
        helper.registerAllowedMethod('sleep', [String])
        helper.registerAllowedMethod('slackSend', [Map])

        addEnvVar('BUILD_NODE', 'hoge')
        addParam('APP_BRANCH_DISPLAY_NAME', 'hoge')
        Object library = library()
            .name('my_library')
            .retriever(projectSource('src/main/groovy/'))
            .defaultVersion('<notNeeded>')
            .targetPath('<notNeeded>')
            .allowOverride(true)
            .implicit(false)
            .build()
        helper.registerSharedLibrary(library)
    }

    @Test
    void test_sample() {
        runScript('sample.Jenkinsfile')

        printCallStack()
        assertJobStatusSuccess()
        assertCallStack().contains('hello world.')
    }

}
