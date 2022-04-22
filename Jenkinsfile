pipeline{
    agent any 
    environment{
        registry= 'jorgemongelos/project2'
        dockerHubCreds = 'dockerhub'
        dockerImage =''
    }
    stages{
        stage('Code quality analysis'){
            steps{
                withSonarQubeEnv(credentialsId: 'sonar-token', installationName: 'sonar-scanner'){
                    sh 'mvn verify org.sonarsource.scanner.maven:sonar-maven-plugin:sonar -Dsonar.projectKey=030722-VA-SRE_TeamWW'
                }
            }
        }
        stage("Maven clean package"){
            steps{
                sh 'mvn clean package'            
            }
        }
        stage("Docker build"){
            steps{
                script{
                    dockerImage = docker.build "$registry"
                }
            }
        }
        stage("Sending docker's image to DockerHub"){
            steps{
                script{
                    docker.withRegistry('', dockerHubCreds){
                        dockerImage.push("$currentBuild.number")
                        dockerImage.push("latest")
                    }
                }
            }
        }
    }
    // post{
    //     // always{
    //     //     echo "========always========"
    //     // }
    //     // success{
    //     //     echo "========pipeline executed successfully ========"
    //     // }
    //     // failure{
    //     //     echo "========pipeline execution failed========"
    //     // }
    // }
}