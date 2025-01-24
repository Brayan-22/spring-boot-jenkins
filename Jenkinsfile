pipeline {
    agent any
    stages {
        stage('SCM') {
            steps {
                // Cambiar 'my-ssh-key-id' por el ID de la credencial configurada en Jenkins
                git credentialsId: 'Jenkins-github', url: 'git@github.com:Brayan-22/spring-boot-jenkins.git', branch: master
                stash name: 'source', useDefaultExcludes: false
                script {
                    def commitSha = sh(script: 'git rev-parse HEAD', returnStdout: true).trim()
                    githubNotify context: 'SCM', status: 'PENDING', description: 'SCM stage started', sha: commitSha
                }
            }
        }
        stage('SonarQube Quality Test') {
            environment {
                scannerHome = tool 'sq1'
            }
            steps {
                unstash 'source'
                withSonarQubeEnv('sq1') {
                    sh 'mvn clean package -DskipTests'
                    sh '${scannerHome}/bin/sonar-scanner -Dsonar.projectKey=spring-jenkins -Dsonar.java.binaries=target'
                }
                script {
                    def commitSha = sh(script: 'git rev-parse HEAD', returnStdout: true).trim()
                    githubNotify context: 'SonarQube Quality Test', status: 'SUCCESS', description: 'SonarQube Quality Test passed', sha: commitSha
                }
            }
        }
        stage('SonarQube Quality Gate') {
            steps {
                timeout(time: 1, unit: 'HOURS') {
                    waitForQualityGate abortPipeline: true
                }
                script {
                    def commitSha = sh(script: 'git rev-parse HEAD', returnStdout: true).trim()
                    githubNotify context: 'SonarQube Quality Gate', status: 'SUCCESS', description: 'SonarQube Quality Gate passed', sha: commitSha
                }
            }
        }
    }
    post {
        failure {
            script {
                def commitSha = sh(script: 'git rev-parse HEAD', returnStdout: true).trim()
                githubNotify context: 'Pipeline', status: 'FAILURE', description: 'Pipeline failed', sha: commitSha
            }
        }
        success {
            script {
                def commitSha = sh(script: 'git rev-parse HEAD', returnStdout: true).trim()
                githubNotify context: 'Pipeline', status: 'SUCCESS', description: 'Pipeline succeeded', sha: commitSha
            }
        }
    }
}