package com.rssl.phizic.web.mail;

import com.rssl.phizic.business.persons.ActivePerson;

import java.util.List;

/**
 * @author akrenev
 * @ created 23.05.2014
 * @ $Author$
 * @ $Revision$
 *
 * ����� ������ ������� ��� �������� ������
 */

public class ChooseClientForm extends com.rssl.phizic.web.ext.sbrf.mail.EditMailForm
{
	private Long mailRecipientId;
	private String mailRecipientName;
	private List<ActivePerson> data;

	/**
	 * @return ������������� ����������
	 */
	public Long getMailRecipientId()
	{
		return mailRecipientId;
	}

	/**
	 * ������ ������������� ����������
	 * @param mailRecipientId ������������� ����������
	 */
	@SuppressWarnings("UnusedDeclaration") // jsp
	public void setMailRecipientId(Long mailRecipientId)
	{
		this.mailRecipientId = mailRecipientId;
	}

	/**
	 * @return ��� ����������
	 */
	public String getMailRecipientName()
	{
		return mailRecipientName;
	}

	/**
	 * ������ ��� ����������
	 * @param mailRecipientName ��� ����������
	 */
	@SuppressWarnings("UnusedDeclaration") // jsp
	public void setMailRecipientName(String mailRecipientName)
	{
		this.mailRecipientName = mailRecipientName;
	}

	/**
	 * ������ ������ ��������
	 * @param data ������ ��������
	 */
	public void setData(List<ActivePerson> data)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.data = data;
	}

	/**
	 * @return ������ ��������
	 */
	public List<ActivePerson> getData()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return data;
	}

}
