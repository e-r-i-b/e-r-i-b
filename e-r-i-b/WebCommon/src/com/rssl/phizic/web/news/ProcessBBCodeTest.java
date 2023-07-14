package com.rssl.phizic.web.news;

import junit.framework.TestCase;

@SuppressWarnings({"JavaDoc"})
public class ProcessBBCodeTest extends TestCase
{
	public void test ()
	{
		String sB = ProcessBBCode.preparePostText("[b]Bold[/b]");
		String sI = ProcessBBCode.preparePostText("[i]Italic[/i]");
		String sU = ProcessBBCode.preparePostText("[u]Underline[/u]");
		String sL = ProcessBBCode.preparePostText("[list]\n" +
				"[*]Red\n" +
				"[*]Blue\n" +
				"[*]Yellow\n" +
				"[/list]");
		String sA = ProcessBBCode.preparePostText("[url]URL[/url]");
		String sC = ProcessBBCode.preparePostText("[color=red]Color[/color]");
		String sS_ = ProcessBBCode.preparePostText("<script>Script</script>");
		String sBR_ = ProcessBBCode.preparePostText("<br>");
		System.out.println(sB);
		System.out.println(sI);
		System.out.println(sU);
		System.out.println(sL);
		System.out.println(sA);
		System.out.println(sC);
		System.out.println(sS_);
		System.out.println(sBR_);
	}
}
