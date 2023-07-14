package com.rssl.phizic.web.creditcards.incomes;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.creditcards.conditions.IncomeCondition;
import com.rssl.phizic.business.creditcards.incomes.IncomeLevel;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.creditcards.incomes.EditIncomeLevelOperation;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.common.EditActionBase;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.log.FormLogParametersReader;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Dorzhinov
 * @ created 29.06.2011
 * @ $Author$
 * @ $Revision$
 */
public class EditIncomeLevelAction extends EditActionBase
{
	protected Form getEditForm(EditFormBase frm)
	{
		return EditIncomeLevelForm.FORM;
	}

	protected EditEntityOperation createEditOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		EditIncomeLevelOperation operation = createOperation(EditIncomeLevelOperation.class);
		Long id = frm.getId();
		if (id != null && id != 0)
			operation.initialize(id);
		else
			operation.initializeNew();

		return operation;
	}

	protected void updateForm(EditFormBase frm, Object entity)
	{
		EditIncomeLevelForm form = (EditIncomeLevelForm) frm;
		IncomeLevel incomeLevel = (IncomeLevel) entity;
		if(incomeLevel.getId() == null)
			return;

		form.setField("minIncome", incomeLevel.getMinIncome() == null ? "" : incomeLevel.getMinIncome().getDecimal());
		form.setField("maxIncome", incomeLevel.getMaxIncome() == null ? "" : incomeLevel.getMaxIncome().getDecimal());
		form.setField("isMaxIncomeInclude", incomeLevel.isMaxIncomeInclude());
	}

	protected void updateFormAdditionalData(EditFormBase form, EditEntityOperation operation) throws BusinessException
	{
		EditIncomeLevelOperation op = (EditIncomeLevelOperation) operation;
		EditIncomeLevelForm frm = (EditIncomeLevelForm) form;

		IncomeLevel incomeLevel = op.getEntity();
		frm.setCurrencies(op.getCurrencies());
		frm.setConditions(incomeLevel.getConditions());
		frm.setCreditLimits(op.getCreditLimits());
	}

	protected void updateEntity(Object entity, Map<String, Object> data) throws BusinessException
	{
		IncomeLevel incomeLevel = (IncomeLevel) entity;

		Currency currency;
		try
		{
			currency = GateSingleton.getFactory().service(CurrencyService.class).getNationalCurrency();
		}
		catch (GateException e)
		{
			throw new BusinessException("Невозможно получить национальную валюту", e);
		}

		Money minIncome = data.get("minIncome") == null ? null : new Money((BigDecimal) data.get("minIncome"), currency);
		incomeLevel.setMinIncome(minIncome);
		Money maxIncome = data.get("maxIncome") == null ? null : new Money((BigDecimal) data.get("maxIncome"), currency);
		incomeLevel.setMaxIncome(maxIncome);
		incomeLevel.setMaxIncomeInclude((Boolean) data.get("isMaxIncomeInclude"));
	}

	protected ActionMessages validateAdditionalFormData(EditFormBase frm, EditEntityOperation operation) throws Exception
	{
		ActionMessages actionMessages = new ActionMessages();
		EditIncomeLevelOperation op = (EditIncomeLevelOperation) operation;

		EditIncomeLevelForm form = (EditIncomeLevelForm) frm;
		Form editConditionForm = EditIncomeLevelForm.CONDITION_FORM;

		//условия в разрезе валют
		List<IncomeCondition> conditions = new ArrayList<IncomeCondition>();
		for(int i = 0; i < form.getConditionId().length; ++i)
		{
			Map<String, Object> fields = new HashMap<String, Object>();

			if(StringHelper.isEmpty(form.getCurrencyNumber()[i]))
				continue;

			fields.put("conditionId",              form.getConditionId()[i]);
			fields.put("currencyNumber",           form.getCurrencyNumber()[i]);
			fields.put("minCreditLimitId",         form.getMinCreditLimitId()[i]);
			fields.put("maxCreditLimitId",         form.getMaxCreditLimitId()[i]);
			fields.put("isMaxCreditLimitInclude",  form.getMaxCreditLimitInclude()[i]);

			FieldValuesSource valuesSource = new MapValuesSource(fields);
			//Фиксируем данные, введенные пользователе
			addLogParameters(new FormLogParametersReader("Данные, введенные пользователем", editConditionForm, fields));

			FormProcessor<ActionMessages, ?> processor = createFormProcessor(valuesSource, editConditionForm);
			if (processor.process())
				conditions.add(op.createCondition(fields));
			else
			{
				actionMessages.add(processor.getErrors());
				return actionMessages;
			}
		}
		IncomeLevel product = op.getEntity();
		product.setConditions(conditions);

		return actionMessages;
	}

	protected ActionForward doSave(EditEntityOperation operation, ActionMapping mapping, EditFormBase frm) throws BusinessException
	{
		ActionMessages msgs = new ActionMessages();
		try
		{
			return super.doSave(operation, mapping, frm);
		}
		catch (BusinessLogicException e)
		{
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(e.getMessage(), false));
			saveErrors(currentRequest(), msgs);
			return mapping.findForward(FORWARD_START);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

}
