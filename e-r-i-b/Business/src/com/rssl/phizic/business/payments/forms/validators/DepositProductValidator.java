package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.common.forms.validators.MultiFieldsValidatorBase;
import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizic.business.deposits.DepositProductService;
import com.rssl.phizic.business.deposits.DepositProduct;
import com.rssl.phizic.business.BusinessException;

import java.util.Map;

/**
 * @author osminin
 * @ created 04.12.2008
 * @ $Author$
 * @ $Revision$
 */

public class DepositProductValidator extends MultiFieldsValidatorBase
{
	public static final String FIELD_DEPOSIT = "product";

	public boolean validate(Map values) throws TemporalDocumentException
	{
		String depositProductId = (String) retrieveFieldValue(FIELD_DEPOSIT, values);
		DepositProductService depositProductService = new DepositProductService();
		try{
			DepositProduct depositProduct = depositProductService.findById(Long.parseLong(depositProductId));
			return depositProduct != null;
		}catch(BusinessException e){
			throw new RuntimeException(e);
		}
	}
}
