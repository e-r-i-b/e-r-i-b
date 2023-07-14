package com.rssl.phizic.web.dictionaries.pfp.products.simple.trustManaging;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.pfp.products.simple.TrustManagingProduct;
import com.rssl.phizic.business.dictionaries.pfp.risk.Risk;
import com.rssl.phizic.business.dictionaries.pfp.period.InvestmentPeriod;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.dictionaries.pfp.products.simple.EditTrustManagingProductOperation;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.dictionaries.pfp.products.EditProductFormBase;
import com.rssl.phizic.web.dictionaries.pfp.products.simple.EditSimpleProductActionBase;

import java.util.Map;

/**
 * @author akrenev
 * @ created 16.07.2013
 * @ $Author$
 * @ $Revision$
 */

public class EditTrustManagingProductAction extends EditSimpleProductActionBase
{
	protected EditEntityOperation createEditOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		EditTrustManagingProductOperation operation = createOperation(EditTrustManagingProductOperation.class);
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
		return EditTrustManagingProductForm.getEditForm(((EditProductFormBase) frm).getProductTypeParameters());
	}

	@Override
	protected void updateForm(EditFormBase frm, Object entity)
	{
		super.updateForm(frm, entity);
		TrustManagingProduct product = (TrustManagingProduct) entity;
		if (product.getRisk() != null)
			frm.setField("riskId", product.getRisk().getId());
		if (product.getInvestmentPeriod() != null)
			frm.setField("periodId", product.getInvestmentPeriod().getId());
	}

	@Override
	protected void updateFormAdditionalData(EditFormBase frm, EditEntityOperation editOperation) throws Exception
	{
		super.updateFormAdditionalData(frm, editOperation);
		EditTrustManagingProductOperation operation = (EditTrustManagingProductOperation)editOperation;
		frm.setField("riskList", operation.getAllRisks());
		frm.setField("periodList", operation.getAllInvestmentPeriod());
	}

	@Override
	protected void updateOperationAdditionalData(EditEntityOperation editOperation, EditFormBase editForm, Map<String, Object> validationResult) throws Exception
	{
		super.updateOperationAdditionalData(editOperation, editForm, validationResult);
		EditTrustManagingProductOperation operation = (EditTrustManagingProductOperation) editOperation;
		TrustManagingProduct product = operation.getEntity();
		Risk risk = operation.getRiskById((Long) validationResult.get("riskId"));
		product.setRisk(risk);
		InvestmentPeriod investmentPeriod = operation.getInvestmentPeriodById((Long) validationResult.get("periodId"));
		product.setInvestmentPeriod(investmentPeriod);
	}
}
