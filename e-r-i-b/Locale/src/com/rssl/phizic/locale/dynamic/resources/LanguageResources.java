package com.rssl.phizic.locale.dynamic.resources;

import java.io.Serializable;

/**
 * ��������� ��� ������������ ������������� ���������
 * @author komarov
 * @ created 02.10.2014
 * @ $Author$
 * @ $Revision$
 */
public interface LanguageResources<T> extends Serializable
{
	/**
	 * @return �������������
	 */
	T getId();

	/**
	 * @return ������������� ������
	 */
	String getLocaleId();
}
