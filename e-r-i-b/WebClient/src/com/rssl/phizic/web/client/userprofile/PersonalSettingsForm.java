package com.rssl.phizic.web.client.userprofile;

import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.favouritelinks.FavouriteLink;
import com.rssl.phizic.business.menulinks.MenuLinkInfo;
import com.rssl.phizic.business.skins.Skin;
import com.rssl.phizic.web.ext.sbrf.products.ProductsSetupForm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author mihaylov
 * @ created 28.04.2010
 * @ $Author$
 * @ $Revision$
 */

public class PersonalSettingsForm extends ProductsSetupForm
{
	private List<FavouriteLink> links;
	private List<TemplateDocument> templates;
	private List<MenuLinkInfo> menuLinks;
	private Long selectedMenuLinkId;
	private int[] sortMenuLinks = new int[]{};
	private Boolean bankOfferViewed;

	private String[] selectedMenuLinks = new String[]{};
	private String option;

	private boolean useWidget; //показывать или нет виджеты на главной странице

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

	///////////////////////////////////////////////////////////////////////////

	public List<MenuLinkInfo> getMenuLinks()
	{
		return menuLinks;
	}

	public void setMenuLinks(List<MenuLinkInfo> menuLinks)
	{
		this.menuLinks = menuLinks;
	}

	public Long getSelectedMenuLinkId()
	{
		return selectedMenuLinkId;
	}

	public void setSelectedMenuLinkId(Long selectedMenuLinkId)
	{
		this.selectedMenuLinkId = selectedMenuLinkId;
	}

	public int[] getSortMenuLinks()
	{
		return sortMenuLinks;
	}

	public void setSortMenuLinks(int[] sortMenuLinks)
	{
		this.sortMenuLinks = sortMenuLinks;
	}

	public String[] getSelectedMenuLinks()
	{
		return selectedMenuLinks;
	}

	public void setSelectedMenuLinks(String[] selectedMenuLinks)
	{
		this.selectedMenuLinks = selectedMenuLinks;
	}

	public String getOption()
	{
		return option;
	}

	public void setOption(String option)
	{
		this.option = option;
	}

	public List<Skin> getSkins()
	{
		return skins;
	}

	public void setSkins(List<Skin> skins)
	{
		this.skins = skins;
	}

	public Long getPreviewSkin()
	{
		return previewSkin;
	}

	public void setPreviewSkin(Long previewSkin)
	{
		this.previewSkin = previewSkin;
	}

	public Skin getCurrentSkin()
	{
		return currentSkin;
	}

	public void setCurrentSkin(Skin currentSkin)
	{
		this.currentSkin = currentSkin;
	}

	public Boolean getBankOfferViewed()
	{
		return bankOfferViewed;
	}

	public void setBankOfferViewed(Boolean bankOfferViewed)
	{
		this.bankOfferViewed = bankOfferViewed;
	}

	public boolean getUseWidget()
	{
		return useWidget;
	}

	public void setUseWidget(boolean useWidget)
	{
		this.useWidget = useWidget;
	}
}
