package com.rssl.phizic.business.profile.images;

/**
 * ���������� � ��������.
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
	 * @return �� � ���� ������.
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * @param id �� � ���� ������.
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return ���� � �����������.
	 */
	public String getPath()
	{
		return path;
	}

	/**
	 * @param path ���� � �����������.
	 */
	public void setPath(String path)
	{
		this.path = path;
	}
}
