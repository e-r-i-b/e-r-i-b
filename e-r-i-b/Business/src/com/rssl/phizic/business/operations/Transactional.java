package com.rssl.phizic.business.operations;

import java.lang.annotation.*;

/**
 * ������ ��������, ���������� ������ ����������, ����������� � ���������.
 *
 * @author Kidyaev
 * @ created 26.04.2006
 * @ $Author$
 * @ $Revision$
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Transactional
{
}
