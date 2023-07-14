package com.rssl.phizic.gate.bankroll;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.common.types.Money;
import java.util.Calendar;

/**
 * Транзакция по вкладу.
 */
public interface DepositTransaction extends TransactionBase
{
   /**
    * Контрагент. Если отсутствует == null.
    * Domain: Text
    *
    * @return контрагент или null
    */
   String getCounteragent();
   /**
    * Номер счета корреспондента. Если отсутствует == null
    * Domain: AccountNumber
    *
    * @return nomer счета или null
    */
   String getCounteragentAccount();
   /**
    * Банк корреспондента, текстовое описание (например наименование + БИК). Если отсутствует == null.
    * Domain: Text
    *
    * @return БИК или null
    */
   String getCounteragentBank();
   /**
    * кор. счет банка получателя
    */
   String getCounteragentCorAccount();
   /**
    * Шифр операции
    */
   String getOperationCode();
   /**
    * Номер документа, соответствующий операции.
    */
   String getDocumentNumber();

}