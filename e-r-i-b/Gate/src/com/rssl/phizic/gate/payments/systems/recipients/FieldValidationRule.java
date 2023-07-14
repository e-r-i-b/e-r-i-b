package com.rssl.phizic.gate.payments.systems.recipients;

import java.util.Map;

/**
 * @author akrenev
 * @ created 28.12.2009
 * @ $Author$
 * @ $Revision$
 */

/**
 * ������� ��������
 */
public interface FieldValidationRule
{
   /**
    * ��� ��������
    *
    * @return ��� ��������
    */
   FieldValidationRuleType getFieldValidationRuleType();

   /**
    * ��������� � ������ ������������ �������� ���� �������
    *
    * @return ��������� � ������ ������������ �������� ���� �������
    */
   String getErrorMessage();

   /**
    * ��������� � �������
    *
    * @return ��������� � �������
    */
   Map<String, Object> getParameters();
}