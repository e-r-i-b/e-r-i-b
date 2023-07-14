package com.rssl.phizic.business.documents;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.MessageDocumentLogicException;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.BusinessDocumentOwner;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.ExternalResourceLink;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.MessageCollector;
import com.rssl.phizic.common.types.documents.FormType;
import com.rssl.phizic.common.types.exceptions.LogicException;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.BankrollService;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.GroupResultHelper;
import com.rssl.phizic.utils.MaskUtil;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.Calendar;
import java.util.Date;
import java.util.Set;

/**
 * @author Krenev
 * @ created 15.10.2008
 * @ $Author$
 * @ $Revision$
 */
public abstract class AbstractAccountsTransfer extends AbstractLongOfferDocument
{
	public static final String DESTINATION_RESOURCE_CURRENCY_ATTRIBUTE_NAME = "to-resource-currency";//������ ��������� ����������
	public static final String TO_ACCOUNT_NAME_ATTRIBUTE_NAME               = "to-account-name";
	public static final String TO_ACCOUNT_SELECT_ATTRIBUTE_NAME             = "to-account-select";
	public static final String TO_RESOURCE_LINK_CODE_ATTRIBUTE_NAME         = "to-resource-link";

	public static final String RECEIVER_BANK_IS_OUR = "is-our-bank";
	public static final String RECEIVER_ACCOUNT_ATTRIBUTE_NAME = "receiver-account";
	public static final String RECEIVER_SUR_NAME_ATTRIBUTE_NAME = "receiver-surname";
	public static final String RECEIVER_FIRST_NAME_ATTRIBUTE_NAME = "receiver-first-name";
	public static final String RECEIVER_PATR_NAME_ATTRIBUTE_NAME = "receiver-patr-name";
	public static final String RECEIVER_RESOURCE_TYPE_ATTRIBUTE_NAME = "to-resource-type";
	public static final String RECEIVER_RESOURCE_ATTRIBUTE_NAME = "to-resource";
	public static final String IS_RECEIVER_CARD_EXTENAL_ATTRIBUTE_NAME = "is-external-card";
	public static final String RECEIVER_CARD_EXPIRE_DATE_ATTRIBUTE_NAME = "to-card-expire-date";
	public static final String IS_CONVERSION_OPERATION_NAME = "is-conversion";

	private String destinationResource;
	private String receiverName;

	/**
	 * ���� ����������, ��������������� destinationResource
	 */
	private ExternalResourceLink destinationResourceLink = null;

	/**
	 * @return ���� ����������.
	 */
	public String getReceiverAccount()
	{
		return destinationResource;
	}

	/**
	 * ��������� ����� ����������
	 * @param receiverAccount ���� ����������.
	 */
	public void setReceiverAccount(String receiverAccount)
	{
		this.destinationResource = receiverAccount;
		this.destinationResourceLink = null;
	}

	/**
	 * @return ��� ��������� ��������(����/�����)
	 */
	public ResourceType getDestinationResourceType()
	{
		if (isReceiverCardExternal())
		{
			return ResourceType.EXTERNAL_CARD;
		}
		String type = getNullSaveAttributeStringValue(RECEIVER_RESOURCE_TYPE_ATTRIBUTE_NAME);
		if (StringHelper.isEmpty(type))
		{
			//��� ��������� ������ ��������
			//�������� �� ����� ����. �������, ��� ����.
			return ResourceType.ACCOUNT;
		}
		if (AccountLink.class.getName().equals(type))
		{
			return ResourceType.ACCOUNT;
		}
		if (CardLink.class.getName().equals(type))
		{
			return ResourceType.CARD;
		}
		throw new IllegalStateException("����������� ��� ��������� ���������� " + type);
	}

	/**
	 * ������� ������ �� ������� ����������
	 * ��������: ������ ������������ �� �������� ��������� �������,
	 * �.�. ����� ������������� ��� ������ ���������� (� ������ ������ ������������ null)
	 * @return ������ ��� null, ���� ����-��-������� ���������� ����� ���� ����� ��������� ���������� �� ������
	 */
	public ExternalResourceLink getDestinationResourceLink() throws DocumentException
	{
		if (destinationResourceLink != null)
			return destinationResourceLink;

		String destNumber = getReceiverAccount();
		if (StringHelper.isEmpty(destNumber))
			return null;

		ResourceType destResourceType = getDestinationResourceType();
		try
		{
			BusinessDocumentOwner documentOwner = getOwner();
			if (documentOwner.isGuest())
				throw new UnsupportedOperationException("�������� � �������� ������ �� ��������������");
			ExternalResourceLink link = externalResourceService.findLinkByNumber(documentOwner.getLogin(), destResourceType, destNumber);
			// ������ ����� ���� ������� � �� �����������
			if (link == null)
				return null;

			destinationResourceLink = link;
			return destinationResourceLink;
		}
		catch (BusinessException e)
		{
			throw new DocumentException("���� ��� ��������� �����-��-������� ����������", e);
		}
	}


