<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<tiles:importAttribute/>
<html:form action="/private/favourite/list/PaymentsAndTemplates" onsubmit="return setEmptyAction(event)">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="activeTemplates" value="${form.activeTemplates}"/>
    <c:set var="activeTemplatesCount" value="${phiz:size(activeTemplates)}"/>
    <c:set var="inactiveTemplates" value="${form.inactiveTemplates}"/>
    <c:set var="inactiveTemplatesCount" value="${phiz:size(inactiveTemplates)}"/>
    <c:set var="editTemplateURL" value="${phiz:calculateActionURL(pageContext, '/private/payments/template')}"/>
    <c:set var="url" value="${phiz:calculateActionURL(pageContext,'/private/async/favourite/save/templates')}"/>
    <c:set var = "isReminderManagementAccess" value="${phiz:impliesService('ReminderManagment')}"/>

    <tiles:insert definition="paymentMain">
        <c:set var="globalImagePath" value="${globalUrl}/images"/>
        <c:set var="imagePath" value="${skinUrl}/images"/>
        <c:set var="commonImagePath" value="${globalUrl}/commonSkin/images"/>
        <c:set var="helpLink" value="${phiz:calculateActionURL(pageContext,'/help.do?id=/private/receivers/list')}"/>
        <c:set var="faqLink" value="${phiz:calculateActionURLWithAnchor(pageContext, '/faq.do', 'q19')}"/>
        <c:set var="faqLinkPersonal" value="${phiz:calculateActionURLWithAnchor(pageContext, '/faq.do', 'm19')}"/>

        <tiles:put name="mainmenu" value=""/>

        <tiles:put name="data" type="string">
            <div id="payments-templates">
                <tiles:insert definition="mainWorkspace" flush="false">
                    <tiles:put name="data">
                        <div class="clear">&nbsp;</div>
                        <div class="titleItems float"><h1>Шаблоны</h1></div>
                        <div class="clear"></div>
                        <div class="paddBottom10 titleItems">
                            Подтвердите шаблон в Контактном центре по телефонам +7 (495) 500-5550, 8 (800) 555-5550, чтобы выполнять операции сверх установленного лимита.
                        </div>
                        <div class="clear"></div>

                        <c:choose>
                            <c:when test="${not empty activeTemplates or not empty inactiveTemplates}">
                                <div>
                                    <div class="smallTitlePosition">
                                        <div class="titleItems uppercase float titleItemsSmall">
                                            <h3>Мои активные шаблоны</h3>
                                        </div>

                                        <div class="floatRight">
                                            <tiles:insert definition="clientButton" flush="false">
                                                <tiles:put name="commandTextKey"    value="button.addPaymentTemplate"/>
                                                <tiles:put name="commandHelpKey"    value="button.addPaymentTemplate.help"/>
                                                <tiles:put name="bundle"            value="paymentsBundle"/>
                                                <tiles:put name="action"            value="/private/template/select-category.do"/>
                                            </tiles:insert>
                                        </div>
                                    </div>

                                    <div class="clear"></div>

                                    <div class="payment-templates templateList">
                                        <input type="hidden" id="selectedIds" name="selectedIds"/>
                                        <c:set var="templateUrl" value="${phiz:calculateActionURL(pageContext,'/private/templates/default-action.do?')}"/>
                                        <c:set var="statusHint" value="Шаблон подтвержден в Контактном центре – можно выполнять операции без подтверждения по SMS или паролем с чека."/>
                                        <tiles:insert definition="simpleTableTemplate" flush="false">
                                           <tiles:put name="grid">
                                                <c:if test="${not empty activeTemplates}">
                                                    <sl:collection id="template" name="activeTemplates" model="sort-list" styleClass="rowOver ${activeTemplatesCount==1 ? 'no-sort' : 'sort'}" indexId="ind">
                                                        <c:set var="templateId"         value="${template.id}"/>
                                                        <c:set var="creationDate"       value="${template.clientCreationDate}"/>
                                                        <c:set var="templateName"       value="${template.templateInfo.name}"/>
                                                        <c:set var="formType"           value="${template.formType}"/>
                                                        <c:set var="formTypeName"       value="${template.formType.name}"/>
                                                        <c:set var="exactAmount"        value="${template.exactAmount}"/>
                                                        <c:set var="stateCode"          value="${template.state}"/>

                                                        <html:hidden property="sortTemplates" value="${templateId}"/>
                                                        <sl:collectionItem styleClass="align-left repeatLink sortableCells" styleTitleClass="sortableCells">
                                                            <div class="relative">
                                                                <img src="${commonImagePath}/slip.gif" class="slipImage"/>
                                                                <sl:collectionItemParam id="title">
                                                                    <div align="left">
                                                                        <bean:message bundle="paymentsBundle" key="label.template.name"/>
                                                                    </div>
                                                                </sl:collectionItemParam>

                                                                <c:set var="textClass" value="sortableHiddenText"/>
                                                                <c:set var="showState" value="false"/>
                                                                <%-- для переводов между своими счетами сверхлимитный отображаем как активный --%>
                                                                <c:set var="isInternalPayment" value="${formTypeName=='InternalPayment' || formTypeName=='IMAPayment' || formTypeName=='LoanPayment' || formTypeName=='AccountClosingPayment' || formTypeName=='ConvertCurrencyPayment'}"/>
                                                                <c:if test="${stateCode == 'TEMPLATE' && !isInternalPayment}">
                                                                    <c:set var="textClass" value="sortableHiddenTextWithHint"/>
                                                                    <c:set var="showState" value="true"/>
                                                                </c:if>

                                                                <span class="templateId ${textClass} <c:if test="${showState}">textNobr</c:if>" onclick="window.location = '${templateUrl}&id=${templateId}&objectFormName=${formTypeName}&stateCode=${stateCode}'">
                                                                    <div class="sortableIconBlock">
                                                                        <span class="reminder-${isReminderManagementAccess && template.reminderInfo != null ? 'icon' : 'none'}"></span>
                                                                    </div>
                                                                    <span class="sortNameBreak"><c:out value="${templateName}"/></span>
                                                                </span>
                                                                <c:if test="${!showState}">
                                                                    <div class="shading emptyStatuSshading"></div>
                                                                </c:if>

                                                                <c:if test="${showState}">
                                                                    <div class="shading"></div>
                                                                    <a id="state_${ind}" class="text-highlight text-green stateReminder" onmouseout="hideLayer('stateDescription_${ind}');" onmouseover="showLayer('state_${ind}','stateDescription_${ind}');">
                                                                        <bean:message key="template.state.TEMPLATE" bundle="paymentsBundle"/>
                                                                        <span id="stateDescription_${ind}" onmouseover="showLayer('state_${ind}','stateDescription_${ind}','default');" onmouseout="hideLayer('stateDescription_${ind}');" class="layerFon stateDescription">
                                                                            <div class="floatMessageHeader"></div>
                                                                            <div class="layerFonBlock">${statusHint}</div>
                                                                        </span>
                                                                    </a>
                                                                </c:if>
                                                            </div>
                                                        </sl:collectionItem>
                                                        <sl:collectionItem styleClass="align-left-fix-date-create repeatLink2 sortableCells">
                                                            <sl:collectionItemParam id="title">
                                                                <div align="center">
                                                                    <bean:message bundle="paymentsBundle" key="label.template.date"/>
                                                                </div>
                                                            </sl:collectionItemParam>
                                                                <span>
                                                                    ${phiz:formatDateDependsOnSysDate(creationDate, true, false)}
                                                                </span>
                                                        </sl:collectionItem>
                                                        <sl:collectionItem styleClass="align-right repeatLink3 sortableCells">
                                                            <sl:collectionItemParam id="title">
                                                                <div align="right">
                                                                    <bean:message bundle="paymentsBundle" key="label.template.amount"/>
                                                                </div>
                                                            </sl:collectionItemParam>
                                                            <span class="bold" onclick="window.location = '${templateUrl}&id=${templateId}&objectFormName=${formTypeName}&stateCode=${stateCode}'">
                                                                <c:if test="${not empty exactAmount}">
                                                                    <c:out value="${phiz:formatAmount(exactAmount)}"/>
                                                                </c:if>
                                                            </span>
                                                        </sl:collectionItem>
                                                        <sl:collectionItem  styleClass="align-right listOfOperationWidth editColumn sortableCells" styleTitleClass="" name="" title="">
                                                            <tiles:insert definition="listOfOperation" flush="false">
                                                                <tiles:putList name="items">
                                                                    <c:if test="${template.activityInfo.availablePay}">
                                                                        <tiles:add><a href="${phiz:getTemplateLinkByTemplateIdAndFormType(pageContext, templateId, formType)}">Оплатить</a></tiles:add>
                                                                    </c:if>
                                                                    <c:if test="${template.activityInfo.availableEdit}">
                                                                        <tiles:add><a onclick="editTemplate(${templateId});">Редактировать</a></tiles:add>
                                                                    </c:if>
                                                                    <c:if test="${template.activityInfo.availableAutoPay}">
                                                                        <tiles:add><a onclick="saveAutoPaymentForTemplate('${templateId}');">Подключить автоплатеж</a></tiles:add>
                                                                    </c:if>
                                                                    <c:if test="${template.reminderInfo == null && template.activityInfo.availableEdit && phiz:impliesService('ReminderManagment')}">
                                                                        <tiles:add>
                                                                            <a onclick="openAddReminderForm('${templateId}');">
                                                                                <bean:message key="button.${template.formType.name == 'RurPayJurSB' ? 'payment' : 'transfer'}.saveAsReminder" bundle="reminderBundle"/>
                                                                            </a>
                                                                        </tiles:add>
                                                                    </c:if>
                                                                    <tiles:add>
                                                                        <tiles:insert definition="confirmationButton" flush="false">
                                                                            <tiles:put name="winId" value="confirmationRemoveTemplate${templateId}" />
                                                                            <tiles:put name="title" value="Подтверждение удаления шаблона"/>
                                                                            <tiles:put name="message" value="Вы действительно хотите удалить выбранный шаблон?"/>
                                                                            <tiles:put name="currentBundle" value="paymentsBundle"/>
                                                                            <tiles:put name="confirmKey" value="button.template.delete"/>
                                                                            <tiles:put name="confirmCommandKey" value="button.remove"/>
                                                                            <tiles:put name="buttonViewType" value="simpleLink"/>
                                                                            <tiles:put name="validationFunction" value="removeTemplate(${templateId})"/>
                                                                        </tiles:insert>
                                                                    </tiles:add>
                                                                </tiles:putList>
                                                            </tiles:insert>
                                                        </sl:collectionItem>
                                                    </sl:collection>
                                                </c:if>
                                            </tiles:put>
                                            <tiles:put name="isEmpty" value="${empty activeTemplates}"/>
                                            <tiles:put name="emptyMessage">
                                                <span class="normal">Пока у Вас нет ни одного активного шаблона.
                                                    Для его создания нажмите на кнопку <b>Создать шаблон</b> или подтвердите существующий черновик шаблона одноразовым SMS-паролем.</span>
                                            </tiles:put>
                                        </tiles:insert>
                                    </div>

                                    <c:if test="${activeTemplatesCount>1}">
                                        <div class="sortAutoPaymentAndTemplateText">
                                            Перемещайте шаблоны в нужном Вам порядке
                                        </div>
                                    </c:if>

                                    <div class="titleItems uppercase smallTitlePosition"><h3>Мои черновики</h3></div>

                                    <div class="payment-templates templateList">
                                        <c:set var="templateUrl" value="${phiz:calculateActionURL(pageContext,'/private/templates/default-action.do?')}"/>
                                        <tiles:insert definition="simpleTableTemplate" flush="false">
                                           <tiles:put name="grid">
                                                <c:if test="${not empty inactiveTemplates}">
                                                    <sl:collection id="template" name="inactiveTemplates" model="sort-list" styleClass="rowOver ${inactiveTemplatesCount==1 ? 'no-sort' : 'sort'}" indexId="tableIndex">
                                                        <c:set var="templateId"         value="${template.id}"/>
                                                        <c:set var="creationDate"       value="${template.clientCreationDate}"/>
                                                        <c:set var="templateName"       value="${template.templateInfo.name}"/>
                                                        <c:set var="formType"           value="${template.formType}"/>
                                                        <c:set var="formTypeName"       value="${template.formType.name}"/>
                                                        <c:set var="exactAmount"        value="${template.exactAmount}"/>
                                                        <c:set var="stateCode"          value="${template.state}"/>

                                                        <html:hidden property="sortTemplates" value="${templateId}"/>
                                                        <sl:collectionItem styleClass="align-left repeatLink sortableCells" styleTitleClass="sortableCells">
                                                            <div class="relative">
                                                                <img src="${commonImagePath}/slip.gif" class="slipImage"/>
                                                                <sl:collectionItemParam id="title">
                                                                    <div align="left">
                                                                        <bean:message bundle="paymentsBundle" key="label.template.name"/>
                                                                    </div>
                                                                </sl:collectionItemParam>
                                                                <span class="templateId sortableHiddenText" onclick="window.location = '${templateUrl}&id=${templateId}&objectFormName=${formTypeName}&stateCode=${stateCode}'">
                                                                    <div class="sortableIconBlock">
                                                                        <span class="reminder-${template.reminderInfo != null ? 'icon' : 'none'}"></span>
                                                                    </div>
                                                                    <span class="sortNameBreak"><c:out value="${templateName}"/></span>
                                                                </span>
                                                                <div class="shading emptyStatuSshading"></div>
                                                            </div>
                                                        </sl:collectionItem>
                                                        <sl:collectionItem styleClass="align-left-fix-date-create repeatLink2 sortableCells">
                                                            <sl:collectionItemParam id="title">
                                                                <div align="center">
                                                                    <bean:message bundle="paymentsBundle" key="label.template.date"/>
                                                                </div>
                                                            </sl:collectionItemParam>
                                                                <span>
                                                                    ${phiz:formatDateDependsOnSysDate(creationDate, true, false)}
                                                                </span>
                                                        </sl:collectionItem>
                                                        <sl:collectionItem styleClass="align-right repeatLink3 sortableCells">
                                                            <sl:collectionItemParam id="title">
                                                                <div align="right">
                                                                    <bean:message bundle="paymentsBundle" key="label.template.amount"/>
                                                                </div>
                                                            </sl:collectionItemParam>
                                                            <span class="bold" onclick="window.location = '${templateUrl}&id=${templateId}&objectFormName=${formTypeName}&stateCode=${stateCode}'">
                                                                <c:if test="${not empty exactAmount}">
                                                                    <c:out value="${phiz:formatAmount(exactAmount)}"/>
                                                                </c:if>
                                                            </span>
                                                        </sl:collectionItem>
                                                        <sl:collectionItem  styleClass="align-right listOfOperationWidth editColumn sortableCells" styleTitleClass="" name="" title="">
                                                            <tiles:insert definition="listOfOperation" flush="false">
                                                                <tiles:putList name="items">
                                                                    <c:if test="${template.activityInfo.availablePay}">
                                                                        <tiles:add><a href="${phiz:getTemplateLinkByTemplateIdAndFormType(pageContext, templateId, formType)}">Оплатить</a></tiles:add>
                                                                    </c:if>
                                                                    <c:if test="${template.activityInfo.availableEdit}">
                                                                        <tiles:add><a onclick="editTemplate(${templateId});">Редактировать</a></tiles:add>
                                                                    </c:if>
                                                                    <c:if test="${template.activityInfo.availableAutoPay}">
                                                                        <tiles:add><a onclick="saveAutoPaymentForTemplate('${templateId}');">Подключить автоплатеж</a></tiles:add>
                                                                    </c:if>
                                                                    <c:if test="${template.reminderInfo == null && template.activityInfo.availableEdit && phiz:impliesService('ReminderManagment')}">
                                                                        <tiles:add>
                                                                            <a onclick="openAddReminderForm('${templateId}');">
                                                                                <bean:message key="button.${template.formType.name == 'RurPayJurSB' ? 'payment' : 'transfer'}.saveAsReminder" bundle="reminderBundle"/>
                                                                            </a>
                                                                        </tiles:add>
                                                                    </c:if>
                                                                    <tiles:add>
                                                                        <tiles:insert definition="confirmationButton" flush="false">
                                                                            <tiles:put name="winId" value="confirmationRemoveTemplate${templateId}" />
                                                                            <tiles:put name="title" value="Подтверждение удаления шаблона"/>
                                                                            <tiles:put name="message" value="Вы действительно хотите удалить выбранный шаблон?"/>
                                                                            <tiles:put name="currentBundle" value="paymentsBundle"/>
                                                                            <tiles:put name="confirmKey" value="button.template.delete"/>
                                                                            <tiles:put name="confirmCommandKey" value="button.remove"/>
                                                                            <tiles:put name="buttonViewType" value="simpleLink"/>
                                                                            <tiles:put name="validationFunction" value="removeTemplate(${templateId})"/>
                                                                        </tiles:insert>
                                                                    </tiles:add>
                                                                </tiles:putList>
                                                            </tiles:insert>
                                                        </sl:collectionItem>
                                                    </sl:collection>
                                                </c:if>
                                            </tiles:put>
                                            <tiles:put name="isEmpty" value="${empty inactiveTemplates}"/>
                                            <tiles:put name="emptyMessage">
                                                <span class="normal">У Вас нет ни одного черновика шаблона.</span>
                                            </tiles:put>
                                        </tiles:insert>
                                    </div>

                                    <c:if test="${inactiveTemplatesCount>1}">
                                        <div class="sortAutoPaymentAndTemplateText">
                                            Перемещайте шаблоны в нужном Вам порядке
                                        </div>
                                    </c:if>

                                    <div class="paddBottom10">
                                        Для того чтобы изменить доступность шаблонов в различных каналах, настройте их видимость на странице
                                        <nobr>
                                            <c:set var="temaplatesViewSettingsUrl" value="/private/userprofile/templatesShowSettings.do"/>
                                            <c:if test="${phiz:impliesService('NewClientProfile')}">
                                                <c:set var="temaplatesViewSettingsUrl" value="/private/userprofile/accountSecurity.do?needOpenTab=templateView"/>
                                            </c:if>
                                            <html:link action="${temaplatesViewSettingsUrl}" styleClass="templateSettings">
                                                <bean:message bundle="commonBundle" key="button.template.showSettings"/>
                                            </html:link>
                                        </nobr>
                                    </div>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="emptyText greenBlock">
                                    <div class="floatRight">
                                        <tiles:insert definition="clientButton" flush="false">
                                        <tiles:put name="commandTextKey"    value="button.addPaymentTemplate"/>
                                        <tiles:put name="commandHelpKey"    value="button.addPaymentTemplate.help"/>
                                        <tiles:put name="bundle"            value="paymentsBundle"/>
                                        <tiles:put name="action"            value="/private/template/select-category.do"/>
                                        </tiles:insert>
                                    </div>
                                    <div class="clear">&nbsp;</div>
                                    <tiles:insert definition="roundBorderLight" flush="false">
                                        <tiles:put name="color" value="greenBold"/>
                                        <tiles:put name="data">
                                            <span class="normal">Вы можете быстро и легко совершать операции с помощью шаблонов платежей. Пока у Вас нет ни одного
                                            шаблона. Для создания шаблона нажмите на кнопку <b>Создать шаблон</b> или используйте ссылку <b>Сохранить как шаблон</b> при выполнении
                                            платежей.<br/>
                                            <a href="" onclick="openHelp('${helpLink}'); return false;">Подробнее»</a></span>
                                        </tiles:put>
                                    </tiles:insert>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </tiles:put>
                 </tiles:insert>
            </div>
        </tiles:put>
    </tiles:insert>
