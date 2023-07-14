package com.rssl.phizic.business.dictionaries.loanclaim.validators;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.loanclaim.dictionary.LoanClaimDictionaryService;
import com.rssl.phizic.gate.loanclaim.dictionary.AbstractDictionaryEntry;
import com.rssl.phizic.gate.loanclaim.dictionary.Street;
import com.rssl.phizic.gate.loanclaim.dictionary.TypeOfStreet;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Nady
 * @ created 02.12.2014
 * @ $Author$
 * @ $Revision$
 */

/**
 * Валидатор загрузки справочника "Улицы"
 */
public class DictionaryStreetValidator implements AddressDictionaryValidator
{
	private List<String> typeCodes = new ArrayList<String>();
	private LoanClaimDictionaryService dictionaryService = new LoanClaimDictionaryService();

	public DictionaryStreetValidator() throws BusinessException
	{
		for (TypeOfStreet typeOfStreet : dictionaryService.getTypeOfStreetDictionary())
		{
			typeCodes.add(typeOfStreet.getCode());
		}
	}

	public boolean validate(AbstractDictionaryEntry entry)
	{
		return typeCodes.contains(((Street) entry).getTypeOfStreet());
	}
}
