package com.rssl.phizic.business.ant.pfp.dictionary;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.ant.pfp.dictionary.actions.PFPDictionaryConfig;
import com.rssl.phizic.gate.dictionaries.DictionaryRecord;

import java.util.Collection;

/**
 * @author akrenev
 * @ created 11.09.2012
 * @ $Author$
 * @ $Revision$
 *
 * интерфейс предоставл€ющий список записей определенного справочника ѕ‘ѕ
 */
public interface PFPDictionary<DR extends DictionaryRecord> 
{
	/**
	 * получить справочник запсей по типу справочника
	 * @param dictionaryConfig тип справочника
	 * @return справочник запсей
	 */
	public Collection<DR> getDictionary(PFPDictionaryConfig dictionaryConfig) throws BusinessException;
}
