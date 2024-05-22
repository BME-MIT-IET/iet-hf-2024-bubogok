package temalab.logger;

public class Label {
	public enum Color {
		Black(30, 40),
		Red(31, 41),
		Green(32, 42),
		Yellow(33, 43),
		Blue(34, 44),
		Magenta(35, 45),
		Cyan(36, 46),
		White(37, 47),
		Default(39, 49),
		None(0, 0);

		final String foreground;
		final String background;

		Color(int foreground, int background) {
			this.foreground = "\u001B[" + foreground + "m";
			this.background = "\u001B[" + background + "m";
		}
	}

	public final String label;
	public final String color;

	public Label(String label, Label.Color foregroundColor, Label.Color backgroundColor) {
		this.label = label;
		this.color = 
			(foregroundColor.equals(Label.Color.None) ? "" : foregroundColor.foreground)
			+ (backgroundColor.equals(Label.Color.None) ? "" : backgroundColor.background);
	}
}
