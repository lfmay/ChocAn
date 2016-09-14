import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.*; 

/**
 * This class is an abstract/interface class for the other menus to pull off of
 * @author Kurt Anderson
 * @version 1.0
 */

public class Menu extends JFrame implements ActionListener{
	
	private JButton mLogoffBttn;
	private JLabel mWelcomeLabel;
	protected Container c = getContentPane();
	private MainMenu mMainMenu;
	
	//Initialize Menu
	/**
	 * Initializes the menu
	 * @param titleString - The string for the title in the dialog
	 * @param Menu - This is the name of the menu
	 * @param mainMenu - The Main Menu object, passes in to keep the dialog tree going. 
	 */
	public void Menu(String titleString, String Menu, MainMenu mainMenu) {
		
		c.setLayout(null);
		setTitle(titleString);
		
		mMainMenu = mainMenu; //Attaches a main Menu to the menu class
		
		mLogoffBttn = new JButton("Log Off");
		mLogoffBttn.addActionListener(this);
		mLogoffBttn.setSize( 150, 30 );
		mLogoffBttn.setLocation( 600, 20);
		c.add(mLogoffBttn);
		
		//Create the Welcome Label
		mWelcomeLabel = new JLabel(Menu);
		mWelcomeLabel.setFont(new Font("Dialog", Font.BOLD, 30)); //Enlarge the font
		mWelcomeLabel.setSize(300,50);
		mWelcomeLabel.setLocation(265, 10);
		c.add(mWelcomeLabel);
		
	}
	/**
	 * This creates the menu, and is called throughout the menus to create the menu to a standardized format
	 */
	public void createMenu() {
		//Creates the Menu
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize(); //Grabs the screen resolution
		setSize(800, 500);
		setLocation( dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2); //Sets the mainMenu to center regardless of screen resoulution
		setVisible(true);
		
		addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                try {
					mMainMenu.mChocAn.saveRecords();
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
                e.getWindow().dispose();
            }
        });
	}
	
	/**
	 * This overrights the actionlistener, making sure that when buttons are clicked they work. 
	 */
	
	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == mLogoffBttn) {
			mMainMenu.setVisible(true);
			this.dispose(); //When logged off the menu is deleted and closed
		}
		
	}
}
