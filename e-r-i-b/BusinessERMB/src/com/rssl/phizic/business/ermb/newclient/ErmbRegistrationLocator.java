package com.rssl.phizic.business.ermb.newclient;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.ermb.mobileOperator.ErmbMobileOperator;
import com.rssl.phizic.business.ermb.mobileOperator.ErmbMobileOperatorService;
import com.rssl.phizic.business.ermb.registration.ErmbRegistrationEvent;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.PhoneNumber;

import java.util.*;

/**
 * Распределение сообщений о новых регистрациях ЕРМБ по разным веб-сервисам операторов сотовой связи
 * @author Puzikov
 * @ created 11.03.15
 * @ $Author$
 * @ $Revision$
 */

public class ErmbRegistrationLocator
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private static final ErmbMobileOperatorService service = new ErmbMobileOperatorService();

	private Map<String, ErmbRegistrationBatch> urlToBatch = new HashMap<String, ErmbRegistrationBatch>();
	private List<PhoneNumber> skipped = new ArrayList<PhoneNumber>();

	/**
	 * Разбить регистрации по веб-сервисам, в зависимости от настроек
	 * @param registrations набор новых регистраций
	 */
	public void locate(Collection<ErmbRegistrationEvent> registrations) throws BusinessException
	{
		for (ErmbRegistrationEvent registration : registrations)
		{
			PhoneNumber phone = registration.getPhone();
			ErmbMobileOperator mobileOperator = service.getByPhone(phone);
			if (mobileOperator == null)
			{
				log.error("Невозможно оповестить оператора сотовой связи о новом телефоне ЕРМБ. Отсутствуют настойки ОСС. Телефон: " + phone.hideAbonent());
				continue;
			}
			if (!mobileOperator.isUseIntegration())
			{
				skipped.add(phone);
				continue;
			}

			String url = mobileOperator.getServiceUrl();
			String login = mobileOperator.getServiceLogin();
			String password = mobileOperator.getServicePassword();

			if (urlToBatch.containsKey(url))
			{
				ErmbRegistrationBatch batch = urlToBatch.get(url);
				batch.addRegistration(registration);
			}
			else
			{
				ErmbRegistrationBatch batch = new ErmbRegistrationBatch(url, login, password);
				batch.addRegistration(registration);
				urlToBatch.put(url, batch);
			}
		}
	}

	/**
	 * @return пачки регистраций, разбитые по веб-сервисам интеграции
	 */
	public Collection<ErmbRegistrationBatch> getBatches()
	{
		return urlToBatch.values();
	}

	/**
	 * Телефоны, для осс которых интеграция выключена в настройках
	 * @return список номеров телефонов
	 */
	public Collection<PhoneNumber> getSkipped()
	{
		return Collections.unmodifiableList(skipped);
	}
}
