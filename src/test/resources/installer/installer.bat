

@echo off

ECHO Install .jar

SET GROUP_ID=com.ivoslabs.records
SET ARTIFACT_ID=ivos-records
SET VERSION=1.0.0
SET FILE=%ARTIFACT_ID%-%VERSION%
SET INSTALLER_CMD=mvn install:install-file -Dfile=%FILE%.jar -Dsources=%FILE%-sources.jar -Djavadoc=%FILE%-javadoc.jar -DgroupId=%GROUP_ID% -DartifactId=%ARTIFACT_ID% -Dversion=%VERSION% -Dpackaging=jar
 
ECHO %INSTALLER_CMD%

CALL %INSTALLER_CMD%
 
ECHO Artifact installed groupId=%GROUP_ID%  artifactId=%ARTIFACT_ID%  version=%VERSION%

SET /p exit=Press Enter to exit


