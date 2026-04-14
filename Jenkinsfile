pipeline {
    agent any

    stages {
        stage('Clone') {
            steps {
                checkout scm
            }
        }

        stage('Maven Build') {
            steps {
                // This mimics exactly what your freestyle job just did
                sh 'mvn clean package'
            }
        }

        stage('Docker Build') {
            steps {
                // We use the JAR produced in the previous step
                sh 'docker build -t event-app:latest .'
            }
        }

        stage('Deploy') {
            steps {
                // Stop and remove old container if it exists, then run new one
                sh 'docker stop event-app || true'
                sh 'docker rm event-app || true'
                sh 'docker run -d --name event-app -p 8080:8080 event-app:latest'
            }
        }
    }
}
