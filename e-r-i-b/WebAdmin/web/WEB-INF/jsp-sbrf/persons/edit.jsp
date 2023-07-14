<%@ page import="javax.servlet.ServletRequest"%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/persons/edit" onsubmit="return setEmptyAction(event);">
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<%-- переменная используется в левом меню, сюда вынесена
для корректной работы редиректа при технологическом перерыве--%>
<c:set var="needChargeOff" value="${phiz:isNeedChargeOff()}"/>

<tiles:insert definition="personEdit">
	<tiles:put name="submenu" type="string" value="Edit"/>
    <tiles:put type="string" name="additionalInfoBlock" value=""/>

<tiles:put name="menu" type="string">
    <c:set var="isModified" value="${form.modified}"/>
    <c:set var="useOwnAuthentication" value="${phiz:useOwnAuthentication()}"/>

    <c:set var="person" value="${form.activePerson}"/>
    <c:set var="isNew"  value="${empty person.id}"/>
    <c:set var="personStatus" value="${person.status}"/>

    <c:set var="isTemplate" value="${personStatus == 'T'}"/>
	<c:set var="isAgreementSign" value="${personStatus == 'S'}"/>
    <c:set var="isDeleted"  value="${personStatus == 'D'}"/>
    <c:set var="isActive" value="${personStatus == 'A'}"/>
    <c:set var="isCancellation" value="${personStatus == 'W'}"/>

    <c:set var="editNotSupported" value="${person.creationType == 'CARD' or person.creationType == 'UDBO'}"/>
    <c:set var="isAgreementSignMandatory" value="${phiz:isAgreementSignMandatory()}"/>
    <c:set var="notShowSavesIgnoreCreationType" value="${(isTemplate && isAgreementSignMandatory)}"/>
	<c:set var="isShowSaves" value="${not (notShowSavesIgnoreCreationType or editNotSupported) }"/>
	<c:set var="extendedLoggingEntry" value="${form.extendedLoggingEntry}"/>

    <c:choose>
        <c:when test="${not empty extendedLoggingEntry}">
            <tiles:insert definition="commandButton" flush="false">
                <tiles:put name="viewType" value="blueBorder"/>
                <tiles:put name="commandKey" value="button.extenededLog.off"/>
                <tiles:put name="commandHelpKey" value="button.extenededLog.off.help"/>
                <tiles:put name="bundle" value="personsBundle"/>
                <tiles:put name="confirmText" value="Вы действительно хотите отключить расширенное логирование для данного клиента?"/>
            </tiles:insert>
        </c:when>
        <c:otherwise>
            <c:choose>
                <c:when test="${phiz:impliesServiceRigid('ClientExtendedLoggingWithTimeManagement')}">
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.extenededLog"/>
                        <tiles:put name="commandHelpKey" value="button.extenededLog.on.help"/>
                        <tiles:put name="bundle" value="personsBundle"/>
                        <tiles:put name="onclick" value="openExtendedLoggingWindow()"/>
                        <tiles:put name="viewType" value="blueBorder"/>
                    </tiles:insert>
                </c:when>
                <c:otherwise>
                    <tiles:insert definition="commandButton" flush="false" service="ClientExtendedLoggingManagement">
                        <tiles:put name="commandKey" value="button.extenededLog.on"/>
                        <tiles:put name="commandTextKey" value="button.extenededLog"/>
                        <tiles:put name="commandHelpKey" value="button.extenededLog.on.help"/>
                        <tiles:put name="bundle" value="personsBundle"/>
                        <tiles:put name="viewType" value="blueBorder"/>
                    </tiles:insert>
                </c:otherwise>
            </c:choose>
        </c:otherwise>
    </c:choose>


        <c:if test="${isActive or isCancellation}">
            <c:choose>
                <c:when test="${not empty person.login.blocks}">
                    <tiles:insert definition="commandButton" flush="false" operation="ChangeLockPersonOperation">
                        <tiles:put name="commandKey" value="button.unlock"/>
                        <tiles:put name="commandHelpKey" value="button.unlock.help"/>
                        <tiles:put name="bundle" value="personsBundle"/>
                        <tiles:put name="viewType" value="blueBorder"/>
                    </tiles:insert>
                </c:when>
                <c:otherwise>
                    <tiles:insert definition="clientButton" flush="false" operation="ChangeLockPersonOperation">
                        <tiles:put name="commandTextKey" value="button.lock"/>
                        <tiles:put name="commandHelpKey" value="button.lock"/>
                        <tiles:put name="bundle" value="personsBundle"/>
                        <tiles:put name="onclick" value="CallReasonWindow()"/>
                        <tiles:put name="viewType" value="blueBorder"/>
                    </tiles:insert>
                </c:otherwise>
            </c:choose>
            <tiles:insert definition="clientButton" flush="false" service="RestoreClientPasswordService">
                <tiles:put name="commandTextKey" value="button.sendnew.password"/>
                <tiles:put name="commandHelpKey" value="button.sendnew.password.help"/>
                <tiles:put name="bundle"         value="personsBundle"/>
                <tiles:put name="action"         value="/persons/restorepassword.do?id=${person.id}"/>
                <tiles:put name="viewType" value="blueBorder"/>
            </tiles:insert>
            <tiles:insert definition="clientButton" flush="false" service="GetRegistrationService">
                <tiles:put name="commandTextKey" value="button.createDisposableLogin"/>
                <tiles:put name="commandHelpKey" value="button.createDisposableLogin.help"/>
                <tiles:put name="bundle" value="personsBundle"/>
                <tiles:put name="onclick" value="CallLoginWindow()"/>
                <tiles:put name="viewType" value="blueBorder"/>
            </tiles:insert>
       </c:if>
        <c:if test="${isAgreementSign and not isNew}">
            <tiles:insert definition="commandButton" flush="false" operation="SignAgreementOperation">
                <tiles:put name="commandKey"     value="button.sign.agreement"/>
                <tiles:put name="commandHelpKey" value="button.sign.agreement.help"/>
                <tiles:put name="bundle"  value="personsBundle"/>
                <tiles:put name="confirmText" value="Вы уверены, что пользователь подписал заявление о предоставлении услуг?"/>
                <tiles:put name="viewType" value="blueBorder"/>
            </tiles:insert>
        </c:if>

        <c:if test="${not isNew and isTemplate}">
            <tiles:insert definition="commandButton" flush="false" operation="RegisterClientOperation">
                <tiles:put name="commandKey"     value="button.activate.person"/>
                <tiles:put name="commandHelpKey" value="button.activate.person.help"/>
                <tiles:put name="bundle"  value="personsBundle"/>
                <tiles:put name="viewType" value="blueBorder"/>
            </tiles:insert>
        </c:if>

        <c:if test="${isTemplate}">
            <tiles:insert definition="commandButton" flush="false" operation="EditPersonOperation">
                <tiles:put name="commandKey"     value="button.edit.return"/>
                <tiles:put name="commandHelpKey" value="button.edit.return.help"/>
                <tiles:put name="bundle"  value="personsBundle"/>
                <tiles:put name="viewType" value="blueBorder"/>
            </tiles:insert>
        </c:if>



        <c:if test="${not (isTemplate or isDeleted or isAgreementSign or editNotSupported)}">
            <c:if test="${isModified}">
                <tiles:insert definition="commandButton" flush="false" operation="RegisterPersonChangesOperation">
                    <tiles:put name="commandKey"     value="button.register.changes.person"/>
                    <tiles:put name="commandHelpKey" value="button.register.changes.person.help"/>
                    <tiles:put name="bundle"  value="personsBundle"/>
                    <tiles:put name="validationFunction">
                        confirmRegistration();
                    </tiles:put>
                    <tiles:put name="viewType" value="blueBorder"/>
                </tiles:insert>
            </c:if>
        </c:if>

    <c:if test="${not isNew and not editNotSupported}">
        <c:choose>
            <c:when test="${isTemplate or isAgreementSign}">
                <tiles:insert definition="commandButton" flush="false" operation="RemovePersonOperation">
                    <tiles:put name="commandKey"     value="button.remove.template.person"/>
                    <tiles:put name="commandHelpKey" value="button.remove"/>
                    <tiles:put name="bundle"  value="personsBundle"/>
                    <tiles:put name="confirmText"    value="Вы действительно хотите удалить клиента?"/>
                    <tiles:put name="viewType" value="blueBorder"/>
                </tiles:insert>
            </c:when>
            <c:otherwise>
                <tiles:insert definition="commandButton" flush="false" operation="RemovePersonOperation">
                    <tiles:put name="commandKey"     value="button.remove"/>
                    <tiles:put name="commandHelpKey" value="button.remove"/>
                    <tiles:put name="bundle"  value="personsBundle"/>
                    <tiles:put name="confirmText" value="Вы действительно хотите удалить клиента?. Все непроведенные платежи клиента будут отозваны."/>
                    <tiles:put name="viewType" value="blueBorder"/>
                </tiles:insert>
            </c:otherwise>
        </c:choose>
    </c:if>

    <c:if test="${isAgreementSign or not (isTemplate or isDeleted or isAgreementSign)}">
        <tiles:insert definition="commandButton" flush="false" operation="EditPersonOperation">
            <tiles:put name="commandKey"     value="button.partly.save.person"/>
            <tiles:put name="commandHelpKey" value="button.partly.save.person.help"/>
            <tiles:put name="bundle"  value="personsBundle"/>
            <tiles:put name="isDefault" value="true"/>
            <tiles:put name="postbackNavigation" value="true"/>
            <tiles:put name="validationFunction">
                validateOnSave();
            </tiles:put>
            <tiles:put name="viewType" value="blueBorder"/>
        </tiles:insert>
    </c:if>

    <c:if test="${phiz:impliesOperation('GetPersonsListOperation', '*')}">
        <tiles:insert definition="clientButton" flush="false">
            <tiles:put name="commandTextKey" value="button.close"/>
            <tiles:put name="commandHelpKey" value="button.close.help"/>
            <tiles:put name="bundle"         value="personsBundle"/>
            <tiles:put name="action"         value="/persons/list.do?field(excludeStartTest)=true"/>
			<tiles:put name="viewType" value="blueBorder"/>
        </tiles:insert>
    </c:if>
