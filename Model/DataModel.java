package Model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataModel {
    private List<StudentsModel> students; 
    private List<SubjectsModel> subjects;
    private List<RegisterModel> registrations ;

    public DataModel() {
        students = new ArrayList<>();
        subjects = new ArrayList<>();
        registrations = new ArrayList<>();
        loadStudents("Data/students.csv"); 
        loadSubjects("Data/subjects.csv");
        loadRegistrations("Data/Register.csv");
    }

    // โหลดข้อมูลนักเรียนจากไฟล์ CSV
    private void loadStudents(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // อ่านบรรทัดแรก (header) แล้วข้ามไป ไม่เอามาใช้
            while ((line = br.readLine()) != null) {  // อ่านบรรทัดถัดไปจนกว่าจะหมด
                String[] data = line.split(","); //แยกข้อมูลแต่ละคอลัมน์ด้วยเครื่องหมาย ,
                if (data.length >= 7) { // ตรวจสอบว่ามีข้อมูลครบ 7 ช่อง
                    students.add(new StudentsModel(
                            data[0], data[1], data[2], data[3],
                            data[4], data[5], data[6]
                    ));
                }
            }
        } catch (IOException e) {
            System.out.println("ไม่พบไฟล์ students.csv");
        }
    }

    // โหลดข้อมูลวิชาจากไฟล์ CSV
    private void loadSubjects(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // อ่านบรรทัดแรก (header) แล้วข้ามไป ไม่เอามาใช้
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 7) {
                    subjects.add(new SubjectsModel(
                        data[0],                 // รหัสวิชา
                        data[1],                 // ชื่อวิชา
                        Integer.parseInt(data[2]), // หน่วยกิต (แปลงเป็น int)
                        data[3],                 // ชื่ออาจารย์
                        data[4].isEmpty() ? "need" : data[4], // วิชาที่ต้องเรียนก่อน (ถ้าว่างให้เป็น null)
                        Integer.parseInt(data[5]), // จำนวนนักเรียนสูงสุด
                        Integer.parseInt(data[6])  // จำนวนนักเรียนปัจจุบัน
                    ));
                    System.out.println(data[4].isEmpty() ? null : data[4]);
                }
                
            }
        } catch (IOException e) {
            System.out.println("ไม่พบไฟล์ subjects.csv");
        }
    }

    // โหลดข้อมูลการลงทะเบียนจากไฟล์ CSV
    private void loadRegistrations(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // อ่านบรรทัดแรก (header) แล้วข้ามไป ไม่เอามาใช้
            while ((line = br.readLine()) != null) {  // อ่านบรรทัดถัดไปจนกว่าจะหมด
                String[] data = line.split(","); //แยกข้อมูลแต่ละคอลัมน์ด้วยเครื่องหมาย ,
                if (data.length >= 2) { // ตรวจสอบว่ามีข้อมูลครบ 2 ช่อง
                    registrations.add(new RegisterModel(
                            data[0], data[1]
                    ));
                }
            }
        } catch (IOException e) {
            System.out.println("ไม่พบไฟล์ Register.csv");
        }
    }
    public List<StudentsModel> getStudents() { return students; }
    public List<SubjectsModel> getSubjects() { return subjects; }
    public List<RegisterModel> getRegistrations() { return registrations; }

    public StudentsModel findStudentById(String id) {
        return students.stream().filter(s -> s.getStudentId().equals(id)).findFirst().orElse(null);
    }

    public SubjectsModel findSubjectById(String id) {
        return subjects.stream().filter(s -> s.getSubjectId().equals(id)).findFirst().orElse(null);
    }
    public RegisterModel findRegistration(String studentId, String subjectCode) {
        return registrations.stream()
                .filter(r -> r.getStudentId().equals(studentId) && r.getSubjectCode().equals(subjectCode))
                .findFirst()
                .orElse(null);
    }


    // ตรวจสอบและลงทะเบียนนักเรียนในวิชา
    public boolean registerStudentToSubject(String subjectId) {
        SubjectsModel sub = findSubjectById(subjectId);
        if (sub == null) return false;
        try {
            sub.register();
            return true;
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
            return false;
          }
    }   

    public void saveRegistrationsToCSV() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Data/Register.csv"))) {
            writer.write("studentId,subjectId"); // header
            writer.newLine();
            for (RegisterModel r : registrations) {
                writer.write(r.getStudentId() + "," + r.getSubjectCode());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
