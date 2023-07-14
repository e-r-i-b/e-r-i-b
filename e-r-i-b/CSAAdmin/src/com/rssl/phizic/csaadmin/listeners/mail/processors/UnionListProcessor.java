package com.rssl.phizic.csaadmin.listeners.mail.processors;

import com.rssl.phizic.csaadmin.business.common.AdminException;
import com.rssl.phizic.csaadmin.listeners.mail.MultiNodeDataIterator;
import com.rssl.phizic.gate.multinodes.MultiNodeEntityBase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author mihaylov
 * @ created 18.06.14
 * @ $Author$
 * @ $Revision$
 *
 * Процессор объединения данных из нескольких блоков.
 * Склеивает листы предварительно отсортированных данных сравнивая сущности между собой через compareTo.
 */
public class UnionListProcessor extends MultiNodeDataProcessorBase
{
	/**
	 * Обработать данные из нескольких блоков.
	 * @param data - мапа с данными из нескольких блоков
	 * @param <T> - тип данных
	 * @return список обработанных данных
	 * @throws com.rssl.phizic.csaadmin.business.common.AdminException
	 */
	public static <T extends MultiNodeEntityBase & Comparable> List<T> process(Map<Long,List<T>> data) throws AdminException
	{
		List<T> resultList = new ArrayList<T>();
		List<MultiNodeDataIterator<T>> iteratorList = convertToIteratorList(data);
		while(iteratorList.size() > 0)
		{
			MultiNodeDataIterator<T> minIterator = iteratorList.get(0);
			for(int i=1; i<iteratorList.size(); i++)
			{
				MultiNodeDataIterator<T> currentIterator = iteratorList.get(i);

				if(currentIterator.getCursor().compareTo(minIterator.getCursor())<0)
				{
					minIterator = currentIterator;
				}
			}
			T entity = (T)minIterator.next();
			entity.setNodeId(minIterator.getNodeId());
			resultList.add(entity);

			if(!minIterator.hasNext())
				iteratorList.remove(minIterator);
		}
		return resultList;
	}
}
