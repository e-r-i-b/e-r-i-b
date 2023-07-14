package com.rssl.phizic.business.quick.pay;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderBase;
import static com.rssl.phizic.business.quick.pay.QuickPaymentPanelState.ACTIVE;
import com.rssl.phizic.test.BusinessTestCaseBase;

import java.util.*;

/**
 * @author komarov
 * @ created 06.07.2012
 * @ $Author$
 * @ $Revision$
 */

public class QuickPaymentPanelServiceTest  extends BusinessTestCaseBase
{
	private static final QuickPaymentPanelService quickPaymentPanelService = new QuickPaymentPanelService();
	private static final DepartmentService        departmentService        = new DepartmentService();
	private static final SimpleService            simpleService            = new SimpleService();	

	private List<Long> ids = new ArrayList<Long>();

	private Set<String> getDepartments() throws BusinessException
	{
		Set<String> departments = new HashSet<String>();
		for(Department tb : departmentService.getTerbanks())
			departments.add(tb.getRegion());
		return departments;
	}

	private void clearChanged() throws BusinessException
	{
		simpleService.removeList(simpleService.findByIds( QuickPaymentPanel.class, ids));
	}

	private ServiceProviderBase getServiceProvider() throws Exception
	{
		List<ServiceProviderBase> providers = simpleService.getAll(ServiceProviderBase.class);
		return providers.get(new Random().nextInt(providers.size()));
	}

	private List<PanelBlock> getPanelBlocks() throws Exception
	{
		List<PanelBlock> list = new ArrayList<PanelBlock>();

		PanelBlock panelBlock = new PanelBlock();
		panelBlock.setOrder(0L);
		panelBlock.setShow(true);
		panelBlock.setProviderId(getServiceProvider().getId());
		list.add(panelBlock);
		return list;
	}

	private QuickPaymentPanel getSaveQuickPaymentPanel() throws BusinessException
	{
		List<QuickPaymentPanel> panels = simpleService.findByIds(QuickPaymentPanel.class, ids);
		int size = panels.size();
		return panels.get(new Random().nextInt(size));
	}

	private QuickPaymentPanel createQuickPaymentPanel() throws Exception
	{
		QuickPaymentPanel panel = new QuickPaymentPanel();
		panel.setName("testName");
		panel.setState(ACTIVE);
		panel.setPeriodFrom(Calendar.getInstance());
		panel.setDepartments(getDepartments());
		panel.setPanelBlocks(getPanelBlocks());
		return panel;
	}

	public void testAddQuickPaymentPanel() throws Exception
	{
		QuickPaymentPanel panel = createQuickPaymentPanel();
		QuickPaymentPanel addingPanel = quickPaymentPanelService.addOrUpdate(panel, null);
		ids.add(addingPanel.getId());
		QuickPaymentPanel savedPanel = quickPaymentPanelService.findById(addingPanel.getId());
		assertNotNull(savedPanel);
	}

	public void testUpdateQuickPaymentPanel() throws Exception
	{
		QuickPaymentPanel addingPanel = getSaveQuickPaymentPanel();
		addingPanel.setName("new Name");
		List<PanelBlock> blocks = addingPanel.getPanelBlocks();
		blocks.clear();
		blocks.addAll(getPanelBlocks());
		addingPanel.setPanelBlocks(blocks);


		addingPanel = quickPaymentPanelService.addOrUpdate(addingPanel,null);
		assertNotNull(addingPanel);
	}

	public void testRemoveQuickPaymentPanel() throws Exception
	{
		for (int index = 0; index < ids.size(); index++)
		{
			Long id = ids.get(index);
			QuickPaymentPanel addingPanel = quickPaymentPanelService.findById(id);
			quickPaymentPanelService.remove(addingPanel,null);
		}
	}

	public void testQuickPaymentPanelService() throws Exception
	{
		try
		{
			 testAddQuickPaymentPanel();
			 testUpdateQuickPaymentPanel();
			 testRemoveQuickPaymentPanel();
		}
		finally
		{
			clearChanged();
		}
	}
}
