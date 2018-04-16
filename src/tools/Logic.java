package tools;

public class Logic {
    /**
     * Checks whether patron can take the following book.
     *
     * @param DocumentID   documents.Book ID to check.
     * @param database tools.Database that stores the information.
     * @return {@code true} if this patron can get the book, otherwise {@code false}.
     */
    public static boolean canRequestDocument(int DocumentID, int patronId, Database database) {
        return false;
    }

    public static boolean canRenewDocument(int documentId, int patronId, Database database){
        return false;
    }

    public static boolean canReturnDocument(int documentId, int patronId, Database database){
        return false;
    }

    public static boolean canFine(int documentId, int patronId, Database database){
        return false;
    }
}
