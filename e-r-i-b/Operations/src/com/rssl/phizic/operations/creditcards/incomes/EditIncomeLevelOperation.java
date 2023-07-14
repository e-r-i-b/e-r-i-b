package com.rssl.phizic.operations.creditcards.incomes;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.cardAmountStep.CardAmountStep;
import com.rssl.phizic.business.cardAmountStep.CardAmountStepService;
import com.rssl.phizic.business.creditcards.conditions.IncomeCondition;
import com.rssl.phizic.business.creditcards.incomes.IncomeLevel;
import com.rssl.phizic.business.creditcards.incomes.IncomeLevelService;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.utils.StringHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Dorzhinov
 * @ created 29.06.2011
 * @ $Author$
 * @ $Revision$
 */
public class EditIncomeLevelOperation extends OperationBase implements EditEntityOperation
{
	private static final IncomeLevelService incomeLevelService = new IncomeLevelService();
	private static final CardAmountStepService creditLimitService = new CardAmountStepService();

	private IncomeLevel incomeLevel;
	private List<Currency> currencies = new ArrayList<Currency>();

	public void initializeNew() throws BusinessException
	{
		incomeLevel = new IncomeLevel();
		initCurrencies();
	}

	public void initialize(Long productId) throws BusinessException
	{
		incomeLevel = incomeLevelService.findById(productId);

		if (incomeLevel == null)
			throw new BusinessException("Соответствие доходов клиента доступным кредитным лимитам с Id = " + productId + " не найдено.");

		initCurrencies();
	}

	public IncomeLevel getEntity()
	{
		return incomeLevel;
	}

	public void save() throws BusinessException, BusinessLogicException
	{
		if(incomeLevel.getMinIncome() == null && incomeLevel.getMaxIncome() == null)
			throw new BusinessLogicException("Вы не указали среднюю сумму дохода клиента. Пожалуйста, введите минимальную и (или) максимальную сумму дохода клиента.");

		if(incomeLevel.getMinIncome() != null && incomeLevel.getMaxIncome() != null && incomeLevel.getMinIncome().getAsCents() >= incomeLevel.getMaxIncome().getAsCents())
			throw new BusinessLogicException("Вы неправильно указали среднюю сумму дохода клиента. Пожалуйста, проверьте минимальную и максимальную сумму.");

		//Интервал не должен пересекаться с уже существующими
		//если минимальный не задан, то считаем его равным 0
		//если максимальный не задан, то считаем его равным Long.MAX_VALUE
		for(IncomeLevel incomeLevel : incomeLevelService.getAll())
		{
			if(incomeLevel.getId().equals(this.incomeLevel.getId()))
				continue;
			
			long min = incomeLevel.getMinIncome() != null ? incomeLevel.getMinIncome().getAsCents() : 0;
			long max = incomeLevel.getMaxIncome() != null ? incomeLevel.getMaxIncome().getAsCents() : Long.MAX_VALUE;
			boolean include = incomeLevel.isMaxIncomeInclude();
			long thisMin = this.incomeLevel.getMinIncome() != null ? this.incomeLevel.getMinIncome().getAsCents() : 0;
			long thisMax = this.incomeLevel.getMaxIncome() != null ? this.incomeLevel.getMaxIncome().getAsCents() : Long.MAX_VALUE;
			boolean thisInclude = this.incomeLevel.isMaxIncomeInclude();
			
			if((include ? max >= thisMin : max > thisMin)
					&& (thisInclude ? min <= thisMax : min < thisMax))
				throw new BusinessLogicException("Вы неправильно указали среднюю сумму дохода клиента. Указанное значение пересекается с уже введенным интервалом. Пожалуйста, проверьте среднюю сумму дохода клиента.");
		}

		if(incomeLevel.getId() == null)
			incomeLevelService.add(incomeLevel);
		else
			incomeLevelService.update(incomeLevel);
	}

	public List<Currency> getCurrencies()
	{
		return currencies;
	}

	public List<CardAmountStep> getCreditLimits() throws BusinessException
	{
		return creditLimitService.getAll();
	}

	public IncomeCondition createCondition(Map<String, Object> fields) throws BusinessException, BusinessLogicException
	{
		IncomeCondition condition = new IncomeCondition();

		//id интервала дохода
		condition.setIncomeId(incomeLevel.getId());

		//id условия
		if(!StringHelper.isEmpty((String)fields.get("conditionId")))
			condition.setId(Long.valueOf((String)fields.get("conditionId")));

		//валюта
		Currency currency;
		try{
			CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);
			currency = currencyService.findByNumericCode((String)fields.get("currencyNumber"));
			condition.setCurrency(currency);
		}
		catch(GateException e){
			throw new BusinessException(e);
		}

		//кредитный лимит
		CardAmountStep minCreditLimit = creditLimitService.getById(Long.valueOf((String)fields.get("minCreditLimitId")));
		CardAmountStep maxCreditLimit = creditLimitService.getById(Long.valueOf((String)fields.get("maxCreditLimitId")));
		if(minCreditLimit.getValue().getAsCents() >= maxCreditLimit.getValue().getAsCents())
			throw new BusinessLogicException("Вы неправильно указали кредитный лимит продукта. Пожалуйста, проверьте сумму минимального и максимального кредитного лимита. Минимальный лимит должен быть меньше максимального.");

		condition.setMinCreditLimit(minCreditLimit);
		condition.setMaxCreditLimit(maxCreditLimit);
		condition.setMaxCreditLimitInclude(Boolean.valueOf((String)fields.get("isMaxCreditLimitInclude")));

		return condition;
	}

	private void initCurrencies() throws BusinessException
	{
		try
		{
			CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);
			currencies.add(currencyService.findByAlphabeticCode("RUB"));
			currencies.add(currencyService.findByAlphabeticCode("USD"));
			currencies.add(currencyService.findByAlphabeticCode("EUR"));
		}
		catch(GateException e)
		{
			throw new BusinessException(e);
		}
	}

}
