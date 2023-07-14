package com.rssl.phizic.gate.dictionaries;

import java.util.Comparator;

/**
 * Компаратор для упорядочивания списка результатов синхронизации
 * @author Pankin
 * @ created 30.05.2011
 * @ $Author$
 * @ $Revision$
 */

public class SynchronizeResultsComparator implements Comparator<SynchronizeResult>
{
	public int compare(SynchronizeResult result1, SynchronizeResult result2)
	{
		if (result1.getDictionaryType() == DictionaryType.OTHER && result2.getDictionaryType() == DictionaryType.OTHER)
			return  0;
		if (result1.getDictionaryType() == DictionaryType.OTHER)
			return -1;
		if (result2.getDictionaryType() == DictionaryType.OTHER)
			return 1;
		return 0;
	}
}
