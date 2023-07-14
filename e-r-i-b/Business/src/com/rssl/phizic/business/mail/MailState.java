package com.rssl.phizic.business.mail;

/**
 * Enum возможных статусов письма
 * @author komarov
 * @ created 11.07.2011
 * @ $Author$
 * @ $Revision$
 */

public enum MailState implements EnumWithDescription
{
	NEW("Отправлено"), // Письмо не является черновиком
	CLIENT_DRAFT("Черновик"), //Письмо черновик ответа клиента
	EMPLOYEE_DRAFT("Черновик"), // Письмо черновик ответа сотрудника
	TEMPLATE("Новое");          //Новое письмо, временное состояние, в системе такого не должно быть видно 

	MailState(String description)
	{
		this.description = description;
	}

	private String description;


	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}
}
