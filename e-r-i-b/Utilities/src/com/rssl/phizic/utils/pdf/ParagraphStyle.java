package com.rssl.phizic.utils.pdf;

/**
 * ����� ������
 * @author lepihina
 * @ created 16.04.2012
 * @ $Author$
 * @ $Revision$
 */
public class ParagraphStyle
{
	private float leading; // ����������� ��������
	private float spacingBefore; // ������ ����� �������
	private float spacingAfter; // ������ ����� ������
	private float firstLineIndent; // ������� ������
	private Alignment alignment; // ������������ � ������

	/**
	 * @param leading - ����������� ��������
	 * @param spacingBefore - ������ ����� �������
	 * @param spacingAfter - ������ ����� ������
	 * @param firstLineIndent - ������� ������
	 * @param alignment - ������������ � ������
	 */
	public ParagraphStyle(float leading, float spacingBefore, float spacingAfter, float firstLineIndent, Alignment alignment)
	{
		this.leading = leading;
		this.spacingBefore = spacingBefore;
		this.spacingAfter = spacingAfter;
		this.firstLineIndent = firstLineIndent;
		this.alignment = alignment;
	}

	/**
	 * @param leading - ����������� ��������
	 * @param spacingBefore - ������ ����� �������
	 * @param spacingAfter - ������ ����� ������
	 * @param firstLineIndent - ������� ������
	 */
	public ParagraphStyle(float leading, float spacingBefore, float spacingAfter, float firstLineIndent)
	{
		this.leading = leading;
		this.spacingBefore = spacingBefore;
		this.spacingAfter = spacingAfter;
		this.firstLineIndent = firstLineIndent;
		this.alignment = Alignment.left;
	}

	public float getLeading()
	{
		return leading;
	}

	public void setLeading(float leading)
	{
		this.leading = leading;
	}

	public float getSpacingBefore()
	{
		return spacingBefore;
	}

	public void setSpacingBefore(float spacingBefore)
	{
		this.spacingBefore = spacingBefore;
	}

	public float getSpacingAfter()
	{
		return spacingAfter;
	}

	public void setSpacingAfter(float spacingAfter)
	{
		this.spacingAfter = spacingAfter;
	}

	public float getFirstLineIndent()
	{
		return firstLineIndent;
	}

	public void setFirstLineIndent(float firstLineIndent)
	{
		this.firstLineIndent = firstLineIndent;
	}

	public Alignment getAlignment()
	{
		return alignment;
	}

	public void setAlignment(Alignment alignment)
	{
		this.alignment = alignment;
	}
}
