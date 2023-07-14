<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [<!ENTITY nbsp "&#160;">]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="html" version="1.0"  indent="yes"/>
	<xsl:param name="mode" select="'edit'"/>
	<xsl:param name="webRoot" select="'webRoot'"/>
	<xsl:param name="resourceRoot" select="'resourceRoot'"/>
	<xsl:param name="personAvailable" select="true()"/>
    <xsl:param name="receiverInfo" select="'receiverInfo'"/>
    <xsl:template match="/">

		<xsl:choose>
			<xsl:when test="$mode = 'edit'">
				<xsl:apply-templates mode="edit"/>
			</xsl:when>
			<xsl:when test="$mode = 'view'">
				<xsl:apply-templates mode="view"/>
			</xsl:when>
		</xsl:choose>
    </xsl:template>

    <xsl:template match="/form-data" mode="edit">
		<script type="text/javascript" language="JavaScript">
			document.webRootPrivate = '<xsl:value-of select="$webRoot"/>/private';
          </script>
	    <input id="documentDate" name="documentDate"  type="hidden" value="{documentDate}"/>
            <tr>
				<td class="Width120 LabelAll">Списать со счета</td>
				<td>
					<xsl:variable name="payerAccount" select="payerAccountSelect"/>
					<xsl:if test="$personAvailable">
						<xsl:variable name="accounts" select="document('active-debit-rur-accounts.xml')/entity-list/*"/>
							<select id="payerAccountSelect" name="payerAccountSelect" onchange="javascript:showHideOperationCode();">
								<xsl:choose>
									<xsl:when test="count($accounts) = 0">
										<option value="">нет счетов</option>
									</xsl:when>
									<xsl:otherwise>
										<xsl:for-each select="$accounts">
											<option>
												<xsl:attribute name="value">
														<xsl:value-of select="./@key"/>
													</xsl:attribute>
													<xsl:if test="$payerAccount = ./@key">
														<xsl:attribute name="selected">true</xsl:attribute>
													</xsl:if>
													<xsl:value-of select="./@key"/>&nbsp;
													[<xsl:value-of select="./field[@name='type']"/>]
													<xsl:value-of select="format-number(./field[@name='amountDecimal'], '0.00')"/>&nbsp;
													<xsl:value-of select="./field[@name='currencyCode']"/>
												</option>
										</xsl:for-each>
									</xsl:otherwise>
								</xsl:choose>
							</select>
					</xsl:if>
					<xsl:if test="not($personAvailable)">
						<select disabled="disabled"><option selected="selected">Счет клиента</option></select>
					</xsl:if>
				</td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Сумма <span class="asterisk">*</span></td>
				<td><input id="amount" name="amount" type="text" value="{amount}" size="24"/>&nbsp;RUB</td>
			</tr>
			<tr>
				<td colspan="2" style="text-align:left;" class="Width120 LabelAll">&nbsp;<b>Получатель платежа</b></td>
			</tr>
	        <tr>
		        <td class="Width120 LabelAll">Вид получателя платежа</td>
		        <td>
					<select id="receivPayType" name="receivPayType" onchange="clearValues();changeReceiverPayType(this.selectedIndex);showHideOperationCode();">
						<option value="contract">По договору</option>
						<option value="another">Другой</option>
					</select>
			    </td>
		    </tr>
			<tr id="receivPaySelectLine">
			    <td class="Width120 LabelAll">Условное обозначение</td>
				<td>
					<select id="receivPaySelect" name="receivPaySelect" onchange="setReceivParam(this.selectedIndex); showHideOperationCode();"/>
			    </td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">ФИО<span class="asterisk">*</span></td>
				<td>
                    <table class="MaxSize" cellspacing="0" cellspadding="0">
                    <tr>
                        <td>
                            <input id="receiverName" name="receiverName" type="text" value="{receiverName}" size="80" readonly="true"/>
                        </td>
                    </tr>
                    </table>
                </td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Счет <span class="asterisk">*</span></td>
				<td><input id="receiverAccount" name="receiverAccount" type="text" value="{receiverAccount}" maxlength="25" size="30" readonly="true" onchange="showHideOperationCode();"/></td>
			</tr>
			<tr>
				<td colspan="2" style="text-align:left;" class="Width120 LabelAll"><b>&nbsp;Банк получателя</b></td>
			</tr>			
			<tr>
				<td class="Width120 LabelAll">Наименование <span class="asterisk">*</span></td>
				<td><input id="bank" name="bank" type="text" value="{bank}" size="80" readonly="true"/>
					<input type="button" class="buttWhite smButt" onclick="javascript:openNationalBanksDictionary(setBankInfo,getFieldValue('bank'),getFieldValue('receiverBIC'));" value="..."/>
				</td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">БИК <span class="asterisk">*</span></td>
				<td><input id="receiverBIC" name="receiverBIC" type="text" value="{receiverBIC}" maxlength="9" size="24" readonly="true"/></td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Корр. счет <span class="asterisk">*</span></td>
				<td><input id="receiverCorAccount" name="receiverCorAccount" type="text" value="{receiverCorAccount}" maxlength="20" size="24" readonly="true"/></td>
			</tr>
			<tr>
				<td class="Width120 LabelAll" valign="top">Назначение платежа <span class="asterisk">*</span></td>
				<td>
					<textarea id="ground" name="ground" rows="4" cols="81"><xsl:value-of  select="ground"/></textarea>
					</td>
			</tr>
			<tr id="operationCodeRow">
				<td class="Width120 LabelAll" nowrap="true">Код валютной операции</td>
				<td>
					<input id="operationCode" size="24" value="{operationCode}" name="operationCode" onchange="refreshGround();"/>&nbsp;
					<input type="button" class="buttWhite" style="height:18px;width:18;"
				       onclick="javascript:openSelectOperationTypeWindow();" value="..."/>
				</td>
			</tr>
	<script type="text/javascript" language="JavaScript">

         var receivers = new Array();

            <xsl:if test="$personAvailable">
			  <xsl:for-each select="document('receivpay.xml')/entity-list/*">
                 receivers[<xsl:value-of select="position()-1"/>] =
  new ReceivProp ("<xsl:value-of select="@key"/>",
                  "<xsl:value-of select="./field[@name='name']"/>",
                  '<xsl:value-of select="./field[@name='alias']"/>',
                  "<xsl:value-of select="./field[@name='account']"/>",
                  "<xsl:value-of select="./field[@name='branch']"/>",
                  "<xsl:value-of select="./field[@name='office']"/>",
                  '<xsl:value-of select="./field[@name='info']"/>',
                  "<xsl:value-of select="./field[@name='bic']"/>",
                  '<xsl:value-of select="./field[@name='bankName']"/>',
                  "<xsl:value-of select="./field[@name='corAccount']"/>",
				  "<xsl:value-of select="./field[@name='ground']"/>");
              </xsl:for-each>
            </xsl:if>

         function setReceivParam (id)
         {
              var rec = receivers[id];
              document.getElementById("receiverName").value = rec.name;
              document.getElementById("receiverAccount").value = rec.acc;
              var ground = document.getElementById("ground").value;
		      if (rec.info != "")
                document.getElementById("ground").value = rec.info;
              document.getElementById("receiverBIC").value = rec.bic;
              document.getElementById("receiverCorAccount").value = rec.corAccount;
              document.getElementById("bank").value = rec.bankName;
         }

		 function changeReceiverPayType(id)
         {			
			if(id==0)
			{
				document.getElementById("receivPaySelectLine").style.display = "";
				document.getElementById("button").style.display = "none";
				document.getElementById("receiverName").readOnly = true;
				document.getElementById("receiverAccount").readOnly = true;
				document.getElementById("bank").readOnly = true;
				document.getElementById("receiverBIC").readOnly = true;
				document.getElementById("receiverCorAccount").readOnly = true;

				var receiverSelect = document.getElementById("receivPaySelect");
				if(receivers.length>0)
				{
					var rec = receivers[receiverSelect.selectedIndex];

					document.getElementById("receiverName").value = rec.name;
					document.getElementById("receiverAccount").value = rec.acc;
					document.getElementById("receiverCorAccount").value = rec.corAccount;
					document.getElementById("receiverBIC").value = rec.bic;
					document.getElementById("bank").value = rec.bankName;
				}
			}
			if(id==1)
			{
				document.getElementById("receivPaySelectLine").style.display = "none";
				document.getElementById("button").style.display = "";
				document.getElementById("receiverName").readOnly = false;
				document.getElementById("receiverAccount").readOnly = false;
				document.getElementById("bank").readOnly = false;
				document.getElementById("receiverBIC").readOnly = false;
				document.getElementById("receiverCorAccount").readOnly = false;
			}
         }

		function clearValues()
		{
			document.getElementById("receiverName").value = "";
			document.getElementById("receiverAccount").value = "";
			document.getElementById("bank").value = "";
			document.getElementById("receiverBIC").value = "";
			document.getElementById("receiverCorAccount").value = "";
		}

		function setBankInfo(bankInfo)
		{			
			setElement("bank", bankInfo["name"]);
			setElement("receiverBIC", bankInfo["BIC"]);
			setElement("receiverCorAccount", bankInfo["account"]);
		}

         function ReceivProp(_id, _name, _alias, _acc, _branch, _office, _info, _bic, _bankName, _corAccount, _ground)
         {
            this.id = _id;
            this.name = _name;
            this.alias = _alias;
            this.acc = _acc;
            this.branch = _branch;
            this.office = _office;
            this.info = _info;
            this.bic = _bic;
            this.bankName = _bankName;
            this.corAccount = _corAccount;
		    this.ground = _ground;
         }

		 function setOption()
		 {
		    var o = document.getElementById("receivPaySelect");
			var num = receivers.length;
            var receivPaySelect = "<xsl:value-of select="receivPaySelect"/>";
			if(num == 0)
				o.options.add(new Option ("нет получателей", "нет получателей", true));

			for (var i=0; i &lt; num; i++)
               if ((receivPaySelect == "") &amp;&amp; (i == 0))
               {
                  o.options.add(new Option (receivers[i].alias, receivers[i].alias, true));
               }
               else if (receivers[i].alias == receivPaySelect)
               {
                  o.options.add(new Option (receivers[i].alias, receivers[i].alias, true));
                  o.options[i].selected=true;				  
			   }
			   else
                  o.options.add(new Option (receivers[i].alias, receivers[i].alias, false));
			var receiverType = '<xsl:value-of select="receivPayType"/>';
			if(receiverType == "another")
			{
				setElement("receivPayType", '<xsl:value-of select="receivPayType"/>');
				changeReceiverPayType(1);
			}
			else
			{
				changeReceiverPayType(0);				
			}
		 }
		function openSelectOperationTypeWindow()
		{
	        var val = getElementValue("operationCode");
	        if (val.length > 0)
	             window.open('../operationCodes.do?' + "fltrCode=" + val.substring(3,8), 'OperationTypes', "resizable=1,menubar=0,toolbar=0,scrollbars=1");
	        else
                window.open('../operationCodes.do', 'OperationTypes', "resizable=1,menubar=0,toolbar=0,scrollbars=1");
		}

		function showHideOperationCode()
		{
			var isRes = '<xsl:value-of select="document('currentPerson.xml')/entity-list/entity/field[@name = 'isResident']"/>';
			if(isRes == 'false' || isResident(document.getElementById("receiverAccount").value))
			{
				hideOrShow(document.getElementById("operationCodeRow"), false);
			}
			else
			{
				hideOrShow(document.getElementById("operationCodeRow"), true);
			}
		}
	    function setOperationCodeInfo(operationCodeInfo)
		{
	       setElement('operationCode', "{VO" + operationCodeInfo["operationCode"] + "}");
		   refreshGround();
		}
		function refreshGround()
		{
			var groundValue = document.getElementById("ground").value;
			if(groundValue.indexOf("{VO") >= 0)
				groundValue = groundValue.substring(0, groundValue.indexOf("{VO"));
			document.getElementById("ground").value = groundValue + document.getElementById("operationCode").value;
		}
       setOption();
	   showHideOperationCode();
    </script>
	</xsl:template>

    <xsl:template match="/form-data" mode="view">
            <tr>
				<td class="Width120 LabelAll">Списать со счета</td>
				<td>
					<xsl:value-of select="payerAccountSelect"/>&nbsp;
					[<xsl:value-of select="payerAccountSelectType"/>]&nbsp;
					<xsl:value-of select="amountCurrency"/>
				</td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Сумма</td>
				<td>
					<xsl:value-of select="amount"/>&nbsp;RUB &nbsp;&nbsp;Комиссия:&nbsp;<xsl:value-of select="commission"/>&nbsp;RUB</td>
			</tr>
            <tr>
				<td class="Width120 LabelAll" style="text-align:left;">&nbsp;<b>Получатель платежа</b></td>
                <td><xsl:value-of  select="receiverAlias"/></td>
            </tr>
            <tr>
				<td class="Width120 LabelAll">ФИО</td>
				<td><xsl:value-of  select="receiverName"/></td>
            </tr>
			<tr>
				<td class="Width120 LabelAll">Счет</td>
				<td><xsl:value-of  select="receiverAccount"/></td>
            </tr>
            <tr>
				<td class="Width120 LabelAll" colspan="2" style="text-align:left;">&nbsp;<b>Банк получателя</b></td>
            </tr>
            <tr>
				<td class="Width120 LabelAll">Наименование</td>
				<td>
					<xsl:value-of  select="bank"/>					
				</td>
            </tr>
			 <tr>
				<td class="Width120 LabelAll">БИК</td>
				<td><xsl:value-of  select="receiverBIC"/></td>
            </tr>
	        <tr>
				<td class="Width120 LabelAll">Корр.Счет</td>
				<td><xsl:value-of  select="receiverCorAccount"/></td>
            </tr>
			<tr>
				<td class="Width120 LabelAll">Назначение платежа</td>
				<td><xsl:value-of  select="ground"/></td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Статус платежа</td>
				<td><div id="state">
						<a onmouseover="showLayer('state','stateDescription');" onmouseout="hideLayer('stateDescription');" style="text-decoration:underline">
							<xsl:call-template name="state2text">
								<xsl:with-param name="code">
									<xsl:value-of select="state"/>
								</xsl:with-param>
							</xsl:call-template>
						</a>
					</div>
				</td>
			</tr>
	    <tr>
				<td class="Width120 LabelAll">Плановая дата исполнения</td>
				<td><xsl:value-of  select="admissionDate"/></td>
			</tr>
    </xsl:template>
	
	<xsl:template name="state2text">
		<xsl:param name="code"/>
		<xsl:choose>
			<xsl:when test="$code='I'">Введен</xsl:when>
			<xsl:when test="$code='W'">Обрабатывается</xsl:when>
			<xsl:when test="$code='S'">Исполнен</xsl:when>
			<xsl:when test="$code='E'">Отказан</xsl:when>
			<xsl:when test="$code='V'">Отозван</xsl:when>
			<xsl:when test="$code='F'">Приостановлен</xsl:when>
		</xsl:choose>
	</xsl:template>
</xsl:stylesheet>
