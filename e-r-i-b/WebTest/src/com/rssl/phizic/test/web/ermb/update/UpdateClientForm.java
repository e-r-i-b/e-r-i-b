package com.rssl.phizic.test.web.ermb.update;

import org.apache.struts.action.ActionForm;

/**
 * Форма заглушки отправки оповещений об изменении данных клиента в ЕРМБ
 * @author Rtischeva
 * @ created 14.03.2013
 * @ $Author$
 * @ $Revision$
 */
public class UpdateClientForm extends ActionForm
{
	private String lastname;
	private String firstname;
	private String middlename;
	private String birthday;
	private String idType;
	private String idSeries;
	private String idNum;
	private String issuedBy;
	private String issueDt;
	private String tb;

	private String oldLastname;
	private String oldFirstname;
	private String oldMiddlename;
	private String oldBirthday;
	private String oldIdType;
	private String oldIdSeries;
	private String oldIdNum;
	private String oldIssuedBy;
	private String oldIssueDt;
	private String oldTb;

	public String getLastname()
	{
		return lastname;
	}

	public void setLastname(String lastname)
	{
		this.lastname = lastname;
	}

	public String getFirstname()
	{
		return firstname;
	}

	public void setFirstname(String firstname)
	{
		this.firstname = firstname;
	}

	public String getMiddlename()
	{
		return middlename;
	}

	public void setMiddlename(String middlename)
	{
		this.middlename = middlename;
	}

	public String getBirthday()
	{
		return birthday;
	}

	public void setBirthday(String birthday)
	{
		this.birthday = birthday;
	}

	public String getIdType()
	{
		return idType;
	}

	public void setIdType(String idType)
	{
		this.idType = idType;
	}

	public String getIdSeries()
	{
		return idSeries;
	}

	public void setIdSeries(String idSeries)
	{
		this.idSeries = idSeries;
	}

	public String getIdNum()
	{
		return idNum;
	}

	public void setIdNum(String idNum)
	{
		this.idNum = idNum;
	}

	public String getIssuedBy()
	{
		return issuedBy;
	}

	public void setIssuedBy(String issuedBy)
	{
		this.issuedBy = issuedBy;
	}

	public String getIssueDt()
	{
		return issueDt;
	}

	public void setIssueDt(String issueDt)
	{
		this.issueDt = issueDt;
	}

	public String getOldLastname()
	{
		return oldLastname;
	}

	public void setOldLastname(String oldLastname)
	{
		this.oldLastname = oldLastname;
	}

	public String getOldFirstname()
	{
		return oldFirstname;
	}

	public void setOldFirstname(String oldFirstname)
	{
		this.oldFirstname = oldFirstname;
	}

	public String getOldMiddlename()
	{
		return oldMiddlename;
	}

	public void setOldMiddlename(String oldMiddlename)
	{
		this.oldMiddlename = oldMiddlename;
	}

	public String getOldBirthday()
	{
		return oldBirthday;
	}

	public void setOldBirthday(String oldBirthday)
	{
		this.oldBirthday = oldBirthday;
	}

	public String getOldIdType()
	{
		return oldIdType;
	}

	public void setOldIdType(String oldIdType)
	{
		this.oldIdType = oldIdType;
	}

	public String getOldIdSeries()
	{
		return oldIdSeries;
	}

	public void setOldIdSeries(String oldIdSeries)
	{
		this.oldIdSeries = oldIdSeries;
	}

	public String getOldIdNum()
	{
		return oldIdNum;
	}

	public void setOldIdNum(String oldIdNum)
	{
		this.oldIdNum = oldIdNum;
	}

	public String getOldIssuedBy()
	{
		return oldIssuedBy;
	}

	public void setOldIssuedBy(String oldIssuedBy)
	{
		this.oldIssuedBy = oldIssuedBy;
	}

	public String getOldIssueDt()
	{
		return oldIssueDt;
	}

	public void setOldIssueDt(String oldIssueDt)
	{
		this.oldIssueDt = oldIssueDt;
	}

	public String getTb()
	{
		return tb;
	}

	public void setTb(String tb)
	{
		this.tb = tb;
	}

	public String getOldTb()
	{
		return oldTb;
	}

	public void setOldTb(String oldTb)
	{
		this.oldTb = oldTb;
	}
}
