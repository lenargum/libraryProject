package materials;

import java.util.Date;

/**
 * interface describing class Audio and Video Materials
 */
public interface AudioVideoMaterialInterface {

	/**
	 * returns date when the material was published
	 *
	 * @return
	 */
	public String getPublishingDate();

	/**
	 * sets date when the material was published
	 *
	 * @param publDate
	 */
	public void setPublishingDate(Date publDate);
}