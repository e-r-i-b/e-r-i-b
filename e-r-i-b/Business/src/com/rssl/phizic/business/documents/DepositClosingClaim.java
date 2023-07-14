package com.rssl.phizic.business.documents;

import com.rssl.common.forms.doc.TypeOfPayment;
import com.rssl.phizic.business.extendedattributes.ExtendedAttribute;
import com.rssl.phizic.gate.documents.GateDocument;

/**
 * ������ �� �������� ��������
 * @author Kidyaev
 * @ created 12.02.2007
 * @ $Author$
 * @ $Revision$
 */
public class DepositClosingClaim extends ClosingClaim implements com.rssl.phizic.gate.claims.DepositClosingClaim
{
	public static final String DESTINATION_ACCOUNT_ATTRIBUTE_NAME = "destination-account";
	public static final String DEPOSIT_ID_ATTRIBUTE_NAME = "deposit-id";

	/**
	 * ���������� ��� ���������
	 *
	 * @return ���������� ��� ���������
	 */
	public Class<? extends GateDocument> getType()
	{
		return com.rssl.phizic.gate.claims.DepositClosingClaim.class;
	}

	public String getDestinationAccount()
	{
		return getNullSaveAttributeStringValue(DESTINATION_ACCOUNT_ATTRIBUTE_NAME);
	}

	public void setExternalDepositId(String id)
	{
		ExtendedAttribute extendedAttribute = getAttribute(DEPOSIT_ID_ATTRIBUTE_NAME);
		extendedAttribute.setValue(id);
	}

	public String getExternalDepositId()
	{
		return getNullSaveAttributeStringValue(DEPOSIT_ID_ATTRIBUTE_NAME);
	}

	public TypeOfPayment getTypeOfPayment()
	{
		return TypeOfPayment.INTERNAL_PAYMENT_OPERATION;
	}
}
