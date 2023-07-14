package com.rssl.phizic.web.loanclaim.creditProduct.type;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.loanclaim.creditProduct.type.CreditProductType;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.ext.sbrf.dictionaries.loanclaim.creditProduct.type.EditCreditProductTypeOperation;
import com.rssl.phizic.web.common.EditActionBase;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.loanclaim.Constants;

import java.util.Map;

/**
 * @author Moshenko
 * @ created 26.12.2013
 * @ $Author$
 * @ $Revision$
 * Ёкшен редактировани€ типов кредитных продуктов.
 */
public class EditCreditProductTypeAction extends EditActionBase
{
	protected EditEntityOperation createEditOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		EditCreditProductTypeForm form = (EditCreditProductTypeForm) frm;
		EditCreditProductTypeOperation op = createOperation(EditCreditProductTypeOperation.class);
		Long id = form.getId();
		if (id != null)
			op.initialize(form.getId());
		else
			op.initializeNew();
		return op;
	}

	protected Form getEditForm(EditFormBase frm)
	{
		return EditCreditProductTypeForm.EDIT_FORM;
	}

	protected void updateEntity(Object entity, Map<String, Object> data) throws Exception
	{
		CreditProductType creditType = (CreditProductType) entity;
		creditType.setName((String)data.get(Constants.NAME));
		creditType.setCode((String) data.get(Constants.CODE));
	}

	protected void updateForm(EditFormBase frm, Object entity) throws Exception
	{
		CreditProductType creditType = (CreditProductType) entity;
		frm.setField(Constants.NAME, creditType.getName());
		frm.setField(Constants.CODE, creditType.getCode());
	}
}
