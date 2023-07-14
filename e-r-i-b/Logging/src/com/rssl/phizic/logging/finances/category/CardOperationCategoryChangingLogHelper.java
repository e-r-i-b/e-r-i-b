package com.rssl.phizic.logging.finances.category;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.ips.IPSConfig;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.finances.ALFLogConfig;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.writers.CardOperationCategoryChangingLogWriter;


/**
 * @author lepihina
 * @ created 28.10.13
 * @ $Author$
 * @ $Revision$
 */
public class CardOperationCategoryChangingLogHelper
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	/**
	 * ��������� ������ � ��� � ��������� �������� ���������
	 * @param entry - ������ �� ��������� �������� ���������
	 */
	public static void writeEntryToLog(CardOperationCategoryChangingLogEntry entry)
	{
		try
		{
			if (!isLogCardOperationCategoryOn())
				return;

			writeToActionLog(entry);
		}
		catch (Throwable t)
		{
			//������� ����������� �� ������ �������� ������ �������.
			log.error(t);
		}
	}


	private static void writeToActionLog(CardOperationCategoryChangingLogEntry entry)
	{
		if(entry == null)
			return;

		ALFLogConfig config = ConfigFactory.getConfig(ALFLogConfig.class);
		CardOperationCategoryChangingLogWriter writer = config.getCardOperationCategoryChangingLogWriter();
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

	/**
	 * @return �������� �� ����������� true - ��������
	 */
	public static boolean isLogCardOperationCategoryOn()
	{
		return ConfigFactory.getConfig(IPSConfig.class).isLogCardOperationCategoryOn();
	}
}
