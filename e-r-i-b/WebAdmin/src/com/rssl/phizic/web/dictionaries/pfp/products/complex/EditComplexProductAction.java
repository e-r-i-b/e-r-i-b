package com.rssl.phizic.web.dictionaries.pfp.products.complex;

import com.rssl.phizic.business.dictionaries.pfp.products.simple.AccountProduct;
import com.rssl.phizic.business.dictionaries.pfp.products.complex.ComplexProductBase;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.dictionaries.pfp.products.complex.EditComplexProductOperation;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.dictionaries.pfp.products.EditProductActionBase;

import java.util.Map;

/**
 * @author akrenev
 * @ created 01.03.2012
 * @ $Author$
 * @ $Revision$
 */
public abstract class EditComplexProductAction extends EditProductActionBase
{
	protected void updateOperationAdditionalData(EditEntityOperation editOperation, EditFormBase editForm, Map<String, Object> validationResult) throws Exception
	{
		super.updateOperationAdditionalData(editOperation, editForm, validationResult);
		EditComplexProductOperation operation = (EditComplexProductOperation) editOperation;
		operation.setAccount((Long) validationResult.get("accountId"));
	}

	protected void updateForm(EditFormBase frm, Object entity)
	{
		super.updateForm(frm, entity);
		ComplexProductBase product = (ComplexProductBase) entity;
		AccountProduct account = product.getAccount();
		if (account != null)
		{
			frm.setField("accountId", account.getId());
			frm.setField("accountName", account.getName());
		}
	}
}
