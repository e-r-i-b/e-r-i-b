package com.rssl.phizic.test.web.ermb.update;

import org.apache.struts.action.ActionForm;

/**
 * @author osminin
 * @ created 27.09.13
 * @ $Author$
 * @ $Revision$
 */
public class UpdatePhoneRegistrationsForm extends ActionForm
{
	private String firstName;
	private String surName;
	private String patrName;
	private String birthDate;
	private String passport;
	private String cbCode;
	private String phoneNumber;
	private String addPhones;
	private String removePhones;
	private String errors;
	private boolean deleteDuplicates;

	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public String getSurName()
	{
		return surName;
	}

	public void setSurName(String surName)
	{
		this.surName = surName;
	}

	public String getPatrName()
	{
		return patrName;
	}

	public void setPatrName(String patrName)
	{
		this.patrName = patrName;
	}

	public String getBirthDate()
	{
		return birthDate;
	}

	public void setBirthDate(String birthDate)
	{
		this.birthDate = birthDate;
	}

	public String getPassport()
	{
		return passport;
	}

	public void setPassport(String passport)
	{
		this.passport = passport;
	}

	public String getCbCode()
	{
		return cbCode;
	}

	public void setCbCode(String cbCode)
	{
		this.cbCode = cbCode;
	}

	public String getPhoneNumber()
	{
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}

	public String getRemovePhones()
	{
		return removePhones;
	}

	public void setRemovePhones(String removePhones)
	{
		this.removePhones = removePhones;
	}

	public String getErrors()
	{
		return errors;
	}

	public void setErrors(String errors)
	{
		this.errors = errors;
	}

	public String getAddPhones()
	{
		return addPhones;
	}

	public void setAddPhones(String addPhones)
	{
		this.addPhones = addPhones;
	}

	public boolean isDeleteDuplicates()
	{
		return deleteDuplicates;
	}

	public void setDeleteDuplicates(boolean deleteDuplicates)
	{
		this.deleteDuplicates = deleteDuplicates;
	}
}
