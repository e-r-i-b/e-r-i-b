<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<tiles:importAttribute/>
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>

<div class="il-invoicesList">
    <c:set var="invoiceViewUrl" value="${phiz:calculateActionURL(pageContext, '/private/basket/invoice/view')}"/>
    <c:set var="invoiceWinId" value="invoiceViewDiv"/>
    <tiles:insert definition="window" flush="false">
        <tiles:put name="id" value="${invoiceWinId}"/>
        <tiles:put name="loadAjaxUrl" value="${invoiceViewUrl}"/>
    </tiles:insert>

    <c:if test="${not empty form.bannerText}">
        <div class="il-banner">
            <div class="il-closeIcon floatRight"></div>
            <div class="il-bannerTitle">
                Работать со счетами стало проще!
            </div>
            <div class="il-bannerText">
                ${form.bannerText}
            </div>
            <div class="il-bannerPic floatLeft"></div>
            <div class="il-autoSearch floatLeft">
                <div class="il-autoSearchText">
                    Настройте автопоиск неоплаченных счетов и они будут приходить автоматически.
                </div>
                <div class="il-autoSearchButton">
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.autoSearch"/>
                        <tiles:put name="commandHelpKey" value="button.autoSearch.help"/>
                        <tiles:put name="bundle" value="paymentsBundle"/>
                        <tiles:put name="action" value="/private/userprofile/basket"/>
                        <tiles:put name="image" value=""/>
                        <tiles:put name="isDefault" value="true"/>
                    </tiles:insert>
                </div>

            </div>
            <div class="clear"></div>
            <div class="il-triangleOuter"></div>
            <div class="il-bannerTriangle"></div>


        </div>
    </c:if>
    <div class="il-header">
        <span class="il-invoicesTitle categoriesTitle floatLeft">
            Счета к оплате
        </span>

        <span class="il-beta floatLeft">
            &#946;
        </span>
        <a class="il-basketRef floatRight blueGrayLink" href="${phiz:calculateActionURL(pageContext, "/private/userprofile/basket")}"><bean:message key="button.search.unpaid.bills" bundle="paymentsBundle"/></a>
    </div>
    <div class="il-fakeDiv"></div>
    <div class="clear"></div>

    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/invoices.js"></script>
    <div class="invoice-process-messages">
        <phiz:invoiceMessages id="item">
            <c:choose>
                <c:when test="${item.type == 'payment'}">
                    <div class="invoice-report">
                        <span class="action-type">СЧЕТ ОПЛАЧЕН</span>
                        <c:out value="${item.name}"/> - <c:out value="${item.amount}"/> руб.
                    </div>
                    <c:if test="${item.additionMessage}">
                        <div class="invoice-info">
                            Спасиба за оплату! Мы будем регулярно проверять задолженность по данному поставщику услуг<br/> и отображать счета в этом списке
                        </div>
                    </c:if>
                </c:when>
                <c:when test="${item.type == 'delete'}">
                    <div class="invoice-report">
                        <span class="action-type">СЧЕТ УДАЛЕН</span>
                        <c:out value="${item.name}"/> - <c:out value="${item.amount}"/>  руб.
                    </div>
                    <c:if test="${item.additionMessage}">
                        <div class="invoice-info">
                            Хотите в дальнейшем получать счета от этого поставщика услуг?
                            <span class="invoice-process-answer" onclick="return {id : ${item.subscriptionId}, answer : true};">Да, хочу</span>
                            <span class="invoice-process-answer" onclick="return {id : ${item.subscriptionId}, answer : false};">Нет, спасибо</span>
                        </div>
                    </c:if>
                </c:when>
                <c:when test="${item.type == 'delay'}">
                    <div class="invoice-report">
                        <span class="action-type">СЧЕТ ОТЛОЖЕН</span>
                        <c:out value="${item.name}"/> - <c:out value="${item.amount}"/>  руб.
                    </div>
                </c:when>
            </c:choose>
        </phiz:invoiceMessages>
    </div>
    <div class="clear"></div>

    <c:if test="${phiz:size(form.data) > 0}">
        <div class="il-list">
            <c:forEach items="${form.data}" var="item">
                <tiles:insert definition="invoice">
                    <tiles:put name="id" value="${item.type == 'shopOrder'?item.uuid:item.id}"/>
                    <tiles:put name="name" value="${item.name}"/>
                    <tiles:put name="providerName" value="${item.providerName}"/>
                    <tiles:put name="imageId" value="${item.imageId}"/>
                    <tiles:put name="entityName" value="${item.entityName}"/>
                    <tiles:put name="state" value="${item.state}"/>
                    <tiles:put name="keyName" value="${item.keyName}"/>
                    <tiles:put name="keyValue" value="${item.keyValue}"/>
                    <tiles:put name="sum" value="${item.sum}"/>
                    <tiles:put name="type" value="${item.type}"/>
                    <c:choose>
                        <c:when test="${item.type == 'shopOrder'}">
                            <tiles:put name="onclick" value="openViewInvoiceWindow('${item.uuid}','${item.type}'); refreshNewCounter(${item.isNew}, '${item.uuid}', '${item.type}');"/>
                        </c:when>
                        <c:when test="${item.type == 'invoice'}">
                            <tiles:put name="onclick" value="openViewInvoiceWindow('${item.id}','${item.type}'); refreshNewCounter(${item.isNew}, '${item.id}', '${item.type}' );"/>
                            <tiles:put name="accessRecoverAutoSub" value="true"/>
                        </c:when>
                        <c:when test="${item.type == 'reminder'}">
                            <c:set var="reminderUrl" value="${phiz:getTemplateLinkByTemplateIdAndFormType(pageContext, item.id, item.formType)}&markReminder=true"/>
                            <tiles:put name="onclick" value="openViewInvoiceWindow('${item.id}','${item.type}', '${reminderUrl}'); refreshNewCounter(${item.isNew}, '${item.id}', '${item.type}' );"/>
                        </c:when>
                    </c:choose>
                    <tiles:put name="externalId" value="${item.externalId}"/>
                    <tiles:put name="isNew" value="${item.isNew}"/>
                </tiles:insert>
            </c:forEach>
        </div>
        <div class="clear"></div>
        <c:if test="${form.showInvoices}">
            <div class="il-allSum floatRight">
                <div class="il-allSumValue floatRight">
                    ${form.sumValue} руб.
                </div>
                <div class="il-allSumText floatRight">
                    Итого:
                </div>
            </div>
            <div class="clear"></div>
        </c:if>
        <c:if test="${!form.showInvoices && form.amountOfHiddenInvoices > 0}">
            <div class="il-showButton css3">
                <a href="${phiz:calculateActionURL(pageContext,"/private/payments")}?showInvoices=true">
                    <span class="floatLeft il-showText"> Показать остальные счета к оплате</span>
                    <span class="il-point floatLeft"></span>
                    <span class="il-hiddenNumber floatLeft">${form.amountOfHiddenInvoices}</span>
                    <div class="clear"></div>
                </a>
            </div>
            <div class="clear"></div>
        </c:if>
    </c:if>
    <c:if test="${phiz:size(form.data) == 0}">
        <div class="il-noInvoices">
            На сегодня нет выставленных счетов
        </div>
    </c:if>

    <c:if test="${phiz:impliesService('FinanceCalendarService')}">
    <div class="il-paymentsCalendar floatLeft">
        <tiles:insert definition="clientButton" flush="false">
            <tiles:put name="commandTextKey" value="button.calendar"/>
            <tiles:put name="commandHelpKey" value="button.calendar.help"/>
            <tiles:put name="bundle" value="paymentsBundle"/>
            <tiles:put name="viewType" value="buttonGrey"/>
            <tiles:put name="action" value="/private/finances/financeCalendar"/>
            <tiles:put name="image" value="calendarIcon.png"/>
            <tiles:put name="imageHover" value="calendarIconHover.png"/>
            <tiles:put name="imagePosition" value="left"/>
        </tiles:insert>
    </div>
    </c:if>
    <div class="clear"></div>

