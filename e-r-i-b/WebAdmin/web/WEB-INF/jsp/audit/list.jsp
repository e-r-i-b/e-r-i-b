<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html:form action="/audit/businessDocument">
<tiles:insert definition="auditMain">
<tiles:put name="submenu" type="string" value="BusinessDocumentList"/>
<c:set var="form" value="${ShowBusinessDocumentListForm}"/>
<tiles:put name="pageTitle" type="string" value="Список платежей и заявок"/>
<tiles:put name="menu" type="string">
	<tiles:insert definition="clientButton" flush="false">
		<tiles:put name="commandTextKey" value="button.printlist"/>
		<tiles:put name="commandHelpKey" value="button.printlist.help"/>
		<tiles:put name="bundle"         value="claimsBundle"/>
		<tiles:put name="onclick">
		  printPaymentList(event)
		</tiles:put>
        <tiles:put name="viewType" value="blueBorder"/>
	</tiles:insert>
	<tiles:insert definition="clientButton" flush="false">
		<tiles:put name="commandTextKey" value="button.print"/>
		<tiles:put name="commandHelpKey" value="button.print.help"/>
		<tiles:put name="bundle"         value="claimsBundle"/>
		<tiles:put name="onclick">
		  printPayments(event)
		</tiles:put>
        <tiles:put name="viewType" value="blueBorder"/>
	</tiles:insert>
</tiles:put>

