package com.rssl.phizic.business.dictionaries.offices.extended.replication.tree;

import com.rssl.phizic.business.dictionaries.offices.extended.ExtendedCodeImpl;
import com.rssl.phizic.gate.dictionaries.officies.Code;
import com.rssl.phizic.utils.StringHelper;

/**
 * ѕуть до подразделени€ в дереве подразделений
 * @author niculichev
 * @ created 18.09.13
 * @ $Author$
 * @ $Revision$
 */
public class DepartmentPath
{
	private static final String DELIMITER = "|";

	private String tb;
	private String osb;
	private String vsp;

	DepartmentPath(String tb, String osb, String vsp)
	{
		this.tb = tb;
		this.osb = osb;
		this.vsp = vsp;
	}

	DepartmentPath(Code code)
	{
		ExtendedCodeImpl extendedCode = new ExtendedCodeImpl(code);
		this.tb = extendedCode.getRegion();
		this.osb = extendedCode.getBranch();
		this.vsp = extendedCode.getOffice();
	}

	public DepartmentPath getRootPath()
	{
		if(StringHelper.isEmpty(osb) && StringHelper.isEmpty(vsp))
			return null;

		return new DepartmentPath(this.tb, null, null);
	}

	public DepartmentPath getParentPath()
	{
		if(StringHelper.isNotEmpty(vsp))
			return new DepartmentPath(this.tb, this.osb, null);

		if(StringHelper.isNotEmpty(osb))
			return new DepartmentPath(this.tb, null, null);

		return null;
	}

	public Code getCode()
	{
		return new ExtendedCodeImpl(tb, osb, vsp);
	}

	public String getTb()
	{
		return tb;
	}

	public String getOsb()
	{
		return osb;
	}

	public String getVsp()
	{
		return vsp;
	}

	public boolean equals(Object obj)
	{
		if(!(obj instanceof DepartmentPath))
			return false;

		DepartmentPath identity = (DepartmentPath) obj;
		return StringHelper.equalsNullIgnore(tb, identity.getTb())
				&& StringHelper.equalsNullIgnore(osb, identity.getOsb())
				&& StringHelper.equalsNullIgnore(vsp, identity.getVsp());
	}

	public int hashCode()
	{
		return (StringHelper.getEmptyIfNull(tb)
				+ DELIMITER + StringHelper.getEmptyIfNull(osb)
				+ DELIMITER + StringHelper.getEmptyIfNull(vsp))
				.hashCode();
	}
}
