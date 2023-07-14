package com.rssl.phizic.business.ermb.migration.list.config;

import com.rssl.phizic.common.types.annotation.PlainOldJavaObject;

import javax.xml.bind.annotation.*;

/**
 * @author Gulov
 * @ created 27.01.14
 * @ $Author$
 * @ $Revision$
 */
@XmlRootElement(name="config")
@XmlType(name = "Config")
@XmlAccessorType(XmlAccessType.NONE)
@PlainOldJavaObject
public class ErmbInstructionMigrationConfigBean
{
	@XmlElement(name = "SEGMENT_1", required = true)
	private String instruction1;

	@XmlElement(name = "SEGMENT_1_1", required = true)
	private String instruction1_1;

	@XmlElement(name = "SEGMENT_1_2", required = true)
	private String instruction1_2;

	@XmlElement(name = "SEGMENT_2_1", required = true)
	private String instruction2_1;

	@XmlElement(name = "SEGMENT_2_2", required = true)
	private String instruction2_2;

	@XmlElement(name = "SEGMENT_2_2_1", required = true)
	private String instruction2_2_1;

	@XmlElement(name = "SEGMENT_3_1", required = true)
	private String instruction3_1;

	@XmlElement(name = "SEGMENT_3_2_1", required = true)
	private String instruction3_2_1;

	@XmlElement(name = "SEGMENT_3_2_2", required = true)
	private String instruction3_2_2;

	@XmlElement(name = "SEGMENT_3_2_3", required = true)
	private String instruction3_2_3;

	@XmlElement(name = "SEGMENT_4", required = true)
	private String instruction4;

	@XmlElement(name = "SEGMENT_5_1", required = true)
	private String instruction5_1;

	@XmlElement(name = "SEGMENT_5_2", required = true)
	private String instruction5_2;

	@XmlElement(name = "SEGMENT_5_3", required = true)
	private String instruction5_3;

	@XmlElement(name = "SEGMENT_5_4", required = true)
	private String instruction5_4;

	@XmlElement(name = "SEGMENT_5_5", required = true)
	private String instruction5_5;

	public String getInstruction1()
	{
		return instruction1;
	}

	public void setInstruction1(String instruction1)
	{
		this.instruction1 = instruction1;
	}

	public String getInstruction1_1()
	{
		return instruction1_1;
	}

	public void setInstruction1_1(String instruction1_1)
	{
		this.instruction1_1 = instruction1_1;
	}

	public String getInstruction1_2()
	{
		return instruction1_2;
	}

	public void setInstruction1_2(String instruction1_2)
	{
		this.instruction1_2 = instruction1_2;
	}

	public String getInstruction2_1()
	{
		return instruction2_1;
	}

	public void setInstruction2_1(String instruction2_1)
	{
		this.instruction2_1 = instruction2_1;
	}

	public String getInstruction2_2()
	{
		return instruction2_2;
	}

	public void setInstruction2_2(String instruction2_2)
	{
		this.instruction2_2 = instruction2_2;
	}

	public String getInstruction2_2_1()
	{
		return instruction2_2_1;
	}

	public void setInstruction2_2_1(String instruction2_2_1)
	{
		this.instruction2_2_1 = instruction2_2_1;
	}

	public String getInstruction3_1()
	{
		return instruction3_1;
	}

	public void setInstruction3_1(String instruction3_1)
	{
		this.instruction3_1 = instruction3_1;
	}

	public String getInstruction3_2_1()
	{
		return instruction3_2_1;
	}

	public void setInstruction3_2_1(String instruction3_2_1)
	{
		this.instruction3_2_1 = instruction3_2_1;
	}

	public String getInstruction3_2_2()
	{
		return instruction3_2_2;
	}

	public void setInstruction3_2_2(String instruction3_2_2)
	{
		this.instruction3_2_2 = instruction3_2_2;
	}

	public String getInstruction3_2_3()
	{
		return instruction3_2_3;
	}

	public void setInstruction3_2_3(String instruction3_2_3)
	{
		this.instruction3_2_3 = instruction3_2_3;
	}

	public String getInstruction4()
	{
		return instruction4;
	}

	public void setInstruction4(String instruction4)
	{
		this.instruction4 = instruction4;
	}

	public String getInstruction5_1()
	{
		return instruction5_1;
	}

	public void setInstruction5_1(String instruction5_1)
	{
		this.instruction5_1 = instruction5_1;
	}

	public String getInstruction5_2()
	{
		return instruction5_2;
	}

	public void setInstruction5_2(String instruction5_2)
	{
		this.instruction5_2 = instruction5_2;
	}

	public String getInstruction5_3()
	{
		return instruction5_3;
	}

	public void setInstruction5_3(String instruction5_3)
	{
		this.instruction5_3 = instruction5_3;
	}

	public String getInstruction5_4()
	{
		return instruction5_4;
	}

	public void setInstruction5_4(String instruction5_4)
	{
		this.instruction5_4 = instruction5_4;
	}

	public String getInstruction5_5()
	{
		return instruction5_5;
	}

	public void setInstruction5_5(String instruction5_5)
	{
		this.instruction5_5 = instruction5_5;
	}
}
