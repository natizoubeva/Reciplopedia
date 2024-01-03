package reciplopedia;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class HomePageUI extends JFrame {

    private JPanel contentPane;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
	EventQueue.invokeLater(new Runnable() {
	    public void run() {
		try {
		    HomePageUI window = new HomePageUI();
		    window.setVisible(true);
		} catch (Exception e) {
		    e.printStackTrace();
		}
	    }
	});
    }

    /**
     * Create the application.
     */
    public HomePageUI() {
	setTitle("Homepage");
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setBounds(500, 200, 900, 600);
	contentPane = new JPanel();
	contentPane.setBackground(new Color(223, 242, 255));
	contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

	setContentPane(contentPane);
	contentPane.setLayout(null);

	JLabel label1 = new JLabel("Welcome to");
	label1.setFont(new Font("Tahoma", Font.PLAIN, 55));
	label1.setBounds(293, 95, 298, 99);
	contentPane.add(label1);

	JLabel label2 = new JLabel("Reciplopedia");
	label2.setFont(new Font("Tahoma", Font.BOLD, 62));
	label2.setBounds(237, 170, 409, 68);
	contentPane.add(label2);

	JButton button1 = new JButton("Add Groceries");
	button1.setFont(new Font("Tahoma", Font.PLAIN, 24));
	button1.setBackground(new Color(255, 234, 254));
	button1.setBounds(150, 295, 250, 70);
	contentPane.add(button1);
	button1.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		EnterIngredientsUI newFrame = new EnterIngredientsUI();
		newFrame.setVisible(true);
	    }
	});

	JButton button2 = new JButton("Let's cook");
	button2.setFont(new Font("Tahoma", Font.PLAIN, 24));
	button2.setBackground(new Color(255, 234, 254));
	button2.setBounds(450, 295, 250, 70);
	contentPane.add(button2);
	button2.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		LetsCookUI newFrame = new LetsCookUI();
		newFrame.setVisible(true);
	    }
	});

	JButton button3 = new JButton("My Ingredients");
	button3.setFont(new Font("Tahoma", Font.PLAIN, 20));
	button3.setBackground(new Color(255, 254, 232));
	button3.setBounds(185, 380, 180, 50);
	contentPane.add(button3);
	button3.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		MyIngredientsUI newFrame = new MyIngredientsUI();
		newFrame.setVisible(true);
	    }
	});

	JButton button4 = new JButton("My Recipes");
	button4.setFont(new Font("Tahoma", Font.PLAIN, 20));
	button4.setBackground(new Color(255, 254, 232));
	button4.setBounds(485, 380, 180, 50);
	contentPane.add(button4);
	button4.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		MyRecipesUI newFrame = new MyRecipesUI();
		newFrame.setVisible(true);
	    }
	});
    }

}
