package com.rssl.phizic.common.types.transmiters;

import java.io.Serializable;

/**
 * @ author: filimonova
 * @ created: 27.04.2010
 * @ $Author$
 * @ $Revision$
 */
public class Pair<T, S> implements Serializable
{
	private T first;
	private S second;

	public Pair()
	{
		first = null;
		second = null;
	}

	public Pair(T f, S s)
	{
	    first = f;
	    second = s;
	}

	public T getFirst()
	{
		return first;
	}

	public void setFirst(T first)
	{
		this.first = first;
	}

	public S getSecond()
	{
		return second;
	}

	public void setSecond(S second)
	{
		this.second = second;
	}

	public String toString()
	{
	   return "(" + first + ", " + second + ")";
	}
}
