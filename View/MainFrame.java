package View;

import Controller.Controller;

import javax.swing.*;

public class MainFrame extends JFrame {
    private JButton btnHistory, btnAvailable, btnRegister, btnDetail;
    private Controller controller; // ตัวเชื่อมไปยัง Controller เพื่อเรียกฟังก์ชัน logic

    public MainFrame(Controller controller) {
        this.controller = controller;


        setTitle("Student Menu");               // ตั้งชื่อหน้าต่าง
        setSize(400, 250);                      // กำหนดขนาดหน้าต่าง
        setLocationRelativeTo(null);            // แสดงตรงกลางหน้าจอ
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // ปิดโปรแกรมเมื่อปิดหน้าต่าง
        setLayout(null);                        // ใช้ layout แบบ null เพื่อกำหนดตำแหน่งเอง

        // สร้างปุ่มสำหรับดูประวัติการลงทะเบียน
        btnHistory = new JButton("Registration History");
        btnHistory.setBounds(100, 20, 200, 30); // กำหนดตำแหน่งและขนาด
        add(btnHistory);                        // เพิ่มลง JFrame

        // ปุ่มดูรายวิชาที่มีให้ลงทะเบียน
        btnAvailable = new JButton("Available Subjects");
        btnAvailable.setBounds(100, 60, 200, 30);
        add(btnAvailable);

        // ปุ่มลงทะเบียนวิชา
        btnRegister = new JButton("Register Subject");
        btnRegister.setBounds(100, 100, 200, 30);
        add(btnRegister);

        // ปุ่มดูรายละเอียดวิชา
        btnDetail = new JButton("View Subject Details");
        btnDetail.setBounds(100, 140, 200, 30);
        add(btnDetail);

        // เพิ่ม event listener ให้ปุ่มแต่ละปุ่ม
        btnHistory.addActionListener(e -> showHistory());
        btnAvailable.addActionListener(e -> showAvailable());
        btnRegister.addActionListener(e -> registerSubject());
        btnDetail.addActionListener(e -> showDetail());
    }

    // ฟังก์ชันแสดงประวัติการลงทะเบียน
    private void showHistory() {
        StringBuilder sb = new StringBuilder();
        controller.getRegistrationsOfCurrentStudent().forEach(r -> {
            sb.append(r.getSubjectCode()).append(" - ") // แสดงรหัสวิชา
              .append(controller.getSubjectName(r.getSubjectCode())).append("\n"); // แสดงชื่อวิชา
        });
        JOptionPane.showMessageDialog(this, sb.length() > 0 ? sb.toString() : "No registration history");
    }

    // ฟังก์ชันแสดงรายวิชาที่สามารถลงทะเบียนได้
    private void showAvailable() {
        StringBuilder sb = new StringBuilder();
        controller.getAvailableSubjects().forEach(s -> sb.append(s.getSubjectId())
            .append(" - ").append(s.getSubjectName()).append("\n"));
        JOptionPane.showMessageDialog(this, sb.length() > 0 ? sb.toString() : "No available subjects");
    }

    // ฟังก์ชันลงทะเบียนวิชา
    private void registerSubject() {
        String subId = JOptionPane.showInputDialog(this, "Enter the subject ID to register:"); // รับรหัสวิชาจากผู้ใช้
        if (subId != null && !subId.isEmpty()) {
            String result = controller.registerSubject(subId); // เรียก Controller ลงทะเบียน
            JOptionPane.showMessageDialog(this, result); // แสดงผลลัพธ์ลงทะเบียน
        }
    }

    // ฟังก์ชันดูรายละเอียดวิชา
    private void showDetail() {
        String subId = JOptionPane.showInputDialog(this, "Enter the subject ID:"); // รับรหัสวิชาจากผู้ใช้
        if (subId != null && !subId.isEmpty()) {
            String detail = controller.getSubjectDetail(subId); // เรียก Controller ดึงรายละเอียดวิชา
            JOptionPane.showMessageDialog(this, detail);        // แสดงรายละเอียด
        }
    }
}
