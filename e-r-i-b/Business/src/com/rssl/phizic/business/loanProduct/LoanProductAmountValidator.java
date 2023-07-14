package com.rssl.phizic.business.loanProduct;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.validators.MultiFieldsValidatorBase;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.loans.conditions.LoanCondition;
import com.rssl.phizic.business.loans.products.ModifiableLoanProductService;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @author Mescheryakova
 * @ created 21.06.2011
 * @ $Author$
 * @ $Revision$
 *
 * Проверка корректности введенной пользователем суммы в заявке на кредит с учетом данных кредита
 */

public class LoanProductAmountValidator extends MultiFieldsValidatorBase
{
	private static final String LOAN_PRODUCT_ID  = "loan";
	private static final String AMOUNT_FIELD     = "amount";
	private static final String CURRENCY = "currency";
	private static final BigDecimal MIN_VALUE = new BigDecimal(1);
	private static final BigDecimal MAX_VALUE = new BigDecimal(9999999);
	private static final ModifiableLoanProductService service = new ModifiableLoanProductService();

	private ThreadLocal<String> currentMessage  = new ThreadLocal<String>();

	public boolean validate(Map values) throws TemporalDocumentException
	{
		Long conditionId  = (Long) retrieveFieldValue(LOAN_PRODUCT_ID, values);
		BigDecimal amount  = (BigDecimal) retrieveFieldValue(AMOUNT_FIELD, values);
		String currency  = (String) retrieveFieldValue(CURRENCY, values);
		boolean isValidate = true;
		BigDecimal tempMinValue = MIN_VALUE;
		BigDecimal tempMaxValue = MAX_VALUE;

		try
		{
			LoanCondition loanCondition = service.findLoanConditionById(conditionId);

			if (loanCondition == null || !loanCondition.getCurrency().getCode().equals(currency))   // кредитного продукта уже нет, валюты не совпадают - значит ошибка
			{
				currentMessage.set("Нет кредитного продукта, или валюты не совпадают");
				return false;
			}
			if (loanCondition.getMinAmount() != null && loanCondition.getMaxAmount() != null)
			{
				tempMinValue = loanCondition.getMinAmount().getDecimal();
				tempMaxValue = loanCondition.getMaxAmount().getDecimal();
				if (!loanCondition.isMaxAmountInclude())
					tempMaxValue = tempMaxValue.subtract(new BigDecimal(1));
				isValidate = tempMinValue.compareTo(amount) <= 0 &&	tempMaxValue.compareTo(amount) >= 0;
			}
			else if (loanCondition.getMinAmount() != null)
			{
				tempMinValue = loanCondition.getMinAmount().getDecimal();
				isValidate = tempMinValue.compareTo(amount) <= 0;
			}
			else if (loanCondition.getMaxAmount() != null)
			{
				tempMaxValue = loanCondition.getMaxAmount().getDecimal();
				if (loanCondition.isMaxAmountInclude())
					tempMaxValue = tempMaxValue.subtract(MIN_VALUE);
				isValidate = tempMaxValue.compareTo(amount) >= 0;
			}
		}
		catch(BusinessException e)
		{
			 throw new TemporalDocumentException(e);
		}
		if (!isValidate)
			currentMessage.set(createMessage(tempMinValue, tempMaxValue));

		return isValidate;
	}

	public String getMessage()
	{
		return currentMessage.get();
	}

	private String createMessage(BigDecimal minValue, BigDecimal maxValue)
	{
		return "Вы неправильно указали сумму кредита. Минимальная сумма - " + minValue.setScale(0, BigDecimal.ROUND_HALF_UP) +
			   ", максимальная сумма - " + maxValue.setScale(0, BigDecimal.ROUND_HALF_UP);
	}
}
