package com.rssl.phizicgate.manager.routing;

import com.rssl.phizic.gate.utils.ExternalSystem;

/**
 * @author akrenev
 * @ created 07.12.2009
 * @ $Author$
 * @ $Revision$
 */
public class Adapter implements ExternalSystem
{
	private Long id;
	private String UUID;
	private String name;
	private AdapterType type = AdapterType.NONE;
	private String listenerUrl;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getUUID()
	{
		return UUID;
	}

	public void setUUID(String UUID)
	{
		this.UUID = UUID;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return ��� ��������
	 */
	public AdapterType getType()
	{
		return type;
	}

	/**
	 * @param type ��� ��������
	 */
	public void setType(AdapterType type)
	{
		this.type = type;
	}

	/**
	 * @return ����� ����������� ��������� �� ��
	 */
	public String getListenerUrl()
	{
		return listenerUrl;
	}

	/**
	 * ���������� ����� ����������� ��������� �� ��
	 * @param listenerUrl - ����� �����������
	 */
	public void setListenerUrl(String listenerUrl)
	{
		this.listenerUrl = listenerUrl;
	}
}
