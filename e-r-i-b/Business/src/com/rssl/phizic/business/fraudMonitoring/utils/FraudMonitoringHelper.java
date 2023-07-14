package com.rssl.phizic.business.fraudMonitoring.utils;

import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.rsa.config.RSAConfig;
import com.rssl.phizic.rsa.integration.ws.notification.generated.ResolutionTypeList;
import com.rssl.phizic.rsa.notifications.FraudNotification;
import com.rssl.phizic.rsa.notifications.FraudNotificationService;
import com.rssl.phizic.rsa.senders.UpdateActivitySender;
import com.rssl.phizic.rsa.senders.initialization.UpdateActivityInitializationData;

/**
 * @author tisov
 * @ created 30.06.15
 * @ $Author$
 * @ $Revision$
 * Хелпер по функционалу фрод-мониторинга
 */
public class FraudMonitoringHelper
{
	private static final String IMSI_OPERATION_DESCRIPTION                                  = "Выявлена замена СИМ-карты";
	private static final String IMPOSSIBLE_PERFORM_OPERATION_DESCRIPTION                    = "Превышение лимита";

	private static final Log LOG = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	/**
	 * Позволено ли сотруднику отправлять офицеру безопасности поясняющий текст при выставлении вердикта
	 * @return
	 */
	public static boolean isOfficerTextSendingActive()
	{
		return RSAConfig.getInstance().isSendingVerdictCommentActive();
	}

	/**
	 * Сохранить оповещение о сработавшем ИМСИ лимите
	 * @param document документ
	 */
	public static void saveIMSIOperationNotification(BusinessDocument document)
	{
		try
		{
			FraudNotificationService.getInstance().save(getIMSIOperationNotification(document));
		}
		catch (Exception e)
		{
			LOG.error("Ошибка сохранения оповещения о смене сим-карты.", e);
		}
	}

	private static FraudNotification getIMSIOperationNotification(BusinessDocument document) throws GateLogicException, GateException
	{
		UpdateActivitySender sender = new UpdateActivitySender();
		sender.initialize(new UpdateActivityInitializationData(document.getClientTransactionId(), ResolutionTypeList.UNKNOWN, IMSI_OPERATION_DESCRIPTION, document.getId()));
		return new FraudNotification(sender.getRequestBody());
	}

	/**
	 * Сохранить оповещение о сработавшем заградительном лимите
	 * @param document документ
	 */
	public static void saveImpossiblePerformOperationNotification(BusinessDocument document)
	{
		try
		{
			FraudNotificationService.getInstance().save(getImpossiblePerformOperationNotification(document));
		}
		catch (Exception e)
		{
			LOG.error("Ошибка сохранения оповещения о сработавшем заградительном лимите.", e);
		}
	}

	private static FraudNotification getImpossiblePerformOperationNotification(BusinessDocument document) throws GateLogicException, GateException
	{
		UpdateActivitySender sender = new UpdateActivitySender();
		sender.initialize(new UpdateActivityInitializationData(document.getClientTransactionId(), ResolutionTypeList.UNKNOWN, IMPOSSIBLE_PERFORM_OPERATION_DESCRIPTION, document.getId()));
		return new FraudNotification(sender.getRequestBody());
	}
}
