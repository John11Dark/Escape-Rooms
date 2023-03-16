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
import javax.swing.*;


public class MainForm extends javax.swing.JFrame {

    // * Variables

    // create custom LOGO
    final ImageIcon LOGO = new ImageIcon(".//Assets//Images//Logo.png");
    final String FILEPATH = ".//Assets//Data/bookings.txt";
    final DateTimeFormatter DATE_PATTERN = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    final String[] MONTHS_LIST = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};

    byte availableRooms = 5;
    int numberOfGuests = 0;
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

    String[] roomInfo = new String[5];


    // Functions
    // these functions are not pure-functions (it modifies data outside it)
    // cause there is no need to make a simple app complicated

    /**
     * This method loads / read bookings from the database (file)
     * it clears and modify the outer / corresponding lists
     *
     * @return void
     */
    void loadBookings() {
        //clearAll(true);
        roomNumbers.removeAll(roomNumbers);
        numberOfPersons.removeAll(numberOfPersons);
        names.removeAll(names);
        surnames.removeAll(surnames);
        contactNumbers.removeAll(contactNumbers);
        extraRequirements.removeAll(extraRequirements);
        dates.removeAll(dates);
        //Read data From File
        String dataString = FileIO.readTextFile(this, FILEPATH);
        // Split each line and store it in a list to iterate over it
        String[] dataList = dataString.split("\n");
        for (String row : dataList) {
            String[] data = row.split(",");
            // storing the data into their lists "manually"
            roomNumbers.add(Integer.parseInt(data[0].trim()));
            names.add(data[1].trim());
            surnames.add(data[2].trim());
            contactNumbers.add(data[3].trim());
            // parse the date and fixing its format
            // sometimes it gives an error while parsing the date so. I wrap it with
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
            //@if (data.length == 7) extraRequirements.add(data[6].trim());
            //@else
            // else append empty string
            //@extraRequirements.add("");
            // a cleaner way of doing it using ternary operator
            extraRequirements.add(data.length >= 7 ? data[6] : "");
        }
    }

    /**
     * this method applies the current date to combo boxes
     *
     * @return void
     */
    void applyCurrentDateToComboBoxes() {
        String dateString = LocalDate.now().toString();
        String[] date = dateString.split("-");
        yearsComboBox.setSelectedItem(date[0]);
        monthsComboBox.setSelectedIndex(Integer.parseInt(date[1]) - 1);
        daysComboBox.setSelectedIndex(Integer.parseInt(date[2]) - 1);
        //System.out.println(Arrays.toString(date) + dateString);
    }

    /**
     * This method gets the selected date from the combo box
     * and return it as a local date format
     *
     * @return LocalDate
     */
    LocalDate getDate() {
        String day = daysComboBox.getSelectedItem().toString().length() == 1 ? ("0" + daysComboBox.getSelectedItem().toString()) : (daysComboBox.getSelectedItem().toString());
        String date = String.format("%s/%s/%s", day, MONTHS_LIST[monthsComboBox.getSelectedIndex()], yearsComboBox.getSelectedItem());
        return LocalDate.parse(date, DATE_PATTERN);

    }

    /**
     * fill the correct dates on cobo box according to each month and year
     *
     * @return void
     */
    void fillDatesComboBox() {
        // apply / fill after two years ,
        // so the year will be dynamically filed according
        // to the year (date) the app is loaded
        yearsComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{Integer.toString(LocalDate.now().getYear()), Integer.toString(LocalDate.now().getYear() + 1)}));

        // no need to fill months using code because months does not change
        // but in case I wanted to fill it using code
        // monthsComboBox.setModel(
        //          new javax.swing.DefaultComboBoxModel<>(new String[]{
        //          "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"}));
        // or using  a for loop

        // fill days
        fillDaysComboBox();
    }

    /**
     * fill days according to each month
     * and keeps track of the last selected date if in
     * the length of the new month
     *
     * @return void
     */
    void fillDaysComboBox() {
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

    /**
     * make booking shows the selected room and passes
     * the date and room number to that room
     *
     * @param room integer room number
     * @return void
     */
    void makeBooking(int room) {
        // Checking the selected date before showing the makeBooking screen
        // cause the user can not change the date in make booking screen,
        // so it makes more since to get the selected date and on that the programme decides to show the screen or not
        // so if there is a booking on that date will display a dialog box informing them that there is bookings on that selected date
        // otherwise it's available, so they can make a booking
        String data = "You can not make booking on these dates.\n";
        int counter = 0;
        for (int i = 0; i < dates.size(); i++) {
            if (dates.get(i).isEqual(getDate()) && roomNumbers.get(i) == room) {
                data = String.format("%s Room: %s is booked on : %s\n", data, roomNumbers.get(i), dates.get(i));
                counter++;
            }
        }
        if (counter >= 1) {
            Dialog.notify(this, ("Make booking " + room), data);
        } else {

            MakeBooking make = new MakeBooking();
            // pass the room number
            make.roomNumber = room;
            // set selected date

            make.date = getDate().format(DATE_PATTERN);
            make.roomNumberLbl.setText(" " + room);
            // show the form
            make.setVisible(true);
        }

    }

    /**
     * view booking creates new instance of view booking
     * and passes its corresponding data to it
     *
     * @param room integer room number
     * @return void
     */
    void viewBooking(int room) throws Exception {
        ViewBooking view = new ViewBooking();
        // get the room information

        String[] info = getRoomBookingInfo(room);


        // check if there is an extra requirements

        // (extraRequirements) is used to set the text based on the condition
        // it's initial to empty string to reduce the use of else
        //String extraRequirements = " ";

        //
        //if (info.length >= 6) extraRequirements = info[5];
        //view.extraReqLbl.setText(extraRequirements);
        // this is an easier way to do it using the ternary operator
        view.extraReqLbl.setText(info.length >= 6 ? info[5] : " ");
        view.roomNumberLbl.setText(info[0]);
        view.nameLbl.setText(info[1]);
        view.surnameLbl.setText(info[2]);
        view.contactNumLbl.setText(info[3]);
        view.numOfPersonsLbl.setText(info[4]);

        view.setVisible(true);
    }

    /**
     * clears all the previous data
     *
     * @@return void but it modifies global variables
     */
    void clearAll() {
        upComingBookings.removeAll(upComingBookings);
        filteredList.removeAll(filteredList);
        availableRooms = 5;
        numberOfGuests = 0;
        JButton[] buttons = {this.roomOne, this.roomTwo, this.roomThree, this.roomFour, this.roomFive};
        for (JButton button : buttons) {
            button.putClientProperty("isBooked", false);
        }
        // System.out.println("Clear all");
        //System.out.println("after clearing inside " + availableRooms);
        //System.out.println("after clearing inside " + numberOfGuests);
    }


    /**
     * filters the current bookings according to the selectedDate
     *
     * @param selectedDate LocalDate pass the date you need to make filters too
     * @return void but it modifies outside code
     */
    void filterBookings(LocalDate selectedDate) {
        JButton[] buttons = {this.roomOne, this.roomTwo, this.roomThree, this.roomFour, this.roomFive};
        String bookingInfo;
        String data;
        clearAll();
        //System.out.println("\nFilter list");
        //System.out.println("after clearing outside " + availableRooms);
        //System.out.println("after clearing outside " + numberOfGuests);

        for (int i = 0; i < dates.size(); i++) {
            if (selectedDate.compareTo(dates.get(i)) == 0) {
                // decrement the available rooms
                availableRooms--;

                // increment the number of guests
                numberOfGuests += numberOfPersons.get(i);

                //System.out.println(numberOfPersons.get(i) + ": number of guests at index : " + i);
                // System.out.println("index: " + i + " after decrementing inside Filter list for loop" + availableRooms);
                //System.out.println("index: " + i + " after incrementing inside Filter list for loop" + numberOfGuests);

                // This bookingInfo variable is used to display the bookings information
                bookingInfo = String.format("Room Number: %s<br>" + "Guest name: %s<br>" + "Guest surname: %s<br>" + "Guest phone Number: %s<br>" + "number of persons: %s<br>" + "Date: %s<br>" + "Extra requirements: %s<br>", roomNumbers.get(i), names.get(i), surnames.get(i), contactNumbers.get(i), numberOfPersons.get(i), dates.get(i), extraRequirements.get(i));
                // store the booking information
                upComingBookings.add(bookingInfo);

                // This Data variable is to store the room information to be used when viewing the booking
                data = String.format("%s,%s,%s,%s,%s,%s", roomNumbers.get(i), names.get(i), surnames.get(i), contactNumbers.get(i), numberOfPersons.get(i), extraRequirements.get(i));

                // add store the booking index to be able to fetch them later
                filteredList.add(i);
                buttons[roomNumbers.get(i) - 1].putClientProperty("isBooked", true);

                // update the buttons state

                switch (roomNumbers.get(i)) {
                    case 1 -> roomInfo[0] = data;
                    case 2 -> roomInfo[1] = data;
                    case 3 -> roomInfo[2] = data;
                    case 4 -> roomInfo[3] = data;
                    case 5 -> roomInfo[4] = data;

                }
            }
        }
        updateIcons();
        updateLabels(upComingBookings, getDate(), availableRooms, numberOfGuests);
    }

    /**
     * This method returns a single booking information in a String list from the
     * passed room number
     * in case it gets a wrong number it throws an exception error
     *
     * @param room the room number to get its booking information
     * @return String[]
     * @throws Exception when the provided number is out of bounds
     */
    String @NotNull [] getRoomBookingInfo(int room) throws Exception {
        String data = switch (room) {
            case 1 -> roomInfo[0];
            case 2 -> roomInfo[1];
            case 3 -> roomInfo[2];
            case 4 -> roomInfo[3];
            case 5 -> roomInfo[4];
            default -> throw new Exception(room + ": is Invalid room number");
        };
        return data.split(",");
    }

    /**
     * updates bookings icons and click status
     *
     * @return void
     */
    void updateIcons() {
        JButton[] buttons = {this.roomOne, this.roomTwo, this.roomThree, this.roomFour, this.roomFive};
        for (JButton button : buttons) {
            if ((boolean) button.getClientProperty("isBooked")) {
                button.setIcon(new javax.swing.ImageIcon((".//Assets//Images//" + button.getName() + "Busy.png")));

            } else {
                button.setIcon(new javax.swing.ImageIcon(".//Assets//Images//" + button.getName() + "Free.png"));
            }

        }

    }

    /**
     * update labels
     * and shows number of guests and available rooms
     *
     * @param upComingBookingsList ArrayList<String> list of upcoming bookings to loop over
     * @param date                 LocalDate to show the available bookings on the current date
     * @param availableRooms       int shows the number of available rooms
     * @param numberOfGuests       int shows the number of guests
     * @return void
     */
    void updateLabels(@NotNull ArrayList<String> upComingBookingsList, LocalDate date, int availableRooms, int numberOfGuests) {
        if (upComingBookingsList.size() != 0) {
            // will create a thread to loop over all selected bookings
            /*for (String booking : upComingBookingsList) {
                infoContainer.setText("<html>" + booking + "</html>");
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    Dialog.notify(this, "Escape Rooms", "unknown error occurred");
                }
                }
                */
            // using html format to break line
            infoContainer.setText("<html> Available rooms are: " + availableRooms + "<br> number of guests is: " + numberOfGuests + "</html>");


        } else {
            infoContainer.setText("<html> There is no bookings " + (LocalDate.now().equals(date) ? "for today " : ("on <br>this date : " + date)) + "</html>");
        }
    }

    /**
     * initialize the ui components
     *
     * @return void
     */
    private void initUI() {
        // apply custom LOGO
        this.setIconImage(LOGO.getImage());

        // set current date label
        this.todayDateLabel.setText(LocalDate.now().toString());

        // fill the combo boxes
        fillDatesComboBox();

        // this is an extra future I think it makes more since to have the current date selected
        applyCurrentDateToComboBoxes();

        // filter bookings to see if there is a booking for the current date
        filterBookings(getDate());
    }

    /**
     *
     */
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
        //System.out.println("\nRefresh main menu");
        // reload bookings from the file
        loadBookings();
        // filter bookings
        filterBookings(getDate());
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
        //System.out.println("\nGet bookings");
        filterBookings(getDate());
        Dialog.notify(this, "Escape Rooms", "Bookings has been fetched from the database!");
    }

    private void refreshBookingButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // this refresh button refreshes all selected dates to the current date
        // and filter bookings of now()
        // so it will set the date to the current date and filter the list to today's date
        //System.out.println("\nRefresh custom");
        loadBookings();
        applyCurrentDateToComboBoxes();
        filterBookings(getDate());
        Dialog.notify(this, "Escape Rooms", "Bookings has been reloaded\nand fetched from the database!");

    }

    private void roomOneActionPerformed(java.awt.event.ActionEvent evt) {
        if ((boolean) roomOne.getClientProperty("isBooked")) {
            try {
                viewBooking(1);
            } catch (Exception e) {
                Dialog.notify(this, "Escape Rooms", "unknown error occurred");
            }
        } else makeBooking(1);
    }

    private void roomTwoActionPerformed(java.awt.event.ActionEvent evt) {
        if ((boolean) roomTwo.getClientProperty("isBooked")) {
            try {
                viewBooking(2);
            } catch (Exception e) {
                Dialog.notify(this, "Escape Rooms", "unknown error occurred");
            }
        } else makeBooking(2);
    }

    private void roomThreeActionPerformed(java.awt.event.ActionEvent evt) {
        if ((boolean) roomThree.getClientProperty("isBooked")) {
            try {
                viewBooking(3);
            } catch (Exception e) {
                Dialog.notify(this, "Escape Rooms", "unknown error occurred");
            }
        } else makeBooking(3);
    }

    private void RoomFourActionPerformed(java.awt.event.ActionEvent evt) {
        if ((boolean) roomFour.getClientProperty("isBooked")) {
            try {
                viewBooking(4);
            } catch (Exception e) {
                Dialog.notify(this, "Escape Rooms", "unknown error occurred");
            }
        } else makeBooking(4);

    }

    private void RoomFiveActionPerformed(java.awt.event.ActionEvent evt) {
        if ((boolean) roomFive.getClientProperty("isBooked")) {
            try {
                viewBooking(5);
            } catch (Exception e) {
                Dialog.notify(this, "Escape Rooms", "unknown error occurred");

            }
        } else makeBooking(5);

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
        infoContainer = new javax.swing.JLabel();
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
        roomOne.putClientProperty("isBooked", false);
        roomOne.setName("roomOne");
        backgroundImagePanel.add(roomOne);
        roomOne.setBounds(90, 270, 80, 80);
        roomTwo.setIcon(new javax.swing.ImageIcon(".//Assets//Images//roomTwoFree.png"));

        roomTwo.setBackground(new java.awt.Color(38, 65, 70));
        roomTwo.setName("roomTwo");

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
        roomTwo.putClientProperty("isBooked", false);

        backgroundImagePanel.add(roomTwo);
        roomTwo.setBounds(180, 270, 80, 80);
        roomThree.setIcon(new javax.swing.ImageIcon(".//Assets//Images//roomThreeFree.png"));

        roomThree.setBackground(new java.awt.Color(38, 65, 70));
        roomThree.setName("roomThree");

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
        roomThree.putClientProperty("isBooked", false);

        backgroundImagePanel.add(roomThree);
        roomThree.setBounds(270, 270, 80, 80);
        roomFour.setIcon(new javax.swing.ImageIcon(".//Assets//Images//roomFourFree.png"));

        roomFour.setBackground(new java.awt.Color(38, 65, 70));
        roomFour.setName("roomFour");

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
        roomFour.putClientProperty("isBooked", false);

        backgroundImagePanel.add(roomFour);
        roomFour.setBounds(360, 270, 80, 80);
        roomFive.setIcon(new javax.swing.ImageIcon(".//Assets//Images//roomFiveFree.png"));

        roomFive.setBackground(new java.awt.Color(38, 65, 70));
        roomFive.setName("roomFive");

        roomFive.setBorderPainted(false);
        roomFive.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        roomFive.setHideActionText(true);
        roomFive.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        roomFive.setIconTextGap(0);
        roomFive.setPreferredSize(new java.awt.Dimension(70, 70));
        roomFive.putClientProperty("isBooked", false);

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
        detailsLbl.setBounds(230, 80, 60, 18);

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

        todayDateLabel.setFont(new java.awt.Font("Montserrat Alternates Medium", 1, 12)); // NOI18N
        todayDateLabel.setForeground(new java.awt.Color(26, 90, 101));
        todayDateLabel.setText("Sat, 24, 12, 2022");
        backgroundImagePanel.add(todayDateLabel);
        todayDateLabel.setBounds(230, 107, 150, 16);


        infoContainer.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        infoContainer.setForeground(new java.awt.Color(26, 90, 101));
        infoContainer.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        backgroundImagePanel.add(infoContainer);
        infoContainer.setBounds(230, 140, 300, 120);

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

    // Combo boxes
    private javax.swing.JComboBox<String> yearsComboBox;
    private javax.swing.JComboBox<String> monthsComboBox;
    private javax.swing.JComboBox<String> daysComboBox;

    // Labels
    private javax.swing.JLabel backgroundImageLabel;
    private javax.swing.JLabel dayLbl;
    private javax.swing.JLabel detailsLbl;
    private javax.swing.JLabel todayDateLabel;
    private javax.swing.JLabel yearLbl;
    private javax.swing.JLabel monthsLbl;
    private javax.swing.JLabel infoContainer;

    //Buttons
    private javax.swing.JButton roomOne;
    private javax.swing.JButton roomTwo;
    private javax.swing.JButton roomThree;
    private javax.swing.JButton roomFour;
    private javax.swing.JButton roomFive;
    private javax.swing.JButton getBookingBtn;
    private javax.swing.JButton refreshBookingButton;
}