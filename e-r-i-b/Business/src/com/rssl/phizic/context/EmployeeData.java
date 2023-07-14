package com.rssl.phizic.context;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.clients.list.ClientNodeState;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.employees.Employee;

/** Created by IntelliJ IDEA. User: Evgrafov Date: 17.10.2005 Time: 12:21:04 */
public interface EmployeeData extends MultiNodeEmployeeData
{
    Employee getEmployee();

	/**
	 * @return подразделение сотрудника
	 */
	Department getDepartment();

	String getSkinUrl();

	void setSkinUrl(String skinUrl);

	String getGlobalUrl() throws BusinessException;

	/**
	 * получить состояние клиента
	 * @return состояние
	 */
	public ClientNodeState getCurrentClientNodeState();

	/**
	 * задать состояние клиента
	 * @param state состояние
	 * @return состояние
	 */
	public ClientNodeState setCurrentClientNodeState(ClientNodeState state);

	/**
	 * Сохраняет количество отображаемых записей.
	 * @param gridId идентификатор
	 * @param countRecord количество отображаемых записей
	 * @throws BusinessException
	 */
	void setNumberDisplayedEntry(String gridId, Long countRecord) throws BusinessException;

	/**
	 * количество записей в гриде с gridId для текущего пользователя.
	 * @param gridId идентификатор грида
	 * @return количество записей
	 * @throws BusinessException
	 */
	Long getNumberDisplayedEntries(String gridId) throws BusinessException;

	/**
	 * Доступна ли группа из operations-tree.xml
	 * @param groupName название группы
	 * @return да нет
	 */
	boolean availableGroup(String groupName);

	/**
	 * @return иднтификатор профиля клиента в ЦСА
	 */
	public Long getCurrentClientCsaProfileId();

	/**
	 * @param csaProfileId иднтификатор профиля клиента в ЦСА
	 */
	public void setCurrentClientCsaProfileId(Long csaProfileId);

	/**
	 * @return идентификатор МДМ
	 */
	public String getCurrentClientMdmId();

	/**
	 * @param mdmId идентификатор МДМ
	 */
	public void setCurrentClientMdmId(String mdmId);

	/**
	 * @param clientId идентификатор профиля в блоке
	 */
	void setCurrentClientId(Long clientId);
}
