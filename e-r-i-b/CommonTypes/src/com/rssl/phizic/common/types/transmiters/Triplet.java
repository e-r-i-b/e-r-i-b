package com.rssl.phizic.common.types.transmiters;

import java.io.Serializable;

/**
 * @author basharin
 * @ created 06.12.13
 * @ $Author$
 * @ $Revision$
 */

public class Triplet<T, S, K> implements Serializable
{
	private T first;
	private S second;
	private K third;

	public Triplet()
	{
		first = null;
		second = null;
		third = null;
	}

	public Triplet(T f, S s, K k)
	{
	    first = f;
	    second = s;
	    third = k;
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

	public K getThird()
	{
		return third;
	}

	public void setThird(K third)
	{
		this.third = third;
	}

	public String toString()
	{
	   return "(" + first + ", " + second + ", " + third + ")";
	}
}
