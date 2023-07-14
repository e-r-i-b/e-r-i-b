package com.rssl.phizic.business.payments;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.TemporalBusinessException;
import com.rssl.phizic.business.documents.*;
import com.rssl.phizic.business.documents.payments.BusinessDocumentOwner;
import com.rssl.phizic.business.resources.external.*;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.common.types.ApplicationInfo;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.util.ApplicationUtil;
import com.rssl.phizic.utils.MaskUtil;
import com.rssl.phizic.utils.StringHelper;

/**
 * @author Omeliyanchuk
 * @ created 02.05.2007
 * @ $Author$
 * @ $Revision$
 */

/**
 * проверка счета на заблокированность при отправке документа
 * checkType:
 * reciever - счет получателя
 * payer - счет плательщтка
 * both - оба
 * если параметр пуст, то оба
 * AccountBlockedException - счет заблокирован
 */
public class ValidatePaymentAccountHandler extends BusinessDocumentHandlerBase
{
	private static final String CHECK_TYPE            = "checkType";
	private static final String ALLOW_ARRESTED_TYPE   = "allowArrestedType";
	private static final String ALLOW_ARRESTED_FOR_PAYER_AND_RECIEVER = "allowArrestedForPayerAndReciever";
	private static final String ALLOW_ARRESTED_FOR_RECIEVER = "allowArrestedForReciever";
	private static final String BOTH                  = "both";
	private static final String PAYER                 = "payer";
	private static final String RECIEVER              = "reciever";

	private static final String PARAMETER_ERROR_MESSAGE = "errorMessage";
	private static final String DEFAULT_ERROR_MESSAGE   = "Невозможно подтвердить платеж.";

	private static final ExternalResourceService externalResourceService = new ExternalResourceService();

	private static final ActiveOrArrestedAccountFilter activeOrArrestedAccountFilter = new ActiveOrArrestedAccountFilter();
	private static final NotArrestedAccountFilter notArrestedAccountFilter = new NotArrestedAccountFilter();
	private static final ActiveCardWithArrestedAccountFilter activeOrArrestedCardFilter = new ActiveCardWithArrestedAccountFilter();
	protected static final NotBlockedCardFilter notBlockedCardFilter = new NotBlockedCardFilter();
	private static final NotArrestedCardFilter notArrestedCardFilter = new NotArrestedCardFilter();
	private static final ActiveOrArrestedIMAccountFilter activeOrArrestedIMAccountFilter = new ActiveOrArrestedIMAccountFilter();
	private static final NotArrestedIMAccountFilter notArrestedIMAccountFilter = new NotArrestedIMAccountFilter();

	private static final String YOUR_CARD = "Вашей карты ";
	private static final String YOUR_ACCOUNT = "Вашего счета ";

	///////////////////////////////////////////////////////////////////////////

	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (ApplicationUtil.isErmbSms())
			return;

		if (!(document instanceof AbstractPaymentDocument)){
			throw new DocumentException("Action поддерживает работу только с платежами");
		}

		if (document instanceof AccountOpeningClaim)
		{
			// Для заявки на открытие вклада без первоначального взноса ничего проверять не нужно
			AccountOpeningClaim accountOpeningClaim = (AccountOpeningClaim) document;
			if (!accountOpeningClaim.isNeedInitialFee())
				return;
		}

		AbstractPaymentDocument abstractPaymentDocument = (AbstractPaymentDocument) document;

		String type = getParameter( CHECK_TYPE);
		if(type == null)
			type = BOTH;

		String allowArrestedType = getParameter( ALLOW_ARRESTED_TYPE);
		if (allowArrestedType == null)
			allowArrestedType = ALLOW_ARRESTED_FOR_RECIEVER;

		boolean both = type.equals( BOTH );