</div>

<script type="text/javascript">
    function refreshNewCounter(isNew, id, type)
    {
        if(isNew == false)
            return;

        var counterElement = document.getElementById('newInvoicesCounter');
        if(counterElement.innerHTML.trim()!="")
        {
            if(counterElement.innerHTML.trim()-1!=0)
                counterElement.innerHTML = counterElement.innerHTML.trim()-1;
            else
            {
                counterElement.innerHTML = "";
                $('#newInvoicesCounter').removeClass('newInvoicesNumber');
            }
        }
        var newInvoiceDiv = document.getElementById('invoice-newIcon_'+type+id);
        newInvoiceDiv.innerHTML = "";
    }

	function delayInvoice(id, type)
    {
        var actionUrl, params;
        var delayDate = document.getElementById('chooseDelayDate'+type+id).value;
        if (type == 'invoice')
        {
            actionUrl = "${phiz:calculateActionURL(pageContext, '/private/async/basket/invoice/process.do')}";
            params = 'field(chooseDelayDateInvoice)='+delayDate+"&id="+id;
        }
        else if(type == 'reminder')
        {
            actionUrl = "${phiz:calculateActionURL(pageContext, '/private/async/payments/quicklyCreateReminder.do')}";
            params = 'field(chooseDelayDateReminder)='+delayDate+"&reminderId=" + id;
        }
        else if (type == 'shopOrder')
        {
            actionUrl = "${phiz:calculateActionURL(pageContext, '/private/payments/internetShops/payment.do')}";
            params = 'field(chooseDelayDateOrder)='+delayDate+"&orderId="+id;
        }
        executeInvoiceOperation('delay',params, actionUrl);
    }

    function removeInvoice(id, type)
    {
        if (type == 'invoice')
        {
            var actionUrl = "${phiz:calculateActionURL(pageContext, '/private/async/basket/invoice/process.do')}";
            var params = "id="+id;
            executeInvoiceOperation('delete',params, actionUrl);
        }
        else if (type == 'shopOrder')
        {
            location.href = '${phiz:calculateActionURL(pageContext,'/private/payments/internetShops/orderCancel.do')}?orderId=' + id + '&pageType=list'
        }
        else if(type == 'reminder')
        {
            var actionUrl = "${phiz:calculateActionURL(pageContext, '/private/async/payments/quicklyCreateReminder.do')}";
            executeInvoiceOperation('delete', "reminderId="+id, actionUrl);
        }
    }

    function executeInvoiceOperation(operationName, params, actionUrl)
    {
        ajaxQuery(
            params + "&operation=button." + operationName,
            actionUrl,
            function(data)
            {
                removeAllMessages();
                removeAllErrors();

                if (data.success || data.state == "success")
                {
                    if (data.messages.length > 0)
                        post(location.href, {successMessage: data.messages[0]});
                    else
                        post(location.href);
                    return;
                }

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
            "json"
        );
    }

    doOnLoad(function(){
        initInvoiceTitle();

        $(".il-showButton").bind("mousedown", function() {
            $(this).addClass('pressed');
            $(this).removeClass('hover');
        });
        $(".il-showButton").bind("mouseup", function() {
            $(this).removeClass('pressed');
            $(this).addClass('hover');
        });
        $(".il-showButton").bind("mouseenter", function() {
            $(this).addClass('hover');
        });
        $(".il-showButton").bind("mouseleave", function() {
            $(this).removeClass('hover');
            $(this).removeClass('pressed');
        });
        $(".il-invoice").bind("mouseenter", function() {
            $(this).find('.il-payText').show();
            $(this).addClass('hover');
            initInvoiceTitleOne($(this).parent('.il-main'));
        });
        $(".il-invoice").bind("mouseleave", function() {
            $(this).find('.il-payText').hide();
            $(this).removeClass('hover');
            initInvoiceTitleOne($(this).parent('.il-main'));
        });
        $(".il-sum").bind("mouseenter", function() {
            $(this).addClass('hover');
        });
        $(".il-sum").bind("mouseleave", function() {
            $(this).removeClass('hover');
            $(this).removeClass('pressed');
            initInvoiceTitleOne($(this).parents('.il-main'));
        });
        $(".il-sum").bind("mousedown", function() {
            $(this).addClass('pressed');
            $(this).removeClass('hover');
        });
        $(".il-sum").bind("mouseup", function() {
            $(this).removeClass('pressed');
            $(this).addClass('hover');
        });
        $(".il-hiddenListButton").bind("mouseenter", function() {
            $(this).addClass('hover');
            $(this).parents('.il-right-style').find('.il-sumTextWrap .il-payText').hide();
            initInvoiceTitleOne($(this).parents('.il-main'));
        });
        $(".il-hiddenListButton").bind("mouseleave", function() {
            $(this).removeClass('hover');
            $(this).parents('.il-right-style').find('.il-sumTextWrap .il-payText').show();
            $(this).parents('.il-right-style').find('.il-sumTextWrap').removeClass('hover');
            $(this).removeClass('pressed');
            $(".il-hiddenList").each(function() {
                $(this).hide();
            });
            initInvoiceTitleOne($(this).parents('.il-main'));
        });
        $(".il-hiddenListButton").bind("mousedown", function() {
            $(this).addClass('pressed');
            $(this).removeClass('hover');
        });
        $(".il-hiddenListButton").bind("click", function() {
            $(this).parent().find(".il-hiddenList").show();
        });
        $(".il-hiddenListButton").bind("mouseup", function() {
            $(this).removeClass('pressed');
            $(this).addClass('hover');
        });
        $(".il-hiddenList").bind("mouseleave", function() {
            $(this).hide();
        });
        $(".il-delayButton").bind("mouseenter", function() {
            $(this).addClass('hover');
        });
        $(".il-delayButton").bind("mouseleave", function() {
            $(this).removeClass('hover');
            $(this).removeClass('pressed');
        });
        $(".il-recoverAutoSubButton").bind("mouseenter", function() {
            $(this).addClass('hover');
        });
        $(".il-recoverAutoSubButton").bind("mouseleave", function() {
            $(this).removeClass('hover');
            $(this).removeClass('pressed');
        });
        $(".il-deleteButton").bind("mouseenter", function() {
            $(this).addClass('hover');
        });
        $(".il-deleteButton").bind("mouseleave", function() {
            $(this).removeClass('hover');
            $(this).removeClass('pressed');
        });
        $(".il-closeIcon").bind("mouseenter", function() {
            $(this).addClass('hover');
        });
        $(".il-closeIcon").bind("mouseleave", function() {
            $(this).removeClass('hover');
        });
        $(".il-closeIcon").bind("click", function() {
            $('.il-banner').hide();
        });
    });

     function openViewInvoiceWindow(id, type, reminderUrl)
     {
         var params;
         if(type == "shopOrder")
         {
             params = {id: id, type: "page", pageUrl: ("${phiz:calculateActionURL(pageContext, '/private/payments/internetShops/payment')}?internetOrder=true&orderId=" + id)}
         }
         else if(type == "reminder")
         {
             params = {id: id, type: "page", pageUrl: reminderUrl}
         }
         else
         {
             params = {id: id, type: "window", winId: "${invoiceWinId}", viewUrl: "${invoiceViewUrl}", actionUrl: "${phiz:calculateActionURL(pageContext, '/private/async/basket/invoice/process')}"}
         }

         new InvoiceManager(params).open();
         return false;
     }

     function getInvoiceId(invoice)
     {
         var elementId = invoice.attr("id");
         var id = elementId.replace("invoice","");
         return id;
     }

     function getInvoiceType(invoice)
     {
         return $(invoice).find(".il-type").html();
     }

    </script>

