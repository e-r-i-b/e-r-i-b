package com.rssl.phizic.business.persons.stoplist;

import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.clients.stoplist.ClientStopListState;
import com.rssl.phizic.gate.clients.stoplist.StopListService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.ClientConfig;
import com.rssl.phizic.utils.StringHelper;

import java.util.Calendar;

/**
 * Проверка по стоп-листу.
 * @author Roshka
 * @ created 15.02. 2007
 * @ $Author$
 * @ $Revision$
 */

public class StopListHelper
{

	private static final String ERROR_CHECKING_RECIPIENT = "Сбой при попытке проверить получателя бизнес-документа по стоп-листу.";
	private static final String CONDITION_ON_SEND_DATA = "Для поиска необходимо наличие одного из следующих атрибутов:\\n\\n 1.\\tФ.И.О.\\n 2.\\tДата рождения\\n 3.\\tСерия или номер документа";
	protected static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	/**
	 *
	 * @param firstName имя
	 * @param secondName фамилия
	 * @param fatherName отчество
	 * @param docSeries серия документа
	 * @param docNumber номер документа
	 * @param birthday дата рождения
	 * @param inn инн
	 * @return Статус клиента в стоп листе.
	 * @throws StopListLogicException
	 */
	@SuppressWarnings("MethodWithTooManyParameters")
	public static ClientStopListState checkPerson(String firstName, String secondName, String fatherName, String docSeries, String docNumber, Calendar birthday, String inn) throws StopListLogicException
	{
		ClientConfig clientConfig = ConfigFactory.getConfig(ClientConfig.class);
		if (!clientConfig.getNeedStopListCheck())
			return ClientStopListState.trusted;

		if (StringHelper.isEmpty(docNumber) || StringHelper.isEmpty(firstName) || StringHelper.isEmpty(secondName) || StringHelper.isEmpty(fatherName))
		{
			throw new StopListLogicException(CONDITION_ON_SEND_DATA);
		}

		try
		{
			StopListService service = GateSingleton.getFactory().service(StopListService.class);
			return service.check(inn, firstName, fatherName, secondName, docSeries, docNumber, birthday);

		}
		catch (Exception e)
		{
			log.error(StopListHelper.ERROR_CHECKING_RECIPIENT, e);
			// Дальше не перебрасываем, т.к. ошибки стоп-листа не должны мешать проводке документа
			return ClientStopListState.trusted;
		}
	}
}
