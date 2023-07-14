package com.rssl.phizic.web.common.mobile.payments.internetShops;

import com.rssl.phizic.web.actions.ActionFormBase;

/**
 * Проверка статуса выпуска билетов
 * @author Dorzhinov
 * @ created 25.06.2013
 * @ $Author$
 * @ $Revision$
 */
public class GetTicketsStatusForm extends ActionFormBase
{
	//in
	private Long id; //id документа
	//out
	private String ticketsStatus; //статус выпуска билетов

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getTicketsStatus()
	{
		return ticketsStatus;
	}

	public void setTicketsStatus(String ticketsStatus)
	{
		this.ticketsStatus = ticketsStatus;
	}
}
