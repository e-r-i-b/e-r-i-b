package com.rssl.phizic.operations.person;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.business.operations.Transactional;

/**
 * @author Omeliyanchuk
 * @ created 24.07.2008
 * @ $Author$
 * @ $Revision$
 */

/**
 * ���������� �������� � �������������
 */
public class SignAgreementOperation extends EditPersonOperation
{
	private static final PersonService personService = new PersonService();

	/**
	 * ������� ��������
	 * @throws BusinessException
	 */
	@Transactional
	public void sign() throws BusinessException
	{
		Person person = getPerson();
		if(!person.getStatus().equals(Person.SIGN_AGREEMENT))
			throw new BusinessException("��� ���������� �������� ������������ ������ ���� �� �������� \"���������� ��������\", id =."+person.getId());

		person.setStatus(Person.TEMPLATE);
		personService.update(person);
	}

	/**
	 * ������� ������� ���������
	 * @throws BusinessException
	 */
	@Transactional
	public void returnToEdit() throws BusinessException
	{
		Person person = getPerson();
		if(!person.getStatus().equals(Person.TEMPLATE))
			throw new BusinessException("��� �������������� �������� ������������ ������ ���� �� �������� \"�����������\", id =."+person.getId());

		person.setStatus(Person.SIGN_AGREEMENT);
		personService.update(person);
	}
}
