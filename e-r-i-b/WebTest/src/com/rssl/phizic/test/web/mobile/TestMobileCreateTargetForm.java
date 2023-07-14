package com.rssl.phizic.test.web.mobile;

import com.rssl.phizic.utils.StringHelper;
import org.apache.struts.upload.FormFile;

/**
 * @author koptyaev
 * @ created 20.05.14
 * @ $Author$
 * @ $Revision$
 */
@SuppressWarnings("JavaDoc")
public class TestMobileCreateTargetForm extends TestMobileForm
{
	private String type;                  // тип цели
	private String name;                  // название цели
	private String comment;               // комментарий к названию цели
	private String amount;                // стоимость цели
	private String date;                  // планируемая дата покупки
	private String fileName;              // название файла
	private FormFile imageFile;           // картинка

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getComment()
	{
		return comment;
	}

	public void setComment(String comment)
	{
		this.comment = comment;
	}

	public String getAmount()
	{
		return amount;
	}

	public void setAmount(String amount)
	{
		this.amount = amount;
	}

	public String getDate()
	{
		return date;
	}

	public void setDate(String date)
	{
		this.date = date;
	}

	public String getFileName()
	{
		return fileName;
	}

	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}

	public FormFile getFile()
	{
		return imageFile;
	}

	public void setFile(FormFile imageFile)
	{
		this.imageFile = imageFile;
		if (StringHelper.isEmpty(fileName))
			setFileName(imageFile.getFileName());
	}
}
