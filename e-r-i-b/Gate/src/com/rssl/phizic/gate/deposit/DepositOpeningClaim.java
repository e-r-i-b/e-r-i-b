/***********************************************************************
 * Module:  DepositOpeningClaim.java
 * Author:  Evgrafov
 * Purpose: Defines the Interface DepositOpeningClaim
 ***********************************************************************/

package com.rssl.phizic.gate.deposit;

import com.rssl.phizic.common.types.DateSpan;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.documents.AbstractAccountTransfer;

import java.util.Calendar;

/**
 * «а€вка на открытие депозита\вклада
 */
public interface DepositOpeningClaim extends AbstractAccountTransfer
{
   /**
    * ¬нешний ID определ€ющий услови€ дл€ открываемого вклада
    * (дл€ RS Retail V51 это AccountType)
    * Domain: ExternalID
    *
    * @return id
    */
   String getDepositConditionsId();
   /**
    * —рок на который открываетс€ депозит
    *
    * @return срок
    */
   DateSpan getPeriod();
   /**
    * —чет на который зачислить остаток по вкладу по окончании срока
    * Domain: AccountNumber
    *
    * @return счет
    */
   String getTransferAccount();
   /**
    * ƒата визита в банк дл€ окончательного открыти€ вклада.
    * Domain: Date
    */
   Calendar getVisitDate();
   /**
    * ѕроизводить ли автоматическую пролонгацию
    *
    * @return true == проводить
    */
   boolean isAutomaticRenewal();
   /**
    * ¬нешний ID подразделени€ банка в котором открываетс€ депозит
    * Domain: ExternalID
    *
    * @return внешний ID
    */
   String getOfficeExternalId();

	/**
	 * ”становка номера свежесазданного счета
	 * @param externalId - referenc счета в ритейле
	 */
	void setAccount(String externalId);

	/**
	 * Ќомер открытого по за€вке счета в RS Retail  
	 * @return referenc счета в ритейле (externalId в нашей системе)
	 */
	String getAccount() throws GateLogicException, GateException;
}