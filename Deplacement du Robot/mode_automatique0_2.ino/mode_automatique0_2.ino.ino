/*
debut = 26
stop = 90
fin = 159
*/

/* Avec commande moteurs
  + bluetooth serie physique  */

#include <Encoder.h>
#include <Servo.h>
//#include <SoftwareSerial.h>
#include "Ultrasonic.h"

//constantes
#define ENCA 2
#define ENCB 3
#define pinAvance 8
#define pinMaDirection 9

//objets
Servo avance;
Servo maDirection;
Encoder codeur(ENCA, ENCB);

Ultrasonic ultrasonAvant(5,27); //Trig, Echo    1 SCOTCH 
Ultrasonic ultrasonDroite(6,26);//              2 SCOTCH
Ultrasonic ultrasonGauche(4,25);//              0 SCOTCH

//variables
int control = 115;//0 en ascii
int prevControl = 115; 
int distanceAvant = 999;
int distanceDroite = 999;
int distanceGauche = 999;

void setup() {
  Serial.begin(9600);
  Serial1.begin(9600); 

  pinMode(ENCA, INPUT);
  pinMode(ENCB, INPUT);
  avance.attach(pinAvance);
  maDirection.attach(pinMaDirection);
}

void loop() {

  mesureDisAvant();
//arret obstacle à l'avant
 /* if(distanceAvant < 40){
    control = 115;//on arrete
  }else{
    control = 117;//on avance
  }
  
  mesureDisGauche(); 
  mesureDisDroite(); 


  */
 if(Serial1.available()){
    control = Serial1.read();
    Serial.println(Serial1.read());
  }

  switch(control){
    case 117 ://117
      avancer();
      //Serial.println("AVANCER");
      break;
    case 100 ://100
      reculer();
      //Serial.println("RECULER");
      break;
    case 114 ://114
      tournerSensAH();
      control = 115;
      //Serial.println("ROTATION SENS HORAIRE");
      break;
    case 108 : //108
      tournerSensH();
      control = 115;
      //Serial.println("ROTATION SENS ANTI-HORAIRE");
      break;
    case 115 : //115
      arret();
      //Serial.println("ROTATION SENS ANTI-HORAIRE");
      break;
  }
  delay(50);
}

void arret(){
  avance.write(90);
  maDirection.write(90);
}

void avancer(){//int vitesse
  avance.write(60);
}

void reculer(){
  avance.write(120);
}

void tournerSensH(){
  maDirection.write(60);
  delay(360);
}

void tournerSensAH(){
  maDirection.write(120);
  delay(360);
  //ajouter le gyro pour controler la rotation
}

void mesureDisAvant(){
  distanceAvant = ultrasonAvant.read();
  Serial.print(distanceAvant);
  Serial.print(" , "); 
}

void mesureDisDroite(){
  distanceDroite = ultrasonDroite.read();
  Serial.print(distanceDroite);
   Serial.print(" , "); 
}

void mesureDisGauche(){
  distanceGauche = ultrasonGauche.read();
  Serial.println(distanceGauche);
    
}
