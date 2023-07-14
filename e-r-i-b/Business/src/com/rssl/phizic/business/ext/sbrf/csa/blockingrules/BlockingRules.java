package com.rssl.phizic.business.ext.sbrf.csa.blockingrules;

import java.util.Calendar;

/**
 * @author vagin
 * @ created 21.08.2012
 * @ $Author$
 * @ $Revision$
 * Класс сущность. Правило блокировки.
 */
public class BlockingRules
{
	private Long id;
	private String name;        // название правила
	private String departments; //перечисленные через запятую коды подразделений или маски
	private BlockingState state;      //состояние заблокировано(true)/не заблокировано(false)
	private Calendar resumingTime; // время восстановления доступа

	//настройки параметров уведомления
	private Calendar fromPublishDate;
	private Calendar toPublishDate;
	private Calendar fromRestrictionDate;
	private Calendar toRestrictionDate;

	private boolean applyToERIB;    //распространяется на канал ЕРИБ
	private boolean applyToMAPI;    //распространяется на канал мАпи
	private boolean applyToATM;     //распространяется на канал АТМ
	private boolean applyToERMB;    //распространяется на канал ЕРМБ

	private String ERIBMessage;     //сообщение для канала ЕРИБ
	private String mapiMessage;     //сообщение для канала мАпи
	private String ATMMessage;      //сообщение для канала АТМ
	private String ERMBMessage;     //сообщение для канала ЕРМБ

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getDepartments()
	{
		return departments;
	}

	public void setDepartments(String departments)
	{
		this.departments = departments;
	}

	public BlockingState getState()
	{
		return state;
	}

	public void setState(BlockingState state)
	{
		this.state = state;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Calendar getResumingTime()
	{
		return resumingTime;
	}

	public void setResumingTime(Calendar resumingTime)
	{
		this.resumingTime = resumingTime;
	}

	/**
	 *
	 * @return период публикации уведомления о блокировке c
	 */
	public Calendar getFromPublishDate()
	{
		return fromPublishDate;
	}

	/**
	 *
	 * @param fromPublishDate период публикации уведомления о блокировке c
	 */
	public void setFromPublishDate(Calendar fromPublishDate)
	{
		this.fromPublishDate = fromPublishDate;
	}

	/**
	 *
	 * @return период публикации уведомления о блокировке по
	 */
	public Calendar getToPublishDate()
	{
		return toPublishDate;
	}

	/**
	 *
	 * @param toPublishDate период публикации уведомления о блокировке по
	 */
	public void setToPublishDate(Calendar toPublishDate)
	{
		this.toPublishDate = toPublishDate;
	}

	/**
	 *
	 * @return период, отображаемый в уведомлении о блокировке с
	 */
	public Calendar getFromRestrictionDate()
	{
		return fromRestrictionDate;
	}

	/**
	 *
	 * @param fromRestrictionDate период, отображаемый в уведомлении о блокировке с
	 */
	public void setFromRestrictionDate(Calendar fromRestrictionDate)
	{
		this.fromRestrictionDate = fromRestrictionDate;
	}

	/**
	 *
	 * @return период, отображаемый в уведомлении о блокировке по
	 */
	public Calendar getToRestrictionDate()
	{
		return toRestrictionDate;
	}

	/**
	 * @param toRestrictionDate период, отображаемый в уведомлении о блокировке по
	 */
	public void setToRestrictionDate(Calendar toRestrictionDate)
	{
		this.toRestrictionDate = toRestrictionDate;
	}

	public boolean isApplyToERIB()
	{
		return applyToERIB;
	}

	public void setApplyToERIB(boolean applyToERIB)
	{
		this.applyToERIB = applyToERIB;
	}

	public boolean isApplyToERMB()
	{
		return applyToERMB;
	}

	public void setApplyToERMB(boolean applyToERMB)
	{
		this.applyToERMB = applyToERMB;
	}

	public boolean isApplyToMAPI()
	{
		return applyToMAPI;
	}

	public void setApplyToMAPI(boolean applyToMAPI)
	{
		this.applyToMAPI = applyToMAPI;
	}

	public boolean isApplyToATM()
	{
		return applyToATM;
	}

	public void setApplyToATM(boolean applyToATM)
	{
		this.applyToATM = applyToATM;
	}

	public String getERIBMessage()
	{
		return ERIBMessage;
	}

	public void setERIBMessage(String ERIBMessage)
	{
		this.ERIBMessage = ERIBMessage;
	}

	public String getERMBMessage()
	{
		return ERMBMessage;
	}

	public void setERMBMessage(String ERMBMessage)
	{
		this.ERMBMessage = ERMBMessage;
	}

	public String getMapiMessage()
	{
		return mapiMessage;
	}

	public void setMapiMessage(String mapiMessage)
	{
		this.mapiMessage = mapiMessage;
	}

	public String getATMMessage()
	{
		return ATMMessage;
	}

	public void setATMMessage(String ATMMessage)
	{
		this.ATMMessage = ATMMessage;
	}
}