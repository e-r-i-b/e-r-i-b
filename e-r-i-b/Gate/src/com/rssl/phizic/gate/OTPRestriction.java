package com.rssl.phizic.gate;

/**
 * ������ ����������� ��� ����������� ������� (OTP)
 * @author lukina
 * @ created 11.12.2011
 * @ $Author$
 * @ $Revision$
 */

public interface OTPRestriction extends AdditionalProductData
{
	/** ������� ����������� ������ ����������� ������� �� �����
	 * @return true - ���������� �����������, false - ��������� �����������
	 */
	Boolean isOTPGet();

	/** ������� ����������� ������������� ����� �������� ����������� �������.
     *  ���������� ��� OPTGet=false
	 * @return true � �������� ����� ����������� ������ �� �����������,
	 * false - - �������� ����� ����������� ����������������
	 */
	Boolean isOTPUse();
}
