package com.rssl.phizic.logging.push;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.messaging.MessageLogConfig;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.writers.StatisticLogWriter;

/**
 * Writer ��� ������ ����� push � mail �����������
 * @author basharin
 * @ created 13.11.13
 * @ $Author$
 * @ $Revision$
 */

public class PushMailStatisticLogWriter
{
	private static Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	/**
	 * ������������� push ���������.
	 * @param entry - ����� ��� �����������
	 */
	public void writeToPushWriter(StatisticLogEntry entry) throws Exception
	{
		try
		{
			StatisticLogWriter writer = getPushWriter();
			//���� writer==null ������ ������ � ������ ���������
			if (writer != null)
				writer.write(entry);
		}
		catch (Exception me)
		{
			log.warn("������ ������ � ������ ���������.", me);
			throw me;
		}
	}


	/**
	 * @return writer ����������� push ���������.
	 */
	private StatisticLogWriter getPushWriter()
	{
		return ConfigFactory.getConfig(MessageLogConfig.class).getPushStatisticWriter();
	}
}
