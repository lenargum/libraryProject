public class AudioVideoMaterial extends Document{
    /**
     * constructor
     * @param Title
     * @param Authors
     * @param IsAllowedForStudents
     * @param NumberOfCopies
     * @param IsReference
     * @param Price
     * @param KeyWords
     */
    public AudioVideoMaterial(String Title, String Authors, boolean IsAllowedForStudents, int NumberOfCopies, boolean IsReference, double Price, String KeyWords){
        super(Title, Authors, IsAllowedForStudents, NumberOfCopies, IsReference, Price, KeyWords);
        setType("audio/video material");
    }
}
