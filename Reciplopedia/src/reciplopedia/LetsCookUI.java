package reciplopedia;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class LetsCookUI extends JFrame {

    private JPanel contentPane;
    private JPanel panel;
    private JScrollPane scrollPane;
    private JComboBox<String> sortByComboBox;

    private ArrayList<Recipe> availableRecipes;
    private ArrayList<Recipe> fileRecipes;

    private int spacing = 5;
    private JLabel lblYouDontLike;
    private JButton btnMyRecipes;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
	EventQueue.invokeLater(new Runnable() {
	    public void run() {
		try {
		    LetsCookUI frame = new LetsCookUI();
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
    public LetsCookUI() {
	setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	setBounds(100, 100, 900, 600);
	contentPane = new JPanel();
	contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

	setContentPane(contentPane);
	contentPane.setLayout(null);

	availableRecipes = new ArrayList<Recipe>();

	panel = new JPanel();
	panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
	panel.setBorder(BorderFactory.createEmptyBorder(spacing, spacing, spacing, spacing));
	scrollPane = new JScrollPane(panel);

	scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	scrollPane.setBounds(83, 80, 720, 360);
	contentPane.add(scrollPane);

	lblYouDontLike = new JLabel("<html><center>You don't like anything?<br>Go to My Recipes page →<center></html>");
	lblYouDontLike.setHorizontalAlignment(SwingConstants.CENTER);
	lblYouDontLike.setFont(new Font("Tahoma", Font.PLAIN, 18));
	lblYouDontLike.setBounds(190, 460, 210, 71);
	contentPane.add(lblYouDontLike);

	btnMyRecipes = new JButton("My Recipes");
	btnMyRecipes.setFont(new Font("Tahoma", Font.PLAIN, 20));
	btnMyRecipes.setBounds(460, 475, 200, 40);
	contentPane.add(btnMyRecipes);
	btnMyRecipes.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		MyRecipesUI newFrame = new MyRecipesUI();
		newFrame.setVisible(true);
		dispose();
	    }
	});

	JLabel lblSortBy = new JLabel("Sort by:");
	lblSortBy.setFont(new Font("Tahoma", Font.ITALIC, 14));
	lblSortBy.setBounds(580, 40, 55, 30);
	contentPane.add(lblSortBy);
	sortByComboBox = new JComboBox<>();
	sortByComboBox.addItem("from A to Z");
	sortByComboBox.addItem("from Z to A");
	sortByComboBox.addItem("Cooking Time");
	sortByComboBox.setFont(new Font("Tahoma", Font.PLAIN, 14));
	sortByComboBox.setSelectedIndex(-1);
	sortByComboBox.setBounds(638, 40, 165, 30);
	contentPane.add(sortByComboBox);
	sortByComboBox.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		displayRecipes(availableRecipes);
	    }
	});

	loadAvailableRecipes();
    }

    // loads the recipes that can be cooked
    public void loadAvailableRecipes() {
	fileRecipes = ReaderWriter.loadFileRecipes();
	for (Recipe recipe : fileRecipes) {
	    Map<String, Ingredient> recipeIngredients = recipe.getIngridients();
	    Map<String, Ingredient> myIngredients = ReaderWriter.loadMyIngredients();
	    boolean canBeCooked = true;
	    for (Map.Entry<String, Ingredient> recipeIngredient : recipeIngredients.entrySet()) {
		if (myIngredients.containsKey(recipeIngredient.getKey())) {
		    Ingredient fileIngredient = myIngredients.get(recipeIngredient.getKey());
		    double quantity = fileIngredient.getQuantity();
		    double neededQuantity = recipeIngredient.getValue().getQuantity();
		    if (quantity < neededQuantity) {
			canBeCooked = false;
			break;
		    }
		} else {
		    canBeCooked = false;
		    break;
		}
	    }
	    if (canBeCooked) {
		availableRecipes.add(recipe);
	    }
	}
	displayRecipes(availableRecipes);
    }

    // displays the available recipes in the panel
    // if there are no available recipes shows a message and a button for adding new
    // groceries
    public void displayRecipes(ArrayList<Recipe> recipes) {
	if (recipes.isEmpty()) {
	    panel.setLayout(new BorderLayout());
	    JLabel noRecipesLabel = new JLabel(
		    "<html><center>You can't cook with<br>the ingredients you have<br>right now :(<center></html>");
	    noRecipesLabel.setHorizontalAlignment(SwingConstants.CENTER);
	    noRecipesLabel.setVerticalAlignment(SwingConstants.CENTER);
	    noRecipesLabel.setFont(new Font("Tahoma", Font.BOLD, 32));
	    panel.add(noRecipesLabel, BorderLayout.CENTER);

	    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
	    JButton button = new JButton("Add Groceries");
	    button.setFont(new Font("Tahoma", Font.PLAIN, 18));
	    buttonPanel.add(button);
	    panel.add(buttonPanel, BorderLayout.SOUTH);
	    button.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    EnterIngredientsUI newFrame = new EnterIngredientsUI();
		    newFrame.setVisible(true);
		    dispose();
		}
	    });
	} else {
	    panel.removeAll();
	    sortByFactor(recipes, sortByComboBox.getSelectedIndex());
	    int visiblePanels = recipes.size();
	    for (int i = 0; i < visiblePanels; i++) {
		Recipe recipe = recipes.get(i);
		RecipeListLayout recipePanel = new RecipeListLayout();
		recipePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		recipePanel.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent e) {
			CookRecipeUI newFrame = new CookRecipeUI();
			CookRecipeUI.setRecipe(recipe);
			newFrame.setVisible(true);
		    }
		});
		RecipeListLayout.setRecipeListLayout(recipe);
		panel.add(recipePanel);
		panel.add(Box.createVerticalStrut(spacing));
	    }
	    int panelHeight = (visiblePanels * (150 + spacing)) + spacing;
	    panel.setPreferredSize(new Dimension(panel.getWidth(), panelHeight));
	    panel.revalidate();
	}
    }

    public static void sortByFactor(ArrayList<Recipe> recipes, int comboBoxIndex) {
	if (comboBoxIndex == 0) { // from A to Z
	    RecipeSorting.mergeSortAtoZ(recipes);
	} else if (comboBoxIndex == 1) { // from Z to A
	    RecipeSorting.mergeSortZtoA(recipes);
	} else if (comboBoxIndex == 2) { // cooking Time
	    RecipeSorting.mergeSortCookTime(recipes);
	}
    }
}
