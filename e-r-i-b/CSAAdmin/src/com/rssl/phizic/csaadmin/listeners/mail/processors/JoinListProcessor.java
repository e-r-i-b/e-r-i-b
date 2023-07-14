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
 * ��������� ��� ���������� ������� ������ �� ���������� ������.
 * ���� � ������� �� ���������� ������ �������� ���������� ������(compareTo == 0),
 * �� ���������� ������� ��� ������ � ���� ����� join.
 *
 * �����!!! ���� ����� �� ������� �� ���������.
 * �����!!! �������� ������ �� ��������������� ������� ������ �� ������.
 */
public class JoinListProcessor extends MultiNodeDataProcessorBase
{

	/**
	 * ���������� ������ �� ���������� ������.
	 * @param data - ���� � ������� �� ���������� ������
	 * @param requestType - ��� �������
	 * @param <T> - ��� ������
	 * @return ������ ������������ ������
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
