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

	//������������ ���� ����� ���� � �� ���������� � ��������
	private static final int MAX_AGE_PROMOTERID_COOKIE = 60*60*24*365*60;//<-60 ���
	private static final DepartmentService departmentService = new DepartmentService();
	private static final PromoterAndPromoClientSessionService promoterSessionService = new PromoterAndPromoClientSessionService();
	
	/**
	 * @return ������ ���������
	 * @throws BusinessException
	 */
	public List<Department> getPromoterTbList() throws BusinessException
	{
		return departmentService.getPromoterTbList();
	}

	//������ ������� ������� � �������
	public List<PromoChannel> getPromoChannels()
	{
		return Arrays.asList(PromoChannel.VSP,PromoChannel.MVC,PromoChannel.BAW);
	}

	/**
	 * ��������� �������� ����� ���������� �� ����������
	 * @param promoterData
	 * @return
	 * @throws Exception
	 */
	public PromoterSession getPromoterOpenedSession(Map<String, String> promoterData) throws Exception
	{
		return promoterSessionService.getPromoterOpenSessions(promoterData);
	}

	/**
	 * ��������� �������� ����� ���������� �� � ��
	 * @param sessionId - �� �����
	 * @return
	 * @throws Exception
	 */
	public PromoterSession getPromoterOpenedSessionBySessionId(Long sessionId) throws Exception
	{
		return promoterSessionService.getPromoterOpenSessionsById(sessionId);
	}

	/**
	 * �������� �������� ����� ����� ����������
	 * @param promoterOpenSession - �����, ������� ���������� �������
	 * @throws BusinessException
	 */
	public void closePromoterSession(PromoterSession promoterOpenSession) throws BusinessException
	{
		promoterSessionService.closePromoterOpenedSession(promoterOpenSession);
	}

	/**
	 * ���� � �� �������� ����� ���������� �� �� ����� �� Cookie
	 * @param request
	 * @return PromoterSession - �������� ����� ����������
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
	 * ��������� ������������ �� ���� (tb|osb|vsp)
	 * @param code
	 * @return
	 * @throws BusinessException
	 */
	public Department getDepartmentByCode(Code code) throws BusinessException
	{
		return departmentService.findByCode(code);
	}

	/**
	 * ����������/���������� cookie ������ ����������
	 * @param promoterSession ������ ����������
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException
	 */
	public void addOrUpdateAllCookies(PromoterSession promoterSession, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException
	{
		if (promoterSession != null)
		{
			//���������� cookie �������������� ����������, ���������� � ���� ������ ������ ������-�������-���-��ϻ
			//����� ����� ��� �� ����������
			String tbOsbVspCodes = StringHelper.addLeadingZeros(promoterSession.getTb(),2)+
									StringHelper.addLeadingZeros(promoterSession.getOsb(),4)+
									StringHelper.addLeadingZeros(StringHelper.getEmptyIfNull(promoterSession.getOffice()),4);

			//���� �������������� ����������
			addOrUpdateCookie(request, response, MAX_AGE_PROMOTERID_COOKIE, PROMOTERID_COOKIE_NAME, promoterSession.getChannel().getId()+tbOsbVspCodes);
			//���� PROMOTERTEMPID ����������� � "promoRegistration.jsp"
		}
	}

	/**
	 * �������� ������ �� ������ ���������� (������|123456)
	 * @param promoterSession ������ ����������
	 * @param request
	 * @return ������ ��� ������������ ���� �� ������ ����������
	 */
	public String getTempCookieData(PromoterSession promoterSession, HttpServletRequest request) throws UnsupportedEncodingException
	{
		String promoterTempId = promoterSession.getPromoter() + "|" + promoterSession.getSessionId();
		//����� ����� ���� ��������� � ����� ������ ������� �����
		String encodeCookieValue = URLEncoder.encode(promoterTempId, CODING);
		//����� ����� ���� ������������ �� ������� � js (promoRegistration.jsp)
		String tempCookieDataValue = PROMOTERTEMPID_COOKIE_NAME + "=" + encodeCookieValue +
				"; path=/" +
				"; domain=" + getTopLevelDomainString();
		return tempCookieDataValue;
	}

	/**
	 * ������/��������� cookie
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
			//����� ����� ���� ��������� � ����� ������ ������� �����
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
			//���� AS WebSphere, ����� ���������� ������� ������ cookie � ����� ��������� �� � response,
			//����� ��������� �� ����������
			response.addCookie(cookie);
			return cookie;
		}
		return null;
	}

	/**
	 * ���������� ����� ��� ��� ������ ���������� �� �������, �������� ".sberbank.ru"
	 * @return
	 */
	private String getTopLevelDomainString()
	{
		CSAFrontConfig frontConfig = ConfigFactory.getConfig(CSAFrontConfig.class);
		return frontConfig.getPromoterDomainName();
	}


	/**
	 * �������� cookie �� �� ��������. ��� �������� ������������ ������������� ���� �������� ����
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
	 * ���� cookie �� � �������� �� �������� �������
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
	 * ������ �� ����� ���������� �� Cookies
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
	 * ���� ������ � ������������ ��� ���������� � �� ������� �� ����� ����������
	 * @param dataMap
	 * @return Map<String, String> - ������ �����
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
			//��� ������ B@W ��� �� �����
			String vspCode = channelId.equals(PromoChannel.BAW.name()) ? null : (String) dataMap.get(VSP_FIELD_NAME);
			result.put(VSP_FIELD_NAME, vspCode);

			//������������� ���������� ������ � ������� ��������
			String promoterId = (String) dataMap.get(PROMOID_FIELD_NAME);
			result.put(PROMOID_FIELD_NAME, promoterId.toUpperCase());
		}
		return result;
	}

	/**
	 * �������� ����� ����������
	 * @param promoterData
	 * @return PromoterSession - ����� �������� �����
	 */
	public PromoterSession addPromoSession(Map<String, String> promoterData) throws Exception
	{
		//�������� ����� �����
		return CollectionUtils.isNotEmpty(promoterData.entrySet()) ? createPromoterSession(promoterData) : null;
	}

	/**
	 * �������� ����� ����� ����������
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

