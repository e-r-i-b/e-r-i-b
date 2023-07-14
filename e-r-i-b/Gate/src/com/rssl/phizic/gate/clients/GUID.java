package com.rssl.phizic.gate.clients;

import java.io.Serializable;
import java.util.Calendar;

/**
 * ��������� �������� �������������� ������� (���+���+��+��)
 *
 * @author khudyakov
 * @ created 17.06.14
 * @ $Author$
 * @ $Revision$
 */
public interface GUID extends Serializable
{
	/**
	 * @return �������
	 */
	String getSurName();

	/**
	 * @return ���
	 */
	String getFirstName();

	/**
	 * @return ��������
	 */
	String getPatrName();

	/**
	 * @return �������
	 */
	String getPassport();

	/**
	 * @return ���� ��������
	 */
	Calendar getBirthDay();

	/**
	 * @return ������� ������������ ������������
	 */
	String getTb();
}