	public void setReceiverCard(String receiverCard)
	{
		destinationResource = receiverCard;	
	}

	/**
	 * @return ����� ����������.
	 */
	public String getReceiverCard()
	{
		return destinationResource;
	}

	/**
	 * @return ���� ��������� ����� �������� ����� ��������
	 */
	public Calendar getReceiverCardExpireDate()
	{
		return DateHelper.toCalendar((Date) getNullSaveAttributeValue(RECEIVER_CARD_EXPIRE_DATE_ATTRIBUTE_NAME));
	}

	/**
	 * ���������� ���� ��������� ����� �������� ����� ��������
	 * @param date ����
	 */
	public void setReceiverCardExpireDate(Calendar date)
	{
		setNullSaveAttributeCalendarValue(RECEIVER_CARD_EXPIRE_DATE_ATTRIBUTE_NAME, date);
	}

	public void setReceiverCardExternal(boolean isReceiverCardExternal)
	{
		setNullSaveAttributeBooleanValue(IS_RECEIVER_CARD_EXTENAL_ATTRIBUTE_NAME, isReceiverCardExternal);
	}

	/**
	 * @return �������� �� �������� ���������� ����� ������.
	 */
	public boolean isReceiverCardExternal()
	{
		Boolean value = (Boolean) getNullSaveAttributeValue(IS_RECEIVER_CARD_EXTENAL_ATTRIBUTE_NAME);
		if (value == null)
		{
			//��� ��������� ������ ��������
			//������� �� �����. �������, ��� ������� �� ����.
			return false;
		}
		return value;
	}

	public AbstractAccountsTransfer createCopy(Class<? extends BusinessDocument> instanceClass) throws DocumentException, DocumentLogicException
	{
		AbstractAccountsTransfer document = (AbstractAccountsTransfer) super.createCopy(instanceClass);
		document.setReceiverAccount(getReceiverAccount());
		document.setReceiverName(getReceiverName());
		document.setReceiverFirstName(getReceiverFirstName());
		document.setReceiverSurName(getReceiverSurName());
		document.setReceiverPatrName(getReceiverPatrName());
		return document;
	}

	public String getAuthorizeCode()
	{
		return getNullSaveAttributeStringValue(AUTHORIZE_CODE_ATTRIBUTE_NAME);
	}

	public void setAuthorizeCode(String authorizeCode)
	{
		setNullSaveAttributeStringValue(AUTHORIZE_CODE_ATTRIBUTE_NAME, authorizeCode);
	}

	public Calendar getAuthorizeDate()
	{
		return getNullSaveAttributeDateTimeValue(AUTHORIZE_DATE_ATTRIBUTE_NAME);
	}

	public void setAuthorizeDate(Calendar authorizeDate)
	{
		setNullSaveAttributeDateTimeValue(AUTHORIZE_DATE_ATTRIBUTE_NAME, authorizeDate);
	}

	/**
	 * @return ������ ��������� ����������
	 */
	public Currency getDestinationCurrency() throws GateException
	{
		String currencyCode = getNullSaveAttributeStringValue(DESTINATION_RESOURCE_CURRENCY_ATTRIBUTE_NAME);
		if (currencyCode == null)
		{
			return null;
		}
		return findCurrencyByISOCode(currencyCode);
	}

	/**
	 * ���������� ������ ��������� ����������
	 * @param currency ������
	 */
	protected void setDestinationCurrency(Currency currency)
	{
		setNullSaveAttributeStringValue(DESTINATION_RESOURCE_CURRENCY_ATTRIBUTE_NAME, currency.getCode());
	}

	public String getToAccountName()
	{
		return getNullSaveAttributeStringValue(TO_ACCOUNT_NAME_ATTRIBUTE_NAME);
	}

	public Document convertToDom() throws DocumentException
	{
		Document document = super.convertToDom();
		Element root = document.getDocumentElement();
		appendNullSaveString(root, RECEIVER_ACCOUNT_ATTRIBUTE_NAME, getReceiverAccount());
		appendNullSaveString(root, RECEIVER_NAME_ATTRIBUTE_NAME, getReceiverName());
		return document;
	}

