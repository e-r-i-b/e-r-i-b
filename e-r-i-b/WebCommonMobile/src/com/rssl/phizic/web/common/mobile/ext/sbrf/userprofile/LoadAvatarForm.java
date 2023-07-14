package com.rssl.phizic.web.common.mobile.ext.sbrf.userprofile;

import com.rssl.phizic.web.common.EditFormBase;
import org.apache.struts.upload.FormFile;

/**
 * @author EgorovaA
 * @ created 26.06.14
 * @ $Author$
 * @ $Revision$
 */
public class LoadAvatarForm extends EditFormBase
{
	private FormFile attach;
	private int x;
	private int y;
	private int width;
	private int height;
	private String filePath;

	public FormFile getAttach()
	{
		return attach;
	}

	public void setAttach(FormFile attach)
	{
		this.attach = attach;
	}

	public int getHeight()
	{
		return height;
	}

	public void setHeight(int height)
	{
		this.height = height;
	}

	public int getWidth()
	{
		return width;
	}

	public void setWidth(int width)
	{
		this.width = width;
	}

	public int getX()
	{
		return x;
	}

	public void setX(int x)
	{
		this.x = x;
	}

	public int getY()
	{
		return y;
	}

	public void setY(int y)
	{
		this.y = y;
	}

	public String getFilePath()
	{
		return filePath;
	}

	public void setFilePath(String filePath)
	{
		this.filePath = filePath;
	}
}
