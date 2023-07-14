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
 * ������ ��� ������ � ������� ������ ����������
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
	 * ������ �� ����� ���������� (�����-�������-���-���) �� �� ����������
	 * @param promoterId - �� ���������� ��� ����� � �����
	 * @return Map<String, Object> - ���� � ������� (Key-��������, Value-��������)
	 * @throws UnsupportedEncodingException
	 */
	public static Map<String, Object> getPromoterSessionInfoByPromoterID(String promoterId) throws UnsupportedEncodingException
	{
		Map<String, Object> promoterDataMap = new HashMap<String, Object>();

		//�����-�������-���-���
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
				//�����
				channelId = decodeCookieValue.substring(0,1);
				if (StringHelper.isNotEmpty(channelId))
					promoterDataMap.put(CHANNEL_FIELD_NAME, PromoChannel.values()[Integer.valueOf(channelId)-1]);
				//��
				tbCode = StringHelper.removeLeadingZeros(decodeCookieValue.substring(1,3));
				promoterDataMap.put(TB_FIELD_NAME, tbCode);
				//���
				osbCode = StringHelper.removeLeadingZeros(decodeCookieValue.substring(3,7));
				promoterDataMap.put(OSB_FIELD_NAME, osbCode);
				//���
				vspDataFromCookie = StringHelper.removeLeadingZeros(decodeCookieValue.substring(7,11));
				String vspCode = vspDataFromCookie.equals("0") ? null : vspDataFromCookie;
				promoterDataMap.put(VSP_FIELD_NAME, vspCode);
				//�� ����������, ��������� ��� �������� �����
				promoterDataFromCookie = decodeCookieValue.substring(11);
				promoterDataMap.put(PROMOID_FIELD_NAME, promoterDataFromCookie);
			}
			catch (Exception e)
			{
				log.error(String.format("����������� ���������� � ������ ���������� ChannelId=%s, Tb=%s, Osb=%s, Vsp=%s",
						channelId, tbCode, osbCode, vspDataFromCookie),e);
			}
		}

		return promoterDataMap;
	}

	/**
	 * ������ �� ����� ���������� (�� ��������� ����������� - �� ������ ����������)
	 * @param promoterTempId - ���-�� ����� "������|123"
	 * @return Map<String, Object> - ���� � ������� (Key-��������, Value-��������)
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
	 * ��������� ���� � ������� �� ����� ���������� �� Cookie
	 * @param cookies - ����
	 * @return Map<String, Object> - ���� � ������� (Key-��������, Value-��������)
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
					//��������� ���� �������� ����
					String decodeCookieValue = URLDecoder.decode(cookie.getValue(), CODING);
					promoterDataMap.put(PROMOTERID_COOKIE_NAME,decodeCookieValue);
					//������ �������� ����. �������� "�����-�������-���-���"
					promoterDataMap.putAll(getPromoterSessionInfoByPromoterID(cookie.getValue()));

					//���� ���� �� �������������� ���������� ��� ��������, �� �� ��� ������ ����� � �������
					if (promoterDataMap.get(PROMOTERTEMPID_COOKIE_NAME) != null)
						break;
				}
				//������������� ����������
				if (cookie.getName().equalsIgnoreCase(PROMOTERTEMPID_COOKIE_NAME))
				{
					//��������� ���� �������� ����
					String decodeCookieValue = URLDecoder.decode(cookie.getValue(), CODING);
					promoterDataMap.put(PROMOTERTEMPID_COOKIE_NAME,decodeCookieValue);
					//������ �������� ����. �������� "�� ����������-�� ������ ����������"
					promoterDataMap.putAll(getPromoterSessionInfoByPromoterTempId(cookie.getValue()));

					//���� ���� �� ��, ���, ��� ��� ��������, �� �� ��� ������ ����� � �������
					if (promoterDataMap.get(PROMOTERID_COOKIE_NAME) != null)
						break;
				}
			}
		}

		return promoterDataMap;
	}
}
