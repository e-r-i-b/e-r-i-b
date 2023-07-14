package com.rssl.phizic.web.advertising;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.advertising.AdvertisingBlock;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.operations.advertising.EditAdvertisingBlockOperation;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.common.ViewActionBase;

/**
 * @author lukina
 * @ created 13.01.2012
 * @ $Author$
 * @ $Revision$
 */

public class ViewAdvertisingBlockAction extends ViewActionBase
{
	protected ViewEntityOperation createViewEntityOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		EditAdvertisingBlockOperation editOperation = createOperation(EditAdvertisingBlockOperation.class, "AdvertisingBlockManagment");
		Long id = frm.getId();
		if (id != null && id != 0)
			editOperation.initialize(id);
		return editOperation;
	}

	protected void updateFormData(ViewEntityOperation operation, EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		ViewAdvertisingBlockForm form= (ViewAdvertisingBlockForm)frm;
		form.setAdvertisingBlock((AdvertisingBlock)operation.getEntity());
	}
}

