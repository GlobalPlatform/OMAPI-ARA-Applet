set JC_HOME=%~dp0java_card_kit-2_2_1
set PATH=%PATH%;%JC_HOME%\bin

mkdir bin

rem USING Java version > 1.3
rem ------------------------
rem javac -g -source 1.3 -target 1.1 -classpath %JC_HOME%\lib\api.jar src\org\simalliance\javacard\omapitest1\*.java -d bin\
rem javac -g -source 1.3 -target 1.1 -classpath %JC_HOME%\lib\api.jar src\org\simalliance\javacard\omapitest2\*.java -d bin\
rem javac -g -source 1.3 -target 1.1 -classpath %JC_HOME%\lib\api.jar src\org\simalliance\javacard\omapitest3\*.java -d bin\
rem javac -g -source 1.3 -target 1.1 -classpath %JC_HOME%\lib\api.jar src\org\simalliance\javacard\omapitest4\*.java -d bin\

rem USING Java version 1.3
rem ----------------------
javac -g -target 1.1 -classpath %JC_HOME%\lib\api.jar src\org\simalliance\javacard\omapitest1\*.java -d bin\
javac -g -target 1.1 -classpath %JC_HOME%\lib\api.jar src\org\simalliance\javacard\omapitest2\*.java -d bin\
javac -g -target 1.1 -classpath %JC_HOME%\lib\api.jar src\org\simalliance\javacard\omapitest3\*.java -d bin\
javac -g -target 1.1 -classpath %JC_HOME%\lib\api.jar src\org\simalliance\javacard\omapitest4\*.java -d bin\
