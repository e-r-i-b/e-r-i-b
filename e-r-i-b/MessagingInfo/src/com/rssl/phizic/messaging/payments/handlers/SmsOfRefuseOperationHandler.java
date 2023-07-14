package com.rssl.phizic.messaging.payments.handlers;

import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.documents.BusinessDocumentBase;
import com.rssl.phizic.business.documents.payments.BusinessDocumentOwner;
import com.rssl.phizic.messaging.*;

/**
 * Отправляет СМС с информацией о невозможности совершить операцию.
 * Отправка СМС не должна влиять на перевод документа в статус REFUSED, поэтому исключения логируем и дальше не кидаем
 *
 * @author komarov
 * @ created 21.02.2013
 * @ $Author$
 * @ $Revision$
 */
public class SmsOfRefuseOperationHandler extends BusinessDocumentHandlerBase
{
	private final MessageComposer messageComposer = new MessageComposer();

	public void process(StateObject document, StateMachineEvent stateMachineEvent)
	{
		if(document instanceof BusinessDocumentBase)
		{
			BusinessDocumentBase doc = (BusinessDocumentBase)document;
			MessagingService messagingService = MessagingSingleton.getInstance().getMessagingService();

			try
			{
				BusinessDocumentOwner documentOwner = doc.getOwner();
				if (documentOwner.isGuest())
					throw new UnsupportedOperationException("Операция в гостевой сессии не поддерживается");
				Login userLogin = documentOwner.getLogin();
				IKFLMessage message = messageComposer.buildRefusePaymentMessage(userLogin, doc);
				messagingService.sendSms(message);
			}
			catch(IKFLMessagingLogicException e)
			{
				log.error(e.getMessage(), e);
			}
			catch (Exception e)
			{
				log.error("Ошибка при отправке смс об отказе исполнения документа " + doc.getId(), e);
			}
		}
	}

}
