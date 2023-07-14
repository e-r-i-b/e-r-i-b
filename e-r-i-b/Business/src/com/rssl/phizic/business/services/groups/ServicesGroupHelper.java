package com.rssl.phizic.business.services.groups;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.services.groups.destenaion.GroupDestenation;
import com.rssl.phizic.business.services.groups.destenaion.ListRootGroupDestenation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author akrenev
 * @ created 02.09.2014
 * @ $Author$
 * @ $Revision$
 *
 * Хелпер работы с группами сервисов
 */

public class ServicesGroupHelper
{
	private static final ServicesGroupService servicesGroupService = new ServicesGroupService();

	/**
	 * @return группы сервисов
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public static ServicesGroupIterator getServicesGroups() throws BusinessException
	{
       	ListRootGroupDestenation destenation = new ListRootGroupDestenation();
		addServicesGroupsInformation(destenation);
		return new ServicesGroupIterator(destenation.getResult());
	}

	/**
	 * @return группы сервисов
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public static List<ServicesGroupInformation> getServicesGroupsList() throws BusinessException
	{
		ListRootGroupDestenation destenation = new ListRootGroupDestenation();
		addServicesGroupsInformation(destenation);
		return destenation.getResult();
	}

	private static <T> void addServicesGroupsInformation(GroupDestenation<T> result) throws BusinessException
	{
		List<ServicesGroup> groups = servicesGroupService.getAll();
		Map<Long, ServicesGroupInformation> informationMap = new HashMap<Long, ServicesGroupInformation>(groups.size());
		Map<Long, List<ServicesGroupInformation>> withoutParentActions = new HashMap<Long, List<ServicesGroupInformation>>(groups.size());
		Map<Long, List<ServicesGroupInformation>> withoutParentSubgroups = new HashMap<Long, List<ServicesGroupInformation>>(groups.size());		

		for (ServicesGroup group : groups)
		{
			ServicesGroupInformation information = new ServicesGroupInformation(group);
			informationMap.put(information.getId(), information);
			information.addActions(withoutParentActions.remove(information.getId()));
			information.addSubgroups(withoutParentSubgroups.remove(information.getId()));
			Long parentGroupId = group.getParentId();
			if (parentGroupId != null)
			{
				ServicesGroupInformation parent = informationMap.get(parentGroupId);
				if (parent != null)
				{
					if (group.getIsAction())
						parent.addAction(information);
					else
						parent.addSubgroup(information);
				}
				else
				{
					Map<Long, List<ServicesGroupInformation>> withoutParentMap = group.getIsAction()? withoutParentActions: withoutParentSubgroups;
					List<ServicesGroupInformation> withoutParentGroups = withoutParentMap.get(parentGroupId);
					if (withoutParentGroups == null)
					{
						withoutParentGroups = new ArrayList<ServicesGroupInformation>();
						withoutParentMap.put(parentGroupId, withoutParentGroups);
					}
					withoutParentGroups.add(information);
				}
			}
			result.add(information);
		}
	}
}