		if( both || type.equals( PAYER ) )
		{

			ResourceType chargeResurceType = abstractPaymentDocument.getChargeOffResourceType();
			String payerAccount = abstractPaymentDocument.getChargeOffAccount(); ;

			if( payerAccount == null )
				throw new DocumentException("Не найден счет получателя для платежа с id:" + abstractPaymentDocument.getId());

			try
			{
				BusinessDocumentOwner documentOwner = abstractPaymentDocument.getOwner();
				if (documentOwner.isGuest())
					throw new UnsupportedOperationException("Операция в гостевой сессии не поддерживается");
				checkAccount(document, documentOwner.getLogin(), payerAccount, chargeResurceType, allowArrestedType.equals(ALLOW_ARRESTED_FOR_PAYER_AND_RECIEVER));
			}
			catch (BusinessException e)
			{
				throw new DocumentException(e);
			}
		}
		if(both || type.equals( RECIEVER ))
		{
			AbstractAccountsTransfer accountsTransfer = (AbstractAccountsTransfer) abstractPaymentDocument;
			String recieverAccount = accountsTransfer.getReceiverAccount();

			if(StringHelper.isEmpty(recieverAccount))
			{
				if (document instanceof AccountClosingPayment || document instanceof IMAOpeningClaim )
				{
					return;
				}

				throw new DocumentException("Не найден счет получателя для платежа с id:" + accountsTransfer.getId());
			}

			ResourceType destinationResourceType  = accountsTransfer.getDestinationResourceType();
			try
			{
				BusinessDocumentOwner documentOwner = abstractPaymentDocument.getOwner();
				if (documentOwner.isGuest())
					throw new UnsupportedOperationException("Операция в гостевой сессии не поддерживается");
				checkAccount(document, documentOwner.getLogin(), recieverAccount,destinationResourceType, allowArrestedType.equals(ALLOW_ARRESTED_FOR_RECIEVER) || allowArrestedType.equals(ALLOW_ARRESTED_FOR_PAYER_AND_RECIEVER));
			}
			catch (BusinessException e)
			{
				throw new DocumentException(e);
			}
		}
	}

	/*
	*   @accountOrCard - номер карты/счета
	*   @resourceType - тип источника
	*/
	private void checkAccount(StateObject document, Login ownerLogin, String accountOrCard, ResourceType resourceType, boolean allowArrested) throws DocumentLogicException, TemporalDocumentException
	{
		try
		{
			ApplicationConfig applicationConfig = ApplicationConfig.getIt();
			ExternalResourceLink link;

			ApplicationInfo applicationInfo = applicationConfig.getApplicationInfo();

			if (applicationInfo.isMobileApi())
				link = externalResourceService.findInMobileLinkByNumber(ownerLogin, resourceType, accountOrCard);
			else if (applicationInfo.isATM())
				link = externalResourceService.findInATMLinkByNumber(ownerLogin, resourceType, accountOrCard);
			else if (applicationConfig.getLoginContextApplication() == Application.PhizIA)
				//для приложения сотрудника, мы не должны скрывать карты и счета, невидимые в системе.
				link = externalResourceService.findLinkByNumber(ownerLogin, resourceType, accountOrCard);
			else
				link = externalResourceService.findInSystemLinkByNumber(ownerLogin, resourceType, accountOrCard);

			if (link == null)
				throw makeValidationFail(getDisabledErrorMessage(resourceType));

			String errorMessage = getErrorMessage(document, link, accountOrCard, resourceType, allowArrested);
			if (errorMessage != null)
				throw makeValidationFail(errorMessage);
		}
		catch (BusinessException e)
		{
			throw new DocumentLogicException(e);
		}
	}

	protected String getDisabledErrorMessage(ResourceType resourceType)
	{
		String message =  String.format("Вы не можете подтвердить данную операцию. Для доступа измените настройки видимости %s в пункте меню " +
			"«Настройки» – «Настройка безопасности» – «Настройка видимости продуктов».", resourceType == ResourceType.CARD? YOUR_CARD: YOUR_ACCOUNT);
		return message;
	}

	protected String getClosedErrorMessage(String accountOrCard)
	{
		return "Операции по выбранному счету или карте приостановлены. Пожалуйста, укажите другой счет или карту списания.";
	}

	protected String getErrorMessage(StateObject document, ExternalResourceLink link, String accountOrCard, ResourceType resourceType, boolean allowArrested) throws DocumentLogicException, TemporalBusinessException
	{
		String errorMessagePrefix = getErrorMessagePrefix();
		switch (resourceType)
		{
			case ACCOUNT:
				Account account = ((AccountLink) link).getAccount();

				if (!allowArrested && !notArrestedAccountFilter.accept(account))
					return "Невозможно подтвердить платеж. Счет " + accountOrCard + " арестован.";

				if (!activeOrArrestedAccountFilter.accept(account))
					return getClosedErrorMessage(accountOrCard);
				break;

			case CARD:
				Card card = ((CardLink) link).getCard();
				if (!notBlockedCardFilter.accept(card))
					return errorMessagePrefix + " Карта " + MaskUtil.getCutCardNumber(accountOrCard) + " заблокирована.";

				if (!allowArrested && !notArrestedCardFilter.accept(card))
					return errorMessagePrefix + " Карта " + MaskUtil.getCutCardNumber(accountOrCard) + " арестована.";

				if (!activeOrArrestedCardFilter.accept(card))
					return errorMessagePrefix + " Карта " + MaskUtil.getCutCardNumber(accountOrCard) + " закрыта.";
				break;

			case IM_ACCOUNT:
				if (!allowArrested && !notArrestedIMAccountFilter.accept((IMAccountLink) link))
					return errorMessagePrefix + " Cчёт ОМС " + MaskUtil.getCutCardNumber(accountOrCard) + " арестован.";

				if (!activeOrArrestedIMAccountFilter.accept((IMAccountLink) link))
					return errorMessagePrefix + " Cчёт ОМС " + MaskUtil.getCutCardNumber(accountOrCard) + " закрыт.";
				break;
		}
		return null;
	}

	private String getErrorMessagePrefix()
	{
		String errorMessageParameter = getParameter(PARAMETER_ERROR_MESSAGE);
		if (StringHelper.isEmpty(errorMessageParameter))
		{
			return DEFAULT_ERROR_MESSAGE;
		}
		return errorMessageParameter;
	}
}
