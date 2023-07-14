package com.rssl.phizic.common.types.annotation;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * @author Erkin
 * @ created 25.12.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * Такой аннотацией помечаются параметры методов и атрибуты классов-функций
 * (задачи, экшены, операции, джобы и т.п.),
 * которые нужно указать в обязательном порядке
 */
@Inherited
@Target({METHOD, PARAMETER, FIELD})
@Retention(SOURCE)
public @interface MandatoryParameter
{
}
