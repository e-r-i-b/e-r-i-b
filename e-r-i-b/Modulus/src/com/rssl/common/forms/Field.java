package com.rssl.common.forms;

import com.rssl.common.forms.expressions.Expression;
import com.rssl.common.forms.parsers.FieldValueParser;
import com.rssl.common.forms.types.BusinessCategory;
import com.rssl.common.forms.types.SubType;
import com.rssl.common.forms.types.Type;
import com.rssl.common.forms.validators.FieldValidator;
import com.rssl.phizic.common.types.VersionNumber;
import com.rssl.phizic.common.types.BusinessFieldSubType;

import java.io.Serializable;
import java.util.List;

/**
 * @author Evgrafov
 * @ created 26.05.2006
 * @ $Author: niculichev $
 * @ $Revision: 52888 $
 */

public interface Field extends Serializable
{
	/**
	 * ��� ���� - �������� ��� ���� HTML;
	  * @return ���
	 */
	String getName();

	/**
	 * ���������� ��� ����
	 * @param name ��� ����
	 */
	void setName(String name);

	/**
	 * @return �������� ����
	 */
	String getDescription();

	/**
	 * ���������� �������� ����
	 * @param description �������� ����
	 */
	void setDescription(String description);

	/**
	 * ��� ���� (string|date|money|BIC|account|etc)
	 * @return ���
	 */
	Type getType();

	/**
	 * ���������� ��� ����
 	 * @param type ��� ����
	 */
	void setType(Type type);

	/**
	 * @return ���. ��� ����
	 */
	SubType getSubType();

	/**
	 * ���������� �������� ���. ���� ����
	 * @param subType ���. ���
	 */
	void setSubType(SubType subType);

	/**
	 * ���������� ��������������� � �����
	 *
	 * @return ������ �����������
	 */
	List<FieldValidator> getValidators();

	/**
	 * @return ������ ����
	 */
	FieldValueParser getParser();

	/**
	 * ������ ���� - ����������� ��������� �������������
	 * �������� �� ���������� �������������
	 * @param parser ������
	 */
	void setParser(FieldValueParser parser);

	/**
	 * XPath ��������� ��� ��������� ������� ���� �� Payment.xml
	 * @return �������� ����
	 */
	String getSource();

	/**
	 * ���������� XPath ��������� ��� ��������� ������� ����
	 * @param source XPath ��������� ��� ��������� ������� ����
	 */
	void setSource(String source);

	/**
	 * @return �������� �� �������
	 */
	boolean isSignable();

	/**
	 * @return ��������� "������������" ����
	 */
	Expression getEnabledExpression();

	/**
	 * @return ���������� ���������
	 */
	Expression getValueExpression();

	/**
	 * @return ��������� ��� ���������� ���������� �������� ����.
	 */
	Expression getInitalValueExpression();

	/**
	 * @return ���������� ���� ��� ���
	 */
	boolean isChanged();

	/**
	 *  ���������� ������� ����������� ����
	 * @param isChanged ������� ����������� ����
	 */
	void setChanged(boolean isChanged);

	/**
	 * @return � ����� ������ mobileApi �������������� ������ ����
	 */
	@Deprecated
	VersionNumber getFromApi();

	@Deprecated
	void setFromApi(VersionNumber fromApi);


	/**
	 * @return �� ����� ������ mobileApi �������������� ������ ����
	 */
	@Deprecated
	VersionNumber getToApi();

	@Deprecated
	void setToApi(VersionNumber toApi);

	/**
	 * @return �������� �� ���� ��������
	 * @deprecated ��� ����������� ������� ��������� ������������
	 */
	@Deprecated
	boolean isKey();

	/**
	 * @return �����, ��������������� �� ����
	 */
	String getMask();

	/**
	 * @return ������ ��������� ����
	 */
	BusinessCategory getBusinessCategory();

}
