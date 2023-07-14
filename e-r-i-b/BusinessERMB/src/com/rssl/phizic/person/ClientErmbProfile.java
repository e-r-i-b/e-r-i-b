package com.rssl.phizic.person;

import com.rssl.phizic.common.types.annotation.PlainOldJavaObject;
import com.rssl.phizic.utils.PhoneNumber;

import java.util.Collection;

/**
 * @author Erkin
 * @ created 31.07.2014
 * @ $Author$
 * @ $Revision$
 */

/**
 * ����-������� �������
 * �������� ��������� ������ ���-�������� �� �������
 */
@PlainOldJavaObject
@SuppressWarnings("PublicField")
public class ClientErmbProfile
{
	/**
	 * ������������� �������
	 */
	public long id;

	/**
	 * ������ ����������� ������
	 * true - ������ ����������
	 * false - ������ �� ����������
	 */
	public boolean serviceStatus;

	/**
	 * ������ ���������� ����������
	 * true - ������ ������������� ��������
	 * false - ������ �� ������������� ��������
	 */
	public boolean clientBlocked;

	/**
	 * ������ ���������� �� ��������
	 * true - ������ ������������� �� ��������
	 * false - ������ �� ������������� �� ��������
	 */
	public boolean paymentBlocked;

	/**
	 * ������� �������, ������������ � ������.
	 * ����������� ������, ���� ������ ����������.
	 * ����������� ������ � �������� ���������, ���� ������.
	 * ����� ���� �� ������, ���� ������ �� ����������.
	 */
	public PhoneNumber mainPhone;

	/**
	 * ������ ���������, ������������ � ������, ������� ������� �������.
	 * ����������� �� ����, ���� ������ ����������.
	 * ����� ���� ����, ���� ������ �� ����������.
	 */
	public Collection<PhoneNumber> phones;

	/**
	 * ������ �������
	 */
	public long version;
}
