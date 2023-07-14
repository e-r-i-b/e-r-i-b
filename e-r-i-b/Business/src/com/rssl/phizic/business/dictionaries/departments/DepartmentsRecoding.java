package com.rssl.phizic.business.dictionaries.departments;

import com.rssl.phizic.dictionaries.synchronization.MultiBlockDictionaryRecord;
import com.rssl.phizic.gate.dictionaries.DictionaryRecordBase;

import java.util.Calendar;

/**
 *
 * @author Balovtsev
 * @version 15.04.13 12:49
 */
public class DepartmentsRecoding extends DictionaryRecordBase implements MultiBlockDictionaryRecord
{
	private Long    id;
	private String  tbErib;
	private String  osbErib;
	private String  officeErib;
	private String  tbSpoobk2;
	private String  osbSpoobk2;
	private String  officeSpoobk2;
	private String  despatch;
	private Calendar dateSuc;

	public String getMultiBlockRecordId()
	{
		return getSynchKey();
	}

	public String getDespatch()
	{
		return despatch;
	}

	public void setDespatch(String despatch)
	{
		this.despatch = despatch;
	}

	public Calendar getDateSuc()
	{
		return dateSuc;
	}

	public void setDateSuc(Calendar dateSuc)
	{
		this.dateSuc = dateSuc;
	}

	public String getOfficeSpoobk2()
	{
		return officeSpoobk2;
	}

	public void setOfficeSpoobk2(String officeSpoobk2)
	{
		this.officeSpoobk2 = officeSpoobk2;
	}

	public String getOsbSpoobk2()
	{
		return osbSpoobk2;
	}

	public void setOsbSpoobk2(String osbSpoobk2)
	{
		this.osbSpoobk2 = osbSpoobk2;
	}

	public String getTbSpoobk2()
	{
		return tbSpoobk2;
	}

	public void setTbSpoobk2(String tbSpoobk2)
	{
		this.tbSpoobk2 = tbSpoobk2;
	}

	public String getOfficeErib()
	{
		return officeErib;
	}

	public void setOfficeErib(String officeErib)
	{
		this.officeErib = officeErib;
	}

	public String getOsbErib()
	{
		return osbErib;
	}

	public void setOsbErib(String osbErib)
	{
		this.osbErib = osbErib;
	}

	public String getTbErib()
	{
		return tbErib;
	}

	public void setTbErib(String tbErib)
	{
		this.tbErib = tbErib;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getSynchKey()
	{
		StringBuilder builder = new StringBuilder();
		builder.append(tbErib);
		builder.append("|");
		builder.append(osbErib);
		builder.append("|");
		builder.append(officeErib);
		builder.append(":");
		builder.append(tbSpoobk2);
		builder.append("|");
		builder.append(osbSpoobk2);
		builder.append("|");
		builder.append(officeSpoobk2);
		return builder.toString();
	}
}
