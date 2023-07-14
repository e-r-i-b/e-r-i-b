package com.rssl.phizic.business.payments;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.ApplicationAutoRefreshConfig;
import com.rssl.phizic.business.documents.AbstractAccountsTransfer;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.logging.monitoring.MonitoringDocumentType;
import com.rssl.phizic.logging.monitoring.MonitoringLogEntry;
import com.rssl.phizic.logging.monitoring.MonitoringOperationConfig;

import java.util.Calendar;

/**
 * базовый класс для записи в лог моинторинга данных о платежах
 * @author basharin
 * @ created 08.05.15
 * @ $Author$
 * @ $Revision$
 */

public abstract class WriteToMonitoringLogHandlerBase extends BusinessDocumentHandlerBase
{
	private static final int ACCOUNT_LENGTH = 20;

	abstract protected String getStateCode(AbstractAccountsTransfer document);

	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentLogicException, DocumentException
	{
		if (!(document instanceof AbstractAccountsTransfer)){
			throw new DocumentException("Логирование происходит для платежей");
		}
		AbstractAccountsTransfer abstractPaymentDocument = (AbstractAccountsTransfer) document;

		MonitoringOperationConfig config = ConfigFactory.getConfig(MonitoringOperationConfig.class);
		if (!config.isActive())
			return;

		MonitoringDocumentType documentType;
		int payerAccountLength = abstractPaymentDocument.getChargeOffAccount().length();
		int receiverAccountLength = abstractPaymentDocument.getReceiverAccount().length();
		if (payerAccountLength < ACCOUNT_LENGTH && receiverAccountLength >= ACCOUNT_LENGTH)
			documentType = MonitoringDocumentType.TCD;
		else if (payerAccountLength >= ACCOUNT_LENGTH && receiverAccountLength < ACCOUNT_LENGTH)
			documentType = MonitoringDocumentType.TDC;
		else
			return;

		try
		{
			MonitoringLogEntry entry = new MonitoringLogEntry();
			entry.setApplication(Application.PhizIC.name());
			entry.setNodeId(ConfigFactory.getConfig(ApplicationAutoRefreshConfig.class).getNodeNumber());
			entry.setCreationDate(abstractPaymentDocument.getDateCreated());
			entry.setStartDate(Calendar.getInstance());
			entry.setStateCode(getStateCode(abstractPaymentDocument));
			entry.setDocumentType(documentType.name());
			config.writeLog(entry);
		}
		catch (Exception e)
		{
			log.error("Проблемы с записью статуса в журнал мониторинга", e);
		}
	}
}