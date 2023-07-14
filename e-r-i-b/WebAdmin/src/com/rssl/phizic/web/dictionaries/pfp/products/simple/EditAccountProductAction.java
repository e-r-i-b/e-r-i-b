package com.rssl.phizic.web.dictionaries.pfp.products.simple;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.pfp.products.simple.AccountProduct;
import com.rssl.phizic.business.dictionaries.pfp.products.simple.AdvisableSum;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.dictionaries.pfp.products.simple.EditAccountProductOperation;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.dictionaries.pfp.products.EditProductFormBase;

import java.util.Map;

/**
 * @author akrenev
 * @ created 22.02.2012
 * @ $Author$
 * @ $Revision$
 */
public class EditAccountProductAction extends EditProductAction
{
	protected EditEntityOperation createEditOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		EditAccountProductOperation operation = createOperation(EditAccountProductOperation.class);
		EditProductFormBase form = (EditProductFormBase) frm;
		Long id = form.getId();
		if (id == null)
			operation.initialize();
		else
			operation.initialize(id);
		form.setProductTypeParameters(operation.getProductTypeParameters());
		return operation;
	}

	protected Form getEditForm(EditFormBase frm)
	{
		return EditAccountProductForm.getEditForm(((EditProductFormBase) frm).getProductTypeParameters());
	}

	protected void updateEntity(Object entity, Map<String, Object> data)
	{
		super.updateEntity(entity, data);
		AccountProduct product = (AccountProduct) entity;
		product.setAdvisableSum(AdvisableSum.valueOf((String)data.get("advisableSum")));
		product.setAccountId((Long)data.get("accountId"));
	}

	protected void updateForm(EditFormBase frm, Object entity)
	{
		super.updateForm(frm, entity);
		AccountProduct product = (AccountProduct) entity;
		frm.setField("advisableSum", product.getAdvisableSum());
		frm.setField("accountId", product.getAccountId());
	}

	protected void updateFormAdditionalData(EditFormBase frm, EditEntityOperation operation) throws Exception
	{
		super.updateFormAdditionalData(frm, operation);
		EditAccountProductOperation op = (EditAccountProductOperation)operation;
		frm.setField("accountName", op.getDepositName());
	}
}
