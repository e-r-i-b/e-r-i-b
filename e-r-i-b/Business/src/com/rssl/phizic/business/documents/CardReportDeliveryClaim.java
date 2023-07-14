package com.rssl.phizic.business.documents;

import com.rssl.common.forms.doc.TypeOfPayment;
import com.rssl.phizic.gate.bankroll.ReportDeliveryLanguage;
import com.rssl.phizic.gate.bankroll.ReportDeliveryType;
import com.rssl.phizic.gate.documents.GateDocument;

/**
 * @author akrenev
 * @ created 05.05.2014
 * @ $Author$
 * @ $Revision$
 *
 * «а€вка на изменение параметров подписки по карте
 */

@SuppressWarnings("ClassNameSameAsAncestorName")
public class CardReportDeliveryClaim extends GateExecutableDocument implements com.rssl.phizic.gate.claims.CardReportDeliveryClaim
{
	public static final String FORM_NAME                        = CardReportDeliveryClaim.class.getSimpleName();
	public static final String CARD_ID_ATTRIBUTE_NAME           = "card-id-delivery";
	public static final String CARD_CLIENT_ID_ATTRIBUTE_NAME    = "card-client-id-delivery";
	public static final String CARD_EXTERNAL_ID_ATTRIBUTE_NAME  = "card-external-id-delivery";
	public static final String CARD_NUMBER_ATTRIBUTE_NAME       = "card-number-delivery";
	public static final String CARD_NAME_ATTRIBUTE_NAME         = "card-name-delivery";
	public static final String USE_ATTRIBUTE_NAME               = "use-delivery";
	public static final String E_MAIL_ATTRIBUTE_NAME            = "email-delivery";
	public static final String TYPE_ATTRIBUTE_NAME              = "type-delivery";
	public static final String LANGUAGE_ATTRIBUTE_NAME          = "language-delivery";
	public static final String CONTRACT_NUMBER_ATTRIBUTE_NAME   = "contract-number";

	public TypeOfPayment getTypeOfPayment()
	{
		return TypeOfPayment.NOT_PAYMENT_OPEATION;
	}

	public Class<? extends GateDocument> getType()
	{
		return com.rssl.phizic.gate.claims.CardReportDeliveryClaim.class;
	}

	/**
	 * @return идентификатор карты
	 */
	public Long getCardIdReportDelivery()
	{
		return getNullSaveAttributeLongValue(CARD_ID_ATTRIBUTE_NAME);
	}

	/**
	 * задать идентификатор карты
	 * @param cardId идентификатор карты
	 */
	public void setCardIdReportDelivery(Long cardId)
	{
		setNullSaveAttributeLongValue(CARD_ID_ATTRIBUTE_NAME, cardId);
	}

	public String getCardClientIdReportDelivery()
	{
		return getNullSaveAttributeStringValue(CARD_CLIENT_ID_ATTRIBUTE_NAME);
	}

	/**
	 * задать внешний идентификатор клиента
	 * @param clientId внешний идентификатор клиента
	 */
	public void setCardClientIdReportDelivery(String clientId)
	{
		setNullSaveAttributeStringValue(CARD_CLIENT_ID_ATTRIBUTE_NAME, clientId);
	}

	public String getCardExternalIdReportDelivery()
	{
		return getNullSaveAttributeStringValue(CARD_EXTERNAL_ID_ATTRIBUTE_NAME);
	}

	/**
	 * задать внешний идентификатор карты
	 * @param cardExternalId внешний идентификатор карты
	 */
	public void setCardExternalIdReportDelivery(String cardExternalId)
	{
		setNullSaveAttributeStringValue(CARD_EXTERNAL_ID_ATTRIBUTE_NAME, cardExternalId);
	}

	/**
	 * @return номер карты
	 */
	@SuppressWarnings("UnusedDeclaration") // дл€ истории операций
	public String getCardNumberReportDelivery()
	{
		return getNullSaveAttributeStringValue(CARD_NUMBER_ATTRIBUTE_NAME);
	}

	/**
	 * задать номер карты
	 * @param cardNumber номер карты
	 */
	public void setCardNumberReportDelivery(String cardNumber)
	{
		setNullSaveAttributeStringValue(CARD_NUMBER_ATTRIBUTE_NAME, cardNumber);
	}

	/**
	 * @return название карты
	 */
	@SuppressWarnings("UnusedDeclaration") // дл€ истории операций
	public String getCardNameReportDelivery()
	{
		return getNullSaveAttributeStringValue(CARD_NAME_ATTRIBUTE_NAME);
	}

	/**
	 * задать название карты
	 * @param cardName название карты
	 */
	public void setCardNameReportDelivery(String cardName)
	{
		setNullSaveAttributeStringValue(CARD_NAME_ATTRIBUTE_NAME, cardName);
	}

	public boolean isUseReportDelivery()
	{
		return (Boolean) getNullSaveAttributeValue(USE_ATTRIBUTE_NAME);
	}

	public String getEmailReportDelivery()
	{
		return getNullSaveAttributeStringValue(E_MAIL_ATTRIBUTE_NAME);
	}

	public ReportDeliveryType getReportDeliveryType()
	{
		return getNullSaveAttributeEnumValue(ReportDeliveryType.class, TYPE_ATTRIBUTE_NAME);
	}

	public ReportDeliveryLanguage getReportDeliveryLanguage()
	{
		return getNullSaveAttributeEnumValue(ReportDeliveryLanguage.class, LANGUAGE_ATTRIBUTE_NAME);
	}

	public String getContractNumber()
	{
		return getNullSaveAttributeStringValue(CONTRACT_NUMBER_ATTRIBUTE_NAME);
	}

	/**
	 * «адать номер договора
	 * @param contractNumber номер договора
	 */
	public void setContractNumber(String contractNumber)
	{
		setNullSaveAttributeStringValue(CONTRACT_NUMBER_ATTRIBUTE_NAME, contractNumber);
	}
}
