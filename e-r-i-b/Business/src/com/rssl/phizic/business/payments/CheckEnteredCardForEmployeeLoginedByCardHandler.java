package com.rssl.phizic.business.payments;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.gate.longoffer.autosubscription.AutoSubscription;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.auth.modes.UserVisitingMode;

/**
 * хендлер на проверку того, что платже идет с карты, с помощью которой пользователь авторизовался в АРМ сотрудника.
 *
 * @author bogdanov
 * @ created 26.07.2012
 * @ $Author$
 * @ $Revision$
 */

public class CheckEnteredCardForEmployeeLoginedByCardHandler extends BusinessDocumentHandlerBase
{
	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (!(document instanceof AutoSubscription))
			return;

		AutoSubscription subscription = (AutoSubscription) document;

		try
		{
			if (PersonHelper.getLastClientLogin() == null)
				return;

			if (PersonHelper.getLastClientLogin().getLastUserVisitingMode() == UserVisitingMode.EMPLOYEE_INPUT_BY_CARD)
				if (!subscription.getCardNumber().equals(PersonHelper.getLastClientLogin().getLastLogonCardNumber()))
					throw new DocumentLogicException("Возможна работа только с карты, с которой произведен вход клиента");
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}
}
