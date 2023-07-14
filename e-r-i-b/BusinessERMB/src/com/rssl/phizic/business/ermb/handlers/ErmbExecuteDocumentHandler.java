package com.rssl.phizic.business.ermb.handlers;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.FormConstants;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.*;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.BusinessDocumentOwner;
import com.rssl.phizic.business.documents.payments.JurPayment;
import com.rssl.phizic.business.ermb.ErmbHelper;
import com.rssl.phizic.business.ermb.ErmbPaymentType;
import com.rssl.phizic.business.ermb.payments.SmsServicePaymentStatisticsService;
import com.rssl.phizic.business.ermb.sms.messaging.MessageBuilder;
import com.rssl.phizic.business.ermb.sms.messaging.SmsSenderUtils;
import com.rssl.phizic.common.types.UUID;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.ermb.ErmbConfig;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.logging.ermb.ErmbServicePaymentState;
import com.rssl.phizic.messaging.ermb.ErmbSmsContext;
import com.rssl.phizic.person.ErmbProfile;
import com.rssl.phizic.utils.StringHelper;

import java.util.Calendar;

/**
 * Хендлер для ЕРМБ, отправляющий клиенту сообщения об успешном выполнении платежа, если в документе не выставлен MBOperCode
 * @author Rtischeva
 * @ created 22.04.14
 * @ $Author$
 * @ $Revision$
 */
public class ErmbExecuteDocumentHandler extends BusinessDocumentHandlerBase<BusinessDocument>
{
	private final SmsServicePaymentStatisticsService servicePaymentHistoryService = new SmsServicePaymentStatisticsService();

	public void process(BusinessDocument document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		GateExecutableDocument gateDocument = (GateExecutableDocument) document;

		String documentFormName = gateDocument.getFormName();

		if(!(document instanceof AbstractPaymentDocument) && !(documentFormName.equals(FormConstants.LOSS_PASSBOOK_APPLICATION)))
			throw new DocumentException("Ожидается наследник AbstractPaymentDocument или LossPassbookApplication");

		MessageBuilder messageBuilder = new MessageBuilder();
		String message;

		if (document instanceof AbstractPaymentDocument)
		{
			AbstractPaymentDocument doc = (AbstractPaymentDocument)document;
			doc.setExecutionDate(Calendar.getInstance());

			ErmbPaymentType paymentType = ErmbPaymentType.valueOf(doc.getErmbPaymentType());

			try
			{
				//проверка, является ли платеж оплатой ПУ. Если да, записываем в таблицу истории оплаты ПУ
				if((ErmbPaymentType.SERVICE_PAYMENT.equals(paymentType) || ErmbPaymentType.TEMPLATE_PAYMENT.equals(paymentType) || ErmbPaymentType.RECHARGE_PHONE.equals(paymentType)) && FormConstants.SERVICE_PAYMENT_FORM.equals(documentFormName))
					servicePaymentHistoryService.writeServicePaymentStatisticsEntry((JurPayment) doc, ErmbServicePaymentState.SUCCESS);

				//включен ли механизм дедубликации
				boolean needSendNotification = ConfigFactory.getConfig(ErmbConfig.class).needSendPaymentSmsNotification();
				//если механизм дедубликации включен и документ является платежом поставщику услуг через IQWave, ничего не отправляем клиенту
				if (needSendNotification && FormConstants.SERVICE_PAYMENT_FORM.equals(documentFormName) && DocumentHelper.isIQWaveDocument(document))
					return;
			}
			catch (BusinessException e)
			{
				throw new DocumentException(e);
			}
			catch (GateException e)
			{
				throw new DocumentException(e);
			}

			//устанавливаем в ErmbSmsContext идентификатор смс-запроса
			String ermbSmsRequestId = doc.getErmbSmsRequestId();
			if (StringHelper.isNotEmpty(ermbSmsRequestId))
			{
				ErmbSmsContext.setIncomingSMSRequestUID(UUID.fromValue(ermbSmsRequestId));
			}

			message =  messageBuilder.buildPaymentSmsMessage(doc);
		}
		else
		{
			message = messageBuilder.buildAccountBlockSuccessMessage((LossPassbookApplication) document).getText();
		}

		try
		{
			BusinessDocumentOwner documentOwner = ((BusinessDocumentBase) document).getOwner();
			if (documentOwner.isGuest())
				throw new UnsupportedOperationException("Операция в гостевой сессии не поддерживается");
			ErmbProfile ermbProfile = ErmbHelper.getErmbProfileByLogin(documentOwner.getLogin());
			SmsSenderUtils.notifyClientMessage(ermbProfile.getMainPhoneNumber(), message);
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}
}
