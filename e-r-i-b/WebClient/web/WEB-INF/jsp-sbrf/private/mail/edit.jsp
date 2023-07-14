<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>


<c:set var="imagePath"      value="${skinUrl}/images"/>
<c:set var="textLength"     value="${form.clientTextLength}"/>
<c:set var="sender"         value="${form.fields.sender}"/>                  <%-- отправитель, проинициализировано когда смотрим пришедшее письмо --%>
<c:set var="mailSavedState" value="${form.fields.mailState}"/>               <%-- статус письма --%>
<c:set var="isNotAnswer"    value="${form.fields.isNotAnswer}"/>             <%-- не ответ (parentId == null) --%>
<c:set var="isNew"            value="${mailSavedState == 'TEMPLATE'}"/>        <%-- новое письмо --%>
<c:set var="view"           value="${mailSavedState == 'NEW'}"/>             <%-- письмо отправлено или пришло (просмотр письма) --%>
<c:set var="incomingView"   value="${not empty sender}"/>                    <%-- просматриваем входящее письмо --%>
<c:set var="reply"          value="${!view && !isNotAnswer}"/>               <%-- ответ -- новый ответ или черновик ответа (не отправленный ответ) --%>
<c:set var="sendingReply"   value="${view && !isNotAnswer}"/>                <%-- отправленный ответ --%>
<c:set var="draft"          value="${mailSavedState == 'CLIENT_DRAFT'}"/>    <%-- черновик --%>
<c:set var="newMailDraft"   value="${draft && isNotAnswer}"/>                <%-- черновик нового письма --%>

