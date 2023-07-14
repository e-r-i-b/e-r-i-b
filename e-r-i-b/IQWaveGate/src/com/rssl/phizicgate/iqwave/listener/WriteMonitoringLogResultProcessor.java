package com.rssl.phizicgate.iqwave.listener;

import com.rssl.phizic.ApplicationAutoRefreshConfig;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.common.types.ApplicationInfo;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.PropertyCategory;
import com.rssl.phizic.gate.documents.SynchronizableDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.logging.monitoring.MonitoringDocumentType;
import com.rssl.phizic.logging.monitoring.MonitoringLogEntry;
import com.rssl.phizic.logging.monitoring.MonitoringOperationConfig;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizicgate.iqwave.messaging.Constants;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.Calendar;

/**
 * запись в лог мониторинга.
 * @author basharin
 * @ created 13.05.15
 * @ $Author$
 * @ $Revision$
 */

public class WriteMonitoringLogResultProcessor extends PaymentExecutionResultProcessor
{

	@Override
	public void fillPaymentData(SynchronizableDocument synchronizableDocument, Document bodyContent) throws GateException
	{
		super.fillPaymentData(synchronizableDocument, bodyContent);

		MonitoringOperationConfig config = ConfigFactory.getConfig(MonitoringOperationConfig.class, ApplicationInfo.getCurrentApplication(), PropertyCategory.Phizic.getValue());
		if (!config.isActive())
			return;

		try
		{
			MonitoringLogEntry entry = new MonitoringLogEntry();
			entry.setApplication(Application.PhizIC.name());
			entry.setNodeId(ConfigFactory.getConfig(ApplicationAutoRefreshConfig.class).getNodeNumber());
			entry.setCreationDate(synchronizableDocument.getClientCreationDate());
			entry.setStartDate(Calendar.getInstance());
			Element element = bodyContent.getDocumentElement();
			entry.setStateCode(XmlHelper.getSimpleElementValue(element, Constants.ERROR_CODE_TAG));
			entry.setDocumentType(MonitoringDocumentType.IQWAVE.name());
			config.writeLog(entry);
		}
		catch (Exception e)
		{
			LogFactory.getLog(LogModule.Gate.name()).error("Проблемы с записью статуса в журнал мониторинга", e);
		}
	}
}
