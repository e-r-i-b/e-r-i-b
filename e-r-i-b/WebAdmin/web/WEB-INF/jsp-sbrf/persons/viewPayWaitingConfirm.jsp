<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/persons/payment_wait_confirm/edit" onsubmit="return setEmptyAction(event);">

    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="document" value="${form.document}"/>
    <c:set var="metadata" value="${form.metadata}"/>

    <tiles:insert definition="personEdit">
        <tiles:put name="submenu" type="string" value="OperationsForConfirm"/>
        <tiles:put name="menu" type="string"/>

        <tiles:put name="needSave" value="false"/>
        <tiles:put name="filter" type="string"/>
        <tiles:put name="data" type="string">
            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="id" value="audit_document"/>
                <c:choose>
                    <c:when test="${document.longOffer}">
                        <tiles:put name="name" value="Автоплатеж (регуляная операция)"/>
                    </c:when>
                    <c:otherwise>
                        <tiles:put name="name" value="${form.metadata.form.description}"/>
                    </c:otherwise>
                </c:choose>
                <tiles:put name="data">
                    <c:if test="${document.fraudReasonForAdditionalConfirm}">
                        <c:set var="fraudWinId" value="fraudActionConfirmationWindowId"/>
                        <c:set var="fraudUrl" value="${phiz:calculateActionURL(pageContext, '/fraud/async/confirm' )}"/>
                        <tiles:insert definition="window" flush="false">
                            <tiles:put name="id" value="${fraudWinId}"/>
                            <tiles:put name="loadAjaxUrl" value="${fraudUrl}"/>
                        </tiles:insert>
                    </c:if>
                    <tr>
                        <td height="100%">
                                ${form.html}
                            <c:if test="${phiz:checkEvent(form.document , 'button.confirm')}">
                                <tiles:insert definition="formRow" flush="false">
                                    <tiles:put name="title">Формат:</tiles:put>
                                    <tiles:put name="data"><c:out value="${form.document.byTemplate?'Операция по шаблону':'Разовая операция'}"/></tiles:put>
                                </tiles:insert>
                                <c:choose>
                                    <%-- операция по шаблону --%>
                                    <c:when test="${form.document.byTemplate}">
                                        <tiles:insert definition="formRow" flush="false">
                                            <tiles:put name="title" value="&nbsp;"/>
                                            <tiles:put name="data">
                                                <table style="margin:0;">
                                                    <tr>
                                                        <td><html:checkbox property="field(confirmTemplate)" style="border:none; float:left; margin: 0 7px 0 3px;"
                                                                           styleId="checkbx_confirmTemplate"/></td>
                                                        <td>Подтвердить шаблон</td>
                                                    </tr>
                                                </table>
                                            </tiles:put>
                                        </tiles:insert>
                                    </c:when>
                                    <%-- разовая операция, проверяем еще и признак доступности создания шаблонов--%>
                                    <c:otherwise>
                                        <c:set var="supportedActions" value="${phiz:getDocumentSupportedActions(document)}"/>
                                        <c:if test="${supportedActions['isTemplateSupported']}">
                                            <script type="text/javascript">
                                                function showTemplateNameField()
                                                {
                                                    var checkbx = document.getElementById('checkbx_createTemplate');
                                                    var row = document.getElementById('templateNameRowDiv');
                                                    row.style.display = checkbx.checked ? "block" : "none";
                                                }
                                                doOnLoad(function () {showTemplateNameField();});
                                            </script>
                                            <tiles:insert definition="formRow" flush="false">
                                                <tiles:put name="title" value="&nbsp;"/>
                                                <tiles:put name="data">
                                                    <table style="margin:0;">
                                                        <tr>
                                                            <td><html:checkbox property="field(createTemplate)" style="border:none; float:left; margin: 0 7px 0 3px;" styleId="checkbx_createTemplate"
                                                                               onchange="showTemplateNameField();" onclick="showTemplateNameField();"/></td>
                                                            <td>Создать сверхлимитный шаблон</td>
                                                        </tr>
                                                    </table>
                                                </tiles:put>
                                            </tiles:insert>
                                            <div id="templateNameRowDiv">
                                                <tiles:insert definition="formRow" flush="false">
                                                    <tiles:put name="title">Название шаблона:</tiles:put>
                                                    <tiles:put name="data">
                                                        <html:text property="field(templateName)" size="30" maxlength="50" value="${form.templateName}"/>
                                                    </tiles:put>
                                                </tiles:insert>
                                            </div>
                                        </c:if>
                                    </c:otherwise>
                                </c:choose>
                                <c:if test="${form.document.reasonForAdditionalConfirm == 'IMSI'}">
                                    <tiles:insert definition="formRow" flush="false">
                                        <tiles:put name="title" value="&nbsp;"/>
                                        <tiles:put name="data">
                                            <table style="margin:0;">
                                                <tr>
                                                    <td><html:checkbox property="field(isIMSIChecked)" style="border:none; float:left; margin: 0 7px 0 3px;" styleId="checkbx_isIMSIChecked"/></td>
                                                    <td><bean:message key="label.changePersonIMSIConfirm" bundle="personsBundle"/></td>
                                                </tr>
                                            </table>
                                        </tiles:put>
                                    </tiles:insert>
                                </c:if>
                            </c:if>
                        </td>
                    </tr>
                </tiles:put>
                <tiles:put name="alignTable" value="center"/>
                <c:if test="${not empty form.document.state.code and form.document.state.code =='REFUSED'
                        and not empty form.document.refusingReason
                        and (phiz:isInstance(form.document, 'com.rssl.phizic.business.documents.payments.LoanClaimBase')
                        or phiz:isInstance(form.document, 'com.rssl.phizic.business.documents.payments.VirtualCardClaim'))}">
                    <div id="stateDescription" onmouseover="showLayer('state','stateDescription','default');"
                         onmouseout="hideLayer('stateDescription');" class="layerFon" style="position:absolute; display:none; width:400px; min-height:20px;overflow:auto;">
                        <c:out value="${form.document.refusingReason}"/>
                    </div>
                </c:if>
                <tiles:put name="buttons">
                    <c:choose>
                        <c:when test="${document.fraudReasonForAdditionalConfirm}">
                            <tiles:insert definition="clientButton" flush="false">
                                <tiles:put name="commandTextKey" value="button.unknown"/>
                                <tiles:put name="commandHelpKey" value="button.unknown.help"/>
                                <tiles:put name="bundle" value="personsBundle"/>
                                <tiles:put name="onclick" value="openFraudWindow('unknown')"/>
                            </tiles:insert>
                            <tiles:insert definition="clientButton" flush="false">
                                <tiles:put name="commandTextKey" value="button.refuse"/>
                                <tiles:put name="commandHelpKey" value="button.refuse.help"/>
                                <tiles:put name="bundle" value="personsBundle"/>
                                <tiles:put name="onclick" value="openFraudWindow('refuse')"/>
                            </tiles:insert>
                            <tiles:insert definition="clientButton" flush="false">
                                <tiles:put name="commandTextKey" value="button.confirm"/>
                                <tiles:put name="commandHelpKey" value="button.confirm.help"/>
                                <tiles:put name="bundle" value="personsBundle"/>
                                <tiles:put name="onclick" value="openFraudWindow('accept')"/>
                            </tiles:insert>
                        </c:when>
                        <c:otherwise>
                            <c:set var="isCancel" value="${document.state.code == 'WAIT_CONFIRM' || document.state.code == 'WAIT_CONFIRM_TEMPLATE'}"/>
                            <tiles:insert definition="clientButton" flush="false">
                                <tiles:put name="commandTextKey" value="${isCancel ? 'button.cancel' : 'button.close.payment'}"/>
                                <tiles:put name="commandHelpKey" value="${isCancel ? 'button.cancel' : 'button.close.payment.help'}"/>
                                <tiles:put name="bundle" value="personsBundle"/>
                                <tiles:put name="action" value="/persons/payment_wait_confirm/list.do?person=${form.activePerson.id}"/>
                            </tiles:insert>
                            <tiles:insert definition="commandButton" flush="false">
                                <tiles:put name="commandKey" value="button.confirm"/>
                                <tiles:put name="commandHelpKey" value="button.confirm.help"/>
                                <tiles:put name="bundle" value="personsBundle"/>
                                <tiles:put name="stateObject" value="document"/>
                            </tiles:insert>
                        </c:otherwise>
                    </c:choose>

                    <script type="text/javascript">
                        function openFraudWindow(verdict)
                        {
                            var ajaxUrl = document.getElementById('hiddenAjaxUrl${fraudWinId}');
                            ajaxUrl.value = ajaxUrl.value + "?" + "verdict" + "=" + verdict
                                    + "&" + "documentId" + "=" + "${form.id}"
                                    + "&" + "person" + "=" + "${form.activePerson.id}";
                            win.open("${fraudWinId}");
                        }

                        function closeFraudWindow()
                        {
                            win.close("${fraudWinId}");
                        }
                    </script>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>
