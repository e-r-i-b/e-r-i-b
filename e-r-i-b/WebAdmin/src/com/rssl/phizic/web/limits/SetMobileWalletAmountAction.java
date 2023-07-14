package com.rssl.phizic.web.limits;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.limits.Limit;
import com.rssl.phizic.business.util.MoneyUtil;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.limits.EditMobileWalletLimitOperation;
import com.rssl.phizic.web.common.EditActionBase;
import com.rssl.phizic.web.common.EditFormBase;
import org.apache.struts.action.ActionForward;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @author osminin
 * @ created 30.08.2012
 * @ $Author$
 * @ $Revision$
 */
public class SetMobileWalletAmountAction extends EditActionBase
{
	protected EditEntityOperation createEditOperation(EditFormBase form) throws BusinessException, BusinessLogicException
	{
		SetMobileWalletAmountForm frm = (SetMobileWalletAmountForm) form;
		EditMobileWalletLimitOperation operation = createOperation("EditMobileWalletLimitOperation", "MobileWalletManagment");
		operation.initialize(frm.getDepartmentId());

		return operation;
	}

	protected Form getEditForm(EditFormBase frm)
	{
		return SetMobileWalletAmountForm.EDIT_MOBILE_WALLET_FORM;
	}

	protected void updateEntity(Object entity, Map<String, Object> data) throws Exception
	{
		Limit limit = (Limit) entity;
		limit.setAmount(new Money((BigDecimal) data.get("amount"), MoneyUtil.getNationalCurrency()));
	}

	protected void updateForm(EditFormBase frm, Object entity) throws Exception
	{
		Limit limit = (Limit) entity;
		frm.setField("amount", limit.getAmount().getDecimal());
	}

	protected ActionForward createSaveActionForward(EditEntityOperation operation, EditFormBase frm) throws BusinessException
	{
		return getCurrentMapping().findForward(FORWARD_START);
	}
}
