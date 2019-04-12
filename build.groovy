pipeline {
  environment {
    registry = "https://451393511481.dkr.ecr.sa-east-1.amazonaws.com"
    registryCredential = "ecr:sa-east-1:ecr-id"
    repository = "repo-docker/eshop/"
    REGISTRY = "https://451393511481.dkr.ecr.sa-east-1.amazonaws.com/repo-docker/eshop/"
  }
  agent any
  stages {
    stage('Cloning Git') {
      steps {
        git(
            url: 'https://github.com/juniortads/eshop.git',
            credentialsId: 'eshop_gilmarsilva',
            branch: "master"
        )
      }
    }
    stage('Building image') {
      steps {
        script{
            sh 'ls -la'
            sh 'docker-compose build docker-compose_aws.yml'
        }
      }
    }
    stage('Deploy image to ECR') {
      steps {
        script{
                docker.withRegistry(registry, registryCredential)
                {
                    docker.image(repository + 'identity.api').push(env.BUILD_NUMBER)
                    docker.image(repository + 'basket.api').push(env.BUILD_NUMBER)
                    docker.image(repository + 'catalog.api').push(env.BUILD_NUMBER)
                    docker.image(repository + 'ordering.api').push(env.BUILD_NUMBER)
                    docker.image(repository + 'ordering.backgroundtasks').push(env.BUILD_NUMBER)
                    docker.image(repository + 'marketing.api').push(env.BUILD_NUMBER)
                    docker.image(repository + 'payment.api').push(env.BUILD_NUMBER)
                    docker.image(repository + 'locations.api').push(env.BUILD_NUMBER)
                    docker.image(repository + 'webhooks.api').push(env.BUILD_NUMBER)
                    docker.image(repository + 'mobileshoppingapigw').push(env.BUILD_NUMBER)
                    docker.image(repository + 'mobilemarketingapigw').push(env.BUILD_NUMBER)
                    docker.image(repository + 'webshoppingapigw').push(env.BUILD_NUMBER)
                    docker.image(repository + 'webmarketingapigw').push(env.BUILD_NUMBER)
                    docker.image(repository + 'mobileshoppingagg').push(env.BUILD_NUMBER)
                    docker.image(repository + 'webshoppingagg').push(env.BUILD_NUMBER)
                    docker.image(repository + 'ordering.signalrhub').push(env.BUILD_NUMBER)
                    docker.image(repository + 'webstatus').push(env.BUILD_NUMBER)
                    docker.image(repository + 'webspa').push(env.BUILD_NUMBER)
                    docker.image(repository + 'webmvc').push(env.BUILD_NUMBER)
                    docker.image(repository + 'webhooks.client').push(env.BUILD_NUMBER)
                }
        }
      }
    }
    stage('Remove Unused docker image'){
        steps{
            sh "docker image prune -all"
        }
    }
  }
}