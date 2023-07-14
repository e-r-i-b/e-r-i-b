package com.rssl.phizic.business.services.groups;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;

import java.util.List;

/**
 * @author akrenev
 * @ created 21.08.2014
 * @ $Author$
 * @ $Revision$
 *
 * Сервис работы с сущностью группа сервисов
 */

public class ServicesGroupService
{
	private static final SimpleService service = new SimpleService();

	/**
	 * @return полный список групп сервисов
	 * @throws BusinessException
	 */
	public List<ServicesGroup> getAll() throws BusinessException
	{
		return service.getAll(ServicesGroup.class);
	}

	/**
	 * сохранить группу
	 * @param group группа
	 * @return сохраненная группа
	 * @throws BusinessException
	 */
	public ServicesGroup addOrUpdate(ServicesGroup group) throws BusinessException
	{
		return service.addOrUpdate(group);
	}

	/**
	 * удалить группу
	 * @param group группа
	 * @throws BusinessException
	 */
	public void remove(ServicesGroup group) throws BusinessException
	{
		service.remove(group);
	}
}
