package com.rssl.phizic.web.dictionaries.pfp.products.loan;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.pfp.products.loan.LoanKindProduct;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.dictionaries.pfp.products.loan.EditLoanKindProductOperation;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.image.ImageEditActionBase;
import org.apache.struts.action.ActionMessages;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @author akrenev
 * @ created 29.03.2012
 * @ $Author$
 * @ $Revision$
 */
public class EditLoanKindProductAction extends ImageEditActionBase
{
	protected EditEntityOperation createEditOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		EditLoanKindProductOperation operation = createOperation(EditLoanKindProductOperation.class);
		operation.initialize(frm.getId());
		return operation;
	}

	protected Form getEditForm(EditFormBase frm)
	{
		return EditLoanKindProductForm.EDIT_FORM;
	}

	protected void updateEntity(Object entity, Map<String, Object> data) throws Exception
	{
		LoanKindProduct loanKindProduct = (LoanKindProduct) entity;
		loanKindProduct.setName((String) data.get("name"));
		loanKindProduct.setFromAmount((BigDecimal) data.get("fromAmount"));
		loanKindProduct.setToAmount((BigDecimal) data.get("toAmount"));
		loanKindProduct.setFromPeriod((Long) data.get("fromPeriod"));
		loanKindProduct.setToPeriod((Long) data.get("toPeriod"));
		loanKindProduct.setDefaultPeriod((Long) data.get("defaultPeriod"));
		loanKindProduct.setFromRate((BigDecimal) data.get("fromRate"));
		loanKindProduct.setToRate((BigDecimal) data.get("toRate"));
		loanKindProduct.setDefaultRate((BigDecimal) data.get("defaultRate"));

	}

	protected void updateForm(EditFormBase frm, Object entity) throws Exception
	{
		LoanKindProduct loanKindProduct = (LoanKindProduct) entity;
		frm.setField("name", loanKindProduct.getName());
		frm.setField("fromAmount",loanKindProduct.getFromAmount());
		frm.setField("toAmount",loanKindProduct.getToAmount());
		frm.setField("fromPeriod",loanKindProduct.getFromPeriod());
		frm.setField("toPeriod",loanKindProduct.getToPeriod());
		frm.setField("defaultPeriod",loanKindProduct.getDefaultPeriod());
		frm.setField("fromRate",loanKindProduct.getFromRate());
		frm.setField("toRate",loanKindProduct.getToRate());
		frm.setField("toRate",loanKindProduct.getToRate());
		frm.setField("defaultRate",loanKindProduct.getDefaultRate());

	}

	protected ActionMessages validateAdditionalFormData(EditFormBase frm, EditEntityOperation operation) throws Exception
	{
		return validateImageFormData(frm, operation);
	}

	protected void updateFormAdditionalData(EditFormBase frm, EditEntityOperation operation) throws Exception
	{
		updateFormImageData(frm, operation);
	}

	protected void updateOperationAdditionalData(EditEntityOperation editOperation, EditFormBase editForm, Map<String, Object> validationResult) throws Exception
	{
		updateOperationImageData(editOperation, editForm, validationResult);
	}
}
