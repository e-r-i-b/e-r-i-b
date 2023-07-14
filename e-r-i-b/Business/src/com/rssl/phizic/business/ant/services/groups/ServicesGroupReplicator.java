package com.rssl.phizic.business.ant.services.groups;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.services.groups.ServiceInformation;
import com.rssl.phizic.business.services.groups.ServicesGroup;
import com.rssl.phizic.business.services.groups.ServicesGroupService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author akrenev
 * @ created 21.08.2014
 * @ $Author$
 * @ $Revision$
 *
 * Репликатор групп сервисов
 */

class ServicesGroupReplicator
{
	private static final ServicesGroupService service = new ServicesGroupService();

	private Map<String, ServicesGroup> databaseData;
	private List<XMLServicesGroupEntity> source;

	/**
	 * инициализация
	 * @param source источник данных
	 * @throws BusinessException
	 */
	void initialize(List<XMLServicesGroupEntity> source) throws BusinessException
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.source = source;

		databaseData = new HashMap<String, ServicesGroup>();
		List<ServicesGroup> groupList = service.getAll();
		for (ServicesGroup group : groupList)
			databaseData.put(group.getKey(), group);
	}

	private void replicate(XMLServicesGroupEntity source, ServicesGroup parent) throws BusinessException
	{
		ServicesGroup destination = databaseData.remove(source.getKey());
		if (destination == null)
		{
			destination = new ServicesGroup();
			destination.setKey(source.getKey());
			destination.setServices(new ArrayList<ServiceInformation>());
		}

		destination.setName(source.getName());
		destination.setParentId(parent == null ? null : parent.getId());
		destination.setCategory(source.getCategory());
		destination.updateServices(source.getServices());
		destination.setIsAction(source.isAction());
		destination.setOrder(source.getOrder());

		service.addOrUpdate(destination);

		for (XMLServicesGroupEntity child : source.getChildren())
			replicate(child, destination);

	}

	/**
	 * запустить репликацию
	 * @throws Exception
	 */
	void replicate() throws Exception
	{
		for (XMLServicesGroupEntity group : source)
			replicate(group, null);

		for (ServicesGroup group : databaseData.values())
			service.remove(group);
	}
}
