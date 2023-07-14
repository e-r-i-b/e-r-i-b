package com.rssl.phizic.web.mail;

import com.rssl.phizic.web.actions.ActionFormBase;

/**
 * @author akrenev
 * @ created 27.05.2014
 * @ $Author$
 * @ $Revision$
 *
 * ����� �������� � �������
 */

public class MailManagerForm extends ActionFormBase
{
	private Long mailId;
	private String action;
	private boolean fromQuestionary;

	/**
	 * @return ������������� ���������
	 */
	public Long getMailId()
	{
		return mailId;
	}

	/**
	 * ������ ������������� ���������
	 * @param mailId ������������� ���������
	 */
	@SuppressWarnings("UnusedDeclaration") // �������
	public void setMailId(Long mailId)
	{
		this.mailId = mailId;
	}

	/**
	 * @return ��������
	 */
	public String getAction()
	{
		return action;
	}

	/**
	 * ������ ��������
	 * @param action ��������
	 */
	@SuppressWarnings("UnusedDeclaration") // �������
	public void setAction(String action)
	{
		this.action = action;
	}

	/**
	 * @return true -- ������ � ������ �������
	 */
	public boolean isFromQuestionary()
	{
		return fromQuestionary;
	}

	/**
	 * ������ ������� ����, ��� ������ � ������ �������
	 * @param fromQuestionary true -- ������ � ������ �������
	 */
	@SuppressWarnings("UnusedDeclaration") // �������
	public void setFromQuestionary(boolean fromQuestionary)
	{
		this.fromQuestionary = fromQuestionary;
	}

}
