package com.rssl.phizic.gate.mobilebank;

import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

import java.util.List;

/**
 * @author Erkin
 * @ created 06.10.2010
 * @ $Author$
 * @ $Revision$
 * @deprecated ���������� �� �������� ���
 */

/**
 * ������ ��� ������ � ��������� �������� �� ������� �������
 */
@Deprecated
//todo CHG059738 �������
//todo ������� ����� �������� �� ������������� MobileBankGate � �������� ���������� ����������
public interface GatePaymentTemplateService extends Service
{	
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
