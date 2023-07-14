package com.rssl.phizic.web.webApi.protocol.jaxb.constructors.utils;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.image.Image;
import com.rssl.phizic.business.profile.Utils;
import com.rssl.phizic.business.profile.images.AvatarType;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.profile.ProfileConfig;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.util.ImageUtil;
import com.rssl.phizic.web.util.SkinHelper;

import java.util.Locale;

/**
 * @author Balovtsev
 * @since 08.05.2014
 */
public class LinkUtils
{
	public static final String PHIZIC_WEB_ROOT      = "/" + Application.PhizIC;
	public static final String PHIZIC_RESOURCE_ROOT = "/PhizIC-res";

	/**
	 * Создать ссылку
	 *
	 * @param url url без указания web root
	 * @param args если данный аргумент указан, то используется для форматирования url
	 * @param locale локаль
	 * @return ссылка
	 */
	public static final String createRedirectUrl(String url, Locale locale, Object... args)
	{
		StringBuilder builder = new StringBuilder();
		builder.append(PHIZIC_WEB_ROOT);

		if (args == null || args.length == 0)
		{
			return builder.append(url).toString();
		}

		if (locale == null)
		{
			return String.format(builder.append(url).toString(), args);
		}

		return String.format(locale, builder.append(url).toString(), args);
	}

	/**
	 *
	 * Создать ссылку на изображение
	 *
	 * @param mockURL картинка по-умолчанию
	 * @param image картинка
	 * @return Ссылка на картинку
	 * @throws BusinessException
	 */
	public static final String createPictureUrl(final String mockURL, final Image image) throws BusinessException
	{
		if (image == null)
			return SkinHelper.getGlobalSkinUrl() + mockURL;
		String result = ImageUtil.getAddressImage(image, PHIZIC_WEB_ROOT);
		if (StringHelper.isEmpty(result))
			return SkinHelper.getGlobalSkinUrl() + mockURL;
		return result;
	}

	/**
	 * Метод получения юзерпиков в WebApi
	 *
	 * @param type тип картинки
	 * @return url картинки
	 */
	public static final String createUserImageUri(AvatarType type) throws ConfigurationException
	{
		String path = Utils.getAvatarPath(type.name(), null);
		if (StringHelper.isEmpty(path))
		{
			return PHIZIC_RESOURCE_ROOT + "/commonSkin/images/profile/" + type.name() + ".png";
		}

		String localUrl = ConfigFactory.getConfig(ProfileConfig.class).getLocalImageServletUri();
		if (!StringHelper.isEmpty(type.name()))
		{
			localUrl += "/" + type.name();
		}
		localUrl += path;

		return localUrl;
	}
}
