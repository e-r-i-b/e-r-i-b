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
           setSelectBoxValue ("payerAccount","<xsl:value-of select="/list-data/filter-data/payerAccount"/>");
        </script>
    </xsl:template>
	
	<!-- filter -->
	<xsl:template match="/list-data/filter-data">
		<span class="filterLabel">������</span>
		<select name="state" onkeydown="onTabClick(event,'fromDate')">
			<xsl:when test="$code='SAVED'">������</xsl:when>
			<xsl:when test="$code='DISPATCHED'">������</xsl:when>
			<xsl:when test="$code='ACCEPTED'">�������</xsl:when>
			<xsl:when test="$code='EXECUTED'">��������</xsl:when>
			<xsl:when test="$code='REFUSED'">�������</xsl:when>
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
              <span class="filterLabel">���� ����������</span>
			  <input name="receiverAccount" value="{receiverAccount}" type="text" size="27" maxlength="20" class="filterInput"/>
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