package com.rssl.phizic.business.profile.images;

/**
 * Информация о картинке.
 *
 * @author bogdanov
 * @ created 21.04.14
 * @ $Author$
 * @ $Revision$
 */

public class ImageInfo
{
	private Long id;
	private String path;

	/**
	 * @return ид в базе данных.
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * @param id ид в базе данных.
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return путь к изображению.
	 */
	public String getPath()
	{
		return path;
	}

	/**
	 * @param path путь к изображению.
	 */
	public void setPath(String path)
	{
		this.path = path;
	}
}
