package com.rssl.phizic.business.payments.forms.meta.receivers;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.BusinessDocumentService;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.ShortLoanClaim;
import com.rssl.phizic.utils.StringHelper;

/**
 * @author Moshenko
 * @ created 30.01.2014
 * @ $Author$
 * @ $Revision$
 *
 * ���������, ��� �� ���������� ���������� �������� ��� ��� ������, ������������ �� ������������, � ������ �������  ���� ����������� �����������
 */
public class ShortLoanClaimHandler  extends BusinessDocumentHandlerBase
{
	private static final BusinessDocumentService service = new BusinessDocumentService();

	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if(!(document instanceof ShortLoanClaim))
		{
			throw new DocumentException("�������� ��� ������� id=" + ((BusinessDocument) document).getId() + " (��������� ShortLoanClaim)");
		}

		ShortLoanClaim claim = (ShortLoanClaim) document;
		try
		{
			//���� ��������� ���� ���������� �����������
			if (StringHelper.isNotEmpty(claim.getLoanOfferId()))
			{
				//���� ���� ��� ������������ � ����.
				if(service.isExistDispatchedClaim(claim))
					throw new DocumentLogicException("�� �� ������ �������� ��������� � ���� ������ �� ������ ��������������� �������, ���� ���������� ������ �� ���������� � �����.");
			}
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}


}
