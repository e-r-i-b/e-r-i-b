package com.rssl.phizic.messaging;

import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;

import java.util.Collections;

/**
 * @author khudyakov
 * @ created 20.07.15
 * @ $Author$
 * @ $Revision$
 */
public class MessagingHelper
{
	public static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	/**
	 * Отправить сообщение по документу
	 * @param document документ
	 * @param messageKey ключ к сообщению
	 */
	public static void sendMessage(BusinessDocument document, String messageKey)
	{
		try
		{
			IKFLMessage message = MessageComposer.buildInformingSmsMessage(document.getOwner().getLogin().getId(), messageKey, Collections.<String, Object>singletonMap("document", document), Collections.<String, Object>emptyMap());
			MessagingSingleton.getInstance().getMessagingService().sendSms(message);
		}
		catch (Exception e)
		{
			log.error("Ошибка отправки смс-сообщения.", e);
		}
	}

	/**
	 * Отправить сообщение
	 * @param loginId документ
	 * @param messageKey ключ к сообщению
	 */
	public static void sendMessage(Long loginId, String messageKey)
	{
		try
		{
			IKFLMessage message = MessageComposer.buildInformingSmsMessage(loginId, messageKey, Collections.<String, Object>emptyMap(), Collections.<String, Object>emptyMap());
			MessagingSingleton.getInstance().getMessagingService().sendSms(message);
		}
		catch (Exception e)
		{
			log.error("Ошибка отправки смс-сообщения.", e);
		}
	}
}
