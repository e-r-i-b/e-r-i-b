package com.rssl.phizicgate.sbrf.ws.mock;

/**
 * @author Omeliyanchuk
 * @ created 06.08.2009
 * @ $Author$
 * @ $Revision$
 */

public class AgreementCancelationMandatoryWithOutQHandler extends AgreementCancelationQHandler
{
	protected String getMessageName()
	{
		return "àgreementCancellationWithoutCharge_q";
	}
}
