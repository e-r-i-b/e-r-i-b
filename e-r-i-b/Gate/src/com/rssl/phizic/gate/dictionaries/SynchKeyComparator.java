package com.rssl.phizic.gate.dictionaries;

import java.util.Comparator;

/**
 * @author Roshka
 * @ created 13.12.2006
 * @ $Author$
 * @ $Revision$
 */

public class SynchKeyComparator implements Comparator<DictionaryRecord>
{
	public int compare(DictionaryRecord o1, DictionaryRecord o2)
	{
		return o1.getSynchKey().compareTo(o2.getSynchKey());
	}
}