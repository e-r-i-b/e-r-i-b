package com.rssl.phizic.operations.person;

import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonOperationMode;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.operations.restrictions.UserRestriction;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.dataaccess.DataAccessException;
import com.rssl.phizic.operations.ListEntitiesOperation;

import java.util.List;
import java.util.ArrayList;

/**
 * @author Evgrafov
 * @ created 24.07.2006
 * @ $Author: khivin $
 * @ $Revision: 4595 $
 */

public class GetEmpoweredPersonsListOperation extends PersonOperationBase implements ListEntitiesOperation<UserRestriction>
{
	public void initialize(Long trustingPersonId) throws BusinessException, BusinessLogicException
	{
		setPersonId(trustingPersonId);

		PersonOperationBase.checkRestriction(getRestriction(), getPerson());

		if(getPerson().getTrustingPersonId() != null)
			throw new BusinessLogicException("ѕользователь id: " + trustingPersonId + " не может передовер€ть");
	}

	public Query createQuery(String name)
	{
		Query query = super.createQuery(name);
		query.setFilterRestriction(getRestriction());
		return query;
	}

	Long getTrustingPersonId()
	{
		return getPerson().getId();
	}

	/**
	 * ѕолучить список представителей клиента, т.к. в теневую копируем только активных,
	 * то склеиваем список из двух.
	 * @return
	 * @throws BusinessException
	 * @throws DataAccessException
	 */
	public List<ActivePerson> getList() throws BusinessException, DataAccessException
	{
		List<ActivePerson> result = new ArrayList<ActivePerson>();

		if(PersonOperationMode.shadow.equals(getMode()))
		{
		    List<ActivePerson> listShadow= createQuery("list").<ActivePerson>executeList();
			setMode(PersonOperationMode.direct);
			List<ActivePerson> listDirect= createQuery("list.not.active").<ActivePerson>executeList();
			setMode(PersonOperationMode.shadow);
			result.addAll(listShadow);
			result.addAll(listDirect);
		}
		else
		{
			result.addAll( createQuery("list").<ActivePerson>executeList());
		}

		return result;
	}
}
