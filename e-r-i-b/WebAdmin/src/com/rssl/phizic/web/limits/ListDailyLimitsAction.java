package com.rssl.phizic.web.limits;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.limits.*;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.common.types.limits.ChannelType;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.RemoveEntityOperation;
import com.rssl.phizic.operations.limits.ListLimitsOperation;
import com.rssl.phizic.operations.limits.RemoveLimitOperation;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.actions.SaveFilterParameterAction;
import com.rssl.phizic.web.common.ListFormBase;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import java.util.Collections;
import java.util.Date;
import java.util.Map;

/**
 * @author: vagin
 * @ created: 26.01.2012
 * @ $Author$
 * @ $Revision$
 * Экшен для получения списка суточных комулятивных лимитов
 */
public class ListDailyLimitsAction extends SaveFilterParameterAction
{
	protected static final Integer INIT_PAGINATION_SIZE = 21;  //20 по умолчанию, +1 для правильной отрисовки пагинации
	protected static final Integer INIT_PAGINATION_OFFSET = 0; //по умолчанию

	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		ListLimitsOperation operation = createOperation(ListLimitsOperation.class, "LimitsManagment");

		ListDailyLimitsForm form = (ListDailyLimitsForm) frm;
		operation.initialize(form.getDepartmentId());

		return operation;
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return ListDailyLimitsForm.FILTER_FORM;
	}

	protected void fillQuery(Query query, Map<String, Object> filterParams, LimitType type, Integer paginationOffset, Integer paginationSize)
	{
		query.setParameter("fromCreationDate", filterParams.get("fromCreationDate"));
		Object toCreationDate = filterParams.get("toCreationDate");
		query.setParameter("toCreationDate", toCreationDate == null ? null : DateHelper.add((Date) toCreationDate, 0, 0, 1));
		query.setParameter("fromStartDate", filterParams.get("fromStartDate"));
		query.setParameter("toStartDate", filterParams.get("toStartDate"));
		query.setParameter("amount", filterParams.get("amount"));
		query.setParameter("status", filterParams.get("status"));
		query.setParameter("operationType", null);
		query.setParameter("restrictionType", null);
		query.setParameter("type", type.name());
		query.setParameter("groupRiskId", null);
		query.setParameter("operationCount", null);
		query.setParameter("securityType", null);
		query.setParameter("channel", getChannelType().name());
		query.setMaxResults((paginationSize != null) ? paginationSize+1 : INIT_PAGINATION_SIZE);
		query.setFirstResult((paginationOffset != null) ? paginationOffset : INIT_PAGINATION_OFFSET);
	}

	protected void doFilter(Map<String, Object> filterParams, ListEntitiesOperation operation, ListFormBase frm) throws Exception
	{
		ListDailyLimitsForm form = (ListDailyLimitsForm) frm;

		String strLimitType = (String)filterParams.get("limitType");
		LimitType filterLimitType = StringHelper.isEmpty(strLimitType) ? null : LimitType.valueOf(strLimitType);

		//заполнение суточных кумулятивных заградительных лимитов
		if (filterLimitType == null || filterLimitType == LimitType.OBSTRUCTION_FOR_AMOUNT_OPERATIONS)
		{
			Query dailyLimitsQuery = operation.createQuery(getQueryName(frm));
			fillQuery(dailyLimitsQuery, filterParams, LimitType.OBSTRUCTION_FOR_AMOUNT_OPERATIONS, form.getPagination_offset0(), form.getPagination_size0());
			form.setData(dailyLimitsQuery.<Limit>executeList());
		}

		//заполнение лимитов IMSI
		if (filterLimitType == null || filterLimitType == LimitType.IMSI)
		{
			Query imsiLimitsQuery = operation.createQuery(getQueryName(frm));
			fillQuery(imsiLimitsQuery, filterParams, LimitType.IMSI, form.getPagination_offset1(), form.getPagination_size1());
			form.setImsiLimit(imsiLimitsQuery.<Limit>executeList());
		}

		if (ChannelType.MOBILE_API == getChannelType())
		{
			if (filterLimitType == null || filterLimitType == LimitType.EXTERNAL_CARD)
			{
				Query mobileExternalCardLimitsQuery = operation.createQuery(getQueryName(frm));
				fillQuery(mobileExternalCardLimitsQuery, filterParams, LimitType.EXTERNAL_CARD, form.getPagination_offset2(), form.getPagination_size2());
				form.setMobileExternalCardLimit(mobileExternalCardLimitsQuery.<Limit>executeList());
			}
		}

		if (ChannelType.ERMB_SMS == getChannelType())
		{
			//заполнение лимитов на получателя ЕРМБ для оплаты чужого телефона
			if (filterLimitType == null || filterLimitType == LimitType.EXTERNAL_PHONE)
			{
				Query ermbExternalTelephoneLimitsQuery = operation.createQuery(getQueryName(frm));
				fillQuery(ermbExternalTelephoneLimitsQuery, filterParams, LimitType.EXTERNAL_PHONE, form.getPagination_offset2(), form.getPagination_size2());
				form.setErmbExternalTelephone(ermbExternalTelephoneLimitsQuery.<Limit>executeList());
			}

			//заполнение лимитов на получателя ЕРМБ для переводов на чужую карту
			if (filterLimitType == null || filterLimitType == LimitType.EXTERNAL_CARD)
			{
				Query ermbExternalCardLimitsQuery = operation.createQuery(getQueryName(frm));
				fillQuery(ermbExternalCardLimitsQuery, filterParams, LimitType.EXTERNAL_CARD, form.getPagination_offset3(), form.getPagination_size3());
				form.setErmbExternalCard(ermbExternalCardLimitsQuery.<Limit>executeList());
			}
		}

		form.setFilters(filterParams);
		updateFormAdditionalData(frm, operation);
	}

	protected RemoveEntityOperation createRemoveOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(RemoveLimitOperation.class, "ConfirmLimitsManagment");
	}

	protected Map<String, Object> getDefaultFilterParameters(ListFormBase frm, ListEntitiesOperation operation)
			throws BusinessException, BusinessLogicException
	{
		return Collections.<String, Object> singletonMap("status", Status.ACTIVE.toValue());
	}

	protected ActionMessages doRemove(RemoveEntityOperation op, Long id) throws Exception
	{
		ActionMessages msgs = new ActionMessages();
		RemoveLimitOperation operation = (RemoveLimitOperation) op;
		try
		{
			operation.initialize(id);
			operation.remove();
		}
		catch (InactiveExternalSystemException e)
		{
			saveInactiveESMessage(currentRequest(), e);
		}
		catch (BusinessLogicException e)
		{
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Невозможно удалить лимит id=" + operation.getEntity().getId() + ". Причина: " + e.getMessage(), false));
		}
		catch (BusinessException e)
		{
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Невозможно удалить лимит id=" + operation.getEntity().getId() + ".", false));
		}
		return msgs;
	}

	protected ChannelType getChannelType()
	{
		return ChannelType.INTERNET_CLIENT;
	}
}
