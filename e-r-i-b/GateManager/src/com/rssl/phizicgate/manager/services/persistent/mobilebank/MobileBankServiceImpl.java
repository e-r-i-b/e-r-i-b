package com.rssl.phizicgate.manager.services.persistent.mobilebank;

import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.ermb.MBKRegistration;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.mobilebank.*;
import com.rssl.phizic.gate.profile.MBKPhone;
import com.rssl.phizicgate.manager.services.persistent.PersistentServiceBase;

import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
* @author Jatsky
* @ created 28.05.15
* @ $Author$
* @ $Revision$
*/

public class MobileBankServiceImpl extends PersistentServiceBase<MobileBankService> implements MobileBankService
{
	protected MobileBankServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	public GroupResult<String, List<MobileBankRegistration>> getRegistrations(Boolean alternative, String... cardNumbers)
	{
		return delegate.getRegistrations(alternative, cardNumbers);
	}

	public List<MobileBankRegistration3> getRegistrations3(String cardNumber) throws GateException, GateLogicException
	{
		return delegate.getRegistrations3(cardNumber);
	}

	public List<MobileBankRegistration> getRegistrationsPack(Boolean alternative, String... cardNumbers) throws GateLogicException, GateException
	{
		return delegate.getRegistrationsPack(alternative, cardNumbers);
	}

	public GroupResult<MobileBankCardInfo, List<MobileBankTemplate>> getTemplates(Long count, MobileBankCardInfo... cardInfo)
	{
		return delegate.getTemplates(count, cardInfo);
	}

	public void addTemplates(MobileBankCardInfo cardInfo, List<MobileBankTemplate> templates) throws GateException, GateLogicException
	{
		delegate.addTemplates(cardInfo, templates);
	}

	public void removeTemplates(MobileBankCardInfo cardInfo, List<MobileBankTemplate> templates) throws GateException, GateLogicException
	{
		delegate.removeTemplates(cardInfo, templates);
	}

	public void sendSMS(String text, String textToLog, int id, String phone) throws GateException, GateLogicException
	{
		delegate.sendSMS(text, textToLog, id, phone);
	}

	public Map<String, SendMessageError> sendSMSWithIMSICheck(MessageInfo messageInfo, String... toPhones) throws GateException, GateLogicException
	{
		return delegate.sendSMSWithIMSICheck(messageInfo, toPhones);
	}

	public Card getCardByPhone(Client client, String phone) throws GateException, GateLogicException
	{
		return delegate.getCardByPhone(client, phone);
	}

	public UserInfo getClientByPhone(String phone) throws GateException, GateLogicException
	{
		return delegate.getClientByPhone(phone);
	}

	public QuickServiceStatusCode setQuickServiceStatus(String phoneNumber, QuickServiceStatusCode status) throws GateException, GateLogicException
	{
		return delegate.setQuickServiceStatus(phoneNumber, status);
	}

	public QuickServiceStatusCode getQuickServiceStatus(String phoneNumber) throws GateException, GateLogicException
	{
		return delegate.getQuickServiceStatus(phoneNumber);
	}

	public void addRegistration(Client client, String cardNumber, String phoneNumber, String netType, MobileBankTariff packetType) throws GateException, GateLogicException
	{
		delegate.addRegistration(client, cardNumber, phoneNumber, netType, packetType);
	}

	public UserInfo getClientByCardNumber(String cardNumber) throws GateException, GateLogicException
	{
		return delegate.getClientByCardNumber(cardNumber);
	}

	public UserInfo getClientByLogin(String userId) throws GateException, GateLogicException
	{
		return delegate.getClientByLogin(userId);
	}

	public String getMobileContact(String phoneNumbersToFind) throws GateException, GateLogicException
	{
		return delegate.getMobileContact(phoneNumbersToFind);
	}

	public void sendOfferMessageSMS(String text, String textLog, Long id, String phone) throws GateException, GateLogicException
	{
		delegate.sendOfferMessageSMS(text, textLog, id, phone);
	}

	public List<AcceptInfo> getOfferConfirm() throws GateException, GateLogicException
	{
		return delegate.getOfferConfirm();
	}

	public void sendOfferQuit(Long id) throws GateException, GateLogicException
	{
		delegate.sendOfferQuit(id);
	}

	public Set<String> getCardsByPhone(String phone) throws GateException, GateLogicException
	{
		return delegate.getCardsByPhone(phone);
	}

	public Set<String> getCardsByPhoneViaReportDB(String phone) throws GateException, GateLogicException
	{
		return delegate.getCardsByPhoneViaReportDB(phone);
	}

