<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html:form action="/persons/payment_wait_confirm/list" onsubmit="return setEmptyAction(event);">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="imagePath" value="${skinUrl}/images"/>
    <tiles:insert definition="personEdit">
        <tiles:put name="submenu" type="string" value="OperationsForConfirm"/>
        <tiles:put name="needSave" value="false"/>

        <c:set var="bundle" value="personsBundle"/>
        <tiles:put name="pageTitle" type="string">
	        <bean:message key="edit.payment.title" bundle="${bundle}"/>
        </tiles:put>

        <%-- ������ --%>
        <tiles:put name="filter" type="string">
            <c:set var="colCount" value="3" scope="request"/>
            <%-- ������ --%>
            <tiles:insert definition="filterEntryField" flush="false">
                <tiles:put name="label" value="filter.period"/>
                <tiles:put name="bundle" value="${bundle}"/>
                <tiles:put name="mandatory" value="false"/>
                <tiles:put name="data">
                   <bean:message bundle="${bundle}" key="filter.period.from"/>&nbsp;
                   <span style="font-weight:normal;overflow:visible;cursor:default;">
                       <input type="text"
                               size="10" name="filter(fromDate)"  class="dot-date-pick"
                               value="<bean:write name="form" property="filters.fromDate" format="dd.MM.yyyy"/>"/>
                       <input type="text"
                               size="8" name="filter(fromTime)"  class="time-template"
                               value="<bean:write name="form" property="filters.fromTime" format="HH:mm:ss"/>"
                               onkeydown="onTabClick(event,'filter(toDate)');"/>
                   </span>
                   &nbsp;<bean:message bundle="${bundle}" key="filter.period.to"/>&nbsp;
                   <span style="font-weight:normal;cursor:default;">
                       <input type="text"
                               size="10" name="filter(toDate)"  class="dot-date-pick"
                               value="<bean:write name="form" property="filters.toDate" format="dd.MM.yyyy"/>"/>

                       <input type="text"
                               size="8" name="filter(toTime)"   class="time-template"
                               value="<bean:write name="form" property="filters.toTime" format="HH:mm:ss"/>"/>
                   </span>
                </tiles:put>
            </tiles:insert>

            <%-- ��� �������� --%>
            <tiles:insert definition="filterEntryField" flush="false">
                <tiles:put name="label" value="label.operation.type"/>
                <tiles:put name="bundle" value="${bundle}"/>
                <tiles:put name="mandatory" value="false"/>
                <tiles:put name="data">
                    <html:select property="filter(formName)" >
                        <html:option value="">���</html:option>
                        <html:option value="InternalPayment">������� ����� ������ �������</html:option>
                        <html:option value="RurPayment">������� ������� ��������� ��� �������� ���� � ������ ����</html:option>
                        <html:option value="JurPayment">������� �����������</html:option>
                        <html:option value="InvoicePayment">������ �������������</html:option>
                        <html:option value="RurPayJurSB">������ �����</html:option>
                        <html:option value="LongOffer">����������</html:option>
                        <html:option value="IMAPayment">�������/������� �������</html:option>
                        <html:option value="AccountClosingPayment">������ �� �������� ������</html:option>
                        <html:option value="CreditReportPayment">��������� ���������� ������</html:option>
                        <html:option value="RemoteConnectionUDBOClaim">��������� ���� ������������ �������� ������</html:option>
                    </html:select>
                </tiles:put>
             </tiles:insert>

             <%-- ���������� --%>
            <tiles:insert definition="filterEntryField" flush="false">
                <tiles:put name="label" value="filter.receiver"/>
                <tiles:put name="bundle" value="${bundle}"/>
                <tiles:put name="mandatory" value="false"/>
                <tiles:put name="data">
                    <html:text property="filter(receiverName)" size="20" maxlength="100"/>
                </tiles:put>
             </tiles:insert>

            <tiles:insert definition="filterEmptytField" flush="false"/>

             <%-- ������ �������� --%>
            <tiles:insert definition="filterEntryField" flush="false">
                <tiles:put name="label" value="label.operation.state"/>
                <tiles:put name="bundle" value="${bundle}"/>
                <tiles:put name="mandatory" value="false"/>
                <tiles:put name="data">
                    <html:select property="filter(state)" >
                        <html:option value="WAIT_CONFIRM">������� ��������������� �������������</html:option>
                        <html:option value="DISPATCHED">�����������</html:option>
                        <html:option value="DELAYED_DISPATCH">������� ���������</html:option>
                        <html:option value="EXECUTED">��������</html:option>
                        <html:option value="UNKNOW,SENT">�������������</html:option>
                        <html:option value="REFUSED">�������</html:option>
                        <html:option value="DISPATCHED">��������������</html:option>
                        <html:option value="ALL">���</html:option>
                    </html:select>
                </tiles:put>
             </tiles:insert>

             <%-- � �������/������ --%>
            <tiles:insert definition="filterEntryField" flush="false">
                <tiles:put name="label" value="filter.number.payment"/>
                <tiles:put name="bundle" value="${bundle}"/>
                <tiles:put name="mandatory" value="false"/>
                <tiles:put name="data">
                    <html:text property="filter(number)" size="20" maxlength="10"/>
                </tiles:put>
             </tiles:insert>

            <tiles:insert definition="filterEmptytField" flush="false"/>

             <%-- ����������� --%>
            <tiles:insert definition="filterEntryField" flush="false">
                <tiles:put name="label" value="filter.confirm"/>
                <tiles:put name="bundle" value="${bundle}"/>
                <tiles:put name="mandatory" value="false"/>
                <tiles:put name="data">
                    <html:select property="filter(additionConfirm)" >
                        <html:option value=""></html:option>
                        <html:option value="internet">����� ��</html:option>
                        <html:option value="atm">����� ��</html:option>
                    </html:select>
                </tiles:put>
             </tiles:insert>

             <%-- ���������� ��������� --%>
            <tiles:insert definition="filterEntryField" flush="false">
                <tiles:put name="label" value="filter.confirm.employee"/>
                <tiles:put name="bundle" value="${bundle}"/>
                <tiles:put name="mandatory" value="false"/>
                <tiles:put name="data">
                    <html:text property="filter(confirmEmployee)" size="20" maxlength="50"/>
                </tiles:put>
            </tiles:insert>

        </tiles:put>

        <%-- ������ --%>
        <tiles:put name="data" type="string">
            <script type="text/javascript">
                function openDetails(operationUID)
                {
                    window.open("${phiz:calculateActionURL(pageContext, '/log/confirmationInfo.do')}?filter(operationUID)=" + operationUID + "&isAudit=true",'confirmationInfo', "resizable=1,menubar=1,toolbar=1,scrollbars=1");
                }
                addClearMasks(null,
                        function(event)
                        {
                            clearInputTemplate('filter(fromDate)', '__.__.____');
                            clearInputTemplate('filter(toDate)', '__.__.____');
                            clearInputTemplate('filter(fromTime)', '__:__:__');
                            clearInputTemplate('filter(toTime)', '__:__:__');
                        });
            </script>
            <c:if test="${form.activePerson.securityType != 'LOW'}">
                <tiles:insert definition="roundBorderLight" flush="false">
                    <tiles:put name="color" value="redBlock"/>
                    <tiles:put name="data">
                        ��������� ����� ������������ ����������������
                    </tiles:put>
                </tiles:insert>
            </c:if>

            <jsp:include page="/WEB-INF/jsp-sbrf/persons/existsAnotherNodeDocumentMessage.jsp" flush="false"/>

            <tiles:insert definition="tableTemplate" flush="false">
                <tiles:put name="id" value="limitsList"/>
                <tiles:put name="grid">
                    <sl:collection id="document" model="list" property="data">
                        <c:set var="state" value="${document.state.code}"/>
                        <sl:collectionItem title="���� � �����">
                            <c:choose>
                                <%--���� ���������� ��������� ��� ��������� "��������", "�������"--%>
                                <c:when test="${(document.executionDate != null)&&(state == 'EXECUTED' || state == 'REFUSED')}">
                                    <c:set var="statusChangedDate" value="${document.executionDate.time}"/>
                                </c:when>
                                <%--���� ������������� ��� �������� "��������", "�������"--%>
                                <c:when test="${(document.admissionDate != null)&&(state == 'EXECUTED' || state == 'REFUSED')}">
                                    <c:set var="statusChangedDate" value="${document.admissionDate.time}"/>
                                </c:when>
                                <%--CONFIRMED - "�����������" (��� ��� admissionDate = null, � ���� ������������� � "changed")--%>
                                <%--DISPATCHED - �������� ����������� � ������� �� ��������� � ����. ���� ������������� � "changed"--%>
                                <c:when test="${state == 'CONFIRMED' || state == 'DISPATCHED'}">
                                    <c:set var="statusChangedDate" value="${document.changed}"/>
                                </c:when>
                                <%--���� ���������� �������� �������� ��� ������� "�� ����������� � ��"--%>
                                <c:when test="${state == 'WAIT_CONFIRM'}">
                                    <c:set var="statusChangedDate" value="${document.operationDate.time}"/>
                                </c:when>
                                <c:otherwise>
                                    <c:set var="statusChangedDate" value="${document.dateCreated.time}"/>
                                </c:otherwise>
                            </c:choose>

                            <table>
                            <tr>
                                <td style="white-space:nowrap;border-style:none;">
                                    <a href="${phiz:calculateActionURL(pageContext,"/persons/payment_wait_confirm/edit.do")}?id=${document.id}">
                                        <fmt:formatDate value="${statusChangedDate}" pattern="dd.MM.yyyy HH:mm:ss"/>
                                    </a>
                                </td>
                                <td align="center" width="16px" style="border-style:none;">
                                    <c:set var="channel" value="${document.creationType}"/>
                                    <c:choose>
                                        <c:when test="${channel == 'mobile'}">
                                            <img border="0" alt="" width="10" height="15" src="${imagePath}/icon-mobile.gif" title="�������� ��������� � ��������� ����������"/>
                                        </c:when>
                                        <c:when test="${channel == 'social'}">
                                            <img border="0" alt="" width="10" height="15" src="${imagePath}/icon-social.gif" title="�������� ��������� ����� ���������� ����"/>
                                        </c:when>
                                        <c:when test="${channel == 'atm'}">
                                            <img border="0" alt="" width="16" height="16" src="${imagePath}/icon-atm.gif" title="�������� ��������� ����� ���������� ����������������"/>
                                        </c:when>
                                        <c:when test="${channel == 'sms'}">
                                            <img border="0" alt="" width="16" height="16" src="${imagePath}/icon-ermb.gif" title="�������� ��������� ����� ����"/>
                                        </c:when>
                                    </c:choose>
                                </td>
                            </tr>
                            </table>
                        </sl:collectionItem>

                        <sl:collectionItem title="�����">
                            <sl:collectionItemParam id="action" value="/persons/payment_wait_confirm/edit.do?id=${document.id}"/>
                            ${document.documentNumber}
                        </sl:collectionItem>

                        <sl:collectionItem title="��� ��������">
                            <c:if test="${document.byTemplate}">
                                <img border="0" alt="" src="${imagePath}/icon-byTemplate.gif" title="�������� ��������� �� �������"/>
                            </c:if>
                            <c:choose>
                                <c:when test="${document.longOffer}">����������</c:when>
                                <c:when test="${document.formName == 'InternalPayment'}">������� ����� ������ ������� � �������</c:when>
                                <c:when test="${document.formName == 'RurPayJurSB'}">������ �����</c:when>
                                <c:when test="${document.formName == 'RurPayment'}">
                                    <c:choose>
                                        <c:when test="${document.receiverSubType == 'externalAccount'}">
                                            ������� �������� ���� � ������ ���� �� ����������
                                        </c:when>
                                        <c:when test="${document.receiverSubType == 'ourCard' or document.receiverSubType == 'ourAccount' or  document.receiverSubType == 'ourPhone'}">
                                            ������� ������� ���������
                                        </c:when>
                                        <c:otherwise>
                                            ������� �� ����� � ������ �����
                                        </c:otherwise>
                                    </c:choose>
                                </c:when>
                                <c:when test="${document.formName == 'NewRurPayment'}">
                                    <c:choose>
                                        <c:when test="${document.receiverSubType == 'ourAccount' || document.receiverSubType == 'externalAccount'}">
                                                <bean:message key="label.translate.bankAccount" bundle="paymentsBundle"/>
                                        </c:when>
                                        <c:when test="${document.receiverSubType == 'visaExternalCard' || document.receiverSubType == 'masterCardExternalCard' || document.receiverSubType == 'ourContactToOtherCard'}">
                                                <bean:message key="label.translate.card.otherbank" bundle="paymentsBundle"/>
                                        </c:when>
                                        <c:when test="${document.receiverSubType == 'yandexWallet' || document.receiverSubType == 'yandexWalletOurContact' || document.receiverSubType == 'yandexWalletByPhone'}">
                                                <bean:message key="label.translate.yandex" bundle="paymentsBundle"/>
                                        </c:when>
                                        <c:otherwise>
                                                <bean:message key="label.translate.ourClient" bundle="paymentsBundle"/>
                                        </c:otherwise>
                                    </c:choose>
                                </c:when>
                                <c:when test="${document.formName == 'InvoicePayment'}">������ �������������</c:when>
                                <c:when test="${document.formName == 'JurPayment'}">������� �����������</c:when>
                                <c:when test="${document.formName == 'IMAPayment'}">�������/������� �������</c:when>
                                <c:when test="${document.formName == 'ConvertCurrencyPayment'}">����� �����</c:when>
                                <c:when test="${document.formName == 'CreditReportPayment'}">��������� ���������� ������</c:when>
                                <c:when test="${phiz:isExternalPayment(document)}">������ ����� � �������� �����</c:when>
                            </c:choose>
                        </sl:collectionItem>

                        <c:set var="style" value="${state == 'WAIT_CONFIRM' ? 'red' : ''}"/>

                        <sl:collectionItem title="������" styleClass="${style}">
                            <c:set var="infoMessage">
                                <img border="0" alt="" src="${imagePath}/info.gif" title="������, ������������ � ������� � ������� �������� � �� ����� �������"/>
                            </c:set>
                            <c:choose>
                                <c:when test="${state == 'WAIT_CONFIRM'}">������� ��������������� ������������� <span class="text-dark-gray">(����������� � ���������� ������ <c:if test="${'IMSI' == document.reasonForAdditionalConfirm}"> (����� SIM-�����)</c:if>${infoMessage})</span></c:when>
                                <c:when test="${state == 'DISPATCHED'}">�������������� (����������� ������${infoMessage})</c:when>
                                <c:when test="${state == 'INITIAL' || state == 'SAVED'}">������ (��������${infoMessage})</c:when>
                                <c:when test="${state == 'DELAYED_DISPATCH'}">������� ���������</c:when>
                                <c:when test="${state == 'CONFIRMED'}">�����������</c:when>
                                <c:when test="${state == 'EXECUTED'}">��������</c:when>
                                <c:when test="${state == 'UNKNOW' or state == 'SENT'}">������������� (����������� ������${infoMessage})</c:when>
                                <c:when test="${state == 'REFUSED'}">������� (��������� ������${infoMessage})</c:when>
                            </c:choose>
                        </sl:collectionItem>

                        <sl:collectionItem title="��� �������">
                             ${document.payerName}
                        </sl:collectionItem>

                        <sl:collectionItem title="���� ��������">
                            <c:choose>
                                <c:when test="${document.chargeOffResourceType eq 'CARD'}">
                                    ${phiz:getCutCardNumber(document.chargeOffAccount)}
                                </c:when>
                                <c:otherwise>
                                      ${document.chargeOffAccount}
                                </c:otherwise>
                            </c:choose>
                        </sl:collectionItem>

                        <sl:collectionItem title="���� ����������">
                            <c:if test="${phiz:isInstance(document, 'com.rssl.phizic.business.documents.AbstractAccountsTransfer')}">
                                 <c:choose>
                                    <c:when test="${document.destinationResourceType eq 'CARD' || document.destinationResourceType eq 'EXTERNAL_CARD'}">
                                        ${phiz:getCutCardNumber(document.receiverAccount)}
                                    </c:when>
                                    <c:otherwise>
                                        ${document.receiverAccount}
                                    </c:otherwise>
                                </c:choose>
                            </c:if>
                        </sl:collectionItem>

                        <sl:collectionItem title="�����">
                            <c:choose>
                                <c:when test="${document.chargeOffAmount != null}">
                                    <bean:write name="document" property="chargeOffAmount.decimal" format="0.00"/>
                                </c:when>
                                <c:when test="${document.destinationAmount != null}">
                                    <bean:write name="document" property="destinationAmount.decimal" format="0.00"/>
                                </c:when>
                            </c:choose>
                        </sl:collectionItem>

                        <sl:collectionItem title="������">
                             <c:choose>
                                <c:when test="${document.chargeOffAmount != null}">
                                    <bean:write name="document" property="chargeOffAmount.currency.code"/>
                                </c:when>
                                <c:when test="${document.destinationAmount != null}">
                                    <bean:write name="document" property="destinationAmount.currency.code"/>
                                </c:when>
                            </c:choose>
                        </sl:collectionItem>

                        <sl:collectionItem title="����������">
                            <c:if test="${phiz:isInstance(document, 'com.rssl.phizic.business.documents.AbstractAccountsTransfer')}">
                                <bean:write name="document" property="receiverName"/>
                            </c:if>
                        </sl:collectionItem>

                        <sl:collectionItem title="�������������">
                            <c:choose>
                                <c:when test="${document.additionalOperationChannel == 'internet'}">����� ��</c:when>
                                <c:when test="${document.additionalOperationChannel == 'atm'}">����� ��</c:when>
                            </c:choose>
                        </sl:collectionItem>

                        <sl:collectionItem title="���������� ���������">
                            <c:if test="${not empty document.confirmEmployee}">
                                <bean:write name="document" property="confirmEmployee"/>
                            </c:if>
                        </sl:collectionItem>
                                           <sl:collectionItem title="������������� ������">
                        <c:out value="${document.sessionId}"/>
                    </sl:collectionItem>
                    <sl:collectionItem title="�������������">
                        <c:set var="strategy" value="${phiz:getConfirmStrategyType(document)}"/>
                        <c:choose>
                            <c:when test="${strategy == 'none'}">
                                <bean:message bundle="auditBundle" key="label.confirm.none"/>
                            </c:when>
                            <c:when test="${strategy == 'sms'}">
                                <bean:message bundle="auditBundle" key="label.confirm.sms"/>
                            </c:when>
                            <c:when test="${strategy == 'card'}">
                                <bean:message bundle="auditBundle" key="label.confirm.card"/>
                            </c:when>
                            <c:when test="${strategy == 'cap'}">
                                <bean:message bundle="auditBundle" key="label.confirm.cap"/>
                            </c:when>
                            <c:when test="${strategy == 'need'}">
                                <bean:message bundle="auditBundle" key="label.confirm.need"/>
                            </c:when>
                            <c:when test="${strategy == 'push'}">
                                <bean:message bundle="auditBundle" key="label.confirm.push"/>
                            </c:when>
                            <c:otherwise>&nbsp;</c:otherwise>
                        </c:choose>
                        <c:if test="${strategy == 'sms' || strategy == 'card'}">
                            &nbsp;<input type="button" class="buttWhite smButt" onclick="openDetails('${document.operationUID}');" value="&hellip;"/>
                        </c:if>
                    </sl:collectionItem>
                    <sl:collectionItem title="label.document.node.temporary">
                        <sl:collectionItemParam id="title"><bean:message bundle="auditBundle" key="label.document.node.temporary"/></sl:collectionItemParam>
                        <c:if test="${not empty document}">
                            <bean:message bundle="auditBundle" key="label.document.node.temporary.${not empty document.temporaryNodeId}"/>
                        </c:if>
                    </sl:collectionItem>
                    </sl:collection>
                    <tiles:put name="isEmpty" value="${empty form.data}"/>
                </tiles:put>
                <tiles:put name="emptyMessage">
                    <bean:message key="list.payment.empty" bundle="${bundle}"/>
                </tiles:put>                          
            </tiles:insert>
        </tiles:put>

    </tiles:insert>
</html:form>