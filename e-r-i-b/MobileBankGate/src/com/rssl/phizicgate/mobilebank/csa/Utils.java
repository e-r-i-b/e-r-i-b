package com.rssl.phizicgate.mobilebank.csa;

import com.rssl.phizic.common.types.exceptions.SystemException;

/**
 * @author krenev
 * @ created 13.02.2013
 * @ $Author$
 * @ $Revision$
 * ��������� ����� ��� ��������� ������� �� �� �� ��������� ������ � ������������.
 */
public class Utils
{
	/**
	 * ��������� � ���������� �� �� �������� addInfoCn � ���������� �������� �� ����� ���������� �� ���
	 * @param addInfoCn ��������
	 * @return �������� �� ����� ������������ �������� ���������
	 * @throws SystemException
	 */
	static boolean isMainCard(int addInfoCn) throws SystemException
	{
		//AddInfoCn ����� ��������� �������� 1 - ��������, 2- �������������� �����.
		if (addInfoCn != 1 && addInfoCn != 2)
		{
			throw new SystemException("��������� ������������ �������������� � ��: �������� addInfoCn=" + addInfoCn);
		}
		return addInfoCn == 1;
	}

	/**
	 *
	 * ��������� � ���������� �� �� �������� addInfoCn � ���������� �������� �� ����� ���������� �� ���
	 * @param contrStatus ������� ������ �����
	 * @return ������� �� �����.
	 * @throws SystemException
	 */
	static boolean isCardActive(int contrStatus) throws SystemException
	{
		//ContrStatus - ������� ������ ����� (ID), ��� �������� ����� ����� ������������: 14 ��� 239.
		return contrStatus == 14 || contrStatus == 239;
	}
}
