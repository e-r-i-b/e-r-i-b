package com.rssl.phizic.csaadmin.listeners.mail;

import java.util.Iterator;
import java.util.List;

/**
 * @author mihaylov
 * @ created 26.05.14
 * @ $Author$
 * @ $Revision$
 *
 * �������� � ������������ ��������� �������� ��������.
 */
public class MultiNodeDataIterator<E> implements Iterator
{
	private Iterator<E> iterator;
	private E cursor;
	private Long nodeId;

	/**
	 * �����������
	 * @param list - ���� ��� ������������
	 * @param nodeId - ������������� ����� � ������� ��������� ������
	 */
	public MultiNodeDataIterator(List<E> list, Long nodeId)
	{
		iterator = list.iterator();
		cursor = iterator.next();
		this.nodeId = nodeId;
	}

	public boolean hasNext()
	{
		return cursor != null;
	}

	public E next()
	{
		E current = cursor;
		if(iterator.hasNext())
			cursor = iterator.next();
		else
			cursor = null;
		return current;
	}

	public void remove()
	{
		throw new UnsupportedOperationException("���������������� �����");
	}

	/**
	 * @return ���������� ������� ������� ��� �������� � ����������
	 */
	public E getCursor()
	{
		return cursor;
	}

	/**
	 * @return ������������� ����� � �������� ��������� ������
	 */
	public Long getNodeId()
	{
		return nodeId;
	}
}
