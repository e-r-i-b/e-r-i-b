package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.country.CountryCode;
import com.rssl.phizic.business.dictionaries.country.CountryService;
import com.rssl.phizic.business.dictionaries.finances.CardOperationCategory;
import com.rssl.phizic.business.dictionaries.providers.*;
import com.rssl.phizic.business.documents.payments.BusinessDocumentOwner;
import com.rssl.phizic.business.documents.payments.JurPayment;
import com.rssl.phizic.business.finances.CardOperation;
import com.rssl.phizic.business.finances.CardOperationService;
import com.rssl.phizic.business.finances.OperationType;
import com.rssl.phizic.business.profile.ProfileUtils;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.finances.FinancesConfig;
import com.rssl.phizic.utils.StringHelper;

/**
 * @author koptyaev
 * @ created 01.04.14
 * @ $Author$
 * @ $Revision$
 */
public class ExecutedDocumentSendToIPSHandler extends BusinessDocumentHandlerBase
{
	private static final int DESCRIPTION_MAX_LENGTH = 300;
	private static ServiceProviderService serviceProviderService = new ServiceProviderService();
	private static CardOperationService cardOperationService = new CardOperationService();
	private static CountryService countryService = new CountryService();

	/**
	 * Обработать платеж
	 * @param document документ
	 * @param stateMachineEvent ивент
	 * @throws com.rssl.common.forms.DocumentLogicException неправильно заполнен документ, нужно исправить ошибки
	 */
	@SuppressWarnings("OverlyLongMethod")
	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (!ConfigFactory.getConfig(FinancesConfig.class).getOperationLinkingEnabled())
			return;
		if (!(document instanceof JurPayment))
			return;
		JurPayment payment = (JurPayment) document;
		ServiceProviderShort serviceProvider = null;
		try
		{
			serviceProvider = ServiceProviderHelper.getServiceProvider(payment.getReceiverInternalId());
			if (serviceProvider == null || !(serviceProvider.getKind().equals("B")))
				return;
			if (payment.isLongOffer())
				return;
			if (!(payment.getChargeOffResourceLink() instanceof CardLink))
				return;
			BusinessDocumentOwner documentOwner = payment.getOwner();
			if (documentOwner.isGuest())
				throw new UnsupportedOperationException("Операция в гостевой сессии не поддерживается");
			if (!ProfileUtils.getProfileByLogin(documentOwner.getLogin()).isShowPersonalFinance())
				return;
		}
		catch (BusinessException e)
		{
			throw new  DocumentException(e);
		}

		CardOperation cardOperation = new CardOperation();
		try
		{
			BusinessDocumentOwner documentOwner = payment.getOwner();
			if (documentOwner.isGuest())
				throw new UnsupportedOperationException("Операция в гостевой сессии не поддерживается");
			cardOperation.setOwnerId(documentOwner.getLogin().getId());
		}
		catch (BusinessException e)
		{
			throw new  DocumentException(e);
		}

		cardOperation.setCardNumber(payment.getCardNumber());
		cardOperation.setNationalAmount(payment.getDestinationAmount().getDecimal().add(payment.getCommission().getDecimal()).negate());
		cardOperation.setCash(false);
		String description = StringHelper.getEmptyIfNull(payment.getGround());
		if (StringHelper.isNotEmpty(description))
			description = description + " ";
		description = description + StringHelper.getEmptyIfNull(payment.getReceiverName());
		description = description.length()>DESCRIPTION_MAX_LENGTH?description.substring(0,DESCRIPTION_MAX_LENGTH):description;
		cardOperation.setDescription(description);
		cardOperation.setOriginalDescription(description);
		cardOperation.setOperationType(OperationType.BY_CARD);
		cardOperation.setBusinessDocumentId(payment.getId());
		cardOperation.setDate(payment.getExecutionDate());
		try
		{
			CountryCode country = countryService.getCountryByISO3("RUS");
			cardOperation.setOriginalCountry(country);
			cardOperation.setClientCountry(country);
			CardOperationCategory category =  serviceProviderService.getFirstCardOperationCategoryForServiceProvider(serviceProvider.getId());
			cardOperation.setCategory(category);
		}
		catch (Exception e)
		{
			log.error("Ошибка получения категории карточных операций", e);
		}
		try
		{
			cardOperationService.addOrUpdate(cardOperation);
		}
		catch (BusinessException e)
		{
			log.error("Ошибка сохранения карточной операции", e);
		}
	}
}
