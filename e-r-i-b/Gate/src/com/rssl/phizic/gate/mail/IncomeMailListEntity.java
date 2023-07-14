package com.rssl.phizic.gate.mail;

/**
 * @author mihaylov
 * @ created 20.05.14
 * @ $Author$
 * @ $Revision$
 *
 * Сущность для представления входящего письма сотрудника
 */
public class IncomeMailListEntity extends MailListEntityBase
{
	private String senderFIO;
	private Long senderId;

	/**
	 * @return ФИО отправителя
	 */
	public String getSenderFIO()
	{
		return senderFIO;
	}

	/**
	 * Установить ФИО отпаравителя
	 * @param senderFIO - ФИО отпаравителя
	 */
	public void setSenderFIO(String senderFIO)
	{
		this.senderFIO = senderFIO;
	}

	/**
	 * @return идентификатор отправителя в блоке
	 */
	public Long getSenderId()
	{
		return senderId;
	}

	/**
	 * Установить идентификатор отправителя в блоке
	 * @param senderId - идентификатор отправителя в блоке
	 */
	public void setSenderId(Long senderId)
	{
		this.senderId = senderId;
	}
}
