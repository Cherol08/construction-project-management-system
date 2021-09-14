import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * This java program manages construction projects by storing pending and
 * complete project details, as well as maintaining updates about the project.
 *
 * @author Cherol Phoshoko
 * @version 16.0.2, 2021-09-09
 */
public class PoisedProject {
    /**
     * Main method of the program used to access the existing projects, add updates,
     * finalize them, or even add new projects at runtime. <br>
     *
     * @param args The command line arguments
     * @throws IOException if error occurs
     */

    public static void main(String[] args) throws IOException {
        //  Field variables used for project and persons' details.
        String name, surname;
        String userType;
        String telephoneNum, email, physicalAdd;
        String projectName, buildingType, erfNum, deadline;
        double totalFee, totalPaid;
        ProjectFile projectList = new ProjectFile();

        // User prompted to add new project details when program runs
        // System.out.println("1- ADD NEW PROJECT 2- Access existing file:");
        while (true) {
            System.out.println("""
                    \nProgram menu:
                    1 - Display all projects
                    2 - Add new project
                    3 - Edit existing project
                    4 - Display incomplete projects
                    5 - Display overdue projects
                    0 - exit
                    Enter option:""");

            Scanner input = new Scanner(System.in);
            int option = input.nextInt();
            if (option == 1) {
                projectList.printAll();

            } else if (option == 2) {
                Project newProject;
                input.nextLine();
                System.out.println("\nEnter Project name:");
                projectName = input.nextLine();
                System.out.println("Enter building type:");
                buildingType = input.nextLine();
                System.out.println("Enter building address:");
                physicalAdd = input.nextLine();
                System.out.println("Enter building ERF number:");
                erfNum = input.nextLine();
                System.out.println("Enter project deadline(yyyy-MM-dd):");
                deadline = input.nextLine();
                while (validDateFormat(deadline) == false) {
                    System.out.println("\nInvalid date format. Try again.");
                    System.out.println("Enter project deadline(yyyy-MM-dd):");
                    deadline = input.nextLine();
                }
                while (true) {
                    try {
                        System.out.println("Overall Project Fee:");
                        totalFee = input.nextDouble();
                        System.out.println("Total paid to date:");
                        totalPaid = input.nextDouble();

                        // new project Object
                        newProject = new Project(projectName, buildingType, physicalAdd, erfNum, deadline, totalFee,
                                totalPaid);
                        break;
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid Input!\nPlease enter valid amount.");
                        input.nextLine();
                    }
                }
                // created person objects, they're accessible out of the loops
                Person customer = null;
                Person architect = null;
                Person contractor;

                // loop used to add details of project customer, architect and contractor
                System.out.println("\nCustomer Details".toUpperCase());
                input.nextLine();
                int loop = 0;

                while (true) {
                    loop++;
                    System.out.println("\nEnter name:");
                    name = input.nextLine();
                    System.out.println("Enter surname:");
                    surname = input.nextLine();
                    System.out.println("Enter telephone number:");
                    telephoneNum = input.nextLine();
                    System.out.println("Enter email address:");
                    email = input.nextLine();
                    System.out.println("Enter physical address:");
                    physicalAdd = input.nextLine();

                    if (loop == 1) {
                        userType = "Customer";
                        customer = new Person(name, surname, userType, telephoneNum, email, physicalAdd);
                        if (projectName.isBlank()) {
                            // if project name not provided Building type & client surname used in place of.
                            newProject.projectName = newProject.buildingType + " " + surname;
                        }
                        System.out.println("\nProject Architect Details".toUpperCase());
                    }
                    if (loop == 2) {
                        userType = "Architect";
                        architect = new Person(name, surname, userType, telephoneNum, email, physicalAdd);
                        System.out.println("\nProject Contractor Details".toUpperCase());
                    }
                    if (loop == 3) {
                        userType = "Contractor";
                        contractor = new Person(name, surname, userType, telephoneNum, email, physicalAdd);
                        break;
                    }
                }
                // assert used to declare null values as false.
                System.out.println("\n\nInformation Successfully Saved!\n\n");
                System.out.println(newProject.projectDetails() + "\n\n");
                System.out.println(customer.personDetails() + "\n\n");
                System.out.println(architect.personDetails() + "\n\n");
                System.out.println(contractor.personDetails());

                projectList.addNewProject(newProject, customer, architect, contractor);

                //calling projectList methods with Project name as input specifies project to edit
            } else if (option == 3) {
                input.nextLine();
                String project2Edit;
                System.out.println("\nEnter project name:");
                project2Edit = input.nextLine();
                projectList.showProject(project2Edit);
                while (true) {
                    System.out.println("""
                            \n1 - Edit contractor's details
                            2 - Edit project deadline
                            3 - Edit Amount paid to date
                            4 - Finalize project
                            0 - Return to main menu
                            """);

                    int choice = input.nextInt();
                    if (choice == 1) {
                        input.nextLine();
                        System.out.println("\nEnter contractor's new telephone number:");
                        telephoneNum = input.nextLine();
                        System.out.println("Enter contractor's new email Address: ");
                        email = input.nextLine();
                        projectList.changeContractorDetails(project2Edit, telephoneNum, email);

                    } else if (choice == 2) {
                        input.nextLine();
                        System.out.println("\nEnter New deadline(yyyy-MM-dd):");
                        deadline = input.nextLine();
                        while ((validDateFormat(deadline)) == false) {
                            System.out.println("\nInvalid date format. Try again.");
                            System.out.println(validDateFormat(deadline));
                        }
                        projectList.changeDeadline(project2Edit, deadline);

                    } else if (choice == 3) {
                        while (true) {
                            try {
                                System.out.println("\nEnter amount paid:");
                                Double paidAmount = input.nextDouble();
                                projectList.changeAmountPaid(project2Edit, paidAmount);
                                break;
                            } catch (InputMismatchException e) {
                                System.out.println("Invalid Input!\nPlease enter valid amount.");
                                input.nextLine();
                            }
                        }

                    } else if (choice == 4) {
                        projectList.finalizeProject(project2Edit);
                    } else if (choice == 0) {
                        break;
                    } else {
                        System.out.println("Invalid option!");
                    }
                }

            } else if (option == 4) {
                projectList.incompleteProjects();
            } else if (option == 5) {
                try {
                    projectList.overdueProjects();
                } catch (ParseException e) {
                    System.out.println("Parse Exception occurred!");
                }

            } else if (option == 0) {
                System.out.println("\nLogout Successful!");
                break;
            }
        }
        //writes updated project object list, to projects file.
        projectList.writeToFile();
    }

    /**
     * This method validates if user inputs the correct date format for the project
     * deadlines <br>
     *
     * @param deadline String will be formatted to a date
     * @return boolean value for valid date.
     */

    public static boolean validDateFormat(String deadline) {
        boolean valid;
        try {
            String dateFormat = "yyyy-MM-dd";
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(dateFormat)
                    .withResolverStyle(ResolverStyle.LENIENT);
            LocalDate date = LocalDate.parse(deadline, dateFormatter);
            valid = true;
        } catch (DateTimeParseException e) {
            valid = false;
        }
        return valid;
    }
}
