void setBuildStatus(String message, String state) {
  step([
      $class: "GitHubCommitStatusSetter",
      reposSource: [$class: "ManuallyEnteredRepositorySource", url: "https://github.com/my-org/my-repo"],
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
    }
    post{
        success {
            setBuildStatus("Build exitoso", "SUCCESS")
        }
        failure {
            setBuildStatus("Build fallido", "FAILURE")
        }
    }
}