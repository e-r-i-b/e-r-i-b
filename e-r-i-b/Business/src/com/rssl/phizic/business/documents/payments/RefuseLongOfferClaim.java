package com.rssl.phizic.business.documents.payments;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.TypeOfPayment;
import com.rssl.phizic.auth.AuthModule;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.access.AccessException;
import com.rssl.phizic.business.documents.GateExecutableDocument;
import com.rssl.phizic.business.documents.ResourceType;
import com.rssl.phizic.common.types.MessageCollector;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.business.resources.external.ExternalResourceLink;
import com.rssl.phizic.business.resources.external.ExternalResourceService;
import com.rssl.phizic.business.resources.external.LongOfferLink;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.documents.AbstractTransfer;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.longoffer.LongOffer;
import com.rssl.phizic.gate.payments.*;
import com.rssl.phizic.gate.payments.systems.AccountPaymentSystemPayment;
import com.rssl.phizic.gate.payments.systems.CardPaymentSystemPayment;
import com.rssl.phizic.utils.MaskUtil;
import com.rssl.phizic.utils.StringHelper;
import org.w3c.dom.Document;

import java.lang.reflect.Method;
import java.math.BigDecimal;

/**
 * @author gladishev
 * @ created 12.10.2010
 * @ $Author$
 * @ $Revision$
 */
public class RefuseLongOfferClaim extends GateExecutableDocument implements RefuseLongOffer
{
	private static final String OFFER_EXTERNAL_ID_ATTRIBUTE = "offer-external-id";
	private static final String LONG_OFFER_ATTRIBUTE = "long-offer-id";
	private static final String LONG_OFFER_TYPE_ATTRIBUTE = "long-offer-type";
	private static final String RECEIVER_RESOURCE_ATTRIBUTE = "receiver-resource";
	private static final String RECEIVER_RESOURCE_NAME_ATTRIBUTE = "receiver-resource-name";
	private static final String RECEIVER_RESOURCE_TYPE_ATTRIBUTE = "receiver-resource-type";
	private static final String PAYER_RESOURCE_ATTRIBUTE = "payer-resource";
	private static final String PAYER_RESOURCE_TYPE_ATTRIBUTE = "payer-resource-type";
	private static final String PAYER_RESOURCE_NAME_ATTRIBUTE = "payer-resource-name";
	private static final String AMOUNT_ATTRIBUTE = "amount";
	private static final String CURRENCY_ATTRIBUTE = "amount-currency";
	private static final String START_DATE_ATTRIBUTE = "start-date";
	private static final String END_DATE_ATTRIBUTE = "end-date";
	private static final String EXECUTION_TYPE_ATTRIBUTE = "execution-event-type";
	private static final String OFFER_OFFICE_ATTRIBUTE = "long-offer-office";
	private static final String OFFER_NAME_ATTRIBUTE = "long-offer-name";

	private static final String RECEIVER_ACCOUNT_METHOD_NAME = "getReceiverAccount";
	private static final String RECEIVER_CARD_METHOD_NAME = "getReceiverCard";
	private static final String CHARGE_ACCOUNT_METHOD_NAME = "getChargeOffAccount";
	private static final String CHARGE_CARD_METHOD_NAME = "getChargeOffCard";
	private static final String LOAN_ACCOUNT_NUMBER_METHOD_NAME = "getAccountNumber";

	private static ExternalResourceService externalResourceService = new ExternalResourceService();
	private boolean longOffer;

	public Class<? extends GateDocument> getType()
	{
		return RefuseLongOffer.class;
	}

	protected void readFromDom(Document document, InnerUpdateState updateState, MessageCollector messageCollector) throws DocumentException, DocumentLogicException
	{
		super.readFromDom(document, updateState, messageCollector);
		storeOfferData();
		setLongOffer(true);
	}

	public TypeOfPayment getTypeOfPayment()
	{
		return TypeOfPayment.NOT_PAYMENT_OPEATION;
	}

