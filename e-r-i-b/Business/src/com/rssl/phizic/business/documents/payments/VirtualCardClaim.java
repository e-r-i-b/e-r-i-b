package com.rssl.phizic.business.documents.payments;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.doc.TypeOfPayment;
import com.rssl.phizic.business.documents.AbstractPaymentDocument;
import com.rssl.phizic.business.resources.external.ExternalResourceLink;
import com.rssl.phizic.gate.documents.GateDocument;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Mescheryakova
 * @ created 06.10.2011
 * @ $Author$
 * @ $Revision$
 *
 * ������ �� ����������� �����
 */

public class VirtualCardClaim extends AbstractPaymentDocument
{
 	private static final String CARD_PRODUCT_NAME_ATTRIBUTE_NAME = "�ardProduct"; // �������� ���� ���� ������, �.�. ������������ ���������� ��������
	private static final String CARD_PRODUCT_KIND_ATTRIBUTE_NAME = "kindCardProduct"; // ��� ���������� ��������
	private static final String CARD_PRODUCT_SUBKIND_ATTRIBUTE_NAME = "subKindCardProduct"; // ������ ���������� ��������
	private static final String CARD_PRODUCT_CURRENCY_CODE_ATTRIBUTE_NAME = "currencyCodeCardProduct"; // ��� ������ ���������� ��������
	private static final String CARD_PRODUCT_CURRENCY_NAME_ATTRIBUTE_NAME = "currencyNameCardProduct"; // ��������� ��� ������ ���������� ��������
	private static final String SUR_NAME_ATTRIBUTE_NAME = "surName";        // ������� �������
	private static final String FIRST_NAME_ATTRIBUTE_NAME = "firstName";    // ��� �������
	private static final String PATR_NAME_ATTRIBUTE_NAME = "patrName";     // �������� �������
	private static final String MOBILE_PHONE_ATTRIBUTE_NAME = "mobilePhone";     // ��������� �������	
	private static final String CODE_MOBILE_OPERATOR_ATTRIBUTE_NAME = "codeMobileOperator";     // ��� ���������� ���������
	private static final String TARIFF_MOBILE_OPERATOR_ATTRIBUTE_NAME = "mobileTariff";     // ����� ���������� �����: 1- ��������� �����, 2- ������ �����		
	private static final String CARD_NUMBER_ATTRIBUTE_NAME = "card-number";     // ����� �����, ��� ������� ����� ������
	private static final String CARD_PRODUCT_ID_ATTRIBUTE_NAME = "cardProductId";  // id ���������� ��������

	/**
    * ���������� ��� ���������
    */
	public Class<? extends GateDocument> getType()
	{
		return com.rssl.phizic.gate.claims.cards.VirtualCardClaim.class;
	}

	public String getNameCardProduct()
	{
		return getNullSaveAttributeStringValue(CARD_PRODUCT_NAME_ATTRIBUTE_NAME);	
	}

	public Long getKindCardProduct()
	{
		return Long.valueOf(getNullSaveAttributeStringValue(CARD_PRODUCT_KIND_ATTRIBUTE_NAME));
	}

	public Long getSubKindCardProduct()
	{
		return Long.valueOf(getNullSaveAttributeStringValue(CARD_PRODUCT_SUBKIND_ATTRIBUTE_NAME));
	}

	public String getCurrencyCodeCardProduct()
	{
		return getNullSaveAttributeStringValue(CARD_PRODUCT_CURRENCY_CODE_ATTRIBUTE_NAME);
	}

	public String getCurrencyNameCardProduct()
	{
		return getNullSaveAttributeStringValue(CARD_PRODUCT_CURRENCY_NAME_ATTRIBUTE_NAME);		
	}

	public String getSurName()
	{
		return getNullSaveAttributeStringValue(SUR_NAME_ATTRIBUTE_NAME);
	}

	public String getFirstName()
	{
		return getNullSaveAttributeStringValue(FIRST_NAME_ATTRIBUTE_NAME);		
	}

	public String getPatrName()
	{
		return getNullSaveAttributeStringValue(PATR_NAME_ATTRIBUTE_NAME);
	}

	public String getMobilePhone()
	{
		return getNullSaveAttributeStringValue(MOBILE_PHONE_ATTRIBUTE_NAME);
	}

	public String getCodeMobileOperator()
	{
		return getNullSaveAttributeStringValue(CODE_MOBILE_OPERATOR_ATTRIBUTE_NAME);		
	}

	public String getTariffMobileOperator()
	{
		return getNullSaveAttributeStringValue(TARIFF_MOBILE_OPERATOR_ATTRIBUTE_NAME);		
	}

	public String getLastCardNumber()
	{
		return getNullSaveAttributeStringValue(CARD_NUMBER_ATTRIBUTE_NAME);				
	}

	public Long getCardProductId()
	{
		return Long.valueOf(getNullSaveAttributeStringValue(CARD_PRODUCT_ID_ATTRIBUTE_NAME));
	}

	public Set<ExternalResourceLink> getLinks() throws DocumentException
	{
		//������ ������������ �� AbstractPaymentDocument. �� ����� ���� ������ �� ������� �� � ����� �� ��������.
		return new HashSet<ExternalResourceLink>();
	}

	public TypeOfPayment getTypeOfPayment()
	{
		return TypeOfPayment.NOT_PAYMENT_OPEATION;
	}
}
