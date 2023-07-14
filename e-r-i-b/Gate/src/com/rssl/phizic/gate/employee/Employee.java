package com.rssl.phizic.gate.employee;

import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.login.Login;
import com.rssl.phizic.gate.schemes.AccessScheme;

/**
 * @author akrenev
 * @ created 03.10.13
 * @ $Author$
 * @ $Revision$
 *
 * �������� ��������� ����������
 */

public interface Employee
{
	/**
	 * @return �������������
	 */
	public Long getId();

	/**
	 * @return �������������
	 */
	public Long getExternalId();

	/**
	 * ������ �������������
	 * @param externalId �������������
	 */
	public void setExternalId(Long externalId);

	/**
	 * @return �����
	 */
	public Login getLogin();

	/**
	 * @return ����� ����
	 */
	public AccessScheme getScheme();

	/**
	 * ������ ����� ����
	 * @param scheme ����� ����
	 */
	public void setScheme(AccessScheme scheme);

	/**
	 * ������ ����� �������������
	 * @param office ����� �������������
	 */
	public void setDepartment(Office office);

	/**
	 * @return �������������
	 */
	public Office getDepartment();

	/**
	 * @return �������
	 */
	public String getSurName();

	/**
	 * ������ �������
	 * @param surname �������
	 */
	public void setSurName(String surname);
	/**
	 * @return ���
	 */
	public String getFirstName();

	/**
	 * ������ ���
	 * @param firstName ���
	 */
	public void setFirstName(String firstName);

	/**
	 * @return ��������
	 */
	public String getPatrName();

	/**
	 * ������ ��������
	 * @param patrName ��������
	 */
	public void setPatrName(String patrName);

	/**
	 * @return ���. ����������
	 */
	public String getInfo();
	/**
	 * ������ ���. ����������
	 * @param info ���. ����������
	 */
	public void setInfo(String info);

	/**
	 * @return �����
	 */
	public String getEmail();

	/**
	 * ������ �����
	 * @param email �����
	 */
	public void setEmail(String email);

	/**
	 * @return ��������� �������
	 */
	public String getMobilePhone();

	/**
	 * ������ ��������� �������
	 * @param mobilePhone ��������� �������
	 */
	public void setMobilePhone(String mobilePhone);

	/**
	 * @return �������, ������������, �������� �� ��������� ��������������� ��
	 */
	public boolean isCAAdmin();

	/**
	 * ���������� �������, ������������, �������� �� ��������� ��������������� ��
	 * @param CAAdmin ������� �������������� ��
	 */
	public void setCAAdmin(boolean CAAdmin);

	/**
	 * @return ������� ���������� ���.
	 */
	public boolean isVSPEmployee();

	/**
	 * ������ ������� ���������� ���.
	 * @param vspEmployee ��������� ���.
	 */
	public void setVSPEmployee(boolean vspEmployee);

	/**
	 * @return ID ���������
	 */
	public String getManagerId();

	/**
	 * ������ ������������� ���������
	 * @param managerId ������������� ���������
	 */
	public void setManagerId(String managerId);

	/**
	 * ���������� ����� ��������, ������������ ��������.
	 * @return ����� ��������
	 */
	public String getManagerPhone();

	/**
	 * ������ ����� ��������
	 * @param managerPhone ����� ��������
	 */
	public void setManagerPhone(String managerPhone);

	/**
	 * ���������� ����� ����������� �����, ������������ ��������
	 * @return ����� ����������� �����
	 */
	public String getManagerEMail();

	/**
	 * ������ ����� ����������� �����
	 * @param managerEMail ����� ����������� �����
	 */
	public void setManagerEMail(String managerEMail);

	/**
	 * ���������� ���������� ����� ����������� ����� ������������ ������� ����������
	 * @return ���������� ����� ����������� �����
	 */
	public String getManagerLeadEMail();

	/**
	 * ������ ���������� ����� ����������� ����� ������������ ������� ����������
	 * @param managerLeadEMail ���������� ����� ����������� ����� ������������ ������� ����������
	 */
	public void setManagerLeadEMail(String managerLeadEMail);

	/**
	 * ���������� ���������� ����� ����������� ����� ������������ ������� ����������
	 * @return ���������� ����� ����������� �����
	 */
	public String getManagerChannel();

	/**
	 * ������ ����� ����������
	 * @param managerChannel ����� ����������
	 */
	public void setManagerChannel(String managerChannel);

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
