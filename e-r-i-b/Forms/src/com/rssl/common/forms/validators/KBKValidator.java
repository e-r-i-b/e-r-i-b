package com.rssl.common.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;

import java.util.Map;

/**
 * ��������� ������������ ���������� ��� � �����.�������
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
	 * @param values ������������ �� ����� ����
	 * @return true, ���� ��� ������������� ���� �������
	 */
	public boolean validate(Map values) throws TemporalDocumentException
	{
	   String budgetCode = (String) retrieveFieldValue(FIELD_TAXKBK, values);

       if(budgetCode.substring(0,3).equals("182")) // ������������� - ��������� ������
       {
	      String taxPaymentType = (String) retrieveFieldValue(FIELD_TAXTYPE, values);
          String code = budgetCode.substring(13,14);
          if(((taxPaymentType.equals("��")) || (taxPaymentType.equals("��")) || (taxPaymentType.equals("��"))) && (!code.equals("1")))
          {
	         setMessage ("14 ������ � ��� �� ������������� ���� ���������� ������� " + taxPaymentType + ". �������� ������ ���� ����� 1");
	         return false;
          }
	      else
          {
             if ((taxPaymentType.equals("��") || taxPaymentType.equals("��")) && (!code.equals("2")))
             {
	            setMessage ("14 ������ � ��� �� ������������� ���� ���������� ������� " + taxPaymentType + ". �������� ������ ���� ����� 2");
	            return false;
             }
	         else
             {
	             if((taxPaymentType.equals("��") || taxPaymentType.equals("��") || taxPaymentType.equals("��")) && (!code.equals("3")))
	             {
		            setMessage("14 ������ � ��� �� ������������� ���� ���������� ������� " + taxPaymentType + ". �������� ������ ���� ����� 3");
		            return false;
	             }
	             else
	                if ((taxPaymentType.equals("0")|| taxPaymentType.equals("��") || taxPaymentType.equals("��"))&&(!(code.equals("1")||code.equals("2")||code.equals("3"))))
	                {
		               setMessage("14 ������ � ��� �� ������������� ���� ���������� ������� " + taxPaymentType + ". �������� ������ ���� ����� 1, 2 ��� 3");
		               return false;
	                }
             }
          }
       }

		return true;
    }
}
