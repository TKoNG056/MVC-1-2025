package Model;

public class SubjectsModel {
    private String subjectId;
    private String subjectName;
    private int credit;
    private String teacher;
    private String prerequisite;  // อาจเป็น null
    private int maxStudents;
    private int currentStudents;

    public SubjectsModel(String subjectId, String subjectName, int credit, String teacher, String prerequisite, int maxStudents, int currentStudents) {
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.credit = credit;
        this.teacher = teacher;
        this.prerequisite = prerequisite;
        this.maxStudents = maxStudents;
        this.currentStudents = currentStudents;
    }
    public String getSubjectId() { return subjectId; }
    public String getSubjectName() { return subjectName; }
    public int getCredit() { return credit; }
    public String getTeacher() { return teacher; }
    public String getPrerequisite() { return prerequisite; }
    public int getMaxStudents() { return maxStudents; }
    public int getCurrentStudents() { return currentStudents; }

    public void setSubjectId(String subjectId) { this.subjectId = subjectId; }
    public void setSubjectName(String subjectName) { this.subjectName = subjectName; }
    public void setCredit(int credit) { this.credit = credit; }
    public void setTeacher(String teacher) { this.teacher = teacher; }
    public void setPrerequisite(String prerequisite) { this.prerequisite = prerequisite; }
    public void setMaxStudents(int maxStudents) { this.maxStudents = maxStudents; }
    public void setCurrentStudents(int currentStudents) { this.currentStudents = currentStudents; }

    // ตรวจสอบว่าสามารถลงทะเบียนได้หรือไม่
    public boolean canRegister() {
        // ถ้า maxStudents == -1 แปลว่าไม่จำกัดจำนวน -> ลงทะเบียนได้เสมอ
        if (maxStudents == -1) return true;
        return currentStudents < maxStudents;
    }

    public void register() {
        if (canRegister()) {
        currentStudents++;
        } else {
        throw new IllegalStateException("ไม่สามารถลงทะเบียนได้ เนื่องจากจำนวนคนเต็มแล้ว");
        }
    }
}
