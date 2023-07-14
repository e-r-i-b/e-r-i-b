package com.rssl.phizic.web.mail;

import com.rssl.phizic.business.persons.ActivePerson;

import java.util.List;

/**
 * @author akrenev
 * @ created 23.05.2014
 * @ $Author$
 * @ $Revision$
 *
 * Форма выбора клиента для отправки письма
 */

public class ChooseClientForm extends com.rssl.phizic.web.ext.sbrf.mail.EditMailForm
{
	private Long mailRecipientId;
	private String mailRecipientName;
	private List<ActivePerson> data;

	/**
	 * @return идентификатор получателя
	 */
	public Long getMailRecipientId()
	{
		return mailRecipientId;
	}

	/**
	 * задать идентификатор получателя
	 * @param mailRecipientId идентификатор получателя
	 */
	@SuppressWarnings("UnusedDeclaration") // jsp
	public void setMailRecipientId(Long mailRecipientId)
	{
		this.mailRecipientId = mailRecipientId;
	}

	/**
	 * @return имя получателя
	 */
	public String getMailRecipientName()
	{
		return mailRecipientName;
	}

	/**
	 * задать имя получателя
	 * @param mailRecipientName имя получателя
	 */
	@SuppressWarnings("UnusedDeclaration") // jsp
	public void setMailRecipientName(String mailRecipientName)
	{
		this.mailRecipientName = mailRecipientName;
	}

	/**
	 * задать список клиентов
	 * @param data список клиентов
	 */
	public void setData(List<ActivePerson> data)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.data = data;
	}

	/**
	 * @return список клиентов
	 */
	public List<ActivePerson> getData()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return data;
	}

}
