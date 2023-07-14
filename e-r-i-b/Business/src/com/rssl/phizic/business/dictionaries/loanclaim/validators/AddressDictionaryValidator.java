package com.rssl.phizic.business.dictionaries.loanclaim.validators;

import com.rssl.phizic.gate.loanclaim.dictionary.AbstractDictionaryEntry;

/**
 * @author Nady
 * @ created 02.12.2014
 * @ $Author$
 * @ $Revision$
 */

/**
 * Интерфейс валидатора для загрузки справочников Заявки на кредит
 */
public interface AddressDictionaryValidator
{
	public  boolean validate(AbstractDictionaryEntry entry);
}
