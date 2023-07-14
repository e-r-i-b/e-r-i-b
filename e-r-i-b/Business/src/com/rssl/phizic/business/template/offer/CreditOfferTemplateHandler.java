package com.rssl.phizic.business.template.offer;

import com.rssl.phizic.utils.DateHelper;
import org.apache.commons.collections.Closure;

import java.util.Calendar;

/**
 * @author Balovtsev
 * @since  07.06.2015.
 */

/**
 * ���������� ������
 *
 * � ����� ������ ������� ���� �� ����� ������ ������������ �������.
 * ����������� ������ - ��������� �� ��������� �� �������.
 * ��� �� ���� - � �����.
 * ��� ����� - ������� ������ ������ �������� (������)
 * ����� ���������� ��������
 */
public class CreditOfferTemplateHandler implements Closure
{
	private CreditOfferTemplate example;
	private final Calendar currMinute = DateHelper.clearSeconds(Calendar.getInstance());

	public void execute(Object value)
	{
		CreditOfferTemplate template = (CreditOfferTemplate) value;

		/*
		 * ������� ��������������� ������ ��� ������� �����
		 */
		if (template.getType() != CreditOfferTemplateType.ERIB)
		{
			return;
		}

		boolean expired = template.getTo() != null && !template.getTo().after(currMinute);
		boolean inFuture = template.getFrom().after(currMinute);

		if (expired)
		{
			template.setStatus(Status.ARCHIVE);
		}
		else if (inFuture)
		{
			template.setStatus(Status.INTRODUCED);
		}
		else if (example == null)
		{
			example = template;
			example.setStatus(Status.OPERATE);
		}
		else if (template.getFrom().before(example.getFrom()))
		{
			template.setStatus(Status.ARCHIVE);
		}
		else if (template.getFrom().after(example.getFrom()))
		{
			example.setStatus(Status.ARCHIVE);
			template.setStatus(Status.OPERATE);

			example = template;
		}
		else if (example.getId().equals(template.getId()))
		{
			return;
		}
		else
		{
			throw new IllegalStateException();
		}
	}
}
