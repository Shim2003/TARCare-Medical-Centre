/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Control.PatientManagement;
import Entity.Patient;
import Utility.UtilityClass;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author user
 */
public class ClinicData {

    public static void run() {
        addSamplePatients();
    }

    public static void addSamplePatients() {
        SimpleDateFormat sdf = new SimpleDateFormat(UtilityClass.DATE_FORMAT);
        try {
            Patient p1 = new Patient("Alice Tan", "A123456789", sdf.parse("01/01/1990"), 'F', "0123456789", "alice@example.com", "123 Jalan ABC, Kuala Lumpur", "01122334455", new Date());
            Patient p2 = new Patient("Bob Lim", "B987654321", sdf.parse("15/05/1985"), 'M', "0198765432", "bob@example.com", "456 Jalan XYZ, Penang", "01233445566", new Date());
            Patient p3 = new Patient("Charlie Wong", "C111222333", sdf.parse("20/12/1975"), 'M', "0171122334", "charlie@example.com", "789 Jalan DEF, Johor", "01344556677", new Date());
            Patient p4 = new Patient("Daphne Lee", "D555666777", sdf.parse("10/03/1992"), 'F', "0169988776", "daphne@example.com", "12 Jalan Hijau, Melaka", "01455667788", new Date());
            Patient p5 = new Patient("Ethan Tan", "E999888777", sdf.parse("05/07/1988"), 'M', "0183344556", "ethan@example.com", "23 Jalan Bunga, Selangor", "01566778899", new Date());
            Patient p6 = new Patient("Fiona Ng", "F112233445", sdf.parse("22/11/1995"), 'F', "0127766554", "fiona@example.com", "34 Jalan Mawar, Perak", "01377889900", new Date());
            Patient p7 = new Patient("George Ho", "G556677889", sdf.parse("09/09/1980"), 'M', "0174455667", "george@example.com", "45 Jalan Cempaka, Kedah", "01288990011", new Date());
            Patient p8 = new Patient("Hannah Lim", "H223344556", sdf.parse("17/02/1993"), 'F', "0192233445", "hannah@example.com", "56 Jalan Kenanga, Terengganu", "01433445566", new Date());
            Patient p9 = new Patient("Ivan Chong", "I667788990", sdf.parse("28/06/1978"), 'M', "0165566778", "ivan@example.com", "67 Jalan Teratai, Sabah", "01344556688", new Date());
            Patient p10 = new Patient("Jasmine Tan", "J334455667", sdf.parse("14/12/1999"), 'F', "0129988775", "jasmine@example.com", "78 Jalan Dahlia, Sarawak", "01255667788", new Date());
            Patient p11 = new Patient("Kevin Ong", "K445566778", sdf.parse("03/08/1987"), 'M', "0156677889", "kevin@example.com", "89 Jalan Melati, Negeri Sembilan", "01366778899", new Date());
            Patient p12 = new Patient("Linda Goh", "L778899001", sdf.parse("12/04/1991"), 'F', "0134455667", "linda@example.com", "90 Jalan Orkid, Pahang", "01477889900", new Date());
            Patient p13 = new Patient("Marcus Teo", "M123789456", sdf.parse("25/09/1983"), 'M', "0187766554", "marcus@example.com", "101 Jalan Anggrek, Kelantan", "01588990011", new Date());
            Patient p14 = new Patient("Nina Choo", "N456123789", sdf.parse("07/01/1996"), 'F', "0165544332", "nina@example.com", "112 Jalan Bakawali, Perlis", "01699001122", new Date());
            Patient p15 = new Patient("Oscar Yap", "O789456123", sdf.parse("18/11/1979"), 'M', "0198877665", "oscar@example.com", "123 Jalan Flamboyan, Putrajaya", "01700112233", new Date());
            Patient p16 = new Patient("Priya Sharma", "P321654987", sdf.parse("29/06/1994"), 'F', "0177665544", "priya@example.com", "134 Jalan Bougainvillea, Labuan", "01511223344", new Date());
            Patient p17 = new Patient("Quincy Lee", "Q654987321", sdf.parse("13/03/1986"), 'M', "0144332211", "quincy@example.com", "145 Jalan Hibiskus, Kuala Lumpur", "01622334455", new Date());
            Patient p18 = new Patient("Rachel Koh", "R987321654", sdf.parse("02/10/1998"), 'F', "0166554433", "rachel@example.com", "156 Jalan Pokok Kelapa, Penang", "01733445566", new Date());
            Patient p19 = new Patient("Samuel Chin", "S135792468", sdf.parse("21/07/1981"), 'M', "0155443322", "samuel@example.com", "167 Jalan Pandan, Johor", "01844556677", new Date());
            Patient p20 = new Patient("Tiffany Woo", "T468135792", sdf.parse("08/12/1997"), 'F', "0122119988", "tiffany@example.com", "178 Jalan Cemara, Melaka", "01955667788", new Date());
            Patient p21 = new Patient("Uncle Ben", "U792468135", sdf.parse("16/05/1974"), 'M', "0199887766", "uncle.ben@example.com", "189 Jalan Pinang, Selangor", "01066778899", new Date());
            Patient p22 = new Patient("Victoria Soo", "V246813579", sdf.parse("04/02/1989"), 'F', "0177889900", "victoria@example.com", "190 Jalan Kelapa Sawit, Perak", "01177889900", new Date());
            Patient p23 = new Patient("William Yong", "W813579246", sdf.parse("23/09/1984"), 'M', "0188990011", "william@example.com", "201 Jalan Durian, Kedah", "01288001122", new Date());
            Patient p24 = new Patient("Xin Yi Chen", "X579246813", sdf.parse("11/06/1993"), 'F', "0155667788", "xinyi@example.com", "212 Jalan Rambutan, Terengganu", "01399112233", new Date());
            Patient p25 = new Patient("Yusuf Rahman", "Y357924681", sdf.parse("30/01/1977"), 'M', "0166778899", "yusuf@example.com", "223 Jalan Mangga, Sabah", "01400223344", new Date());
            Patient p26 = new Patient("Zara Hassan", "Z924681357", sdf.parse("19/08/1995"), 'F', "0133445566", "zara@example.com", "234 Jalan Jambu, Sarawak", "01511334455", new Date());
            Patient p27 = new Patient("Aaron Ooi", "AA111222333", sdf.parse("06/04/1982"), 'M', "0144556677", "aaron@example.com", "245 Jalan Kedondong, Negeri Sembilan", "01622445566", new Date());
            Patient p28 = new Patient("Bella Tan", "BB444555666", sdf.parse("24/10/1990"), 'F', "0177889911", "bella@example.com", "256 Jalan Belimbing, Pahang", "01733556677", new Date());
            Patient p29 = new Patient("Caleb Ng", "CC777888999", sdf.parse("15/07/1988"), 'M', "0188991122", "caleb@example.com", "267 Jalan Betik, Kelantan", "01844667788", new Date());
            Patient p30 = new Patient("Diana Lau", "DD000111222", sdf.parse("09/12/1996"), 'F', "0199112233", "diana@example.com", "278 Jalan Cempedak, Perlis", "01955778899", new Date());

            PatientManagement.add(p1);
            PatientManagement.add(p2);
            PatientManagement.add(p3);
            PatientManagement.add(p4);
            PatientManagement.add(p5);
            PatientManagement.add(p6);
            PatientManagement.add(p7);
            PatientManagement.add(p8);
            PatientManagement.add(p9);
            PatientManagement.add(p10);
            PatientManagement.add(p11);
            PatientManagement.add(p12);
            PatientManagement.add(p13);
            PatientManagement.add(p14);
            PatientManagement.add(p15);
            PatientManagement.add(p16);
            PatientManagement.add(p17);
            PatientManagement.add(p18);
            PatientManagement.add(p19);
            PatientManagement.add(p20);
            PatientManagement.add(p21);
            PatientManagement.add(p22);
            PatientManagement.add(p23);
            PatientManagement.add(p24);
            PatientManagement.add(p25);
            PatientManagement.add(p26);
            PatientManagement.add(p27);
            PatientManagement.add(p28);
            PatientManagement.add(p29);
            PatientManagement.add(p30);

        } catch (ParseException e) {
            System.out.println("Error parsing date in sample data.");
        }
    }

}
