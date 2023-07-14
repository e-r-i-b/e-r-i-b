package com.rssl.phizic.business.documents;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.dictionaries.offices.extended.ExtendedCodeImpl;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.BusinessDocumentOwner;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.documents.templates.service.TemplateDocumentService;
import com.rssl.phizic.business.resources.external.*;
import com.rssl.phizic.common.types.*;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.dictionaries.officies.Code;
import com.rssl.phizic.gate.documents.InputSumType;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.util.ApplicationUtil;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.MaskUtil;
import com.rssl.phizic.utils.MockHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

/**
 * @author Krenev
 * @ created 15.10.2008
 * @ $Author$
 * @ $Revision$
 */
public abstract class AbstractPaymentDocument extends GateExecutableDocument
{
	private static final String NATIONAL_CURRENCY_ATTRIBUTE_NAME = "national-currency";//������������ ������

	public static final String CHARGE_OFF_RESOURCE_ATTRIBUTE_NAME = "payer-account";
	public static final String CHARGE_OFF_RESOURCE_TYPE_ATTRIBUTE_NAME = "from-resource-type";
	public static final String CHARGE_OFF_RESOURCE_CURRENCY_ATTRIBUTE_NAME = "from-resource-currency";//������ ��������� ��������
	public static final String CHARGE_OFF_RESOURCE_NAME_ATTRIBUTE_NAME = "from-account-name";
	public static final String CHARGE_OFF_AMOUNT_ATTRIBUTE_NAME = "amount";//����� ��������

	public static final String PAYER_CARD_EXPIRE_DATE_ATTRIBUTE_NAME = "from-card-expire-date";
	public static final String FROM_CARD_DESCRIPTION_ATTRIBUTE_NAME = "from-charge-off-card-description";
	public static final String PAYER_CARD_ACCOUNT_ATTRIBUTE_NAME = "from-card-account";
	public static final String FROM_RESOURCE_LINK_CODE_ATTRIBUTE_NAME = "from-resource-link";
	public static final String PAYER_NAME_ATTRIBUTE_NAME = "payer-name";
	public static final String GROUND_ATTRIBUTE_NAME = "ground";

	public static final String DESTINATION_AMOUNT_ATTRIBUTE_NAME = "destination-amount";//����� ����������

	//����, � ������� ������ �������� �����(������� ������������)
	public static final String EXACT_AMOUNT_FIELD_ATTRIBUTE_NAME = "exact-amount";
	//������ �������� � ���� ����� ��������
	public static final String CHARGE_OFF_FIELD_EXACT_AMOUNT_ATTRIBUTE_VALUE = "charge-off-field-exact";
	//������ �������� � ���� ����� ����������
	public static final String DESTINATION_FIELD_EXACT_AMOUNT_ATTRIBUTE_VALUE = "destination-field-exact";
	//��� ��������
	public static final String OPERATION_CODE_ATTRIBUTE_NAME = "operation-code";

	protected static final ExternalResourceService externalResourceService = new ExternalResourceService();


	private String chargeOffResource;
	private Money chargeOffAmount;
	private Money destinationAmount;
	private String ground;
	private InputSumType inputSumType;
	private String confirmEmployee;             // ��������� ������������� ��������
	private String receiverName;


	/**
	 * ���� ��������, ��������������� �hargeOffResource
	 */
	private PaymentAbilityERL chargeOffResourceLink = null;

	/**
	 * ��� ��������� �����, ��� ��������� ����� (������������ ��� ��������).
	 */
	private boolean sumIncreasedOverLimit = false;

    /**
     * ��� ������ �� ������� ������������ ����������� ������ ����������� ������� (��������� ����� ������ ���������� ���������, ��������� �������� ����� � �.�.)
     */
    private boolean isPaymentFromTemplateNeedConfirm = false;


	/**
	 * @return ����/����� �����������
	 */
	public String getChargeOffAccount()
	{
		return chargeOffResource;
	}

	/**
	 * ��������� �����/����� �����������
	 * @param chargeOffAccount ���� �����������
	 */
	public void setChargeOffAccount(String chargeOffAccount)
	{
		this.chargeOffResource = chargeOffAccount;
		// �� �������� �������� ����
		this.chargeOffResourceLink = null;
	}

