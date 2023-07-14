package com.rssl.auth.csa.front.operations.promo;

import com.rssl.auth.csa.front.business.promo.PromoterAndPromoClientSessionService;
import com.rssl.auth.csa.front.business.promo.PromoterSession;
import com.rssl.auth.csa.front.operations.OperationBase;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.dictionaries.offices.extended.ExtendedDepartment;
import com.rssl.phizic.config.CSAFrontConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.dictionaries.officies.Code;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.business.promoters.PromoChannel;
import com.rssl.phizic.business.promoters.PromoterSessionHelper;
import org.apache.commons.collections.CollectionUtils;

import java.io.UnsupportedEncodingException;
import java.net.*;
import java.util.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ author: Gololobov
 * @ created: 11.09.2012
 * @ $Author$
 * @ $Revision$
 */
public class PromoSessionOperation extends OperationBase
{
	private static final String TB_FIELD_NAME = "tb";
	private static final String OSB_FIELD_NAME = "osb";
	private static final String VSP_FIELD_NAME = "vsp";
	private static final String PROMOID_FIELD_NAME = "promoId";
	private static final String PROMOSESSIONID_FIELD_NAME = "sessionId";
	private static final String CHANNEL_FIELD_NAME = "channelId";
	private static final String SEPARATOR = "\\|";

	private static final String PROMOTERID_COOKIE_NAME = "PROMOTERID";
	private static final String PROMOTERTEMPID_COOKIE_NAME = "PROMOTERTEMPID";
	private static final String PROMOTER_COOKIE_PATH = "/";
	private static final String CODING = "Windows-1251";

	//Максимальный срок жизни куки с ИД промоутера в секундах
	private static final int MAX_AGE_PROMOTERID_COOKIE = 60*60*24*365*60;//<-60 лет
	private static final DepartmentService departmentService = new DepartmentService();
	private static final PromoterAndPromoClientSessionService promoterSessionService = new PromoterAndPromoClientSessionService();
	
	/**
	 * @return список тербанков
	 * @throws BusinessException
	 */
	public List<Department> getPromoterTbList() throws BusinessException
	{
		return departmentService.getPromoterTbList();
	}

	//Список каналов доступа в систему
	public List<PromoChannel> getPromoChannels()
	{
		return Arrays.asList(PromoChannel.VSP,PromoChannel.MVC,PromoChannel.BAW);
	}

	/**
	 * Получение открытой смены промоутера по параметрам
	 * @param promoterData
	 * @return
	 * @throws Exception
	 */
	public PromoterSession getPromoterOpenedSession(Map<String, String> promoterData) throws Exception
	{
		return promoterSessionService.getPromoterOpenSessions(promoterData);
	}

	/**
	 * Получение открытой смены промоутера по её ИД
	 * @param sessionId - ИД смены
	 * @return
	 * @throws Exception
	 */
	public PromoterSession getPromoterOpenedSessionBySessionId(Long sessionId) throws Exception
	{
		return promoterSessionService.getPromoterOpenSessionsById(sessionId);
	}

	/**
	 * Закрытие открытой ранее смены промоутера
	 * @param promoterOpenSession - смена, которую необходимо закрыть
	 * @throws BusinessException
	 */
	public void closePromoterSession(PromoterSession promoterOpenSession) throws BusinessException
	{
		promoterSessionService.closePromoterOpenedSession(promoterOpenSession);
	}

	/**
	 * Ищет в БД открытую смену промоутера по ИД смены из Cookie
	 * @param request
	 * @return PromoterSession - открытая смена промоутера
	 * @throws Exception
	 */
	public PromoterSession getPromoterOpenedSessionBySessionIdFromCookie(HttpServletRequest request) throws Exception
	{
		Map<String, Object> promoterDataMap = getPromoterDataFromCookie(request);
		if (CollectionUtils.isNotEmpty(promoterDataMap.entrySet()))
		{
			String promoSessionId = (String) promoterDataMap.get(PROMOSESSIONID_FIELD_NAME);
			if (StringHelper.isNotEmpty(promoSessionId))
				return getPromoterOpenedSessionBySessionId(Long.valueOf(promoSessionId));
		}
		return null;
	}

