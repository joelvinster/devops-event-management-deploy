pipeline {
    agent any

    stages {
        stage('Clone') {
            steps {
                // Pulls the latest code from your GitHub repository
                checkout scm
            }
        }

        stage('Maven Build') {
            steps {
                // Compiles the Java code and creates the Shaded JAR
                sh 'mvn clean package'
            }
        }

        stage('Docker Build') {
            steps {
                // Builds the Docker image using the Dockerfile in your repo
                sh 'docker build -t event-app:latest .'
            }
        }

        stage('Deploy') {
            steps {
                // Stop and remove any existing container to avoid name conflicts
                sh 'docker stop event-app || true'
                sh 'docker rm event-app || true'
                
                // Map EC2 port 8081 to the Container port 8080
                // This ensures Jenkins (8080) and your App (8081) don't clash
                sh 'docker run -d --name event-app -p 8081:8080 event-app:latest'
            }
        }
    }

    post {
        success {
            echo '🚀 Pipeline successful! Access the app at http://<EC2-IP>:8081'
        }
        failure {
            echo '❌ Pipeline failed. Check the console output for errors.'
        }
    }
}
