package com.rssl.phizic.business.pereodicalTask;

/**
 * User: Moshenko
 * Date: 01.11.2011
 * Time: 14:22:33
 * ������ ��������
 */
public class PereodicalTaskError
{
	/**
	 * id ������
	 */
	private Long id;
	/**
	 * id unloadTaskResult
	 */
	private PereodicalTaskResult result;
	/**
	 * ��������� ������
	 */
	private String errText;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public PereodicalTaskResult getResult()
	{
		return result;
	}

	public void setResult(PereodicalTaskResult result)
	{
		this.result = result;
	}

	public String getErrText()
	{
		return errText;
	}

	public void setErrText(String errText)
	{
		this.errText = errText;
	}
}
