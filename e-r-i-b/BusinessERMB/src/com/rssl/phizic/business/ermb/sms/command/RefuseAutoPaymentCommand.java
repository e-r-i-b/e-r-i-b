package com.rssl.phizic.business.ermb.sms.command;

import com.rssl.phizic.bankroll.BankrollProductLink;
import com.rssl.phizic.bankroll.PersonBankrollManager;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.providers.BillingServiceProvider;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderService;
import com.rssl.phizic.business.ermb.card.PrimaryCardResolver;
import com.rssl.phizic.business.longoffer.autopayment.AutoPaymentHelper;
import com.rssl.phizic.business.longoffer.autopayment.links.AutoPaymentLinksService;
import com.rssl.phizic.business.resources.external.AutoPaymentLink;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.common.types.exceptions.InternalErrorException;
import com.rssl.phizic.common.types.exceptions.UserErrorException;
import com.rssl.phizic.payment.PersonPaymentManager;
import com.rssl.phizic.payment.PersonPaymentManagerImpl;
import com.rssl.phizic.payment.RefuseAutoPaymentTask;
import com.rssl.phizic.utils.PhoneNumber;
import com.rssl.phizic.utils.PhoneNumberFormat;

import java.util.Collection;
import java.util.List;

/**
 * СМС-Команда отключения автоплатежа
 * @author Rtischeva
 * @ created 24.06.14
 * @ $Author$
 * @ $Revision$
 */
public class RefuseAutoPaymentCommand extends CommandBase
{
	/**
	 * номер телефона
	 */
	private PhoneNumber phoneNumber;

	/**
	 * алиас карты списания
	 */
	private String cardAlias;

	private static final AutoPaymentLinksService autoPaymentLinksService = new AutoPaymentLinksService();

	private static final ServiceProviderService serviceProviderService = new ServiceProviderService();

	@Override
	protected void doExecute()
	{
		PersonBankrollManager bankrollManager = getPersonBankrollManager();

		Collection<CardLink> cardLinks = (Collection<CardLink>) bankrollManager.getCards();

        //ищем приоритетную карту или карту по присланному алиасу
		BankrollProductLink link = cardAlias == null ? getPriorityCardLink(cardLinks) : bankrollManager.findProductBySmsAlias(cardAlias, CardLink.class);
		if (link == null || !link.getShowInSms())
		{
			if (link != null)
				throw new UserErrorException(messageBuilder.buildRefuseAutoPayIncorrectCardMessage(link.getAutoSmsAlias().length()));

			throw new UserErrorException(messageBuilder.buildRefuseAutoPayIncorrectCardMessage(getCardSmsAutoAliasLength(cardLinks)));
		}

		CardLink cardLink = (CardLink) link;

		List<AutoPaymentLink> autoPaymentLinks = updateAutoPaymentLinks(cardLinks);
		//ищем автоплатеж по телефону и карте
		AutoPaymentLink autoPaymentLink = findAutoPayment(autoPaymentLinks, cardLink);

		if (autoPaymentLink == null || !AutoPaymentHelper.isRefuseSupported(autoPaymentLink.getValue()))
			throw new UserErrorException(messageBuilder.buildRefuseAutoPaymentNotExistsMessage(phoneNumber));

		PersonPaymentManager paymentManager = new PersonPaymentManagerImpl(getModule(), getPerson());
		RefuseAutoPaymentTask task = paymentManager.createRefuseAutoPaymentTask();
		task.setCardLink(cardLink);
		task.setAutoPaymentLinkId(String.valueOf(autoPaymentLink.getId()));
		task.setPhoneNumber(phoneNumber);
		task.execute();
	}

	private BankrollProductLink getPriorityCardLink(Collection<CardLink> cardLinks)
	{
		try
		{
			return PrimaryCardResolver.getPrimaryLink(cardLinks);
		}
		catch (BusinessException e)
		{
			throw new UserErrorException(messageBuilder.buildRefuseAutoPayIncorrectCardMessage(getCardSmsAutoAliasLength(cardLinks)), e);
		}
	}

	private BillingServiceProvider findProvider()
	{
		try
		{
			return serviceProviderService.getMobileOperatorByPhoneNumber(phoneNumber);
		}
		catch (BusinessException e)
		{
			throw new InternalErrorException(e);
		}
	}

	private List<AutoPaymentLink> updateAutoPaymentLinks(Collection<CardLink> cardLinks)
	{
		try
		{
			List<AutoPaymentLink> autoPaymentLinks = autoPaymentLinksService.findByUserId(getPerson().getLogin());
			autoPaymentLinksService.updateAutoPaymentLinks(getPerson().getLogin(), cardLinks, autoPaymentLinks);
			return autoPaymentLinks;
		}
		catch (BusinessLogicException e)
		{
			throw new UserErrorException(messageBuilder.buildRefuseAutoPaymentExtSystemErrorMessage(phoneNumber), e);
		}
		catch (BusinessException e)
		{
			throw new UserErrorException(messageBuilder.buildRefuseAutoPaymentExtSystemErrorMessage(phoneNumber), e);
		}
	}

	private AutoPaymentLink findAutoPayment(List<AutoPaymentLink> autoPaymentLinks, CardLink cardLink)
	{
		BillingServiceProvider serviceProvider = findProvider();

		if (serviceProvider == null)
			throw new UserErrorException(messageBuilder.buildNotFoundRefuseAutoPayProvider(phoneNumber));

		String phone = PhoneNumberFormat.SIMPLE_NUMBER.format(phoneNumber) ;
		String cardNumber = cardLink.getNumber();
		String providerCode = serviceProvider.getCode();

		for (AutoPaymentLink autoPaymentLink : autoPaymentLinks)
		{
			String requsite = autoPaymentLink.getRequisite();
            String receiverCode = autoPaymentLink.getReceiverCode();
            String cardNum = autoPaymentLink.getCardNumber();
			if(requsite.equals(phone) && receiverCode.equals(providerCode) && cardNum.equals(cardNumber))
				return autoPaymentLink;
		}
		return null;
	}

	public void setPhoneNumber(PhoneNumber phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}

	public PhoneNumber getPhoneNumber()
	{
		return phoneNumber;
	}

	public void setCardAlias(String cardAlias)
	{
		this.cardAlias = cardAlias;
	}

	public String getCommandName()
	{
		return "REFUSE_AUTO_PAYMENT";
	}
}
