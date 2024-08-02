# Sprinter
## Overview
This project was created for the Digital Demand & Solution Team within the Data and Digital Transformation Department at Orange Egypt during the July 2024 Summer Internship. 
It is a sprint-making automation desktop application written in Java and JavaFX which creates a sprint schedule using a user-input backlog table as a CSV file. 

## Features
* The user can upload any number of backlog documents (Refer to Input File Restrictions section).
* The user is asked to enter the preferred number of StoryPoints which will produce an intermediary output table containing the chosen features from the backlog (can be saved to device).
* The user can then generate the final sprint table which schedules the chosen features across an appropriate number of days and with respect to dependencies (e.g. Quality Control cannot start testing before a feature's development is done). This table can also be saved to the user's device.
* The user is allowed to change the sprint table's header names any number of times in order to assign to specific team members. 

## Installing and Running the Project
You must have a running Java version. This project was created using the 2022 version and backwards compatibility is not guaranteed.
1. Download Java JDK 22 by copying and pasting this link in preferred browser: https://download.oracle.com/java/22/latest/jdk-22_windows-x64_bin.exe 
2. Run jdk-22_windows-x64_bin.exe file and follow onscreen instructions 
3. Go to Windows Search and look up 'Edit the System Environment Variables'
4. Go to 'Advanced' tab 
5. Click on 'Environment Variables'
6. Under 'User Variables', click 'New' and set variable name to 'JAVA_HOME' and variable value to 'C:\Program Files\Java\jdk-22\bin' and click 'OK'
7. Under 'System Variables', click on 'Path' variable and click 'Edit'
8. Click 'New' and write '%JAVA_HOME%\bin' and click 'OK' on all open windows 
9. Run Sprinter.exe

### Input File Restrictions
Refer to --- 
1. File of type CSV
2. Any entry must be a number, no letters and no commas
3. Decimal numbers should be in increments of 0.5
4. For an entry with an undecided number of days, write a zero
5. No cell merging i.e. each cell on its own

## Collaborators
* [Haya Shalaby](https://github.com/HayaShalaby)

## License
Copyright 2024 Mazen Ahmed 
Copyright 2024 Haya Shalaby  

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
