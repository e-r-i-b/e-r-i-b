package com.rssl.phizic.business.limits.link;

import com.rssl.phizic.business.limits.GroupRisk;
import com.rssl.phizic.dictionaries.synchronization.MultiBlockDictionaryRecord;
import com.rssl.phizic.gate.dictionaries.officies.Office;

import java.util.List;

/**
 * C���� ������� � ������ �� ������ �����
 */
public interface LimitPaymentsLink extends MultiBlockDictionaryRecord
{
	/**
	 * @return ��� �����
	 */
	LimitPaymentsType getLinkType();

	/**
	 * @return ������ ����� ������������ ��������
	 */
	List<Class> getPaymentTypes();

	/**
	 * @return ������ �����
	 */
	GroupRisk getGroupRisk();

	/**
	 * @return �������������
	 */
	String getTb();
}
