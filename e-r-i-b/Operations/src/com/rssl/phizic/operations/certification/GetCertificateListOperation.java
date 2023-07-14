package com.rssl.phizic.operations.certification;

import com.rssl.phizic.business.operations.restrictions.UserRestriction;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.context.PersonContext;

/**
 * @author Omeliyanchuk
 * @ created 28.03.2007
 * @ $Author$
 * @ $Revision$
 */

public class GetCertificateListOperation extends OperationBase<UserRestriction> implements ListEntitiesOperation<UserRestriction>
{
	Login userLogin;

	public Login getUserLogin()
	{
		return PersonContext.getPersonDataProvider().getPersonData().getPerson().getLogin();
	}

	public void setUserLogin(Login userLogin)
	{
		this.userLogin = userLogin;
	}
}
