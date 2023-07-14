<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="html" version="1.0" indent="yes" encoding="windows-1251"/>
<xsl:param name="resourceRoot" select="'resourceRoot'"/>
<xsl:param name="createDate" select="'asd'"/>

<xsl:include href="e319-360-body.xsl"/>
<xsl:include href="extract-360.xsl"/>
<xsl:include href="extract-319.xsl"/>

<xsl:template match="message">
	<xsl:apply-templates select="billing_a"/>
</xsl:template>

<xsl:template match="billing_a">
<script LANGUAGE="JavaScript">
function createDate(str1)
{
	var day,month,year,res;
	day = str1.substring(8, 10);
	month = str1.substring(5, 7);
	year = str1.substring(0, 4);
	res = day + "." + month + "." + year;
	return res;
}
function periodToDays(str1)

{
     var date1;

     var date2;

     var diff;

     var timediff;

     

     date1 = new Date(str1.substring(0, 4),str1.substring(5, 7)-1,str1.substring(8, 10));

     date2 = new Date();

     diff = new Date();

     if(date2.getTime() - date1.getTime() >= 0)return 0;

     diff.setTime(Math.abs(date1.getTime() - date2.getTime()));    

     

     timediff = diff.getTime();     

     return Math.floor(timediff / (1000*60*60*24)); 

}

function monthToStringOnly(str1)
{
	var month;
	var date;
	date = "";
	month = str1.substring(5, 7)-1;
	switch(month)
	{
		    case 0:date = date + "января";break;
            case 1:date = date + "февраля";break;
            case 2:date = date + "марта";break;
            case 3:date = date + "апреля";break;
            case 4:date = date + "мая";break;
            case 5:date = date + "июня";break;
            case 6:date = date + "июля";break;
            case 7:date = date + "августа";break;
            case 8:date = date + "сентября";break;
            case 9:date = date + "октября";break;
            case 10:date = date + "ноября";break;
            case 11:date = date + "декабря";break;
			default:date = date + "unknown";break;
	}
	return date;
}


function monthToStringWithoutYear(str1)
{
	var date;
	var month;
	date = "";
	date = date + str1.substring(8, 10);
	date = date + " ";
	var month = monthToStringOnly(str1); 
	date = date + month + " ";
	return date;
}

function monthToString(str1)
{
	var date;
	date = monthToStringWithoutYear(str1);
	date = date + str1.substring(0, 4);
	return date; 
}

function getYoungDigitsYear(str1)
{
	return str1.substring(2,4); 
}

function getDateOnly(str1)
{
	return str1.substring(8,10); 
}
</script>
	<xsl:if test="formType='204'">
		<xsl:call-template name="f204"/>
	</xsl:if>
	<xsl:if test="formType='360'">
		<xsl:call-template name="f360"/>
	</xsl:if>
	<xsl:if test="formType='319'">
		<xsl:call-template name="f319"/>
	</xsl:if>
	<xsl:if test="formType='297'">		
		<xsl:call-template name="f297"/>
	</xsl:if>
</xsl:template>


</xsl:stylesheet><!-- Stylus Studio meta-information - (c) 2004-2005. Progress Software Corporation. All rights reserved.
<metaInformation>
<scenarios ><scenario default="yes" name="extract" userelativepaths="yes" externalpreview="no" url="extract.xml" htmlbaseurl="" outputurl="" processortype="xalan" useresolver="yes" profilemode="0" profiledepth="" profilelength="" urlprofilexml="" commandline="" additionalpath="" additionalclasspath="" postprocessortype="none" postprocesscommandline="" postprocessadditionalpath="" postprocessgeneratedext="" validateoutput="no" validator="internal" customvalidator="" ><parameterValue name="createDate" value="'2006&#x2D;11&#x2D;25'"/></scenario></scenarios><MapperMetaTag><MapperInfo srcSchemaPathIsRelative="yes" srcSchemaInterpretAsXML="no" destSchemaPath="" destSchemaRoot="" destSchemaPathIsRelative="yes" destSchemaInterpretAsXML="no"/><MapperBlockPosition></MapperBlockPosition><TemplateContext></TemplateContext><MapperFilter side="source"></MapperFilter></MapperMetaTag>
</metaInformation>
-->