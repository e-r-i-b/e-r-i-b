package com.rssl.phizic.common.types.collection;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.EmptyStackException;

/**
 * @author Erkin
 * @ created 24.12.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * Реализация стека на базе связанного списка
 */
public class LinkedListStack<E> extends LinkedList<E> implements Stack<E>
{
	public void push(E elem)
	{
		addFirst(elem);
	}

	public E pop() throws EmptyStackException
	{
		try
		{
			return removeFirst();
		}
		catch (NoSuchElementException ignored)
		{
			//noinspection ThrowInsideCatchBlockWhichIgnoresCaughtException
			throw new EmptyStackException();
		}
	}

	@Override
	public E peek()
	{
		try
		{
			return super.peek();
		}
		catch (NoSuchElementException ignored)
		{
			return null;
		}
	}

	@Override
	public Object clone()
	{
		return super.clone();
	}
}
