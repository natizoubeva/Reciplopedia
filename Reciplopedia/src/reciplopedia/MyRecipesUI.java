package reciplopedia;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

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
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class MyRecipesUI extends JFrame {

    private JPanel contentPane;
    private JPanel panel;
    private JScrollPane scrollPane;
    private JComboBox<String> sortByComboBox;
    private JButton btnAddRecipe;

    private ArrayList<Recipe> fileRecipes;

    private int spacing = 5;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
	EventQueue.invokeLater(new Runnable() {
	    public void run() {
		try {
		    MyRecipesUI frame = new MyRecipesUI();
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
    public MyRecipesUI() {
	setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	setBounds(100, 100, 900, 600);
	contentPane = new JPanel();
	contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

	setContentPane(contentPane);
	contentPane.setLayout(null);

	panel = new JPanel();
	panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
	panel.setBorder(BorderFactory.createEmptyBorder(0, spacing, 0, spacing));
	scrollPane = new JScrollPane(panel);
	scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	scrollPane.setBounds(83, 80, 720, 360);
	contentPane.add(scrollPane);

	btnAddRecipe = new JButton("+ Add New Recipe");
	btnAddRecipe.setFont(new Font("Tahoma", Font.PLAIN, 20));
	btnAddRecipe.setBounds(343, 475, 200, 40);
	contentPane.add(btnAddRecipe);
	btnAddRecipe.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		EnterRecipeUI newFrame = new EnterRecipeUI();
		newFrame.setVisible(true);
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
		loadRecipes();
	    }
	});

	loadRecipes();
    }

    // loads the all the recipes
    public void loadRecipes() {
	fileRecipes = ReaderWriter.loadFileRecipes();
	displayRecipes(fileRecipes);
    }

    // displays all of the recipes in the panel
    public void displayRecipes(ArrayList<Recipe> recipes) {
	panel.removeAll();
	LetsCookUI.sortByFactor(recipes, sortByComboBox.getSelectedIndex());
	int visiblePanels = recipes.size();
	for (int i = 0; i < visiblePanels; i++) {
	    Recipe recipe = recipes.get(i);
	    RecipeListLayout recipePanel = new RecipeListLayout();
	    recipePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
	    recipePanel.addMouseListener(new MouseAdapter() {
		public void mouseClicked(MouseEvent e) {
		    CookRecipeUI newFrame = new CookRecipeUI();
		    CookRecipeUI.setRecipe(recipe);
		    CookRecipeUI.displayRecipe();
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
