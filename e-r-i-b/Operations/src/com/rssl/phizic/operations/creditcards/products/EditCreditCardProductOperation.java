package com.rssl.phizic.operations.creditcards.products;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.cardAmountStep.CardAmountStep;
import com.rssl.phizic.business.cardAmountStep.CardAmountStepService;
import com.rssl.phizic.business.creditcards.conditions.CreditCardCondition;
import com.rssl.phizic.business.creditcards.products.CreditCardProduct;
import com.rssl.phizic.business.creditcards.products.CreditCardProductService;
import com.rssl.phizic.business.loans.products.Publicity;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.loans.products.LoanProductHelper;
import com.rssl.phizic.utils.NumericUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * @author Dorzhinov
 * @ created 15.06.2011
 * @ $Author$
 * @ $Revision$
 */
public class EditCreditCardProductOperation extends OperationBase implements EditEntityOperation
{
	private static final CreditCardProductService creditCardProductService = new CreditCardProductService();
	private static final CardAmountStepService creditLimitService = new CardAmountStepService();

	private CreditCardProduct product;
	private List<Currency> currencies = new ArrayList<Currency>();

	public void initializeNew() throws BusinessException
	{
		product = new CreditCardProduct();
		initCurrencies();
	}

	public void initialize(Long productId) throws BusinessException
	{
		product = creditCardProductService.findById(productId);

		if (product == null)
			throw new BusinessException("Кредитный продукт с Id = " + productId + " не найден.");

		initCurrencies();
	}

	public CreditCardProduct getEntity()
	{
		return product;
	}

	public void save() throws BusinessException, BusinessLogicException
	{
		if(product.getPublicity() == Publicity.PUBLISHED && product.getConditions().isEmpty())
				throw new BusinessLogicException("Для опубликованного продукта должно быть задано хотя бы одно условие в разрезе валют.");
		
		setChangeDate(product);
		if(product.getId() == null)
			creditCardProductService.add(product);
		else
			creditCardProductService.update(product);
	}

	private void setChangeDate(CreditCardProduct product) throws BusinessException
	{
		if (NumericUtil.isEmpty(product.getId()))
		{
			product.setChangeDate(Calendar.getInstance());
			return;
		}
		
		CreditCardProduct old = creditCardProductService.findById(product.getId());
		if (!LoanProductHelper.loanCardProductEquals(product, old))
			product.setChangeDate(Calendar.getInstance());
	}

	public List<Currency> getCurrencies()
	{
		return currencies;
	}

	public List<CardAmountStep> getCreditLimits() throws BusinessException
	{
		return creditLimitService.getAll();
	}

	public CreditCardCondition createCondition(Map<String, Object> fields) throws BusinessException, BusinessLogicException
	{
		CreditCardCondition condition = new CreditCardCondition();

		//id продукта
		condition.setProductId(product.getId());

		//id условия
		condition.setId((Long)fields.get("conditionId"));

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
		CardAmountStep minCreditLimit = creditLimitService.getById((Long)fields.get("minCreditLimitId"));
		CardAmountStep maxCreditLimit = creditLimitService.getById((Long)fields.get("maxCreditLimitId"));
		if(minCreditLimit.getValue().getAsCents() >= maxCreditLimit.getValue().getAsCents())
			throw new BusinessLogicException("Вы неправильно указали кредитный лимит продукта. Пожалуйста, проверьте сумму минимального и максимального кредитного лимита. Минимальный лимит должен быть меньше максимального.");

		condition.setMinCreditLimit(minCreditLimit);
		condition.setMaxCreditLimit(maxCreditLimit);
		condition.setMaxCreditLimitInclude((Boolean)fields.get("isMaxCreditLimitInclude"));

		//% ставка
		condition.setInterestRate((BigDecimal)fields.get("interestRate"));

		//% ставка для по предодобренной карте
		condition.setOfferInterestRate((BigDecimal)fields.get("offerInterestRate"));



		//плата за годовое обслуживание
		condition.setFirstYearPayment(new Money((BigDecimal)fields.get("firstYearPayment"), currency));
		condition.setNextYearPayment(new Money((BigDecimal)fields.get("nextYearPayment"), currency));

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
