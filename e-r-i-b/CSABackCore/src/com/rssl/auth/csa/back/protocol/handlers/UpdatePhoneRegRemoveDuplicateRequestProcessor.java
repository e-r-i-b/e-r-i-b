package com.rssl.auth.csa.back.protocol.handlers;

import com.rssl.auth.csa.back.integration.erib.ERIBService;
import com.rssl.auth.csa.back.servises.Connector;
import com.rssl.auth.csa.back.servises.ConnectorState;
import com.rssl.auth.csa.back.servises.Profile;
import com.rssl.auth.csa.back.servises.connectors.ERMBConnector;

import java.util.List;

/**
 * @author osminin
 * @ created 30.06.14
 * @ $Author$
 * @ $Revision$
 *
 * Обработчик запроса на обновление телефонов клиента, удаляющий дубликаты
 */
public class UpdatePhoneRegRemoveDuplicateRequestProcessor extends UpdatePhoneRegistrationsRequestProcessor
{
	private static final String REQUEST_TYPE  = "updatePhoneRegRemoveDuplicateRq";
	private static final String RESPONSE_TYPE = "updatePhoneRegRemoveDuplicateRs";

	private static ERIBService eribService = new ERIBService();

	@Override
	protected String getResponceType()
	{
		return RESPONSE_TYPE;
	}

	@Override
	protected String getRequestType()
	{
		return REQUEST_TYPE;
	}

	protected void processDuplicates(List<String> duplicatePhones) throws Exception
	{
		for (String duplicatePhone : duplicatePhones)
		{
			processDuplicate(duplicatePhone);
		}
	}

	private void processDuplicate(String duplicatePhone) throws Exception
	{
		//Получаем дубликат с акутальными данными
		Connector duplicate = Connector.findByPhoneNumber(duplicatePhone);
        //Если дубликат уже удален, ничего не делаем
		if (duplicate == null)
		{
			return;
		}

		Profile profile = duplicate.getProfile();

		// Отправляем запрос на удаление дублката из блока ЕРИБ
		// Если запрос в блок не отработал (ошибка или тайм-аут), полетит исключение и коннектор не будет удалён (см. ниже)
		eribService.removeErmbPhone(profile, duplicate.getPhoneNumber());

		//Удаляем дубликат из БД ЦСА
		duplicate.delete();

		if (ConnectorState.ACTIVE == duplicate.getState())
		{
			//Если дубликат был активным, делаем активным любой другой коннектор
			ERMBConnector.setActiveFirstConnectorByProfile(profile.getId());
		}
	}
}
