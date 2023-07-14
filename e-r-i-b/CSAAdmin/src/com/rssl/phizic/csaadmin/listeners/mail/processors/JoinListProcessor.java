package com.rssl.phizic.csaadmin.listeners.mail.processors;

import com.rssl.phizic.csaadmin.business.common.AdminException;
import com.rssl.phizic.csaadmin.listeners.mail.MultiNodeDataIterator;
import com.rssl.phizic.csaadmin.listeners.mail.MultiNodeRequestType;
import com.rssl.phizic.gate.multinodes.JoinMultiNodeEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author mihaylov
 * @ created 11.06.14
 * @ $Author$
 * @ $Revision$
 *
 * Процессор для склеивания списков данных из нескольких блоков.
 * Если в списках из нескольких блоков появятся одинаковые записи(compareTo == 0),
 * то необходимо склеить эти записи в одну через join.
 *
 * ВАЖНО!!! пока никак не завязан на пагинацию.
 * ВАЖНО!!! работает только на отсортированных наборах данных из блоков.
 */
public class JoinListProcessor extends MultiNodeDataProcessorBase
{

	/**
	 * Обработать данные из нескольких блоков.
	 * @param data - мапа с данными из нескольких блоков
	 * @param requestType - тип запроса
	 * @param <T> - тип данных
	 * @return список обработанных данных
	 * @throws AdminException
	 */
	public static <T extends JoinMultiNodeEntity> List<T> process(Map<Long,List<T>> data, MultiNodeRequestType requestType) throws AdminException
	{
		List<T> resultList = new ArrayList<T>();
		List<MultiNodeDataIterator<T>> iteratorList = convertToIteratorList(data);
		T lastEntity = null;
		while(iteratorList.size() > 0)
		{
			MultiNodeDataIterator<T> minIterator = iteratorList.get(0);
			for(int i=1; i<iteratorList.size(); i++)
			{
				MultiNodeDataIterator<T> currentIterator = iteratorList.get(i);
				int compareResult = currentIterator.getCursor().compareTo(minIterator.getCursor());
				if(compareResult < 0)
				{
					minIterator = currentIterator;
				}
			}
			T minEntity = minIterator.next();
			if(lastEntity != null && lastEntity.compareTo(minEntity) == 0)
			{
				lastEntity.join(minEntity);
			}
			else
			{
				lastEntity = minEntity;
				resultList.add(minEntity);
			}
			if(!minIterator.hasNext())
				iteratorList.remove(minIterator);
		}
		return resultList;
	}

}
