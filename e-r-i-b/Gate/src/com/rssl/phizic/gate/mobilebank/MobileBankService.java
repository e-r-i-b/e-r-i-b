package com.rssl.phizic.gate.mobilebank;

import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.ermb.MBKRegistration;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.profile.MBKPhone;

import java.sql.ResultSet;
import java.util.*;

/**
 * @author Erkin
 * @ created 14.04.2010
 * @ $Author$
 * @ $Revision$
 * @deprecated ���������� �� ���
 */

/**
 * ������ ��� ��������� � �������������� ���������� � �������� ��������
 * �� �� "��������� ����" (��)
 */
public interface MobileBankService extends Service
{

	/**
	 * ��������� ����������� ����� � �� ��
	 * @param alternative ������������ �� �������������� ������� ��������� �����������
	 * false - ������������ GetRegistrations, true - ������������ GetRegistrations � GetRegistrations2, null - ������������ GetRegistrations2
	 * @param cardNumbers ������ ������� ����,
	 *  �� ������� ���������� �������� �����������
	 * @return ����������� ����� � �� ��
	 */
	GroupResult<String, List<MobileBankRegistration>> getRegistrations(Boolean alternative, String... cardNumbers);

	/**
	 * ��������� ����������� ����� � �� ��. ������������ �������� ��������� getRegistrations3
	 * @param cardNumber ����� �����
	 * @return ������ ����������� �����
	 */
	List<MobileBankRegistration3> getRegistrations3(String cardNumber) throws GateException, GateLogicException;

	/**
	 * ��������� ����� ����������� �� ������ � �� ��
	 * @param alternative ������������ �� �������������� ������� ��������� �����������
	 * false - ������������ GetRegistrations, true - ������������ GetRegistrations � GetRegistrations2, null - ������������ GetRegistrations2
	 * @param cardNumbers ������ ������� ����,
	 *  �� ������� ���������� �������� �����������
	 * @return ����������� ����� � �� ��
	 */
	List<MobileBankRegistration> getRegistrationsPack(Boolean alternative, String... cardNumbers) throws GateException, GateLogicException;

	/**
	 * �������� ������ �������� �������� �� ����� � ������ �������� �������
	 * @param count ���-�� �������� (�� ������ �����),
	 *  ������� ���������� �������� ��� null, ���� ����� ���
	 * @param cardInfo ����� � ����� ��������
	 * @return ������ �������� ��������
	 */
	GroupResult<MobileBankCardInfo, List<MobileBankTemplate>>
	getTemplates(Long count, MobileBankCardInfo... cardInfo);

	/**
	 * �������� ������� � ������ �������� �������� ���������� �����
	 * @param cardInfo ����� � ����� ��������
	 * @param templates ������ �������� ��������, ������� ����� ��������
	 * @exception GateException
	 */
	void addTemplates(MobileBankCardInfo cardInfo, List<MobileBankTemplate> templates) throws GateException, GateLogicException;

	/**
	 * ������� ������� �� ������ �������� �������� ���������� �����
	 * @param cardInfo ����� � ����� ��������
	 * @param templates ������ �������� ��������, ������� ����� �������
	 * @exception GateException
	 */
	void removeTemplates(MobileBankCardInfo cardInfo, List<MobileBankTemplate> templates) throws GateException, GateLogicException;

	/**
	 * ��������� ��� �� ������ ��������
	 * @param text ����� ���������
	 * @param textToLog ����� ��� ����������
	 * @param id ���������� ������������� ���������
	 * @param phone ����� �������� � �������
	 *  PhoneNumberValidator.REGEXP_FOR_PHONE_NUMBER
	 * @throws GateException
	 */
	void sendSMS(String text, String textToLog, int id, String phone) throws GateException, GateLogicException;

	/**
	 * �������� ��� � ��������� IMSI �� ������ toPhones
	 * @param messageInfo ���������� � ���������
	 * @param toPhones ��������
	 * @return ��� (����� �������� - ������)
	 */
	Map<String, SendMessageError> sendSMSWithIMSICheck(MessageInfo messageInfo, String... toPhones) throws GateException, GateLogicException;

