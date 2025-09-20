package Model;

public class RegisterModel {
    private String studentId;
    private String subjectCode;
    public RegisterModel(String studentId, String subjectCode) {
        this.studentId = studentId;
        this.subjectCode = subjectCode;
    }
    public String getStudentId() { return studentId; }
    public String getSubjectCode() { return subjectCode; }
    public void setStudentId(String studentId) { this.studentId = studentId; }
    public void setSubjectCode(String subjectCode) { this.subjectCode = subjectCode; }
    
}
