<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz"%>
<table cellpadding="0" cellspacing="0" width="180mm" style="margin-left:5mm;margin-right:5mm;margin-top:10mm;margin-bottom:7mm;table-layout:fixed;">
<col style="width:180mm">
<tr>
<td>
<table style="width:100%;height:100%;" cellpadding="0" cellspacing="0" class="textDoc">

<%@include file="footer.jsp"%>

<tbody>
<!-- Шапка документа -->
<tr>
	<td align="right">
		<b>Приложение 2</b><br><br>&nbsp;
		<span class="italic font10">к Договору о предоставлении<br>
		   услуг с использованием системы<br>
		   &ldquo;Электронная Сберкасса&rdquo;<br>
	    </span>
		№<input type="Text" value='${person.agreementNumber}' readonly="true" class="insertInput" style="width:21%">от&nbsp;&ldquo;<input value='<bean:write name="agreementDate" format="dd"/>' type="Text" readonly="true" class="insertInput" style="width:4%">&rdquo;
		 <input id='monthStr22' value='' type="Text" readonly="true" class="insertInput" style="width:13%">20<input value='<bean:write name="agreementDate" format="y"/>' type="Text" readonly="true" class="insertInput" style="width:3%">г.
	</td>
    <script>
       document.getElementById('monthStr22').value = monthToStringOnly('<bean:write name="agreementDate" format="dd.MM.yyyy"/>');
    </script>

</tr>
<tr>
	<td>
		<br>
	</td>
</tr>
<tr>
	<td>
	<table cellspacing="0" class="textDoc docTableBorder docTdBorder" width="100%">
		<tr>
			<td class="textPadding" align="center" width="80">
				Код&nbsp;(идентификатор)&nbsp;Клиента&nbsp;в&nbsp;системе&nbsp;<br>&ldquo;Электронная&nbsp;Сберкасса&rdquo;
			</td>
            <td class="textPadding" width="20">
               ${form.pin}
            </td>
		</tr>
	</table>
	</td>
</tr>

<tr>
	<td align="center">
		<b>ПЕРЕЧЕНЬ<br>
		представителей Клиента, имеющих доступ к счетам Клиента через систему &ldquo;Электронная Сберкасса&rdquo;
	</td>
</tr>

