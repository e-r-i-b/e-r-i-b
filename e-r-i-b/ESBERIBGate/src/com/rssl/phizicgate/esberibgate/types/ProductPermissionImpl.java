package com.rssl.phizicgate.esberibgate.types;

import com.rssl.phizic.gate.ProductPermission;

/**
 * @author gladishev
 * @ created 13.01.2011
 * @ $Author$
 * @ $Revision$
 */
public class ProductPermissionImpl implements ProductPermission
{
	private Boolean showInSbol;
	private Boolean showInES;
	private Boolean showInMobile;
	private Boolean showInSocial;
	private Boolean showInATM;

	public Boolean showInSbol()
	{
		return showInSbol;
	}

	public void setShowInSbol(Boolean showInSbol)
	{
		this.showInSbol = showInSbol;
	}

	public Boolean showInES()
	{
		return showInES;
	}

	public void setShowInES(Boolean showInES)
	{
		this.showInES = showInES;
	}

	public Boolean showInATM()
	{
		return showInATM;
	}

	public void setShowInATM(Boolean showInATM)
	{
		this.showInATM = showInATM;
	}

	public Boolean showInMobile()
	{
		return showInMobile;
	}

	public void setShowInMobile(Boolean showInMobile)
	{
		this.showInMobile = showInMobile;
	}

	public Boolean showInSocial()
	{
		return showInSocial;
	}

	public void setShowInSocial(Boolean showInSocial)
	{
		this.showInSocial = showInSocial;
	}
}

