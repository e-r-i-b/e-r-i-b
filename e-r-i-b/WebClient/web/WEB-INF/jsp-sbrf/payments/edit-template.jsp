<%@ page import="com.rssl.phizic.web.actions.payments.forms.CreatePaymentForm"%>
<%@ page contentType="text/html;charset=windows-1251" language="java"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>

<html:form action="/private/payments/template" onsubmit="return setEmptyAction(event)">
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="isERMBConnectedPerson" value="${phiz:isERMBConnectedPerson()}"/>
<c:set var="formName" value="${form.form}"/>
<c:set var="metadataPath" value="${form.metadataPath}"/>
<c:set var="metadata" value="${form.metadata}"/>
<c:set var="fromFinanceCalendar" value="${form.fromFinanceCalendar}"/>
<c:set var="extractId" value="${form.extractId}"/>
<c:set var="isDepositaryClaim" value="${ metadata.form.name == 'SecurityRegistrationClaim'
                                              || metadata.form.name == 'SecuritiesTransferClaim'
                                              || metadata.form.name == 'DepositorFormClaim'
                                              || metadata.form.name == 'RecallDepositaryClaim'}"/>
<c:set var="isNewP2P" value="${metadata.form.name == 'NewRurPayment'
                                || metadata.form.name == 'CreateP2PAutoTransferClaim'
                                || metadata.form.name == 'EditP2PAutoTransferClaim'
                                || metadata.form.name == 'RecoveryP2PAutoTransferClaim'
                                || metadata.form.name == 'CloseP2PAutoTransferClaim'
                                || metadata.form.name == 'DelayP2PAutoTransferClaim'}"/>
    <c:choose>
       <c:when test="${isDepositaryClaim}">
           <c:set var="mainmenuButton" value="Depo"/>
       </c:when>
       <c:otherwise>
           <c:set var="mainmenuButton" value=""/>
       </c:otherwise>
    </c:choose>

<tiles:insert definition="accountInfo">
	<tiles:put name="pageTitle" value="Ввод/редактирование шаблона"/>
    <tiles:put name="mainmenu" value="${mainmenuButton}" />
	<tiles:put name="submenu" value="${formName}"/>
    <tiles:put name="breadcrumbs">
        <tiles:insert definition="breadcrumbsLink" flush="false">
            <tiles:put name="main" value="true"/>
            <tiles:put name="action" value="/private/accounts.do"/>
        </tiles:insert>
        <c:choose>
           <c:when test="${isDepositaryClaim}">
              <tiles:insert definition="breadcrumbsLink" flush="false">
                  <tiles:put name="name" value="Счета депо"/>
                  <tiles:put name="action" value="/private/depo/list.do"/>
               </tiles:insert>
           </c:when>
           <c:otherwise>
               <tiles:insert definition="breadcrumbsLink" flush="false">
                   <tiles:put name="name" value="Мои шаблоны"/>
                   <tiles:put name="action" value="/private/favourite/list/PaymentsAndTemplates.do"/>
               </tiles:insert>
           </c:otherwise>
        </c:choose>
        <tiles:insert definition="breadcrumbsLink" flush="false">
            <tiles:put name="name" value="Создание шаблона"/>
            <tiles:put name="last" value="true"/>
        </tiles:insert>
    </tiles:put>
    <c:if test="${isNewP2P}">
        <tiles:put name="rightSection" value="false"/>
    </c:if>
	<%-- собственно данные --%>
	<tiles:put name="data" type="string">
        <script type="text/javascript">
            <%-- Заполняем карту < ISO-код валюты, символ валюты > --%>
            <c:forEach var="entry" items="${phiz:getCurrencySignMap()}">
                currencySignMap.map['${entry.key}'] = '${entry.value}';
            </c:forEach>
            <c:if test="${isERMBConnectedPerson == true}">
                doOnLoad(setSmsCommandText);
                function setSmsCommandText()
                {
                    $(tempName).text($('input[name=templateName]').val());
                }
            </c:if>

            <c:set var="createTemplateUrl" value="${phiz:calculateActionURL(pageContext, '/private/async/payments/template')}"/>
            function clearAllMessages()
            {
                removeAllMessages();
                removeAllErrors();
                $("#payment .form-row").each(function(){
                    payInput.formRowClearError(this);
                });
            }

            function saveDraftTemplate()
            {
                var frm = document.forms.item(0);
                var params = $(frm).serializeToWin();
                ajaxQuery(
                        params + "&operation=button.saveDraftTemplate&form=" + "${form.form}",
                        "${createTemplateUrl}",
                        function(data)
                        {
                            clearAllMessages();

                            if(data.errors != null && data.errors.length > 0)
                            {
                                var errors = data.errors;
                                for(var i = 0; i <errors.length; i++)
                                    addError(errors[i]);
                            }

                            if(data.errorFields != null && data.errorFields.length > 0)
                            {
                                var errorFields = data.errorFields;
                                for(var j = 0; j <errorFields.length; j++)
                                    payInput.fieldError(errorFields[j].name, errorFields[j].value);
                            }

                            if(data.messages != null && data.messages.length > 0)
                            {
                                var messages = data.messages;
                                for(var k = 0; k <messages.length; k++)
                                    addMessage(messages[k]);
                            }
                        },
                        "json");
            }
        </script>

        <c:if test="${metadata.form.name == 'LoanPayment'}">
            <jsp:include page="../loans/annLoanMessageWindow.jsp" flush="false"/>
        </c:if>

        <tiles:insert definition="paymentForm" flush="false">
			<tiles:put name="id" value="${metadata.form.name}"/>
            <c:choose>
                <c:when test="${isDepositaryClaim}">
                    <tiles:put name="name" value="Шаблон поручения"/>
                    <tiles:put name="title" value="Шаблон поручения"/>
			        <tiles:put name="description" value="Используйте эту страницу для создания шаблона поручения. Выполнение операции с помощью шаблона избавит Вас от повторного ввода реквизитов при оформлении поручения в следующий раз.
