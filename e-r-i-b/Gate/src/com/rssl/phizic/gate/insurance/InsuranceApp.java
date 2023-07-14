package com.rssl.phizic.gate.insurance;

import com.rssl.phizic.common.types.Money;

import java.util.Calendar;
import java.io.Serializable;

/**
 * ���������� � ���������/��� ��������
 * @author lukina
 * @ created 27.02.2013
 * @ $Author$
 * @ $Revision$
 */

public interface InsuranceApp  extends Serializable
{
	/**
    * ������� ID ���������� ��������
    * @return ID
    */
	String getId();

	/**
	 * @return  ID (��������) ���������, �� ������� ������������� ������
	 */
	String getReference();

	/**
	 * @return  ������������ ������-��������, � ������ �������� ��������� ���������
	 */
	String getBusinessProcess();

	/**
	 * @return ������������ ���� ��������, � ������ �������� ��������� ���������
	 */
	String getProductType();

	/**
	 * @return ������ ��������� ��� �����������.
	 */
	String getStatus();

	/**
	 * @return ��������� �������� ��� �����������
	 */
	String getCompany();

	/**
	 * @return ��������� ��������� ��� �����������
	 */
	String getProgram();

	/**
	 * @return ���� ������ �������� ���������
	 */
	Calendar getStartDate();

	/**
	 * @return ���� ��������� �������� ���������
	 */
	Calendar getEndDate();

	/**
	 * @return ����� ����� ��� ����������� �����������
	 */
	String getSNILS();

	/**
	 * @return ��������� �����
	 */
	Money getAmount();

	/**
	 * @return �������� ��������� ������
	 */
	String getRisk();

	/**
	 * @return �������������� ���������� (� � ���� ���������� �������� (���
     * ������� ���������� ��������� � ������ ��������� ������)
	 */
	String getAdditionalInfo();

	/**
	 * @return �������� ���������� �������� �����������: �����, ����� � ����
     * ������ ������
	 */
	PolicyDetails getPolicyDetails();
}
