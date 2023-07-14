package com.rssl.phizic.business.dictionaries.finances;

import java.util.Comparator;

/**
 * ���������� ��� ��������� ������� ����������� ����� ��������. 
 * @author Gololobov
 * @ created 02.08.2011
 * @ $Author$
 * @ $Revision$
 */

public class CardOperationTypeComparator implements Comparator<CardOperationType>
{
	//����� ���������� ��� ��������� ������� � ������� � ������� �� ���� - ����������� ��� ��������� ����������.
	//��������������� ������ ������������ �� "SynchKey"
	public int compare(CardOperationType o1, CardOperationType o2)
	{
		int compareResult = Long.valueOf(o1.getCode()).compareTo(Long.valueOf(o2.getCode()));
		if (compareResult == 0)
			compareResult = o1.isCash() != o2.isCash() ? 1:0;

		return compareResult; 
	}
}
