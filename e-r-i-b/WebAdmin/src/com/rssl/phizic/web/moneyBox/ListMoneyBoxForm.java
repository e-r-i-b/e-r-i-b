package com.rssl.phizic.web.moneyBox;

import com.rssl.phizic.business.resources.external.AutoSubscriptionLink;
import com.rssl.phizic.web.autopayments.PersonFormBase;

/**
 * @author osminin
 * @ created 14.10.14
 * @ $Author$
 * @ $Revision$
 * ����� ��� ����������� ������ ������� � ��� ����������
 */
public class ListMoneyBoxForm extends PersonFormBase<AutoSubscriptionLink>
{
	private Long id;
	private String buttonName;

	/**
	 * @return ������������� ��������� �������
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * @param id ������������� ��������� �������
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return ������������ ���-������, ������� ����� ���������� ����� ������������� ������
	 */
	public String getButtonName()
	{
		return buttonName;
	}

	/**
	 * @param buttonName ������������ ���-������, ������� ����� ���������� ����� ������������� ������
	 */
	public void setButtonName(String buttonName)
	{
		this.buttonName = buttonName;
	}
}
