
#include <Servo.h>
Servo myservoS1; //gère les roues avant arrière
Servo myservoS2; //gère les rotations des roues
int diametreRoue = 0.12;

/*
//    attachInterrupt(digitalPinToInterrupt(broche), ISR, mode);
    attachInterrupt(digitalPinToInterrupt(2), ISR, FALLING );

int distance(){
  int nombreTourDeRoues = 0;
  float PérimetreRoue=
  
}*/


void setup() {
  myservoS1.attach(5); //S1 sur la pin 5 de l'arduino
  myservoS2.attach(6); //S2 sur la pin 5 de l'arduino
  myservoS1.writeMicroseconds(1500); //stop
  myservoS2.writeMicroseconds(1500); //stop
}

//Avance, prend en paramètre le delai en paramètre en milisecondes
void avancer(int delai){
  myservoS1.writeMicroseconds(1300);// les 4 rouent avancent 
  delay(delai);

}


//Recule, prend en paramètre le delai en paramètre en milisecondes
void reculer(int delai){
  myservoS1.writeMicroseconds(2000);
  delay(delai);
  
}

//Stop, prend en paramètre le delai en paramètre en milisecondes
void arreter(int delai){
   myservoS2.writeMicroseconds(1500);
   myservoS1.writeMicroseconds(1500);
   delay(delai);
}

//fait un demi tour à gauche
void demiTourGauche(){
  myservoS2.writeMicroseconds(1300); //roues gauches vers l'arrière et roues droites vers l'avant 
  delay(666); //pour faire un demi tour
}


//fait un demi tour à droite
void demiTourDroite(){
  myservoS2.writeMicroseconds(2000); //roues droite vers l'arrière et roues gauches vers l'avant 
  delay(666); //pour faire un demi tour
}

//Fait une rotation à droite pendant le delai indiqué
void TournerDroite(int delai){
  myservoS2.writeMicroseconds(2000); //roues droite vers l'arrière et roues gauches vers l'avant 
  delay(delai); //pour faire un demi tour
}

//Fait une rotation à gauche pendant le delai indiqué
void TournerGauche(int delai){
  myservoS2.writeMicroseconds(2000); //roues droite vers l'arrière et roues gauches vers l'avant 
  delay(delai); //pour faire un demi tour
}

void loop(){
  //essai
  avancer(2000);
  arreter(2000);
  reculer(2000); 
  arreter(2000);
  demiTourGauche();
  arreter(2000);
  demiTourDroite();
  arreter(2000);
 
}
