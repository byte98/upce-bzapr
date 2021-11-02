# Basics of programming
This repository contains all sources related to subject 'Basics of programming using Java' on University of Pardubice

>If you plan to use this repository during your studies, please provide link to this repository or it could be considered to be plagiarism.

## Content
Content of repositroy is basically tutorials and semestral project.

### Tutorials
 - [Tutorial 01](https://github.com/byte98/upce-bzapr/tree/master/tut01) - Introduction to JAVA (variables, data types, ...)
 - [Tutorial 02](https://github.com/byte98/upce-bzapr/tree/master/tut02) - Conditions
 - [Tutorial 03](https://github.com/byte98/upce-bzapr/tree/master/tut03) - Cycles

If you are looking for more tutorials from Java programming, i recommend you to look at my repository [Algorithmization and Programming Practicum](https://github.com/byte98/upce-bpalp). This repository ends with third tutorial, because i skipped other ones by doing semestral project.

### Semestral project
I have created simple program for selling train tickets called 'JTicket'. You can find it [here](https://github.com/byte98/upce-bzapr/tree/master/SemestralProject). If you are looking for semestral project only, you can find actual releases [here](https://github.com/byte98/upce-bzapr/tree/master/SemestralProject/release).

#### Assignment

Assignment of semestral project is just list of necessary Java 'features' which are required to use in semestral project. Basics of Java (such as variables, data types, operators, etc.) are not listed.

*Note: not all examples are listed*

- Static variables and methods
  - Implemented in clases which implements singleton design pattern (examples: [`Controller`](https://github.com/byte98/upce-bzapr/blob/master/SemestralProject/src/main/java/cz/upce/fei/skodaj/bzapr/semestralproject/Controller.java), [`Stations`](https://github.com/byte98/upce-bzapr/blob/master/SemestralProject/src/main/java/cz/upce/fei/skodaj/bzapr/semestralproject/data/Stations.java), [`Tariffs`](https://github.com/byte98/upce-bzapr/blob/master/SemestralProject/src/main/java/cz/upce/fei/skodaj/bzapr/semestralproject/data/Stations.java), [`Distances`](https://github.com/byte98/upce-bzapr/blob/master/SemestralProject/src/main/java/cz/upce/fei/skodaj/bzapr/semestralproject/data/Distances.java))
- Abstract class
  - Implemented in class [`Tariff`](https://github.com/byte98/upce-bzapr/blob/master/SemestralProject/src/main/java/cz/upce/fei/skodaj/bzapr/semestralproject/data/Tariff.java), which is parent to available types of tariffs: [`DistanceTariff`](https://github.com/byte98/upce-bzapr/blob/master/SemestralProject/src/main/java/cz/upce/fei/skodaj/bzapr/semestralproject/data/DistanceTariff.java) and [`ZoneTariff`](https://github.com/byte98/upce-bzapr/blob/master/SemestralProject/src/main/java/cz/upce/fei/skodaj/bzapr/semestralproject/data/ZoneTariff.java)
- Working with files
  - Text files (examples: [`Tariffs`](https://github.com/byte98/upce-bzapr/blob/master/SemestralProject/src/main/java/cz/upce/fei/skodaj/bzapr/semestralproject/data/Stations.java)([`SaveTariffs()`](https://github.com/byte98/upce-bzapr/blob/master/SemestralProject/src/main/java/cz/upce/fei/skodaj/bzapr/semestralproject/data/Tariffs.java#L173), [`LoadTariffs()`](https://github.com/byte98/upce-bzapr/blob/master/SemestralProject/src/main/java/cz/upce/fei/skodaj/bzapr/semestralproject/data/Tariffs.java#L201)), [`Stations`](https://github.com/byte98/upce-bzapr/blob/master/SemestralProject/src/main/java/cz/upce/fei/skodaj/bzapr/semestralproject/data/Stations.java)([`SaveStations()`](https://github.com/byte98/upce-bzapr/blob/master/SemestralProject/src/main/java/cz/upce/fei/skodaj/bzapr/semestralproject/data/Stations.java#L110), [`LoadStations()`](https://github.com/byte98/upce-bzapr/blob/master/SemestralProject/src/main/java/cz/upce/fei/skodaj/bzapr/semestralproject/data/Stations.java#L69)), etc.)
  - Binary files (example: [`Distances`](https://github.com/byte98/upce-bzapr/blob/master/SemestralProject/src/main/java/cz/upce/fei/skodaj/bzapr/semestralproject/data/Distances.java)([`SaveDistances()`](https://github.com/byte98/upce-bzapr/blob/master/SemestralProject/src/main/java/cz/upce/fei/skodaj/bzapr/semestralproject/data/Distances.java#L80), [`LoadDistances()`](https://github.com/byte98/upce-bzapr/blob/master/SemestralProject/src/main/java/cz/upce/fei/skodaj/bzapr/semestralproject/data/Distances.java#L171)), etc.)
- Recursion
  - Implemented in [`NextStationsDistance`](https://github.com/byte98/upce-bzapr/blob/master/SemestralProject/src/main/java/cz/upce/fei/skodaj/bzapr/semestralproject/states/DistancesCreate.java#L201) as a method of [`DistancesCreate`](https://github.com/byte98/upce-bzapr/blob/master/SemestralProject/src/main/java/cz/upce/fei/skodaj/bzapr/semestralproject/states/DistancesCreate.java) [`State`](https://github.com/byte98/upce-bzapr/blob/master/SemestralProject/src/main/java/cz/upce/fei/skodaj/bzapr/semestralproject/states/State.java)  of program
- Exceptions
  - Handled when working with files (see *Working with files* item in this list)
- Overloading
  - Method overloading (example: in [`Controller`](https://github.com/byte98/upce-bzapr/blob/master/SemestralProject/src/main/java/cz/upce/fei/skodaj/bzapr/semestralproject/Controller.java) class method for changing actual state of program has two implementations [`ChangeState(String nextState)`](https://github.com/byte98/upce-bzapr/blob/master/SemestralProject/src/main/java/cz/upce/fei/skodaj/bzapr/semestralproject/Controller.java#L213) and [`ChangeState(String nextState, Map<String, String> data)`](https://github.com/byte98/upce-bzapr/blob/master/SemestralProject/src/main/java/cz/upce/fei/skodaj/bzapr/semestralproject/Controller.java#L234))
  - Constructor overloading (example: new [`Station`](https://github.com/byte98/upce-bzapr/blob/master/SemestralProject/src/main/java/cz/upce/fei/skodaj/bzapr/semestralproject/data/Station.java) can be created [with](https://github.com/byte98/upce-bzapr/blob/f6f8d8146ad8caeaf012b0d94b60fd9f65255872/SemestralProject/src/main/java/cz/upce/fei/skodaj/bzapr/semestralproject/data/Station.java#L59) or [without](https://github.com/byte98/upce-bzapr/blob/f6f8d8146ad8caeaf012b0d94b60fd9f65255872/SemestralProject/src/main/java/cz/upce/fei/skodaj/bzapr/semestralproject/data/Station.java#L47) known identifier of station)
- String (*implemented just because its in requirements*)
  - StringBuilder (example: used to make list of all available stations [`GenerateTableRows()`](https://github.com/byte98/upce-bzapr/blob/master/SemestralProject/src/main/java/cz/upce/fei/skodaj/bzapr/semestralproject/data/Stations.java#L372))
  - StringBuffer (example: used to make list of all available tariffs [`GenerateTariffsTableRows()`](https://github.com/byte98/upce-bzapr/blob/master/SemestralProject/src/main/java/cz/upce/fei/skodaj/bzapr/semestralproject/data/Tariffs.java#L99))

