package com.rssl.auth.csa.wsclient.responses;

import com.rssl.phizic.common.types.ConfirmStrategyType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author akrenev
 * @ created 11.04.2013
 * @ $Author$
 * @ $Revision$
 *
 *  информация о подтверждении
 */

@SuppressWarnings({"ReturnOfCollectionOrArrayField"})
public class ConfirmationInformation
{
	private ConfirmStrategyType preferredConfirmType;
	private List<String> cardConfirmationSource = new ArrayList<String>();
	private boolean pushAllowed;

	/**
	 * @return предпочтительный способ подтверждения
	 */
	public ConfirmStrategyType getPreferredConfirmType()
	{
		return preferredConfirmType;
	}

	/**
	 * задать предпочтительный способ подтверждения (по строке)
	 * @param preferredConfirmType предпочтительный способ подтверждения
	 */
	public void setPreferredConfirmType(String preferredConfirmType)
	{
		this.preferredConfirmType = ConfirmStrategyType.valueOf(preferredConfirmType);
	}

	/**
	 * @return информация о картах, как источнике подтверждения      
	 */
	public List<String> getCardConfirmationSource()
	{
		return cardConfirmationSource;
	}

	/**
	 * добавить информацию о карте, как источнике подтверждения
	 * @param cardNumber информация о карте, как источнике подтверждения
	 */
	public void addCardConfirmationSource(String cardNumber)
	{
		cardConfirmationSource.add(cardNumber);
	}

	/**
	 * @return доступно ли подтверждение по push
	 */
	public boolean isPushAllowed()
	{
		return pushAllowed;
	}

	/**
	 * задать доступно ли подтверждение по push
	 * @param pushAllowed доступно ли подтверждение по push
	 */
	public void setPushAllowed(boolean pushAllowed)
	{
		this.pushAllowed = pushAllowed;
	}
}
