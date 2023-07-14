package com.rssl.phizic.web.client.header;

import com.rssl.phizic.web.actions.ActionFormBase;
import com.rssl.phizic.business.skins.Skin;

import java.util.List;

/**
 * @author Dorzhinov
 * @ created 28.06.2012
 * @ $Author$
 * @ $Revision$
 */
public class AsyncHeaderSkinsForm extends ActionFormBase
{
	/**
	 * Список стилей, доступных пользователю
	 */
	private List<Skin> skins;

	/**
	 * Стиль для предпросмотра
	 */
	private Long previewSkin;

	/**
	 * Текущий стиль пользователя
	 */
	private Skin currentSkin;

	public List<Skin> getSkins()
	{
		return skins;
	}

	public void setSkins(List<Skin> skins)
	{
		this.skins = skins;
	}

	public Skin getCurrentSkin()
	{
		return currentSkin;
	}

	public void setCurrentSkin(Skin currentSkin)
	{
		this.currentSkin = currentSkin;
	}

	public Long getPreviewSkin()
	{
		return previewSkin;
	}

	public void setPreviewSkin(Long previewSkin)
	{
		this.previewSkin = previewSkin;
	}
}
