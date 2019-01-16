pipeline {
    agent any
    tools {
        maven 'Apache Maven 3.5.2'
    }
    stages{
        stage('Checkout') {
            steps {
                git 'https://github.com/vyjorg/LPDM-Location'
            }
        }
        stage('Tests') {
            steps {
                sh 'mvn clean test'
            }
            post {
                always {
                    junit 'target/surefire-reports/**/*.xml'
                }
                failure {
                    error 'The tests failed'
                }
            }
        }
        stage('Push to DockerHub') {
            steps {
                sh 'mvn clean package'
            }
        }
        stage('Deploy') {
            steps {
                sh 'docker stop LPDM-LocationMS || true && docker rm LPDM-LocationMS || true'
                sh 'docker pull vyjorg/lpdm-location:latest'
                sh 'docker run -d --name LPDM-LocationMS -p 28087:28087 --link LPDM-LocationDB --restart always --memory-swappiness=0  vyjorg/lpdm-location:latest'
            }
        }
    }
}
