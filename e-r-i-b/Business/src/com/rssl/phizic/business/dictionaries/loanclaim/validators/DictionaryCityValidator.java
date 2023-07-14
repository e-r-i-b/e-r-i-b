package com.rssl.phizic.business.dictionaries.loanclaim.validators;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.loanclaim.dictionary.LoanClaimDictionaryService;
import com.rssl.phizic.gate.loanclaim.dictionary.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Nady
 * @ created 02.12.2014
 * @ $Author$
 * @ $Revision$
 */

/**
 * ��������� �������� ����������� "������"
 */
public class DictionaryCityValidator implements AddressDictionaryValidator
{
	private List<String> typeCodes = new ArrayList<String>();
	private LoanClaimDictionaryService dictionaryService = new LoanClaimDictionaryService();

	public DictionaryCityValidator() throws BusinessException
	{
		for (TypeOfCity typeOfCity : dictionaryService.getTypeOfCityDictionary())
		{
			typeCodes.add(typeOfCity.getCode());
		}
	}

	public boolean validate(AbstractDictionaryEntry entry)
	{
		return typeCodes.contains(((City) entry).getTypeOfCity());
	}
}
