package com.rssl.phizic.gate.monitoring;

import com.rssl.phizic.ApplicationAutoRefreshConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.messaging.MessagingLogEntry;
import com.rssl.phizic.logging.monitoring.MonitoringDocumentType;
import com.rssl.phizic.logging.monitoring.MonitoringLogEntry;
import com.rssl.phizic.logging.monitoring.MonitoringOperationConfig;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.person.DeviceInfoObject;

import java.util.Calendar;

/**
 * @author akrenev
 * @ created 04.06.2015
 * @ $Author$
 * @ $Revision$
 *
 * ������ ������ � ������������ ��������
 */

public final class MonitoringLogHelper
{
	private MonitoringLogHelper(){}

	private static MonitoringOperationConfig getConfig()
	{
		return ConfigFactory.getConfig(MonitoringOperationConfig.class);
	}

	/**
	 * ������ � ���������� ������ ���������� �������� �� ������ �� �����
	 * @param document ��������
	 * @param statusCode ��� ������
	 */
	public static void writeAccountToCardTransferErrorLog(GateDocument document, String statusCode)
	{
		try
		{
			//�������� ������.
			MonitoringLogEntry entry = new MonitoringLogEntry();
			entry.setStateCode(statusCode);
			entry.setDocumentType(MonitoringDocumentType.AC.name());
			entry.setCreationDate(document.getClientCreationDate());
			entry.setStartDate(Calendar.getInstance());
			entry.setNodeId(ConfigFactory.getConfig(ApplicationAutoRefreshConfig.class).getNodeNumber());
			entry.setApplication(document.getClientCreationChannel().name());
			entry.setTb(document.getOffice().getCode().getFields().get("region"));
			if (document instanceof DeviceInfoObject)
				entry.setPlatform(((DeviceInfoObject) document).getDeviceInfo());
			getConfig().writeLog(entry);
		}
		catch (Exception e)
		{
			PhizICLogFactory.getLog(LogModule.Web).error(e);
		}
	}

	/**
	 * ������ � �������� �����������
	 * @param logEntry �������� ���� ���������
	 * @param stateCode ��� ������
	 * @param documentType ��� ���������
	 */
	public static void writeLog(MessagingLogEntry logEntry, String stateCode, String documentType)
	{
		try
		{
			MonitoringLogEntry entry = new MonitoringLogEntry();
			entry.setApplication(logEntry.getApplication().name());
			entry.setNodeId(ConfigFactory.getConfig(ApplicationAutoRefreshConfig.class).getNodeNumber());
			entry.setCreationDate(logEntry.getDate());
			entry.setStartDate(Calendar.getInstance());
			entry.setStateCode(stateCode);
			entry.setDocumentType(documentType);
			entry.setTb(logEntry.getTb());
			getConfig().writeLog(entry);
		}
		catch (Exception e)
		{
			PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE).error("�������� � ������� ������ � ������ �����������", e);
		}
	}

}
