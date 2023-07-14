package com.rssl.phizic.gate;

/**
 * ������������ ��� �������� ���� �����������
 * �������� � �������� (����, ��)
 * @author gladishev
 * @ created 13.01.2011
 * @ $Author$
 * @ $Revision$
 */
public interface ProductPermission  extends AdditionalProductData
{
	/**
	 * ���������� ������� � ����
	 * @return true - ����������
	 */
	Boolean showInSbol();

	/**
	 * ���������� ������� � ����������� ����������������
	 * @return true - ����������
	 */
	Boolean showInES();

	/**
	 * ���������� ������� � ���
	 * @return true - ����������
	 */
	Boolean showInATM();

	/**
	 * ���������� ������� � ��������� ������
	 * @return true - ����������
	 */
	Boolean showInMobile();

    /**
	 * ���������� ������� � ���. �����������
	 * @return true - ����������
	 */
	Boolean showInSocial();
}
