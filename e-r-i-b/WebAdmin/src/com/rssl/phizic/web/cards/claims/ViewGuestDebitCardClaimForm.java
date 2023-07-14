package com.rssl.phizic.web.cards.claims;

import com.rssl.phizic.gate.claims.sbnkd.impl.IssueCardDocumentImpl;
import com.rssl.phizic.web.common.EditFormBase;

/**
 * ����� ��� ��������� ������ ����� �� ��������� �����
 * User: miklyaev
 * @ $Author$
 * @ $Revision$
 */
public class ViewGuestDebitCardClaimForm extends EditFormBase
{
	private IssueCardDocumentImpl claim;

	/**
	 * @return ������ �����
	 */
	public IssueCardDocumentImpl getClaim()
	{
		return claim;
	}

	/**
	 * ������ ������ �����
	 * @param claim - ������
	 */
	public void setClaim(IssueCardDocumentImpl claim)
	{
		this.claim = claim;
	}
}
