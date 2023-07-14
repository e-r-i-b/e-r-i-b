package com.rssl.phizicgate.sbrf.client;

import com.rssl.phizgate.common.routable.ClientBase;

import java.util.Calendar;

/**
 * @author Omeliyanchuk
 * @ created 14.05.2008
 * @ $Author$
 * @ $Revision$
 */

public class SBRFClient extends ClientBase
{
    // Поля, специфичные для Client
    private String docNumber;
    private String docSeries;
    private Calendar docIssueDate;
    private String docIssueBy;
    private String registrationAddress;
    private Boolean partial = true;

	public boolean isResident()
	{
		return false;
	}

	public String getResidenceAddress()
	{
		return "";
	}

	public String getDocTypeName()
	{
		return "Паспорт";
	}

	public String getDocumentType()//тип документа
	{
		return "REGULAR_PASSPORT_RF";
	}

	public String getDisplayId()
	{
		return getId();
	}

	/**
	 * устанавливает полное имя, парсит и заполняет ФИО
	 * @param fullName Строка содержащая полное имя в формате Ф И О
	 */
    public void setFullName(String fullName)
    {
	    this.fullName = fullName;
	    parseFullName(fullName);
    }

    public String getDocNumber()
    {
        return docNumber;
    }

    void setDocNumber(String docNumber)
    {
        this.docNumber = docNumber;
    }

    public String getDocSeries()
    {
        return docSeries;
    }

    void setDocSeries(String docSeries)
    {
        this.docSeries = docSeries;
    }

    public Calendar getDocIssueDate()
    {
        return docIssueDate;
    }

    void setDocIssueDate(Calendar docIssueDate)
    {
        this.docIssueDate = docIssueDate;
    }

    public String getDocIssueBy()
    {
        return docIssueBy;
    }

    void setDocIssueBy(String docIssueBy)
    {
        this.docIssueBy = docIssueBy;
    }

    public String getRegistrationAddress()
    {
        return registrationAddress;
    }

    public void setRegistrationAddress(String registrationAddress)
    {
        this.registrationAddress = registrationAddress;
    }

    public boolean isPartial()
    {
        return partial;
    }

    void setPartial(boolean value)
    {
        partial = value;
    }

	private void parseFullName(String fullName)
	{
		String trFullName = fullName.trim();
		int sStart = trFullName.indexOf(' ', 0);
		if(sStart==-1)return;
		surName = trFullName.substring(0, sStart);

		int fStart = trFullName.indexOf(' ', sStart+1);
		if(fStart==-1)return;
		firstName = trFullName.substring(sStart+1, fStart);

		patrName = trFullName.substring(fStart+1);
		if(trFullName.length()<= fStart+1)
			return;
	}

}
