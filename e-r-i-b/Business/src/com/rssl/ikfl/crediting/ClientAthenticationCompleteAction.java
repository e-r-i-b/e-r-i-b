package com.rssl.ikfl.crediting;

import com.rssl.phizic.auth.modes.AthenticationCompleteAction;
import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.security.SecurityLogicException;

/**
 * @author Erkin
 * @ created 26.12.2014
 * @ $Author$
 * @ $Revision$
 */

/**
 * ����� ����� �� ������ "������������ ���"
 * ������:
 * + �������� ������� �� ��������������� ������������� �� �������� / ��������� ������ [� CRM].
 * �����! ����� ���������� PersonContext, ������� �� ������ ���� ������ ������� SetupPersonContextFieldsAction
 */
public class ClientAthenticationCompleteAction implements AthenticationCompleteAction
{

	public void execute(AuthenticationContext context) throws SecurityException, SecurityLogicException
	{
		GetPersonOffers getPersonOffers = new GetPersonOffers();
		getPersonOffers.execute();
	}
}
