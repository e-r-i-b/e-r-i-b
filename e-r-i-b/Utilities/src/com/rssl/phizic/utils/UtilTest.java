package com.rssl.phizic.utils;

import junit.framework.TestCase;

/**
 * @author Evgrafov
 * @ created 28.03.2006
 * @ $Author: zhuravleva $
 * @ $Revision: 1570 $
 */
public class UtilTest extends TestCase
{
    public void testExceptionUtil()
    {
        try
        {
            foo3();
        }
        catch (Exception e)
        {
            String value = ExceptionUtil.printStackTrace(e);

            assertTrue(value.indexOf("[test1]") > 0);
            assertTrue(value.indexOf("[test2]") > 0);
            assertTrue(value.indexOf("[test3]") > 0);
        }
    }

    private void foo1()
    {
        throw new NullPointerException("[test1]");
    }

    private void foo2() throws Exception
    {
        try
        {
            foo1();
        }
        catch (Exception e)
        {
            throw new Exception("[test2]", e);
        }
    }

    private void foo3() throws Exception
    {
        try
        {
            foo2();
        }
        catch (Exception e)
        {
            throw new Exception("[test3]", e);
        }
    }
}