	/**
	 * Получение департамента по коду (tb|osb|vsp)
	 * @param code
	 * @return
	 * @throws BusinessException
	 */
	public Department getDepartmentByCode(Code code) throws BusinessException
	{
		return departmentService.findByCode(code);
	}

	/**
	 * Добавление/обновление cookie данных промоутера
	 * @param promoterSession сессия промоутера
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException
	 */
	public void addOrUpdateAllCookies(PromoterSession promoterSession, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException
	{
		if (promoterSession != null)
		{
			//Добавление cookie идентификатора промоутера, включающая в себя только связку «Канал-Тербанк-ОСБ-ВСП»
			//Время жизни его не ограничено
			String tbOsbVspCodes = StringHelper.addLeadingZeros(promoterSession.getTb(),2)+
									StringHelper.addLeadingZeros(promoterSession.getOsb(),4)+
									StringHelper.addLeadingZeros(StringHelper.getEmptyIfNull(promoterSession.getOffice()),4);

			//Кука идентификатора промоутера
			addOrUpdateCookie(request, response, MAX_AGE_PROMOTERID_COOKIE, PROMOTERID_COOKIE_NAME, promoterSession.getChannel().getId()+tbOsbVspCodes);
			//Кука PROMOTERTEMPID добавляется в "promoRegistration.jsp"
		}
	}

	/**
	 * Получить данные по сессии промоутера (ПЕТРОВ|123456)
	 * @param promoterSession сессия промоутера
	 * @param request
	 * @return данные для формирования куки по сессии промоутера
	 */
	public String getTempCookieData(PromoterSession promoterSession, HttpServletRequest request) throws UnsupportedEncodingException
	{
		String promoterTempId = promoterSession.getPromoter() + "|" + promoterSession.getSessionId();
		//Чтобы можно было сохранять и потом читать русские буквы
		String encodeCookieValue = URLEncoder.encode(promoterTempId, CODING);
		//Время жизни куки определяется на клиенте в js (promoRegistration.jsp)
		String tempCookieDataValue = PROMOTERTEMPID_COOKIE_NAME + "=" + encodeCookieValue +
				"; path=/" +
				"; domain=" + getTopLevelDomainString();
		return tempCookieDataValue;
	}

	/**
	 * Создаёт/обновляет cookie
	 * @param response
	 * @param cookieMaxAge
	 * @param cookieName
	 * @return
	 */
	private Cookie addOrUpdateCookie(HttpServletRequest request, HttpServletResponse response,
	                                 int cookieMaxAge, String cookieName, String cookieValue) throws UnsupportedEncodingException
	{
		if (StringHelper.isNotEmpty(cookieName) && StringHelper.isNotEmpty(cookieValue))
		{
			//Чтобы можно было сохранять и потом читать русские буквы
			String encodeCookieValue = URLEncoder.encode(cookieValue, CODING);
			Cookie cookie = getCookieByNameIgnoreCase(request, cookieName);
			if (cookie == null)
				cookie = new Cookie(cookieName,encodeCookieValue);
			else
				cookie.setValue(encodeCookieValue);
			String partsDomain = getTopLevelDomainString();
			cookie.setDomain(partsDomain);
			cookie.setPath(PROMOTER_COOKIE_PATH);
			cookie.setMaxAge(cookieMaxAge);
			//Если AS WebSphere, нужно обязатеьно сначала менять cookie а потом добавлять ее в response,
			//иначе изменения не сохранятся
			response.addCookie(cookie);
			return cookie;
		}
		return null;
	}

	/**
	 * Возвращает домен для кук сессии промоутера из конфига, например ".sberbank.ru"
	 * @return
	 */
	private String getTopLevelDomainString()
	{
		CSAFrontConfig frontConfig = ConfigFactory.getConfig(CSAFrontConfig.class);
		return frontConfig.getPromoterDomainName();
	}


	/**
	 * Удаление cookie по ее названию. Для удаления выставляется простроченная дата годности куки
	 * @param response
	 */
	public void deletePromoterIdCookie(HttpServletRequest request, HttpServletResponse response)
	{
		Cookie cookie = getCookieByNameIgnoreCase(request, PROMOTERTEMPID_COOKIE_NAME);
		if (cookie != null)
		{
			cookie.setMaxAge(0);
			cookie.setValue("");
			cookie.setPath(PROMOTER_COOKIE_PATH);
			cookie.setDomain(getTopLevelDomainString());
			response.addCookie(cookie);
		}
	}

	/**
	 * Ищет cookie по её названию не учитывая регистр
	 * @param request
	 * @param cookieName
	 * @return
	 */
	private Cookie getCookieByNameIgnoreCase(HttpServletRequest request, String cookieName)
	{
		if (StringHelper.isNotEmpty(cookieName))
		{
			Cookie[] cookies = request.getCookies();
			if (cookies != null)
			{
				for (Cookie cookie : cookies)
					if (cookie.getName().equalsIgnoreCase(cookieName))
						return cookie;
			}
		}
		return null;
	}

	/**
	 * Данные по смене промоутера из Cookies
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public Map<String, Object> getPromoterDataFromCookie(HttpServletRequest request) throws UnsupportedEncodingException, BusinessException
	{
		Cookie[] cookies = request.getCookies();
		return PromoterSessionHelper.getPromoterDataFromCookie(cookies);
	}

	/**
	 * Мапа только с необходимыми для сохранения в БД данными по смене промоутера
	 * @param dataMap
	 * @return Map<String, String> - данные формы
	 */
	public Map<String, String> getPromoterData(Map<String, Object> dataMap)
	{
		Map<String, String> result = new HashMap<String, String>();
		if (CollectionUtils.isNotEmpty(dataMap.entrySet()))
		{
    		String channelId = (String) dataMap.get(CHANNEL_FIELD_NAME);
			result.put(CHANNEL_FIELD_NAME, (String) dataMap.get(CHANNEL_FIELD_NAME));
			result.put(TB_FIELD_NAME, (String) dataMap.get(TB_FIELD_NAME));
			result.put(OSB_FIELD_NAME, (String) dataMap.get(OSB_FIELD_NAME));
			//Для канала B@W ВСП не нужно
			String vspCode = channelId.equals(PromoChannel.BAW.name()) ? null : (String) dataMap.get(VSP_FIELD_NAME);
			result.put(VSP_FIELD_NAME, vspCode);

			//Идентификатор промоутера всегда в верхнем регистре
			String promoterId = (String) dataMap.get(PROMOID_FIELD_NAME);
			result.put(PROMOID_FIELD_NAME, promoterId.toUpperCase());
		}
		return result;
	}

	/**
	 * Открытие смены промоутера
	 * @param promoterData
	 * @return PromoterSession - новая открытая смена
	 */
	public PromoterSession addPromoSession(Map<String, String> promoterData) throws Exception
	{
		//Открытие новой смены
		return CollectionUtils.isNotEmpty(promoterData.entrySet()) ? createPromoterSession(promoterData) : null;
	}

	/**
	 * Открытие новой смены промоутера
	 * @param promoterData
	 * @return
	 */
	private PromoterSession createPromoterSession(Map<String, String> promoterData) throws BusinessException
	{
		if (CollectionUtils.isNotEmpty(promoterData.entrySet()))
		{
			PromoterSession newPromoterSession = new PromoterSession();

			newPromoterSession.setChannel(PromoChannel.valueOf(promoterData.get("channelId")));
			newPromoterSession.setCreationDate(Calendar.getInstance());
			newPromoterSession.setTb(promoterData.get(TB_FIELD_NAME));
			newPromoterSession.setOsb(promoterData.get(OSB_FIELD_NAME));
			newPromoterSession.setOffice(promoterData.get(VSP_FIELD_NAME));
			newPromoterSession.setPromoter(promoterData.get(PROMOID_FIELD_NAME));

			return promoterSessionService.addPromoterSession(newPromoterSession);
		}
		return null;
	}
}

