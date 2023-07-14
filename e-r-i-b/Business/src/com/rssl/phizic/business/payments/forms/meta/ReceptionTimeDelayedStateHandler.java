package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.GateExecutableDocument;
import com.rssl.phizic.business.receptiontimes.ReceptionTime;
import com.rssl.phizic.business.receptiontimes.ReceptionTimeService;

/**
 * Используется для он-лайн платежей, которые не могут быть выполнены в не операционное время
 *  и не могут быть отложены(отличие от DelayedStateHandler в том, что текстовка содержит время, когда может быть совершена операция)
 * @author niculichev
 * @ created 28.06.2012
 * @ $Author$
 * @ $Revision$
 */
public class ReceptionTimeDelayedStateHandler extends DelayedStateHandler
{
	private static final String ERROR_MESSAGE = "Вы не можете выполнить операцию во внеоперационное время банка. Пожалуйста, повторите ее в рабочие дни с %s до %s.";
	private static final String TIME_FORMATE = "%1$tH:%1$tM:%1$tS";

	private static ReceptionTimeService receptionTimeService = new ReceptionTimeService();

	protected String getMessage(StateObject stateObject) throws DocumentException, DocumentLogicException
	{
		if(!(stateObject instanceof GateExecutableDocument))
			throw new DocumentException("Ожидался GateExecutableDocument");

		GateExecutableDocument document = (GateExecutableDocument) stateObject;

		try
		{
			ReceptionTime receptionTime = receptionTimeService.getRecepionTime(document.getDepartment(), document.getType());
			//если время приема для документа не задано, используем стандартное сообщение.
			if(receptionTime == null)
				return DEFAULT_ERROR_MESSAGE;

			return String.format(
					ERROR_MESSAGE,
					String.format(TIME_FORMATE, receptionTime.getReceptionTimeStart()),
					String.format(TIME_FORMATE, receptionTime.getReceptionTimeEnd()));

		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}
}
