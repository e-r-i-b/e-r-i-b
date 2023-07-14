package com.rssl.phizic.web.skins;

import com.rssl.common.forms.validators.FieldValidatorBase;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.TemporalDocumentException;

/**
 * ��������� ���������� �� ����, ���� �� �� �������, ���� ����������� �������� ���������� true
 * � ����� ������ ������ false.
 * ���� true � ������� ������������ 'http://' � ����.
 * ��� ���������� �������� ������� �������������� ��� �� ��������.
 * @author egorova
 * @ created 19.03.2009
 * @ $Author$
 * @ $Revision$
 */
public class SkinUrlValidator  extends FieldValidatorBase
{
	private static final String ABSOLUTE_URL_PATTERN = ".*\\.\\w{2,3}.*";
	private static final String PROTOCOL_PATTERN = "\\b(http|ftp):\\/\\/.*";

	public boolean validate(String value) throws TemporalDocumentException
	{
		RegexpFieldValidator urlIsAbsolute = new RegexpFieldValidator(ABSOLUTE_URL_PATTERN);
		RegexpFieldValidator containsProtocol = new RegexpFieldValidator(PROTOCOL_PATTERN);
		return urlIsAbsolute.validate(value) && !containsProtocol.validate(value);
	}
}
