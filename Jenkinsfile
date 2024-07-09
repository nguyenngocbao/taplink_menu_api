pipeline {
    agent any
    tools {
        maven 'Maven 3.8.1'
        jdk 'JDK 8'
    }
    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/nguyenngocbao/taplink_menu_api.git'
            }
        }
        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }
    }
}