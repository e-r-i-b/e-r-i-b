package com.rssl.phizic.web.dictionaries.pfp.products.complex;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.pfp.products.complex.ComplexIMAInvestmentProduct;
import com.rssl.phizic.business.dictionaries.pfp.products.simple.IMAProduct;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.dictionaries.pfp.products.complex.EditComplexIMAInvestmentProductOperation;
import com.rssl.phizic.operations.dictionaries.pfp.products.complex.EditComplexInvestmentProductOperationBase;
import com.rssl.phizic.web.common.EditFormBase;

import java.util.List;
import java.util.Map;

/**
 * @author akrenev
 * @ created 01.03.2012
 * @ $Author$
 * @ $Revision$
 */
public class EditComplexIMAInvestmentProductAction extends EditComplexInvestmentProductAction
{
	protected EditEntityOperation createEditOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		EditComplexInvestmentProductOperationBase operation = createOperation(EditComplexIMAInvestmentProductOperation.class);
		EditComplexIMAInvestmentProductForm form = (EditComplexIMAInvestmentProductForm) frm;
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
		EditComplexIMAInvestmentProductForm form = (EditComplexIMAInvestmentProductForm) frm;
		return form.createEditForm(form.getProductTypeParameters());
	}

	protected void updateOperationAdditionalData(EditEntityOperation editOperation, EditFormBase editForm, Map<String, Object> validationResult) throws Exception
	{
		super.updateOperationAdditionalData(editOperation, editForm, validationResult);
		EditComplexIMAInvestmentProductOperation operation = (EditComplexIMAInvestmentProductOperation) editOperation;
		EditComplexIMAInvestmentProductForm form = (EditComplexIMAInvestmentProductForm) editForm;
		operation.setIMAProducts(form.getImaProductIds());
	}

	protected void updateForm(EditFormBase frm, Object entity)
	{
		super.updateForm(frm, entity);
		EditComplexIMAInvestmentProductForm form = (EditComplexIMAInvestmentProductForm) frm;
		ComplexIMAInvestmentProduct product = (ComplexIMAInvestmentProduct) entity;
		List<IMAProduct> imaProducts = product.getImaProducts();
		Long[] ids = new Long[imaProducts.size()];
		int i = 0;
		for (IMAProduct imaProduct: imaProducts)
		{
			Long id = imaProduct.getId();
			ids[i++] = id;
			form.setField("imaProductNameFor" + id, imaProduct.getName());
		}
		form.setImaProductIds(ids);
	}
}