	/**
	 * �������� �����
	 * @param client ������, ������� ��� � ��������� �������� ������������� ����������
	 * @param phone ����� �������� (10 ����, ������ ������ ��������� 7)
	 * @return �����, ��� null
	 */
	Card getCardByPhone(Client client,String phone)  throws GateException, GateLogicException;

	/**
	 * �������� ���������� � ������� �� ������ ��������
	 * @param phone ����� �������� (10 ����, ������ ������ ��������� 7)
	 * @return ���������� � �������, ��� null
	 */
	UserInfo getClientByPhone(String phone)  throws GateException, GateLogicException;

	/**
	 * ������������ ��� ����� ������� ������� �������� ��� ���������� ������ ��������.
	 * ����� ������� ������� QUICK_SERVICE_STATUS_REPEAT_QUERY ��� QUICK_SERVICE_STATUS_UNKNOWN ��� ������� �� ������
	 * �� ����� ��������� �������. 
	 *
	 * @param phoneNumber ����� ��������
	 * @param status ����� ������
	 * @return QuickServiceStatusCode
	 */
	QuickServiceStatusCode setQuickServiceStatus(String phoneNumber, QuickServiceStatusCode status) throws GateException, GateLogicException;

	/**
	 *
	 * ������������ ��� ��������� ������� ������� �������� ��� ���������� ������ ��������
	 *
	 * @param phoneNumber ����� �������� ��� �������� ������� ������
	 * @return QuickServiceStatusCode
	 */
	QuickServiceStatusCode getQuickServiceStatus(String phoneNumber) throws GateException, GateLogicException;

	/**
	 * ����������� ������� � ���������� ����� (�������� ���������������� �����)
	 * @param client ������
	 * @param cardNumber ����� �����
	 * @param phoneNumber ����� ��������
	 * @param packetType ��� ������
	 * @param netType ��� ���������  (000 - ���, 001 � ������, 002 - �������)
	 * @throws GateException
	 */
	void addRegistration(Client client, String cardNumber, String phoneNumber, String netType, MobileBankTariff packetType) throws GateException, GateLogicException;

	/**
	 * ��������� �������� � ������� �� ������ ��� �����
	 *
	 * @param cardNumber ����� �����
	 * @return ���������� � ������������
	 */
	UserInfo getClientByCardNumber(String cardNumber) throws GateException, GateLogicException;

	/**
	 * �������� ���������� � ������������ �� ������ iPas
	 * @param userId ����� iPas
	 * @return ���������� � ���������e�� ��� null, ���� ������������ �� ������
	 * @throws GateException
	 */
	UserInfo getClientByLogin(String userId) throws GateException, GateLogicException;

	/**
	 * ����������� ����������� � �� �� ������� ���������
	 * @param phoneNumbersToFind ������ � ����������, ������������ ";", ������� ���������� ��������� �� ����������� � ��
	 * @return phoneNumbersFound ������ � ����������, ������������ ";", ������� ���������� � ��
	 * @throws GateException
	 */
	String getMobileContact(String phoneNumbersToFind) throws GateException, GateLogicException;

	/**
	 * �������� ��������� � ���������� ��������-������.
	 *
	 * @param text ����� ���������.
	 * @param textLog ����� ��������� ��� ������������.
	 * @param id ���������� ������������� ���������.
	 * @param phone ����� ��������.
	 */
	void sendOfferMessageSMS(String text, String textLog, Long id, String phone) throws GateException, GateLogicException;

	/**
	 * @return ������ �������������� �������������� �������.
	 * @throws GateException
	 */
	List<AcceptInfo> getOfferConfirm() throws GateException, GateLogicException;

	/**
	 * @param id ���������� ������������� ���������.
	 * @throws GateException
	 */
	void sendOfferQuit(Long id) throws GateException, GateLogicException;

