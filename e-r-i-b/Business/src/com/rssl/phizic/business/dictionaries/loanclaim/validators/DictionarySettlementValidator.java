package com.rssl.phizic.business.dictionaries.loanclaim.validators;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.loanclaim.dictionary.LoanClaimDictionaryService;
import com.rssl.phizic.gate.loanclaim.dictionary.AbstractDictionaryEntry;
import com.rssl.phizic.gate.loanclaim.dictionary.Settlement;
import com.rssl.phizic.gate.loanclaim.dictionary.TypeOfLocality;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Nady
 * @ created 02.12.2014
 * @ $Author$
 * @ $Revision$
 */

/**
 * Валидатор загрузки справочника "Населенные пункты"
 */
public class DictionarySettlementValidator implements AddressDictionaryValidator
{
	private List<String> typeCodes = new ArrayList<String>();
	private LoanClaimDictionaryService dictionaryService = new LoanClaimDictionaryService();

	public DictionarySettlementValidator() throws BusinessException
	{
		for (TypeOfLocality typeOfSettlement : dictionaryService.getTypeOfLocalityDictionary())
		{
			typeCodes.add(typeOfSettlement.getCode());
		}
	}

	public boolean validate(AbstractDictionaryEntry entry)
	{
		return typeCodes.contains(((Settlement) entry).getTypeOfLocality());
	}
}