<%-- фильтр --%>
<tiles:put name="filter" type="string">

    <c:set var="colCount" value="3" scope="request"/>
    <%-- ------------- row 1 ----------------------------- --%>
    <tiles:insert definition="filterTextField" flush="false">
        <tiles:put name="label"  value="label.client"/>
        <tiles:put name="bundle" value="logBundle"/>
        <tiles:put name="name"   value="fio"/>
        <tiles:put name="size"   value="40"/>
        <tiles:put name="maxlength"  value="255"/>
        <tiles:put name="isDefault" value="Фамилия Имя Отчество"/>
    </tiles:insert>

    <tiles:insert definition="filterEntryField" flush="false">
           <tiles:put name="label" value="label.period"/>
           <tiles:put name="bundle" value="logBundle"/>
           <tiles:put name="mandatory" value="false"/>
           <tiles:put name="data">
               <span style="white-space:nowrap;">
                    &nbsp;&nbsp;&nbsp;с&nbsp;
                    <span style="font-weight:normal;overflow:visible;cursor:default;">
                        <input type="text"
                                size="8" name="filter(fromDate)" class="dot-date-pick"
                                maxsize="10"
                                value="<bean:write name="form" property="filters.fromDate" format="dd.MM.yyyy"/>"/>
                        <input type="text"
                                size="6" name="filter(fromTime)"
                                maxsize="8" class="time-template"
                                value="<bean:write name="form" property="filters.fromTime" format="HH:mm:ss"/>"
                                onkeydown="onTabClick(event,'filter(toDate)');"/>
                    </span></br>
                    &nbsp;по&nbsp;
                    <span style="font-weight:normal;cursor:default;">
                        <input type="text"
                                size="8" name="filter(toDate)" class="dot-date-pick"
                                maxsize="10"
                                value="<bean:write name="form" property="filters.toDate" format="dd.MM.yyyy"/>"/>

                        <input type="text"
                                size="6" name="filter(toTime)"
                                maxsize="7"   class="time-template"
                                value="<bean:write name="form" property="filters.toTime" format="HH:mm:ss"/>"/>
                    </span>
               </span>
           </tiles:put>
    </tiles:insert>

    <tiles:insert definition="filterEntryField" flush="false">
        <tiles:put name="label" value="label.formName"/>
        <tiles:put name="bundle" value="claimsBundle"/>
        <tiles:put name="data">
            <script type="text/javascript">
                var autoPaymentForms = new Array();

                function changeAutoPayment()
                {
                    var ids = ensureElement("filter(formId)").value;
                    ensureElement("filter(autoPayment)").value = autoPaymentForms[ids];
                }

            </script>

            <html:select property="filter(formId)" value="${form.filters['formId']}" onclick="changeAutoPayment()" onchange="changeAutoPayment()" styleId="filter(formId)" styleClass="filterSelectWidth">
                <html:option value="">Все</html:option>
                <c:forEach items="${form.filterPaymentForms}" var="frm">
                    <c:set var="formName">
                        <bean:message bundle="auditBundle" key="paymentform.${frm.name}" failIfNone="false"/>
                    </c:set>
                    <c:if test="${not empty formName}">
                        <html:option value="${frm.ids}">
                            <c:out value="${formName}"/>
                        </html:option>
                    </c:if>

                    <script type="text/javascript">
                        autoPaymentForms['${frm.ids}'] = '${frm.autoPayment}';
                    </script>

                </c:forEach>
            </html:select>

            <html:hidden property="filter(autoPayment)" styleId="filter(autoPayment)"/>
        </tiles:put>
    </tiles:insert>

    <%-- ------------- row 2 ----------------------------- --%>
    <tiles:insert definition="filterTextField" flush="false">
        <tiles:put name="label"  value="label.document"/>
        <tiles:put name="bundle" value="claimsBundle"/>
        <tiles:put name="name"   value="dul"/>
        <tiles:put name="size"   value="32"/>
        <tiles:put name="maxlength"  value="32"/>
        <tiles:put name="isDefault" value="Серия номер"/>
    </tiles:insert>

    <tiles:insert definition="filterEntryField" flush="false">
        <tiles:put name="label" value="label.empty"/>
        <tiles:put name="bundle" value="claimsBundle"/>
        <tiles:put name="data">
            <html:checkbox property="filter(showInitialPayments)" styleId="showInitial"/>&nbsp;<b><bean:message bundle="claimsBundle" key="label.show.initial.payments"/></b>
        </tiles:put>
    </tiles:insert>


    <tiles:insert definition="filterEntryField" flush="false">
        <tiles:put name="label" value="label.state"/>
        <tiles:put name="bundle" value="claimsBundle"/>
        <tiles:put name="data">
            <c:set var="status" value="${form.status}"/>
            <html:select property="filter(state)" styleClass="filterSelect" style="width:180px" styleId="state">
                <html:option value="">Все</html:option>
                <html:option value="CANCELATION">Аннулирован</html:option>
                <html:option value="CONSIDERATION">В рассмотрении</html:option>
                <html:option value="INITIAL,SAVED">Введен</html:option>
                <html:option value="RETURNED">Возвращен</html:option>
                <html:option value="MODIFICATION">Изменён</html:option>
                <html:option value="EXECUTED">Исполнен</html:option>
                <html:option value="TICKETS_WAITING">Ожидание билетов</html:option>
                <html:option value="DISPATCHED,SENDED,STATEMENT_READY">Обрабатывается / Принято к исполнению</html:option>
                <html:option value="ACCEPTED">Одобрен</html:option>
                <html:option value="WAIT_CONFIRM">Ожидает дополнительного подтверждения</html:option>
                <html:option value="DELAYED_DISPATCH,OFFLINE_DELAYED">Ожидается обработка</html:option>
                <html:option value="REFUSED">Отказан</html:option>
                <html:option value="RECALLED">Отозван</html:option>
                <html:option value="RECEIVED">Получен банком получателем</html:option>
                <html:option value="ADOPTED">Принята</html:option>
                <html:option value="ERROR,UNKNOW,SENT">Приостановлен</html:option>
                <html:option value="ABS_RECALL_TIMEOUT">Таймаут при отзыве в АБС (ЕРИБ)</html:option>
                <html:option value="ABS_GATE_RECALL_TIMEOUT">Таймаут при отзыве в АБС (шлюз)</html:option>
                <html:option value="BILLING_CONFIRM_TIMEOUT">Таймаут при подтверждении в биллинге (ЕРИБ)</html:option>
                <html:option value="BILLING_GATE_CONFIRM_TIMEOUT">Таймаут при подтверждении в биллинге (шлюз)</html:option>
                <html:option value="COMPLETION">Требуется доработка</html:option>
                <html:option value="APPROVED">Утвержден</html:option>
                <html:option value="DRAFT">Частично заполнен</html:option>
            </html:select>
        </tiles:put>
    </tiles:insert>

    <%-- ------------- row 3 ----------------------------- --%>
    <tiles:insert definition="filterDateField" flush="false">
        <tiles:put name="label" value="label.birthDate"/>
        <tiles:put name="bundle" value="claimsBundle"/>
        <tiles:put name="name" value="birthday"/>
    </tiles:insert>

    <tiles:insert definition="filterEntryField" flush="false">
        <tiles:put name="label" value="label.empty"/>
        <tiles:put name="bundle" value="claimsBundle"/>
        <tiles:put name="data">
            <html:checkbox property="filter(showDeleted)" styleId="showDeleted"/>&nbsp;<b><bean:message bundle="claimsBundle" key="label.show.deleted.payments"/></b>
        </tiles:put>
    </tiles:insert>

    <tiles:insert definition="filterEmptytField" flush="false">
    </tiles:insert>


    <%-- ------------- row 4 ----------------------------- --%>
    <tiles:insert definition="filterTextField" flush="false">
        <tiles:put name="label" value="label.personId"/>
        <tiles:put name="bundle" value="logBundle"/>
        <tiles:put name="name" value="loginId"/>
        <tiles:put name="isFastSearch" value="true"/>
    </tiles:insert>

    <tiles:insert definition="filterEmptytField" flush="false">
    </tiles:insert>


    <tiles:insert definition="filterTextField" flush="false">
        <tiles:put name="label" value="label.receiver"/>
        <tiles:put name="bundle" value="claimsBundle"/>
        <tiles:put name="name" value="receiverName"/>
        <tiles:put name="isFastSearch" value="true"/>
        <tiles:put name="maxlength"  value="255"/>
        <tiles:put name="size" value="35"/>
    </tiles:insert>

    <%-- ------------- row 5 ----------------------------- --%>
    <tiles:insert definition="filterEntryField" flush="false">
        <tiles:put name="label" value="label.amount.operation"/>
        <tiles:put name="bundle" value="claimsBundle"/>
        <tiles:put name="isFastSearch" value="true"/>
        <tiles:put name="data">
            от
            <html:text property="filter(fromAmount)" size="10" maxlength="10"  styleClass="moneyField"/>
            &nbsp;
            до
            <html:text property="filter(toAmount)"   size="10" maxlength="10"  styleClass="moneyField"/>

        </tiles:put>
    </tiles:insert>

    <tiles:insert definition="filterTextField" flush="false">
        <tiles:put name="label"  value="label.employeeFIO"/>
        <tiles:put name="bundle" value="logBundle"/>
        <tiles:put name="name"   value="employeeFIO"/>
        <tiles:put name="size"   value="40"/>
        <tiles:put name="maxlength"  value="255"/>
        <tiles:put name="isFastSearch" value="true"/>
    </tiles:insert>

    <tiles:insert definition="filterTextField" flush="false">
        <tiles:put name="label" value="label.paymentNumber"/>
        <tiles:put name="noWrapLabel" value="true"/>
        <tiles:put name="bundle" value="logBundle"/>
        <tiles:put name="name" value="number"/>
        <tiles:put name="isFastSearch" value="true"/>
        <tiles:put name="size" value="35"/>
    </tiles:insert>

    <%-- ------------- row 6 ----------------------------- --%>
    <tiles:insert definition="filterTextField" flush="false">
        <tiles:put name="label" value="label.nameOSB"/>
        <tiles:put name="bundle" value="claimsBundle"/>
        <tiles:put name="name" value="nameOSB"/>
        <tiles:put name="isFastSearch" value="true"/>
        <tiles:put name="size" value="35"/>
        <tiles:put name="maxlength" value="100"/>
    </tiles:insert>

    <tiles:insert definition="filterEmptytField" flush="false">
    </tiles:insert>

    <tiles:insert definition="filterTextField" flush="false">
        <tiles:put name="label" value="label.madeOperationId"/>
        <tiles:put name="bundle" value="claimsBundle"/>
        <tiles:put name="name" value="madeOperationId"/>
        <tiles:put name="isFastSearch" value="true"/>
        <tiles:put name="size" value="35"/>
        <tiles:put name="maxlength" value="100"/>
    </tiles:insert>

    <tiles:insert definition="filterEntryField" flush="false">
        <tiles:put name="label" value="label.creation.type"/>
        <tiles:put name="bundle" value="auditBundle"/>
        <tiles:put name="isFastSearch" value="true"/>
        <tiles:put name="data">
            <html:select property="filter(creationType)" styleClass="filterSelect" style="width:180px" styleId="state">
                <html:option value="">Все</html:option>
                <html:option value="1">Веб-приложение</html:option>
                <html:option value="3">Мобильные приложения</html:option>
                <html:option value="2">SMS-приложения</html:option>
                <html:option value="4">Терминалы банка</html:option>
                <html:option value="5">Социальные приложения</html:option>
            </html:select>
        </tiles:put>
    </tiles:insert>

	<script type="text/javascript">
		function initTemplates()
		{
            addClearMasks(null,
                    function(event)
                    {
                        clearInputTemplate('filter(fromDate)','__.__.____');
                        clearInputTemplate('filter(toDate)','__.__.____');
                        clearInputTemplate('filter(fromTime)','__:__:__');
                        clearInputTemplate('filter(toTime)','__:__:__');
                    });
		}

		initTemplates();
	</script>
