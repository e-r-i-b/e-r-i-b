package com.rssl.phizic.business.claims.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.validators.MultiFieldsValidatorBase;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.loans.products.LoanProduct;
import com.rssl.phizic.business.loans.products.LoanProductService;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.exceptions.IKFLException;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Map;

/**
 * @author Krenev
 * @ created 30.05.2008
 * @ $Author$
 * @ $Revision$
 */
public class LoanFirstAmountValidator extends MultiFieldsValidatorBase
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	private static final LoanProductService loanProductService = new LoanProductService();

	private static final String LOAN_PRODUCT_FIELD_NAME             = "loan-product";
	private static final String REQUEST_AMOUNT_FIELD_NAME           = "request-amount";
	private static final String FIRST_AMOUNT_FIELD_NAME           = "first-amount";
	private static final String LOAN_OFFICE_FIELD_NAME              = "office";
	private static final String LOAN_CURRENCY_FIELD_NAME            = "loan-currency";

	public static final String ERROR_RECV_CURRENCY = "Ошибка при получении валюты";

	public boolean validate(Map values) throws TemporalDocumentException
	{
		CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);

		String productId  = (String) retrieveFieldValue(LOAN_PRODUCT_FIELD_NAME, values);
		BigDecimal amount  = (BigDecimal) retrieveFieldValue(REQUEST_AMOUNT_FIELD_NAME, values);
		BigDecimal firstAmount  = (BigDecimal) retrieveFieldValue(FIRST_AMOUNT_FIELD_NAME, values);
		String officeId  = (String) retrieveFieldValue(LOAN_OFFICE_FIELD_NAME, values);
		String currencyId  = (String) retrieveFieldValue(LOAN_CURRENCY_FIELD_NAME, values);

		if (isEmpty(productId) || isEmpty(officeId) || amount==null || firstAmount==null){
			return true;
		}

		Currency currency = null;

		try
		{
			currency = currencyService.findByAlphabeticCode(currencyId);
		}
		catch (IKFLException ex)
		{
			log.error(ERROR_RECV_CURRENCY, ex);
			throw new TemporalDocumentException(ERROR_RECV_CURRENCY,ex);
		}

		try
		{
			LoanProduct product = loanProductService.findById(Long.valueOf(productId));

			String conditionId = product.getConditionId(officeId, currency);

			BigDecimal minAmountPercent = product.getMinInitialInstalment(conditionId);
			/*<Запрашиваемая сумма кредита>*<Процент первоначального взноса по кредиту>/(100%-<Процент первоначального взноса по кредиту>)*/
			return (amount.multiply(minAmountPercent)).divide(BigDecimal.ONE.subtract(minAmountPercent), new MathContext(8)).compareTo(firstAmount)<=0;
		}
		catch (BusinessException e)
		{
			return false;
		}
	}

	private boolean isEmpty(String value)
	{
		return value==null || value.length()==0;
	}
}
