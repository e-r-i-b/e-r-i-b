package com.rssl.phizic.operations.push;

import com.rssl.phizic.common.types.exceptions.IKFLMessagingException;
import com.rssl.phizic.messaging.IKFLMessagingLogicException;
import com.rssl.phizic.messaging.push.PushMessage;
import com.rssl.phizic.operations.sms.CallBackHandlerSmsImpl;

/**
 * отправка push-уведомления при подтверждении операции
 * @author basharin
 * @ created 21.10.13
 * @ $Author$
 * @ $Revision$
 */

public class CallBackHandlerPushImpl extends CallBackHandlerSmsImpl
{
	@Override
	public String proceed() throws IKFLMessagingException, IKFLMessagingLogicException
	{
		PushMessage message = messageComposer.buildConfirmationPushPasswordMessage(
				login, confirmableObject, password, needAdditionalCheck);
		message.setUseRecipientMobilePhoneOnly(useRecipientMobilePhoneOnly);
		message.setUseAlternativeRegistrations(useAlternativeRegistrations);
		message.setOperationType(operationType);

		return messagingService.sendOTPPush(message);
	}
}
