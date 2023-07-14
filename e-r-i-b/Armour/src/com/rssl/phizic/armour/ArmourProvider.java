package com.rssl.phizic.armour;

/**
 * @author Krenev
 * @ created 05.06.2008
 * @ $Author$
 * @ $Revision$
 */
public interface ArmourProvider
{
	/**
	 * ��������� ���-�� ������������� � ��������
	 * @return ���-�� ������������� � ��������
	 * @throws ArmourException ������ ��� ������
	 */
	long getUsersAmount() throws ArmourException;

	/**
	 * ���������, ��� ������� ���-�� ������������� �� ��������� ���-�� � ��������
	 * @param currentAmount ������� ���-��.
	 * @return true - �� ���������.
	 * @throws ArmourException ������ ��� ������
	 */
	boolean isUserAmountNotExceed(long currentAmount) throws ArmourException;

	/**
	 * ��������, �������� �� ��������
	 * @return true - ��������
	 * @throws ArmourException ������ ��� ������
	 */
	boolean isLicenseExist() throws ArmourException;

	/**
	 * ������������ ��������
	 */
	public void release();
}
