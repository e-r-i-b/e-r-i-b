package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.business.documents.DocumentHelper;

/**
 * ������ ����������� �������� �� ������ ��� ���.��� � ������ �������������.
 *
 * @author bogdanov
 * @ created 18.10.2012
 * @ $Author$
 * @ $Revision$
 */

public class AllowedExternalPhizAccountFilter implements AccountFilter
{
	public boolean accept(AccountLink accountLink)
	{
		return DocumentHelper.isExternalPhizAccountPaymentsAllowed();
	}
}
