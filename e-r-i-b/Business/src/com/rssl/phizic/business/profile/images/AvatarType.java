package com.rssl.phizic.business.profile.images;

/**
 * ���� �������� ��� �������.
 *
 * @author bogdanov
 * @ created 21.04.14
 * @ $Author$
 * @ $Revision$
 */

public enum AvatarType
{
	/**
	 * ��������� ��������.
	 */
	TEMP(-1),
	/**
	 * �������� ��������.
	 */
	SOURCE(-1),
	/**
	 * ������� ������ (������������ �� �������� �������).
	 */
	AVATAR(300),
	/**
	 * ��������� ������.
	 */
	SMALL(80),
	/**
	 * ������ (������������ � ������� ���� ������������).
	 */
	ICON(40);

	private int size;

	/**
	 * @param size ������ �������� � ��������.
	 */
	AvatarType(int size)
	{
		this.size = size;
	}

	/**
	 * @return ������ �������� � ��������.
	 */
	public int getImageSize()
	{
		return size;
	}
}