	public BeginMigrationResult beginMigrationErmb(Set<MbkCard> cards, Set<String> phoneMigrateNumbers, Set<String> phoneDeleteNumbers, MigrationType migrationType) throws GateException, GateLogicException
	{
		return delegate.beginMigrationErmb(cards, phoneMigrateNumbers, phoneDeleteNumbers, migrationType);
	}

	public List<CommitMigrationResult> commitMigrationErmb(Long migrationId) throws GateException, GateLogicException
	{
		return delegate.commitMigrationErmb(migrationId);
	}

	public void rollbackMigration(Long migrationId) throws GateException, GateLogicException
	{
		delegate.rollbackMigration(migrationId);
	}

	public void reverseMigration(Long migrationId, ClientTariffInfo clientTariffInfo) throws GateException, GateLogicException
	{
		delegate.reverseMigration(migrationId, clientTariffInfo);
	}

	public List<DisconnectedPhoneResult> getDisconnectedPhones(int maxResults) throws GateException, GateLogicException
	{
		return delegate.getDisconnectedPhones(maxResults);
	}

	public void updateDisconnectedPhonesState(List<Integer> processedPhones) throws GateException, GateLogicException
	{
		delegate.updateDisconnectedPhonesState(processedPhones);
	}

	public List<P2PRequest> getMobilePaymentCardRequests() throws GateException, GateLogicException
	{
		return delegate.getMobilePaymentCardRequests();
	}

	public void sendMobilePaymentCardResult(MobilePaymentCardResult cardResult) throws GateException, GateLogicException
	{
		delegate.sendMobilePaymentCardResult(cardResult);
	}

	public List<MBKRegistration> getMbkRegistrationsForErmb() throws GateException, GateLogicException
	{
		return delegate.getMbkRegistrationsForErmb();
	}

	public void confirmMbRegistrationsLoading(List<Long> regIds) throws GateException, GateLogicException
	{
		delegate.confirmMbRegistrationsLoading(regIds);
	}

	public void sendMbRegistrationProcessingResult(long id, MBKRegistrationResultCode resultCode, String errorDescr) throws GateException, GateLogicException
	{
		delegate.sendMbRegistrationProcessingResult(id, resultCode, errorDescr);
	}

	public void updateErmbPhonesInMb(List<ERMBPhone> ermbPhones) throws GateException, GateLogicException
	{
		delegate.updateErmbPhonesInMb(ermbPhones);
	}

	public List<MBKPhone> getPhonesForPeriod(Calendar start) throws GateException, GateLogicException
	{
		return delegate.getPhonesForPeriod(start);
	}

	public String getCardNumberByPhone(Client client, String phone) throws GateException, GateLogicException
	{
		return delegate.getCardNumberByPhone(client, phone);
	}

	public List<UESIMessage> getUESIMessagesFromMBK() throws GateException, GateLogicException
	{
		return delegate.getUESIMessagesFromMBK();
	}

	public void confirmUESIMessages(List<Long> ids) throws GateException, GateLogicException
	{
		delegate.confirmUESIMessages(ids);
	}

	public Map<String, SendMessageError> sendIMSICheck(String... phones) throws GateException, GateLogicException
	{
		return delegate.sendIMSICheck(phones);
	}

	public Set<String> getRegPhonesByCardNumbers(List<String> cardNumbers, GetRegistrationMode mode) throws GateException, GateLogicException
	{
		return delegate.getRegPhonesByCardNumbers(cardNumbers, mode);
	}

	public List<MobileBankRegistration> getRegistrations4(String cardNumber, GetRegistrationMode mode) throws GateException, GateLogicException, SystemException
	{
		return delegate.getRegistrations4(cardNumber, mode);
	}

	public Boolean generatePassword(String cardNumber) throws GateException, GateLogicException
	{
		return delegate.generatePassword(cardNumber);
	}

	public Boolean generatePasswordForErmbClient(String cardNumber, String phoneNumber) throws GateException, GateLogicException
	{
		return delegate.generatePasswordForErmbClient(cardNumber, phoneNumber);
	}

	public GroupResult<String, List<GatePaymentTemplate>> getPaymentTemplates(String... cardNumbers)
	{
		return delegate.getPaymentTemplates(cardNumbers);
	}

	public GatePaymentTemplate getPaymentTemplate(String externalId) throws GateException, GateLogicException
	{
		return delegate.getPaymentTemplate(externalId);
	}
}
