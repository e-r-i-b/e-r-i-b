<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz"%>

<tiles:insert definition="personsContract9">
<tiles:put name="data" type="string">
    <c:set var="form" value="${PrintPersonForm}"/>
    <c:set var="empoweredPersons" value="${form.empoweredPersons}"/>
    <c:set var="empoweredPersonsAccounts" value="${form.empoweredPersonsAccounts}"/>
    <c:set var="empoweredPersonsServices" value="${form.empoweredPersonsServices}"/>
    <c:set var="department" value="${form.department}"/>
    <c:set var="employee" value="${phiz:getEmployeeInfo()}"/>
    <c:set var="document" value="${form.activeDocument}"/>

    <html:form action="/persons/print">
    <bean:define id="person" name="PrintPersonForm" property="activePerson"/>
    <bean:define id="currentDate" name="PrintPersonForm" property="currentDate"/>
    <c:set var="agreementDate" value="${(empty person.agreementDate) ? '' : person.agreementDate.time}"/>

    <tiles:importAttribute/>
    <c:set var="globalImagePath" value="${globalUrl}/images"/>
    <c:set var="imagePath" value="${skinUrl}/images"/>

    <table cellpadding="0" cellspacing="0" width="175mm" style="font-size:8pt;font-family:Arial;margin-left:12mm;margin-right:10mm;margin-top:5mm;margin-bottom:5mm;table-layout:fixed;">
        <col style="width:175mm">
        <tr>
        <td>
        <table style="width:175mm;height:100%;" cellpadding="0" cellspacing="0" class="textDoc font11">

    <%@include file="footer.jsp"%>

    <tbody>
    <!-- Пространство над шапкой -->
    <!--Логотип Сбера-->
    <tr>
        <td>
            <%@ include file="/WEB-INF/jsp-sbrf/sbrfPrintHeader.jsp" %>
        </td>
    </tr>

    <!--Шапка текста-->
    <tr>
        <td align="center"><b>Дополнительное соглашение<br>
            к &ldquo;Договору о предоставлении услуг с использованием системы<br>
            &quot;Электронная Сберкасса&quot;&rdquo;</b>
        </td>
    </tr>
    <tr>
	    <td>
		    <table align="center" class="textDoc font11">
			    <tr>
					<td><b>№</b><input value='<bean:write name="PrintPersonForm" property="addAgreementNumber"/>' type="Text" class="insertInput font11" style="width:170px"></td>
				    <td><b>от&nbsp;"</b></td>
				    <td width="15px"><input value='<bean:write name='agreementDate' format="dd"/>' type="Text" class="insertInput font11" style="width: 20px"></td>
				    <td><b>"</b></td>
				    <td><input id="monthStr1" value='' type="Text" class="insertInput font11" style="width: 100px"></td>
				    <td><b>20</b></td>
				    <td width="15px"><input value='<bean:write name='agreementDate' format="y"/>' type="Text" class="insertInput font11" style="width: 20px"></td>
				    <td><b>года</b></td>
			    </tr>
		    </table>
	    </td>
    </tr>
    <tr>
        <td>
            <table cellpadding="0" cellspacing="0" width="100%">
            <tr>
                <td width="70%"><nobr>г. <input value='${department.city}' type="Text" class="insertInput font11" style="width: 60%"></nobr></td>
                <td>&nbsp;</td>
                <td><nobr>&ldquo;<input value='<bean:write name='currentDate' format="dd"/>' type="Text" class="insertInput font11" style="width: 70%">&rdquo;</nobr></td>
                <td><input id='monthStr2' value="" type="Text" class="insertInput font11" style="width: 100px"></td>
	            <td>20</td>
	            <td><input value='<bean:write name='currentDate' format="y"/>' type="Text" class="insertInput font11" style="width: 20px">г.</td>
            </tr>
            </table>
        </td>
    </tr>
   <script type="text/javascript">
        document.getElementById('monthStr1').value = monthToStringOnly('<bean:write name="agreementDate" format="dd.MM.yyyy"/>');
        document.getElementById('monthStr2').value = monthToStringOnly('<bean:write name="currentDate" format="dd.MM.yyyy"/>');
   </script>

    <!-- Основной текст договора -->
    <tr>
        <td>
            <table width="100%" cellpadding="0" cellspacing="0" class="textDoc font11">
            <tr valign="bottom">
                <td colspan="2">
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Открытое акционерное общество «Сбербанк России»
                </td>
            </tr>
            <tr>
                <td>
                    <nobr>,&nbsp;именуемое&nbsp;в&nbsp;дальнейшем&nbsp;"Банк",&nbsp;в&nbsp;лице</nobr>
                </td>
                <td width="100%">
                    <input type="Text" readonly="true" class="insertInput font11" style="width:100%">
                </td>
            </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td><nobr><input value="${employee.surName} ${employee.firstName} ${employee.patrName}" type="Text" readonly="true" class="insertInput font11" style="width:99%">,</nobr></td>
    </tr>
    <tr>
        <td align="center" valign="top" style="font-size:8pt;">(фамилия, имя, отчество полностью и должность уполномоченного лица)</td>
    </tr>
    <%--<tr>--%>
        <%--<td>--%>
            <%--<table width="100%" cellpadding="0" cellspacing="0" class="textDoc font11">--%>
            <%--<tr>--%>
                <%--<td class="textDoc font11">--%>
                    <%--<nobr>действующего&nbsp;на&nbsp;основании</nobr>--%>
                <%--</td>--%>
                <%--<td width="100%" class="tdUnderlinedItalic">--%>
                    <%--<nobr>${representative.document}</nobr>--%>
                <%--</td>--%>
	            <%--<td>,</td>--%>
            <%--</tr>--%>
            <%--<tr>--%>
                <%--<td>&nbsp;</td>--%>
                <%--<td align="center" valign="top" style="font-size:8pt;">(документ, удостоверяющий полномочия)</td>--%>
            <%--</tr>--%>
            <%--</table>--%>
        <%--</td>--%>
    <%--</tr>--%>
    <tr>
        <td>
            <table width="100%" cellpadding="0" cellspacing="0" class="textDoc font11">
            <tr>
                <td>
                    <nobr>с&nbsp;одной&nbsp;стороны,&nbsp;и</nobr>
                </td>
                <td width="100%">
                    <nobr><input value='${person.fullName}' type="Text" readonly="true" class="insertInput font11" style="width:99%">,</nobr>
                </td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td align="center" valign="top" style="font-size:8pt;">(фамилия, имя и отчество полностью)</td>
            </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td class="textDoc font11">именуемый(ая) в дальнейшем "Клиент", с другой стороны, совместно именуемые "Стороны", заключили настоящее Дополнительное соглашение о нижеследующем.</td>
    </tr>
    <tr>
        <td class="textDoc font11">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1.Изложить содержание Приложения 1 (Приложения 2 ) (Приложения 5 ) к "Договору о предоставлении услуг с использованием системы "Электронная Сберкасса"</td>
	</tr>
    <tr>
	    <td>
	        <table width="100%"  class="textDoc font11">
		        <tr>
			        <td>№<input value='<bean:write name='person' property="agreementNumber"/>' type="Text" class="insertInput font11" style="width: 170px"></td>
			        <td>от&nbsp;</td>
			        <td>"<input value='<bean:write name='agreementDate' format="dd"/>' type="Text" class="insertInput font11" style="width: 20px">"</td>
			        <td><input id="monthStr3" value='' type="Text" class="insertInput font11" style="width: 100px"></td>
			        <td>20</td>
			        <td><input value='<bean:write name='agreementDate' format="y"/>' type="Text" class="insertInput font11" style="width: 20px"></td>
			        <td>года</td>
			        <td width="30%">(далее-Договор) в редакции,</td>
			    </tr>
		    </table>
	    </td>
    </tr>
    <tr><td> прилагаемой к настоящему Дополнительному соглашению.</td></tr>
    <script>
       document.getElementById('monthStr3').value = monthToStringOnly('<bean:write name='agreementDate' format="dd.MM.yyyy"/>');
    </script>
    <tr>
        <td class="textDoc font11">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2.Настоящее Дополнительное соглашение вступает в силу с момента его подписания обеими Сторонами, действует в течение срока действия ДОГОВОРА и является его неотъемлемой частью.</td>
    </tr>
    <tr>
        <td class="textDoc font11">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3.Настоящее Дополнительное соглашение составлено в двух экземплярах, имеющих равную силу, по одному экземпляру для каждой из Сторон.</td>
    </tr>
    <tr>
        <td>
            <table width="100%" cellpadding="0" cellspacing="0" class="textDoc font11">
            <tr>
                <td class="italic">
                    <b><nobr>Клиент:</nobr></b>
                </td>
                <td width="100%">
                    <input value='${person.fullName}' type="Text" readonly="true" class="insertInput font11" style="width:100%">
                </td>
            </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td>
            <table width="100%" cellpadding="0" cellspacing="0" class="textDoc font11">
            <tr>
                <td>
                    <nobr>адрес&nbsp;регистрации</nobr>
                </td>
				<script type="text/javascript">
					var str = '${person.registrationAddress}';
					var address = breakString(str, 60);
					document.write("<td class='tdUnderlinedItalic' style='width:75%'><nobr>"+address+"</nobr></td>");
					str = str.substring(address.length, str.length);
				</script>
	        </tr>
			<script type="text/javascript">
					 while (str.length > 0)
					 {
					    address = breakString(str, 75);
						document.write("<tr><td colspan='2' class='tdUnderlinedItalic' style='width:100%'><nobr>"+address+"</nobr></td></tr>");
						str = str.substring(address.length, str.length);
					 }
			</script>
            </table>
        </td>
    </tr>
    <tr>
        <td>
            <table width="100%" cellpadding="0" cellspacing="0" class="textDoc font11">
				<tr>
					<td>
						<nobr>адрес&nbsp;фактического&nbsp;проживания&nbsp;(для&nbsp;почтовых&nbsp;отправлений)</nobr>
					</td>
					<script type="text/javascript">
						var str = '${person.residenceAddress}';
						var address = breakString(str, 30);
						document.write("<td align='left' class='tdUnderlinedItalic'style='width:40%'><nobr>"+address+"</nobr></td>");
						str = str.substring(address.length, str.length);
					</script>
				</tr>
				<script type="text/javascript">
						 while (str.length > 0)
						 {
							address = breakString(str, 80);
							document.write("<tr><td colspan='2' class='tdUnderlinedItalic' style='width:100%'><nobr>"+address+"</nobr></td></tr>");
							str = str.substring(address.length, str.length);
						 }
				</script>
			</table>
        </td>
    </tr>
    <tr>
        <td>
            <table width="100%" class="textDoc font11" cellpadding="0" cellspacing="0">
            <tr valign="bottom">
                <td style="font-size:12pt;">
                    <nobr>адрес&nbsp;электронной&nbsp;почты&nbsp;(E-mail)</nobr>
                </td>
                <td width="60%">
                    <input value='${person.email}' type="Text" readonly="true" class="insertInput font11" style="width:100%">
                </td>
            </tr>
            </table>
        <td>
    </tr>
    <tr>
        <td>
            <table width="100%" class="textDoc font11" cellpadding="0" cellspacing="0">
            <tr valign="bottom">
                <td>
                    <nobr>телефон&nbsp;(дом.)</nobr>
                </td>
                <td width="30%">
                    <input value='${person.homePhone}' type="Text" readonly="true" class="insertInput font11" style="width:100%">
                </td>
	            <td>
                    <nobr>(раб.)</nobr>
                </td>
                <td width="30%">
                    <input value='${person.jobPhone}' type="Text" readonly="true" class="insertInput font11" style="width:100%">
                </td>
                <td>
                    <nobr>(моб.)</nobr>
                </td>
                <td width="30%">
                    <input value='${person.mobilePhone}' type="Text" readonly="true" class="insertInput font11" style="width:100%">
                </td>
            </tr>
            </table>
        <td>
    </tr>
    <tr>
        <td>
            <table width="100%" class="textDoc font11" cellpadding="0" cellspacing="0">
            <tr valign="bottom">
                <td>
                    <nobr>мобильный&nbsp;оператор</nobr>
                </td>
                <td width="30%">
                    <input value='${person.mobileOperator}' type="Text" readonly="true" class="insertInput font11" style="width:100%">
                </td>
	            <td>
                    <nobr>ИНН</nobr>
                </td>
                <td width="30%">
                    <input value='${person.inn}' type="Text" readonly="true" class="insertInput font11" style="width:100%">
                </td>
                <td>
                    <nobr>гражданство</nobr>
                </td>
                <td width="40%">
                    <input value='${person.citizenship}' type="Text" readonly="true" class="insertInput font11" style="width:100%">
                </td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td align="center" valign="top" style="font-size:8pt;">(при его наличии)</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
            </tr>
            </table>
        <td>
    </tr>
    <tr>
    <tr>
        <td>
            <table width="100%" cellpadding="0" cellspacing="0" class="textDoc font11">
            <tr>
                <td>
                    <nobr>вид&nbsp;документа,&nbsp;удостоверяющего&nbsp;личность,</nobr>
                </td>
                <td width="100%">
                    <c:if test="${(document.documentType == 'REGULAR_PASSPORT_RF')}">
                        <input value="Общегражданский паспорт РФ" type="Text" readonly="true" class="insertInput font11" style="width:100%">
                    </c:if>
                    <c:if test="${(document.documentType == 'MILITARY_IDCARD')}">
                        <input value="Удостоверение личности военнослужащего" readonly="true" class="insertInput font11" style="width:100%">
                    </c:if>
                    <c:if test="${(document.documentType == 'SEAMEN_PASSPORT')}">
                        <input value="Паспорт моряка" readonly="true" class="insertInput font11" style="width:100%">
                    </c:if>
                    <c:if test="${(document.documentType == 'RESIDENСЕ_PERMIT_RF')}">
                        <input value="Вид на жительство РФ" readonly="true" class="insertInput font11" style="width:100%">
                    </c:if>
                    <c:if test="${(document.documentType == 'FOREIGN_PASSPORT_RF')}">
                        <input value="Заграничный паспорт РФ" readonly="true" class="insertInput font11" style="width:100%">
                    </c:if>
                    <c:if test="${(document.documentType == 'OTHER')}">
                        <input value="" readonly="true" class="insertInput font11" style="width:100%">
                       ${document.documentType}
                    </c:if>
                </td>
            </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td>
            <table width="100%" class="textDoc font11" cellpadding="0" cellspacing="0">
            <tr valign="bottom">
                <td>
                    <nobr>серия&nbsp;(номер)</nobr>
                </td>
                <td width="45%">
                    <input value='${document.documentSeries} ${document.documentNumber}' type="Text" readonly="true" class="insertInput font11" style="width:100%">
                </td>
                <td>
                    <nobr>кем,&nbsp;где,&nbsp;когда&nbsp;выдан</nobr>
                </td>
                <td width="50%">
                    <input type="Text" readonly="true" class="insertInput font11" style="width:100%">
                </td>
            </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td><input value='${document.documentIssueBy}, <bean:write name="document" property="documentIssueDate.time" format="dd.MM.yyyy"/>' type="Text" readonly="true" class="insertInput font11" style="width:99%"></td>
    </tr>
    <tr>
        <td>
            <table width="100%" cellpadding="0" cellspacing="0" class="textDoc font11">
            <tr>
                <td>
                    <nobr>код&nbsp;подразделения&nbsp;(при&nbsp;наличии)</nobr>
                </td>
                <td width="100%">
                    <input value='${document.documentIssueByCode}' type="Text" readonly="true" class="insertInput font11" style="width:100%">
                </td>
            </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td>
            <table width="100%" cellpadding="0" cellspacing="0" class="textDoc font11">
            <tr>
                <td>
                    <nobr>дата&nbsp;и&nbsp;место&nbsp;рождения</nobr>
                </td>
                <td width="100%">
                    <input value='<bean:write name="PrintPersonForm" property="activePerson.birthDay" format="dd.MM.yyyy"/>, <bean:write name="person" property="birthPlace" format="dd.MM.yyyy"/>' type="Text" readonly="true" class="insertInput font11" style="width:100%">
                </td>
            </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td class="italic"><b>Банк:</b></td>
    </tr>
    <tr>
        <td>
            <table width="100%" class="textDoc font11" cellpadding="0" cellspacing="0">
            <tr valign="bottom">
                <td>
                    Филиал
                </td>
                <td width="50%">
                    <input value="${department.code.fields[branch]}" type="Text" readonly="true" class="insertInput font11" style="width:100%">
                </td>
                <td>
                    <nobr>структурное&nbsp;подразделение&nbsp;№</nobr>
                </td>
                <td width="50%">
                    <input value="${department.code.fields[office]}" type="Text" readonly="true" class="insertInput font11" style="width:100%">
                </td>
            </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td>
            <table width="100%" cellpadding="0" cellspacing="0" class="textDoc font11">
				<tr>
					<td>
						<nobr>почтовый&nbsp;адрес</nobr>
					</td>
					<script type="text/javascript">
						var str = '${department.address}';
						var address = breakString(str, 60);
						document.write("<td class='tdUnderlinedItalic' style='width:85%'><nobr>"+address+"</nobr></td>");
						str = str.substring(address.length, str.length);
					</script>
				</tr>
				<script type="text/javascript">
						 while (str.length > 0)
						 {
							 address = breakString(str, 70);
							document.write("<tr><td colspan='2' class='tdUnderlinedItalic' style='width:100%'><nobr>"+address+"</nobr></td></tr>");
							str = str.substring(address.length, str.length);
						 }
				</script>
            </table>
        </td>
    </tr>
    <tr>
        <td>
            <table width="100%" cellpadding="0" cellspacing="0" class="textDoc font11">
				<tr>
					<td>
						местонахождение
					</td>
					<script type="text/javascript">
						var str = '${department.location}';
						var address = breakString(str,60);
						document.write("<td class='tdUnderlinedItalic' style='width:85%'><nobr>"+address+"</nobr></td>");
						str = str.substring(address.length, str.length);
					</script>
				</tr>
				<script type="text/javascript">
					 while (str.length > 0)
					 {
						 address = breakString(str, 70);
						 document.write("<tr><td colspan='2' class='tdUnderlinedItalic' style='width:100%'><nobr>"+address+"</nobr></td></tr>");
						 str = str.substring(address.length, str.length);
					 }
				</script>
            </table>
        </td>
    </tr>
    <tr>
        <td>
            <table width="100%" cellpadding="0" cellspacing="0" class="textDoc font11">
            <tr>
                <td>
                    телефон
                </td>
                <td width="100%" align="left">
                    <input value="${department.telephone}" type="Text" readonly="true" class="insertInput font11" style="width:100%">
                </td>
            </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td>
            <table width="100%" cellpadding="0" cellspacing="0" class="textDoc font11">
            <tr>
                <td width="50%" align="center"><b>от Банка:</b></td>
                <td align="center"><b>Клиент:</b></td>
            </tr>
            <tr>
                <td style="padding-right:8;"><input value="${employee.surName} ${employee.firstName}" type="Text" readonly="true" class="insertInput font11" style="width:100%"></td>
                <td style="padding-left:8;"><input value="${person.surName} ${person.firstName}" type="Text" readonly="true" class="insertInput font11" style="width:100%"></td>
            </tr>
            <tr>
                <td style="padding-right:8;"><input value="${employee.patrName}" type="Text" readonly="true" class="insertInput font11" style="width:100%"></td>
                <td style="padding-left:8;"><input value="${person.patrName}"  type="Text" readonly="true" class="insertInput font11" style="width:100%"></td>
            </tr>
            <tr>
                <td colspan="2">
                    <table width="100%" cellpadding="0" cellspacing="0" class="textDoc font11">
                    <tr>
                        <td width="20%"><input type="Text" readonly="true" class="insertInput font11" style="width:100%"></td>
                        <td width="25%" style="padding-right:8;"><nobr>(<input value="${employee.surName} ${phiz:substring(employee.firstName,0,1)}.${phiz:substring(employee.patrName,0,1)}." type="Text" readonly="true" class="insertInput font11" style="width:96%">)</nobr></td>
                        <td width="20%" style="padding-left:8;"><input type="Text" readonly="true" class="insertInput font11" style="width:100%"></td>
                        <td width="25%"><nobr>(<input value="${person.surName} ${phiz:substring(person.firstName,0,1)}.${phiz:substring(person.patrName,0,1)}." type="Text" readonly="true" class="insertInput font11" style="width:95%">)</nobr></td>
                    </tr>
                    </table>
                </td>
            </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>М.П.</b></td>
    </tr>
    </tbody>
    </table>
    </td>
    </tr>
    </table>

    <br style="page-break-after:always;">
    <!--------------------------------- ПРИЛОЖЕНИЕ №8_1 ---------------------------------------->
    <%@ include file="contract8_pr1.jsp"%>

    <br style="page-break-after:always;">
    <!--------------------------------- ПРИЛОЖЕНИЕ №8_2 ---------------------------------------->
    <%@ include file="contract8_pr2.jsp"%>

    </html:form>
</tiles:put>
</tiles:insert>
