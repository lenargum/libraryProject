package materials;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BookTest {
	/**
	 * these tests are standard tests on equals function
	 */
	@Test
	void equals() {
		Book a = new Book("gfdghsjkgh", "HJGH", 567, false, 1234);
		Book b = new Book("gfdghsjkgh", "HJGH", 867, false, 1234);
		a.setEditionYear(2003);
		b.setEditionYear(2003);

		a.setPublisher("fff");
		b.setPublisher("fff");

		assertFalse(a.equals(a));
		assertTrue(a.equals(b));
		assertTrue(b.equals(a));
	}

	@Test
	void equals001() {
		Book a = new Book("gfdghsjkgh", "HJGH", 567, false, 1234);
		Book b = new Book("gfdghsjkgh", "HJGH", 867, false, 1234);
		a.setEditionYear(2003);
		b.setEditionYear(2003);

		a.setPublisher("fff");
		b.setPublisher("fff");

		b.setDocID(567);

		assertFalse(a.equals(b));
		assertFalse(b.equals(b));

		b.setDocID(65897);
		b.setEditionYear(2017);

		assertFalse(a.equals(b));
		assertFalse(b.equals(a));

		System.out.println("2 tests are passed\nFunction 'equals' in Book class tested successfully!");

	}

}