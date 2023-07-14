package com.rssl.phizic.business.dictionaries.departments;

import com.rssl.phizic.gate.dictionaries.DictionaryRecord;
import com.rssl.phizic.gate.exceptions.GateException;

/**
 * @author Balovtsev
 * @version 19.04.13 14:31
 */
public class DepartmentsRecordingReplicaDestination extends PreClearReplicaDestinationBase
{
	private int loadedRecords;

	@Override
	protected String getDestinationEntityName()
	{
		return "DepartmentsRecodingTemporary";
	}

	@Override
	protected String getDestinationClearQuery()
	{
		return "com.rssl.phizic.business.dictionaries.departments.DepartmentsRecoding.clearTemporary";
	}

	@Override
	public void add(DictionaryRecord newValue) throws GateException
	{
		super.add(newValue);

		loadedRecords ++;
	}

	@Override
	public void close()
	{
		super.close();

		if (isTransactionComplete())
		{
			log.info("¬ременна€ таблица дл€ формировани€ справочника перекодировки подготовлена.  оличество записей в таблице - " + loadedRecords);
		}
	}
}