<tr>
	<td>
		<table cellspacing="0" class="textDoc docTableBorder" width="100%">
		<tr>
			<td class="docTdBorder" valign="top" align="center" width="5%"><b>№ п/п</b></td>
			<td class="docTdBorder" valign="top" align="center" width="47%"><b>&nbsp;Ф.И.О. представителей Клиента, данные документа, удостоверяющего личность представителя Клиента, адрес регистрации, адрес фактического проживания, адрес электронной почты, телефон (дом.), телефон (раб.), телефон (моб.), ИНН (при наличии), гражданство, дата и место рождения, вид и реквизиты документа, подтверждающего право на пребывание (проживание) в Российской Федерации*, данные миграционной карты*
									</b>
			</td>
			<td class="docTdBorder" valign="top" align="center"><b>&nbsp;Код (идентификатор) Представителя Клиента в системе  &ldquo;Электронная Сберкасса&rdquo;&nbsp;</b></td>
			<td class="docTdBorder" valign="top" align="center"><b>&nbsp;Номера счетов, к которым имеют доступ представители Клиента&nbsp;</b></td>
		</tr>

            <c:set var="lineNumber" value= "0"/>
            <c:forEach var="empoweredPerson" items="${empoweredPersons}">

	            <c:set var="empoweredDocument" value="${activeEmpoweredsDocument[empoweredPerson.id]}"/>
				<c:set var="empoweredNotResidentDocument" value="${activeEmpoweredsNotResidentDocument[empoweredPerson.id]}"/>
				<c:set var="empoweredMigratoryCard" value="${empoweredsMigratoryCard[empoweredPerson.id]}"/>

                <c:set var="accountLinks" value="${empoweredPersonsAccounts[empoweredPerson.id]}"/>
                <c:set var="accountsSize" value= "0"/>
                <c:forEach var="accountLink" items="${accountLinks}">
                    <c:set var="accountsSize" value="${accountsSize+1}"/>
                </c:forEach>

                <c:set var="lineNumber" value="${lineNumber+1}"/>
                <tr>
                    <td class="docTdBorder" align="center">&nbsp;<c:out value="${lineNumber}"/>&nbsp;</td>
                    <td class="docTdBorder" style="text-align:left; padding-left:2mm">
	                    <table cellspacing="0" cellpadding="0">
		                    <tr>
			                    <td><i>Ф.И.О.</i>:&nbsp;${empoweredPerson.fullName}</td>
		                    </tr>
		                    <tr>
			                    <td><i>Документ, удостоверяющий личность</i>:<br/>
				                   <c:if test="${(empoweredDocument.documentType == 'REGULAR_PASSPORT_RF')}">
										Общегражданский паспорт РФ
									</c:if>
									<c:if test="${(empoweredDocument.documentType == 'MILITARY_IDCARD')}">
										Удостоверение личности военнослужащего
									</c:if>
									<c:if test="${(empoweredDocument.documentType == 'SEAMEN_PASSPORT')}">
										Паспорт моряка
									</c:if>
									<c:if test="${(empoweredDocument.documentType == 'RESIDENСЕ_PERMIT_RF')}">
										Вид на жительство РФ
									</c:if>
									<c:if test="${(empoweredDocument.documentType == 'FOREIGN_PASSPORT_RF')}">
										Заграничный паспорт РФ
									</c:if>
									<c:if test="${(empoweredDocument.documentType == 'OTHER')}">
									   ${empoweredDocument.documentName}
									</c:if>
				                    &nbsp;${empoweredDocument.documentSeries}&nbsp;${empoweredDocument.documentNumber}&nbsp;
				                    <c:if test="${not empty empoweredDocument.documentIssueBy || not empty empoweredDocument.documentIssueDate}"><i>выдан</i>:</c:if>
									<c:if test="${not empty empoweredDocument.documentIssueBy}">&nbsp;${empoweredDocument.documentIssueBy}</c:if>
				                    <c:if test="${not empty empoweredDocument.documentIssueDate}">&nbsp;<bean:write name="empoweredDocument" property="documentIssueDate.time" format="dd.MM.yyyy"/></c:if>
			                    </td>
		                    </tr>
		                    <c:if test="${not empty empoweredPerson.registrationAddress && empoweredPerson.registrationAddress!=''}">
		                    <tr>
			                    <td><i>Адрес регистрации</i>:${empoweredPerson.registrationAddress}</td>
		                    </tr>
			                </c:if>
		                    <c:if test="${not empty empoweredPerson.residenceAddress && empoweredPerson.residenceAddress!=''}">
		                    <tr>
			                    <td><i>Адрес фактического проживания</i>:${empoweredPerson.residenceAddress}</td>
		                    </tr>			                    
		                    </c:if>
		                    <c:if test="${not empty empoweredPerson.email}">
			                    <tr>
				                    <td><i>Адрес электронной почты</i>:${empoweredPerson.email}</td>
			                    </tr>
		                    </c:if>
		                    <c:if test="${not empty empoweredPerson.homePhone}">
			                    <tr>
				                    <td><i>Телефон (дом.)</i>:${empoweredPerson.homePhone}</td>
			                    </tr>
		                    </c:if>
		                    <c:if test="${not empty empoweredPerson.jobPhone}">
			                    <tr>
				                    <td><i>Телефон (раб.)</i>:${empoweredPerson.jobPhone}</td>
			                    </tr>
		                    </c:if>
		                    <c:if test="${not empty empoweredPerson.mobilePhone}">
			                    <tr>
				                    <td><i>Телефон (моб.)</i>:${empoweredPerson.mobilePhone}</td>
			                    </tr>
		                    </c:if>
		                    <c:if test="${not empty empoweredPerson.inn}">
			                    <tr>
				                    <td><i>ИНН</i>:${empoweredPerson.inn}</td>
			                    </tr>
		                    </c:if>
		                    <c:if test="${not empty empoweredPerson.citizenship}">
			                    <tr>
				                    <td><i>Гражданство</i>:${empoweredPerson.citizenship}</td>
			                    </tr>
		                    </c:if>
		                    <c:if test="${not empty empoweredPerson.birthDay || not empty empoweredPerson.birthPlace}">
			                    <tr>
				                    <td><i>Дата и место рождения</i>:
					                    <bean:write name="empoweredPerson" property="birthDay" format="dd.MM.yyyy"/>, <bean:write name="empoweredPerson" property="birthPlace" format="dd.MM.yyyy"/>
				                    </td>
			                    </tr>
		                    </c:if>
		                    <c:if test="${not empty empoweredNotResidentDocument}">
			                    <tr>
				                    <td><i>Документ, подтверждающий право на пребывание (проживание) в РФ</i>:
					                    <c:if test="${(empoweredNotResidentDocument.documentType == 'IMMIGRANT_REGISTRATION')}">
											Свидетельство о регистрации ходатайства иммигранта о признании его беженцем
										</c:if>
										<c:if test="${(empoweredNotResidentDocument.documentType == 'RESIDENTIAL_PERMIT_RF')}">
											Вид на жительство РФ
										</c:if>
										<c:if test="${(empoweredNotResidentDocument.documentType == 'REFUGEE_IDENTITY')}">
											Удостоверение беженца в РФ
										</c:if>
										<c:if test="${not empty empoweredNotResidentDocument.documentSeries}">&nbsp;${empoweredNotResidentDocument.documentSeries}</c:if>
										<c:if test="${not empty empoweredNotResidentDocument.documentNumber}">&nbsp;${empoweredNotResidentDocument.documentNumber}</c:if>

										<c:if test="${not empty empoweredNotResidentDocument.documentIssueBy || not empty empoweredNotResidentDocument.documentIssueDate}">, выдан: </c:if>
										<c:if test="${not empty empoweredNotResidentDocument.documentIssueBy}">${empoweredNotResidentDocument.documentIssueBy}</c:if>
										<c:if test="${not empty empoweredNotResidentDocument.documentIssueDate}"><bean:write name="empoweredNotResidentDocument" property="documentIssueDate.time" format="dd.MM.yyyy"/></c:if>
				                    </td>
			                    </tr>
		                    </c:if>
		                    <c:if test="${not empty empoweredMigratoryCard}">
			                    <tr>
				                    <td><i>Миграционная карта</i>:
					                    <c:if test="${not empty empoweredMigratoryCard.documentNumber}">${empoweredMigratoryCard.documentNumber}</c:if>
										<c:if test="${not empty empoweredMigratoryCard.documentIssueDate}">c <bean:write name="empoweredMigratoryCard" property="documentIssueDate.time" format="dd.MM.yyyy"/></c:if>
										<c:if test="${not empty empoweredMigratoryCard.documentTimeUpDate}"> по <bean:write name="empoweredMigratoryCard" property="documentTimeUpDate.time" format="dd.MM.yyyy"/></c:if>
				                    </td>
			                    </tr>
		                    </c:if>
	                    </table>
                    </td>
	                <td class="docTdBorder">&nbsp;
		                <c:if test="${not empty empoweredPerson.login.userId}">
							<bean:write name="empoweredPerson" property="login.userId"/>
						</c:if>
		                &nbsp;</td>
                    <td class="docTdBorder">&nbsp;
                <c:set var="accountNumber" value= "0"/>
                <c:forEach var="accountLink" items="${accountLinks}">
                    <c:set var="accountNumber" value="${accountNumber+1}"/>
                    <c:choose>
                        <c:when test="${accountNumber=='1'}">
                            ${accountLink.value.number}<br>
                        </c:when>
                        <c:otherwise>
                            &nbsp;&nbsp;${accountLink.value.number}<br>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
                   </td>
	            </tr>
            </c:forEach>
        </table>
	</td>
