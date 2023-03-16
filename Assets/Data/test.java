/**
 * @author John Muller
 */
package eu.johnmuller.ecaperooms.Views;

import eu.johnmuller.ecaperooms.Classes.Dialog;
import eu.johnmuller.ecaperooms.Classes.FileIO;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.*;

public class MainForm extends javax.swing.JFrame {
    // create custom LOGO
    private static final ImageIcon LOGO = new ImageIcon(".//Assets//Images//Logo.png");
    private static final String FILEPATH = ".//Assets//Data/bookings.txt";
    private static final DateTimeFormatter DATE_PATTERN = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final String[] MONTHS_LIST = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};

    private static final String[] AVAILABLE_ROOMS_ICON_PATH = {".//Assets//Images//roomOneFree.png", ".//Assets//Images//roomTwoFree.png", ".//Assets//Images//roomThreeFree.png", ".//Assets//Images//roomFourFree.png", ".//Assets//Images//roomFiveFree.png",};
    private static final String[] BUSY_ROOMS_ICON_PATH = {".//Assets//Images//roomOneBusy.png", ".//Assets//Images//roomTwoBusy.png", ".//Assets//Images//roomThreeBusy.png", ".//Assets//Images//roomFourBusy.png", ".//Assets//Images//roomFiveBusy.png",};
    // used to store the bookings info then display it as upcoming bookings
    ArrayList<String> upComingBookings = new ArrayList<>();

    ArrayList<Integer> filteredList = new ArrayList<>();
    ArrayList<Integer> roomNumbers = new ArrayList<>();
    ArrayList<Integer> numberOfPersons = new ArrayList<>();
    ArrayList<String> names = new ArrayList<>();
    ArrayList<String> surnames = new ArrayList<>();
    ArrayList<String> contactNumbers = new ArrayList<>();
    ArrayList<String> extraRequirements = new ArrayList<>();
    ArrayList<LocalDate> dates = new ArrayList<>();


    // these functions are not pure-functions (it modifies data outside it)
    // cause there is no need to make a simple app complicated
    public void loadBookings() {
        // all commented code was used For testing purposes

        // Read data From File
        String dataString = FileIO.readTextFile(this, FILEPATH);
        // Split each line and store it in a list to iterate over it
        String[] dataList = dataString.split("\n");

        // I had an error in fileIO it was returning null, so I commented the below code after I fixed the error
        //System.out.println("'mainForm' Data string: " + dataString);
        //System.out.println("'mainForm' array list: " + Arrays.toString(dataList));
        //System.out.println("'mainForm' list length: " + dataList.length);

        for (String row : dataList) {
            String[] data = row.split(",");
            //System.out.println(data);
            // storing the data into their lists "manually"
            roomNumbers.add(Integer.parseInt(data[0]));
            names.add(data[1]);
            surnames.add(data[2]);
            contactNumbers.add(data[3]);
            // parse the date and fixing its format
            dates.add(LocalDate.parse(data[4], DATE_PATTERN));
            numberOfPersons.add(Integer.parseInt(data[5]));
            // if data.length equals 7
            // that means the booking has extra requirements, so I appended to the list
            if (data.length == 7) extraRequirements.add(data[6]);
            else
                // else append empty string
                extraRequirements.add("");
        }

        // making sure that everything was stored correctly
        //System.out.println(Arrays.toString(roomNumbers.toArray()));
        //System.out.println(Arrays.toString(names.toArray()));
        //System.out.println(Arrays.toString(surnames.toArray()));
        //System.out.println(Arrays.toString(contactNumbers.toArray()));
        //System.out.println(Arrays.toString(dates.toArray()));
        //System.out.println(Arrays.toString(numberOfPersons.toArray()));
        //System.out.println(Arrays.toString(extraRequirements.toArray()));
    }

    public void filterBookings(String date) {
        LocalDate selectedDate = LocalDate.parse(date, DATE_PATTERN);
        String bookingInfo;
        upComingBookings.removeAll(upComingBookings);
        filteredList.removeAll(filteredList);

        for (int i = 0; i < dates.size(); i++) {
            if (selectedDate.compareTo(dates.get(i)) == 0) {
                bookingInfo = String.format("Room Number: %s\n" + "Guest name: %s\n" + "Guest surname: %s\n" + "Guest phone Number: %s\n" + "number of persons: %s\n" + "Date: %s\n" + "Extra requirements: %s\n", roomNumbers.get(i), names.get(i), surnames.get(i), contactNumbers.get(i), numberOfPersons.get(i), dates.get(i), extraRequirements.get(i));
                filteredList.add(i);
                upComingBookings.add(bookingInfo);
                System.out.println(i + ": " + bookingInfo);
            }

            //else System.out.println("failed: " + dates.get(i) + "selected date is: " + selectedDate);
        }
    }

    public void fillDaysComboBox() {
        int year = Integer.parseInt(yearsComboBox.getSelectedItem().toString());
        YearMonth daysInMonth = YearMonth.of(year, Month.of(monthsComboBox.getSelectedIndex() + 1));
        //System.out.println(daysInMonth);
        //System.out.println(daysInMonth.lengthOfMonth());
        int previousIndex = daysComboBox.getSelectedIndex();
        daysComboBox.removeAllItems(); // clear the list
        for (int day = 1; day <= daysInMonth.lengthOfMonth(); day++) {
            //System.out.println(day);
            daysComboBox.addItem(Integer.toString(day));
        }

        // keep the previous day selected in case the user
        // selected the day then month it won't be visually deleted
        if (daysComboBox.getItemCount() >= previousIndex) {
            if (daysComboBox.getItemCount() == previousIndex) daysComboBox.setSelectedIndex(previousIndex - 1);
            else daysComboBox.setSelectedIndex(previousIndex);
        } else {
            daysComboBox.setSelectedIndex(daysComboBox.getItemCount() - 1);
        }
    }

    public void fillDatesComboBox() {
        // apply / fill after two years ,
        // so the year will be dynamically filed according
        // to the year (date) the app is loaded
        yearsComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{Integer.toString(LocalDate.now().getYear()), Integer.toString(LocalDate.now().getYear() + 1), Integer.toString(LocalDate.now().getYear() + 2),}));

        // no need to fill months using code because months does not change
        // but in case I wanted to fill it using code
        // monthsComboBox.setModel(
        //          new javax.swing.DefaultComboBoxModel<>(new String[]{
        //          "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"}));
        // or using  a for loop

        // fill days
        fillDaysComboBox();

        LocalDate now = LocalDate.now();
        //String[] date = now.toString().split("-");
        //System.out.println(date[0]); // years
        //System.out.println(date[1]); // months
        //System.out.println(date[2]);  // days
    }

    public void updateIcons(@NotNull ArrayList<Integer> bookings) {
        ArrayList<JButton> buttons = new ArrayList<>(Arrays.asList(roomOne, roomTwo, roomThree, roomFour, roomFive));

        for (int booking : bookings) {
            System.out.println(roomNumbers.get(booking));
        }
//        if (bookings.size() > 0) {
//            for (int i = 0; i <= buttons.size(); i++) {
//                if (bookings.size() > i) {
//                    if (roomNumbers.contains(bookings.get(i))) {
//                        buttons.get(roomNumbers.get(bookings.get(i))).setIcon(new javax.swing.ImageIcon(BUSY_ROOMS_ICON_PATH[roomNumbers.get(bookings.get(i))]));
//                        System.out.println(roomNumbers.get(i) + ": busy");
//                    } else {
//                        //buttons.get(i).setIcon(new javax.swing.ImageIcon(AVAILABLE_ROOMS_ICON_PATH[i]));
//                        System.out.println(roomNumbers.contains(bookings.get(i)));
//                    }
//
//                }
//            }
        // }
    }

    public void updateLabel(){

    }

    public MainForm() {
        initComponents();
        // apply custom LOGO
        this.setIconImage(LOGO.getImage());
        // fill the combo boxes
        fillDatesComboBox();
        // load bookings
        loadBookings();
        // update UI

    }


    /**
     * @author John Muller
     */
package eu.johnmuller.ecaperooms.Views;

