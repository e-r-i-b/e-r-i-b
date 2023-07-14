package com.rssl.phizic.web.loanclaim.creditProduct.condition;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.currencies.CurrencyImpl;
import com.rssl.phizic.business.loanclaim.creditProduct.CreditProduct;
import com.rssl.phizic.business.loanclaim.creditProduct.condition.CreditProductCondition;
import com.rssl.phizic.business.loanclaim.creditProduct.condition.CurrencyCreditProductCondition;
import com.rssl.phizic.business.loanclaim.creditProduct.type.CreditProductType;
import com.rssl.phizic.business.loans.products.MaxDuration;
import com.rssl.phizic.business.loans.products.YearMonth;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.ext.sbrf.dictionaries.loanclaim.creditProduct.condition.EditCreditProductConditionOperation;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.common.EditActionBase;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.loanclaim.Constants;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author Moshenko
 * @ created 16.01.2014
 * @ $Author$
 * @ $Revision$
 * Ёкшен редактировани€ условий по кредитным продуктам.
 */
public class EditCreditProductConditionAction  extends EditActionBase
{
	private static final String[] currArr = {Constants.RUB_CODE_NUMBER,Constants.USD_CODE_NUMBER,Constants.EUR_CODE_NUMBER};

	protected EditEntityOperation createEditOperation(EditFormBase form) throws BusinessException, BusinessLogicException
	{
		EditCreditProductConditionForm frm = (EditCreditProductConditionForm) form;
		EditCreditProductConditionOperation op = createOperation(EditCreditProductConditionOperation.class);
		Long id = frm.getId();
		if (id != null)
			op.initialize(frm.getId());
		else
			op.initializeNew();
		frm.setTbCodes(op.getAllTb());
		return op;
	}

	protected Form getEditForm(EditFormBase frm)
	{
		EditCreditProductConditionForm form = (EditCreditProductConditionForm) frm;
		return form.createForm();
	}

	protected void updateForm(EditFormBase frm, Object entity) throws Exception
	{
	}

	protected void updateEntity(Object entity, Map<String, Object> data) throws Exception
	{
	}

