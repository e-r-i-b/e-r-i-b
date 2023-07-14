package com.rssl.phizic.business.dictionaries.pages;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.test.BusinessTestCaseBase;
import com.rssl.phizic.utils.RandomHelper;
import org.hibernate.exception.ConstraintViolationException;

import java.util.*;

/**
 * @author akrenev
 * @ created 29.08.2011
 * @ $Author$
 * @ $Revision$
 */
public class PageServiceTest extends BusinessTestCaseBase
{
	private static final PageService pageService = new PageService();
	private static final SimpleService simpleService = new SimpleService();

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

	private Page getNewPage()
	{
		String uniqueKey = RandomHelper.rand(5, RandomHelper.ENGLISH_LETTERS);
		return getNewPage(uniqueKey, "page_name_for_".concat(uniqueKey), "page_url_for_".concat(uniqueKey), null, null);
	}

	private Page getAnySavedPage() throws BusinessException
	{
		List<Page> pages = simpleService.findByIds(Page.class, ids);
		assertNotNull(pages);
		assertFalse(pages.isEmpty());
		Random rundom = new Random();
		int index = rundom.nextInt(pages.size());
		return pages.get(index);
	}

	private void clearChanged() throws BusinessException
	{
		simpleService.removeList(simpleService.findByIds(Page.class, ids));
	}

	public void testDublicatePageKey() throws Exception
	{
		Page page = getAnySavedPage();
		Page pageDublicate = getNewPage();
		pageDublicate.setKey(page.getKey());
		try
		{
			pageService.addOrUpdate(pageDublicate);
		}
		catch (ConstraintViolationException ex)
		{
			return;
		}
		fail();
	}

	public void testAddingPage() throws Exception
	{
		Page newPage = getNewPage();
		Page addingPage = pageService.addOrUpdate(newPage);
		ids.add(addingPage.getId());
		Page savedPage = pageService.findPageByID(addingPage.getId());
		assertNotNull(savedPage);
	}

	public void testAddingFullPage() throws Exception
	{
		Page newPage = getNewPage();
		Page parentPage = getAnySavedPage();
		newPage.setParent(parentPage);
		Map<String, String> parameters = new HashMap<String, String>();
		String parameterKey = RandomHelper.rand(5, RandomHelper.ENGLISH_LETTERS);
		parameters.put("parameterKey = " + parameterKey, "parameterValue = " + parameterKey);
		parameterKey = RandomHelper.rand(5, RandomHelper.ENGLISH_LETTERS);
		parameters.put("parameterKey = " + parameterKey, "parameterValue = " + parameterKey);
		newPage.setParameters(parameters);
		Page savedPage = pageService.addOrUpdate(newPage);
		assertNotNull(savedPage);
		ids.add(savedPage.getId());
	}

	public void testUpdatingPage() throws Exception
	{
		Page updatingPage = getAnySavedPage();
		Page parentPage = getAnySavedPage();
		while (updatingPage.getId().equals(parentPage.getId()))
		{
			parentPage = getAnySavedPage();
		}
		updatingPage.setParent(parentPage);
		updatingPage = pageService.addOrUpdate(updatingPage);
		assertNotNull(updatingPage);

		updatingPage.setName("New Name");
		updatingPage = pageService.addOrUpdate(updatingPage);
		assertNotNull(updatingPage);

		updatingPage.setUrl("New URL");
		updatingPage = pageService.addOrUpdate(updatingPage);
		assertNotNull(updatingPage);

		try
		{
			updatingPage.setId(Long.valueOf(999));
			updatingPage = pageService.addOrUpdate(updatingPage);
		}
		catch (Exception e)
		{
			updatingPage = getAnySavedPage();
			assertTrue(true);
		}

		try
		{
			updatingPage.setKey("New Key");
			updatingPage = pageService.addOrUpdate(updatingPage);
		}
		catch (Exception e)
		{
			assertTrue(true);
		}
	}

	public void testDeletingPage() throws Exception
	{
		for (int index = 0; index < ids.size(); index++)
		{
			Long id = ids.get(index);
			Page page = pageService.findPageByID(id);
			pageService.remove(page);
		}
	}

	public void testFindPagesByUrlAndParameters() throws BusinessException
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("form", "LoanPayment");
		String url = "/private/payments/payment.do";
		assertNotNull(pageService.findPagesByUrlAndParameters(url, map));
	}

	public void testPageService() throws Exception
	{
		try
		{
			testAddingPage();

			testDublicatePageKey();

			testAddingFullPage();

			testUpdatingPage();

			testFindPagesByUrlAndParameters();

			testDeletingPage();
		}
		finally
		{
			clearChanged();
		}
	}
}
