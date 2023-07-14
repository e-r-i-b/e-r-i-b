package com.rssl.phizic.business.ant.pfp.dictionary;

import com.rssl.phizic.business.dictionaries.pfp.common.PFPDictionaryRecord;
import com.rssl.phizic.gate.dictionaries.DictionaryRecord;

/**
 * @author akrenev
 * @ created 11.09.2012
 * @ $Author$
 * @ $Revision$
 *
 * ќболочка дл€ записи справочника
 * необходима т.к. картинки не €вл€ютс€ частью сущности,
 * а сохран€ть картинки до сохранени€ объектов не корректно
 */
public class PFPDictionaryRecordWrapper<R extends PFPDictionaryRecord> extends PFPDictionaryRecord
{
	private final R record;    //запись

	/**
	 * инициализаци€ записью (соответствующей картинки не задано)
	 * @param record запись
	 */
	public PFPDictionaryRecordWrapper(R record)
	{
		this.record = record;
	}

	/**
	 * @return запись
	 */
	public R getRecord()
	{
		return record;
	}

	public Comparable getSynchKey()
	{
		return record.getSynchKey();
	}

	public void updateFrom(DictionaryRecord that)
	{
		record.updateFrom(that);
	}
}
