package com.rssl.phizic.web.dictionaries.pfp.products.simple.pension;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.pfp.products.simple.pension.PensionProduct;
import com.rssl.phizic.business.dictionaries.pfp.products.simple.pension.fund.PensionFund;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.dictionaries.pfp.products.simple.pension.EditPensionProductOperation;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.dictionaries.pfp.products.EditProductActionBase;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @author akrenev
 * @ created 18.07.2013
 * @ $Author$
 * @ $Revision$
 *
 * Ёкшен редактировани€ пенсионного продукта
 */

public class EditPensionProductAction extends EditProductActionBase
{
	protected EditEntityOperation createEditOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		EditPensionProductOperation operation = createOperation(EditPensionProductOperation.class);
		Long id = frm.getId();
		if (id == null)
			operation.initialize();
		else
			operation.initialize(id);
		return operation;
	}

	protected Form getEditForm(EditFormBase frm)
	{
		return EditPensionProductForm.EDIT_FORM;
	}

	@Override
	protected void updateForm(EditFormBase form, Object entity)
	{
		super.updateForm(form, entity);
		PensionProduct pensionProduct = (PensionProduct) entity;
		form.setField(EditPensionProductForm.ENTRY_FEE_FIELD_NAME, pensionProduct.getEntryFee());
		form.setField(EditPensionProductForm.QUARTERLY_FEE_FIELD_NAME, pensionProduct.getQuarterlyFee());
		form.setField(EditPensionProductForm.MIN_PERIOD_FIELD_NAME, pensionProduct.getMinPeriod());
		form.setField(EditPensionProductForm.MAX_PERIOD_FIELD_NAME, pensionProduct.getMaxPeriod());
		form.setField(EditPensionProductForm.DEFAULT_PERIOD_FIELD_NAME, pensionProduct.getDefaultPeriod());
		if (pensionProduct.getPensionFund() != null)
			form.setField(EditPensionProductForm.PENSION_FUND_ID_FIELD_NAME, pensionProduct.getPensionFund().getId());
	}

	@Override
	protected void updateFormAdditionalData(EditFormBase frm, EditEntityOperation editOperation) throws Exception
	{
		super.updateFormAdditionalData(frm, editOperation);
		EditPensionProductOperation operation = (EditPensionProductOperation)editOperation;
		frm.setField("pensionFundList", operation.getAllPensionFunds());
	}

	@Override
	protected void updateEntity(Object entity, Map<String, Object> data)
	{
		super.updateEntity(entity, data);
		PensionProduct pensionProduct = (PensionProduct) entity;
		pensionProduct.setEntryFee((BigDecimal) data.get(EditPensionProductForm.ENTRY_FEE_FIELD_NAME));
		pensionProduct.setQuarterlyFee((BigDecimal) data.get(EditPensionProductForm.QUARTERLY_FEE_FIELD_NAME));
		pensionProduct.setMinPeriod((Long) data.get(EditPensionProductForm.MIN_PERIOD_FIELD_NAME));
		pensionProduct.setMaxPeriod((Long) data.get(EditPensionProductForm.MAX_PERIOD_FIELD_NAME));
		pensionProduct.setDefaultPeriod((Long) data.get(EditPensionProductForm.DEFAULT_PERIOD_FIELD_NAME));
	}

	@Override
	protected void updateOperationAdditionalData(EditEntityOperation editOperation, EditFormBase editForm, Map<String, Object> validationResult) throws Exception
	{
		super.updateOperationAdditionalData(editOperation, editForm, validationResult);
		EditPensionProductOperation operation = (EditPensionProductOperation) editOperation;
		PensionProduct product = operation.getEntity();
		PensionFund fund = operation.getPensionFundById((Long) validationResult.get(EditPensionProductForm.PENSION_FUND_ID_FIELD_NAME));
		product.setPensionFund(fund);
	}

}
