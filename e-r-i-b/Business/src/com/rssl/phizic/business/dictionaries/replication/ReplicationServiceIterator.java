package com.rssl.phizic.business.dictionaries.replication;

import com.rssl.phizic.gate.dictionaries.ReplicationService;
import com.rssl.phizic.gate.dictionaries.DictionaryRecord;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.ArrayList;

/**
 * @author Krenev
 * @ created 27.05.2009
 * @ $Author$
 * @ $Revision$
 */
class ReplicationServiceIterator<E extends DictionaryRecord> implements Iterator<E>
{

	private int firstResult;
	private int pageSize;
	private Iterator<E> bufferIterator;
	private E nextElement;
	private int currentIteration;
	private int actualSize;
	private static final int UNDEFINED = -1;
	private ReplicationService<E> replicationService;
	private E template;

	ReplicationServiceIterator(ReplicationService<E> replicationService)
	{
		this(replicationService, 1000);
	}

	ReplicationServiceIterator(ReplicationService<E> replicationService, E template)
	{
		this(replicationService, 1000);
		this.template = template;
	}

	ReplicationServiceIterator(ReplicationService<E> replicationService, int pageSize)
	{
		this.replicationService = replicationService;
		this.pageSize = pageSize;
	}

	private void shureInitialized() throws GateLogicException, GateException
	{
		if (bufferIterator == null)
		{
			readFirst();
		}
	}

	public boolean hasNext()
	{
		try
		{
			shureInitialized();
			return hasNextInternal();
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	private boolean hasNextInternal()
	{
		return nextElement != null && ((actualSize == UNDEFINED) || (actualSize > pageSize) || (currentIteration < pageSize));
	}

	public E next()
	{
		try
		{
			shureInitialized();
			if (hasNextInternal())
			{
				E current = nextElement;
				currentIteration++;

				if (bufferIterator.hasNext() && currentIteration < pageSize)
				{
					nextElement = bufferIterator.next();
				}
				else
				{
					readNext();
				}

				return current;
			}
			else
			{
				throw new NoSuchElementException();
			}
		}
		catch (GateLogicException e)
		{
			throw new RuntimeException(e);
		}
		catch (GateException e)
		{
			throw new RuntimeException(e);
		}
	}

	public void remove()
	{
		throw new UnsupportedOperationException();
	}

	private void readNext() throws GateLogicException, GateException
	{
		firstResult += pageSize;
		readNextInternal();
	}

	private void readNextInternal() throws GateLogicException, GateException
	{
		currentIteration = 0;
		List<E> list;
		//получаем на единицу больше, дабы узнать есть ли еще.
		if (template!=null)
		{
			list = replicationService.getAll(template, firstResult, pageSize + 1);
		}
		else
		{
			list = replicationService.getAll(firstResult, pageSize + 1);
		}
		actualSize = list.size();
		bufferIterator = list.iterator();

		if (bufferIterator.hasNext())
		{
			nextElement = bufferIterator.next();
		}
		else
		{
			nextElement = null;
		}
	}

	private void readFirst() throws GateLogicException, GateException
	{
		firstResult = 0;
		actualSize = UNDEFINED;
		readNextInternal();
	}
}
