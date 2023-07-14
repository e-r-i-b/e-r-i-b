package com.rssl.phizic.common.types.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * @author Erkin
 * @ created 25.12.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * ����� ���������� ���������� ������ ��������, �� ������� ���������� ���������
 */
@Target({TYPE})
@Retention(SOURCE)
public @interface Stateless
{
}
