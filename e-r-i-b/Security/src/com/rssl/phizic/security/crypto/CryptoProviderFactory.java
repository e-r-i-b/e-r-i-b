package com.rssl.phizic.security.crypto;

import java.io.Serializable;

/**
 * @author Roshka
 * @ created 29.08.2006
 * @ $Author$
 * @ $Revision$
 */

public interface CryptoProviderFactory extends Serializable
{
	/**
	 * @return ������������ ���������� (agava, dummy etc)
	 */
	String getName();

	/**
	 * ���������� ��������� ������-����������.
	 * @return � ����������� �� ���������� �������
	 * ����� ���� ��������� ���� ����� ���������, ���� ���������, ��������� �������
	 * never null
	 */
	CryptoProvider getProvider();

	void close();
}
