package com.rssl.phizic.operations.ext.sbrf.payment;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.doc.DocumentEvent;
import com.rssl.common.forms.state.ObjectEvent;
import com.rssl.phizic.auth.modes.UserVisitingMode;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ResourceNotFoundBusinessException;
import com.rssl.phizic.business.documents.DocumentHelper;
import com.rssl.phizic.business.documents.payments.ChangePaymentStatusType;
import com.rssl.phizic.business.documents.payments.source.ChangeStatusMoneyBoxSource;
import com.rssl.phizic.business.operations.Transactional;
import com.rssl.phizic.business.operations.restrictions.ChangeStatusMoneyBoxRestriction;
import com.rssl.phizic.business.payments.BusinessOfflineDocumentException;
import com.rssl.phizic.business.payments.BusinessTimeOutException;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.AutoSubscriptionLink;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.LinkHelper;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.common.types.ApplicationInfo;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.gate.bankroll.AccountState;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.longoffer.autosubscription.AutoPayStatusType;
import com.rssl.phizic.gate.payments.longoffer.CardToAccountLongOffer;
import com.rssl.phizic.operations.payment.EditDocumentOperationBase;
import com.rssl.phizic.utils.MaskUtil;

import static com.rssl.phizic.business.documents.strategies.limits.Constants.TIME_OUT_ERROR_MESSAGE;

/**
 * @author tisov
 * @ created 01.10.14
 * @ $Author$
 * @ $Revision$
 * Операция смены статуса копилки
 */

public class ChangeStatusMoneyBoxOperation extends EditDocumentOperationBase
{
	private AutoSubscriptionLink moneyBoxLink;
	private ChangePaymentStatusType changeStatusType;

	/**
	 * инициализируем операцию
	 * @param autoSubID - ид автоплатежа-копилки
	 * @param changePaymentStatusType - тип изменения статуса
	 */
	public void initialize(Long autoSubID, ChangePaymentStatusType changePaymentStatusType) throws BusinessException, BusinessLogicException
	{
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		moneyBoxLink = personData.getAutoSubscriptionLink(autoSubID);

		if (moneyBoxLink.getAutoSubscriptionInfo().getType() != CardToAccountLongOffer.class)
		{
			throw new BusinessException("автоплатёж с идентификатором " + autoSubID + " не является копилкой");
		}

		changeStatusType = changePaymentStatusType;

		checkingResources();

		AutoPayStatusType currentStatusType = moneyBoxLink.getValue().getAutoPayStatusType();
		if (!((ChangeStatusMoneyBoxRestriction) getRestriction()).accept(currentStatusType, changePaymentStatusType))
		{
			 throw new BusinessException("Вы не можете выполнить данную операцию для копилки в текущем статусе");
		}

		initialize(getSource(changePaymentStatusType));
	}

	private ChangeStatusMoneyBoxSource getSource(ChangePaymentStatusType changePaymentStatusType) throws BusinessException, BusinessLogicException
	{
		try
		{
			return new ChangeStatusMoneyBoxSource(moneyBoxLink, changePaymentStatusType);
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
		catch (DocumentException e)
		{
			throw new BusinessException(e);
		}
	}

	private void checkingResources() throws BusinessLogicException, BusinessException
	{
		AccountLink accountLink = moneyBoxLink.getAccountLink();
		if (accountLink == null)
		{
			throw new BusinessLogicException("Невозможно получить информацию по счёту зачисления.");
		}
		CardLink cardLink = moneyBoxLink.getCardLink();
		if (cardLink == null || cardLink.getCard() == null)
		{
			throw new BusinessLogicException("Невозможно получить информацию по карте списания." + MaskUtil.getCutCardNumber(moneyBoxLink.getAutoSubscriptionInfo().getCardNumber()));
		}
		if (accountLink.getAccount()== null || AccountState.CLOSED.equals(accountLink.getAccount().getAccountState()))
		{
			throw new ResourceNotFoundBusinessException("Вы не можете выполнить данную операцию. Для доступа измените настройки видимости Вашей карты и Вашего счета зачисления в пункте меню «Настройки» - «Безопасность и доступы» - «Настройка видимости продуктов»", AccountLink.class, "_For_MoneyBox");
		}
		if (!LinkHelper.isVisibleInChannel(accountLink) && !LinkHelper.isVisibleInChannel(cardLink))
			throw new BusinessLogicException("Вы не можете выполнить данную операцию. Для доступа измените настройки видимости Вашей карты и Вашего счета зачисления в пункте меню «Настройки» - «Безопасность и доступы» - «Настройка видимости продуктов»");
		if (!LinkHelper.isVisibleInChannel(accountLink))
			throw new BusinessLogicException("Вы не можете выполнить данную операцию. Для доступа измените настройки видимости Вашего счета зачисления в пункте меню «Настройки» - «Безопасность и доступы» - «Настройка видимости продуктов»");
		if (!LinkHelper.isVisibleInChannel(cardLink))
			throw new BusinessLogicException("Вы не можете выполнить данную операцию. Для доступа измените настройки видимости Вашей карты в пункте меню «Настройки» - «Безопасность и доступы» - «Настройка видимости продуктов»");

		if(changeStatusType == ChangePaymentStatusType.RECOVER)
		{
			if (cardLink.getCard().getCardAccountState() == AccountState.ARRESTED)
			{
				throw new BusinessException("Вы не можете возобновить копилку, так как в ней используется арестованная карта.");
			}
		}
	}

	/**
	 * Изменение статуса документа в машине состояний, сохранение документа в базе
	 * @return - код транзакции
	 */
	@Transactional
	public Long changeStatus() throws BusinessLogicException, BusinessException
	{
		confirm();
	    target.save(document);
		moneyBoxLink.getValue().setAutoPayStatusType(getAutoPayStatusType());
	    return document.getId();
	}

	private void confirm() throws BusinessException, BusinessLogicException
	{
		try
		{
			executor.fireEvent(new ObjectEvent(DocumentEvent.CONFIRM, ObjectEvent.CLIENT_EVENT_TYPE));
		}
		catch (BusinessTimeOutException e)
		{
			log.error(String.format(TIME_OUT_ERROR_MESSAGE, document.getId()), e);
			DocumentHelper.fireDounknowEvent(executor, ObjectEvent.CLIENT_EVENT_TYPE, e);
		}
		catch (BusinessOfflineDocumentException e)
		{
			if (DocumentHelper.isExternalPayment(document))
				throw new BusinessLogicException(e.getMessage(), e);

			DocumentHelper.repeatOfflineDocument(executor, new ObjectEvent(DocumentEvent.CONFIRM, ObjectEvent.CLIENT_EVENT_TYPE), document, e.getMessage());
		}
	}

	private AutoPayStatusType getAutoPayStatusType()
	{
		if (isUserVisitingMode(UserVisitingMode.EMPLOYEE_INPUT_BY_IDENTITY_DOCUMENT))
		{
			return AutoPayStatusType.WaitForAccept;
		}

		return changeStatusType.getAutoPayStatusType();
	}

	private boolean isUserVisitingMode(UserVisitingMode mode)
	{
		if (!ApplicationInfo.getCurrentApplication().equals(Application.PhizIA))
		{
			return false;
		}

		if (!PersonContext.isAvailable())
		{
			return false;
		}

		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		UserVisitingMode lastMode = personData.getPerson().getLogin().getLastUserVisitingMode();

		return lastMode == mode;
	}
}
