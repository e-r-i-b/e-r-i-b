<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [
	<!ENTITY nbsp "&#160;">
]>
<?altova_samplexml list-data.xml?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="html" version="1.0" encoding="windows-1251" indent="yes"/>
	<xsl:param name="webRoot" select="'/PhizIC'"/>

	<xsl:template match="/">
		<xsl:apply-templates select="/list-data/filter-data"/>
        <script language="javascript">
            function initTemplates() {
             setInputTemplate("fromDate",DATE_TEMPLATE);
             setInputTemplate("toDate",DATE_TEMPLATE);
           }
          function clearMasks(event){
             clearInputTemplate("fromDate",DATE_TEMPLATE);
             clearInputTemplate("toDate",DATE_TEMPLATE);
           }
           function checkData() {
            if (!checkSumRange ("minAmount","maxAmount","����� �","����� ��")) return false;
             if (!checkPeriod ("fromDate", "toDate", "������ �", "������ ��")) return false;
             return true;
           }
           setSelectBoxValue ("state","<xsl:value-of select="/list-data/filter-data/state.code"/>");
           setSelectBoxValue ("month","<xsl:value-of select="/list-data/filter-data/month"/>");
           setSelectBoxValue ("year","<xsl:value-of select="/list-data/filter-data/year"/>");
           setSelectBoxValue ("payerAccount","<xsl:value-of select="/list-data/filter-data/payerAccount"/>");
           initTemplates();
        </script>
    </xsl:template>
	
	<!-- filter -->
	<xsl:template match="/list-data/filter-data">
	<td>
      <span class="filterLabel">������</span>
			<select name="state" style="filterSelect">
			<option value="">���</option>
			<option value="INITIAL,SAVED">������</option>
			<option value="DISPATCHED">��������������</option>
			<option value="DELAYED_DISPATCH">�������� ���������</option>
            <option value="EXECUTED">��������</option>
            <option value="REFUSED">�������</option>
            <option value="RECALLED">�������</option>
   		   </select>
		 <br/>
         <span class="filterLabel">�����</span>
		 <input name="number" value="{number}" class="filterInput" type="text" size="10"/>
         <br/>
        <span class="filterLabel">���</span>
		<input name="fio" value="{fio}" class="filterInput" type="text" size="27"/>
        <br/>
        <span class="filterLabel">�����</span>
         <select name="month">
          <option value=""></option>
          <option value="1">������</option>
          <option value="2">�������</option>
          <option value="3">����</option>
          <option value="4">������</option>
          <option value="5">���</option>
          <option value="6">����</option>
          <option value="7">����</option>
          <option value="8">������</option>
          <option value="9">��������</option>
          <option value="10">�������</option>
          <option value="11">������</option>
          <option value="12">�������</option>
         </select>
	 	 &nbsp;���&nbsp;
		 <select name="year" onkeydown="onTabClick(event,'fromDate')">
           <option value=""></option>
          <option value="2005">2005</option>
          <option value="2006">2006</option>
          <option value="2007">2007</option>
          <option value="2008">2008</option>
          <option value="2009">2009</option>
          <option value="2010">2010</option>
         </select>
         <br/>
         <span class="filterLabel">������&nbsp;c</span>
           <input name="fromDate" value="{fromDate}" class="filterInput" type="text" size="10" onkeydown="enterNumericTemplateFld(event,this,DATE_TEMPLATE);onTabClick(event,'toDate')"/>
            &nbsp;��&nbsp;
		   <input name="toDate" value="{toDate}" class="filterInput" type="text" size="10" onkeydown="enterNumericTemplateFld(event,this,DATE_TEMPLATE)"/>
		 <br/>
		 <span class="filterLabel">�����&nbsp;(RUB)&nbsp;c</span>
		 <input name="minAmount" value="{minAmount}" class="filterInput" type="text" size="10"/>
         &nbsp;��&nbsp;
	     <input name="maxAmount" value="{maxAmount}" class="filterInput" type="text" size="10"/>
        <br/>
		<span class="filterLabel">������</span>
			<input name="consumption" value="{consumption}" class="filterInput" type="text" size="10"/>
		<br/>
        <span class="filterLabel">���� ��������</span>
 		  <select id="payerAccount" name="payerAccount">
			<option value="">���</option>
			<xsl:for-each select="document('accounts.xml')/entity-list/*">
			<option value="{./@key}">
                 <xsl:value-of select="./@key"/>&nbsp;
				 [<xsl:value-of select="./field[@name='type']"/>]
			</option>
		   </xsl:for-each>
		 </select>
       <br/>
         <span class="filterLabel">�������� �������</span>
		<input name="isShowCounter" style="border:none" type="checkbox" value="true">
			<xsl:if test="isShowCounter='true'">
				<xsl:attribute name="checked"/>
			</xsl:if>
		</input>
		&nbsp;�������� �����&nbsp;
		<input name="isShowTarif" style="border:none" type="checkbox" value="true">
			<xsl:if test="isShowTarif='true'">
				<xsl:attribute name="checked"/>
			</xsl:if>
		</input>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    </td>
    </xsl:template>
</xsl:stylesheet>