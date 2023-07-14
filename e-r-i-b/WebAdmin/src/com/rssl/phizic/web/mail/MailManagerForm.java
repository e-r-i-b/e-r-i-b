package com.rssl.phizic.web.mail;

import com.rssl.phizic.web.actions.ActionFormBase;

/**
 * @author akrenev
 * @ created 27.05.2014
 * @ $Author$
 * @ $Revision$
 *
 * форма перехода к письмам
 */

public class MailManagerForm extends ActionFormBase
{
	private Long mailId;
	private String action;
	private boolean fromQuestionary;

	/**
	 * @return идентификатор сообщения
	 */
	public Long getMailId()
	{
		return mailId;
	}

	/**
	 * задать идентификатор сообщения
	 * @param mailId идентификатор сообщения
	 */
	@SuppressWarnings("UnusedDeclaration") // реквест
	public void setMailId(Long mailId)
	{
		this.mailId = mailId;
	}

	/**
	 * @return действие
	 */
	public String getAction()
	{
		return action;
	}

	/**
	 * задать действие
	 * @param action действие
	 */
	@SuppressWarnings("UnusedDeclaration") // реквест
	public void setAction(String action)
	{
		this.action = action;
	}

	/**
	 * @return true -- пришли с анкеты клиента
	 */
	public boolean isFromQuestionary()
	{
		return fromQuestionary;
	}

	/**
	 * задать признак того, что пришли с анкеты клиента
	 * @param fromQuestionary true -- пришли с анкеты клиента
	 */
	@SuppressWarnings("UnusedDeclaration") // реквест
	public void setFromQuestionary(boolean fromQuestionary)
	{
		this.fromQuestionary = fromQuestionary;
	}

}
