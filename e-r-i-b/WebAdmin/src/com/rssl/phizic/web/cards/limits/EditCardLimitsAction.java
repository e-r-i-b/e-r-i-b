package com.rssl.phizic.web.cards.limits;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.cardAmountStep.CardAmountStep;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.cardAmountStep.CardAmountStepOperationEdit;
import com.rssl.phizic.web.common.EditActionBase;
import com.rssl.phizic.web.common.EditFormBase;

import java.math.BigDecimal;
import java.util.Map;

/**
 * User: Moshenko
 * Date: 07.06.2011
 * Time: 12:37:14
 * Экшен редактирования и создания лимитов по карточным предложениям
 */
public class EditCardLimitsAction extends EditActionBase
{

    protected EditEntityOperation createEditOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
    {
        EditCardLimitsForm form = (EditCardLimitsForm) frm;
        CardAmountStepOperationEdit operation = createOperation(CardAmountStepOperationEdit.class);
	    if(frm.getId() != null && frm.getId() != 0)
			operation.initialize(form.getId());
	    else
	        operation.initializeNew();
        return operation;
    }


    protected Form getEditForm(EditFormBase frm)
    {
        return EditCardLimitsForm.CARDLIMIT_FORM;
    }

    protected void updateEntity(Object entity, Map<String, Object> data) throws Exception
    {
    }

	protected void updateOperationAdditionalData(EditEntityOperation editOperation, EditFormBase editForm, Map<String, Object> validationResult) throws Exception
	{
	    CardAmountStepOperationEdit operation = (CardAmountStepOperationEdit) editOperation;
		BigDecimal amount = (BigDecimal) validationResult.get("decimal");
		String moneyCurrency = (String) validationResult.get("currency");
		operation.setMoney(moneyCurrency, amount);
	}

    protected void updateForm(EditFormBase frm, Object entity) throws Exception
    {
	    CardAmountStep cardAmountStep = (CardAmountStep) entity;
	    if(cardAmountStep.getValue() != null)
	    {
			frm.setField("decimal",cardAmountStep.getValue().getDecimal().longValue());
	        frm.setField("currency",cardAmountStep.getValue().getCurrency().getCode());
	    }	    		
    }
}
