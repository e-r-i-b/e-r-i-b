package com.rssl.common.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;

import java.util.Map;

/**
 * Проверяет правильность заполнения КБК в налог.платеже
 * User: novikov_a
 * Date: 06.08.2009
 * Time: 18:24:46
 */
public class KBKValidator extends MultiFieldsValidatorBase
{
	public static final String FIELD_TAXKBK = "taxKBK";
	public static final String FIELD_TAXTYPE = "taxType";

	/**
	 *
	 * @param values передаваемые из формы поля
	 * @return true, если КБК соответствует типу платежа
	 */
	public boolean validate(Map values) throws TemporalDocumentException
	{
	   String budgetCode = (String) retrieveFieldValue(FIELD_TAXKBK, values);

       if(budgetCode.substring(0,3).equals("182")) // Администратор - налоговые органы
       {
	      String taxPaymentType = (String) retrieveFieldValue(FIELD_TAXTYPE, values);
          String code = budgetCode.substring(13,14);
          if(((taxPaymentType.equals("НС")) || (taxPaymentType.equals("АВ")) || (taxPaymentType.equals("ГП"))) && (!code.equals("1")))
          {
	         setMessage ("14 разряд в КБК не соответствует типу налогового платежа " + taxPaymentType + ". Значение должно быть равно 1");
	         return false;
          }
	      else
          {
             if ((taxPaymentType.equals("ПЕ") || taxPaymentType.equals("ПЦ")) && (!code.equals("2")))
             {
	            setMessage ("14 разряд в КБК не соответствует типу налогового платежа " + taxPaymentType + ". Значение должно быть равно 2");
	            return false;
             }
	         else
             {
	             if((taxPaymentType.equals("СА") || taxPaymentType.equals("АШ") || taxPaymentType.equals("ИШ")) && (!code.equals("3")))
	             {
		            setMessage("14 разряд в КБК не соответствует типу налогового платежа " + taxPaymentType + ". Значение должно быть равно 3");
		            return false;
	             }
	             else
	                if ((taxPaymentType.equals("0")|| taxPaymentType.equals("ВЗ") || taxPaymentType.equals("ПЛ"))&&(!(code.equals("1")||code.equals("2")||code.equals("3"))))
	                {
		               setMessage("14 разряд в КБК не соответствует типу налогового платежа " + taxPaymentType + ". Значение должно быть равно 1, 2 или 3");
		               return false;
	                }
             }
          }
       }

		return true;
    }
}
