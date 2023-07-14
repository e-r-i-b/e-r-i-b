package com.rssl.phizic.rsa.senders.initialization;

import com.rssl.phizic.rsa.InteractionType;
import com.rssl.phizic.rsa.PhaseType;

/**
 * @author tisov
 * @ created 26.02.15
 * @ $Author$
 * @ $Revision$
 * ��������� ��� ������ ��� ������������� �������� �� ����-�����������
 */
public interface InitializationData
{
	/**
	 * @return ��� �������������� � �������� ��
	 */
	InteractionType getInteractionType();

	/**
	 * @return ������ �������� �� ��
	 */
	PhaseType getPhaseType();

	/**
	 * �������� �� ����� ��� �����
	 * @return true - ��
	 */
	boolean isIMSI();
}
