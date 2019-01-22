pipeline {
    agent any
    tools {
        maven 'Apache Maven 3.5.2'
    }
     environment {
                KEY = ''
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
        stage('Load Key') {
                            steps {
                                script {
                                    configFileProvider([configFile(fileId: '2bd4e734-a03f-4fce-9015-aca988614b4e', targetLocation: 'lpdm.key')]) {
                                        lpdm_keys = readJSON file: 'lpdm.key'
                                        KEY = lpdm_keys.location
                                    }
                                }
                            }
                        }
        stage('Deploy') {
            steps {
                sh "docker stop LPDM-LocationMS || true && docker rm LPDM-LocationMS || true"
                sh "docker pull vyjorg/lpdm-location:latest"
                sh "docker run -d --name LPDM-LocationMS -p 28087:28087 --link LPDM-LocationDB --restart always --memory-swappiness=0 -e 'JAVA_TOOL_OPTIONS=-Djasypt.encryptor.password=$KEY' vyjorg/lpdm-location:latest"
            }
        }
    }
}
