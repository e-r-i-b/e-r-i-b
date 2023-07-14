package com.rssl.phizic.operations.ext.sbrf.payment;

import java.util.Map;

/**
 * @author krenev
 * @ created 07.05.2010
 * @ $Author$
 * @ $Revision$
 * �������� �������������� ���������� ��� ������� ������ �����
 */
public interface ServicePaymentInfoSource
{
	/**
	 * �������� ������������ ������
	 * @return ������������ ������
	 */
	Long getServiceId();

	/**
	 * �������� ������������ ����������
	 * @return ������������ �����������
	 */
	Long getProviderId();

	/**
	 * ������� �������� ����
	 * @return  �������� ����
	 */
	Map<String, Object> getKeyFields();

}
