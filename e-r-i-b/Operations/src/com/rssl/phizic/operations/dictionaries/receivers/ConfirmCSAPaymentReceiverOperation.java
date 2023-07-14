package com.rssl.phizic.operations.dictionaries.receivers;

import com.rssl.phizic.authgate.AuthGateException;
import com.rssl.phizic.authgate.AuthGateLogicException;
import com.rssl.phizic.authgate.AuthGateSingleton;
import com.rssl.phizic.authgate.AuthParamsContainer;
import com.rssl.phizic.authgate.authorization.AuthGateService;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.messaging.*;
import com.rssl.phizic.common.types.exceptions.IKFLMessagingException;

/**
 * @author Gainanov
 * @ created 23.11.2009
 * @ $Author$
 * @ $Revision$
 */
public class ConfirmCSAPaymentReceiverOperation extends ConfirmPaymentReceiverOperation
{
	private final MessageComposer messageComposer = new MessageComposer();

	///////////////////////////////////////////////////////////////////////////

	public void validateConfirm(String token) throws BusinessLogicException, BusinessException
	{
		try
		{
			AuthGateService service = AuthGateSingleton.getAuthService();
			AuthParamsContainer container = new AuthParamsContainer();
			container.addParameter("AuthToken", token);
			container.addParameter("Service", "ESK2");
			AuthParamsContainer result = service.checkAuthentication(container);
			if (!result.getParameter("OID").equals(getConfirmableObject().getId().toString()))
				throw new BusinessLogicException("Произошла ошибка при подтверждении платежа, необходимо повторить попытку");
		}
		catch (AuthGateLogicException e)
		{
			throw new BusinessLogicException(e);
		}
		catch (AuthGateException e)
		{
			throw new BusinessException(e);
		}
	}

	public String prepareConfirm(String SID, String backRef) throws BusinessException, BusinessLogicException
	{
		try
		{
			ActivePerson person = PersonContext.getPersonDataProvider().getPersonData().getPerson();
			IKFLMessage message = messageComposer.buildConfirmationSmsPasswordMessage(
					person.getLogin(), getConfirmableObject(), null, false);

			AuthGateService service = AuthGateSingleton.getAuthService();
			AuthParamsContainer container = new AuthParamsContainer();
			container.addParameter("SID", SID);
			container.addParameter("Service", "ESK2");
			container.addParameter("BackRef", backRef);
			container.addParameter("Text", message.getText());
			container.addParameter("AuthType", "");
			container.addParameter("OID", getConfirmableObject().getId().toString());

			AuthParamsContainer result = service.prepareAuthentication(container);
			return result.getParameter("AuthToken");
		}
		catch(AuthGateException ex)
		{
			throw new BusinessException(ex);
		}
		catch (AuthGateLogicException ex)
		{
			throw new BusinessLogicException(ex);
		}
		catch (IKFLMessagingException ex)
		{
			throw new BusinessException(ex);
		}
	}
}
