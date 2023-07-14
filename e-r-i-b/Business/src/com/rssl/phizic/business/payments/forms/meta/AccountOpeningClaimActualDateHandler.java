package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.documents.AccountOpeningClaim;
import com.rssl.phizic.utils.DateHelper;

import java.util.Calendar;

/** Хендлер для проверки актуальности заявки на открытие вклада
 * @author akrenev
 * @ created 10.01.2012
 * @ $Author$
 * @ $Revision$
 */
// Для заявок на открытие вклада нужно обязательно сравнить дату открытия с текущей.
// Если дата открытия меньше - не отправлять документ, если больше - отказывать, иначе отправить в бэк-офис
public class AccountOpeningClaimActualDateHandler extends BusinessDocumentHandlerBase
{
	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (!(document instanceof AccountOpeningClaim))
			throw new DocumentException("Неверный тип платежа id=" + ((BusinessDocument) document).getId() + " (Ожидается AccountOpeningClaim)");

		AccountOpeningClaim accountOpeningClaim = (AccountOpeningClaim) document;
		Calendar openingDate = DateHelper.startOfDay(accountOpeningClaim.getOpeningDate());
		Calendar currentDate = DateHelper.getCurrentDate();
		// текущая дата больше даты начала срока действия вкладa
		if (currentDate.after(openingDate))
		{
			throw new DocumentLogicException("Срок действия Вашей заявки закончился. Пожалуйста, отправьте в банк новую заявку.");
		}
	}
}