import eu.johnmuller.ecaperooms.Classes.Dialog;
import eu.johnmuller.ecaperooms.Classes.FileIO;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.*;

    public class MainForm extends javax.swing.JFrame {
        // create custom LOGO
        private static final ImageIcon LOGO = new ImageIcon(".//Assets//Images//Logo.png");
        private static final String FILEPATH = ".//Assets//Data/bookings.txt";
        private static final DateTimeFormatter DATE_PATTERN = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        private static final String[] MONTHS_LIST = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};

        private static final String[] AVAILABLE_ROOMS_ICON_PATH = {".//Assets//Images//roomOneFree.png", ".//Assets//Images//roomTwoFree.png", ".//Assets//Images//roomThreeFree.png", ".//Assets//Images//roomFourFree.png", ".//Assets//Images//roomFiveFree.png",};
        private static final String[] BUSY_ROOMS_ICON_PATH = {".//Assets//Images//roomOneBusy.png", ".//Assets//Images//roomTwoBusy.png", ".//Assets//Images//roomThreeBusy.png", ".//Assets//Images//roomFourBusy.png", ".//Assets//Images//roomFiveBusy.png",};
        // used to store the bookings info then display it as upcoming bookings
        ArrayList<String> upComingBookings = new ArrayList<>();

        ArrayList<Integer> filteredList = new ArrayList<>();
        ArrayList<Integer> roomNumbers = new ArrayList<>();
        ArrayList<Integer> numberOfPersons = new ArrayList<>();
        ArrayList<String> names = new ArrayList<>();
        ArrayList<String> surnames = new ArrayList<>();
        ArrayList<String> contactNumbers = new ArrayList<>();
        ArrayList<String> extraRequirements = new ArrayList<>();
        ArrayList<LocalDate> dates = new ArrayList<>();


        // these functions are not pure-functions (it modifies data outside it)
        // cause there is no need to make a simple app complicated
        public void loadBookings() {
            // all commented code was used For testing purposes

            // Read data From File
            String dataString = FileIO.readTextFile(this, FILEPATH);
            // Split each line and store it in a list to iterate over it
            String[] dataList = dataString.split("\n");

            // I had an error in fileIO it was returning null, so I commented the below code after I fixed the error
            //System.out.println("'mainForm' Data string: " + dataString);
            //System.out.println("'mainForm' array list: " + Arrays.toString(dataList));
            //System.out.println("'mainForm' list length: " + dataList.length);

            for (String row : dataList) {
                String[] data = row.split(",");
                //System.out.println(data);
                // storing the data into their lists "manually"
                roomNumbers.add(Integer.parseInt(data[0]));
                names.add(data[1]);
                surnames.add(data[2]);
                contactNumbers.add(data[3]);
                // parse the date and fixing its format
                dates.add(LocalDate.parse(data[4], DATE_PATTERN));
                numberOfPersons.add(Integer.parseInt(data[5]));
                // if data.length equals 7
                // that means the booking has extra requirements, so I appended to the list
                if (data.length == 7) extraRequirements.add(data[6]);
                else
                    // else append empty string
                    extraRequirements.add("");
            }

            // making sure that everything was stored correctly
            //System.out.println(Arrays.toString(roomNumbers.toArray()));
            //System.out.println(Arrays.toString(names.toArray()));
            //System.out.println(Arrays.toString(surnames.toArray()));
            //System.out.println(Arrays.toString(contactNumbers.toArray()));
            //System.out.println(Arrays.toString(dates.toArray()));
            //System.out.println(Arrays.toString(numberOfPersons.toArray()));
            //System.out.println(Arrays.toString(extraRequirements.toArray()));
        }

        public void filterBookings(String date) {
            LocalDate selectedDate = LocalDate.parse(date, DATE_PATTERN);
            String bookingInfo;
            upComingBookings.removeAll(upComingBookings);
            filteredList.removeAll(filteredList);

            for (int i = 0; i < dates.size(); i++) {
                if (selectedDate.compareTo(dates.get(i)) == 0) {
                    bookingInfo = String.format("Room Number: %s\n" + "Guest name: %s\n" + "Guest surname: %s\n" + "Guest phone Number: %s\n" + "number of persons: %s\n" + "Date: %s\n" + "Extra requirements: %s\n", roomNumbers.get(i), names.get(i), surnames.get(i), contactNumbers.get(i), numberOfPersons.get(i), dates.get(i), extraRequirements.get(i));
                    filteredList.add(i);
                    upComingBookings.add(bookingInfo);
                    System.out.println(i + ": " + bookingInfo);
                }

                //else System.out.println("failed: " + dates.get(i) + "selected date is: " + selectedDate);
            }
        }

        public void fillDaysComboBox() {
            int year = Integer.parseInt(yearsComboBox.getSelectedItem().toString());
            YearMonth daysInMonth = YearMonth.of(year, Month.of(monthsComboBox.getSelectedIndex() + 1));
            //System.out.println(daysInMonth);
            //System.out.println(daysInMonth.lengthOfMonth());
            int previousIndex = daysComboBox.getSelectedIndex();
            daysComboBox.removeAllItems(); // clear the list
            for (int day = 1; day <= daysInMonth.lengthOfMonth(); day++) {
                //System.out.println(day);
                daysComboBox.addItem(Integer.toString(day));
            }

            // keep the previous day selected in case the user
            // selected the day then month it won't be visually deleted
            if (daysComboBox.getItemCount() >= previousIndex) {
                if (daysComboBox.getItemCount() == previousIndex) daysComboBox.setSelectedIndex(previousIndex - 1);
                else daysComboBox.setSelectedIndex(previousIndex);
            } else {
                daysComboBox.setSelectedIndex(daysComboBox.getItemCount() - 1);
            }
        }

        public void fillDatesComboBox() {
            // apply / fill after two years ,
            // so the year will be dynamically filed according
            // to the year (date) the app is loaded
            yearsComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{Integer.toString(LocalDate.now().getYear()), Integer.toString(LocalDate.now().getYear() + 1), Integer.toString(LocalDate.now().getYear() + 2),}));

            // no need to fill months using code because months does not change
            // but in case I wanted to fill it using code
            // monthsComboBox.setModel(
            //          new javax.swing.DefaultComboBoxModel<>(new String[]{
            //          "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"}));
            // or using  a for loop

            // fill days
            fillDaysComboBox();

            LocalDate now = LocalDate.now();
            //String[] date = now.toString().split("-");
            //System.out.println(date[0]); // years
            //System.out.println(date[1]); // months
            //System.out.println(date[2]);  // days
        }

        public void updateIcons(@NotNull ArrayList<Integer> bookings) {
            ArrayList<JButton> buttons = new ArrayList<>(Arrays.asList(roomOne, roomTwo, roomThree, roomFour, roomFive));

            for (int booking : bookings) {
                System.out.println(roomNumbers.get(booking));
            }
//        if (bookings.size() > 0) {
//            for (int i = 0; i <= buttons.size(); i++) {
//                if (bookings.size() > i) {
//                    if (roomNumbers.contains(bookings.get(i))) {
//                        buttons.get(roomNumbers.get(bookings.get(i))).setIcon(new javax.swing.ImageIcon(BUSY_ROOMS_ICON_PATH[roomNumbers.get(bookings.get(i))]));
//                        System.out.println(roomNumbers.get(i) + ": busy");
//                    } else {
//                        //buttons.get(i).setIcon(new javax.swing.ImageIcon(AVAILABLE_ROOMS_ICON_PATH[i]));
//                        System.out.println(roomNumbers.contains(bookings.get(i)));
//                    }
//
//                }
//            }
            // }
        }

        public void updateLabel(){

        }

        public MainForm() {
            initComponents();
            // apply custom LOGO
            this.setIconImage(LOGO.getImage());
            // fill the combo boxes
            fillDatesComboBox();
            // load bookings
            loadBookings();
            // update UI

        }

        /**
         * This method is called from within the constructor to initialize the form.
         * WARNING: Do NOT modify this code. The content of this method is always
         * regenerated by the Form Editor.
         */
        @SuppressWarnings("unchecked")
        // <editor-fold defaultstate="collapsed" desc="Generated Code">
        private void initComponents() {

            backgroundImagePanel = new javax.swing.JPanel();
            roomOne = new javax.swing.JButton();
            roomTwo = new javax.swing.JButton();
            roomThree = new javax.swing.JButton();
            RoomFour = new javax.swing.JButton();
            RoomFive = new javax.swing.JButton();
            monthsComboBox = new javax.swing.JComboBox<>();
            daysComboBox = new javax.swing.JComboBox<>();
            yearsComboBox = new javax.swing.JComboBox<>();
            getBookingBtn = new javax.swing.JButton();
            detailsLbl = new javax.swing.JLabel();
            yearLbl = new javax.swing.JLabel();
            monthsLbl = new javax.swing.JLabel();
            dayLbl = new javax.swing.JLabel();
            jButton1 = new javax.swing.JButton();
            todayDateLabel = new javax.swing.JLabel();
            datePickerContainer = new javax.swing.JLabel();
            bookingsInfoContainer = new javax.swing.JTextField();
            backgroundImagelabel = new javax.swing.JLabel();
            menuBar = new javax.swing.JMenuBar();
            fileButton = new javax.swing.JMenu();
            refreshBookingMenuButton = new javax.swing.JMenuItem();
            exitButton = new javax.swing.JMenuItem();
            HelpButton = new javax.swing.JMenu();
            aboutButton = new javax.swing.JMenuItem();

            setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
            setTitle("Escape Rooms");
            setResizable(false);

            backgroundImagePanel.setLayout(null);

            roomOne.setBackground(new java.awt.Color(38, 65, 70));
            roomOne.setBorderPainted(false);
            roomOne.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            roomOne.setHideActionText(true);
            roomOne.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
            roomOne.setIconTextGap(0);
            roomOne.setPreferredSize(new java.awt.Dimension(70, 70));
            roomOne.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    roomOneActionPerformed(evt);
                }
            });
            backgroundImagePanel.add(roomOne);
            roomOne.setBounds(90, 270, 80, 80);

            roomTwo.setBackground(new java.awt.Color(38, 65, 70));
            roomTwo.setBorderPainted(false);
            roomTwo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            roomTwo.setHideActionText(true);
            roomTwo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
            roomTwo.setIconTextGap(0);
            roomTwo.setPreferredSize(new java.awt.Dimension(70, 70));
            roomTwo.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    roomTwoActionPerformed(evt);
                }
            });
            backgroundImagePanel.add(roomTwo);
            roomTwo.setBounds(180, 270, 80, 80);

            roomThree.setBackground(new java.awt.Color(38, 65, 70));
            roomThree.setIcon(new javax.swing.ImageIcon("E:\\OneDrive - Malta College of Arts, Science & Technology\\MCAST\\MCAST MQF4\\Year 2\\Lv4-Y2-Sem1\\ITSFT-406-2005  Programming Concepts\\Assignments\\Assignment 2\\EcapeRooms_MullerJohn4_2A\\Assets\\Images\\roomThreeFree.png")); // NOI18N
            roomThree.setBorderPainted(false);
            roomThree.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            roomThree.setHideActionText(true);
            roomThree.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
            roomThree.setIconTextGap(0);
            roomThree.setPreferredSize(new java.awt.Dimension(70, 70));
            roomThree.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    roomThreeActionPerformed(evt);
                }
            });
            backgroundImagePanel.add(roomThree);
            roomThree.setBounds(270, 270, 80, 80);

            RoomFour.setBackground(new java.awt.Color(38, 65, 70));
            RoomFour.setBorderPainted(false);
            RoomFour.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            RoomFour.setHideActionText(true);
            RoomFour.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
            RoomFour.setIconTextGap(0);
            RoomFour.setPreferredSize(new java.awt.Dimension(70, 70));
            RoomFour.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    RoomFourActionPerformed(evt);
                }
            });
            backgroundImagePanel.add(RoomFour);
            RoomFour.setBounds(360, 270, 80, 80);

            RoomFive.setBackground(new java.awt.Color(38, 65, 70));
            RoomFive.setBorderPainted(false);
            RoomFive.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            RoomFive.setHideActionText(true);
            RoomFive.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
            RoomFive.setIconTextGap(0);
            RoomFive.setPreferredSize(new java.awt.Dimension(70, 70));
            RoomFive.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    RoomFiveActionPerformed(evt);
                }
            });
            backgroundImagePanel.add(RoomFive);
            RoomFive.setBounds(450, 270, 80, 80);

            monthsComboBox.setBackground(new java.awt.Color(39, 64, 68));
            monthsComboBox.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
            monthsComboBox.setForeground(new java.awt.Color(225, 225, 225));
            monthsComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" }));
            monthsComboBox.setBorder(null);
            monthsComboBox.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            monthsComboBox.setFocusable(false);
            monthsComboBox.setLightWeightPopupEnabled(false);
            monthsComboBox.setRequestFocusEnabled(false);
            monthsComboBox.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    monthsComboBoxActionPerformed(evt);
                }
            });
            backgroundImagePanel.add(monthsComboBox);
            monthsComboBox.setBounds(90, 150, 120, 20);

            daysComboBox.setBackground(new java.awt.Color(39, 64, 68));
            daysComboBox.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
            daysComboBox.setForeground(new java.awt.Color(225, 225, 225));
            daysComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" }));
            daysComboBox.setBorder(null);
            daysComboBox.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            daysComboBox.setFocusable(false);
            daysComboBox.setLightWeightPopupEnabled(false);
            daysComboBox.setRequestFocusEnabled(false);
            backgroundImagePanel.add(daysComboBox);
            daysComboBox.setBounds(90, 200, 120, 20);

            yearsComboBox.setBackground(new java.awt.Color(39, 64, 68));
            yearsComboBox.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
            yearsComboBox.setForeground(new java.awt.Color(225, 225, 225));
            yearsComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2022", "2023", "2024" }));
            yearsComboBox.setBorder(null);
            yearsComboBox.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            yearsComboBox.setFocusable(false);
            yearsComboBox.setLightWeightPopupEnabled(false);
            yearsComboBox.setRequestFocusEnabled(false);
            yearsComboBox.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    yearsComboBoxActionPerformed(evt);
                }
            });
            backgroundImagePanel.add(yearsComboBox);
            yearsComboBox.setBounds(90, 100, 120, 20);

            getBookingBtn.setBackground(new java.awt.Color(27, 105, 120));
            getBookingBtn.setFont(new java.awt.Font("Montserrat Alternates SemiBold", 1, 12)); // NOI18N
            getBookingBtn.setForeground(new java.awt.Color(225, 225, 225));
            getBookingBtn.setText("Get Bookings");
            getBookingBtn.setBorderPainted(false);
            getBookingBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            getBookingBtn.setFocusable(false);
            getBookingBtn.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    getBookingBtnActionPerformed(evt);
                }
            });
            backgroundImagePanel.add(getBookingBtn);
            getBookingBtn.setBounds(90, 230, 120, 30);

            detailsLbl.setFont(new java.awt.Font("Montserrat Alternates Light", 1, 14)); // NOI18N
            detailsLbl.setForeground(new java.awt.Color(27, 105, 120));
            detailsLbl.setText("Details");
            backgroundImagePanel.add(detailsLbl);
            detailsLbl.setBounds(250, 80, 60, 18);

            yearLbl.setFont(new java.awt.Font("Montserrat Alternates Light", 1, 14)); // NOI18N
            yearLbl.setForeground(new java.awt.Color(27, 105, 120));
            yearLbl.setText("Year");
            backgroundImagePanel.add(yearLbl);
            yearLbl.setBounds(90, 80, 38, 18);

            monthsLbl.setFont(new java.awt.Font("Montserrat Alternates Light", 1, 14)); // NOI18N
            monthsLbl.setForeground(new java.awt.Color(27, 105, 120));
            monthsLbl.setText("Month");
            backgroundImagePanel.add(monthsLbl);
            monthsLbl.setBounds(90, 130, 60, 18);

            dayLbl.setFont(new java.awt.Font("Montserrat Alternates Light", 1, 14)); // NOI18N
            dayLbl.setForeground(new java.awt.Color(27, 105, 120));
            dayLbl.setText("Day");
            backgroundImagePanel.add(dayLbl);
            dayLbl.setBounds(90, 180, 60, 18);

            jButton1.setBackground(new java.awt.Color(22, 97, 112));
            jButton1.setFont(new java.awt.Font("Montserrat Alternates SemiBold", 1, 12)); // NOI18N
            jButton1.setForeground(new java.awt.Color(236, 236, 236));
            jButton1.setText("Refresh ");
            jButton1.setBorderPainted(false);
            jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            jButton1.setFocusable(false);
            backgroundImagePanel.add(jButton1);
            jButton1.setBounds(430, 100, 100, 22);

            todayDateLabel.setFont(new java.awt.Font("Montserrat Alternates Medium", 1, 12)); // NOI18N
            todayDateLabel.setForeground(new java.awt.Color(26, 90, 101));
            todayDateLabel.setText("Sat, 24, 12, 2022");
            backgroundImagePanel.add(todayDateLabel);
            todayDateLabel.setBounds(260, 102, 100, 16);

            datePickerContainer.setIcon(new javax.swing.ImageIcon("E:\\OneDrive - Malta College of Arts, Science & Technology\\MCAST\\MCAST MQF4\\Year 2\\Lv4-Y2-Sem1\\ITSFT-406-2005  Programming Concepts\\Assignments\\Assignment 2\\EcapeRooms_MullerJohn4_2A\\Assets\\Images\\DatePicker.png")); // NOI18N
            backgroundImagePanel.add(datePickerContainer);
            datePickerContainer.setBounds(250, 100, 160, 20);

            bookingsInfoContainer.setEditable(false);
            bookingsInfoContainer.setBackground(new java.awt.Color(39, 64, 68));
            bookingsInfoContainer.setFont(new java.awt.Font("Montserrat Alternates ExLight", 0, 12)); // NOI18N
            bookingsInfoContainer.setForeground(new java.awt.Color(236, 236, 236));
            bookingsInfoContainer.setBorder(null);
            bookingsInfoContainer.setFocusable(false);
            bookingsInfoContainer.setSelectionColor(new java.awt.Color(39, 64, 68));
            bookingsInfoContainer.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    bookingsInfoContainerActionPerformed(evt);
                }
            });
            backgroundImagePanel.add(bookingsInfoContainer);
            bookingsInfoContainer.setBounds(250, 130, 280, 130);

            backgroundImagelabel.setBackground(new java.awt.Color(25, 43, 50));
            backgroundImagelabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
            backgroundImagelabel.setIcon(new javax.swing.ImageIcon("E:\\OneDrive - Malta College of Arts, Science & Technology\\MCAST\\MCAST MQF4\\Year 2\\Lv4-Y2-Sem1\\ITSFT-406-2005  Programming Concepts\\Assignments\\Assignment 2\\EcapeRooms_MullerJohn4_2A\\Assets\\Images\\MainPage.png")); // NOI18N
            backgroundImagelabel.setToolTipText("");
            backgroundImagelabel.setIconTextGap(0);
            backgroundImagelabel.setRequestFocusEnabled(false);
            backgroundImagePanel.add(backgroundImagelabel);
            backgroundImagelabel.setBounds(0, 0, 900, 450);

            menuBar.setBackground(new java.awt.Color(39, 64, 68));
            menuBar.setForeground(new java.awt.Color(236, 236, 236));
            menuBar.setFocusTraversalPolicyProvider(true);

            fileButton.setText("File");

            refreshBookingMenuButton.setText("Refresh booking");
            fileButton.add(refreshBookingMenuButton);

            exitButton.setText("Exit");
            fileButton.add(exitButton);

            menuBar.add(fileButton);

            HelpButton.setText("Help");

            aboutButton.setText("About");
            HelpButton.add(aboutButton);

            menuBar.add(HelpButton);

            setJMenuBar(menuBar);

            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
            getContentPane().setLayout(layout);
            layout.setHorizontalGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(backgroundImagePanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 900, Short.MAX_VALUE)
            );
            layout.setVerticalGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(backgroundImagePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 448, javax.swing.GroupLayout.PREFERRED_SIZE)
            );

            pack();
        }// </editor-fold>

        private void yearsComboBoxActionPerformed(java.awt.event.ActionEvent evt) {
            fillDaysComboBox();
            // System.out.println("years Clicked");
        }

        private void getBookingBtnActionPerformed(java.awt.event.ActionEvent evt) {
            String day = daysComboBox.getSelectedItem().toString().length() == 1 ? ("0" + daysComboBox.getSelectedItem().toString()) : (daysComboBox.getSelectedItem().toString());
            String date = String.format("%s/%s/%s", day, MONTHS_LIST[monthsComboBox.getSelectedIndex()], yearsComboBox.getSelectedItem());
            filterBookings(date);
            updateIcons(filteredList);
        }

        private void monthsComboBoxActionPerformed(java.awt.event.ActionEvent evt) {
            fillDaysComboBox();
            //System.out.println("months Clicked");
        }

        private void bookingsInfoContainerActionPerformed(java.awt.event.ActionEvent evt) {
            // TODO add your handling code here:
        }

        private void roomOneActionPerformed(java.awt.event.ActionEvent evt) {
            // TODO add your handling code here:
        }

        private void roomTwoActionPerformed(java.awt.event.ActionEvent evt) {
            // TODO add your handling code here:
        }

        private void roomThreeActionPerformed(java.awt.event.ActionEvent evt) {
            // TODO add your handling code here:
        }

        private void RoomFourActionPerformed(java.awt.event.ActionEvent evt) {
            // TODO add your handling code here:
        }

        private void RoomFiveActionPerformed(java.awt.event.ActionEvent evt) {
            // TODO add your handling code here:
        }

        /**
         * @param args the command line arguments
         */
        public static void main(String args[]) {
            /* Set the Nimbus look and feel */
            //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
            /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
             * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
             */
            try {
                for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        javax.swing.UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
            } catch (ClassNotFoundException ex) {
                java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (InstantiationException ex) {
                java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (javax.swing.UnsupportedLookAndFeelException ex) {
                java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
            //</editor-fold>
            //</editor-fold>
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    new MainForm().setVisible(true);
                }
            });
        }

        // Variables declaration - do not modify
        private javax.swing.JMenu HelpButton;
        private javax.swing.JButton RoomFive;
        private javax.swing.JButton RoomFour;
        private javax.swing.JMenuItem aboutButton;
        private javax.swing.JPanel backgroundImagePanel;
        private static javax.swing.JLabel backgroundImagelabel;
        private javax.swing.JTextField bookingsInfoContainer;
        private javax.swing.JLabel datePickerContainer;
        private javax.swing.JLabel dayLbl;
        private javax.swing.JComboBox<String> daysComboBox;
        private javax.swing.JLabel detailsLbl;
        private javax.swing.JMenuItem exitButton;
        private javax.swing.JMenu fileButton;
        private javax.swing.JButton getBookingBtn;
        private javax.swing.JButton jButton1;
        private javax.swing.JMenuBar menuBar;
        private javax.swing.JComboBox<String> monthsComboBox;
        private javax.swing.JLabel monthsLbl;
        private javax.swing.JMenuItem refreshBookingMenuButton;
        private javax.swing.JButton roomOne;
        private javax.swing.JButton roomThree;
        private javax.swing.JButton roomTwo;
        private javax.swing.JLabel todayDateLabel;
        private javax.swing.JLabel yearLbl;
        private javax.swing.JComboBox<String> yearsComboBox;
        // End of variables declaration
    }

    /**
     * @author John Muller
     */
