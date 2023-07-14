package com.rssl.phizic.csaadmin.listeners.mail.comparators;

import com.rssl.phizic.dataaccess.query.OrderParameter;

import java.util.List;

/**
 * @author mihaylov
 * @ created 02.06.14
 * @ $Author$
 * @ $Revision$
 *
 * ���������� �������� ���������� �� ������
 */
public abstract class MultiNodeEntityComparator<T>
{

	/**
	 * �������� ��� ������� �� ������ ���������� ����������
	 * @param entity1 - ������ ������
	 * @param entity2 - ������ ������
	 * @param orderParameters - ��������� ����������
	 * @return
	 *         -1 - ������ ������ ������
	 *          0 - ������� ����������
	 *          1 - ������ ������ ������
	 */
	public int compare(T entity1, T entity2, List<OrderParameter> orderParameters)
	{
		for(OrderParameter orderParameter : orderParameters)
		{
			int compareResult = compare(entity1, entity2, orderParameter);
			if(compareResult != 0)
				return compareResult;
		}
		return 0;
	}

	protected abstract int compare(T entity1, T entity2, OrderParameter orderParameter);


	protected int compare(Comparable entity1, Comparable entity2)
	{
		if(entity1 == null && entity2 == null)
			return 0;
		else if(entity1 == null)
			return -1;
		else if(entity2 == null)
			return 1;
		else
			return entity1.compareTo(entity2);
	}

}
