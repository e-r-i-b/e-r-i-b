package com.rssl.phizic.operations.depo;

import com.rssl.phizic.business.resources.external.DepoAccountLink;
import com.rssl.phizic.business.resources.external.StoredDepoAccount;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.context.PersonDataProvider;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.utils.MockHelper;

/**
 * @author lukina
 * @ created 22.09.2010
 * @ $Author$
 * @ $Revision$
 */

public class GetDepoAccountLinkOperation  extends OperationBase
{
	private DepoAccountLink depoAccountLink;

	public void initialize(Long depoAccountLinkId) throws BusinessException
	{
		PersonDataProvider provider = PersonContext.getPersonDataProvider();
		depoAccountLink = provider.getPersonData().getDepoAccount(depoAccountLinkId);

	}

	public void initialize(String accountNumber) throws BusinessException
	{
		PersonDataProvider provider = PersonContext.getPersonDataProvider();
		depoAccountLink = provider.getPersonData().findDepo(accountNumber);
	}
	
	public DepoAccountLink getDepoAccountLink()
	{
		return depoAccountLink;
	}

	public boolean isUseStoredResource()
	{
		return (depoAccountLink != null && depoAccountLink.getDepoAccount() instanceof StoredDepoAccount);
	}

	/**
	 * @return была ли ошибка во время получения данных
	 */
	public boolean isBackError()
	{
		return (depoAccountLink != null && MockHelper.isMockObject(depoAccountLink.getDepoAccount()));
	}
}
