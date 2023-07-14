package com.rssl.common.forms.parsers;

import java.text.ParseException;
import java.io.Serializable;

/**
 * ������ ������ ��������� ������������� � ����������� ���
 * @author Evgrafov
 * @ created 01.12.2005
 * @ $Author: khudyakov $
 * @ $Revision: 50095 $
 */

public interface FieldValueParser<T extends Serializable> extends Serializable
{
    T parse(String value) throws ParseException;
}