package com.rssl.phizic.web.common.socialApi.deposits;

import com.rssl.phizic.web.common.client.ext.sbrf.deposits.ListDepositProductsExtendedAction;
import com.rssl.phizic.operations.deposits.GetDepositProductsListOperation;
import com.rssl.phizic.business.BusinessException;

import javax.xml.transform.Source;

/**
 * @author Pankin
 * @ created 12.01.2012
 * @ $Author$
 * @ $Revision$
 */

public class ListDepositProductsMobileAction extends ListDepositProductsExtendedAction
{
	protected Source getTemplateSource(GetDepositProductsListOperation op) throws BusinessException
	{
		return op.getMobileTemplateSource();
	}

	protected boolean isUseCasNsiDictionaries()
	{
		return false;
	}
}
