package com.rssl.phizic.web.common.client.ext.sbrf.resources;

import com.rssl.phizic.auth.modes.AthenticationCompleteAction;
import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.StaticPersonData;
import com.rssl.phizic.business.cards.CardsUtil;
import com.rssl.phizic.business.clients.ClientResourceHelper;
import com.rssl.phizic.business.clients.ClientResourcesService;
import com.rssl.phizic.business.documents.BusinessDocumentService;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.ClientConfig;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * @author egorova
 * @ created 11.08.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * В КЛАССЕ НЕ ДОЛЖНО БЫТЬ СОСТОЯНИЯ!!!!!!
 */
public class AddOrUpdateClientResourcesAction implements AthenticationCompleteAction
{
	private static final ClientResourcesService service = new ClientResourcesService();
	private static final BusinessDocumentService documentService  = new BusinessDocumentService();
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	public void execute(AuthenticationContext context) throws SecurityException
	{
		//при переходе из СБОЛ2 в СБОЛ3 не обновляем продукты
		if (!context.isNeedReloadProducts())
			return;

		StaticPersonData personData = (StaticPersonData) PersonContext.getPersonDataProvider().getPersonData();

		ActivePerson person = personData.getPerson();

		try
		{
			Pair<List<Class>, Map<Class, String>> pair = getClientProducts(person);

			if (CollectionUtils.isNotEmpty(pair.getFirst()))
			{
				//если список продуктов не пуст, выполняем gfl запрос
				service.updateResources(person, getOnlyWayCards(), pair.getFirst().toArray(new Class[pair.getFirst().size()]));
			}
			if (PersonContext.isAvailable())
			{
				try
				{
					if (CardsUtil.hasClientActiveCreditCard())
						personData.setLoanCardClaimAvailable(false);
					else
					{
						ClientConfig clientConfig = ConfigFactory.getConfig(ClientConfig.class);
						if (clientConfig.isDoLoanCardClaimExistenceRequest())
							personData.setLoanCardClaimAvailable(!documentService.isExistLoanCardClaim(person.getLogin()));
					}
				}
				catch (BusinessException e)
				{
					log.error("Ошибка при попытке определить наличие активной кредитной карты у клиента", e);
				}
				catch (BusinessLogicException e)
				{
					log.error("Ошибка при попытке определить наличие активной кредитной карты у клиента", e);
				}
			}
		}
		catch (InactiveExternalSystemException e)
		{
			log.warn(e.getMessage(), e);
			context.putInactiveESMessage(e.getMessage());
		}
		catch (BusinessException e)
		{
			throw new SecurityException(e);
		}
	}


	protected Pair<List<Class>, Map<Class, String>> getClientProducts(ActivePerson person) throws BusinessException
	{
		return ClientResourceHelper.getClientsProducts(person, true);
	}

	protected boolean getOnlyWayCards()
	{
		return false;
	}
}
