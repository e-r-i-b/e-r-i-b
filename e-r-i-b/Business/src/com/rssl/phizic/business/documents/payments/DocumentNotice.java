package com.rssl.phizic.business.documents.payments;

/**
 * @author Gulov
 * @ created 05.03.15
 * @ $Author$
 * @ $Revision$
 */

import com.rssl.phizic.utils.StringHelper;

/**
 * ����������� � ���������
 */
public class DocumentNotice
{
	public static enum Type
	{
		SIMPLE("", "orangeLight"),
		ATTENTION("noticeAttention", "redLight"),
		BEFORE_TICK("noticeBeforeTick", "orangeLight"),  // ������� ��������������� ������� �� ������ �� ������
		CANCEL("noticeCancel", "redLight"),  // ������ �� ������ ��������� ������
		CREDIT_DONE("noticeCreditDone", "infMesGreen"),  // ������ �����
		TICK("noticeTick", "infMesGreen"),   // ������ �������
		VISIT("noticeVisit", "orangeLight");   // ��������� ����� � ��������� ����� ��� �������������� ����������

		/**
		 * css �����
		 */
		private final String className;

		/**
		 * ���� �����
		 */
		private final String color;

		private Type(String className, String color)
		{
			this.className = className;
			this.color = color;
		}

		public String getClassName()
		{
			return className;
		}

		public String getColor()
		{
			return color;
		}
	}

	/**
	 * ����� �����������
	 */
	private final Type type;

	/**
	 * ���������
	 */
	private final String title;

	/**
	 * �����
	 */
	private final String text;

	public DocumentNotice(String type, String title, String text)
	{
		this.type = StringHelper.isEmpty(type) ? Type.SIMPLE : Type.valueOf(type);
		this.title = title;
		this.text = text;
	}

	public Type getType()
	{
		return type;
	}

	public String getTitle()
	{
		return title;
	}

	public String getText()
	{
		return text;
	}
}
