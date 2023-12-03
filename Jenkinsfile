pipeline {
  agent any
    environment {
      registry = "noah0812/payment"
      registryCredential="dockerhub"
      mvnHome = tool 'maven'
    }
  stages {
    stage('build') {
      steps {
        withEnv(["MVN_HOME=$mvnHome"]) {
          sh '$MVN_HOME/bin/mvn clean package'
        }
        script {
          dockerImage = docker.build(registry)
        }
      }
    }
    stage('Tag and docker push') {
      steps{
        script{
          docker.withRegistry( '', registryCredential ) {
            dockerImage.push("v0.0.$BUILD_NUMBER")
            dockerImage.push("latest")
          }
        }
      }

    }
    stage('docker clean') {
      steps{
        sh "docker rmi $registry"
        sh "docker rmi $registry:v0.0.$BUILD_NUMBER"
      }
    }
  }
}