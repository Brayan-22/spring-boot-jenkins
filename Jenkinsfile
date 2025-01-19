pipeline{
    agent any
    tools{
        maven 'Maven Apache'
    }
    stages{
        stage('Checkout'){
            steps{
                //Clonar repositorio
                git url: 'https://github.com/Brayan-22/spring-boot-jenkins'
            }
        }
        stage('Build'){
            steps{
                script{
                    if(!isUnix()){
                        bat 'mvn --version || echo Maven is not installed' 
                    }else{
                        sh 'mvn --version || echo Maven is not installed'
                    }
                }
                sh 'mvn clean install'
            }
        }
    }
    post{
        success {
            echo 'Build Success'
        }
        failure{
            echo 'Build Failed'
        }
    }
}