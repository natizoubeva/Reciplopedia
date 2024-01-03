package reciplopedia;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class AddIngredientsToRecipeUI extends JFrame {

    private JPanel contentPane;
    private JTextField txtEnterIngredient;
    private JTextField txtHowMuchmany;
    private JLabel lblUnit;
    private JLabel lblEntering;

    private List<String> validIngredients;
    private List<String> suggestionList;
    private JPopupMenu suggestionPopup;

    private IngredientEnteredListener ingredientEnteredListener;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
	EventQueue.invokeLater(new Runnable() {
	    public void run() {
		try {
		    AddIngredientsToRecipeUI frame = new AddIngredientsToRecipeUI();
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
    public AddIngredientsToRecipeUI() {
	setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	setBounds(600, 300, 700, 210);
	contentPane = new JPanel();
	contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

	setContentPane(contentPane);
	contentPane.setLayout(null);

	validIngredients = new ArrayList<>();
	ReaderWriter.loadValidIngredients(validIngredients);
	suggestionList = new ArrayList<>();

	txtEnterIngredient = new JTextField();
	txtEnterIngredient.setFont(new Font("Tahoma", Font.ITALIC, 18));
	txtEnterIngredient.setText("Enter ingredient");
	txtEnterIngredient.setBounds(49, 37, 300, 40);
	contentPane.add(txtEnterIngredient);
	txtEnterIngredient.setColumns(10);
	txtEnterIngredient.addKeyListener(new KeyAdapter() {
	    @Override
	    public void keyPressed(KeyEvent e) {
		if (txtEnterIngredient.getText().equals("Enter ingredient")) {
		    txtEnterIngredient.setText("");
		}
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
		    saveIngredient();
		    txtEnterIngredient.setText("");
		    txtHowMuchmany.setText("");
		    txtEnterIngredient.requestFocus();
		}
	    }

	    public void keyReleased(KeyEvent e) {
		suggestIngredients(txtEnterIngredient.getText());

		if (Character.isLetter(e.getKeyChar())) {
		    suggestionPopup.requestFocus();
		}

		String ingredient = txtEnterIngredient.getText().toLowerCase();
		String unit = ReaderWriter.getValidIngredientUnit(ingredient);
		lblUnit.setText(unit);
		if (suggestionList.contains(ingredient)) {
		    suggestionPopup.setVisible(false);
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
	txtHowMuchmany.setBounds(359, 37, 180, 40);
	contentPane.add(txtHowMuchmany);
	txtHowMuchmany.setColumns(10);
	txtHowMuchmany.addKeyListener(new KeyAdapter() {
	    @Override
	    public void keyPressed(KeyEvent e) {
		if (txtHowMuchmany.getText().equals("How much/many?")) {
		    txtHowMuchmany.setText("");
		}
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
		    saveIngredient();
		    txtEnterIngredient.setText("");
		    txtHowMuchmany.setText("");
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
	lblUnit.setBackground(new Color(255, 255, 255));
	lblUnit.setFont(new Font("Tahoma", Font.PLAIN, 18));
	lblUnit.setBounds(549, 37, 70, 40);
	contentPane.add(lblUnit);

	lblEntering = new JLabel("Hit ENTER to add ingredient to the list");
	lblEntering.setFont(new Font("Tahoma", Font.PLAIN, 14));
	lblEntering.setBounds(221, 99, 245, 40);
	contentPane.add(lblEntering);

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

    // makes suggestions when entering an ingredient
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

    // registers the listener for when adding
    public void registerIngredientEnteredListener(IngredientEnteredListener listener) {
	this.ingredientEnteredListener = listener;
    }

    // saves the entered ingedient
    public void saveIngredient() {
	String ingredientName = txtEnterIngredient.getText();
	double ingredientQuantity = Double.parseDouble(txtHowMuchmany.getText());
	Unit ingredientUnit = Unit.valueOf(lblUnit.getText());

	if (!ingredientName.isEmpty() && ingredientQuantity != 0 && ingredientEnteredListener != null) {
	    ingredientEnteredListener.onIngredientEntered(ingredientName, ingredientQuantity, ingredientUnit);
	}
    }

}
