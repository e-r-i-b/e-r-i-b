package com.rssl.phizic.gate.payments.systems.recipients;

/**
 * ������
 * @author mihaylov
 * @ created 07.04.2010
 * @ $Author$
 * @ $Revision$
 */

public interface Service
{
	/**
    * ������������ ������ � ����������� �������
    * @return ������������ ������.
    */
   String getName();
	/**
    * ������������� ������ � ����������� �������
    * @return ������������� ������.
    */
   String getCode();
}
