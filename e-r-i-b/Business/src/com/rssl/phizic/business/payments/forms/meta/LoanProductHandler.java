package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.BusinessDocumentService;
import com.rssl.phizic.business.documents.payments.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Mescheryakova
 * @ created 07.12.2011
 * @ $Author$
 * @ $Revision$
 *
 * ��� ������ �� ����� ���������� ���������, ��� �� ���������� ���������� ��������/����� ��� ��� ������, ������������ �� ������������
 * ��� ��������������� �������� / ��������� ���� ��������� ���������� ������, ������������ �� ������������
 */

public class LoanProductHandler extends BusinessDocumentHandlerBase
{
	private static final Map<Class, String> messages = new HashMap<Class, String>();
	private static final BusinessDocumentService service = new BusinessDocumentService();

	public static final String PREAPPROVED_LOAN_CARD_TEXT = "�� �� ������ �������� ��������� � ���� ������ �� ������ �������������� ��������� �����, ���� ���������� ������ �� ���������� � �����.";

	static
	{
		messages.put(LoanProductClaim.class, "�� �� ������ �������� ��������� � ���� ������ �� ������ ������� �� ������� ��������, ���� ���������� ������ �� ���������� � �����.");
		messages.put(LoanCardProductClaim.class, "�� �� ������ �������� ��������� � ���� ������ �� ������ ������ ��������� �����, ���� ���������� ������ �� ���������� � �����.");
		messages.put(LoanOfferClaim.class, "�� �� ������ �������� ��������� � ���� ������ �� ������ ��������������� �������, ���� ���������� ������ �� ���������� � �����.");
		messages.put(LoanCardOfferClaim.class, PREAPPROVED_LOAN_CARD_TEXT);
		messages.put(PreapprovedLoanCardClaim.class, PREAPPROVED_LOAN_CARD_TEXT);
	}

	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (!(document instanceof LoanClaimBase))
			throw new DocumentException("�������� ��� ������� id=" + ((BusinessDocument) document).getId() + " (��������� LoanClaimBase)");

		try
		{
			if(service.isExistDispatchedClaim((LoanClaimBase)document))
				throw new DocumentLogicException(messages.get(document.getClass()));
		}
		catch(BusinessException e)
		{
			throw new DocumentException(e);
		}
	}
}

