pipeline {
    agent any
    stages {
        stage('Checkout') {
            steps {
                // Clonar el repositorio
                git branch: 'master', url: 'https://github.com/Brayan-22/spring-boot-jenkins.git'
            }
        }
        stage('Build') {
            steps {
                // Compilación del proyecto usando Maven
                sh './mvnw clean package'
            }
        }
        stage('Test') {
            steps {
                // Ejecución de pruebas
                sh './mvnw test'
            }
        }
        stage('SonarQube Analysis') {
            steps {
                // Ejecución del análisis estático con SonarQube
                withSonarQubeEnv($installationName: 'sq1') {
                    sh './mvnw clean compile org.sonarsource.scanner.maven:sonar-maven-plugin:3.9.0.2155:sonar'
                }
            }
        }
        stage('Quality Gate') {
            steps {
                // Esperar y verificar la Quality Gate de SonarQube
                timeout(time: 1, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }
    }
    post {
        always {
            // Archivar los artefactos generados
            archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
        }
        failure {
            // Notificar fallos
            echo 'Pipeline falló. Por favor, revisa los errores.'
        }
        success {
            // Notificar éxito
            echo 'Pipeline ejecutado con éxito.'
        }
    }
}
