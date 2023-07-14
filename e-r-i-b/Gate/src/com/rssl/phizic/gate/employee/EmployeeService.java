package com.rssl.phizic.gate.employee;

import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.dictionaries.officies.AllowedDepartment;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.login.Login;
import com.rssl.phizic.gate.login.LoginBlock;
import com.rssl.phizic.gate.schemes.AccessScheme;

import java.util.List;

/**
 * @author akrenev
 * @ created 03.10.13
 * @ $Author$
 * @ $Revision$
 *
 * Сервис работы с сотрудниками
 */

public interface EmployeeService extends Service
{
	/**
	 * @param filterParameters параметры поиска
	 * @return список сотрудников
	 * @throws GateException
	 */
	public List<Employee> getList(EmployeeListFilterParameters filterParameters) throws GateException, GateLogicException;

	/**
	 * @param filterParameters параметры поиска
	 * @return список сотрудников
	 * @throws GateException
	 */
	public List<ContactCenterEmployee> getListMailManagers(ContactCenterEmployeeFilterParameters filterParameters) throws GateException, GateLogicException;

	/**
	 * @param managerId идентификатор менеджера
	 * @return информация о персональном менеджере
	 * @throws GateException
	 * @throws GateLogicException
	 */
	public ManagerInfo getManagerInfo(String managerId) throws GateException, GateLogicException;

	/**
	 * @return текущий сотрудник
	 * @throws GateException
	 */
	public Employee getCurrent() throws GateException, GateLogicException;

	/**
	 * @param id идентификатор
	 * @return сотрудник с идентификаотром id
	 * @throws GateException
	 */
	public Employee getById(Long id) throws GateException, GateLogicException;

	/**
	 * сохранение данных сотрудника
	 *
	 * @param employee резактируемый сотрудник
	 * @return сохраненный сотрудник
	 * @throws GateException
	 */
	public Employee save(Employee employee) throws GateException, GateLogicException;

	/**
	 * назначение схемы прав
	 * @param schemeId идентификатор новой схемы
	 * @param employee редактируемый сотрудник
	 * @return новая схема
	 * @throws GateException
	 */
	public AccessScheme assign(Long schemeId, Employee employee) throws GateException, GateLogicException;

	/**
	 * назначение схемы прав
	 * @param serviceIds сервисы
	 * @param category категория доступа
	 * @param employee редактируемый сотрудник
	 * @return новая схема
	 * @throws GateException
	 */
	public AccessScheme assign(List<Long> serviceIds, String category, Employee employee) throws GateException, GateLogicException;

	/**
	 * удалить данные о сотруднике
	 * @param employee сотрудник
	 * @throws GateException
	 */
	public void delete(Employee employee) throws GateException, GateLogicException;

	/**
	 * заблокировать логин сотрудника
	 * @param lockedLogin логин блокируемого сотрудника
	 * @param block блокировка
	 * @throws GateException
	 * @throws GateLogicException
	 */
	public void lock(Login lockedLogin, LoginBlock block) throws GateException, GateLogicException;

	/**
	 * разблокировать логин сотрудника
	 * @param login логин сотрудника
	 * @return снятые блокировки
	 * @throws GateException
	 * @throws GateLogicException
	 */
	public List<LoginBlock> unlock(Login login) throws GateException, GateLogicException;

	/**
	 * изменить пароль сотрудника
	 * @param login логин сотрудника
	 * @return измененный пароль
	 * @throws GateException
	 * @throws GateLogicException
	 */
	public Login changePassword(Login login) throws GateException, GateLogicException;

	/**
	 * изменить пароль текущему сотруднику
	 * @param password - новый пароль
	 * @throws GateException
	 * @throws GateLogicException
	 */
	public void selfPasswordChange(String password) throws GateException, GateLogicException;

	/**
	 * поиск доступных подразделений
	 * @param employee сотрудник для которого ищем подразделения
	 * @return доступные подразделения для сотрудника
	 * @throws GateException
	 * @throws GateLogicException
	 */
	public List<AllowedDepartment> getAllowedDepartments(Employee employee) throws GateException, GateLogicException;

	/**
	 * поиск доступных подразделений
	 * @param employee сотрудник для которого ищем подразделения
	 * @param addAllowedDepartments доступные подразделения для добавления
	 * @param removeAllowedDepartments доступные подразделения для удаления
	 * @return доступные подразделения для сотрудника
	 * @throws GateException
	 * @throws GateLogicException
	 */
	public void saveAllowedDepartments(Employee employee, List<AllowedDepartment> addAllowedDepartments, List<AllowedDepartment> removeAllowedDepartments) throws GateException, GateLogicException;
}
