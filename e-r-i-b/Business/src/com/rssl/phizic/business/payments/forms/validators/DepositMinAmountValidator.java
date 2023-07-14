package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.common.forms.validators.MultiFieldsValidatorBase;
import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizic.business.deposits.DepositProductService;
import com.rssl.phizic.business.deposits.DepositProduct;
import com.rssl.phizic.business.BusinessException;

import java.util.Map;
import java.math.BigDecimal;

/**
 * @author osminin
 * @ created 06.12.2008
 * @ $Author$
 * @ $Revision$
 */

public class DepositMinAmountValidator extends MultiFieldsValidatorBase
{
	public static final String FIELD_CURRENCY     = "currency";
	public static final String FIELD_PRODUCT      = "product";
	public static final String FIELD_AMOUNT       = "amount";
	public static final String FIELD_PERIOD       = "period";

	public boolean validate(Map values) throws TemporalDocumentException
	{
		String depositProductId = (String) retrieveFieldValue(FIELD_PRODUCT, values);
		String currency = (String) retrieveFieldValue(FIELD_CURRENCY, values);
		String period = (String) retrieveFieldValue(FIELD_PERIOD, values);
		BigDecimal amount = (BigDecimal) retrieveFieldValue(FIELD_AMOUNT, values);

		DepositProductService depositProductService = new DepositProductService();
		try{
			DepositProduct depositProduct = depositProductService.findById(Long.parseLong(depositProductId));
			BigDecimal minAmount = depositProduct.getMinAmount(currency, period);
			return amount.compareTo(minAmount)>=0;
		} catch(BusinessException e){
			throw new RuntimeException(e);
		}
	}
}
