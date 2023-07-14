package com.rssl.phizic.gate.cache.proxy;

import java.lang.annotation.*;

/**
 * ���������� ��� ������� �������, ���������� ������� ���������� ����������.
 * ��� ���� ��������� �� ������� � void ����� ��������������.
 *
 * � ������ ��������� ���������� �������� ���������� �� ���������.
 * �������� �������� ������� �� ����� ehcache.xml.
 *
 * @author Omeliyanchuk
 * @ created 29.04.2010
 * @ $Author$
 * @ $Revision$
 * @see com.rssl.phizic.gate.cache.CacheServiceCreator
 */

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Inherited
public @interface Cachable
{
	/**
	 *
	 * @return ��� ����, ���������� � erib-ehcache.xml.
	 */
	String name();
	//����� ��� ���������� �����
	Class<? extends CacheKeyComposer> keyResolver();
	//��������� ��� ��������� ���������, �������� ���������� ����� ��������� ����
	String resolverParams() default "";
	//������������ ���-�� ��������� ���������� � ������
	int maxElementsInMemory() default 200;
	//����� ��� ���������, ����� �������� ������� ����� ������
	int timeToIdleSeconds() default 900;
	//����� ����� �����
	int timeToLiveSeconds() default 900;
	//����� �� ������������ ��������� �������� � ������ ���������
	boolean linkable() default true;
	//���������� �� ��������� ���� Value == null. ������������ ������ ��� ������ �������������!
	boolean cachingWithNullValue() default false;
	//���������� �� ��������� ����� ������, ���� �� ������������� ���. ������������ ������ ��� ������� � ���������� ��������.
	boolean cachingWithWaitInvoke() default false;
}
