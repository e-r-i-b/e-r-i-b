package com.rssl.phizic.web.webApi.protocol.jaxb.model.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.xml.bind.annotation.*;

/**
 * ����� ��������� ��������� �������� ���������������� ���������� � ������� ������.
 * <br/>
 * � ����� ������ ������ ����� ����� ��������� xml-�������������:
 *
 * <pre>
 * {@code
 * <status>
 *     <code>3</code>
 *     <errors>                               <!--�������������� �������-->
 *          <error>                           <!--������ ����������� 1 ��� ����� ���-->
 *              <elementId>string</elementId> <!--�������������� �������-->
 *              <text>string</text>
 *          </error>
 *     </errors>
 *     <warnings>                             <!--�������������� �������-->
 *          <warning>                         <!--������ ����������� 1 ��� ����� ���-->
 *              <elementId>string</elementId> <!--�������������� �������-->
 *              <text>string</text>
 *          </warning>
 *     </warnings>
 * </status>
 * }
 * </pre>
 *
 * @author Balovtsev
 * @since 22.04.14
 */
@XmlType(propOrder = {"code", "errors", "warnings"})
@XmlRootElement (name = "status")
public class Status
{
	private StatusCode    code;
	private List<Error>   errors;
	private List<Warning> warnings;

	/**
	 *
	 */
	public Status() {}

	/**
	 * @param code ��� ������
	 */
	public Status(StatusCode code)
	{
		this.code = code;
	}

	/**
	 * @param code ��� ������
	 */
	public Status(StatusCode code, String errorText)
	{
		this.code = code;
		Error error = new Error(errorText, null);
		List<Error> errorList = new ArrayList<Error>();
		errorList.add(error);
		this.setErrors(errorList);
	}

	/**
	 * @param code ��� ������
	 */
	public Status(StatusCode code, Map<String, String> errors)
	{
		this.code = code;
		List<Error> errorList = new ArrayList<Error>();
		for (Map.Entry<String, String> entry : errors.entrySet())
		{
			Error error = new Error(entry.getKey(), entry.getValue());
			errorList.add(error);
		}
		this.setErrors(errorList);
	}

	/**
	 * @param warningText ����� ��������������� ���������
	 */
	public Status(String warningText)
	{
		this.code = StatusCode.SUCCESS;
		Warning warning = new Warning(warningText, null);
		List<Warning> warningList = new ArrayList<Warning>();
		warningList.add(warning);
		this.setWarnings(warningList);
	}

	/**
	 * @return ��� �������. ��-��������� ���������� ������ SUCCESS
	 * ��� ������������� �������� 0.
	 */
	@XmlElement(name = "code", required = true)
	public StatusCode getCode()
	{
		return code == null ? StatusCode.SUCCESS : code;
	}

	/**
	 * @return ��������� ������
	 */
	@XmlElementWrapper(name = "errors", required = false)
	@XmlElement(name="error", required = true)
	public List<Error> getErrors()
	{
		return errors;
	}

	/**
	 * @return ��������� ���������
	 */
	@XmlElementWrapper(name = "warnings", required = false)
	@XmlElement(name="warning", required = true)
	public List<Warning> getWarnings()
	{
		return warnings;
	}

	public void setCode(StatusCode code)
	{
		this.code = code;
	}

	public void setErrors(List<Error> errors)
	{
		this.errors = errors;
	}

	public void setWarnings(List<Warning> warnings)
	{
		this.warnings = warnings;
	}
}
