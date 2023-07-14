package com.rssl.phizic.business.groups;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;

import java.util.List;

/**
 * User: Balovtsev
 * Date: 25.05.2011
 * Time: 19:39:25
 */
public class GroupLoaderSynchronizer
{
	private final GroupService  groupService  = new GroupService();
	private final SimpleService simpleService = new SimpleService();
	
	private List<Group> groups;

	public GroupLoaderSynchronizer(List<Group> groups)
	{
		this.groups = groups;
	}

	/**
	 * Если записи о группе нет, то она добавляется. Существующая запись обновляется.
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public void update() throws BusinessException
	{
		for (Group group: groups)
		{
			Group existedGroup = groupService.getGroupBySystemName(group.getSystemName());

			if(existedGroup == null)
			{
				simpleService.add( group );
			}
			else
			{
				existedGroup.setSkin( group.getSkin() );
				existedGroup.setName( group.getName() );
				existedGroup.setPriority( group.getPriority() );
				existedGroup.setCategory( group.getCategory() );
				existedGroup.setDepartment( group.getDepartment() );
				existedGroup.setDescription( group.getDescription() );

				simpleService.update( existedGroup );
			}
		}
	}
}
