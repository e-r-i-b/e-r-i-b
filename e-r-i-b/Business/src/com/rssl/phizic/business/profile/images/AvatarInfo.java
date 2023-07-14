package com.rssl.phizic.business.profile.images;

/**
 * Информация об аватарах.
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
	 * Создание информации об аватаре.
	 */
	public AvatarInfo()
	{
	}

	/**
	 * Создание информации об аватаре из текущей информации об аватаре.
	 * @param type новый тип аватара.
	 * @param info информация об аватаре.
	 */
	public AvatarInfo(AvatarType type, AvatarInfo info)
	{
		this(info);
		setType(type);
	}

	/**
	 * Создание информмации об аватаре из контейнера изображения.
	 * @param container контейнер
	 */
	public AvatarInfo(ImageContainer container)
	{
		setImageInfo(container.getImageInfo());
		setImage(container.getImage());
	}

	/**
	 * @return тип аватара.
	 */
	public AvatarType getType()
	{
		return type;
	}

	/**
	 * @param type тип аватара.
	 */
	public void setType(AvatarType type)
	{
		this.type = type;
	}
}
