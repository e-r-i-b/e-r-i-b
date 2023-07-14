package com.rssl.phizic.operations.dictionaries;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.access.AccessException;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.ExternalResourceService;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.context.PersonDataProvider;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.logging.LogThreadContext;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.OperationBase;

import java.util.List;

/**
 * @author Omeliyanchuk
 * @ created 07.02.2007
 * @ $Author$
 * @ $Revision$
 */

/**
 * ��������� ������ ������ ������������
 */
public class GetAccountsListOperation extends OperationBase implements ListEntitiesOperation
{
	private static final PersonService personService = new PersonService();
	private static final ExternalResourceService externalResourceService = new ExternalResourceService();

	private ActivePerson person;

	/**
	 * ������������ � ���������� ����������
 	 * @throws BusinessException
	 */
	public void initialize() throws BusinessException
	{
		PersonDataProvider provider   = PersonContext.getPersonDataProvider();
		PersonData personData = provider.getPersonData();
		Login login = personData.getPerson().getLogin();
		person = personService.findByLogin(login);
	}

	/**
	 * � ������ ���� �������� id �������
	 * @param personId id �������
	 * @throws BusinessException
	 */
	public void initialize(long personId) throws BusinessException
	{
		if (LogThreadContext.getApplication() == Application.PhizIA)
		{
			person = (ActivePerson) personService.findById(personId);
		}
		else
		{
			//���� ��������� � ���������� ����������, �� personId ������������ �� ������ 
			throw new AccessException("���������� �������� ������ ������.");
		}
	}

	/**
	 * ���������� ������� ������ �������
 	 * @return   ������� ������ �������
	 * @throws BusinessException
	 */
	public List<AccountLink> getAccountLinks() throws BusinessException, BusinessLogicException
	{
		if( person == null )
			throw new BusinessException("�� �������� �������� ������ ������:�� ���������� ������");

		if( person.getLogin() == null )
			throw new BusinessException("�� �������� �������� ������ ������:�� ���������� login �������");		

		return externalResourceService.getLinks(person.getLogin(), AccountLink.class);
	}

}
