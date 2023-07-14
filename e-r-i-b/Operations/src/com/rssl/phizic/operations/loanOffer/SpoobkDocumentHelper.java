package com.rssl.phizic.operations.loanOffer;

import com.rssl.phizic.business.NotFoundException;
import com.rssl.phizic.person.PersonDocumentType;

/**
 * User: moshenko
 * Date: 01.02.2013
 * Time: 12:00:58
 * ������ ��� ������ �� ������������ ������
 */
public class SpoobkDocumentHelper
{

	/**
	 *
	 * 121 - ������� ���������� ��
	 * 122 - ������������� ���������� ��
	 * 194 - ���� ��������� ��������������� ����������� �����������������
	 * 192 - ��������, �������� ������.���-��� � ���������� � ��
	 *
	 *
	 * @param doc ��������
	 * @return ��� ��������� �� ������
	 */
	public static String getSpoobkDocumentNumber(PersonDocumentType doc) throws NotFoundException
	{
		if(doc == PersonDocumentType.REGULAR_PASSPORT_RF)
			return "121";
		else if (doc == PersonDocumentType.FOREIGN_PASSPORT_RF)
			return "122";
		else if (doc == PersonDocumentType.FOREIGN_PASSPORT_LEGAL)
			return "192";
		else if (doc == PersonDocumentType.OTHER_NOT_RESIDENT)
			return "194";
		else
			throw new NotFoundException("�������� ������� �� ������������� �� ������ �� ����� ������: " + doc);
	}
}
