package com.rssl.phizic.csaadmin.business.operation;

import com.rssl.phizic.csaadmin.business.session.Session;

/**
 * @author mihaylov
 * @ created 04.11.13
 * @ $Author$
 * @ $Revision$
 * Контекст операции
 */
public class OperationContext
{
	private Long id;            //идентификатор контекста
	private Session session;    //сессия в рамках которой выполняется операция
	private String context;  //собстенно сам контекст, XML в которую сериализуются параметры операции
	private OperationContextState state; //статус

	/**
	 * Пустой конструктор, для хибернета
	 */
	public OperationContext()
	{
	}

	/**
	 * Конструктор
	 * @param session - сессия в рамках которой действует контекст
	 */
	public OperationContext(Session session)
	{
		this.session = session;
		this.state = OperationContextState.ACTIVE;
	}

	/**
	 * @return идентификтор контекста
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * Установить идентификатор
	 * @param id - идентификатор
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return сессия
	 */
	public Session getSession()
	{
		return session;
	}

	/**
	 * Установить сессию
	 * @param session - сессия
	 */
	public void setSession(Session session)
	{
		this.session = session;
	}

	/**
	 * @return контекст операции
	 */
	public String getContext()
	{
		return context;
	}

	/**
	 * Установить контекст операции
	 * @param context - контекст
	 */
	public void setContext(String context)
	{
		this.context = context;
	}

	/**
	 * @return статус контекста
	 */
	public OperationContextState getState()
	{
		return state;
	}

	/**
	 * Установить статус контекста
	 * @param state - статус
	 */
	public void setState(OperationContextState state)
	{
		this.state = state;
	}
}
