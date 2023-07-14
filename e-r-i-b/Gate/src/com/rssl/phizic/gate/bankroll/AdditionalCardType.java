package com.rssl.phizic.gate.bankroll;

/**
 * @ author: filimonova
 * @ created: 26.04.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * Тип доп. карты
 */

public enum AdditionalCardType
{
	/**
    * Доп. карта клиента к своей же карте.
    */
   CLIENTTOCLIENT("Client2Client"),
   /**
    * Доп. карта к карте клиента выданная другому лицу.
    */
   CLIENTTOOTHER("Client2Other"),
   /**
    * Доп. карта выданная на имя клиента другим лицом.
    */
   OTHERTOCLIENT("Other2Client");

   private String value;

    AdditionalCardType(String value)
    {
        this.value = value;
    }

    public String toValue()
    {
        return value;
    }

	public String getValue()
	{
		return value;
	}
}
