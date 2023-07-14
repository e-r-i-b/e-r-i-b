package com.rssl.phizic.operations.groups;

import com.rssl.phizic.business.operations.restrictions.UserRestriction;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.ListEntitiesOperation;

import java.util.Date;

/**
 * Created by IntelliJ IDEA. User: Omeliyanchuk Date: 15.11.2006 Time: 17:01:26 To change this template use
 * File | Settings | File Templates.
 */
public class GetEmployeeListDictionaryOperation extends OperationBase<UserRestriction> implements ListEntitiesOperation<UserRestriction>
{
	private Long groupId;
	private Integer blocked;
	private Date blockedUntil;
    private String  state;

	/** Методы для задания параметров поиска пользователей по фильтру */
	public Long getGroupId()
	{
		return groupId;
	}

	public void setGroupId(Long groupId)
	{
		this.groupId = groupId;
	}

	public Integer getBlocked()
	{
		return blocked;
	}

	public void setBlocked(Integer blocked)
	{
		this.blocked = blocked;
	}

	public Date getBlockedUntil()
	{
		return blockedUntil;
	}

	public void setBlockedUntil(Date blockedUntil)
	{
		this.blockedUntil = blockedUntil;
	}

    public String getState()
    {
        return state;
    }

    public void setState(String state)
    {
        this.state = state;
    }
}
