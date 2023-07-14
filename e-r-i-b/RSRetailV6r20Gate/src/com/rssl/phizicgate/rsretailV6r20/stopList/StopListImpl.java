package com.rssl.phizicgate.rsretailV6r20.stopList;

import java.util.Date;
import java.util.Calendar;

/**
 * @author osminin
 * @ created 26.01.2009
 * @ $Author$
 * @ $Revision$
 */

public class StopListImpl
{
	private String id;
	private String cliId;
	private String family;
    private String name;
    private String patrName;
    private String inn;
	private String series;
	private String number;
	private Calendar birthDate;
	private int slType;
	private int isTer;
	private int docType;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getCliId()
	{
		return cliId;
	}

	public void setCliId(String cliId)
	{
		this.cliId = cliId;
	}

	public String getFamily()
	{
		return family;
	}

	public void setFamily(String family)
	{
		this.family = family;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getPatrName()
	{
		return patrName;
	}

	public void setPatrName(String patrName)
	{
		this.patrName = patrName;
	}

	public String getInn()
	{
		return inn;
	}

	public void setInn(String inn)
	{
		this.inn = inn;
	}

	public String getSeries()
	{
		return series;
	}

	public void setSeries(String series)
	{
		this.series = series;
	}

	public String getNumber()
	{
		return number;
	}

	public void setNumber(String number)
	{
		this.number = number;
	}

	public  Calendar  getBirthDate()
	{
		return birthDate;
	}

	public void setBirthDate(Calendar  birthDate)
	{
		this.birthDate = birthDate;
	}

	public int getSlType()
	{
		return slType;
	}

	public void setSlType(int slType)
	{
		this.slType = slType;
	}

	public int getIsTer()
	{
		return isTer;
	}

	public void setIsTer(int isTer)
	{
		this.isTer = isTer;
	}

	public int getDocType()
	{
		return docType;
	}

	public void setDocType(int docType)
	{
		this.docType = docType;
	}
}