	/**
	 * @return ��� ��������� ��������(����/�����)
	 */
	public ResourceType getChargeOffResourceType()
	{
		String type = getNullSaveAttributeStringValue(CHARGE_OFF_RESOURCE_TYPE_ATTRIBUTE_NAME);
		if (StringHelper.isEmpty(type))
		{
			return ResourceType.NULL;
		}
		if (AccountLink.class.getName().equals(type))
		{
			return ResourceType.ACCOUNT;
		}
		if (CardLink.class.getName().equals(type))
		{
			return ResourceType.CARD;
		}
		throw new IllegalStateException("����������� ��� ��������� �������� " + type);
	}

	/**
	 * ���������� �������� "��� ������� ��������"
	 * @param resourceType
	 */
	public void setChargeOffResourceType(String resourceType)
	{
		setAttributeValue("string", AbstractPaymentDocument.CHARGE_OFF_RESOURCE_TYPE_ATTRIBUTE_NAME,  resourceType);
	}

	/**
	 * ���������� �������� "��� ������� ��������"
	 * @param resourceType ��� ���������
	 */
	public void setChargeOffResourceType(ResourceType resourceType)
	{
		setAttributeValue("string", AbstractPaymentDocument.CHARGE_OFF_RESOURCE_TYPE_ATTRIBUTE_NAME,  resourceType.getResourceLinkClass().getName());
	}

	/**
	 * ������� ������ �� �������� ��������
	 * ��������: ������ ������������ �� �������� ��������� �������,
	 * �.�. ����� ������������� ��� ������ ���������� (� ������ ������ ������������ null)
	 * @return ������ ��� null, ���� ����-��-�������� �������� ����� ���� ����� ��������� �������� �� ������ 
	 */
	public PaymentAbilityERL getChargeOffResourceLink() throws DocumentException
	{
		if (chargeOffResourceLink != null)
			return chargeOffResourceLink;

		String chargeOffNumber = getChargeOffAccount();
		if (StringHelper.isEmpty(chargeOffNumber))
			return null;

		ResourceType chargeOffResourceType = getChargeOffResourceType();
		try
		{
			BusinessDocumentOwner documentOwner = getOwner();
			if (documentOwner.isGuest())
				throw new UnsupportedOperationException("�������� � �������� ������ �� ��������������");
			ExternalResourceLink link = externalResourceService.findLinkByNumber(documentOwner.getLogin(), chargeOffResourceType, chargeOffNumber);
			// ������ ����� ���� ������� � �� �����������
			if (link == null)
				return null;

			if (!(link instanceof PaymentAbilityERL))
				throw new UnsupportedOperationException("���������� ��� ��������� �������� " + chargeOffResourceType);

			chargeOffResourceLink = (PaymentAbilityERL) link;
			return chargeOffResourceLink;
		}
		catch (BusinessException e)
		{
			throw new DocumentException("���� ��� ��������� �����-��-�������� ��������", e);
		}
	}

	/**
	 * @return ����� ����� ��������
	 */
	public String getChargeOffCard()
	{
		return chargeOffResource;
	}

	/**
	 * @return ���� ��������� ����� �������� ����� ��������
	 */
	public Calendar getChargeOffCardExpireDate()
	{
		return DateHelper.toCalendar((Date) getNullSaveAttributeValue(PAYER_CARD_EXPIRE_DATE_ATTRIBUTE_NAME));
	}

	/**
	 * ���������� ���� ��������� ����� �������� ����� ��������
	 * @param date ����
	 */
	protected void setChargeOffCardExpireDate(Calendar date)
	{
		setNullSaveAttributeCalendarValue(PAYER_CARD_EXPIRE_DATE_ATTRIBUTE_NAME, date);
	}

	/**
	 * @return  �������� ����� (Visa Classic, MasterCard, Maestro Cirrus etc)
	 */
	public String getChargeOffCardDescription()
	{
		return getNullSaveAttributeStringValue(FROM_CARD_DESCRIPTION_ATTRIBUTE_NAME);
	}

	/**
	 *  ���������� �������� ����� (Visa Classic, MasterCard, Maestro Cirrus etc)
	 * @param description �������� �����
	 */
	protected void setChargeOffCardDescription(String description)
	{
		setNullSaveAttributeStringValue(FROM_CARD_DESCRIPTION_ATTRIBUTE_NAME, description);
	}


	/**
	 * @return ���� ��������� ����� �������� ����� ��������
	 */
	public String getChargeOffCardAccount()
	{
		return getNullSaveAttributeStringValue(PAYER_CARD_ACCOUNT_ATTRIBUTE_NAME);
	}

