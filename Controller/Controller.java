package Controller;

import Model.DataModel;
import Model.StudentsModel;
import Model.SubjectsModel;
import Model.RegisterModel;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

public class Controller {
    private DataModel dataModel;      // ตัวเชื่อมข้อมูลหลัก ใช้ดึงข้อมูลนักเรียน รายวิชา และการลงทะเบียน
    private StudentsModel currentStudent;  // เก็บนักเรียนที่กำลังล็อกอินอยู่

    public Controller(DataModel dataModel) {
        this.dataModel = dataModel;   // กำหนด DataModel ที่จะใช้ในการทำงาน
    }

    //ล็อกอินนักเรียนด้วยรหัสนักเรียน
    public boolean loginWithId(String studentId) {
        StudentsModel student = dataModel.findStudentById(studentId); // ค้นหานักเรียนตาม ID
        if (student == null) return false;  // ถ้าไม่พบ รหัสไม่ถูกต้อง
        
        // ตรวจสอบอายุ โดยแปลงวันเกิดเป็น LocalDate
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate birthDate = LocalDate.parse(student.getBirthDate(), formatter);
        int age = Period.between(birthDate, LocalDate.now()).getYears();
        if (age < 15) return false;  // นักเรียนอายุต่ำกว่า 15 ปี ไม่สามารถล็อกอินได้

        this.currentStudent = student;  // ถ้าผ่านทุกเงื่อนไข ให้เก็บนักเรียนปัจจุบัน
        return true;  // ล็อกอินสำเร็จ
    }

    // ดึงรายการวิชาที่นักเรียนปัจจุบันลงทะเบียนแล้ว
    public List<RegisterModel> getRegistrationsOfCurrentStudent() {
        return dataModel.getRegistrations().stream()
            .filter(r -> r.getStudentId().equals(currentStudent.getStudentId()))
            .collect(Collectors.toList());
    }

    // ดึงรายวิชาที่นักเรียนยังไม่ได้ลงทะเบียน
    public List<SubjectsModel> getAvailableSubjects() {
        return dataModel.getSubjects().stream()
            .filter(s -> dataModel.findRegistration(currentStudent.getStudentId(), s.getSubjectId()) == null)
            .collect(Collectors.toList());
    }

    // ดึงชื่อวิชาจากรหัสวิชา
    public String getSubjectName(String subId) {
        SubjectsModel sub = dataModel.findSubjectById(subId);
        return sub != null ? sub.getSubjectName() : "Subject not found";
    }

    // ลงทะเบียนวิชา พร้อมส่งข้อความผลลัพธ์
    public String registerSubjectWithMessage(String subId) {
        SubjectsModel sub = dataModel.findSubjectById(subId);
        if (sub == null) return "Subject not found";  // ถ้าวิชาไม่ถูกต้อง

        // ตรวจสอบวิชาที่เป็น มีวิชาต้องเรียนก่อน
        if (sub.getPrerequisite() != null) { // ถ้ามี
            RegisterModel prereg = dataModel.findRegistration(currentStudent.getStudentId(), sub.getPrerequisite());
            if (prereg == null) return "Prerequisite required: " + sub.getPrerequisite();
        }

        // ตรวจสอบว่าวิชายังลงทะเบียนได้หรือไม่
        if (!sub.canRegister()) return "Class is full, registration not possible";

        // ลงทะเบียนนักเรียนกับ DataModel
        if (dataModel.registerStudentToSubject(subId)) {
            dataModel.getRegistrations().add(new RegisterModel(currentStudent.getStudentId(), subId)); // เพิ่มรายการลงทะเบียน
            return "Registration successful!";
        }
        return "Registration failed";
    }

     // แสดงรายละเอียดวิชา
    public String getSubjectDetail(String subId) {
        SubjectsModel sub = dataModel.findSubjectById(subId);
        if (sub == null) return "Subject not found";
        return "Subject ID: " + sub.getSubjectId() + "\n" +
            "Subject Name: " + sub.getSubjectName() + "\n" +
            "Max Students: " + sub.getMaxStudents() + "\n" +
            "Current Students: " + sub.getCurrentStudents();
    }

    
}
