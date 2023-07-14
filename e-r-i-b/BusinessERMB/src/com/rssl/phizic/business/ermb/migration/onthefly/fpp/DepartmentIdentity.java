package com.rssl.phizic.business.ermb.migration.onthefly.fpp;

import com.rssl.phizic.common.types.annotation.PlainOldJavaObject;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
/**
 * ÒÁ + (ÎÑÁ) + (ÂÑÏ)
 */
@XmlType(name = "DepartmentIdentity")
@XmlAccessorType(XmlAccessType.NONE)
@PlainOldJavaObject
public class DepartmentIdentity
{
    @XmlElement(required = true)
    private String tb;

	@XmlElement(required = true)
	private String osb;

	@XmlElement(required = true)
	private String vsp;

	///////////////////////////////////////////////////////////////////////////

	public String getTb()
	{
		return tb;
	}

	public void setTb(String tb)
	{
		this.tb = tb;
	}

	public String getOsb()
	{
		return osb;
	}

	public void setOsb(String osb)
	{
		this.osb = osb;
	}

	public String getVsp()
	{
		return vsp;
	}

	public void setVsp(String vsp)
	{
		this.vsp = vsp;
	}
}
