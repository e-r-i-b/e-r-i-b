package com.rssl.phizic.business.services.groups;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.test.BusinessTestCaseBase;

import java.util.ArrayList;
import java.util.List;

/**
 * @author akrenev
 * @ created 21.08.2014
 * @ $Author$
 * @ $Revision$
 *
 * Тест сервиса работы с сущностью группа сервисов
 */

public class ServicesGroupServiceTest extends BusinessTestCaseBase
{
	private static final ServicesGroupService groupService = new ServicesGroupService();
	private static final SimpleService service = new SimpleService();

	private ServicesGroup find(Long id) throws BusinessException
	{
		return service.findById(ServicesGroup.class, id);
	}

	private ServicesGroup save(ServicesGroup group) throws BusinessException
	{
		return service.addOrUpdate(group);
	}

	private void remove(ServicesGroup group) throws BusinessException
	{
		service.remove(group);
	}

	private ServiceInformation getNewServiceInformation(String key, ServiceMode mode)
	{
		ServiceInformation information = new ServiceInformation();
		information.setKey(key);
		information.setMode(mode);
		return information;
	}

	private ServicesGroup getNewGroup() throws BusinessException
	{
		ServicesGroup group = new ServicesGroup();
		group.setKey("TEST");
		group.setName("TEST");
		group.setCategory(ServicesGroupCategory.employee);
		List<ServiceInformation> services = new ArrayList<ServiceInformation>();
		services.add(getNewServiceInformation("PersonManagement", ServiceMode.view));
		services.add(getNewServiceInformation("CSAClientManagement", ServiceMode.edit));
		group.updateServices(services);
		group.setIsAction(false);
		group.setOrder(0);
		return group;
	}

	/**
	 * проверить работоспособность поиска сервисом
	 * @throws BusinessException
	 */
	public void testsGroupServiceGetAll() throws BusinessException
	{
		List<ServicesGroup> servicesGroups = groupService.getAll();
		assertNotNull(servicesGroups);
	}

	/**
	 * проверить работоспособность сохранения сервисом
	 * @throws BusinessException
	 */
	public void testsGroupServiceSave() throws BusinessException
	{
		ServicesGroup servicesGroup = groupService.addOrUpdate(getNewGroup());
		assertNotNull(find(servicesGroup.getId()));
		remove(servicesGroup);
	}

	/**
	 * проверить работоспособность удаления сервисом
	 * @throws BusinessException
	 */
	public void testsGroupServiceRemove() throws BusinessException
	{
		ServicesGroup savedGroup = save(getNewGroup());
		groupService.remove(savedGroup);
		assertNull(find(savedGroup.getId()));
	}

	/**
	 * Проверить работоспособность сервиса
	 * @throws BusinessException
	 */
	public void testsGroupService() throws BusinessException
	{
		testsGroupServiceSave();
		testsGroupServiceRemove();
		testsGroupServiceGetAll();
	}
}