	protected void readFromDom(Document document, InnerUpdateState updateState, MessageCollector messageCollector) throws DocumentException, DocumentLogicException
	{
		super.readFromDom(document, updateState, messageCollector);
		Element root = document.getDocumentElement();

		if(XmlHelper.tagTest(RECEIVER_ACCOUNT_ATTRIBUTE_NAME, root))
			setReceiverAccount(XmlHelper.getSimpleElementValue(root, RECEIVER_ACCOUNT_ATTRIBUTE_NAME));

		ResourceType destinationResourceType = getDestinationResourceType();
		ResourceType chargeOffResourceType = getChargeOffResourceType();

		storeCardsInfo(chargeOffResourceType, destinationResourceType, updateState, messageCollector);
		storeDestinationCurrencyInfo(destinationResourceType, updateState, messageCollector);
	}

	/**
	 * ��������� ���������� � ������ ��������� ����������.
	 * @param destinationResourceType ��� ��������� ����������
	 */
	protected void storeDestinationCurrencyInfo(ResourceType destinationResourceType, InnerUpdateState updateState, MessageCollector messageCollector) throws DocumentLogicException, DocumentException
	{
		try
		{
			if (StringHelper.isEmpty(destinationResource))
			{
				return;
			}
			Currency currency = null;
			switch (destinationResourceType)
			{
				case CARD:
				case ACCOUNT:
				case IM_ACCOUNT:
					ExternalResourceLink link = getDestinationResourceLink();
					if (link == null)
						throw new DocumentException("�� ������ ����-��-������� ���������� ���� " + getDestinationResourceType());
					currency = link.getCurrency();
					break;
				case EXTERNAL_CARD:
				{
					if(isOurBank())
					{
						Card card = receiveDestinationCard(messageCollector);
						currency = card.getCurrency();
					}
					// ��� �������� �� ����� � ������ ���� ������ ������ �����
					else
					{
						currency = getNationalCurrency();
					}
					break;
				}
				case EXTERNAL_ACCOUNT:
				{
					//������ ����� ����������  �� ������ ����� -  ��� 6-8 ������� � ��������� �����.
					String currencyCode = getReceiverAccount().substring(5, 8);
					currency = findCurrencyByNumericCode(currencyCode);
					break;
				}
			}
			if (currency == null)
			{
				throw new DocumentLogicException("���������� ���������� ������ �����/����� ����������");
			}
			setDestinationCurrency(currency);
		}
		catch (DocumentLogicException e)
		{
			// ��� ������������� ���� ������
			if(updateState != InnerUpdateState.INIT)
				throw e;

			log.warn(e);
		}
		catch (DocumentException e)
		{
			// ��� ������������� ���� ������
			if(updateState != InnerUpdateState.INIT)
				throw e;

			log.warn(e);
		}
	}

	/**
	 * ��������� ��� ���������� � ������(��� �������������)
	 * @param chargeOffResourceType ��� ��������� ��������
	 * @param destinationResourceType ��� ��������� ����������
	 * @throws DocumentLogicException
	 * @throws DocumentException
	 */
	protected void storeCardsInfo(ResourceType chargeOffResourceType, ResourceType destinationResourceType, InnerUpdateState updateState, MessageCollector messageCollector) throws DocumentLogicException, DocumentException
	{
		if (chargeOffResourceType == ResourceType.CARD)
		{
			try
			{
				//��������� ���� � ����� �������� ����� ��������
				Card chargeOffCard = receiveChargeOffCard(messageCollector);
				setChargeOffCardExpireDate(chargeOffCard.getExpireDate());
			}
			catch (DocumentLogicException e)
			{
				// ��� ������������� ���� ������
				if(updateState != InnerUpdateState.INIT)
					throw e;

				log.warn(e);
			}
		}
		if  (destinationResourceType == ResourceType.CARD || destinationResourceType == ResourceType.EXTERNAL_CARD)
		{
			try
			{
				//��������� ���� � ����� �������� ����� ����������
				Card destinationCard = receiveDestinationCard(messageCollector);
				setReceiverCardExpireDate(destinationCard == null ? null: destinationCard.getExpireDate());
			}
			catch (DocumentLogicException e)
			{
				// ��� ������������� ���� ������
				if(updateState != InnerUpdateState.INIT)
					throw e;

				log.warn(e);
			}
		}
	}

