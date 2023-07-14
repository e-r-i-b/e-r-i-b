package com.rssl.phizic.operations.groups;

import com.rssl.phizic.business.operations.restrictions.UserRestriction;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.ListEntitiesOperation;

/**
 * Created by IntelliJ IDEA. User: Omeliyanchuk Date: 15.11.2006 Time: 15:46:10 To change this template use
 * File | Settings | File Templates.
 */
public class ListGroupEmployersOperation extends OperationBase<UserRestriction> implements ListEntitiesOperation<UserRestriction>
{
	private Long groupId;

	/** Методы для задания параметров поиска пользователей по фильтру */
	public Long getGroupId()
	{
		return groupId;
	}

	public void setGroupId(Long groupId)
	{
		this.groupId = groupId;
	}
}
