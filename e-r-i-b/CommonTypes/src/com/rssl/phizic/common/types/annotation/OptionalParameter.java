package com.rssl.phizic.common.types.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
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
 * которые указывать не обязательно
 */
@Target({METHOD, PARAMETER, FIELD})
@Retention(SOURCE)
public @interface OptionalParameter
{
}