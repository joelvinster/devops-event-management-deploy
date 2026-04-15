pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/joelvinster/devops-event-management-deploy.git'
            }
        }
        stage('Build JAR') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }
        stage('Docker Build & Run') {
            steps {
                sh 'docker stop event-app || true'
                sh 'docker rm event-app || true'
                sh 'docker build -t event-app:latest .'
                sh 'docker run -d --name event-app -p 8082:8082 event-app:latest'
            }
        }
    }
}
