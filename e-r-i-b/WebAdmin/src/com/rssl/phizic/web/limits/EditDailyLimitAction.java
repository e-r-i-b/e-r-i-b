package com.rssl.phizic.web.limits;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.limits.*;
import com.rssl.phizic.business.util.MoneyUtil;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.security.SecurityType;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.web.common.EditFormBase;
import org.apache.struts.action.ActionForward;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @author: vagin
 * @ created: 26.01.2012
 * @ $Author$
 * @ $Revision$
 * Экшен редактирования суточного кумулятивного лимита
 */
public class EditDailyLimitAction extends EditLimitAction
{
	@Override
	protected Form getEditForm(EditFormBase frm)
	{
		return EditDailylLimitsForm.EDIT_FORM;
	}

	@Override
	protected void updateEntity(Object entity, Map<String, Object> data) throws Exception
	{
		Limit limit = (Limit) entity;
		Status status = limit.getStatus();

		limit.setAmount(new Money((BigDecimal) data.get("amount"), MoneyUtil.getNationalCurrency()));

		if (status == null)
		{
			//тип лимита обновляем только, если создаем новый
			LimitType limitType = LimitType.valueOf((String) data.get("limitType"));
			limit.setType(limitType);
			limit.setRestrictionType(getRestrictionType(limitType));
			limit.setOperationType(getOperationType(limitType));
			limit.setStartDate(getStartDate(data));
		}

		if (status == Status.DRAFT)
		{
			//дату начала действия обновляем только, если создаем новый или редактируем введенный лимит
			limit.setStartDate(getStartDate(data));
		}
	}

	@Override
	protected void updateOperationAdditionalData(EditEntityOperation editOperation, EditFormBase editForm, Map<String, Object> validationResult) throws Exception
	{
		//суточным лимитам не нужно обновлять группу риска т.к. у их ее нет.
	}

	@Override
	protected ActionForward createSaveActionForward(EditEntityOperation operation, EditFormBase frm) throws BusinessException
	{
		EditLimitForm form = (EditLimitForm) frm;
		return new ActionForward(getCurrentMapping().findForward(FORWARD_SUCCESS).getPath()+ "?departmentId=" + form.getDepartmentId() + "&channel=" + getChannelType(), true);
	}

	@Override
	protected void updateFormAdditionalData(EditFormBase frm, EditEntityOperation operation) throws Exception
	{
		updateFormAdditionalDataBase(frm,operation);
	}

	@Override
	protected LimitType getLimitType(EditLimitForm frm)
	{
		return LimitType.OBSTRUCTION_FOR_AMOUNT_OPERATION;
	}

	@Override
	protected boolean isChangeLimitData(Limit limit, Map<String, Object> formData) throws BusinessException
	{
		return !limit.getStartDate().equals(getStartDate(formData)) || !limit.getAmount().equals(new Money((BigDecimal) formData.get("amount"), MoneyUtil.getNationalCurrency()));
	}

	@Override
	protected SecurityType getLimitSecurityType(EditLimitForm frm)
	{
		//суточный лимит не имеет уровня безопасности
		return null;
	}

	private OperationType getOperationType(LimitType limitType)
	{
		if (LimitType.IMSI == limitType)
		{
			return OperationType.READ_SIM;
		}

		if (LimitType.isObstruction(limitType))
		{
			return OperationType.IMPOSSIBLE_PERFORM_OPERATION;
		}

		throw new IllegalArgumentException("некорректный тип лимита" + limitType);
	}

	private RestrictionType getRestrictionType(LimitType limitType)
	{
		if (LimitType.EXTERNAL_PHONE == limitType)
		{
			return RestrictionType.PHONE_ALL_AMOUNT_IN_DAY;
		}

		if (LimitType.EXTERNAL_CARD == limitType)
		{
			return RestrictionType.CARD_ALL_AMOUNT_IN_DAY;
		}
		return RestrictionType.AMOUNT_IN_DAY;
	}
}