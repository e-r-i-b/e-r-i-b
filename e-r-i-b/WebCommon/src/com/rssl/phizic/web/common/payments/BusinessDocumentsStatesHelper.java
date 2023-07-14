package com.rssl.phizic.web.common.payments;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.DefaultClaim;
import com.rssl.phizic.business.documents.DepositOpeningClaim;
import com.rssl.phizic.business.documents.LoanClaim;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.messages.MessageConfig;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.web.common.messages.MessageConfigRouter;

/**
 * @author gladishev
 * @ created 12.12.2011
 * @ $Author$
 * @ $Revision$
 */
public class BusinessDocumentsStatesHelper
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private static final MessageConfig messageConfig = MessageConfigRouter.getInstance();
	private static final String PREFIX = "state.";

	/**
	 * Преобразует строку со списком статус-кодов платежа в понятный для пользователя вид
	 * @param bundle бандл поиска описания статуса
	 * @param doc - документ
	 * @param statesStr список статусов через запятую
	 * @return список описаний статусов
	 */
	public static String parseStates(String bundle, BusinessDocument doc, String statesStr)
	{
		String[] states = statesStr.split(",");
		StringBuilder resultMesage = new StringBuilder();
		for (String state : states)
		{
			state = state.trim();
			try
			{
				String additPrefix = "";
				if (doc instanceof DefaultClaim)
					additPrefix = "DefaultClaim";
				else if (doc instanceof DepositOpeningClaim)
					additPrefix = "DepositOpeningClaim";
				else if (doc instanceof LoanClaim)
					additPrefix = "LoanClaim";

				String formatState;
				try
				{
					formatState = messageConfig.message(bundle, PREFIX + additPrefix + state);
				}
				catch (ConfigurationException e)
				{
					formatState = messageConfig.message(bundle, PREFIX  + state);
				}

				if (resultMesage.indexOf(formatState) == -1)
				{
					if(resultMesage.length()>0)
						resultMesage.append(", ");

					resultMesage.append(formatState);
				}
			}
			catch (ConfigurationException e)
			{
				log.error("Неизвестный статус документа " + state, e);
			}
		}
		return resultMesage.toString();
	}
}
