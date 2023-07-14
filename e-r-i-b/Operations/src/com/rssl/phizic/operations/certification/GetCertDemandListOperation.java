package com.rssl.phizic.operations.certification;

import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.business.certification.CertDemand;
import com.rssl.phizic.business.certification.CertDemandService;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.operations.restrictions.UserRestriction;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.ListEntitiesOperation;

import java.util.List;

/**
 * Created by IntelliJ IDEA. User: Omeliyanchuk Date: 17.11.2006 Time: 12:12:35 To change this template use
 * File | Settings | File Templates.
 */
public class GetCertDemandListOperation extends OperationBase<UserRestriction> implements ListEntitiesOperation<UserRestriction>
{
	private static final PersonService personService = new PersonService();
	private static final CertDemandService demandService = new CertDemandService();
	private ActivePerson person;

	public void initialize(ActivePerson person) throws BusinessException
	{
		this.person = person;
	}

	public void initialize(CommonLogin login) throws BusinessException
	{
		this.person = personService.findByLoginId(login.getId());
	}

	public List<CertDemand> getPersonCertDemands() throws BusinessException
	{
		checkPerson();
		try
		{
			return demandService.getPersonsCertDemands( person.getLogin() );
		}
		catch( SecurityException ex)
		{
			throw new BusinessException(ex);
		}
	}

	void checkPerson() throws BusinessException
	{
		if(person == null)
			throw new BusinessException("Не установлен пользователь");
	}
}
