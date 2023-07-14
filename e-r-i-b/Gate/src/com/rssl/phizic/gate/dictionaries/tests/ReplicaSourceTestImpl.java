package com.rssl.phizic.gate.dictionaries.tests;

import com.rssl.phizic.gate.dictionaries.ReplicaSource;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.GateFactory;

import java.util.Iterator;
import java.util.List;

/**
 * @author Omeliyanchuk
 * @ created 07.02.2008
 * @ $Author$
 * @ $Revision$
 */

public class ReplicaSourceTestImpl implements ReplicaSource<ReplicaTestData>
{
	private List<ReplicaTestData> list = null;

	public ReplicaSourceTestImpl(List<ReplicaTestData> list)
	{
		this.list = list;
	}

	public void close()
	{

	}

	public void initialize(GateFactory factory) throws GateException
	{
	}

	public Iterator<ReplicaTestData> iterator() throws GateException
	{
		return list.iterator();
	}

	void printInit()
	{
		System.out.println("Source");
		for (ReplicaTestData testData : list)
			testData.print();
	}

	public int getSize()
	{
		return list.size();
	}
}
