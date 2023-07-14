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
 * Хендлер для проверки получателя по стоп листу
 */
public class CheckRecipientOnStopListHandler extends BusinessDocumentHandlerBase
{
	private static final String REFUSE_BAD_RECIPIENT_CLIENT_MESSAGE = "Вы не можете выполнить данную операцию, т.к. получатель содержится в блокирующем списке банка. Вам необходимо обратиться в банк.";
	private static final String REFUSE_BAD_RECIPIENT_EMPLOYEE_MESSAGE = "Данная операция недоступна, т.к. получатель не прошел проверку в АС «Стоп-лист.";
	private static final String PAYMENT_WITH_RECIPIENT_IN_WARNING_STOPLIST = "Выполнение операции разрешена, хотя его получатель находится в предупреждающем стоп-листе.";
	private static final String PAYMENT_WITH_RECIPIENT_IN_LOCK_STOPLIST = "Выполнение операции отклонено, т.к. его получатель находится в блокирующем стоп-листе.";

	public void process(StateObject stateObject, StateMachineEvent stateMachineEvent) throws DocumentLogicException, DocumentException
	{
		if (!(stateObject instanceof RurPayment))
		{
			throw new DocumentException("Неверный тип платежа. Ожидается RurPayment");
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
	 * Проверка, содержится ли человек в стоп-листе
	 * @param doc Платёж, в рамках которого нужно производить проверку.
	 * @return Да, если проведение документа разрещено. Нет, в противном случае.
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
					// Разрешаем операцию
					return true;
				case shady:
					// Разрешаем операцию (BUG026708)
					log.info(PAYMENT_WITH_RECIPIENT_IN_WARNING_STOPLIST + "BUSINESS_DOCUMENT.ID = " + doc.getId());
					return true;
				case blocked:
					// Запрещаем операцию
					log.info(PAYMENT_WITH_RECIPIENT_IN_LOCK_STOPLIST + "BUSINESS_DOCUMENT.ID = " + doc.getId());
					return false;
				default:
					throw new IllegalStateException("Неизвестный статус клиента в стоп-листе: " + state);
			}
		}
		catch (StopListLogicException e)
		{
			log.error("Ошибка проверки по стоп-листу", e);
			// Дальше не перебрасываем, т.к. ошибки стоп-листа не должны мешать проводке документа
			return true;
		}
	}
}
