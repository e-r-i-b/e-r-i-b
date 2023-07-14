package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.AccountOpeningClaim;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.utils.ExternalSystemGateService;
import com.rssl.phizicgate.manager.services.IDHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @author  Balovtsev
 * @created 23.09.14.
 */
public class AccountOpeningDateReplaceHandler extends AccountOpeningDateHandler
{
	@Override
	public void process(BusinessDocument document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (!(document instanceof AccountOpeningClaim))
		{
			throw new DocumentException(String.format(INCORRECT_DOCUMENT_TYPE_MESSAGE, document.getId(), "AccountOpeningClaim"));
		}

		AccountOpeningClaim openingClaim = (AccountOpeningClaim) document;
		Calendar            openingDate  = openingClaim.getOpeningDate();

		if (isOpeningDateEQCurrent(openingDate) || !checkOpeningDateGECurrent(openingDate))
		{
			Calendar endOfTechnoBreak = getEndOfTechnoBreak(openingClaim);

			if (endOfTechnoBreak != null)
			{
				String cause = openingClaim.isNeedInitialFee() ? NOT_WORK_TIME_MESSAGE: NOT_WORK_TIME_MESSAGE_OPENING_ONLY;

				recalculateDates   (openingDate, endOfTechnoBreak);
				updateDocumentDates(openingDate, openingClaim, String.format(cause, new SimpleDateFormat("dd.MM.yyyy").format(openingDate.getTime())), stateMachineEvent);
			}
		}
	}

	/**
	 *
	 * @param document заявка на открытие вклада
	 *
	 * @return дата окончания технолошического перерыва, если есть активный или null в случае отсутствия такового
	 * @throws DocumentException
	 * @see {@link ExternalSystemGateService#getTechnoBreakToDateWithAllowPayments}
	 */
	protected Calendar getEndOfTechnoBreak(final AccountOpeningClaim document) throws DocumentException
	{
		ExternalSystemGateService externalSystemGateService = GateSingleton.getFactory().service(ExternalSystemGateService.class);

		Calendar openingDate = null;
		try
		{
			String uuid = IDHelper.restoreRouteInfo(document.getDepartment().getSynchKey().toString());
			openingDate = externalSystemGateService.getTechnoBreakToDateWithAllowPayments(uuid);
		}
		catch (GateException e)
		{
			throw new DocumentException(e);
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}

		return openingDate;
	}
}
