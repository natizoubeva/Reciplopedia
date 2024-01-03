package reciplopedia;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.SystemColor;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class RecipeListLayout extends JPanel {

    private static JLabel lblImage;
    private static JLabel lblName;
    private static JLabel lblCookTime;

    /**
     * Create the panel.
     */
    public RecipeListLayout() {
	setBackground(Color.LIGHT_GRAY);
	setLayout(null);

	lblImage = new JLabel("");
	lblImage.setHorizontalAlignment(SwingConstants.CENTER);
	lblImage.setBounds(10, 10, 156, 130);
	add(lblImage);

	lblName = new JLabel("");
	lblName.setFont(new Font("Tahoma", Font.PLAIN, 20));
	lblName.setBounds(180, 10, 530, 40);
	add(lblName);

	lblCookTime = new JLabel("Cooking Time: ");
	lblCookTime.setBackground(SystemColor.textHighlight);
	lblCookTime.setFont(new Font("Tahoma", Font.PLAIN, 14));
	lblCookTime.setBounds(180, 45, 240, 25);
	add(lblCookTime);

    }

    public JLabel getLblImage() {
	return lblImage;
    }

    public JLabel getLblName() {
	return lblName;
    }

    public JLabel getLblCookTime() {
	return lblCookTime;
    }

    public static void setRecipeListLayout(Recipe recipe) {
	ImageIcon imageIcon = recipe.getImage();
	Image image = imageIcon.getImage().getScaledInstance(lblImage.getWidth(), lblImage.getHeight(),
		Image.SCALE_SMOOTH);
	imageIcon.setImage(image);
	lblImage.setIcon(imageIcon);

	lblName.setText(recipe.getName());

	lblCookTime.setText(lblCookTime.getText() + " " + recipe.getCookingTime() + " minutes");

    }

}
