package com.rssl.phizic.business.mail;

import com.rssl.phizic.auth.CommonLogin;

import java.util.Calendar;

/**
 * @author Gainanov
 * @ created 26.02.2007
 * @ $Author$
 * @ $Revision$
 */
public class Mail
{
	private Long        id;
    private Long        parentId;//id письма на которое отвечаем;
	private Long        num;
	private MailType    type;
	private MailState state = MailState.NEW;
	private String      subject;
	private String      body;
	private Calendar    date;
	private CommonLogin sender;  //Отправитель
	private CommonLogin employee;//Сотрудник работающий с письмом.
	private MailDirection direction;
	private String      phone;
	private RecipientType recipientType;//Тип получателя
	private String recipientName;//Имя получатели группы название департамента
	private Long recipientId;//Идентификатор группы, логина клиента, департамента
	private Boolean important;
	private String fileName;
	private byte[] data;
	private Boolean deleted = false;
	private Boolean show = true;
	private MailResponseMethod responseMethod=MailResponseMethod.BY_PHONE;//Способ получения ответа
	private String email;//Адрес электронной почты куда присылать уведомления о письмах.
	private MailSubject theme;//Тематика обращения
	private Long responseTime;//Время ответа на обращение клиента

	public CommonLogin getEmployee()
	{
		return employee;
	}

	public void setEmployee(CommonLogin employee)
	{
		this.employee = employee;
	}

	public Boolean isShow()
	{
		return show;
	}

	public Boolean getShow()
	{
		return show;
	}

	public void setShow(Boolean show)
	{
		this.show = show;
	}

	public Long getParentId()
	{
		return parentId;
	}

	public void setParentId(Long parentId)
	{
		this.parentId = parentId;
	}

		

	public MailState getState()
	{
		return state;
	}

	public void setState(MailState state)
	{
		this.state = state;
	}

	public MailResponseMethod getResponseMethod()
	{
		return responseMethod;
	}

	public void setResponseMethod(MailResponseMethod responseMethod)
	{
		this.responseMethod = responseMethod;
	}

	public RecipientType getRecipientType()
	{
		return recipientType;
	}

	public void setRecipientType(RecipientType recipientType)
	{
		this.recipientType = recipientType;
	}

	public String getRecipientName()
	{
		return recipientName;
	}

	public void setRecipientName(String recipientName)
	{
		this.recipientName = recipientName;
	}

	public Long getRecipientId()
	{
		return recipientId;
	}

	public void setRecipientId(Long recipientId)
	{
		this.recipientId = recipientId;
	}

	public Long getNum()
	{
		return num;
	}

	public void setNum(Long num)
	{
		this.num = num;
	}

	public MailDirection getDirection()
	{
		return direction;
	}

	public void setDirection(MailDirection direction)
	{
		this.direction = direction;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public MailType getType()
	{
		return type;
	}

	public void setType(MailType type)
	{
		this.type = type;
	}

	public String getSubject()
	{
		return subject;
	}

	public void setSubject(String subject)
	{
		this.subject = subject;
	}

	public String getBody()
	{
		return body;
	}

	public void setBody(String body)
	{
		this.body = body;
	}

	public Calendar getDate()
	{
		return date;
	}

	public void setDate(Calendar date)
	{
		this.date = date;
	}

	public CommonLogin getSender()
	{
		return sender;
	}

	public void setSender(CommonLogin sender)
	{
		this.sender = sender;
	}

	/**
	 * @return признак обязательности прочтения письма
	 */
	public Boolean getImportant()
	{
		return important;
	}
    	
	public String getPhone()
	{
		return phone;
	}

	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getFileName()
	{
		return fileName;
	}

	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}

	public byte[] getData()
	{
		return data;
	}

	public void setData(byte[] data)
	{
		this.data = data;
	}

	public Boolean getDeleted()
	{
		return deleted;
	}

	public void setDeleted(Boolean deleted)
	{
		this.deleted = deleted;
	}

	/**
	 *  Установить признак обязательности прочтения письма
	 * @param important признак обязательности прочтения письма
	 */
	public void setImportant(Boolean important)
	{
		this.important = important;
	}

	public MailSubject getTheme()
	{
		return theme;
	}

	public void setTheme(MailSubject theme)
	{
		this.theme = theme;
	}

	/**
	 * @return скорость ответа на письмо клиента в миллисекундах
	 */
	public Long getResponseTime()
	{
		return responseTime;
	}

	/**
	 * @param responseTime скорость ответа на письмо клиента в миллисекундах
	 */
	public void setResponseTime(Long responseTime)
	{
		this.responseTime = responseTime;
	}

	public String getDate2String()
	{
		return String.format("%1$td.%1$tm.%1$tY %1$tH:%1$tM:%1$tS", date);
	}
}