package eu.johnmuller.ecaperooms.Views;

import eu.johnmuller.ecaperooms.Classes.Dialog;
import eu.johnmuller.ecaperooms.Classes.FileIO;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.*;

    public class MainForm extends javax.swing.JFrame {
        // create custom LOGO
        private static final ImageIcon LOGO = new ImageIcon(".//Assets//Images//Logo.png");
        private static final String FILEPATH = ".//Assets//Data/bookings.txt";
        private static final DateTimeFormatter DATE_PATTERN = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        private static final String[] MONTHS_LIST = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};

        private static final String[] AVAILABLE_ROOMS_ICON_PATH = {".//Assets//Images//roomOneFree.png", ".//Assets//Images//roomTwoFree.png", ".//Assets//Images//roomThreeFree.png", ".//Assets//Images//roomFourFree.png", ".//Assets//Images//roomFiveFree.png",};
        private static final String[] BUSY_ROOMS_ICON_PATH = {".//Assets//Images//roomOneBusy.png", ".//Assets//Images//roomTwoBusy.png", ".//Assets//Images//roomThreeBusy.png", ".//Assets//Images//roomFourBusy.png", ".//Assets//Images//roomFiveBusy.png",};
        // used to store the bookings info then display it as upcoming bookings
        ArrayList<String> upComingBookings = new ArrayList<>();

        ArrayList<Integer> filteredList = new ArrayList<>();
        ArrayList<Integer> roomNumbers = new ArrayList<>();
        ArrayList<Integer> numberOfPersons = new ArrayList<>();
        ArrayList<String> names = new ArrayList<>();
        ArrayList<String> surnames = new ArrayList<>();
        ArrayList<String> contactNumbers = new ArrayList<>();
        ArrayList<String> extraRequirements = new ArrayList<>();
        ArrayList<LocalDate> dates = new ArrayList<>();


        // these functions are not pure-functions (it modifies data outside it)
        // cause there is no need to make a simple app complicated
        public void loadBookings() {
            // all commented code was used For testing purposes

            // Read data From File
            String dataString = FileIO.readTextFile(this, FILEPATH);
            // Split each line and store it in a list to iterate over it
            String[] dataList = dataString.split("\n");

            // I had an error in fileIO it was returning null, so I commented the below code after I fixed the error
            //System.out.println("'mainForm' Data string: " + dataString);
            //System.out.println("'mainForm' array list: " + Arrays.toString(dataList));
            //System.out.println("'mainForm' list length: " + dataList.length);

            for (String row : dataList) {
                String[] data = row.split(",");
                //System.out.println(data);
                // storing the data into their lists "manually"
                roomNumbers.add(Integer.parseInt(data[0]));
                names.add(data[1]);
                surnames.add(data[2]);
                contactNumbers.add(data[3]);
                // parse the date and fixing its format
                dates.add(LocalDate.parse(data[4], DATE_PATTERN));
                numberOfPersons.add(Integer.parseInt(data[5]));
                // if data.length equals 7
                // that means the booking has extra requirements, so I appended to the list
                if (data.length == 7) extraRequirements.add(data[6]);
                else
                    // else append empty string
                    extraRequirements.add("");
            }

            // making sure that everything was stored correctly
            //System.out.println(Arrays.toString(roomNumbers.toArray()));
            //System.out.println(Arrays.toString(names.toArray()));
            //System.out.println(Arrays.toString(surnames.toArray()));
            //System.out.println(Arrays.toString(contactNumbers.toArray()));
            //System.out.println(Arrays.toString(dates.toArray()));
            //System.out.println(Arrays.toString(numberOfPersons.toArray()));
            //System.out.println(Arrays.toString(extraRequirements.toArray()));
        }

        public void filterBookings(String date) {
            LocalDate selectedDate = LocalDate.parse(date, DATE_PATTERN);
            String bookingInfo;
            upComingBookings.removeAll(upComingBookings);
            filteredList.removeAll(filteredList);

            for (int i = 0; i < dates.size(); i++) {
                if (selectedDate.compareTo(dates.get(i)) == 0) {
                    bookingInfo = String.format("Room Number: %s\n" + "Guest name: %s\n" + "Guest surname: %s\n" + "Guest phone Number: %s\n" + "number of persons: %s\n" + "Date: %s\n" + "Extra requirements: %s\n", roomNumbers.get(i), names.get(i), surnames.get(i), contactNumbers.get(i), numberOfPersons.get(i), dates.get(i), extraRequirements.get(i));
                    filteredList.add(i);
                    upComingBookings.add(bookingInfo);
                    System.out.println(i + ": " + bookingInfo);
                }

                //else System.out.println("failed: " + dates.get(i) + "selected date is: " + selectedDate);
            }
        }

        public void fillDaysComboBox() {
            int year = Integer.parseInt(yearsComboBox.getSelectedItem().toString());
            YearMonth daysInMonth = YearMonth.of(year, Month.of(monthsComboBox.getSelectedIndex() + 1));
            //System.out.println(daysInMonth);
            //System.out.println(daysInMonth.lengthOfMonth());
            int previousIndex = daysComboBox.getSelectedIndex();
            daysComboBox.removeAllItems(); // clear the list
            for (int day = 1; day <= daysInMonth.lengthOfMonth(); day++) {
                //System.out.println(day);
                daysComboBox.addItem(Integer.toString(day));
            }

            // keep the previous day selected in case the user
            // selected the day then month it won't be visually deleted
            if (daysComboBox.getItemCount() >= previousIndex) {
                if (daysComboBox.getItemCount() == previousIndex) daysComboBox.setSelectedIndex(previousIndex - 1);
                else daysComboBox.setSelectedIndex(previousIndex);
            } else {
                daysComboBox.setSelectedIndex(daysComboBox.getItemCount() - 1);
            }
        }

        public void fillDatesComboBox() {
            // apply / fill after two years ,
            // so the year will be dynamically filed according
            // to the year (date) the app is loaded
            yearsComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{Integer.toString(LocalDate.now().getYear()), Integer.toString(LocalDate.now().getYear() + 1), Integer.toString(LocalDate.now().getYear() + 2),}));

            // no need to fill months using code because months does not change
            // but in case I wanted to fill it using code
            // monthsComboBox.setModel(
            //          new javax.swing.DefaultComboBoxModel<>(new String[]{
            //          "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"}));
            // or using  a for loop

            // fill days
            fillDaysComboBox();

            LocalDate now = LocalDate.now();
            //String[] date = now.toString().split("-");
            //System.out.println(date[0]); // years
            //System.out.println(date[1]); // months
            //System.out.println(date[2]);  // days
        }

        public void updateIcons(@NotNull ArrayList<Integer> bookings) {
            ArrayList<JButton> buttons = new ArrayList<>(Arrays.asList(roomOne, roomTwo, roomThree, roomFour, roomFive));

            for (int booking : bookings) {
                System.out.println(roomNumbers.get(booking));
            }
//        if (bookings.size() > 0) {
//            for (int i = 0; i <= buttons.size(); i++) {
//                if (bookings.size() > i) {
//                    if (roomNumbers.contains(bookings.get(i))) {
//                        buttons.get(roomNumbers.get(bookings.get(i))).setIcon(new javax.swing.ImageIcon(BUSY_ROOMS_ICON_PATH[roomNumbers.get(bookings.get(i))]));
//                        System.out.println(roomNumbers.get(i) + ": busy");
//                    } else {
//                        //buttons.get(i).setIcon(new javax.swing.ImageIcon(AVAILABLE_ROOMS_ICON_PATH[i]));
//                        System.out.println(roomNumbers.contains(bookings.get(i)));
//                    }
//
//                }
//            }
            // }
        }

        public void updateLabel(){

        }

        public MainForm() {
            initComponents();
            // apply custom LOGO
            this.setIconImage(LOGO.getImage());
            // fill the combo boxes
            fillDatesComboBox();
            // load bookings
            loadBookings();
            // update UI

        }

        /**
         * This method is called from within the constructor to initialize the form.
         * WARNING: Do NOT modify this code. The content of this method is always
         * regenerated by the Form Editor.
         */
        @SuppressWarnings("unchecked")
        // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
        private void initComponents() {

            backgroundImagePanel = new javax.swing.JPanel();
            roomOne = new javax.swing.JButton();
            roomTwo = new javax.swing.JButton();
            roomThree = new javax.swing.JButton();
            roomFour = new javax.swing.JButton();
            roomFive = new javax.swing.JButton();
            monthsComboBox = new javax.swing.JComboBox<>();
            daysComboBox = new javax.swing.JComboBox<>();
            yearsComboBox = new javax.swing.JComboBox<>();
            getBookingBtn = new javax.swing.JButton();
            jLabel1 = new javax.swing.JLabel();
            jLabel2 = new javax.swing.JLabel();
            jLabel3 = new javax.swing.JLabel();
            backgroundImageLabel = new javax.swing.JLabel();
            jMenuBar1 = new javax.swing.JMenuBar();
            jMenu1 = new javax.swing.JMenu();
            refreshButton = new javax.swing.JMenuItem();
            exitButton = new javax.swing.JMenuItem();
            jMenu2 = new javax.swing.JMenu();
            aboutButton = new javax.swing.JMenuItem();

            setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
            setTitle("Escape Rooms");
            setResizable(false);

            backgroundImagePanel.setLayout(null);
            roomOne.setBackground(new java.awt.Color(38, 65, 70));
            roomOne.setBorderPainted(false);
            roomOne.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            roomOne.setHideActionText(true);
            roomOne.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
            roomOne.setIconTextGap(0);
            roomOne.setPreferredSize(new java.awt.Dimension(70, 70));
            backgroundImagePanel.add(roomOne);
            roomOne.setBounds(90, 270, 80, 80);

            roomTwo.setBackground(new java.awt.Color(38, 65, 70));
            roomTwo.setBorderPainted(false);
            roomTwo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            roomTwo.setHideActionText(true);
            roomTwo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
            roomTwo.setIconTextGap(0);
            roomTwo.setPreferredSize(new java.awt.Dimension(70, 70));
            backgroundImagePanel.add(roomTwo);
            roomTwo.setBounds(180, 270, 80, 80);

            roomThree.setBackground(new java.awt.Color(38, 65, 70));
            roomThree.setBorderPainted(false);
            roomThree.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            roomThree.setHideActionText(true);
            roomThree.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
            roomThree.setIconTextGap(0);
            roomThree.setPreferredSize(new java.awt.Dimension(70, 70));
            backgroundImagePanel.add(roomThree);
            roomThree.setBounds(270, 270, 80, 80);

            roomFour.setBackground(new java.awt.Color(38, 65, 70));
            roomFour.setBorderPainted(false);
            roomFour.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            roomFour.setHideActionText(true);
            roomFour.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
            roomFour.setIconTextGap(0);
            roomFour.setPreferredSize(new java.awt.Dimension(70, 70));
            backgroundImagePanel.add(roomFour);
            roomFour.setBounds(360, 270, 80, 80);

            roomFive.setBackground(new java.awt.Color(38, 65, 70));
            roomFive.setBorderPainted(false);
            roomFive.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            roomFive.setHideActionText(true);
            roomFive.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
            roomFive.setIconTextGap(0);
            roomFive.setPreferredSize(new java.awt.Dimension(70, 70));
            backgroundImagePanel.add(roomFive);
            roomFive.setBounds(450, 270, 80, 80);

            monthsComboBox.setBackground(new java.awt.Color(39, 64, 68));
            monthsComboBox.setEditable(true);
            monthsComboBox.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
            monthsComboBox.setForeground(new java.awt.Color(225, 225, 225));
            monthsComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"}));
            monthsComboBox.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 0, true));
            monthsComboBox.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            monthsComboBox.setEditable(false);
            monthsComboBox.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    monthsComboBoxActionPerformed(evt);
                }
            });
            backgroundImagePanel.add(monthsComboBox);
            monthsComboBox.setBounds(90, 170, 120, 24);

            daysComboBox.setBackground(new java.awt.Color(39, 64, 68));
            daysComboBox.setEditable(true);
            daysComboBox.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
            daysComboBox.setForeground(new java.awt.Color(225, 225, 225));
            daysComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"}));
            daysComboBox.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 0, true));
            daysComboBox.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            backgroundImagePanel.add(daysComboBox);
            daysComboBox.setBounds(90, 230, 120, 24);
            daysComboBox.setEditable(false);

            yearsComboBox.setBackground(new java.awt.Color(39, 64, 68));
            yearsComboBox.setEditable(true);
            yearsComboBox.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
            yearsComboBox.setForeground(new java.awt.Color(225, 225, 225));
            yearsComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"2022", "2023", "2024"}));
            yearsComboBox.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 0, true));
            yearsComboBox.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            yearsComboBox.setEditable(false);
            yearsComboBox.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    yearsComboBoxActionPerformed(evt);
                }
            });
            backgroundImagePanel.add(yearsComboBox);
            yearsComboBox.setBounds(90, 110, 120, 24);

            getBookingBtn.setBackground(new java.awt.Color(27, 105, 120));
            getBookingBtn.setFont(new java.awt.Font("Montserrat Alternates SemiBold", 1, 14)); // NOI18N
            getBookingBtn.setForeground(new java.awt.Color(225, 225, 225));
            getBookingBtn.setText("Get Bookings");
            getBookingBtn.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 0, true));
            getBookingBtn.setBorderPainted(false);
            getBookingBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            getBookingBtn.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    getBookingBtnActionPerformed(evt);
                }
            });
            backgroundImagePanel.add(getBookingBtn);
            getBookingBtn.setBounds(390, 100, 140, 30);

            jLabel1.setFont(new java.awt.Font("Montserrat Alternates Light", 1, 14)); // NOI18N
            jLabel1.setForeground(new java.awt.Color(27, 105, 120));
            jLabel1.setText("Day");
            backgroundImagePanel.add(jLabel1);
            jLabel1.setBounds(90, 210, 60, 18);

            jLabel2.setFont(new java.awt.Font("Montserrat Alternates Light", 1, 14)); // NOI18N
            jLabel2.setForeground(new java.awt.Color(27, 105, 120));
            jLabel2.setText("Year");
            backgroundImagePanel.add(jLabel2);
            jLabel2.setBounds(90, 90, 38, 18);

            jLabel3.setFont(new java.awt.Font("Montserrat Alternates Light", 1, 14)); // NOI18N
            jLabel3.setForeground(new java.awt.Color(27, 105, 120));
            jLabel3.setText("Month");
            backgroundImagePanel.add(jLabel3);
            jLabel3.setBounds(90, 150, 60, 18);
            backgroundImagePanel.add(backgroundImageLabel);
            backgroundImageLabel.setBounds(0, -10, 900, 460);

            jMenuBar1.setBackground(new java.awt.Color(27, 105, 120));
            jMenuBar1.setBorder(null);
            jMenuBar1.setForeground(new java.awt.Color(0, 153, 153));

            jMenu1.setText("File");



            jMenuBar1.add(jMenu1);

            jMenu2.setBackground(new java.awt.Color(0, 153, 153));
            jMenu2.setText("Help");

            aboutButton.setText("About");

            jMenu2.add(aboutButton);

            jMenuBar1.add(jMenu2);

            setJMenuBar(jMenuBar1);

            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
            getContentPane().setLayout(layout);
            layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(backgroundImagePanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 900, Short.MAX_VALUE));
            layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(backgroundImagePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 448, javax.swing.GroupLayout.PREFERRED_SIZE));

            pack();
        }// </editor-fold>//GEN-END:initComponents

        private void yearsComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_yearsComboBoxActionPerformed
            fillDaysComboBox();
            // System.out.println("years Clicked");
        }//GEN-LAST:event_yearsComboBoxActionPerformed



        private void getBookingBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_getBookingBtnActionPerformed
            String day = daysComboBox.getSelectedItem().toString().length() == 1 ? ("0" + daysComboBox.getSelectedItem().toString()) : (daysComboBox.getSelectedItem().toString());
            String date = String.format("%s/%s/%s", day, MONTHS_LIST[monthsComboBox.getSelectedIndex()], yearsComboBox.getSelectedItem());
            filterBookings(date);
            updateIcons(filteredList);
        }//GEN-LAST:event_getBookingBtnActionPerformed

        private void monthsComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_monthsComboBoxActionPerformed
            fillDaysComboBox();
            //System.out.println("months Clicked");
        }//GEN-LAST:event_monthsComboBoxActionPerformed

        /**
         * @param args the command line arguments
         */
        public static void main(String args[]) {
            /* Set the Nimbus look and feel */
            //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
            /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
             * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
             */
            try {
                for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        javax.swing.UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
            } catch (ClassNotFoundException ex) {
                java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (InstantiationException ex) {
                java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (javax.swing.UnsupportedLookAndFeelException ex) {
                java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
            //</editor-fold>
            //</editor-fold>
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    new MainForm().setVisible(true);
                }
            });
        }

        // Variables declaration - do not modify//GEN-BEGIN:variables
        private javax.swing.JMenuItem aboutButton;
        private javax.swing.JPanel backgroundImagePanel;
        private static javax.swing.JLabel backgroundImageLabel;
        private javax.swing.JComboBox<String> daysComboBox;
        private javax.swing.JMenuItem exitButton;
        private javax.swing.JButton getBookingBtn;
        private javax.swing.JLabel jLabel1;
        private javax.swing.JLabel jLabel2;
        private javax.swing.JLabel jLabel3;
        private javax.swing.JMenu jMenu1;
        private javax.swing.JMenu jMenu2;
        private javax.swing.JMenuBar jMenuBar1;
        private javax.swing.JComboBox<String> monthsComboBox;
        private javax.swing.JMenuItem refreshButton;
        private javax.swing.JButton roomOne;
        private javax.swing.JButton roomTwo;
        private javax.swing.JButton roomThree;
        private javax.swing.JButton roomFour;
        private javax.swing.JButton roomFive;
        private javax.swing.JComboBox<String> yearsComboBox;
        // End of variables declaration//GEN-END:variables
    }







    /**
     * @author John Muller
     */
