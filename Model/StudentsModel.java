package Model;

public class StudentsModel {
    private String studentId;
    private String title;
    private String firstName;
    private String lastName;
    private String birthDate;
    private String school;
    private String email;

    public StudentsModel(String studentId, String title, String firstName, String lastName, String birthDate, String school, String email) {
        this.studentId = studentId;
        this.title = title;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.school = school;
        this.email = email;
    }
    public String getStudentId() { return studentId; }
    public String getTitle() { return title; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getBirthDate() { return birthDate; }
    public String getSchool() { return school; }
    public String getEmail() { return email; }

    public void setStudentId(String studentId) { this.studentId = studentId; }
    public void setTitle(String title) { this.title = title; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setBirthDate(String birthDate) { this.birthDate = birthDate; }
    public void setSchool(String school) { this.school = school; }
    public void setEmail(String email) { this.email = email; }

}
