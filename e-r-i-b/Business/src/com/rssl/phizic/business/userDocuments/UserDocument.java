package com.rssl.phizic.business.userDocuments;

import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.profile.ProfileConfig;
import com.rssl.phizic.security.ConfirmableObject;
import com.rssl.phizic.security.SecurityLogicException;
import com.rssl.phizic.utils.StringHelper;

import java.util.Calendar;

/**
 * Документ, добавленный пользователем.
 *
 * @author bogdanov
 * @ created 16.04.14
 * @ $Author$
 * @ $Revision$
 */

public class UserDocument  implements ConfirmableObject
{
	/**
	 * Идентификатор документа.
	 */
	private Long id;
	/**
	 * Идентификатор связанного пользователя.
	 */
	private Long loginId;
	/**
	 * Тип документа.
	 */
	private DocumentType documentType;
	/**
	 * Название документа.
	 */
	private String name;
	/**
	 * Серия документа.
	 */
	private String series;
	/**
	 * Номер документа.
	 */
	private String number;
	/**
	 * Дата выдачи
	 */
	private Calendar issueDate;
	/**
	 * Действует до.
	 */
	private Calendar expireDate;
	/**
	 * кем выдан документ.
	 */
	private String issueBy;

	/**
	 * @return тип документа.
	 */
	public DocumentType getDocumentType()
	{
		return documentType;
	}

	/**
	 * @param documentType тип документа.
	 */
	public void setDocumentType(DocumentType documentType)
	{
		this.documentType = documentType;
	}

	/**
	 * @return Действует до.
	 */
	public Calendar getExpireDate()
	{
		return expireDate;
	}

	/**
	 * @param expireDate Действует до.
	 */
	public void setExpireDate(Calendar expireDate)
	{
		this.expireDate = expireDate;
	}

	/**
	 * @return Идентификатор документа.
	 */
	public Long getId()
	{
		return id;
	}

	public byte[] getSignableObject() throws SecurityException, SecurityLogicException
	{
		return null;
	}

	public void setConfirmStrategyType(ConfirmStrategyType confirmStrategyType)
	{
	}

	/**
	 * @param id Идентификатор документа.
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return кем выдан документ.
	 */
	public String getIssueBy()
	{
		return issueBy;
	}

	/**
	 * @param issueBy кем выдан документ.
	 */
	public void setIssueBy(String issueBy)
	{
		this.issueBy = issueBy;
	}

	/**
	 * @return Дата выдачи.
	 */
	public Calendar getIssueDate()
	{
		return issueDate;
	}

	/**
	 * @param issueDate Дата выдачи.
	 */
	public void setIssueDate(Calendar issueDate)
	{
		this.issueDate = issueDate;
	}

	/**
	 * @return Название документа.
	 */
	public String getName()
	{
		if (StringHelper.isEmpty(name))
			return ConfigFactory.getConfig(ProfileConfig.class).getDocumentDefaultName(documentType.name());

		return name;
	}

	/**
	 * @param name название документа.
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return номер документа.
	 */
	public String getNumber()
	{
		return number;
	}

	/**
	 * @param number номер документа.
	 */
	public void setNumber(String number)
	{
		this.number = number;
	}

	/**
	 * @return серия документа.
	 */
	public String getSeries()
	{
		return series;
	}

	/**
	 * @param series серия документа.
	 */
	public void setSeries(String series)
	{
		this.series = series;
	}

	/**
	 * @return идентификатор логина.
	 */
	public Long getLoginId()
	{
		return loginId;
	}

	/**
	 * @param loginId идентификатор логина.
	 */
	public void setLoginId(Long loginId)
	{
		this.loginId = loginId;
	}
}
