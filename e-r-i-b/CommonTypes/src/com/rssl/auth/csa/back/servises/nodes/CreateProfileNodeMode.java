package com.rssl.auth.csa.back.servises.nodes;

/**
 * @author akrenev
 * @ created 18.02.2014
 * @ $Author$
 * @ $Revision$
 *
 * ������� �������� �������� ������� � �����
 */

public enum CreateProfileNodeMode
{
	CREATION_DENIED,                  // �������� ���������
	CREATION_ALLOWED_FOR_MAIN_NODE,   // �������� ��� ��������� �����
	CREATION_ALLOWED_FOR_ALL_NODES    // �������� ��� ������ �����
}