	/**
	 * �������� ����� �� ������ ��������
	 * @param phone ����� �������� (10 ����, ������ ������ ��������� 7)
	 * @return �����, ��� null
	 */
	Set<String> getCardsByPhone(String phone) throws GateException, GateLogicException;

	/**
	 * �������� ����� �� ������ ��������
	 * @param phone ����� �������� (10 ����, ������ ������ ��������� 7)
	 * @return �����, ��� null
	 */
	Set<String> getCardsByPhoneViaReportDB(String phone) throws GateException, GateLogicException;

	/**
	 *  �� �������� ��� ������ ����� ������
     �	����������� �� ��� � ���������� ��������� �������� ��� ������ ������� �������
     �	�������� �� �� ��� ������ ���������� � ������������ ��� ��������� � �������� �������.
     �	����������� � �� ��� �������� ����� ����������� ���, ������� �� �������������� �����������
     * @param cards ������ ���� ���� �������
	 *      �	�������� �����, ���������� ������� �������� ������
	 *		�	���. ����� ���������� �������� ������ ����������,
	 *		�	���. ����� ���������� �������� ������ ����
	 *		�	���. ����� ���������� ������� ��������� ������������ �������
	 * @param phoneMigrateNumbers ������ ���������. ����������� ���������� ��� �������� �������� ��������
	 * @param phoneDeleteNumbers ������ ��������� ��� ������� ����� ��������� ��� �����������. ���������� ������ ��� ��������� ��������.
	 * @param migrationType ��� ��������
	 * @return id �������� � ������ ������ ��� (����� ���� ������)
	 * @throws GateException
	 */
	BeginMigrationResult beginMigrationErmb(Set<MbkCard> cards, Set<String> phoneMigrateNumbers, Set<String> phoneDeleteNumbers, MigrationType migrationType) throws GateException, GateLogicException;

	/**
	 * �� �������� ��� ������ ����� ������ ���������� �� ���, ��� ��������������� ��� �������� BEGIN, ��� ������ ������������ ����������, ����������� ����� ���� ������� �� �� ��� � ���������� � �����.
	 * @param migrationId ���������� �� ������� ��������
	 * @return ���������� �� �������� ��������� ����������� ����� �� ������ ������ ���
	 */
	List<CommitMigrationResult> commitMigrationErmb(Long migrationId) throws GateException, GateLogicException;

	/**
	 * �� �������� ��� ������ ����� ���������� ���������� �� ���, ��� ��������������� �����, ��� ������ ������������ ����������, �����������, ����� ���� ��������������. �������� ��� ��� �������� ����� ��������� � �������
	 * @param migrationId  ���������� �� ������� ��������
	 * @throws GateException
	 */
	void rollbackMigration(Long migrationId) throws GateException, GateLogicException;

	/**
	 * �� �������� ��� ������ ����� ���������� ����� ���������� � �� ��� �����������, ��� ������� ����� �� ��� �������� COMMIT ��������.
	 * @param migrationId ���������� �� ������� ��������
	 * @param clientTariffInfo ���������� � ��������� �������� ����������� ����� �������
	 * @throws GateException
	 */
	void reverseMigration(Long migrationId, ClientTariffInfo clientTariffInfo) throws GateException, GateLogicException;

	/**
	 * �������� ��������, ����������� ���
	 * @param maxResults ������������ ���������� �������
	 * @return - ������ ����������� ���������
	 * @throws GateException
	 */
	List<DisconnectedPhoneResult> getDisconnectedPhones(int maxResults) throws GateException, GateLogicException;

	/**
	 * �������� ������ ����������� ���������
	 * @param processedPhones - ��������
	 */
	void updateDisconnectedPhonesState(List<Integer> processedPhones) throws GateException, GateLogicException;

	/**
	 * ��������� �������� ���, �� ������� ����� ���������� ����� �������� (P2P)
	 * @throws GateException
	 */
	List<P2PRequest> getMobilePaymentCardRequests() throws GateException, GateLogicException;

