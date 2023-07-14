package com.rssl.phizic.business.documents;

/**
 * @author osminin
 * @ created 07.02.2013
 * @ $Author$
 * @ $Revision$
 *
 * Валидатор на проверку, возможности совершения платежа внешнему получателю со счета физю лица.
 */
public class AllowedExternalPhizAccountPaymentValidator extends AllowedExternalAccountPaymentValidatorBase
{
	public AllowedExternalPhizAccountPaymentValidator()
	{
		super();
	}

	protected boolean isExternalEccountPaymentAllowed()
	{
		return DocumentHelper.isExternalPhizAccountPaymentsAllowed();
	}
}