	/**
	 * �������� ���� �������� �����
	 * @param destinationResourceType ��� ��������� ����������
	 * @return ���� ��������
	 * @throws DocumentException
	 * @throws DocumentLogicException
	 */
	protected Calendar getDestinationCardExpireDate(ResourceType destinationResourceType, MessageCollector messageCollector) throws DocumentException, DocumentLogicException
	{
		if (destinationResourceType == ResourceType.CARD)
		{
			return receiveCardLinkByCardNumber(getReceiverCard(), messageCollector).getExpireDate();
		}
		if (destinationResourceType == ResourceType.EXTERNAL_CARD)
		{
			// ���� �� ��������������� �������, �� ����� ���
			if(!isOurBank())
				return null;

			Card card = receiveExternalCard(getReceiverCard());
			return card != null? card.getExpireDate() : null;
		}
		throw new IllegalStateException("�������� ��� ��������� ���������� " + destinationResourceType);
	}

	/**
	 * �������� ����� ����������
	 * @throws DocumentException
	 * @throws DocumentLogicException
	 * @return ����� ����������
	 */
	protected Card receiveDestinationCard(MessageCollector messageCollector) throws DocumentException, DocumentLogicException
	{
		String receiverCard = getReceiverCard();
		ResourceType destinationResourceType = getDestinationResourceType();
		if (destinationResourceType == ResourceType.CARD)
		{
			return receiveCardLinkByCardNumber(receiverCard, messageCollector).getCard();
		}
		if (destinationResourceType == ResourceType.EXTERNAL_CARD)
		{
			return isOurBank() ? receiveExternalCard(receiverCard) : null;
		}
		throw new IllegalStateException("�������� ��� ��������� ���������� " + destinationResourceType);
	}

	/**
	 * �������� ��� ����� ����������
	 * @throws DocumentException
	 * @throws DocumentLogicException
	 * @return ��� ����� ����������
	 */
	protected Account receiveDestinationCardAccount(MessageCollector messageCollector) throws DocumentLogicException, DocumentException
	{
		String receiverCard = getReceiverCard();
		ResourceType destinationResourceType = getDestinationResourceType();
		if (destinationResourceType == ResourceType.CARD)
		{
			Account cardAccount = receiveCardLinkByCardNumber(receiverCard, messageCollector).getCardAccount();
			if (cardAccount != null)
				return cardAccount;

			throw new MessageDocumentLogicException("��� ������ ����� �������� ���������� ����������.");
		}
		if (destinationResourceType == ResourceType.EXTERNAL_CARD)
		{
			return isOurBank() ? receiveExternalCardAccount(receiverCard) : null;
		}
		throw new IllegalStateException("�������� ��� ��������� ���������� " + destinationResourceType);
	}

	/**
	 * �������� ����� ����� ��������
	 * @param cardNumber ����� �����
	 * @return �����
	 */
	protected Card getCardIfOurBank(String cardNumber) throws DocumentLogicException, DocumentException
	{
		if (StringHelper.isEmpty(cardNumber))
		{
			return null;
		}

		BankrollService bankrollService = GateSingleton.getFactory().service(BankrollService.class);
		ActivePerson activePerson = null;
		try
		{
			BusinessDocumentOwner documentOwner = getOwner();
			if (documentOwner.isGuest())
				throw new UnsupportedOperationException("�������� � �������� ������ �� ��������������");
			activePerson = documentOwner.getPerson();
		}
		catch (BusinessException e)
		{
			throw new DocumentLogicException("���������� �������� ���������� � ��������� ���������");
		}
		if (activePerson == null)
		{
			throw new DocumentLogicException("���������� �������� ���������� � ��������� ���������");
		}
		try
		{
			return GroupResultHelper.getOneResult(bankrollService.getCardByNumber(activePerson.asClient(), new Pair<String, Office>(cardNumber, null)));
		}
		catch (LogicException e)
		{
			throw new DocumentLogicException(e);
		}
		catch (SystemException e)
		{
			throw new DocumentException(e);
		}
	}

	/**
	 * �������� ����� ����� ��������
	 * @param cardNumber ����� �����
	 * @return �����
	 */
	public Card receiveExternalCard(String cardNumber) throws DocumentLogicException, DocumentException
	{
		Card card = getCardIfOurBank(cardNumber);
		if (card == null)
		{
			throw new DocumentLogicException("�� ������� ���������� �� ����� " + MaskUtil.getCutCardNumber(cardNumber));
		}
		return card;
	}

