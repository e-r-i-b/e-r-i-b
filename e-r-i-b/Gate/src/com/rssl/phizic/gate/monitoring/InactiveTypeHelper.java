package com.rssl.phizic.gate.monitoring;

import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.exceptions.InactiveExternalServiceException;
import com.rssl.phizic.gate.exceptions.OfflineExternalServiceException;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.lang.time.DateUtils;

import java.util.Date;
import java.util.Calendar;
import java.util.regex.Pattern;

/**
 * @author akrenev
 * @ created 26.02.2013
 * @ $Author$
 * @ $Revision$
 *
 * ������-����� ��� ������ � �������� �������������
 */

public class InactiveTypeHelper
{
	private static final String TIME_MARK_IN_MESSAGE = "<time>";
	private static final Pattern TIME_MARK_IN_MESSAGE_PATTERN = Pattern.compile(TIME_MARK_IN_MESSAGE);	
	private static final String DEFAULT_ERROR_MESSAGE = "������ �������� ����������. ���������� ��������� ������� �����.";

	/**
	 * ���������� ��������� � ������������� �����������
	 * @param stateConfig ��������� ����������� ������� �������
	 * @return ��������� �������, ��������� �� ����������� �������
	 */
	public static String getClientMessage(MonitoringServiceGateStateConfig stateConfig)
	{
		if(stateConfig == null)
			return DEFAULT_ERROR_MESSAGE;

		// ���� ������ �� �����, �� � ���������� ������
		String messageText = stateConfig.getLocaledMessageText();
		if (StringHelper.isEmpty(messageText))
			return DEFAULT_ERROR_MESSAGE;

		// ���� ����� ��������� �� �����, �� ��� � ����������
		if (!messageText.contains(TIME_MARK_IN_MESSAGE))
			return messageText;

		// ���� ����� ����� ��������, �� ������ �� �������, ������ ������ �� ����� �������
		Calendar deteriorationTime = stateConfig.getDeteriorationTime();
		Long recoveryTime = stateConfig.getRecoveryTime();
		if (deteriorationTime == null || recoveryTime == null)
			return DEFAULT_ERROR_MESSAGE;

		// ��������� � ������ ����� �����
		Date date = DateUtils.addMilliseconds(deteriorationTime.getTime(), recoveryTime.intValue());
		return TIME_MARK_IN_MESSAGE_PATTERN.matcher(messageText).replaceAll(DateHelper.formatDateDependsOnSysDate(date));
	}

	/**
	 * ������������ ����� ������������� ��� ������� �� ��������� ���������� ��������
	 * @param stateConfig ��������� ����������� ������� �������
	 * @throws GateLogicException
	 */
	public static void activate(MonitoringServiceGateStateConfig stateConfig) throws GateLogicException
	{
		if(stateConfig == null)
			return;

		switch (stateConfig.getInactiveType())
		{
			case offline:  throw new OfflineExternalServiceException(getClientMessage(stateConfig));
			case inactive: throw new InactiveExternalServiceException(getClientMessage(stateConfig));
		}
	}
}
