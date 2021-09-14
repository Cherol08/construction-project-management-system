/**
 * This class is used to create person objects that store information about
 * members of a project.
 *
 * @author Cherol Phoshoko
 * @version 16.0.2, 2021-09-09
 */
public class Person {
    // field variables
    String name, surname;
    String userType;
    String telephoneNum;
    String email;
    String physicalAdd;

    /**
     * Person constructor <br>
     *
     * @param name         String contains first name of the person
     * @param surname      String contains last name of the person
     * @param userType     String determines the type of person object created
     * @param telephoneNum String determines the phone contact of the person
     * @param email        String contains the email address of the person
     * @param physicalAdd  String contains the physical address number person
     */

    public Person(String name, String surname, String userType, String telephoneNum, String email, String physicalAdd) {
        this.name = name;
        this.surname = surname;
        this.userType = userType;
        this.telephoneNum = telephoneNum;
        this.email = email;
        this.physicalAdd = physicalAdd;
    }

    /**
     * @return Properly formatted object details
     */
    public String personDetails() {
        return userType + "\nName: " + name + " " + surname + "\nTelephone Number: " + telephoneNum
                + "\nEmail address: " + email + "\nPhysical Address: " + physicalAdd;
    }

}
