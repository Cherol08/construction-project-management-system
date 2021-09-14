import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Formatter;
import java.util.Scanner;

/**
 * This class is used to create project list objects from a file containing
 * project information, which can accessed or modified accordingly. <br>
 *
 * @author Cherol Phoshoko
 * @version 16.0.2, 2021-09-09
 */
public class ProjectFile {
    ArrayList<Object> projectList = new ArrayList<>();
    ArrayList<Object> completeProjects = new ArrayList<>();

    /**
     * Array constructor <br>
     * Creates a two-arraylist object, the first which contains all existing
     * projects and the second for complete projects which are removed from the
     * first array once the program is terminated. <br>
     *
     * @throws IOException Exception thrown if error occurred accessing projects
     *                     file.
     */

    public ProjectFile() throws IOException {
        accessFile();

    }

    /**
     * This method accesses projects file and adds all the files to the project list
     * constructed. <br>
     *
     * @throws FileNotFoundException Exception thrown if file is not found or is
     *                               misplaced.
     */

    public void accessFile() throws FileNotFoundException {
        Scanner projectsFile = new Scanner(new File("projects.txt"));
        while (projectsFile.hasNextLine()) {
            projectList.add(projectsFile.nextLine());

        }
    }

    /**
     * This method prints out all projects in the projects list.
     */

    public void printAll() {
        int count = 0;
        System.out.println("Project List:\n\n");
        for (Object project : projectList) {
            count++;
            System.out.println(count + ". " + project);
        }

    }

    /**
     * This method writes updated information about the projects to the projects
     * file, removing all finalized projects from the list before doing so. <br>
     *
     * @throws IOException Exception thrown if error occurs when writing to the
     *                     file.
     */

    public void writeToFile() throws IOException {
        projectList.removeAll(completeProjects);
        Formatter projectsFile = new Formatter("projects.txt");
        for (Object project : projectList) {
            projectsFile.format(project.toString());
            projectsFile.format(System.lineSeparator());
        }
        projectsFile.close();
    }

    /**
     * This method updates an existing project's contractor's contact information
     * and displays the updated details. <br>
     *
     * @param projectName  String contains name of project, so it can be found in the
     *                     list.
     * @param telephoneNum String contains contractor's new telephone number.
     * @param email        String contains contractor's new email address.
     */

    public void changeContractorDetails(String projectName, String telephoneNum, String email) {
        Object newProjectObject;
        String[] objectDetails = new String[26];

        for (Object project : projectList) {
            // regionMatches() method enables string indexing of project string, to retrieve specified project
            // ignoring character case
            if (projectName.regionMatches(true, 0, String.valueOf(project), 0, projectName.length())) {
                objectDetails = ((String.valueOf(project)).split(", "));
                StringBuilder newDetails = new StringBuilder();
                int pos = projectList.indexOf(project);

                objectDetails[22] = telephoneNum;
                objectDetails[23] = email;

                for (String detail : objectDetails) {
                    newDetails.append(detail).append(", ");
                }
                newProjectObject = newDetails.toString();
                projectList.set(pos, newProjectObject);

            }
        }
        System.out.println("\nUPDATED CONTRACTOR DETAILS:\n" + objectDetails[19] + "\nName: " + objectDetails[20] + " "
                + objectDetails[21] + "\nTelephone Number: " + objectDetails[22] + "\nEmail address: "
                + objectDetails[23] + "\nPhysical Address: " + objectDetails[24]);

    }

    /**
     * This method updates the deadline of an existing project. <br>
     *
     * @param projectName String contains name of project, so it can be found in the
     *                    list.
     * @param deadline    String contains new deadline of the project.
     */

    public void changeDeadline(String projectName, String deadline) {
        Object newProjectObject;
        String[] objectDetails;

        for (Object project : projectList) {
            if (projectName.regionMatches(true, 0, String.valueOf(project), 0, projectName.length())) {
                objectDetails = ((String.valueOf(project)).split(", "));
                String newDetails = "";
                int pos = projectList.indexOf(project);

                objectDetails[4] = deadline;

                for (String detail : objectDetails) {
                    newDetails = detail + ", ";
                }
                newProjectObject = newDetails;
                projectList.set(pos, newProjectObject);

            }
        }
        System.out.println("\nDeadline changed: " + deadline);
    }

