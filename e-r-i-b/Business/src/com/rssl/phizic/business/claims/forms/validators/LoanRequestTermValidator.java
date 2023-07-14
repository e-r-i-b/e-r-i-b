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

import java.util.List;
import java.util.Map;

/**
 * @author gladishev
 * @ created 10.09.2008
 * @ $Author$
 * @ $Revision$
 */

public class LoanRequestTermValidator extends MultiFieldsValidatorBase
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	private static final LoanProductService loanProductService = new LoanProductService();

	private static final String LOAN_PRODUCT_FIELD_NAME  = "loan-product";
	private static final String REQUEST_TERM_FIELD_NAME  = "request-term";
	private static final String LOAN_OFFICE_FIELD_NAME   = "office";
	private static final String LOAN_CURRENCY_FIELD_NAME = "loan-currency";

	public boolean validate(Map values) throws TemporalDocumentException
	{
		CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);

		String productId  = (String) retrieveFieldValue(LOAN_PRODUCT_FIELD_NAME, values);
		String termStr = (String) retrieveFieldValue(REQUEST_TERM_FIELD_NAME, values);
		String officeId  = (String) retrieveFieldValue(LOAN_OFFICE_FIELD_NAME, values);
		String currencyId  = (String) retrieveFieldValue(LOAN_CURRENCY_FIELD_NAME, values);
		if (isEmpty(productId) || isEmpty(officeId) || isEmpty(currencyId) || termStr==null){
			return true;
		}

		int term  = Integer.parseInt(termStr);

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

			List<String> durations = product.getDurations(conditionId);

			for (String duration : durations)
			{
				String[] tmp = duration.split(":");
				if (term == Integer.parseInt(tmp[1]))
					return true;
			}

			return false;
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
