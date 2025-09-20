package View;

import Controller.Controller;
import Model.DataModel;

import javax.swing.*;

public class LoginFrame extends JFrame {
    private JTextField txtStudentId; // กล่องข้อความสำหรับใส่รหัสนักเรียน
    private JButton btnLogin;        // ปุ่มสำหรับล็อกอิน
    private Controller controller;   // Controller สำหรับเรียกฟังก์ชัน logic ของระบบ

    public LoginFrame(DataModel dataModel) {
        controller = new Controller(dataModel); // สร้าง Controller เพื่อจัดการ login และข้อมูลนักเรียน

        setTitle("Login");                     // ตั้งชื่อหน้าต่าง
        setSize(300, 150);                     // กำหนดขนาดหน้าต่าง
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // ปิดโปรแกรมเมื่อปิดหน้าต่าง
        setLocationRelativeTo(null);           // แสดงหน้าต่างตรงกลางหน้าจอ
        setLayout(null);                       // ใช้ layout แบบ null เพื่อกำหนดตำแหน่งเอง

        // สร้าง label สำหรับ Student ID
        JLabel lblId = new JLabel("Student ID:");
        lblId.setBounds(20, 20, 80, 25);       // กำหนดตำแหน่งและขนาด
        add(lblId);                             // เพิ่ม label ลง JFrame

        // สร้างกล่องข้อความสำหรับใส่รหัสนักเรียน
        txtStudentId = new JTextField();
        txtStudentId.setBounds(100, 20, 150, 25); // กำหนดตำแหน่งและขนาด
        add(txtStudentId);                        // เพิ่มลง JFrame

        // สร้างปุ่มล็อกอิน
        btnLogin = new JButton("Login");
        btnLogin.setBounds(100, 60, 80, 25);    // กำหนดตำแหน่งและขนาด
        add(btnLogin);                           // เพิ่มลง JFrame

        // เพิ่ม event listener ให้ปุ่มล็อกอิน เมื่อกดปุ่มจะเรียกเมธอด login()
        btnLogin.addActionListener(e -> login());
    }

    // ฟังก์ชันล็อกอิน
    private void login() {
        String studentId = txtStudentId.getText();      // ดึงรหัสนักเรียนจากกล่องข้อความ
        if (controller.loginWithId(studentId)) {        // เรียก Controller เพื่อตรวจสอบรหัสนักเรียน
            JOptionPane.showMessageDialog(this, "Login Success!"); // แสดง popup ล็อกอินสำเร็จ
            new MainFrame(controller).setVisible(true); // เปิด MainFrame สำหรับนักเรียน
            this.dispose();                              // ปิดหน้าต่าง Login
        } else {
            JOptionPane.showMessageDialog(this, "Login Failed!");  // แสดง popup ล็อกอินล้มเหลว
        }
    }
}
