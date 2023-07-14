package com.rssl.phizic.person;

import com.rssl.phizic.common.types.annotation.PlainOldJavaObject;

/**
 * @author Erkin
 * @ created 02.08.2014
 * @ $Author$
 * @ $Revision$
 */
@PlainOldJavaObject
@SuppressWarnings("PublicField")
public class UpdateErmbProfileOptions
{
	/**
	 * ������: ��������� ��������� ����-������� � ��� (UpdateProfile)
	 */
	public boolean notifyMSS;

	/**
	 * ������: ��������� ��������� ����-������� � ��� (��������)
	 */
	public boolean notifyMBK;

	/**
	 * ������: ��������� ��������� ����-������� � ��� (��������)
	 */
	public boolean notifyCSA;

	/**
	 * ������: ��������� ��������� ����-������� � ��� (������������� ���/����)
	 */
	public boolean notifyCOD;

	/**
	 * ������: ��������� ��������� ����-������� � WAY (������������� ���/����)
	 */
	public boolean notifyWAY;

	/**
	 * ������: ��������� ��������� ����-������� ������� (��� � ������������)
	 */
	public boolean notifyClient;
}
