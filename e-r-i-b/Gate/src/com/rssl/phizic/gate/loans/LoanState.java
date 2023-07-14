package com.rssl.phizic.gate.loans;

/**
 * @author Omeliyanchuk
 * @ created 13.03.2008
 * @ $Author$
 * @ $Revision$
 */

/**
 * Перечисление статусов кредита
 */
public enum LoanState
{
   /**
    * неопределен
    */
   undefined(""),
	/**
    * открыт
    */
   open("Открыт"),
   /**
    * просрочен
    */
   overdue("Просрочен"),
   /**
    * закрыт
    */
   closed("Закрыт");

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