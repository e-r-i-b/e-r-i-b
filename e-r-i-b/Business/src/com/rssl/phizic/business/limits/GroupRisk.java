package com.rssl.phizic.business.limits;

import com.rssl.phizic.dictionaries.synchronization.MultiBlockDictionaryRecord;
import com.rssl.phizic.utils.RandomGUID;

import java.io.Serializable;

/**
 * @author basharin
 * @ created 01.08.2012
 * @ $Author$
 * @ $Revision$
 * Группы риска
 */

public class GroupRisk implements Serializable, MultiBlockDictionaryRecord
{
	private Long id;
	private String name;
	private boolean isDefault;
	private GroupRiskRank rank;
	private String externalId = new RandomGUID().getStringValue();

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public boolean getIsDefault()
	{
		return isDefault;
	}

	public void setIsDefault(boolean aDefault)
	{
		this.isDefault = aDefault;
	}

	public GroupRiskRank getRank()
	{
		return rank;
	}

	public void setRank(GroupRiskRank rank)
	{
		this.rank = rank;
	}

	public String getExternalId()
	{
		return externalId;
	}

	public void setExternalId(String externalId)
	{
		this.externalId = externalId;
	}

	public String getMultiBlockRecordId()
	{
		return externalId;
	}
}
