package com.rssl.phizic.web.limits;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.limits.*;
import com.rssl.phizic.business.util.MoneyUtil;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.limits.ChannelType;
import com.rssl.phizic.common.types.security.SecurityType;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.limits.EditLimitOperation;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.web.common.EditFormBase;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * @author sergunin
 * @ created: 26.01.2015
 * @ $Author$
 * @ $Revision$
 * Экшен редактирования единого суточного кумулятивного лимита
 */
public class EditOverallLimitAction extends EditDailyLimitAction
{
	@Override
	protected Form getEditForm(EditFormBase frm)
	{
		return EditOverallLimitsForm.EDIT_FORM;
	}

	@Override
	protected void updateEntity(Object entity, Map<String, Object> data) throws Exception
	{   /** тут точно надо всё сделать по своему **/
		Limit limit = (Limit) entity;
		Status status = limit.getStatus();

		limit.setAmount(new Money((BigDecimal) data.get("amount"), MoneyUtil.getNationalCurrency()));

		if (status == null)
		{
			limit.setType(getLimitType());
			limit.setRestrictionType(getRestrictionType(getLimitType()));
			limit.setOperationType(getOperationType(getLimitType()));
			limit.setStartDate(getStartDate(data));
            limit.setEndDate(getEndDate(data));
		}

		if (status == Status.DRAFT || limit.getType() == LimitType.OVERALL_AMOUNT_PER_DAY)
		{
			//дату начала действия обновляем только, если создаем новый или редактируем введенный или ЕСК лимит
			limit.setStartDate(getStartDate(data));
			limit.setEndDate(getEndDate(data));
		}
	}

	@Override
	protected ActionForward createSaveActionForward(EditEntityOperation operation, EditFormBase frm) throws BusinessException
	{
		return new ActionForward(getCurrentMapping().findForward(FORWARD_SUCCESS).getPath(), true);
	}

	@Override
	protected void updateFormAdditionalData(EditFormBase frm, EditEntityOperation operation) throws Exception
	{
		updateFormAdditionalDataBase(frm,operation);
	}

	@Override
	protected LimitType getLimitType(EditLimitForm frm)
	{
		return LimitType.OVERALL_AMOUNT_PER_DAY;
	}

	protected LimitType getLimitType()
	{
		return LimitType.OVERALL_AMOUNT_PER_DAY;
	}

	@Override
	protected boolean isChangeLimitData(Limit limit, Map<String, Object> formData) throws BusinessException
	{
		return !limit.getStartDate().equals(getStartDate(formData)) || !limit.getEndDate().equals(getEndDate(formData)) || !limit.getAmount().equals(new Money((BigDecimal) formData.get("amount"), MoneyUtil.getNationalCurrency()));
	}

	@Override
	protected SecurityType getLimitSecurityType(EditLimitForm frm)
	{
		//суточный лимит не имеет уровня безопасности
		return null;
	}

	protected OperationType getOperationType(LimitType limitType)
	{
        return OperationType.IMPOSSIBLE_PERFORM_OPERATION;
	}

	private RestrictionType getRestrictionType(LimitType limitType)
	{
		return RestrictionType.AMOUNT_IN_DAY;
	}

    protected void initOperation(EditLimitForm frm, EditLimitOperation operation) throws BusinessException
    {
        Long limitId = frm.getId();

        if ((limitId != null) && (limitId != 0))
            operation.initialize(limitId, getLimitChannelType(), false);
        else
            operation.initializeNew(getLimitType(frm), getLimitChannelType());
    }

    protected ChannelType getLimitChannelType()
    {
        return ChannelType.ALL;
    }

    protected Calendar getStartDate(Map<String, Object> data)
    {
        Calendar date = DateHelper.toCalendar((Date) data.get("startDate"));
        return date;
    }

    protected Calendar getEndDate(Map<String, Object> data)
    {
        Calendar date = DateHelper.toCalendar((Date) data.get("endDate"));
        return date;
    }

    protected void updateForm(EditFormBase frm, Object entity) throws Exception
    {
        Limit limit = (Limit) entity;

        frm.setField("creationDate",    limit.getCreationDate().getTime());

        if (limit.getStartDate() != null)
        {
            frm.setField("startDate",   limit.getStartDate().getTime());
        }

        if (limit.getEndDate() != null)
        {
            frm.setField("endDate",   limit.getEndDate().getTime());
        }

        if (limit.getStatus() != null)
        {
            frm.setField("status",      limit.getStatus().name());
        }

        if (limit.getAmount() != null)
        {
            frm.setField("amount",      limit.getAmount().getDecimal());
            frm.setField("currency",    limit.getAmount().getCurrency().getCode());
        }
    }

    protected void updateFormAdditionalDataBase(EditFormBase frm, EditEntityOperation operation) throws Exception
    {
    }

    protected ActionForward doSave(EditEntityOperation operation, ActionMapping mapping, EditFormBase form) throws Exception
    {
        operation.save();
        addLogParameters(new BeanLogParemetersReader("Данные редактируемой сущности", operation.getEntity()));
        return createSaveActionForward(operation, form);
    }
}