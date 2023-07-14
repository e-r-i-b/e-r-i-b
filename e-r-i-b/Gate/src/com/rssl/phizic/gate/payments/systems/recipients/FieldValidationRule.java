package com.rssl.phizic.gate.payments.systems.recipients;

import java.util.Map;

/**
 * @author akrenev
 * @ created 28.12.2009
 * @ $Author$
 * @ $Revision$
 */

/**
 * Правило проверки
 */
public interface FieldValidationRule
{
   /**
    * тип проверки
    *
    * @return тип проверки
    */
   FieldValidationRuleType getFieldValidationRuleType();

   /**
    * сообшение в случае несооветсвия значения поля правилу
    *
    * @return сообшение в случае несооветсвия значения поля правилу
    */
   String getErrorMessage();

   /**
    * параметры к правилу
    *
    * @return параметры к правилу
    */
   Map<String, Object> getParameters();
}