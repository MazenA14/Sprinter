# Sprinter
### Description
Automatic sprint creation for Agile workflow from input csv file

### Installation Process
1. Download Java JDK 22 by copying and pasting this link in preferred browser: https://download.oracle.com/java/22/latest/jdk-22_windows-x64_bin.exe 
2. Run jdk-22_windows-x64_bin.exe file and follow onscreen instructions 
3. Go to Windows Search and look up 'Edit the System Environment Variables'
4. Go to Advanced tab 
5. Click on 'Environment Variables'
6. Under 'User Variables', click 'New' and set variable name to 'JAVA_HOME' and variable value to 'C:\Program Files\Java\jdk-22\bin' and click 'OK'
7. Under 'System Variables', click on 'Path' variable and click 'Edit'
8. Click 'New' and write '%JAVA_HOME%\bin' and click 'OK' on all open windows 
9. Run Sprinter.exe

### Input File Restrictions
1. File of type CSV
2. An entry without number of days write a zero
3. Any entry must be a number without letters
4. Don't write any commas in the file
5. No cell merging -> Each cell on its own
