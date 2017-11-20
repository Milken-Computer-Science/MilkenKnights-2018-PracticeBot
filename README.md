# MilkenKnights-2018-PracticeBot
##### Code for Practice Bot used for prototyping
###### (Runs on the same frc.team1836.robot as the Madtown Code however this repo will be used for new code/testing while Madtown will keep the working code used for demos)

## Installation Instructions:
1. Install Java JDK 8 (macOS or Windows x64)
2. (ONLY ON WINDOWS) Go to Control Panel -> System and Security -> System -> Advanced System Settings -> Enviorment Variables. Add a new system variable called "JAVA_HOME" -> Click on Browse Directory and go to "C:\Program Files\Java\jdk1.8.0_151" (Java Version Number may differ)
2. Install Intellij IDEA (Either Version)
3. Go to File -> Settings -> Editor -> Code Style -> Java. Click the Gear Icon -> Import Scheme -> IntelliJ XML file -> then browse to the repo and select MilkenKnightsStyle.xml
4. Go to File -> Open and open the repo
A box will pop up -> Select Auto-Import and select JAVA_HOME if needed -> Click OK
5. WAIT UNTIL THE PROGRESS BAR AT THE BOTTOM IS FULL COMPLETE -> It will take a minute to download everything, but after that everything should be fully configured

## Code Deploy Instructions:
1. In Intellij, Click Terminal at the bottom right hand corner (You may have to click a square with a line below it and then open terminal). If the terminal option is unavailable go to step 3.
2. Type "gradlew deploy" (Without the quotes). The robot must be connected over usb, ethernet, or wifi
2. Install Intellij IDEA (Either Version)
3. Open Command Prompt or Terminal and navigate to the project directory ('cd ..' goes up a directory level and 'cd [directory_name' goes into a directory). Use 'dir' on windows or 'ls' on mac or linux to view the current directory
 

