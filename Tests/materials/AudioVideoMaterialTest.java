package materials;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AudioVideoMaterialTest {
	/**
	 * standard test on "equals"
	 */
	@Test
	void equals() {
		AudioVideoMaterial a = new AudioVideoMaterial("HJHLKJHK", "treuiyeryiuey, dufyhiuy", 123, 45, true);
		AudioVideoMaterial b = new AudioVideoMaterial("HJHLKJHK", "treuiyeryiuey, dufyhiuy", 124, 45, true);

		assertTrue(a.equals(b));
		assertTrue(b.equals(a));

		a.setTitle("hgj");
		assertFalse(a.equals(b));
		assertFalse(b.equals(a));
		assertFalse(a.equals(a));

		System.out.println("1 test is passed\nFunction 'equals' in the Audio/Video material class tested successfully!");
	}


}