package com.rssl.phizic.business.payments;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.businessProperties.LoanReceptionTimeHelper;
import com.rssl.phizic.business.documents.payments.LoanPayment;
import com.rssl.phizic.common.types.transmiters.Triplet;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizicgate.manager.ext.sbrf.ESBHelper;

import java.sql.Time;
import java.text.ParseException;
import java.util.Properties;

/** CHG030960: запретить клиентам оплачивать кредит во внеоперационное время
 * @author akrenev
 * @ created 01.09.2011
 * @ $Author$
 * @ $Revision$
 */
public class ValidateLoanReceptionTimeHandler extends BusinessDocumentHandlerBase
{
	// храним идентификаторы кредитных систем и время приема документов (в количестве милисекунд от начала дня)
	private static final String SYSTEM_ID_PREFIX = "urn:sbrfsystems:";

	private boolean isWorkTime(Time fromTime, Time toTime, int timeZone)
	{
		Time currentTime = new Time(DateHelper.getTime(DateHelper.getCurrentTimeWithRawOffset(timeZone)).getTimeInMillis());
		return currentTime.after(fromTime) && currentTime.before(toTime);
	}

	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (!(document instanceof LoanPayment))
		{
			throw new DocumentException("Неверный тип платежа. Ожидается LoanPayment.");
		}
		LoanPayment loanPayment = (LoanPayment) document;
		String loanSystemId = ESBHelper.parseSystemId(loanPayment.getLoanExternalId());
		try
		{
			Properties properties = ConfigFactory.getConfig(PaymentsConfig.class).getLoanReceptionTimes();
			if (properties.isEmpty())
				return;

			for (Object key : properties.keySet())
				if (loanSystemId.equals(SYSTEM_ID_PREFIX + LoanReceptionTimeHelper.getNameLoanSystem((String) key)))
				{
					Triplet<Time, Time, String> time = LoanReceptionTimeHelper.getTimeFromValue((String) properties.get(key));
					if (!isWorkTime(time.getFirst(), time.getSecond(), Integer.parseInt(time.getThird())))
					{
						throw new DocumentLogicException("Вы не можете оплатить кредит во внеоперационное время банка. Пожалуйста, выполните платеж в другое время.");
					}
					return;
				}
		}
		catch (ParseException e)
		{
			throw new DocumentException(e);
		}
	}
}