	//�������� �� ���� "�����", �.�. ��� ���������������� �������
	public boolean isOurBank()
	{
		String isOurBank = getNullSaveAttributeStringValue(RECEIVER_BANK_IS_OUR);
		return Boolean.parseBoolean(isOurBank);
	}

	public void setOurBank(boolean isOur)
	{
		setNullSaveAttributeBooleanValue(RECEIVER_BANK_IS_OUR, isOur);
	}

	/**
	 * �������� ��� ����� ����� ��������
	 * @param cardNumber ����� �����
	 * @return ��� �����
	 */
	protected Account receiveExternalCardAccount(String cardNumber) throws DocumentLogicException, DocumentException
	{
		try
		{
			Card card = receiveExternalCard(cardNumber);
			BankrollService bankrollService = GateSingleton.getFactory().service(BankrollService.class);
			Account account = GroupResultHelper.getOneResult(bankrollService.getCardPrimaryAccount(card));
			if (account == null)
				throw new DocumentLogicException("�� ������ ��������� ���� ��� ����� �" + MaskUtil.getCutCardNumber(cardNumber));
			return account;
		}
		catch (LogicException e)
		{
			throw new DocumentLogicException(e);
		}
		catch (SystemException e)
		{
			throw new DocumentException(e);
		}
	}

	/**
	 * �������� ��������� �� �������� �����. ���� � ����������� ��������� �� ��������� �������� ����,
	 *  � � �������� ���������, ��������� ����������.
	 * @param template ������, �� ������ �������� ��� ������ ��������
	 * @return true - �������� ���� � ���������� ����������, false - �������� ���� ������
	 */
	public boolean equalsKeyEssentials(TemplateDocument template) throws BusinessException
	{
		if (!super.equalsKeyEssentials(template))
		{
			return false;
		}

		FormType formType = template.getFormType();
		if (FormType.INDIVIDUAL_TRANSFER == formType || FormType.INDIVIDUAL_TRANSFER_NEW == formType)
		{
			boolean temp = StringHelper.equalsNullIgnore(getReceiverSurName(), template.getReceiverSurName())
						&& StringHelper.equalsNullIgnore(getReceiverFirstName(), template.getReceiverFirstName());
			if (!temp)
			{
				return false;
			}
		}

		if (FormType.JURIDICAL_TRANSFER == formType)
		{
			boolean temp = StringHelper.equalsNullIgnore(getReceiverName(), template.getReceiverName());
			if (!temp)
			{
				return false;
			}
		}

		return StringHelper.equalsNullIgnore(getReceiverAccount(), template.getReceiverAccount())
			&& StringHelper.equalsNullIgnore(getReceiverCard(), template.getReceiverCard());
	}

	public String getReceiverSurName()
	{
		return getNullSaveAttributeStringValue(RECEIVER_SUR_NAME_ATTRIBUTE_NAME);
	}

	public void setReceiverSurName(String value)
	{
		setAttributeValue("string", RECEIVER_SUR_NAME_ATTRIBUTE_NAME, value);
	}

	public String getReceiverFirstName()
	{
		return getNullSaveAttributeStringValue(RECEIVER_FIRST_NAME_ATTRIBUTE_NAME);
	}

	public void setReceiverFirstName(String value)
	{
		setAttributeValue("string", RECEIVER_FIRST_NAME_ATTRIBUTE_NAME, value);
	}

	public String getReceiverPatrName()
	{
		return getNullSaveAttributeStringValue(RECEIVER_PATR_NAME_ATTRIBUTE_NAME);
	}

	public void setReceiverPatrName(String value)
	{
		setAttributeValue("string", RECEIVER_PATR_NAME_ATTRIBUTE_NAME, value);
	}

	/**
	 * @return ������������� �������� ��� ���.
	 */
	public boolean getIsConversionOperation()
	{
		return Boolean.parseBoolean(getNullSaveAttributeStringValue(IS_CONVERSION_OPERATION_NAME));
	}

	/**
	 * @param conversion ������� ������������� ��������
	 */
	public void setIsConversionOperation(boolean conversion)
	{
		setNullSaveAttributeBooleanValue(IS_CONVERSION_OPERATION_NAME, conversion);
	}

	public Set<ExternalResourceLink> getLinks() throws DocumentException
	{
		Set<ExternalResourceLink> links = super.getLinks();
		ExternalResourceLink link = getDestinationResourceLink();
		if (link != null)
		{
			links.add(link);
		}
		return links;
	}
}
