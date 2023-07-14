package com.rssl.phizic.gate;

import com.rssl.phizic.gate.cache.proxy.Cachable;
import com.rssl.phizic.gate.cache.proxy.composers.BilingCacheKeyComposer;
import com.rssl.phizic.gate.cache.proxy.composers.OfficeCacheKeyComposer;
import com.rssl.phizic.gate.cache.proxy.composers.StringCacheKeyComposer;
import com.rssl.phizic.gate.dictionaries.billing.Billing;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.utils.InputMode;

/**
 * @author Krenev
 * @ created 22.04.2009
 * @ $Author$
 * @ $Revision$
 */

/**
 * ��������� ���������� � ������������ �����.
 */
public interface GateInfoService extends Service
{
	/**
	 * ������������� ������� �������
	 *
	 * @param office ����, ��� �������� ���������� �������� ����������.
	 * @return ������, ���������������� ������� �������
	 * @exception GateException
	 * @exception GateLogicException
	 */
	@Cachable(keyResolver= OfficeCacheKeyComposer.class,linkable = false, name = "GateInfo.UID")
	String getUID(Office office) throws GateException, GateLogicException;

	/**
	 * ����� �� ������� ������� ��������� ����� �� ������������.
	 *
	 * @param office ����, ��� �������� ���������� �������� ����������.
	 * @return true - ����� ����������� ������
	 * @exception GateException
	 * @exception GateLogicException
	 */
	@Cachable(keyResolver= OfficeCacheKeyComposer.class,linkable = false, name = "GateInfo.needChargeOff")
	Boolean isNeedChargeOff(Office office) throws GateException, GateLogicException;

	/**
	 * ����� �� ������� ������� ������������ ���������� � ������������ ��� �������.
	 *
	 * @param office ����, ��� �������� ���������� �������� ����������.
	 * @return true - ������������ �������������
	 * @exception GateException
	 * @exception GateLogicException
	 */
	@Cachable(keyResolver= OfficeCacheKeyComposer.class,linkable = false, name = "GateInfo.clientImportEnable")
	Boolean isClientImportEnable(Office office) throws GateException, GateLogicException;

	/**
	 * ��������� �� ����������� ������������, ��������� � ������� ������������ �� ������� �������
	 *
	 * @param office ����, ��� �������� ���������� �������� ����������.
	 * @return true - ����� �������� ������ ���������� ����������������(������������ ������������) �� ������� �������
	 * @exception GateException
	 * @exception GateLogicException
	 */
	@Cachable(keyResolver= OfficeCacheKeyComposer.class,linkable = false, name = "GateInfo.registrationEnable")
	Boolean isRegistrationEnable(Office office) throws GateException, GateLogicException;


	/**
	 * ��������� �� ����������� �������� �� ������� ������� ����� ��������� ��������� �������.
	 *
	 * @param office ����, ��� �������� ���������� �������� ����������.
	 * @return true - ���������� ����������� ������� ����� ��������� ��������� �������
	 * @exception GateException
	 * @exception GateLogicException
	 */
	@Cachable(keyResolver= OfficeCacheKeyComposer.class,linkable = false, name = "GateInfo.needAgrementCancellation")
	Boolean isNeedAgrementCancellation(Office office) throws GateException, GateLogicException;

	/**
	 * ������� ������� ����� ������������ ���������� � ������ �����.
	 *
	 * @param office ����, ��� �������� ���������� �������� ����������.
	 * @return true - �������� ��������� ������ �����.
	 * @exception GateException
	 * @exception GateLogicException
	 */
	@Cachable(keyResolver= OfficeCacheKeyComposer.class,linkable = false, name = "GateInfo.currencyRateAvailable")
	Boolean isCurrencyRateAvailable(Office office) throws GateException, GateLogicException;

	/**
	 * ������� ������� ����� ���������� ��������.
	 *
	 * @param office ����, ��� �������� ���������� �������� ����������.
	 * @return true - ���� ����������� ��������.
	 * @exception GateException
	 * @exception GateLogicException
	 */
	@Cachable(keyResolver= OfficeCacheKeyComposer.class,linkable = false, name = "GateInfo.paymentCommissionAvailable")
	Boolean isPaymentCommissionAvailable(Office office) throws GateException, GateLogicException;

	/**
	  * ����������� ������� ����� ���������� ��������.
	  * @param billing �������, ��� �������� ���������� �������� ����������.
	  * @return true - ����������� ��������.
	  * @exception GateException
	  * @exception GateLogicException
	  */
	 @Cachable(keyResolver= BilingCacheKeyComposer.class,linkable = false, name = "GateInfo.paymentCommissionAvailableForBilling")
	 Boolean isPaymentCommissionAvailable(Billing billing) throws GateException, GateLogicException;

	/**
	 * ������� ������� ����� ������������ ���������� � ������� ���������.
	 *
	 * @param office ����, ��� �������� ���������� �������� ����������.
	 * @return true - �������� ��������� �������� ���������. (�� ����, ���������� �� CalendarGateService � �����)
	 * @exception GateException
	 * @exception GateLogicException
	 */
	@Cachable(keyResolver= OfficeCacheKeyComposer.class,linkable = false, name = "GateInfo.calendarAvailable")
	Boolean isCalendarAvailable(Office office) throws GateException, GateLogicException;

