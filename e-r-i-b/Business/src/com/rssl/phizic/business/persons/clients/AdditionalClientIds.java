package com.rssl.phizic.business.persons.clients;

import java.util.Set;
import java.util.HashSet;

/**
 * Дополнительные id клиента, если к одному клиенту привязано несколлько "внешних" клиентов
 * @author egorova
 * @ created 27.01.2009
 * @ $Author$
 * @ $Revision$
 */
public class AdditionalClientIds
{
	private Long id;
	private String clientId;
	private String parentId;
	private Set<AdditionalClientIds> addClientIds = new HashSet<AdditionalClientIds>();

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getClientId()
	{
		return clientId;
	}

	public void setClientId(String clientId)
	{
		this.clientId = clientId;
	}
	
	public String getParentId()
	{
		return parentId;
	}

	public void setParentId(String parentId)
	{
		this.parentId = parentId;
	}

	public Set<AdditionalClientIds> getAddClientIds()
	{
		return addClientIds;
	}

	private void setAddClientIds(Set<AdditionalClientIds> addClientIds)
	{
		this.addClientIds = addClientIds;
	}

	public void clearOldAddClientIds()
	{
		addClientIds.clear();
	}

	public void addAddClientIds(AdditionalClientIds addClientIds1)
	{
		addClientIds.add(addClientIds1);
	}

	//Если не пустие "дети", значит это основной id клиента
	public boolean isMain()
	{
		return !getAddClientIds().isEmpty();
	}
}
