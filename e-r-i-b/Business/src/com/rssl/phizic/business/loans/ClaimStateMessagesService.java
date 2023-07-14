package com.rssl.phizic.business.loans;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.BusinessDocumentService;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.config.*;
import com.rssl.phizic.utils.DateHelper;

import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

/**
 * Сервис для получения сообщений о статусах кредитных заявок
 * @author gladishev
 * @ created 18.02.2008
 * @ $Author$
 * @ $Revision$
 */

public class ClaimStateMessagesService extends Config
{
	private static final BusinessDocumentService businessDocumentService = new BusinessDocumentService();

	private final String STATE_PREFIX = "claim.state";
	private final String MESSAGE_PREFIX = "message";
	private final String END_DATE = "{endDate}"; //заменяется на дату, до наступления которой клиент должен прийти в банк для оформления кредита.
	private final String DAYS_COUNT = "days-count"; //количество дней, в течение которых клиент должен прийти в банк для оформления кредита.

	public ClaimStateMessagesService(PropertyReader reader)
	{
		super(reader);
	}

	public String getMessage(Long claimId) throws BusinessException
	{
		BusinessDocument document = businessDocumentService.findById(claimId);

		String message = getProperty(STATE_PREFIX + "." + document.getState().getCode() + "." + MESSAGE_PREFIX);

		if (message == null)
			return null;

		if (message.contains(END_DATE))
		{
			Calendar admissionDate = document.getAdmissionDate();

			if (admissionDate == null)
				throw new BusinessException("Для заявки с id=" + claimId + " не указана дата приема платежа банком.");

			String daysCount = getProperty(DAYS_COUNT);
			Date date = DateHelper.add(admissionDate.getTime(), 0, 0, Integer.parseInt(daysCount));

			message = message.replace(END_DATE, String.format("%1$te.%1$tm.%1$tY", date));
		}

		return message;
	}

	/**
	 * @param prefix префикс (подстрока, с которой начинаются именв свойств)
	 * @return список свойств
	 */
	public Properties getProperties(String prefix)
	{
		return super.getProperties(prefix);
	}

	@Override
	protected void doRefresh() throws ConfigurationException
	{
	}
}
