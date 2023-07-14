package com.rssl.phizic.operations.person;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;

import java.util.List;

/**
 * @author Omeliyanchuk
 * @ created 14.05.2008
 * @ $Author$
 * @ $Revision$
 */

public interface AddResourcesOperation<V>
{
	/**
	 * ��������� �������
	 * @param value personId
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	void setPersonId(Long value) throws BusinessException, BusinessLogicException;

	/**
	 * �������� ������
	 * @param resourseId ������������� �������(����� �����, ����� �����)
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void addResource(String resourseId) throws BusinessException, BusinessLogicException;

	/**
	 * �������� ������
	 * @param resource - ������
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void addResource(V resource) throws BusinessException, BusinessLogicException;

	/**
	 * �������� ������
	 * @param resource - ������ ��������
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void addResources(List<V> resource) throws BusinessException, BusinessLogicException;

	/**
	 * ��������� ����������� �������
	 * @return ������ ��� ����������
	 * @throws BusinessException
	 */
	public String save() throws BusinessException, BusinessLogicException;

	/**
	 * ������ ����������� ��������
	 * @return
	 * @throws BusinessException
	 */
	public String getAddedNumbers() throws BusinessException;
}
