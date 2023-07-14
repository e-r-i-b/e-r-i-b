package com.rssl.phizic.business.payments;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.documents.payments.RurPayment;
import com.rssl.phizic.business.persons.stoplist.StopListHelper;
import com.rssl.phizic.business.persons.stoplist.StopListLogicException;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.clients.stoplist.ClientStopListState;
import com.rssl.phizic.gate.clients.stoplist.StopListService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.utils.StringHelper;

import java.util.Calendar;

/**
 * @author usachev
 * @ created 08.07.15
 * @ $Author$
 * @ $Revision$
 * ������� ��� �������� ���������� �� ���� �����
 */
public class CheckRecipientOnStopListHandler extends BusinessDocumentHandlerBase
{
	private static final String REFUSE_BAD_RECIPIENT_CLIENT_MESSAGE = "�� �� ������ ��������� ������ ��������, �.�. ���������� ���������� � ����������� ������ �����. ��� ���������� ���������� � ����.";
	private static final String REFUSE_BAD_RECIPIENT_EMPLOYEE_MESSAGE = "������ �������� ����������, �.�. ���������� �� ������ �������� � �� �����-����.";
	private static final String PAYMENT_WITH_RECIPIENT_IN_WARNING_STOPLIST = "���������� �������� ���������, ���� ��� ���������� ��������� � ��������������� ����-�����.";
	private static final String PAYMENT_WITH_RECIPIENT_IN_LOCK_STOPLIST = "���������� �������� ���������, �.�. ��� ���������� ��������� � ����������� ����-�����.";

	public void process(StateObject stateObject, StateMachineEvent stateMachineEvent) throws DocumentLogicException, DocumentException
	{
		if (!(stateObject instanceof RurPayment))
		{
			throw new DocumentException("�������� ��� �������. ��������� RurPayment");
		}

		if (!isClientLock((RurPayment) stateObject))
		{
			ApplicationConfig applicationConfig = ApplicationConfig.getIt();
			if (applicationConfig.getLoginContextApplication() == Application.PhizIA)
			{
				throw new DocumentLogicException(REFUSE_BAD_RECIPIENT_EMPLOYEE_MESSAGE);
			}
			else
			{
				throw new DocumentLogicException(REFUSE_BAD_RECIPIENT_CLIENT_MESSAGE);
			}
		}
	}

	/**
	 * ��������, ���������� �� ������� � ����-�����
	 * @param doc �����, � ������ �������� ����� ����������� ��������.
	 * @return ��, ���� ���������� ��������� ���������. ���, � ��������� ������.
	 */
	private boolean isClientLock(RurPayment doc)
	{
		try
		{
			String firstName = doc.getReceiverFirstName();
			String secondName = doc.getReceiverSurName();
			String fatherName = doc.getReceiverPatrName();
			String docSeries = doc.getReceiverDocSeries();
			String docNumber = doc.getReceiverDocNumber();
			Calendar birthday = doc.getReceiverBirthday();

			ClientStopListState state = StopListHelper.checkPerson(firstName, secondName, fatherName, docSeries, docNumber, birthday, null);

			switch (state)
			{
				case trusted:
					// ��������� ��������
					return true;
				case shady:
					// ��������� �������� (BUG026708)
					log.info(PAYMENT_WITH_RECIPIENT_IN_WARNING_STOPLIST + "BUSINESS_DOCUMENT.ID = " + doc.getId());
					return true;
				case blocked:
					// ��������� ��������
					log.info(PAYMENT_WITH_RECIPIENT_IN_LOCK_STOPLIST + "BUSINESS_DOCUMENT.ID = " + doc.getId());
					return false;
				default:
					throw new IllegalStateException("����������� ������ ������� � ����-�����: " + state);
			}
		}
		catch (StopListLogicException e)
		{
			log.error("������ �������� �� ����-�����", e);
			// ������ �� �������������, �.�. ������ ����-����� �� ������ ������ �������� ���������
			return true;
		}
	}
}