	/**
	 * ���������� ��������� ���� ����� ��������
	 * @param number ����� ��
	 */
	protected void setChargeOffCardAccount(String number)
	{
		setNullSaveAttributeStringValue(PAYER_CARD_ACCOUNT_ATTRIBUTE_NAME, number);
	}

	public String getFromAccountName()
	{
		return getNullSaveAttributeStringValue(CHARGE_OFF_RESOURCE_NAME_ATTRIBUTE_NAME);
	}

	/**
	 * @return ����� �������� ��� ����� ��������.
	 */
	public Money getChargeOffAmount()
	{
		return chargeOffAmount;
	}

	/**
	 * ��������� ����� ���������
	 * @param amount ����� �������.
	 */
	public void setChargeOffAmount(Money amount)
	{
		this.chargeOffAmount = amount;
	}

	/**
	 * ����� ����������. (��� ��������)
	 *
	 * @return ����� ����������.
	 */
	public Money getDestinationAmount()
	{
		return destinationAmount;
	}

	/**
	 * ���������� ����� ����������(� ��� ������, ���� ��� ���������� � �������� ���������� �������)
	 * @param amount ����� ����������� �� ����
	 */
	public void setDestinationAmount(Money amount)
	{
		destinationAmount = amount;
	}

	/**
	 * �������� ������(��������� �������������) ����� ���������
	 * @return ������ �����
	 */
	public Money getExactAmount()
	{
		InputSumType sumType = getInputSumType();
		if (InputSumType.CHARGEOFF == sumType)
		{
			return getChargeOffAmount();
		}
		if (InputSumType.DESTINATION == sumType)
		{
			return getDestinationAmount();
		}
		if (isTemplate())
		{
			return null;
		}
		if (DocumentHelper.isLongOffer(this))
		{
			return null;
		}
		if (DocumentHelper.isRemoteConnectionUDBOClaim(this))
		{
			return null;
		}
		throw new IllegalStateException("���������� ���������� ������ �����: �� ����� ������� " + EXACT_AMOUNT_FIELD_ATTRIBUTE_NAME);
	}

	/**
	 * @return o��������(����������) �������.
	 */
	public String getGround()
	{
		return ground;
	}

	/**
	 *  ��������� o��������(����������) �������.
	 * @param ground o��������(����������) �������.
	 */
	public void setGround(String ground)
	{
		this.ground = ground;
	}

	private void setNationalCurrency(Currency currency)
	{
		setNullSaveAttributeStringValue(NATIONAL_CURRENCY_ATTRIBUTE_NAME, currency.getCode());
	}

	/**
	 * �������� ������������ ������ �� ������ ���������� �������
	 * @return ������������ ������
	 */
	public Currency getNationalCurrency() throws DocumentException
	{
		try
		{
			String currencyCode = getNullSaveAttributeStringValue(NATIONAL_CURRENCY_ATTRIBUTE_NAME);
			if (currencyCode == null)
			{
				return null;
			}
			return findCurrencyByISOCode(currencyCode);
		}
		catch (GateException e)
		{
			throw new DocumentException(e);
		}
	}

	/**
	 * @return ������ ��������.
	 */
	public Currency getChargeOffCurrency() throws GateException
	{
		String currencyCode = getNullSaveAttributeStringValue(CHARGE_OFF_RESOURCE_CURRENCY_ATTRIBUTE_NAME);
		if (currencyCode == null)
		{
			return null;
		}
		return findCurrencyByISOCode(currencyCode);
	}

	public AbstractPaymentDocument createCopy(Class<? extends BusinessDocument> instanceClass) throws DocumentException, DocumentLogicException
	{
		AbstractPaymentDocument document = (AbstractPaymentDocument) super.createCopy(instanceClass);
		document.setChargeOffAmount(getChargeOffAmount());
		document.setDestinationAmount(getDestinationAmount());
		document.setChargeOffAccount(getChargeOffAccount());
		document.setPayerName(getPayerName());
		document.setGround(getGround());
		document.setInputSumType(getInputSumType());
		return document;
	}

