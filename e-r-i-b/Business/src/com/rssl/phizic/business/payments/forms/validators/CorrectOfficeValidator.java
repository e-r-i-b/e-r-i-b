package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.validators.MultiFieldsValidatorBase;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.offices.extended.ExtendedCodeImpl;
import com.rssl.phizic.business.documents.ResourceType;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.ExternalResourceService;
import com.rssl.phizic.config.OfficeCodeReplacer;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.utils.StringHelper;

import java.util.Map;

/**
 * @author mescheryakova
 * @ created 24.10.2012
 * @ $Author$
 * @ $Revision$
 * ��������, ������ ��  ��������� ��� � ��� �������� �� ��� ����� ��������
 * ���� ��� ����� �������� �� ���������, �� ��������� �������� ��� ����� ���� �����
 */

public class CorrectOfficeValidator extends MultiFieldsValidatorBase
{
	public static final String OFFICE_BRANCH = "officeBranch"; //���, � ������� ����������� ���
	public static final String FROM_ACCOUNT = "fromAccount"; //����� ��������� ��������
	public static final String FROM_RESOURCE_TYPE = "fromResourceType"; // ��� ��������� ��������
	private static ExternalResourceService resourceService = new ExternalResourceService();
	private final OfficeCodeReplacer officeCodeReplacer = ConfigFactory.getConfig(OfficeCodeReplacer.class);

	public boolean validate(Map values) throws TemporalDocumentException
	{
		String officeBranch = (String) retrieveFieldValue(OFFICE_BRANCH, values);
		String fromAccount = (String) retrieveFieldValue(FROM_ACCOUNT, values);
		String resourceLinkName = (String)  retrieveFieldValue(FROM_RESOURCE_TYPE, values);

		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		Login login = personData.getPerson().getLogin();

		try
		{
			Class<?> clazz = Class.forName(resourceLinkName);
			ExtendedCodeImpl officeCode;
			if (resourceLinkName.equals("com.rssl.phizic.business.resources.external.AccountLink"))
			{
				AccountLink accountLink = resourceService.findLinkByNumber(login, findResourceType(clazz), fromAccount);
				officeCode = new ExtendedCodeImpl(accountLink.getOffice().getCode());
			}
			else if (resourceLinkName.equals("com.rssl.phizic.business.resources.external.CardLink"))
			{
				CardLink cardLink = resourceService.findLinkByNumber(login, findResourceType(clazz), fromAccount);
				officeCode = new ExtendedCodeImpl(cardLink.getGflTB(), cardLink.getGflOSB(), cardLink.getGflVSP());
			}
			else
			{
				officeCode = null;
			} 
			if (officeCode == null || StringHelper.isEmpty(officeBranch))
				return true;

			// �������� ���
			officeCode.setBranch(officeCodeReplacer.replaceCode(officeCode.getRegion(), officeCode.getBranch()));

			String accountBranch = officeCode.getBranch();
			// ���� � ����� �� ��� ��������� ����, �� �� ����� ������ ����� ������ ����
			if (StringHelper.isEmpty(accountBranch))
				return true;

			if (!officeBranch.equals(accountBranch))
				return false;
		}
		catch(BusinessException e)
		{
			throw new TemporalDocumentException(e);
		}
		catch(Exception e)
		{
			throw new TemporalDocumentException(e);
		}
		
		return true;
	}

	private ResourceType findResourceType(Class<?> clazz)
	{
		ResourceType[] resourceLinks = ResourceType.values();

		for (ResourceType resourceLink : resourceLinks)
		{
			if (resourceLink.getResourceLinkClass() != null && resourceLink.getResourceLinkClass().equals(clazz))
				return resourceLink;
		}

		return null;
	}
}
