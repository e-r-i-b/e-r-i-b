package com.rssl.phizic.gate.cache;

import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.depo.DepoAccount;

/**
 * @author mihaylov
 * @ created 19.11.2010
 * @ $Author$
 * @ $Revision$
 */
/*
������ ��� ��������������� ���������� ����. ���� ������ �������� ���������� ������ ��� �����������
� ������� ����� ����������� ������������, ������� ����� � ��������� ������
 */
public interface RefreshCacheService extends Service
{
	/**
	 * ������� ������ �� ���� � ������ ��� �� ��������� � ��� ���������,
	 * ���� ������ �������� � ���� ������ ��� seconds
	 * @param depoAccount - ���� ����
	 * @param seconds - �����, ����� �������� ������ ���������� ������� �� ����
	 * @return true - ������ ��� ������
	 */
	boolean refreshDepoCacheService(DepoAccount depoAccount, int seconds);

}
