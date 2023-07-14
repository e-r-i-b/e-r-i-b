package com.rssl.phizic.gate.loans;

import org.w3c.dom.Document;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.Service;

import java.util.List;

/**
 * @author Omeliyanchuk
 * @ created 03.12.2007
 * @ $Author$
 * @ $Revision$
 */

/**
 * ��������� ���������� � ��������.
 */
public interface LoanProductsService extends Service
{
   /**
    * ��������� ���������� � ���� ��������� ���������.
    * ������������ �������� �������� ����������, ����������� ��� �������� ���������� ��������.
    * ������ ������������� xml ��������� ������������ �����������.
    *
    *
    * @return xml
    * @exception com.rssl.phizic.gate.exceptions.GateException
    */
   Document getLoansInfo() throws GateException;
   /**
    * ��������� ���������� � ��������� ��������.
    * ������ ��������� ������������ �����������.
    *
    * @param conditions ������ ID ������� (Domain: ExternalID)
    * @return xml
    * @exception com.rssl.phizic.gate.exceptions.GateException
    */
   Document getLoanProduct(List<String> conditions) throws GateException;
}