package materials;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JournalArticleTest {
    @Test
    void equals() {
        JournalArticle a = new JournalArticle("SomeTittle", "author1, author2, author 3", 123456, 300 ,true);
        JournalArticle b = new JournalArticle("SomeTittle", "author1, author2, author 3", 122456, 300, true);

        a.setJournalTitle("DerLazerDrucer");
        b.setJournalTitle("DerLazerDrucer");

        assertTrue(a.equals(b));
        assertTrue(b.equals(a));
        assertFalse(a.equals(a));
        assertFalse(b.equals(b));
    }

    @Test
    void equals001() {
        JournalArticle a = new JournalArticle("SomeTittle", "author1, author2, author 3", 123456, 300 ,true);
        JournalArticle b = new JournalArticle("SomeTittle", "author1, author2, author 3", 123456, 300, true);

        a.setJournalTitle("DerLazerDrucer");
        b.setJournalTitle("Drukarka");

        assertFalse(a.equals(b));
        assertFalse(b.equals(a));

    }

    @Test
    void equals002(){
        JournalArticle a = new JournalArticle("SomeTittle", "author1, author2, author 3", 123456, 300 ,true);
        JournalArticle b = new JournalArticle("SomeTittle", "author1, author2, author 3", 122456, 300, true);

        a.setJournalTitle("DerLazerDrucer");
        b.setJournalTitle("DerLazerDrucer");

        a.setPrice(200);

        assertTrue(a.equals(b));
        assertTrue(b.equals(a));
    }

    @Test
    void equals003(){
        JournalArticle a = new JournalArticle("SomeTittle", "author 3, author1, author2", 123456, 300 ,true);
        JournalArticle b = new JournalArticle("SomeTittle", "author1, author2, author 3", 122456, 300, true);

        a.setJournalTitle("DerLazerDrucer");
        b.setJournalTitle("DerLazerDrucer");

        a.setPrice(200);

        assertTrue(a.equals(b));
        assertTrue(b.equals(a));
    }


    /**
     * These tests should show that the result of equals function not depends on the price of documents, 
     * their publisher and date of publishing.
     * But it depends on tile of document, journal, list of authors and id of  document
     */
}