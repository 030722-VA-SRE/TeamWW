pipeline{
    agent any 
    stages{
        stage("Maven clean package"){
            steps{
                sh. 'mvn clean package'            
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