    /**
     * This method updates the amount a customer has paid for their project fees.
     * <br>
     *
     * @param projectName String contains name of project, so it can be found in the
     *                    list
     * @param paidAmount  Double contains the amount the customer has paid
     */

    public void changeAmountPaid(String projectName, Double paidAmount) {
        Object newProjectObject;
        String[] objectDetails = new String[26];
        for (Object project : projectList) {
            if (projectName.regionMatches(true, 0, String.valueOf(project), 0, projectName.length())) {
                objectDetails = ((String.valueOf(project)).split(", "));
                StringBuilder newDetails = new StringBuilder();
                int pos = projectList.indexOf(project);

                // adds new paid amount to amount already paid
                objectDetails[6] = String.valueOf(Double.parseDouble(objectDetails[6]) + paidAmount);

                for (String detail : objectDetails) {
                    newDetails.append(detail).append(", ");
                }
                newProjectObject = newDetails.toString();
                projectList.set(pos, newProjectObject);

            }
        }
        System.out.println(
                "PROJECT FEES UPDATED!\nCurrent Amount Paid: R" + objectDetails[6] + "\nTotal Fee: R" + objectDetails[5]);

    }

    /**
     * This method returns a list of all incomplete projects in the projects lists
     */

    public void incompleteProjects() {
        int count = 0;
        System.out.println("\nIncomplete Projects List:\n");
        for (Object project : projectList) {
            count++;
            if (String.valueOf(project).endsWith("not finalized, ")) {
                System.out.println(count + ". " + project);
            }
        }

    }

    /**
     * This method finalizes projects and returns an invoice if the user hasn't
     * fully paid for the project. If they have fully paid no invoice is provided
     * and the object is moved to a completed projects file. <br>
     *
     * @param projectName String contains name of the project, so it can be found in
     *                    the file.
     * @throws IOException Exception thrown if an error occurs when writing to the
     *                     file.
     */

    public void finalizeProject(String projectName) throws IOException {
        ArrayList<Object> finalizedProjects = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Object newProjectObject;
        String[] objectDetails;
        Date currentDate = new Date();

        for (Object project : projectList) {
            if (projectName.regionMatches(true, 0, String.valueOf(project), 0, projectName.length())) {
                objectDetails = ((String.valueOf(project)).split(", "));
                StringBuilder newDetails = new StringBuilder();

                objectDetails[25] = "finalized";

                for (String detail : objectDetails) {
                    newDetails.append(detail).append(", ");
                }
                // appends date on which project is finalized
                newDetails.append(sdf.format(currentDate));
                newProjectObject = newDetails.toString();
                completeProjects.add(project);
                finalizedProjects.add(newProjectObject);

                // writes finalized projects to new file.
                FileWriter newFile = new FileWriter("Completed project.txt", true);
                for (Object finalizedProject : finalizedProjects) {
                    newFile.write(finalizedProject.toString());
                    newFile.write(System.lineSeparator());
                }
                newFile.close();

                // prints invoice
                double balanceOutstanding = Double.parseDouble(objectDetails[5]) - Double.parseDouble(objectDetails[6]);
                if (balanceOutstanding == 0) {
                    System.out.print("\nProject Finalized!\nNo invoice available, balance fully paid.\n");
                } else if (balanceOutstanding > 0) {
                    System.out.print("\nProject Finalized!\nINVOICE\nProject " + objectDetails[0] + "\nClient name: "
                            + objectDetails[8] + " " + objectDetails[9] + "\nTotal Project Fees: R" + objectDetails[5]
                            + "\nTotal Paid: R" + objectDetails[6] + "\nBalance Outstanding: R" + balanceOutstanding
                            + "\n");
                }
            }
        }

    }

    /**
     * This method returns a list of overdue projects in the projects list. <br>
     *
     * @throws ParseException Exception thrown if project deadline date format is
     *                        invalid
     */

