package com.rssl.phizic.gorod.recipients;

import com.rssl.phizgate.common.payments.systems.recipients.CommonField;
import com.rssl.phizic.gorod.messaging.Constants;
import com.rssl.phizic.utils.StringHelper;

import java.util.Comparator;

/**
 * @author osminin
 * @ created 20.06.2011
 * @ $Author$
 * @ $Revision$
 * ���������� ��� ���������� �������� ������� ������ ������ � ����:
 * 1. ���� �������� ���������
 * 2. ���� ������������� ���������
 * 3. ���� ����� ���������
 * 4... � ����������� �� ������������ ������� ��������� �����
 */
public class GorodViewSubServiceFieldComparator implements Comparator
{
	private static final int SUB_SERVICE_LENGTH = 11; //����� ������ SubService- �� �������������� ���������

	public int compare(Object o1, Object o2)
	{
		//�������� ����� ����� �����
		String groupName1 = ((CommonField) o1).getGroupName();
		String groupName2 = ((CommonField) o2).getGroupName();

		//���� ���� �� ����������� �������, �� ������������� �� �� ������
		if (StringHelper.isEmpty(groupName1) && StringHelper.isEmpty(groupName2))
			return ((CommonField) o1).getNum() - ((CommonField) o2).getNum();
		//���� ��� ������ ���������� ���� ����� ��������
		else if (StringHelper.isEmpty(groupName1))
			return -1;
		//���� ��� ������ ���������� ���� ����� ��������
		else if (StringHelper.isEmpty(groupName2))
			return 1;
		else if (groupName1.equals(groupName2))
			return getSSFieldNum(o1) - getSSFieldNum(o2);

		//���� ������ �������� ������������� �� ������ ��������, ������� �������� � ��������������
		int subServiceId1 = Integer.decode(groupName1.substring(SUB_SERVICE_LENGTH, groupName1.length()));
		int subServiceId2 = Integer.decode(groupName2.substring(SUB_SERVICE_LENGTH, groupName2.length()));
		return subServiceId1 - subServiceId2;
	}

	/**
	 * �������� ����� ���� ���������, ��������������� � ����������� �� �������� ��������������,
	 * ��� �� ������ ��������������� � ���������� �������
	 * @param o ������
	 * @return ���������� ����� ����
	 */
	private int getSSFieldNum(Object o)
	{
		//�������� ������������� ����
		String externalId = ((CommonField) o).getExternalId();
		//������ ��������� ��� ���������
		if (externalId.startsWith(Constants.SS_NAME_PREFIX))
			return 0;
		//������ ��������� �������������
		else if (externalId.endsWith(Constants.DEBT_FIELD_NAME))
			return 1;
		//������� ��������� �����
		else if (externalId.startsWith(Constants.SS_AMOUNT_PREFIX))
			return 2;
		//��������� ���� ��������� ���� � ������ ������������ ����������� ������
		return 3 + ((CommonField) o).getNum();
	}
}
