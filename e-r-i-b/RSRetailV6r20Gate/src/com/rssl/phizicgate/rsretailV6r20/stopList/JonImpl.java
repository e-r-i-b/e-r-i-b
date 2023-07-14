package com.rssl.phizicgate.rsretailV6r20.stopList;

import java.util.Date;
import java.util.Calendar;

/**
 * @author osminin
 * @ created 26.01.2009
 * @ $Author$
 * @ $Revision$
 */

public class JonImpl
{
	private long id;
	private String operName;
    private String slCliId;
    private int rsCliId;
    private String recordId;
    private String inn;
	private int operator;
	private Calendar slExpDate;
	private Calendar  slExpTime;
	private String slName;
	private Calendar  operDate;
	private Calendar  operTime;
	private String absSubsys;
	private int operCode;
	private String nameIdCard;
	private String snIdCard;
	private String fio;
	private String urName;
	private String urAddress;
	private String operId;
	private String operNote;
	private int isExport;

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public String getOperName()
	{
		return operName;
	}

	public void setOperName(String operName)
	{
		this.operName = operName;
	}

	public String getSlCliId()
	{
		return slCliId;
	}

	public void setSlCliId(String slCliId)
	{
		this.slCliId = slCliId;
	}

	public int getRsCliId()
	{
		return rsCliId;
	}

	public void setRsCliId(int rsCliId)
	{
		this.rsCliId = rsCliId;
	}

	public String getRecordId()
	{
		return recordId;
	}

	public void setRecordId(String recordId)
	{
		this.recordId = recordId;
	}

	public String getInn()
	{
		return inn;
	}

	public void setInn(String inn)
	{
		this.inn = inn;
	}

	public int getOperator()
	{
		return operator;
	}

	public void setOperator(int operator)
	{
		this.operator = operator;
	}

	public Calendar getSlExpDate()
	{
		return slExpDate;
	}

	public void setSlExpDate(Calendar slExpDate)
	{
		this.slExpDate = slExpDate;
	}

	public Calendar getSlExpTime()
	{
		return slExpTime;
	}

	public void setSlExpTime(Calendar slExpTime)
	{
		this.slExpTime = slExpTime;
	}

	public String getSlName()
	{
		return slName;
	}

	public void setSlName(String slName)
	{
		this.slName = slName;
	}

	public Calendar getOperDate()
	{
		return operDate;
	}

	public void setOperDate(Calendar operDate)
	{
		this.operDate = operDate;
	}

	public Calendar getOperTime()
	{
		return operTime;
	}

	public void setOperTime(Calendar operTime)
	{
		this.operTime = operTime;
	}

	public String getAbsSubsys()
	{
		return absSubsys;
	}

	public void setAbsSubsys(String absSubsys)
	{
		this.absSubsys = absSubsys;
	}

	public int getOperCode()
	{
		return operCode;
	}

	public void setOperCode(int operCode)
	{
		this.operCode = operCode;
	}

	public String getNameIdCard()
	{
		return nameIdCard;
	}

	public void setNameIdCard(String nameIdCard)
	{
		this.nameIdCard = nameIdCard;
	}

	public String getSnIdCard()
	{
		return snIdCard;
	}

	public void setSnIdCard(String snIdCard)
	{
		this.snIdCard = snIdCard;
	}

	public String getFio()
	{
		return fio;
	}

	public void setFio(String fio)
	{
		this.fio = fio;
	}

	public String getUrName()
	{
		return urName;
	}

	public void setUrName(String urName)
	{
		this.urName = urName;
	}

	public String getUrAddress()
	{
		return urAddress;
	}

	public void setUrAddress(String urAddress)
	{
		this.urAddress = urAddress;
	}

	public String getOperId()
	{
		return operId;
	}

	public void setOperId(String operId)
	{
		this.operId = operId;
	}

	public String getOperNote()
	{
		return operNote;
	}

	public void setOperNote(String operNote)
	{
		this.operNote = operNote;
	}

	public int getIsExport()
	{
		return isExport;
	}

	public void setIsExport(int isExport)
	{
		this.isExport = isExport;
	}
}
