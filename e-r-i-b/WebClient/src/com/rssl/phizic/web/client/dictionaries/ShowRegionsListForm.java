package com.rssl.phizic.web.client.dictionaries;

import com.rssl.phizic.business.dictionaries.regions.Region;
import com.rssl.phizic.web.actions.ActionFormBase;

import java.util.List;

/**
 * @author Rydvanskiy
 * @ created 26.04.2010
 * @ $Author$
 * @ $Revision$
 */
public class ShowRegionsListForm extends ActionFormBase
{
	// id выбранного региона
	private Long id;
	// флаг обозначающий что надо вывести соседей если нет детей
	//  иначе при отсутствии детей выбор сохраняется в PersonContext
	private boolean isOpening = false;
	// Навигационный массив
	private List<Region> navigation = null;
	// сортированный по алфавиту список регионов
	private List<Region> regions = null;
	// флаг, показывающий, что необходимо сохранить текущий id как регион.
	private boolean select = false;
	//флаг, блокирующий запись в personalContext
	private boolean setCnt = false;
	//флаг, показывающий, что необходимо сохранить выбранный регион, как регион оплаты
	private boolean needSave = true;

	private boolean saved;
	//флаг, указывающий доступна ли возможность сохранения региона
	private Boolean saveRegion;

	private String regionName;
	private Long oldId;

	public boolean getSetCnt()
	{
		return setCnt;
	}

	public void setSetCnt(boolean setCnt)
	{
		this.setCnt = setCnt;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public boolean getIsOpening()
	{
		return isOpening;
	}

	public void setIsOpening(boolean isOpening)
	{
		this.isOpening = isOpening;
	}

	public void setNavigation(List<Region> navigation)
	{
		this.navigation = navigation;
	}

	public List<Region> getNavigation()
	{
		return this.navigation;
	}

	public List<Region> getRegions()
	{
		return this.regions;
	}

	public void setRegions(List<Region> regions)
	{
		this.regions = regions;
	}

	public boolean getSelect()
	{
		return select;
	}

	public void setSelect(boolean select)
	{
		this.select = select;
	}

	public boolean getNeedSave()
	{
		return needSave;
	}

	public void setNeedSave(boolean needSave)
	{
		this.needSave = needSave;
	}

	public boolean isOpening()
	{
		return isOpening;
	}

	public void setOpening(boolean opening)
	{
		isOpening = opening;
	}

	public boolean isSaved()
	{
		return saved;
	}

	public void setSaved(boolean saved)
	{
		this.saved = saved;
	}

	public Boolean getSaveRegion()
	{
		return saveRegion;
	}

	public void setSaveRegion(Boolean saveRegion)
	{
		this.saveRegion = saveRegion;
	}

	public String getRegionName()
	{
		return regionName;
	}

	public void setRegionName(String regionName)
	{
		this.regionName = regionName;
	}

	public Long getOldId()
	{
		return oldId;
	}

	public void setOldId(Long oldId)
	{
		this.oldId = oldId;
	}
}
