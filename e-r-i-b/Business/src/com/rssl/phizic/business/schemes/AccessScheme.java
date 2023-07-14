package com.rssl.phizic.business.schemes;

import com.rssl.phizic.business.services.Service;

import java.util.List;

/**
 * @author Evgrafov
 * @ created 06.04.2006
 * @ $Author: krenev_a $
 * @ $Revision: 54593 $
 */
public interface AccessScheme extends com.rssl.phizic.gate.schemes.AccessScheme
{
	/**
	 * @return �������������
	 */
	public Long getId();

	/**
	 * @return ������ �����
	 */
	List<Service> getServices();

	/**
	 * @return ����� �� ����� ���� �������
	 */
	boolean canDelete();

	/**
	 * @return ������� ������� � ����� ������� "������ ��-��������� �� ���� ��" (AllTBAccess)
	 */
	boolean isContainAllTbAccessService();
}
