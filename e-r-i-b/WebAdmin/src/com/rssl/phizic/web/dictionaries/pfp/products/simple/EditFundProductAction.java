package com.rssl.phizic.web.dictionaries.pfp.products.simple;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.pfp.period.InvestmentPeriod;
import com.rssl.phizic.business.dictionaries.pfp.products.simple.FundProduct;
import com.rssl.phizic.business.dictionaries.pfp.risk.Risk;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.dictionaries.pfp.products.simple.EditFundProductOperation;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.dictionaries.pfp.products.EditProductFormBase;

import java.util.Map;

/**
 * @author akrenev
 * @ created 22.02.2012
 * @ $Author$
 * @ $Revision$
 */
public class EditFundProductAction extends EditProductAction
{
	protected EditEntityOperation createEditOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		EditFundProductOperation operation = createOperation(EditFundProductOperation.class);
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
		return EditFundProductForm.getEditForm(((EditProductFormBase) frm).getProductTypeParameters());
	}

	@Override
	protected void updateForm(EditFormBase frm, Object entity)
	{
		super.updateForm(frm, entity);
		FundProduct product = (FundProduct) entity;
		if (product.getRisk() != null)
			frm.setField("riskId", product.getRisk().getId());
		if (product.getInvestmentPeriod() != null)
			frm.setField("periodId", product.getInvestmentPeriod().getId());
	}

	@Override
	protected void updateFormAdditionalData(EditFormBase frm, EditEntityOperation editOperation) throws Exception
	{
		super.updateFormAdditionalData(frm, editOperation);
		EditFundProductOperation operation = (EditFundProductOperation)editOperation;
		frm.setField("riskList", operation.getAllRisks());
		frm.setField("periodList", operation.getAllInvestmentPeriod());
	}

	@Override
	protected void updateOperationAdditionalData(EditEntityOperation editOperation, EditFormBase editForm, Map<String, Object> validationResult) throws Exception
	{
		super.updateOperationAdditionalData(editOperation, editForm, validationResult);
		EditFundProductOperation operation = (EditFundProductOperation) editOperation;
		FundProduct product = operation.getEntity();
		Risk risk = operation.getRiskById((Long) validationResult.get("riskId"));
		product.setRisk(risk);
		InvestmentPeriod investmentPeriod = operation.getInvestmentPeriodById((Long) validationResult.get("periodId"));
		product.setInvestmentPeriod(investmentPeriod);
	}
}
