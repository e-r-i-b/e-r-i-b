package com.rssl.phizic.business.profile.images;

/**
 * Типы картинок для аватара.
 *
 * @author bogdanov
 * @ created 21.04.14
 * @ $Author$
 * @ $Revision$
 */

public enum AvatarType
{
	/**
	 * Временная картинка.
	 */
	TEMP(-1),
	/**
	 * Исходная картинка.
	 */
	SOURCE(-1),
	/**
	 * Большой аватар (отображается на странице Профиль).
	 */
	AVATAR(300),
	/**
	 * Маленький аватар.
	 */
	SMALL(80),
	/**
	 * Иконка (отображается в верхнем меню пользователя).
	 */
	ICON(40);

	private int size;

	/**
	 * @param size размер картинки в пикселях.
	 */
	AvatarType(int size)
	{
		this.size = size;
	}

	/**
	 * @return размер картинки в пикселях.
	 */
	public int getImageSize()
	{
		return size;
	}
}
