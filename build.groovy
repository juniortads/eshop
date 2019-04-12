pipeline {
  environment {
    registry = "https://451393511481.dkr.ecr.sa-east-1.amazonaws.com"
    registryCredential = "ecr:sa-east-1:ecr-id"
    TAG = $BUILD_NUMBER
    REGISTRY = "repo-docker"
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
            sh "docker images ls"
            sh 'docker-compose -f ./docker-compose_aws.yml build'
        }
      }
    }
    stage('Deploy image to ECR') {
      steps {
        script{
                docker.withRegistry(registry, registryCredential)
                {
                    //docker.image('identity.api').push(env.BUILD_NUMBER)
                    //docker.image('basket.api').push(env.BUILD_NUMBER)
                    //docker.image('catalog.api').push(env.BUILD_NUMBER)
                    //docker.image('ordering.api').push(env.BUILD_NUMBER)
                    //docker.image('ordering.backgroundtasks').push(env.BUILD_NUMBER)
                    //docker.image('marketing.api').push(env.BUILD_NUMBER)
                    //docker.image('payment.api').push(env.BUILD_NUMBER)
                    //docker.image('locations.api').push(env.BUILD_NUMBER)
                    //docker.image('webhooks.api').push(env.BUILD_NUMBER)
                    //docker.image('mobileshoppingapigw').push(env.BUILD_NUMBER)
                    //docker.image('mobilemarketingapigw').push(env.BUILD_NUMBER)
                    //docker.image('webshoppingapigw').push(env.BUILD_NUMBER)
                    //docker.image('webmarketingapigw').push(env.BUILD_NUMBER)
                    //docker.image('mobileshoppingagg').push(env.BUILD_NUMBER)
                    //docker.image('webshoppingagg').push(env.BUILD_NUMBER)
                    //docker.image('ordering.signalrhub').push(env.BUILD_NUMBER)
                    docker.image(env.REGISTRY + '/webstatus:latest').push(env.BUILD_NUMBER)
                    //docker.image('webspa').push(env.BUILD_NUMBER)
                    //docker.image('webmvc').push(env.BUILD_NUMBER)
                    //docker.image('webhooks-client').push(env.BUILD_NUMBER)
                }
        }
      }
    }
    stage('Remove Unused docker image'){
        steps{
            //sh "docker image prune -all"
        }
    }
  }
}