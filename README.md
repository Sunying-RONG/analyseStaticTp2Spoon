# analyseStaticTp2Spoon
1. Git clone https://github.com/Sunying-RONG/analyseStaticTp2Spoon.git
2. In eclipse, import maven project analyseStaticTp2Spoon
3. Change JRE System Library to version 8
4. Add maven dependencies (should be included already in pom.xml)
5. Add Referenced Libraries : commons-io-2.4.jar  
Build Path - Configure Build Path - Java Build Path - Libraries - Add JARs
(commons-io-2.4.jar in the root of the project analyseStaticTp2)
6. Run /analyseStaticTp2Spoon/src/main/java/main/CodeGenerationProcessorMain.java  
Input test project location.  
Input "1"  
7. Generated "graphCouplagePondere.png" will be in root of "analyseStaticTp2Spoon" project
