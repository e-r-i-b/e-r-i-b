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
	 * @return ������������� ����������
	 */
	Department getDepartment();

	String getSkinUrl();

	void setSkinUrl(String skinUrl);

	String getGlobalUrl() throws BusinessException;

	/**
	 * �������� ��������� �������
	 * @return ���������
	 */
	public ClientNodeState getCurrentClientNodeState();

	/**
	 * ������ ��������� �������
	 * @param state ���������
	 * @return ���������
	 */
	public ClientNodeState setCurrentClientNodeState(ClientNodeState state);

	/**
	 * ��������� ���������� ������������ �������.
	 * @param gridId �������������
	 * @param countRecord ���������� ������������ �������
	 * @throws BusinessException
	 */
	void setNumberDisplayedEntry(String gridId, Long countRecord) throws BusinessException;

	/**
	 * ���������� ������� � ����� � gridId ��� �������� ������������.
	 * @param gridId ������������� �����
	 * @return ���������� �������
	 * @throws BusinessException
	 */
	Long getNumberDisplayedEntries(String gridId) throws BusinessException;

	/**
	 * �������� �� ������ �� operations-tree.xml
	 * @param groupName �������� ������
	 * @return �� ���
	 */
	boolean availableGroup(String groupName);

	/**
	 * @return ������������ ������� ������� � ���
	 */
	public Long getCurrentClientCsaProfileId();

	/**
	 * @param csaProfileId ������������ ������� ������� � ���
	 */
	public void setCurrentClientCsaProfileId(Long csaProfileId);

	/**
	 * @return ������������� ���
	 */
	public String getCurrentClientMdmId();

	/**
	 * @param mdmId ������������� ���
	 */
	public void setCurrentClientMdmId(String mdmId);

	/**
	 * @param clientId ������������� ������� � �����
	 */
	void setCurrentClientId(Long clientId);
}
