package com.rssl.phizic.business.claims.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.validators.MultiFieldsValidatorBase;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.loans.products.LoanProduct;
import com.rssl.phizic.business.loans.products.LoanProductService;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.exceptions.IKFLException;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @author Krenev
 * @ created 30.05.2008
 * @ $Author$
 * @ $Revision$
 */
public class LoanRequestAmountValidator extends MultiFieldsValidatorBase
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	private static final LoanProductService loanProductService = new LoanProductService();

	private static final String LOAN_PRODUCT_FIELD_NAME             = "loan-product";
	private static final String REQUEST_AMOUNT_FIELD_NAME           = "request-amount";
	private static final String LOAN_OFFICE_FIELD_NAME              = "office";
	private static final String LOAN_CURRENCY_FIELD_NAME            = "loan-currency";

	public boolean validate(Map values) throws TemporalDocumentException
	{
		CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);

		String productId  = (String) retrieveFieldValue(LOAN_PRODUCT_FIELD_NAME, values);
		BigDecimal amount  = (BigDecimal) retrieveFieldValue(REQUEST_AMOUNT_FIELD_NAME, values);
		String officeId  = (String) retrieveFieldValue(LOAN_OFFICE_FIELD_NAME, values);
		String currencyId  = (String) retrieveFieldValue(LOAN_CURRENCY_FIELD_NAME, values);
		if (isEmpty(productId) || isEmpty(officeId) || isEmpty(currencyId) || amount==null){
			return true;
		}

		Currency currency = null;

		try
		{
			currency = currencyService.findByAlphabeticCode(currencyId);
		}
		catch (IKFLException ex)
		{
			log.error(LoanFirstAmountValidator.ERROR_RECV_CURRENCY, ex);
			throw new TemporalDocumentException(LoanFirstAmountValidator.ERROR_RECV_CURRENCY,ex);
		}

		try
		{
			LoanProduct product = loanProductService.findById(Long.valueOf(productId));

			String conditionId = product.getConditionId(officeId, currency);

			Money minAmount = product.getMinAmount(conditionId);
			Money maxAmount = product.getMaxAmount(conditionId);
			return minAmount.getDecimal().compareTo(amount)<=0 && maxAmount.getDecimal().compareTo(amount)>=0;
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
