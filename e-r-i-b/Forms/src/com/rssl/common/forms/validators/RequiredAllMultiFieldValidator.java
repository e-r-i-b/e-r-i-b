package com.rssl.common.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;

import java.util.Map;

/**  Определяет, все ли поля имеют непустое (!= null) значение
 * @author akrenev
 * @ created 12.01.2012
 * @ $Author$
 * @ $Revision$
 */
public class RequiredAllMultiFieldValidator extends MultiFieldsValidatorBase
{
	public boolean validate(Map values) throws TemporalDocumentException
	{
		String[] names = getBindingsNames();
		for (String name : names)
		{
			if (retrieveFieldValue(name, values) == null)
				return false;
		}
		return true;
	}
}
