#include <Servo.h>
#include "Ultrasonic.h"

//constantes
#define pinAvance 8
#define pinMaDirection 9
const float pi = 3.1415;

//objets
Servo avance;
Servo maDirection;

Ultrasonic ultrasonAvant(5,27); //Trig, Echo    1 SCOTCH 
Ultrasonic ultrasonDroite(6,26);//              2 SCOTCH
Ultrasonic ultrasonGauche(4,25);//              0 SCOTCH

//variables
int control = 115;//0 en ascii
int prevControl = 115; 

int finRota = 0;

int EtatPresent = 1;
int EtatSuivant = 1;

int av=0;
int arreter=0;
int droite=0;
int gauche=0;

int distanceAvant = 999;
int distanceDroite = 999;
int distanceGauche = 999;

int man = 1;

void setup() {
  Serial.begin(9600);
  Serial1.begin(9600); 
  avance.attach(pinAvance);
  maDirection.attach(pinMaDirection);

}

void loop() {
  
  if(Serial1.available()){//reception des donnees en bluetooth
    control = Serial1.read();
    Serial.print(control);
  }

  if(control == 109){
    if(man == 0){
      man = 1;
    }else{
      man = 0;
    }
  }
  
  if(man == 1){
    //mode manuel
    switch(control){
    case 117 ://117
      avancer();
      break;
    case 100 ://100
      reculer();
      break;
    case 114 ://114
      tournerSensAH();
      control = 115;
      break;
    case 108 : //108
      tournerSensH();
      control = 115;
      break;
    case 115 : //115
      arret();
      break;
    }
    delay(10);
    
  }else{
    mesureDisAvant();
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
  
    EtatPresent=EtatSuivant;
  
    Serial.print("Etat Present : ");
    Serial.print(EtatPresent);
    Serial.print(" , ");
    
    /************/
    /***Bloc G***/ 
    /************/
  
    av=(EtatPresent==1);
    arreter=(EtatPresent==2)||(EtatPresent==6);
    droite=(EtatPresent==4);
    gauche=(EtatPresent==3);
  
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
    
    if(av==1){
      avancer();
    }
    if(arreter==1){
      arret();
      delay(1000);
    }
    if(droite==1){
      tournerSensAH();
      delay(1000);
    }
    if(gauche==1){
      tournerSensH();
      delay(1000);
    }
  }
}
  

/////////////////////////////////////////////////////////////////////// Mode Automatique /////////////////////////////////////////////

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

void arret(){
  avance.write(90);
  maDirection.write(90);
}

void avancer(){//int vitesse
 //avance.write(60);
 avance.write(80);
}

void reculer(){
  avance.write(120);
}

void tournerSensH(){
  maDirection.write(84);
  //delay(360);
  delay(150);
  finRota=1;
}

void tournerSensAH(){
  maDirection.write(96);
  //delay(360);
  delay(150);
  finRota=1;
  //ajouter le gyro pour controler la rotation
}

void mesureDisAvant(){
  distanceAvant = ultrasonAvant.read();
  /*Serial.print(distanceAvant);
  Serial.print(" , ");*/
}

void mesureDisDroite(){
  distanceDroite = ultrasonDroite.read();
 /* Serial.print(distanceDroite);
   Serial.print(" , "); */
}

void mesureDisGauche(){
  distanceGauche = ultrasonGauche.read();
 // Serial.println(distanceGauche);
  }
