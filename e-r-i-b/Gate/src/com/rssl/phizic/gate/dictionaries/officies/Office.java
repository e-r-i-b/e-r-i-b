package com.rssl.phizic.gate.dictionaries.officies;

import com.rssl.phizic.gate.dictionaries.DictionaryRecord;

import java.io.Serializable;
import java.util.Map;

/**
 * ����. ��������� ��� ������������� ������������� �����.
 * @author Kidyaev
 * @ created 01.11.2006
 * @ $Author$
 * @ $Revision$
 */
public interface Office extends DictionaryRecord, Serializable
{
	/**
	 * ��� ����� - ������, ���������������� ���� � �������. ����� �������� �� ���������� �����.
	 * @return ��� �����
	 */
    Code getCode();

	/**
	 * @param code ��� - ������, ���������� ���������������� ���� � �������
	 */
    void setCode(Code code);

	/**
	 * ���������� ���� ����� (������) � ����������� �� ���������� ����������
	 * @param codeFields - ���� �� ������� ��������
	 */
	void buildCode(Map<String,Object> codeFields);

	/**
	 * @param synchKey ���������� ���� ��� �������������
	 */
	void setSynchKey(Comparable synchKey);

	/**
	 * @return ��� �����
	 */
	String getBIC();

	/**
	 * @param BIC BIC
	 */
    void setBIC(String BIC);

	/**
	 * @return �����
	 */
    String getAddress();

	/**
	 * @param address �����
	 */
    void setAddress(String address);

	/**
	 * @return ����� ��������
	 */
    String getTelephone();

	/**
	 * @param telephone ����� ��������
	 */
    void setTelephone(String telephone);

	/**
	 * @return �������� �����
	 */
    String getName ();

	/**
	 * @param name �������� �����
	 */
    void setName ( String name );

	/**
	 * @return ������ �� ���� ��������� �����
	 */
	boolean isCreditCardOffice();

	/**
	 * @return �������� �� �������� ��� � �����
	 */
	public boolean isOpenIMAOffice();
	

	/**
	 * @return ����� �� ��������� ���� ������ ��������� ����� � ����� ��� ����������
	 */
	boolean isNeedUpdateCreditCardOffice();

	/**
	 * ���������� ���� ���� � ���������� � ���������.
	 * ���������� ������ ���������� ��� ����, ��������� � ��������.
	 * @param o ������ ��� ���������
	 * @return <code>true</code> ���� ����� ������������,
     *         <code>false</code> �����.
	 */
	boolean equals(Object o);

	/**
	 * ���������� ���-��� ��� ����� �����
	 * @return ���-��� ��� ����� �����
	 */
	int hashCode();

	/**
	 * �������������� � ������
	 * @return ����, ��� ������, � ������ synchKey
	 */
	String toString();
}