package eu.johnmuller.ecaperooms.Views;

import eu.johnmuller.ecaperooms.Classes.FileIO;
import eu.johnmuller.ecaperooms.Classes.Dialog;
import org.jetbrains.annotations.NotNull;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import javax.swing.*;


    public class MainForm extends javax.swing.JFrame {

        String today = new Date().toLocaleString();
        // create custom LOGO
        private static final ImageIcon LOGO = new ImageIcon(".//Assets//Images//Logo.png");
        public static final String FILEPATH = ".//Assets//Data/bookings.txt";
        private static final DateTimeFormatter DATE_PATTERN = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        private static final String[] MONTHS_LIST = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};

        private static final String[] AVAILABLE_ROOMS_ICON_PATH = {".//Assets//Images//roomOneFree.png", ".//Assets//Images//roomTwoFree.png", ".//Assets//Images//roomThreeFree.png", ".//Assets//Images//roomFourFree.png", ".//Assets//Images//roomFiveFree.png",};

        private static final String[] BUSY_ROOMS_ICON_PATH = {".//Assets//Images//roomOneBusy.png", ".//Assets//Images//roomTwoBusy.png", ".//Assets//Images//roomThreeBusy.png", ".//Assets//Images//roomFourBusy.png", ".//Assets//Images//roomFiveBusy.png",};
        // used to store the bookings info then display it as upcoming bookings
        ArrayList<String> upComingBookings = new ArrayList<>();

        ArrayList<Integer> filteredList = new ArrayList<>();
        ArrayList<Integer> roomNumbers = new ArrayList<>();
        ArrayList<Integer> numberOfPersons = new ArrayList<>();
        ArrayList<String> names = new ArrayList<>();
        ArrayList<String> surnames = new ArrayList<>();
        ArrayList<String> contactNumbers = new ArrayList<>();
        ArrayList<String> extraRequirements = new ArrayList<>();
        ArrayList<LocalDate> dates = new ArrayList<>();


        // these functions are not pure-functions (it modifies data outside it)
        // cause there is no need to make a simple app complicated

        public void loadBookings() {
            // Read data From File
            String dataString = FileIO.readTextFile(this, FILEPATH);
            // Split each line and store it in a list to iterate over it
            String[] dataList = dataString.split("\n");
            for (String row : dataList) {
                String[] data = row.split(",");
                //System.out.println(data);
                // storing the data into their lists "manually"
                roomNumbers.add(Integer.parseInt(data[0].trim()));
                names.add(data[1].trim());
                surnames.add(data[2].trim());
                contactNumbers.add(data[3].trim());
                // parse the date and fixing its format
                // sometimes it gives an error while parsing the date so i wrap it with
                // a try catch
                try {
                    dates.add(LocalDate.parse(data[4].trim(), DATE_PATTERN));
                } catch (DateTimeException error) {
                    dates.add(LocalDate.now());
                    Dialog.notify(this, "Database error", "An error occurred while trying to parse date format:'" + data[4] + "'\naccepted formats are '" + "[dd/mm/yyyy] | [dd-mm/yyyy]'\ndate stored for " + data[1] + " " + data[2] + " room " + data[0] + " is: " + LocalDate.now());
                }
                numberOfPersons.add(Integer.parseInt(data[5].trim()));
                // if data.length equals 7
                // that means the booking has extra requirements, so I appended to the list
                if (data.length == 7) extraRequirements.add(data[6].trim());
                else
                    // else append empty string
                    extraRequirements.add("");
            }
        }

        public void applyCurrentDateToComboBoxes() {
            String dateString = LocalDate.now().toString();
            String[] date = dateString.split("-");
            yearsComboBox.setSelectedItem(date[0]);
            monthsComboBox.setSelectedIndex(Integer.parseInt(date[1]) - 1);
            daysComboBox.setSelectedItem(date[2]);
        }

        public LocalDate getDate() {
            String day = daysComboBox.getSelectedItem().toString().length() == 1 ? ("0" + daysComboBox.getSelectedItem().toString()) : (daysComboBox.getSelectedItem().toString());
            String date = String.format("%s/%s/%s", day, MONTHS_LIST[monthsComboBox.getSelectedIndex()], yearsComboBox.getSelectedItem());
            return LocalDate.parse(date, DATE_PATTERN);

        }

        public void fillDaysComboBox() {
            int year = Integer.parseInt(yearsComboBox.getSelectedItem().toString());
            YearMonth daysInMonth = YearMonth.of(year, Month.of(monthsComboBox.getSelectedIndex() + 1));
            int previousIndex = daysComboBox.getSelectedIndex();
            daysComboBox.removeAllItems(); // clear the list
            for (int day = 1; day <= daysInMonth.lengthOfMonth(); day++) {
                daysComboBox.addItem(Integer.toString(day));
            }

            // keep the previous day selected in case the user
            // selected the day then month it won't be visually deleted
            if (daysComboBox.getItemCount() >= previousIndex) {
                if (daysComboBox.getItemCount() == previousIndex) daysComboBox.setSelectedIndex(previousIndex - 1);
                else daysComboBox.setSelectedIndex(previousIndex);
            } else {
                daysComboBox.setSelectedIndex(daysComboBox.getItemCount() - 1);
            }
        }

        public void fillDatesComboBox() {
            // apply / fill after two years ,
            // so the year will be dynamically filed according
            // to the year (date) the app is loaded
            yearsComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{Integer.toString(LocalDate.now().getYear()), Integer.toString(LocalDate.now().getYear() + 1), Integer.toString(LocalDate.now().getYear() + 2),}));

            // no need to fill months using code because months does not change
            // but in case I wanted to fill it using code
            // monthsComboBox.setModel(
            //          new javax.swing.DefaultComboBoxModel<>(new String[]{
            //          "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"}));
            // or using  a for loop

            // fill days
            fillDaysComboBox();
        }

        public void filterBookings(LocalDate selectedDate) {
            String bookingInfo;
            upComingBookings.removeAll(upComingBookings);
            filteredList.removeAll(filteredList);

            for (int i = 0; i < dates.size(); i++) {
                if (selectedDate.compareTo(dates.get(i)) == 0) {
                    bookingInfo = String.format("Room Number: %s\n" + "Guest name: %s\n" + "Guest surname: %s\n" + "Guest phone Number: %s\n" + "number of persons: %s\n" + "Date: %s\n" + "Extra requirements: %s\n", roomNumbers.get(i), names.get(i), surnames.get(i), contactNumbers.get(i), numberOfPersons.get(i), dates.get(i), extraRequirements.get(i));
                    filteredList.add(i);
                    upComingBookings.add(bookingInfo);
                    System.out.println(i + ": " + bookingInfo);
                }
                updateIcons(filteredList);
                updateLabel(upComingBookings, selectedDate.toString());
            }
        }

        public void makeBooking(int room) {
            //how should I inform the user about the booked rooms
            // using a dialog or label
            MakeBooking make = new MakeBooking();
            // pass the room number
            make.roomNumber = room;
            // show the form
            make.setVisible(true);
        }

        public void viewBooking(int room, String name, String surname, String contactNumber, String extraInfo, String numberOfPersons) {
            ViewBooking view = new ViewBooking();
            view.roomNumberLbl.setText(Integer.toString(room));
            view.surnameLbl.setText(surname);
            view.nameLbl.setText(name);
            view.contactNumLbl.setText(contactNumber);
            view.extraReqLbl.setText(extraInfo);
            view.numOfPersonsLbl.setText(numberOfPersons);

            view.setVisible(true);
        }

        public void updateIcons(@NotNull ArrayList<Integer> bookings) {
            ArrayList<JButton> buttons = new ArrayList<>(Arrays.asList(roomOne, roomTwo, roomThree, roomFour, roomFive));

            for (int booking : bookings) {
                System.out.println(roomNumbers.get(booking));
            }
//        if (bookings.size() > 0) {
//            for (int i = 0; i <= buttons.size(); i++) {
//                if (bookings.size() > i) {
//                    if (roomNumbers.contains(bookings.get(i))) {
//                        buttons.get(roomNumbers.get(bookings.get(i))).setIcon(new javax.swing.ImageIcon(BUSY_ROOMS_ICON_PATH[roomNumbers.get(bookings.get(i))]));
//                        System.out.println(roomNumbers.get(i) + ": busy");
//                    } else {
//                        //buttons.get(i).setIcon(new javax.swing.ImageIcon(AVAILABLE_ROOMS_ICON_PATH[i]));
//                        System.out.println(roomNumbers.contains(bookings.get(i)));
//                    }
//
//                }
//            }
            // }
        }

        public void updateLabel(@NotNull ArrayList<String> upComingBookingsList, String date) {
            if (upComingBookingsList.size() != 0) {
                for (String booking : upComingBookingsList) {
                    bookingsInfoContainer.setText(booking);
                }
            } else {
                bookingsInfoContainer.setText("there is no bookings on this date: " + date);
            }
        }

        public void initUI() {
            // apply custom LOGO
            this.setIconImage(LOGO.getImage());
            // set current date label
            this.todayDateLabel.setText(today);

            // fill the combo boxes
            fillDatesComboBox();

            // this is an extra future i think it makes more since to have the current date selected
            applyCurrentDateToComboBoxes();

            // filter bookings to see if there is a booking for the current date
            filterBookings(getDate());

            // update UI
            //updateLabel(upComingBookings,);
        }

        public MainForm() {
            // load bookings on load
            loadBookings();
            // initialize components
            initComponents();
            // this is an extra future the explanation is inside the function
            initUI();
        }


        //Event Listeners
        // Menu buttons
        private void aboutButtonActionPerformed(java.awt.event.ActionEvent evt) {
            new AboutForm().setVisible(true);
        }

        private void refreshBookingMenuButton(java.awt.event.ActionEvent evt) {
            // reload bookings from the file
            loadBookings();
            // filter bookings
            filterBookings(getDate());
            // update icons
            updateIcons(filteredList);
            // update labels
            updateLabel(upComingBookings, getDate().toString());
            // inform the user that the bookings has been reloaded
            Dialog.notify(this, "Escape Rooms", "Bookings has been refreshed");
        }

        private void exitButtonActionPerformed(java.awt.event.ActionEvent evt) {
            if (Dialog.showConfirmationDialog(this, "Are you sure you want to exit?", "Exit")) {
                this.dispose();
            }
        }

        // combo box buttons
        private void yearsComboBoxActionPerformed(java.awt.event.ActionEvent evt) {
            // update days
            fillDaysComboBox();
        }

        private void monthsComboBoxActionPerformed(java.awt.event.ActionEvent evt) {
            // update days
            fillDaysComboBox();
        }

        // buttons
        private void getBookingBtnActionPerformed(java.awt.event.ActionEvent evt) {
            filterBookings(getDate());
            updateIcons(filteredList);
            updateLabel(upComingBookings, getDate().toString());
            Dialog.notify(this, "Escape Rooms", "Bookings has been fetched from the database!");
        }

        private void refreshBookingButtonActionPerformed(java.awt.event.ActionEvent evt) {
            // this refresh button refreshes all selected dates to the current date
            // and filter bookings of now()
            // so it will set the date to the current date and filter the list to today's date
            loadBookings();
            applyCurrentDateToComboBoxes();
            filterBookings(getDate());
            updateIcons(filteredList);
            updateLabel(upComingBookings, getDate().toString());
            Dialog.notify(this, "Escape Rooms", "Bookings has been reloaded\nand fetched from the database!");

        }

        private void roomOneActionPerformed(java.awt.event.ActionEvent evt) {
            if (true)
                //viewBooking(1,);
                System.out.println();
            else makeBooking(1);
        }

        private void roomTwoActionPerformed(java.awt.event.ActionEvent evt) {
            if (true)
                //viewBooking(2);
                System.out.println();

            else makeBooking(2);
        }

        private void roomThreeActionPerformed(java.awt.event.ActionEvent evt) {
            if (true)
                //viewBooking(3);
                System.out.println();

            else makeBooking(3);
        }

        private void RoomFourActionPerformed(java.awt.event.ActionEvent evt) {
            if (true)
                // viewBooking(4);
                System.out.println();

            else makeBooking(4);

        }

        private void RoomFiveActionPerformed(java.awt.event.ActionEvent evt) {
            if (true)
                //viewBooking(5);
                System.out.println();

            else makeBooking(5);

        }

        private void initComponents() {

            backgroundImagePanel = new javax.swing.JPanel();
            roomOne = new javax.swing.JButton();
            roomTwo = new javax.swing.JButton();
            roomThree = new javax.swing.JButton();
            roomFour = new javax.swing.JButton();
            roomFive = new javax.swing.JButton();
            monthsComboBox = new javax.swing.JComboBox<>();
            daysComboBox = new javax.swing.JComboBox<>();
            yearsComboBox = new javax.swing.JComboBox<>();
            getBookingBtn = new javax.swing.JButton();
            detailsLbl = new javax.swing.JLabel();
            yearLbl = new javax.swing.JLabel();
            monthsLbl = new javax.swing.JLabel();
            dayLbl = new javax.swing.JLabel();
            refreshBookingButton = new javax.swing.JButton();
            todayDateLabel = new javax.swing.JLabel();
            datePickerContainer = new javax.swing.JLabel();
            bookingsInfoContainer = new javax.swing.JTextField();
            backgroundImageLabel = new javax.swing.JLabel();
            menuBar = new javax.swing.JMenuBar();
            fileButton = new javax.swing.JMenu();
            refreshBookingMenuButton = new javax.swing.JMenuItem();
            exitButton = new javax.swing.JMenuItem();
            helpButton = new javax.swing.JMenu();
            aboutButton = new javax.swing.JMenuItem();

            setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
            setTitle("Escape Rooms");
            setResizable(false);
            backgroundImageLabel.setIcon(new javax.swing.ImageIcon(".//Assets//Images//MainPage.png"));

            backgroundImagePanel.setLayout(null);
            roomOne.setIcon(new javax.swing.ImageIcon(".//Assets//Images//roomOneFree.png"));

            roomOne.setBackground(new java.awt.Color(38, 65, 70));
            roomOne.setBorderPainted(false);
            roomOne.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            roomOne.setHideActionText(true);
            roomOne.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
            roomOne.setIconTextGap(0);
            roomOne.setPreferredSize(new java.awt.Dimension(70, 70));
            roomOne.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    roomOneActionPerformed(evt);
                }
            });
            backgroundImagePanel.add(roomOne);
            roomOne.setBounds(90, 270, 80, 80);
            roomTwo.setIcon(new javax.swing.ImageIcon(".//Assets//Images//roomTwoFree.png"));

            roomTwo.setBackground(new java.awt.Color(38, 65, 70));
            roomTwo.setBorderPainted(false);
            roomTwo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            roomTwo.setHideActionText(true);
            roomTwo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
            roomTwo.setIconTextGap(0);
            roomTwo.setPreferredSize(new java.awt.Dimension(70, 70));
            roomTwo.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    roomTwoActionPerformed(evt);
                }
            });
            backgroundImagePanel.add(roomTwo);
            roomTwo.setBounds(180, 270, 80, 80);
            roomThree.setIcon(new javax.swing.ImageIcon(".//Assets//Images//roomThreeFree.png"));

            roomThree.setBackground(new java.awt.Color(38, 65, 70));
            roomThree.setIcon(new javax.swing.ImageIcon(".//Assets//Images//roomThreeFree.png"));
            roomThree.setBorderPainted(false);
            roomThree.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            roomThree.setHideActionText(true);
            roomThree.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
            roomThree.setIconTextGap(0);
            roomThree.setPreferredSize(new java.awt.Dimension(70, 70));
            roomThree.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    roomThreeActionPerformed(evt);
                }
            });
            backgroundImagePanel.add(roomThree);
            roomThree.setBounds(270, 270, 80, 80);
            roomFour.setIcon(new javax.swing.ImageIcon(".//Assets//Images//roomFourFree.png"));

            roomFour.setBackground(new java.awt.Color(38, 65, 70));
            roomFour.setBorderPainted(false);
            roomFour.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            roomFour.setHideActionText(true);
            roomFour.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
            roomFour.setIconTextGap(0);
            roomFour.setPreferredSize(new java.awt.Dimension(70, 70));
            roomFour.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    RoomFourActionPerformed(evt);
                }
            });
            backgroundImagePanel.add(roomFour);
            roomFour.setBounds(360, 270, 80, 80);
            roomFive.setIcon(new javax.swing.ImageIcon(".//Assets//Images//roomFiveFree.png"));

            roomFive.setBackground(new java.awt.Color(38, 65, 70));
            roomFive.setBorderPainted(false);
            roomFive.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            roomFive.setHideActionText(true);
            roomFive.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
            roomFive.setIconTextGap(0);
            roomFive.setPreferredSize(new java.awt.Dimension(70, 70));
            roomFive.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    RoomFiveActionPerformed(evt);
                }
            });
            backgroundImagePanel.add(roomFive);
            roomFive.setBounds(450, 270, 80, 80);

            monthsComboBox.setBackground(new java.awt.Color(39, 64, 68));
            monthsComboBox.setFont(new java.awt.Font("Segoe UI", 1, 14));
            monthsComboBox.setForeground(new java.awt.Color(225, 225, 225));
            monthsComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"}));
            monthsComboBox.setBorder(null);
            monthsComboBox.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            monthsComboBox.setFocusable(false);
            monthsComboBox.setLightWeightPopupEnabled(false);
            monthsComboBox.setRequestFocusEnabled(false);
            monthsComboBox.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    monthsComboBoxActionPerformed(evt);
                }
            });
            backgroundImagePanel.add(monthsComboBox);
            monthsComboBox.setBounds(90, 150, 120, 20);

            daysComboBox.setBackground(new java.awt.Color(39, 64, 68));
            daysComboBox.setFont(new java.awt.Font("Segoe UI", 1, 14));
            daysComboBox.setForeground(new java.awt.Color(225, 225, 225));
            daysComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"}));
            daysComboBox.setBorder(null);
            daysComboBox.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            daysComboBox.setFocusable(false);
            daysComboBox.setLightWeightPopupEnabled(false);
            daysComboBox.setRequestFocusEnabled(false);
            backgroundImagePanel.add(daysComboBox);
            daysComboBox.setBounds(90, 200, 120, 20);

            yearsComboBox.setBackground(new java.awt.Color(39, 64, 68));
            yearsComboBox.setFont(new java.awt.Font("Segoe UI", 1, 14));
            yearsComboBox.setForeground(new java.awt.Color(225, 225, 225));
            yearsComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"2022", "2023", "2024"}));
            yearsComboBox.setBorder(null);
            yearsComboBox.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            yearsComboBox.setFocusable(false);
            yearsComboBox.setLightWeightPopupEnabled(false);
            yearsComboBox.setRequestFocusEnabled(false);
            yearsComboBox.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    yearsComboBoxActionPerformed(evt);
                }
            });
            backgroundImagePanel.add(yearsComboBox);
            yearsComboBox.setBounds(90, 100, 120, 20);

            getBookingBtn.setBackground(new java.awt.Color(27, 105, 120));
            getBookingBtn.setFont(new java.awt.Font("Montserrat Alternates SemiBold", 1, 12));
            getBookingBtn.setForeground(new java.awt.Color(225, 225, 225));
            getBookingBtn.setText("Get Bookings");
            getBookingBtn.setBorderPainted(false);
            getBookingBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            getBookingBtn.setFocusable(false);
            getBookingBtn.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    getBookingBtnActionPerformed(evt);
                }
            });
            backgroundImagePanel.add(getBookingBtn);
            getBookingBtn.setBounds(90, 230, 120, 30);

            detailsLbl.setFont(new java.awt.Font("Montserrat Alternates Light", 1, 14));
            detailsLbl.setForeground(new java.awt.Color(27, 105, 120));
            detailsLbl.setText("Details");
            backgroundImagePanel.add(detailsLbl);
            detailsLbl.setBounds(250, 80, 60, 18);

            yearLbl.setFont(new java.awt.Font("Montserrat Alternates Light", 1, 14));
            yearLbl.setForeground(new java.awt.Color(27, 105, 120));
            yearLbl.setText("Year");
            backgroundImagePanel.add(yearLbl);
            yearLbl.setBounds(90, 80, 38, 18);

            monthsLbl.setFont(new java.awt.Font("Montserrat Alternates Light", 1, 14));
            monthsLbl.setForeground(new java.awt.Color(27, 105, 120));
            monthsLbl.setText("Month");
            backgroundImagePanel.add(monthsLbl);
            monthsLbl.setBounds(90, 130, 60, 18);

            dayLbl.setFont(new java.awt.Font("Montserrat Alternates Light", 1, 14));
            dayLbl.setForeground(new java.awt.Color(27, 105, 120));
            dayLbl.setText("Day");
            backgroundImagePanel.add(dayLbl);
            dayLbl.setBounds(90, 180, 60, 18);

            refreshBookingButton.setBackground(new java.awt.Color(22, 97, 112));
            refreshBookingButton.setFont(new java.awt.Font("Montserrat Alternates SemiBold", 1, 12));
            refreshBookingButton.setForeground(new java.awt.Color(236, 236, 236));
            refreshBookingButton.setText("Refresh ");
            refreshBookingButton.setBorderPainted(false);
            refreshBookingButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            refreshBookingButton.setFocusable(false);
            refreshBookingButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    refreshBookingButtonActionPerformed(evt);
                }
            });

            backgroundImagePanel.add(refreshBookingButton);
            refreshBookingButton.setBounds(430, 100, 100, 22);

            todayDateLabel.setFont(new java.awt.Font("Montserrat Alternates Medium", 1, 12));
            todayDateLabel.setForeground(new java.awt.Color(26, 90, 101));
            todayDateLabel.setText("Sat, 24, 12, 2022");
            backgroundImagePanel.add(todayDateLabel);
            todayDateLabel.setBounds(260, 102, 150, 16);

            datePickerContainer.setIcon(new javax.swing.ImageIcon(".//Assets//Images//DatePicker.png"));
            backgroundImagePanel.add(datePickerContainer);
            datePickerContainer.setBounds(250, 100, 160, 20);

            bookingsInfoContainer.setEditable(false);
            bookingsInfoContainer.setBackground(new java.awt.Color(39, 64, 68));
            bookingsInfoContainer.setFont(new java.awt.Font("Montserrat Alternates ExLight", 0, 12));
            bookingsInfoContainer.setForeground(new java.awt.Color(236, 236, 236));
            bookingsInfoContainer.setBorder(null);
            bookingsInfoContainer.setFocusable(false);
            bookingsInfoContainer.setSelectionColor(new java.awt.Color(39, 64, 68));
            bookingsInfoContainer.setHorizontalAlignment(10);
            backgroundImagePanel.add(bookingsInfoContainer);
            bookingsInfoContainer.setBounds(250, 130, 280, 130);

            backgroundImageLabel.setBackground(new java.awt.Color(25, 43, 50));
            backgroundImageLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
            backgroundImageLabel.setIcon(new javax.swing.ImageIcon(".//Assets//Images//MainPage.png"));
            backgroundImageLabel.setToolTipText("");
            backgroundImageLabel.setIconTextGap(0);
            backgroundImageLabel.setRequestFocusEnabled(false);
            backgroundImagePanel.add(backgroundImageLabel);
            backgroundImageLabel.setBounds(0, 0, 900, 450);

            menuBar.setBackground(new java.awt.Color(39, 64, 68));
            menuBar.setForeground(new java.awt.Color(236, 236, 236));
            menuBar.setFocusTraversalPolicyProvider(true);

            fileButton.setText("File");

            refreshBookingMenuButton.setText("Refresh booking");
            refreshBookingMenuButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    refreshBookingMenuButton(evt);
                }
            });

            exitButton.setText("Exit");
            exitButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    exitButtonActionPerformed(evt);
                }
            });

            fileButton.add(refreshBookingMenuButton);
            fileButton.add(exitButton);

            menuBar.add(fileButton);


            helpButton.setText("Help");

            aboutButton.setText("About");
            aboutButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    aboutButtonActionPerformed(evt);
                }
            });

            helpButton.add(aboutButton);

            menuBar.add(helpButton);

            setJMenuBar(menuBar);

            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
            getContentPane().setLayout(layout);
            layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(backgroundImagePanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 900, Short.MAX_VALUE));
            layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(backgroundImagePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 448, javax.swing.GroupLayout.PREFERRED_SIZE));

            pack();
        }

        public static void main(String args[]) {
            /* Set the Nimbus look and feel */
            //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
            /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
             * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
             */
            try {
                for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        javax.swing.UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
            } catch (ClassNotFoundException ex) {
                java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (InstantiationException ex) {
                java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (javax.swing.UnsupportedLookAndFeelException ex) {
                java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
            //</editor-fold>
            //</editor-fold>
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    new MainForm().setVisible(true);
                }
            });
        }


        // Variables declaration

        // panel
        private javax.swing.JPanel backgroundImagePanel;

        // Menu *
        private javax.swing.JMenuBar menuBar;
        private javax.swing.JMenu helpButton;
        private javax.swing.JMenu fileButton;
        private javax.swing.JMenuItem aboutButton;
        private javax.swing.JMenuItem exitButton;
        private javax.swing.JMenuItem refreshBookingMenuButton;

        // text field
        private javax.swing.JTextField bookingsInfoContainer;

        // Combo boxes
        private javax.swing.JComboBox<String> yearsComboBox;
        private javax.swing.JComboBox<String> monthsComboBox;
        private javax.swing.JComboBox<String> daysComboBox;

        // Labels
        private javax.swing.JLabel backgroundImageLabel;
        private javax.swing.JLabel datePickerContainer;
        private javax.swing.JLabel dayLbl;
        private javax.swing.JLabel detailsLbl;
        private javax.swing.JLabel todayDateLabel;
        private javax.swing.JLabel yearLbl;
        private javax.swing.JLabel monthsLbl;
        //Buttons
        private javax.swing.JButton roomOne;
        private javax.swing.JButton roomTwo;
        private javax.swing.JButton roomThree;
        private javax.swing.JButton roomFour;
        private javax.swing.JButton roomFive;
        private javax.swing.JButton getBookingBtn;
        private javax.swing.JButton refreshBookingButton;
    }




    /**
     * @author John Muller
     */
