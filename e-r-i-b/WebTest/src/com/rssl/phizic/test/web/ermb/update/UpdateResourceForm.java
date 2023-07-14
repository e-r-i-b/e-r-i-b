package com.rssl.phizic.test.web.ermb.update;

import org.apache.struts.action.ActionForm;

/**
 * Форма заглушки отправки оповещений об изменении продукта клиента в ЕРМБ
 * @author Rtischeva
 * @ created 01.04.2013
 * @ $Author$
 * @ $Revision$
 */
public class UpdateResourceForm extends ActionForm
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

	private String resourceType;
	private String resourceNumber;

	private String productTb;

	public String getResourceType()
	{
		return resourceType;
	}

	public void setResourceType(String resourceType)
	{
		this.resourceType = resourceType;
	}

	public String getResourceNumber()
	{
		return resourceNumber;
	}

	public void setResourceNumber(String resourceNumber)
	{
		this.resourceNumber = resourceNumber;
	}

	public String getTb()
	{
		return tb;
	}

	public void setTb(String tb)
	{
		this.tb = tb;
	}

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

	public String getProductTb()
	{
		return productTb;
	}

	public void setProductTb(String productTb)
	{
		this.productTb = productTb;
	}
}
