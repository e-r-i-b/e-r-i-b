package com.rssl.phizic.web.common.client.rsa;

import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.rsa.exceptions.BlockClientOperationFraudException;
import com.rssl.phizic.rsa.exceptions.ProhibitionOperationFraudGateException;
import com.rssl.phizic.rsa.senders.types.ClientDefinedEventType;
import com.rssl.phizic.security.SecurityLogicException;

/**
 * @author tisov
 * @ created 12.03.15
 * @ $Author$
 * @ $Revision$
 * On-complete экшн для оповещения системы фрод-мониторинга о входе пользователя через мобильное приложение
 */
public class OnMAPILoginSendRSARequestAction extends OnLoginSendRSARequestAction
{
	public void execute(AuthenticationContext context) throws SecurityException, SecurityLogicException
	{
		try
		{
			initialize();
			doFraudControl(context);
		}
		catch (ProhibitionOperationFraudGateException e)
		{
			throw new SecurityLogicException(e.getMessage(), e);
		}
		catch (BlockClientOperationFraudException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			log.error(e);
		}
	}

	protected ClientDefinedEventType getEventTypeByContext(AuthenticationContext context)
	{
		return ClientDefinedEventType.WITHOUT_OTP;
	}
}
