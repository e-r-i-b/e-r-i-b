package com.rssl.phizic.config.externalsystem;

import com.rssl.phizic.config.*;
import com.rssl.phizic.common.types.external.systems.AutoStopSystemType;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * ������ ��� ����������� ����������� ���������� ������ ��� ��������� ������� ������� (��)
 * @author Pankin
 * @ created 23.11.2012
 * @ $Author$
 * @ $Revision$
 */

public class AutoTechnoBreakConfig extends Config
{
	public static final String ERROR_LIMIT_PREFIX = "com.rssl.iccs.external.system.error.limit.";
	public static final String BREAK_DURATION_PREFIX = "com.rssl.iccs.external.system.break.duration.";
	public static final String ALLOW_OFFLINE_PAYMENTS = "com.rssl.iccs.allow.offline.payments";
	public static final String MANUAL_REMOVING_AUTO_TECHNO_BREAK = "com.rssl.iccs.manual.removing.auto.techno.break";
	public static final String ALLOW_GFL_FOR_EACH_PRODUCT = "com.rssl.iccs.allow.gfl.for.each.product";

	private Map<AutoStopSystemType, Long> errorLimitsMap;
	private Map<AutoStopSystemType, Long> technoBreakDurationMap;
	private boolean allowOfflinePayments;
	private boolean manualRemovingAutoTechnoBreak;
	private boolean allowGFLForEachProduct;

	public AutoTechnoBreakConfig(PropertyReader reader)
	{
		super(reader);
	}

	public void doRefresh() throws ConfigurationException
	{
		errorLimitsMap = refreshMap(ERROR_LIMIT_PREFIX);
		technoBreakDurationMap = refreshMap(BREAK_DURATION_PREFIX);
		allowOfflinePayments = getBoolProperty(ALLOW_OFFLINE_PAYMENTS);
		manualRemovingAutoTechnoBreak = getBoolProperty(MANUAL_REMOVING_AUTO_TECHNO_BREAK);
		allowGFLForEachProduct = getBoolProperty(ALLOW_GFL_FOR_EACH_PRODUCT);
	}

	/**
	 * @return �������� ����� <��� �� : ���������� ����� ������>
	 */
	public Map<AutoStopSystemType, Long> getErrorLimitsMap()
	{
		return Collections.unmodifiableMap(errorLimitsMap);
	}

	/**
	 * �������� ���������� ���������� ������ ��� ����������� ���� �������
	 * @param autoStopSystemType ��� �������
	 * @return ���������� ���������� ������
	 */
	public Long getErrorLimitBySystemType(AutoStopSystemType autoStopSystemType)
	{
		return getErrorLimitsMap().get(autoStopSystemType);
	}

	/**
	 * @return �������� ����� <��� �� : ������������ ��������, ������������� �������������>
	 */
	public Map<AutoStopSystemType, Long> getTechnoBreakDurationsMap()
	{
		return Collections.unmodifiableMap(technoBreakDurationMap);
	}

	/**
	 * �������� ������������ ��������, ������������� �������������, ��� ����������� ���� �������
	 * @param autoStopSystemType ��� �������
	 * @return ������������ ��������, ������������� �������������
	 */
	public Long getTechnoBreakDurationBySystemType(AutoStopSystemType autoStopSystemType)
	{
		return getTechnoBreakDurationsMap().get(autoStopSystemType);
	}

	/**
	 * ��������� �� ������� ��� ��������������� ���������
	 * @return true - ���������
	 */
	public boolean isAllowOfflinePayments()
	{
		return allowOfflinePayments;
	}

	/**
	 * ��������� �� �������� ��������������� ���������, ������������ �������������, ������ �������
	 * @return true - ������ �������, false - ����� �������������
	 */
	public boolean isManualRemovingAutoTechnoBreak()
	{
		return manualRemovingAutoTechnoBreak;
	}

	/**
	 * �������� �� ������ GFL �� ������� �� ���������, ���� �������� ������ ������������� ��� ������� ��
	 * ���������� ���������
	 * @return true - ��������
	 */
	public boolean isAllowGFLForEachProduct()
	{
		return allowGFLForEachProduct;
	}

	private Map<AutoStopSystemType, Long> refreshMap(String prefix)
	{
		Map<AutoStopSystemType, Long> resultMap = new HashMap<AutoStopSystemType, Long>();

		for (AutoStopSystemType systemType : AutoStopSystemType.values())
		{
			resultMap.put(systemType, getLongProperty(prefix + systemType.name()));
		}
		return resultMap;
	}
}
