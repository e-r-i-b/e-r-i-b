package com.rssl.phizic.business.documents.payments;

import com.rssl.common.forms.DocumentLogicException;
import com.rssl.ikfl.crediting.ClaimNumberGenerator;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.AbstractPaymentDocument;
import com.rssl.phizic.business.resources.external.ExternalResourceLink;
import com.rssl.common.forms.DocumentException;
import com.rssl.phizic.common.types.MessageCollector;
import org.w3c.dom.Document;

import java.util.Set;
import java.util.HashSet;

/**
 * @author Mescheryakova
 * @ created 30.05.2011
 * @ $Author$
 * @ $Revision$
 */

public abstract class LoanClaimBase extends AbstractPaymentDocument
{
	private static final String LOAN_ATTRIBUTE_NAME = "loan";    // id ����������� ��� id ������� � ������� ������
	private static final  String DURATION_ATTRIBUTE_NAME = "duration";  // ���� �������
	private static final String CHANGE_DATE_ATTRIBUTE_NAME = "changeDate";  // ���� ��������
	private static final String ATTRIBUTE_CLAIM_NUMBER = "claimNumber"; // ����� ������

	public String getLoan()
	{
		return getNullSaveAttributeStringValue(LOAN_ATTRIBUTE_NAME);
	}

	public Long getDuration()
	{
		return Long.valueOf(getNullSaveAttributeStringValue(DURATION_ATTRIBUTE_NAME));
	}

	public Long getChangeDate()
	{
		return Long.valueOf(getNullSaveAttributeStringValue(CHANGE_DATE_ATTRIBUTE_NAME));
	}

	/**
	 * @return ��� ����, �� �������� ������������ ������������ ������
	 */
	public abstract String getIdentityFieldName();

	/**
	 * @return �������� ����, �� �������� ������������ ������������ ������
	 */
	public abstract String getIdentityFieldValue();

	public Set<ExternalResourceLink> getLinks() throws DocumentException
	{
		//������ ������������ �� AbstractPaymentDocument. �� ����� ���� ������ �� ������� �� � ����� �� ��������.
		return new HashSet<ExternalResourceLink>();
	}

	public void initialize(Document document, MessageCollector messageCollector) throws DocumentException, DocumentLogicException
	{
		super.initialize(document, messageCollector);
		setClaimNumber();
	}

	private void setClaimNumber() throws DocumentException
	{
		ClaimNumberGenerator generator = new ClaimNumberGenerator();
		try
		{
			setOperationUID(generator.execute());
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}

	public AbstractPaymentDocument createCopy(Class<? extends BusinessDocument> instanceClass) throws DocumentException, DocumentLogicException
	{
		LoanClaimBase claim = (LoanClaimBase) super.createCopy(instanceClass);
		setClaimNumber();
		return claim;
	}

	public void setClaimNumber(String claimNumber)
	{
		setNullSaveAttributeStringValue(ATTRIBUTE_CLAIM_NUMBER, claimNumber);
	}

	public String getClaimNumber()
	{
		return getNullSaveAttributeStringValue(ATTRIBUTE_CLAIM_NUMBER);
	}
}
