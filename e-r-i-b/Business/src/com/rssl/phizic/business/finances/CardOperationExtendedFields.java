package com.rssl.phizic.business.finances;

import java.util.Calendar;

/**
 * ƒополнительные пол€ карточной операции
 * @author Jatsky
 * @ created 31.10.14
 * @ $Author$
 * @ $Revision$
 */

public class CardOperationExtendedFields
{
	private Long id;
	private Long cardOperationId;
	private Long pushUID;
	private Long parentPushUID;
	private String authCode;
	private String operationTypeWay;
	private Calendar loadDateMAPI;
	private boolean categoryChange;
	private Calendar date;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Long getCardOperationId()
	{
		return cardOperationId;
	}

	public void setCardOperationId(Long cardOperationId)
	{
		this.cardOperationId = cardOperationId;
	}

	public Long getPushUID()
	{
		return pushUID;
	}

	public void setPushUID(Long pushUID)
	{
		this.pushUID = pushUID;
	}

	public Long getParentPushUID()
	{
		return parentPushUID;
	}

	public void setParentPushUID(Long parentPushUID)
	{
		this.parentPushUID = parentPushUID;
	}

	public String getAuthCode()
	{
		return authCode;
	}

	public void setAuthCode(String authCode)
	{
		this.authCode = authCode;
	}

	public String getOperationTypeWay()
	{
		return operationTypeWay;
	}

	public void setOperationTypeWay(String operationTypeWay)
	{
		this.operationTypeWay = operationTypeWay;
	}

	public Calendar getLoadDateMAPI()
	{
		return loadDateMAPI;
	}

	public void setLoadDateMAPI(Calendar loadDateMAPI)
	{
		this.loadDateMAPI = loadDateMAPI;
	}

	public boolean isCategoryChange()
	{
		return categoryChange;
	}

	public void setCategoryChange(boolean categoryChange)
	{
		this.categoryChange = categoryChange;
	}

	public Calendar getDate()
	{
		return date;
	}

	public void setDate(Calendar date)
	{
		this.date = date;
	}
}
