package com.rssl.phizic.gate.dictionaries.tests;

import java.util.Comparator;

/**
 * @author Omeliyanchuk
 * @ created 07.02.2008
 * @ $Author$
 * @ $Revision$
 */

public class ComparatorTestImpl implements Comparator<ReplicaTestData>
{
	public int compare(ReplicaTestData o1, ReplicaTestData o2)
	{
		return o1.getSynchKey().compareTo(o2.getSynchKey());
	}
}