	protected void updateOperationAdditionalData(EditEntityOperation operation, EditFormBase form, Map<String, Object> data) throws Exception
	{
		EditCreditProductConditionForm frm = (EditCreditProductConditionForm) form;
		EditCreditProductConditionOperation op = (EditCreditProductConditionOperation) operation;
		CreditProductCondition condition = op.getEntity();
		condition.setTransactSMPossibility((Boolean) data.get(Constants.TRANSACT_SM_USE));
		condition.setSelectionAvaliable((Boolean) data.get(Constants.SELECTION_AVALIABLE));
		condition.setCreditProduct(op.getCreditProduct((Long)data.get(Constants.PRODUCT_ID)));
		condition.setCreditProductType(op.getCreditProductType((Long)data.get(Constants.PRODUCT_TYPE_ID)));
		YearMonth minDuration = new YearMonth();
		minDuration.setMonth((Integer) data.get(Constants.PRODUCT_DATE_FROM_MONTH));
		minDuration.setYear((Integer) data.get(Constants.PRODUCT_DATE_FROM_YEAR));
		condition.setMinDuration(minDuration);
		MaxDuration maxDuration = new MaxDuration();
		maxDuration.setMonth((Integer) data.get(Constants.PRODUCT_DATE_TO_MONTH));
		maxDuration.setYear((Integer) data.get(Constants.PRODUCT_DATE_TO_YEAR));
		condition.setMaxDuration(maxDuration);
		condition.setMaxRangeInclude((Boolean) data.get(Constants.PRODUCT_MAX_DATE_INCLUDE));
		condition.setUseInitialFee((Boolean) data.get(Constants.USE_INITIAL_FEE));
		condition.setMinInitialFee((BigDecimal) data.get(Constants.MIN_INITIAL_FEE));
		condition.setMaxInitialFee((BigDecimal) data.get(Constants.MAX_INITIAL_FEE));
		condition.setIncludeMaxInitialFee((Boolean) data.get(Constants.MAX_INITIAL_FEE_INCLUDE));
		condition.setAdditionalConditions((String) data.get(Constants.ADDITIONAL_TERMS));

		List<String> checketTb = new ArrayList<String>();
		for(String tbCode:op.getAllTb())
		{
			if (!StringHelper.isEmpty((String)frm.getField(Constants.TB+tbCode)))
				checketTb.add(tbCode);
		}
		condition.setDepartmentsList(checketTb);

		//Ќовые повалютные пришедшие с формы.
		Set<CurrencyCreditProductCondition> currCondSet = new HashSet<CurrencyCreditProductCondition>();
		//¬се повалютные услови€ данного общего услови€, копируем дл€ возможности удал€ть при переборе этогоже набора.
		//ѕри сохранении в условие будем использовать allCurCond
		Set<CurrencyCreditProductCondition>  allCurCond = new HashSet<CurrencyCreditProductCondition>();
		//Ќовые услови€ на удаление.
		Set<CurrencyCreditProductCondition>  remCurCond = new HashSet<CurrencyCreditProductCondition>();
		if (condition.getCurrConditions() != null)
			allCurCond.addAll(condition.getCurrConditions());
		for(String currCode: currArr)
		{
			boolean  fieldEnabled = false;
			//доступность пол€ проверим по наполненности одного из об€зательных полей
			if  (data.get(Constants.CURR_COND_FROM_DATE + currCode) != null)
				fieldEnabled = true;

			//ѕризнак доступности по валюте.
			boolean available = (Boolean) data.get(Constants.CURR_COND_AVALIABLE + currCode);
			op.updateClientAvaliable(available,currCode);

			Long currCondId =  (Long) data.get(Constants.CURR_COND_ID + currCode);
			if (fieldEnabled)
			{
				CurrencyCreditProductCondition currCond = new CurrencyCreditProductCondition();
				//≈сли это редактирование уже созданного то выбираем его из услови€(id приходит с формы).

				if (currCondId != null)
				{
					for(CurrencyCreditProductCondition cond:condition.getCurrConditions())
					{
						Long condId = cond.getId();
						if (condId != null && currCondId.equals(condId))
						{
							currCond = cond;
							allCurCond.remove(cond);
						}
					}
				}
				currCond.setClientAvaliable(available);
				currCond.setCreditProductCondition(condition);
				CurrencyImpl currency  = (CurrencyImpl) op.getCurrency(currCode);
				currency.setNumber(currCode);
				currCond.setCurrency(currency);
				Date startDate = (Date)data.get(Constants.CURR_COND_FROM_DATE + currCode);
				currCond.setStartDate(op.getCorrectTime(startDate));
 				currCond.setMinLimitAmount(new Money((BigDecimal)data.get(Constants.CURR_COND_MIN_LIMIT + currCode),currency));

				boolean isMaxLimPercentUse = (Boolean) data.get(Constants.CURR_COND_MAX_LIMIT_PERCENT_USE + currCode);
				currCond.setMaxLimitPercentUse(isMaxLimPercentUse);
				BigDecimal res = (BigDecimal)data.get(Constants.CURR_COND_MAX_LIMIT + currCode);
				if (isMaxLimPercentUse)
				{
					currCond.setMaxLimitPercent(res);
					currCond.setMaxLimitAmount(null);
				}
				else
				{
					currCond.setMaxLimitPercent(null);
					currCond.setMaxLimitAmount(new Money(res,currency));
				}
				currCond.setMaxLimitInclude((Boolean)data.get(Constants.CURR_COND_MAX_LIMIT_INCLUDE + currCode));
				currCond.setMinPercentRate((BigDecimal)data.get(Constants.CURR_COND_MIN_PERCENT_RATE + currCode));
				currCond.setMaxPercentRate((BigDecimal) data.get(Constants.CURR_COND_MAX_PERCENT_RATE + currCode));
				currCond.setMaxPercentRateInclude((Boolean) data.get(Constants.CURR_COND_MAX_PERCENT_RATE_INCLUDE + currCode));
				currCondSet.add(currCond);
			}

			else if (currCondId != null && !fieldEnabled && !available)
			{
				CurrencyCreditProductCondition currCondtoRemove = null;
				for (CurrencyCreditProductCondition currCond:allCurCond)
				{
					if (currCond.getId().equals(currCondId))
						currCondtoRemove = currCond;
				}
				if (currCondtoRemove != null)
				{
					allCurCond.remove(currCondtoRemove);
					remCurCond.add(currCondtoRemove);
				}
			}
		}
		op.setRemoveCurrCond(remCurCond);
		//ѕроизводим  необходимые проверки новых повалютных пришедших с формы.
		op.currencyConditionTests(currCondSet);
		//ƒл€ случа€ когда создаем новое условие.
		if (allCurCond == null)
			allCurCond = new HashSet<CurrencyCreditProductCondition>();

		allCurCond.addAll(currCondSet);
		condition.setCurrConditions(allCurCond);

	}



