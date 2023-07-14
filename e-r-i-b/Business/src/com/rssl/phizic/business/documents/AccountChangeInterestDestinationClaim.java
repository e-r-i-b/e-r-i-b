package com.rssl.phizic.business.documents;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.doc.TypeOfPayment;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.payments.BusinessDocumentOwner;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.ExternalResourceLink;
import com.rssl.phizic.business.resources.external.ExternalResourceService;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.utils.StringHelper;

import java.util.Calendar;
import java.util.Collections;
import java.util.Set;

/**
 * ������ �� ��������� ������� ������ ��������� �� ������
 * @author Jatsky
 * @ created 26.09.13
 * @ $Author$
 * @ $Revision$
 */

public class AccountChangeInterestDestinationClaim extends GateExecutableDocument implements com.rssl.phizic.gate.claims.AccountChangeInterestDestinationClaim
{
	private static final ExternalResourceService externalResourceService = new ExternalResourceService();
	protected static final String ACCOUNT_ID = "account-id";
	protected static final String ACCOUNT_CODE = "account-code";

	private static final String INTEREST_CARD_NUMBER = "interest-card-number";
	private static final String INTEREST_DESTINATION_SOURCE = "interest-destination-source";
	//������ ��� ������
	//�� ����������
	private static final String PROMOTER_ID = "promoter-id";
	//����� ��� ������� ����� ������
	private static final String LOGON_CARD_NUMBER = "logon-card-number";
	/*������������� ��������*/
	public static final String OPER_UID = "oper-uid";
	/*���� �������� ���������*/
	public static final String OPER_TIME = "oper-time";

	private AccountLink modifiableLink = null;

	public String getAccountCode()
	{
		return getNullSaveAttributeStringValue(ACCOUNT_CODE);
	}

	public Long getAccountId()
	{
		if (StringHelper.isNotEmpty(getAccountCode()))
			return AccountLink.getIdFromCode(getAccountCode());
		// ��� ���������� ��������� ������ ������
		else
			return getNullSaveAttributeLongValue(ACCOUNT_ID);
	}

	public AccountLink getModifiableLink() throws DocumentException
	{
		if (modifiableLink != null)
			return modifiableLink;

		try
		{
			AccountLink link = externalResourceService.findLinkById(AccountLink.class, getAccountId());
			// ������ ����� ���� ������� � �� �����������
			if (link == null || link.getLoginId() == null)
				return null;

			BusinessDocumentOwner documentOwner = getOwner();
			if (documentOwner.isGuest())
				throw new UnsupportedOperationException("�������� � �������� ������ �� ��������������");
			if (!link.getLoginId().equals(documentOwner.getLogin().getId()))
				throw new DocumentException("LoginId ����� � ��������� �� ���������.");

			modifiableLink = link;
			return modifiableLink;
		}
		catch (BusinessException e)
		{
			throw new DocumentException("���� ��� ��������� ����� ����������� ������", e);
		}
	}

	public String getChangePercentDestinationAccountNumber() throws DocumentException
	{
		AccountLink accountLink = getModifiableLink();

		if (accountLink == null)
			return null;

		return accountLink.getNumber();
	}

	public String getPercentCardNumber()
	{
		return getNullSaveAttributeStringValue(INTEREST_CARD_NUMBER);
	}

	public void setPromoterId(String promoterId)
	{
		setNullSaveAttributeStringValue(PROMOTER_ID, promoterId);
	}

	public void setLogonCardNumber(String cardNumber)
	{
		setNullSaveAttributeStringValue(LOGON_CARD_NUMBER, cardNumber);
	}

	public String getInterestDestinationSource()
	{
		return getNullSaveAttributeStringValue(INTEREST_DESTINATION_SOURCE);
	}

	public TypeOfPayment getTypeOfPayment()
	{
		return TypeOfPayment.NOT_PAYMENT_OPEATION;
	}

	public Class<? extends GateDocument> getType()
	{
		return com.rssl.phizic.gate.claims.AccountChangeInterestDestinationClaim.class;
	}

	public String getOperUId()
	{
		return getNullSaveAttributeStringValue(OPER_UID);
	}

	/**
	 * ��������� �������������� ��������
	 * @param operUid
	 */
	public void setOperUId(String operUid)
	{
		setNullSaveAttributeStringValue(OPER_UID, operUid);
	}

	public Calendar getOperTime()
	{
		return getNullSaveAttributeCalendarValue(OPER_TIME);
	}

	/**
	 * ��������� ���� � ������� �������� ���������
	 * @param operTime
	 */
	public void setOperTime(Calendar operTime)
	{
		setNullSaveAttributeCalendarValue(OPER_TIME, operTime);
	}

	public Set<ExternalResourceLink> getLinks() throws DocumentException
	{
		try
		{
			BusinessDocumentOwner documentOwner = getOwner();
			if (documentOwner.isGuest())
				throw new UnsupportedOperationException("�������� � �������� ������ �� ��������������");
			ExternalResourceLink link = externalResourceService.findLinkByNumber(documentOwner.getLogin(), ResourceType.ACCOUNT, getChangePercentDestinationAccountNumber());
			// ������ ����� ���� ������� � �� �����������
			if (link == null)
				return Collections.emptySet();

			return Collections.singleton(link);
		}
		catch (BusinessException e)
		{
			throw new DocumentException("���� ��� ��������� ����� �� ���������� ����", e);
		}
	}

	public Money getExactAmount()
	{
		return null;
	}
}
