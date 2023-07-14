package com.rssl.phizic.web.common.client.ext.sbrf.limits;

import com.rssl.phizic.auth.modes.AthenticationCompleteAction;
import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.business.limits.LimitHelper;
import com.rssl.phizic.business.limits.LimitRecordsReplicator;
import com.rssl.phizic.common.types.csa.MigrationState;
import com.rssl.phizic.common.types.csa.ProfileType;
import com.rssl.phizic.common.types.limits.ChannelType;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.limits.LimitsConfig;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.security.SecurityLogicException;
import com.rssl.phizic.utils.DateHelper;

/**
 * Экшн репликации лимитов из внешней базы
 * @author niculichev
 * @ created 27.01.14
 * @ $Author$
 * @ $Revision$
 */
public class ReplicateDocumentOperationsCompleteAction implements AthenticationCompleteAction
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	public void execute(AuthenticationContext context) throws SecurityException, SecurityLogicException
	{
		LimitsConfig limitsConfig = ConfigFactory.getConfig(LimitsConfig.class);
		if(!limitsConfig.isUseReplicateLimits())
			return;

		if (ProfileType.MAIN == context.getProfileType() && MigrationState.COMPLETE == context.getMigrationState())
		{
			//Репликация производится при входе в резервынй блок всегда и при входе в основной, если клиент немигрирован
			return;
		}

		Person person = PersonContext.getPersonDataProvider().getPersonData().getPerson();
		ChannelType channelType = LimitHelper.getCurrentChannelType();
		if(channelType == null)
		{
			log.error("Не удалось определить канал репликации лимитов из общей базы с лимитами.");
			return;
		}

		try
		{
			LimitRecordsReplicator.getIt().replicate(person, channelType, DateHelper.getPreviousDay());
		}
		catch (Exception e)
		{
			// игнорим все исключения, продолжаем работу
			log.error(e.getMessage(), e);
		}
	}
}
