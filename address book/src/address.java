import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


    /**
     * This is an assignment on Design and develop a project for a simple Address-Book lookup application.
     * The contact address book contains a 'database' of names, addresses and other details
     * - quite small in this version, but in principle it could be quite large.
     * At any one time the details of just one of the contacts will be on display.

     * Buttons are provided to allow the user:
     *  o  to search for a contact by exact name match


     * Of course, in this exercise, the 'database' is a little unrealistic:
     * the information is built-in to the program (whereas in a 'serious' system it would,
     * perhaps, be read in from a file).

     */

    @SuppressWarnings("serial")
    public class address extends JFrame implements ActionListener
    {
        /** Configuration: custom screen colours, layout constants and custom fonts. */
        private final Color
                backGroundColour = Color.white,
                textColour = Color.black;

        private static final int
                windowWidth = 450, windowHeight = 600,               // Overall frame dimensions
                windowLocationX = 200, windowLocationY = 100;        //     and position
        private final int
                panelWidth = 450, panelHeight = 350,                 // The drawing panel dimensions
                leftMargin = 50,                                     // All text and images start here
                mainHeadingY = 30,                                   // Main heading this far down the panel
                detailsY = mainHeadingY+60,                          // Details display starts this far down the panel
                detailsLineSep = 40;                                 // Separation of details text lines
        private final Font
                mainHeadingFont = new Font("Poppins", Font.BOLD, 25),
                detailsFont = new Font("Poppins", Font.BOLD, 20);

        /** The navigation buttons. */

        private JButton
                findContact = new JButton("Search Name"); // To find contact by exact match of name


        /** Text fields for data entry for finding a contact */
        private JTextField
                nameField = new JTextField(20);               // For entering a new name, or a name to find


        /** The contact details drawing panel. */
        @SuppressWarnings("serial")
        private JPanel contactDetails = new JPanel()
        {
            // paintComponent is called automatically when a screen refresh is needed
            public void paintComponent(Graphics g)
            {
                // g is a cleared panel area
                super.paintComponent(g); // Paint the panel's background
                paintScreen(g);          // Then the required graphics
            }
        };

        /**
         *  The main program launcher for the AddressBook class.
         *
         * @param  args  The command line arguments (ignored here).
         */
        public static void main(String[] args)
        {
            address contacts = new address();
            contacts.setSize(windowWidth, windowHeight);
            contacts.setLocation(windowLocationX, windowLocationY);
            contacts.setTitle("My address book");
            contacts.setUpAddressBook();
            contacts.setUpGUI();
            contacts.setVisible(true);
        } // End of main

        /** Organizes overall set up of the address book data at launch time. */
        private void setUpAddressBook()
        {
            // Set up the contacts' details in the database
            currentSize = 0;    // No contacts initially

            addContact("Dave","Smith","123 main st.","seattle","wa","43");
            addContact("Alice","Smith","123 Main St.","Seattle","WA","45");
            addContact("Jhon","Williams","234 2nd Ave.","Tacoma","WA","26");
            addContact("Carol","Johnson","234 2nd Ave","Seattle","WA","67");
            addContact("Tom","Bombadillo","1212 Maple Street","Florida","GA","520");
            addContact("Jimbo","Jones","82 Pine Street","Atlanta","GA","2");
            addContact("Jackie","Jones","82 Pine Street","Atlanta","GA","6");
            addContact("Tommy","Jones","82 Pine Street","Atlanta","GA","29");
            addContact("tammy","Jones","82 Pine Street","Atlanta","GA","27");
            addContact("EvE","Smith","234 2nd Ave.","Tacoma","WA","25");
            addContact("Frank","Jones","234 2nd Ave.","Tacoma","FL","23");
            addContact("george","Brown","345 3rd Blvd., Apt. 200","Seattle","WA","19");
            addContact("Helen","Brown","345 3rd Blvd. Apt. 200","Seattle","WA","18");
            addContact("Ian","smith","123 main st ","Seattle","Wa","18");
            addContact("Jane","Smith","123 Main St.","Seattle","WA","13");
            // currentSize should now be 5

            // Initially selected contact - the first in the database
            currentContact = 0;
        } // End of setUpAddressBook

        /** Sets up the graphical user interface.
         *
         * Some extra embedded JPanels are used to improve layout a little
         */
        private void setUpGUI()
        {
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            Container window = getContentPane();
            window.setLayout(new FlowLayout());

            // Set up the details graphics panel
            contactDetails.setPreferredSize(new Dimension(panelWidth, panelHeight));
            contactDetails.setBackground(backGroundColour);
            window.add(contactDetails);

            // Set up action buttons
            JPanel addDelPanel = new JPanel();
            window.add(addDelPanel);
            JPanel findPanel = new JPanel();
            findPanel.add(findContact);
            findContact.addActionListener(this);

            // (using extra JPanels to improve layout control)
            JPanel namePanel = new JPanel();
            namePanel.add(new JLabel("Name:"));
            namePanel.add(nameField);
            window.add(namePanel);

            window.add(findPanel);
            JPanel sortPanel = new JPanel();
            window.add(sortPanel);

        } // End of setUpGUI

        /**
         *  Display non-background colour areas, heading and currently selected database contact.
         *
         * @param  g  The Graphics area to be drawn on, already cleared.
         */
        private void paintScreen(Graphics g)
        {
            // Main heading
            g.setColor(textColour);
            g.setFont(mainHeadingFont);
            g.drawString("Contact details", leftMargin, mainHeadingY);

            // Current details
            displayCurrentDetails(g);
        } // End of paintScreen

        /**
         *  Display the currently selected contact.
         *
         * @param  g  The Graphics area to be drawn on.
         */
        private void displayCurrentDetails(Graphics g)
        {
            g.setColor(textColour);
            g.setFont(detailsFont);
            if (currentContact == -1)           // Check if no contact is selected, that is there are no contacts
                g.drawString("There are no contacts", leftMargin, detailsY);
            else
            {   // Display selected contact
                g.drawString(name[currentContact], leftMargin, detailsY);
                g.drawString(address[currentContact], leftMargin, detailsY + detailsLineSep);
                g.drawString(city[currentContact], leftMargin, detailsY + detailsLineSep + detailsLineSep);
                g.drawString(port[currentContact], leftMargin, detailsY + detailsLineSep + detailsLineSep+ detailsLineSep);
                g.drawString(code[currentContact], leftMargin, detailsY + detailsLineSep+ detailsLineSep+ detailsLineSep+ detailsLineSep);
            }
        } // End of displayCurrentDetails

        /**
         *  Handle the various button clicks.
         *
         * @param  e  Information about the button click
         */
        public void actionPerformed(ActionEvent e)
        {
            // Find a contact with exact name match
            if (e.getSource() == findContact)
                doFindContact();
            // And refresh the display
            repaint();
        } // End of actionPerformed


        /**
         * Search for the contact whose name is an exact match to the name given in the name text field.
         * The search name must not be empty.
         * If found then the contact becomes selected.
         * If not found then the user is notified, and the selected contact does not change.
         */
        private void doFindContact()
        {
            String searchName = nameField.getText();
            if (searchName.length() == 0)               // Check and exit if the search name is empty
            {
                JOptionPane.showMessageDialog(null, "Name must not be empty");
                return;
            }
            int location = findContact(searchName);     // Location is where found, or -1
            if (location == -1)                         // Check result: not found?
                JOptionPane.showMessageDialog(null, "Name not found");
            else
            {
                currentContact = location;              // Select the found contact
                nameField.setText("");                  // And clear the search field
            }
        } // End of doFindContact

        /**
         * Search for the contact whose name contains the text given in the name text field,
         * case insensitively.
         *
         * The search text must not be empty.
         * If found then the contact becomes selected.
         * If not found then the user is notified, and the selected contact does not change.
         */
        private int addContact(String newName, String newLastname,String newAddress, String newPort, String newCode, String newCity)
        {
            // TO BE DONE: Need to check if there is space available, and return -1 if not


            name[currentSize] = newName;         // Add data at first free element in each array
            lastname[currentSize] = newLastname;
            address[currentSize] = newAddress;
            city[currentSize] = newCity;
            port[currentSize] = newPort;
            code[currentSize] = newCode;

            currentSize++;                       // Count one more contact
            return currentSize-1;                // Success, return where added

        }
        // End of addContact


        //////////////////////////////////////////////////////////////////////////////////////////////

        /** Maximum capacity of the database. */
        private final int databaseSize = 100;

        /** To hold contacts' names, addresses, etc. */
        private String[]
                name = new String[databaseSize],
                lastname = new String[databaseSize],
                address = new String[databaseSize],
                city = new String[databaseSize],
                port = new String[databaseSize],
                code = new String[databaseSize];

        /** The current number of entries - always a value in range 0 .. databaseSize.
         *
         * The entries are held in elements 0 .. currentSize-1 of the arrays.
         */
        private int currentSize = 0;

        /** To hold index of currently selected contact
         *
         * There is always one selected contact, unless there are no entries at all in the database.
         * If there are one or more entries, then currentContact has a value in range 0 .. currentSize-1.
         * If there are no entries, then currentContact is -1.
         */
        private int currentContact = -1;

        /**
         * Search the database for an exact match for the given name.
         *
         * Return the index of the match found, or -1 if no match found.
         */
        private int findContact(String searchName)
        {
            // TO BE DONE: Implement this method body - see comments above
            for (int i = 0; i < currentSize; i++)
            {
                if (name[i].equals(searchName))
                    return i;
            }

            return -1;                          // Return where found or -1
        } // End of findContact

        /**
         * Search the database for a contact whose name contains the given search text, case insensitively.
         *
         * Return the index of the match found, or -1 if no match found.
         */
    } // End of AddressBook

