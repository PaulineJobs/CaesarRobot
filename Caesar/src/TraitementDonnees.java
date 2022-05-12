


public class TraitementDonnees extends Thread{
    //attributs
    CommunicationRobot communicationRobot;

    //constructeur
    public TraitementDonnees () {
    }

    public  void run() {
            try {
                Thread.sleep(1000) ;
                while(communicationRobot.obstaclePosition.robotPosition.etatRobot.equals(EtatRobot.ARRET)){
                    communicationRobot.lancerCommunication();
                }

            }  catch (InterruptedException e) {

                // gestion de l'erreur
            }
        }
}