</tiles:put>

<tiles:put name="data" type="string">

	<table cellpadding="0" cellspacing="0" class="MaxSize">
        <tr>
            <td height="100%">
                <div class="MaxSize">

                    <%@ include file="editFields.jsp" %>

                    <input type="hidden" name="blockReason" id="blockReason" value=""/>
                    <input type="hidden" name="blockStartDate" id="blockStartDate" value=""/>
                    <input type="hidden" name="blockEndDate" id="blockEndDate" value=""/>

                    <input type="hidden" name="field(extendedLoggingEndDate)"   id="extendedLoggingEndDate"   value=""/>
                    <input type="hidden" name="field(termlessExtendedLogging)"  id="termlessExtendedLogging"  value=""/>

                    <script type="text/javascript">
                        function CallReasonWindow()
                        {
                            window.open("${phiz:calculateActionURL(pageContext, '/blocks/userblock.do')}", "", "width=1000,height=270,resizable=0,menubar=0,toolbar=0,scrollbars=yes");
                        }


                        function CallLoginWindow()
                        {
                            var url = "${phiz:calculateActionURL(pageContext,'/persons/disposableLogin')}";
                            ajaxQuery("personId=${person.id}", url, function (data)
                                    {
                                        if (data.error.length > 0)
                                            alert(data.error);
                                        else
                                            alert("Временный логин для входа в Сбербанк Онлайн: " + data.disposableLogin + ". Продиктуйте его клиенту. Новый пароль будет отправлен клиенту по SMS.");
                                    },
                                    "json"
                            );
                        }

                        function openExtendedLoggingWindow()
                        {
                            var win = window.open("${phiz:calculateActionURL(pageContext, '/log/extended/time.do')}", "", "width=1000,height=200,resizable=0,menubar=0,toolbar=0,scrollbars=1");
                            win.moveTo(screen.width / 2 - 375, screen.height / 2 - 200);
                        }

                        function sendExtendedLoggingOn(endDate, termless)
                        {
                            $('#extendedLoggingEndDate').val(endDate);
                            $('#termlessExtendedLogging').val(termless);

                            new CommandButton("button.extenededLog.on").click();
                        }

                        function setReason(reason, startDate, endDate)
                        {
                            var blockReason = document.getElementById("blockReason");
                            blockReason.value = reason;

                            var blockStartDate = document.getElementById("blockStartDate");
                            blockStartDate.value = startDate;
                            if (endDate!=null)
                            {
                                var blockEndDate = document.getElementById("blockEndDate");
                                blockEndDate.value = endDate;
                            }

                            var button = new CommandButton("button.lock", "");
                            button.click();
                        }
                        function confirmRegistration()
                        {
                            if (isDataChanged())
                            {
                                window.alert("Для  регистрации изменений необходимо сначала сохранить анкету клиента");
                                return false;
                            }
                            return true;
                        }

                        function validateOnSave()
                        {
                            postChangedFieldNames();
                            return true;
                        }

                        function printClientInfo(event, personId, operation)
                        {
                            if (personId != null && personId != '' && !isDataChanged())
                            {
                                openWindow(event, 'print.do?person=' + personId);
                            }
                            else
                            {
                                window.alert("Перед печатью анкеты клиента необходимо ее сохранить");
                            }
                        }

                        function printAdditionalContract (event, personId)
                        {
                            if (personId != null && personId != '' && !isDataChanged())
                            {
                               openWindow(event,'printContract9.do?person=' + personId,'5',null);
                               openWindow(event,'printContract8_pr5.do?person=' + personId,'2',null);
                              }
                            else
                            {
                                window.alert("Перед печатью договора необходимо сохранить анкету клиента");
                            }
                        }
                    </script>
                </div>
            </td>
        </tr>
	</table>
</tiles:put>
</tiles:insert>
</html:form>