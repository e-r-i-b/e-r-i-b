package com.rssl.phizic.web.client.loanclaim;

import com.rssl.phizic.web.common.FilterActionForm;

/**
 * Форма асинхронного подтверждения необходимости перезвонить клиенту для продлжения заполнения заявки на кредит
 *
 * @ author: Gololobov
 * @ created: 06.02.15
 * @ $Author$
 * @ $Revision$
 */
public class AsyncConfirmPhoneCallForLoanClaimForm extends FilterActionForm
{
	private static final String ACCEPT_TO_CREDIT_HISTORY_FIELD  = "acceptToCreditHistory";
	//ИД заявки на кредит
	private Long loanClaimId;
	//Сообщение об ошибке
	private String errorMessage;
	//Информационное сообщение
	private String informMessage;
	//Есть активная неподтвержденная смс-кой операция
	private boolean isSmsPasswordExist;
	//Необходим новый пароль
	private boolean isNeedNewPassword;
	//Заголовог сообщения об успехе
	private String successInfoTitle;
	//Содержание сообщения об успехе
	private String successInfoText;

	public Long getLoanClaimId()
	{
		return loanClaimId;
	}

	public void setLoanClaimId(Long loanClaimId)
	{
		this.loanClaimId = loanClaimId;
	}

	public String getErrorMessage()
	{
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage)
	{
		this.errorMessage = errorMessage;
	}

	public String getInformMessage()
	{
		return informMessage;
	}

	public void setInformMessage(String informMessage)
	{
		this.informMessage = informMessage;
	}

	public boolean getIsSmsPasswordExist()
	{
		return isSmsPasswordExist;
	}

	public void setSmsPasswordExist(boolean smsPasswordExist)
	{
		isSmsPasswordExist = smsPasswordExist;
	}

	public boolean getIsNeedNewPassword()
	{
		return isNeedNewPassword;
	}

	public void setNeedNewPassword(boolean needNewPassword)
	{
		isNeedNewPassword = needNewPassword;
	}

	public String getSuccessInfoTitle()
	{
		return successInfoTitle;
	}

	public void setSuccessInfoTitle(String successInfoTitle)
	{
		this.successInfoTitle = successInfoTitle;
	}

	public String getSuccessInfoText()
	{
		return successInfoText;
	}

	public void setSuccessInfoText(String successInfoText)
	{
		this.successInfoText = successInfoText;
	}

	//Есть ли разрешение от клиента для получения информации из Бюро кредитных историй
	public boolean haveAcceptToCreditHistory()
	{
		return Boolean.valueOf((String) getField(ACCEPT_TO_CREDIT_HISTORY_FIELD));
	}
}
