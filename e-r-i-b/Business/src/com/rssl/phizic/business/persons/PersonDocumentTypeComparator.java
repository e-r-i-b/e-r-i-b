package com.rssl.phizic.business.persons;
import com.rssl.phizic.person.PersonDocumentType;
import com.rssl.phizic.person.PersonDocument;

import java.util.Comparator;

/**
 * User: Balovtsev
 * Date: 15.04.2011
 * Time: 13:57:02
 */
public class PersonDocumentTypeComparator implements Comparator<PersonDocument>
{
	public int compare(PersonDocument document1, PersonDocument document2)
	{
		//��������� ���������� ���� �� ��������
		if (document1.getDocumentType().equals(PersonDocumentType.REGULAR_PASSPORT_RF) && document2.getDocumentType().equals(PersonDocumentType.REGULAR_PASSPORT_RF))
			return 0;
		if (document1.getDocumentType().equals(PersonDocumentType.REGULAR_PASSPORT_RF))
			return -1;
		if (document2.getDocumentType().equals(PersonDocumentType.REGULAR_PASSPORT_RF))
			return 1;

		//��������� ����������, �� ���� ��� �������� ������������ ��������
		if (document1.isDocumentIdentify() && document2.isDocumentIdentify())
			return 0;
		if (document1.isDocumentIdentify())
			return -1;
		if (document2.isDocumentIdentify())
			return 1;

		//��������� ������� �������
		return 0;
	}
}
