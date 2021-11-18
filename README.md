# Capstone-Project-8
# Poised Firm: Databases, Dependency Diagram, ERD Diagram, Debugging, Software Testing, Refactoring, Software Documentation, Java Collections Framework
# Project Information
# Description

This Poised Firm is a Programme written in Java language. This Programme was designed to create a project management system for a small structural engineering firm called “Poised”.
Poised does the engineering needed to ensure the structural integrity of various buildings. The Java program was created to help the company to keep track of the many projects that they work on.

# Features (How it works?)

The UML(Unified Modeling Language) diagram is used to display an overall plan of the project to meet the Poised Firm requirments for the Project Management System. The main 'Menu'(PoisedFirm) Java class was created to call methods from sub-classes(depending on what option the user chooses on the main menu). Once the project is completed, the programme will be able to record all the details about new projects, update details on existing projects, finalise existing projects, view projects that are incomplete or overdue, and to find and select a project by number or name. The programme was enhanced by doing the following:

> Exception handling was incoporated by including at least two try-catch blocks(for error handling)
> The code was properly debugged(the runtime and logical errors were identified and corrected)
> The indentation and formatting of the code was fixed so that it adheres to the guidelines provided here (https://google.github.io/styleguide/javaguide.html#s4-formatting)
> All the names of variables, classes, methods, etc. adhere to the guidelines provided (https://google.github.io/styleguide/javaguide.html#s5.1-identifier-names)
> The code was refactored to improve the quality and readability of the code

This is the last stage of the Project. The programme was extended to do the following:

o Reads details about existing projects from a text file and uses this information to create a list of project objects.
o Allows a user to add new objects to this list.
o Allows a user to select and update or finalise any project on the list.
o Allows a user to see a list of projects that still need to be completed.
o Allows a user to see a list of projects that are past the due date.
o Writes the updated details about the projects to the text file when the program ends

> The programme was enhanced by incorporating database programmming into the project management system in place of external text files to store data.
> A database called 'PoisePMS' was created to store project data, and the dependency diagram, as well as the Entity Relationship diagram in this repository, show the relationships between the tables in the PoisePMS database.

Inside the 'src' folder of this projects contents, there are Java classes that run the  Poised Firm Project Management . These Java classes includes the following:


> The 'PoisedCheck' Java class which is the Superclass. This class contains a code that is used to check for users inputs. This class has three try/catch methods that check if the user has entered a String(words/sentence), integer(a whole number) or double (number with decimals) correctly. If not, the user will be prompt to re-enter the correct input repeatedly. This is called defensive programming, it is used to ensures that the program is robust and able to deal with user errors.

> The 'ProjectsDetails' Java class consists of a method used to add a new project object. The new project information is written to a text file('CurrentProjects.txt'). When this method is called in the main menu class('PoisedMenu'), the user is asked to for the new project details (project number, project name, type of building, physical address, ERFNumber, project cost, amount paid, deadline), shows the project object once it's completed, the project details are then written to a text file.

> The 'PersonDetails' Java class consists of a constructor method, and a method which allow the user to create new person object(e.g customer, contractor, architecture) with their specific information(type of person, name, telephone number, emailaddress, physical address) and then shows the person object in a friendly-manner once complete

> The 'PoisedFirmMenu' Java class is the 'Main' class that runs the entire programme. When the programme is exercuted it displays the menu to the user with several options such as:

1. "View Existing Projects"
2. "Add a New Project"
3. "Update Existing Project Infomation"
4. "Finalize the Project"
5. "View Incomplete Projects"
6. "View Overdue Projects"
7. "Find a Specific Project"
8. "Delete a Specific Project
9. "Exit program"

Based on the user option, methods corresponding with the menu options will be called out. The information is then displayed in a friendly-manner. The user will return to the main menu after each option unless the choose to exit the programme

# How to use this Poised Firm programme?

* To run this programme, you need to download Java Development Kit (JDK) onto your computer's operating system 
* Link to download Python on your computer(e.g Mac, Linux, Windows etc.), https://www3.ntu.edu.sg/home/ehchua/programming/howto/JDK_HowTo.html#jdk-install
* Once Java Development Kit (JDK) is downloaded, you need IDLE(An Intergrated Development Environment). IDLE is a safe environment to view and run your code. 
*  A link for an IDE called 'Eclipse', https://www.ntu.edu.sg/home/ehchua/programming/howto/EclipseJava_HowTo.html 

# Contributors

I worked on this project on my own and it was reviewed and commented on by my mentor at Hyperion Development

