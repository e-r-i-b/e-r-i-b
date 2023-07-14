package com.rssl.phizic.business.dictionaries.loanclaim.validators;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.loanclaim.dictionary.LoanClaimDictionaryService;
import com.rssl.phizic.gate.loanclaim.dictionary.AbstractDictionaryEntry;
import com.rssl.phizic.gate.loanclaim.dictionary.Area;
import com.rssl.phizic.gate.loanclaim.dictionary.TypeOfArea;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Nady
 * @ created 02.12.2014
 * @ $Author$
 * @ $Revision$
 */

/**
 * Валидатор загрузки справочника "Районы"
 */
public class DictionaryAreaValidator implements AddressDictionaryValidator
{
	private List<String> typeCodes = new ArrayList<String>();
	private LoanClaimDictionaryService dictionaryService = new LoanClaimDictionaryService();

	public DictionaryAreaValidator() throws BusinessException
	{
		for (TypeOfArea typeOfArea : dictionaryService.getTypeOfAreaDictionary())
		{
			typeCodes.add(typeOfArea.getCode());
		}
	}

	public boolean validate(AbstractDictionaryEntry entry)
	{
		return typeCodes.contains(((Area) entry).getTypeOfArea());
	}
}
