package com.rssl.phizic.web.ermb.migration.list.client;

import com.rssl.phizic.business.ermb.migration.list.entity.migrator.Conflict;
import com.rssl.phizic.web.actions.ActionFormBase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Форма для страницы разрешения конфликтов при миграции
 * @author Puzikov
 * @ created 10.12.13
 * @ $Author$
 * @ $Revision$
 */

public class ResolveConflictClientForm extends ActionFormBase
{
	private String id;
	private boolean vip;
	private Map<String, String> result = new HashMap<String, String>();
	private List<Conflict> conflicts = new ArrayList<Conflict>();

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public Map<String, String> getResult()
	{
		return result;
	}

	public void setResult(Map<String, String> result)
	{
		this.result = result;
	}

	public boolean isVip()
	{
		return vip;
	}

	public void setVip(boolean vip)
	{
		this.vip = vip;
	}

	public List<Conflict> getConflicts()
	{
		return conflicts;
	}

	public void setConflicts(List<Conflict> conflicts)
	{
		this.conflicts = conflicts;
	}
}
