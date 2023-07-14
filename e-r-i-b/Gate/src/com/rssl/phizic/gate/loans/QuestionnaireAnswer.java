package com.rssl.phizic.gate.loans;

/**
 * @author Omeliyanchuk
 * @ created 10.01.2008
 * @ $Author$
 * @ $Revision$
 */

/**
 * ����� �� ������ ������
 */
public interface QuestionnaireAnswer
{
   /**
    * Domain: ExternalID
    */
   String getId();
   /**
    * ����� �� ������ ������
    * �������
    * -������ - as is
    * -���� - yyyy.mm.dd
    * -����� - nnnnn.nnnn (����� ������ �� � ����� ������� ������������)
    * -������ - nnnnnnnnnnnnnn.nn (��� ����� ����� �������)
    *
    * Domain: Text
    */
   String getValue();	
}
