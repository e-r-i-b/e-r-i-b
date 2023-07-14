package com.rssl.phizic.business.dictionaries.pages.messages;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.dictionaries.pages.Page;
import com.rssl.phizic.business.dictionaries.pages.PageService;
import com.rssl.phizic.test.BusinessTestCaseBase;
import com.rssl.phizic.utils.RandomHelper;

import java.util.*;

/**
 * @author komarov
 * @ created 16.09.2011
 * @ $Author$
 * @ $Revision$
 */

public class InformMessageServiceTest extends BusinessTestCaseBase
{
	private static final PageService pageService = new PageService();
	private static final SimpleService simpleService = new SimpleService();
	private static final InformMessageService informMessageService = new InformMessageService();
	private static final DepartmentService departmentService = new DepartmentService();

	private List<Long> ids = new ArrayList<Long>();

	private Page getNewPage(String key,
	                        String name,
	                        String url,
	                        Page parent,
	                        Map<String, String> parameters)
	{
		Page page = new Page();
		page.setKey(key);
		page.setName(name);
		page.setUrl(url);
		page.setParent(parent);
		page.setParameters(parameters);
		return page;
	}

	private List<Page> getPages() throws BusinessException
	{
		return pageService.getAll();
	}
	private InformMessage getNewMessage() throws BusinessException
	{
		InformMessage message = new InformMessage();
		Random rnd = new Random();
		message.setText(RandomHelper.rand(5, RandomHelper.ENGLISH_LETTERS));
		message.setStartPublishDate(Calendar.getInstance());
		message.setCancelPublishDate(Calendar.getInstance());

		Set<Page> pages = new HashSet();
		for(Page page : getPages())
		{
			if(rnd.nextBoolean())
			{
				pages.add(page);
			}
		}
		message.setPages(pages);

		Set<String> departments = new HashSet();
		for(Department department : departmentService.getTerbanks())
		{
			if(rnd.nextBoolean() || true)
			{
				departments.add(department.getRegion());
			}
		}
		message.setDepartments(departments);

		return message;
	}

	private void clearChanged() throws BusinessException
	{
		simpleService.removeList(simpleService.findByIds(InformMessage.class, ids));
	}

	private void testAddingInformMessage() throws Exception
	{
		InformMessage im = getNewMessage();
		InformMessage addingIM = informMessageService.addOrUpdate(im);
		ids.add(addingIM.getId());
		InformMessage savedIM = informMessageService.findInformMessageById(addingIM.getId());
		assertNotNull(savedIM);
	}

	
	public void testFindPagesByUrlAndParameters() throws BusinessException
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("form", "LoanPayment");
		String url = "/private/payments/payment.do";
		//assertNotNull(informMessageService.getMessagesByUrlAndParameters(url, map));
	}


	public void testInformMessageService() throws Exception
	{
		try
		{
			testAddingInformMessage();

			testFindPagesByUrlAndParameters();
		}
		finally
		{
			clearChanged();
		}
	}
}