	public String getName()
	{
		return getNullSaveAttributeStringValue(OFFER_NAME_ATTRIBUTE);
	}

	public void setName(String name)
	{
		setNullSaveAttributeStringValue(OFFER_NAME_ATTRIBUTE, name);
	}

	private void storeOfferData() throws DocumentException, DocumentLogicException
	{
		String offerId = getLongOfferId();
		try
		{
			LongOfferLink link = externalResourceService.findLinkById(LongOfferLink.class, new Long(offerId));

			if (link == null)
			{
				throw new DocumentException("Ќе найдено длительное поручение с id=" + offerId);
			}

			Long loginId = link.getLoginId();
			if(!loginId.equals(AuthModule.getAuthModule().getPrincipal().getLogin().getId()))
			{
				throw new AccessException("” данного пользовател€ нет прав на просмотр " +
									"длительного поручени€ с id=" + link.getId());
			}

			LongOffer offer = link.getValue();
			AbstractTransfer offerInfo = link.getLongOfferInfo();

			setNullSaveAttributeStringValue(OFFER_EXTERNAL_ID_ATTRIBUTE, link.getExternalId());
			setNullSaveAttributeStringValue(LONG_OFFER_TYPE_ATTRIBUTE, offer.getType().getSimpleName());
			addResourcesAttributes(offer.getType(), offerInfo);

			if (offer.getPercent() != null)
			{
				setAttributeValue("decimal", AMOUNT_ATTRIBUTE, offer.getPercent().setScale(2, BigDecimal.ROUND_HALF_UP));
				setNullSaveAttributeStringValue(CURRENCY_ATTRIBUTE, "");
			}
			else
			{
				setAttributeValue("decimal", AMOUNT_ATTRIBUTE, offer.getAmount().getDecimal().setScale(2, BigDecimal.ROUND_HALF_UP));
				setNullSaveAttributeStringValue(CURRENCY_ATTRIBUTE, offer.getAmount().getCurrency().getCode());
			}

			setNullSaveAttributeCalendarValue(START_DATE_ATTRIBUTE, offer.getStartDate());
			setNullSaveAttributeCalendarValue(END_DATE_ATTRIBUTE, offer.getEndDate());
			setNullSaveAttributeStringValue(EXECUTION_TYPE_ATTRIBUTE, link.getExecutionEventType());
			setNullSaveAttributeStringValue(OFFER_OFFICE_ATTRIBUTE, getOfferOffice(offer.getOffice()));
			setNullSaveAttributeStringValue(OFFER_NAME_ATTRIBUTE, link.getName());
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}		
		catch (BusinessLogicException le)
		{
			throw new DocumentException(le);
		}
		catch (NumberFormatException nfe)
		{
			throw new DocumentException("Ќе указан номер длительного поручени€");
		}
		catch (GateException e)
		{
			throw new DocumentException(e);
		}
	}

	private String getOfferOffice(Office office)
	{
		if (office == null)
			return null;
		StringBuilder officeName = new StringBuilder();
		if (office.getName() != null)
			officeName.append(office.getName()).append(" ");
		officeName.append("є ");
		officeName.append(office.getCode().getFields().get("branch"));
		officeName.append(office.getCode().getFields().get("office"));
		officeName.append("/");
		officeName.append(office.getCode().getFields().get("region"));

		return officeName.toString();
	}

	public String getLongOfferId()
	{
		return getNullSaveAttributeStringValue(LONG_OFFER_ATTRIBUTE);
	}

	public String getLongOfferExternalId()
	{
		return getNullSaveAttributeStringValue(OFFER_EXTERNAL_ID_ATTRIBUTE);
	}

