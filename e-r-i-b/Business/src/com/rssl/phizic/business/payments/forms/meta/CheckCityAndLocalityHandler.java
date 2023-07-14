package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.documents.payments.ExtendedLoanClaim;

/**
 * @author Gulov
 * @ created 15.09.14
 * @ $Author$
 * @ $Revision$
 */

/**
 * ��������� ���������� ����� �����, ��� ������, ���������� �����, ��� ������.
 * ���� ��� ���� ���� �� ���� ������ �� ���������, �� ������� ��������������� ��������.
 * ��. ������ CHG077728: ������� ���� � ��������� ������.
 */
public class CheckCityAndLocalityHandler extends BusinessDocumentHandlerBase
{
	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentLogicException, DocumentException
	{
		if(!(document instanceof ExtendedLoanClaim))
			throw new DocumentException("��������� ExtendedLoanClaim");
		ExtendedLoanClaim loanClaim = (ExtendedLoanClaim) document;
		loanClaim.checkCityAndLocality();
	}
}
