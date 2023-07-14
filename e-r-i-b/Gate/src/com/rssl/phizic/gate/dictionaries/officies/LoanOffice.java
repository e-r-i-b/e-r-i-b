package com.rssl.phizic.gate.dictionaries.officies;

import com.rssl.phizic.gate.dictionaries.DictionaryRecordBase;

/**
 * @author Krenev
 * @ created 12.12.2007
 * @ $Author$
 * @ $Revision$
 */
public class LoanOffice extends DictionaryRecordBase
{
	private Comparable synchKey;
	private String name;
	private String address;
	private String info;
	private String telephone;
	private boolean main;

	public Comparable getSynchKey()
	{
		return synchKey;
	}

	public void setSynchKey(Comparable synchKey)
	{
		this.synchKey = synchKey;
	}

	/**
	 * @return название офиса
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name название офиса
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	public String getAddress()
	{
		return address;
	}

	public String getTelephone()
	{
		return telephone;
	}

	public void setTelephone(String telephone)
	{
		this.telephone = telephone;
	}

	public String getInfo()
	{
		return info;
	}

	public void setInfo(String info)
	{
		this.info = info;
	}

	public boolean isMain()
	{
		return main;
	}

	public void setMain(boolean main)
	{
		this.main = main;
	}
}
