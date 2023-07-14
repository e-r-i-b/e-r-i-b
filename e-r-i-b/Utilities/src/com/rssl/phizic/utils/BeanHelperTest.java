package com.rssl.phizic.utils;

import junit.framework.TestCase;

import java.util.*;

/**
 * @author Kosyakov
 * @ created 03.08.2006
 * @ $Author: omeliyanchuk $
 * @ $Revision: 11218 $
 */
public class BeanHelperTest extends TestCase
{
	public void testCopyBeans ()
	{
		Bean1 bean1 = new Bean1();
		Bean2 bean2 = new Bean2();
		bean1.setString("bean");
		bean1.setNumber(new Long(123));
		BeanHelper.copyProperties(bean2, bean1);
		assertEquals(bean1.getString(), bean2.getString());
		assertEquals(bean1.getNumber().toString(), bean2.getNumber());
	}

	public void testBeanFromMap ()
	{
		Bean1 bean = new Bean1();
		Map<String, String> map = new HashMap<String, String>();
		map.put("string", "bean");
		map.put("number", "123");
		BeanHelper.SetPropertiesFromMap(bean, map);
		assertEquals(bean.getString(), map.get("string"));
		assertEquals(bean.getNumber().toString(), map.get("number"));
	}

	public void testMapFromBean ()
	{
		Bean1 bean = new Bean1();
		bean.setString("bean");
		bean.setNumber(new Long(123));
		Map map = BeanHelper.createMapFromProperties(bean);
		assertEquals(bean.getString(), map.get("string"));
		assertEquals(bean.getNumber().toString(), map.get("number"));
	}

	public void testBeanComparator()
	{
		Bean1 bean = new Bean1();
		bean.setString("bean");
		bean.setNumber(123l);

		Bean2 bean2 = new Bean2();
		bean2.setString("bean");
		bean2.setNumber("123");

		Bean1 bean1 = new Bean1();
		bean1.setString("bean2");
		bean1.setNumber(123l);

		assertTrue(BeanHelper.compareBeans(bean,bean1)!=0);

		bean1.setString("bean");

		assertTrue(BeanHelper.compareBeans(bean,bean1)==0);

		BigBean bbean1 = new BigBean();
		bbean1.setBean(bean);
		bbean1.setBean2(bean2);
		bbean1.setValue(123);
		bbean1.setStrValue(null);
		bbean1.setCalendarValue(Calendar.getInstance());

		BigBean bbean2 = new BigBean();
		bbean2.setBean(bean);
		bbean2.setBean2(bean2);
		bbean2.setValue(124);
		bbean2.setStrValue(null);
		bbean2.setCalendarValue(Calendar.getInstance());

		assertTrue(BeanHelper.compareBeans(bbean1,bbean2)!=0);

		bbean2.setValue(123);

		Calendar cal = DateHelper.getCurrentDate();
		bbean1.setCalendarValue((Calendar)cal.clone());
		bbean2.setCalendarValue(cal);

		assertTrue(BeanHelper.compareBeans(bbean1,bbean2)==0);
	}
}

