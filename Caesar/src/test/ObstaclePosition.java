package test;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class ObstaclePosition {
    //attributs
    RobotPosition robotPosition;

    float distanceLidar;
    float angleLidar ;

    float positionLidarX;
    float positionLidarY;

    float positionObstacleX;
    float positionObstacleY;

    //Constructeurs
    public ObstaclePosition(RobotPosition robotPosition){
        this.robotPosition = robotPosition;
    }



    //méthode



    void calculPositionLidarX(){
        this.positionLidarX = (float)(distanceLidar * cos((angleLidar)));
    }

    void calculPositionLidarY(){
        this.positionLidarY = (float)(distanceLidar * sin((angleLidar)));
    }

    void setPositionObstacle(){
        robotPosition.definirPosition();
        calculPositionLidarX();
        calculPositionLidarY();
        if (robotPosition.axeRobot.equals(AxeRobot.Y) && robotPosition.sensAxe.equals(SensAxe.POSITIF) ) {
            positionObstacleX = robotPosition.positionX + positionLidarX;
            positionObstacleY = robotPosition.positionY + positionLidarY;
        }
        else if (robotPosition.axeRobot.equals(AxeRobot.Y) && robotPosition.sensAxe.equals(SensAxe.NEGATIF) ){
            positionObstacleX = robotPosition.positionX - positionLidarY;
            positionObstacleY = robotPosition.positionY - positionLidarX;
        }

        else if (robotPosition.axeRobot.equals(AxeRobot.X) && robotPosition.sensAxe.equals(SensAxe.POSITIF) ){
            positionObstacleX = robotPosition.positionX + positionLidarY;
            positionObstacleY = robotPosition.positionY - positionLidarX;
        }

        else if (robotPosition.axeRobot.equals(AxeRobot.X) && robotPosition.sensAxe.equals(SensAxe.NEGATIF) ){
            positionObstacleX = robotPosition.positionX - positionLidarY;
            positionObstacleY = robotPosition.positionY + positionLidarX;
        }
    }


    public static void main(String[] args) {
        RobotPosition robotPosition = RobotPosition.getInstance();
        ObstaclePosition obstaclePosition = new ObstaclePosition(robotPosition);

        robotPosition.positionX = 3;
        robotPosition.positionY = 5;

        obstaclePosition.distanceLidar=0;
        obstaclePosition.angleLidar=0;
        obstaclePosition.setPositionObstacle();
        System.out.println("position du robot en x : " + robotPosition.positionX);
        System.out.println("position du robot en  y : " + robotPosition.positionY);
        System.out.println("position de l'obstacle en x : " + obstaclePosition.positionObstacleX);
        System.out.println("position de l'obstacle en y : " + obstaclePosition.positionObstacleY);
        System.out.println("\n");

        obstaclePosition.distanceLidar=2;
        obstaclePosition.angleLidar=0;
        obstaclePosition.setPositionObstacle();
        System.out.println("position du robot en x : " + robotPosition.positionX);
        System.out.println("position du robot en  y : " + robotPosition.positionY);
        System.out.println("position de l'obstacle en x : " + obstaclePosition.positionObstacleX);
        System.out.println("position de l'obstacle en y : " + obstaclePosition.positionObstacleY);
        System.out.println("\n");

        obstaclePosition.distanceLidar=5;
        obstaclePosition.angleLidar=180;
        obstaclePosition.setPositionObstacle();
        System.out.println("position du robot en x : " + robotPosition.positionX);
        System.out.println("position du robot en  y : " + robotPosition.positionY);
        System.out.println("position de l'obstacle en x : " + obstaclePosition.positionObstacleX);
        System.out.println("position de l'obstacle en y : " + obstaclePosition.positionObstacleY);
    }


}



