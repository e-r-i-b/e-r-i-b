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
	 * установка клиента
	 * @param value personId
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	void setPersonId(Long value) throws BusinessException, BusinessLogicException;

	/**
	 * Добавить ресурс
	 * @param resourseId идентификатор ресурса(номер счета, номер карты)
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void addResource(String resourseId) throws BusinessException, BusinessLogicException;

	/**
	 * Добавить ресурс
	 * @param resource - объект
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void addResource(V resource) throws BusinessException, BusinessLogicException;

	/**
	 * Добавить ресурс
	 * @param resource - список объектов
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void addResources(List<V> resource) throws BusinessException, BusinessLogicException;

	/**
	 * сохранить добавленные ресурсы
	 * @return ошибки при добавлении
	 * @throws BusinessException
	 */
	public String save() throws BusinessException, BusinessLogicException;

	/**
	 * номера добавленных ресурсов
	 * @return
	 * @throws BusinessException
	 */
	public String getAddedNumbers() throws BusinessException;
}