	/**
	 * C����� ��������� ������ ������������ (IMPORT - ����� ����� ���� �������� �� ������� �������, MANUAL - ����� �������� � ����, OFF - ����� ����������������)
	 *
	 * @param office ����, ��� �������� ���������� �������� ����������.
	 * @return ������ ��������� ������ (IMPORT - ������, MANUAL - ���� �������, OFF - ����� ���������������)
	 * @exception GateException
	 * @exception GateLogicException
	 */
	@Cachable(keyResolver= OfficeCacheKeyComposer.class,linkable = false, name = "GateInfo.accountInputMode")
	InputMode getAccountInputMode(Office office) throws GateException, GateLogicException;

	/**
	 * C����� ��������� ���� ������������ (IMPORT - ����� ����� ���� �������� �� ������� �������, MANUAL - ����� �������� � ����, OFF - ����� ����������������)
	 *
	 * @param office ����, ��� �������� ���������� �������� ����������.
	 * @return ������ ��������� ���� (IMPORT - ������, MANUAL - ���� �������, OFF - ����� ���������������)
	 * @exception GateException
	 * @exception GateLogicException
	 */
	@Cachable(keyResolver= OfficeCacheKeyComposer.class,linkable = false, name = "GateInfo.cardInputMode")
	InputMode getCardInputMode(Office office) throws GateException, GateLogicException;

	/**
	 * ����� �� ����� ��������� ������ �� ������� �������
	 *
	 * @param office ����, ��� �������� ���������� �������� ����������.
	 * @return true - ��������� ������ ����� �� ������� �������
	 * @exception GateException
	 * @exception GateLogicException
	 */
	@Cachable(keyResolver= OfficeCacheKeyComposer.class,linkable = false, name = "GateInfo.delayedPaymentNeedSend")
	Boolean isDelayedPaymentNeedSend(Office office) throws GateException, GateLogicException;

	/**
	 * ������������ �� ���� �������� ������
	 * @param office ����, ��� �������� ���������� �������� ����������.
	 * @return true - ������������
	 * @throws GateException
	 * @throws GateLogicException
	 */
	@Cachable(keyResolver= OfficeCacheKeyComposer.class,linkable = false, name = "GateInfo.officesHierarchySupported")
	Boolean isOfficesHierarchySupported(Office office) throws GateException, GateLogicException;

	/**
	 * ������������ �� ���� ����� ������������� �������� ��� �������� �������
	 * @param office ����, ��� �������� ���������� �������� ����������.
	 * @return true - ������������
	 * @throws GateException
	 * @throws GateLogicException
	 */
	@Cachable(keyResolver= OfficeCacheKeyComposer.class,linkable = false, name = "GateInfo.paymentRecallSupported")
	Boolean isPaymentsRecallSupported(Office office) throws GateException, GateLogicException;

   /**
     * ������������ �� ������� ��������� ����������� ��������� ����������.
    *
     * @param billing �������, ��� �������� ���������� �������� ����������.
     * @return true - ����������� ��������� ����������� ��������� ����������.
     * @exception GateException
     * @exception GateLogicException
     */
    @Cachable(keyResolver= BilingCacheKeyComposer.class,linkable = false, name = "GateInfo.recipientExtendedAttrAvailable")
    Boolean isRecipientExtedendAttributesAvailable(Billing billing) throws GateException, GateLogicException;

    /**
     * �������� ��������� ��������� ����������� � ������� �������
     *
     * @param billing �������, ��� �������� ���������� �������� ����������.
     * @return ��������� ��������� ����������� � ������� �������
     * @exception GateException
     * @exception GateLogicException
     */
    @Cachable(keyResolver= BilingCacheKeyComposer.class,linkable = false, name = "GateInfo.configuration")
    GateConfiguration getConfiguration(Billing billing)  throws GateException, GateLogicException;

	/**
	 * ���������� ������� ������������� ����� ���������� ���������� ����� �������� � ���
	 * @param billing �������
	 * @return true - �����, false - �� �����: �������� ��� ���������� �������� �������.
	 * @throws GateException
	 * @throws GateLogicException
	 */
	@Cachable(keyResolver= BilingCacheKeyComposer.class,linkable = false, name = "GateInfo.needTwoPhaseTransactionForBilling")
	Boolean needTwoPhaseTransaction(Billing billing)  throws GateException, GateLogicException;

	/**
	 * ���������� ������� ������������� ����� ���������� ���������� ����� �������� � ���
	 * @param receiverCodePoint ������������� ���������� � ��������
	 * @return true - �����, false - �� �����: �������� ��� ���������� �������� �������.
	 * @throws GateException
	 * @throws GateLogicException
	 */
	@Cachable(keyResolver= StringCacheKeyComposer.class,linkable = false, name = "GateInfo.neeadTwoPhaseTransaction")
	Boolean needTwoPhaseTransaction(String receiverCodePoint)  throws GateException, GateLogicException;

	/**
	 * ������������ �� ������� ������������ �����������
	 *
	 * @param billing �������, ��� �������� ���������� �������� ����������.
	 * @return true - ����������� ��������� ������������ �����������
	 * @exception GateException
	 * @exception GateLogicException
	 */
	@Cachable(keyResolver= BilingCacheKeyComposer.class,linkable = false, name = "GateInfo.personalRecipientAvailable")
	Boolean isPersonalRecipientAvailable(Billing billing) throws GateException, GateLogicException;
}
