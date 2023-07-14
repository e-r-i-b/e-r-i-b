package com.rssl.phizic.business.ermb.payments;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.payments.JurPayment;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.ermb.ErmbServicePaymentState;
import com.rssl.phizic.logging.ermb.SmsServicePaymentStatisticsEntry;
import com.rssl.phizic.logging.jms.JMSQueueSender;
import com.rssl.phizic.logging.messaging.JMSMessageLogWriter;
import com.rssl.phizic.logging.monitoring.MonitoringOperationConfig;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;

import java.util.Calendar;
import javax.jms.JMSException;

/**
 * @author Rtischeva
 * @ created 29.10.14
 * @ $Author$
 * @ $Revision$
 */
public class SmsServicePaymentStatisticsService
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	/**
	 * отправить в jms-очередь Запись об успешном или неуспешном исполнении платежа ПУ в смс-канале
	 * @param payment - платеж
	 * @param state - статус платежа
	 * @throws BusinessException
	 */
	public void writeServicePaymentStatisticsEntry(JurPayment payment, ErmbServicePaymentState state) throws BusinessException
	{
		SmsServicePaymentStatisticsEntry entry = createEntry(payment, state);
		writeEntry(entry);
	}

	private SmsServicePaymentStatisticsEntry createEntry(JurPayment payment, ErmbServicePaymentState state)
	{
		SmsServicePaymentStatisticsEntry servicePaymentHistoryEntry = new SmsServicePaymentStatisticsEntry();
		servicePaymentHistoryEntry.setPaymentId(payment.getId());
		servicePaymentHistoryEntry.setServiceProviderId(payment.getReceiverInternalId());
		servicePaymentHistoryEntry.setServiceProviderName(payment.getReceiverName());
		servicePaymentHistoryEntry.setPaymentState(state);
		servicePaymentHistoryEntry.setFinalStatusDate(Calendar.getInstance());
		servicePaymentHistoryEntry.setAmount(payment.getExactAmount().getDecimal());
		servicePaymentHistoryEntry.setCurrency(payment.getExactAmount().getCurrency().getCode());
		servicePaymentHistoryEntry.setTb(payment.getOffice().getCode().getFields().get("region"));
		return servicePaymentHistoryEntry;
	}

	private void writeEntry(SmsServicePaymentStatisticsEntry entry)
	{
		if(entry == null)
			return;
		MonitoringOperationConfig conf = ConfigFactory.getConfig(MonitoringOperationConfig.class);
		JMSQueueSender sender = new JMSQueueSender(conf.getJMSQueueFactoryName(), conf.getJMSQueueName());

		try
		{
			sender.send(entry);
		}
		catch (JMSException e)
		{
			log.error(e.getMessage(), e);
			if (e.getLinkedException() != null)
				log.error(e.getLinkedException());
		}
	}
}
