package com.rssl.phizic.gate.utils;

import com.rssl.phizic.common.types.bankroll.BankProductType;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.utils.PropertyConfig;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

/**
 * ������ ��� ������ � �������� ���������
 *
 * @author khudyakov
 * @ created 19.10.2011
 * @ $Author$
 * @ $Revision$
 */
public class ExternalSystemHelper
{
	private static final String ESB_SYSTEM_CODE_NAME = "com.rssl.iccs.externalsystem.code";
	private static final String IPS_SYSTEM_CODE_NAME = "com.rssl.iccs.ips.externalsystem.code";
	private static final String BASKET_AUTOPAY_SYSTEM_CODE_NAME = "com.rssl.iccs.basket.autopay.externalsystem.code";
	private static final String MBK_SYSTEM_CODE_NAME = "com.rssl.iccs.mbk.externalsystem.code";

	/**
	 * ���������� ��� ������� �������
	 * @param code ��� (uuid �������� ���� SystemId ������� �������)
	 * @return ��� (uuid �������� ���� SystemId ������� �������)
 	 * @throws GateException, InactiveExternalSystemGateException
	 */
	public static String getCode(String code) throws GateException
	{
		check(code);
		return code;
	}

	/**
	 * ��������� �� ���������� ������� �������
	 * @param code ��� (uuid �������� ���� SystemId ������� �������)
	 * @throws GateException
	 */
	public static void check(String code) throws GateException
	{
		if (StringHelper.isEmpty(code))
			return;

		ExternalSystemGateService externalSystemGateService = GateSingleton.getFactory().service(ExternalSystemGateService.class);

		//��������� ���������� ������� ������� � ������� ������,
		//���� ������� ��������� ������� InactiveExternalSystemException � ��������� �� ���������������� ��������
		externalSystemGateService.check(code);
	}

	/**
	 * ��������� �� ���������� ������� �������
	 * @param externalSystem ������� �������
	 * @throws GateException
	 */
	public static void check(ExternalSystem externalSystem) throws GateException
	{
		ExternalSystemGateService externalSystemGateService = GateSingleton.getFactory().service(ExternalSystemGateService.class);

		//��������� ���������� ������� ������� � ������� ������,
		//���� ������� ��������� ������� InactiveExternalSystemException � ��������� �� ���������������� ��������
		externalSystemGateService.check(externalSystem);
	}

	/**
	 * ��������� �� ���������� ������� ������� �� ������������� � ��������
	 * @param office �������������
	 * @param product �������
	 * @throws GateException
	 */
	public static void check(final Office office, final BankProductType product) throws GateException
	{
		ExternalSystemGateService externalSystemGateService = GateSingleton.getFactory().service(ExternalSystemGateService.class);

		check(externalSystemGateService.findByProduct(office, product));
	}

	/**
	 * ��������� �� ���������� ������ ������� ������
	 * @param externalSystems ������ ������� ������
	 * @throws GateException
	 */
	public static void check(List<? extends ExternalSystem> externalSystems) throws GateException
	{
		if (CollectionUtils.isEmpty(externalSystems))
			return;

		for (ExternalSystem externalSystem : externalSystems)
		{
			//��������� ���������� ������� ������� � ������� ������,
			//���� ������� ��������� ������� InactiveExternalSystemException � ��������� �� ���������������� ��������
			check(externalSystem);
		}
	}

	/**
	 * ��������� �� ���������� ������� �������
	 * @param externalSystem ������� �������
	 * @throws GateException
	 * @return true/false
	 */
	public static boolean isActive(ExternalSystem externalSystem) throws GateException
	{
		ExternalSystemGateService externalSystemGateService = GateSingleton.getFactory().service(ExternalSystemGateService.class);

		//��������� ���������� ������� ������� � ������� ������
		return externalSystemGateService.isActive(externalSystem);
	}

	/**
	 * ��������� �� ���������� ������ ������� ������
	 * @param externalSystems ������ ������� ������
	 * @throws GateException
	 * @return true/false
	 */
	public static boolean isActive(List<? extends ExternalSystem> externalSystems) throws GateException
	{
		if (CollectionUtils.isEmpty(externalSystems))
			return true;

		for (ExternalSystem externalSystem : externalSystems)
		{
			//��������� ���������� ������� ������� � ������� ������,
			if (!isActive(externalSystem))
				return false;
		}
		return true;
	}

	/**
	 * @return ��� ������� ������� "����"
	 */
	public static String getESBSystemCode()
	{
		PropertyConfig config = ConfigFactory.getConfig(PropertyConfig.class);
		return config.getProperty(ESB_SYSTEM_CODE_NAME);
	}

	public static String getIpsSystemCode()
	{
		PropertyConfig config = ConfigFactory.getConfig(PropertyConfig.class);
		return config.getProperty(IPS_SYSTEM_CODE_NAME);
	}

	public static String getBasketAutoPaySystemCode()
	{
		PropertyConfig config = ConfigFactory.getConfig(PropertyConfig.class);
		return config.getProperty(BASKET_AUTOPAY_SYSTEM_CODE_NAME);
	}

	public static String getMbkSystemCode()
	{
		PropertyConfig config = ConfigFactory.getConfig(PropertyConfig.class);
		return config.getProperty(MBK_SYSTEM_CODE_NAME);
	}
}