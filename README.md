# SIRS Project 
- Authors:
    - António Filipe, 92425;
    - Lúcia Silva, 92510;
    - Sancha Barroso, 92557;

---
### REQUIREMENTS:
- **Hypervisor; (Recommended: ubuntu 20.04 )**
    Must:
    - Be able to connect to an internal network or configuring one;
    - Java -> version 11 or higher (ex: openjdk 11.0.13 );
    - Apache Maven -> version 3.6.3 or higher;
    - npm framework -> version 6.14.4 or higher;
    - ( All the above can be found in the apt available packages in ubuntu. Just use `sudo apt install ******` )    
   
- **Android Studio;**
    - Make sure Gradle plugin is installed and active;
    - Create a device: Androidx86 Nexus 5x (Emulator);

_Other versions, systems or setups may be used but are untested so may not work_

---
### DEPLOYMENT:

`You will have two virtual machines running simultaneouslly, VM1 will run the server and VM2 will run the web application.`
`To edit the code you can use a terminal editor or another editor of your choosing like VScode`

- **VM1:**
1. Set up your internal network and choose your IP (ex: 192.168.10.1); 
2. Check to see if you can ping it from your computer outside the VM (PING 192.168.10.1). If not, check configuration again, something is wrong ; 
3. Open terminal and navigate to /complete folder with `cd /SIRS/complete/` ;
4. Go to ApplicationControler.java in /SIRS/complete/src/main/java/com/example/springboot/ApplicationControler.java and change the IP in the CrossOrigins to an IP in the same internal network as you chose (ex. 192.168.10.3) ;
5. Compile your project with `mvn clean` and then `mvn compile` ;
6. Start your server with `mvn spring-boot:run`. If for some reason maven isn't in the PATH you can also try `./mvnw spring-boot:run` ;
7. If everything goes normally, the server is now up and running;

- **VM2:**
1. Set up your internal network and choose the IP you chose earlier in the CrossOrigins (ex. 192.168.10.3);
2. Try and ping the other VM (VM1) with (PING 192.168.10.1) and also try and ping VM2 from outside the hypervisor. If erything is ok then proceed;
3. Navigate to desktop folder with `cd /SIRS/complete/`;
4. Go to DataService.js in `SIRS/desktop/src/DataService.js` and change *const SERVER_URL* to use the IP of VM1 (ex: `const SERVER_URL = 'https://192.168.10.1:8443'`);
5. Run `npm install` and if everything installs correctly start your application with `npm start`;
6. The browser will open in http://localhost:3000 and you need to change it to run on your IP  (ex. http://192.168.10.3:3000/ );
7. Due to problems with certificates, open another tab on your browser (ideally firefox) and try to acess the server website (https://192.168.10.1:8443);
8. When the authorization problem message appears choose continue anyway. This will put the website on firefoxe's trusted list and allow the comunication between the app and server;
9. The web application is now ready for use;


- **Android Studio:**
1. Open Android Studio on your computer;
2. Select `open project` and choose the folder SIRS/SmartphoneApplication/;
3. Android studio will automatically build the project and its dependencies because of the *.gradle* files.
4. Add a new device in Device Manager. Recomended: Android Nexus 5x. 
5. Open AssociateMobile.java in `/SIRS/SmartphoneApplication/app/src/main/java/com/example/smartphoneapplication/` and change all the IP's to the IP of your VM1 (ex. 192.168.10.1). It appears a couple times.
6. Repeat step 5 for AssociationCompleted.java.
7. Select this device for the project to run in.
8. `Build` the project and then `run` in.
9. A window with a phone should pop up.
10. Select the thre dots on the side (...) and the go to settings. In settings go to proxy and select Manual Proxy configuration. In hostname write the IP VM1 is running (ex. `192.168.10.1`) and if that doesn't work write the whole url `https://<your IP> (ex. `https://192.168.10.1`). Choose the port 8443. The press on apply. 

---


### Running

**Basic Usage**
1. To register, first use your Android Emulator press Associate account and select Generate Code. 
2. In your Desktop use the generated code to register.
3. After registing, use the TOTP in your phone to login.
4. When buying something use the phone to confirm the action
