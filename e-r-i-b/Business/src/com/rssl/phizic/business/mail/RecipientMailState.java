package com.rssl.phizic.business.mail;

/**
 * Enum статусов отображаемых на форме полученных писем.
 * @author komarov
 * @ created 24.05.2011
 * @ $Author$
 * @ $Revision$
 */

public enum RecipientMailState implements EnumWithDescription
{
	NEW("Новое"),//Новое.
	DRAFT("Черновик ответа"),//Черновик ответа.
	READ("Прочитано"),//Прочитано.
	ANSWER("Ответ направлен"),//Ответ направлен.
	
	NEW_EPLOYEE_MAIL("Новое письмо сотрудника"),//Новое письмо сотрудника.
	ANSWER_EPLOYEE_MAIL("Ответ сотрудника"),
	NONE("Черновик нового письма");//Отправленные письма.

	RecipientMailState(String description)
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
