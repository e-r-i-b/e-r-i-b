package com.rssl.phizic.utils.annotation;

import java.lang.annotation.*;

/**
 * ������ ���������� ���� ��������� ������ ���� public � ����� public ����������� ��� ����������
 * @author Evgrafov
 * @ created 31.03.2006
 * @ $Author: Roshka $
 * @ $Revision: 3671 $
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface PublicDefaultCreatable
{
	/**
	 * ���������� ������ � ������������� ������������,
	 * ���� �������� ������� ������������.
	 * @return ������������
	 */
	String[] configurations() default {};
}
