package reciplopedia;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class CookRecipeUI extends JFrame {

    private static JPanel contentPane;
    private static JLabel lblName;
    private static JLabel lblCookTime;
    private static JLabel lblDishType;
    private static JTextArea lblHowToCook;
    private static JLabel lblMinutes;
    private static JLabel lblImage;
    private static ImageIcon imageIcon;
    private static JTextArea lblIngredients;
    private static JButton btnCook;
    private static JButton btnClose;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
	EventQueue.invokeLater(new Runnable() {
	    public void run() {
		try {
		    CookRecipeUI frame = new CookRecipeUI();
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
    public CookRecipeUI() {
	setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	setBounds(200, 200, 900, 600);
	contentPane = new JPanel();
	contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

	setContentPane(contentPane);
	contentPane.setLayout(null);

	lblName = new JLabel();
	lblName.setFont(new Font("Tahoma", Font.PLAIN, 24));
	lblName.setBounds(50, 45, 455, 40);
	contentPane.add(lblName);

	lblImage = new JLabel();
	lblImage.setHorizontalAlignment(SwingConstants.CENTER);
	lblImage.setBounds(570, 45, 240, 200);
	lblImage.setBorder(BorderFactory.createLineBorder(Color.BLACK));
	contentPane.add(lblImage);

	lblCookTime = new JLabel();
	lblCookTime.setFont(new Font("Tahoma", Font.PLAIN, 14));
	lblCookTime.setHorizontalAlignment(SwingConstants.CENTER);
	lblCookTime.setBounds(680, 255, 33, 30);
	contentPane.add(lblCookTime);
	lblMinutes = new JLabel("min");
	lblMinutes.setFont(new Font("Tahoma", Font.PLAIN, 15));
	lblMinutes.setHorizontalAlignment(SwingConstants.LEADING);
	lblMinutes.setVerticalAlignment(SwingConstants.CENTER);
	lblMinutes.setBounds(720, 255, 25, 30);
	contentPane.add(lblMinutes);

	lblDishType = new JLabel();
	lblDishType.setFont(new Font("Tahoma", Font.PLAIN, 17));
	lblDishType.setBounds(50, 90, 200, 30);
	contentPane.add(lblDishType);

	lblHowToCook = new JTextArea();
	lblHowToCook.setEditable(false);
	lblHowToCook.setBackground(getBackground());
	lblHowToCook.setFont(new Font("Tahoma", Font.PLAIN, 18));
	lblHowToCook.setLineWrap(true);
	lblHowToCook.setWrapStyleWord(true);
	JScrollPane howToCookScrollPane = new JScrollPane(lblHowToCook);
	howToCookScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	howToCookScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	howToCookScrollPane.setBounds(50, 305, 770, 160);
	contentPane.add(howToCookScrollPane);

	lblIngredients = new JTextArea();
	lblIngredients.setEditable(false);
	lblIngredients.setFont(new Font("Tahoma", Font.PLAIN, 18));
	lblIngredients.setBounds(50, 135, 300, 160);
	lblIngredients.setBackground(getBackground());
	lblIngredients.setLineWrap(true);
	lblIngredients.setWrapStyleWord(true);
	contentPane.add(lblIngredients);
	JScrollPane ingredientsScrollPane = new JScrollPane(lblIngredients);
	ingredientsScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	ingredientsScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	ingredientsScrollPane.setBounds(50, 135, 300, 160);
	contentPane.add(ingredientsScrollPane);

	btnCook = new JButton("Cooked It :)");
	btnCook.setFont(new Font("Tahoma", Font.PLAIN, 20));
	btnCook.setBounds(200, 485, 230, 45);
	contentPane.add(btnCook);
	btnCook.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		cookAndRemove();
		System.exit(0);
	    }
	});

	btnClose = new JButton("Maybe another Time");
	btnClose.setFont(new Font("Tahoma", Font.PLAIN, 20));
	btnClose.setBounds(444, 485, 230, 45);
	contentPane.add(btnClose);
	btnClose.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		dispose();
	    }
	});
    }

    // sets recipe in the layout
    public static void setRecipe(Recipe recipe) {
	lblName.setText(recipe.getName());
	lblDishType.setText(recipe.getType().toString());
	imageIcon = recipe.getImage();
	Image image = imageIcon.getImage().getScaledInstance(lblImage.getWidth(), lblImage.getHeight(),
		Image.SCALE_SMOOTH);
	imageIcon.setImage(image);
	lblImage.setIcon(imageIcon);
	lblCookTime.setText(recipe.getCookingTime() + "");
	lblIngredients.setText(ReaderWriter.writeIngredients(recipe.getIngridients()));
	lblIngredients.setSelectionStart(0);
	lblIngredients.setSelectionEnd(0);
	lblHowToCook.setText(recipe.getDescription());
	lblHowToCook.setSelectionStart(0);
	lblHowToCook.setSelectionEnd(0);
    }

    // removes the used ingredients from he file my_ingredients.txt
    public void cookAndRemove() {
	Map<String, Ingredient> recipeIngredients = new HashMap<>();
	ReaderWriter.getIngredientsFromStringToMap(lblIngredients.getText(), recipeIngredients);
	Map<String, Ingredient> fileIngredients = ReaderWriter.loadMyIngredients();
	for (Map.Entry<String, Ingredient> entry : recipeIngredients.entrySet()) {
	    Ingredient ingredient = entry.getValue();
	    Ingredient fileIngredient = fileIngredients.get(entry.getKey());
	    fileIngredient.setQuantity(fileIngredient.getQuantity() - ingredient.getQuantity());
	    if (fileIngredient.getQuantity() != 0) {
		fileIngredients.put(entry.getKey(), fileIngredient);
	    } else {
		fileIngredients.remove(entry.getKey());
	    }
	}
	ReaderWriter.writeIngredientsInFile(fileIngredients);
    }

    // using the layout without the buttons for display
    public static void displayRecipe() {
	contentPane.remove(btnClose);
	contentPane.remove(btnCook);
    }
}
