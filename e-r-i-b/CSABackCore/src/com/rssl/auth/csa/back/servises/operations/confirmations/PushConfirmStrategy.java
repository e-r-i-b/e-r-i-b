package com.rssl.auth.csa.back.servises.operations.confirmations;

import com.rssl.auth.csa.back.integration.mobilebank.SendMessageInfo;
import com.rssl.auth.csa.back.integration.mobilebank.SendMessageRouter;
import com.rssl.auth.csa.back.servises.ClientDataImpl;
import com.rssl.auth.csa.back.servises.Profile;
import com.rssl.auth.csa.back.servises.connectors.MAPIConnector;
import com.rssl.auth.csa.back.servises.operations.CSAPushResourcesOperation;
import com.rssl.auth.csa.back.servises.operations.CSASmsResourcesOperation;
import com.rssl.auth.csa.back.servises.operations.ConfirmableOperationBase;
import com.rssl.phizic.common.types.transmiters.Triplet;
import com.rssl.phizic.logging.push.PushEventType;
import com.rssl.phizic.messaging.mail.messagemaking.push.PushPrivacyType;
import com.rssl.phizic.messaging.mail.messagemaking.push.PushPublicityType;
import com.rssl.phizic.messaging.push.PushMessage;
import com.rssl.phizic.messaging.push.PushTransportService;
import com.rssl.phizic.messaging.push.PushTransportServiceImpl;
import com.rssl.phizic.messaging.push.PushXmlCreator;
import com.rssl.phizic.gate.mobilebank.GetRegistrationMode;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * стратегия подтверждения push-паролем
 * @author basharin
 * @ created 02.10.14
 * @ $Author$
 * @ $Revision$
 */

public class PushConfirmStrategy extends SMSConfirmStrategy
{
	private Profile profile;

	/**
	 *
	 * @param profile профиль
	 * @param ouid идентификатор операции (для логов)
	 * @param operationClass класс вызывающей операции (для получения текста смс)
	 * @param cardNumber номер карты (для отправки смс)
	 * @param useAlternativeRegistrationsMode использовать ли альтернативные способы поиска получателя (для отправки смс)
	 * @param checkIMSI использовать ли проверку IMSI (для отправки смс)
	 * @param password объект пароля (для валидации)
	 */
	public PushConfirmStrategy(Profile profile, String ouid, Class<? extends ConfirmableOperationBase> operationClass, String cardNumber, boolean useAlternativeRegistrationsMode, boolean checkIMSI, DisposablePassword password, String appName)
	{
		super(ouid, operationClass, cardNumber, useAlternativeRegistrationsMode, checkIMSI, password, appName);
		this.profile = profile;
	}

	@Override
	public void publishCode() throws Exception
	{
		String name = getOperationClass().getName();

		String text = CSAPushResourcesOperation.getFormattedSmsResourcesText(name + ".confirm", code);
		Object[] params = CSAPushResourcesOperation.getShortText(name + ".confirm");
		String smsText = CSASmsResourcesOperation.getFormattedSmsResourcesText(name + ".confirm", code);
		String textToLog = CSAPushResourcesOperation.getFormattedSmsResourcesText(name + ".confirm", CSASmsResourcesOperation.PASSWORD_MASK);

		PushMessage pushMessage = new PushMessage();
		pushMessage.setText(text);
		pushMessage.setShortMessage((String)params[0]);
		pushMessage.setSmsMessage(smsText);
		pushMessage.setTextToLog(PushXmlCreator.createXml(textToLog, StringUtils.EMPTY, ConfirmableOperationBase.getCongirmationLifeTime(), true, pushMessage.getPrivacyType(), pushMessage.getPublicityType(), (Integer)params[3]));
		pushMessage.setSmsBackup(true);
		pushMessage.setPrivacyType(PushPrivacyType.valueOf((String)params[1]));
		pushMessage.setPublicityType(PushPublicityType.valueOf((String)params[2]));
		pushMessage.setFullMessage(PushXmlCreator.createXml(text, StringUtils.EMPTY, ConfirmableOperationBase.getCongirmationLifeTime(), true, pushMessage.getPrivacyType(), pushMessage.getPublicityType(), (Integer)params[3]));
		pushMessage.setTypeCode(PushEventType.OPERATION_CONFIRM.fromValue(String.valueOf(params[3])));

		GetRegistrationMode registrationMode = useAlternativeRegistrationsMode ? GetRegistrationMode.BOTH : GetRegistrationMode.SOLID;
		SendMessageInfo sendMessageInfo = new SendMessageInfo(cardNumber, null, isCheckIMSI, registrationMode);
		List<String> phones = SendMessageRouter.getInstance().getPhones(sendMessageInfo);

		List<MAPIConnector> connectors = MAPIConnector.findNotClosedByProfileID(profile.getId());
		List<Triplet<String, String, String>> deviceInfo = new ArrayList<Triplet<String, String, String>>();
		for (MAPIConnector connector : connectors)
		{
			if (!connector.getPushSupported())
			{
				continue;
			}
			Triplet<String, String, String> triplet = new Triplet<String, String, String>();
			triplet.setFirst(connector.getDeviceId());
			triplet.setSecond(connector.getGuid());
			triplet.setThird(connector.getDeviceInfo());
			deviceInfo.add(triplet);
		}

		PushTransportService pushTransportService = new PushTransportServiceImpl();
		pushTransportService.sendPush(new ClientDataImpl(profile), pushMessage, phones, deviceInfo);
	}
}
