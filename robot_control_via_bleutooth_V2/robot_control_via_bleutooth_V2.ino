/*
debut = 26
stop = 90
fin = 159
*/
#include <Encoder.h>
#include <Servo.h>
#include <SoftwareSerial.h>

//constantes
#define ENCA 2
#define ENCB 3
#define pinAvance 5
#define pinMaDirection 6

//objets
SoftwareSerial bluetoothSerial(11, 12); // RX, TX
Servo avance;
Servo maDirection;
Encoder codeur(ENCA, ENCB);

//variables
int control = 115;//0 en ascii
int prevControl = 115; 

float distance = 0;//
long positionLeft  = -999;//200 000 tours avant overflow
long newLeft = 0;
  //--------------------------------------------------------------------------------------------------------------
void setup() {
  Serial.begin(9600);
  bluetoothSerial.begin(9600);
  pinMode(ENCA, INPUT);
  pinMode(ENCB, INPUT);
  avance.attach(pinAvance);
  maDirection.attach(pinMaDirection);
}
  //--------------------------------------------------------------------------------------------------------------
void loop() {
  
  if(bluetoothSerial.available()){//reception des donnees en bluetooth
    control = bluetoothSerial.read();
  }
  //--------------------------------------------------------------------------------------------------------------
  newLeft = codeur.read();//lire la position du codeur
  if (newLeft != positionLeft) {
    //Serial.print("Left = ");
    //Serial.print(newLeft);
    //Serial.print("   ");
    positionLeft = newLeft;
    distance = 0.0314166 * newLeft;//calcule distance en mm
    Serial.println(distance);
  }
  // si on envoie un char sur Serial,
  // on remet a zero le conteur.
  if (Serial.available()) {
    Serial.read();
    codeur.write(0);
  }
  //--------------------------------------------------------------------------------------------------------------
  //controle moteur
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
}
//--------------------------------------------------------------------------------------------------------------
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

void ajusterSensH(){// pour ajuster la direction lors du mode automatique
  maDirection.write(60);
  delay(20);
}

void ajusterSensAH(){// pour ajuster la direction lors du mode automatique
  maDirection.write(120);
  delay(20);
  //ajouter le gyro pour controler la rotation
}
