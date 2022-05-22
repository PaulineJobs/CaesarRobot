#include <Servo.h>
#include "Ultrasonic.h"

//constantes
#define pinAvance 8  // définition d'une constante pour la commande de l'avance du robot
#define pinMaDirection 9  // définition d'une constante pour la commande de direction du robot

//objets
Servo avance;  // création de l'objet Servo pour controller l'avance du robot
Servo maDirection;  // création de l'objet Servo pour controller la direction du robot

// création des objets Ultrasonic pour utiliser les capteurs à ultrason
Ultrasonic ultrasonAvant(5,27); //Trig, Echo    1 SCOTCH  repères câblage
Ultrasonic ultrasonDroite(6,26);//              2 SCOTCH
Ultrasonic ultrasonGauche(4,25);//              0 SCOTCH

//variables
//par defaut la variable control prend la valeur correspondant à l'arret
int control = 115;  //115 en decimal correspond à '0' en ascii

int finRota = 0;  // variable indiquant la fin d'une étape de rotaion du robot (machine à état)

int EtatPresent = 1;  // initialisation de la machine à état
int EtatSuivant = 1;


int av=0;  // sorties de la la machine à état
int arreter=0;
int droite=0;
int gauche=0;


int distanceAvant = 999;  // initialisation des variable lue par les capeurs à ultrason
int distanceDroite = 999;
int distanceGauche = 999;

// variable definisant le fonctionnement en mode manuel ou automatique
boolean man2 = true;  // par defaut le robot est en mode de fonctionnement manuel


void setup() {
  Serial.begin(9600);  // initialisation du port série pour la communication avec l'ordinateur (tests, débogage...)
  Serial1.begin(9600);  //initialisation du port série 1 pour la communication Bluetooth
  avance.attach(pinAvance);  // attribution des broches aux objets Servo
  maDirection.attach(pinMaDirection);

}


void loop() {
  
  if(Serial1.available()){  // réception des données en sur le port série correspondant au Bluetooth
    control = Serial1.read();
    // Serial.print("control = ");
    // Serial.println(control);  // affichage des données sur le port serie USB
  }

  // si le robot reçoit 'm' alors il passe en mode automatique, sinon le robot repasse en mode manuel
  if(control == 109){// 109 = 'm' en ascii  
    man2 = false;
  }else{
    man2 = true;
  }
  
  // Serial.print("man = ");
  // Serial.println(man2);  // affichage de la variable man2 sur le port série USB

  // si man2 == true, alors on execute le code pour controller le robot en mode manuel
  if(man2){
    // mode manuel
    switch(control){
      case 117 :// 117 = 'u' en ascii (Up)
        avancer();
        break;
      case 100 :// 100 = 'd' en ascii (Down)
        reculer();
        break;
      case 114 :// 114 = 'r' en ascii (Right)
        tournerSensAHManu();
        control = 115;
        break;
      case 108 : // 108 = 'l' en ascii (Left)
        tournerSensHManu();
        control = 115;
        break;
      case 115 : // 115 = 's' en ascii (Stop)
        arret();
        break;
    }
    delay(10);

  // si man2 == false, alors on execute la machine à état pour diriger le robot
  }else{
    mesureDisAvant();  // appel des fonctions de mesure de distance
    mesureDisGauche(); 
    mesureDisDroite(); 
  
    /************/
    /***Bloc F***/ 
    /************/
  
    switch(EtatPresent){
      case 1  : if(distanceAvant<50){
                  EtatSuivant=2;
                }
                break;
  
      case 2  : if(distanceDroite<40 && distanceGauche>40){
                  EtatSuivant=3;
                }
                /*else if(distanceGauche<20 && distanceDroite>20){
                  EtatSuivant=4;
                }
                */
                else{
                  EtatSuivant=4;
                }
                break;
  
      case 3  : if(finRota==1){
                  finRota=0;
                  EtatSuivant=6;
                }
                break;
  
      case 4  : if(finRota==1){
                  finRota=0;
                  EtatSuivant=6;
                }
                break;
  
      case 5  : if(distanceAvant>20){
                  EtatSuivant=6;
                }
                break;
  
      case 6  : if(distanceAvant>20){
                  EtatSuivant=1;
                }
                break;
  
      default : arret();
                break;
                
    }
  
    /************/
    /***Bloc M***/ 
    /************/
  
    EtatPresent=EtatSuivant;  // mise à jour des états
  /*
    Serial.print("Etat Present : ");
    Serial.print(EtatPresent);
    Serial.print(" , ");
    */
    /************/
    /***Bloc G***/ 
    /************/
    
    av=(EtatPresent==1);
    arreter=(EtatPresent==2)||(EtatPresent==6);
    droite=(EtatPresent==4);
    gauche=(EtatPresent==3);
  /*
    // Affichage sur le port série USB
    Serial.print("avance : ");
    Serial.print(av);
    Serial.print(" , ");
  
    Serial.print("arreter : ");
    Serial.print(arreter);
    Serial.print(" , ");
  
    Serial.print("droite : ");
    Serial.print(droite);
    Serial.print(" , ");
  
    Serial.print("gauche : ");
    Serial.print(gauche);
    Serial.print(" , ");
  
    Serial.print("distAV : ");
    Serial.print(distanceAvant);
    Serial.print(" , ");
  
    Serial.print("distD : ");
    Serial.print(distanceDroite);
    Serial.print(" , ");
  
    Serial.print("distG : ");
    Serial.print(distanceGauche);
    Serial.println(" , ");
    */

    // déplacement du robot en fonction des sorties de la machine à état
    if(av==1){
      avancer();
    }
    if(arreter==1){
      arret();
      delay(1000);
    }
    if(droite==1){
      tournerSensAHAuto();
      delay(1000);
    }
    if(gauche==1){
      tournerSensHAuto();
      delay(1000);
    }
  }
}
  

/////////////////////////////////////////////////////////////////////// Mode Automatique /////////////////////////////////////////////

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

void arret(){  // arreter le robot
  avance.write(90);
  maDirection.write(90);
}

void avancer(){  // faire avancer le robot
 avance.write(80);
}

void reculer(){  // faire reculer le robot
  avance.write(120);
}

void tournerSensHAuto(){  // faire tourner le robot dans le sens horaire de 90° (mode Automatique)
  maDirection.write(84);
  delay(150);
  finRota=1;
}

void tournerSensAHAuto(){  // faire tourner le robot dans le sens anti-horaire de 90° (mode Automatique)
  maDirection.write(96);
  delay(150);
  finRota=1;
}

void tournerSensHManu(){  // faire tourner le robot dans le sens horaire de 90° (mode Manuel)
  maDirection.write(60);
  delay(360);
}

void tournerSensAHManu(){  // faire tourner le robot dans le sens anti-horaire de 90° (mode Manuel)
  maDirection.write(120);
  delay(360);
}

void mesureDisAvant(){  // donne la distance mesuré par le capteur à l'avant 
  distanceAvant = ultrasonAvant.read();
  /*Serial.print(distanceAvant);
  Serial.print(" , ");*/
}

void mesureDisDroite(){  // donne la distance mesuré par le capteur à droite
  distanceDroite = ultrasonDroite.read();
 /* Serial.print(distanceDroite);
   Serial.print(" , "); */
}

void mesureDisGauche(){  // donne la distance mesuré par le capteur à gauche
  distanceGauche = ultrasonGauche.read();
 // Serial.println(distanceGauche);
}
