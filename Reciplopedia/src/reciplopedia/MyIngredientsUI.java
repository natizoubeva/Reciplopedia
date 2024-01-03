package reciplopedia;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class MyIngredientsUI extends JFrame {

    private JPanel contentPane;
    private JTextArea textArea;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
	EventQueue.invokeLater(new Runnable() {
	    public void run() {
		try {
		    MyIngredientsUI frame = new MyIngredientsUI();
		    frame.setVisible(true);
		} catch (Exception e) {
		    e.printStackTrace();
		}
	    }
	});
    }

    /**
     * Create the frame.
     */
    public MyIngredientsUI() {
	setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	setBounds(100, 100, 900, 600);
	contentPane = new JPanel();
	contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

	setContentPane(contentPane);
	contentPane.setLayout(null);

	textArea = new JTextArea();
	textArea.setText(ReaderWriter.displayMyIngredients());
	textArea.setFont(new Font("Tahoma", Font.PLAIN, 16));
	JScrollPane scrollArea = new JScrollPane(textArea);
	scrollArea.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	scrollArea.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	scrollArea.setBounds(35, 56, 650, 450);
	contentPane.add(scrollArea);

	JButton btnSave = new JButton("Save");
	btnSave.setFont(new Font("Tahoma", Font.PLAIN, 24));
	btnSave.setBounds(720, 466, 140, 40);
	contentPane.add(btnSave);
	btnSave.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		saveIngredientsInFile();
		dispose();
	    }
	});

    }

    // saves the ingredients in the file
    public void saveIngredientsInFile() {
	Map<String, Ingredient> ingredients = new HashMap<>();
	Map<String, Ingredient> fileIngredients = new HashMap<>();
	ReaderWriter.getIngredientsFromStringToMap(textArea.getText(), ingredients);
	for (Map.Entry<String, Ingredient> entry : ingredients.entrySet()) {
	    fileIngredients.put(entry.getKey(), entry.getValue());
	}
	ReaderWriter.writeIngredientsInFile(fileIngredients);
    }
}
