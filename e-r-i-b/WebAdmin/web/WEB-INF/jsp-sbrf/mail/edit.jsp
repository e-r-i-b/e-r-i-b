<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<c:set var="form"           value="${EditMailForm}"/>
<c:set var="textLength"     value="${form.employeeTextLength}"/>
<c:set var="sender"         value="${form.fields.sender}"/>                      <%-- отправитель, проинициализировано когда смотрим пришедшее письмо --%>
<c:set var="isNotAnswer"    value="${form.fields.isNotAnswer}"/>                 <%-- не ответ (parentId == null) --%>
<c:set var="mailSavedState" value="${form.fields.mailState}"/>                   <%-- статус письма --%>
<c:set var="view"           value="${mailSavedState == 'NEW'}"/>                 <%-- письмо отправлено или пришло (просмотр письма) --%>
<c:set var="isNew"          value="${mailSavedState == 'TEMPLATE'}"/>            <%-- новое письмо --%>
<c:set var="reply"          value="${mailSavedState != 'NEW' && !isNotAnswer}"/> <%-- ответ -- новый ответ или черновик ответа (не отправленный ответ) --%>
<c:set var="sendingReply"   value="${view && !isNotAnswer}"/>                    <%-- отправленный ответ --%>
<c:set var="draft"          value="${mailSavedState == 'EMPLOYEE_DRAFT'}"/>      <%-- черновик --%>
<c:set var="newMailDraft"   value="${draft && isNotAnswer}"/>                    <%-- черновик нового письма --%>
<c:set var="message_empty">
    <bean:message bundle="mailBundle" key="label.message.empty"/>
</c:set>
<c:set var="subject_empty">
    <bean:message bundle="mailBundle" key="label.subject.empty"/>
</c:set>

