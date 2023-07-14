package com.rssl.phizic.gate.mail;

/**
 * @author mihaylov
 * @ created 30.05.14
 * @ $Author$
 * @ $Revision$
 *
 * Сущность для представления удаленного письма сотрудника
 */
public class RemovedMailListEntity extends MailListEntityBase
{
	private String recipientName;
	private String directionDescription;

	/**
	 * @return Название получателя письма.
	 * Если письмо отправлялось клиентом, то название департамента к которому привязан клиент.
	 * Если сотрудником, то ФИО клиента.
	 */
	public String getRecipientName()
	{
		return recipientName;
	}

	/**
	 * Установить наименование получателя письма
	 * @param recipientName - наименование получателя письма
	 */
	public void setRecipientName(String recipientName)
	{
		this.recipientName = recipientName;
	}

	/**
	 * @return описание направления письма(отправленное/полученное)
	 */
	public String getDirectionDescription()
	{
		return directionDescription;
	}

	/**
	 * Установить описание направления письма(отправленное/полученное)
	 * @param directionDescription - описание направления письма(отправленное/полученное)
	 */
	public void setDirectionDescription(String directionDescription)
	{
		this.directionDescription = directionDescription;
	}
}
