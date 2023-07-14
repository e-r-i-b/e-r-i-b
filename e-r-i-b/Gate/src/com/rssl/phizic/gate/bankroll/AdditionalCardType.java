package com.rssl.phizic.gate.bankroll;

/**
 * @ author: filimonova
 * @ created: 26.04.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * ��� ���. �����
 */

public enum AdditionalCardType
{
	/**
    * ���. ����� ������� � ����� �� �����.
    */
   CLIENTTOCLIENT("Client2Client"),
   /**
    * ���. ����� � ����� ������� �������� ������� ����.
    */
   CLIENTTOOTHER("Client2Other"),
   /**
    * ���. ����� �������� �� ��� ������� ������ �����.
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
