package materials;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JournalArticleTest {
    /**
     * These tests should show that the result of equals function not depends on the price of documents,
     * their publisher and date of publishing.
     * But it depends on tile of document, journal, list of authors and id of  document
     */
    @Test
    void equals() {
        JournalArticle a = new JournalArticle("SomeTittle", "author1, author2, author 3", 123456, 300, true, true);
        JournalArticle b = new JournalArticle("SomeTittle", "author1, author2, author 3", 122456, 300, true, true);

        a.setJournalTitle("DerLazerDrucer");
        b.setJournalTitle("DerLazerDrucer");

        assertTrue(a.equals(b));
        assertTrue(b.equals(a));
        assertFalse(a.equals(a));
        assertFalse(b.equals(b));
    }

    @Test
    void equals001() {
        JournalArticle a = new JournalArticle("SomeTittle", "author1, author2, author 3", 123456, 300, true, true);
        JournalArticle b = new JournalArticle("SomeTittle", "author1, author2, author 3", 123456, 300, true, true);

        a.setJournalTitle("DerLazerDrucer");
        b.setJournalTitle("Drukarka");

        assertFalse(a.equals(b));
        assertFalse(b.equals(a));

    }

    @Test
    void equals002() {
        JournalArticle a = new JournalArticle("SomeTittle", "author1, author2, author 3", 123456, 300, true, true);
        JournalArticle b = new JournalArticle("SomeTittle", "author1, author2, author 3", 122456, 300, true, true);

        a.setJournalTitle("DerLazerDrucer");
        b.setJournalTitle("DerLazerDrucer");

        a.setPrice(200);

        assertTrue(a.equals(b));
        assertTrue(b.equals(a));
    }

    @Test
    void equals003() {
        JournalArticle a = new JournalArticle("SomeTittle", "author 3, author1, author2", 123456, 300, true, true);
        JournalArticle b = new JournalArticle("SomeTittle", "author1, author2, author 3", 122456, 300, true, true);

        a.setJournalTitle("DerLazerDrucer");
        b.setJournalTitle("DerLazerDrucer");

        a.setPrice(200);

        assertTrue(a.equals(b));
        assertTrue(b.equals(a));

        System.out.println("4 tests are passed\nFunction 'equals' in the 'Journal Article' class tested successfully!");
    }
}