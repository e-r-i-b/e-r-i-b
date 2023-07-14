package com.rssl.phizic.config.atm;

import com.rssl.phizic.common.types.annotation.Singleton;
import com.rssl.phizic.common.types.annotation.ThreadSafe;
import com.rssl.phizic.config.*;

import java.util.HashMap;
import java.util.Map;

/**
 * ������ atmAPI
 * @author Dorzhinov
 * @ created 30.04.2013
 * @ $Author$
 * @ $Revision$
 */
@Singleton
@ThreadSafe
public class AtmApiConfig extends Config
{
	public static final String SHOW_SERVICES = "atm.api.show.services";
	private static final String ONLY_WAY_CARDS = "atm.api.only.way.cards.onload";
	private static final String CHECK_UDBO_ON_LOGIN_ATM = "atm.api.login.check.udbo";
	private static final String FIELD_RISK_INFO_MESSAGE = "atm.api.field.risk.message";

	private static final String DICTIONARY_SERVICES_PATH = "com.rssl.phizic.config.dictionary.path.services";

	private static final String DICTIONARY_SERVICES_PREFIX = "com.rssl.phizic.config.dictionary.prefix.services";

	private static final String DICTIONARY_SERVICES_SUFFIX = "com.rssl.phizic.config.dictionary.sufix.services";
	private static final String LOGIN_REGION_CODE_IS_OKATO = "atm.api.login.regioncode.okato";
	public static final String OLD_DEPOSIT_GROUP_CODES     = "old.deposit.group.codes";
	public static final String SHOW_DEPOSIT_GROUP_CODES    = "show.deposit.group.codes";

	private boolean showServices = true; //������� ����������� ����� ����� � �������� �����������
	private Boolean onlyWayCards;
	private boolean checkUDBOOnLogin;
	private String fieldRiskInfoMessage; //����� ��������� � ����������� �������

	//���� ��� �������� ������������ ����� � �����������
	private String servicesPath;
	//�������� ������
	private String servicesPrefix;
	//���������� ������
	private String servicesSuffix;
	//��� �������, ���������� ��� ����� � ��� (true - ��� ������ 5 ���� ���� �����, false - ��� ������������� ������� � �� ����)
	private boolean regionCodeIsOkato;
	//���� ����� ������ (�� ��_14) �������
	private Map<String, String> oldDepositCodesList;
	//���������� �� �������� � ������ ����� �����
	private boolean showDepositCodes;

	public AtmApiConfig(PropertyReader reader)
	{
		super(reader);
	}

	protected void doRefresh() throws ConfigurationException
	{
		String stringValue = getProperty(SHOW_SERVICES);
		showServices = stringValue == null ? true : Boolean.valueOf(stringValue);
		onlyWayCards = getBoolProperty(ONLY_WAY_CARDS);
		checkUDBOOnLogin = getBoolProperty(CHECK_UDBO_ON_LOGIN_ATM);
		fieldRiskInfoMessage = getProperty(FIELD_RISK_INFO_MESSAGE);

		servicesPath = getProperty(DICTIONARY_SERVICES_PATH);

		servicesPrefix = getProperty(DICTIONARY_SERVICES_PREFIX);

		servicesSuffix = getProperty(DICTIONARY_SERVICES_SUFFIX);

		regionCodeIsOkato = getBoolProperty(LOGIN_REGION_CODE_IS_OKATO);

		oldDepositCodesList = getOldDepositCodes();

		showDepositCodes = getBoolProperty(SHOW_DEPOSIT_GROUP_CODES);
	}

	private Map<String, String> getOldDepositCodes()
	{
		String codesStr = getProperty(OLD_DEPOSIT_GROUP_CODES);
		String[] codes = codesStr.replace(" ", "").split(",");

		Map<String, String> depositCodesMap = new HashMap<String, String>(codes.length);

		for (String pair : codes)
		{
			String[] entity = pair.split(":");
			depositCodesMap.put(entity[0], entity[1]);
		}

		return depositCodesMap;
	}

	/**
	 * @return ������� ����������� ����� ����� � �������� �����������
	 */
	public Boolean isShowServices()
	{
		return showServices;
	}

	/**
	 * @return ������� ��������� ������ ���� Way �� �����
	 */
	public boolean getOnlyWayCards()
	{
		return onlyWayCards;
	}

	public boolean isCheckUDBOOnLogin()
	{
		return checkUDBOOnLogin;
	}

	/**
	 * @return ����� ��������� � ����������� �������
	 */
	public String getFieldRiskInfoMessage()
	{
		return fieldRiskInfoMessage;
	}

	/**
	 * @return ���� ��� �������� ����������� �����
	 */
	public String getServicesPath()
	{
		return servicesPath;
	}
	/**
	 * @return ������� ����� ����������� �����
	 */
	public String getServicesPrefix()
	{
		return servicesPrefix;
	}
	/**
	 * @return ���������� ����� ����������� �����
	 */
	public String getServicesSuffix()
	{
		return servicesSuffix;
	}

	/**
	 * @return true - ��� �������, ���������� ��� ����� � ��� ��� ������ 5 ���� ���� �����, false - ��� ������������� ������� � �� ����
	 */
	public boolean isRegionCodeIsOkato()
	{
		return regionCodeIsOkato;
	}

	/**
	 * @return ������ "������" �������, ������� ����� �������� ������� � ���� < 8
	 */
	public Map<String, String> getOldDepositCodesList()
	{
		return oldDepositCodesList;
	}

	/**
	 * @return true, ���� ���� ����������� �������� ��� �������� ��� �������� ��� ��������� ��������� ����������.
	 * ���������� ������ � ������ ����� �����
	 */
	public boolean isShowDepositCodes()
	{
		return showDepositCodes;
	}
}
