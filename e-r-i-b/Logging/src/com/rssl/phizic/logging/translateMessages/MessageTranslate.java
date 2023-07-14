package com.rssl.phizic.logging.translateMessages;


/**
 * @author Mescheryakova
 * @ created 14.07.2011
 * @ $Author$
 * @ $Revision$
 * ������� ����� "��� �������" � "��� ������" ������� ���������
 */

public class MessageTranslate
{
	private Long id;
	private String code;    // ����������� ���������
	private String translate; // �������
	private TypeMessageTranslate type;   // ��� �������: R-������, A-�����
	private boolean isNew; // ����� ��� ������ ���������
	private LogType logType; // ��� �����������: ���������� ��� ������������

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
