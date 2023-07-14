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
           initTemplates();
           setSelectBoxValue ("state","<xsl:value-of select="/list-data/filter-data/state.code"/>");
           setSelectBoxValue ("currencyCode","<xsl:value-of select="/list-data/filter-data/currencyCode"/>");
           setSelectBoxValue ("payerAccount","<xsl:value-of select="/list-data/filter-data/payerAccount"/>");
           setSelectBoxValue ("operator","<xsl:value-of select="/list-data/filter-data/operator"/>");
        </script>
    </xsl:template>
	
	<!-- filter -->
	<xsl:template match="/list-data/filter-data">
	<span class="filterLabel">������</span>
			<select name="state" style="filterSelect">
  		  	   <option value="">���</option>
			   <option value="I">������</option>
			   <option value="W">��������������</option>
               <option value="S">��������</option>
               <option value="E">�������</option>
   		   </select>
		 <br/>
        <span class="filterLabel">��������</span>
		 <select name="operator" onkeydown="onTabClick(event,'fromDate')">
            <option value="">���</option>
			<option value="Stream">���-�����</option>
		    <option value="Corbina">�������-�������</option>
            <option value="Zebra">�����-�������</option>
            <option value="ROL">���</option>
          </select>
        <br/>
         <span class="filterLabel">������&nbsp;c</span>
           <input name="fromDate" value="{fromDate}" class="filterInput" type="text" size="10" onkeydown="enterNumericTemplateFld(event,this,DATE_TEMPLATE);onTabClick(event,'toDate')"/>
            &nbsp;��&nbsp;
		   <input name="toDate" value="{toDate}" class="filterInput" type="text" size="10" onkeydown="enterNumericTemplateFld(event,this,DATE_TEMPLATE)"/>
		 <br/>
		 <span class="filterLabel">�����&nbsp;c</span>
		 <input name="minAmount" value="{minAmount}" class="filterInput" type="text" size="10"/>
         &nbsp;��&nbsp;
	     <input name="maxAmount" value="{maxAmount}" class="filterInput" type="text" size="10"/>
         &nbsp;
	    <select name="currencyCode">
			<option value="">���</option>
            <option value="RUB">RUB</option>
            <option value="USD">USD</option>
            <option value="EUR">EUR</option>
		</select>
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
	</xsl:template>
</xsl:stylesheet>