<html:form action="/mail/edit" enctype="multipart/form-data" onsubmit="return setEmptyAction(event);">
    <tiles:insert definition="mailEdit">
        <tiles:put name="submenu" type="string" value="SentMailList"/>
        <tiles:put name="menu" type="string">
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.cancel"/>
                <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                <tiles:put name="bundle" value="mailBundle"/>
                <tiles:put name="action" value="/mail/list.do"/>
                <c:if test="${isNew}">
                    <tiles:put name="onclick" value="removeCurrentNewMail();"/>
                </c:if>
                <tiles:put name="viewType" value="blueBorder"/>
            </tiles:insert>
        </tiles:put>
        <tiles:put name="data" type="string">

            <c:set var="isMultiBlock" value="${phiz:isMailMultiBlockMode()}"/>

            <c:choose>
                <c:when test="${isMultiBlock}">
                    <c:set var="personListURL" value="${phiz:calculateActionURL(pageContext, '/persons/active/list/full')}"/>
                </c:when>
                <c:otherwise>
                    <c:set var="personListURL" value="${phiz:calculateActionURL(pageContext, '/persons/activeList')}"/>
                </c:otherwise>
            </c:choose>

            <script type="text/javascript">
                <c:if test="${isNew}">
                    function removeCurrentNewMail()
                    {
                        ajaxQuery("id=${form.id}", "${phiz:calculateActionURL(pageContext, '/private/async/mail/remove')}", function(data){}, null, true);
                        return true;
                    }

                    function redirectResolved()
                    {
                        removeCurrentNewMail();
                        return true;
                    }
                </c:if>

                function initFunc()
                {
                    document.getElementById("cancelButton").style.display = "none";
                    rollBack();
                }

                function choiceRecipient()
                {
                    var h = getClientHeight - 100;
                    var w = getClientWidth - 100;
                    var winpar = "fullscreen=0,location=0,menubar=0,status=0,toolbar=0,resizable=1" +
                                 ", width=" + w +
                                 ", height=" + h +
                                 ", left=50" +
                                 ", top=50";
                    var pwin = openWindow(null, "${personListURL}", "dialog2", winpar);
                    pwin.focus();
                }

                function setGroupData(groupData)
                {
                    <c:choose>
                        <c:when test="${isMultiBlock}">
                            setElement('field(firstName)',  groupData["firstName"]);
                            setElement('field(patrname)',   groupData["patrname"]);
                            setElement('field(surname)',    groupData["surname"]);
                            setElement('field(passport)',   groupData["passport"]);
                            setElement('field(birthDate)',  groupData["birthDate"]);
                            setElement('field(tb)',         groupData["tb"]);
                            setElement('field(nodeId)',     groupData["nodeId"]);
                        </c:when>
                        <c:otherwise>
                            $("#recipientIdField").val(groupData["loginId"]);
                        </c:otherwise>
                    </c:choose>
                    setElement('field(recipient)', groupData["name"]);
                }

                function setAnswer()
                {
                    $('#newText').val($('#response_by_phone').val());
                }

                function createMail(type)
                {
                     $("#mailState").val(type);
                     var newText = document.getElementById("newText");
                     var oldText = document.getElementById("oldText");
                     //Одновременно отсутствовать поля newText и oldText не могут.
                     var text =trim(newText == null ? oldText.value : newText.value);
                     var Subject = trim(document.getElementById("subject").value);
                     <c:if test="${empty sender and not reply}">
                        var Recipient = document.getElementById("recipient").value;
                     </c:if>
                     if(text.length <= 0)
                     {
                         var mes = "${message_empty}";
                         alert(mes);
                         clearLoadMessage();
                     }
                     else if(Subject.length <= 0)
                     {
                         var mes = "${subject_empty}";
                         alert(mes);
                         clearLoadMessage();
                     }
                     <c:if test="${empty sender and not reply}">
                     else if(Recipient.length <= 0)
                     {
                         alert("Введите значение в поле Получатель");
                         clearLoadMessage();
                     }
                     </c:if>
                     else
                     {
                        new CommandButton('button.save').click();
                     }
                }

                function newAttachFile()
                {
                    document.getElementById("attachField").style.display = "";
                    document.getElementById("oldFileField").style.display = "none";
                    $("#setNewFile").val('true');
                }

                function removeAttachFile()
                {
                    newAttachFile();
                    document.getElementById("rollBackButton").style.display = "none";
                    document.getElementById("cancelButton").style.display = "";
                }

                function rollBack()
                {
                    document.getElementById("attachField").style.display = "none";
                    document.getElementById("oldFileField").style.display = "";
                    $("#setNewFile").val('false');
                    cancel();
                }

                function cancel()
                {
                    var elem = document.getElementById('FileField');
                    elem.parentNode.removeChild(elem);
                    var attach = document.getElementById('attachField');
                    attach.innerHTML= '<html:file property="field(file)" size="60" styleId="FileField" styleClass="float"/>' + attach.innerHTML;
                }

                $(document).ready(function()
                {

                    if(document.getElementById("newText") != null)
                        initAreaMaxLengthRestriction("newText", ${textLength}, true);
                    if(document.getElementById("oldText") != null)
                        initAreaMaxLengthRestriction("oldText", ${textLength}, true);

                    if(!isIE())
                        $('div .blocker').remove();
                });

            </script>
            <html:hidden property="id"/>
            <c:if test="${isMultiBlock}">
                <html:hidden property="field(firstName)"/>
                <html:hidden property="field(patrname)"/>
                <html:hidden property="field(surname)"/>
                <html:hidden property="field(passport)"/>
                <html:hidden property="field(birthDate)"/>
                <html:hidden property="field(tb)"/>
                <html:hidden property="field(nodeId)"/>
            </c:if>
            <html:hidden property="field(recipientId)"       styleId="recipientIdField"/>
            <html:hidden property="field(mailState)"/>
            <html:hidden property="field(isNotAnswer)"/>
            <html:hidden property="field(newMailState)"      styleId="mailState"/>
            <html:hidden property="field(response_by_phone)" styleId="response_by_phone"/>
            <html:hidden property="field(type)"/>
            <html:hidden property="field(mail_theme)"/>
            <html:hidden property="field(response_method)"/>
            
            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="name">
                    <c:choose>
                        <c:when test="${reply}"><bean:message key="reply.title" bundle="mailBundle"/></c:when>
                        <c:when test="${isNew}"><bean:message key="edit.title" bundle="mailBundle"/></c:when>
                    </c:choose>
                </tiles:put>
                <tiles:put name="data">

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.number" bundle="mailBundle"/>
                        </tiles:put>
                        <tiles:put name="needMargin" value="true"/>
                        <tiles:put name="data">
                            <b>${form.fields.num}<html:hidden property="field(num)"/></b>
                        </tiles:put>
                    </tiles:insert>

                    <c:if test="${reply or sendingReply}">
                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message key="label.mail.theme" bundle="mailBundle"/>
                            </tiles:put>
                            <tiles:put name="data">
                                <input type="text" value="${form.fields.mail_theme}" size="35" readonly="true" maxlength="100" id="mail_theme"/>
                            </tiles:put>
                        </tiles:insert>
                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message key="response_method" bundle="mailBundle"/>
                            </tiles:put>
                            <tiles:put name="isNecessary" value="true"/>
                            <tiles:put name="data">
                                <input type="text" value="${form.fields.response_method}" size="35" readonly="true" maxlength="40" id="response_method"/>
                            </tiles:put>
                        </tiles:insert>
                        <c:choose>
                            <c:when test="${not empty form.fields.phone}">
                                <tiles:insert definition="simpleFormRow" flush="false">
                                    <tiles:put name="title">
                                        <bean:message key="label.phone" bundle="mailBundle"/>
                                    </tiles:put>
                                    <tiles:put name="data">
                                        <html:text property="field(phone)"  size="35" readonly="true"/>
                                    </tiles:put>
                                </tiles:insert>
                            </c:when>
                            <c:when test="${not empty form.fields.email}">
                                <tiles:insert definition="simpleFormRow" flush="false">
                                    <tiles:put name="title">
                                        <bean:message key="label.email" bundle="mailBundle"/>
                                    </tiles:put>
                                    <tiles:put name="data">
                                        <html:text property="field(email)"  size="35" readonly="true"/>
                                    </tiles:put>
                                </tiles:insert>
                            </c:when>
                        </c:choose>
                    </c:if>
                    <c:set var="senderOrRecipient" value="label.client"/>
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="${senderOrRecipient}" bundle="mailBundle"/>
                        </tiles:put>
                        <tiles:put name="needMargin" value="true"/>
                        <tiles:put name="data">
                            <c:choose>
                                <c:when test="${reply}">
                                    <html:hidden property="field(recipient)"/>
                                    <phiz:link action="/persons/edit"
                                               operationClass="ViewPersonOperation">
                                        <phiz:param name="person" value="${form.fields.recipientId}"/>
                                        <c:out value="${form.fields.recipient}"/>
                                    </phiz:link>
                                </c:when>
                                <c:otherwise>
                                    <html:text property="field(recipient)" readonly="true" size="60" styleId="recipient"/>
                                    <input type="button"
                                           class="buttWhite smButt"
                                           onclick="choiceRecipient();"
                                           value="..."/>
                                </c:otherwise>
                            </c:choose>
                        </tiles:put>
                    </tiles:insert>
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.subject" bundle="mailBundle"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:text property="field(subject)" size="60" maxlength="40" styleId="subject"/>
                        </tiles:put>
                    </tiles:insert>
                    <c:if test="${not form.fields.setNewFile}">
                        <c:set var="file" value="${form.fields.file}"/>
                    </c:if>
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.attach" bundle="mailBundle"/>
                        </tiles:put>
                        <tiles:put name="data">
                            <html:hidden property="field(setNewFile)" styleId="setNewFile"/>
                            <div id="attachField">
                                <html:file property="field(file)" size="70" styleId="FileField" onchange="$('#setNewFile').val('true');" styleClass="float"/>
                                <div class="blocker"></div>
                                <c:set var="funcName" value="cancel()"/>
                                <c:if test="${!empty file}">
                                    <c:set var="funcName" value="rollBack()"/>
                                </c:if>

                               <%-- <input id="rollBackButton" type="button" class="buttWhite" style="height:21px;width:70px;"
                                       onclick="${funcName};" value="Отменить"/>--%>
                                <div id="rollBackButton">
                                    <tiles:insert definition="clientButton" flush="false">
                                        <tiles:put name="commandTextKey" value="button.cancel"/>
                                        <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                                        <tiles:put name="bundle" value="mailBundle"/>
                                        <tiles:put name="onclick" value="${funcName};"/>
                                        <tiles:put name="viewType" value="buttonGrayNew"/>
                                    </tiles:insert>
                                </div>

                                <%--<input id="cancelButton" type="button" class="buttWhite" style="height:21px;width:70px;"
                                       onclick="cancel();" value="Отменить"/>--%>
                                <div id="cancelButton" style="display:none;">
                                    <tiles:insert definition="clientButton" flush="false">
                                        <tiles:put name="commandTextKey" value="button.cancel"/>
                                        <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                                        <tiles:put name="bundle" value="mailBundle"/>
                                        <tiles:put name="onclick" value="cancel();"/>
                                    </tiles:insert>
                                </div>


                            </div>
                            <c:if test="${not empty file}">
                                <div id="oldFileField">
                                    <html:link onclick="new CommandButton('button.unload').click('', true);" href="#">
                                        <bean:write name='form' property="field(fileName)"/>
                                    </html:link>
                                    <input type="button" class="buttWhite" style="height:25px;width:80px;"
                                           onclick="newAttachFile();" value="Заменить"/>
                                    <input type="button" class="buttWhite" style="height:25px;width:80px;"
                                           onclick="removeAttachFile();" value="Удалить"/>
                                </div>
                                <br/>
                                <script type="text/javascript">
                                    doOnLoad(function()
                                    {
                                        initFunc();
                                    });
                                </script>
                            </c:if>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="mailBundle" key="label.message"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <c:choose>
                                <c:when test="${reply}">
                                    <html:textarea styleId="newText" property="field(newText)" cols="58" rows="10" style="text-align:justify;"/>
                                    <html:hidden property="field(body)" value="E"/>
                                </c:when>
                                <c:otherwise>
                                    <html:textarea styleId="oldText" property="field(body)" cols="58" rows="10" style="text-align:justify;"/>
                                    <html:hidden property="field(newText)" value="E"/>
                                </c:otherwise>
                            </c:choose>
                        </tiles:put>
                    </tiles:insert>

                    <c:if test="${reply && not empty form.fields.correspondence}">
                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message bundle="mailBundle" key="label.message.history"/>
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
                </tiles:put>

                <tiles:put name="buttons">
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.close"/>
                        <tiles:put name="commandHelpKey" value="button.close.help"/>
                        <tiles:put name="image" value=""/>
                        <tiles:put name="bundle" value="mailBundle"/>
                        <tiles:put name="action" value="/mail/sentList.do'}"/>
                        <c:if test="${isNew}">
                            <tiles:put name="onclick" value="removeCurrentNewMail();"/>
                        </c:if>
                    </tiles:insert>
                    <c:if test="${reply}">
                        <tiles:insert definition="clientButton" flush="false">
                            <tiles:put name="commandTextKey" value="button.by_phone"/>
                            <tiles:put name="commandHelpKey" value="button.by_phone"/>
                            <tiles:put name="image" value=""/>
                            <tiles:put name="bundle" value="mailBundle"/>
                            <tiles:put name="onclick" value="setAnswer();"/>
                        </tiles:insert>
                    </c:if>
                    <c:choose>
                        <c:when test="${isNew}">
                            <tiles:insert definition="clientButton" flush="false">
                                <tiles:put name="commandTextKey" value="button.save"/>
                                <tiles:put name="commandHelpKey" value="button.save.help"/>
                                <tiles:put name="bundle" value="mailBundle"/>
                                <tiles:put name="onclick" value="createMail('EMPLOYEE_DRAFT')"/>
                            </tiles:insert>
                            <tiles:insert definition="clientButton" flush="false">
                                <tiles:put name="commandTextKey" value="button.send"/>
                                <tiles:put name="commandHelpKey" value="button.send.help"/>
                                <tiles:put name="bundle" value="mailBundle"/>
                                <tiles:put name="onclick" value="createMail('NEW')"/>
                            </tiles:insert>
                            <script type="text/javascript">
                                setPostbackNavigationButton(createClientButton("button.send", "button.send", function(){createMail('EMPLOYEE_DRAFT');}));
                            </script>
                        </c:when>
                        <c:otherwise>
                            <c:if test="${draft}">
                                <tiles:insert definition="clientButton" flush="false">
                                    <tiles:put name="commandTextKey" value="button.save"/>
                                    <tiles:put name="commandHelpKey" value="button.save.help"/>
                                    <tiles:put name="bundle" value="mailBundle"/>
                                    <tiles:put name="onclick" value="createMail('SAVE_DRAFT')"/>
                                </tiles:insert>
                            <tiles:insert definition="clientButton" flush="false">
                                <tiles:put name="commandTextKey" value="button.send"/>
                                <tiles:put name="commandHelpKey" value="button.send.help"/>
                                <tiles:put name="bundle" value="mailBundle"/>
                                <tiles:put name="onclick" value="createMail('SEND_DRAFT')"/>
                            </tiles:insert>
                                <script type="text/javascript">
                                    setPostbackNavigationButton(createClientButton("button.send", "button.send", function(){createMail('SAVE_DRAFT');}));
                                </script>
                            </c:if>
                        </c:otherwise>
                    </c:choose>
                </tiles:put>
                <tiles:put name="alignTable" value="center"/>
            </tiles:insert>

            <script type="text/javascript">
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
                    <c:if test="${form.fields.setNewFile}">
                        alert('<bean:message key="message.repeat-fill-file" bundle="mailBundle"/>');
                    </c:if>
                });
            </script>
        </tiles:put>
    </tiles:insert>
</html:form>