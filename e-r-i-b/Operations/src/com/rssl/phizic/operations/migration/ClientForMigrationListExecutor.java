package com.rssl.phizic.operations.migration;

import com.rssl.phizic.ApplicationAutoRefreshConfig;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.clients.list.ClientForMigration;
import com.rssl.phizic.business.clients.list.ClientInformationService;
import com.rssl.phizic.business.util.AllowedDepartmentsUtil;
import com.rssl.phizic.common.types.client.CreationType;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.dataaccess.DataAccessException;
import com.rssl.phizic.dataaccess.query.CustomListExecutor;
import com.rssl.phizic.dataaccess.query.OrderParameter;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author akrenev
 * @ created 26.09.2014
 * @ $Author$
 * @ $Revision$
 *
 * Класс получения инфы по клиентам для миграции
 */

class ClientForMigrationListExecutor implements CustomListExecutor<ClientForMigration>
{
	private static final ClientInformationService service = new ClientInformationService();

	static final String CLIENT_PARAMETER_NAME            = "client";
	static final String DOCUMENT_PARAMETER_NAME          = "document";
	static final String DEPARTMENT_PARAMETER_NAME        = "department";
	static final String BIRTHDAY_PARAMETER_NAME          = "birthday";
	static final String AGREEMENT_TYPE_PARAMETER_NAME    = "agreementType";
	static final String AGREEMENT_NUMBER_PARAMETER_NAME  = "agreementNumber";

	public List<ClientForMigration> getList(Map<String, Object> parameters, int size, int offset, List<OrderParameter> orderParameters) throws DataAccessException
	{
		try
		{
			String client              = (String) parameters.get(CLIENT_PARAMETER_NAME);
			String document            = (String) parameters.get(DOCUMENT_PARAMETER_NAME);
			String department          = (String) parameters.get(DEPARTMENT_PARAMETER_NAME);
			Date birthday              = (Date) parameters.get(BIRTHDAY_PARAMETER_NAME);
			CreationType agreementType = (CreationType) parameters.get(AGREEMENT_TYPE_PARAMETER_NAME);
			String agreementNumber     = (String) parameters.get(AGREEMENT_NUMBER_PARAMETER_NAME);
			Long nodeId                = ConfigFactory.getConfig(ApplicationAutoRefreshConfig.class).getNodeNumber();

			return service.getTemporaryNodeClientsInformation(client, document, DateHelper.toCalendar(birthday), agreementType, agreementNumber, getTBList(department), nodeId, size, offset);
		}
		catch (BusinessLogicException e)
		{
			throw new DataAccessException("Ошибка получения списка клиентов в резервном блоке.", e);
		}
		catch (BusinessException e)
		{
			throw new DataAccessException("Ошибка получения списка клиентов в резервном блоке.", e);
		}
	}

	private List<String> getTBList(String department) throws BusinessException
	{
		List<String> tbSet = AllowedDepartmentsUtil.getAllowedTerbanksNumbers();

		if (StringHelper.isEmpty(department))
			return tbSet;

		if (tbSet.contains(department))
			return Collections.singletonList(department);

		return Collections.emptyList();
	}
}
