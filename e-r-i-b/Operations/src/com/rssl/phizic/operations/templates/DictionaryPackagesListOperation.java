package com.rssl.phizic.operations.templates;

import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.template.ClientsPackageTemplatesService;
import com.rssl.phizic.business.template.ClientsPackageTemplate;
import com.rssl.phizic.business.template.DublicatePackageNameException;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Novikov_A
 * Date: 16.02.2007
 * Time: 16:27:05
 * To change this template use File | Settings | File Templates.
 */
public class DictionaryPackagesListOperation  extends OperationBase implements ListEntitiesOperation
{
	private final static PersonService personService = new PersonService();
	private ClientsPackageTemplatesService clientsPackageService = new ClientsPackageTemplatesService();

	private ActivePerson person;

	public void initialize(Long personId) throws BusinessException
	{
		this.person = (ActivePerson) personService.findById( personId );
	}

	public ActivePerson getActivePerson() throws BusinessException
	{
		if(person == null)
			throw new BusinessException("Не установлен пользователь");

		return person;
	}

	public List<ClientsPackageTemplate> getClientsPackageTemplates() throws BusinessException
	{
		return  clientsPackageService.getClientsPackageById(person.getLogin());
	}

	public void addClientsPackage(ClientsPackageTemplate clientsPackageTemplate) throws DublicatePackageNameException, BusinessException
	{
		clientsPackageService.AddClientsPackage(clientsPackageTemplate);
	}

	public void editClientsPackage(ClientsPackageTemplate packages) throws BusinessException
	{
		clientsPackageService.EditClientsPackage(packages);
	}
}
