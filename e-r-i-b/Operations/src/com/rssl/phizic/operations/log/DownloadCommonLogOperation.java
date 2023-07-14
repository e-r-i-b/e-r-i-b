package com.rssl.phizic.operations.log;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.business.persons.PersonService;

/**
 * @author Erkin
 * @ created 04.04.2011
 * @ $Author$
 * @ $Revision$
 */
public class DownloadCommonLogOperation extends LogFilterOperationBase
{
	private static final PersonService personService = new PersonService();
	private static final DepartmentService departmentService = new DepartmentService();

	/**
	 * ������������, ���� �������� ����� ��������
	 */
	private Person clientPerson = null;	
	private static final String FIO = "fio";

	///////////////////////////////////////////////////////////////////////////

	/**
	 * ������������� ��������
	 * @param clientId - USER_ID �������, ���� �������� ����� ��������
	 */
	public void initialize(Long clientId) throws BusinessException
	{
		if (clientId != null && clientId > 0) {
			clientPerson = personService.findById(clientId);
			if (clientPerson == null)
				throw new BusinessException("�� ������ ������ � ID=" + clientId);
		}
	}

	public String getClientTB() throws BusinessException
	{
		return departmentService.getNumberTB(clientPerson.getDepartmentId());
	}

	public Person getClientPerson()
	{
		return clientPerson;
	}

	public Object getEntity()
	{
		return clientPerson;
	}

}
