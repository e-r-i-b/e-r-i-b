package com.rssl.phizic.business.dictionaries;

import com.rssl.phizic.business.departments.TerBankDetails;
import com.rssl.phizic.utils.StringHelper;

import java.util.Comparator;

/**
 * ���������� ��� ��������� ����������� ��� � ������������ ������ �������
 * @author Moshenko
 * @ created 18.02.14
 * @ $Author$
 * @ $Revision$                                                                                                      1
 */
public class VSPLoanReplicaComparator implements Comparator<VSPLoanReplicaDepartment>
{

	public int compare(VSPLoanReplicaDepartment o1, VSPLoanReplicaDepartment o2)
	{
		if(!StringHelper.equals(o1.getTb(), o2.getTb()))
		return -1;

		if(!StringHelper.equals(o1.getOsb(), o2.getOsb()))
			return -1;

		if(!StringHelper.equals(o1.getOffice(), o2.getOffice()))
			return -1;

		return 0;
	}
}
