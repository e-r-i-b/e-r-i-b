package com.rssl.phizic.gate.dictionaries.tests;

import com.rssl.phizic.gate.dictionaries.DictionaryRecord;

/**
 * @author Omeliyanchuk
 * @ created 07.02.2008
 * @ $Author$
 * @ $Revision$
 */

public class ReplicaTestData implements DictionaryRecord
{
	private Long value = null;
	public ReplicaTestData(Long value)
	{
		this.value = value;
	}

	public Comparable getSynchKey()
	{
		return value;
	}

	public void updateFrom(DictionaryRecord that)
	{
		
	}

	public void print()
	{
		System.out.println( Long.toString(value) );
	}
}
