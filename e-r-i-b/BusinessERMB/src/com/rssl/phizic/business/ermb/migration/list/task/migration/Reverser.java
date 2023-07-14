package com.rssl.phizic.business.ermb.migration.list.task.migration;

import com.rssl.auth.csa.wsclient.CSABackRequestHelper;
import com.rssl.auth.csa.wsclient.exceptions.BackException;
import com.rssl.auth.csa.wsclient.exceptions.BackLogicException;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ermb.ErmbProfileBusinessService;
import com.rssl.phizic.business.ermb.ErmbProfileImpl;
import com.rssl.phizic.business.ermb.migration.CodWayErmbConnectionSender;
import com.rssl.phizic.business.ermb.migration.list.MigrationStatus;
import com.rssl.phizic.business.ermb.migration.list.entity.migrator.Client;
import com.rssl.phizic.business.ermb.migration.list.entity.migrator.MigrationInfo;
import com.rssl.phizic.business.ermb.migration.list.task.TaskLog;
import com.rssl.phizic.business.ermb.migration.list.task.TaskType;
import com.rssl.phizic.business.ermb.migration.list.task.hibernate.ClientService;
import com.rssl.phizic.gate.ermb.MbkTariff;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.common.types.UUID;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.dataaccess.common.counters.CounterException;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.mobilebank.ClientTariffInfo;
import com.rssl.phizic.gate.mobilebank.DepoMobileBankConfig;
import com.rssl.phizic.gate.mobilebank.DepoMobileBankService;
import com.rssl.phizic.gate.mobilebank.MobileBankService;
import com.rssl.phizic.utils.StringHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Выполнение отката миграции
 * @author Puzikov
 * @ created 28.02.14
 * @ $Author$
 * @ $Revision$
 */

public class Reverser
{
	private final static ClientService clientService = new ClientService();
	private final static ErmbProfileBusinessService profileService = new ErmbProfileBusinessService();
	@SuppressWarnings("deprecation")
	private final MobileBankService mbkService = GateSingleton.getFactory().service(MobileBankService.class);
	private final CodWayErmbConnectionSender codWaySender = new CodWayErmbConnectionSender();

	private final TaskLog log;
	private final Long currentBlock;

	private int reversedCount;
	private int errorCount;

	/**
	 * ctor
	 * @param currentBlock текущий блок, в котором запущена откат миграции
	 */
	public Reverser(Long currentBlock) throws IOException, CounterException
	{
		this.log = new TaskLog(TaskType.ROLLBACK_MIGRATION);
		log.add("Откат миграции в блоке " + currentBlock + " начался");
		this.currentBlock = currentBlock;
	}

	/**
	 * Запустить
	 * @param migrationInfo информация о миграции
	 */
	public void reverse(MigrationInfo migrationInfo) throws BusinessException
	{
		boolean mbkRolledBack = false;
		boolean mbvRolledBack = false;
		boolean ermbRolledBack = false;

		try
		{
			ErmbProfileImpl profile = findErmbProfile(migrationInfo);
			//noinspection deprecation
			Long mbkMigrationId = migrationInfo.getMbkMigrationId();
			if (mbkMigrationId != null)
			{
				ClientTariffInfo clientTariffInfo = createCurrentTariffInfo(profile);
				mbkService.reverseMigration(mbkMigrationId, clientTariffInfo);
			}
			mbkRolledBack = true;

			DepoMobileBankConfig mbvConfig = ConfigFactory.getConfig(DepoMobileBankConfig.class);
			if (mbvConfig.isMbvAvaliability())
			{
				DepoMobileBankService mbvService = GateSingleton.getFactory().service(DepoMobileBankService.class);
				UUID mbvMigrationId = migrationInfo.getMbvMigrationId();
				if (mbvMigrationId != null)
					mbvService.reverseMigration(mbvMigrationId);
			}
			mbvRolledBack = true;

			rollbackErmbProfile(profile);
			ermbRolledBack = true;

			clientService.deleteMigrationInfo(migrationInfo);
			clientService.setMigrationStatus(migrationInfo.getClient(), MigrationStatus.ROLLED_BACK);
			reversedCount++;
		}
		catch (Exception e)
		{
			clientService.setRollbackError(migrationInfo);
			if (log != null)
			{
				log.add("Ошибка во время отката миграции клиента id=" + migrationInfo.getClient().getId())
				.add("Откат в МБК произошел: " + mbkRolledBack)
				.add("Откат в МБВ произошел: " + mbvRolledBack)
				.add("Откат профиля ЕРМБ произошел: " + ermbRolledBack)
				.add(e);
			}
			errorCount++;
		}
		finally
		{
			if (log != null)
				log.flush();
		}
	}

	private ErmbProfileImpl findErmbProfile(MigrationInfo migrationInfo) throws BusinessException
	{
		Client client = migrationInfo.getClient();
		ErmbProfileImpl profile = profileService.findByFIOAndDocInTB(
				client.getLastName(),
				client.getFirstName(),
				StringHelper.getNullIfEmpty(client.getMiddleName()),
				null,
				client.getDocument(),
				client.getBirthday(),
				StringHelper.getNullIfEmpty(client.getTb())
		);
		if (profile == null)
		{
			throw new BusinessException("Не найден профиль ЕРМБ по информации о миграции id=" + migrationInfo.getClient().getId());
		}
		else if (!profile.isServiceStatus())
		{
			throw new BusinessException("Откатываемый профиль находится в состоянии НЕ ПОДКЛЮЧЕН");
		}
		return profile;
	}

	private void rollbackErmbProfile(ErmbProfileImpl profile) throws BusinessException, BusinessLogicException
	{
		List<String> removePhones = new ArrayList<String>(profile.getPhoneNumbers());

		profileService.disconnect(profile);
		codWaySender.sendErmbDisconnected(profile);
		try
		{
			CSABackRequestHelper.sendUpdatePhoneRegistrationsRq(
					null,
					PersonHelper.buildUserInfo(profile.getPerson()),
					Collections.<String>emptyList(),
					removePhones
			);
		}
		catch (BackLogicException e)
		{
			throw new BusinessLogicException(e);
		}
		catch (BackException e)
		{
			throw new BusinessException(e);
		}
	}

	private ClientTariffInfo createCurrentTariffInfo(ErmbProfileImpl profile)
	{
		ClientTariffInfo result = new ClientTariffInfo();
		result.setLinkTariff(MbkTariff.fromStringCode(profile.getTarif().getCode()).getMbkCode());
		result.setLinkPaymentBlockID(profile.isPaymentBlocked() ? 1 : 0);
		result.setNextPaidPeriod(profile.getChargeNextDate());
		result.setPayDate(profile.getChargeDayOfMonth());

		return result;
	}

	/**
	 * Записать в лог информацию о работе
	 */
	public void flushInfo()
	{
		log.add("Откат миграции в блоке " + currentBlock + " завершился")
		.add(reversedCount + "\tклиентов восстановлено")
		.add(errorCount + "\tклиентов не удалось восстановить")
		.flush();
	}
}
