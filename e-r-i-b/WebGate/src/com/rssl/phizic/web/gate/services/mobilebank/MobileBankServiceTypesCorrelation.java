package com.rssl.phizic.web.gate.services.mobilebank;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jatsky
 * @ created 19.05.15
 * @ $Author$
 * @ $Revision$
 */

public class MobileBankServiceTypesCorrelation
{
	public static final Map<Class, Class> types = new HashMap<Class,Class>();

	static
	{
		types.put(com.rssl.phizic.common.types.transmiters.GroupResult.class, com.rssl.phizic.web.gate.services.mobilebank.generated.GroupResult.class);
		types.put(com.rssl.phizic.gate.clients.Client.class, com.rssl.phizic.web.gate.services.mobilebank.generated.Client.class);
		types.put(com.rssl.phizic.gate.clients.ClientState.class, com.rssl.phizic.web.gate.services.mobilebank.generated.ClientState.class);
		types.put(com.rssl.phizic.web.gate.services.mobilebank.generated.Client.class, com.rssl.phizic.gate.clients.Client.class);
		types.put(com.rssl.phizic.web.gate.services.mobilebank.generated.ClientState.class, com.rssl.phizic.gate.clients.ClientState.class);
		types.put(com.rssl.phizic.web.gate.services.mobilebank.generated.MobileBankCardInfo.class, com.rssl.phizic.web.gate.services.types.MobileBankCardInfoImpl.class);
		types.put(com.rssl.phizic.web.gate.services.mobilebank.generated.MobileBankTemplate.class, com.rssl.phizgate.mobilebank.MobileBankTemplateImpl.class);
		types.put(com.rssl.phizic.web.gate.services.types.MobileBankCardInfoImpl.class, com.rssl.phizic.web.gate.services.mobilebank.generated.MobileBankCardInfo.class);
		types.put(com.rssl.phizic.gate.mobilebank.MobileBankTemplate.class, com.rssl.phizic.web.gate.services.mobilebank.generated.MobileBankTemplate.class);
		types.put(com.rssl.phizic.web.gate.services.mobilebank.generated.MbkCard.class, com.rssl.phizic.gate.mobilebank.MbkCard.class);
		types.put(com.rssl.phizic.web.gate.services.mobilebank.generated.MbkConnectionInfo.class, com.rssl.phizic.gate.mobilebank.MbkConnectionInfo.class);
		types.put(com.rssl.phizic.web.gate.services.mobilebank.generated.InfoCardImpl.class, com.rssl.phizic.gate.mobilebank.InfoCardImpl.class);
		types.put(com.rssl.phizic.web.gate.services.mobilebank.generated.MbkConnectionTemplates.class, com.rssl.phizic.gate.mobilebank.MobileBankTemplate.class);
		types.put(com.rssl.phizic.gate.mobilebank.BeginMigrationResult.class, com.rssl.phizic.web.gate.services.mobilebank.generated.BeginMigrationResult.class);
		types.put(com.rssl.phizic.gate.mobilebank.CommitMigrationResult.class, com.rssl.phizic.web.gate.services.mobilebank.generated.CommitMigrationResult.class);
		types.put(com.rssl.phizic.gate.bankroll.Card.class, com.rssl.phizic.web.gate.services.mobilebank.generated.Card.class);
		types.put(com.rssl.phizic.gate.mobilebank.UserInfo.class, com.rssl.phizic.web.gate.services.mobilebank.generated.UserInfo.class);
		types.put(com.rssl.phizic.gate.mobilebank.DisconnectedPhoneResult.class, com.rssl.phizic.web.gate.services.mobilebank.generated.DisconnectedPhoneResult.class);
		types.put(com.rssl.phizic.gate.mobilebank.P2PRequest.class, com.rssl.phizic.web.gate.services.mobilebank.generated.P2PRequest.class);
		types.put(com.rssl.phizic.gate.mobilebank.AcceptInfo.class, com.rssl.phizic.web.gate.services.mobilebank.generated.AcceptInfo.class);
		types.put(com.rssl.phizic.gate.profile.MBKPhone.class, com.rssl.phizic.web.gate.services.mobilebank.generated.MBKPhone.class);
		types.put(com.rssl.phizic.gate.mobilebank.MobileBankRegistration.class, com.rssl.phizic.web.gate.services.mobilebank.generated.MobileBankRegistration.class);
		types.put(com.rssl.phizic.gate.mobilebank.MobileBankRegistration3.class, com.rssl.phizic.web.gate.services.mobilebank.generated.MobileBankRegistration3.class);
		types.put(com.rssl.phizic.gate.mobilebank.UESIMessage.class, com.rssl.phizic.web.gate.services.mobilebank.generated.UESIMessage.class);
		types.put(com.rssl.phizic.web.gate.services.mobilebank.generated.ClientTariffInfo.class, com.rssl.phizic.gate.mobilebank.ClientTariffInfo.class);
		types.put(com.rssl.phizic.web.gate.services.mobilebank.generated.MobilePaymentCardResult.class, com.rssl.phizic.gate.mobilebank.MobilePaymentCardResult.class);
		types.put(com.rssl.phizic.web.gate.services.mobilebank.generated.MessageInfo.class, com.rssl.phizic.web.gate.services.types.MessageInfoImpl.class);
		types.put(com.rssl.phizic.web.gate.services.mobilebank.generated.ERMBPhone.class, com.rssl.phizic.gate.mobilebank.ERMBPhone.class);
		types.put(com.rssl.phizic.utils.PhoneNumber.class, com.rssl.phizic.web.gate.services.mobilebank.generated.PhoneNumber.class);
		types.put(com.rssl.phizic.web.gate.services.mobilebank.generated.PhoneNumber.class, com.rssl.phizic.utils.PhoneNumber.class);
		types.put(com.rssl.phizic.gate.mobilebank.GatePaymentTemplate.class, com.rssl.phizic.web.gate.services.mobilebank.generated.GatePaymentTemplate.class);
		types.put(com.rssl.phizgate.mobilebank.MobileBankCardInfoImpl.class, com.rssl.phizic.web.gate.services.mobilebank.generated.MobileBankCardInfo.class);
		types.put(com.rssl.phizic.gate.ermb.MBKRegistration.class, com.rssl.phizic.web.gate.services.mobilebank.generated.MBKRegistration.class);
		types.put(com.rssl.phizic.gate.csa.MQInfo.class, com.rssl.phizic.web.gate.services.mobilebank.generated.MQInfo.class);
		types.put(com.rssl.phizic.gate.csa.NodeInfo.class, com.rssl.phizic.web.gate.services.mobilebank.generated.NodeInfo.class);
		types.put(com.rssl.phizic.gate.csa.UserInfo.class, com.rssl.phizic.web.gate.services.mobilebank.generated.UserInfoCSA.class);
		types.put(com.rssl.phizic.gate.dictionaries.officies.Code.class, com.rssl.phizic.web.gate.services.mobilebank.generated.CodeOffice.class);
		types.put(com.rssl.phizic.gate.clients.Address.class, com.rssl.phizic.web.gate.services.mobilebank.generated.Address.class);

		types.put(com.rssl.phizic.gate.mobilebank.MobileBankCardStatus.class, null);
		types.put(com.rssl.phizic.gate.mobilebank.MobileBankRegistrationStatus.class, null);
		types.put(com.rssl.phizic.gate.mobilebank.MobileBankTariff.class, null);
		types.put(com.rssl.phizic.common.types.SegmentCodeType.class, null);
		types.put(com.rssl.phizic.gate.clients.ClientStateCategory.class, null);
		types.put(com.rssl.phizic.gate.bankroll.StatusDescExternalCode.class, null);
		types.put(com.rssl.phizic.gate.bankroll.CardType.class, null);
		types.put(com.rssl.phizic.gate.bankroll.AdditionalCardType.class, null);
		types.put(com.rssl.phizic.gate.bankroll.CardState.class, null);
		types.put(com.rssl.phizic.gate.bankroll.AccountState.class, null);
		types.put(com.rssl.phizic.gate.bankroll.CardLevel.class, null);
		types.put(com.rssl.phizic.gate.bankroll.CardBonusSign.class, null);
		types.put(com.rssl.phizic.gate.bankroll.ReportDeliveryType.class, null);
		types.put(com.rssl.phizic.gate.bankroll.ReportDeliveryLanguage.class, null);
		types.put(com.rssl.phizic.common.types.client.LoginType.class, null);
		types.put(com.rssl.phizic.gate.mobilebank.PhoneDisconnectionReason.class, null);
		types.put(com.rssl.phizic.gate.ermb.RegAction.class, null);
		types.put(com.rssl.phizic.gate.ermb.RegTranType.class, null);
		types.put(com.rssl.phizic.gate.ermb.FiltrationReason.class, null);
		types.put(com.rssl.phizic.gate.ermb.MbkTariff.class, null);
		types.put(com.rssl.phizic.gate.mobilebank.MBKRegistrationResultCode.class, null);
		types.put(com.rssl.phizic.common.types.limits.ChannelType.class, null);
		types.put(com.rssl.phizic.gate.mobilebank.SendMessageError.class, null);

		types.put(com.rssl.phizic.common.types.exceptions.IKFLException.class, com.rssl.phizic.web.gate.services.mobilebank.generated.IKFLException.class);
		types.put(com.rssl.phizic.errors.ErrorMessage.class, com.rssl.phizic.web.gate.services.mobilebank.generated.ErrorMessage.class);
		types.put(java.lang.StackTraceElement.class, com.rssl.phizic.web.gate.services.mobilebank.generated.StackTraceElement.class);
		types.put(java.lang.Throwable.class, com.rssl.phizic.web.gate.services.mobilebank.generated.Throwable.class);
		types.put(com.rssl.phizic.web.gate.services.mobilebank.generated.IKFLException.class, com.rssl.phizic.common.types.exceptions.IKFLException.class);
		types.put(com.rssl.phizic.web.gate.services.mobilebank.generated.ErrorMessage.class, com.rssl.phizic.errors.ErrorMessage.class);
		types.put(com.rssl.phizic.web.gate.services.mobilebank.generated.StackTraceElement.class, java.lang.StackTraceElement.class);
		types.put(com.rssl.phizic.web.gate.services.mobilebank.generated.Throwable.class, java.lang.Throwable.class);
	}
}
