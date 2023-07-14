package com.rssl.phizic.business.finances.targets;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.profile.images.ImageInfo;
import com.rssl.phizic.business.profile.images.UserImageService;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.business.resources.external.AccountLink;

import java.util.Calendar;

/**
 * Цель клиента для открытия вклада
 *
 * @author lepihina
 * @ created 19.03.2013
 * @ $Author$
 * @ $Revision$
 */
public class AccountTarget
{
	private Long id;
	private Long loginId;                // ид логина клиента, создавшего цель
	private TargetType dictionaryTarget; // цель из справочника
	private String name;                 // название цели
	private String nameComment;          // комментарий к названию цели
	private Money amount;                // стоимость цели
	private Calendar plannedDate;        // планируемая дата покупки
	private Long claimId;                // ид заявки на открытие вклада
	private String accountNum;           // номер вклада, к которому привязана цель
	private AccountLink accountLink;     // линк вклада, к которому привязана цель
	private Calendar creationDate;       // дата создания цели
	private Long userImage;              // id картинки, загруженной клиентом для цели (только для мАПИ)

	/**
	 * Проставляется дата создания цели - текущая дата.
	 */
	public AccountTarget()
	{
		this.creationDate = Calendar.getInstance();
	}

	/**
	 * @param loginId - ид логина клиента, создавшего цель
	 * @param targetType - цель из справочника
	 */
	public AccountTarget(Long loginId, TargetType targetType)
	{
		this.loginId = loginId;
		this.dictionaryTarget = targetType;
		this.name = targetType.getDescription();
		this.creationDate = Calendar.getInstance();
	}

	/**
	 * @return идентификатор цели
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * @param id - идентификатор цели
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return ид логина клиента, создавшего цель
	 */
	public Long getLoginId()
	{
		return loginId;
	}

	/**
	 * @param loginId - ид логина клиента, создавшего цель
	 */
	public void setLoginId(Long loginId)
	{
		this.loginId = loginId;
	}

	/**
	 * @return цель из справочника
	 */
	public TargetType getDictionaryTarget()
	{
		return dictionaryTarget;
	}

	/**
	 * @param dictionaryTarget - цель из справочника
	 */
	public void setDictionaryTarget(TargetType dictionaryTarget)
	{
		this.dictionaryTarget = dictionaryTarget;
	}

	/**
	 * @return название цели
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name - название цели
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return комментарий к названию цели
	 */
	public String getNameComment()
	{
		return nameComment;
	}

	/**
	 * @param nameComment - комментарий к названию цели
	 */
	public void setNameComment(String nameComment)
	{
		this.nameComment = nameComment;
	}

	/**
	 * @return стоимость цели
	 */
	public Money getAmount()
	{
		return amount;
	}

	/**
	 * @param amount - стоимость цели
	 */
	public void setAmount(Money amount)
	{
		this.amount = amount;
	}

	/**
	 * @return планируемая дата покупки
	 */
	public Calendar getPlannedDate()
	{
		return plannedDate;
	}

	/**
	 * @param plannedDate планируемая дата покупки
	 */
	public void setPlannedDate(Calendar plannedDate)
	{
		this.plannedDate = plannedDate;
	}

	/**
	 * @return идентификатор заявки на открытие вклада
	 */
	public Long getClaimId()
	{
		return claimId;
	}

	/**
	 * @param claimId - идентификатор заявки на открытие вклада
	 */
	public void setClaimId(Long claimId)
	{
		this.claimId = claimId;
	}

	/**
	 * @return номер вклада, к которому привязана цель
	 */
	public String getAccountNum()
	{
		return accountNum;
	}

	/**
	 * @param accountNum - номер вклада, к которому привязана цель
	 */
	public void setAccountNum(String accountNum)
	{
		this.accountNum = accountNum;
	}

	/**
	 * @return линк вклада, к которому привязана цель
	 */
	public AccountLink getAccountLink()
	{
		return accountLink;
	}

	/**
	 * @param accountLink - линк вклада, к которому привязана цель
	 */
	public void setAccountLink(AccountLink accountLink)
	{
		this.accountLink = accountLink;
	}

	/**
	 * @return дата создания цели
	 */
	public Calendar getCreationDate()
	{
		return creationDate;
	}

	/**
	 * @param creationDate - дата создания цели
	 */
	public void setCreationDate(Calendar creationDate)
	{
		this.creationDate = creationDate;
	}

	/**
	 * @return id пользовательской картинки
	 */
	public Long getUserImage()
	{
		return userImage;
	}

	/**
	 * @param userImage id пользовательской картинки
	 */
	public void setUserImage(Long userImage)
	{
		this.userImage = userImage;
	}

	/**
	 * @return относительный путь к файлу
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
