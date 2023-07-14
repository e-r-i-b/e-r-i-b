package com.rssl.phizic.person;

import com.rssl.phizic.common.types.Entity;

import java.io.Serializable;
import java.util.Set;

/**
 * @author EgorovaA
 * @ created 14.11.2012
 * @ $Author$
 * @ $Revision$
 */
public interface ErmbProfile extends Entity, Serializable
{
	/**
	 * ������������� ������� ����. ������ ��������� � ���.
	 * @return id ������� ���� (��������� � ���)
	 */
	Long getId();

	boolean isServiceStatus();

	/**
	 * ��������� �������� ������ ���.�������� ����-�������
	 * @return ����� ��������
	 */
	 String getMainPhoneNumber();

	/**
	 * ��������� ������� ������ ������� ���. ���������
	 * @return ������ �����
	 */
	Set<String> getPhoneNumbers();

	/**
	 * ���������� ����� ������ �������
	 * @return ����� ������
	 */
	Long getProfileVersion();

	/**
	 * �������������� ����� ������ �������
	 * @return ����� ������
	 */
	Long getConfirmProfileVersion();

}
