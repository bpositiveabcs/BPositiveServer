package bpos.server.service.Implementation;

import bpos.common.model.*;
import bpos.server.repository.Interfaces.*;
import bpos.server.service.IObserver;
import bpos.server.service.Interface.IDonationService;
import bpos.server.service.ServicesExceptions;
import bpos.server.service.WebSockets.NotificationService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class DonationService implements IDonationService {
    private final NotificationService notificationService;

    private DonationRepository donationRepository;
    private DonationTypeRepository donationTypeRepository;
    private PersonRepository  dbPerson;
    private EventRepository dbEvent;
    private StudentRepository studentRepository;
    private Map<Integer,IObserver> loggedClients;

    public DonationService(NotificationService notificationService, DonationRepository donationRepository, DonationTypeRepository donationTypeRepository, PersonRepository dbPerson, EventRepository dbEvent,StudentRepository studentRepository) {
        this.notificationService = notificationService;
        this.donationRepository = donationRepository;
        this.donationTypeRepository = donationTypeRepository;
        this.studentRepository = studentRepository;
        this.dbEvent = dbEvent;
        this.dbPerson=dbPerson;
    }

    @Override
    public Iterable<Donation> findByTipDonation(String tipDonation) throws ServicesExceptions {
        return donationRepository.findByTipDonatie(tipDonation);
    }

    @Override
    public Iterable<Donation> findByIdTipDonation(Integer tipDonation) throws ServicesExceptions {
        return donationRepository.findByIdTipDonatie(tipDonation);
    }

    @Override
    public Optional<Donation> findOneDonation(Integer integer) throws ServicesExceptions {
        return donationRepository.findOne(integer);
    }

    @Override
    public Iterable<Donation> findAllDonations() throws ServicesExceptions {
        return donationRepository.findAll();
    }

    @Override
    public Optional<Donation> saveDonation(Donation entity) throws ServicesExceptions {
        return donationRepository.save(entity);
    }

    @Override
    public Optional<Donation> deleteDonation(Donation entity) throws ServicesExceptions {
        return donationRepository.delete(entity);
    }

    @Override
    public Optional<Donation> updateDonation(Donation entity) throws ServicesExceptions {
        return donationRepository.update(entity);
    }

    @Override
    public Optional<DonationType> findOneDonationType(Integer integer) throws ServicesExceptions {
        return donationTypeRepository.findOne(integer);
    }

    @Override
    public Iterable<DonationType> findAllDonationType() throws ServicesExceptions {
        return donationTypeRepository.findAll();
    }

    @Override
    public Optional<DonationType> saveDonationType(DonationType entity) throws ServicesExceptions {
        return donationTypeRepository.save(entity);
    }

    @Override
    public Optional<DonationType> deleteDonationType(DonationType entity) throws ServicesExceptions {
        return donationTypeRepository.delete(entity);
    }

    @Override
    public Optional<DonationType> updateDonationType(DonationType entity) throws ServicesExceptions {
        return donationTypeRepository.update(entity);
    }

    @Override
    public void donationRegister(Donation donation, Person person) throws ServicesExceptions {
        Optional<Donation> donationOptional = donationRepository.save(donation);
        List<Donation> donationArrayList=person.getDonations();
        donationArrayList.add(donation);
        // List<Event> eventArrayList=person.get();
        person.setDonations(donationArrayList);
        person.setPoints(person.getPoints() + donation.getPoints());
        Optional<Person> personOptional = dbPerson.update(person);

        Student student = studentRepository.findByUsername(person.getPersonLogInfo().getUsername());

        /////////////
        // Check if the person is a Student object
        if (student != null) {

            String grupa="";
            String semigrupa = "";
            String grupaSiSemigrupa = student.getGroup();
            String[] parts = grupaSiSemigrupa.split("/");
            if (parts.length == 2) {
                grupa = parts[0];
                semigrupa = parts[1];
            }
            String an = student.getYear().toString();

            String limba;
            String specializare;

            char firstDigit = grupa.charAt(0);
            switch (firstDigit) {
                case '1':
                    specializare = "M" + an;
                    limba = "RO-EN";
                    break;
                case '2':
                    specializare = "I" + an;
                    limba = "RO-EN";
                    break;
                case '3':
                    specializare = "MI" + an;
                    limba = "RO-EN";
                    break;
                case '8':
                    specializare = "MIE" + an;
                    limba = "RO-EN";
                    break;
                case '9':
                    specializare = "IE" + an;
                    limba = "RO-EN";
                    break;
                case '4':
                    specializare = "MM" + an;
                    limba = "MA-GE";
                    break;
                case '5':
                    specializare = "IM" + an;
                    limba = "MA-GE";
                    break;
                case '6':
                    specializare = "MIM" + an;
                    limba = "MA-GE";
                    break;
                case '7':
                    specializare = "IG" + an;
                    limba = "MA-GE";
                    break;
                default:
                    limba = null;
                    specializare = null;
            }

            if(limba!=null && specializare!=null)
                pregatireDatePreluareOrar(specializare, grupa, semigrupa, limba);

        }
        /////////////

        if(personOptional.isPresent() && donationOptional.isPresent()){
            notificationService.notifyClient(person.getId()+" "+donationOptional.get());
        }
        else{
            throw new ServicesExceptions("Donation could not be registered");
        }
    }

    /////////////////////////

    public static void pregatireDatePreluareOrar(String specializare, String grupa, String semigrupa, String limba){
        String filePath = "C:\\Users\\hp\\Documents\\UiPath\\PreluareOrar\\getParams.xlsx"; // Calea catre workbook-ul existent

        try (FileInputStream fileIn = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fileIn)) {

            Sheet sheet = workbook.getSheetAt(0); // Datele trebuie scrise in prima foaie

            // Creeaza o linie pentru fiecare parametru si scrie parametrii in coloana a doua
            String[] parametri = {specializare, grupa, semigrupa, limba};
            for (int i = 0; i < parametri.length; i++) {
                Row row = sheet.getRow(i);
                if (row != null) {
                    Cell cell = row.getCell(1); // A doua coloana (index 1)
                    if (cell == null) {
                        cell = row.createCell(1);
                    }
                    cell.setCellValue(parametri[i]);
                } else {
                    System.out.println("Randul " + (i + 1) + " nu exista in foaie.");
                }
            }

            // Scrie workbook-ul actualizat intr-un fisier
            try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                workbook.write(fileOut);
                apelPreluareOrar();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void apelPreluareOrar(){
        System.setProperty("java.awt.headless", "false");
        String uipathProjectPath = "C:\\Users\\hp\\Documents\\UiPath\\PreluareOrar\\Main.xaml";

        try {
            // Step 1: Open UiPath Studio with the specified project
            // Update the path to the UiPath Studio executable or shortcut
            Runtime.getRuntime().exec(new String[]{"C:\\Users\\hp\\AppData\\Local\\Programs\\UiPath\\Studio\\UiPath.Studio.exe", uipathProjectPath});

            // Allow some time for UiPath Studio to open and load the project
            Thread.sleep(15000);

            // Step 2: Create a Robot instance
            Robot robot = new Robot();

            // Step 3: Simulate pressing Ctrl+F6 to run the project
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_F6);
            robot.keyRelease(KeyEvent.VK_F6);
            robot.keyRelease(KeyEvent.VK_CONTROL);

            // Allow some time for the process to start
            Thread.sleep(5000);

            // Step 4: Wait for a certain time (adjust as needed)
            Thread.sleep(60000); // Wait for 1 min

            // Step 5: close the UiPath Studio window
            int x = 1920; // Replace with the x-coordinate of the box
            int y = 0; // Replace with the y-coordinate of the box
            robot.mouseMove(x, y);

            // Simulate a mouse click
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK); // Left mouse button down
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK); // Left mouse button up

        } catch (IOException | AWTException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /////////////////////////

    private void notifyDonationRegistered(Optional<Donation> donationOptional) {
        Iterable<Person> personIterable=dbPerson.findAll();
        personIterable.forEach(person -> {
            IObserver client=loggedClients.get(person.getId());
            if(client!=null){
                try {
                    client.donationRegistered(donationOptional.get());
                } catch (ServicesExceptions e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

}
