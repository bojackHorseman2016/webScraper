### Build
- Requires JDK8
- Run "mvn test" to run the Unit Tests on their own
- Run "mvn clean install" to generate an executable-Jar in the target directory 

### Run
- Requires Java 8 to run, run as an executable jar:
- java -jar webscraper-0.0.1-SNAPSHOT.jar

### Notes
- Uses JavaFX WebEngine since this can deal with JavaScript and DOM manipulation (this makes it pretty slow though)
