package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.common.forms.validators.MultiFieldsValidatorBase;
import com.rssl.common.forms.TemporalDocumentException;

import java.util.Map;
import java.math.BigDecimal;

/**
 * @author osminin
 * @ created 14.01.2009
 * @ $Author$
 * @ $Revision$
 */

public class AllAmountFieldRequiredValidator extends MultiFieldsValidatorBase
{
	public static final String FIELD_1 = "field_1";
    public static final String FIELD_2 = "field_2";

	public boolean validate(Map values) throws TemporalDocumentException
	{
		BigDecimal amount_1 = (BigDecimal) retrieveFieldValue(FIELD_1,values);
        BigDecimal amount_2 = (BigDecimal) retrieveFieldValue(FIELD_2,values);
		return (amount_1 != null || amount_2 != null);
	}
}