	public Document convertToDom() throws DocumentException
	{
		Document document = super.convertToDom();
		Element root = document.getDocumentElement();
		appendNullSaveString(root, CHARGE_OFF_RESOURCE_ATTRIBUTE_NAME, getChargeOffAccount());
		appendNullSaveString(root, PAYER_NAME_ATTRIBUTE_NAME, getPayerName());
		appendNullSaveString(root, GROUND_ATTRIBUTE_NAME, getGround());
		appendNullSaveMoney(root, CHARGE_OFF_AMOUNT_ATTRIBUTE_NAME, getChargeOffAmount());
		appendNullSaveMoney(root, DESTINATION_AMOUNT_ATTRIBUTE_NAME, getDestinationAmount());
		appendNullSaveString(root, EXACT_AMOUNT_FIELD_ATTRIBUTE_NAME, getInputSumType()==null?null: getInputSumType().toValue());
		appendNullSaveString(root, RECEIVER_NAME_ATTRIBUTE_NAME, getReceiverName());
		return document;
	}

	protected void readFromDom(Document document, InnerUpdateState updateState, MessageCollector messageCollector) throws DocumentException, DocumentLogicException
	{
		super.readFromDom(document, updateState, messageCollector);
		Element root = document.getDocumentElement();

		InputSumType sumType = InputSumType.fromValue(XmlHelper.getSimpleElementValue(document.getDocumentElement(), "exact-amount"));
		if (sumType == null)
		{
			setChargeOffAmount(null);
			setDestinationAmount(null);
		}
		else if (InputSumType.CHARGEOFF == sumType)
		{
			//�������� ������ ���� �����, ������ ��������
			if (XmlHelper.tagTest(CHARGE_OFF_AMOUNT_ATTRIBUTE_NAME, root))
			{
				setChargeOffAmount(getMoneyFromDom(root, CHARGE_OFF_AMOUNT_ATTRIBUTE_NAME));
			}
			setDestinationAmount(null);
		}
		else if (InputSumType.DESTINATION == sumType)
		{
			//�������� ������ ���� �����, ������ ��������
			if (XmlHelper.tagTest(DESTINATION_AMOUNT_ATTRIBUTE_NAME, root))
			{
				setDestinationAmount(getMoneyFromDom(root, DESTINATION_AMOUNT_ATTRIBUTE_NAME));
			}
			setChargeOffAmount(null);
		}

		if (XmlHelper.tagTest(PAYER_NAME_ATTRIBUTE_NAME, root))
			setPayerName(XmlHelper.getSimpleElementValue(root, PAYER_NAME_ATTRIBUTE_NAME));

		if(XmlHelper.tagTest(CHARGE_OFF_RESOURCE_ATTRIBUTE_NAME, root))
			setChargeOffAccount(XmlHelper.getSimpleElementValue(root, CHARGE_OFF_RESOURCE_ATTRIBUTE_NAME));

		if(XmlHelper.tagTest(RECEIVER_NAME_ATTRIBUTE_NAME, root))
			setReceiverName(XmlHelper.getSimpleElementValue(root, RECEIVER_NAME_ATTRIBUTE_NAME));

		setInputSumType(sumType);
		setGround(XmlHelper.getSimpleElementValue(root, GROUND_ATTRIBUTE_NAME));
		setNationalCurrency(findNationalCurrency());
		storeChargeOffCurrencyInfo(updateState);
		storeDocumentOfficeInfo();
	}

