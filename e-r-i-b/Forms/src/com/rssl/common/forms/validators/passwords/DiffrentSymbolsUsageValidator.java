package com.rssl.common.forms.validators.passwords;

import com.rssl.common.forms.validators.FieldValidatorBase;
import com.rssl.common.forms.TemporalDocumentException;

/**
 * @author Roshka
 * @ created 28.03.2007
 * @ $Author$
 * @ $Revision$
 */

public class DiffrentSymbolsUsageValidator extends FieldValidatorBase
{
	private static final String MIN_AMOUNT_OF_DIFFRENT_SYMBOLS = "minAmountOfDiffrentSymbols";

	private int minAmountOfDiffrentSymbols = 4;

	public void setParameter(String name, String value)
	{
		if (name.equals(MIN_AMOUNT_OF_DIFFRENT_SYMBOLS))
		{
			minAmountOfDiffrentSymbols = Integer.parseInt(value);
		}
	}

	public String getParameter(String name)
	{
		if (name.equals(MIN_AMOUNT_OF_DIFFRENT_SYMBOLS))
		{
			return String.valueOf(minAmountOfDiffrentSymbols);
		}
		return null;
	}

	public boolean validate(String value) throws TemporalDocumentException
	{
		String noDuplicatesStr = removeDuplicates(value);
		return noDuplicatesStr.length() >= minAmountOfDiffrentSymbols;
	}

	private String removeDuplicates(String str)
	{
		String result = new String();
		int length = str.length();
		for (int i = 0; i < length; i++)
		{
			char ch = str.charAt(i);
			if (result.indexOf(ch) == -1)
				result += ch;
		}
		return result;
	}
}