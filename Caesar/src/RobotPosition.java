import java.lang.Math.*;

public class RobotPosition {
    //attributs
    int nbrToursDroite=0;
    int nbrToursGauche=0;
    float positionX=0;
    float positionY=0;
    double diametreRoue = 0.12 ;
    double pi = Math.PI;
    AxeRobot axeRobot = AxeRobot.Y;
    EtatRobot etatRobot ;
    SensAxe sensAxe = SensAxe.POSITIF;



    // public method to create objet
    public static RobotPosition getInstance(){
        return RobotPositionHolder.instance;
    }

    private static class RobotPositionHolder {
        private final static RobotPosition instance = new RobotPosition();
    }



    //Méthodes
    //Liaison avec Arduino
    void setNombreTours(){

    }

    void resetRoues(){
        //envoyer l'info à l'arduino de reset les roues
    }




    //Calculs préliminaire
    boolean tourneAdroite(){
        return (nbrToursDroite<nbrToursGauche);
    }

    boolean tourneAgauche(){
        return (nbrToursDroite>nbrToursGauche);
    }

    float distanceParcourue(){
        return  (float)(diametreRoue*pi*nbrToursDroite);
    }

    void changerAxeEtSens(){
        //Si Axe positif
        //Si Axe X
        //Si Tourne à gauche
        if(sensAxe.equals(SensAxe.POSITIF) && axeRobot.equals(AxeRobot.X) && tourneAgauche()){
            axeRobot= AxeRobot.Y;
            sensAxe=SensAxe.POSITIF;
        }
        //Si Tourne à droite
        else if(sensAxe.equals(SensAxe.POSITIF) && axeRobot.equals(AxeRobot.X) && tourneAdroite()){
            axeRobot= AxeRobot.Y;
            sensAxe=SensAxe.NEGATIF;
        }

        //Si Axe Y
        //Si Tourne à gauche
        else if(sensAxe.equals(SensAxe.POSITIF) && axeRobot.equals(AxeRobot.Y) && tourneAgauche()){
            axeRobot= AxeRobot.X;
            sensAxe=SensAxe.NEGATIF;
        }
        //Si Tourne à droite
        else if(sensAxe.equals(SensAxe.POSITIF) && axeRobot.equals(AxeRobot.Y) && tourneAdroite()){
            axeRobot= AxeRobot.X;
            sensAxe=SensAxe.POSITIF;
        }

        //Si Axe negatif
        //Si Axe X
        //Si Tourne à gauche
        else if(sensAxe.equals(SensAxe.NEGATIF) && axeRobot.equals(AxeRobot.X) && tourneAgauche()){
            axeRobot= AxeRobot.Y;
            sensAxe=SensAxe.NEGATIF;
        }
        //Si Tourne à droite
        else if(sensAxe.equals(SensAxe.NEGATIF) && axeRobot.equals(AxeRobot.X) && tourneAdroite()){
            axeRobot= AxeRobot.Y;
            sensAxe=SensAxe.POSITIF;
        }

        //Si Axe Y
        //Si Tourne à gauche
        else if(sensAxe.equals(SensAxe.NEGATIF) && axeRobot.equals(AxeRobot.Y) && tourneAgauche()){
            axeRobot= AxeRobot.X;
            sensAxe=SensAxe.POSITIF;
        }
        //Si Tourne à droite
        else if(sensAxe.equals(SensAxe.NEGATIF) && axeRobot.equals(AxeRobot.Y) && tourneAdroite()){
            axeRobot= AxeRobot.X;
            sensAxe=SensAxe.NEGATIF;
        } else {

        }
    }





    //Définir la position du robot
    void setPositionX() {
        if (sensAxe == SensAxe.POSITIF) {
            this.positionX += distanceParcourue();
        } else {
            this.positionX -= distanceParcourue();
        }
    }

    void setPositionY(){
        if (sensAxe == SensAxe.POSITIF) {
            this.positionY += distanceParcourue();
        } else {
            this.positionY -= distanceParcourue();
        }
    }


    //Algo
    void definirPosition(){
        //on récupère le nombre de tours de l'encodeur
        if (nbrToursDroite==nbrToursGauche) {
            if (axeRobot.toString().equals(AxeRobot.X.toString())) {
                setPositionX();
            } else {
                setPositionY();
            }
        } else {
            changerAxeEtSens();
        }
    }

    public static void main(String[] args) {

        //test effectuer un carré
        RobotPosition robotPosition = RobotPosition.getInstance();
        robotPosition.nbrToursDroite=10;
        robotPosition.nbrToursGauche=10;
        robotPosition.definirPosition();
        System.out.println("position du robot en x : " + robotPosition.positionX);
        System.out.println("position du robot en  y : " + robotPosition.positionY);
        System.out.println("axe actuel du robot : " + robotPosition.axeRobot);
        System.out.println("\n");

        robotPosition.nbrToursDroite=9;
        robotPosition.nbrToursGauche=10;
        robotPosition.definirPosition();
        System.out.println("position du robot en x : " + robotPosition.positionX);
        System.out.println("position du robot en  y : " + robotPosition.positionY);
        System.out.println("axe actuel du robot : " + robotPosition.axeRobot);
        System.out.println("à tourné à droite  : " + robotPosition.tourneAdroite());
        System.out.println("\n");

        robotPosition.nbrToursDroite=10;
        robotPosition.nbrToursGauche=10;
        robotPosition.definirPosition();
        System.out.println("position du robot en x : " + robotPosition.positionX);
        System.out.println("position du robot en  y : " + robotPosition.positionY);
        System.out.println("axe actuel du robot : " + robotPosition.axeRobot);
        System.out.println("\n");

        robotPosition.nbrToursDroite=11;
        robotPosition.nbrToursGauche=10;
        robotPosition.definirPosition();
        System.out.println("position du robot en x : " + robotPosition.positionX);
        System.out.println("position du robot en  y : " + robotPosition.positionY);
        System.out.println("axe actuel du robot : " + robotPosition.axeRobot);
        System.out.println("à tourné à gauche  : " + robotPosition.tourneAgauche());
        System.out.println("\n");

        robotPosition.nbrToursDroite=-10;
        robotPosition.nbrToursGauche=-10;
        robotPosition.definirPosition();
        System.out.println("position du robot en x : " + robotPosition.positionX);
        System.out.println("position du robot en  y : " + robotPosition.positionY);
        System.out.println("axe actuel du robot : " + robotPosition.axeRobot);


    }



}
