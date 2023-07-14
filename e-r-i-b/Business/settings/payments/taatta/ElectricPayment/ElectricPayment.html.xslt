<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [<!ENTITY nbsp "&#160;">]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="html" version="1.0" indent="yes"/>

	<xsl:param name="mode" select="'view'"/>
	<xsl:param name="webRoot" select="'webRoot'"/>
	<xsl:param name="resourceRoot" select="'resourceRoot'"/>
	<xsl:param name="personAvailable" select="true()"/>

	<xsl:template match="/">
		<xsl:choose>
			<xsl:when test="$mode='edit'">
				<xsl:apply-templates mode="edit"/>
			</xsl:when>
			<xsl:when test="$mode='view'">
				<xsl:apply-templates mode="view"/>
			</xsl:when>
		</xsl:choose>
	</xsl:template>

	<xsl:template match="/form-data" mode="edit">
		<xsl:variable name="account" select="accountsSelect"/>
		<script type="text/javascript" language="JavaScript">
			document.webRootPrivate = '<xsl:value-of select="$webRoot"/>/private';

      function setConsumption()
      {
        var curr = parseInt(document.getElementById('counterCurr').value);
        var prev = parseInt(document.getElementById('counterPrev').value);
        var res;
        if ( (curr &gt; 0) &amp;&amp; ( prev &gt; 0) )
        {
           res = curr-prev;
           if ( res &gt; 0)
           {
                document.getElementById('consumption').value=res;
                setAmount();
           }
           else alert("������� ��������� �������� ������ �����������!");
        }
      }

      function setAmount()
      {
        if ((document.getElementById('tarif1').value == "") &amp;&amp; (document.getElementById('tarif2').value != ""))
            document.getElementById('tarif1').value = "0";
        var cons  = parseInt  (document.getElementById('consumption').value);
        var tarif = parseFloat(document.getElementById('tarif1').value);
        var kop =  document.getElementById('tarif2').value;
        if ( kop != "") tarif +=  parseFloat(kop)/100;

        if ( (cons &gt; 0) &amp;&amp; (tarif &gt; 0.0) )
        {
              document.getElementById('tarif' ).value = tarif;
  			  document.getElementById('amount').value = cons * tarif;
			  splitSum('amount', document.getElementById('amount').value);
        }

        if  ( (cons == "0") || (isNaN(cons)) || (tarif == "0.0") || (isNaN(tarif)) ) splitSum('amount', "0");

      }

      function mergeSum(field)
      {
        document.getElementById('field').value =
			parseFloat(document.getElementById(field+'1').value) +
					parseFloat(document.getElementById(field+'2').value) / 100;
      }

      function copyAmount()
      {
        if ((document.getElementById('tarif1').value == "") &amp;&amp; (document.getElementById('tarif2').value != ""))
            document.getElementById('tarif1').value = "0"; 
         document.getElementById('amount').value = document.getElementById('amount1').value + "." +  document.getElementById('amount2').value;
         document.getElementById('tarif').value = document.getElementById('tarif1').value + "." +    document.getElementById('tarif2').value;
       }

      function splitSum(fld,val)
      {
        if ( val == '') return;
        var	sum = parseFloat(val);
        if ( isNaN (sum)) return;
        var rub = Math.floor(sum);
        var kop = Math.round((sum-rub)*100);
        if ( kop == 100 ) {
            rub++;
            kop=0;
        }
				document.getElementById(fld+'1').value = rub;
				if ( kop &gt; 9 )
					document.getElementById(fld+'2').value = kop;
				else
					document.getElementById(fld+'2').value = '0'+kop;
      }

		function setReceiverInfo(receiverInfo)
			{
			    setElement("receiverName", receiverInfo["receiverName"]);
				setElement("receiverINN", receiverInfo["receiverINN"]);
				setElement("receiverAccount", receiverInfo["receiverAccount"]);
				setBankInfo(receiverInfo);
			}
		</script>
		<table cellspacing="4" cellpadding="0" border="0" class="paymentFon">
        <tr>
                <td align="right" valign="middle"><img src="{$resourceRoot}/images/ElectricPayment.gif" border="0"/>&nbsp;</td>
                <td colspan="2">
                    <table class="MaxSize">
                   
                    <tr>
                        <td align="center" style="border-bottom:1 solid silver"  class="paperTitle">
                            <table height="100%" width="520px" cellspacing="0" cellpadding="0">
                            <tr>
                                <td class="titleHelp">
                                    <span class="formTitle">������ ��������������</span>
                                    <br/><br/>
                                    ������� �������� ������� � ���� ������ ��������������.
                                </td>
                            </tr>
                            </table>
                        </td>
                        <td width="100%">&nbsp;</td>
                    </tr>
                    </table>
                </td>
            </tr>
            <input id="amount" name="amount" type="hidden" value="{amount}"/>
			<input id="tarif" name="tarif" type="hidden" value="{tarif}"/>
			<tr>
				<td align="right" valign="top">���� �����������</td>
				<td valign="top">
					<xsl:if test="$personAvailable">
						<select id="accountsSelect" name="accountsSelect">
							<xsl:for-each select="document('active-accounts.xml')/entity-list/*">
								<option>
									<xsl:attribute name="value">
										<xsl:value-of select="./@key"/>
									</xsl:attribute>
									<xsl:if test="$account = ./@key">
										<xsl:attribute name="selected">true</xsl:attribute>
									</xsl:if>
									<xsl:value-of select="./@key"/>
									[<xsl:value-of select="./field[@name='type']"/>]
									<xsl:value-of select="format-number(./field[@name='amountDecimal'], '0.00')"/>
									&nbsp;
									<xsl:value-of select="./field[@name='currencyCode']"/>
								</option>
							</xsl:for-each>
						</select>
					</xsl:if>
					<xsl:if test="not($personAvailable)">
						<select disabled="disabled">
							<option selected="selected">���� �������</option>
						</select>
					</xsl:if>
				</td>
				<td valign="top" rowspan="3">
					<table cellspacing="0" cellpadding="2"
					       style="border:1px solid #67747F;border-collapse:collapse;margin-left:10px;">
						<tr>
							<td style="border:1px solid #67747F">��� ��&nbsp;</td>
							<td style="border:1px solid #67747F">&nbsp;
								<select id="code1Select" name="code1Select">
									<option value="12">12 (������)</option>
									<option value="14">14 (����������)</option>
								</select>
							</td>
						</tr>
						<tr>
							<td style="border:1px solid #67747F">���</td>
							<td style="border:1px solid #67747F">&nbsp;
								<select id="code2Select" name="code2Select">
									<option value="-">-</option>
									<option value="0">0</option>
									<option value="1">1</option>
									<option value="2">2</option>
									<option value="3">3</option>
									<option value="4">4</option>
									<option value="5">5</option>
									<option value="6">6</option>
									<option value="7">7</option>
									<option value="8">8</option>
									<option value="9">9</option>
								</select>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td align="right">���������� �������<span class="asterisk">*</span></td>
				<td>
					<input id="receiverName" name="receiverName" type="text" value="{receiverName}" size="57"/>
					<xsl:if test="$personAvailable">
						<input type="button" class="buttWhite smButt" 
						       onclick="openSelectReceiverWindow();" value="..."/>
					</xsl:if>
				</td>
			</tr>
			<tr>
				<td align="right">���</td>
				<td>
					<input id="receiverINN" name="receiverINN" type="text" value="{receiverINN}"
					       maxlength="12" size="14"/>
					&nbsp;�/c:&nbsp;<span class="asterisk">*</span>&nbsp;
					<input id="receiverAccount" name="receiverAccount" type="text" value="{receiverAccount}" maxlength="20" size="24"/>
				</td>
			</tr>
			<tr>
				<td align="right">���� ����������<span class="asterisk">*</span></td>
				<td colspan="2">
					<input id="bankName" name="bankName" type="text" value="{bankName}" size="57"/>
					<input type="button" class="buttWhite smButt" onclick="javascript:openNationalBanksDictionary(setBankInfo,getFieldValue('bankName'),getFieldValue('receiverBIC'));" value="..."/>
				</td>
			</tr>
			<tr>
				<td align="right">���<span class="asterisk">*</span></td>
				<td colspan="2">
					<input id="receiverBIC" name="receiverBIC" type="text" value="{receiverBIC}" maxlength="9" size="14"/>&nbsp;�/�:&nbsp;<span class="asterisk">*</span>&nbsp;
				 	<input id="receiverCorAccount" name="receiverCorAccount" type="text" value="{receiverCorAccount}" maxlength="20" size="24"/>
				</td>
			</tr>
			<tr>
				<td align="right">�����&nbsp;��������<span class="asterisk">*</span></td>
				<td>
					<table cellspacing="0" cellpadding="0"
					       style="border:1px solid #67747F;border-collapse:collapse;padding-bottom:2;padding-top:2;">
						<tr>
							<td style="border:1px solid #67747F" align="center">
								&nbsp;
								<input id="abonentBook" value="{abonentBook}" name="abonentBook" size="10"
								       maxlength="5"/>&nbsp;</td>
							<td style="border:1px solid #67747F" align="center">
								&nbsp;
								<input id="abonentNumber" value="{abonentNumber}" name="abonentNumber" size="10"
								       maxlength="3"/>&nbsp;</td>
							<td style="border:1px solid #67747F" align="center">
								&nbsp;
								<input id="abonentCD" value="{abonentCD}" name="abonentCD" size="10" maxlength="2"/>&nbsp;</td>
						</tr>
						<tr>
							<td align="center" style="border:1px solid #67747F">�����</td>
							<td align="center" style="border:1px solid #67747F">�������</td>
							<td align="center" style="border:1px solid #67747F">�.�.</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td align="right">�.�.�.</td>
				<td colSpan="2">
					<input id="abonentName" size="41" value="{abonentName}" name="abonentName"/>
				</td>
			</tr>
			<tr>
				<td align="right">�����: ��.</td>
				<td colspan="2">
					<input id="abonentStreet" value="{abonentStreet}" name="abonentStreet" size="41"/>
					&nbsp;���&nbsp;
					<input id="abonentHouse" value="{abonentHouse}" name="abonentHouse" size="5"/>
					&nbsp;����.&nbsp;
					<input id="abonentBuilding" name="abonentBuilding" value="{abonentBuilding}" size="5"/>
					&nbsp;��.&nbsp;
					<input id="abonentFlat" value="{abonentFlat}" name="abonentFlat" size="5"/>
				</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td>
					<table cellspacing="0" cellpadding="2"
					       style="border:1px solid #67747F;border-collapse:collapse;padding-bottom:2;padding-top:2;">
						<tr>
							<td rowSpan="2" align="right" style="border:1px solid #67747F">&nbsp;���������&nbsp;<br/>&nbsp;��������&nbsp;</td>
							<td align="right" style="border:1px solid #67747F">&nbsp;�������&nbsp;</td>
							<td style="border:1px solid #67747F">
								&nbsp;
								<input id="counterCurr" value="{counterCurr}" name="counterCurr" size="10"
								       maxlength="5" onchange="setConsumption();"/>&nbsp;</td>
						</tr>
						<tr>
							<td align="right" style="border:1px solid #67747F">&nbsp;����������&nbsp;</td>
							<td style="border:1px solid #67747F">
								&nbsp;
								<input id="counterPrev" value="{counterPrev}" name="counterPrev" size="10"
								       maxlength="5" onchange="setConsumption();"/>&nbsp;</td>
						</tr>
						<tr>
							<td colSpan="2" align="right" style="border:1px solid #67747F">������, ���.�.&nbsp;<span class="asterisk">*</span>&nbsp;</td>
							<td style="border:1px solid #67747F">
								&nbsp;
								<input id="consumption" value="{consumption}" name="consumption" size="10"
								       maxlength="5" onchange="setAmount();"/>&nbsp;</td>
						</tr>
					</table>
				</td>
				<td>
					<table cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;padding-top:2;padding-bottom:2;">
						<tr>
							<td colspan="2" align="center" style="border:1px solid #67747F">�����</td>
						</tr>
						<tr>
							<td style="border:1px solid #67747F">
								&nbsp;
								<input id="tarif1" name="tarif1" value="" size="10" onchange="setAmount();"/>&nbsp;���.</td>
							<td style="border:1px solid #67747F">
								&nbsp;
								<input id="tarif2" name="tarif2" value="" size="4" maxlength="2"
								       onchange="setAmount();"/>&nbsp;���.</td>
						</tr>
						<tr>
							<td colspan="2" height="4px"></td>
						</tr>
						<tr>
							<td colspan="2" align="center" style="border:1px solid #67747F">����� � ������<span class="asterisk">*</span></td>
						</tr>
						<tr>
							<td style="border:1px solid #67747F">
								&nbsp;
								<input id="amount1" value="" name="amount1" size="10"
								       onchange="copyAmount();"/>&nbsp;���.</td>
							<td style="border:1px solid #67747F">
								&nbsp;
								<input id="amount2" value="" name="amount2" size="4" maxlength="2"
								       onchange="copyAmount();"/>&nbsp;���.</td>
						</tr>
						<tr>
                            <td align="right" valign="bottom">���&nbsp;������&nbsp;</td>
                            <td align="right" style="padding-top:4px">
								<select id="dayPartSelect" name="dayPartSelect">
									<option value="0">����+����</option>
									<option value="1">����</option>
									<option value="2">����</option>
								</select>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td colSpan="3">������������ �������:<b> ����� �� �������������� ��&nbsp;</b>
					<select id="monthSelect" name="monthSelect">
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
					<select id="yearSelect" name="yearSelect">
						<option value="2005">2005</option>
						<option value="2006">2006</option>
						<option value="2007">2007</option>
						<option value="2008">2008</option>
						<option value="2009">2009</option>
						<option value="2010">2010</option>
					</select>
				</td>
			</tr>
            <tr><td colspan="3">&nbsp;</td></tr>
        </table>
		<script type="text/javascript">
         if ('<xsl:value-of select="code1Select"/>' != '')
			setElement('code1Select', '<xsl:value-of select="code1Select"/>');
         else document.getElementById('code1Select').selected = "true";
         if ('<xsl:value-of select="code2Select"/>' != '')
            setElement('code2Select', '<xsl:value-of select="code2Select"/>');
         else document.getElementById('code2Select').selected = "true";
         if ('<xsl:value-of select="dayPartSelect"/>' != '')
            setElement('dayPartSelect', '<xsl:value-of select="dayPartSelect"/>');
         else document.getElementById('dayPartSelect').selected = "true";
            setElement('monthSelect', '<xsl:value-of select="monthSelect"/>');
			setElement('yearSelect', '<xsl:value-of select="yearSelect"/>');
            splitSum('tarif','<xsl:value-of select="tarif"/>');
            splitSum('amount','<xsl:value-of select="amount"/>');
		</script>
	</xsl:template>

	<xsl:template match="/form-data" mode="view">
		<script type="text/javascript" language="JavaScript">
			tarifType = new Array ("����+����","����","����");
		</script>
		<table cellspacing="4" cellpadding="0" border="0" class="paymentFon">
             <tr>
                <td align="right" valign="middle"><img src="{$resourceRoot}/images/ElectricPayment.gif" border="0"/>&nbsp;</td>
                <td colspan="2">
                    <table class="MaxSize">
                     <tr>
                        <td align="center" style="border-bottom:1 solid silver"  class="paperTitle">
                            <table height="100%" width="300px" cellspacing="0" cellpadding="0">
                            <tr>
                                <td class="titleHelp">
                                    <span class="formTitle">������ ��������������</span>
                                    <br/>
                                    <span id="titleHelp"></span></td>
                            </tr>
                            </table>
                        </td>

                    </tr>
                    </table>
                </td>
            </tr>
			<input id="amount" name="amount" type="hidden" value="{amount}"/>
			<input id="tarif" name="tarif" type="hidden" value="{tarif}"/>
			<tr>
				<td align="right" valign="top" class="fontBold">���� �����������:</td>
				<td valign="top">
					<xsl:variable name="acc" select="accountsSelect"/>
					<!-- TODO �� ����� ��������� ������ ������ ������� �� ���� -->
					<xsl:variable name="account"
					              select="document('accounts.xml')/entity-list/entity[@key=$acc]"/>
					<xsl:value-of select="accountsSelect"/>&nbsp;<xsl:value-of select="$account/field[@name='currencyCode']"/></td>
				<td valign="top" rowspan="3">
					<table cellspacing="0" cellpadding="2"
					       style="border:1px solid #67747F;border-collapse:collapse;margin-left:10px">
						<tr>
							<td style="border:1px solid #67747F" class="fontBold">��� ��&nbsp;</td>
							<td style="border:1px solid #67747F" width="40px">&nbsp;<xsl:value-of select="code1Select"/></td>
						</tr>
						<tr>
							<td style="border:1px solid #67747F" class="fontBold">���</td>
							<td style="border:1px solid #67747F" width="40px">&nbsp;<xsl:value-of select="code2Select"/></td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td align="right" class="fontBold">���������� �������:</td>
				<td>
					<xsl:value-of select="receiverName"/>
				</td>
			</tr>
			<tr>
				<td align="right" class="fontBold">���:</td>
				<td>
					<xsl:value-of select="receiverINN"/>
					&nbsp;�/c:&nbsp;<xsl:value-of select="receiverAccount"/></td>
			</tr>
			<tr>
				<td align="right" class="fontBold">���� ����������:</td>
				<td colspan="2">
					<xsl:variable name="bank"
					              select="document( concat( 'banklist.xml?BIC=', receiverBIC ) )/entity-list/*"/>
					<xsl:value-of select="$bank/field[@name='name']"/>
				</td>
			</tr>
			<tr>
				<td align="right" class="fontBold">���:</td>
				<td colspan="2">
					<xsl:value-of select="receiverBIC"/>&nbsp;<span class="fontBold">�/�:</span>&nbsp;<xsl:value-of select="receiverCorAccount"/></td>
			</tr>
			<tr>
				<td align="right" class="fontBold">�����&nbsp;��������:</td>
				<td>
					<table cellspacing="0" cellpadding="0"
					       style="border:1px solid #67747F;border-collapse:collapse;padding-bottom:2;padding-top:2;">
						<tr>
							<td style="border:1px solid #67747F" align="center" width="60px">
								&nbsp;
								<xsl:value-of select="abonentBook"/>&nbsp;</td>
							<td style="border:1px solid #67747F" align="center" width="60px">
								&nbsp;
								<xsl:value-of select="abonentNumber"/>&nbsp;</td>
							<td style="border:1px solid #67747F" align="center" width="60px">
								&nbsp;
								<xsl:value-of select="abonentCD"/>&nbsp;</td>
						</tr>
						<tr>
							<td align="center" style="border:1px solid #67747F" class="fontBold">�����</td>
							<td align="center" style="border:1px solid #67747F" class="fontBold">�������</td>
							<td align="center" style="border:1px solid #67747F" class="fontBold">�.�.</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td align="right" class="fontBold">�.�.�.:</td>
				<td colSpan="2">
					<xsl:value-of select="abonentName"/>
				</td>
			</tr>
			<tr>
				<td align="right" class="fontBold">�����: ��.</td>
				<td colspan="2">
					<xsl:value-of select="abonentStreet"/>
					<span
                    class="fontBold">&nbsp;���&nbsp;</span><xsl:value-of select="abonentHouse"/>
	        <span class="fontBold">&nbsp;����.&nbsp;</span><xsl:value-of select="abonentBuilding"/>
  	      <span class="fontBold">&nbsp;��.&nbsp;</span><xsl:value-of select="abonentFlat"/></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td>
					<table cellspacing="0" cellpadding="0"
					       style="border:1px solid #67747F;border-collapse:collapse;padding-top:2;padding-bottom:2;">
						<tr>
							<td rowSpan="2" align="right" style="border:1px solid #67747F" class="fontBold">&nbsp;���������&nbsp;<br/>&nbsp;��������&nbsp;</td>
							<td align="right" style="border:1px solid #67747F" class="fontBold">&nbsp;�������&nbsp;</td>
							<td style="border:1px solid #67747F" width="60px">
								&nbsp;
								<xsl:value-of select="counterCurr"/>&nbsp;</td>
						</tr>
						<tr>
							<td align="right" style="border:1px solid #67747F" class="fontBold">&nbsp;����������&nbsp;</td>
							<td style="border:1px solid #67747F" width="60px">
								&nbsp;
								<xsl:value-of select="counterPrev"/>&nbsp;</td>
						</tr>
						<tr>
							<td colSpan="2" align="right" style="border:1px solid #67747F" class="fontBold">������, ���.�.&nbsp;</td>
							<td style="border:1px solid #67747F" width="60px">
								&nbsp;
								<xsl:value-of select="consumption"/>&nbsp;</td>
						</tr>
					</table>
				</td>
				<td>
					<table cellspacing="0" cellpadding="2" border="0"
					       style="border-collapse:collapse;margin-left:40px;padding-top:2;padding-bottom:2;">
						<tr>
							<td colspan="2" align="center" style="border:1px solid #67747F" class="fontBold">�����</td>
						</tr>
						<tr>
							<td style="border:1px solid #67747F" width="70px">
								&nbsp;
								<xsl:value-of select="substring-before(tarif,'.')"/>&nbsp;���.</td>
							<td style="border:1px solid #67747F" width="50px">
								&nbsp;
								<xsl:value-of select="substring-after(tarif,'.')"/>&nbsp;���.</td>
						</tr>
						<tr>
							<td colspan="2" height="4px"></td>
						</tr>
						<tr>
							<td colspan="2" align="center" style="border:1px solid #67747F" class="fontBold">����� � ������</td>
						</tr>
						<tr>
							<td style="border:1px solid #67747F" width="70px">
								&nbsp;
								<xsl:value-of select="substring-before(amount,'.')"/>&nbsp;���.</td>
							<td style="border:1px solid #67747F" width="50px">
								&nbsp;
								<xsl:value-of select="substring-after(amount,'.')"/>&nbsp;���.</td>
						</tr>
						<tr>
                            <td align="right" valign="bottom"><b>���&nbsp;������:&nbsp;</b></td>
                            <td align="right" style="padding-top:4px">
								<script language="JavaScript">
									document.write(tarifType[<xsl:value-of select="dayPartSelect"/>]);
								</script>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td colSpan="3">
					<span class="fontBold">������������ �������:</span> ����� �� �������������� ��
					<xsl:value-of select="monthSelect"/>.<xsl:value-of select="yearSelect"/>
				</td>
			</tr>

        </table>
	</xsl:template>

</xsl:stylesheet>