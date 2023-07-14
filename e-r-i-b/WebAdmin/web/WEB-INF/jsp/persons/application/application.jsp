<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<style type="text/css">
.tdRLB {border-right:1px solid black; border-left:1px solid black; border-bottom:1px solid black;}
.tdRL { border-right:1px solid black; border-left:1px solid black;}
.tdLB { border-bottom:1px solid black; border-left:1px solid black;}
.tdB { border-bottom:1px solid black;}
.tdL { border-left:1px solid black;}
.tdR { border-right:1px solid black;}
.tdTR { border-top:1px solid black; border-right:1px solid black;}
.tdAll {border:1px solid black}
</style>

<tiles:insert definition="personsContract8">
<tiles:put name="data" type="string">

    <tiles:importAttribute/>
    <c:set var="globalImagePath" value="${globalUrl}/images"/>
    <c:set var="imagePath" value="${skinUrl}/images"/>

<c:set var="form" value="${PrintPersonForm}"/>
<script type="text/javascript">
function getStringToPrint(text, size)
{
    var charCodeArus = 192;    // код заглавной русской буквы А
    var charCodeJarus = 223;   // код русской Я
    var charCodeA = 65;        // код английской A
    var charCodeZ = 90;        // код янглийской Z
    var coefficient = 1.3;    //  коэффициент, уменьшающий допустимую длину строки, если буква заглавная (подобран экспериментальным методом)

    var textToPrint = text == null || text == '' ? '&nbsp;' : '' ;
    for (var i = 0; i < text.length && size > 0; i++)
    {
        if ((text.charCodeAt(i) >= charCodeArus && text.charCodeAt(i) <= charCodeJarus) || (text.charCodeAt(i) >= charCodeA && text.charCodeAt(i) <= charCodeZ))
             size -= coefficient;
        else
            size--;

        textToPrint += text.charAt(i);
    }
    return textToPrint;
}

function printFirstString(str, firstSize)
{
    var text = getStringToPrint(str, firstSize);
    document.write("<td class='tdUnderlinedItalic' width='100%'>" + text + "</td>");
    str = str.substring(text.length, str.length);
    return str;
}

function printOtherStrings(str)
{
    var text = '';
    while(str.length > 0)
    {
        text = getStringToPrint(str, 80);
        document.write("</tr><tr><td colspan='2' class='tdUnderlinedItalic' width='100%'>" + text + "</td>");
        str = str.substring(text.length, str.length);
    }
}

function printAddress(str, firstSize)
{
    str = printFirstString(str, firstSize);
    printOtherStrings(str);
}

function printEmail(str, firstSize, homeTelephone, jobTelephone)
{
   if (homeTelephone == null || homeTelephone == "")
        homeTelephone = '&nbsp;';

   if (jobTelephone == null || jobTelephone == "")
        jobTelephone = '&nbsp;';

   var oneString = str + "телефон&nbsp;(дом.)" +  homeTelephone + "(раб.)"+ jobTelephone;
   if (oneString.length <= 80)
   {
      document.write( "<td class='tdUnderlinedItalic' width='30%'>" + str + "</td>"
                    + "<td class='textDoc'><nobr>телефон&nbsp;(дом.)</nobr></td>"
                    + "<td class='tdUnderlinedItalic' width='50%'>" + homeTelephone + "</td>"
                    + "<td class='textDoc'><nobr>(раб.)</nobr></td>"
                    + "<td class='tdUnderlinedItalic' width='50%'>" + jobTelephone + "</td>"
               );
       return;
   }

   str = printFirstString(str, firstSize);
   printOtherStrings(str);

  document.write("</tr><tr><table  class='textDoc' style='width:100%;padding:0px;margin:0px;' cellpadding='0' cellspacing='0' border='0'><tr><td class='textDoc'><nobr>телефон&nbsp;(дом.)</nobr></td>"
                    + "<td class='tdUnderlinedItalic' width='50%'>" + homeTelephone + "</td>"
                    + "<td class='textDoc'><nobr>(раб.)</nobr></td>"
                    + "<td class='tdUnderlinedItalic' width='50%'>" + jobTelephone + "</td></tr></table>"
  );

}

    function printPhone( homeTelephone, jobTelephone)
    {
        if (homeTelephone == null || homeTelephone == "")
            homeTelephone = '&nbsp;';

        if (jobTelephone == null || jobTelephone == "")
            jobTelephone = '&nbsp;';

      document.write("<td class='textDoc'><nobr>телефон&nbsp;(дом.)</nobr></td>"
                    + "<td class='tdUnderlinedItalic' width='50%'>" + homeTelephone + "</td>"
                    + "<td class='textDoc'><nobr>(раб.)</nobr></td>"
                    + "<td class='tdUnderlinedItalic' width='50%'>" + jobTelephone + "</td>"
               );
    }
