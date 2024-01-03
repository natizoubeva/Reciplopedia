package reciplopedia;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class EnterRecipeUI extends JFrame {

    private JPanel contentPane;
    private JTextField txtName;
    private JTextField txtCookTime;
    private JLabel dishTypeLabel;
    private JComboBox<String> dishTypeComboBox;
    private JTextArea txtHowToCook;
    private JButton btnAddNewRecipe;
    private JLabel lblMinutes;
    private JLabel lblImage;
    private ImageIcon imageIcon;
    private JTextArea txtAddIngredients;
    private JButton btnAdd;
    private JLabel lblAdd;
    private AddIngredientsToRecipeUI addIngredientsWindow;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
	EventQueue.invokeLater(new Runnable() {
	    public void run() {
		try {
		    EnterRecipeUI frame = new EnterRecipeUI();
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
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public EnterRecipeUI() {
	setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	setBounds(300, 200, 900, 600);
	contentPane = new JPanel();
	contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

	setContentPane(contentPane);
	contentPane.setLayout(null);

	txtName = new JTextField();
	txtName.setFont(new Font("Tahoma", Font.ITALIC, 18));
	txtName.setText("Enter the recipe's name");
	txtName.setBounds(50, 45, 455, 40);
	contentPane.add(txtName);
	txtName.setColumns(10);
	txtName.addKeyListener(new KeyAdapter() {
	    public void keyPressed(KeyEvent e) {
		// remove the text when start typing
		if (txtName.getText().equals("Enter the recipe's name")) {
		    txtName.setText("");
		}
	    }
	});
	txtName.addFocusListener(new FocusAdapter() {
	    @Override
	    public void focusLost(FocusEvent e) {
		// returns the text if the field is empty
		if (txtName.getText().isEmpty()) {
		    txtName.setText("Enter the recipe's name");
		}
	    }

	    public void focusGained(FocusEvent e) {
		txtName.setCaretPosition(0);
	    }
	});

	lblImage = new JLabel("Click here to upload an image");
	lblImage.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 14));
	lblImage.setHorizontalAlignment(SwingConstants.CENTER);
	lblImage.setBounds(570, 45, 240, 200);
	lblImage.setBorder(BorderFactory.createLineBorder(Color.BLACK));
	contentPane.add(lblImage);
	lblImage.addMouseListener(new MouseAdapter() {
	    @Override
	    public void mouseClicked(MouseEvent e) {
		// allows only images
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
		    public boolean accept(File file) {
			return file.isDirectory() || file.getName().toLowerCase().endsWith(".jpg")
				|| file.getName().toLowerCase().endsWith(".png")
				|| file.getName().toLowerCase().endsWith(".gif");
		    }

		    public String getDescription() {
			return "Image files (*.jpg, *.png, *.gif)";
		    }
		});
		int result = fileChooser.showOpenDialog(null);
		// fits the image in the label
		if (result == JFileChooser.APPROVE_OPTION) {
		    File selectedFile = fileChooser.getSelectedFile();
		    imageIcon = new ImageIcon(selectedFile.getAbsolutePath());
		    Image image = imageIcon.getImage().getScaledInstance(lblImage.getWidth(), lblImage.getHeight(),
			    Image.SCALE_SMOOTH);
		    imageIcon.setImage(image);
		    lblImage.setIcon(imageIcon);
		    lblImage.setText("");
		}
	    }
	});

	txtCookTime = new JTextField();
	txtCookTime.setFont(new Font("Tahoma", Font.ITALIC, 14));
	txtCookTime.setHorizontalAlignment(SwingConstants.CENTER);
	txtCookTime.setText("Cook time?");
	txtCookTime.setBounds(626, 259, 85, 30);
	contentPane.add(txtCookTime);
	txtCookTime.setColumns(10);
	txtCookTime.addKeyListener(new KeyAdapter() {
	    public void keyPressed(KeyEvent e) {
		if (txtCookTime.getText().equals("Cook time?")) {
		    txtCookTime.setText("");
		}
	    }
	});
	txtCookTime.addFocusListener(new FocusAdapter() {
	    @Override
	    public void focusLost(FocusEvent e) {
		if (txtCookTime.getText().isEmpty()) {
		    txtCookTime.setText("Cook time?");
		}
	    }

	    public void focusGained(FocusEvent e) {
		txtCookTime.setCaretPosition(0);
	    }
	});
	lblMinutes = new JLabel("min");
	lblMinutes.setFont(new Font("Tahoma", Font.ITALIC, 14));
	lblMinutes.setHorizontalAlignment(SwingConstants.LEADING);
	lblMinutes.setVerticalAlignment(SwingConstants.CENTER);
	lblMinutes.setBounds(721, 259, 25, 30);
	contentPane.add(lblMinutes);

	dishTypeLabel = new JLabel("Dish Type:");
	dishTypeLabel.setFont(new Font("Tahoma", Font.ITALIC, 14));
	dishTypeLabel.setBounds(50, 95, 200, 30);
	contentPane.add(dishTypeLabel);
	dishTypeComboBox = new JComboBox<>(new DefaultComboBoxModel(reciplopedia.Type.values()));
	dishTypeComboBox.setFont(new Font("Tahoma", Font.PLAIN, 14));
	dishTypeComboBox.setSelectedIndex(-1);
	dishTypeComboBox.setBounds(50, 95, 200, 30);
	contentPane.add(dishTypeComboBox);
	dishTypeComboBox.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		// removes the text after choosing
		boolean hasSelection = dishTypeComboBox.getSelectedIndex() != -1;
		dishTypeLabel.setVisible(!hasSelection);
	    }
	});

	txtHowToCook = new JTextArea();
	txtHowToCook.setFont(new Font("Tahoma", Font.ITALIC, 17));
	txtHowToCook.setText("How to cook it?");
	txtHowToCook.setBounds(50, 305, 770, 160);
	txtHowToCook.setLineWrap(true);
	txtHowToCook.setWrapStyleWord(true);
	contentPane.add(txtHowToCook);
	JScrollPane howToCookScrollPane = new JScrollPane(txtHowToCook);
	howToCookScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	howToCookScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	howToCookScrollPane.setBounds(50, 305, 770, 160);
	contentPane.add(howToCookScrollPane);
	txtHowToCook.addKeyListener(new KeyAdapter() {
	    public void keyPressed(KeyEvent e) {
		if (txtHowToCook.getText().equals("How to cook it?")) {
		    txtHowToCook.setText("");
		}
	    }
	});
	txtHowToCook.addFocusListener(new FocusAdapter() {
	    @Override
	    public void focusLost(FocusEvent e) {
		if (txtHowToCook.getText().isEmpty()) {
		    txtHowToCook.setText("How to cook it?");
		}
	    }

	    public void focusGained(FocusEvent e) {
		txtHowToCook.setCaretPosition(0);
	    }
	});

	txtAddIngredients = new JTextArea();
	txtAddIngredients.setFont(new Font("Tahoma", Font.PLAIN, 17));
	txtAddIngredients.setBounds(50, 135, 250, 160);
	txtAddIngredients.setBackground(getBackground());
	txtAddIngredients.setBorder(BorderFactory.createLineBorder(Color.BLACK));
	contentPane.add(txtAddIngredients);

	btnAdd = new JButton("+");
	btnAdd.setBounds(310, 135, 45, 40);
	contentPane.add(btnAdd);
	createAddIngredientsWindow();
	btnAdd.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		addIngredientsWindow.setVisible(true);
	    }
	});

	lblAdd = new JLabel("Add ingredient");
	lblAdd.setFont(new Font("Tahoma", Font.PLAIN, 15));
	lblAdd.setBounds(365, 135, 110, 40);
	contentPane.add(lblAdd);

	btnAddNewRecipe = new JButton("Add New Recipe");
	btnAddNewRecipe.setFont(new Font("Tahoma", Font.PLAIN, 20));
	btnAddNewRecipe.setBounds(309, 480, 240, 48);
	contentPane.add(btnAddNewRecipe);
	btnAddNewRecipe.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		saveRecipe();
		System.exit(0);
	    }
	});

    }

    // creates the AddIngredientsToRecipeUI window
    private void createAddIngredientsWindow() {
	addIngredientsWindow = new AddIngredientsToRecipeUI();
	addIngredientsWindow.registerIngredientEnteredListener(new IngredientEnteredListener() {
	    @Override
	    public void onIngredientEntered(String name, double quantity, Unit unit) {
		String ingredient = "* " + name + " " + quantity + " " + ReaderWriter.getUnitString(unit);
		txtAddIngredients.setText(txtAddIngredients.getText() + ingredient + "\n");
	    }
	});
    }

    // saves the recipe from the fields
    public void saveRecipe() {
	String name = txtName.getText();
	Map<String, Ingredient> map = new HashMap<String, Ingredient>();
	ReaderWriter.getIngredientsFromStringToMap(txtAddIngredients.getText(), map);
	reciplopedia.Type type = (reciplopedia.Type) dishTypeComboBox.getSelectedItem();
	int cookingTime = Integer.parseInt(txtCookTime.getText());
	String description = txtHowToCook.getText();
	Recipe newRecipe = new Recipe(name, type, cookingTime, imageIcon, map, description);
	addRecipeToFile(newRecipe);
    }

    // adds the recipe to the file
    public void addRecipeToFile(Recipe recipe) {
	try (FileWriter writer = new FileWriter("recipes.txt", true)) {
	    // replaces the imagePath so when the project is send to another person it
	    // doesn't have a problem
	    String recipeText = recipe.toString();
	    String imagePath = recipe.getImage().toString();
	    imagePath = imagePath.substring(imagePath.indexOf("images\\"));
	    String[] recipeLines = recipeText.split("\n");
	    recipeLines[3] = imagePath;
	    recipeText = "";
	    for (int i = 0; i < recipeLines.length; i++) {
		recipeText += recipeLines[i] + "\n";
	    }
	    writer.write("New Recipe" + "\n" + recipeText + "\n");
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }
}