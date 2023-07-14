package com.rssl.phizic.web.common.client.finances.financeCalendar;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.parsers.DateParser;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.MonthPeriodFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.business.finances.financeCalendar.CalendarDataDescription;
import com.rssl.phizic.business.tree.TreeNode;
import com.rssl.phizic.web.common.client.finances.FinanceFormBase;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

/**
 * @author lepihina
 * @ created 26.03.14
 * $Author$
 * $Revision$
 * ����� ��������� ����������� ���������.
 */
public class ShowFinanceCalendarForm extends FinanceFormBase
{
	private static final String DATE_FORMAT = "dd/MM/yyyy";
	protected static final int BEFORE_MONTHS = -12;
	protected static final int AFTER_MONTHS = 12;

	private TreeNode node;
	private String selectedId;
	private Calendar openPageDate;
	private BigDecimal cardsBalance;
	private List<CalendarDataDescription> calendarData;
	private String extractId;

	/**
	 * @return ������ ����
	 */
	public TreeNode getNode()
	{
		return node;
	}

	/**
	 * @param node - ������ ����
	 */
	public void setNode(TreeNode node)
	{
		this.node = node;
	}

	/**
	 * @return - id �����/������ ����, �� ������� ���������� �������
	 */
	public String getSelectedId()
	{
		return selectedId;
	}

	/**
	 * @param selectedId - id �����/������ ����, �� ������� ���������� �������
	 */
	public void setSelectedId(String selectedId)
	{
		this.selectedId = selectedId;
	}

	/**
	 * @return ���� �������� ��������. ������������ ���� ���� ������������ �������� � ������� (������ CHG058580)
	 */
	public Calendar getOpenPageDate()
	{
		return openPageDate;
	}

	/**
	 * @param openPageDate - ���� �������� ��������. ������������ ���� ���� ������������ �������� � ������� (������ CHG058580)
	 */
	public void setOpenPageDate(Calendar openPageDate)
	{
		this.openPageDate = openPageDate;
	}

	/**
	 * @return ������� ������ �� ��������� ������
	 */
	public BigDecimal getCardsBalance()
	{
		return cardsBalance;
	}

	/**
	 * @param cardsBalance - ������� ������ �� ��������� ������
	 */
	public void setCardsBalance(BigDecimal cardsBalance)
	{
		this.cardsBalance = cardsBalance;
	}

	/**
	 * @return ������ ��� ����������� �� ���������
	 */
	public List<CalendarDataDescription> getCalendarData()
	{
		return calendarData;
	}

	/**
	 * @param calendarData - ������ ��� ����������� �� ���������
	 */
	public void setCalendarData(List<CalendarDataDescription> calendarData)
	{
		this.calendarData = calendarData;
	}

	protected static Form createFilterForm()
	{
		DateParser dataParser = new DateParser(DATE_FORMAT);

		MonthPeriodFieldValidator dateFieldValidator = new MonthPeriodFieldValidator(DATE_FORMAT);
		dateFieldValidator.setParameter(MonthPeriodFieldValidator.BEFORE_MONTH, BEFORE_MONTHS);
		dateFieldValidator.setParameter(MonthPeriodFieldValidator.AFTER_MONTH, AFTER_MONTHS);
		dateFieldValidator.setMessage("�� ������ ����������� �������� ������� ������ �� ��� ������ � �� ��� ����� �� �������� ������.");

		FormBuilder formBuilder = new FormBuilder();
		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("onDate");
		fieldBuilder.setDescription("�����");
		fieldBuilder.setType("date");
		fieldBuilder.setParser(dataParser);
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(new RequiredFieldValidator(), dateFieldValidator);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("customSelectId");
		fieldBuilder.setDescription("�����");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}

	public String getExtractId()
	{
		return extractId;
	}

	public void setExtractId(String extractId)
	{
		this.extractId = extractId;
	}
}
