package com.rssl.phizic.web.client.dictionaries;

import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.regions.Region;
import com.rssl.phizic.business.dictionaries.regions.RegionDictionaryService;
import com.rssl.phizic.business.regions.RegionHelper;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.context.PersonDataProvider;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.dictionaries.regions.RegionsListOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Rydvanskiy
 * @ created 26.04.2010
 * @ $Author$
 * @ $Revision$
 */
public class ShowRegionsListAction extends OperationalActionBase
{
	private static final RegionDictionaryService regionService = new RegionDictionaryService();

	protected void doFilter(RegionsListOperation operation, ShowRegionsListForm frm) throws Exception
	{
		Query query = operation.createQuery("list");

		Region region = operation.getRegion();
		//Ищем всех детей выбранного региона
		Long regionId = region.getId();
		query.setParameter("parent", RegionHelper.isAllRegionsSelected(regionId)? null: regionId);
		List<Region> regions = query.executeList();
		// Если дети отсутствуют и страница только открылась необходимо показать соседей
		if (regions.size() == 0 && !frm.getIsOpening()) // Устанавливаем регион
		{
			if (!frm.getSetCnt())
			{
				PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
				personData.getProfile().selectRegion(region);
				personData.setCurrentRegion(region);
				if (frm.getNeedSave())
					saveRegion(operation, frm, personData.getPerson().getLogin());
			}
			else
				if (RegionHelper.isOneRegionSelected(regionId))
					frm.setNavigation(RegionHelper.arrayToList(region));
			return;
		}

		if (regions.isEmpty())
		{
			region = region.getParent();
			query.setParameter("parent", (region == null) ? null : region.getId());
			regions = query.executeList();
		}

		if (RegionHelper.isOneRegionSelected(regionId))
			frm.setNavigation(RegionHelper.arrayToList(region));

		frm.setRegions(regions);
	}

	protected boolean getEmptyErrorPage()
	{
		return true;
	}

	public final ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ShowRegionsListForm frm = (ShowRegionsListForm) form;
		RegionsListOperation operation;
		operation = createOperation("RegionsListOperation", "RegionsList");

		if (frm.getIsOpening() && frm.getId() == null)
		{
			Region region = null;
			if (PersonContext.isAvailable())
				region = PersonContext.getPersonDataProvider().getPersonData().getCurrentRegion();
			frm.setId((region == null) ? null : region.getId());
		}
		//Все проверки и так были в операции.
		operation.initialize(frm.getId());
		if (frm.getOldId() != null)
		{
			Region region = regionService.findById(frm.getOldId());
			if (region != null)
				frm.setRegionName(region.getName());
		}
		else
		{
			frm.setRegionName(operation.getRegion().getName());
		}

		// выбираем регион если стоит флаг "select"
		if (frm.getSelect())
		{
			if (!frm.getSetCnt())
			{
				PersonDataProvider personDataProvider = PersonContext.getPersonDataProvider();
				Region region = RegionHelper.isOneRegionSelected(frm.getId()) ? operation.getRegion() : null;
				personDataProvider.getPersonData().getProfile().selectRegion(region);
				personDataProvider.getPersonData().setCurrentRegion(region);
				PersonContext.setPersonDataProvider(personDataProvider);
				if (frm.getNeedSave())
					saveRegion(operation, frm, personDataProvider.getPersonData().getPerson().getLogin());
			}
			else if (RegionHelper.isOneRegionSelected(frm.getId()))
				frm.setNavigation(RegionHelper.arrayToList(operation.getRegion()));
		}
		else
			doFilter(operation, frm);

		return getCurrentMapping().findForward(FORWARD_START);
	}

	private void saveRegion(RegionsListOperation operation, ShowRegionsListForm form, CommonLogin login) throws BusinessException
	{
		operation.saveRegion(login);
		form.setSaved(true);
	}
}
