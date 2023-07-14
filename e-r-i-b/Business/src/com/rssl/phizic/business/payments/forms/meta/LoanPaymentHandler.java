package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.payments.LoanPayment;
import com.rssl.phizic.business.resources.external.LoanLink;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.loans.LoansService;
import com.rssl.phizic.gate.loans.ScheduleItem;

import java.math.BigDecimal;

/**
 * ������� ��� ��������� �������� ��� ������� �� ������� �������
 * � ��������� � ������
 * @author gladishev
 * @ created 20.09.2010
 * @ $Author$
 * @ $Revision$
 */
public class LoanPaymentHandler extends BusinessDocumentHandlerBase
{
	private static final String ANNUITY_ERROR_TEXT = "�� �� ������ �������� ������������� �� ������������� �������.";

	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (!(document instanceof LoanPayment))
			throw new DocumentException("�������� ��� �������. ��������� LoanPayment");

		LoanPayment payment = (LoanPayment) document;

		try
		{
			PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
			LoanLink link = personData.findLoan(payment.getAccountNumber());

			if (link.getLoan().getIsAnnuity())
				throw new DocumentLogicException(ANNUITY_ERROR_TEXT);

			LoansService loanService = GateSingleton.getFactory().service(com.rssl.phizic.gate.loans.LoansService.class);
			ScheduleItem item = loanService.getNextScheduleItem(link.getLoan(), payment.getChargeOffAmount());

			//���� ��������� 0-�� ����� ����� �������, �� �� ���� ���������� ������ ������
			if(item.getTotalPaymentAmount().getDecimal().compareTo(BigDecimal.ZERO) == 0)
					throw new DocumentLogicException("����� ����� ������� �� ����� ���� ����� 0");
			//��������� ���������� �������� ������� ��� ����������� �������
			payment.addAmountSplitting(item);
		}
		catch (GateException ge)
		{
			throw new DocumentException(ge);
		}
		catch (GateLogicException gle)
		{
			throw new DocumentLogicException(gle);
		}
		catch (BusinessException be)
		{
			throw new DocumentException(be);
		}
	}
}
