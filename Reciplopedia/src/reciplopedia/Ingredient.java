package reciplopedia;

enum Unit {
    PIECES, ML, GRAMS, TSP, TBSP, CLOVES
}

public class Ingredient {

    private String name;
    private double quantity;
    private Unit unit;

    public Ingredient(String name, double quantity, Unit unit) {
	this.name = name;
	this.quantity = quantity;
	this.unit = unit;
    }

    public double getQuantity() {
	return quantity;
    }

    public void setQuantity(double quantity) {
	this.quantity = quantity;
    }

    public String getName() {
	return name;
    }

    public Unit getUnit() {
	return unit;
    }

    @Override
    public String toString() {
	return name + " " + quantity + " " + unit;
    }
}
