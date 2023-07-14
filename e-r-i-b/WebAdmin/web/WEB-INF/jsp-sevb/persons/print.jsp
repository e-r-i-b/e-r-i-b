<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>

<tiles:insert definition="personsPrint">
<tiles:put name="data" type="string">
    <body onLoad="showMessage();" Language="JavaScript">
    <html:form action="/persons/print">
      <bean:define id="person" name="PrintPersonForm" property="activePerson"/>
      <c:set var="document" value="${PrintPersonForm.activeDocument}"/>
      <table cellpadding="0" cellspacing="0" width="172mm" style="margin-left:15mm;margin-right:12mm;margin-top:10mm;margin-bottom:15mm;table-layout:fixed;">
      <col style="width:172mm">
      <tr>
      <td>
      <table style="width:172mm;height:100%;" cellpadding="0" cellspacing="0" class="textDoc">
      <tr>
          <td align="center">
              <c:choose>
                  <c:when test="${empty PrintPersonForm.principalPerson}">
                     <b>������ �������</b>
                  </c:when>
                  <c:otherwise>
                      <b>������ ������������� �������. ������ ${PrintPersonForm.principalPerson.surName} ${PrintPersonForm.principalPerson.firstName} ${PrintPersonForm.principalPerson.patrName}</b>
                  </c:otherwise>
              </c:choose>
          </td>
      </tr>
      <tr>
          <td>
              <br>
              <br>
          </td>
      </tr>
      <tr>
          <td align="center">
              <b>����� ��������</b>
          </td>
      </tr>
      <tr>
          <td>
              <br>
          </td>
      </tr>
      <tr>
          <td>
              <table cellspacing="0" class="textDoc docTableBorder" width="100%">
                  <tr>
                      <td class="docTdBorder textPadding" width="50%">
                          �������������
                      </td>
                      <td class="docTdBorder textPadding" width="50%">
                          &nbsp;${person.clientId}
                      </td>
                  </tr>
                  <tr>
                      <td class="docTdBorder textPadding">
                          �������
                      </td>
                      <td class="docTdBorder textPadding">
                          &nbsp;${person.surName}
                      </td>
                  </tr>
                  <tr>
                      <td class="docTdBorder textPadding">
                          ���
                      </td>
                      <td class="docTdBorder textPadding">
                          &nbsp;${person.firstName}
                      </td>
                  </tr>
                  <tr>
                      <td class="docTdBorder textPadding">
                          ��������
                      </td>
                      <td class="docTdBorder textPadding">
                          &nbsp;${person.patrName}
                      </td>
                  </tr>
                  <tr>
                      <td class="docTdBorder textPadding">
                          ���
                      </td>
                      <td class="docTdBorder textPadding">
                          <c:choose>
                              <c:when test="${(person.gender == 'M')}">
                                 &nbsp;�������
                              </c:when>
                              <c:otherwise>
                                 &nbsp;�������
                              </c:otherwise>
                          </c:choose>
                      </td>
                  </tr>
                  <tr>
                      <td class="docTdBorder textPadding">
                          ���� ��������
                      </td>
                      <td class="docTdBorder textPadding">
                          &nbsp;<bean:write name="person" property="birthDay" format="dd.MM.yyyy"/>
                      </td>
                  </tr>
                  <tr>
                      <td class="docTdBorder textPadding">
                          ����� ��������
                      </td>
                      <td class="docTdBorder textPadding">
                           &nbsp;${person.birthPlace}
                      </td>
                  </tr>
                  <tr>
                      <td class="docTdBorder textPadding">
                          �����������
                      </td>
                      <td class="docTdBorder textPadding">
                          &nbsp;${person.citizenship}
                      </td>
                  </tr>
                  <tr>
                      <td class="docTdBorder textPadding">
                          ���
                      </td>
                      <td class="docTdBorder textPadding">
                          &nbsp;${person.inn}
                      </td>
                  </tr>
              </table>
          </td>
      </tr>
      <tr>
          <td>
              <br>
              <br>
          </td>
      </tr>
      <tr>
          <td align="center">
              <b>�������� �������������� ��������</b>
          </td>
      </tr>
      <tr>
          <td>
              <br>
          </td>
      </tr>
      <tr>
          <td>
              <table cellspacing="0" class="textDoc docTableBorder" width="100%">
                  <c:choose>
                      <c:when test="${(document.documentType == 'OTHER')}">
                          <tr>
                              <td class="docTdBorder textPadding" width="50%">
                                  ������������ ���������
                              </td>
                              <td class="docTdBorder textPadding">
                                  <c:choose>
                                      <c:when test="${(document.documentType == 'OTHER')}">
                                         &nbsp;${document.documentName}
                                      </c:when>
                                      <c:otherwise>
                                         &nbsp;
                                      </c:otherwise>
                                  </c:choose>
                              </td>
                          </tr>
                      </c:when>
                      <c:otherwise>
                          <tr>
                              <td class="docTdBorder textPadding" width="50%">
                                  ��� ���������
                              </td>
                              <td class="docTdBorder textPadding" width="50%">
                                  <c:if test="${(document.documentType == 'REGULAR_PASSPORT_RF')}">
                                     &nbsp;��������������� ������� ��
                                  </c:if>
                                  <c:if test="${(document.documentType == 'MILITARY_IDCARD')}">
                                     &nbsp;������������� �������� ���������������
                                  </c:if>
                                  <c:if test="${(document.documentType == 'SEAMEN_PASSPORT')}">
                                     &nbsp;������� ������
                                  </c:if>
                                  <c:if test="${(document.documentType == 'RESIDEN��_PERMIT_RF')}">
                                     &nbsp;��� �� ���������� ��
                                  </c:if>
                                  <c:if test="${(document.documentType == 'FOREIGN_PASSPORT_RF')}">
                                     &nbsp;����������� ������� ��
                                  </c:if>
                                  <c:if test="${(document.documentType == 'OTHER')}">
                                     &nbsp;
                                  </c:if>
                              </td>
                          </tr>
                      </c:otherwise>
                  </c:choose>
                  <tr>
                      <td class="docTdBorder textPadding">
                          C����
                      </td>
                      <td class="docTdBorder textPadding">
                          &nbsp;${document.documentSeries}
                      </td>
                  </tr>
                  <tr>
                      <td class="docTdBorder textPadding">
                          �����
                      </td>
                      <td class="docTdBorder textPadding">
                          &nbsp;${document.documentNumber}
                      </td>
                  </tr>
                  <tr>
                      <td class="docTdBorder textPadding">
                          ���� ������
                      </td>
                      <td class="docTdBorder textPadding">
                          &nbsp;<c:if test="${not empty document.documentIssueDate}">
	                                <bean:write name="document" property="documentIssueDate.time" format="dd.MM.yyyy"/>
	                            </c:if>
                      </td>
                  </tr>
                  <tr>
                      <td class="docTdBorder textPadding">
                          ��� �����
                      </td>
                      <td class="docTdBorder textPadding">
                          &nbsp;${document.documentIssueBy}
                      </td>
                  </tr>
                  <tr>
                      <td class="docTdBorder textPadding">
                          ��� �������������
                      </td>
                      <td class="docTdBorder textPadding">
                          &nbsp;${document.documentIssueByCode}
                      </td>
                  </tr>
              </table>
          </td>
      </tr>
      <tr>
          <td>
              <br>
              <br>
          </td>
      </tr>
      <tr>
          <td align="center">
              <b>���������� ����������</b>
          </td>
      </tr>
      <tr>
          <td>
              <br>
          </td>
      </tr>
      <tr>
          <td>
              <table cellspacing="0" class="textDoc docTableBorder" width="100%">
                  <tr>
                      <td class="docTdBorder textPadding" width="50%">
                          ����� �����������
                      </td>
                      <td class="docTdBorder textPadding" width="50%">
                          <c:if test="${person.registrationAddress != null}">
                            &nbsp;${person.registrationAddress}
		                  </c:if>
                      </td>
                  </tr>
                  <tr>
                      <td class="docTdBorder textPadding">
                          ����� ������������ ����������
                      </td>
                      <td class="docTdBorder textPadding">
                          <c:if test="${person.residenceAddress != null}">
                            &nbsp;${person.residenceAddress}
	                      </c:if>
                      </td>
                  </tr>
	              <c:if test="${empty PrintPersonForm.principalPerson}">
					  <tr>
						  <td class="docTdBorder textPadding">
							  ������ �������� ����������
						  </td>
						  <td class="docTdBorder textPadding">
							  &nbsp;${person.messageService}
						  </td>
					  </tr>
	              </c:if>
                  <tr>
                      <td class="docTdBorder textPadding">
                          ����� ����������� �����
                      </td>
                      <td class="docTdBorder textPadding">
                          &nbsp;${person.email}
                      </td>
                  </tr>
                  <tr>
                      <td class="docTdBorder textPadding">
                          �������� �������
                      </td>
                      <td class="docTdBorder textPadding">
                          &nbsp;${person.homePhone}
                      </td>
                  </tr>
                  <tr>
                      <td class="docTdBorder textPadding">
                          ������� �������
                      </td>
                      <td class="docTdBorder textPadding">
                          &nbsp;${person.jobPhone}
                      </td>
                  </tr>
                  <tr>
                      <td class="docTdBorder textPadding">
                          ��������� �������
                      </td>
                      <td class="docTdBorder textPadding">
                          &nbsp;${person.mobilePhone}
                      </td>
                  </tr>
                  <tr>
                      <td class="docTdBorder textPadding">
                          ��������� ��������
                      </td>
                      <td class="docTdBorder textPadding">
                          &nbsp;${person.mobileOperator}
                      </td>
                  </tr>
	              <c:if test="${empty PrintPersonForm.principalPerson}">
					  <tr>
						  <td class="docTdBorder textPadding">
							  ������ SMS ���������
						  </td>
						  <td class="docTdBorder textPadding">
							  <c:choose>
								  <c:when test="${(person.SMSFormat == 'DEFAULT')}">
									 &nbsp;�������
								  </c:when>
								  <c:otherwise>
									 &nbsp;��������������
								  </c:otherwise>
							  </c:choose>
						  </td>
					  </tr>
	              </c:if>
              </table>
          </td>
      </tr>
      <tr>
          <td>
              <br>
              <br>
          </td>
      </tr>
      <tr>
          <td align="center">
              <b>�������</b>
          </td>
      </tr>
      <tr>
          <td>
              <br>
          </td>
      </tr>
      <tr>
          <td>
              <table cellspacing="0" class="textDoc docTableBorder" width="100%">
                  <tr>
                      <td class="docTdBorder textPadding" width="50%">
                          ����� ��������
                      </td>
                      <td class="docTdBorder textPadding" width="50%">
                          &nbsp;${person.agreementNumber}
                      </td>
                  </tr>
                  <tr>
                      <td class="docTdBorder textPadding">
                          �������������
                      </td>
                      <td class="docTdBorder textPadding">
                          &nbsp;${PrintPersonForm.department.code.branch}/${PrintPersonForm.department.code.office} ${PrintPersonForm.department.name}
                      </td>
                  </tr>
                  <tr>
                      <td class="docTdBorder textPadding">
                          �����
                      </td>
                      <td class="docTdBorder textPadding">
                          &nbsp;${PrintPersonForm.pin}
                      </td>
                  </tr>
	              <c:if test="${empty PrintPersonForm.principalPerson}">
                  <tr>
                      <td class="docTdBorder textPadding">
                          ���� ����������� ��������
                      </td>
                      <td class="docTdBorder textPadding">
                      <c:choose>
                      <c:when test="${empty person.prolongationRejectionDate}">
                         &nbsp;
                      </c:when>
                      <c:otherwise>
                         &nbsp;<bean:write name="person" property="prolongationRejectionDate.time" format="dd.MM.yyyy"/>
                      </c:otherwise>
                      </c:choose>
                       </td>
                  </tr>
	              </c:if>
                  <tr>
                      <td class="docTdBorder textPadding">
                          ���� ������ ������������
                      </td>
                      <td class="docTdBorder textPadding">
                          <c:choose>
                          <c:when test="${empty person.serviceInsertionDate}">
                             &nbsp;
                          </c:when>
                          <c:otherwise>
                              &nbsp;<bean:write name="person" property="serviceInsertionDate.time" format="dd.MM.yyyy"/>
                          </c:otherwise>
                          </c:choose>
                      </td>
                  </tr>
              </table>
          </td>
      </tr>
      <tr>
          <td>
              <br>
              <br>
          </td>
      </tr>

      <c:set var="lineNumber" value= "0"/>
      <logic:iterate id="entry" name="PrintPersonForm" property="accountLinks">
          <c:set var="lineNumber" value="${lineNumber+1}"/>
      </logic:iterate>
      <c:if test="${(lineNumber > 0)}">
      <tr>
          <td align="center">
              <b>����� � �����<br></b>
          </td>
      </tr>
      <tr>
          <td>
              <br>
          </td>
      </tr>
      <tr>
          <td>
              <table cellspacing="0" class="textDoc docTableBorder" width="100%">
                  <tr>
                      <td valign="top" align="center" class="docTdBorder textPadding" width="10%">
                          � �/�
                      </td>
                      <td valign="top" align="center" class="docTdBorder textPadding" width="70%">
                          ����� �����
                      </td>
                      <td valign="top" align="center" class="docTdBorder textPadding" width="20%">
                          ��������� ������ �����
                      </td>
                  </tr>
                  <c:set var="lineNumber" value= "0"/>
                  <logic:iterate id="entry" name="PrintPersonForm" property="accountLinks">
                      <c:set var="lineNumber" value="${lineNumber+1}"/>
                      <tr>
                         <td class="docTdBorder" align="center">&nbsp;<c:out value="${lineNumber}"/>&nbsp;</td>
                         <td class="docTdBorder">&nbsp;${entry.account.number}</td>
                         <td align="center" class="docTdBorder">
                              <c:choose>
                                  <c:when test="${(entry.paymentAbility == 'true')}">
                                      &nbsp;��
                                  </c:when>
                                  <c:otherwise>
                                      &nbsp;���
                                  </c:otherwise>
                               </c:choose>
                         </td>
                      </tr>
                  </logic:iterate>
              </table>
          </td>
      </tr>
      <tr>
          <td>
              <br>
              <br>
          </td>
      </tr>
      </c:if>

      <c:set var="lineNumber" value= "0"/>
      <logic:iterate id="entry" name="PrintPersonForm" property="services">
          <c:set var="lineNumber" value="${lineNumber+1}"/>
      </logic:iterate>
      <c:if test="${(lineNumber > 0)}">
      <tr>
          <td align="center">
              <b>�������� ��������<br></b>
          </td>
      </tr>
      <tr>
          <td>
              <br>
          </td>
      </tr>
      <tr>
          <td>
              <table cellspacing="0" class="textDoc docTableBorder" width="100%">
                  <tr>
                     <td class="docTdBorder" align="center" width="10%">� �/�</td>
                     <td class="docTdBorder" align="center">������������ ��������</td>
                  </tr>
                  <tr>
                      <c:set var="lineNumber" value= "0"/>
                      <logic:iterate id="entry" name="PrintPersonForm" property="services">
                             <c:set var="lineNumber" value="${lineNumber+1}"/>
                                    <tr>
                                        <td class="docTdBorder" align="center">&nbsp;<c:out value="${lineNumber}"/>&nbsp;</td>
                                        <td class="docTdBorder">&nbsp;${entry.name}</td>
                                    </tr>
                      </logic:iterate>
                  </tr>
              </table>
          </td>
      </tr>
      <tr>
          <td>
              <br>
              <br>
          </td>
      </tr>
      </c:if>

      <c:set var="lineNumber" value= "0"/>
      <logic:iterate id="entry" name="PrintPersonForm" property="empoweredPersons">
          <c:set var="lineNumber" value="${lineNumber+1}"/>
      </logic:iterate>
      <c:if test="${(lineNumber > 0)}">
      <tr>
          <td align="center">
              <b>�������������<br></b>
          </td>
      </tr>
      <tr>
          <td>
              <br>
          </td>
      </tr>
      <tr>
          <td>
              <table cellspacing="0" class="textDoc docTableBorder" width="100%">
                  <tr>
                     <td class="docTdBorder" align="center" width="10%">� �/�</td>
                     <td class="docTdBorder" align="center">�.�.�.</td>
                  </tr>
                  <c:set var="lineNumber" value= "0"/>
                  <logic:iterate id="entry" name="PrintPersonForm" property="empoweredPersons">
                         <c:set var="lineNumber" value="${lineNumber+1}"/>
                                <tr>
                                    <td class="docTdBorder" align="center" width="10%">&nbsp;<c:out value="${lineNumber}"/>&nbsp;</td>
                                    <td class="docTdBorder">&nbsp;${entry.surName} ${entry.firstName} ${entry.patrName}</td>
                                </tr>
                  </logic:iterate>
              </table>
          </td>
      </tr>
      <tr>
          <td>
              <br>
              <br>
          </td>
      </tr>
      </c:if>

      <c:set var="lineNumber" value= "0"/>
      <logic:iterate id="entry" name="PrintPersonForm" property="receivers">
	      <c:if test="${entry.kind == 'S'}">
            <c:set var="lineNumber" value="${lineNumber+1}"/>
	      </c:if>
      </logic:iterate>
      <c:if test="${(lineNumber > 0)}">
      <tr>
          <td align="center">
              <b>���������� ��������(���. ����)<br></b>
          </td>
      </tr>
      <tr>
          <td>
              <br>
          </td>
      </tr>
      <tr>
          <td>
              <table cellspacing="0" class="textDoc docTableBorder" width="100%">
                  <c:set var="lineNumber" value= "0"/>
                  <logic:iterate id="entry" name="PrintPersonForm" property="receivers">
	                  <c:if test="${entry.kind == 'S'}">
                      <c:set var="lineNumber" value="${lineNumber+1}"/>
                      <tr>
                          <td align="center" rowspan="5" class="docTdBorder" width="10%"><c:out value="${lineNumber}"/></td>
                          <td class="docTdBorder textPadding" width="20%">
                             �����������
                          </td>
                          <td class="docTdBorder">&nbsp;${entry.alias}</td>
                      </tr>
                      <tr>
                          <td class="docTdBorder textPadding">
                             �.�.�.
                          </td>
                          <td class="docTdBorder">&nbsp;${entry.name}</td>
                      </tr>
                      <tr>
                          <td class="docTdBorder textPadding">
                              ����
                          </td>
                          <td class="docTdBorder">&nbsp;${entry.account}</td>
                      </tr>
                      <tr>
                          <td class="docTdBorder textPadding">
                             ���
                          </td>
                          <td class="docTdBorder">&nbsp;${entry.officeKey.code.branch}</td>
                      </tr>
                      <tr>
                          <td class="docTdBorder textPadding">
                             ������
                          </td>
                          <td class="docTdBorder">&nbsp;${entry.officeKey.code.office}</td>
                      </tr>
	                  </c:if>
                  </logic:iterate>
              </table>
          </td>
      </tr>
      <tr>
          <td>
              <br>
              <br>
          </td>
      </tr>
      </c:if>

      <c:set var="lineNumber" value= "0"/>
      <logic:iterate id="receiver" name="PrintPersonForm" property="receivers">
	      <c:if test="${entry.kind == 'J'}">
            <c:set var="lineNumber" value="${lineNumber+1}"/>
	      </c:if>
      </logic:iterate>
      <c:if test="${(lineNumber > 0)}">
      <tr>
          <td align="center">
              <b>���������� ��������(��. ����)<br></b>
          </td>
      </tr>
      <tr>
          <td>
              <br>
          </td>
      </tr>
      <tr>
          <td>
              <table cellspacing="0" class="textDoc docTableBorder" width="100%">
                      <c:set var="lineNumber" value= "0"/>
                      <logic:iterate id="receiver" name="PrintPersonForm" property="receivers">
	                       <c:if test="${entry.kind == 'J'}">
                          <c:set var="lineNumber" value="${lineNumber+1}"/>
                          <tr>
                              <td align="center" rowspan="7" class="docTdBorder" width="10%"><c:out value="${lineNumber}"/></td>
                              <td class="docTdBorder textPadding"  width="20%">
                                 �����������
                              </td>
                              <td class="docTdBorder">&nbsp;${receiver.alias}</td>
                          </tr>
                          <tr>
                              <td class="docTdBorder textPadding">
                                 ������������
                              </td>
                              <td class="docTdBorder">&nbsp;${receiver.name}</td>
                          </tr>
                          <tr>
                              <td class="docTdBorder textPadding">
                                  ���
                              </td>
                              <td class="docTdBorder">&nbsp;${receiver.INN}</td>
                          </tr>
                          <tr>
                              <td class="docTdBorder textPadding">
                                  ����
                              </td>
                              <td class="docTdBorder">&nbsp;${receiver.account}</td>
                          </tr>
                          <tr>
                              <td class="docTdBorder textPadding">
                                 ����
                              </td>
                              <td class="docTdBorder">&nbsp;${receiver.bankName}</td>
                          </tr>
                          <tr>
                              <td class="docTdBorder textPadding">
                                 ���
                              </td>
                              <td class="docTdBorder">&nbsp;${receiver.BIC}</td>
                          </tr>
                          <tr>
                              <td class="docTdBorder textPadding">
                                 ����.����
                              </td>
                              <td class="docTdBorder">&nbsp;${receiver.correspondentAccount}</td>
                          </tr>
	                      </c:if>
                      </logic:iterate>
              </table>
          </td>
      </tr>
      <tr>
          <td>
              <br>
              <br>
          </td>
      </tr>
      </c:if>
      </table>


    </html:form>
    </body>
</tiles:put>
</tiles:insert>
