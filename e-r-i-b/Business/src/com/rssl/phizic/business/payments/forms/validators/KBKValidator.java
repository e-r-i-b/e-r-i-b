package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.common.forms.validators.FieldValidatorBase;
import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizic.business.dictionaries.kbk.KBKService;
import com.rssl.phizic.business.BusinessException;

/**
 * @author gladishev
 * @ created 18.06.2010
 * @ $Author$
 * @ $Revision$
 */
public class KBKValidator extends FieldValidatorBase
{

	private static final KBKService service = new KBKService();

	public boolean validate(String value) throws TemporalDocumentException
	{
		if(isValueEmpty(value))
            return true;

		try
		{
			if (service.findByCode(value)==null)
				return false;
		}
		catch (BusinessException e)
		{
			throw new TemporalDocumentException(e);
		}

		return true;
	}
}
