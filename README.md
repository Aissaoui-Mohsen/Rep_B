# neolians.com


## Launch tests

### Pre requirement

Install

 * JDK >= 1.8 
 * Git: https://git-scm.com/book/en/v2/Getting-Started-Installing-Git
 * Batch: For Windows users, install Git Batch: https://gitforwindows.org/ , For Linux users, install the shell bash
 * Maven > 3.16: https://maven.apache.org/install.html
 
Configure Environment variables:

* MAVEN_HOME=PATH_TO_MAVEN_DIRECTORY
* JAVA_HOME=PATH_TO_THE_JAVA_HOME # "/c/app/jdk1.8.0_191"
* PATH=%PATH%;%MAVEN_HOME%\bin;%JAVA_HOME%\bin

Get source:

```
git clone
```

## Test Execution

To execute test on the neolians.com environment: (@see exect.bat file)

```sh
git pull
mvn clean compile test
```

Execution with some parameters
```bat
mvn  
 -Dsut.test.environment=neolians.com \
 -Dbrowser=CHROME \
 -Dbrowser.headless=false \
 -Dtest=**/*.java \
 clean compile test
```

Report are available at:

```
$Project_Folder\reports\manualLaunch\report.html
```