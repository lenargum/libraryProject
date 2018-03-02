public class AudioVideoMaterial extends Document{
    /**
     * constructor
     * @param ID
     * @param Title
     * @param Authors
     * @param IsAllowedForStudents
     * @param NumberOfCopies
     * @param IsReference
     * @param Price
     * @param KeyWords
     */
    public AudioVideoMaterial(int ID, String Title, String Authors, boolean IsAllowedForStudents, int NumberOfCopies, boolean IsReference, double Price, String KeyWords){
        super(ID, Title, Authors, IsAllowedForStudents, NumberOfCopies, IsReference, Price, KeyWords);
    }
}
