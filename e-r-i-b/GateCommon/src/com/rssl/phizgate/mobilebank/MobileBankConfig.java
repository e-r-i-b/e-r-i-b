package com.rssl.phizgate.mobilebank;

import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.PropertyReader;
import com.rssl.phizic.utils.StringHelper;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Erkin
 * @ created 17.08.2011
 * @ $Author$
 * @ $Revision$
 */
public class MobileBankConfig extends Config
{
	/**
	 * ������������ ����� ������ SMS ��-���������
	 */
	private static final int DEFAULT_MAX_SMS_LENGTH = 160;

	/**
	 * ID ���������� ��������� � �� "��������� ����"
	 */
	private static final int MOBILE_OPERATOR_ID = 1;

	private static final String CLIENT_REGISTRATION_UNLOAD_DIR = "mb.client.registration.unload.dir";
	private static final String SMS_ID_MAX_VALUE = "sms.id.max.value";
	private static final String IMSI_FAIL_STUBTEXT = "mb.sms.imsi.fail.stubText";
	private static final String SP_CACHE_LIFETIME = "mb.cache.lifetime.hours";
	private static final String SP_USE_CACHE = "mb.cache.on";
	private static final String OFFLINE_ERROR_CODES = "mb.offline.error.codes";
	private static final String MOBILEBANK_WEB_SERVICE_URL = "com.rssl.gate.mobilebank.web.service.url";
	private static final String USE_MOBILEBANK_AS_APP = "use.mobilebank.as.app";
	private static final String MAX_TIMEOUT = "max.timeout";
	private static final String USE_PACK_REGISTRATIONS = "use.pack.registrations";

	private static final String CODES_DELIMITER = ";";

	private int maxSMSID;
	private String clientRegUnloadDir; //���������� �������� ����������� ��
	private String mbSystemId;
	private String imsiFailStubText;
	private int spCacheLifetime;        //����� ����� ���� �������� �������� � �����
	private boolean spCacheOn;          //������������ �� ��� �������� ��������
	private Set<Integer> offlineCodes; //���� ��������, ��������������� � ������������� ��
	private String mobilebankWebServiceUrl; //��� ��������� ����� �������
	private boolean useMobilebankAsApp;  //������� ������������� MobileBankGate � �������� ���������� ����������
	private Long maxTimeout;          //������������ ������� ������
	private boolean usePackRegistrations;  //������������ �������� ������ ��������� �����������

	///////////////////////////////////////////////////////////////////////////

	public MobileBankConfig(PropertyReader reader)
	{
		super(reader);
	}

	public void doRefresh()
	{
		clientRegUnloadDir = getProperty(CLIENT_REGISTRATION_UNLOAD_DIR);
		mbSystemId = getProperty(com.rssl.phizic.jmx.MobileBankConfig.MB_SYSTEM_ID);
		imsiFailStubText = getProperty(IMSI_FAIL_STUBTEXT);
		spCacheLifetime = getIntProperty(SP_CACHE_LIFETIME);
		spCacheOn = getBoolProperty(SP_USE_CACHE);
		offlineCodes = parseOfflineCodes(getProperty(OFFLINE_ERROR_CODES));
		mobilebankWebServiceUrl = getProperty(MOBILEBANK_WEB_SERVICE_URL);
		useMobilebankAsApp = getBoolProperty(USE_MOBILEBANK_AS_APP);
		maxTimeout = getLongProperty(MAX_TIMEOUT);
		usePackRegistrations = getBoolProperty(USE_PACK_REGISTRATIONS);

		maxSMSID = 0;
		try
		{
			maxSMSID = Integer.valueOf(getProperty(SMS_ID_MAX_VALUE));
		}
		catch (NumberFormatException ignored) {}
	}

	public int getMaxSMSID()
	{
		return maxSMSID;
	}

	public int getMaxSMSTestLenght()
	{
		return DEFAULT_MAX_SMS_LENGTH;
	}

	public String getMbSystemId()
	{
		return mbSystemId;
	}

	/**
	 * ������������ � ��������� ����������� ������ ��������� ����
	 * @return ���������� �������� ����������� ��
	 */
	public String getClientRegUnloadDir()
	{
		return clientRegUnloadDir;
	}

	public static int getMobileOperatorId()
	{
		return MOBILE_OPERATOR_ID;
	}

	public String getImsiFailStubText()
	{
		return imsiFailStubText;
	}

	/**
	 * @return ����� ����� ���� �������� �������� � �����
	 */
	public int getSpCacheLifetime()
	{
		return spCacheLifetime;
	}

	/**
	 * @return ������������ �� ��� �������� ��������
	 */
	public boolean isSpCacheOn()
	{
		return spCacheOn;
	}

	/**
	 * @return  ���� ���������, ��������������� � ������������� ���
	 */
	public Set<Integer> getOfflineCodes()
	{
		return offlineCodes;
	}

	private Set<Integer> parseOfflineCodes(String offlineErrorCodes)
	{
		if (StringHelper.isEmpty(offlineErrorCodes))
			return Collections.emptySet();

		Set<Integer> result = new HashSet<Integer>();
		try
		{
			for (String code : offlineErrorCodes.split(CODES_DELIMITER))
				result.add(Integer.valueOf(code));
		}
		catch (NumberFormatException ignore)
		{
		}

		return result;
	}

	public String getMobilebankWebServiceUrl()
	{
		return mobilebankWebServiceUrl;
	}

	public boolean isUseMobilebankAsApp()
	{
		return useMobilebankAsApp;
	}

	public Long getMaxTimeout()
	{
		return maxTimeout;
	}

	public boolean isUsePackRegistrations()
	{
		return usePackRegistrations;
	}
}
