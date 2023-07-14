package com.rssl.phizic.web.configure.exceptions;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.validators.MultiFieldsValidatorBase;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.mobileDevices.MobilePlatform;
import com.rssl.phizic.business.mobileDevices.MobilePlatformService;

import java.util.Map;

/**
 * ��������� ����� �������������� �������� mAPI � ������� ��������.
 * @author Jatsky
 * @ created 19.08.13
 * @ $Author$
 * @ $Revision$
 */

public class MobilePlatformFormValidator extends MultiFieldsValidatorBase
{
	private MobilePlatformService service = new MobilePlatformService();

	public boolean validate(Map values) throws TemporalDocumentException
	{
		Long id = (Long) values.get("id");
		String platformId = values.get("platformId").toString();
		String platformName = values.get("platformName").toString();

		boolean validPlatformId = false;
		boolean validPlatformName = false;

		try
		{
			MobilePlatform platform = service.findByPlatformId(platformId);
			if (platform == null || platform.getId().equals(id))
				validPlatformId = true;
		}
		catch (BusinessException e)
		{
			throw new TemporalDocumentException("������ ��������� ��������� ��������� �� �� " + platformId, e);
		}

		try
		{
			MobilePlatform platform = service.findByPlatformName(platformName);
			if (platform == null || platform.getId().equals(id))
				validPlatformName = true;
		}
		catch (BusinessException e)
		{
			throw new TemporalDocumentException("������ ��������� ��������� ��������� �� ����� " + platformName, e);
		}

		boolean result = validPlatformId && validPlatformName;
		if (!result)
		{
			this.setMessage("������ � ����� ��������������� ��� ��������� ��� ������������. ����������, ������� ������ ��������.");
		}

		return result;
	}
}
