package com.rssl.phizic.business.finances.targets;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.profile.images.ImageInfo;
import com.rssl.phizic.business.profile.images.UserImageService;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.business.resources.external.AccountLink;

import java.util.Calendar;

/**
 * ���� ������� ��� �������� ������
 *
 * @author lepihina
 * @ created 19.03.2013
 * @ $Author$
 * @ $Revision$
 */
public class AccountTarget
{
	private Long id;
	private Long loginId;                // �� ������ �������, ���������� ����
	private TargetType dictionaryTarget; // ���� �� �����������
	private String name;                 // �������� ����
	private String nameComment;          // ����������� � �������� ����
	private Money amount;                // ��������� ����
	private Calendar plannedDate;        // ����������� ���� �������
	private Long claimId;                // �� ������ �� �������� ������
	private String accountNum;           // ����� ������, � �������� ��������� ����
	private AccountLink accountLink;     // ���� ������, � �������� ��������� ����
	private Calendar creationDate;       // ���� �������� ����
	private Long userImage;              // id ��������, ����������� �������� ��� ���� (������ ��� ����)

	/**
	 * ������������� ���� �������� ���� - ������� ����.
	 */
	public AccountTarget()
	{
		this.creationDate = Calendar.getInstance();
	}

	/**
	 * @param loginId - �� ������ �������, ���������� ����
	 * @param targetType - ���� �� �����������
	 */
	public AccountTarget(Long loginId, TargetType targetType)
	{
		this.loginId = loginId;
		this.dictionaryTarget = targetType;
		this.name = targetType.getDescription();
		this.creationDate = Calendar.getInstance();
	}

	/**
	 * @return ������������� ����
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * @param id - ������������� ����
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return �� ������ �������, ���������� ����
	 */
	public Long getLoginId()
	{
		return loginId;
	}

	/**
	 * @param loginId - �� ������ �������, ���������� ����
	 */
	public void setLoginId(Long loginId)
	{
		this.loginId = loginId;
	}

	/**
	 * @return ���� �� �����������
	 */
	public TargetType getDictionaryTarget()
	{
		return dictionaryTarget;
	}

	/**
	 * @param dictionaryTarget - ���� �� �����������
	 */
	public void setDictionaryTarget(TargetType dictionaryTarget)
	{
		this.dictionaryTarget = dictionaryTarget;
	}

	/**
	 * @return �������� ����
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name - �������� ����
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return ����������� � �������� ����
	 */
	public String getNameComment()
	{
		return nameComment;
	}

	/**
	 * @param nameComment - ����������� � �������� ����
	 */
	public void setNameComment(String nameComment)
	{
		this.nameComment = nameComment;
	}

	/**
	 * @return ��������� ����
	 */
	public Money getAmount()
	{
		return amount;
	}

	/**
	 * @param amount - ��������� ����
	 */
	public void setAmount(Money amount)
	{
		this.amount = amount;
	}

	/**
	 * @return ����������� ���� �������
	 */
	public Calendar getPlannedDate()
	{
		return plannedDate;
	}

	/**
	 * @param plannedDate ����������� ���� �������
	 */
	public void setPlannedDate(Calendar plannedDate)
	{
		this.plannedDate = plannedDate;
	}

	/**
	 * @return ������������� ������ �� �������� ������
	 */
	public Long getClaimId()
	{
		return claimId;
	}

	/**
	 * @param claimId - ������������� ������ �� �������� ������
	 */
	public void setClaimId(Long claimId)
	{
		this.claimId = claimId;
	}

	/**
	 * @return ����� ������, � �������� ��������� ����
	 */
	public String getAccountNum()
	{
		return accountNum;
	}

	/**
	 * @param accountNum - ����� ������, � �������� ��������� ����
	 */
	public void setAccountNum(String accountNum)
	{
		this.accountNum = accountNum;
	}

	/**
	 * @return ���� ������, � �������� ��������� ����
	 */
	public AccountLink getAccountLink()
	{
		return accountLink;
	}

	/**
	 * @param accountLink - ���� ������, � �������� ��������� ����
	 */
	public void setAccountLink(AccountLink accountLink)
	{
		this.accountLink = accountLink;
	}

	/**
	 * @return ���� �������� ����
	 */
	public Calendar getCreationDate()
	{
		return creationDate;
	}

	/**
	 * @param creationDate - ���� �������� ����
	 */
	public void setCreationDate(Calendar creationDate)
	{
		this.creationDate = creationDate;
	}

	/**
	 * @return id ���������������� ��������
	 */
	public Long getUserImage()
	{
		return userImage;
	}

	/**
	 * @param userImage id ���������������� ��������
	 */
	public void setUserImage(Long userImage)
	{
		this.userImage = userImage;
	}

	/**
	 * @return ������������� ���� � �����
	 * @throws BusinessException
	 */
	public String getImagePath() throws BusinessException
	{
		if (userImage!=null)
		{
			ImageInfo info =  UserImageService.get().getImageInfo(userImage);
			if (info!= null)
				return info.getPath();
		}
		return null;
	}
}