    public void overdueProjects() throws ParseException {
        ArrayList<Object> dueProjectsList = new ArrayList<>();
        String[] objectDetails;
        int count = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date currentDate = new Date();

        // indexes deadline and compares it to current date
        for (Object project : projectList) {
            objectDetails = ((String.valueOf(project)).split(", "));
            String dateStr = objectDetails[4];
            Date dueDate = sdf.parse(dateStr);
            if (currentDate.after(dueDate)) {
                dueProjectsList.add(project);
            }
        }
        System.out.println("\nOverDue Projects List:\n");
        for (Object dueProject : dueProjectsList) {
            count++;
            System.out.println(count + ". " + dueProject);
        }
    }

    /**
     * This method displays an existing project's properly formatted information
     * when user chooses to edit the project. <br>
     *
     * @param projectName String contains name of the project
     */

    public void showProject(String projectName) {
        String[] objectDetails = new String[26];
        for (Object project : projectList) {

            if (projectName.regionMatches(true, 0, String.valueOf(project), 0, projectName.length())) {
                objectDetails = ((String.valueOf(project)).split(", "));
            }
        }
        System.out.println("\nProject: " + objectDetails[0] + "\nBuilding Type: " + objectDetails[1]
                + "\nBuilding Address: " + objectDetails[2] + "\nERF number: " + objectDetails[3]
                + "\nProject deadline: " + objectDetails[4] + "\n\nTotal Project fee: R" + objectDetails[5]
                + "\nTotal amount paid to date: R" + objectDetails[6]);
        System.out.println("\n" + objectDetails[7] + "\nName: " + objectDetails[8] + " " + objectDetails[9]
                + "\nTelephone Number: " + objectDetails[10] + "\nEmail address: " + objectDetails[11]
                + "\nPhysical Address: " + objectDetails[12]);

        System.out.println("\n" + objectDetails[13] + "\nName: " + objectDetails[14] + " " + objectDetails[15]
                + "\nTelephone Number: " + objectDetails[16] + "\nEmail address: " + objectDetails[17]
                + "\nPhysical Address: " + objectDetails[18]);

        System.out.println("\n" + objectDetails[19] + "\nName: " + objectDetails[20] + " " + objectDetails[21]
                + "\nTelephone Number: " + objectDetails[22] + "\nEmail address: " + objectDetails[23]
                + "\nPhysical Address: " + objectDetails[24]);

    }

    /**
     * This method adds a new project to list of existing projects.
     *
     * @param newProject Contains new project object created using
     *                   {@link Project#Project(String, String, String, String, String, double, double)
     *                   Project} method
     * @param customer   Person contains project customer's information
     * @param architect  Person contains project architect's information
     * @param contractor contains project contractor's information <br>
     *                   All person objects created using
     *                   {@link Person#Person(String, String, String, String, String, String)
     *                   Person} method
     */

    public void addNewProject(Project newProject, Person customer, Person architect, Person contractor) {

        String projectDetails = "";
        projectDetails += newProject.projectName + ", ";
        projectDetails += newProject.buildingType + ", ";
        projectDetails += newProject.buildingAddress + ", ";
        projectDetails += newProject.erfNum + ", ";
        projectDetails += newProject.deadline + ", ";
        projectDetails += newProject.totalFee + ", ";
        projectDetails += newProject.totalPaid + ", ";

        projectDetails += customer.userType + ", ";
        projectDetails += customer.name + ", ";
        projectDetails += customer.surname + ", ";
        projectDetails += customer.telephoneNum + ", ";
        projectDetails += customer.email + ", ";
        projectDetails += customer.physicalAdd + ", ";

        projectDetails += architect.userType + ", ";
        projectDetails += architect.name + ", ";
        projectDetails += architect.surname + ", ";
        projectDetails += architect.telephoneNum + ", ";
        projectDetails += architect.email + ", ";
        projectDetails += architect.physicalAdd + ", ";

        projectDetails += contractor.userType + ", ";
        projectDetails += contractor.name + ", ";
        projectDetails += contractor.surname + ", ";
        projectDetails += contractor.telephoneNum + ", ";
        projectDetails += contractor.email + ", ";
        projectDetails += contractor.physicalAdd + ", ";

        // marker for project completion
        projectDetails += "not finalized, ";
        Object newProjectObject = projectDetails;
        projectList.add(newProjectObject);

    }

}
