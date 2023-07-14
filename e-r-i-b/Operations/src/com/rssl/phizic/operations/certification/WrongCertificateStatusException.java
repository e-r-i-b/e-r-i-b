package com.rssl.phizic.operations.certification;

import com.rssl.phizic.business.BusinessLogicException;

/**
 * @author Omeliyanchuk
 * @ created 02.04.2007
 * @ $Author$
 * @ $Revision$
 */

public class WrongCertificateStatusException extends BusinessLogicException
{
	WrongCertificateStatusException()
	{
		super("Неверный статус сертификата");
	}
}
