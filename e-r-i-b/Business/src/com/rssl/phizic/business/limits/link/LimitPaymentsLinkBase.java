package com.rssl.phizic.business.limits.link;

import com.rssl.phizic.business.limits.GroupRisk;
import com.rssl.phizic.common.types.AbstractEntity;
import com.rssl.phizic.gate.dictionaries.officies.Office;

/**
 * Базовый класс связывающий платеж и ллимит по группе риска
 *
 * @author vagin
 * @ created 30.05.2012
 * @ $Author$
 * @ $Revision$
 */
public abstract class LimitPaymentsLinkBase extends AbstractEntity implements LimitPaymentsLink
{
	private GroupRisk groupRisk;                        //группа риска
	private String tb;

	public GroupRisk getGroupRisk()
	{
		return groupRisk;
	}

	public void setGroupRisk(GroupRisk groupRisk)
	{
		this.groupRisk = groupRisk;
	}

	public String getTb()
	{
		return tb;
	}

	public void setTb(String tb)
	{
		this.tb = tb;
	}

	public String getMultiBlockRecordId()
	{
		return getTb();
	}
}