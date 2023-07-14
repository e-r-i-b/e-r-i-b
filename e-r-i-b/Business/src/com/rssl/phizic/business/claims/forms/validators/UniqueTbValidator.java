package com.rssl.phizic.business.claims.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.validators.MultiFieldsValidatorBase;


import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author komarov
 * @ created 10.09.2014
 * @ $Author$
 * @ $Revision$
 */
public class UniqueTbValidator extends MultiFieldsValidatorBase
{
	/**
	 *  онструктор
	 * @param message сообщение пользователю
	 */
	public UniqueTbValidator(String message)
	{
		super(message);
	}

	public boolean validate(Map values) throws TemporalDocumentException
	{
		String[] names = getBindingsNames();
		Set<Object> tbs = new HashSet<Object>();
		for (String name : names)
		{
			if (!tbs.add(retrieveFieldValue(name, values)))
				return false;
		}
		return true;
	}
}
