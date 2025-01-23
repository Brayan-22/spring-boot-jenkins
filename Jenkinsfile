// pipeline{
//     agent any
//     tools{
//         maven 'Maven Apache'
//     }
//     stages{
//         stage('Checkout'){
//             steps{
//                 //Clonar repositorio
//                 git url: 'https://github.com/Brayan-22/spring-boot-jenkins'
//             }
//         }
//         stage('Build'){
//             steps{
//                 script{
//                     if(!isUnix()){
//                         bat 'mvn --version || echo Maven is not installed' 
//                     }else{
//                         sh 'mvn --version || echo Maven is not installed'
//                     }
//                 }
//                 sh 'mvn clean install'
//             }
//         }
//     }
//     post{
//         success {
//             echo 'Build Success'
//         }
//         failure{
//             echo 'Build Failed'
//         }
//     }
// }
void setBuildStatus(String message,String state){
    step([
        $class: "GitHubCommitStatusSetter",
        reposSource: [$class: "ManuallyEnteredRepositorySource", url: env.GIT_URL],
        contextSource: [$class: "ManuallyEnteredCommitContextSource", context: "ci/jenkins/build-status"],
        errorHandlers: [[$class: "ChangingBuildStatusErrorHandler", result: "UNSTABLE"]],
        statusResultSource: [ $class: "ConditionalStatusResultSource", results: [[$class: "AnyBuildResult", message: message, state: state]] ]    
    ]);
}
pipeline{
    agent any
    stages{
        stage('Build'){
            steps{
                echo "Etapa Build no disponible"
            }
        }
        stage('Tests'){
            steps{
                echo "Etapa Tests no disponible"
            }
        }
        // stage('Deploy'){
        //     steps{
        //         sh "docker compose down -v"
        //         sh "docker compose up -d --build"
        //     }
        // }
        post{
            success{
                setBuildStatus("Build Success","SUCCESS")
            }
            failure{
                setBuildStatus("Build Failed","FAILURE")
            }
        }
    }
}