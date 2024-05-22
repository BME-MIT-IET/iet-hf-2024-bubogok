package temalab.logger;

public class Label {
	public enum Color {
		BLACK(30, 40),
		RED(31, 41),
		GREEN(32, 42),
		YELLOW(33, 43),
		BLUE(34, 44),
		MAGENTA(35, 45),
		CYAN(36, 46),
		WHITE(37, 47),
		DEFAULT(39, 49),
		NONE(0, 0);

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
			(foregroundColor.equals(Label.Color.NONE) ? "" : foregroundColor.foreground)
			+ (backgroundColor.equals(Label.Color.NONE) ? "" : backgroundColor.background);
	}
}
