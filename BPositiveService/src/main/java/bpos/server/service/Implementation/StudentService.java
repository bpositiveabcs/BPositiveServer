package bpos.server.service.Implementation;

import bpos.common.model.Student;
import bpos.server.repository.Interfaces.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.*;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class StudentService {

    private StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public void pregatireCitirePdf(String filepath) {
        String filePath = "C:\\Users\\hp\\Documents\\UiPath\\ReadText\\nume_fisier.xlsx";

        try (FileInputStream fileIn = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fileIn)) {

            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(0);
            Cell cell = row.getCell(0);
            if (cell == null) {
                cell = row.createCell(0);
            }
            cell.setCellValue(filepath);

            try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                workbook.write(fileOut);
                apelCitirePdf();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void apelCitirePdf() {
        System.setProperty("java.awt.headless", "false");
        String uipathProjectPath = "C:\\Users\\hp\\Documents\\UiPath\\ReadText\\Main.xaml";

        try {
            Runtime.getRuntime().exec(new String[]{"C:\\Users\\hp\\AppData\\Local\\Programs\\UiPath\\Studio\\UiPath.Studio.exe", uipathProjectPath});

            Thread.sleep(15000);

            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_F6);
            robot.keyRelease(KeyEvent.VK_F6);
            robot.keyRelease(KeyEvent.VK_CONTROL);

            Thread.sleep(5000);
            Thread.sleep(60000);

            int x = 1920;
            int y = 0;
            robot.mouseMove(x, y);
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

        } catch (IOException | AWTException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void saveStudent(Student student) {
        studentRepository.save(student);
    }
}
