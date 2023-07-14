package com.rssl.phizic.business.ermb.migration.list.task.migration.asfilial;

import com.rssl.phizic.common.types.annotation.PlainOldJavaObject;

import java.util.HashSet;
import java.util.Set;

/**
 * ��������� ����-��������� ������� � ��� � ��������� �������� ���->���� �� ������� �� ���
 * @author Puzikov
 * @ created 22.04.15
 * @ $Author$
 * @ $Revision$
 */

@PlainOldJavaObject
@SuppressWarnings("PackageVisibleField")
class MbkState
{
	Set<String> phones = new HashSet<String>();         //��� �������� �������
	Set<String> conflictPhones = new HashSet<String>(); //����������� ��������
	boolean infoCardConflict = false;                   //�������� �� ���. �����
}
