package com.rssl.phizic.logging.operations;

import com.rssl.phizic.logging.LoggingAccessor;
import com.rssl.phizic.logging.operations.config.OperationsLogConfig;
import com.rssl.phizic.config.ConfigFactory;

/**
 * �������� ���������� ������������� ����������� ��������� ��������
 * @author gladishev
 * @ created 20.05.2013
 * @ $Author$
 * @ $Revision$
 */
public class PassiveOperationsLoggingAccessor implements LoggingAccessor
{
	public boolean needNormalModeLogging(Object... parameters)
	{
		OperationsLogConfig config = ConfigFactory.getConfig(OperationsLogConfig.class);
		//��������� �������� ����� ������ � ������ "��� ��������".
		return config.getMode() == OperationsLogConfig.Mode.FULL;
	}

	public boolean needExtendedModeLogging(Object... parameters)
	{
		OperationsLogConfig config = ConfigFactory.getConfig(OperationsLogConfig.class);
		//��������� �������� ����� ������ � ������ "��� ��������".
		return config.getExtendedMode() == OperationsLogConfig.Mode.FULL;
	}
}