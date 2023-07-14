package com.rssl.phizic.dataaccess.query;

import com.rssl.phizic.dataaccess.DataAccessException;

import java.util.*;

/**
 * @author Krenev
 * @ created 09.02.2009
 * @ $Author$
 * @ $Revision$
 */
class QueryPaginalDataSource<E> implements PaginalDataSource<E>
{
	private Integer offset;
	private Integer size;
	private List<OrderParameter> orderParameters;
	private InternalQuery query;
	private List<E> data;

	QueryPaginalDataSource(InternalQuery query)
	{
		this.query = query;
	}

	public void setOffset(int value)
	{
		offset = value;
	}

	public void setSize(int value)
	{
		size = value;
	}

	public void setOrderParameters(List<OrderParameter> value)
	{
		orderParameters = value;
	}

	public int getAllItemsCount()
	{
		throw new UnsupportedOperationException("realise me!");//TODO реализовать
	}

	protected List<E> prepareData()
	{
		if (data != null)
		{
			return data;
		}
		if (offset != null)
		{
			query.setFirstResult(offset);
		}
		if (size != null)
		{
			query.setMaxResults(size);
		}
		if(orderParameters != null)
		{
			query.setOrderParameterList(orderParameters);
		}
		try
		{
			data = query.executeListInternal();
		}
		catch (DataAccessException e)
		{
			throw new RuntimeException(e);
		}
		return data;
	}

	public int size()
	{
		return prepareData().size();
	}

	protected Integer getOffset()
	{
		return offset;
	}

	protected Integer getSize()
	{
		return size;
	}

	protected List<OrderParameter> getOrderParameters()
	{
		return Collections.unmodifiableList(orderParameters);
	}

	protected InternalQuery getQuery()
	{
		return query;
	}

	public boolean isEmpty()
	{
		return prepareData().isEmpty();
	}

	public boolean contains(Object o)
	{
		return prepareData().contains(o);
	}

	public Iterator<E> iterator()
	{
		return prepareData().iterator();
	}

	public Object[] toArray()
	{
		return prepareData().toArray();
	}

	public <T> T[] toArray(T[] a)
	{
		return prepareData().toArray(a);
	}

	public boolean add(E o)
	{
		throw new UnsupportedOperationException();
	}

	public boolean remove(Object o)
	{
		throw new UnsupportedOperationException();
	}

	public boolean containsAll(Collection<?> c)
	{
		return prepareData().containsAll(c);
	}

	public boolean addAll(Collection<? extends E> c)
	{
		throw new UnsupportedOperationException();
	}

	public boolean addAll(int index, Collection<? extends E> c)
	{
		throw new UnsupportedOperationException();
	}

	public boolean removeAll(Collection<?> c)
	{
		throw new UnsupportedOperationException();
	}

	public boolean retainAll(Collection<?> c)
	{
		throw new UnsupportedOperationException();
	}

	public void clear()
	{
		throw new UnsupportedOperationException();
	}

	public E get(int index)
	{
		return prepareData().get(index);
	}

	public E set(int index, E element)
	{
		throw new UnsupportedOperationException();
	}

	public void add(int index, E element)
	{
		throw new UnsupportedOperationException();
	}

	public E remove(int index)
	{
		throw new UnsupportedOperationException();
	}

	public int indexOf(Object o)
	{
		return prepareData().indexOf(o);
	}

	public int lastIndexOf(Object o)
	{
		return prepareData().lastIndexOf(o);
	}

	public ListIterator<E> listIterator()
	{
		return prepareData().listIterator();
	}

	public ListIterator<E> listIterator(int index)
	{
		return prepareData().listIterator(index);
	}

	public List<E> subList(int fromIndex, int toIndex)
	{
		return prepareData().subList(fromIndex, toIndex);
	}
}
