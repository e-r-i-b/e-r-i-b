package com.rssl.phizic.business.documents;

import com.rssl.common.forms.DocumentException;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.payments.BusinessDocumentOwner;
import com.rssl.phizic.business.resources.external.DepoAccountLink;
import com.rssl.phizic.business.resources.external.ExternalResourceService;
import com.rssl.phizic.business.resources.external.ExternalResourceLink;
import com.rssl.phizic.utils.StringHelper;

import java.util.Set;

/**
 * @author lukina
 * @ created 06.10.2010
 * @ $Author$
 * @ $Revision$
 */

public abstract class AbstractDepoAccountClaim extends GateExecutableDocument
{
	public static final ExternalResourceService externalResourceService = new ExternalResourceService();

	public static final String DEPO_ACCOUNT_ATTRIBUTE_NAME = "depo-account";    //����� ����� ����
	public static final String DEPO_EXTERNAL_ID_ATTRIBUTE_NAME = "depo-external-id";  //������� id ����� ����
	public static final String DEPO_ID_ATTRIBUTE_NAME = "depo-id";  //id ����� ����
	public static final String DEPOSITOR_ATTRIBUTE_NAME = "depositor";  //��������, �������� ����� ����

	/**
	 *  ������� id ����� ����
	 * @return externalId
	 */
	public String getDepoExternalId()
	{
		return getNullSaveAttributeStringValue(DEPO_EXTERNAL_ID_ATTRIBUTE_NAME);
	}

	/**
	 *  id ����� ����
	 * @return depoId
	 */
	public String getDepoId()
	{
		return getNullSaveAttributeStringValue(DEPO_ID_ATTRIBUTE_NAME);
	}

	/**
	 *  ����� ����� ����
	 * @return accountNumber
	 */
	public String getDepoAccountNumber()
	{
		return getNullSaveAttributeStringValue(DEPO_ACCOUNT_ATTRIBUTE_NAME);
	}

	/**
	 *  ��������
	 * @return depositor
	 */
	public String getDepositor()
	{
		return getNullSaveAttributeStringValue(DEPOSITOR_ATTRIBUTE_NAME);
	}

	/**
	 * ������� ������ �� ���� ����
	 * ��������: ������ ������������ �� �������� ��������� �������,
	 * �.�. ����� ������������� ��� ������ ���������� (� ������ ������ ������������ null)
	 * @return ������ ��� null, ���� ����-��-�������� �������� ����� ���� ����� ��������� �������� �� ������
	 */
	public DepoAccountLink getDepoLink() throws DocumentException
	{
		String number = getDepoAccountNumber();
		if (StringHelper.isEmpty(number))
			return null;

		try
		{
			BusinessDocumentOwner documentOwner = getOwner();
			if (documentOwner.isGuest())
				throw new UnsupportedOperationException("�������� � �������� ������ �� ��������������");
			return externalResourceService.findLinkByNumber(documentOwner.getLogin(), ResourceType.DEPO_ACCOUNT, number);
		}
		catch (BusinessException e)
		{
			throw new DocumentException("���� ��� ��������� �����-��-�������� ����� ����", e);
		}
	}

	public Set<ExternalResourceLink> getLinks() throws DocumentException
	{
		Set<ExternalResourceLink> links = super.getLinks();
		DepoAccountLink depoLink = getDepoLink();
		if (depoLink != null)
		{
			links.add(depoLink);
		}
		return links;
	}
}