	protected void updateFormAdditionalData(EditFormBase form, EditEntityOperation operation) throws Exception
	{
		EditCreditProductConditionForm frm = (EditCreditProductConditionForm) form;
		EditCreditProductConditionOperation op = (EditCreditProductConditionOperation) operation;
		CreditProductCondition condition = op.getEntity();

		CreditProductType type = condition.getCreditProductType();
		CreditProduct product = condition.getCreditProduct();
		//ѕол€ общего услови€.
		frm.setField(Constants.TRANSACT_SM_USE, condition.isTransactSMPossibility());
		frm.setField(Constants.SELECTION_AVALIABLE, condition.isSelectionAvaliable());
		if (type != null)
		{
			frm.setField(Constants.PRODUCT_TYPE_ID, type.getId());
			frm.setField(Constants.PRODUCT_TYPE_CODE, type.getId());
		}
		if (product != null)
		{
			frm.setField(Constants.PRODUCT_ID, product.getId());
			frm.setField(Constants.PRODUCT_CODE, product.getCode());
			frm.setField(Constants.ENSURING, product.isEnsuring());
		}
		YearMonth minDuration = condition.getMinDuration();
		if (!minDuration.isEmpty())
		{
			frm.setField(Constants.PRODUCT_DATE_FROM_MONTH, minDuration.getMonth());
			frm.setField(Constants.PRODUCT_DATE_FROM_YEAR, minDuration.getYear());
		}
		YearMonth maxDuration = condition.getMaxDuration();
		if (!maxDuration.isEmpty())
		{
			frm.setField(Constants.PRODUCT_DATE_TO_MONTH, maxDuration.getMonth());
			frm.setField(Constants.PRODUCT_DATE_TO_YEAR, maxDuration.getYear());
		}
		frm.setField(Constants.PRODUCT_MAX_DATE_INCLUDE, condition.isMaxRangeInclude());
		frm.setField(Constants.USE_INITIAL_FEE, condition.isUseInitialFee());
		frm.setField(Constants.MIN_INITIAL_FEE, condition.getMinInitialFee());
		frm.setField(Constants.MAX_INITIAL_FEE, condition.getMaxInitialFee());
		frm.setField(Constants.MAX_INITIAL_FEE_INCLUDE, condition.isIncludeMaxInitialFee());
		frm.setField(Constants.ADDITIONAL_TERMS, condition.getAdditionalConditions());
		//ѕол€ новых повалютных условий.
		for (String currCode:currArr)
		{
			//Ќовое условие.
			CurrencyCreditProductCondition newCurrCond = op.getNewCurrCondition(currCode);
			if (newCurrCond != null)
			{
				frm.setField(Constants.CURR_COND_ID + currCode,newCurrCond.getId());
				frm.setField(Constants.CURR_COND_FROM_DATE + currCode, DateHelper.formatDateToStringWithPoint(newCurrCond.getStartDate()));
				frm.setField(Constants.CURR_COND_MIN_LIMIT + currCode,newCurrCond.getMinLimitAmount().getDecimal());

				boolean maxLimitPercentUse = newCurrCond.isMaxLimitPercentUse();
				frm.setField(Constants.CURR_COND_MAX_LIMIT_PERCENT_USE + currCode,maxLimitPercentUse);
				if (maxLimitPercentUse)
					frm.setField(Constants.CURR_COND_MAX_LIMIT + currCode,newCurrCond.getMaxLimitPercent());
				else
					frm.setField(Constants.CURR_COND_MAX_LIMIT + currCode,newCurrCond.getMaxLimitAmount().getDecimal());
				frm.setField(Constants.CURR_COND_MAX_LIMIT_INCLUDE + currCode,newCurrCond.isMaxLimitInclude());
				frm.setField(Constants.CURR_COND_MIN_PERCENT_RATE + currCode,newCurrCond.getMinPercentRate());
				frm.setField(Constants.CURR_COND_MAX_PERCENT_RATE + currCode,newCurrCond.getMaxPercentRate());
				frm.setField(Constants.CURR_COND_MAX_PERCENT_RATE_INCLUDE + currCode,newCurrCond.isMaxPercentRateInclude());
			}
			//“екущие условие.
			CurrencyCreditProductCondition currentCurrCond = op.getCurrentCurrCondition(currCode);
			if (currentCurrCond != null)
				frm.setField(Constants.CURR_COND_AVALIABLE + currCode,currentCurrCond.isClientAvaliable());
			else if (newCurrCond != null)
				frm.setField(Constants.CURR_COND_AVALIABLE + currCode,newCurrCond.isClientAvaliable());

			if (StringHelper.equals(currCode,Constants.RUB_CODE_NUMBER))
				frm.setRubCondition(currentCurrCond);
			else if (StringHelper.equals(currCode,Constants.USD_CODE_NUMBER))
				frm.setUsdCondition(currentCurrCond);
			else if (StringHelper.equals(currCode,Constants.EUR_CODE_NUMBER))
				frm.setEurCondition(currentCurrCond);

		}
		//—писок тербанков.
		List<String> tbList = condition.getDepartmentsList();
		if (tbList != null)
			for(String tb:tbList)
				frm.setField(Constants.TB + tb,true);

		frm.setTbCodes(op.getAllTb());
		frm.setCondition(op.getEntity());
	}

	protected ActionForward doSave(EditEntityOperation operation, ActionMapping mapping, EditFormBase frm) throws Exception
	{
		try
		{
			super.doSave(operation, mapping, frm);
		}
		catch(BusinessLogicException e)
		{
			saveError(currentRequest(), e);
			return createStartActionForward(frm, mapping);
		}
		CreditProductCondition condition = (CreditProductCondition)operation.getEntity();
		return new ActionForward(getCurrentMapping().findForward(FORWARD_SUCCESS).getPath() + "?id=" + condition.getId(),true);
	}
}
