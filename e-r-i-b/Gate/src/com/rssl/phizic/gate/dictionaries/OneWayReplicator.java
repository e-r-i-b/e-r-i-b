package com.rssl.phizic.gate.dictionaries;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import org.hibernate.exception.ConstraintViolationException;

import java.util.Comparator;
import java.util.Iterator;

/** Created by IntelliJ IDEA. User: Roshka Date: 25.10.2005 Time: 14:49:43 */
public class OneWayReplicator implements Replicator
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_GATE);

	private ReplicaSource source;
	private ReplicaDestination destination;
	private Comparator comparator;

	public OneWayReplicator(ReplicaSource source, ReplicaDestination destination, Comparator comparator)
	{
		this.source = source;
		this.destination = destination;
		this.comparator = comparator;
	}

	private Iterator<? extends DictionaryRecord> removeAllFromDestination(DictionaryRecord current, Iterator<? extends DictionaryRecord> iter) throws GateException
	{
		if( current != null)
			destination.remove( current);

		while( iter.hasNext() )
			destination.remove( iter.next() );

		return iter;
	}

	private Iterator<? extends DictionaryRecord> addAllToDestination(DictionaryRecord current, Iterator<? extends DictionaryRecord> iter) throws GateException
	{
		if( current != null)
			destination.add( current);

		while( iter.hasNext() )
			destination.add( iter.next() );

		return iter;
	}

	/**
	 * Реплицировать списки  
	 * @throws GateException
	 */
	public void replicate() throws GateException, GateLogicException
	{
		/*
			При изменении алгоритма:
			1. Повторить ошибочную операцию на тесте OneWayReplicatorTest.
			2. Исправить алгоритм.
			3. Проверить все ситуации из теста.
		 */
		try
		{
			Iterator<? extends DictionaryRecord> srcIter = source.iterator();
			Iterator<? extends DictionaryRecord> destIter = destination.iterator();

			Boolean needToReplicate = true;

			//todo. Очень узко. Переделать. Временное решение для Среднерусского банка.
			if (!srcIter.hasNext() && source.getClass().getName().equals("com.rssl.phizic.business.dictionaries.replication.DepartmentsReplicaSource"))
			{
				log.warn("Список подразделений во внешней системе пуст");
				needToReplicate = false;
			}

			DictionaryRecord dest = null;
			DictionaryRecord src = null;

			boolean stepSrc = srcIter.hasNext();
			boolean stepDest = destIter.hasNext();

			while( (destIter.hasNext() || srcIter.hasNext()) && needToReplicate)
			{
				if(stepSrc)
					src = srcIter.next();

				if(stepDest)
					dest = destIter.next();

				stepDest = false;
				stepSrc = false;
				
				//null одновременно быть не может, т.к. не попадем в while
				Comparable destKey = dest==null?null:dest.getSynchKey();
				Comparable srcKey = src==null?null:src.getSynchKey();

				int compareResult = 0;
				if(destKey != null && srcKey != null)
					compareResult = destKey.compareTo(srcKey);
				else if(srcKey == null)
					compareResult = -1;
				else compareResult = 1;
				
				if (compareResult > 0)
				{
					destination.add(src);
					if(!srcIter.hasNext())
						destIter = removeAllFromDestination(dest, destIter);
					else
						stepSrc = true;
					
				}
				else if (compareResult < 0)
				{
					destination.remove(dest);					
					if(!destIter.hasNext())
						srcIter = addAllToDestination(src, srcIter);
					else
						stepDest = true;
				}
				else
				{
					if (comparator.compare(src, dest) != 0)
						destination.update(dest, src);

					if(!srcIter.hasNext() && destIter.hasNext())
						destIter = removeAllFromDestination(null, destIter);

					if(!destIter.hasNext() && srcIter.hasNext())
						srcIter = addAllToDestination(null, srcIter);

					stepDest = true;
					stepSrc = true;
				}
			}
		}
		finally
		{
			try
			{
				source.close();
				destination.close();
			}
			catch (ConstraintViolationException e)
			{
				throw new GateException("Ошибка при добавлении/обновлении записи: идентифицирующие поля не уникальны или не заполнены", e);
			}
		}
	}
}