	/**
	 * ƒобавл€ет атрибуты счет списани€, счет зачислени€,
	 * тип ресурса зачислени€ в документ
	 * @param offerType - тип длительного поручени€
	 * @param offerInfo - длительное поручение
	 * @throws DocumentException
	 * @throws DocumentLogicException
	 */
	private void addResourcesAttributes(Class<?extends GateDocument> offerType, AbstractTransfer offerInfo)
			throws DocumentException, DocumentLogicException
	{
		if (offerType == AccountPaymentSystemPayment.class)
		{
			addResources(getResource(offerInfo, CHARGE_ACCOUNT_METHOD_NAME), ResourceType.ACCOUNT,
						 getResource(offerInfo, RECEIVER_ACCOUNT_METHOD_NAME), ResourceType.ACCOUNT);
		}
		else if (offerType == CardPaymentSystemPayment.class)
		{
			addResources(getResource(offerInfo, CHARGE_CARD_METHOD_NAME), ResourceType.CARD,
					 getResource(offerInfo, RECEIVER_ACCOUNT_METHOD_NAME), ResourceType.ACCOUNT);
		}
		else if (offerType == ClientAccountsTransfer.class  || offerType == AccountJurIntraBankTransfer.class ||
				 offerType == AccountIntraBankPayment.class || offerType == AccountRUSPayment.class ||
				 offerType == AccountJurTransfer.class)
		{  //счет - счет
			addResources(getResource(offerInfo, CHARGE_ACCOUNT_METHOD_NAME), ResourceType.ACCOUNT,
						 getResource(offerInfo, RECEIVER_ACCOUNT_METHOD_NAME), ResourceType.ACCOUNT);
		}
		else if ( offerType == AccountToCardTransfer.class)
		{  //счет - карта
			addResources(getResource(offerInfo, CHARGE_ACCOUNT_METHOD_NAME), ResourceType.ACCOUNT,
						 getResource(offerInfo, RECEIVER_CARD_METHOD_NAME), ResourceType.CARD);
		}
		else if ( offerType == CardToAccountTransfer.class || offerType == CardJurIntraBankTransfer.class ||
				  offerType == CardJurTransfer.class || offerType == CardIntraBankPayment.class ||
				  offerType == CardRUSPayment.class)
		{ //карта - счет
			addResources(getResource(offerInfo, CHARGE_CARD_METHOD_NAME), ResourceType.CARD,
						 getResource(offerInfo, RECEIVER_ACCOUNT_METHOD_NAME), ResourceType.ACCOUNT);
		}
		else if ( offerType == InternalCardsTransfer.class)
		{ //карта - карта
			addResources(getResource(offerInfo, CHARGE_CARD_METHOD_NAME), ResourceType.CARD,
						 getResource(offerInfo, RECEIVER_CARD_METHOD_NAME), ResourceType.CARD);
		}
		else if (offerType.equals(LoanTransfer.class))
		{
			addResources(getResource(offerInfo, CHARGE_CARD_METHOD_NAME), ResourceType.CARD,
					     getResource(offerInfo, LOAN_ACCOUNT_NUMBER_METHOD_NAME), ResourceType.LOAN);
		}
		else
			throw new DocumentException("Ќеверный тип документа " + offerType);
	}

	private void addResources(String fromResource, ResourceType fromResourceType,
	                          String toResource,   ResourceType toResourceType) throws DocumentException
	{
		//формируем и добавл€ем счет списани€
		setNullSaveAttributeStringValue(PAYER_RESOURCE_NAME_ATTRIBUTE, getResourceName(fromResourceType, fromResource));
		if (fromResourceType == ResourceType.CARD)
			setNullSaveAttributeStringValue(PAYER_RESOURCE_ATTRIBUTE,(MaskUtil.getCutCardNumber(fromResource)));
		else
			setNullSaveAttributeStringValue(PAYER_RESOURCE_ATTRIBUTE, fromResource);


		//добавл€ем тип счета списани€ и зачислени€
		setNullSaveAttributeStringValue(PAYER_RESOURCE_TYPE_ATTRIBUTE, fromResourceType.toString());
		setNullSaveAttributeStringValue(RECEIVER_RESOURCE_TYPE_ATTRIBUTE, toResourceType.toString());

		//формируем и добавл€ем счет зачислени€
		if (toResourceType == ResourceType.CARD)
			setNullSaveAttributeStringValue(RECEIVER_RESOURCE_ATTRIBUTE, MaskUtil.getCutCardNumber(toResource));
		else if (toResourceType == ResourceType.LOAN)
		{
			setNullSaveAttributeStringValue(RECEIVER_RESOURCE_ATTRIBUTE, toResource);
			setNullSaveAttributeStringValue(RECEIVER_RESOURCE_NAME_ATTRIBUTE, getResourceName(toResourceType, toResource));
		}
		else
			setNullSaveAttributeStringValue(RECEIVER_RESOURCE_ATTRIBUTE, toResource);
	}

