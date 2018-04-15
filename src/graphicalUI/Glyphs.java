package graphicalUI;

import com.jfoenix.svg.SVGGlyph;

/**
 * SVG glyphs used in user interface.
 */
public final class Glyphs {
	public static SVGGlyph BOOK_BLACK() {
		return new SVGGlyph("M18 2H6c-1.1 0-2 .9-2 2v16c0 1.1.9 2 2 2h12c1.1 0 2-.9 " +
				"2-2V4c0-1.1-.9-2-2-2zM6 4h5v8l-2.5-1.5L6 12V4z");
	}

	public static SVGGlyph ACCOUNT_CIRCLE() {
		return new SVGGlyph("M12 2C6.48 2 2 6.48 2 12s4.48 10 10 10 10-4.48 " +
				"10-10S17.52 2 12 2zm0 3c1.66 0 3 1.34 3 3s-1.34 3-3 3-3-1.34-3-3 1.34-3 3-3zm0 14.2c-2.5 " +
				"0-4.71-1.28-6-3.22.03-1.99 4-3.08 6-3.08 1.99 0 5.97 1.09 6 3.08-1.29 1.94-3.5 3.22-6 3.22z");
	}

	public static SVGGlyph ARROW_BACK() {
		return new SVGGlyph("M20 11H7.83l5.59-5.59L12 4l-8 8 8 8 1.41-1.41L7.83 13H20v-2z");
	}

	public static SVGGlyph SEARCH() {
		return new SVGGlyph("M 15.5 14 h -0.79 l -0.28 -0.27 C 15.41 12.59 16 11.11 16 9.5 C 16 " +
				"5.91 13.09 3 9.5 3 S 3 5.91 3 9.5 S 5.91 16 9.5 16 c 1.61 0 3.09 -0.59 4.23 -1.57 l 0.27 0.28 " +
				"v 0.79 l 5 4.99 L 20.49 19 l -4.99 -5 Z m -6 0 C 7.01 14 5 11.99 5 9.5 S 7.01 5 9.5 5 S 14 7.01 " +
				"14 9.5 S 11.99 14 9.5 14 Z");
	}
}
