/**
 * This class is used to create project objects that store information about
 * construction projects.
 *
 * @author Cherol Phoshoko
 * @version 16.0.2, 2021-09-09
 */
public class Project {
    // project instance variables
    String projectName;
    String buildingType;
    String buildingAddress;
    String erfNum;
    String deadline;
    double totalFee;
    double totalPaid;

    /**
     * Project constructor. <br>
     *
     * @param projectName     String contains name of the project
     * @param buildingType    String determines what type of project it is i.e.
     *                        Apartment, house etc.
     * @param buildingAddress String contains address for the construction
     * @param erfNum          String contains ERF number for the building /
     *                        construction location
     * @param deadline        String contains deadline to complete project
     * @param totalFee        Double contains overall project fee
     * @param totalPaid       Double contains amount paid by the client
     */

    public Project(String projectName, String buildingType, String buildingAddress, String erfNum, String deadline,
                   double totalFee, double totalPaid) {

        this.projectName = projectName;
        this.buildingType = buildingType;
        this.buildingAddress = buildingAddress;
        this.deadline = deadline;
        this.erfNum = erfNum;
        this.totalFee = totalFee;
        this.totalPaid = totalPaid;
    }

    /**
     * @return Properly formatted project details
     */

    public String projectDetails() {
        return "Project: " + projectName + "\nBuilding Type: " + buildingType + "\nBuilding Address: " + buildingAddress
                + "\nERF number: " + erfNum + "\nProject deadline: " + deadline + "\n\nTotal Project fee: R" + totalFee
                + "\nTotal amount paid to date: R" + totalPaid;
    }
}
