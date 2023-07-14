package com.rssl.phizic.business.ant.pfp.dictionary;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.ant.pfp.dictionary.actions.PFPDictionaryConfig;
import com.rssl.phizic.business.dictionaries.pfp.common.PFPDictionaryRecord;

import java.util.Collection;
import java.util.List;

/**
 * @author akrenev
 * @ created 11.09.2012
 * @ $Author$
 * @ $Revision$
 * 
 * Справочники ПФП из бд
 */
public class DbPFPDictionary implements PFPDictionary<PFPDictionaryRecord>
{
	private static final SimpleService service = new SimpleService();
	private final String dbInstanceName;

	/**
	 * конструктор
	 * @param dbInstanceName имя инстанса БД
	 */
	public DbPFPDictionary(String dbInstanceName)
	{
		this.dbInstanceName = dbInstanceName;
	}

	public Collection<PFPDictionaryRecord> getDictionary(PFPDictionaryConfig dictionaryConfig) throws BusinessException
	{
		//noinspection unchecked
		return (List<PFPDictionaryRecord>) service.getAll(dictionaryConfig.getDictionaryClass(), dbInstanceName);
	}
}
