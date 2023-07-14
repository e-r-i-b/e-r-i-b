package com.rssl.phizic.scheduler.ermb;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ermb.MobileBankManager;
import com.rssl.phizic.business.ermb.auxiliary.ErmbAuxChannel;
import com.rssl.phizic.business.ermb.disconnector.DisconnectorPhone;
import com.rssl.phizic.business.ermb.disconnector.ErmbPhoneDisconnector;
import com.rssl.phizic.business.ermb.disconnector.OSSConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.mobilebank.DisconnectedPhoneResult;
import com.rssl.phizic.gate.mobilebank.PhoneDisconnectionReason;
import com.rssl.phizic.logging.LogThreadContext;
import com.rssl.phizic.module.Module;
import com.rssl.phizic.scheduler.StatefulModuleJob;
import org.apache.commons.collections.CollectionUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Gulov
 * @ created 09.09.13
 * @ $Author$
 * @ $Revision$
 */

/**
 * Отключение телефонов от клиентов ЕРМБ
 */
public class ErmbDisconnectorPhoneJob extends StatefulModuleJob
{
	public static final String UPDATE_STATUS_DISCONNECTED_ERROR = "Ошибка обновления статуса отключенных телефонов";
	public static final String DISCONNECT_PHONE_ERROR = "Ошибка отключения телефона от клиента";
	public static final String FIND_DISCONNECT_PHONE_ERROR = "Ошибка поиска списка отключенных телефонов";

	@Override
	public Class<? extends Module> getModuleClass()
	{
		return ErmbAuxChannel.class;
	}

	protected void executeJob(JobExecutionContext context) throws JobExecutionException
	{
		OSSConfig config = ConfigFactory.getConfig(OSSConfig.class);
		if (!config.getUseIntegration())
			return;

		try
		{
			List<DisconnectedPhoneResult> phones = getDisconnectedPhones(config);
			if (CollectionUtils.isEmpty(phones))
				return;
			List<Integer> processedPhones = new LinkedList<Integer>();
			int count = 0;
			for (DisconnectedPhoneResult phone : phones)
			{
				disconnectPhone(phone.getPhoneNumber(), phone.getReason());
				processedPhones.add(phone.getId());
				if (++count == phones.size() || processedPhones.size() == config.getMaxDisconnectorsForUpdate())
				{
					updateState(processedPhones);
					processedPhones.clear();
				}
			}
		}
		finally
		{
            LogThreadContext.clear();
		}
	}

	private void updateState(List<Integer> processedPhones) throws JobExecutionException
	{
		try
		{
			MobileBankManager.updateDisconnectedPhonesState(processedPhones);
		}
		catch (BusinessException e)
		{
			throw new JobExecutionException(UPDATE_STATUS_DISCONNECTED_ERROR, e);
		}
	}

	private void disconnectPhone(String phoneNumber, PhoneDisconnectionReason reason) throws JobExecutionException
	{
		try
		{
			ErmbPhoneDisconnector disconnector = new DisconnectorPhone();
			disconnector.disconnect(phoneNumber, reason);
		}
		catch (BusinessException e)
		{
			throw new JobExecutionException(DISCONNECT_PHONE_ERROR, e);
		}
		catch (BusinessLogicException e)
		{
			throw new JobExecutionException(DISCONNECT_PHONE_ERROR, e);
		}
	}

	private List<DisconnectedPhoneResult> getDisconnectedPhones(OSSConfig config) throws JobExecutionException
	{
		try
		{
			return MobileBankManager.getDisconnectedPhones(config.getMaxDisconnectors());
		}
		catch (BusinessException e)
		{
			throw new JobExecutionException(FIND_DISCONNECT_PHONE_ERROR, e);
		}
	}
}
