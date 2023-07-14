package com.rssl.phizic.test;

import com.rssl.phizic.config.ConfigurationContext;
import com.rssl.phizic.utils.StringUtils;
import com.rssl.phizic.utils.annotation.PublicDefaultCreatable;
import com.rssl.phizic.utils.clazz.ClassFilter;
import com.rssl.phizic.utils.clazz.ClassFinder;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.Constants;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.List;

/**
 * @author Evgrafov
 * @ created 31.03.2006
 * @ $Author: balovtsev $
 * @ $Revision: 17865 $
 */

@SuppressWarnings({"JavaDoc"})
@PublicDefaultCreatable
public class PublicDefaultCreatableTest extends BusinessTestCaseBase
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private static final String configuration = ConfigurationContext.getIntstance().getActiveConfiguration();

	private class PublicDefaultCreatableFilter implements ClassFilter
	{
		public boolean accept(Class<?> clazz)
		{
			if (!clazz.isAnnotationPresent(PublicDefaultCreatable.class))
				return false;

			if (Modifier.isAbstract(clazz.getModifiers()))
				return false;

			PublicDefaultCreatable publicDefaultCreatable = clazz.getAnnotation(PublicDefaultCreatable.class);
			String[] strings = publicDefaultCreatable.configurations();
			if (strings != null && strings.length > 0 && !StringUtils.contains(strings, configuration))
				return false;

			return true;
		}
	}

	protected void setUp() throws Exception
	{
		super.setUp();
		initializeRsV51Gate();
	}

	public void testFilter() throws IOException
	{
		assertTrue(new PublicDefaultCreatableFilter().accept(this.getClass()));
	}

	public void testTestPublicDefaultCreatable() throws ClassNotFoundException
	{
		int brockenClasses = 0;

		brockenClasses += validate("com.rssl.phizic.auth");
		brockenClasses += validate("com.rssl.phizic.business");
		brockenClasses += validate("com.rssl.phizic.config");
		brockenClasses += validate("com.rssl.phizic.context");
		brockenClasses += validate("com.rssl.phizic.gate");
		brockenClasses += validate("com.rssl.phizic.operations");
		brockenClasses += validate("com.rssl.phizic.security");
		brockenClasses += validate("com.rssl.phizic.utils");
		brockenClasses += validate("com.rssl.common.forms");

		assertEquals(0, brockenClasses);
	}

	private int validate(String packageName) throws ClassNotFoundException
	{
		ClassFinder classFinder = new ClassFinder(packageName, true);
		List<Class> classes = classFinder.find(new PublicDefaultCreatableFilter());

		int brockenClasses = 0;
		for (Class clazz : classes)
		{
			try
			{
				log.info("Starting validation of " + clazz);
				clazz.newInstance();
				log.info("Validation of " + clazz + " passed.");
			}
			catch (Throwable e)
			{
				brockenClasses++;
				log.error("Instantiation of " + clazz + " failed", e);
			}
		}

		log.info("Validated " + classes.size() + " class(es) in package " + packageName + " , " + brockenClasses + " failed");

		return brockenClasses;
	}
}
