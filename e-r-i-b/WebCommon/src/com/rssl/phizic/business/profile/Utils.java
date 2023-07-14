package com.rssl.phizic.business.profile;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.payments.PaymentsConfig;
import com.rssl.phizic.business.profile.images.AvatarInfo;
import com.rssl.phizic.business.profile.images.AvatarType;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.person.PersonDocument;
import com.rssl.phizic.person.PersonDocumentType;
import com.rssl.phizic.profile.ProfileConfig;
import com.rssl.phizic.utils.StringHelper;

import java.util.HashSet;
import java.util.Set;
import javax.servlet.jsp.PageContext;

/**
 * @author bogdanov
 * @ created 23.04.14
 * @ $Author$
 * @ $Revision$
 */

public class Utils
{
	private static final int APPROX_URL_LENGTH = 200;

	/**
	 * ƒ”Ћ иностранных граждан
	 */
	private static final Set<PersonDocumentType> FOREIGN_DOCUMENTS = new HashSet<PersonDocumentType>();
	static
	{
		FOREIGN_DOCUMENTS.add(PersonDocumentType.FOREIGN_PASSPORT);
		FOREIGN_DOCUMENTS.add(PersonDocumentType.FOREIGN_PASSPORT_LEGAL);
		FOREIGN_DOCUMENTS.add(PersonDocumentType.RESIDENTIAL_PERMIT_RF);
		FOREIGN_DOCUMENTS.add(PersonDocumentType.TEMPORARY_PERMIT);
		FOREIGN_DOCUMENTS.add(PersonDocumentType.OTHER_NOT_RESIDENT);
		FOREIGN_DOCUMENTS.add(PersonDocumentType.IMMIGRANT_REGISTRATION);
		FOREIGN_DOCUMENTS.add(PersonDocumentType.REFUGEE_IDENTITY);
		FOREIGN_DOCUMENTS.add(PersonDocumentType.DISPLACED_PERSON_DOCUMENT);
		FOREIGN_DOCUMENTS.add(PersonDocumentType.MIGRATORY_CARD);
		FOREIGN_DOCUMENTS.add(PersonDocumentType.FOREIGN_PASSPORT_OTHER);
	}

	/**
	 * ‘ормирует html-element аватар пользовател€.
	 *
	 * @param resourcesRealPath путь к ресурсам.
	 * @param path путь до изображени€.
	 * @param selector тип изображени€.
	 * @param temp временна€ картинка.
	 * @return путь к пользовательской картинке.
	 */
	public static String buildUserImage(String resourcesRealPath, String path, String selector, boolean temp, String imgStyle, String imgId, String style)
	{
		String[] userImageUrl = buildUserImageUri(resourcesRealPath, path, selector, temp);
		StringBuilder result = new StringBuilder();
		result.append("<img src=\"").append(userImageUrl[0]).append(" \" onerror=\"if (this.src.lastIndexOf('")
				.append(userImageUrl[1]).append("') < 0) this.src='") .append(userImageUrl[1]).append("'\"");
		if (StringHelper.isNotEmpty(imgStyle))
			result.append(" class=\"").append(imgStyle).append("\"");

		if (StringHelper.isNotEmpty(imgId))
			result.append(" id=\"").append(imgId).append("\"");

		if (StringHelper.isNotEmpty(style))
			result.append(" style=\"").append(style).append("\"");

		result.append("/>");

		return result.toString();
	}

	/**
	 * ¬ыдаем путь к пользовательской картинке.
	 *
	 * @param context контекст старницы.
	 * @param path путь до изображени€.
	 * @param selector тип изображени€.
	 * @param temp временна€ картинка.
	 * @return путь к пользовательской картинке.
	 */
	public static String[] buildUserImageUri(PageContext context, String path, String selector, boolean temp)
	{
		return buildUserImageUri(context.getServletContext().getInitParameter("resourcesRealPath"), path, selector, temp);
	}

	private static String[] buildUserImageUri(String resourcesRealPath, String path, String selector, boolean temp)
	{
		try
		{
			if (StringHelper.isEmpty(path))
			{
				StringBuilder url = new StringBuilder(APPROX_URL_LENGTH);
				url.append(resourcesRealPath).append("/commonSkin/images/profile/").append(selector).append(".png");
				return new String[] {url.toString(), ""};
			}

			StringBuilder staticUrl = new StringBuilder(APPROX_URL_LENGTH);
			if (!temp)
			{
				staticUrl.append(ConfigFactory.getConfig(ProfileConfig.class).getStaticImageUri());
				if (!StringHelper.isEmpty(selector))
					staticUrl.append("/").append(selector);
				staticUrl.append(path);
			}

			StringBuilder localUrl = new StringBuilder(APPROX_URL_LENGTH);
			String localPath = ConfigFactory.getConfig(ProfileConfig.class).getLocalImageServletUri();
			localUrl.append(localPath);
			if (!StringHelper.isEmpty(selector))
				localUrl.append("/").append(selector);
			localUrl.append(path);

			if (temp)
				return new String[] {localUrl.toString() + "?temp=true", ""};

			return new String[] {staticUrl.toString(), localUrl.toString()};
		}
		catch (Exception e)
		{
			PhizICLogFactory.getLog(LogModule.Web).error("ќшибка построени€ пути к изображению", e);
			return new String[] {"", ""};
		}
	}

	/**
	 * ѕолучение пути к аватарке.
	 * @param type - тип аватара.
	 * @return путь к изображению.
	 */
	public static String getAvatarPath(String type)
	{
		return getAvatarPath(type, null);
	}

	/**
	 * ѕолучение пути к аватарке.
	 * @param type - тип аватара.
	 * @param avatarInfo - информаци€ об аватаре.
	 * @return путь к изображению.
	 */
	public static String getAvatarPath(String type, AvatarInfo avatarInfo)
	{
		if (!PersonContext.isAvailable() && avatarInfo == null)
			return "";

		try
		{
			AvatarInfo info = avatarInfo != null ? avatarInfo : PersonContext.getPersonDataProvider().getPersonData().getAvatarInfoByType(AvatarType.valueOf(type));
			if (info == null || info.getImageInfo() == null)
				return "";
			return info.getImageInfo().getPath();
		}
		catch(BusinessException e)
		{
			PhizICLogFactory.getLog(LogModule.Web).error("ќшибка при получении изображени€", e);
			return "";
		}
	}

	/**
	 * ќпредел€ем €вл€етс€ ли документ документом иностранного гражданина
	 * @param doc документ клиента
	 * @return  true - €вл€етс€, false - не €вл€етс€
	 */

	public static boolean isForeignDocument(PersonDocument doc)
	{
		return FOREIGN_DOCUMENTS.contains(doc.getDocumentType());
	}

	/**
	 * @return путь до изображени€ в статических ресурсах
	 */
	public static String getStaticImageUri()
	{
		return ConfigFactory.getConfig(ProfileConfig.class).getStaticImageUri();
	}

	/**
	 * получить номер “Ѕ из CBCode
	 * @param cbCode CBCode
	 * @return номер “Ѕ
	 */
	public static String getCutTBByCBCode(String cbCode)
	{
		return cbCode.substring(0, 2);
	}

	/**
	 * @return подсчет комисси€ дл€ p2p через WAY4.
	 */
	public static boolean isNewP2PCommissionCalculation()
	{
		return ConfigFactory.getConfig(PaymentsConfig.class).isP2pAutoPayCommissionViaWAY4();
	}
}
