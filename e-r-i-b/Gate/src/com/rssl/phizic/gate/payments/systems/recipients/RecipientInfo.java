package com.rssl.phizic.gate.payments.systems.recipients;

import com.rssl.phizic.gate.dictionaries.ResidentBank;

/**
 * �������������� ���������� �� ����������
 * @author Gainanov
 * @ created 03.07.2008
 * @ $Author$
 * @ $Revision$
 */
public interface RecipientInfo
{
	/**
	 * ��� ����������.
	 *
	 * @return ��� ����������  ��� null, ���� ������ �� ����� ���� ��������.
	 */
   String getINN();
	
	/**
	 * ��� ����������.
	 *
	 * @return ��� ����������  ��� null, ���� ������ �� ����� ���� ��������.
	 */
   String getKPP();

	/**
	 * ���� ���������� � ���-����� (�.�. �� ������ ��������� �������). ��� ������ ��������� ���������.
	 *
	 * @return ���� ����������   ��� null, ���� ������ �� ����� ���� ��������.
	 */
   String getAccount();

   /**
    * �������� ����, � ������� ��������������� ���� ����������. ��� ������ ��������� ���������.
    *
    * @return ���� ���������� ��� null, ���� ������ �� ����� ���� ��������.
    */
   ResidentBank getBank();

	/**
	 * ����� ����������� �����
	 *
	 * @return ����� ����������� �����
	 */
	String getTransitAccount();
}