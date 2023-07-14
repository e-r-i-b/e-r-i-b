/***********************************************************************
 * Module:  DepositClosingClaim.java
 * Author:  Omeliyanchuk
 * Purpose: Defines the Interface DepositClosingClaim
 ***********************************************************************/

package com.rssl.phizic.gate.claims;

import com.rssl.phizic.gate.documents.SynchronizableDocument;

import java.util.*;

/**
 * Заявка на закрытие депозита
 */
public interface DepositClosingClaim extends SynchronizableDocument
{
   /**
    * Желаемая дата закрытия
    *
    * @return Желаемая дата закрытия
    */
   Calendar getClosingDate();
   /**
    * Внешний идентификатор вклада
    *
    * @return Внешний идентификатор вклада
    */
   String getExternalDepositId();
   /**
    * Номер счета для зачисления средств
    *
    * @return Номер счета для зачисления средств
    */
   String getDestinationAccount();

	/**
	 * Установить внешний идентификатор вклада
	 * @param id Внешний идентификатор вклада
	 */
	@Deprecated
	void setExternalDepositId(String id);
}