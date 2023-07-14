<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>

<html:form action="/log/csa/action/guest">

<c:set var="form" value="${phiz:currentForm(pageContext)}"/>

<tiles:insert definition="logMain">
<tiles:put name="submenu" type="string" value="CSAGuestEntryLog"/>
<tiles:put name="pageTitle" type="string">
    <bean:message key="label.guest.entry.page.name" bundle="logBundle"/>
</tiles:put>

<tiles:put name="filter" type="string">
<tiles:put name="fastSearchFilter" value="true"/>
<c:set var="colCount" value="2" scope="request"/>
<%-- row1 --%>
<tiles:insert definition="filterTextField" flush="false">
    <tiles:put name="label" value="label.client"/>
    <tiles:put name="bundle" value="personsBundle"/>
    <tiles:put name="mandatory" value="false"/>
    <tiles:put name="size" value="50"/>
    <tiles:put name="maxlength" value="255"/>
    <tiles:put name="name" value="fio"/>
    <tiles:put name="isDefault" value="Фамилия Имя Отчество"/>
</tiles:insert>

<tiles:insert definition="filterEntryField" flush="false">
    <tiles:put name="label" value="label.period"/>
    <tiles:put name="bundle" value="logBundle"/>
    <tiles:put name="mandatory" value="false"/>
    <tiles:put name="data">
        с&nbsp;
            <span style="font-weight:normal;overflow:visible;cursor:default;">
                <input type="text"
                       size="10" name="filter(fromDate)" class="dot-date-pick"
                       value="<bean:write name="form" property="filters.fromDate" format="dd.MM.yyyy"/>"
                        />
                <input type="text"
                       size="8" name="filter(fromTime)"
                       value="<bean:write name="form" property="filters.fromTime" format="HH:mm:ss"/>"
                       class="time-template"
                       onkeydown="onTabClick(event,'filter(toDate)')"/>
            </span>
        &nbsp;по&nbsp;
            <span style="font-weight:normal;cursor:default;">
                <input type="text"
                       size="10" name="filter(toDate)" class="dot-date-pick"
                       value="<bean:write name="form" property="filters.toDate" format="dd.MM.yyyy"/>"/>

                <input type="text"
                       size="8" name="filter(toTime)"
                       value="<bean:write name="form" property="filters.toTime" format="HH:mm:ss"/>"
                       class="time-template"/>
            </span>
    </tiles:put>
</tiles:insert>

<tiles:insert definition="filterDateField" flush="false">
    <tiles:put name="label" value="label.birthDay"/>
    <tiles:put name="bundle" value="personsBundle"/>
    <tiles:put name="mandatory" value="false"/>
    <tiles:put name="name" value="birthday"/>
</tiles:insert>

<tiles:insert definition="filterTextField" flush="false">
    <tiles:put name="label" value="label.department.code"/>
    <tiles:put name="bundle" value="logBundle"/>
    <tiles:put name="mandatory" value="false"/>
    <tiles:put name="name" value="TB"/>
    <tiles:put name="isFastSearch" value="true"/>
</tiles:insert>

<tiles:insert definition="filterTextField" flush="false">
    <tiles:put name="label" value="label.ip.address"/>
    <tiles:put name="bundle" value="logBundle"/>
    <tiles:put name="mandatory" value="false"/>
    <tiles:put name="maxlength" value="15"/>
    <tiles:put name="name" value="ipAddres"/>
</tiles:insert>

<tiles:insert definition="filterTextField" flush="false">
    <tiles:put name="label" value="label.log.guest.phone.number"/>
    <tiles:put name="bundle" value="logBundle"/>
    <tiles:put name="mandatory" value="false"/>
    <tiles:put name="name" value="phoneNumber"/>
    <tiles:put name="size"   value="11"/>
    <tiles:put name="maxlength" value="11"/>
</tiles:insert>

<script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/jquery.autocomplete.js"></script>
<script type="text/javascript">
    jQuery.fn.setType = function(name, type, idGreyText)
    {
        $("input[name=" + name + "]").autocomplete(
                "${phiz:calculateActionURL(pageContext, "/log/csa/messages")}",
                {
                    extraParams: "operation=button.ajaxSearch&field(type)=" + type,
                    delay:500,
                    minChars:3,
                    maxItemsToShow:10,
                    matchSubset:0,
                    width:350,
                    autoFill:true,
                    greyStyle:true,
                    greyStyleElementId:idGreyText,
                    lineSeparator:'@'
                }
        );
    }

    $(document).ready(function(){
        // для каждого поля свой типа запроса
        $("input[name=filter(requestTag)]").setType("filter(requestTag)", "R", "requestTagHidden");

        <c:if test="${form.fromStart}">
        //показываем фильтр при старте
        switchFilter(this);
        </c:if>
        chooseGuestJournal();
    });

    function chooseGuestJournal()
    {
        if ($("input[name='filter(isGuestLog)']").attr("checked") == true)
        {
            $(".mainJournal").css("display", "none");
            $(".guestJournal").css("display", "");
        }
        else
        {
            $(".mainJournal").css("display", "");
            $(".guestJournal").css("display", "none");
        }
    };
</script>

</tiles:put>

<tiles:put name="data" type="string">
    <jsp:include page="guestActionLogData.jsp" flush="false"/>
</tiles:put>
</tiles:insert>
</html:form>
