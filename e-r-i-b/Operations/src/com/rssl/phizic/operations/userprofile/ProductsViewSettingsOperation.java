package com.rssl.phizic.operations.userprofile;

import com.rssl.phizic.security.ConfirmableObject;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.context.PersonDataProvider;
import com.rssl.phizic.operations.ConfirmableOperationBase;
import com.rssl.phizic.security.SecurityLogicException;

/**
 * @author lukina
 * @ created 22.09.2011
 * @ $Author$
 * @ $Revision$
 */

public class ProductsViewSettingsOperation extends ConfirmableOperationBase
{
	private static String PAGE_URL = "/private/userprofile/accountsSystemView";
	private Login login;
	private ConfirmableObject confirmableObject;
	
	public void initialize() throws BusinessException
	{
		PersonDataProvider dataProvider = PersonContext.getPersonDataProvider();
		PersonData personData = dataProvider.getPersonData();
		login = personData.getPerson().getLogin();
	}

	/**
	 * @return Логин клиента
	 */
	public Login getLogin()
	{
		return login;
	}

	protected void saveConfirm() throws BusinessException, BusinessLogicException, SecurityLogicException
	{}

	public ConfirmableObject getConfirmableObject()
	{
		if (confirmableObject == null)
			confirmableObject = new ConfirmViewPage(PAGE_URL);
		return confirmableObject;  
	}
}