<tiles:insert definition="mailEdit">
    <tiles:put name="breadcrumbs">
        <tiles:insert definition="breadcrumbsLink" flush="false">
            <tiles:put name="main" value="true"/>
            <tiles:put name="action" value="/private/accounts.do"/>
        </tiles:insert>
        <tiles:insert definition="breadcrumbsLink" flush="false">
            <tiles:put name="name"><bean:message key="list.title" bundle="mailBundle"/></tiles:put>
            <tiles:put name="action" value="/private/mail/sentList.do"/>
        </tiles:insert>
        <tiles:insert definition="breadcrumbsLink" flush="false">
            <tiles:put name="name">
                <c:choose>
                    <c:when test="${view or reply}">
                        <c:out value="${form.fields.subject}"/>
                    </c:when>
                    <c:otherwise>
                        <bean:message key="edit.title" bundle="mailBundle"/>
                    </c:otherwise>
                </c:choose>
            </tiles:put>
           <tiles:put name="last" value="true"/>
        </tiles:insert>
    </tiles:put>

    <tiles:put name="data" type="string">
        <tiles:insert page="/WEB-INF/jsp/common/layout/emailValidation.jsp" flush="false"/>
        <script type="text/javascript">
             var REGEXP_FOR_PHONE_NUMBER =  /^\+7( )?\(((\d{3}\)( )?\d{7})|(\d{4}\)( )?\d{6})|(\d{5}\)( )?\d{5}))$/;
             var REGEXP_FOR_MAIL = /^[^\sа-яА-Я]+@[^\sа-яА-Я]+\.[^\sа-яА-Я]+$/;
             var descriptionMessages = new Array(6);

             descriptionMessages['CONSULTATION'] = '<bean:message bundle="commonBundle" key="text.mail.descriptionMessage.consultation"/>';
             descriptionMessages['COMPLAINT'] ='<bean:message bundle="commonBundle" key="text.mail.descriptionMessage.complaint"/>';
             descriptionMessages['CLAIM'] = '<bean:message bundle="commonBundle" key="text.mail.descriptionMessage.claim"/>';
             descriptionMessages['GRATITUDE'] = "Можно выразить благодарность за хорошее обслуживание.";
             descriptionMessages['IMPROVE'] = '<bean:message bundle="commonBundle" key="text.mail.descriptionMessage.improve"/>';
             descriptionMessages['OTHER'] = "Прочее.";

             var phoneNumberFormatErrorMessage = '<bean:message bundle="mailBundle" key="message.phone-number-format.error"/>';
             var phoneHint = '<bean:message bundle="mailBundle" key="label.phone"/>';

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

             function showPhoneOrMailField(first)
             {
                 var response_method = $('#response_method_select :selected').val();
                 if(response_method == 'BY_PHONE')
                 {
                     $('#eMailSpan').hide();
                     $('#phone').show();
                     $('#phone_response_method').show();
                     $('#phone_response_method').html(phoneHint);
                 }
                 else
                 {
                     $('#eMailSpan').show();
                     $('#phone').hide();
                     $('#phone_response_method').html('');
                     if(!first)
                     {
                        win.open('eMailConfirmWindow');
                     }
                 }
             }

             function saveEMail()
             {
                 var first = $('#firstEMail');
                 var second = $('#secondEMail');
                 var msg = validateEmail(first, 'E-mail');
                 if(isNotEmpty(msg))
                 {
                     removeAllErrors('eMailwarningMessages');
                     addError(msg, 'eMailwarningMessages');
                     return;
                 }
                 if(first.val() != second.val())
                 {
                     removeAllErrors('eMailwarningMessages');
                     addError('Вы неправильно указали E-mail. Пожалуйста, проверьте заполнение данного поля. Адреса электронной почты должны совпадать.', 'eMailwarningMessages');
                     return;
                 }
                 removeAllErrors('eMailwarningMessages');
                 $('#email').val(first.val());
                 win.close('eMailConfirmWindow');
             }

             function createMail(type)
             {
                 $("#mailState").val(type);
                 var responseMethod = $('#response_method_select').val();
                 var newText = document.getElementById("newText");
                 var oldText = document.getElementById("oldText");
                 var subject = trim(document.getElementById("subject").value);
                 var phone = document.getElementById("phone").value;
                 var email = document.getElementById("email").value;
                 //Одновременно отсутствовать поля newText и oldText не могут.
                 var text = trim(newText == null ? oldText.value : newText.value);

                 if(text.length <= 0)
                 {
                     removeAllErrors();
                     addError("Введите значение в поле Cообщение");
                 }
                 else if(text.length > ${textLength})
                 {
                     removeAllErrors();
                     addError("Текст сообщения должен быть не более ${textLength} символов");
                 }
                 else if(subject.length <= 0)
                 {
                    removeAllErrors();
                    addError("Введите значение в поле Тема");
                 }
                 else if(responseMethod == 'BY_PHONE' &&
                         !templateObj.SIMPLE_NUMBER.validate(phone) &&
                         !templateObj.SIMPLE_NUMBER_MASK.validate(phone))
                 {
                    removeAllErrors();
                    addError(phoneNumberFormatErrorMessage);
                 }
                 else if(responseMethod == 'IN_WRITING' && email.length <= 0)
                 {
                    removeAllErrors();
                    addError("Введите адрес электронной почты в поле Способ получения ответа");
                 }
                 else
                 {
                    new CommandButton('button.save').click();
                 }
             }

             function changeDescription(elem)
             {
                 var value = $(elem).val();
                 $('#typeDescription').show();
                 $('#typeDescription').html(descriptionMessages[value]);
             }


             $(document).ready(function()
             {
                 showPhoneOrMailField(true);
                 $('#eMailwarningMessages').hide();
                 <c:if test="${!view || savedNewMailDraft}">
                     if(document.getElementById("newText") != null)
                        initAreaMaxLengthRestriction("newText", ${textLength}, true);
                     if(document.getElementById("oldText") != null)
                        initAreaMaxLengthRestriction("oldText", ${textLength}, true);
                 </c:if>
                 $('#typeDescription').hide();
                 $('#typeDescription').html(descriptionMessages['CONSULTATION']);
                 $('#phone_response_method').hide();
             });
            doOnLoad(
                function(){
                    if (${form.fields.relocateToDownload != null && form.fields.relocateToDownload == true})
                    {
                        clientBeforeUnload.showTrigger=false;
                        new CommandButton('download.file').click();
                        clientBeforeUnload.showTrigger=false;
                    }
                }
            );
        </script>
        <div id="feedback">
            <tiles:insert definition="window" flush="false">
                <tiles:put name="id" value="eMailConfirmWindow"/>
                <tiles:put name="styleClass" value="confirmEMail"/>
                <tiles:put name="data">
                   <div class="warningMessage" id="eMailwarningMessages">
                        <tiles:insert definition="roundBorderLight" flush="false">
                            <tiles:put name="color" value="red"/>
                            <tiles:put name="data">
                                <div class="messageContainer">

                                </div>
                                <div class="clear"></div>
                            </tiles:put>
                        </tiles:insert>
                   </div>
                   <h2>Адрес электронной почты</h2>
                   <div class="messageContainer">
                       <table class="paddingTblStyle">
                           <tr>
                               <td>Введите e-mail :&nbsp;</td>
                               <td><html:text property="field(email)" styleId="firstEMail" size="30" maxlength="256"/></td>
                           </tr>
                           <tr>
                               <td>Повторите ввод :&nbsp;</td>
                               <td><html:text property="field(secondEMail)" styleId="secondEMail" size="30" maxlength="256"/></td>
                           </tr>
                       </table>
                   </div>
                   <div class="buttonsArea">
                       <tiles:insert definition="clientButton" flush="false">
                            <tiles:put name="commandTextKey" value="button.close.window"/>
                            <tiles:put name="commandHelpKey" value="button.close.window"/>
                            <tiles:put name="bundle" value="commonBundle"/>
                            <tiles:put name="viewType" value="buttonGrey"/>
                            <tiles:put name="onclick" value="win.close('eMailConfirmWindow');"/>
                       </tiles:insert>
                       <tiles:insert definition="clientButton" flush="false">
                            <tiles:put name="commandTextKey" value="button.save"/>
                            <tiles:put name="commandHelpKey" value="button.save"/>
                            <tiles:put name="bundle" value="mailBundle"/>
                            <tiles:put name="onclick" value="saveEMail();"/>
                       </tiles:insert>
                       <div class="clear"></div>
                   </div>
                </tiles:put>
            </tiles:insert>
            <tiles:insert definition="mainWorkspace" flush="false">
                <tiles:put name="title"><bean:message bundle="mailBundle" key="list.title"/></tiles:put>
                <tiles:put name="data">
                    <html:hidden property="id"/>
                    <html:hidden property="field(mailState)"/>
                    <html:hidden property="field(isNotAnswer)"/>
                    <html:hidden property="field(newMailState)" styleId="mailState"/>
                    <tiles:insert definition="formHeader" flush="false">
                        <tiles:put name="image" value="${imagePath}/MailTo_big.png"/>
                        <tiles:put name="description">
                            <c:set var="mess" value="${phiz:getStaticMessage('com.rssl.iccs.CREATE_MAIL_FORM')}"/>
                            <c:set var="processed" value="${phiz:processBBCode(mess)}"/>
                            <h3>${processed}</h3>
                        </tiles:put>
                    </tiles:insert>
                    <div id="paymentForm">
                        <tiles:insert definition="fieldWithHint" flush="false">
                            <tiles:put name="fieldName"><span class="bold"><bean:message key="label.number" bundle="mailBundle"/></span></tiles:put>
                            <tiles:put name="externalId" value="num"/>
                            <tiles:put name="data">
                               <span class="bold"><c:out value="${form.fields.num}"/></span>
                            </tiles:put>
                        </tiles:insert>
                        <tiles:insert definition="fieldWithHint" flush="false">
                            <tiles:put name="fieldName"><span class="bold"><bean:message key="label.messageType" bundle="mailBundle"/></span><span class="asterisk">*</span></tiles:put>
                            <tiles:put name="externalId" value="messageType"/>
                            <tiles:put name="data">
                                <c:if test="${view}"><html:hidden property="field(type)"/></c:if>
                                <html:select property="field(type)" styleClass="select" disabled="${view}" onchange="changeDescription(this);">
                                    <html:option value="CONSULTATION"><bean:message key="mailType.CONSULTATION" bundle="mailBundle"/></html:option>
                                    <html:option value="COMPLAINT"><bean:message key="mailType.COMPLAINT" bundle="mailBundle"/></html:option>
                                    <html:option value="CLAIM"><bean:message key="mailType.CLAIM" bundle="mailBundle"/></html:option>
                                    <html:option value="GRATITUDE"><bean:message key="mailType.GRATITUDE" bundle="mailBundle"/></html:option>
                                    <html:option value="IMPROVE"><bean:message key="mailType.IMPROVE" bundle="mailBundle"/></html:option>
                                    <html:option value="OTHER"><bean:message key="mailType.OTHER" bundle="mailBundle"/></html:option>
                                </html:select>
                                <div id="typeDescription" class="description"></div>
                            </tiles:put>
                        </tiles:insert>
                        <tiles:insert definition="fieldWithHint" flush="false">
                            <tiles:put name="fieldName"><span class="bold"><bean:message key="label.mail.theme" bundle="mailBundle"/></span></tiles:put>
                            <tiles:put name="externalId" value="theme"/>
                            <tiles:put name="data">
                                <c:set var="themes" value="${form.themes}"/>
                                <c:if test="${view}">
                                    <html:hidden property="field(mail_theme)"/>
                                </c:if>
                                <html:select property="field(mail_theme)" styleClass="select" styleId="mail_theme" disabled="${view}">
                                    <c:forEach items="${themes}" var="theme">
                                        <html:option value="${theme.id}">${theme.description}</html:option>
                                    </c:forEach>
                                </html:select>
                            </tiles:put>
                        </tiles:insert>
                        <tiles:insert definition="fieldWithHint" flush="false">
                            <tiles:put name="fieldName"><span class="bold"><bean:message key="response_method" bundle="mailBundle"/></span></tiles:put>
                            <tiles:put name="externalId" value="response_method"/>
                            <tiles:put name="data">
                                <c:if test="${view}">
                                    <html:hidden property="field(response_method)"/>
                                </c:if>
                                <html:select property="field(response_method)" styleClass="select" styleId="response_method_select" disabled="${view}" onchange="showPhoneOrMailField();">
                                    <html:option value="BY_PHONE"><bean:message key="method.by_phone" bundle="mailBundle"/></html:option>
                                    <html:option value="IN_WRITING"><bean:message key="method.in_writing" bundle="mailBundle"/></html:option>
                                </html:select>
                                <c:set var="styleClass" value=""/>
                                <c:if test="${!view}">
                                    <c:set var="styleClass" value="masked-phone-number"/>
                                </c:if>
                                <html:text property="field(phone)" styleId="phone" styleClass="${form.maskedFields['phone'] ? styleClass : ''}" size="20" readonly="${view}" maxlength="20"/>
                                <span id="eMailSpan">
                                    <html:text property="field(email)" styleId="email" size="20" readonly="${true}" maxlength="256"/>
                                    <c:if test="${!view}">
                                        <tiles:insert definition="clientButton" flush="false">
                                            <tiles:put name="commandTextKey" value="button.change"/>
                                            <tiles:put name="commandHelpKey" value="button.change"/>
                                            <tiles:put name="bundle" value="mailBundle"/>
                                            <tiles:put name="viewType" value="buttonGrey"/>
                                            <tiles:put name="onclick" value="showPhoneOrMailField();"/>
                                       </tiles:insert>
                                   </c:if>
                               </span>
                               <div id="phone_response_method" class="description"></div>
                            </tiles:put>
                        </tiles:insert>
                        <tiles:insert definition="fieldWithHint" flush="false">
                            <tiles:put name="fieldName"><span class="bold"><bean:message key="subject" bundle="mailBundle"/></span><span class="asterisk">*</span></tiles:put>
                            <tiles:put name="externalId" value="subject"/>
                            <tiles:put name="data">
                               <html:text property="field(subject)" styleId="subject" size="60" readonly="${view}" maxlength="40"/>
                            </tiles:put>
                        </tiles:insert>
                        <tiles:insert definition="fieldWithHint" flush="false">
                            <tiles:put name="fieldName"><span class="bold"><bean:message key="label.attach" bundle="mailBundle"/></span></tiles:put>
                            <tiles:put name="externalId" value="attach"/>
                            <tiles:put name="data">
                                <c:set var="file" value="${form.fields.file}"/>
                                <c:choose>
                                    <c:when test="${view}">
                                        <c:if test="${not empty file}">
                                            <html:link styleClass="orangeText" onclick="clientBeforeUnload.showTrigger=false; new CommandButton('button.upload').click(); clientBeforeUnload.showTrigger=false; return false;"
                                                       href="#">
                                                <span><bean:write name='form' property="field(fileName)"/></span>
                                            </html:link>
                                        </c:if>
                                    </c:when>
                                    <c:otherwise>
                                        <tiles:insert name="fileInput" flush="false">
                                            <tiles:put name="file" value="field(file)"/>
                                            <tiles:put name="isFileAttached" value="${not empty file and not form.fields.setNewFile}"/>
                                            <tiles:put name="fileName" value="${form.fields.fileName}"/>
                                            <tiles:put name="form" value="${form}"/>
                                        </tiles:insert>
                                    </c:otherwise>
                                </c:choose>
                            </tiles:put>
                        </tiles:insert>
                        <tiles:insert definition="fieldWithHint" flush="false">
                            <tiles:put name="fieldName"><span class="bold"><bean:message bundle="mailBundle" key="label.message"/></span><span class="asterisk">*</span></tiles:put>
                            <tiles:put name="externalId" value="message"/>
                            <tiles:put name="data">
                                <c:choose>
                                    <c:when test="${reply}">
                                        <html:textarea styleId="newText" property="field(newText)" cols="55" rows="8" style="text-align:justify;"/>
                                        <html:hidden property="field(body)" value="E"/>
                                    </c:when>
                                    <c:otherwise>
                                        <html:hidden property="field(newText)" value="E"/>
                                        <html:textarea styleId="oldText" property="field(body)" cols="55" rows="8" style="text-align:justify;" readonly="${view}"/>
                                    </c:otherwise>
                                 </c:choose>
                            </tiles:put>
                        </tiles:insert>


                        <c:if test="${reply && not empty form.fields.correspondence}">
                            <tiles:insert definition="fieldWithHint" flush="false">
                                <tiles:put name="fieldName"><span class="bold"><bean:message bundle="mailBundle" key="label.message.history"/></span></tiles:put>
                                <tiles:put name="externalId" value="history"/>
                                <tiles:put name="data">
                                   <c:set var="length" value="${phiz:size(form.fields.correspondence)}"/>
                                    <div class="correspondenceClient">
                                        <c:set var="num" value="0"/>
                                        <c:set var="pos" value="10"/>
                                        <c:forEach items="${form.fields.correspondence}" var="listElem">

                                            <c:if test="${num <= 2}">
                                                <c:set var="pos" value="${pos + 10*num}"/>
                                            </c:if>
                                            <c:if test="${num == 2}">
                                                <a style="margin-left: ${pos}px;"><u>последние письма:</u></a>
                                                <hr class="mail-line" noshade size="1"/>
                                            </c:if>
                                            <c:choose>
                                                <c:when test="${num < 2}">
                                                    <i style="margin-left:${pos}px;">
                                                       <bean:write name="listElem" property="date.time" format="dd.MM.yyyy hh:mm"/>
                                                       <c:choose>
                                                           <c:when test="${listElem.direction == 'CLIENT'}">
                                                               <bean:message key="label.employee" bundle="mailBundle"/>:
                                                           </c:when>
                                                           <c:otherwise>
                                                                <bean:message key="label.you" bundle="mailBundle"/>:
                                                           </c:otherwise>
                                                       </c:choose>
                                                    </i><br/>
                                                </c:when>
                                                <c:otherwise>
                                                    <i style="color:gray; margin-left:${pos}px;">
                                                       <c:choose>
                                                           <c:when test="${listElem.direction == 'CLIENT'}">
                                                               <bean:message key="label.employee" bundle="mailBundle"/>:
                                                           </c:when>
                                                           <c:otherwise>
                                                                <bean:message key="label.you" bundle="mailBundle"/>:
                                                           </c:otherwise>
                                                       </c:choose>
                                                    </i>
                                                </c:otherwise>
                                            </c:choose>

                                            <div style="margin-left:${pos}px;"><c:out value="${listElem.body}"/></div>

                                            <c:set var="num" value="${num +1}"/>
                                            <c:if test="${length != num}">
                                                <hr class="mail-line" noshade size="1"/>
                                            </c:if>
                                        </c:forEach>
                                    </div>
                                </tiles:put>
                            </tiles:insert>
                        </c:if>
                    </div>

                    <div class="buttonsArea">
                        <tiles:insert definition="clientButton" flush="false">
                            <tiles:put name="commandTextKey" value="button.cancel"/>
                            <tiles:put name="commandHelpKey" value="button.close.help"/>
                            <tiles:put name="bundle" value="mailBundle"/>
                            <tiles:put name="viewType" value="buttonGrey"/>
                            <tiles:put name="action" value="/private/mail/${not empty viewType && viewType == 'outgoing' ? 'sentList.do' : 'list.do'}"/>
                            <c:if test="${isNew}">
                                <tiles:put name="onclick" value="removeCurrentNewMail();"/>
                            </c:if>
                        </tiles:insert>
                        <c:choose>
                            <c:when test="${isNew}">
                                <tiles:insert definition="clientButton" flush="false" operation="EditClientMailOperation">
                                    <tiles:put name="commandTextKey" value="button.save"/>
                                    <tiles:put name="commandHelpKey" value="button.save.help"/>
                                    <tiles:put name="bundle" value="mailBundle"/>
                                    <tiles:put name="onclick" value="createMail('CLIENT_DRAFT')"/>
                                    <tiles:put name="viewType" value="buttonGrey"/>
                                </tiles:insert>
                                <tiles:insert definition="clientButton" operation="EditClientMailOperation" flush="false">
                                    <tiles:put name="commandTextKey" value="button.send"/>
                                    <tiles:put name="commandHelpKey" value="button.send.help"/>
                                    <tiles:put name="bundle" value="mailBundle"/>
                                    <tiles:put name="onclick" value="createMail('NEW')"/>
                                </tiles:insert>
                            </c:when>
                            <c:otherwise>
                                <c:if test="${draft}">
                                    <tiles:insert definition="clientButton" flush="false" operation="EditClientMailOperation">
                                        <tiles:put name="commandTextKey" value="button.save"/>
                                        <tiles:put name="commandHelpKey" value="button.save.help"/>
                                        <tiles:put name="bundle" value="mailBundle"/>
                                        <tiles:put name="onclick" value="createMail('SAVE_DRAFT')"/>
                                        <tiles:put name="viewType" value="buttonGrey"/>
                                    </tiles:insert>
                                    <tiles:insert definition="clientButton" operation="EditClientMailOperation" flush="false">
                                        <tiles:put name="commandTextKey" value="button.send"/>
                                        <tiles:put name="commandHelpKey" value="button.send.help"/>
                                        <tiles:put name="bundle" value="mailBundle"/>
                                        <tiles:put name="onclick" value="createMail('SEND_DRAFT')"/>
                                    </tiles:insert>
                                </c:if>
                                <c:if test="${incomingView}">
                                    <tiles:insert definition="commandButton" flush="false" operation="EditClientMailOperation">
                                        <tiles:put name="commandKey"     value="button.reply"/>
                                        <tiles:put name="commandHelpKey" value="button.reply.help"/>
                                        <tiles:put name="bundle"         value="mailBundle"/>
                                    </tiles:insert>
                                </c:if>
                             </c:otherwise>
                        </c:choose>
                    <div class="clear"></div>
                </div>

                    <div>
                        <c:choose>
                            <c:when test="${not empty viewType && viewType == 'incoming'}">
                                <tiles:insert definition="clientButton" flush="false">
                                    <tiles:put name="commandTextKey" value="label.show_incoming"/>
                                    <tiles:put name="commandHelpKey" value="label.show_incoming"/>
                                    <tiles:put name="bundle" value="mailBundle"/>
                                    <tiles:put name="viewType" value="blueGrayLink"/>
                                    <tiles:put name="action" value="/private/mail/list"/>
                                    <c:if test="${isNew}">
                                        <tiles:put name="onclick" value="removeCurrentNewMail();"/>
                                    </c:if>
                                </tiles:insert>
                            </c:when>
                            <c:otherwise>
                                <tiles:insert definition="clientButton" flush="false">
                                    <tiles:put name="commandTextKey" value="label.show-all"/>
                                    <tiles:put name="commandHelpKey" value="label.show-all"/>
                                    <tiles:put name="bundle" value="mailBundle"/>
                                    <tiles:put name="viewType" value="blueGrayLink"/>
                                    <tiles:put name="action" value="/private/mail/sentList"/>
                                    <c:if test="${isNew}">
                                        <tiles:put name="onclick" value="removeCurrentNewMail();"/>
                                    </c:if>
                                </tiles:insert>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </tiles:put>
            </tiles:insert>
        </div>
        </tiles:put>
    </tiles:insert>
