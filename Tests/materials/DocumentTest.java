package materials;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


class DocumentTest {

	@org.junit.jupiter.api.Test
	void isWrittenBy() {
		Book a = new Book("ABC", "a, b, c, d", 1234, true, 45);

		assertTrue(a.isWrittenBy("b"));
		assertFalse(a.isWrittenBy("Christie"));

		a.addAuthor("Christie");
		System.out.println("Author Christie added!");

		assertTrue(a.isWrittenBy("Christie"));
		assertFalse(a.isWrittenBy("Hcristie"));
		assertTrue(a.isWrittenBy("cHrIsTiE"));

		System.out.println("function 'isWriittenBy' tested successfully!");

	}


	@org.junit.jupiter.api.Test
	void equals() {

		System.out.println("We cannot check equals function on Document class because it is abstract, but it works on other instances of class");
	}

}