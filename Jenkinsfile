pipeline {
    agent any
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