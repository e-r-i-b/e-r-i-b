package com.rssl.phizic.test.web.atm.payments;

/**
 * @author Pankin
 * @ created 19.01.2012
 * @ $Author$
 * @ $Revision$
 */

public class TestATMAccountOpeningClaimForm extends TestATMDocumentForm
{
	private String depositId;
	private String depositType;
	private String depositGroup;
	private String atmPlace;
	private String atmTB;
	private String atmOSB;
	private String atmVSP;

	public String getDepositId()
	{
		return depositId;
	}

	public void setDepositId(String depositId)
	{
		this.depositId = depositId;
	}

	public String getDepositType()
	{
		return depositType;
	}

	public void setDepositType(String depositType)
	{
		this.depositType = depositType;
	}

	public String getDepositGroup()
	{
		return depositGroup;
	}

	public void setDepositGroup(String depositGroup)
	{
		this.depositGroup = depositGroup;
	}

	public String getAtmPlace() {
        return atmPlace;
    }

    public void setAtmPlace(String atmPlace) {
        this.atmPlace = atmPlace;
    }

    public String getAtmTB() {
        return atmTB;
    }

    public void setAtmTB(String atmTB) {
        this.atmTB = atmTB;
    }

    public String getAtmOSB() {
        return atmOSB;
    }

    public void setAtmOSB(String atmOSB) {
        this.atmOSB = atmOSB;
    }

    public String getAtmVSP() {
        return atmVSP;
    }

    public void setAtmVSP(String atmVSP) {
        this.atmVSP = atmVSP;
	}
}