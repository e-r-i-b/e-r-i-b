package com.rssl.phizic.web.persons;

import com.rssl.phizic.business.documents.strategies.ClientAccumulateLimitsInfo;
import com.rssl.phizic.business.limits.Limit;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.web.common.ListFormBase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author vagin
 * @ created 26.09.2012
 * @ $Author$
 * @ $Revision$
 * Форма списка лимитов конкретного клиента
 */
public class ListUserLimitsForm extends ListFormBase
{
	private String type;      //тип запрашиваемых лимитов(основное/мобильное)
	private Long personId;    //идентификатор клиента, в рамках которго запрашивается информация о лимитах
	private Person activePerson;
	private boolean modified = false;
	private Map<Long, Pair<String, List<Limit>>> groupRiskLimitsMap;
	private List<Limit> obstructionLimits;
	private List<Limit> imsiLimits;
	private ClientAccumulateLimitsInfo limitsInfo;

	private List<Limit> overallAmountPerDayLimits = new ArrayList<Limit>();

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public Person getActivePerson()
	{
		return activePerson;
	}

	public void setActivePerson(Person activePerson)
	{
		this.activePerson = activePerson;
	}

	public Long getPerson()
	{
		return personId;
	}

	public void setPerson(Long personId)
	{
		this.personId = personId;
	}

	public boolean isModified()
	{
		return modified;
	}

	public void setModified(boolean modified)
	{
		this.modified = modified;
	}

	public List<Limit> getObstructionLimits()
	{
		return obstructionLimits;
	}

	public void setObstructionLimits(List<Limit> obstructionLimits)
	{
		this.obstructionLimits = obstructionLimits;
	}

	public List<Limit> getImsiLimits()
	{
		return imsiLimits;
	}

	public void setImsiLimits(List<Limit> imsiLimits)
	{
		this.imsiLimits = imsiLimits;
	}

	public Map<Long, Pair<String, List<Limit>>> getGroupRiskLimitsMap()
	{
		return groupRiskLimitsMap;
	}

	public void setGroupRiskLimitsMap(Map<Long, Pair<String, List<Limit>>> groupRiskLimitsMap)
	{
		this.groupRiskLimitsMap = groupRiskLimitsMap;
	}

	public ClientAccumulateLimitsInfo getLimitsInfo()
	{
		return limitsInfo;
	}

	public void setLimitsInfo(ClientAccumulateLimitsInfo limitsInfo)
	{
		this.limitsInfo = limitsInfo;
	}

	public void setOverallAmountPerDayLimits(List<Limit> overallAmountPerDayLimits)
	{
		this.overallAmountPerDayLimits.addAll(overallAmountPerDayLimits);
	}

	public List<Limit> getOverallAmountPerDayLimits()
	{
		return Collections.unmodifiableList(overallAmountPerDayLimits);
	}
}
