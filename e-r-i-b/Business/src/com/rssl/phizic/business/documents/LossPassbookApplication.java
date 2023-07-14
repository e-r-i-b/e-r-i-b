package com.rssl.phizic.business.documents;

import com.rssl.phizic.business.extendedattributes.ExtendedAttribute;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.claims.LossPassbookApplicationClaim;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.common.forms.doc.TypeOfPayment;

/**
 * @author Kidyaev
 * @ created 17.08.2006
 * @ $Author$
 * @ $Revision$
 */
public class LossPassbookApplication extends GateExecutableDocument implements LossPassbookApplicationClaim
{
	private static final String CLOSE_ACCOUNT_OR_PASSBOOK_DUPLICATE_FIELD_NAME = "amount-or-passbook";
	private static final String DEPOSIT_ACCOUNT_TAGNAME = "account-number";
	private static final String GET_MONEY_OR_TRANSFER_TO_ACCOUNT_FIELD_NAME = "money-or-transfer";
	private static final String GET_RECEIVER_ACCOUNT = "receiver-account";
	private static final String FROM_ACCOUNT_NAME_ATTRIBUTE_NAME = "from-account-name";
	private static final String CURRENCY_ATTRIBUTE = "amount-currency";
	private static final String ACCOUNT_REST = "account-rest";
	private static final String CLOSE_AMOUNT_VALUE = "0";
	private static final String PASSBOOK_DUPLICATE_VALUE = "1";
	private static final String GET_MONEY_VALUE = "0";
	private static final String TRANSFER_TO_ACCOUNT_VALUE = "1";

	public static final int GET_MONEY = 1;
	public static final int TRANSFER_TO_ACCOUNT = 2;
	public static final int PASSBOOK_DUPLICATE = 3;

	private Money amount;

	public Money getChargeOffAmount(){
		return amount;
	}

	public void setChargeOffAmount(Money amount){
		this.amount = amount;
	}

	public String getDepositAccount()
	{
		return getNullSaveAttributeStringValue(DEPOSIT_ACCOUNT_TAGNAME);
	}

	public boolean getCloseAmountFlag()
	{
		return equalToAttributeValue(CLOSE_ACCOUNT_OR_PASSBOOK_DUPLICATE_FIELD_NAME, CLOSE_AMOUNT_VALUE);
	}

	public boolean getPassbookDuplicateFlag()
	{
		return equalToAttributeValue(CLOSE_ACCOUNT_OR_PASSBOOK_DUPLICATE_FIELD_NAME, PASSBOOK_DUPLICATE_VALUE);
	}

	public boolean getMoneyFlag()
	{
		return equalToAttributeValue(GET_MONEY_OR_TRANSFER_TO_ACCOUNT_FIELD_NAME, GET_MONEY_VALUE);
	}

	public boolean getTransferToAccountFlag()
	{
		return equalToAttributeValue(GET_MONEY_OR_TRANSFER_TO_ACCOUNT_FIELD_NAME, TRANSFER_TO_ACCOUNT_VALUE);
	}

	public int getAccountAction()
	{
		if(getMoneyFlag())
			return GET_MONEY;
		else if (getPassbookDuplicateFlag())
			return PASSBOOK_DUPLICATE;
		return TRANSFER_TO_ACCOUNT;
	}

	public String getReceiverAccount()
	{
		return getAttribute(GET_RECEIVER_ACCOUNT).getStringValue();
	}

	public String getFromAccountName()
	{
		return getNullSaveAttributeStringValue(FROM_ACCOUNT_NAME_ATTRIBUTE_NAME);
	}

	private boolean equalToAttributeValue(String attributeName, String value)
	{
		boolean result = false;
		ExtendedAttribute attribute = getAttribute(attributeName);

		if (attribute != null)
		{
			String attributeValue = attribute.getStringValue();
			result = attributeValue.equals(value);
		}

		return result;
	}

	/**
	 * Фактичский тип документа
	 *
	 * @return фактичский тип документа
	 */
	public Class<? extends GateDocument> getType()
	{
		return LossPassbookApplicationClaim.class;
	}

	/**
	 * Остаток на счете
	 *
	 * @return  Остаток на счете
	 */
	public Money getAccountRest()
	{
		String decimalPart  = getNullSaveAttributeValue(ACCOUNT_REST).toString();
		String currencyPart = getNullSaveAttributeValue(CURRENCY_ATTRIBUTE).toString();

	    return createMoney(decimalPart, currencyPart);
	}

	public TypeOfPayment getTypeOfPayment()
	{
		return TypeOfPayment.NOT_PAYMENT_OPEATION;
	}
}
