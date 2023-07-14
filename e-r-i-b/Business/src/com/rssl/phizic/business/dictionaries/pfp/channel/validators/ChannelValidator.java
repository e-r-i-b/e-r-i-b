package com.rssl.phizic.business.dictionaries.pfp.channel.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.validators.FieldValidatorBase;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.pfp.channel.ChannelService;

/**
 * @author akrenev
 * @ created 23.10.13
 * @ $Author$
 * @ $Revision$
 *
 * ��������� �������
 */

public class ChannelValidator extends FieldValidatorBase
{
	private static final ChannelService service = new ChannelService();

	public boolean validate(String value) throws TemporalDocumentException
	{
		try
		{
			if (service.getByName(value) != null)
				return true;

			setMessage("������ ����������� �����.");
			return false;
		}
		catch (BusinessException e)
		{
			throw new TemporalDocumentException(e);
		}
	}
}
