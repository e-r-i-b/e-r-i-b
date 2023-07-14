<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [
<!ENTITY nbsp "&#160;">
]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="html" version="1.0" indent="yes"/>
	<xsl:param name="mode" select="'edit'"/>
	<xsl:param name="webRoot" select="'webRoot'"/>
	<xsl:param name="resourceRoot" select="'resourceRoot'"/>
	<xsl:param name="personAvailable" select="true()"/>
	<xsl:variable name="extendedFields" select="document('extendedFields.xml')/entity-list/*"/>
	<xsl:variable name="formData" select="/form-data"/>

	<xsl:template match="/">
	<!--
		МОНУМЕНТ ИМЕНИ АЛЕКСАНДРА МАКЕДОНСКОГО.
		МОЖЕТ БЫТЬ ЕСТЬ ДРУГОЙ СПОСОБ РЕАЛИЗОВАТЬ ВСЕ ТРЕБОВАНИЯ ТЗ, НО Я ЕГО НЕ УВИДЕЛ.
		ПРЕДЛОЖЕНИЯ ПО ИЗМЕНЕНИЮ(УПРОЩЕНИЮ) ПРИВЕТСТВУЮТСЯ :)
	-->
	<script type="text/javascript">
		<!--как отмечать календарь - с возможностью выбора отдельных месяцев или одним неразрывным периодом-->
		var calendarBroken = 'broken';
		var style='none';
		var minDate='';
		var maxDate='';
		function setPayPeriod( month)
		{
			var period = document.getElementById('hiddenPeriod').value;
			var select = document.getElementById('payYear');
			var year = select.options[select.selectedIndex].value;
			var period = document.getElementById('payPeriod').value;
			var selectedPeriod = month + '.' + year;
		    if(calendarBroken == 'broken')
		    {
				var index = period.indexOf(selectedPeriod);
				if( index > -1)
					period = period.substring(0, index) + period.substring(index + selectedPeriod.length,period.length);
				else
				{
					if(period.length==0)
						period = selectedPeriod;
					else
						period = period + ";" + selectedPeriod;
				}
				document.getElementById('payPeriod').value = period;
			}
		    else
			{
				if(minDate=='')
				{
					minDate = selectedPeriod;
					maxDate = selectedPeriod;
					document.getElementById('payPeriod').value = selectedPeriod;
				}
				else
				{
					var minMonth = minDate.substring(0, minDate.indexOf('.'));
					var minYear = minDate.substring(minDate.indexOf('.')+1,minDate.length);
					var maxMonth = maxDate.substring(0, maxDate.indexOf('.'));
					var maxYear = maxDate.substring(maxDate.indexOf('.')+1,maxDate.length);
		            var bl = true;
					if(minYear == year)
					{
						if(minMonth &gt; month)
						{
							minMonth = month;
							bl = false;
						}

						if(minMonth == month &amp;&amp; minMonth != 11 &amp;&amp; bl)
						{
							minMonth = parseInt(minMonth)+1;
							bl = false;
						}

						if(minMonth == month &amp;&amp; minMonth == 11 &amp;&amp; bl)
						{
							minMonth = 0;
							minYear = parseInt(minYear)+1;
							bl = false;
						}

					}

					if(maxYear == year)
					{
						if(maxMonth &lt; month)
						{
							maxMonth = month;
							bl = false;
						}

						if(maxMonth == month &amp;&amp; maxMonth != 0 &amp;&amp; bl)
						{
							maxMonth = maxMonth-1;
							bl = false;
						}

						if(maxMonth == month &amp;&amp; maxMonth == 0 &amp;&amp; bl)
						{
							maxMonth = 11;
							maxYear = maxYear-1;
							bl = false;
						}
					}
					if(minYear &gt; year &amp;&amp; bl)
					{
						minYear = year;
						minMonth = month;
						bl = false;
					}

					if(maxYear &lt; year &amp;&amp; bl)
					{
						maxYear = year;
						maxMonth = month;
						bl = false;
					}
		
					minDate = minMonth + "." + minYear;
					maxDate = maxMonth + "." + maxYear;
					document.getElementById('payPeriod').value = minDate + ";" + maxDate;
					refreshCalendar();
				}
			}
		}
		function refreshCalendar()
		{
			for(var i=0; i &lt; 12;i++)
				document.getElementById('m_'+i).checked = false;	
			if(calendarBroken == 'broken')
		 	{
				var period = document.getElementById('payPeriod').value;
				while(period.indexOf(";") > -1)
				{
					var date = period.substring(0,period.indexOf(";"));
					period = period.substring(period.indexOf(";")+1, period.length);
					var month = date.substring(0, date.indexOf("."));
					var year =  date.substring(date.indexOf(".")+1, date.length);
					var select = document.getElementById('payYear');
					var selectedYear = select.options[select.selectedIndex].value;
					if(year == selectedYear)
						document.getElementById('m_'+month).checked = true;
				}
				if(period.length > 0)
				{
					var month = period.substring(0, period.indexOf("."));

					var year =  period.substring( period.indexOf(".")+1, period.length);

					var select = document.getElementById('payYear');
					var selectedYear = select.options[select.selectedIndex].value;
					if(year == selectedYear)
						document.getElementById('m_'+month).checked = true;
				}
			}
			else
			{
				var minMonth = minDate.substring(0, minDate.indexOf('.'));
				var minYear = minDate.substring(minDate.indexOf('.')+1,minDate.length);
				var maxMonth = maxDate.substring(0, maxDate.indexOf('.'));
				var maxYear = maxDate.substring(maxDate.indexOf('.')+1,maxDate.length);
				var select = document.getElementById('payYear');
				var year = select.options[select.selectedIndex].value;
				var i;
				if(year &gt; minYear &amp;&amp; year &lt;maxYear)
				{
					for(i=0; i&lt;12; i++)
					{
						document.getElementById('m_'+i).checked = true;
					}
				}
				if(year == minYear &amp;&amp; year != maxYear)
				{
					for( i = minMonth; i&lt;12; i++)
					{
						document.getElementById('m_'+i).checked = true;
					}
				}
				if(year == maxYear &amp;&amp; year != minYear)
				{
					for( i = 0; i &lt; parseInt(maxMonth)+1; i++)
					{
						document.getElementById('m_'+i).checked = true;
					}
				}
				if(year == maxYear &amp;&amp; year == minYear)
				{
					for(i = minMonth; i&lt; parseInt(maxMonth)+1; i++)
					{
						document.getElementById('m_'+i).checked = true;
					}
				}
			}
		}
		function setSum(id)
		{
			document.getElementById('amount').value=document.getElementById(id).value;;
			document.getElementById('hiddenAmount').value = document.getElementById(id).value;
		}
        function setField(id)
		{
			document.getElementById('hid_'+id).value=document.getElementById(id).value;;
		}
	</script>
		<xsl:choose>
			<xsl:when test="$mode = 'edit'">
				<xsl:apply-templates mode="edit"/>
				<xsl:call-template name="editInitValues"/>
			</xsl:when>
			<xsl:when test="$mode = 'view'">
				<xsl:apply-templates mode="view"/>
			</xsl:when>
		</xsl:choose>
	</xsl:template>


	<xsl:template match="/form-data" mode="edit">
		<script type="text/javascript">document.webRootPrivate = '<xsl:value-of select="$webRoot"/>/private';</script>
		<input id="documentDate" name="documentDate"  type="hidden" value="{documentDate}"/>
			<tr>
				<td class="Width120 LabelAll">Списать со счета</td>
				<td>
					<xsl:variable name="payerAccount" select="payerAccountSelect"/>
					<xsl:if test="$personAvailable">
						<select id="payerAccountSelect" name="payerAccountSelect" onchange="javascript:showHideOperationCode();">
							<xsl:for-each select="document('active-debit-rur-accounts.xml')/entity-list/*">
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
						</select>
					</xsl:if>
					<xsl:if test="not($personAvailable)">
						<select disabled="disabled">
							<option selected="selected">Счет клиента</option>
						</select>
					</xsl:if>
				</td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Сумма <span class="asterisk">*</span></td>
				<td>
					<input id="amount" name="amount" type="text" value="{amount}" size="24" onchange="setSum('amount')"/>&nbsp;RUB</td>
					<input type="hidden" name="amount" id="hiddenAmount" value="{amount}"/>
			</tr>
			<tr>
				<td colspan="2" style="text-align:left;" class="Width120 LabelAll">&nbsp;<b>Получатель платежа</b></td>
			</tr>			
			<tr>
				<td class="Width120 LabelAll">Наименование</td>
				<td><input type="text" value="{receiverName}" size="24" readonly="true" class="disabledInput"/></td>
			</tr>
			<xsl:choose>
				<xsl:when test="appointment != 'gorod'">
					<tr>
						<td class="Width120 LabelAll">Назначение платежа <span class="asterisk">*</span></td>
						<td><textarea id="ground" name="ground" rows="4" cols="81"><xsl:value-of  select="ground"/></textarea></td>
					</tr>
				</xsl:when>
				<xsl:otherwise>
					<!--Поля для получателя города в соответствии с ТЗ-->
					<xsl:variable name="recInfo" select="document(concat(concat(concat('paymentReceiverJur.xml?ReceiverId=',receiverId),'&amp;appointment='),appointment))"/>
					<tr>
						<td colspan="2">
							<table cellpadding="0" cellspacing="0" class="MaxSize">
								<tr>
									<td colspan="2" style="text-align:left;" class="Width120 LabelAll"><b>&nbsp;Задолженность</b>
									</td>
								</tr>
								<tr>
									<td>
										<table>
											<tr>
												<td class="Width120 LabelAll">№ Лицевого счета</td>
												<td>
													<xsl:variable name="acc" select="$recInfo/entity-list/entity/field[@name='payerAccount']"/>
													<input type="text" value="{$acc}" size="24"
													       readonly="true"
														   class="disabledInput"/>
												</td>
											</tr>
										</table>
									</td>
								</tr>
								<tr>
									<td >
										<table class="MaxSize">
											<tr>
												<xsl:variable name="debt" select="$recInfo/entity-list/entity/field[@name='debtAmount']"/>
												<td class="Width120 LabelAll">Текущая задолженность</td>
												<td><input type="text" value="{$debt}" maxlength="9" size="24"
														   readonly="true"
														   class="disabledInput"/></td>
												<xsl:variable name="fine" select="$recInfo/entity-list/entity/field[@name='fine']"/>
												<td class="Width120 LabelAll">Пеня</td>
												<td><input  type="text" value="{$fine}" maxlength="20" size="24"
														   readonly="true" class="disabledInput"/></td>
											</tr>
										</table>
									</td>
								</tr>
								<tr>
									<td>
										<table>
											<tr>
												<xsl:variable name="lastPayDate" select="$recInfo/entity-list/entity/field[@name='lastPayDate']"/>
												<td class="Width120 LabelAll">Последняя оплаченная дата</td>
												<td><input type="text" value="{$lastPayDate}" maxlength="20" size="24"
												   readonly="true" class="disabledInput"/></td>
											</tr>
										</table>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</xsl:otherwise>
			</xsl:choose>
			
			<xsl:variable name="receiverId" select="receiverId"/>
			<xsl:variable name="appointment" select="appointment"/>
			<input type="hidden" id="hidReceiverId" name="receiverId" value="{receiverId}"/>
			<input type="hidden" id="hidAppointment" name="appointment" value="{appointment}"/>
			<input type="hidden" id="payPeriod" name="payPeriod" value="{payPeriod}"/>
			<input type="hidden" id="receivPayType" name="receivPayType" value="{receivPayType}"/>
			<!--служебные поля-->
			<input type="hidden" id="hiddenMonth"/>
			<input type="hidden" id="hiddenYear"/>
			<input type="hidden" id="hiddenPeriod"/>

			<xsl:if test="appointment = 'gorod'">
			<tr>
				<td colspan="2">
			<fieldset>
						<legend>Детали платежа</legend>
			<table width="100%">
			<tr>
				<td>
					<table cellpadding="0" cellspacing="0">
					<xsl:for-each select="$extendedFields">
						<xsl:variable name="name" select="./field[@name='name']"/>
						<xsl:variable name="id" select="./@key"/>
						<tr id='tr_{$id}' style="padding-top:2px; padding-bottom:2px">
							<td class="Width120 LabelAll" style="font-size:7.5pt; width:140px; vertical-align:middle; text-transform:uppercase;">
								<xsl:value-of select="./field[@name='name']"/>
							</td>
							<td style="vertical-align:middle">
								<xsl:variable name="length" select="./field[@name='length']"/>
								<xsl:variable name="editable" select="./field[@name='isEditable']"/>
								<xsl:variable name="val" select="./field[@name='val']"/>
								<xsl:variable name="mainSum" select="./field[@name='mainSum']"/>
								<xsl:variable name="sum" select="./field[@name='sum']"/>

								<xsl:if test="./field[@name='visible']='true'"></xsl:if>
								<xsl:choose>
									<xsl:when test="./field[@name='type']='list'">
										<select id='{$id}' name="{$id}">
											<script type="text/javascript">
												var j=0;
											<xsl:for-each select="./field[@name='value']">
												document.getElementById('<xsl:value-of select="$id"/>').options[j] =
													new Option('<xsl:value-of select="."/>','<xsl:value-of select="."/>');
												j++;
											</xsl:for-each>
											</script>
										</select>
									</xsl:when>
									<xsl:when test="./field[@name='type']='calendar'">
										<script type="text/javascript">
											style='block';
											var month = '<xsl:value-of select="./field[@name='month']"/>';
											if(month != '')
											{
												document.getElementById('hiddenMonth').value = parseInt(month.substring(0,month.indexOf('.')));
												document.getElementById('hiddenYear').value = parseInt(month.substring(month.indexOf('.')+1, month.length));
											}
											calendarBroken = "<xsl:value-of select="./field[@name='period']"/>";
										</script>
										<xsl:variable name="period" select="./field[@name='period']"/>
										<input type="hidden" value="{$period}" id="{$id}" size="{$length}" name="{$id}"/>
									</xsl:when>
									<xsl:otherwise>
										<xsl:if test="$mainSum='true'">
											 <script type="text/javascript">
												document.getElementById('amount').disabled=true;
												 <xsl:choose>
													 <xsl:when test="$val=''">
														document.getElementById('amount').value = 0.00;
												        document.getElementById('hiddenAmount').value = 0.00;
													 </xsl:when>
													 <xsl:otherwise>
														document.getElementById('amount').value = <xsl:value-of select="$val"/>;
														document.getElementById('hiddenAmount').value = <xsl:value-of select="$val"/>;
													 </xsl:otherwise>
												 </xsl:choose>
											</script>
											<input type="text" value="{$val}" id="{$id}" size="{$length}" name="{$id}" onChange="setSum('{$id}')"/>&nbsp;RUB
										</xsl:if>
										<xsl:if test="$mainSum='false'">
											<input type="text" value="{$val}" id="{$id}" size="{$length}" name="{$id}" onchange="setField('{$id}')"/>
											<xsl:if test="$sum='true'">
												&nbsp;RUB	
											</xsl:if>
											<input type="hidden" value="{$val}" id="hid_{$id}" size="{$length}" name="{$id}" />
										</xsl:if>
									</xsl:otherwise>
								</xsl:choose>
								<xsl:choose>
									<xsl:when test="./field[@name='visible']='false'">
										<script type="text/javascript">
											document.getElementById('tr_<xsl:value-of select="$id"/>').style.display='none';
										</script>
									</xsl:when>
									<xsl:when test="./field[@name='isEditable']='false'">
										<script type="text/javascript">
											document.getElementById('<xsl:value-of select="$id"/>').disabled=true;
										</script>
									</xsl:when>
								</xsl:choose>
							</td>
						</tr>
					</xsl:for-each>
					</table>

				</td>
				<td>&nbsp;</td>

				<td>
					<table id="calendarTable" style="display:none;">
						<script type="text/javascript">
							if(style=='block')
								document.getElementById('calendarTable').style.display='block';
						</script>
						<tr>
							<td>
								Год:&nbsp;
							</td>
							<td >
								<xsl:variable name="curYear" select="document('currentDate.xml')/entity-list/entity[@key='year']/field[@name='value']"/>
								<select id='payYear' onchange="refreshCalendar()">
									<script type="text/javascript">
										var options = new Array;
										for(i=-2, j=0; i &lt; 3;j++, i++)
										{
											var year = <xsl:value-of select="$curYear"/>+i;
											document.getElementById('payYear').options[j] = new Option(year, year);
											if(document.getElementById('hiddenYear').value==year)
											{
												document.getElementById('payYear').options[j].selected=true;
										    }
										 }

									</script>
								</select>
							</td>
						</tr>

						<tr>
							<td>
								<input type="checkbox" id="m_0" onclick="javascript:setPayPeriod(0)"/>Январь
							</td>
							<td>
								<input type="checkbox" id="m_6" onclick="javascript:setPayPeriod(6)"/>Июль
							</td>
						</tr>
						<tr>
							<td>
								<input type="checkbox" id="m_1" onclick="javascript:setPayPeriod(1)"/>Февраль
							</td>
							<td>
								<input type="checkbox" id="m_7" onclick="javascript:setPayPeriod(7)"/>Август
							</td>
						</tr>
						<tr>
							<td>
								<input type="checkbox" id="m_2" onclick="javascript:setPayPeriod(2)"/>Март
							</td>
							<td>
								<input type="checkbox" id="m_8" onclick="javascript:setPayPeriod(8)"/>Сентябрь
							</td>
						</tr>
						<tr>
							<td>
								<input type="checkbox" id="m_3" onclick="javascript:setPayPeriod(3)"/>Апрель
							</td>
							<td>
								<input type="checkbox" id="m_9" onclick="javascript:setPayPeriod(9)"/>Октябрь
							</td>
						</tr>
						<tr>
							<td>
								<input type="checkbox" id="m_4" onclick="javascript:setPayPeriod(4)"/>Май
							</td>
							<td>
								<input type="checkbox" id="m_10" onclick="javascript:setPayPeriod(10)"/>Ноябрь
							</td>
						</tr>
						<tr>
							<td>
								<input type="checkbox" id="m_5" onclick="javascript:setPayPeriod(5)"/>Июнь
							</td>
							<td>
								<input type="checkbox" id="m_11" onclick="javascript:setPayPeriod(11)"/>Декабрь
							</td>
						</tr>
					</table>
					<script type="text/javascript">
						var month = document.getElementById('hiddenMonth').value;
						if(month==11) month=0;
						var yearSelect = document.getElementById('payYear');
						if(month!='')
							if(yearSelect.options[yearSelect.selectedIndex].value == document.getElementById('hiddenYear').value)
							{
								document.getElementById('m_'+month).checked = true;
								setPayPeriod(month);
							}

					</script>
				</td>
			</tr>
			</table>
				</fieldset>
					
				</td>
			</tr>
			</xsl:if>
			<tr id="operationCodeRow">
				<td class="Width120 LabelAll" nowrap="true">Код валютной операции</td>
				<td>
					<xsl:if test="appointment!='gorod'">
						<input id="operationCode" size="24" value="{operationCode}" name="operationCode" onchange="refreshGround();"/>
					</xsl:if>
					<xsl:if test="appointment = 'gorod'">
						<input id="operationCode" size="24" value="{operationCode}" name="operationCode"/>
					</xsl:if>
					<input type="button" class="buttWhite" style="height:18px;width:18;"
				       onclick="javascript:openSelectOperationTypeWindow();" value="..."/>
				</td>
			</tr>
			<script type="text/javascript">

				function showHideOperationCode()
				{
				    var payer = document.getElementById("payerAccountSelect").value;
					if (payer.substring(0,5)=="40807"||payer.substring(0,5)=="40820"||payer.substring(0,3)=="426")
					{
						hideOrShow(document.getElementById("operationCodeRow"), false);
					}
					else
					{
						hideOrShow(document.getElementById("operationCodeRow"), true);
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

				function setOperationCodeInfo(operationCodeInfo)
				{
					setElement('operationCode', "{VO" + operationCodeInfo["operationCode"] + "}");
					if (document.getElementById("ground")){
				        refreshGround();
					}
				}

				function refreshGround()
				{
					var groundValue = document.getElementById("ground").value;
					if(groundValue.indexOf("{VO") >= 0)
						groundValue = groundValue.substring(0, groundValue.indexOf("{VO"));
					document.getElementById("ground").value = groundValue + document.getElementById("operationCode").value;
				}
				showHideOperationCode();
			</script>


	</xsl:template>

	<xsl:template match="/form-data" mode="view">
			<tr>
				<td class="Width120 LabelAll">Списать со счета</td>
				<td>
					<xsl:value-of select="payerAccountSelect"/>
				</td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Сумма</td>
				<td>
					<xsl:value-of select="amount"/>&nbsp;RUB &nbsp;&nbsp;Комиссия:&nbsp;<xsl:value-of select="commission"/>&nbsp;RUB</td>
			</tr>
			<tr>
				<td class="Width120 LabelAll" style="text-align:left;">&nbsp;<b>Получатель платежа</b></td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Наименование</td>
				<td>
					<xsl:value-of select="receiverName"/>
				</td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">ИНН</td>
				<td>
					<xsl:value-of select="receiverINN"/>
				</td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">КПП</td>
				<td>
					<xsl:variable name="kpp" select="receiverKPP"/>
						<xsl:value-of select="receiverKPP"/>
				</td>
			</tr>
		<script type="text/javascript">
			var receiverKPPValue = "<xsl:value-of select="receiverKPP"/>";
			if(receiverKPPValue=="")
				document.getElementById("receiverKPP").style.display='none';
		</script>
		    <xsl:if test="appointment != 'gorod'" >
				<tr>
					<td class="Width120 LabelAll">Счет</td>
					<td>
						<xsl:value-of select="receiverAccount"/>
					</td>
				</tr>
		    </xsl:if>
			<tr>
				<td class="Width120 LabelAll" colspan="2" style="text-align:left;">&nbsp;<b>Банк получателя</b></td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Наименование</td>
				<td>
					<xsl:value-of  select="receiverBankName"/>
				</td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">БИК</td>
				<td>
					<xsl:value-of select="receiverBIC"/>
				</td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Корр.Счет</td>
				<td>
					<xsl:value-of select="receiverCorAccount"/>
				</td>
			</tr>
			<xsl:if test="appointment != 'gorod'">
			<tr>
				<td class="Width120 LabelAll">Назначение платежа</td>
				<td>
					<xsl:value-of select="ground"/>
				</td>
			</tr>
			</xsl:if>
			<xsl:if test="appointment = 'gorod'">
			<tr>
				<td colspan="2">
					<fieldset>
						<legend>Детали платежа</legend>
					<table width="100%">
						<tr>
							<td width="50%">
								<table cellpadding="0" cellspacing="0">
									<xsl:for-each select="$extendedFields">
										<xsl:variable name="fieldName" select="./@key"/>
										<xsl:choose>
											<xsl:when test="./field[@name='type']='calendar'">
												<script type="text/javascript">
													style='block';
													calendarBroken = "<xsl:value-of select="./field[@name='period']"/>";
												</script>
											</xsl:when>
											<xsl:otherwise>
												<tr id='tr_{$fieldName}' style="padding-top:2px; padding-bottom:2px">
													<td class="Width120 LabelAll" style="font-size:7.5pt; width:140px; vertical-align:middle; text-transform: uppercase">
														<xsl:value-of select="./field[@name='name']"/>
													</td>
													<td style="vertical-align:middle">
														<xsl:choose>
															<xsl:when test="$formData/*[name()=$fieldName]/@changed='true'">
																<b><xsl:value-of select="$formData/*[name()=$fieldName]"/></b>
															</xsl:when>
															<xsl:otherwise>
																<xsl:value-of select="$formData/*[name()=$fieldName]"/>
															</xsl:otherwise>
														</xsl:choose>
														&nbsp;
													</td>
												</tr>
												<xsl:if test="./field[@name='visible']='false'">
													<script type="text/javascript">
														document.getElementById('tr_<xsl:value-of select="$fieldName"/>').style.display='none';
													</script>
												</xsl:if>
											</xsl:otherwise>
										</xsl:choose>
									</xsl:for-each>
								</table>
							</td>
							<td width="50%" style="display:none" id="calendarTable">
								<table>
									<script type="text/javascript">
										<![CDATA[
										if(style=='block')
											document.getElementById('calendarTable').style.display='block';
										var monthNames = new Array();
										monthNames[0] = 'Январь';
										monthNames[1] = 'Февраль';
										monthNames[2] = 'Март';
										monthNames[3] = 'Апрель';
										monthNames[4] = 'Май';
										monthNames[5] = 'Июнь';
										monthNames[6] = 'Июль';
										monthNames[7] = 'Август';
										monthNames[8] = 'Сентябрь';
										monthNames[9] = 'Октябрь';
										monthNames[10] = 'Ноябрь';
										monthNames[11] = 'Декабрь';
										]]>
										var payPeriod = '<xsl:value-of select="payPeriod"/>';
										<![CDATA[
										var years = new Array();
										var months = new Array();
										var k = 0, idx = new Array();
										var datesCount = 0;
										while(payPeriod.indexOf(";") > -1)
										{
											var date = payPeriod.substring(0,payPeriod.indexOf(";"));
											payPeriod = payPeriod.substring(payPeriod.indexOf(";")+1, payPeriod.length);
											if(date.length == 0)
												continue;
											var month = date.substring(0, date.indexOf("."));
											var year =  date.substring(date.indexOf(".")+1, date.length);
											var b = true;
											for(var i = 0; i < k; i++)
											{
												if(years[i] == year)
												{
													months[i][idx[i]++] = month;
													b = false;
                                                    datesCount++;
												}
											}
											if(b)
											{
												years[k] = year;
												idx[k] = 0;
												months[k] = new Array();
												months[k][idx[k++]++] = month;
                                                datesCount++;
											}
										}
										if(payPeriod.length > 0)
										{
											var month = payPeriod.substring(0, payPeriod.indexOf("."));
											var year =  payPeriod.substring( payPeriod.indexOf(".")+1, payPeriod.length);
										    var b = true;
											for(var i = 0; i < k; i++)
											{
												if(years[i] == year)
												{
													months[i][idx[i]++] = month;
													b = false;
													datesCount++;
												}
											}
											if(b)
											{
												years[k] = year;
												idx[k] = 0;
												months[k] = new Array();
												months[k][idx[k++]++] = month;
												datesCount++;
											}
										}
										document.write('<tr>');
										document.write("<td colspan='2'>");
										document.write('Период оплаты:');
										document.write('</td>');
										document.write('</tr>');
										if(calendarBroken == "broken")
										{
											for(var i = 0; i < k; i++)
											{
												document.write('<tr>');
												document.write('<td>');
												document.write(years[i]);
												document.write('</td>');
												document.write('<td>');
												for(var j = 0; j < idx[i]; j++)
												{
													document.write(monthNames[months[i][j]]);
													document.write('&nbsp;');
												}
												document.write('</td>');
												document.write('</tr>');
											}
										}
										else
										{
											if(datesCount == 2)
											{
											    document.write('<tr>');
												document.write('<td>');
												document.write("С" + "&nbsp;");
												document.write(years[0] + "&nbsp;");
												document.write(monthNames[months[0][0]]);
												document.write("&nbsp;" + "по" + "&nbsp;");
												if(k == 2)
												{
													document.write(years[1] + "&nbsp;");
													document.write(months[1][0]);
												}
												else
												{
													document.write(years[0] + "&nbsp;");
													document.write(monthNames[months[0][1]]);
												}
												document.write('</td>');
												document.write('</tr>');
											}
											else
											{
												document.write('<tr>');
												document.write('<td>');
												document.write(years[0] + "&nbsp;");
												document.write(monthNames[months[0][0]]);
												document.write('</td>');
												document.write('</tr>');
											}
										}

										]]>
									</script>
								</table>
							</td>
						</tr>
					</table>
					</fieldset>
				</td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Код валютной операции</td>
				<td><xsl:value-of  select="operationCode"/></td>
			</tr>
			</xsl:if>
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

	<xsl:template name="init-values">
		<xsl:param name="form-data"/>
		<xsl:choose>
			<xsl:when test="$form-data">
				<xsl:for-each select="$form-data/*">
					<xsl:if test="string-length(text()) > 0">
				setInitValue('<xsl:value-of select="name()"/>', '<xsl:value-of select="text()"/>');
					</xsl:if>
				</xsl:for-each>
			</xsl:when>
		</xsl:choose>
	</xsl:template>

	<xsl:template name="editInitValues">
		<script type="text/javascript">
		function setInitValue(elementId, value)
		{
			var elem = document.getElementById(elementId);
			var hidElem = document.getElementById('hid_' + elementId);
			if(hidElem != null)
				hidElem.value=value;	
			if (elem == null)
			    return;
			elem.value = value;
			if (elem.type == "checkbox")
			{
				var boolVal = false;
				if (value == "true" || value == "on")
					boolVal = true;
				elem.checked = boolVal;
			}
			if(elementId=='amount')
			{
			<!--подпорка ужаснейшая-->
				document.getElementById('hiddenAmount').value = value;
				setSum('hiddenAmount');
			}
		}

		<xsl:call-template name="init-values">
		   <xsl:with-param name="form-data" select="$formData"/>
		</xsl:call-template>
		</script>
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
            <xsl:when test="$code='G'">Обрабатывается</xsl:when>
		</xsl:choose>
	</xsl:template>	

	<xsl:template name="editU" match="sss">
		
	</xsl:template>

</xsl:stylesheet><!-- Stylus Studio meta-information - (c) 2004-2005. Progress Software Corporation. All rights reserved.
<metaInformation>
<scenarios ><scenario default="yes" name="Edit" userelativepaths="yes" externalpreview="no" url="form&#x2D;data.xml" htmlbaseurl="" outputurl="" processortype="xalan" useresolver="yes" profilemode="0" profiledepth="" profilelength="" urlprofilexml="" commandline="" additionalpath="" additionalclasspath="" postprocessortype="none" postprocesscommandline="" postprocessadditionalpath="" postprocessgeneratedext="" validateoutput="no" validator="internal" customvalidator="" ><parameterValue name="mode" value="'edit'"/></scenario><scenario default="no" name="View" userelativepaths="yes" externalpreview="no" url="form&#x2D;data.xml" htmlbaseurl="" outputurl="" processortype="internal" useresolver="yes" profilemode="0" profiledepth="" profilelength="" urlprofilexml="" commandline="" additionalpath="" additionalclasspath="" postprocessortype="none" postprocesscommandline="" postprocessadditionalpath="" postprocessgeneratedext="" validateoutput="no" validator="internal" customvalidator="" ><parameterValue name="mode" value="'view'"/></scenario></scenarios><MapperMetaTag><MapperInfo srcSchemaPathIsRelative="yes" srcSchemaInterpretAsXML="no" destSchemaPath="" destSchemaRoot="" destSchemaPathIsRelative="yes" destSchemaInterpretAsXML="no" ><SourceSchema srcSchemaPath="form&#x2D;data.xml" srcSchemaRoot="form&#x2D;data" AssociatedInstance="" loaderFunction="document" loaderFunctionUsesURI="no"/><SourceSchema srcSchemaPath="preparedPayment_a.xml" srcSchemaRoot="preparedPayment_a" AssociatedInstance="file:///c:/Projects/RS/PhizIC&#x2D;SBRF/Business/settings/forms/RurPayJurSB/preparedPayment_a.xml" loaderFunction="document" loaderFunctionUsesURI="no"/></MapperInfo><MapperBlockPosition><template match="/"></template><template match="/form&#x2D;data"><block path="script/xsl:value&#x2D;of" x="167" y="18"/><block path="table/tr[1]/td[1]/xsl:if" x="127" y="137"/><block path="table/tr[1]/td[1]/xsl:if/select/xsl:for&#x2D;each" x="167" y="177"/><block path="table/tr[1]/td[1]/xsl:if/select/xsl:for&#x2D;each/option/xsl:attribute/xsl:value&#x2D;of" x="87" y="177"/><block path="table/tr[1]/td[1]/xsl:if/select/xsl:for&#x2D;each/option/xsl:if/=[0]" x="1" y="175"/><block path="table/tr[1]/td[1]/xsl:if/select/xsl:for&#x2D;each/option/xsl:if" x="47" y="177"/><block path="table/tr[1]/td[1]/xsl:if/select/xsl:for&#x2D;each/option/xsl:value&#x2D;of" x="47" y="57"/><block path="table/tr[1]/td[1]/xsl:if/select/xsl:for&#x2D;each/option/xsl:value&#x2D;of[1]" x="127" y="17"/><block path="table/tr[1]/td[1]/xsl:if/select/xsl:for&#x2D;each/option/xsl:value&#x2D;of[2]" x="87" y="17"/><block path="table/tr[1]/td[1]/xsl:if/select/xsl:for&#x2D;each/option/xsl:value&#x2D;of[3]" x="47" y="17"/><block path="table/tr[1]/td[1]/xsl:if[1]" x="87" y="137"/><block path="table/tr[5]/td[1]/textarea/xsl:value&#x2D;of" x="47" y="137"/><block path="table/tr[5]/td[1]/textarea/xsl:value&#x2D;of[1]" x="167" y="97"/><block path="table/xsl:for&#x2D;each" x="167" y="137"/><block path="table/xsl:for&#x2D;each/tr/td/xsl:value&#x2D;of" x="207" y="137"/><block path="table/xsl:for&#x2D;each/tr/td[1]/nobr/xsl:for&#x2D;each" x="97" y="67"/><block path="table/xsl:for&#x2D;each/tr/td[1]/nobr/xsl:for&#x2D;each/xsl:value&#x2D;of" x="207" y="97"/><block path="table/xsl:for&#x2D;each/tr/td[1]/nobr/xsl:for&#x2D;each/xsl:choose" x="217" y="27"/><block path="table/xsl:for&#x2D;each/tr/td[1]/nobr/xsl:for&#x2D;each/xsl:choose/=[0]" x="171" y="21"/><block path="table/xsl:for&#x2D;each/tr/td[1]/nobr/xsl:for&#x2D;each/xsl:choose/xsl:when/select/xsl:for&#x2D;each" x="207" y="217"/><block path="table/xsl:for&#x2D;each/tr/td[1]/nobr/xsl:for&#x2D;each/xsl:choose/xsl:when/select/xsl:for&#x2D;each/xsl:element/xsl:if/=[0]" x="81" y="215"/><block path="table/xsl:for&#x2D;each/tr/td[1]/nobr/xsl:for&#x2D;each/xsl:choose/xsl:when/select/xsl:for&#x2D;each/xsl:element/xsl:if" x="127" y="217"/><block path="table/xsl:for&#x2D;each/tr/td[1]/nobr/xsl:for&#x2D;each/xsl:choose/xsl:when/select/xsl:for&#x2D;each/xsl:element/xsl:value&#x2D;of" x="167" y="217"/><block path="table/xsl:for&#x2D;each/tr/td[1]/nobr/xsl:for&#x2D;each/xsl:choose/=[1]" x="171" y="49"/><block path="" x="47" y="97"/><block path="table/xsl:for&#x2D;each/tr/td[1]/nobr/xsl:for&#x2D;each/xsl:choose/xsl:otherwise/xsl:if/>[0]" x="161" y="175"/><block path="table/xsl:for&#x2D;each/tr/td[1]/nobr/xsl:for&#x2D;each/xsl:choose/xsl:otherwise/xsl:if/>[0]/number[0]" x="115" y="169"/><block path="table/xsl:for&#x2D;each/tr/td[1]/nobr/xsl:for&#x2D;each/xsl:choose/xsl:otherwise/xsl:if" x="207" y="177"/><block path="table/xsl:for&#x2D;each/tr/td[1]/nobr/xsl:for&#x2D;each/xsl:choose/xsl:otherwise/xsl:if[1]" x="127" y="177"/><block path="table/xsl:for&#x2D;each/tr/td[1]/nobr/xsl:for&#x2D;each/xsl:value&#x2D;of[1]" x="167" y="57"/></template></MapperBlockPosition><TemplateContext></TemplateContext><MapperFilter side="source"></MapperFilter></MapperMetaTag>
</metaInformation>
-->
