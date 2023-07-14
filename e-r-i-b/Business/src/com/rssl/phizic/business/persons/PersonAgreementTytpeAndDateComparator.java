package com.rssl.phizic.business.persons;

import com.rssl.phizic.common.AbstractCompatator;
import com.rssl.phizic.common.types.client.CreationType;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.utils.DateHelper;

/**
 * � ������ ������� ��������� �� ���� �������� �������. � ����� ������� �� ������� � ����������: ����, ����, ���������.
 * ���� ������� �� ���� �������� �� ���������������, ��������� �� ���� ���������� ��������.
 * ���� �� ��������������, �� ���� ������ ������������, ���� �� ��������������, �� ������ �� ��������������.
 * @author egorova
 * @ created 11.01.2011
 * @ $Author$
 * @ $Revision$
 */

public class PersonAgreementTytpeAndDateComparator extends AbstractCompatator
{
	public int compare(Object o1, Object o2)
	{
		Person p1 = (Person) o1;
		Person p2 = (Person) o2;

		//�� null ������ �������� �� �����������, ��� ��� ��� �� ����� ���� null
		CreationType ct1 = p1.getCreationType();
		CreationType ct2 = p2.getCreationType();

		if (ct1 != ct2)
		{
			if (ct1 == CreationType.UDBO)
				return -1;
			if (ct2 == CreationType.UDBO)
				return 1;
			if (ct1 == CreationType.SBOL && ct2 == CreationType.CARD)
				return -1;
			if (ct2 == CreationType.SBOL && ct1 == CreationType.CARD)
				return 1;
		}


		int res = DateHelper.nullSafeCompare(p1.getAgreementDate(), p2.getAgreementDate());

		if (res == 0)
			res = DateHelper.nullSafeCompare(p1.getServiceInsertionDate(), p2.getServiceInsertionDate());

		if (res == 0)
			res = p1.getId().compareTo(p2.getId());

		//������ �������� �������, �.�. ��� ����� ���������� ��������, �� ����� ������� ���� � ����� ������.
		return -res;		
	}
}
