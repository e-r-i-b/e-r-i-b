package com.rssl.phizic.common.types.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.Inherited;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * @author Erkin
 * @ created 25.12.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * ����� ���������� ���������� ������ ��������, ������� ���������� ���������
 */
@Inherited
@Target({TYPE})
@Retention(SOURCE)
public @interface Statefull
{
}