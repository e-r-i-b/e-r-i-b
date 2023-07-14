package com.rssl.phizic.messaging.payments.handlers;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.auth.GuestLogin;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.payments.BusinessDocumentOwner;
import com.rssl.phizic.business.documents.payments.LoanCardClaim;
import com.rssl.phizic.business.ermb.ErmbHelper;
import com.rssl.phizic.business.sms.InformingBusinessMessageLoader;
import com.rssl.phizic.common.types.TextMessage;
import com.rssl.phizic.common.types.exceptions.IKFLMessagingException;
import com.rssl.phizic.messaging.*;
import com.rssl.phizic.messaging.mail.SmsTransportService;
import com.rssl.phizic.messaging.mail.messagemaking.sms.TemplateMessageBuilder;
import com.rssl.phizic.utils.StringHelper;

import java.util.Collections;

/**
 * @author Nady
 * @ created 23.05.15
 * @ $Author$
 * @ $Revision$
 */

/**
 * Хендлер отправляющий смс-сообщение клиенту о том, что заявка на кредитную карту принята и находится в обработку
 */
public class LoanCardClaimAdoptedHandler extends BusinessDocumentHandlerBase
{
	private static final String MESSAGE_ABOUT_SEND = "loan.card.claim.adopted";

	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentLogicException, DocumentException
	{
		if (!(document instanceof LoanCardClaim))
		{
			throw new DocumentException("Неверный тип платежа id=" + document.getId() + " (Ожидается LoanCardClaim)");
		}

		LoanCardClaim loanCardClaim = (LoanCardClaim) document;
		try
		{
			TextMessage textMessage = MessageComposeHelper.buildTextMessage(MessageTemplateType.INFORM, MESSAGE_ABOUT_SEND, Collections.<String, Object>emptyMap(), Collections.<String, Object>emptyMap());
			IKFLMessage message = getMessage(textMessage, loanCardClaim.getOwner());
			MessagingService messagingService = MessagingSingleton.getInstance().getMessagingService();
			messagingService.sendSms(message);
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
		catch (IKFLMessagingException e)
		{
			//В случае ошибки при отправке сообщения, сообщение об ошибке не отображаем, исключение не пробрасываем.
			log.error(e);
		}
		catch (IKFLMessagingLogicException e)
		{
			//В случае ошибки при отправке сообщения, сообщение об ошибке не отображаем, исключение не пробрасываем.
			log.error(e);
		}
	}

	/**
	 * Получения сообщения для отправки.
	 * @param textMessage Текстовки для сообщения
	 * @param owner Владелец документа
	 * @return Сообщение для отправки.
	 * @throws BusinessException
	 */
	private IKFLMessage getMessage(TextMessage textMessage, BusinessDocumentOwner owner) throws BusinessException
	{
		IKFLMessage message = new IKFLMessage();
		message.setText(textMessage.getText());
		message.setTextToLog(textMessage.getTextToLog());
		message.setAdditionalCheck(false);
		message.setPriority(textMessage.getPriority());

		if (owner.isGuest())
		{
			GuestLogin login = (GuestLogin)owner.getLogin();
			message.setRecipientMobilePhone(login.getAuthPhone());
			message.setRecipientLogin(login);
		}
		else
		{
			Long loginId = owner.getLogin().getId();
			message.setRecipientLoginId(loginId);
			message.setErmbConnectedPerson(ErmbHelper.hasErmbProfileByLogin(loginId));
		}

		return message;
	}
}
