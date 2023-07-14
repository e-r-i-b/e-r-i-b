package com.rssl.phizic.business.dictionaries.finances;

import com.rssl.phizic.business.dictionaries.QueryReplicaDestinationBase;
import com.rssl.phizic.business.locale.MultiLocaleQueryHelper;
import com.rssl.phizic.dataaccess.DataAccessException;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.dataaccess.query.BeanQuery;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Gololobov
 * @ created 29.07.2011
 * @ $Author$
 * @ $Revision$
 */

public class CardOperationCategoryReplicaDestinations extends QueryReplicaDestinationBase<CardOperationCategory>
{
	//��������� �������� ����������� ����� � owner is null
	private Map<String,CardOperationCategory> operationCategoriesWithOwnerNull = null;

	public CardOperationCategoryReplicaDestinations()
	{
		super("com.rssl.phizic.business.dictionaries.finances.getOperationCategoriesWithOwnerNull",true);
	}

	public void initialize(GateFactory factory) throws GateException
	{
		//Key - CardOperationCategory.externalId, Value - CardOperationCategory
		if (this.operationCategoriesWithOwnerNull == null)
			this.operationCategoriesWithOwnerNull = loadAllOperationCategoriesWithOwnerNull();
	}

	/**
	 * �������� ���� ��������� �������� ������� ���� ������ ��� ������ "_Load3_Dictionaries"
	 * @throws GateException
	 */
	private Map<String,CardOperationCategory> loadAllOperationCategoriesWithOwnerNull() throws GateException
	{
		List<CardOperationCategory> categoriesList = null;
		try
		{
			BeanQuery query = MultiLocaleQueryHelper.getQuery("com.rssl.phizic.business.dictionaries.finances.getOperationCategoriesWithOwnerNull");
			categoriesList = query.executeListInternal();
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}

		Map<String,CardOperationCategory> operationCategoriesWithOwnerNull = new HashMap<String,CardOperationCategory>();
		for (CardOperationCategory category : categoriesList)
			operationCategoriesWithOwnerNull.put(category.getExternalId(),category);

		return operationCategoriesWithOwnerNull;
	}

	/**
	 * ���������� ����� ��������� ��������
	 * @param newValue - ����� ��������� ��������
	 * @throws GateException
	 */
	public void add(CardOperationCategory newValue) throws GateException
	{
		CardOperationCategory oldCategory = this.operationCategoriesWithOwnerNull.get(newValue.getExternalId());
		if (oldCategory != null)
			update(oldCategory,newValue);
		else
			super.add(newValue);
	}

	/**
	 * �������� ������ ������ ��������� ��������
	 * @param oldValue - ������ ��������� ��������
	 * @throws GateException
	 */
	public void remove(CardOperationCategory oldValue) throws GateException
	{
		//�� ������� ��������� ��������
	}
}
