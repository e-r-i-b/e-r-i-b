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


@Deprecated //������������ UpdateDocumentService 
public interface UpdatePaymentsStateService extends Service
{
  /**
   * ���������� ������� �������
   *@param documentId id �������
   *@param event �������, �������� ������ ������������� �������
   */
    void update(Long documentId, ObjectEvent event) throws GateException, GateLogicException;
}
