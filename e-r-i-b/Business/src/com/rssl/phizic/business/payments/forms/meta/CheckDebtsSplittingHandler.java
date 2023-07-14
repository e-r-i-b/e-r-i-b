package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.RedirectDocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.documents.payments.LoanPayment;
import com.rssl.phizic.business.extendedattributes.ExtendedAttribute;
import com.rssl.phizic.business.resources.external.LoanLink;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.loans.DateDebtItem;
import com.rssl.phizic.gate.loans.LoansService;
import com.rssl.phizic.gate.loans.PenaltyDateDebtItemType;
import com.rssl.phizic.gate.loans.ScheduleItem;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Map;

/**
 * �������� �������� ����� ������� ��������� �������
 * ���� �������� ���������� - ������ ������� ������ ��������������� ������.
 * @author gladishev
 * @ created 06.10.2010
 * @ $Author$
 * @ $Revision$
 */
public class CheckDebtsSplittingHandler extends BusinessDocumentHandlerBase
{
	private static final LoansService loanService = GateSingleton.getFactory().service(com.rssl.phizic.gate.loans.LoansService.class);
	private static final String WARNING_MESSAGE = "�������� ������� ����������! ����������, �������������� ������.";

	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (!(document instanceof LoanPayment))
		{
			throw new DocumentException("�������� ��� �������. ��������� LoanPayment)");
		}

		LoanPayment payment = (LoanPayment) document;
		ScheduleItem item = getNextScheduleItem(payment);

		//������� ��������� ����� ����� ������� � ����� ����� � ����� ��������
		checkAttribute(payment.getAttribute(LoanPayment.TOTAL_PAYMENT_ATTRIBUTE_NAME), item.getTotalPaymentAmount());

		//��������� ������ PrincipalAmount & InterestsAmount
		checkAttribute(payment.getAttribute(LoanPayment.PRINCIPAL_AMOUNT_ATTRIBUTE_NAME), item.getPrincipalAmount());
		checkAttribute(payment.getAttribute(LoanPayment.INTERESTS_AMOUNT_ATTRIBUTE_NAME), item.getInterestsAmount());

		//��������� ��������� �������������
		Map<PenaltyDateDebtItemType, DateDebtItem> newDebtsMap = item.getPenaltyDateDebtItemMap();
		for (PenaltyDateDebtItemType itemType : PenaltyDateDebtItemType.values())
		{
			ExtendedAttribute attribute = payment.getAttribute(itemType.name());
			DateDebtItem newItem = newDebtsMap.get(itemType);

			if ((attribute == null) && (newItem == null))
				continue;
			
			if ((attribute == null)^(newItem == null))
				throw new RedirectDocumentLogicException(WARNING_MESSAGE);

			if (((BigDecimal)attribute.getValue()).compareTo(newItem.getAmount().getDecimal())!=0)
				throw new RedirectDocumentLogicException(WARNING_MESSAGE);
		}


		//�������� ������ ������� - ������������� ����� id �������� �������
		payment.setIdSpacing(item.getIdSpacing());
		//� ���� �������� ��� �������� � ��
		Calendar currentDate = new GregorianCalendar(); //������������� ������� ����
		payment.setSpacingDate(currentDate);
	}

	/**
	 * ���������� ��������� ������ ��� �������
	 * @param payment - ������ �� �������	 
	 * @return ScheduleItem
	 */
	private ScheduleItem getNextScheduleItem(LoanPayment payment) throws DocumentException, DocumentLogicException
	{
		try
		{			
			LoanLink link = payment.getLoanLink();
			return loanService.getNextScheduleItem(link.getLoan(),payment.getChargeOffAmount());
		}
		catch (GateException ge)
		{
			throw new DocumentException(ge);
		}
		catch (GateLogicException gle)
		{
			throw new DocumentLogicException(gle);
		}
	}

	/**
	 * �������� �� ��������� ����� � �������� � ������ ��������� amount
	 * @param atribute - �������
	 * @param amount - �����
	 * @exception RedirectDocumentLogicException - ���� ����� �� �����
	 */
	private void checkAttribute(ExtendedAttribute atribute, Money amount) throws RedirectDocumentLogicException
	{
		if ((amount==null)^(atribute==null))
		{
			throw new RedirectDocumentLogicException(WARNING_MESSAGE);
		}
		else if ((amount!=null)&&(atribute!=null))
		{
			if (((BigDecimal)atribute.getValue()).compareTo(amount.getDecimal())!=0)
				throw new RedirectDocumentLogicException(WARNING_MESSAGE);
		}
	}
}
