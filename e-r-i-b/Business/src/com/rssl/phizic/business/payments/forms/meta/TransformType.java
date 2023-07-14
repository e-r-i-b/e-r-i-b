package com.rssl.phizic.business.payments.forms.meta;

/**
 * ��� xslt-�������������� ��������� �����
 * ����� ��������������, ����������� ��� ������������ �������, ������ ��������� ��������������� ������� Application
 * @see com.rssl.phizic.common.types.Application
 * @author Dorzhinov
 * @ created 03.09.2012
 * @ $Author$
 * @ $Revision$
 */
public enum TransformType
{
    /**
     * WebClient + WebAdmin
     */
	html,
	print,
    /**
     * WebMobile
     */
	mobile5,
    mobile6,
    mobile7,
	mobile8,
	mobile9,
    /**
     * WebATM
     */
	atm,
	check,
	webapi,
	social
}
