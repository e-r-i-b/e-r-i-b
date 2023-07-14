package com.rssl.common.forms.validators.passwords;

import com.rssl.common.forms.validators.passwords.generated.Charset;
import com.rssl.common.forms.validators.passwords.generated.Parameter;
import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.PropertyReader;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author Roshka
 * @ created 31.01.2007
 * @ $Author$
 * @ $Revision$
 */

public abstract class PasswordValidationConfig extends Config
{
	protected PasswordValidationConfig(PropertyReader reader)
	{
		super(reader);
	}

	/**
	 * ��� ���������
	 */
	public abstract Collection<ValidationStrategy> getStrategies();

	/**
	 * ����� ��������� �� �����.
	 * @param key ����
	 */
	public abstract ValidationStrategy findStrategy(String key);

	/**
	 * �������� ��� ���������
	 * @return
	 */
	public abstract Map<String, List<Parameter>> getParameters();

	/**
	 * �������� ��������� ��� ������������ ���������
	 * @param key
	 * @return
	 */
	public abstract List<Parameter> getParameters(String key);

	/**
	 * ����������� ����� ������
	 * @return
	 */
	public abstract int getMinimalLength(String key);

	/**
	 * ������������� ����� ������
	 * @return
	 */
	public abstract int getActualPasswordLength(String key);

	/**
	 * ���������� ���������� ������� ��� ������
	 * @return
	 */
	public abstract Map<String, List<Charset>> getAllowedCharsets();

	/**
	 * ���������� ���������� ������� ��� ������
	 * @param key
	 * @return
	 */
	public abstract List<Charset> getAllowedCharsets(String key);

	/**
	 * ���������� ���������� ������� ��� ������ (�� ����� settingDescriptor, � �� ���������)
	 * @param key
	 * @return
	 */
	public abstract String getPreferredAllowedCharset(String key);

	public abstract int getLoginAttempts();

	public abstract int getBlockedTimeout();

	/**
	 * @return ����������� ����� ������.
	 */
	public abstract int getMinimunLoginLength();
}
