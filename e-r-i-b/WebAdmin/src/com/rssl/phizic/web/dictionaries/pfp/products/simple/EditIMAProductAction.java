package com.rssl.phizic.web.dictionaries.pfp.products.simple;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.pfp.period.InvestmentPeriod;
import com.rssl.phizic.business.dictionaries.pfp.products.simple.IMAProduct;
import com.rssl.phizic.business.dictionaries.pfp.risk.Risk;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.dictionaries.pfp.products.simple.EditIMAProductOperation;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.dictionaries.pfp.products.EditProductFormBase;

import java.util.Map;

/**
 * @author akrenev
 * @ created 24.02.2012
 * @ $Author$
 * @ $Revision$
 */
public class EditIMAProductAction extends EditProductAction
{
	protected EditEntityOperation createEditOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		EditIMAProductOperation operation = createOperation(EditIMAProductOperation.class);
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
		return EditIMAProductForm.getEditForm(((EditProductFormBase) frm).getProductTypeParameters());
	}

	protected void updateEntity(Object entity, Map<String, Object> data)
	{
		super.updateEntity(entity, data);
		IMAProduct product = (IMAProduct) entity;
		product.setImaId((Long)data.get("imaId"));
		product.setImaAdditionalId((Long)data.get("imaAdditionalId"));
	}

	protected void updateForm(EditFormBase frm, Object entity)
	{
		super.updateForm(frm, entity);
		IMAProduct product = (IMAProduct) entity;
		frm.setField("imaId", product.getImaId());
		frm.setField("imaAdditionalId", product.getImaAdditionalId());
		if (product.getRisk() != null)
			frm.setField("riskId", product.getRisk().getId());
		if (product.getInvestmentPeriod() != null)
			frm.setField("periodId", product.getInvestmentPeriod().getId());
	}

	protected void updateFormAdditionalData(EditFormBase frm, EditEntityOperation operation) throws Exception
	{
		super.updateFormAdditionalData(frm, operation);
		EditIMAProductOperation op = (EditIMAProductOperation)operation;
		frm.setField("imaName", op.getIMAName());
		frm.setField("riskList", op.getAllRisks());
		frm.setField("periodList", op.getAllInvestmentPeriod());
	}
	@Override
	protected void updateOperationAdditionalData(EditEntityOperation editOperation, EditFormBase editForm, Map<String, Object> validationResult) throws Exception
	{
		super.updateOperationAdditionalData(editOperation, editForm, validationResult);
		EditIMAProductOperation operation = (EditIMAProductOperation) editOperation;
		IMAProduct product = operation.getEntity();
		Risk risk = operation.getRiskById((Long) validationResult.get("riskId"));
		product.setRisk(risk);
		InvestmentPeriod investmentPeriod = operation.getInvestmentPeriodById((Long) validationResult.get("periodId"));
		product.setInvestmentPeriod(investmentPeriod);
	}
}
