library 'my_library'
pipeline {
    agent any
    stages {
        stage('hoge') {
            steps {
                echo 'hello world.'
            }
        }
    }
    post {
        success {
            script {
                slack.postSlackMessage credentialsId: 'credentialsId', postChannel: 'postChannel', text: 'hello world.'
            }
        }
    }
}
