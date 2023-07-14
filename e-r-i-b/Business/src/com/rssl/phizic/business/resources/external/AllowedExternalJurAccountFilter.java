package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.business.documents.DocumentHelper;

/**
 * @author osminin
 * @ created 07.02.2013
 * @ $Author$
 * @ $Revision$
 *
 * ������ ����������� �������� �� ������ ��� ��.��� � ������ �������������.
 */
public class AllowedExternalJurAccountFilter implements AccountFilter
{

	public boolean accept(AccountLink accountLink)
	{
		return DocumentHelper.isExternalJurAccountPaymentsAllowed();
	}
}
