package com.rssl.phizic.gate.payments;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.Service;
import com.rssl.common.forms.state.ObjectEvent;

/**
 * User: Novikov
 * Date: 28.05.2007
 * Time: 18:30:57
 */


@Deprecated //использовать UpdateDocumentService 
public interface UpdatePaymentsStateService extends Service
{
  /**
   * обновление статуса платежа
   *@param documentId id платежа
   *@param event событие, меняющее статус платежастатус платежа
   */
    void update(Long documentId, ObjectEvent event) throws GateException, GateLogicException;
}
