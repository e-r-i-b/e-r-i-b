package com.rssl.phizic.business.documents.strategies.monitoring;

import com.rssl.phizic.business.documents.strategies.ProcessDocumentStrategy;
import com.rssl.phizic.rsa.InteractionType;
import com.rssl.phizic.rsa.PhaseType;

/**
 * ��������� ���������� ���������, �������� �� �������������
 *
 * @author khudyakov
 * @ created 21.05.15
 * @ $Author$
 * @ $Revision$
 */
public interface FraudMonitoringDocumentStrategy extends ProcessDocumentStrategy
{
	/**
	 * @return �������� ��� �������������� � �� ��
	 */
	InteractionType getInteractionType();

	/**
	 * @return �������� ������ ��������
	 */
	PhaseType getPhaseType();
}
