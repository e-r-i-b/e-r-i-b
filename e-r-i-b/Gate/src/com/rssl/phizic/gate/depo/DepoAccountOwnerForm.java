package com.rssl.phizic.gate.depo;

import com.rssl.phizic.gate.clients.Address;

import java.util.Calendar;
import java.util.Set;

/**
 * @author lukina
 * @ created 16.10.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * ������ ��������
 */
public interface DepoAccountOwnerForm
{
	/**
	 * @return ����� �����
	 */
	public String getDepoAccountNumber();

	/**
	 * @return id ������
	 */
	public Long getLoginId();

	/**
	 * @return ���
	 */
	String getINN();

	/**
	 * @return �������
	 */
	String getSurName();

	/**
	 * @return ���
	 */
	String getFirstName();

	/**
	 * @return  ��������
	 */
	String getPartName();

	/**
	 * @return ���� ��������
	 */
	Calendar getBirthday();

	/**
	 * @return ����� ��������
	 */
	String getBirthPlace();

	/**
	 * @return ����� �����������
	 */
	Address getRegistrationAddress();

	/**
	 * @return ����� �����������(������)
	 */
	String getRegAddressCountry();

	/**
	 * @return  ����� ����������
	 */
	Address getResidenceAddress();

	/**
	 * @return ����� ����������(������)
	 */
	String getResAddressCountry();

	/**
	 * @return ����� ��� ��������� ������ �������� ������������
	 */
	Address getForPensionAddress();

	/**
	 * @return ����� ��� ��������� ������ �������� ������������(������)
	 */
	String getForPensionAddressCountry();

	/**
	 * @return ����� ��� �������� �����������
	 */
	Address getMailAddress();

	/**
	 * @return ����� ��� �������� �����������(������)
	 */
	String getMailAddressCountry();

	/**
	 * @return ����� ����� ������
	 */
	Address getWorkAddress();

	/**
	 * @return ����� ����� ������(������)
	 */
	String getWorkAddressCountry();

	/**
	 * @return ��� ���������, ��������������� ��������
	 */
	String getIdType();

	/**
	 * @return ����� ���������
	 */
	String getIdSeries();

	/**
	 * @return ����� ���������
	 */	
	String getIdNum();

	/**
	 * @return ���� ������
	 */
	Calendar getIdIssueDate();

	/**
	 * @return ������������ ��
	 */
	Calendar getIdExpDate();

	/**
	 * @return ��� �����
	 */
	String getIdIssuedBy();

	/**
	 * @return ��� �������������
	 */
	String getIdIssuedCode();

	/**
	 * @return �����������
	 */
	String getCitizenship();

	/**
	 * @return �������������� ����������
	 */
	String getAdditionalInfo();

	/**
	 * @return �������� �������
	 */
	String getHomeTel();

	/**
	 * @return ������� �������
	 */
	String getWorkTel();

	/**
	 * @return ��������� �������
	 */
	String getMobileTel();

	/**
	 * @return ��������
	 */
	String getPhoneOperator();

	/**
	 * @return ������������ email
	 */
	String getPrivateEmail();

	/**
	 * @return ������� email
	 */
	String getWorkEmail();

	/**
	 * @return ����
	 */
	String getFax();

	/**
	 * @return ������ ��������� �������
	 */
	String getRecIncomeMethod();

	/**
	 * @return ������ ������ ��������� �� ��������� ����� ����
	 */
	String getRecInstructionMethod();

	/**
	 * @return ������ �������� ������ ��������� ����� ����
	 */
	String getRecInfoMethod();

	/**
	 * @return ���� ����������
	 */
	Calendar getStartDate();

	/**
	 * @return  ���������� � ������
	 */
	Set<DepositorAccount> getDepositorAccounts();
}
