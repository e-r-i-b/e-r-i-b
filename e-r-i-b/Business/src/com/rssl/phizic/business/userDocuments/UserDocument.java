package com.rssl.phizic.business.userDocuments;

import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.profile.ProfileConfig;
import com.rssl.phizic.security.ConfirmableObject;
import com.rssl.phizic.security.SecurityLogicException;
import com.rssl.phizic.utils.StringHelper;

import java.util.Calendar;

/**
 * ��������, ����������� �������������.
 *
 * @author bogdanov
 * @ created 16.04.14
 * @ $Author$
 * @ $Revision$
 */

public class UserDocument  implements ConfirmableObject
{
	/**
	 * ������������� ���������.
	 */
	private Long id;
	/**
	 * ������������� ���������� ������������.
	 */
	private Long loginId;
	/**
	 * ��� ���������.
	 */
	private DocumentType documentType;
	/**
	 * �������� ���������.
	 */
	private String name;
	/**
	 * ����� ���������.
	 */
	private String series;
	/**
	 * ����� ���������.
	 */
	private String number;
	/**
	 * ���� ������
	 */
	private Calendar issueDate;
	/**
	 * ��������� ��.
	 */
	private Calendar expireDate;
	/**
	 * ��� ����� ��������.
	 */
	private String issueBy;

	/**
	 * @return ��� ���������.
	 */
	public DocumentType getDocumentType()
	{
		return documentType;
	}

	/**
	 * @param documentType ��� ���������.
	 */
	public void setDocumentType(DocumentType documentType)
	{
		this.documentType = documentType;
	}

	/**
	 * @return ��������� ��.
	 */
	public Calendar getExpireDate()
	{
		return expireDate;
	}

	/**
	 * @param expireDate ��������� ��.
	 */
	public void setExpireDate(Calendar expireDate)
	{
		this.expireDate = expireDate;
	}

	/**
	 * @return ������������� ���������.
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
	 * @param id ������������� ���������.
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return ��� ����� ��������.
	 */
	public String getIssueBy()
	{
		return issueBy;
	}

	/**
	 * @param issueBy ��� ����� ��������.
	 */
	public void setIssueBy(String issueBy)
	{
		this.issueBy = issueBy;
	}

	/**
	 * @return ���� ������.
	 */
	public Calendar getIssueDate()
	{
		return issueDate;
	}

	/**
	 * @param issueDate ���� ������.
	 */
	public void setIssueDate(Calendar issueDate)
	{
		this.issueDate = issueDate;
	}

	/**
	 * @return �������� ���������.
	 */
	public String getName()
	{
		if (StringHelper.isEmpty(name))
			return ConfigFactory.getConfig(ProfileConfig.class).getDocumentDefaultName(documentType.name());

		return name;
	}

	/**
	 * @param name �������� ���������.
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return ����� ���������.
	 */
	public String getNumber()
	{
		return number;
	}

	/**
	 * @param number ����� ���������.
	 */
	public void setNumber(String number)
	{
		this.number = number;
	}

	/**
	 * @return ����� ���������.
	 */
	public String getSeries()
	{
		return series;
	}

	/**
	 * @param series ����� ���������.
	 */
	public void setSeries(String series)
	{
		this.series = series;
	}

	/**
	 * @return ������������� ������.
	 */
	public Long getLoginId()
	{
		return loginId;
	}

	/**
	 * @param loginId ������������� ������.
	 */
	public void setLoginId(Long loginId)
	{
		this.loginId = loginId;
	}
}
