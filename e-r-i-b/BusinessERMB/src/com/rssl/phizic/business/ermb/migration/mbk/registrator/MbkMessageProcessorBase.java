package com.rssl.phizic.business.ermb.migration.mbk.registrator;

import com.rssl.phizic.gate.csa.UserInfo;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.ermb.ErmbHelper;
import com.rssl.phizic.business.ermb.ErmbProfileBusinessService;
import com.rssl.phizic.business.ermb.ErmbProfileImpl;
import com.rssl.phizic.business.ermb.migration.list.entity.migrator.MigrationClient;
import com.rssl.phizic.gate.ermb.MBKRegistration;
import com.rssl.phizic.business.ermb.migration.mbk.registrator.sender.ProcessResult;
import com.rssl.phizic.business.ermb.migration.onthefly.fpp.FPPMigrationConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.mobilebank.MBKRegistrationResultCode;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.PhoneNumber;

/**
 * Обработчик сообщений о регистрации МБК (прокси)
 * @author Puzikov
 * @ created 08.07.14
 * @ $Author$
 * @ $Revision$
 */

public abstract class MbkMessageProcessorBase implements MbkMessageProcessor
{
	protected final Log log = PhizICLogFactory.getLog(LogModule.Core);
	private static final ErmbProfileBusinessService profileService = new ErmbProfileBusinessService();

	protected ProcessResult createSuccessResult(MBKRegistration message)
	{
		return new ProcessResult(
				message.getId(),
				MBKRegistrationResultCode.SUCCESS,
				null
		);
	}

	protected ProcessResult createErrorResult(MBKRegistration message, boolean mbkRegister, String description)
	{
		return new ProcessResult(
				message.getId(),
				mbkRegister ? MBKRegistrationResultCode.ERROR_REG : MBKRegistrationResultCode.ERROR_NOT_REG,
				description
		);
	}

	protected ErmbProfileImpl findByPhone(PhoneNumber phoneNumber) throws BusinessException
	{
		return profileService.findByPhone(phoneNumber);
	}

	/**
	 * Найти профиль ЕРМБ в текущем блоке
	 */
	protected ErmbProfileImpl findByFioDulDrPilotTb(MigrationClient person) throws BusinessException
	{
		return profileService.findByFIOAndDocInTB(
				person.getLastName(),
				person.getFirstName(),
				person.getMiddleName(),
				person.getDocument(),
				null,
				person.getBirthday(),
				getPilotTb()
		);
	}

	/**
	 * Проверить подключенность ЕРМБ клиента с учетом многоблочности
	 */
	protected boolean isErmbConnected(MigrationClient client) throws BusinessException
	{
		//Ищем в блоке
		boolean found = findByFioDulDrPilotTb(client) != null;
		//Ищем в ЦСА
		if (!found)
		{
			UserInfo userInfo = new UserInfo(getPilotTb(), client.getFirstName(), client.getLastName(), client.getMiddleName(),
					client.getDocument(), client.getBirthday());
			found = ErmbHelper.isCsaErmbConnected(userInfo);
		}

		StringBuilder logInfo = new StringBuilder();
		logInfo.append("Ищем в ЕРМБ клиента")
				.append(";Фио:").append(client.getFirstName()).append(' ').append(client.getLastName()).append(' ').append(client.getMiddleName())
				.append(";Дул:").append(client.getDocument())
				.append(";Др:").append(DateHelper.formatCalendar(client.getBirthday()))
				.append(";Тб(пилотный):").append(getPilotTb())
				.append(";Результат:").append(found ? "найден" : "не найден");
		log.info(logInfo.toString());
		return found;
	}

	protected String getPilotTb()
	{
		return ConfigFactory.getConfig(FPPMigrationConfig.class).getPilotTb();
	}
}
