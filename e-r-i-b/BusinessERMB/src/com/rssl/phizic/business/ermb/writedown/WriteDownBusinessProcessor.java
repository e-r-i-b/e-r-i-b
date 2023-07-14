package com.rssl.phizic.business.ermb.writedown;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.ermb.*;
import com.rssl.phizic.business.ermb.products.ErmbNotificationSettingsController;
import com.rssl.phizic.common.types.Period;
import com.rssl.phizic.common.types.exceptions.IKFLMessagingException;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.synchronization.types.IdentityCardType;
import com.rssl.phizic.synchronization.types.IdentityType;
import com.rssl.phizic.synchronization.types.ServiceFeeResultRq;
import com.rssl.phizic.utils.PhoneNumber;
import com.rssl.phizic.utils.StringHelper;

import java.util.Collections;

/**
 * Обработчик ответа WAY из СОС о списании абонентской платы
 * @author Puzikov
 * @ created 06.06.14
 * @ $Author$
 * @ $Revision$
 */

public class WriteDownBusinessProcessor
{
	private static final ErmbProfileBusinessService profileService = new ErmbProfileBusinessService();
	private final ErmbChargeDateUpdater updater = new ErmbChargeDateUpdater();
	private final BlockSmsSender smsSender = new BlockSmsSender();

	private static final Log log = PhizICLogFactory.getLog(LogModule.Core);

	/**
	 * Обработать ответ
	 * @param request ответ о списании
	 * @return результат обработки ответа
	 */
	public WriteDownResult process(ServiceFeeResultRq request) throws BusinessException, IKFLMessagingException
	{
		WriteDownResult result = new WriteDownResult();

		ErmbProfileImpl ermbProfile = findErmbProfile(request.getClientIdentity());
		result.setOldServiceStatus(ErmbHelper.getServiceStatus(ermbProfile));
		result.setPhoneNumber(PhoneNumber.fromString(ermbProfile.getMainPhoneNumber()));

		boolean successful = "0".equals(request.getPaymentStatus());
		result.setSuccessful(successful);

		//забываетм про бывшую блокировку МБК, т.к. уже прошел цикл оплаты в ЕРМБ
		ermbProfile.setMbkPaymentBlocked(false);
		ermbProfile.setFppInProgress(false);
		boolean needUpdateRequest = false;
		boolean needSendUnblockSms = false;
		boolean needSendBlockSms = false;
		Period writeDownPeriod = null;

		if (ermbProfile.isPaymentBlocked())
		{
			if (successful)
			{
				ermbProfile.setPaymentBlocked(false);
				writeDownPeriod = updater.resetChargeDate(ermbProfile);
				updateProductsErmbNotification(ermbProfile, true);
				increaseProfileVersion(ermbProfile);
				needUpdateRequest = true;
				needSendUnblockSms = true;
			}
			else
			{
				log.error("Пришло сообщение о неуспешном списании абонентской платы для заблокированного по неоплате профиля ермб");
			}
		}
		else
		{
			if (successful)
			{
				writeDownPeriod = updater.shiftChargeDate(ermbProfile);
			}
			else
			{
				ermbProfile.setPaymentBlocked(true);
				updateProductsErmbNotification(ermbProfile, false);
				increaseProfileVersion(ermbProfile);
				needUpdateRequest = true;
				needSendBlockSms = true;
			}
		}
		profileService.addOrUpdate(ermbProfile);

		if (needSendUnblockSms)
			smsSender.sendUnblockSms(ermbProfile);

		if (needSendBlockSms)
			smsSender.sendBlockSms(ermbProfile);

		if (needUpdateRequest)
			sendUpdateNotification(ermbProfile);

		result.setWriteOffPeriod(writeDownPeriod);
		result.setNewServiceStatus(ErmbHelper.getServiceStatus(ermbProfile));
		return result;
	}

	private ErmbProfileImpl findErmbProfile(IdentityType clientIdentity) throws BusinessException
	{
		IdentityCardType identityCard = clientIdentity.getIdentityCard();
		ErmbProfileImpl ermbProfile = profileService.findByFIOAndDocInTB(
				clientIdentity.getLastname(),
				clientIdentity.getFirstname(),
				clientIdentity.getMiddlename(),
				identityCard.getIdSeries(),
				identityCard.getIdNum(),
				clientIdentity.getBirthday(),
				StringHelper.removeLeadingZeros(clientIdentity.getTb())
		);

		if (ermbProfile == null || !ermbProfile.isServiceStatus())
		{
			throw new IllegalStateException("Не найден активный ермб-профиль в системе по сообщению о результате списания абонентской платы из сос");
		}

		return ermbProfile;
	}

	private void sendUpdateNotification(ErmbProfileImpl ermbProfile)
	{
		if (!ermbProfile.getProfileVersion().equals(ermbProfile.getConfirmProfileVersion()) && (ermbProfile.isServiceStatus()))
		{
			ErmbOfficialInfoNotificationSender sender = new ErmbOfficialInfoNotificationSender();
			sender.sendNotification(Collections.singletonList(ermbProfile));
		}
	}

	private void increaseProfileVersion(ErmbProfileImpl ermbProfile)
	{
		Long version = ermbProfile.getProfileVersion();
		ermbProfile.setProfileVersion(version != null ? ++version : 1L);
	}

	private void updateProductsErmbNotification(ErmbProfileImpl ermbProfile, boolean notify) throws BusinessException
	{
		ermbProfile.setNewProductNotification(notify);

		ErmbNotificationSettingsController notificationController = new ErmbNotificationSettingsController(ermbProfile);
		notificationController.resetAll();
	}

}
