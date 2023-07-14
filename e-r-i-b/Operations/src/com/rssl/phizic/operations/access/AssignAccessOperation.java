package com.rssl.phizic.operations.access;

import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.auth.modes.AccessPolicy;
import com.rssl.phizic.auth.modes.AccessType;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.operations.Operation;
import com.rssl.phizic.business.operations.OperationDescriptor;
import com.rssl.phizic.business.operations.restrictions.Restriction;
import com.rssl.phizic.business.schemes.AccessScheme;
import com.rssl.phizic.business.services.Service;

import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author Evgrafov
 * @ created 17.01.2006
 * @ $Author: pankin $
 * @ $Revision: 28273 $
 */

public interface AssignAccessOperation<R extends Restriction> extends Operation<R>
{
	/**
	 * @return ����� ��� �������� ����������� �����
	 */
	CommonLogin getLogin();

	/**
	 * @return �������� (value) �� ������ (key)
	 */
	Map<Service, List<OperationDescriptor>> getServicesTuple() throws BusinessException;

	/**
	 * ���������� ������� ����� ������� ��� ������������
	 * @return AccessScheme, ���� ����� ������, ����� - null
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	AccessScheme getCurrentAccessScheme () throws BusinessException;

	/**
	*���������� ����������� ����� �������
	* @return AccessSheme
	*/
	AccessScheme getNewAccessScheme();

	/**
	 * ��������� ����� �����
	 * @param accessSchemeId - ID ����� �����
	 * @throws BusinessException
	 */
	void setNewAccessSchemeId ( Long accessSchemeId ) throws BusinessException, BusinessLogicException;


	/**
	 * ��������� ������������ �����
	 * @param services - id �����, �������� � �����
	 */
	void setPersonalScheme ( List<Long> services) throws BusinessException;

	/**
	 * ��������� ��� ��������� � ��
	 * @throws BusinessException
	 */
	void assign () throws BusinessException, BusinessLogicException;

	/**
	 * @return ��� �������
	 */
	AccessType getAccessType();

	/**
	 * @param flag ��������� ��������� ��� �������
	 */
	void setNewAccessTypeAllowed(boolean flag);

	/**
	 * @return true = ��� ������� ��������
	 */
	boolean getAccessTypeAllowed();

	/**
	 * @return ��������� �������� ������������
	 */
	Properties getProperties();

	/**
	 * ���������� �������� �������� �������� ������������
	 * @param name ��� ��������
	 * @param value ��������
	 */
	void setNewProperty(String name, String value);

	/**
	 * �������� ������������ ��� ������� ����������� ���������
	 * @return ��������
	 */
	AccessPolicy getPolicy();

	/**
	 * ������� ��� ��������� ��������� ���� � �����
	 * @return ������ ����
	 */
	List<AssignAccessHelper> getHelpers() throws BusinessException;

	/**
	 * @param category ���������� ����� ��������� �������
	 */
	void setNewCategory(String category);

	/**
	 * @return ������� ��������� �����
	 */
	String getCategory() throws BusinessException;
}
