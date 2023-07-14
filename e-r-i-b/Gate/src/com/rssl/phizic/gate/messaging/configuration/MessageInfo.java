package com.rssl.phizic.gate.messaging.configuration;

import java.util.*;

/**
 * �������� ����������� ���������, ������-����� � �� �������������� ����������� �������.
 *
 * @author Roshka
 * @ created 03.10.2006
 * @ $Author$
 * @ $Revision$
 */

public class MessageInfo
{
	private String      name;
	private String      endpoint;
	private String      subsystem;
	private Set<String> responses;
	private Map<String, String> attributes;
	private String      cacheClassName;

	/**
	 * ctor
	 * @param name ��� ���������
	 * @param endpoint ������������� �������-����������
	 * @param responses ����� ��������� ������� ������ � �����
	 * @param subsystem ����������, � ������� ��������� ���������
	 * @param cacheClassName ��� ������ ��� ���������� ������ �����������, ��������� MessagesCache.
	 * @param attributes ����� ���.���������� � �� ��������
	 */
	public MessageInfo(String name, String endpoint, Set<String> responses, String subsystem,String cacheClassName, Map<String, String> attributes)
	{
		this.name = name;
		this.endpoint = endpoint;
		this.subsystem = subsystem;
		this.responses = new HashSet<String>(responses);
		this.attributes = attributes;
		this.cacheClassName = cacheClassName;
	}

	/**
	 * @return ������������� �������-����������
	 */
	public String getEndpoint()
	{
		return endpoint;
	}

	/**
	 * @return ����� ��������� ������� ������ � �����
	 */
	public Set<String> getResponses()
	{
		return Collections.unmodifiableSet(responses);
	}

	/**
	 * @return ��� ���������
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @return ����������, � ������� ��������� ���������
	 */
	public String getSubsystem()
	{
		return subsystem;
	}

	/**
	 * @return ����� ���������� � �� ��������
	 */
	public Map<String, String> getAttributes()
	{
		return Collections.unmodifiableMap(attributes);
	}

	/**
	 * ��� ������ ��� ���������� ������ �����������, ��������� MessagesCache.
	 * @return
	 */
	public String getCacheClassName()
	{
		return cacheClassName;
	}

	/**
	 * @param key ��� ��������
	 * @return �������� ��������
	 */
	public String getAttribute(String key)
	{
		return attributes.get(key);
	}
}