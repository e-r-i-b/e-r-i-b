package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.ExtendedLoanClaim;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.util.ApplicationUtil;
import com.rssl.phizic.utils.StringHelper;

/**
 * ������� �������� ������ ����������/���������� �����/������ (���� ��� ���� �������) � ���� ���� ���. ����������
 * @author Rtischeva
 * @ created 05.04.14
 * @ $Author$
 * @ $Revision$
 */
public class ExtendedLoanClaimSbrfResourceHandler extends BusinessDocumentHandlerBase
{
	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (!(document instanceof ExtendedLoanClaim))
		{
			throw new DocumentException("�������� ��� ������ id=" + ((BusinessDocument) document).getId() + " (��������� ExtendedLoanClaim)");
		}

		ExtendedLoanClaim loanClaim = (ExtendedLoanClaim) document;
		//��� ��� ���������� ��� �������� ������ �� ������ ��� �����, �� �������� ����� �������� �����. ��������� ��� �� ������.
		if (ApplicationUtil.isAdminApplication() && PersonHelper.isGuest() &&
				StringHelper.isEmpty(loanClaim.getOwnerGuestPhone()))
			loanClaim.setOwnerGuestPhone(loanClaim.getFullMobileNumber());

		if (StringHelper.isNotEmpty(loanClaim.getLoanOfferId()))
			return;

		loanClaim.storeAdditionalResourceData();
	}
}
