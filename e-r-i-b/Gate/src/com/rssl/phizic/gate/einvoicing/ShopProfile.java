package com.rssl.phizic.gate.einvoicing;

import java.io.Serializable;
import java.util.Calendar;

/**
 * ���������� � ������������, �������� ��������-������.
 *
 * @author bogdanov
 * @ created 10.02.14
 * @ $Author$
 * @ $Revision$
 */

public interface ShopProfile extends Serializable
{
	/**
	* @return ������������� (PK)
	*/
	public Long getId();

	/**
	* @return ���
	*/
	public String getFirstName();

	/**
	* @return �������
	*/
	public String getSurName();

	/**
	* @return ��������
	*/
	public String getPatrName();

	/**
	* @return ���
	*/
	public String getPassport();

	/**
	* @return ���� ��������
	*/
	public Calendar getBirthdate();

	/**
	* @return ��
	*/
	public String getTb();
}