Для создания шаблона заполните необходимые поля и нажмите на кнопку «Сохранить»."/>
               </c:when>
                <c:otherwise>
                    <tiles:put name="name" value="Шаблон платежа"/>
                    <tiles:put name="title" value="Создание шаблона"/>
                    <tiles:put name="description">
                        Для создания шаблона заполните необходимые поля и нажмите на кнопку «Сохранить шаблон».
                        <br/>
                        <span class="text-gray">Поля, обязательные для заполнения, отмечены</span>
                        <span class="text-red">*</span>
                        <span class="text-gray">.</span>
                    </tiles:put>
                    <c:if test="${isNewP2P}">
                         <tiles:put name="showHeader" value="false"/>
                    </c:if>
               </c:otherwise>
            </c:choose>
            <tiles:put name="isTemplate" value="true"/>
            <tiles:put name="stripe">
                <tiles:insert definition="stripe" flush="false">
                    <tiles:put name="name" value="выбор операции"/>
                    <tiles:put name="future" value="false"/>
                </tiles:insert>
                <tiles:insert definition="stripe" flush="false">
                    <tiles:put name="name" value="заполнение реквизитов"/>
                    <tiles:put name="current" value="true"/>
                </tiles:insert>
                <tiles:insert definition="stripe" flush="false">
                    <tiles:put name="name" value="подтверждение"/>
                </tiles:insert>
                <tiles:insert definition="stripe" flush="false">
                    <tiles:put name="name" value="статус операции"/>
                </tiles:insert>
            </tiles:put>
			<tiles:put name="data">
				<span onkeypress="onEnterKey(event);">
				 	${form.html}
                    <c:choose>
                        <c:when test="${isNewP2P}">
                            <tiles:insert definition="fieldWithHintNew" flush="false">
                                <tiles:put name="fieldName"     value="Название шаблона:"/>
                                <tiles:put name="required"      value="true"/>
                                <tiles:put name="externalId"    value="templateName"/>
                                <tiles:put name="fieldType"     value="text"/>
                                <tiles:put name="description"   value="Введите название шаблона, под которым он будет сохранен в Личном меню. Название должно быть не более 50 символов."/>
                                <tiles:put name="descriptionClass"   value="fixInputSize"/>
                                <tiles:put name="data">
                                    <html:text property="templateName" size="40" maxlength="50"  styleClass="fixInputSize"/>
                                </tiles:put>
                                <c:if test="${isERMBConnectedPerson == true}">
                                    <tiles:put name="onKeyUp" value="setSmsCommandText()"/>
                                </c:if>
                            </tiles:insert>
                        </c:when>
                        <c:otherwise>
                            <tiles:insert definition="fieldWithHint" flush="false">
                                <tiles:put name="fieldName"     value="Название шаблона:"/>
                                <tiles:put name="required"      value="true"/>
                                <tiles:put name="externalId"    value="templateName"/>
                                <tiles:put name="fieldType"     value="text"/>
                                <tiles:put name="description"   value="Введите название шаблона, под которым он будет сохранен в Личном меню. Название должно быть не более 50 символов."/>
                                <tiles:put name="data">
                                    <html:text property="templateName" size="40" maxlength="50"/>
                                </tiles:put>
                                <c:if test="${isERMBConnectedPerson == true}">
                                    <tiles:put name="onKeyUp" value="setSmsCommandText()"/>
                                </c:if>
                            </tiles:insert>
                        </c:otherwise>
                    </c:choose>
                    <c:if test="${isNewP2P}"><div class="fakeFormRowNew form-row-edit"></c:if>


                    <c:if test="${phiz:impliesService('ReminderManagment')}">
                         <c:if test="${isNewP2P}"><div class="fakeFormRowNew form-row-edit"></c:if>
                            <tiles:insert page="/WEB-INF/jsp-sbrf/reminder/reminderFormRow.jsp" flush="false">
                                <tiles:put name="enableReminder"    value="${form.fields['enableReminder']}"/>
                                <tiles:put name="reminderType"      value="${form.fields['reminderType']}"/>
                                <tiles:put name="dayOfMonth"        value="${form.fields['dayOfMonth']}"/>
                                <tiles:put name="monthOfQuarter"    value="${form.fields['monthOfQuarter']}"/>
                                <tiles:put name="onceDate"          value="${form.fields['onceDate']}"/>
                                <tiles:put name="editable"          value="true"/>
                            </tiles:insert>
                        <c:if test="${isNewP2P}"></div></c:if>
                    </c:if>

                    <c:if test="${isERMBConnectedPerson == true}">
                        <c:choose>
                            <c:when test="${metadata.form.name == 'NewRurPayment'}">
                                <div class="form-row-edit form-row-new">
                                    <div class="paymentLabelNew">
                                        <span class="paymentTextLabel">SMS-команда</span>
                                    </div>
                                    <div class="paymentValue paymentValueNew">
                                        <div class="paymentInputDiv autoInputWidth allWidth">
                                            <div class="linear line20">
                                                <label id="tempName"></label>
                                                <c:out value="<сумма>"/>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="clear"></div>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="form-row">
                                    <div class="paymentLabel">
                                        <span class="paymentTextLabel">SMS-команда</span>
                                    </div>
                                    <div class="paymentValue">
                                        <div class="paymentInputDiv">
                                            <b>
                                                <label id="tempName"></label>
                                                <c:out value="<сумма>"/>
                                            </b>
                                        </div>
                                    </div>
                                    <div class="clear"></div>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </c:if>
				</span>
			</tiles:put>
			<tiles:put name="alignTable" value="center"/>
			<tiles:put name="buttons">
                <c:choose>
                    <c:when test="${fromFinanceCalendar}">
                        <c:set var="action" value="/private/finances/financeCalendar.do?fromFinanceCalendar=true&extractId=${extractId}"/>
                        <c:set var="selectServiceAction" value="/private/template/select-category.do?fromFinanceCalendar=true&extractId=${extractId}"/>
                    </c:when>
                    <c:otherwise>
                        <c:set var="action" value="/private/payments"/>
                        <c:set var="selectServiceAction" value="/private/template/select-category"/>
                    </c:otherwise>
                </c:choose>

                <div class="backToService backToServiceBottom">
                    <div id="editTemplateButton" style="display: none;" class="clientButton">
                        <tiles:insert definition="commandButton" flush="false">
                            <tiles:put name="commandKey"     value="button.edit_template"/>
                            <tiles:put name="commandHelpKey" value="button.template.edit"/>
                            <tiles:put name="viewType"       value="buttonGrey"/>
                            <tiles:put name="bundle"         value="paymentsBundle"/>
                            <tiles:put name="imageUrl"       value="${globalUrl}/commonSkin/images/backIcon.png"/>
                            <tiles:put name="imageHover"     value="${globalUrl}/commonSkin/images/backIconHover.png"/>
                            <tiles:put name="imagePosition"  value="left"/>
                        </tiles:insert>
                    </div>
                </div>
                <div class="buttonsArea">
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.cancel"/>
                        <tiles:put name="commandHelpKey" value="button.cancel"/>
                        <tiles:put name="bundle" value="paymentsBundle"/>
                        <tiles:put name="viewType" value="buttonGrey"/>
                        <tiles:put name="action" value="${action}"/>
                    </tiles:insert>
                    <%-- если можно редактировать имя то можно и черновик сохранять --%>
                    <c:if test="${empty form.id}">
                        <tiles:insert definition="clientButton" flush="false">
                            <tiles:put name="commandTextKey" value="button.saveDraftTemplate"/>
                            <tiles:put name="commandHelpKey" value="button.saveDraftTemplate.help"/>
                            <tiles:put name="bundle"         value="paymentsBundle"/>
                            <tiles:put name="imagePosition"  value="left"/>
                            <tiles:put name="viewType"       value="buttonGrey"/>
                            <tiles:put name="onclick"        value="saveDraftTemplate();"/>
                        </tiles:insert>
                    </c:if>
                    <tiles:insert definition="createTemplateConfirmButton" flush="false">
                        <tiles:put name="commandKey"        value="button.confirmTemplate"/>
                        <tiles:put name="templateNameId"    value="input[name=templateName]"/>
                        <tiles:put name="isDefault"         value="true"/>
                        <tiles:put name="bundle"            value="paymentsBundle"/>
                    </tiles:insert>
                </div>
                <tiles:insert definition="clientButton" flush="false">
                    <tiles:put name="commandTextKey"    value="button.goto.select.service"/>
                    <tiles:put name="commandHelpKey"    value="button.goto.select.service"/>
                    <tiles:put name="bundle"            value="paymentsBundle"/>
                    <tiles:put name="viewType"          value="blueGrayLink"/>
                    <tiles:put name="action"            value="${selectServiceAction}"/>
                </tiles:insert>
			</tiles:put>
		</tiles:insert>
	</tiles:put>
</tiles:insert>
</html:form>
