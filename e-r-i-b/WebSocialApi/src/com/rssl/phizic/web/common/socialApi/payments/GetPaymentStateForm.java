package com.rssl.phizic.web.common.socialApi.payments;

import com.rssl.phizic.web.actions.ActionFormBase;

/**
 * Проверка статуса документа
 * @author Dorzhinov
 * @ created 25.06.2013
 * @ $Author$
 * @ $Revision$
 */
public class GetPaymentStateForm extends ActionFormBase
{
	//in
	private Long id; //id документа
	//out
	private String state; //статус документа

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
