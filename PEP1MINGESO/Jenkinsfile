pipeline{
    agent any
    tools {
        maven "maven"
    }
    stages{
        stage("build JAR file"){
            steps{
                checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/pablomunozramirez/MINGESO_1']])
                dir("PEP1MINGESO"){
                    sh "mvn clean install"
                }
            }
        }
        stage("Test"){
            steps{
                dir("PEP1MINGESO"){
                    sh "mvn test"
                }
            }
        }
        stage("Build Docker Image"){
            steps{
                dir("PEP1MINGESO"){
                    sh "docket build -t 2pablo2/mingeso1 ."
                }
            }
        }
        stage("Push Docker Image"){
            steps{
                dir("PEP1MINGESO"){
                    withCredentials([string(credentialsId: 'dckrhubpassword', variable: 'dckpass')]) {
                        sh "docker login -u 2pablo2 -p ${dckpass}"
                    }
                    sh "docker push 2pavlo2/mingeso1"
                }
            }
        }
    }
    post{
        always{
            dir("PEP1MINGESO"){
                sh "docker logout"
            }
        }
    }
}