package com.rssl.phizic.web.common.socialApi.ext.sbrf.userprofile;

import com.rssl.phizic.business.userDocuments.UserDocument;
import com.rssl.phizic.person.PersonDocument;
import com.rssl.phizic.web.actions.ActionFormBase;

import java.util.List;

/**
 * @author EgorovaA
 * @ created 16.06.14
 * @ $Author$
 * @ $Revision$
 *
 * Форма просмотра личной информации клиента
 */
public class ProfileInfoForm extends ActionFormBase
{
	private String surName;
	private String firstName;
	private String patrName;
	private String mobilePhone;
	private String jobPhone;
	private String homePhone;
	private String email;
	private List<PersonDocument> mainDocuments;
	private List<UserDocument> additionalDocuments;
	private String avatarPath;

	public String getSurName()
	{
		return surName;
	}

	public void setSurName(String surName)
	{
		this.surName = surName;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public String getPatrName()
	{
		return patrName;
	}

	public void setPatrName(String patrName)
	{
		this.patrName = patrName;
	}

	public String getMobilePhone()
	{
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone)
	{
		this.mobilePhone = mobilePhone;
	}

	public String getJobPhone()
	{
		return jobPhone;
	}

	public void setJobPhone(String jobPhone)
	{
		this.jobPhone = jobPhone;
	}

	public String getHomePhone()
	{
		return homePhone;
	}

	public void setHomePhone(String homePhone)
	{
		this.homePhone = homePhone;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	/**
	 * Список ДУЛ клиента
	 * @return
	 */
	public List<PersonDocument> getMainDocuments()
	{
		return mainDocuments;
	}

	public void setMainDocuments(List<PersonDocument> mainDocuments)
	{
		this.mainDocuments = mainDocuments;
	}

	/**
	 * Дополнительные документы клиента (СНИЛС, ИНН, ВУ)
	 * @return
	 */
	public List<UserDocument> getAdditionalDocuments()
	{
		return additionalDocuments;
	}

	public void setAdditionalDocuments(List<UserDocument> additionalDocuments)
	{
		this.additionalDocuments = additionalDocuments;
	}

	public String getAvatarPath()
	{
		return avatarPath;
	}

	public void setAvatarPath(String avatarPath)
	{
		this.avatarPath = avatarPath;
	}
}
