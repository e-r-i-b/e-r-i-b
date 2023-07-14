package com.rssl.phizic.web.common.client.finances.targets;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.finances.targets.AccountTarget;
import com.rssl.phizic.business.finances.targets.TargetType;
import com.rssl.phizic.business.util.MoneyUtil;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.finances.targets.EditAccountTargetOperation;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.web.common.EditActionBase;
import com.rssl.phizic.web.common.EditFormBase;
import org.apache.struts.action.ActionForward;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * @author lepihina
 * @ created 10.04.2013
 * @ $Author$
 * @ $Revision$
 */
public class ShowTargetNoAccountAction extends EditActionBase
{
	protected EditEntityOperation createEditOperation(EditFormBase form) throws BusinessException
	{
		ShowTargetNoAccountForm frm = (ShowTargetNoAccountForm) form;
		EditAccountTargetOperation operation = createOperation(EditAccountTargetOperation.class);
		operation.initializeById(frm.getId());

		return operation;
	}

	protected Form getEditForm(EditFormBase frm)
	{
		return ShowTargetNoAccountForm.EDIT_FORM;
	}

	protected ActionForward createSaveActionForward(EditEntityOperation operation, EditFormBase frm) throws BusinessException
	{
		return getCurrentMapping().findForward(FORWARD_START);
	}

	protected void updateEntity(Object entity, Map<String, Object> data) throws Exception
	{
		Currency nationalCurrency = MoneyUtil.getNationalCurrency();
		AccountTarget target = (AccountTarget)entity;
		//«начение имени может измен€тьс€ только дл€ типа цели "Other"
		if(TargetType.OTHER == target.getDictionaryTarget())
			target.setName((String) data.get("targetName"));

		target.setNameComment((String) data.get("targetNameComment"));
		target.setAmount(new Money((BigDecimal) data.get("targetAmount"), nationalCurrency));
		target.setPlannedDate(DateHelper.toCalendar((Date) data.get("targetPlanedDate")));
	}

	protected void updateForm(EditFormBase form, Object entity) throws Exception
	{
		ShowTargetNoAccountForm frm = (ShowTargetNoAccountForm) form;
		AccountTarget target = (AccountTarget) entity;
		frm.setField("dictionaryTarget", target.getDictionaryTarget());
		frm.setField("targetName", target.getName());
		frm.setField("targetNameComment", target.getNameComment());
		form.setField("targetAmount", target.getAmount().getDecimal());
		frm.setField("targetPlanedDate", DateHelper.toDate(target.getPlannedDate()));
	}

	protected void updateFormAdditionalData(EditFormBase form, EditEntityOperation operation) throws Exception
	{
		ShowTargetNoAccountForm frm = (ShowTargetNoAccountForm) form;
		EditAccountTargetOperation op = (EditAccountTargetOperation) operation;

		frm.setTarget(op.getEntity());
	}
}
