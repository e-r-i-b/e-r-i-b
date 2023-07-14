package com.rssl.phizic.csaadmin.listeners.mail.processors;

import com.rssl.phizic.csaadmin.business.common.AdminException;
import com.rssl.phizic.csaadmin.listeners.generated.GetMultiNodeListParametersType;
import com.rssl.phizic.csaadmin.listeners.mail.MultiNodeDataIterator;
import com.rssl.phizic.csaadmin.listeners.mail.MultiNodeRequestType;
import com.rssl.phizic.csaadmin.listeners.mail.comparators.IncomeMailListEntityComparator;
import com.rssl.phizic.csaadmin.listeners.mail.comparators.MultiNodeEntityComparator;
import com.rssl.phizic.csaadmin.listeners.mail.comparators.OutcomeMailListEntityComparator;
import com.rssl.phizic.csaadmin.listeners.mail.comparators.RemovedMailListEntityComparator;
import com.rssl.phizic.dataaccess.query.OrderParameter;
import com.rssl.phizic.gate.multinodes.MultiNodeEntityBase;
import com.rssl.phizic.utils.xml.xstream.XStreamSerializer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author mihaylov
 * @ created 11.06.14
 * @ $Author$
 * @ $Revision$
 *  Процессор для склеивания списков данных из нескольких блоков с учетом OrderParameter.
 *  Склеивает данные слиянием. Если в списках есть одинаковвые данные, то они задублируются в итоговом списке.
 *
 *  ВАЖНО!!! работает только на отсортированных наборах данных из блоков.
 */
public class SortUnionListProcessor extends MultiNodeDataProcessorBase
{
	private static final Map<MultiNodeRequestType,MultiNodeEntityComparator> comparators = new HashMap<MultiNodeRequestType, MultiNodeEntityComparator>(3);

	static
	{
		comparators.put(MultiNodeRequestType.INCOME_MAIL,new IncomeMailListEntityComparator());
		comparators.put(MultiNodeRequestType.OUTCOME_MAIL,new OutcomeMailListEntityComparator());
		comparators.put(MultiNodeRequestType.REMOVED_MAIL,new RemovedMailListEntityComparator());
	}

	/**
	 * Обработать данные из нескольких блоков.
	 * @param data - мапа с данными из нескольких блоков
	 * @param parameters - параметры запроса.
	 * @param requestType - тип запроса
	 * @param <T> - тип данных
	 * @return список обработанных данных
	 * @throws AdminException
	 */
	public static <T extends MultiNodeEntityBase> List<T> process(Map<Long,List<T>> data, GetMultiNodeListParametersType parameters, MultiNodeRequestType requestType) throws AdminException
	{
		List<T> resultList = new ArrayList<T>();
		List<MultiNodeDataIterator<T>> iteratorList = convertToIteratorList(data);
		int size = parameters.getMaxResults();
		int firstResult = parameters.getFirstResult();
		MultiNodeEntityComparator<T> comparator = getComparator(requestType);
		int currentElement = 0;
		List<OrderParameter> orderParameters = (List<OrderParameter>) XStreamSerializer.deserialize(parameters.getOrderParameters());
		while(resultList.size() < size && iteratorList.size() > 0)
		{
			MultiNodeDataIterator<T> minIterator = iteratorList.get(0);
			for(int i=1; i<iteratorList.size(); i++)
			{
				MultiNodeDataIterator<T> currentIterator = iteratorList.get(i);
				if(comparator.compare(currentIterator.getCursor(), minIterator.getCursor(), orderParameters)<0)
				{
					minIterator = currentIterator;
				}
			}
			currentElement++;
			T entity = minIterator.next();
			if(firstResult < currentElement)
			{
				entity.setNodeId(minIterator.getNodeId());
				resultList.add(entity);
			}
			if(!minIterator.hasNext())
				iteratorList.remove(minIterator);
		}
		return resultList;
	}

	private static <T> MultiNodeEntityComparator<T> getComparator(MultiNodeRequestType requestType) throws AdminException
	{
		MultiNodeEntityComparator<T> comparator = comparators.get(requestType);
		if(comparator == null)
			throw new AdminException("Неизвестный тип запроса");
		return comparator;
	}

}
