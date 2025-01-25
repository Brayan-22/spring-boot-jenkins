pipeline {
    agent any
    environment {
        GIT_CREDENTIALS_ID = credentials('github-PAT')
    }
    stages {
        stage('SCM') {
            steps {
                script {
                    git url: 'https://github.com/Brayan-22/spring-boot-jenkins.git', branch: params.branch
                    stash name: 'source', useDefaultExcludes: false
                    def commitHash = sh(script: 'git rev-parse HEAD', returnStdout: true).trim()
                    echo "Current commit hash: ${commitHash}"
                    updateGitHubStatus(commitHash, 'pending', 'The build is in progress!')
                }
            }
        }
        stage('SonarQube Quality Test') {
            environment {
                scannerHome = tool 'Sonarqube'
            }
            tools {
                maven 'Maven'
            }
            steps {
                unstash 'source'
                withSonarQubeEnv(installationName: 'sq1') {
                    sh 'mvn clean package -DskipTests'
                    sh '${scannerHome}/bin/sonar-scanner -Dsonar.projectKey=spring-test -Dsonar.java.binaries=target'
                }
            }
        }
        stage('SonarQube Quality Gate') {
            steps {
                timeout(time: 1, unit: 'HOURS') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }
        stage('Build') {
            tools {
                maven 'Maven'
            }
            steps {
                unstash 'source'
                sh 'mvn clean package -DskipTests'
            }
        }
    }
    post {
        always {
            script {
                def commitHash = sh(script: 'git rev-parse HEAD', returnStdout: true).trim()
                echo "Finalizing build for commit: ${commitHash}"
            }
        }
        success {
            script {
                def commitHash = sh(script: 'git rev-parse HEAD', returnStdout: true).trim()
                updateGitHubStatus(commitHash, 'success', 'Build and tests completed successfully!')
            }
        }
        failure {
            script {
                def commitHash = sh(script: 'git rev-parse HEAD', returnStdout: true).trim()
                updateGitHubStatus(commitHash, 'failure', 'Build or tests failed!')
            }
        }
        aborted {
            script {
                def commitHash = sh(script: 'git rev-parse HEAD', returnStdout: true).trim()
                updateGitHubStatus(commitHash, 'failure', 'Build was aborted!')
            }
        }
    }
}

// Función para actualizar el estado en GitHub
def updateGitHubStatus(commitHash, state, description) {
    sh '''
        export COMMIT_HASH='''+ commitHash +'''
        export STATE='''+ state +'''
        curl -L \
        -X POST \
        -H "Accept: application/vnd.github+json" \
        -H "Authorization: Bearer $GIT_CREDENTIALS_ID" \
        -H "X-GitHub-Api-Version: 2022-11-28" \
        https://api.github.com/repos/Brayan-22/spring-boot-jenkins/statuses/$COMMIT_HASH \
        -d '{"state": "'$STATE'", "target_url": "'$BUILD_URL'", "'$description'": "The build is in progress!", "context": "continuous-integration/jenkins"}'
    '''
}
