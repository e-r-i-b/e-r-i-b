package com.rssl.phizicgate.wsgate.services.mobilebank;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jatsky
 * @ created 21.05.15
 * @ $Author$
 * @ $Revision$
 */

public class MobileBankServiceTypesCorrelation
{
	public static final Map<Class, Class> types  = new HashMap<Class,Class>();

	static
	{
		types.put(com.rssl.phizicgate.wsgate.services.mobilebank.generated.GroupResult.class, com.rssl.phizic.common.types.transmiters.GroupResult.class);
		types.put(com.rssl.phizicgate.wsgate.services.mobilebank.generated.MobileBankRegistration.class, com.rssl.phizgate.mobilebank.MobileBankRegistrationImpl.class);
		types.put(com.rssl.phizicgate.wsgate.services.mobilebank.generated.MobileBankRegistration3.class, com.rssl.phizgate.mobilebank.MobileBankRegistration3Impl.class);
		types.put(com.rssl.phizgate.mobilebank.MobileBankCardInfoImpl.class, com.rssl.phizicgate.wsgate.services.mobilebank.generated.MobileBankCardInfo.class);
		types.put(com.rssl.phizicgate.wsgate.services.mobilebank.generated.MobileBankTemplate.class, com.rssl.phizgate.mobilebank.MobileBankTemplateImpl.class);
		types.put(com.rssl.phizicgate.wsgate.services.mobilebank.generated.InfoCardImpl.class, com.rssl.phizic.gate.mobilebank.InfoCardImpl.class);
		types.put(com.rssl.phizic.gate.mobilebank.InfoCardImpl.class,com.rssl.phizicgate.wsgate.services.mobilebank.generated.InfoCardImpl.class);
		types.put(com.rssl.phizicgate.wsgate.services.mobilebank.generated.MobileBankCardInfo.class, com.rssl.phizicgate.wsgate.services.types.MobileBankCardInfoImpl.class);
		types.put(com.rssl.phizic.gate.mobilebank.MobileBankTemplate.class, com.rssl.phizicgate.wsgate.services.mobilebank.generated.MobileBankTemplate.class);
		types.put(com.rssl.phizic.gate.mobilebank.MobileBankCardInfo.class, com.rssl.phizicgate.wsgate.services.mobilebank.generated.MobileBankCardInfo.class);
		types.put(com.rssl.phizic.gate.mobilebank.MessageInfo.class, com.rssl.phizicgate.wsgate.services.mobilebank.generated.MessageInfo.class);
		types.put(com.rssl.phizic.gate.clients.Client.class, com.rssl.phizicgate.wsgate.services.mobilebank.generated.Client.class);
		types.put(com.rssl.phizic.gate.clients.ClientState.class, com.rssl.phizicgate.wsgate.services.mobilebank.generated.ClientState.class);
		types.put(com.rssl.phizicgate.wsgate.services.mobilebank.generated.Card.class, com.rssl.phizicgate.wsgate.services.types.CardImpl.class);
		types.put(com.rssl.phizicgate.wsgate.services.mobilebank.generated.UserInfo.class, com.rssl.phizicgate.wsgate.services.types.UserInfoImpl.class);
		types.put(com.rssl.phizicgate.wsgate.services.mobilebank.generated.AcceptInfo.class, com.rssl.phizic.gate.mobilebank.AcceptInfo.class);
		types.put(com.rssl.phizicgate.wsgate.services.mobilebank.generated.BeginMigrationResult.class, com.rssl.phizic.gate.mobilebank.BeginMigrationResult.class);
		types.put(com.rssl.phizicgate.wsgate.services.mobilebank.generated.CommitMigrationResult.class, com.rssl.phizic.gate.mobilebank.CommitMigrationResult.class);
		types.put(com.rssl.phizic.gate.mobilebank.ClientTariffInfo.class, com.rssl.phizicgate.wsgate.services.mobilebank.generated.ClientTariffInfo.class);
		types.put(com.rssl.phizic.gate.mobilebank.MobilePaymentCardResult.class, com.rssl.phizicgate.wsgate.services.mobilebank.generated.MobilePaymentCardResult.class);
		types.put(com.rssl.phizicgate.wsgate.services.mobilebank.generated.DisconnectedPhoneResult.class, com.rssl.phizic.gate.mobilebank.DisconnectedPhoneResult.class);
		types.put(com.rssl.phizicgate.wsgate.services.mobilebank.generated.P2PRequest.class, com.rssl.phizic.gate.mobilebank.P2PRequest.class);
		types.put(com.rssl.phizic.gate.mobilebank.ERMBPhone.class, com.rssl.phizicgate.wsgate.services.mobilebank.generated.ERMBPhone.class);
		types.put(com.rssl.phizicgate.wsgate.services.mobilebank.generated.MBKPhone.class, com.rssl.phizic.gate.profile.MBKPhone.class);
		types.put(com.rssl.phizicgate.wsgate.services.mobilebank.generated.UESIMessage.class, com.rssl.phizicgate.wsgate.services.types.UESIMessageImpl.class);
		types.put(com.rssl.phizicgate.wsgate.services.mobilebank.generated.GatePaymentTemplate.class, com.rssl.phizgate.mobilebank.GatePaymentTemplateImpl.class);
		types.put(com.rssl.phizic.utils.PhoneNumber.class, com.rssl.phizicgate.wsgate.services.mobilebank.generated.PhoneNumber.class);
		types.put(com.rssl.phizicgate.wsgate.services.mobilebank.generated.MBKRegistration.class, com.rssl.phizic.gate.ermb.MBKRegistration.class);
		types.put(com.rssl.phizicgate.wsgate.services.mobilebank.generated.MQInfo.class, com.rssl.phizic.gate.csa.MQInfo.class);
		types.put(com.rssl.phizicgate.wsgate.services.mobilebank.generated.NodeInfo.class, com.rssl.phizic.gate.csa.NodeInfo.class);
		types.put(com.rssl.phizicgate.wsgate.services.mobilebank.generated.UserInfoCSA.class, com.rssl.phizic.gate.csa.UserInfo.class);
		types.put(com.rssl.phizicgate.wsgate.services.mobilebank.generated.CodeOffice.class, com.rssl.phizgate.common.services.types.CodeImpl.class);
		types.put(com.rssl.phizic.gate.clients.Address.class, com.rssl.phizicgate.wsgate.services.mobilebank.generated.Address.class);

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

		types.put(com.rssl.phizic.common.types.exceptions.IKFLException.class, com.rssl.phizicgate.wsgate.services.mobilebank.generated.IKFLException.class);
		types.put(com.rssl.phizic.errors.ErrorMessage.class, com.rssl.phizicgate.wsgate.services.mobilebank.generated.ErrorMessage.class);
		types.put(java.lang.StackTraceElement.class, com.rssl.phizicgate.wsgate.services.mobilebank.generated.StackTraceElement.class);
		types.put(java.lang.Throwable.class, com.rssl.phizicgate.wsgate.services.mobilebank.generated.Throwable.class);
		types.put(com.rssl.phizicgate.wsgate.services.mobilebank.generated.IKFLException.class, com.rssl.phizic.common.types.exceptions.IKFLException.class);
		types.put(com.rssl.phizicgate.wsgate.services.mobilebank.generated.ErrorMessage.class, com.rssl.phizic.errors.ErrorMessage.class);
		types.put(com.rssl.phizicgate.wsgate.services.mobilebank.generated.StackTraceElement.class, java.lang.StackTraceElement.class);
		types.put(com.rssl.phizicgate.wsgate.services.mobilebank.generated.Throwable.class, java.lang.Throwable.class);
	}
}
