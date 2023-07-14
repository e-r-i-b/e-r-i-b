package com.rssl.phizic.web.dictionaries.pfp.products.complex;

import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.dictionaries.pfp.products.complex.EditComplexInvestmentProductOperationBase;
import com.rssl.phizic.operations.dictionaries.pfp.products.complex.EditComplexFundInvestmentProductOperation;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.common.forms.Form;

/**
 * @author akrenev
 * @ created 11.04.2012
 * @ $Author$
 * @ $Revision$
 */
public class EditComplexFundInvestmentProductAction extends EditComplexInvestmentProductAction
{
	protected EditEntityOperation createEditOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		EditComplexInvestmentProductOperationBase operation = createOperation(EditComplexFundInvestmentProductOperation.class);
		EditComplexInvestmentProductFormBase form = (EditComplexInvestmentProductFormBase) frm;
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
		EditComplexInvestmentProductFormBase form = (EditComplexInvestmentProductFormBase) frm;
		return form.createEditForm(form.getProductTypeParameters());
	}
}
