package reciplopedia;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class EnterIngredientsUI extends JFrame {

    private JPanel contentPane;
    private JTextField txtEnterIngredient;
    private JTextField txtHowMuchmany;
    private JLabel lblUnit;
    private JLabel lblEntering;
    private JTextArea txtYourList;
    private JButton btnSave;

    private List<String> validIngredients;
    private List<String> suggestionList;
    private JPopupMenu suggestionPopup;

    private Map<String, Ingredient> newIngredients;
    private Map<String, Ingredient> fileIngredients;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
	EventQueue.invokeLater(new Runnable() {
	    public void run() {
		try {
		    EnterIngredientsUI frame = new EnterIngredientsUI();
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
    public EnterIngredientsUI() {
	setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	setBounds(100, 100, 900, 600);
	contentPane = new JPanel();
	contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

	setContentPane(contentPane);
	contentPane.setLayout(null);

	validIngredients = new ArrayList<>();
	ReaderWriter.loadValidIngredients(validIngredients);
	suggestionList = new ArrayList<>();

	newIngredients = new HashMap<>();

	txtEnterIngredient = new JTextField();
	txtEnterIngredient.setFont(new Font("Tahoma", Font.ITALIC, 18));
	txtEnterIngredient.setText("Enter ingredient");
	txtEnterIngredient.setBounds(50, 65, 325, 40);
	contentPane.add(txtEnterIngredient);
	txtEnterIngredient.setColumns(10);
	txtEnterIngredient.addKeyListener(new KeyAdapter() {
	    @Override
	    public void keyPressed(KeyEvent e) {
		if (txtEnterIngredient.getText().equals("Enter ingredient")) {
		    txtEnterIngredient.setText("");
		}
	    }

	    public void keyReleased(KeyEvent e) {
		if (Character.isLetter(e.getKeyChar())) {
		    suggestIngredients(txtEnterIngredient.getText());
		    suggestionPopup.requestFocus();
		}

		String ingredient = txtEnterIngredient.getText().toLowerCase();
		String unit = ReaderWriter.getValidIngredientUnit(ingredient);
		lblUnit.setText(unit);
		if (suggestionList.contains(ingredient)) {
		    suggestionPopup.setVisible(false);
		}

		if (e.getKeyCode() == KeyEvent.VK_ENTER && !txtEnterIngredient.getText().isEmpty()
			&& !txtHowMuchmany.getText().isEmpty() && !txtHowMuchmany.getText().equals("How much/many?")) {
		    String quantity = txtHowMuchmany.getText();
		    String entry = "• " + ingredient + " " + quantity + " " + unit;

		    txtYourList.setText(txtYourList.getText() + entry + "\n");

		    txtEnterIngredient.setText("");
		    txtHowMuchmany.setText("");
		    lblUnit.setText("");

		    txtEnterIngredient.requestFocus();
		}

	    }
	});
	txtEnterIngredient.addFocusListener(new FocusAdapter() {
	    @Override
	    public void focusLost(FocusEvent e) {
		if (txtEnterIngredient.getText().isEmpty()) {
		    txtEnterIngredient.setText("Enter ingredient");
		}
	    }

	    public void focusGained(FocusEvent e) {
		txtEnterIngredient.setCaretPosition(0);
	    }
	});

	txtHowMuchmany = new JTextField();
	txtHowMuchmany.setText("How much/many?");
	txtHowMuchmany.setFont(new Font("Tahoma", Font.ITALIC, 18));
	txtHowMuchmany.setBounds(418, 65, 182, 40);
	contentPane.add(txtHowMuchmany);
	txtHowMuchmany.setColumns(10);
	txtHowMuchmany.addKeyListener(new KeyAdapter() {
	    @Override
	    public void keyPressed(KeyEvent e) {
		// clear the field when after start typing
		if (txtHowMuchmany.getText().equals("How much/many?")) {
		    txtHowMuchmany.setText("");
		}
	    }

	    public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER && !txtEnterIngredient.getText().isEmpty()
			&& !txtHowMuchmany.getText().isEmpty()
			&& !txtEnterIngredient.getText().equals("Enter ingredient")) {
		    String ingredient = txtEnterIngredient.getText();
		    String quantity = txtHowMuchmany.getText();
		    String unit = lblUnit.getText();
		    String entry = "* " + ingredient + " " + quantity + " " + unit;

		    txtYourList.setText(txtYourList.getText() + entry + "\n");

		    txtEnterIngredient.setText("");
		    txtHowMuchmany.setText("");
		    lblUnit.setText("");

		    txtEnterIngredient.requestFocus();
		}
	    }
	});
	txtHowMuchmany.addFocusListener(new FocusAdapter() {
	    @Override
	    public void focusLost(FocusEvent e) {
		if (txtHowMuchmany.getText().isEmpty()) {
		    txtHowMuchmany.setText("How much/many?");
		}
	    }

	    public void focusGained(FocusEvent e) {
		txtHowMuchmany.setCaretPosition(0);
	    }
	});

	lblUnit = new JLabel("");
	lblUnit.setFont(new Font("Tahoma", Font.PLAIN, 18));
	lblUnit.setBounds(610, 65, 70, 40);
	contentPane.add(lblUnit);

	lblEntering = new JLabel("Hit ENTER to add ingredient to the list");
	lblEntering.setFont(new Font("Tahoma", Font.PLAIN, 14));
	lblEntering.setBounds(581, 115, 243, 40);
	contentPane.add(lblEntering);

	txtYourList = new JTextArea();
	txtYourList.setFont(new Font("Tahoma", Font.ITALIC, 16));
	txtYourList.setBounds(50, 165, 550, 350);
	contentPane.add(txtYourList);

	btnSave = new JButton("Save");
	btnSave.setFont(new Font("Tahoma", Font.PLAIN, 24));
	btnSave.setBounds(684, 480, 140, 40);
	contentPane.add(btnSave);
	btnSave.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		saveIngredientsInFile();
		dispose();
	    }
	});

	suggestionPopup = new JPopupMenu();
	suggestionPopup.setFocusable(false);
	suggestionPopup.addMouseListener(new MouseAdapter() {
	    @Override
	    public void mouseClicked(MouseEvent e) {
		String suggestion = suggestionPopup.getComponentAt(e.getPoint()).getName();
		txtEnterIngredient.setText(suggestion);
		txtEnterIngredient.requestFocus();
		suggestionPopup.setVisible(false);
	    }
	});
    }

    // suggest when typing an ingredient
    public void suggestIngredients(String str) {
	suggestionList.clear();
	suggestionPopup.removeAll();
	suggestionPopup.revalidate();
	suggestionPopup.repaint();

	if (str.isEmpty()) {
	    suggestionPopup.setVisible(false);
	    return;
	}
	for (String ingredient : validIngredients) {
	    if (ingredient.toLowerCase().contains(str.toLowerCase())) {
		suggestionList.add(ingredient);
	    }
	}
	if (!suggestionList.isEmpty()) {
	    int popupWidth = txtEnterIngredient.getWidth();
	    int popupHeight = suggestionList.size() * 30;
	    suggestionPopup.setPreferredSize(new Dimension(popupWidth, popupHeight));

	    for (String suggestion : suggestionList) {
		JLabel suggestionLabel = new JLabel(suggestion);
		suggestionLabel.setName(suggestion);
		suggestionLabel.setSize(popupWidth, txtEnterIngredient.getHeight());
		suggestionLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		suggestionLabel.setBorder(BorderFactory.createLineBorder(Color.black));
		suggestionLabel.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
			String selectedIngredient = ((JLabel) e.getSource()).getText();
			txtEnterIngredient.setText(selectedIngredient);
			lblUnit.setText(ReaderWriter.getValidIngredientUnit(selectedIngredient));

			suggestionPopup.setVisible(false);
		    }
		});
		suggestionPopup.add(suggestionLabel);
	    }

	    suggestionPopup.show(txtEnterIngredient, 0, txtEnterIngredient.getHeight());
	} else {
	    suggestionPopup.setVisible(false);
	}
    }

    // saves the entered groceries in the file
    public void saveIngredientsInFile() {
	fileIngredients = ReaderWriter.loadMyIngredients();
	ReaderWriter.getIngredientsFromStringToMap(txtYourList.getText(), newIngredients);
	for (Map.Entry<String, Ingredient> entry : newIngredients.entrySet()) {
	    String ingredientName = entry.getKey();
	    Ingredient ingredient = entry.getValue();

	    if (fileIngredients.containsKey(ingredientName)) {
		Ingredient existingIngredient = fileIngredients.get(ingredientName);
		existingIngredient.setQuantity(existingIngredient.getQuantity() + ingredient.getQuantity());
		fileIngredients.put(ingredientName, existingIngredient);
	    } else {
		fileIngredients.put(entry.getKey(), entry.getValue());
	    }
	}
	ReaderWriter.writeIngredientsInFile(fileIngredients);
    }
}
