package com.rssl.phizic.operations.certification;

import com.rssl.phizic.business.BusinessLogicException;

/**
 * @author Omeliyanchuk
 * @ created 30.03.2007
 * @ $Author$
 * @ $Revision$
 */

public class InvalidCertificateFileName extends BusinessLogicException
{
	InvalidCertificateFileName()
	{
		super("Имя файла сертификата не соответствует запросу");
	}

}
