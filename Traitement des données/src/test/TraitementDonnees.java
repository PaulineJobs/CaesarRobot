package test;

import java.util.Random;

public class TraitementDonnees extends Thread{
    //attributs
    CommunicationRobot communicationRobot;

    //constructeur
    public TraitementDonnees () {
       RobotPosition robotPosition = new RobotPosition();
       ObstaclePosition obstaclePosition = new ObstaclePosition(robotPosition);
       this.communicationRobot = new CommunicationRobot(obstaclePosition);
       communicationRobot.obstaclePosition.robotPosition.etatRobot = EtatRobot.ARRET;
    }

    public  void run() {
            try {
                while(true){
                    Thread.sleep(5000) ;
                    communicationRobot.lancerCommunication();
                    Random random = new Random();
                    communicationRobot.obstaclePosition.robotPosition.nbrToursDroite=random.nextInt(1,3);
                    communicationRobot.obstaclePosition.robotPosition.nbrToursGauche=random.nextInt(1,3);
                    communicationRobot.obstaclePosition.angleLidar = random.nextInt(359);
                    communicationRobot.obstaclePosition.distanceLidar = random.nextInt(5);

                    System.out.println("axe actuel du robot : " + communicationRobot.obstaclePosition.robotPosition.axeRobot + " - " + communicationRobot.obstaclePosition.robotPosition.sensAxe);

                    System.out.println("Nombre de tours de roues à droite : " + communicationRobot.obstaclePosition.robotPosition.nbrToursDroite);
                    System.out.println("Nombre de tours de roues à gauche : " + communicationRobot.obstaclePosition.robotPosition.nbrToursGauche);
                    System.out.println("Distance envoyée par le Lidar : " + communicationRobot.obstaclePosition.distanceLidar);
                    System.out.println("Angle envoyée par le Lidar : " + communicationRobot.obstaclePosition.angleLidar);

                    communicationRobot.lancerCalcul();

                    System.out.println("axe actuel du robot : " + communicationRobot.obstaclePosition.robotPosition.axeRobot + " - " + communicationRobot.obstaclePosition.robotPosition.sensAxe);
                    System.out.println("position du robot en x : " + communicationRobot.obstaclePosition.robotPosition.positionX);
                    System.out.println("position du robot en  y : " + communicationRobot.obstaclePosition.robotPosition.positionY);

                    System.out.println("position de l'obstacle en x : " + communicationRobot.obstaclePosition.positionObstacleX);
                    System.out.println("position de l'obstacle en y : " + communicationRobot.obstaclePosition.positionObstacleY);

                    System.out.println("\n");
                }

            }  catch (InterruptedException e) {

                // gestion de l'erreur
            }
        }

    public static void main(String[] args) {
        TraitementDonnees traitementDonnees = new TraitementDonnees();
        traitementDonnees.start();
    }
}


