package com.rssl.phizic.web.auth;

import com.rssl.auth.csa.front.operations.auth.OperationInfo;

/**
 * @author niculichev
 * @ created 19.09.2012
 * @ $Author$
 * @ $Revision$
 */
public class AuthStageFormBase extends AuthenticationFormBase
{
	private boolean successNextStage = false; // успешно ли произошло переключение состо€ни€
	private OperationInfo operationInfo;

	private String state;
	private String stage;
	private String fieldName;
	private String value;
	private String errorText;
	private String popupId;
	private String disabled;
	private String messageText;
	private String messageId;
	private boolean captcha;

	public OperationInfo getOperationInfo()
	{
		return operationInfo;
	}

	public void setOperationInfo(OperationInfo operationInfo)
	{
		this.operationInfo = operationInfo;
	}

	public boolean isSuccessNextStage()
	{
		return successNextStage;
	}

	public void setSuccessNextStage(boolean successNextStage)
	{
		this.successNextStage = successNextStage;
	}


	public String getState()
	{
		return state;
	}

	public void setState(String state)
	{
		this.state = state;
	}

	public String getStage()
	{
		return stage;
	}

	public void setStage(String stage)
	{
		this.stage = stage;
	}

	public String getFieldName()
	{
		return fieldName;
	}

	public void setFieldName(String fieldName)
	{
		this.fieldName = fieldName;
	}

	public String getValue()
	{
		return value;
	}

	public void setValue(String value)
	{
		this.value = value;
	}

	public String getErrorText()
	{
		return errorText;
	}

	public void setErrorText(String text)
	{
		this.errorText = text;
	}

	public String getMessageText()
	{
		return messageText;
	}

	public void setMessageText(String messageText)
	{
		this.messageText = messageText;
	}

	public boolean getCaptcha()
	{
		return captcha;
	}

	public void setCaptcha(boolean captcha)
	{
		this.captcha = captcha;
	}

	public String getPopupId()
	{
		return popupId;
	}

	public void setPopupId(String popupId)
	{
		this.popupId = popupId;
	}

	public String getDisabled()
	{
		return disabled;
	}

	public void setDisabled(String disabled)
	{
		this.disabled = disabled;
	}

	public String getMessageId()
	{
		return messageId;
	}

	public void setMessageId(String messageId)
	{
		this.messageId = messageId;
	}
}
