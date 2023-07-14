package com.rssl.phizic.business.profile.images;

/**
 * ���������� �� ��������.
 *
 * @author bogdanov
 * @ created 21.04.14
 * @ $Author$
 * @ $Revision$
 */

public final class AvatarInfo extends ImageContainer
{
	private AvatarType type;

	/**
	 * �������� ���������� �� �������.
	 */
	public AvatarInfo()
	{
	}

	/**
	 * �������� ���������� �� ������� �� ������� ���������� �� �������.
	 * @param type ����� ��� �������.
	 * @param info ���������� �� �������.
	 */
	public AvatarInfo(AvatarType type, AvatarInfo info)
	{
		this(info);
		setType(type);
	}

	/**
	 * �������� ����������� �� ������� �� ���������� �����������.
	 * @param container ���������
	 */
	public AvatarInfo(ImageContainer container)
	{
		setImageInfo(container.getImageInfo());
		setImage(container.getImage());
	}

	/**
	 * @return ��� �������.
	 */
	public AvatarType getType()
	{
		return type;
	}

	/**
	 * @param type ��� �������.
	 */
	public void setType(AvatarType type)
	{
		this.type = type;
	}
}
