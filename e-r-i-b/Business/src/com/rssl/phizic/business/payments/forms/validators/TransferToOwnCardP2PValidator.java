package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.validators.FieldValidatorBase;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.ResourceType;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.ExternalResourceService;

/**
 * ��������� �������� �������� P2P ������������ �������� ����, ��������� ���� ����� ����������
 *
 * @author khudyakov
 * @ created 16.11.14
 * @ $Author$
 * @ $Revision$
 */
public class TransferToOwnCardP2PValidator extends FieldValidatorBase
{
	public static final String WARNING_MESSAGE_PARAMETER_NAME      = "�� ��������� ������� ���������� ��� �������� ������� ����� ������ �������. ����������, ��������� � �������� ����������� � �������� ������� ��� ���� �����.";

	private static final ExternalResourceService externalResourceService = new ExternalResourceService();


	public boolean validate(String value) throws TemporalDocumentException
	{
		return validate(value, PersonHelper.getContextPerson().getLogin());
	}

	/**
	 * �������� ����� �� �������������� �������
	 * @param value ����� �����
	 * @param login ����� �������
	 * @return true - �� �����������
	 * @throws TemporalDocumentException
	 */
	public boolean validate(String value, Login login) throws TemporalDocumentException
	{
		try
		{
			CardLink cardLink = externalResourceService.findLinkByNumber(login, ResourceType.CARD, value);
			if (cardLink != null)
			{
				setMessage(WARNING_MESSAGE_PARAMETER_NAME);
				return false;
			}
		}
		catch (BusinessException e)
		{
			throw new TemporalDocumentException(e);
		}
		return true;
	}
}