	private String getResourceName(ResourceType type, String number) throws DocumentException
	{
		try
		{
			Person person = PersonContext.getPersonDataProvider().getPersonData().getPerson();
			ExternalResourceLink link = externalResourceService.findLinkByNumber(person.getLogin(), type, number);

			return link == null ? "" : link.getName();
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}

	private String getResource(AbstractTransfer payment, String methodName) throws DocumentLogicException, DocumentException
	{
		try
		{
			Class transferClass = payment.getClass();
			Method method = transferClass.getMethod(methodName);
			return (String) method.invoke(payment);
		}
		catch (Exception e)
		{
			Throwable cause = e.getCause();
			if (cause instanceof GateLogicException)
			{
				throw new DocumentLogicException(cause.getMessage(), e);
			}
			throw new DocumentException(e);
		}
	}

	public String getPayerAccount()
	{
		return getNullSaveAttributeStringValue(PAYER_RESOURCE_ATTRIBUTE);
	}

	public String getPayerAccountName()
	{
		return getNullSaveAttributeStringValue(PAYER_RESOURCE_NAME_ATTRIBUTE);
	}

	public String getReceiverAccount()
	{
		return getNullSaveAttributeStringValue(RECEIVER_RESOURCE_ATTRIBUTE);
	}

	public Money getAmount()
	{
		if (StringHelper.isEmpty(getNullSaveAttributeStringValue(CURRENCY_ATTRIBUTE)))
			return null;

		return createMoney(getNullSaveAttributeStringValue(AMOUNT_ATTRIBUTE),
							getNullSaveAttributeStringValue(CURRENCY_ATTRIBUTE));
	}

	public BigDecimal getPercent()
	{
		if (!StringHelper.isEmpty(getNullSaveAttributeStringValue(CURRENCY_ATTRIBUTE)))
			return null;

		return (BigDecimal) getNullSaveAttributeValue(AMOUNT_ATTRIBUTE);
	}

	public boolean isLongOffer()
	{
		return longOffer;
	}

	public void setLongOffer(boolean longOffer)
	{
		this.longOffer = longOffer;
	}

	/**
	 * @return “ип источника списани€(счет/карта)
	 */
	public ResourceType getChargeOffResourceType()
	{
		String type = getNullSaveAttributeStringValue(PAYER_RESOURCE_TYPE_ATTRIBUTE);

		if (ResourceType.ACCOUNT.toString().equals(type))
		{
			return ResourceType.ACCOUNT;
		}
		if (ResourceType.CARD.toString().equals(type))
		{
			return ResourceType.CARD;
		}
		throw new IllegalStateException("Ќеизвестный тип источника списани€ " + type);
	}

	/**
	 * @return “ип источника зачислени€(счет/карта)
	 */
	public ResourceType getReceiverResourceType()
	{
		String type = getNullSaveAttributeStringValue(RECEIVER_RESOURCE_TYPE_ATTRIBUTE);

		if (ResourceType.ACCOUNT.toString().equals(type))
		{
			return ResourceType.ACCOUNT;
		}
		if (ResourceType.CARD.toString().equals(type))
		{
			return ResourceType.CARD;
		}
		if (ResourceType.LOAN.toString().equals(type))
		{
			return ResourceType.LOAN;
		}
		throw new IllegalStateException("Ќеизвестный тип источника зачислени€ " + type);
	}
}
