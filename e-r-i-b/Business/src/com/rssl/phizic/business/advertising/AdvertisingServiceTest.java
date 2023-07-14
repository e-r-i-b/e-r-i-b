package com.rssl.phizic.business.advertising;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.image.Image;
import com.rssl.phizic.business.image.ImageService;
import com.rssl.phizic.test.BusinessTestCaseBase;

import java.util.*;

/**
 * @author komarov
 * @ created 19.12.2011
 * @ $Author$
 * @ $Revision$
 */

public class AdvertisingServiceTest extends BusinessTestCaseBase
{
	private static final AdvertisingService advertisingService = new AdvertisingService();
	private static final ImageService       imageService = new ImageService();
	private static final DepartmentService  departmentService  = new DepartmentService();
	private static final SimpleService      simpleService      = new SimpleService();	

	private List<Long> ids = new ArrayList<Long>();


	private Set<String> getDepartments() throws BusinessException
	{
		List<Department> tbs =  departmentService.getTerbanks();
		Set<String> departments = new HashSet<String>();
		for(Department tb :tbs)
		{
			departments.add(tb.getRegion());
		}
		return departments;
	}

	private Image getImage() throws BusinessException
	{
		Image img = new Image();
		img.setUpdateTime(Calendar.getInstance().getTime());
		img.setLinkURL("http://vlgwrk192:8888/PhizIC-res/skins/sbrf/7/images/girl_right.gif");
		return imageService.addOrUpdate(img, null);
	}

	private List<AdvertisingButton> getButtons()
	{
		List<AdvertisingButton> buttons = new ArrayList<AdvertisingButton>();

		AdvertisingButton button = new AdvertisingButton();
		button.setOrderIndex(0L);
		button.setShow(false);
		button.setTitle("testTitle");
		button.setUrl("testUrl");
		buttons.add(button);

		button = new AdvertisingButton();
		button.setOrderIndex(0L);
		button.setShow(false);
		button.setTitle("testTitle");
		button.setUrl("testUrl");
		buttons.add(button);
		
		return buttons;
	}

	private AdvertisingBlock getSaveAdvertisingBlock() throws BusinessException
	{
		List<AdvertisingBlock> advesterings = advertisingService.findByIds(ids);
		int size = advesterings.size();
		return advesterings.get(new Random().nextInt(size));
	}

	private AdvertisingBlock getAdvertisingBlock() throws BusinessException
	{
		AdvertisingBlock advertising = new AdvertisingBlock();
		advertising.setName("testName");
		advertising.setPeriodFrom(Calendar.getInstance());
		advertising.setShowTime(10L);
		advertising.setOrderIndex(0L);
		advertising.setImage(getImage());
		advertising.setDepartments(getDepartments());
		advertising.setButtons(getButtons());
		return advertising;

	}

	private void clearChanged() throws BusinessException
	{
		simpleService.removeList(advertisingService.findByIds(ids));
	}

	public void testAddingAdvertising() throws Exception
	{
		AdvertisingBlock advertising = getAdvertisingBlock();

		AdvertisingBlock addingAdvertising = advertisingService.addOrUpdate(advertising, null);
		ids.add(addingAdvertising.getId());
		AdvertisingBlock savedAdvertising = advertisingService.findById(addingAdvertising.getId(), null);
		assertNotNull(savedAdvertising);

	}

	public void testUpdateAdvertising() throws Exception
	{
		AdvertisingBlock addingAdvertising = getSaveAdvertisingBlock();
		addingAdvertising.setName("new Name");
		List<AdvertisingButton> buttons = addingAdvertising.getButtons();
		buttons.clear();
		buttons.addAll(getButtons());
		addingAdvertising.setButtons(buttons);
		addingAdvertising.setImage(getImage());

		addingAdvertising = advertisingService.addOrUpdate(addingAdvertising, null);
		assertNotNull(addingAdvertising);
	}

	public void testRemoveAdvertising() throws Exception
	{
		for (int index = 0; index < ids.size(); index++)
		{
			Long id = ids.get(index);
			AdvertisingBlock addingAdvertising = advertisingService.findById(id, null);
			advertisingService.remove(addingAdvertising, null);
		}
	}

	public void testAdvertisingService() throws Exception
	{
		try
		{
			testAddingAdvertising();
			testUpdateAdvertising();
			testRemoveAdvertising();
		}
		finally
		{
			clearChanged();
		}
	}
}
