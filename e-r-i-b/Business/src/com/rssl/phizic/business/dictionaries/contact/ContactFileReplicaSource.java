package com.rssl.phizic.business.dictionaries.contact;

import com.rssl.phizic.business.dictionaries.config.Constants;
import com.rssl.phizic.business.dictionaries.config.DictionaryPathConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.dictionaries.DictionaryRecord;
import com.rssl.phizic.gate.exceptions.GateException;

import java.io.File;
import java.util.Comparator;
import java.util.Iterator;

/**
 * @author Roshka
 * @ created 28.11.2006
 * @ $Author$
 * @ $Revision$
 */

public abstract class ContactFileReplicaSource extends FoxProFileReplicaSource
{
	private String dbfName;
	private Comparator comparator;

	/**
	 * @param dbfName    имя dbf файла
	 * @param sortComparator компаратор для сортировки
	 */
	protected ContactFileReplicaSource(String dbfName, Comparator sortComparator)
	{
		this.comparator = sortComparator;
		this.dbfName = dbfName;
	}

	private String getContactPath() throws GateException
	{
		DictionaryPathConfig dictionaryPathConfig = ConfigFactory.getConfig(DictionaryPathConfig.class);
		String               contactPath          = dictionaryPathConfig.getPath(Constants.CONTACT_DICTIONARY);
		
		if (contactPath == null)
			throw new GateException("Не найдено свойство " + Constants.CONTACT_DICTIONARY);

		if (!contactPath.endsWith(File.separator))
			contactPath = contactPath + File.separator;

		return contactPath;
	}

	public Iterator<? extends DictionaryRecord> iterator() throws GateException
	{
		return dbfIterator(getContactPath() + dbfName, comparator);
	}
}