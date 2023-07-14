package com.rssl.phizic.business.promoters;

import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.StringHelper;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.Cookie;

/**
 * Хелпер для работы с данными сессии промоутера
 *
 * @ author: Gololobov
 * @ created: 01.10.13
 * @ $Author$
 * @ $Revision$
 */
public class PromoterSessionHelper
{
	private static final String CODING = "Windows-1251";
	public static final String TB_FIELD_NAME = "tb";
	public static final String OSB_FIELD_NAME = "osb";
	public static final String VSP_FIELD_NAME = "vsp";
	private static final String CHANNEL_FIELD_NAME = "channelId";
	private static final String SEPARATOR = "\\|";
	public static final String PROMOID_FIELD_NAME = "promoId";
	public static final String PROMOSESSIONID_FIELD_NAME = "sessionId";
	public static final String PROMOTERID_COOKIE_NAME = "PROMOTERID";
	public static final String PROMOTERTEMPID_COOKIE_NAME = "PROMOTERTEMPID";

	private static final Log log = PhizICLogFactory.getLog(LogModule.Core);

	/**
	 * Данные по смене промоутера (Канал-Тербанк-ОСБ-ВСП) из ИД промоутера
	 * @param promoterId - ИД промоутера как лежит в куках
	 * @return Map<String, Object> - мапа с данными (Key-название, Value-значение)
	 * @throws UnsupportedEncodingException
	 */
	public static Map<String, Object> getPromoterSessionInfoByPromoterID(String promoterId) throws UnsupportedEncodingException
	{
		Map<String, Object> promoterDataMap = new HashMap<String, Object>();

		//Канал-Тербанк-ОСБ-ВСП
		if (StringHelper.isNotEmpty(promoterId))
		{
			String channelId = null;
			String tbCode = null;
			String osbCode = null;
			String vspDataFromCookie = null;
			String promoterDataFromCookie = null;
			try
			{
				String decodeCookieValue = URLDecoder.decode(promoterId, CODING);
				//Канал
				channelId = decodeCookieValue.substring(0,1);
				if (StringHelper.isNotEmpty(channelId))
					promoterDataMap.put(CHANNEL_FIELD_NAME, PromoChannel.values()[Integer.valueOf(channelId)-1]);
				//ТБ
				tbCode = StringHelper.removeLeadingZeros(decodeCookieValue.substring(1,3));
				promoterDataMap.put(TB_FIELD_NAME, tbCode);
				//ОСБ
				osbCode = StringHelper.removeLeadingZeros(decodeCookieValue.substring(3,7));
				promoterDataMap.put(OSB_FIELD_NAME, osbCode);
				//ВСП
				vspDataFromCookie = StringHelper.removeLeadingZeros(decodeCookieValue.substring(7,11));
				String vspCode = vspDataFromCookie.equals("0") ? null : vspDataFromCookie;
				promoterDataMap.put(VSP_FIELD_NAME, vspCode);
				//ИД промоутера, указанный при открытии смены
				promoterDataFromCookie = decodeCookieValue.substring(11);
				promoterDataMap.put(PROMOID_FIELD_NAME, promoterDataFromCookie);
			}
			catch (Exception e)
			{
				log.error(String.format("Некорректна информация о сессии промоутера ChannelId=%s, Tb=%s, Osb=%s, Vsp=%s",
						channelId, tbCode, osbCode, vspDataFromCookie),e);
			}
		}

		return promoterDataMap;
	}

	/**
	 * Данные по смене промоутера (ИД указанный промоутером - ИД сессии промоутера)
	 * @param promoterTempId - что-то вроди "ПЕТРОВ|123"
	 * @return Map<String, Object> - мапа с данными (Key-название, Value-значение)
	 * @throws UnsupportedEncodingException
	 */
	public static Map<String, Object> getPromoterSessionInfoByPromoterTempId(String promoterTempId) throws UnsupportedEncodingException
	{
		Map<String, Object> promoterDataMap = new HashMap<String, Object>();

		if (StringHelper.isNotEmpty(promoterTempId))
		{
			String decodeCookieValue = URLDecoder.decode(promoterTempId, CODING);
			String[] promoId = decodeCookieValue.split(SEPARATOR);
			promoterDataMap.put(PROMOID_FIELD_NAME, promoId[0]);
			promoterDataMap.put(PROMOSESSIONID_FIELD_NAME, promoId[1]);
		}

		return promoterDataMap;
	}

	/**
	 * Получение мапы с данными по смене промоутера из Cookie
	 * @param cookies - куки
	 * @return Map<String, Object> - мапа с данными (Key-название, Value-значение)
	 * @throws UnsupportedEncodingException
	 */
	public static Map<String, Object> getPromoterDataFromCookie(Cookie[] cookies) throws UnsupportedEncodingException
	{
		Map<String, Object> promoterDataMap = new HashMap<String, Object>();
		if (cookies != null)
		{
			for (Cookie cookie : cookies)
			{
				if (cookie.getName().equalsIgnoreCase(PROMOTERID_COOKIE_NAME))
				{
					//Добавляем само значение куки
					String decodeCookieValue = URLDecoder.decode(cookie.getValue(), CODING);
					promoterDataMap.put(PROMOTERID_COOKIE_NAME,decodeCookieValue);
					//Парсим значение куки. Получаем "Канал-Тербанк-ОСБ-ВСП"
					promoterDataMap.putAll(getPromoterSessionInfoByPromoterID(cookie.getValue()));

					//Если инфу по идентификатору промоутера уже добавили, то всё что хотели нашли и выходим
					if (promoterDataMap.get(PROMOTERTEMPID_COOKIE_NAME) != null)
						break;
				}
				//Идентификатор промоутера
				if (cookie.getName().equalsIgnoreCase(PROMOTERTEMPID_COOKIE_NAME))
				{
					//Добавляем само значение куки
					String decodeCookieValue = URLDecoder.decode(cookie.getValue(), CODING);
					promoterDataMap.put(PROMOTERTEMPID_COOKIE_NAME,decodeCookieValue);
					//Парсим значение куки. Получаем "ИД промоутера-ИД сессии промоутера"
					promoterDataMap.putAll(getPromoterSessionInfoByPromoterTempId(cookie.getValue()));

					//Если инфу по ТБ, ОСБ, ВСП уже добавили, то всё что хотели нашли и выходим
					if (promoterDataMap.get(PROMOTERID_COOKIE_NAME) != null)
						break;
				}
			}
		}

		return promoterDataMap;
	}
}
