package com.rssl.phizic.operations.ext.sbrf.person;

import com.rssl.phizic.business.persons.*;
import com.rssl.phizic.business.persons.dbserialize.CopyPersonHelper;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.person.PersonOperationBase;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.person.Person;

/**
 * Created by IntelliJ IDEA.
 * @author mihaylov
 * created 09.09.2008
 * @ $Author$
 * @ $Revision$
 */
public class DeleteEmpoweredPersonOperation extends RemovePersonOperation
{
	private static MultiInstancePersonService personService = new MultiInstancePersonService();
	private ActivePerson empoweredPerson;

	public ActivePerson getEntity()
	{
		return empoweredPerson;
	}

	public void initialize(Long personId, Long trustingPersonId) throws BusinessException, BusinessLogicException
	{
		setPersonId(trustingPersonId);

		ActivePerson temp = (ActivePerson) personService.findById(personId, getInstanceName());
		if(temp==null)
			temp = (ActivePerson) personService.findByIdNotNull(personId, null);

		PersonOperationBase.checkRestriction(getRestriction(), temp);
		if (temp.getTrustingPersonId() == null)
			throw new BusinessException("������������ � id " + personId + " �� �������� ��������������");

		String status = temp.getStatus();
		if(!(Person.ACTIVE.equals(status) || Person.TEMPLATE.equals(status)))
			throw new BusinessLogicException("��������� ������� �������������� ������ �� �������� \"�����������\" ��� \"��������\".");

		empoweredPerson = temp;
		setMode(PersonOperationMode.direct);
	}

	public void remove() throws BusinessException, BusinessLogicException
	{
		if(getNeedAgrementCancellation()&& empoweredPerson.getStatus().equals(Person.ACTIVE))
		{
		//���� ��������� ����� ���� �������� ��������
			//todo ���� ����������� �����, �� ���������� ������� 10159
			empoweredPerson.setContractCancellationCouse("C");
			empoweredPerson.setProlongationRejectionDate(DateHelper.getCurrentDate());
			sendUnregisterRequest(empoweredPerson);
			empoweredPerson.setStatus(Person.WAIT_CANCELATION);
			personService.update(empoweredPerson, getInstanceName());
		}
		else
		{
			//�������� ��� ����������, �� ������ �� �������, ��� ������
			personService.markDeleted(empoweredPerson, getInstanceName());
		}

		//���� � ������ ����, �� ������� ������������� �� ����
		if(PersonOperationMode.shadow.equals(getPersonMode(empoweredPerson.getId())))
		{
			CopyPersonHelper.deleteCopy(MultiInstancePersonService.PERSON_SHADOW_INSTANCE_NAME,empoweredPerson);
		}
	}
}
