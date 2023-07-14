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
          function Provider(_name, _codes)
			{
				this.name = _name;
				this.codes= _codes;
			}

			var providers = new Array(4);
			providers["ALL"] = new Provider('���', new Array("495","903","905","906","910","915","916","917","926", "962", "478", "901", "501", "909"));
			providers["MTS"] = new Provider('���', new Array ("495","910","915","916","917"));
			providers["BEELINE"] = new Provider('������', new Array("495","903","905","906", "962", "909"));
			providers["MEGAFON"] = new Provider ('�������', new Array ("495","926"));
            providers["SKYLINK"] = new Provider ('��������', new Array ("478","901", "501"));
            providers["CORBINAM"] = new Provider ('�������-�������', new Array ("901"));

            function updateCodes(areaCode)
			{
				var areaCodesSelect = getElement('areaCode');
				var provider        = getProvider();
				var codes           = providers[provider].codes;

				clearOptions(areaCodesSelect);
				addOption(areaCodesSelect,"","���");
				for(var i=0; i &lt; codes.length; i++)
					addOption (areaCodesSelect,codes[i],codes[i]);
                setSelectBoxValue('areaCode',areaCode);
			}

			function getProvider()
			{
				var elems = getElement("operator").options;
				for(var i=0; i &lt; elems.length; i++)
					if ( elems[i].selected)
					return ((elems[i].value=="")? "ALL":elems[i].value);
			}
           function initTemplates() {
             setInputTemplate("fromDate",DATE_TEMPLATE);
             setInputTemplate("toDate",DATE_TEMPLATE);
             setInputTemplate("phoneNumber",PHONE_NUMBER_TEMPLATE);
           }
          function clearMasks(event){
             clearInputTemplate("fromDate",DATE_TEMPLATE);
             clearInputTemplate("toDate",DATE_TEMPLATE);
             clearInputTemplate("phoneNumber",PHONE_NUMBER_TEMPLATE);
           }
           function checkData() {
             if (!checkSumRange ("minAmount","maxAmount","����� �","����� ��")) return false;
             if (!checkPeriod ("fromDate", "toDate", "������ �", "������ ��")) return false;
             return true;
           }
           setSelectBoxValue ("state","<xsl:value-of select="/list-data/filter-data/state.code"/>");
           setSelectBoxValue ("currencyCode","<xsl:value-of select="/list-data/filter-data/currencyCode"/>");
           setSelectBoxValue ("payerAccount","<xsl:value-of select="/list-data/filter-data/payerAccount"/>");
           setSelectBoxValue ("operator","<xsl:value-of select="/list-data/filter-data/operator"/>");
           updateCodes('<xsl:value-of select="/list-data/filter-data/areaCode"/>');
           initTemplates();
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
		 <select name="operator" onchange="updateCodes();">
            <option value="">���</option>
			<option value="MTS">���</option>
		    <option value="BEELINE">������</option>
            <option value="MEGAFON">�������</option>
            <option value="SKYLINK">��������</option>
            <option value="CORBINAM">�������-�������</option>
          </select>
        <br/>
        <span class="filterLabel">����� ��������&nbsp;</span>
		    <select name="areaCode" onkeydown="onTabClick(event,'phoneNumber')">
			</select>
			<input type="text" name="phoneNumber" class="filterInput" value="{phoneNumber}" size="10" onkeydown="enterNumericTemplateFld(event,this,PHONE_NUMBER_TEMPLATE);onTabClick(event,'fromDate')"/>
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