package com.rssl.phizic.business.documents.templates;

/**
 * “ипы получателей платежа
 *
 * @author khudyakov
 * @ created 06.06.2013
 * @ $Author$
 * @ $Revision$
 */
public enum ReceiverSubType
{
	INDIVIDUAL_OUR_CARD_BY_CONTACT("ourContact"),
	INDIVIDUAL_OUR_CARD("ourCard"),
	INDIVIDUAL_OUR_PHONE("ourPhone"),
	INDIVIDUAL_EXTERNAL_MASTER_CARD("masterCardExternalCard"),
	INDIVIDUAL_EXTERNAL_VISA_CARD("visaExternalCard"),
	INDIVIDUAL_EXTERNAL_CARD_BY_CONTACT("ourContactToOtherCard"),
	INDIVIDUAL_OUR_ACCOUNT("ourAccount"),
	INDIVIDUAL_EXTERNAL_ACCOUNT("externalAccount"),
	JURIDICAL_EXTERNAL_ACCOUNT("juridicalExternalAccount"),
	YANDEX_WALLET("yandexWallet"),
	YANDEX_WALLET_OUR_CONTACT("yandexWalletOurContact"),
	YANDEX_WALLET_BY_PHONE("yandexWalletByPhone");

	private String type;

	ReceiverSubType(String type)
	{
		this.type = type;
	}

	/**
	 * ѕривести струку к типу енума
	 *
	 * @param value тип
	 * @return значение енума
	 */
	public static ReceiverSubType fromValue(String value)
	{
		if (INDIVIDUAL_OUR_CARD.getType().equals(value))
		{
			return INDIVIDUAL_OUR_CARD;
		}
		if (INDIVIDUAL_OUR_PHONE.getType().equals(value))
		{
			return INDIVIDUAL_OUR_PHONE;
		}
		if (INDIVIDUAL_EXTERNAL_MASTER_CARD.getType().equals(value))
		{
			return INDIVIDUAL_EXTERNAL_MASTER_CARD;
		}
		if (INDIVIDUAL_EXTERNAL_VISA_CARD.getType().equals(value))
		{
			return INDIVIDUAL_EXTERNAL_VISA_CARD;
		}
		if (INDIVIDUAL_OUR_ACCOUNT.getType().equals(value))
		{
			return INDIVIDUAL_OUR_ACCOUNT;
		}
		if (INDIVIDUAL_EXTERNAL_ACCOUNT.getType().equals(value))
		{
			return INDIVIDUAL_EXTERNAL_ACCOUNT;
		}
		if (JURIDICAL_EXTERNAL_ACCOUNT.getType().equals(value))
		{
			return JURIDICAL_EXTERNAL_ACCOUNT;
		}
		if (INDIVIDUAL_OUR_CARD_BY_CONTACT.getType().equals(value))
		{
			return INDIVIDUAL_OUR_CARD_BY_CONTACT;
		}
		if (INDIVIDUAL_EXTERNAL_CARD_BY_CONTACT.getType().equals(value))
		{
			return INDIVIDUAL_EXTERNAL_CARD_BY_CONTACT;
		}
		if (YANDEX_WALLET.getType().equals(value))
		{
			return YANDEX_WALLET;
		}
		if (YANDEX_WALLET_OUR_CONTACT.getType().equals(value))
		{
			return YANDEX_WALLET_OUR_CONTACT;
		}
		if (YANDEX_WALLET_BY_PHONE.getType().equals(value))
		{
			return YANDEX_WALLET_BY_PHONE;
		}
		throw new IllegalArgumentException();
	}

	/**
	 * @return тип
	 */
	public String getType()
	{
		return type;
	}
}
