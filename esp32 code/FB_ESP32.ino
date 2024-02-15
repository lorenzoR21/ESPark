#include <FirebaseESP32.h>
#include <WiFi.h>

//Provide the token generation process info.
#include <addons/TokenHelper.h>

//Provide the RTDB payload printing info and other helper functions.
#include <addons/RTDBHelper.h>

/* 1. Define the WiFi credentials */
#define WIFI_SSID "iPhone di Lorenzo"
#define WIFI_PASSWORD "lorenzo0021"

/* 2. Define the API Key */
#define API_KEY "FiPIXl4N1qNusItJeXggB9AsJ7qBq93Y4BBjbUXp"

/* 3. Define the RTDB URL */
#define DATABASE_URL "https://espark-7ad35-default-rtdb.firebaseio.com/" 

//Define Firebase Data object
FirebaseData fbdo;

FirebaseAuth auth;
FirebaseConfig config;

#define trigPin1 13 // define TrigPin
#define echoPin1 14 // define EchoPin.
#define trigPin2 27 // define TrigPin
#define echoPin2 26 // define EchoPin.
#define trigPin3 25 // define TrigPin
#define echoPin3 33 // define EchoPin.
#define trigPin4 32 // define TrigPin
#define echoPin4 35 // define EchoPin.
#define MAX_DISTANCE 400 // Maximum sensor distance is rated at 400-500cm.
float timeOut = MAX_DISTANCE * 60;
int soundVelocity = 340; // define sound speed=340m/s

int n = 20;

float valSlot1;
int slot1;
float valSlot2;
int slot2;
float valSlot3;
int slot3;
float valSlot4;
int slot4;


void setup()
{

  Serial.begin(115200);

  pinMode(trigPin1,OUTPUT);// set trigPin to output mode
  pinMode(echoPin1,INPUT); // set echoPin to input mode
  pinMode(trigPin2,OUTPUT);
  pinMode(echoPin2,INPUT);
  pinMode(trigPin3,OUTPUT);
  pinMode(echoPin3,INPUT);
  pinMode(trigPin4,OUTPUT);
  pinMode(echoPin4,INPUT);

  delay(2000);
  WiFi.begin(WIFI_SSID, WIFI_PASSWORD);
  Serial.print("Connecting to Wi-Fi");
  while (WiFi.status() != WL_CONNECTED)
  {
    Serial.print(".");
    delay(300);
  }
  Serial.println();
  Serial.print("Connected with IP: ");
  Serial.println(WiFi.localIP());
  Serial.println();

  Serial.printf("Firebase Client v%s\n\n", FIREBASE_CLIENT_VERSION);

  // Assign the api key (required) 
  config.api_key = API_KEY;

  config.database_url = DATABASE_URL;

  Firebase.begin(DATABASE_URL, API_KEY);

  Firebase.setDoubleDigits(5);

}

void loop()
{
 
  delay(2000);


  valSlot1 = getSonar(trigPin1, echoPin1);
  valSlot2 = getSonar(trigPin2, echoPin2);
  valSlot3 = getSonar(trigPin3, echoPin3);
  valSlot4 = getSonar(trigPin4, echoPin4);

  Serial.printf("Distance 1: ");
  Serial.print(valSlot1); // Send ping, get distance in cm and print result 
  Serial.println("cm");

  Serial.printf("Distance 2: ");
  Serial.print(valSlot2); // Send ping, get distance in cm and print result 
  Serial.println("cm");

  Serial.printf("Distance 3: ");
  Serial.print(valSlot3); // Send ping, get distance in cm and print result 
  Serial.println("cm");

  Serial.printf("Distance 4: ");
  Serial.print(valSlot4); // Send ping, get distance in cm and print result 
  Serial.println("cm");

  if (valSlot1 >= 15)
  {
    slot1 = 0;
  }
  else
  {
    slot1 = 1;
  }
  
  if (valSlot2 >= 15)
  {
    slot2 = 0;
  }
  else
  {
    slot2 = 1;
  }
  
  if (valSlot3 >= 15)
  {
    slot3 = 0;
  }
  else
  {
    slot3 = 1;
  }
  
  if (valSlot4 >= 15)
  {
    slot4 = 0;
  }
  else
  {
    slot4 = 1;
  }
  
  if (Firebase.ready()) 
  {
    
    Firebase.setInt(fbdo, "/parking/p1/slot/s1/status", slot1);
    Firebase.setInt(fbdo, "/parking/p1/slot/s2/status", slot2);
    Firebase.setInt(fbdo, "/parking/p1/slot/s3/status", slot3);
    Firebase.setInt(fbdo, "/parking/p1/slot/s4/status", slot4);
    delay(200);


    Serial.println("");
  
    Serial.println();
    Serial.println("------------------");
    Serial.println();
  }
}

float getSonar(int trigP, int echoP) {
  unsigned long pingTime;
  float distance;
  // make trigPin output high level lasting for 10μs to triger HC_SR04
  digitalWrite(trigP, HIGH); 
  delayMicroseconds(10);
  digitalWrite(trigP, LOW);
  // Wait HC-SR04 returning to the high level and measure out this waitting time
  pingTime = pulseIn(echoP, HIGH, timeOut); 
  // calculate the distance according to the time
  distance = (float)pingTime * soundVelocity / 2 / 10000; 
  return distance; // return the distance value
}