</script>

    <c:set var="empoweredPersons" value="${form.empoweredPersons}"/>
    <c:set var="empoweredPersonsAccounts" value="${form.empoweredPersonsAccounts}"/>
    <c:set var="empoweredPersonsServices" value="${form.empoweredPersonsServices}"/>
    <c:set var="activeEmpoweredsDocument" value="${form.activeEmpoweredsDocument}"/>
    <c:set var="activeEmpoweredsNotResidentDocument" value="${form.activeEmpoweredsNotResidentDocument}"/>
    <c:set var="empoweredsMigratoryCard" value="${form.empoweredsMigratoryCard}"/>
    <c:set var="department" value="${form.department}"/>
    <c:set var="employee" value="${phiz:getEmployeeInfo()}"/>

    <html:form action="/persons/print">
    <bean:define id="person" name="PrintPersonForm" property="activePerson"/>
    <bean:define id="currentDate" name="PrintPersonForm" property="currentDate"/>
    <c:set var="agreementDate" value="${(empty person.agreementDate) ? '' : person.agreementDate.time}"/>
    <c:if test="${person.status=='A'}">
        <c:set var="agreementDate" value="${phiz:currentDate().time}"/>
    </c:if>
    <c:set var="weekOperationTimeBegin" value="${empty department.weekOperationTimeBegin ? '' : department.weekOperationTimeBegin}"/>
    <c:set var="weekOperationTimeEnd" value="${empty department.weekOperationTimeEnd ? '' : department.weekOperationTimeEnd}"/>
    <c:set var="fridayOperationTimeBegin" value="${empty department.fridayOperationTimeBegin ? '' : department.fridayOperationTimeBegin}"/>
    <c:set var="fridayOperationTimeEnd" value="${empty department.fridayOperationTimeEnd ? '' : department.fridayOperationTimeEnd}"/>
    <c:set var="document" value="${form.activeDocument}"/>
    <c:set var="notResidentDocument" value="${form.activeNotResidentDocument}"/>
    <c:set var="migratoryCard" value="${form.migratoryCard}"/>
    <c:set var="simpleAuthChoice" value="${form.simpleAuthChoice}"/>

      <!--------------------------------- ЗАЯВЛЕНИЕ НА ПОДКЛЮЧЕНИЕ К УСЛУГЕ «Сбербанк ОнЛ@йн» ----------------------------------------->
    <table cellpadding="0" cellspacing="0" width="180mm" valign="top" style="margin-left:12mm;margin-right:10mm;margin-top:10mm;margin-bottom:5mm;">
    <col style="width:180mm">
    <tr>
    <td>
    <table style="width:100%;height:100%; vertical-align:top" cellpadding="0" cellspacing="0" class="textDoc">
     <tbody>
    <!-- Пространство над шапкой -->
    <tr>
        <td align="right">Код 012180006/3</td>
    </tr>

    <!-- Логотип Сбера-->
    <tr>
        <td>
            <%@ include file="/WEB-INF/jsp-sbrf/sbrfPrintHeader.jsp" %>
        </td>
    </tr>

    <tr>
        <td align="center">
            <bean:message bundle="commonBundle" key="label.declaration.service.start"/>&nbsp;<input value='<bean:write name='person' property="agreementNumber"/>' type="Text" readonly="true" class="insertInput" style="width:21%"></b>
        </td>
    </tr>
    <tr>
        <td style="padding-bottom:6mm">
            <table cellpadding="0" cellspacing="0" width="100%" class="textDoc">
            <tr>
                <td width="70%"><nobr>&nbsp;<input value='${department.city}' type="Text" class="insertInput" style="width: 60%"></nobr></td>
                <td><nobr>&ldquo;<input value='<bean:write name='agreementDate' format="dd"/>' type="Text" class="insertInput" style="width: 70%">&rdquo;</nobr></td>
                <td><input id='monthStr' value='' type="Text" class="insertInput" style="width: 100px"></td>
	            <td>20</td>
	            <td><input value='<bean:write name='agreementDate' format="y"/>' type="Text" class="insertInput" style="width: 20px"></td>
            </tr>
	            <tr>
		           <td colspan="5" style="padding-left:5mm; font-size:8pt">(место подачи заявления)</td>
	            </tr>
            </table>
        </td>
    </tr>
    <script>
        document.getElementById('monthStr').value = monthToStringOnly('<bean:write name='agreementDate' format="dd.MM.yyyy"/>');
    </script>
    <tr>
        <td>
            <table width="100%" cellpadding="0" cellspacing="0" class="textDoc">
            <tr>
                <td><nobr>Я,</nobr></td>
                <script type="text/javascript">
                    printAddress("${phiz:escapeForJS(person.fullName, true)}", 62);
                </script>
                <td><nobr>(далее Клиент),</nobr></td>
            </tr>
            <tr>
                <td colspan="3" width="100%" style="font-size:8pt; text-align:center">(фамилия, имя и отчество)</td>
            </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td>
            <table width="100%" cellpadding="0" cellspacing="0" class="textDoc">
            <tr>
                <td>
                    <nobr>вид&nbsp;документа,&nbsp;удостоверяющего&nbsp;личность,</nobr>
                </td>
                      <c:if test="${(document.documentType == 'OTHER')}">
                        <script type="text/javascript">
                            printAddress("${phiz:escapeForJS(document.documentName, true)}", 75);
                        </script>
                    </c:if>
                <td width="100%">
                    <c:if test="${(document.documentType == 'REGULAR_PASSPORT_RF')}">
                        <input value="Общегражданский паспорт РФ" type="Text" readonly="true" class="insertInput" style="width:100%">
                    </c:if>
                    <c:if test="${(document.documentType == 'MILITARY_IDCARD')}">
                        <input value="Удостоверение личности военнослужащего" readonly="true" class="insertInput" style="width:100%">
                    </c:if>
                    <c:if test="${(document.documentType == 'SEAMEN_PASSPORT')}">
                        <input value="Паспорт моряка" readonly="true" class="insertInput" style="width:100%">
                    </c:if>
                    <c:if test="${(document.documentType == 'RESIDENСЕ_PERMIT_RF')}">
                        <input value="Вид на жительство РФ" readonly="true" class="insertInput" style="width:100%">
                    </c:if>
                    <c:if test="${(document.documentType == 'FOREIGN_PASSPORT_RF')}">
                        <input value="Заграничный паспорт РФ" readonly="true" class="insertInput" style="width:100%">
                    </c:if>
                </td>


            </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td>
            <table width="100%" class="textDoc" cellpadding="0" cellspacing="0">
				<tr valign="bottom">
					<td>
						<nobr>серия&nbsp;(номер)</nobr>
					</td>
					<td width="45%">
						<input value='${document.documentSeries} ${document.documentNumber}' type="Text" readonly="true" class="insertInput" style="width:100%">
					</td>
					<td>
						<nobr>кем,&nbsp;где,&nbsp;когда&nbsp;выдан</nobr>
					</td>
					<td width="50%">
						<input type="Text" readonly="true" class="insertInput" style="width:100%">
					</td>
				</tr>
				<tr>
					<td class="tdUnderlinedItalic" colspan="4" style="padding-top:5; border-left:3px solid #ffffff; border-right:3px solid #ffffff">
						<c:if test="${not empty document.documentIssueBy}">${document.documentIssueBy}</c:if>
						<c:if test="${not empty document.documentIssueDate}">, <bean:write name="document" property="documentIssueDate.time" format="dd.MM.yyyy"/></c:if>
					</td>
				</tr>
            </table>
        </td>
    </tr>
    <tr>
        <td>
            <table width="100%" cellpadding="0" cellspacing="0" class="textDoc">
            <tr>
                <td>
                    <nobr>код&nbsp;подразделения&nbsp;(при&nbsp;наличии)</nobr>
                </td>
                <td width="100%">
                    <input value='${document.documentIssueByCode}' type="Text" readonly="true" class="insertInput" style="width:100%">
                </td>
            </tr>
            </table>
        </td>
    </tr>
    <tr>
	    <td>
		    <table width="100%" cellpadding="0" cellspacing="0" class="textDoc">
			    <tr>
					<td colspan="2" style="text-align:justify;">
						<nobr>вид и реквизиты документа, подтверждающего право на пребывание (проживание) в</nobr>
					</td>
				</tr>
			    <tr>
				    <td><nobr>Российской Федерации*</nobr></td>
					<td class="tdUnderlinedItalic" style="padding-top:5; border-left:3px solid #ffffff; border-right:3px solid #ffffff" width="75%">&nbsp;
						<c:if test="${not empty notResidentDocument}">
							<script type="text/javascript">
								var notResidentDocument;
								<c:if test="${(notResidentDocument.documentType == 'IMMIGRANT_REGISTRATION')}">
									notResidentDocument = "Свидетельство о регистрации ходатайства иммигранта о признании его беженцем";
								</c:if>
								<c:if test="${(notResidentDocument.documentType == 'RESIDENTIAL_PERMIT_RF')}">
									notResidentDocument = "Вид на жительство РФ";
								</c:if>
								<c:if test="${(notResidentDocument.documentType == 'REFUGEE_IDENTITY')}">
									notResidentDocument = "Удостоверение беженца в РФ";
								</c:if>
	                            <c:if test="${not empty notResidentDocument.documentSeries}">notResidentDocument +=", ${notResidentDocument.documentSeries}";</c:if>
								<c:if test="${not empty notResidentDocument.documentNumber}">notResidentDocument +=" ${notResidentDocument.documentNumber}";</c:if>

								<c:if test="${not empty notResidentDocument.documentIssueBy || not empty notResidentDocument.documentIssueDate}">notResidentDocument +=", выдан: ";</c:if>
								<c:if test="${not empty notResidentDocument.documentIssueBy}">notResidentDocument +="${notResidentDocument.documentIssueBy}";</c:if>
								<c:if test="${not empty notResidentDocument.documentIssueDate}">notResidentDocument +=' <bean:write name="notResidentDocument" property="documentIssueDate.time" format="dd.MM.yyyy"/>';</c:if>

								var str = breakString(notResidentDocument, 60);
								document.write("<nobr>"+str+"</nobr>");
								notResidentDocument = notResidentDocument.substring(str.length, notResidentDocument.length);

							</script>
						</c:if>
					</td>
			    </tr>
			    <c:if test="${not empty notResidentDocument}">
				<script type="text/javascript">
					 while (notResidentDocument.length > 0)
					 {
						str = breakString(notResidentDocument, 80);
						document.write("<tr><td colspan='2' class='tdUnderlinedItalic' style='width:100%;padding-top:5; border-left:3px solid #ffffff; border-right:3px solid #ffffff'><nobr>"+str+"</nobr></td></tr>");
						notResidentDocument = notResidentDocument.substring(str.length, notResidentDocument.length);
					 }
				</script>
			    </c:if>
		    </table>
	    </td>
    </tr>
    <tr>
	    <td>
		    <table width="100%" cellpadding="0" cellspacing="0" class="textDoc">
			    <tr>
				    <td style="padding-top:10;"><nobr>данные миграционной карты*</nobr></td>
				    <td class="tdUnderlinedItalic" style="padding-top:5; border-left:3px solid #ffffff; border-right:3px solid #ffffff" width="85%">&nbsp;
					    <c:if test="${not empty migratoryCard}">
							<script type="text/javascript">
								<c:if test="${not empty migratoryCard.documentNumber}">var migratoryCard =" ${migratoryCard.documentNumber}";</c:if>
								<c:if test="${not empty migratoryCard.documentIssueDate}">migratoryCard +=', c <bean:write name="migratoryCard" property="documentIssueDate.time" format="dd.MM.yyyy"/>';</c:if>
								<c:if test="${not empty migratoryCard.documentTimeUpDate}">migratoryCard +=' по <bean:write name="migratoryCard" property="documentTimeUpDate.time" format="dd.MM.yyyy"/>';</c:if>

								var str = breakString(migratoryCard, 45);
								document.write("<nobr>"+str+"</nobr>");
								migratoryCard = migratoryCard.substring(str.length, str.length);
							</script>
						</c:if>
				    </td>
			    </tr>
				<c:if test="${not empty migratoryCard}">
				<script type="text/javascript">
					 while (migratoryCard.length > 0)
					 {
						str = breakString(migratoryCard, 45);
						document.write("<tr><td colspan='2' class='tdUnderlinedItalic' style='width:100%'><nobr>"+str+"</nobr></td></tr>");
						migratoryCard = migratoryCard.substring(str.length, migratoryCard.length);
					 }
				</script>
			    </c:if>
			    <tr>
				    <td colspan="2" style="font-size:8pt; padding-right:40mm; text-align:right">(номер, дата начала и окончания срока пребывания)</td>
			    </tr>
		    </table>
	    </td>
    </tr>
    <tr>
        <td>
            <table width="100%" cellpadding="0" cellspacing="0" class="textDoc">
				<tr>
					<td>
						<nobr>дата&nbsp;и&nbsp;место&nbsp;рождения</nobr>
					</td>
                    <fmt:formatDate var="birthDay" pattern="dd.MM.yyyy" value="${person.birthDay.time}"/>
                    <c:if test="${not empty person.birthPlace}">
                        <c:set var="birthDay" value="${birthDay}, ${person.birthPlace}"/>
                    </c:if>
                    <script type="text/javascript">
                        printAddress("${phiz:escapeForJS(birthDay, true)}", 80);
                    </script>
				</tr>
            </table>
        </td>
    </tr>
    <tr>
        <td>
            <table width="100%" cellpadding="0" cellspacing="0" class="textDoc">
				<tr>
					<td>
						<nobr>адрес&nbsp;регистрации</nobr>
					</td>
                    <script type="text/javascript">

                             printAddress("${phiz:escapeForJS(person.registrationAddress, true)}", 75);

                     </script>
			   </tr>

            </table>
        </td>
    </tr>
    <tr>
        <td>
            <table width="100%" cellpadding="0" cellspacing="0" class="textDoc">
            <tr>
                <td>
                    <nobr>адрес&nbsp;фактического&nbsp;проживания&nbsp;(для&nbsp;почтовых&nbsp;отправлений)</nobr>
                </td>
					<script type="text/javascript">
                        printAddress("${phiz:escapeForJS(person.residenceAddress, true)}", 25);
					</script>
			</tr>
            </table>
        </td>
    </tr>
    <tr>
        <td>
            <table width="100%" class="textDoc" cellpadding="0" cellspacing="0">
				<tr valign="bottom">
					<td>
						<nobr>ИНН</nobr>
					</td>
					<td width="40%">
						<input value='${person.inn}' type="Text" readonly="true" class="insertInput" style="width:100%">
					</td>
					<td>
						<nobr>гражданство</nobr>
					</td>
					<td width="60%">
						<input value='${person.citizenship}' type="Text" readonly="true" class="insertInput" style="width:100%">
					</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td align="center" valign="top" style="font-size:8pt;">(при его наличии)</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
            </table>
        </td>
    </tr>
    <tr>
        <td>
            <table class="textDoc" style="width:100%;" cellpadding="0" cellspacing="0" border='0'>
				<tr valign="bottom">
                        <script type="text/javascript">
                                printPhone( "${phiz:escapeForJS(person.homePhone, true)}", "${phiz:escapeForJS(person.jobPhone, true)}");
                        </script>


				</tr>
            </table>
        </td>
    </tr>
    <tr>
	    <td class="textDoc" style="font-size:9pt; padding:0mm 9mm;">
		    <nobr>* Для физических лиц-нерезидентов и лиц без гражданства</nobr>
	    </td>
    </tr>
    <tr>
        <td style=" padding-top:4mm;"><b><bean:message bundle="commonBundle" key="text.ask.service.start"/></b></td>
    </tr>
    <tr>
        <td style="padding-top:2mm;">
             <i><bean:message bundle="commonBundle" key="text.ask.payments.depo.operations"/> </i>
        </td>
    </tr>
        <tr>
        <td>
            <table width="100%" cellpadding="0" cellspacing="0" class="textDoc">
                <tr>
                    <td><nobr>№</nobr></td>
                    <td class="tdUnderlinedItalic" style="width:100%"><nobr>&nbsp;&nbsp;</nobr></td>
                    <td><nobr>&nbsp;.&nbsp;</nobr>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
    <tr>
       <td style="padding-top:5mm;">
          <table cellspacing="0" class="textDoc docTableBorder font11" width="100%">
              <tr>
                  <td class="docTdBorder" align="center" width="10%"><b>№</b></td>
                   <td class="docTdBorder" align="center"><b>
                  <bean:message bundle="commonBundle" key="label.accountNumbers.deposits"/></b> </td>
              </tr>
      <% int lineNumber = 0;%>
      <logic:iterate id="accountLink" name="PrintPersonForm" property="accountLinks">
		     <%lineNumber++;%>
              <tr>
                  <td class="docTdBorder" align="center">&nbsp;<%=lineNumber%>&nbsp;</td>
                  <td class="docTdBorder">&nbsp;${accountLink.account.number}&nbsp;</td>
              </tr>
      </logic:iterate>
              </table>
	          <table cellspacing="0" class="textDoc docTableBorder" style="border-top:0px" width="100%">
                  <tr>
                      <td class="docTdBorder" align="center" width="10%">&nbsp;</td>
                      <td class="docTdBorder" align="center"><b><bean:message bundle="commonBundle" key="label.accountNumbers.depo"/></b>
                      </td>
                  </tr>
              </table>
          </td>
      </tr>
      <tr>
         <td style="padding-top:5mm">
            <span style="padding-left:10mm;"><bean:message bundle="commonBundle" key="text.service.providing"/></span>
         </td>
      </tr>
        <tr>
            <td style="padding-top:5mm">
                <span style="padding-left:10mm;"><bean:message bundle="commonBundle" key="text.client.agreed"/> </span>
            </td>
        </tr>
        <tr>
            <td><br><br>
                <table width="100%" cellpadding="0" cellspacing="0" class="textDoc">
                <tr>
                        <td width="50%" align="center"><b>от Банка:</b></td>
                        <td align="center"><b>Клиент:</b></td>
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
            <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>М.П.</b></td>
        </tr>
     <tr><td style="padding-top:5mm">&nbsp;</td></tr>
    </tbody>
    </table>

    </td>
    </tr>
    </table>
 </html:form>
</tiles:put>
</tiles:insert>
