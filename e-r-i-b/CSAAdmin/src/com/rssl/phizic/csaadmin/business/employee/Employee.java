package com.rssl.phizic.csaadmin.business.employee;

import com.rssl.phizic.csaadmin.business.departments.Department;
import com.rssl.phizic.csaadmin.business.login.Login;

/**
 * @author akrenev
 * @ created 27.09.13
 * @ $Author$
 * @ $Revision$
 *
 * ���������
 */

public class Employee
{
	private Long externalId;
	private Login login;
	private String surname;
	private String firstName;
	private String patronymic;
	private String info;
	private String email;
	private String mobilePhone;
	private Department department;
	private boolean CAAdmin;
	private boolean VSPEmployee;
	private String managerId;
	private String managerPhone;
	private String managerEMail;
	private String managerLeadEMail;
	private String managerChannel;
	private String sudirLogin;

	/**
	 * @return �������������
	 */
	public Long getExternalId()
	{
		return externalId;
	}

	/**
	 * ������ �������������
	 * @param externalId �������������
	 */
	public void setExternalId(Long externalId)
	{
		this.externalId = externalId;
	}

	/**
	 * @return �����
	 */
	public Login getLogin()
	{
		return login;
	}

	/**
	 * ������ �����
	 * @param login �����
	 */
	public void setLogin(Login login)
	{
		this.login = login;
	}

	/**
	 * @return �������
	 */
	public String getSurname()
	{
		return surname;
	}

	/**
	 * ������ �������
	 * @param surname �������
	 */
	public void setSurname(String surname)
	{
		this.surname = surname;
	}

	/**
	 * @return ���
	 */
	public String getFirstName()
	{
		return firstName;
	}

	/**
	 * ������ ���
	 * @param firstName ���
	 */
	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	/**
	 * @return ��������
	 */
	public String getPatronymic()
	{
		return patronymic;
	}

	/**
	 * ������ ��������
	 * @param patronymic ��������
	 */
	public void setPatronymic(String patronymic)
	{
		this.patronymic = patronymic;
	}

	/**
	 * @return ���. ����������
	 */
	public String getInfo()
	{
		return info;
	}

	/**
	 * ������ ���. ����������
	 * @param info ���. ����������
	 */
	public void setInfo(String info)
	{
		this.info = info;
	}

	/**
	 * @return �����
	 */
	public String getEmail()
	{
		return email;
	}

	/**
	 * ������ �����
	 * @param email �����
	 */
	public void setEmail(String email)
	{
		this.email = email;
	}

	/**
	 * @return ��������� �������
	 */
	public String getMobilePhone()
	{
		return mobilePhone;
	}

	/**
	 * ������ ��������� �������
	 * @param mobilePhone ��������� �������
	 */
	public void setMobilePhone(String mobilePhone)
	{
		this.mobilePhone = mobilePhone;
	}

	/**
	 * @return ������������� ����������
	 */
	public Department getDepartment()
	{
		return department;
	}

	/**
	 * ������ ������������� ����������
	 * @param department ������������� ����������
	 */
	public void setDepartment(Department department)
	{
		this.department = department;
	}

	/**
	 * @return �������, ������������, �������� �� ��������� ��������������� ��
	 */
	public boolean isCAAdmin()
	{
		return CAAdmin;
	}

	/**
	 * ���������� �������, ������������, �������� �� ��������� ��������������� ��
	 * @param CAAdmin ������� �������������� ��
	 */
	public void setCAAdmin(boolean CAAdmin)
	{
		this.CAAdmin = CAAdmin;
	}

	/**
	 * @return ������� ���������� ���.
	 */
	public boolean isVSPEmployee()
	{
		return VSPEmployee;
	}

	/**
	 * ������ ������� ���������� ���.
	 * @param vspEmployee ��������� ���.
	 */
	public void setVSPEmployee(boolean vspEmployee)
	{
		this.VSPEmployee = vspEmployee;
	}

	/**
	 * @return ID ���������
	 */
	public String getManagerId()
	{
		return managerId;
	}

	/**
	 * ������ ID ���������
	 * @param managerId ID ���������
	 */
	public void setManagerId(String managerId)
	{
		this.managerId = managerId;
	}

	/**
	 * ���������� ����� ��������, ������������ ��������.
	 * @return ����� ��������
	 */
	public String getManagerPhone()
	{
		return managerPhone;
	}

	/**
	 * ������������� ����� ��������, ������������ ��������
	 * @param managerPhone ����� ��������
	 */
	public void setManagerPhone(String managerPhone)
	{
		this.managerPhone = managerPhone;
	}

	/**
	 * ���������� ����� ����������� �����, ������������ ��������
	 * @return ����� ����������� �����
	 */
	public String getManagerEMail()
	{
		return managerEMail;
	}

	/**
	 * ������������� ����� ����������� �����, ������������ ��������
	 * @param managerEMail ����� ����������� �����
	 */
	public void setManagerEMail(String managerEMail)
	{
		this.managerEMail = managerEMail;
	}

	/**
	 * ���������� ���������� ����� ����������� ����� ������������ ������� ����������
	 * @return ���������� ����� ����������� �����
	 */
	public String getManagerLeadEMail()
	{
		return managerLeadEMail;
	}

	/**
	 * ������������� ���������� ����� ����������� ����� ������������ ������� ����������
	 * @param managerLeadEMail ���������� ����� ����������� �����
	 */
	public void setManagerLeadEMail(String managerLeadEMail)
	{
		this.managerLeadEMail = managerLeadEMail;
	}

	/**
	 * @return �����
	 */
	public String getManagerChannel()
	{
		return managerChannel;
	}

	/**
	 * ������ �����
	 * @param managerChannel �����
	 */
	public void setManagerChannel(String managerChannel)
	{
		this.managerChannel = managerChannel;
	}

	public String getSudirLogin()
	{
		return sudirLogin;
	}

	public void setSudirLogin(String sudirLogin)
	{
		this.sudirLogin = sudirLogin;
	}
}
