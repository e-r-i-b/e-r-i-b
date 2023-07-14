package com.rssl.phizic.messaging.mail.messagemaking.email;

import java.io.BufferedWriter;
import java.io.Writer;
import java.io.IOException;
import java.io.File;

/**
 * @author Omeliyanchuk
 * @ created 23.07.2007
 * @ $Author$
 * @ $Revision$
 */

public class LimitFileWriter extends BufferedWriter
{
	//флаг,для определение нашего исключения.
	public final static String FILE_LIMIT_REACH = "FILE_LIMIT_REACH";

	private long maxFileSize;
	private boolean isLimited = false;
	private long currentLen=0;
	private long charLenght = 0;

	public void initialize(File file) throws IOException
	{   //смотрим сколько весит один символ при записи в файл.
		long len = file.length();
		super.write('a');
		super.flush();
		charLenght = file.length() - len;

	}

    public LimitFileWriter(Writer out, long maxFileSize)
    {
		super(out);
	    this.maxFileSize = maxFileSize;
    }

    public void write(char cbuf[], int off, int len) throws IOException
    {
		if(makeSizeCheck(len))
	        super.write(cbuf, off, len);
    }

	public void write(String s, int off, int len) throws IOException
	{
	    if(makeSizeCheck(len))
	        super.write(s, off, len);
	}

    public void write(int c) throws IOException
    {
	    if(makeSizeCheck(1))
	        super.write(c);
    }

	private boolean makeSizeCheck(int size) throws IOException
	{
		synchronized(lock)
	    {   //предполагаем, что макс размер на один символ 4 байта
		    isLimited = !(currentLen + size*charLenght< maxFileSize);
		    currentLen += (size*charLenght);
		    if(isLimited)
		        throw new IOException(FILE_LIMIT_REACH);
		    return !isLimited;
	    }
	}

	public boolean isLimited()
	{
		return isLimited;
	}
}