</tiles:put>

<tiles:put name="fastSearchFilter" value="true"/>

<%-- данные --%>
<tiles:put name="data" type="string">
    <script type="text/javascript">
        function addParam(params, name)
        {
            var param;
            var elem = $('[name='+name+']')[0];
            var elemValue = customPlaceholder.getCurrentVal(elem);
            if ( elemValue != "" )
            {
                param = name + "=" + elemValue;
                if (params != "")
                {
                    params = params +'&' + param;
                }
                else
                {
                    params = param;
                }
            }
            return params;
        }

        function printPaymentList(event)
        {
            <c:set var="u" value="/audit/listBusinessDocumentPrint.do"/>
            var url = "${phiz:calculateActionURL(pageContext,u)}";
            var params = "";
            params = addParam(params,"filter(loginId)");
            params = addParam(params,"filter(fio)");
            params = addParam(params,"filter(number)");			params = addParam(params,"filter(formId)");
            params = addParam(params,"filter(state)");
            params = addParam(params,"filter(fromDate)");
            params = addParam(params,"filter(fromTime)");
            params = addParam(params,"filter(toDate)");
            params = addParam(params,"filter(toTime)");
            params = addParam(params,"filter(fromAmount)");
            params = addParam(params,"filter(toAmount)");
            params = addParam(params,"filter(receiverName)");
            params = addParam(params,"filter(showInitialPayments)");
            params = addParam(params,"filter(showDeleted)");
            params = addParam(params,"filter(birthday)");
            params = addParam(params,"filter(dul)");
            params = addParam(params,"filter(nameOSB)");
            params = addParam(params,"filter(madeOperationId)");
            params = addParam(params,"filter(autoPayment)");
            openWindow(event,url+'?status=<c:out value="${ShowBusinessDocumentListForm.status}"/>&'+params,"","resizable=1,menubar=1,toolbar=0,scrollbars=1");
        }

        function printPayments(event)
        {
            checkIfOneItem("selectedIds");
            if (!checkSelection('selectedIds', 'Выберите операции'))
                return;
            if(!checkOneSelection('selectedIds', 'Выберите один документ'))
                return;

            var ids = document.getElementsByName("selectedIds");
            var parameters = '';
            for (var i = 0; i < ids.length; i++)
                if(ids[i].checked)
                    parameters = parameters + '&id='+ids[i].value;
            var winpar = "fullscreen=0,location=0,menubar=0,status=0,toolbar=0,resizable=1" +
                    ", width=400" +
                    ", height=400" +
                    ", left=0" + ((screen.width) / 2 - 100) +
                    ", top=0" + ((screen.height) / 2 - 100);
            <c:set var="url" value="${phiz:calculateActionURL(pageContext,'/payments/check_print.do')}"/>
            var pwin = openWindow(event, '${url}?'+parameters, "dialog", winpar);
            pwin.focus();
        }

        function changeState()
        {
            var disabled = true;
            if ($("#state option:selected").val() == '') disabled = false;
            $("#showInitial").attr("disabled", disabled);
            $("#showDeleted").attr("disabled", disabled);
        }
        $(document).ready(function(){
            $("#state").change(function() {
                changeState();
            });

            changeState();
        });
    </script>
    <jsp:include page="auditData.jsp" flush="false"/>
</tiles:put>
</tiles:insert>
</html:form>
