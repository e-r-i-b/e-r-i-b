<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz"%>
      <table cellpadding="0" cellspacing="0" width="180mm" style="margin-left:5mm;margin-right:5mm;margin-top:10mm;margin-bottom:7mm;table-layout:fixed;">
      <col style="width:180mm">
      <tr>
      <td valign="top">

      <table class="textDoc" cellpadding="0" cellspacing="0" style="vertical-align:top;">
      
      <%@include file="footer.jsp"%>

     <tbody>
      <!-- Шапка документа -->
      <tr>
          <td align="right">
              <b>Приложение 1</b><br><br>&nbsp;
              <span class="italic font10">к Договору о предоставлении<br>
                 услуг с использованием системы<br>
                 &ldquo;Электронная Сберкасса&rdquo;<br>
              </span>
              №<input type="Text" value='${person.agreementNumber}' readonly="true" class="insertInput" style="width:21%">от&nbsp;&ldquo;<input value='<bean:write name="agreementDate" format="dd"/>' type="Text" readonly="true" class="insertInput" style="width:4%">&rdquo;
               <input id='monthStr12' value='' type="Text" readonly="true" class="insertInput" style="width:13%">20<input value='<bean:write name="agreementDate" format="y"/>' type="Text" readonly="true" class="insertInput" style="width:3%">г.
               <script>
                  document.getElementById('monthStr12').value = monthToStringOnly('<bean:write name="agreementDate" format="dd.MM.yyyy"/>');
               </script>

          </td>
      </tr>

      <tr>
          <td align="center">
              <br><br>
              <b>ПЕРЕЧЕНЬ<br>
              номеров счетов (банковских карт) Клиента
              </b>
          </td>
      </tr>

      <tr>
          <td>
              <table cellspacing="0" class="textDoc docTableBorder font11" width="100%">
              <tr>
                  <td class="docTdBorder" align="center" width="10%"><b>п/н</b></td>
                  <td class="docTdBorder" align="center"><b>Номера счетов Клиента, по которым предоставляются Услуги,<br>
                                                  предусмотренные настоящим Договором
                                          </b>
                  </td>
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
                  <td class="docTdBorder" align="center"><b>Номера банковских карт Клиента,
                                    по которым предоставляются Услуги, предусмотренные настоящим Договором</b>
                  </td>
              </tr>
      <% lineNumber = 0;%>
                  <logic:iterate id="cardLink" name="PrintPersonForm" property="cardLinks">
		     <%lineNumber++;%>
                          <tr>
                              <td class="docTdBorder" align="center">&nbsp;<%=lineNumber%>&nbsp;</td>
                              <td class="docTdBorder">&nbsp;${cardLink.externalId}&nbsp;</td>
                          </tr>
                  </logic:iterate>
              </table>
          </td>
      </tr>
      <tr>
          <td><br>
              <table cellspacing="0" class="textDoc docTableBorder" width="100%">
              <tr>
                  <td class="docTdBorder textPadding" width="50%" valign="top">Номер(а) счета(ов) Клиента, с которого(ых) в безакцептном порядке Банком производится списание платы за Услуги, предусмотренные настоящим Договором</td>
                  <td class="docTdBorder" valign="top">
                      <table cellspacing="0" cellpadding="0" width="100%">
                      <tr>
                          <td>
                             <table cellspacing="0" cellpadding="0" width="100%" class="textDoc">

                             <logic:iterate id="entry" name="PrintPersonForm" property="accountLinks">
		                     <c:set var="accountLink" value="${entry.value}"/>
                             <c:if test="${(entry.paymentAbility == 'true')}">
                                 <tr>
                                    <td>&nbsp;${accountLink.number}&nbsp;</td>
                                 </tr>
                             </c:if>
                             </logic:iterate>
                             </table>
                         </td>
                      </tr>
                      </table>
                  </td>
              </tr>
              </table>
          </td>
      </tr>

      <tr>
          <td><br><b>Способ доставки Банком подтверждений об исполнении операций по счету Клиента:</b></td>
      </tr>

      <tr>
          <td><br>
              <c:set var="messageS" value="${person.messageService}"/>
              <table cellspacing="0" class="textDoc docTableBorder font11" width="100%">
              <tr>
                  <td class="docTdBorder" align="center" width="15%">
                  <input type="Checkbox"
                  <c:if test="${messageS == 'sms'}">
                     checked
                  </c:if>
                   style="border:0 solid;">
                  </td>
                  <td class="docTdBorder">&nbsp;SMS-сообщения</td>
              </tr>
              </table>
          </td>
      </tr>

      <tr>
          <td><br>
              <table cellspacing="0" class="textDoc docTableBorder" width="100%">
              <tr>
                  <td class="docTdBorder" align="center" width="15%">
                  <input type="Checkbox"
                  <c:if test="${messageS == 'email'}">
                     checked
                  </c:if>
                   style="border:0 solid;">
                  </td>
                  <td class="docTdBorder">&nbsp;E-mail</td>
              </tr>
              </table>
          </td>
      </tr>

      <tr>
          <td><br>
              <table cellspacing="0" class="textDoc docTableBorder" width="100%">
              <tr>
                  <td class="docTdBorder" align="center" width="15%">
                  <input type="Checkbox"
                  <c:if test="${not (messageS == 'sms') and not (messageS == 'email')}">
                     checked
                  </c:if>
                   style="border:0 solid;">
                  </td>
                  <td class="docTdBorder">&nbsp;От подтверждений отказываюсь</td>
              </tr>
              </table>
          </td>
      </tr>
      <tr>
	     <td style="padding-left:2mm;">
		     <table>
			     <tr>
				     <td class="textPadding" style="border-bottom:1px solid #000000; width:50mm">&nbsp;</td>
				 </tr>
		     </table>
	     </td>
     </tr>
      <tr>
          <td><br>
              <table cellspacing="0" class="textDoc docTableBorder" width="100%">
              <tr>
                  <td class="docTdBorder" align="center" width="15%">
                    <input type="Checkbox"
					  <c:if test="${simpleAuthChoice == 'smsp'}">
						 checked
					  </c:if>
					   style="border:0 solid;">
                  </td>
                  <td class="docTdBorder">&nbsp;Использовать одноразовый пароль, направляемый на мобильный телефон в виде SMS</td>
              </tr>
              </table>
          </td>
      </tr>
    <tr>
        <td>
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
        <td><span class="tabSpan">&nbsp;</span><b>М.П.</b></td>
    </tr>            
      </tbody>
      </table>

      </td>
      </tr>
      </table>

