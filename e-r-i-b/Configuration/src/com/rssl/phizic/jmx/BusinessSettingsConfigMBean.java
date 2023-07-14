package com.rssl.phizic.jmx;

/**
 * ������ ��� ������-��������, ��� ������� ��������� ����������� ��������� � �������������� jmx
 @author Pankin
 @ created 15.04.2011
 @ $Author$
 @ $Revision$
 */
public interface BusinessSettingsConfigMBean
{
	/**
	 * ������ �������� ���� �������������� CAP ������.
	 * @return
	 */
	public String getCAPCompatibleCardNumbers();

	/**
	 * �������� ����������� ������ ����� �� 
	 * @return ������ ���� "xx:yy,xx1:yy1"
	 */
	public String getSpecificTBReplacements();

	/**
	 * �������� �� ������������ ������ �������� ��� ���������� �����, �������� � ��
	 * @return true - ��������
	 */
	public boolean isChangeAccountOwnerEnabled();
}
