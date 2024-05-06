@echo off
REM Check if the target/classes directory exists, if not, create it
if not exist "..\target\classes" mkdir "..\target\classes"

REM Navigate to the project's src directory where the Java files are located
cd /d %~dp0

REM Compile the Java files and save the class files in the target/classes directory
javac -d "..\target\classes" src\main\java\main\tucil_13522019\DictionaryLoader.java src\main\java\main\tucil_13522019\WordLadder.java src\main\java\main\tucil_13522019\WordLadderGreedy.java src\main\java\main\tucil_13522019\WordLadderUCS.java src\main\java\main\tucil_13522019\WordLadderAStar.java src\main\java\main\tucil_13522019\WordLadderCLI.java

REM Run the WordLadderCLI program from the target/classes directory
java -cp "..\target\classes" main.tucil_13522019.WordLadderCLI
