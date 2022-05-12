


public class CommunicationRobot {

ObstaclePosition obstaclePosition;

public CommunicationRobot( ObstaclePosition obstaclePosition){
    this.obstaclePosition=obstaclePosition;
}


    void getInfoLidar(){
        // récolter les infos du lidar
        obstaclePosition.angleLidar = 0;
        obstaclePosition.distanceLidar = 0;
    }

    //Liaison avec Arduino
    void setNombreTours(){
        //this.nbrToursDroite= lire l'encodeur roue Droite
        //this.nbrToursGauche= lire l'encodeur roue Gauche
        obstaclePosition.robotPosition.nbrToursDroite = 0;
        obstaclePosition.robotPosition.nbrToursGauche= 0;
    }

    void resetRoues(){
        obstaclePosition.robotPosition.changerAxeEtSens();
        //envoyer l'info à l'arduino de reset les roues si ça a fini de cliquer pour tourner à gauche ou à droite


    }


    void setEtatRobot(){
        //si le robot marche ou non on set
        obstaclePosition.robotPosition.etatRobot = EtatRobot.MARCHE;
    }


    void lancerCalcul(){
        obstaclePosition.robotPosition.etatRobot=EtatRobot.MARCHE;
        obstaclePosition.robotPosition.definirPosition();
    }

    void lancerCommunication(){
        setNombreTours();
        getInfoLidar();
        lancerCalcul();
        if(obstaclePosition.robotPosition.tourneAdroite() || obstaclePosition.robotPosition.tourneAgauche()){
            resetRoues();
        }
    }

    public static void main(String[] args) {

        RobotPosition robotPosition = RobotPosition.getInstance();
        ObstaclePosition obstaclePosition = new ObstaclePosition(robotPosition);
        CommunicationRobot communicationRobot = new CommunicationRobot(obstaclePosition);

        communicationRobot.obstaclePosition.robotPosition.nbrToursDroite = 10;
        communicationRobot.obstaclePosition.robotPosition.nbrToursGauche = 10;
        communicationRobot.lancerCalcul();
        System.out.println("position du robot en x : " + communicationRobot.obstaclePosition.robotPosition.positionX);
        System.out.println("position du robot en  y : " + communicationRobot.obstaclePosition.robotPosition.positionY);
        System.out.println("axe actuel du robot : " + communicationRobot.obstaclePosition.robotPosition.axeRobot);
        System.out.println("\n");


        communicationRobot.obstaclePosition.robotPosition.nbrToursDroite = 9;
        communicationRobot.obstaclePosition.robotPosition.nbrToursGauche = 10;
        communicationRobot.lancerCalcul();
        System.out.println("position du robot en x : " + communicationRobot.obstaclePosition.robotPosition.positionX);
        System.out.println("position du robot en  y : " + communicationRobot.obstaclePosition.robotPosition.positionY);
        System.out.println("axe actuel du robot : " + communicationRobot.obstaclePosition.robotPosition.axeRobot);
        System.out.println("\n");

        communicationRobot.obstaclePosition.robotPosition.nbrToursDroite = 10;
        communicationRobot.obstaclePosition.robotPosition.nbrToursGauche = 10;
        communicationRobot.lancerCalcul();
        System.out.println("position du robot en x : " + communicationRobot.obstaclePosition.robotPosition.positionX);
        System.out.println("position du robot en  y : " + communicationRobot.obstaclePosition.robotPosition.positionY);
        System.out.println("axe actuel du robot : " + communicationRobot.obstaclePosition.robotPosition.axeRobot);
        System.out.println("\n");

        communicationRobot.obstaclePosition.robotPosition.nbrToursDroite = 9;
        communicationRobot.obstaclePosition.robotPosition.nbrToursGauche = 10;
        communicationRobot.lancerCalcul();
        System.out.println("position du robot en x : " + communicationRobot.obstaclePosition.robotPosition.positionX);
        System.out.println("position du robot en  y : " + communicationRobot.obstaclePosition.robotPosition.positionY);
        System.out.println("axe actuel du robot : " + communicationRobot.obstaclePosition.robotPosition.axeRobot);
        System.out.println("\n");


        communicationRobot.obstaclePosition.robotPosition.nbrToursDroite = 10;
        communicationRobot.obstaclePosition.robotPosition.nbrToursGauche = 10;
        communicationRobot.lancerCalcul();
        System.out.println("position du robot en x : " + communicationRobot.obstaclePosition.robotPosition.positionX);
        System.out.println("position du robot en  y : " + communicationRobot.obstaclePosition.robotPosition.positionY);
        System.out.println("axe actuel du robot : " + communicationRobot.obstaclePosition.robotPosition.axeRobot);
        System.out.println("\n");

        communicationRobot.obstaclePosition.robotPosition.nbrToursDroite = 9;
        communicationRobot.obstaclePosition.robotPosition.nbrToursGauche = 10;
        communicationRobot.lancerCalcul();
        System.out.println("position du robot en x : " + communicationRobot.obstaclePosition.robotPosition.positionX);
        System.out.println("position du robot en  y : " + communicationRobot.obstaclePosition.robotPosition.positionY);
        System.out.println("axe actuel du robot : " + communicationRobot.obstaclePosition.robotPosition.axeRobot);
        System.out.println("\n");


        communicationRobot.obstaclePosition.robotPosition.nbrToursDroite = 10;
        communicationRobot.obstaclePosition.robotPosition.nbrToursGauche = 10;
        communicationRobot.lancerCalcul();
        System.out.println("position du robot en x : " + communicationRobot.obstaclePosition.robotPosition.positionX);
        System.out.println("position du robot en  y : " + communicationRobot.obstaclePosition.robotPosition.positionY);
        System.out.println("axe actuel du robot : " + communicationRobot.obstaclePosition.robotPosition.axeRobot);
        System.out.println("\n");
    }
}
