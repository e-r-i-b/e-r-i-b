package com.rssl.phizic.operations.depo;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.depo.DepoAccountOwnerFormImpl;
import com.rssl.phizic.business.depo.DepoAccountOwnerFormService;
import com.rssl.phizic.business.resources.external.DepoAccountLink;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.ViewEntityOperation;

/**
 * @author lukina
 * @ created 13.10.2010
 * @ $Author$
 * @ $Revision$
 */

public class GetDepoAccountOwnerFormOperation  extends OperationBase implements ViewEntityOperation
{
	private static DepoAccountOwnerFormService service =  new DepoAccountOwnerFormService();

	private DepoAccountOwnerFormImpl depoAccountOwnerForm;
	private DepoAccountLink depoAccountLink;                                                 
	public void initialize (Long linkId) throws BusinessException
	{
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		depoAccountLink = personData.getDepoAccount(linkId);
		depoAccountOwnerForm = service.getDepoAccountOwnerFormByAccNumberAndLoginId(depoAccountLink.getAccountNumber(), depoAccountLink.getLoginId());
	}

	public DepoAccountOwnerFormImpl getEntity()
	{
		return depoAccountOwnerForm;
	}

	public DepoAccountLink getDepoAccountLink()
	{
		return depoAccountLink;
	}

}
