package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.config.ConfigFactory;

/**
 * @author bogdanov
 * @ created 23.04.2012
 * @ $Author$
 * @ $Revision$
 *
 * проверка на заблокированность клиента. что ведет к невозможности выполнения действий с документом.
 */

public class TestClientBlockedHandler extends BusinessDocumentHandlerBase
{
	public static final String CLIENT_BLOCKED_MESSAGE = "Вы не можете выполнить данную операцию, потому что клиент заблокирован.";

	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		//проверяем только в приложении сотрудника банка
		ApplicationConfig applicationConfig = ApplicationConfig.getIt();
		if (Application.PhizIA != applicationConfig.getApplicationInfo().getApplication())
			return;

		if (PersonHelper.isPersonBlocked())
		{
			throw new DocumentLogicException(CLIENT_BLOCKED_MESSAGE);
		}
	}
}
