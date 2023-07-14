package com.rssl.phizic.csaadmin.business.employee;

import com.rssl.phizic.csaadmin.business.departments.Department;
import com.rssl.phizic.csaadmin.business.login.Login;

/**
 * @author akrenev
 * @ created 27.09.13
 * @ $Author$
 * @ $Revision$
 *
 * Сотрудник
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
	 * @return иденитфикатор
	 */
	public Long getExternalId()
	{
		return externalId;
	}

	/**
	 * задать иденитфикатор
	 * @param externalId иденитфикатор
	 */
	public void setExternalId(Long externalId)
	{
		this.externalId = externalId;
	}

	/**
	 * @return логин
	 */
	public Login getLogin()
	{
		return login;
	}

	/**
	 * задать логин
	 * @param login логин
	 */
	public void setLogin(Login login)
	{
		this.login = login;
	}

	/**
	 * @return фамилия
	 */
	public String getSurname()
	{
		return surname;
	}

	/**
	 * задать фамилию
	 * @param surname фамилия
	 */
	public void setSurname(String surname)
	{
		this.surname = surname;
	}

	/**
	 * @return имя
	 */
	public String getFirstName()
	{
		return firstName;
	}

	/**
	 * задать имя
	 * @param firstName имя
	 */
	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	/**
	 * @return отчество
	 */
	public String getPatronymic()
	{
		return patronymic;
	}

	/**
	 * задать отчество
	 * @param patronymic отчество
	 */
	public void setPatronymic(String patronymic)
	{
		this.patronymic = patronymic;
	}

	/**
	 * @return доп. информация
	 */
	public String getInfo()
	{
		return info;
	}

	/**
	 * задать доп. информацию
	 * @param info доп. информация
	 */
	public void setInfo(String info)
	{
		this.info = info;
	}

	/**
	 * @return емейл
	 */
	public String getEmail()
	{
		return email;
	}

	/**
	 * задать емейл
	 * @param email емейл
	 */
	public void setEmail(String email)
	{
		this.email = email;
	}

	/**
	 * @return мобильный телефон
	 */
	public String getMobilePhone()
	{
		return mobilePhone;
	}

	/**
	 * задать мобильный телефон
	 * @param mobilePhone мобильный телефон
	 */
	public void setMobilePhone(String mobilePhone)
	{
		this.mobilePhone = mobilePhone;
	}

	/**
	 * @return подразделение сортудника
	 */
	public Department getDepartment()
	{
		return department;
	}

	/**
	 * задать подразделение сортудника
	 * @param department подразделение сортудника
	 */
	public void setDepartment(Department department)
	{
		this.department = department;
	}

	/**
	 * @return признак, определяющий, является ли сотрудник администратором ЦА
	 */
	public boolean isCAAdmin()
	{
		return CAAdmin;
	}

	/**
	 * установить признак, определяющий, является ли сотрудник администратором ЦА
	 * @param CAAdmin признак администратора ЦА
	 */
	public void setCAAdmin(boolean CAAdmin)
	{
		this.CAAdmin = CAAdmin;
	}

	/**
	 * @return признак сотрдуника ВСП.
	 */
	public boolean isVSPEmployee()
	{
		return VSPEmployee;
	}

	/**
	 * задать признак сотрдуника ВСП.
	 * @param vspEmployee сотрудник ВСП.
	 */
	public void setVSPEmployee(boolean vspEmployee)
	{
		this.VSPEmployee = vspEmployee;
	}

	/**
	 * @return ID менеджера
	 */
	public String getManagerId()
	{
		return managerId;
	}

	/**
	 * задать ID менеджера
	 * @param managerId ID менеджера
	 */
	public void setManagerId(String managerId)
	{
		this.managerId = managerId;
	}

	/**
	 * Возвращает номер телефона, отображаемый клиентам.
	 * @return номер телефона
	 */
	public String getManagerPhone()
	{
		return managerPhone;
	}

	/**
	 * Устанавливает номер телефона, отображаемый клиентам
	 * @param managerPhone номер телефона
	 */
	public void setManagerPhone(String managerPhone)
	{
		this.managerPhone = managerPhone;
	}

	/**
	 * Возвращает адрес электронной почты, отображаемый клиентам
	 * @return адрес электронной почты
	 */
	public String getManagerEMail()
	{
		return managerEMail;
	}

	/**
	 * Устанавливает адрес электронной почты, отображаемый клиентам
	 * @param managerEMail адрес электронной почты
	 */
	public void setManagerEMail(String managerEMail)
	{
		this.managerEMail = managerEMail;
	}

	/**
	 * Возвращает внутренний адрес электронной почты руководителя данного сотрудника
	 * @return внутренний адрес электронной почты
	 */
	public String getManagerLeadEMail()
	{
		return managerLeadEMail;
	}

	/**
	 * Устанавливает внутренний адрес электронной почты руководителя данного сотрудника
	 * @param managerLeadEMail внутренний адрес электронной почты
	 */
	public void setManagerLeadEMail(String managerLeadEMail)
	{
		this.managerLeadEMail = managerLeadEMail;
	}

	/**
	 * @return канал
	 */
	public String getManagerChannel()
	{
		return managerChannel;
	}

	/**
	 * задать канал
	 * @param managerChannel канал
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
