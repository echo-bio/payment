pipeline {
  agent any
    environment {
      registry = "echo-bio/payment"
      registryCredential="dockerhub"
    }
  stages {
    stage('docker build') {
      steps {
        sh 'docker build -t echo-bio-payment .'
      }
    }
    stage('Tag and docker push') {
      steps{
        sh 'docker login -u taobi0812@gmail.com -p bitao123456'
        sh 'docker tag echo-bio-payment noah0812/echo-bio-payment'
        sh 'docker push noah0812/echo-bio-payment'
      }

    }
    stage('docker clean') {
      steps{
        sh 'docker rmi echo-bio-payment'
      }
    }
  }
}