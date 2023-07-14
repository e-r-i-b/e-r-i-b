package com.rssl.phizic.gate.mail;

/**
 * @author mihaylov
 * @ created 29.05.14
 * @ $Author$
 * @ $Revision$
 *
 * Сущность для представления исходящего письма сотрудника
 */
public class OutcomeMailListEntity extends MailListEntityBase
{
	private Long   recipientId;
	private String recipientFIO;

	/**
	 * @return идентификатор получателя
	 */
	public Long getRecipientId()
	{
		return recipientId;
	}

	/**
	 * Установить идентификатор получателя
	 * @param recipientId - идентификатор получателя
	 */
	public void setRecipientId(Long recipientId)
	{
		this.recipientId = recipientId;
	}

	/**
	 * @return ФИО получателя
	 */
	public String getRecipientFIO()
	{
		return recipientFIO;
	}

	/**
	 * Установить ФИО получателя
	 * @param recipientFIO - ФИО получателя
	 */
	public void setRecipientFIO(String recipientFIO)
	{
		this.recipientFIO = recipientFIO;
	}
}
