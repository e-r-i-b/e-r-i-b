package com.rssl.phizic.gate.dictionaries.tests;

import com.rssl.phizic.gate.dictionaries.ReplicaDestination;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.GateFactory;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * @author Omeliyanchuk
 * @ created 07.02.2008
 * @ $Author$
 * @ $Revision$
 */

public class ReplicaDestinationTestImpl implements ReplicaDestination<ReplicaTestData>
{
	private List<ReplicaTestData> list = null;
	private List<ReplicaTestData> listCopy = null;

	public ReplicaDestinationTestImpl(List<ReplicaTestData> list)
	{
		this.list = list;
		this.listCopy = new LinkedList<ReplicaTestData>(list);
	}

	public void add(ReplicaTestData newValue) throws GateException
	{
		if(search(newValue))
			throw new GateException("Попытка добавить существующее значение");
		else
			listCopy.add(newValue);
	}

	public void remove(ReplicaTestData oldValue) throws GateException
	{
		if(!search(oldValue))
			throw new GateException("Попытка удалить несуществующее значение");
		else
		{
			Iterator<ReplicaTestData> iter = listCopy.iterator();
			while(iter.hasNext())
			{
				ReplicaTestData data = iter.next();
				if(data.getSynchKey().equals(oldValue.getSynchKey()))
				{
					iter.remove();
					break;
				}
			}
		}
	}

	public void update(ReplicaTestData oldValue, ReplicaTestData newValue) throws GateException
	{
		if(!oldValue.getSynchKey().equals(newValue.getSynchKey()))
			throw new GateException("Ключи должны быть одинаковы");
	}

	public void initialize(GateFactory factory) throws GateException
	{
	}

	public Iterator iterator() throws GateException
	{
		return list.iterator();
	}

	public void close()
	{

	}

	private boolean search(ReplicaTestData value) throws GateException
	{
		for (ReplicaTestData testData : listCopy)
		{
			if(testData.getSynchKey().equals(value.getSynchKey()))
				return true;
		}
		return false;
	}

	void printInit()
	{
		System.out.println("Destination");
		for (ReplicaTestData testData : listCopy)
			testData.print();
	}

	void printResult()
	{
		System.out.println("Destination result");
		for (ReplicaTestData testData : listCopy)
			testData.print();
	}

	public int getSize()
	{
		return listCopy.size();
	}

	public List<String> getErrors()
	{
		return null;
	}
}