package eu.johnmuller.ecaperooms.Views;

import eu.johnmuller.ecaperooms.Classes.Dialog;
import eu.johnmuller.ecaperooms.Classes.FileIO;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.*;

    public class MainForm extends javax.swing.JFrame {
        // create custom LOGO
        private static final ImageIcon LOGO = new ImageIcon(".//Assets//Images//Logo.png");
        private static final String FILEPATH = ".//Assets//Data/bookings.txt";
        private static final DateTimeFormatter DATE_PATTERN = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        private static final String[] MONTHS_LIST = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};

        private static final String[] AVAILABLE_ROOMS_ICON_PATH = {".//Assets//Images//roomOneFree.png", ".//Assets//Images//roomTwoFree.png", ".//Assets//Images//roomThreeFree.png", ".//Assets//Images//roomFourFree.png", ".//Assets//Images//roomFiveFree.png",};
        private static final String[] BUSY_ROOMS_ICON_PATH = {".//Assets//Images//roomOneBusy.png", ".//Assets//Images//roomTwoBusy.png", ".//Assets//Images//roomThreeBusy.png", ".//Assets//Images//roomFourBusy.png", ".//Assets//Images//roomFiveBusy.png",};
        // used to store the bookings info then display it as upcoming bookings
        ArrayList<String> upComingBookings = new ArrayList<>();

        ArrayList<Integer> filteredList = new ArrayList<>();
        ArrayList<Integer> roomNumbers = new ArrayList<>();
        ArrayList<Integer> numberOfPersons = new ArrayList<>();
        ArrayList<String> names = new ArrayList<>();
        ArrayList<String> surnames = new ArrayList<>();
        ArrayList<String> contactNumbers = new ArrayList<>();
        ArrayList<String> extraRequirements = new ArrayList<>();
        ArrayList<LocalDate> dates = new ArrayList<>();


        // these functions are not pure-functions (it modifies data outside it)
        // cause there is no need to make a simple app complicated
        public void loadBookings() {
            // all commented code was used For testing purposes

            // Read data From File
            String dataString = FileIO.readTextFile(this, FILEPATH);
            // Split each line and store it in a list to iterate over it
            String[] dataList = dataString.split("\n");

            // I had an error in fileIO it was returning null, so I commented the below code after I fixed the error
            //System.out.println("'mainForm' Data string: " + dataString);
            //System.out.println("'mainForm' array list: " + Arrays.toString(dataList));
            //System.out.println("'mainForm' list length: " + dataList.length);

            for (String row : dataList) {
                String[] data = row.split(",");
                //System.out.println(data);
                // storing the data into their lists "manually"
                roomNumbers.add(Integer.parseInt(data[0]));
                names.add(data[1]);
                surnames.add(data[2]);
                contactNumbers.add(data[3]);
                // parse the date and fixing its format
                dates.add(LocalDate.parse(data[4], DATE_PATTERN));
                numberOfPersons.add(Integer.parseInt(data[5]));
                // if data.length equals 7
                // that means the booking has extra requirements, so I appended to the list
                if (data.length == 7) extraRequirements.add(data[6]);
                else
                    // else append empty string
                    extraRequirements.add("");
            }

            // making sure that everything was stored correctly
            //System.out.println(Arrays.toString(roomNumbers.toArray()));
            //System.out.println(Arrays.toString(names.toArray()));
            //System.out.println(Arrays.toString(surnames.toArray()));
            //System.out.println(Arrays.toString(contactNumbers.toArray()));
            //System.out.println(Arrays.toString(dates.toArray()));
            //System.out.println(Arrays.toString(numberOfPersons.toArray()));
            //System.out.println(Arrays.toString(extraRequirements.toArray()));
        }

        public void filterBookings(String date) {
            LocalDate selectedDate = LocalDate.parse(date, DATE_PATTERN);
            String bookingInfo;
            upComingBookings.removeAll(upComingBookings);
            filteredList.removeAll(filteredList);

            for (int i = 0; i < dates.size(); i++) {
                if (selectedDate.compareTo(dates.get(i)) == 0) {
                    bookingInfo = String.format("Room Number: %s\n" + "Guest name: %s\n" + "Guest surname: %s\n" + "Guest phone Number: %s\n" + "number of persons: %s\n" + "Date: %s\n" + "Extra requirements: %s\n", roomNumbers.get(i), names.get(i), surnames.get(i), contactNumbers.get(i), numberOfPersons.get(i), dates.get(i), extraRequirements.get(i));
                    filteredList.add(i);
                    upComingBookings.add(bookingInfo);
                    System.out.println(i + ": " + bookingInfo);
                }

                //else System.out.println("failed: " + dates.get(i) + "selected date is: " + selectedDate);
            }
        }

        public void fillDaysComboBox() {
            int year = Integer.parseInt(yearsComboBox.getSelectedItem().toString());
            YearMonth daysInMonth = YearMonth.of(year, Month.of(monthsComboBox.getSelectedIndex() + 1));
            //System.out.println(daysInMonth);
            //System.out.println(daysInMonth.lengthOfMonth());
            int previousIndex = daysComboBox.getSelectedIndex();
            daysComboBox.removeAllItems(); // clear the list
            for (int day = 1; day <= daysInMonth.lengthOfMonth(); day++) {
                //System.out.println(day);
                daysComboBox.addItem(Integer.toString(day));
            }

            // keep the previous day selected in case the user
            // selected the day then month it won't be visually deleted
            if (daysComboBox.getItemCount() >= previousIndex) {
                if (daysComboBox.getItemCount() == previousIndex) daysComboBox.setSelectedIndex(previousIndex - 1);
                else daysComboBox.setSelectedIndex(previousIndex);
            } else {
                daysComboBox.setSelectedIndex(daysComboBox.getItemCount() - 1);
            }
        }

        public void fillDatesComboBox() {
            // apply / fill after two years ,
            // so the year will be dynamically filed according
            // to the year (date) the app is loaded
            yearsComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{Integer.toString(LocalDate.now().getYear()), Integer.toString(LocalDate.now().getYear() + 1), Integer.toString(LocalDate.now().getYear() + 2),}));

            // no need to fill months using code because months does not change
            // but in case I wanted to fill it using code
            // monthsComboBox.setModel(
            //          new javax.swing.DefaultComboBoxModel<>(new String[]{
            //          "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"}));
            // or using  a for loop

            // fill days
            fillDaysComboBox();

            LocalDate now = LocalDate.now();
            //String[] date = now.toString().split("-");
            //System.out.println(date[0]); // years
            //System.out.println(date[1]); // months
            //System.out.println(date[2]);  // days
        }

        public void updateIcons(@NotNull ArrayList<Integer> bookings) {
            ArrayList<JButton> buttons = new ArrayList<>(Arrays.asList(roomOne, roomTwo, roomThree, roomFour, roomFive));

            for (int booking : bookings) {
                System.out.println(roomNumbers.get(booking));
            }
//        if (bookings.size() > 0) {
//            for (int i = 0; i <= buttons.size(); i++) {
//                if (bookings.size() > i) {
//                    if (roomNumbers.contains(bookings.get(i))) {
//                        buttons.get(roomNumbers.get(bookings.get(i))).setIcon(new javax.swing.ImageIcon(BUSY_ROOMS_ICON_PATH[roomNumbers.get(bookings.get(i))]));
//                        System.out.println(roomNumbers.get(i) + ": busy");
//                    } else {
//                        //buttons.get(i).setIcon(new javax.swing.ImageIcon(AVAILABLE_ROOMS_ICON_PATH[i]));
//                        System.out.println(roomNumbers.contains(bookings.get(i)));
//                    }
//
//                }
//            }
            // }
        }

        public void updateLabel(){

        }

        public MainForm() {
            initComponents();
            // apply custom LOGO
            this.setIconImage(LOGO.getImage());
            // fill the combo boxes
            fillDatesComboBox();
            // load bookings
            loadBookings();
            // update UI

        }

        /**
         * This method is called from within the constructor to initialize the form.
         * WARNING: Do NOT modify this code. The content of this method is always
         * regenerated by the Form Editor.
         */
        @SuppressWarnings("unchecked")
        // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
        private void initComponents() {

            backgroundImagePanel = new javax.swing.JPanel();
            roomOne = new javax.swing.JButton();
            roomTwo = new javax.swing.JButton();
            roomThree = new javax.swing.JButton();
            RoomFour = new javax.swing.JButton();
            RoomFive = new javax.swing.JButton();
            monthsComboBox = new javax.swing.JComboBox<>();
            daysComboBox = new javax.swing.JComboBox<>();
            yearsComboBox = new javax.swing.JComboBox<>();
            getBookingBtn = new javax.swing.JButton();
            detailsLbl = new javax.swing.JLabel();
            yearLbl = new javax.swing.JLabel();
            monthsLbl = new javax.swing.JLabel();
            dayLbl = new javax.swing.JLabel();
            refreshButtonMain = new javax.swing.JButton();
            todayDateLabel = new javax.swing.JLabel();
            infoContainer = new javax.swing.JLabel();
            backgroundImagelabel = new javax.swing.JLabel();
            menuBar = new javax.swing.JMenuBar();
            fileButton = new javax.swing.JMenu();
            refreshBookingMenuButton = new javax.swing.JMenuItem();
            exitButton = new javax.swing.JMenuItem();
            HelpButton = new javax.swing.JMenu();
            aboutButton = new javax.swing.JMenuItem();

            setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
            setTitle("Escape Rooms");
            setResizable(false);

            backgroundImagePanel.setLayout(null);

            roomOne.setBackground(new java.awt.Color(38, 65, 70));
            roomOne.setBorderPainted(false);
            roomOne.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            roomOne.setHideActionText(true);
            roomOne.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
            roomOne.setIconTextGap(0);
            roomOne.setPreferredSize(new java.awt.Dimension(70, 70));
            roomOne.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    roomOneActionPerformed(evt);
                }
            });
            backgroundImagePanel.add(roomOne);
            roomOne.setBounds(90, 270, 80, 80);

            roomTwo.setBackground(new java.awt.Color(38, 65, 70));
            roomTwo.setBorderPainted(false);
            roomTwo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            roomTwo.setHideActionText(true);
            roomTwo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
            roomTwo.setIconTextGap(0);
            roomTwo.setPreferredSize(new java.awt.Dimension(70, 70));
            roomTwo.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    roomTwoActionPerformed(evt);
                }
            });
            backgroundImagePanel.add(roomTwo);
            roomTwo.setBounds(180, 270, 80, 80);

            roomThree.setBackground(new java.awt.Color(38, 65, 70));
            roomThree.setIcon(new javax.swing.ImageIcon("E:\\OneDrive - Malta College of Arts, Science & Technology\\MCAST\\MCAST MQF4\\Year 2\\Lv4-Y2-Sem1\\ITSFT-406-2005  Programming Concepts\\Assignments\\Assignment 2\\EcapeRooms_MullerJohn4_2A\\Assets\\Images\\roomThreeFree.png")); // NOI18N
            roomThree.setBorderPainted(false);
            roomThree.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            roomThree.setHideActionText(true);
            roomThree.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
            roomThree.setIconTextGap(0);
            roomThree.setPreferredSize(new java.awt.Dimension(70, 70));
            roomThree.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    roomThreeActionPerformed(evt);
                }
            });
            backgroundImagePanel.add(roomThree);
            roomThree.setBounds(270, 270, 80, 80);

            RoomFour.setBackground(new java.awt.Color(38, 65, 70));
            RoomFour.setBorderPainted(false);
            RoomFour.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            RoomFour.setHideActionText(true);
            RoomFour.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
            RoomFour.setIconTextGap(0);
            RoomFour.setPreferredSize(new java.awt.Dimension(70, 70));
            RoomFour.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    RoomFourActionPerformed(evt);
                }
            });
            backgroundImagePanel.add(RoomFour);
            RoomFour.setBounds(360, 270, 80, 80);

            RoomFive.setBackground(new java.awt.Color(38, 65, 70));
            RoomFive.setBorderPainted(false);
            RoomFive.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            RoomFive.setHideActionText(true);
            RoomFive.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
            RoomFive.setIconTextGap(0);
            RoomFive.setPreferredSize(new java.awt.Dimension(70, 70));
            RoomFive.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    RoomFiveActionPerformed(evt);
                }
            });
            backgroundImagePanel.add(RoomFive);
            RoomFive.setBounds(450, 270, 80, 80);

            monthsComboBox.setBackground(new java.awt.Color(39, 64, 68));
            monthsComboBox.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
            monthsComboBox.setForeground(new java.awt.Color(225, 225, 225));
            monthsComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" }));
            monthsComboBox.setBorder(null);
            monthsComboBox.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            monthsComboBox.setFocusable(false);
            monthsComboBox.setLightWeightPopupEnabled(false);
            monthsComboBox.setRequestFocusEnabled(false);
            monthsComboBox.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    monthsComboBoxActionPerformed(evt);
                }
            });
            backgroundImagePanel.add(monthsComboBox);
            monthsComboBox.setBounds(90, 150, 120, 20);

            daysComboBox.setBackground(new java.awt.Color(39, 64, 68));
            daysComboBox.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
            daysComboBox.setForeground(new java.awt.Color(225, 225, 225));
            daysComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" }));
            daysComboBox.setBorder(null);
            daysComboBox.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            daysComboBox.setFocusable(false);
            daysComboBox.setLightWeightPopupEnabled(false);
            daysComboBox.setRequestFocusEnabled(false);
            daysComboBox.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    daysComboBoxActionPerformed(evt);
                }
            });
            backgroundImagePanel.add(daysComboBox);
            daysComboBox.setBounds(90, 200, 120, 20);

            yearsComboBox.setBackground(new java.awt.Color(39, 64, 68));
            yearsComboBox.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
            yearsComboBox.setForeground(new java.awt.Color(225, 225, 225));
            yearsComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2022", "2023", "2024" }));
            yearsComboBox.setBorder(null);
            yearsComboBox.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            yearsComboBox.setFocusable(false);
            yearsComboBox.setLightWeightPopupEnabled(false);
            yearsComboBox.setRequestFocusEnabled(false);
            yearsComboBox.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    yearsComboBoxActionPerformed(evt);
                }
            });
            backgroundImagePanel.add(yearsComboBox);
            yearsComboBox.setBounds(90, 100, 120, 20);

            getBookingBtn.setBackground(new java.awt.Color(27, 105, 120));
            getBookingBtn.setFont(new java.awt.Font("Montserrat Alternates SemiBold", 1, 12)); // NOI18N
            getBookingBtn.setForeground(new java.awt.Color(225, 225, 225));
            getBookingBtn.setText("Get Bookings");
            getBookingBtn.setBorderPainted(false);
            getBookingBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            getBookingBtn.setFocusable(false);
            getBookingBtn.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    getBookingBtnActionPerformed(evt);
                }
            });
            backgroundImagePanel.add(getBookingBtn);
            getBookingBtn.setBounds(90, 230, 120, 30);

            detailsLbl.setFont(new java.awt.Font("Montserrat Alternates Light", 1, 14)); // NOI18N
            detailsLbl.setForeground(new java.awt.Color(27, 105, 120));
            detailsLbl.setText("Details");
            backgroundImagePanel.add(detailsLbl);
            detailsLbl.setBounds(230, 80, 60, 18);

            yearLbl.setFont(new java.awt.Font("Montserrat Alternates Light", 1, 14)); // NOI18N
            yearLbl.setForeground(new java.awt.Color(27, 105, 120));
            yearLbl.setText("Year");
            backgroundImagePanel.add(yearLbl);
            yearLbl.setBounds(90, 80, 38, 18);

            monthsLbl.setFont(new java.awt.Font("Montserrat Alternates Light", 1, 14)); // NOI18N
            monthsLbl.setForeground(new java.awt.Color(27, 105, 120));
            monthsLbl.setText("Month");
            backgroundImagePanel.add(monthsLbl);
            monthsLbl.setBounds(90, 130, 60, 18);

            dayLbl.setFont(new java.awt.Font("Montserrat Alternates Light", 1, 14)); // NOI18N
            dayLbl.setForeground(new java.awt.Color(27, 105, 120));
            dayLbl.setText("Day");
            backgroundImagePanel.add(dayLbl);
            dayLbl.setBounds(90, 180, 60, 18);

            refreshButtonMain.setBackground(new java.awt.Color(22, 97, 112));
            refreshButtonMain.setFont(new java.awt.Font("Montserrat Alternates SemiBold", 1, 12)); // NOI18N
            refreshButtonMain.setForeground(new java.awt.Color(236, 236, 236));
            refreshButtonMain.setText("Refresh ");
            refreshButtonMain.setBorderPainted(false);
            refreshButtonMain.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            refreshButtonMain.setFocusable(false);
            refreshButtonMain.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    refreshButtonMainActionPerformed(evt);
                }
            });
            backgroundImagePanel.add(refreshButtonMain);
            refreshButtonMain.setBounds(430, 106, 100, 22);

            todayDateLabel.setFont(new java.awt.Font("Montserrat Alternates Medium", 1, 12)); // NOI18N
            todayDateLabel.setForeground(new java.awt.Color(26, 90, 101));
            todayDateLabel.setText("Sat, 24, 12, 2022");
            backgroundImagePanel.add(todayDateLabel);
            todayDateLabel.setBounds(230, 110, 100, 16);



            backgroundImagelabel.setBackground(new java.awt.Color(25, 43, 50));
            backgroundImagelabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
            backgroundImagelabel.setIcon(new javax.swing.ImageIcon("E:\\OneDrive - Malta College of Arts, Science & Technology\\MCAST\\MCAST MQF4\\Year 2\\Lv4-Y2-Sem1\\ITSFT-406-2005  Programming Concepts\\Assignments\\Assignment 2\\EcapeRooms_MullerJohn4_2A\\Assets\\Images\\MainPage.png")); // NOI18N
            backgroundImagelabel.setToolTipText("");
            backgroundImagelabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);
            backgroundImagelabel.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
            backgroundImagelabel.setIconTextGap(0);
            backgroundImagelabel.setRequestFocusEnabled(false);
            backgroundImagePanel.add(backgroundImagelabel);
            backgroundImagelabel.setBounds(0, 0, 900, 450);

            menuBar.setBackground(new java.awt.Color(39, 64, 68));
            menuBar.setForeground(new java.awt.Color(236, 236, 236));
            menuBar.setFocusTraversalPolicyProvider(true);

            fileButton.setText("File");

            refreshBookingMenuButton.setText("Refresh booking");
            refreshBookingMenuButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    refreshBookingMenuButtonActionPerformed(evt);
                }
            });
            fileButton.add(refreshBookingMenuButton);

            exitButton.setText("Exit");
            exitButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    exitButtonActionPerformed(evt);
                }
            });
            fileButton.add(exitButton);

            menuBar.add(fileButton);

            HelpButton.setText("Help");

            aboutButton.setText("About");
            aboutButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    aboutButtonActionPerformed(evt);
                }
            });
            HelpButton.add(aboutButton);

            menuBar.add(HelpButton);

            setJMenuBar(menuBar);

            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
            getContentPane().setLayout(layout);
            layout.setHorizontalGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(backgroundImagePanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 900, Short.MAX_VALUE)
            );
            layout.setVerticalGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(backgroundImagePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 448, javax.swing.GroupLayout.PREFERRED_SIZE)
            );

            pack();
        }// </editor-fold>//GEN-END:initComponents

        private void yearsComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_yearsComboBoxActionPerformed
            fillDaysComboBox();
            // System.out.println("years Clicked");
        }//GEN-LAST:event_yearsComboBoxActionPerformed

        private void getBookingBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_getBookingBtnActionPerformed
            String day = daysComboBox.getSelectedItem().toString().length() == 1 ? ("0" + daysComboBox.getSelectedItem().toString()) : (daysComboBox.getSelectedItem().toString());
            String date = String.format("%s/%s/%s", day, MONTHS_LIST[monthsComboBox.getSelectedIndex()], yearsComboBox.getSelectedItem());
            filterBookings(date);
            updateIcons(filteredList);
        }//GEN-LAST:event_getBookingBtnActionPerformed

        private void monthsComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_monthsComboBoxActionPerformed
            fillDaysComboBox();
            //System.out.println("months Clicked");
        }//GEN-LAST:event_monthsComboBoxActionPerformed

        private void RoomFiveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RoomFiveActionPerformed
            // TODO add your handling code here:
        }//GEN-LAST:event_RoomFiveActionPerformed

        private void RoomFourActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RoomFourActionPerformed
            // TODO add your handling code here:
        }//GEN-LAST:event_RoomFourActionPerformed

        private void roomThreeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_roomThreeActionPerformed
            // TODO add your handling code here:
        }//GEN-LAST:event_roomThreeActionPerformed

        private void roomTwoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_roomTwoActionPerformed
            // TODO add your handling code here:
        }//GEN-LAST:event_roomTwoActionPerformed

        private void roomOneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_roomOneActionPerformed
            // TODO add your handling code here:
        }//GEN-LAST:event_roomOneActionPerformed

        private void refreshButtonMainActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshButtonMainActionPerformed
            // TODO add your handling code here:
        }//GEN-LAST:event_refreshButtonMainActionPerformed

        private void daysComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_daysComboBoxActionPerformed
            // TODO add your handling code here:
        }//GEN-LAST:event_daysComboBoxActionPerformed

        private void aboutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aboutButtonActionPerformed
            // TODO add your handling code here:
        }//GEN-LAST:event_aboutButtonActionPerformed

        private void refreshBookingMenuButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshBookingMenuButtonActionPerformed
            // TODO add your handling code here:
        }//GEN-LAST:event_refreshBookingMenuButtonActionPerformed

        private void exitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitButtonActionPerformed
            // TODO add your handling code here:
        }//GEN-LAST:event_exitButtonActionPerformed

        /**
         * @param args the command line arguments
         */
        public static void main(String args[]) {
            /* Set the Nimbus look and feel */
            //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
            /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
             * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
             */
            try {
                for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        javax.swing.UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
            } catch (ClassNotFoundException ex) {
                java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (InstantiationException ex) {
                java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (javax.swing.UnsupportedLookAndFeelException ex) {
                java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
            //</editor-fold>
            //</editor-fold>
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    new MainForm().setVisible(true);
                }
            });
        }

        // Variables declaration - do not modify//GEN-BEGIN:variables
        private javax.swing.JMenu HelpButton;
        private javax.swing.JButton RoomFive;
        private javax.swing.JButton RoomFour;
        private javax.swing.JMenuItem aboutButton;
        private javax.swing.JPanel backgroundImagePanel;
        private static javax.swing.JLabel backgroundImagelabel;
        private javax.swing.JLabel dayLbl;
        private javax.swing.JComboBox<String> daysComboBox;
        private javax.swing.JLabel detailsLbl;
        private javax.swing.JMenuItem exitButton;
        private javax.swing.JMenu fileButton;
        private javax.swing.JButton getBookingBtn;
        private javax.swing.JMenuBar menuBar;
        private javax.swing.JComboBox<String> monthsComboBox;
        private javax.swing.JLabel monthsLbl;
        private javax.swing.JMenuItem refreshBookingMenuButton;
        private javax.swing.JButton refreshButtonMain;
        private javax.swing.JButton roomOne;
        private javax.swing.JButton roomThree;
        private javax.swing.JButton roomTwo;
        private javax.swing.JLabel todayDateLabel;
        private javax.swing.JLabel yearLbl;
        private javax.swing.JComboBox<String> yearsComboBox;
        // End of variables declaration//GEN-END:variables
    }