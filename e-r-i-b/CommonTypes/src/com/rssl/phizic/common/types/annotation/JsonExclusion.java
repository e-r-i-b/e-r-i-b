package com.rssl.phizic.common.types.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

/**
 * @author Moshenko
 * @ created 30.11.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * Такой аннотацией помечаются поля объектов, который не будут включаться в json поток.
 */
@Target({FIELD,PARAMETER,LOCAL_VARIABLE})
@Retention(RUNTIME)
public @interface JsonExclusion
{
}
