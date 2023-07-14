package com.rssl.phizic.web.skins;

import com.rssl.common.forms.validators.FieldValidatorBase;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.TemporalDocumentException;

/**
 * Проверяем абсолютный ли путь, если да то смотрим, если отсутствует протокол возвращаем true
 * В любом другом случае false.
 * Если true в экшенах дописывается 'http://' к пути.
 * При построении тестовых страниц использьзкется тот же механизм.
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
