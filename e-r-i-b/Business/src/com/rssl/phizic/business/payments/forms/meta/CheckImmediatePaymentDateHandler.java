package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.documents.AbstractLongOfferDocument;
import com.rssl.phizic.gate.longoffer.LongOffer;

/**
 * �������� ���� ���������� ������� ��� ����������� ���������
 * (������� ���������� ���� ������ ��������� ������ (�.� ��� ������ ���� ��� ���������),
 * ������� ������������ ��� ������ ����� ������ SetBusinessDocumentDateAction)
 *
 * @author hudyakov
 * @ created 05.10.2010
 * @ $Author$
 * @ $Revision$
 */
public class CheckImmediatePaymentDateHandler extends BusinessDocumentHandlerBase
{
	private static final String WARNING_PERIODIC_MESSAGE_FOR_USER = "�������� ��������: ���� ���������� ������� ����������! ���� �� �������� � ����� �����, �� ������� �� ������ �������������. ���� �� ��������, �� �������������� ���� ������.";
	private static final String WARNING_EVENT_MESSAGE_FOR_USER = "�������� ��������: ���� ������ �������� ����������� ������� %s. ���� �� �������� � �����, �� ������� �� ������ �������������. ���� �� ��������, �� �������������� ������ �� ����������� �������";

	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (!(document instanceof LongOffer))
			throw new DocumentException("�������� ��������� LongOffer (���������� ���������)");

		AbstractLongOfferDocument longOffer = (AbstractLongOfferDocument) document;
		if (!longOffer.isLongOffer())
			return;

		if (longOffer.calcStartDate(longOffer.getAdmissionDate()))
		{
			if (longOffer.isPeriodic())
				throw new DocumentLogicException(WARNING_PERIODIC_MESSAGE_FOR_USER);

			String startDate = String.format("%1$te.%1$tm.%1$tY", longOffer.getStartDate());
			throw new DocumentLogicException(String.format(WARNING_EVENT_MESSAGE_FOR_USER, startDate));
		}
	}
}
