package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.validators.FieldValidatorBase;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.forms.types.UserResourceType;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.business.resources.external.*;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.utils.StringHelper;

import java.util.Map;

/**
 * @author: vagin
 * @ created: 26.03.2012
 * @ $Author$
 * @ $Revision$
 * ��������� ��������, ����� �� ������ ��������/���������� � ���������� ��������� ���������.
 */
public class CheckResourceVisibilityValidator extends FieldValidatorBase
{
	private static final ExternalResourceService resourceService = new ExternalResourceService();
	private static final String NOT_FOUND_MESSAGE = "���������� � ��������� ������� ��������/���������� ����� ����������.";
	private static final String NOT_VISIBLE_MESSAGE = "�� �� ������ ���������� ������ ��������. ��� ������� �������� ��������� ��������� %s � ������ ���� ���������� - ���������� ������������ - ���������� ��������� ���������";
	private ThreadLocal<String> message = new ThreadLocal<String>();

	public boolean validate(String value) throws TemporalDocumentException
	{
		try
		{
			if (StringHelper.isEmpty(value))
			{
				setMessage(NOT_FOUND_MESSAGE);
				return false;
			}

			String[] linkId = value.split(ExternalResourceLinkBase.CODE_DELIMITER);
			if (linkId.length != 2)
			{
				setMessage(NOT_FOUND_MESSAGE);
				return false;
			}

			ApplicationConfig applicationConfig = ApplicationConfig.getIt();
			//��� ���������� ���������� �� ����������� ��������� ���� �������.
			if (applicationConfig.getLoginContextApplication() == Application.PhizIA)
				return true;

			ShowInMobileProductLink link = null;
			if (value.startsWith(UserResourceType.ACCOUNT_PREFIX))
			{
				link = resourceService.findLinkById(AccountLink.class, Long.parseLong(linkId[1]));
				setMessage(String.format(NOT_VISIBLE_MESSAGE, "������ ������"));
			}
			else if (value.startsWith(UserResourceType.CARD_PREFIX))
			{
				link = resourceService.findLinkById(CardLink.class, Long.parseLong(linkId[1]));
				setMessage(String.format(NOT_VISIBLE_MESSAGE, "����� �����"));
			}

			if(link == null)
			{
				setMessage(NOT_FOUND_MESSAGE);
				return false;
			}
			if(applicationConfig.getApplicationInfo().isATM())
				return link.getShowInATM();
			if(applicationConfig.getApplicationInfo().isMobileApi())
				return link.getShowInMobile();
			if(applicationConfig.getApplicationInfo().isSocialApi())
				return link.getShowInSocial();
			return link.getShowInSystem();
		}
		catch (BusinessException ignored)
		{
			return false;
		}
	}

	public String getMessage()
	{
		return message.get();
	}

	public void setMessage(String value)
	{
		message.set(value);
	}
}