</html:form>

<script type="text/javascript">
    try
    {
        doOnLoad(function(){
            $( ".sort" ).sortable({
                items: "li:not(.listInfHeader)",
                helper : 'clone',
                axis: "y",
                cancel : ".text-highlight",
                containment: "parent",
                tolerance: "pointer",
                placeholder: "ui-state-highlight",
                start: function( event, ui ) {
                    ui.helper.find(".listOfOperation").mouseout();
                    ui.helper.css('cursor','move');
                },
                update: function( event, ui ) {
                    var parameters = "";
                    $(this).find("li:not(.listInfHeader) input[type='hidden']").each(function(index){
                        parameters += ((index != 0) ? "&sortTemplates=" : "sortTemplates=") + $(this).val();
                    });
                    ajaxQuery(parameters, '${url}', function(data){});
                }});
        });
    } catch (e) { }

    function editTemplate(templateId)
    {
        $("#selectedIds").val(templateId);
        createCommandButton('button.edit_template', 'редактировать шаблон').click('', false);
    }

    function removeTemplate(templateId)
    {
        $("#selectedIds").val(templateId);
        return true;
    }
    var autoPaymentUrlForTemplate = "${phiz:calculateActionURL(pageContext,'/private/payments/template/view')}";
    function saveAutoPaymentForTemplate(templateId)
    {
        changeFormAction(autoPaymentUrlForTemplate + "?id="+templateId); createCommandButton('button.makeLongOffer','').click('', false)
    }
    function openAddReminderForm(templateId)
    {
        $("#selectedIds").val(templateId);
        createCommandButton('button.add_reminder', 'Напоминать о повторной оплате').click('', false);
    }

</script>