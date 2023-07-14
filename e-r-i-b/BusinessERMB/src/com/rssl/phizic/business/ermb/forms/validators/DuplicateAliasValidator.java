package com.rssl.phizic.business.ermb.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.validators.FieldValidatorBase;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.resources.external.*;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.utils.MaskUtil;

/**
 * ��������� ���-��������������� ���������, ����������� �������������� �� ������������ (����� ��������� �������)
 *
 * @author EgorovaA
 * @ created 22.05.2013
 * @ $Author$
 * @ $Revision$
 */
public class DuplicateAliasValidator  extends FieldValidatorBase
{
	private PersonData personData;
	private ExternalResourceLink link;

	public DuplicateAliasValidator(Long id, Class clazz) throws BusinessException
	{
		personData = PersonContext.getPersonDataProvider().getPersonData();
		link = personData.getExternalResourceLink(clazz, id);
	}

	public boolean validate(String alias) throws TemporalDocumentException
	{
		if( isValueEmpty(alias) )
			return true;

		//���������, �� ��������� �� �������� �������� ����� � �����-���� �� ������������� �����������
		//��� ��������� ������������� �������
		try
		{
			ErmbProductLink duplicateAliasLink = personData.findProductByAlias(alias);
			if (duplicateAliasLink == null || link.equals(duplicateAliasLink))
				return true;

			String errorPrefix = "SMS-������������� ��������� � ��������������� ��� ��������: ";
			if (duplicateAliasLink instanceof CardLink)
			{
				setMessage(errorPrefix + "����� �" + duplicateAliasLink.getName() + "� " + MaskUtil.getCutCardNumber(duplicateAliasLink.getNumber()));
				return false;
			}
			if (duplicateAliasLink instanceof AccountLink)
			{
				setMessage(errorPrefix + "����� �" + duplicateAliasLink.getName() + "� " + duplicateAliasLink.getNumber());
				return false;
			}
			if (duplicateAliasLink instanceof LoanLink)
			{
				setMessage(errorPrefix + "������ �" + duplicateAliasLink.getName() + "� " + duplicateAliasLink.getNumber());
				return false;
			}
			return true;
		}
		catch (BusinessException e)
		{
			throw new TemporalDocumentException(e);
		}
		catch (BusinessLogicException e)
		{
			throw new TemporalDocumentException(e);
		}
	}

}
