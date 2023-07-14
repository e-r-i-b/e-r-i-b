package com.rssl.phizic.business.claims.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;

import java.util.Map;

/**
 * @author akrenev
 * @ created 05.08.2011
 * @ $Author$
 * @ $Revision$
 */
public class SecurityRegistrationClaimClientOperationsValidator  extends RequiredMultiFieldValidator
{
	private static final String DELIMITER = "\\|";

	public boolean validate(Map values) throws TemporalDocumentException
	{
		String[] names = getBindingsNames();
		for (String name : names)
		{
			String clientOperations = (String) retrieveFieldValue(name, values);
			if (!(clientOperations.matches("(" + DELIMITER + "[A-Z,a-z,À-ß,à-ÿ,0-9,!,?," + '"' + ", ,(,),\\.,\\,,\\-]{1,50}){1,3}" + DELIMITER)))
			{
				return false;
			}
			String[] clientOperationsArray = clientOperations.substring(1,clientOperations.length()-1).split(DELIMITER);
			for (String operation: clientOperationsArray)
			{
				if (operation == null || operation.trim().length() == 0)
				{
					return false;
				}
			}
			return true;
		}
		return false;
	}
}
