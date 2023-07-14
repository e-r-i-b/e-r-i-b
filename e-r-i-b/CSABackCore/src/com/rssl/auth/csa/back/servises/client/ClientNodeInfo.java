package com.rssl.auth.csa.back.servises.client;

/**
 * @author akrenev
 * @ created 16.10.2014
 * @ $Author$
 * @ $Revision$
 *
 * »нформаци€ о блоке клиента
 */

@SuppressWarnings("PublicInnerClass")
public class ClientNodeInfo
{
	private Long id;
	private Type type;
	private State state;

	/**
	 * @return идентификатор блока
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * задать идентификатор блока
	 * @param id идентификатор
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return тип блока
	 */
	public Type getType()
	{
		return type;
	}

	/**
	 * задать тип блока
	 * @param type тип
	 */
	public void setType(Type type)
	{
		this.type = type;
	}

	/**
	 * @return состо€ние блока
	 */
	public State getState()
	{
		return state;
	}

	/**
	 * задать состо€ние блока
	 * @param state состо€ние
	 */
	public void setState(State state)
	{
		this.state = state;
	}

	/**
	 * тип блока
	 */
	public enum Type
	{
		MAIN,              //ќсновной
		TEMPORARY          //–езервный
	}

	/**
	 * состо€ние блока
	 */
	public enum State
	{
		WAIT_MIGRATION,    //ожидаетс€ миграци€ данных из этого блока
		PROCESS_MIGRATION, //идет миграци€, происходит перенос данных из этого блока в другой
		ACTIVE             //клиент св€зан с блоком, процесс миграции завершен
	}
}