</tr>
<tr>
    <td class="textDoc" style="font-size:9pt; padding:3mm 0mm 0mm 2mm">
	    <nobr>* Для физических лиц-нерезидентов и лиц без гражданства</nobr>
    </td>
</tr>

<c:forEach var="empoweredPerson" items="${empoweredPersons}">
    <c:set var="services" value="${empoweredPersonsServices[empoweredPerson.id]}"/>

        <c:set var="AccountAndCardInfo" value= "0"/>
        <c:set var="Abstract" value= "0"/>
        <c:set var="RurPayment" value= "0"/>
        <c:set var="RurPayJurSB" value= "0"/>
        <c:set var="InternalPayment" value= "0"/>
        <c:set var="PurchaseCurrencyPayment" value= "0"/>
        <c:set var="SaleCurrencyPayment" value= "0"/>
        <c:set var="ConvertCurrencyPayment" value= "0"/>
        <c:set var="PaymentDocumentPreparation" value= "0"/>
        <c:forEach var="service" items="${services}">
           <c:if test="${service.key=='AccountAndCardInfo'}">
               <c:set var="AccountAndCardInfo" value= "1"/>
           </c:if>
           <c:if test="${service.key=='Abstract'}">
               <c:set var="Abstract" value= "1"/>
           </c:if>
           <c:if test="${service.key=='RurPayment'}">
               <c:set var="RurPayment" value= "1"/>
           </c:if>
           <c:if test="${service.key=='InternalPayment'}">
               <c:set var="InternalPayment" value= "1"/>
           </c:if>
            <c:if test="${service.key=='PurchaseCurrencyPayment'}">
                <c:set var="PurchaseCurrencyPayment" value= "1"/>
            </c:if>
            <c:if test="${service.key=='SaleCurrencyPayment'}">
                <c:set var="SaleCurrencyPayment" value= "1"/>
            </c:if>
            <c:if test="${service.key=='ConvertCurrencyPayment'}">
                <c:set var="ConvertCurrencyPayment" value= "1"/>
            </c:if>
            <c:if test="${service.key=='PaymentDocumentPreparation'}">
                <c:set var="PaymentDocumentPreparation" value= "1"/>
            </c:if>
            <c:if test="${service.key=='RurPayJurSB'}">
                <c:set var="RurPayJurSB" value= "1"/>
            </c:if>
	        <c:if test="${service.key=='AccountClosingClaim'}">
		        <c:set var="AccountClosingClaim" value="1"/>
	        </c:if>
        </c:forEach>
    <tr>
        <td>
           <br>
           <br>
        </td>
    </tr>
    <tr>
        <td>
            Перечень операций, разрешенных к проведению исполнению Представителем Клиента
        </td>
    </tr>
    <tr>
        <td><input type="Text" value="${empoweredPerson.fullName}" class="insertInput" style="width: 79%"></td>
    </tr>
    <tr>
        <td>
            <table class="textDoc" cellspacing="0" width="100%">
            <tr>
                <td align="center" valign="top" style="font-size:10pt" width="79%">(Ф.И.О Представителя Клиента)</td>
                <td width="21%">&nbsp;</td>
            </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td>
            <table cellspacing="0" class="textDoc docTableBorder" width="100%">
            <tr>
                <td class="docTdBorder" align="center" width="5%"><b>№ п/п</b></td>
                <td class="docTdBorder" align="center" width="47%"><b>&nbsp;Наименование операции&nbsp;</b></td>
                <td class="docTdBorder" align="center"><b>&nbsp;Разрешенные операции&nbsp;</b></td>
            </tr>
            <tr>
                <td colspan="3" class="docTdBorder textPadding" width="100%">Перечень банковских операций, плата за которые взимается в соответствии с Сборником тарифов Сбербанка России и за предоставление которых с использованием системы &ldquo;Электронная Сберкасса&rdquo; с Клиента взимается ежемесячная абонентская плата:</td>
            </tr>
            <tr>
                <td class="docTdBorder" align="center" valign="top">1</td>
                <td class="docTdBorder textPadding">Перевод (перечисление)  денежных средств, хранящихся на счете2 на другой действующий счет, в том числе открытый в другом филиале Банка (Операционном управлении Сбербанка России ОАО) или в другой кредитной организации</td>
                <c:choose>
                    <c:when test="${InternalPayment=='1' && RurPayment=='1'}">
                        <td class="docTdBorder" align="center">Разрешена</td>
                    </c:when>
                    <c:otherwise>
                        <td class="docTdBorder" align="center">Запрещена</td>
                    </c:otherwise>
                </c:choose>
            </tr>
            <tr>
                <td class="docTdBorder" align="center" valign="top">2</td>
                <td class="docTdBorder textPadding">Закрытие счета по вкладу с переводом (перечислением) денежных средств на другой действующий счет</td>
                <c:choose>
                    <c:when test="${AccountClosingClaim=='1'}">
                        <td class="docTdBorder" align="center">Разрешена</td>
                    </c:when>
                    <c:otherwise>
                        <td class="docTdBorder" align="center">Запрещена</td>
                    </c:otherwise>
                </c:choose>
            </tr>
            <tr>
                <td class="docTdBorder" align="center" valign="top">3</td>
                <td class="docTdBorder textPadding">Продажа Банком иностранной валюты в безналичном порядке за счет средств на счете по вкладу в рублях (с зачислением полученной суммы на действующий счет по вкладу Клиента в иностранной валюте в том же филиале Банка)</td>
                <c:choose>
                    <c:when test="${PurchaseCurrencyPayment=='1'}">
                        <td class="docTdBorder" align="center">Разрешена</td>
                    </c:when>
                    <c:otherwise>
                        <td class="docTdBorder" align="center">Запрещена</td>
                    </c:otherwise>
                </c:choose>
            </tr>
            <tr>
                <td class="docTdBorder" align="center" valign="top">4</td>
                <td class="docTdBorder textPadding">Покупка Банком иностранной валюты в безналичном порядке  со счета по вкладу Клиента в иностранной валюте за рубли с зачислением средств на счет по вкладу Клиента в рублях в том же филиале Банка</td>
                <c:choose>
                    <c:when test="${SaleCurrencyPayment=='1'}">
                        <td class="docTdBorder" align="center">Разрешена</td>
                    </c:when>
                    <c:otherwise>
                        <td class="docTdBorder" align="center">Запрещена</td>
                    </c:otherwise>
                </c:choose>
            </tr>
            <tr>
                <td class="docTdBorder" align="center" valign="top">5</td>
                <td class="docTdBorder textPadding">Покупка и продажа (конверсия) в безналичном порядке иностранной валюты одного вида за иностранную валюту другого вида
    (с зачислением полученной суммы на действующий счет Клиента в иностранной валюте в том же филиале Банка)
                </td>
                <c:choose>
                    <c:when test="${ConvertCurrencyPayment=='1'}">
                        <td class="docTdBorder" align="center">Разрешена</td>
                    </c:when>
                    <c:otherwise>
                        <td class="docTdBorder" align="center">Запрещена</td>
                    </c:otherwise>
                </c:choose>
            </tr>
            <tr>
                <td class="docTdBorder" align="center" valign="top" width="5%">6</td>
                <td class="docTdBorder textPadding" width="47%">Списание денежных средств со счета2 в рублях (в бюджет и государственные внебюджетные фонды, в пользу получателей платежей - юридических лиц или индивидуальных предпринимателей, как имеющих, так и не имеющих договорные отношения с Банком).</td>
                <c:choose>
                    <c:when test="${RurPayJurSB=='1'}">
                        <td class="docTdBorder" align="center">Разрешена</td>
                    </c:when>
                    <c:otherwise>
                        <td class="docTdBorder" align="center">Запрещена</td>
                    </c:otherwise>
                </c:choose>
            </tr>
            <tr>
                <td  colspan="3" class="docTdBorder textPadding">Перечень вспомогательных операций, включающих в себя операции, осуществляемые для получения услуг в Системе, и операции справочно-информационного характера:</td>
            </tr>
            <tr>
                <td class="docTdBorder" align="center" valign="top">1</td>
                <td class="docTdBorder textPadding">Выдача выписки по счету</td>
                <c:choose>
                    <c:when test="${Abstract=='1'}">
                        <td class="docTdBorder" align="center">Разрешена</td>
                    </c:when>
                    <c:otherwise>
                        <td class="docTdBorder" align="center">Запрещена</td>
                    </c:otherwise>
                </c:choose>
            </tr>
            <tr>
                <td class="docTdBorder" align="center" valign="top">2</td>
                <td class="docTdBorder textPadding">Информация о текущем размере остатка средств на счете</td>
                <c:choose>
                    <c:when test="${AccountAndCardInfo=='1'}">
                        <td class="docTdBorder" align="center">Разрешена</td>
                    </c:when>
                    <c:otherwise>
                        <td class="docTdBorder" align="center">Запрещена</td>
                    </c:otherwise>
                </c:choose>
            </tr>
            <tr>
                <td class="docTdBorder" align="center" valign="top">3</td>
                <td class="docTdBorder textPadding">Информация о максимальном размере суммы, которую можно снять со счета по вкладу Клиента без нарушения условий договора</td>
                <c:choose>
                    <c:when test="${AccountAndCardInfo=='1'}">
                        <td class="docTdBorder" align="center">Разрешена</td>
                    </c:when>
                    <c:otherwise>
                        <td class="docTdBorder" align="center">Запрещена</td>
                    </c:otherwise>
                </c:choose>
            </tr>
            <tr>
                <td class="docTdBorder" align="center" valign="top">4</td>
                <td class="docTdBorder textPadding">Подготовка платежного документа (платежного поручения, инкассового поручения, ф.№ПД-4, ф.№ ПД-4 СБ (налог))</td>
                <c:choose>
                    <c:when test="${PaymentDocumentPreparation=='1'}">
                        <td class="docTdBorder" align="center">Разрешена</td>
                    </c:when>
                    <c:otherwise>
                        <td class="docTdBorder" align="center">Запрещена</td>
                    </c:otherwise>
                </c:choose>
            </tr>
            </table>
        </td>
    </tr>
