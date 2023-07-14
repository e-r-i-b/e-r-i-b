package com.rssl.phizic.web.client.loans;

import com.rssl.phizic.business.documents.payments.ExtendedLoanClaim;
import com.rssl.phizic.web.common.ListFormBase;

/**
 * @author usachev
 * @ created 15.05.15
 * @ $Author$
 * @ $Revision$
 * ����� ��� ��������� ������ ����������� ��������� ������
 */
public class AsyncLoadClaimForm extends ListFormBase<ExtendedLoanClaim>
{
	private boolean hasErrors;

	/**
	 * ���� ������
	 * @return ��, ���� ���� ������. ���, � ��������� ������.
	 */
	public boolean getHasErrors()
	{
		return hasErrors;
	}

	/**
	 * ���������� ����, ��� ��������� �����-�� ������
	 * @param hasErrors ����
	 */
	public void setHasErrors(boolean hasErrors)
	{
		this.hasErrors = hasErrors;
	}
}
