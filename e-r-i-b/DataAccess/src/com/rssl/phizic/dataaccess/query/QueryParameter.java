package com.rssl.phizic.dataaccess.query;

import java.lang.annotation.*;

/**
 * ќтмечает геттеры, которые €вл€ютс€ параметрами запроса.
 * @author komarov
 * @ created 07.04.2012
 * @ $Author$
 * @ $Revision$
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface QueryParameter
{
}
