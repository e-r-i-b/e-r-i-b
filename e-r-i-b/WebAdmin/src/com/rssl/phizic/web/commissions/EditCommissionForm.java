package com.rssl.phizic.web.commissions;

import com.rssl.phizic.business.commission.CommissionRule;
import com.rssl.phizic.business.commission.CommissionType;
import com.rssl.phizic.web.actions.ActionFormBase;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Evgrafov
 * @ created 13.09.2007
 * @ $Author: erkin $
 * @ $Revision: 24884 $
 */

public class EditCommissionForm extends ActionFormBase
{
	private Long           id;
	private CommissionType type;
	private Collection<? extends CommissionRule> rules;
	private Map<String, Object> fields = new HashMap<String, Object>();
	private Map<String, Object> result;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Collection<? extends CommissionRule> getRules()
	{
		return rules;
	}

	public void setRules(Collection<? extends CommissionRule> rules)
	{
		this.rules = rules;
	}

	public CommissionType getType()
	{
		return type;
	}

	public void setType(CommissionType type)
	{
		this.type = type;
	}

	public Map<String, Object> getFields()
	{
		return fields;
	}

	public void setFields(Map<String, Object> fields)
	{
		this.fields = fields;
	}

	public Object getField(String key)
	{
		return fields.get(key);
	}

	public void setField(String key, Object obj)
	{
		fields.put(key, obj);
	}

	public Map<String, Object> getResult()
	{
		return result;
	}

	public void setResult(Map<String, Object> result)
	{
		this.result = result;
	}
}