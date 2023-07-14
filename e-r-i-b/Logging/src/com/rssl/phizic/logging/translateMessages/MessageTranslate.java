package com.rssl.phizic.logging.translateMessages;


/**
 * @author Mescheryakova
 * @ created 14.07.2011
 * @ $Author$
 * @ $Revision$
 * ѕеревод полей "“ип запроса" и "“ип за€вки" журнала сообщений
 */

public class MessageTranslate
{
	private Long id;
	private String code;    // переводимое сообщение
	private String translate; // перевод
	private TypeMessageTranslate type;   // тип запроса: R-запрос, A-ответ
	private boolean isNew; // новое или старое сообщение
	private LogType logType; // тип логировани€: финансовое или нефинансовое

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	public String getTranslate()
	{
		return translate;
	}

	public void setTranslate(String translate)
	{
		this.translate = translate;
	}

	public TypeMessageTranslate getType()
	{
		return type;
	}

	public void setType(TypeMessageTranslate type)
	{
		this.type = type;
	}

	public boolean getIsNew()
	{
		return this.isNew;
	}

	public void setLogType(LogType logType)
	{
		this.logType = logType;
	}

	public void setIsNew(boolean isNew)
	{
		this.isNew = isNew;
	}

	public LogType getLogType()
	{
		return this.logType;
	}
}
