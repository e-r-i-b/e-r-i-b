package com.rssl.phizic.csaadmin.listeners.mail.processors;

import com.rssl.phizic.csaadmin.listeners.mail.MultiNodeDataIterator;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author mihaylov
 * @ created 18.06.14
 * @ $Author$
 * @ $Revision$
 *
 * ��������� ��� ���������� ������� ������ �� ���������� ������.
 */
public abstract class MultiNodeDataProcessorBase
{

	/**
	 * ������������� ���� � ������� ������ �� ���������� ������ � ����� ���������� ���� ������
	 * @param data - ���� ������
	 * @param <T> - ��� ������ � �������� ��������
	 * @return ���� � ����������� ������ �� ���������� ������
	 */
	protected static <T> List<MultiNodeDataIterator<T>> convertToIteratorList(Map<Long,List<T>> data)
	{
		List<MultiNodeDataIterator<T>> iteratorList = new ArrayList<MultiNodeDataIterator<T>>();
		for(Map.Entry<Long,List<T>> entry: data.entrySet())
		{
			if(CollectionUtils.isNotEmpty(entry.getValue()))
			{
				iteratorList.add(new MultiNodeDataIterator<T>(entry.getValue(),entry.getKey()));
			}
		}
		return iteratorList;
	}
}
