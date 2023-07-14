package com.rssl.phizic.business.documents.payments;

/**
 * @author Gulov
 * @ created 05.03.15
 * @ $Author$
 * @ $Revision$
 */

import com.rssl.phizic.utils.StringHelper;

/**
 * Уведомление в документе
 */
public class DocumentNotice
{
	public static enum Type
	{
		SIMPLE("", "orangeLight"),
		ATTENTION("noticeAttention", "redLight"),
		BEFORE_TICK("noticeBeforeTick", "orangeLight"),  // принято предварительное решение по заявке на кредит
		CANCEL("noticeCancel", "redLight"),  // заявка на кредит отклонена банком
		CREDIT_DONE("noticeCreditDone", "infMesGreen"),  // кредит выдан
		TICK("noticeTick", "infMesGreen"),   // кредит одобрен
		VISIT("noticeVisit", "orangeLight");   // требуется визит в отделение банка для предоставления документов

		/**
		 * css класс
		 */
		private final String className;

		/**
		 * цвет рамки
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
	 * класс уведомления
	 */
	private final Type type;

	/**
	 * заголовок
	 */
	private final String title;

	/**
	 * текст
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
