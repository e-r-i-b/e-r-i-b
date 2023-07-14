package com.rssl.phizic.business.ermb.handlers;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.FormConstants;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
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
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.logging.ermb.ErmbServicePaymentState;
import com.rssl.phizic.messaging.ermb.ErmbSmsContext;
import com.rssl.phizic.person.ErmbProfile;
import com.rssl.phizic.utils.StringHelper;

/**
 * Хендлер для ЕРМБ, отправляющий клиенту сообщения об отказе платежа
 * @author Gulov
 * @ created 24.04.14
 * @ $Author$
 * @ $Revision$
 */
public class ErmbRefuseDocumentHandler extends BusinessDocumentHandlerBase<BusinessDocument>
{
	private static final String ERROR_SEND_MESSAGE = "Ошибка при отправке смс об отказе исполнения документа %s";
	private final SmsServicePaymentStatisticsService servicePaymentHistoryService = new SmsServicePaymentStatisticsService();

	public void process(BusinessDocument document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		GateExecutableDocument gateDocument = (GateExecutableDocument) document;

		String documentFormName = gateDocument.getFormName();

		if(!(document instanceof AbstractPaymentDocument) && !(documentFormName.equals(FormConstants.LOSS_PASSBOOK_APPLICATION)))
			throw new DocumentException("Ожидается наследник AbstractPaymentDocument или LossPassbookApplication");

		MessageBuilder messageBuilder = new MessageBuilder();
		String message;

		if(document instanceof AbstractPaymentDocument)
		{
			AbstractPaymentDocument doc = (AbstractPaymentDocument)document;

			try
			{
				//устанавливаем в ErmbSmsContext идентификатор смс-запроса
				String ermbSmsRequestId = doc.getErmbSmsRequestId();
				if (StringHelper.isNotEmpty(ermbSmsRequestId))
				{
					ErmbSmsContext.setIncomingSMSRequestUID(UUID.fromValue(ermbSmsRequestId));
				}

				ErmbPaymentType paymentType = ErmbPaymentType.valueOf(doc.getErmbPaymentType());
				//проверка, является ли платеж оплатой ПУ. Если да, записываем в таблицу истории оплаты ПУ
				if((ErmbPaymentType.SERVICE_PAYMENT.equals(paymentType) || ErmbPaymentType.TEMPLATE_PAYMENT.equals(paymentType) || ErmbPaymentType.RECHARGE_PHONE.equals(paymentType)) && FormConstants.SERVICE_PAYMENT_FORM.equals(documentFormName))
					servicePaymentHistoryService.writeServicePaymentStatisticsEntry((JurPayment) doc, ErmbServicePaymentState.REFUSE);

				// в iqwave произошел отказ платежа
				if (DocumentHelper.isIQWaveDocument(document) && ErmbPaymentType.valueOf(doc.getErmbPaymentType()) == ErmbPaymentType.RECHARGE_PHONE)
				{
					doc.setRefusingReason(doc.getRefusingReason());
					message = messageBuilder.buildDocumentRefusedMessage(doc, true, stateMachineEvent.getErrorCode()).getText();
				}
				else
					message = messageBuilder.buildDocumentRefusedMessage(doc, false, stateMachineEvent.getErrorCode()).getText();

			}
			catch (BusinessException e)
			{
				throw new DocumentException(e);
			}
			catch (GateException e)
			{
				log.error(String.format(ERROR_SEND_MESSAGE, doc.getId()), e);
				throw new DocumentException(e);
			}
		}
		else
		{
			message = messageBuilder.buildNotBlockedAccountMessage((LossPassbookApplication) document).getText();
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
