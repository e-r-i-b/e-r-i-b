package com.rssl.phizic.web.atm.payments.forms;

import com.rssl.phizic.web.actions.ActionFormBase;

/**
 * ����� ������� �������
 * @author Jatsky
 * @ created 24.06.2013
 * @ $Author$
 * @ $Revision$
 */

public class PaymentStateForm extends ActionFormBase
{
	Long id; //�� �������
	String state; //������ �������

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getState()
	{
		return state;
	}

	public void setState(String state)
	{
		this.state = state;
	}
}
