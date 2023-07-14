<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>

<html:form action="/mail/view" enctype="multipart/form-data" onsubmit="return setEmptyAction(event);">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="sender" value="${form.sender}"/>
    <c:set var="isERKC" value="${form.erkc}"/>
    <c:choose>
        <c:when test="${isERKC}">
            <c:set var="definitionName" value="erkcMain"/>
            <c:set var="mailListUrl" value="/erkc/mail/list.do"/>
        </c:when>
        <c:otherwise>
            <c:set var="definitionName" value="mailEdit"/>
            <c:set var="mailListUrl" value="/mail/list.do"/>
        </c:otherwise>
    </c:choose>

    <tiles:insert definition="${definitionName}">
        <tiles:put type="string" name="needSave" value="false"/>
        <tiles:put name="submenu" type="string" value="MailList"/>

        <tiles:put name="menu" type="string">
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.cancel"/>
                <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                <tiles:put name="bundle" value="mailBundle"/>
                <tiles:put name="action" value="${mailListUrl}"/>
                <tiles:put name="viewType" value="blueBorder"/>
            </tiles:insert>
        </tiles:put>
        <tiles:put name="data" type="string">
            <tiles:insert definition="paymentForm" flush="false">
                <c:set var="canReassign"  value="${phiz:impliesService('ContactCenterEmployeeManagment')}"/>
                <tiles:put name="name"><bean:message key="view.title" bundle="mailBundle"/></tiles:put>
                <tiles:put name="data">
                    <c:set var="mail" value="${form.mail}"/>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.number" bundle="mailBundle"/>
                        </tiles:put>
                        <tiles:put name="needMargin" value="true"/>
                        <tiles:put name="data">
                            ${mail.num}
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.mail.theme" bundle="mailBundle"/>
                        </tiles:put>
                        <tiles:put name="data">
                            <c:set var="themeDescription" value=""/>
                            <c:if test="${not empty mail.theme}">
                                <c:set var="themeDescription" value="${mail.theme.description}"/>
                            </c:if>
                            <input type="text" value="${themeDescription}" readonly="true" size="35" maxlength="100"/>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="response_method" bundle="mailBundle"/>
                        </tiles:put>
                        <tiles:put name="data">
                            <c:set var="responseMethodDescr" value=""/>
                            <c:if test="${not empty mail.responseMethod}">
                                <c:set var="responseMethodDescr" value="${mail.responseMethod.description}"/>
                            </c:if>
                            <input type="text" value="${responseMethodDescr}" size="35" readonly="true" maxlength="40"/>
                        </tiles:put>
                    </tiles:insert>


                    <c:choose>
                        <c:when test="${not empty mail.phone}">
                            <tiles:insert definition="simpleFormRow" flush="false">
                                <tiles:put name="title">
                                    <bean:message key="label.phone" bundle="mailBundle"/>
                                </tiles:put>
                                <tiles:put name="data">
                                    <html:text name="mail" property="phone" size="35" readonly="true"/>
                                </tiles:put>
                            </tiles:insert>
                        </c:when>
                        <c:when test="${not empty mail.email}">
                            <tiles:insert definition="simpleFormRow" flush="false">
                                <tiles:put name="title">
                                    <bean:message key="label.email" bundle="mailBundle"/>
                                </tiles:put>
                                <tiles:put name="data">
                                    <html:text name="mail" property="email"  size="35" readonly="true"/>
                                </tiles:put>
                            </tiles:insert>
                        </c:when>
                    </c:choose>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.mailType" bundle="mailBundle"/>
                        </tiles:put>
                        <tiles:put name="data">
                            <html:select name="mail" property="type" styleClass="select" disabled="true">
                                <html:option value="${mail.type}"><bean:message key="mailType.${mail.type}" bundle="mailBundle"/></html:option>
                            </html:select>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.sender" bundle="mailBundle"/>
                        </tiles:put>
                        <tiles:put name="needMargin" value="true"/>
                        <tiles:put name="data">
                            <phiz:link action="/persons/edit"
                                       operationClass="ViewPersonOperation">
                                <phiz:param name="person" value="${sender.id}"/>
                                <bean:write name="sender" property="fullName"/>
                            </phiz:link>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.subject" bundle="mailBundle"/>
                        </tiles:put>
                        <tiles:put name="data">
                            <html:text name="mail" property="subject" size="60" readonly="true" maxlength="40" styleId="subject"/>
                        </tiles:put>
                    </tiles:insert>
                    <c:set var="file" value="${mail.data}"/>
                    <c:if test="${not empty file}">
                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="data">
                                <html:link onclick="new CommandButton('button.unload').click();" href="#">
                                    <bean:write name='mail' property="fileName"/>
                                </html:link>
                            </tiles:put>
                        </tiles:insert>
                    </c:if>
                    <c:if test="${not empty form.fields.correspondence}">
                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message bundle="mailBundle" key="label.message"/>
                            </tiles:put>
                            <tiles:put name="data">
                                <div class="textareaImitate">
                                    <c:forEach items="${form.fields.correspondence}" var="listElem" >
                                        <i style="color:gray">
                                            <bean:write name="listElem" property="date.time" format="dd.MM.yyyy hh:mm"/>
                                            <c:choose>
                                                <c:when test="${listElem.direction == 'CLIENT'}">
                                                    <c:set var="emplFIO" value="${phiz:getEmployeeFIO(listElem.sender)}"/>
                                                    <c:if test="${empty emplFIO}">
                                                        <bean:message key="label.you" bundle="mailBundle"/>
                                                    </c:if>
                                                    <c:out value="${emplFIO}"/>
                                                </c:when>
                                                <c:otherwise>
                                                    <bean:message key="label.bankClient" bundle="mailBundle"/>
                                                </c:otherwise>
                                            </c:choose>
                                        </i><br/>
                                        <c:out value="${listElem.body}"/><br/>
                                        <hr color=#9AABAE noshade size="1" width="500" align="left"/>
                                    </c:forEach>
                                </div>
                            </tiles:put>
                        </tiles:insert>
                    </c:if>
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="data">
                            <tiles:insert definition="clientButton" flush="false">
                                <tiles:put name="commandTextKey" value="button.plainText"/>
                                <tiles:put name="commandHelpKey" value="button.plainText"/>
                                <tiles:put name="bundle" value="mailBundle"/>
                                <tiles:put name="onclick" value="showPlainText()"/>
                                <tiles:put name="viewType" value="buttonGrayNew"/>
                            </tiles:insert>
                        </tiles:put>
                    </tiles:insert>

                    <div class="plainText" style="display:none">
                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="data">
                                <textarea id="plainText"  cols="58" rows="10" readonly="readonly" id="oldText" style="text-align:justify;">
                                    <c:out value="${phiz:getPlainTextListMail(form.fields.correspondence)}"/>
                                </textarea>
                            </tiles:put>
                        </tiles:insert>
                    </div>
                    <div class="plainText" style="display:none">
                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="data">
                                <tiles:insert definition="clientButton" flush="false">
                                    <tiles:put name="commandTextKey" value="button.hide"/>
                                    <tiles:put name="commandHelpKey" value="button.hide"/>
                                    <tiles:put name="bundle" value="mailBundle"/>
                                    <tiles:put name="viewType" value="simpleLink"/>
                                    <tiles:put name="onclick" value="hidePlainText()"/>
                                </tiles:insert>
                            </tiles:put>
                        </tiles:insert>
                    </div>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.FIO" bundle="mailBundle"/>
                        </tiles:put>
                        <tiles:put name="data">
                            <html:text property="fields(employeeFIO)" readonly="true"/>
                            <c:if test="${canReassign}">
                                <a href="#" onclick="openContactCenterEmployeeDictionary(setEmployeeInfo);">
                                    <bean:message key="label.reassign" bundle="mailBundle"/>
                                </a>
                                <html:hidden property="fields(employeeLoginId)" styleId="employeeLoginId"/>
                            </c:if>
                        </tiles:put>
                    </tiles:insert>

                    <c:if test="${canReassign}">
                        <fieldset class="reassign displayNone">

                            <tiles:insert definition="simpleFormRow" flush="false">
                                <tiles:put name="title">
                                    <bean:message key="label.new.fio" bundle="mailBundle"/>:
                                </tiles:put>
                                <tiles:put name="data">
                                    <html:text property="fields(employeeFIO)" readonly="true" styleId="employeeFIO"/>
                                </tiles:put>
                            </tiles:insert>

                            <tiles:insert definition="simpleFormRow" flush="false">
                                <tiles:put name="title">
                                    <bean:message key="label.reassign.reason" bundle="mailBundle"/>:
                                </tiles:put>
                                <tiles:put name="data">
                                    <html:textarea property="fields(reassignReason)" cols="50"/>
                                </tiles:put>
                            </tiles:insert>

                            <tiles:insert definition="simpleFormRow" flush="false">
                                <tiles:put name="data">
                                    <tiles:insert definition="clientButton" flush="false">
                                        <tiles:put name="commandTextKey" value="button.close"/>
                                        <tiles:put name="commandHelpKey" value="button.close.help"/>
                                        <tiles:put name="image" value=""/>
                                        <tiles:put name="bundle" value="mailBundle"/>
                                        <tiles:put name="onclick" value="rollBackReassign();"/>
                                    </tiles:insert>
                                    <tiles:insert definition="commandButton" flush="false" service="ContactCenterEmployeeManagment">
                                        <tiles:put name="commandKey" value="button.save"/>
                                        <tiles:put name="commandHelpKey" value="button.save.help"/>
                                        <tiles:put name="bundle" value="mailBundle"/>
                                    </tiles:insert>
                                </tiles:put>
                            </tiles:insert>

                        </fieldset>
                    </c:if>
                </tiles:put>

                <tiles:put name="buttons">
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.close"/>
                        <tiles:put name="commandHelpKey" value="button.close.help"/>
                        <tiles:put name="image" value=""/>
                        <tiles:put name="bundle" value="mailBundle"/>
                        <tiles:put name="action" value="${mailListUrl}"/>
                    </tiles:insert>
                    <c:if test="${form.fields.canReply}">
                        <tiles:insert definition="commandButton" flush="false" operation="EditMailOperation" service="MailManagment">
                            <tiles:put name="commandKey" value="button.reply"/>
                            <tiles:put name="commandHelpKey" value="button.reply.help"/>
                            <tiles:put name="bundle" value="mailBundle"/>
                        </tiles:insert>
                    </c:if>
                </tiles:put>
            </tiles:insert>
            <c:if test="${canReassign}">
                <tiles:insert definition="tableTemplate" flush="false">
                    <tiles:put name="id" value="reassignReasonHistory"/>
                    <tiles:put name="text" value="История назначений"/>
                    <tiles:put name="grid">
                         <sl:collection id="listElement" model="list"  property="fields(reassignHistory)" bundle="mailBundle">
                             <sl:collectionItem title="label.date.reassign">
                                 <c:if test="${not empty listElement.date}">
                                     <bean:write name="listElement" property="date.time" format="dd.MM.yyyy HH:mm"/>
                                 </c:if>
                             </sl:collectionItem>
                             <sl:collectionItem title="label.FIO">
                                 <c:out value="${listElement.employeeFIO}"/>
                             </sl:collectionItem>
                             <sl:collectionItem title="label.reassign.reason">
                                  <c:out value="${listElement.reassignReason}"/>
                             </sl:collectionItem>
                         </sl:collection>
                    </tiles:put>
                    <tiles:put name="isEmpty" value="${empty form.fields.reassignHistory}"/>
                    <tiles:put name="emptyMessage" value="Письмо не переназначалось."/>
                </tiles:insert>
            </c:if>
            <script type="text/javascript">
                function showPlainText()
                {
                    $('.plainText').show();
                    var txt = $('#plainText');
                    txt.focus();
                    txt.select();
                }
                function hidePlainText()
                {
                    var txt = $('.plainText');
                    txt.hide();
                }
                function openContactCenterEmployeeDictionary(callback)
                {
                    window.setEmployeeInfo = callback;
                    var h = 600;
                    var w = 1000;

                    var winpar = "fullscreen=0,location=0,menubar=0,status=0,toolbar=0,resizable=1, scrollbars=1" +
                                 ", width=" + w +
                                 ", height=" + h +
                                 ", left=" + (getClientWidth() - w) / 2 +
                                 ", top=" + (getClientHeight() - h) / 2;
                    var pwin = openWindow(null, "${phiz:calculateActionURL(pageContext, '/contact/center/dictionary/employee.do')}", "dialog2", winpar);
                    pwin.focus();
                }

                function setEmployeeInfo(data)
                {
                    $('#employeeLoginId').val(data['loginId']);
                    $('#employeeFIO').val(data['FIO']);
                    $('.reassign').removeClass('displayNone');
                }

                function rollBackReassign()
                {
                    $('.reassign').addClass('displayNone');
                }

                doOnLoad(function()
                {
                    var cancelButton = document.getElementById("cancelButton");
                    if (cancelButton != null)
                    {
                        cancelButton.style.display = "none";
                    }
                    if (${form.fields.relocateToDownload != null && form.fields.relocateToDownload == true})
                    {
                        <c:set var="downloadFileURL" value="${phiz:calculateActionURL(pageContext,'/mail/downloading')}"/>
                        clientBeforeUnload.showTrigger=false;
                        goTo('${downloadFileURL}');
                        clientBeforeUnload.showTrigger=false;
                    }
                });
            </script>
        </tiles:put>
    </tiles:insert>
</html:form>