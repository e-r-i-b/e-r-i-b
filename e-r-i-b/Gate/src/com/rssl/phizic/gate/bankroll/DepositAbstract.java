/***********************************************************************
 * Module:  DepositAbstract.java
 * Author:  Omeliyanchuk
 * Purpose: Defines the Interface DepositAbstract
 ***********************************************************************/

package com.rssl.phizic.gate.bankroll;

import java.util.*;

/**
 * Выписка по вкладу.
 */
public interface DepositAbstract extends AbstractBase
{
   /**
    * Дата предыдущей операции. Т.е. последней операции, которая была совершенна до начала периода выписки.
    *
    * @return Дата предыдущей операции
    */
   Calendar getPreviousOperationDate();

}