	/**
	 * �������� ������� �� ������ ��� � ��������� ������� ���� �� ������ ��������
	 * @throws GateException
	 */
	void sendMobilePaymentCardResult(MobilePaymentCardResult cardResult) throws GateException, GateLogicException;

	/**
	 * �������� ������ ���->���� �� ��������� ���������� ������ "��������� ����" � ������� ����.
	 * ������ ���������� ����������� � ��� � ����� (�������� �� ����)
	 * @return ���������� �� �������� ��� ��� null, ���� ����� ������ ���
	 */
	List<MBKRegistration> getMbkRegistrationsForErmb() throws GateException, GateLogicException;

	/**
	 * ������������� ���� ��������� ����������� �� ���
	 * @param regIds - ������ id �������� �����������
	 * @throws GateException
	 */
	void confirmMbRegistrationsLoading(List<Long> regIds) throws GateException, GateLogicException;

	/**
	 * ��������� ��������� ��������� � ���� �����������, ����������� �� ���
	 * @param id - ������������� �����������
	 * @param resultCode - ��� ���������� ���������
	 * @param errorDescr - �������� ������
	 * @throws GateException
	 */
	void sendMbRegistrationProcessingResult(long id, MBKRegistrationResultCode resultCode, String errorDescr) throws GateException, GateLogicException;

	/**
	 * ���������� � �� ��� ������� ��������� ������������������ � ����
	 * @param ermbPhones - ������ ����������� ���������
	 * @throws GateException
	 */
	void updateErmbPhonesInMb(List<ERMBPhone> ermbPhones) throws GateException, GateLogicException;

	/**
	 * ��������� ������ ��������� ������� ��������� �� �����.
	 *
	 * @param start ���� ������.
	 * @return ������ ��������� ���������.
	 */
	List<MBKPhone> getPhonesForPeriod(Calendar start) throws GateException, GateLogicException;

	/**
	 * �������� ����� ����� �� ��������
	 * @param client ������
	 * @param phone ����� ��������
	 * @return ����� �����
	 * @throws GateException
	 */
	String getCardNumberByPhone(Client client, String phone) throws GateException, GateLogicException;

	/**
	 * �������� ��������� ���������������� ���������� ���
	 * @return ������ ���������
	 * @throws GateException
	 */
	List<UESIMessage> getUESIMessagesFromMBK() throws GateException, GateLogicException;

	/**
	 * ����������� ��������� ��������� ���������������� ���������� ���
	 * @param ids �������������� ���������
	 * @throws GateException
	 */
	void confirmUESIMessages(List<Long> ids) throws GateException, GateLogicException;

	public Map<String, SendMessageError> sendIMSICheck(String... phones) throws GateException, GateLogicException;

	public Set<String> getRegPhonesByCardNumbers(List<String> cardNumbers, GetRegistrationMode mode) throws GateException, GateLogicException;

	public List<MobileBankRegistration> getRegistrations4(String cardNumber, GetRegistrationMode mode) throws GateException, GateLogicException, SystemException;

	public Boolean generatePassword(String cardNumber) throws GateException, GateLogicException;

	public Boolean generatePasswordForErmbClient(String cardNumber,  String  phoneNumber) throws GateException, GateLogicException;

	/**
	 * ���������� ������ �������� �� ������� ����
	 * @param cardNumbers - ������ ������� ����, �� ������� ����� �������� ������ ��������
	 * @return ������ �������� ��������
	 */
	GroupResult<String, List<GatePaymentTemplate>> getPaymentTemplates(String... cardNumbers);

	/**
	 * ���������� ������ ������� �� ��� ID �� ������� �������
	 * @param externalId - ID ������� �� ������� �������
	 * @return ������ �������
	 *  ��� null, ���� �� ������
	 */
	GatePaymentTemplate getPaymentTemplate(String externalId) throws GateException, GateLogicException;
}
