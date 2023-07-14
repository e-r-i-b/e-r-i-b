package com.rssl.phizic.gate.loans;

/**
 * @author Omeliyanchuk
 * @ created 13.03.2008
 * @ $Author$
 * @ $Revision$
 */

/**
 * ������������ �������� �������
 */
public enum LoanState
{
   /**
    * �����������
    */
   undefined(""),
	/**
    * ������
    */
   open("������"),
   /**
    * ���������
    */
   overdue("���������"),
   /**
    * ������
    */
   closed("������");

	private String description;

	LoanState(String description)
	{
		this.description = description;
	}	

	public String getDescription()
	{
		return description;
	}
}