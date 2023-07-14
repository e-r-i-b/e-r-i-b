package com.rssl.phizic.business.employees;

import com.rssl.phizic.auth.BankLogin;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.messaging.TranslitMode;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Roshka
 * @ created 27.12.2005
 * @ $Author$
 * @ $Revision$
 */
public interface Employee extends Serializable
{
    Long getId();

	/**
	 * @return ������� �������������
	 */
	public Long getExternalId();

	/**
	 * ������ ������� �������������
	 * @param externalId ������� �������������
	 */
	public void setExternalId(Long externalId);

    BankLogin getLogin();

    void setLogin(BankLogin login);

    String getFirstName();

    void setFirstName(String firstName);

    String getSurName();

    void setSurName(String surName);

    String getPatrName();

    void setPatrName(String patrName);

    String getInfo();

    void setInfo(String info);

    String getFullName();

    void setEmail(String eMaile);

    String getEmail();

    void setMobilePhone(String mobilePhone);

    String getMobilePhone();

    TranslitMode getSMSFormat();

    void setSMSFormat(TranslitMode SMSFormat);

	Long getDepartmentId();

	void setDepartmentId(Long departmentId);

	BigDecimal getLoanOfficeId();

	void setLoanOfficeId(BigDecimal loanOfficeId);

	public StringBuilder getLogEmployeeInfo()  throws BusinessException;

	/**
	 * @return �������, ������������, �������� �� ��������� ��������������� ��
	 */
	boolean isCAAdmin();

	/**
	 * ���������� �������, ������������, �������� �� ��������� ��������������� ��
	 * @param cAAdmin ������� �������������� ��
	 */
	void setCAAdmin(boolean cAAdmin);

	/**
	 * @return ������� ���������� ���.
	 */
	boolean isVSPEmployee();

	/**
	 * @param vspEmployee ��������� ���.
	 */
	void setVSPEmployee(boolean vspEmployee);

	/**
	 * @return ID ���������
	 */
	public String getManagerId();

	/**
	 * ������ ID ���������
	 * @param managerId ID ���������
	 */
	public void setManagerId(String managerId);

	/**
	 * ���������� ����� ��������, ������������ ��������.
	 * @return ����� ��������
	 */
	public String getManagerPhone();

	/**
	 * ������������� ����� ��������, ������������ ��������
	 * @param phone ����� ��������
	 */
	public void setManagerPhone(String phone);

	/**
	 * ���������� ����� ����������� �����, ������������ ��������
	 * @return ����� ����������� �����
	 */
	public String getManagerEMail();

	/**
	 * ������������� ����� ����������� �����, ������������ ��������
	 * @param eMail ����� ����������� �����
	 */
	public void setManagerEMail(String eMail);

	/**
	 * ���������� ���������� ����� ����������� ����� ������������ ������� ����������
	 * @return ���������� ����� ����������� �����
	 */
	public String getManagerLeadEMail();

	/**
	 * ������������� ���������� ����� ����������� ����� ������������ ������� ����������
	 * @param eMail ���������� ����� ����������� �����
	 */
	public void setManagerLeadEMail(String eMail);

	/**
	 * ������������� ������������� ������
	 * @param channelId ������������� ������
	 */
	public void setChannelId(Long channelId);

	/**
	 * ���������� ������������� ������
	 * @return �����
	 */
	public Long getChannelId();

	/**
	 * ���������� ����� ���������� � �����
	 * @return ����� ���������� � �����
	 */
	public String getSUDIRLogin();

	/**
	 * ���������� ����� ���������� � �����
	 * @param sudirLogin - ����� ���������� � �����
	 */
	public void setSUDIRLogin(String sudirLogin);

}