</c:forEach>


    <tr>
        <td>
            <table width="100%" cellpadding="0" cellspacing="0" class="textDoc">
            <tr>
                <td width="50%" align="center" style="padding-top:10mm"><b>от Банка:</b></td>
                <td align="center" style="padding-top:10mm"><b>Клиент:</b></td>
            </tr>
            <tr>
                <td colspan="2">
                    <table width="100%" cellpadding="0" cellspacing="0" class="textDoc">
                    <tr>
                        <td width="20%"><input type="Text" readonly="true" class="insertInput" style="width:100%"></td>
                        <td width="25%" style="padding-right:8;"><nobr>(<input value="${employee.surName} ${phiz:substring(employee.firstName,0,1)}.${phiz:substring(employee.patrName,0,1)}." type="Text" readonly="true" class="insertInput" style="width:96%">)</nobr></td>
                        <td width="20%" style="padding-left:8;"><input type="Text" readonly="true" class="insertInput" style="width:100%"></td>
                        <td width="25%"><nobr>(<input value="${person.surName} ${phiz:substring(person.firstName,0,1)}.${phiz:substring(person.patrName,0,1)}." type="Text" readonly="true" class="insertInput" style="width:95%">)</nobr></td>
                    </tr>
                    </table>
                </td>
            </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td><span class="tabSpan">&nbsp;</span><b>М.П.</b></td>
    </tr>
<!--<tr><td><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/></td></tr>-->
</tbody>
</table>

</td>
</tr>
</table>
