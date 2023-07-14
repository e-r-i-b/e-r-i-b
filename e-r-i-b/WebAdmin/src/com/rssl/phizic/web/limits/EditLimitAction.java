package com.rssl.phizic.web.limits;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.limits.*;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.limits.ChannelType;
import com.rssl.phizic.common.types.security.SecurityType;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.limits.ConfirmLimitOperation;
import com.rssl.phizic.operations.limits.EditLimitOperation;
import com.rssl.phizic.operations.limits.LimitOperationBase;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.web.actions.StrutsUtils;
import com.rssl.phizic.web.common.EditActionBase;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Экшен редактирования кумулятивного лимита (основное приложение)
 *
 * @author gulov
 * @ created 23.08.2010
 * @ $Authors$
 * @ $Revision$
 */
public class EditLimitAction extends EditActionBase
{
	private static final String CHANNEL_TYPE = "general";

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = super.getKeyMethodMap();

		map.put("button.save", "save");
		map.put("button.confirm", "confirm");

		return map;
	}


	protected EditEntityOperation createEditOperation(EditFormBase form) throws BusinessException, BusinessLogicException
	{
		EditLimitForm frm = (EditLimitForm) form;

		EditLimitOperation operation = createOperation(EditLimitOperation.class, "LimitsManagment");
		initOperation(frm, operation);

		// добавляем статус для правильного вычисления expression в логической форме
		form.setField("status", ((Limit)operation.getEntity()).getStatus());

		return operation;
	}

	protected EditEntityOperation createViewOperation(EditFormBase form) throws BusinessException, BusinessLogicException
	{
		EditLimitForm frm = (EditLimitForm) form;

		EditLimitOperation operation = createOperation("ViewLimitOperation");
		initOperation(frm, operation);

		return operation;
	}

	protected Form getEditForm(EditFormBase frm)
	{
		return EditLimitForm.EDIT_FORM;
	}

	protected void updateEntity(Object entity, Map<String, Object> data) throws Exception
	{
		Limit limit = (Limit) entity;
		Status status = limit.getStatus();

		// ограничения, и тип лимита обновляем только если создаем новый лимит
		if (status == null)
		{
			limit.setRestrictionType(RestrictionType.valueOf((String) data.get("restrictionType")));
		}

		//если было ограничение по сумме
		if (limit.getRestrictionType() == RestrictionType.AMOUNT_IN_DAY || limit.getRestrictionType() == RestrictionType.MIN_AMOUNT)
		{
			CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);
			BigDecimal amount = (BigDecimal) data.get("amount");
			if (amount == null)
				throw new BusinessLogicException("Введите значение в поле Величина лимита");
			limit.setAmount(new Money(amount, currencyService.getNationalCurrency()));
		}

		//если было ограничение по кол-ву операций
		if (limit.getRestrictionType() == RestrictionType.OPERATION_COUNT_IN_DAY || limit.getRestrictionType() == RestrictionType.OPERATION_COUNT_IN_HOUR)
		{
			Long count = (Long) data.get("operationCount");
			if (count == null)
				throw new BusinessLogicException("Введите значение в поле Количество операций");
			limit.setOperationCount(count);
		}

		// дату начала действия и тип действия при превышении лимита обновляем только если создаем новый или редактируем введенный лимит
		if (status == null || status == Status.DRAFT)
		{
			limit.setOperationType(OperationType.valueOf((String) data.get("operationType")));
			limit.setStartDate(getStartDate(data));
		}
	}

	protected void updateOperationAdditionalData(EditEntityOperation editOperation, EditFormBase editForm, Map<String, Object> validationResult) throws Exception
	{
		LimitOperationBase op = (LimitOperationBase) editOperation;
		op.setGroupRisk((Long) validationResult.get("groupRiskId"));
	}

	protected void updateForm(EditFormBase frm, Object entity) throws Exception
	{
		Limit limit = (Limit) entity;

		frm.setField("creationDate",    limit.getCreationDate().getTime());

		if (limit.getStartDate() != null)
		{
			frm.setField("startDate",   limit.getStartDate().getTime());
			frm.setField("startDateTime", limit.getStartDate().getTime());
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
		frm.setField("restrictionType", limit.getRestrictionType());
		frm.setField("operationType", limit.getOperationType());
		frm.setField("operationCount", limit.getOperationCount());
		frm.setField("limitType", limit.getType());
	}

	protected void updateFormAdditionalDataBase(EditFormBase frm, EditEntityOperation operation) throws Exception
	{
		Limit limit = (Limit) operation.getEntity();
		frm.setField("creationDate",    limit.getCreationDate().getTime());
		if (limit.getStartDate() != null && (frm.getField("startDate") == null || "".equals(frm.getField("startDate").toString())))
		{
			frm.setField("startDate", limit.getStartDate().getTime());
			frm.setField("startDateTime", limit.getStartDate().getTime());
		}

		//если редактирование то нужно обновить неизменяемые поля, т.к. мы не получаем их из FormProcessor
		if (limit.getStatus() != null)
		{
			frm.setField("restrictionType", limit.getRestrictionType());
			frm.setField("operationType", limit.getOperationType());
			frm.setField("limitType", limit.getType());
		}

		Object amount = frm.getField("amount");
		if((amount == null || "".equals(amount.toString())) && limit.getAmount() != null)
		{
			frm.setField("amount", limit.getAmount().getDecimal());
		}

		Object operationCount = frm.getField("operationCount");
		if(operationCount == null || "".equals(operationCount.toString()) && limit.getOperationCount() != null)
		{
			frm.setField("operationCount", limit.getOperationCount());
		}
	}

	protected void updateFormAdditionalData(EditFormBase frm, EditEntityOperation operation) throws Exception
	{
		updateFormAdditionalDataBase(frm, operation);

		//для лимитов на группу риска нужно помнить доступные группы риска
		EditLimitForm form = (EditLimitForm) frm;
		LimitOperationBase op = (LimitOperationBase) operation;
		form.setGroupsRisk(op.getAvailableGroupRisk());
		form.setSecurityType(((Limit)op.getEntity()).getSecurityType().name());
	}

	protected ActionForward createSaveActionForward(EditEntityOperation operation, EditFormBase frm) throws BusinessException
	{
		EditLimitForm form = (EditLimitForm) frm;
		return new ActionForward(getCurrentMapping().findForward(FORWARD_SUCCESS).getPath()+ "?departmentId=" + form.getDepartmentId() + "&channel=" + getChannelType() + "&securityType=" + form.getSecurityType(), true);
	}

	protected String getChannelType()
	{
		return CHANNEL_TYPE;
	}

	protected void initOperation(EditLimitForm frm, EditLimitOperation operation) throws BusinessException
	{
		Long limitId = frm.getId();

		if ((limitId != null) && (limitId != 0))
			operation.initialize(limitId, getLimitChannelType());
		else
			operation.initializeNew(frm.getDepartmentId(), getLimitType(frm), getLimitChannelType(), getLimitSecurityType(frm));
	}

	protected LimitType getLimitType(EditLimitForm frm)
	{
		return LimitType.GROUP_RISK;
	}

	protected ChannelType getLimitChannelType()
	{
		return ChannelType.INTERNET_CLIENT;
	}

	protected Calendar getStartDate(Map<String, Object> data)
	{
		Calendar startDate = DateHelper.toCalendar((Date) data.get("startDate"));
		Calendar startDateTime = DateHelper.toCalendar((Date) data.get("startDateTime"));

		startDate.set(Calendar.HOUR_OF_DAY, startDateTime.get(Calendar.HOUR_OF_DAY));
		startDate.set(Calendar.MINUTE, startDateTime.get(Calendar.MINUTE));
		startDate.set(Calendar.SECOND, startDateTime.get(Calendar.SECOND));

		return startDate;
	}

	protected ActionForward doSave(EditEntityOperation operation, ActionMapping mapping, EditFormBase form) throws Exception
	{
		operation.save();
		addLogParameters(new BeanLogParemetersReader("Данные редактируемой сущности", operation.getEntity()));
		return createSaveActionForward(operation, form);
	}

	public ActionForward confirm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditLimitForm frm = (EditLimitForm) form;
		ConfirmLimitOperation operation = createConfirmOperation(frm);

		try
		{
			// если сотрудник имеет права на редактирование, то проверяем менял ли он что нибудь на форме
			if(checkAccess(EditLimitOperation.class, "LimitsManagment") && !checkConfirmData(frm, operation))
			{
				updateFormAdditionalData(frm, operation);
				return createStartActionForward(form, mapping);
			}

			operation.confirm();
			addLogParameters(new BeanLogParemetersReader("Данные подтверждаемой сущности", operation.getEntity()));
		}
		catch (BusinessLogicException e)
		{
			saveError(request, e.getMessage());
			updateFormAdditionalData(frm, operation);
			return createStartActionForward(frm, mapping);
		}

		return createSaveActionForward(operation, frm);
	}

	private boolean checkConfirmData(EditLimitForm frm, ConfirmLimitOperation operation) throws Exception
	{
		// добавляем статус для правильного вычисления expression в логической форме
		frm.setField("status", ((Limit)operation.getEntity()).getStatus());

		FormProcessor<ActionMessages, ?> processor = getFormProcessor(frm, operation);
		if(processor.process())
		{
			if(isChangeLimitData((Limit) operation.getEntity(), processor.getResult()))
			{
				saveError(currentRequest(), StrutsUtils.getMessage("change.data.limit.error.message", "limitsBundle"));
				return false;
			}
		}
		else
		{
			saveErrors(currentRequest(), processor.getErrors());
			return false;
		}

		return true;
	}

	protected ConfirmLimitOperation createConfirmOperation(EditFormBase form) throws BusinessException, BusinessLogicException
	{
		EditLimitForm frm = (EditLimitForm) form;

		ConfirmLimitOperation operation = createOperation(ConfirmLimitOperation.class, "ConfirmLimitsManagment");
		operation.initialize(frm.getId(), getLimitChannelType());

		return operation;
	}

	/**
	 * Проверка на то, что данные лимита отображенные на форме, были изменены
	 * @param limit лимит
	 * @param formData данные с формы
	 * @return true - данные изменились
	 */
	protected boolean isChangeLimitData(Limit limit, Map<String, Object> formData) throws BusinessException
	{
		CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);
		try
		{
			if(!limit.getStartDate().equals(getStartDate(formData))
					|| !limit.getOperationType().equals(OperationType.valueOf((String) formData.get("operationType"))))
				return true;

			if((limit.getRestrictionType() == RestrictionType.OPERATION_COUNT_IN_DAY || limit.getRestrictionType() == RestrictionType.OPERATION_COUNT_IN_HOUR)
					&& !limit.getOperationCount().equals(formData.get("operationCount")))
				return true;

			if((limit.getRestrictionType() == RestrictionType.AMOUNT_IN_DAY || limit.getRestrictionType() == RestrictionType.MIN_AMOUNT)
					&& !limit.getAmount().equals(new Money((BigDecimal) formData.get("amount"), currencyService.getNationalCurrency())))
				return true;

			return false;
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * @param frm форма
	 * @return уровень безопасности лимита
	 */
	protected SecurityType getLimitSecurityType(EditLimitForm frm)
	{
		return SecurityType.valueOf(frm.getSecurityType());
	}
}
