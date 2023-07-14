package com.rssl.phizic.common.types;

import com.rssl.phizic.common.types.exceptions.MalformedVersionFormatException;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Erkin
 * @ created 15.09.2011
 * @ $Author$
 * @ $Revision$
 */

/**
 * ����� ������
 * ������� �� 2� ���������: ������� ����� . ������� �����
 * ��������: 1.03
 */
public class VersionNumber implements Serializable, Comparable<VersionNumber>
{
	private static final Pattern PATTERN = Pattern.compile("(\\d+)\\.(\\d+)");

	private final int major;

	private final int minor;

	///////////////////////////////////////////////////////////////////////////

	/**
	 * ctor
	 * @param major
	 * @param minor
	 */
	public VersionNumber(int major, int minor)
	{
		this.major = major;
		this.minor = minor;
	}

    public int getMajor()
    {
        return major;
    }

    /**
	 * ��������������� ����� ������ �� ���������� �������������
	 * @param versionNumberAsString - ������ � ������� ������
	 * @return ������ "����� ������" (never null)
	 * @throws MalformedVersionFormatException - ������ � ������� ������ ����� ������������ ������
	 */
	public static VersionNumber fromString(String versionNumberAsString) throws MalformedVersionFormatException
	{
		if (versionNumberAsString == null)
			throw new NullPointerException("�������� 'versionNumberAsString' �� ����� ���� null");

		Matcher matcher = PATTERN.matcher(versionNumberAsString);
		if (!matcher.matches())
			throw new MalformedVersionFormatException("������������ ������ ������: " + versionNumberAsString);

		int major = Integer.parseInt(matcher.group(1));
		int minor = Integer.parseInt(matcher.group(2));
		return new VersionNumber(major, minor);
	}

	/**
	 * ��������������� ����� ������ �� �������� �������������. ��������������, ��� �������� ����� �������� 2 ��������� �������
	 * @param solidVersion - ����� �� ������� ������� ������
	 * @return ������ "������� ����� ������" (never null)
	 * @throws MalformedVersionFormatException - ������� ������ ����� ������������ ������
	 */
	public static VersionNumber fromSolid(Integer solidVersion) throws MalformedVersionFormatException
	{
		if (solidVersion == null)
			throw new NullPointerException("�������� 'solidVersion' �� ����� ���� null");

		int major = solidVersion / 100;
		int minor = solidVersion % 100;

		if (major <= 0 || minor < 0)
			throw new MalformedVersionFormatException("������������ ������ ������: " + solidVersion);

		return new VersionNumber(major, minor);
	}

	public int hashCode()
	{
		int result = major;
		result = 31 * result + minor;
		return result;
	}

	public boolean equals(Object o)
	{
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		VersionNumber other = (VersionNumber) o;

		return (this.major == other.major)
				&& (this.minor == other.minor);
	}

	public int compareTo(VersionNumber o)
	{
		int rc = major - o.major;
		if (rc == 0)
			rc = minor - o.minor;
		return rc;
	}

	/**
	 * @param o
	 * @return true, ���� ������ <this> ���� ���������
	 */
	public boolean gt(VersionNumber o)
	{
		return compareTo(o) > 0;
	}

	/**
	 * @param o
	 * @return true, ���� ������ <this> ���� ��� ����� ���������
	 */
	public boolean ge(VersionNumber o)
	{
		return compareTo(o) >= 0;
	}

	/**
	 * @param o
	 * @return true, ���� ������ <this> ���� ���������
	 */
	public boolean lt(VersionNumber o)
	{
		return compareTo(o) < 0;
	}

	/**
	 * @param o
	 * @return true, ���� ������ <this> ���� ��� ����� ���������
	 */
	public boolean le(VersionNumber o)
	{
		return compareTo(o) <= 0;
	}

	public String toString()
	{
		return String.format("%d.%02d", major, minor);
	}

	/**
	 * @return ������ ������ ��� ����������� (500, 510). ��� �������� ����� ���������� 2 ��������� �������
	 */
	public Integer getSolid()
	{
		return major * 100 + minor;
	}
}
