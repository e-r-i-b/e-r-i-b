package com.rssl.phizic.web.dictionaries;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.dictionaries.GetBankListOperation;
import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.web.common.dictionaries.ShowBankListAction;

/**
 * @author komarov
 * @ created 03.02.2014
 * @ $Author$
 * @ $Revision$
 */
public class ShowMultiBlockBankListAction extends ShowBankListAction
{
	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		GetBankListOperation operation = createOperation(GetBankListOperation.class);
		operation.considerMultiBlock();
		return operation;
	}
}
