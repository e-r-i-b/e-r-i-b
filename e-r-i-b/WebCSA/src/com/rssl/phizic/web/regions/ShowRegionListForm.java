package com.rssl.phizic.web.regions;

import com.rssl.auth.csa.front.business.regions.Region;
import org.apache.struts.action.ActionForm;

import java.util.List;

/**
 * @author komarov
 * @ created 18.03.2013 
 * @ $Author$
 * @ $Revision$
 */

public class ShowRegionListForm extends ActionForm
{
	// id выбранного региона
	private Long id;
	//Список регионов
	private List<Region> regions = null;
	private boolean saved;

	/**
	 * @return идентификатор региона
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * @param id идентификатор региона
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @param regions список регионов
	 */
	public void setRegions(List<Region> regions)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.regions = regions;
	}

	/**
	 * @return список регионов
	 */
	public List<Region> getRegions()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return regions;
	}

	/**
	 * @return регион сохранён
	 */
	public boolean isSaved()
	{
		return saved;
	}

	/**
	 * @param saved регион сохранён
	 */
	public void setSaved(boolean saved)
	{
		this.saved = saved;
	}
}
