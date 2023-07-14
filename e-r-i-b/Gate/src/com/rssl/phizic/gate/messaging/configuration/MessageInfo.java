package com.rssl.phizic.gate.messaging.configuration;

import java.util.*;

/**
 * ќписание соответсви€ сообщений, запрос-ответ и их принадлежности конкретному сервису.
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
	 * @param name им€ сообщени€
	 * @param endpoint идентификатор сервиса-получател€
	 * @param responses имена сообщений могущих прийти в ответ
	 * @param subsystem ѕодсистема, к которой относитс€ сообщение
	 * @param cacheClassName »м€ класса дл€ вычислени€ ключей кешировани€, наследник MessagesCache.
	 * @param attributes набор доп.аттрибутов и их значений
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
	 * @return идентификатор сервиса-получател€
	 */
	public String getEndpoint()
	{
		return endpoint;
	}

	/**
	 * @return имена сообщений могущих прийти в ответ
	 */
	public Set<String> getResponses()
	{
		return Collections.unmodifiableSet(responses);
	}

	/**
	 * @return им€ сообщени€
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @return ѕодсистема, к которой относитс€ сообщение
	 */
	public String getSubsystem()
	{
		return subsystem;
	}

	/**
	 * @return набор аттрибутов и их значений
	 */
	public Map<String, String> getAttributes()
	{
		return Collections.unmodifiableMap(attributes);
	}

	/**
	 * »м€ класса дл€ вычислени€ ключей кешировани€, наследник MessagesCache.
	 * @return
	 */
	public String getCacheClassName()
	{
		return cacheClassName;
	}

	/**
	 * @param key им€ атрибута
	 * @return «начение атрибута
	 */
	public String getAttribute(String key)
	{
		return attributes.get(key);
	}
}