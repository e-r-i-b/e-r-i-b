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
	// id ���������� �������
	private Long id;
	//������ ��������
	private List<Region> regions = null;
	private boolean saved;

	/**
	 * @return ������������� �������
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * @param id ������������� �������
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @param regions ������ ��������
	 */
	public void setRegions(List<Region> regions)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.regions = regions;
	}

	/**
	 * @return ������ ��������
	 */
	public List<Region> getRegions()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return regions;
	}

	/**
	 * @return ������ �������
	 */
	public boolean isSaved()
	{
		return saved;
	}

	/**
	 * @param saved ������ �������
	 */
	public void setSaved(boolean saved)
	{
		this.saved = saved;
	}
}