	/**
	 * ��������� �������� ������ ������������ � ����, � ������� ������� �������� ��������.
	 * ���������� ������ ���� � ���������.
	 */
	protected void storeDocumentOfficeInfo() throws DocumentException
	{
		try
		{
			PaymentAbilityERL chargeOffLink = getChargeOffResourceLink();
			if (chargeOffLink == null)
			{
				return;
			}

			// ���� �������� ���������� � �����, �� ������������� ��������� ������ ���� �������������� �������
			if(chargeOffLink  instanceof CardLink)
			{
				setOffice(getDepartment());
				return;
			}

			//���� �������� ���������� �� �����, �� ���� ������������� � ����� ��.
			if((chargeOffLink instanceof AccountLink) || (chargeOffLink instanceof IMAccountLink))
			{
				Code code = new ExtendedCodeImpl(chargeOffLink.getOffice().getCode());
				Department department = departmentService.findByCode(code);
				// ���� ������������� �� ������� - ������ ����������
				if(department == null)
					throw new DocumentException("�� ������� ����� ������������� � �� = "+ code.getFields().get("region")+" ��� = " +
							code.getFields().get("branch") + " ��� = " + code.getFields().get("office") );

				setOffice(department);
				return;
			}

			throw new DocumentException("���� �������� ������ ���� ���� �����, ���� ���� ");
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}

	/**
	 * ��������� ���������� � ������ ��������� ��������
	 * ����� ���������� ��� �������� � ���������� ���������
	 */
	private void storeChargeOffCurrencyInfo(InnerUpdateState updateState) throws DocumentLogicException, DocumentException
	{
		// 1. ���� ������ ��������� �������� ���, �� � ������ ��������� �� �����
		if (StringHelper.isEmpty(getChargeOffAccount()))
			return;

		// 2. ���� ����� ���, �� ��� ��������
		PaymentAbilityERL link = getChargeOffResourceLink();
		if (link == null)
		{
			if(updateState == InnerUpdateState.INIT)
				return;

			throw new DocumentException("�� ������ ����-��-�������� �������� ���� " + getChargeOffResourceType());
		}

		Currency currency = link.getCurrency();
		if (currency == null)
			throw new DocumentLogicException("���������� ���������� ������ �����/����� ��������");

		setChargeOffCurrency(currency);
	}

	/**
	 * ���������� ������ ��������� ��������
	 * @param currency ������.
	 */
	protected void setChargeOffCurrency(Currency currency)
	{
		setNullSaveAttributeStringValue(CHARGE_OFF_RESOURCE_CURRENCY_ATTRIBUTE_NAME, currency.getCode());
	}

	/**
	 * �������� ����� ��������
	 * @param messageCollector ��������� ������, ����� ���� null
	 * @throws DocumentException
	 * @throws DocumentLogicException
	 * @return �������� ����� ��������
	 */
	protected Card receiveChargeOffCard(MessageCollector messageCollector) throws DocumentException, DocumentLogicException
	{
		ResourceType chargeOffResourceType = getChargeOffResourceType();
		if (chargeOffResourceType == ResourceType.CARD)
		{
			return receiveCardLinkByCardNumber(getChargeOffCard(), messageCollector).getCard();
		}
		throw new IllegalStateException("�������� ��� ��������� �������� " + chargeOffResourceType);
	}

	/**
	 * �������� ���� �������� �����
	 * @param chargeOffResourceType ��� ��������� ��������
	 * @param messageCollector ��������� ������, ����� ���� null
	 * @return ���� �������� �����
	 * @throws DocumentException
	 * @throws DocumentLogicException
	 */
	protected Calendar getChargeOffCardExpireDate(ResourceType chargeOffResourceType, MessageCollector messageCollector) throws DocumentException, DocumentLogicException
	{
		if (chargeOffResourceType == ResourceType.CARD)
		{
			return receiveCardLinkByCardNumber(getChargeOffCard(), messageCollector).getExpireDate();
		}
		throw new IllegalStateException("�������� ��� ��������� �������� " + chargeOffResourceType);
	}

	/**
	 * �������� �������� �� ������ �����
	 * ��������� ���������� �� ���������. ����� ������������ ������ ��� ����� ����.
	 * @param cardNumber ����� �����
	 * @param messageCollector ��������� ������, ����� ���� null
	 * @return ��������
	 * @throws DocumentLogicException
	 * @throws DocumentException
	 */
	protected CardLink receiveCardLinkByCardNumber(String cardNumber, MessageCollector messageCollector) throws DocumentLogicException, DocumentException
	{
		try
		{
			BusinessDocumentOwner documentOwner = getOwner();
			if (documentOwner.isGuest())
				throw new UnsupportedOperationException("�������� � �������� ������ �� ��������������");
			CardLink cardLink = externalResourceService.findLinkByNumber(documentOwner.getLogin(), ResourceType.CARD, cardNumber);
			if (cardLink == null)
			{
				throw new DocumentLogicException("���������� �������� ���������� �� ����� " + MaskUtil.getCutCardNumber(cardNumber));
			}

			if (!LinkHelper.isVisibleInChannel(cardLink))
			{
				cardLink = processErrorCard(cardLink, messageCollector);
			}

			Card card = cardLink.getCard();
			if (MockHelper.isMockObject(card))
			{
				throw new DocumentLogicException("���������� �������� ���������� �� ����� " + MaskUtil.getCutCardNumber(cardNumber));
			}
			return cardLink;
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}

	/**
	 * @return ���� ������� ������ ������� ��� ������ ��������
	 */
	//TODO  ������
	public CurrencyRate getDebetSaleRate()
	{
		return null;
	}

	/**
	 * @return ���� ������� ������ ������� ��� ������ ��������
	 */
	//TODO  ������
	public CurrencyRate getDebetBuyRate()
	{
		return null;
	}

	/**
	 * @return ���� ������� ������ ������� ��� ������ ����������
	 */
	//TODO  ������
	public CurrencyRate getCreditSaleRate()
	{
		return null;
	}

	/**
	 * @return ���� ������� ������ � ������� ��� ������ ����������
	 */
	//TODO  ������
	public CurrencyRate getCreditBuyRate()
	{
		return null;
	}

	/**
	 * �������� ��������� �� �������� �����. ���� � ����������� ��������� �� ��������� �������� ����,
	 *  � � �������� ���������, ��������� ����������.
	 * @param template ������, �� ������ �������� ��� ������ ��������
	 * @return true - �������� ���� � ���������� ����������, false - �������� ���� ������
	 */
	public boolean equalsKeyEssentials(TemplateDocument template) throws BusinessException
	{
		if (template == null)
		{
			return false;
		}

		return StringHelper.equalsNullIgnore(getChargeOffCardAccount(), template.getChargeOffCardAccount());
	}

	//����������� ���������� ��� ���������� ����� cardLink != null
	protected CardLink processErrorCard(CardLink cardlink, MessageCollector messageCollector) throws DocumentLogicException, DocumentException
	{
		throw new DocumentLogicException("�� �� ������ ��������� ������ ��������. ��� ������� �������� ��������� ��������� ����� ����� " + MaskUtil.getCutCardNumber(cardlink.getNumber())
				+ " � ������ ���� ���������� - ���������� ������������ - ���������� ��������� ���������");
	}

	/**
	 * ���������� ��� ���� � ����� �������� ������������ ���� ����� ��������
	 * @return enum, ��� ���� � ������ �����
	 */
	public InputSumType getInputSumType()
	{
		return inputSumType;
	}

	public void setInputSumType(InputSumType inputSumType)
	{
	  	this.inputSumType = inputSumType;
	}

	public void setInputSumType(String inputSumType)
	{
		if(inputSumType == null || inputSumType.trim().length() == 0)
			return;

		this.inputSumType = Enum.valueOf(InputSumType.class, inputSumType);
	}

	/**
	 * @return ��� ��������
	 */
	public String getOperationCode()
	{
		return getNullSaveAttributeStringValue(OPERATION_CODE_ATTRIBUTE_NAME);
	}

	public String getConfirmEmployee()
	{
		return confirmEmployee;
	}

	public void setConfirmEmployee(String confirmEmployee)
	{
		this.confirmEmployee = confirmEmployee;
	}

	/**
	 * @return ������������ ����������
	 */
	public String getReceiverName()
	{
		return receiverName;
	}

	/**
	 * @param receiverName ������������ ����������
	 */
	public void setReceiverName(String receiverName)
	{
		this.receiverName = receiverName;
	}

	/**
	 * �������� �� ������ �� ������������� ��������������� �������
	 * @return true - ��������
	 * @throws DocumentException
	 */
	public boolean isPaymentByConfirmTemplate() throws DocumentException
	{
		// ������ �� �� �������
		if (!isByTemplate())
		{
			return false;
		}

		try
		{
			TemplateDocument template = TemplateDocumentService.getInstance().findById(getTemplateId());
			if (template == null)
			{
				return false;
			}

			return template.getAdditionalOperationChannel() != null && equalsKeyEssentials(template);
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}

	public Set<ExternalResourceLink> getLinks() throws DocumentException
	{
		Set<ExternalResourceLink> result = super.getLinks();
		PaymentAbilityERL link = getChargeOffResourceLink();
		if (link != null)
		{
			result.add(link);
		}
		return result;
	}

	public void setSumIncreasedOverLimit(boolean sumIncreasedOverLimit)
	{
		this.sumIncreasedOverLimit = sumIncreasedOverLimit;
	}

	public boolean getSumIncreasedOverLimit()
	{
		return sumIncreasedOverLimit;
	}

    public boolean isPaymentFromTemplateNeedConfirm()
    {
        return isPaymentFromTemplateNeedConfirm;
    }

    public void setPaymentFromTemplateNeedConfirm(boolean paymentFromTemplateNeedConfirm)
    {
        this.isPaymentFromTemplateNeedConfirm = paymentFromTemplateNeedConfirm;
    }

    public BigDecimal getConvertionRate()
	{
		return null;
	}
}
