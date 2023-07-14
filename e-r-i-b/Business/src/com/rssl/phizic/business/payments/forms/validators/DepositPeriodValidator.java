package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.common.forms.validators.MultiFieldsValidatorBase;
import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizic.business.deposits.DepositProductService;
import com.rssl.phizic.business.deposits.DepositProduct;
import com.rssl.phizic.business.BusinessException;

import java.util.Map;
import java.util.List;

/**
 * @author osminin
 * @ created 08.12.2008
 * @ $Author$
 * @ $Revision$
 */

public class DepositPeriodValidator extends MultiFieldsValidatorBase
{
	public static final String FIELD_DEPOSIT = "product";
	public static final String FIELD_CURRENCY = "currency";
	public static final String FIELD_PERIOD = "period";

	public boolean validate(Map values) throws TemporalDocumentException
	{
		String depositProductId = (String) retrieveFieldValue(FIELD_DEPOSIT, values);
		String currency = (String) retrieveFieldValue(FIELD_CURRENCY, values);
		String period = (String) retrieveFieldValue(FIELD_PERIOD, values);
		DepositProductService depositProductService = new DepositProductService();
		try{
			DepositProduct depositProduct = depositProductService.findById(Long.parseLong(depositProductId));
			List<String> periods = depositProduct.getPeriods(currency);
			return periods.contains(period);
		}catch(BusinessException e){
	    	throw new RuntimeException(e);
		}
	}
}
