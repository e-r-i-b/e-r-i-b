package com.rssl.phizic.web.creditcards.products;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.creditcards.conditions.CreditCardCondition;
import com.rssl.phizic.business.creditcards.products.CreditCardProduct;
import com.rssl.phizic.business.loans.products.Publicity;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.creditcards.products.EditCreditCardProductOperation;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.common.EditActionBase;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.log.FormLogParametersReader;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.hibernate.exception.ConstraintViolationException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Dorzhinov
 * @ created 15.06.2011
 * @ $Author$
 * @ $Revision$
 */
public class EditCreditCardProductAction extends EditActionBase
{

	private final String PRODUCT_ID = "productId";

	protected Form getEditForm(EditFormBase frm)
	{
		return EditCreditCardProductForm.FORM;
	}

	protected EditEntityOperation createEditOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		EditCreditCardProductOperation operation = createOperation(EditCreditCardProductOperation.class);
		Long id;
		id = frm.getId();
		if (id != null && id != 0)
			operation.initialize(id);
		else
			operation.initializeNew();

		return operation;
	}

	protected void updateForm(EditFormBase frm, Object entity)
	{
		EditCreditCardProductForm form = (EditCreditCardProductForm) frm;
		CreditCardProduct product = (CreditCardProduct) entity;
		if(product.getId() == null)
			return;
		form.setField(PRODUCT_ID, product.getId());
		form.setField("name", product.getName());
		form.setField("allowGracePeriod", product.isAllowGracePeriod());
		form.setField("gracePeriodDuration", product.getGracePeriodDuration());
		form.setField("gracePeriodInterestRate", product.getGracePeriodInterestRate());
		form.setField("cardTypeCode", product.getCardTypeCode());
		form.setField("additionalTerms", product.getAdditionalTerms());
		form.setField("useForPreapprovedOffers", product.getUseForPreapprovedOffers());
		form.setField("defaultForPreapprovedOffers", product.getDefaultForPreapprovedOffers());
	}

	protected void updateFormAdditionalData(EditFormBase form, EditEntityOperation operation) throws BusinessException
	{
		EditCreditCardProductOperation op = (EditCreditCardProductOperation) operation;
		EditCreditCardProductForm frm = (EditCreditCardProductForm) form;

		CreditCardProduct product = op.getEntity();
		frm.setCurrencies(op.getCurrencies());
		frm.setConditions(product.getConditions());
		frm.setCreditLimits(op.getCreditLimits());
	}

	protected void updateEntity(Object entity, Map<String, Object> data) throws BusinessException
	{
		CreditCardProduct product = (CreditCardProduct) entity;

		product.setName((String) data.get("name"));
		product.setAllowGracePeriod((Boolean) data.get("allowGracePeriod"));
		product.setGracePeriodDuration((Integer) data.get("gracePeriodDuration"));
		product.setGracePeriodInterestRate((BigDecimal) data.get("gracePeriodInterestRate"));
		product.setAdditionalTerms((String) data.get("additionalTerms"));
		product.setCardTypeCode((Integer)data.get("cardTypeCode"));
		product.setUseForPreapprovedOffers((Boolean)data.get("useForPreapprovedOffers"));
		product.setDefaultForPreapprovedOffers((Boolean)data.get("defaultForPreapprovedOffers"));
		if(product.getPublicity() == null)
			product.setPublicity(Publicity.UNPUBLISHED);
	}

	protected ActionMessages validateAdditionalFormData(EditFormBase frm, EditEntityOperation operation) throws Exception
	{
		ActionMessages actionMessages = new ActionMessages();
		EditCreditCardProductOperation op = (EditCreditCardProductOperation) operation;

		EditCreditCardProductForm form = (EditCreditCardProductForm) frm;
		Form editConditionForm = EditCreditCardProductForm.CONDITION_FORM;

		//условия в разрезе валют
		List<CreditCardCondition> conditions = new ArrayList<CreditCardCondition>();
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
			fields.put("interestRate",             form.getInterestRate()[i]);
			fields.put("offerInterestRate",        form.getOfferInterestRate()[i]);
			fields.put("firstYearPayment",         form.getFirstYearPayment()[i]);
			fields.put("nextYearPayment",          form.getNextYearPayment()[i]);

			FieldValuesSource valuesSource = new MapValuesSource(fields);
			//Фиксируем данные, введенные пользователе
			addLogParameters(new FormLogParametersReader("Данные, введенные пользователем", editConditionForm, fields));

			FormProcessor<ActionMessages, ?> processor = createFormProcessor(valuesSource, editConditionForm);
			if (processor.process())
				conditions.add(op.createCondition(processor.getResult()));
			else
			{
				actionMessages.add(processor.getErrors());
				return actionMessages;
			}
		}
		CreditCardProduct product = op.getEntity();
		product.setConditions(conditions);

		return actionMessages;
	}

	protected ActionForward doSave(EditEntityOperation operation, ActionMapping mapping, EditFormBase frm)
	{
		ActionMessages msgs = new ActionMessages();
		try
		{
			return super.doSave(operation, mapping, frm);
		}
		catch (ConstraintViolationException e)
		{
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.cannot-add-or-update-creditcardproduct"));
		}
		catch (Exception e)
		{
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(e.getMessage(), false));
		}
		saveErrors(currentRequest(), msgs);
		return mapping.findForward(FORWARD_START);
	}
}
