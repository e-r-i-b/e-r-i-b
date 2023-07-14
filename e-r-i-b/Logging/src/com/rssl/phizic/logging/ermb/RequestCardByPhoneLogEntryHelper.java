package com.rssl.phizic.logging.ermb;

import com.rssl.phizic.ApplicationAutoRefreshConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;

import java.util.Calendar;

/**
 * @author lukina
 * @ created 04.11.2014
 * @ $Author$
 * @ $Revision$
 */
public class RequestCardByPhoneLogEntryHelper
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	/**
	 * ���������� � ��� ������ � ���������� ������ �������� � ����/��� �� ������� ��������
	 * @param loginId   - id �������
	 * @param fio - ��� �������
	 * @param docType - ��� ���� �������
	 * @param docNumber - ����� � ����� ���� �������
	 * @param birthday  - ���� �������� �������
	 */
	public static void addLogEntry(Long loginId, String fio, String docType,String docNumber, Calendar birthday)
	{
		try
		{

			RequestCardByPhoneLogEntry logEntry = new RequestCardByPhoneLogEntry();
			logEntry.setLoginId(loginId);
			logEntry.setFio(fio);
			logEntry.setDocType(docType);
			logEntry.setDocNumber(docNumber);
			logEntry.setBirthday(birthday);
			logEntry.setEventDate(Calendar.getInstance());
			logEntry.setBlockId(ConfigFactory.getConfig(ApplicationAutoRefreshConfig.class).getNodeNumber());
			writeToLog(logEntry);
		}
		catch (Throwable t)
		{
			//������� ����������� �� ������ �������� ������ �������.
			log.error(t);
		}
	}

	private static void writeToLog(RequestCardByPhoneLogEntry entry)
	{
		if(entry == null)
			return;

		RequestCardByPhoneLogConfig config = ConfigFactory.getConfig(RequestCardByPhoneLogConfig.class);
		if (!config.isLoggingOn())
			return;

		JMSRequestCardByPhoneLodWriter writer = new JMSRequestCardByPhoneLodWriter();
		try
		{
			if (writer != null)
			{
				writer.write(entry);
			}
		}
		catch (Throwable t)
		{
			//������� ����������� �� ������ �������� ������ �������.
			log.error(t);
		}
	}
}
