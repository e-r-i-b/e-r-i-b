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
 * гейтовый интерфейс сотрудника
 */

public interface Employee
{
	/**
	 * @return иденитфикатор
	 */
	public Long getId();

	/**
	 * @return иденитфикатор
	 */
	public Long getExternalId();

	/**
	 * задать иденитфикатор
	 * @param externalId иденитфикатор
	 */
	public void setExternalId(Long externalId);

	/**
	 * @return логин
	 */
	public Login getLogin();

	/**
	 * @return схема прав
	 */
	public AccessScheme getScheme();

	/**
	 * задать схему прав
	 * @param scheme схема прав
	 */
	public void setScheme(AccessScheme scheme);

	/**
	 * задать новое подразделение
	 * @param office новое подразделение
	 */
	public void setDepartment(Office office);

	/**
	 * @return подразделение
	 */
	public Office getDepartment();

	/**
	 * @return фамилия
	 */
	public String getSurName();

	/**
	 * задать фамилию
	 * @param surname фамилия
	 */
	public void setSurName(String surname);
	/**
	 * @return имя
	 */
	public String getFirstName();

	/**
	 * задать имя
	 * @param firstName имя
	 */
	public void setFirstName(String firstName);

	/**
	 * @return отчество
	 */
	public String getPatrName();

	/**
	 * задать отчество
	 * @param patrName отчество
	 */
	public void setPatrName(String patrName);

	/**
	 * @return доп. информация
	 */
	public String getInfo();
	/**
	 * задать доп. информацию
	 * @param info доп. информация
	 */
	public void setInfo(String info);

	/**
	 * @return емейл
	 */
	public String getEmail();

	/**
	 * задать емейл
	 * @param email емейл
	 */
	public void setEmail(String email);

	/**
	 * @return мобильный телефон
	 */
	public String getMobilePhone();

	/**
	 * задать мобильный телефон
	 * @param mobilePhone мобильный телефон
	 */
	public void setMobilePhone(String mobilePhone);

	/**
	 * @return признак, определяющий, является ли сотрудник администратором ЦА
	 */
	public boolean isCAAdmin();

	/**
	 * установить признак, определяющий, является ли сотрудник администратором ЦА
	 * @param CAAdmin признак администратора ЦА
	 */
	public void setCAAdmin(boolean CAAdmin);

	/**
	 * @return признак сотрдуника ВСП.
	 */
	public boolean isVSPEmployee();

	/**
	 * задать признак сотрдуника ВСП.
	 * @param vspEmployee сотрудник ВСП.
	 */
	public void setVSPEmployee(boolean vspEmployee);

	/**
	 * @return ID менеджера
	 */
	public String getManagerId();

	/**
	 * задать идентификатор менеджера
	 * @param managerId идентификатор менеджера
	 */
	public void setManagerId(String managerId);

	/**
	 * Возвращает номер телефона, отображаемый клиентам.
	 * @return номер телефона
	 */
	public String getManagerPhone();

	/**
	 * задать номер телефона
	 * @param managerPhone номер телефона
	 */
	public void setManagerPhone(String managerPhone);

	/**
	 * Возвращает адрес электронной почты, отображаемый клиентам
	 * @return адрес электронной почты
	 */
	public String getManagerEMail();

	/**
	 * задать адрес электронной почты
	 * @param managerEMail адрес электронной почты
	 */
	public void setManagerEMail(String managerEMail);

	/**
	 * Возвращает внутренний адрес электронной почты руководителя данного сотрудника
	 * @return внутренний адрес электронной почты
	 */
	public String getManagerLeadEMail();

	/**
	 * задать внутренний адрес электронной почты руководителя данного сотрудника
	 * @param managerLeadEMail внутренний адрес электронной почты руководителя данного сотрудника
	 */
	public void setManagerLeadEMail(String managerLeadEMail);

	/**
	 * Возвращает внутренний адрес электронной почты руководителя данного сотрудника
	 * @return внутренний адрес электронной почты
	 */
	public String getManagerChannel();

	/**
	 * задать канал сотрудника
	 * @param managerChannel канал сотрудника
	 */
	public void setManagerChannel(String managerChannel);

	/**
	 * Возвращает логин сотрудника в СУДИР
	 * @return логин сотрудника в СУДИР
	 */
	public String getSUDIRLogin();

	/**
	 * Установить логин сотрудника в СУДИР
	 * @param sudirLogin - логин сотрудника в СУДИР
	 */
	public void setSUDIRLogin(String sudirLogin);
}
