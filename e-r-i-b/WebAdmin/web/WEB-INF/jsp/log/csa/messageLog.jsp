<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>

<html:form action="/log/csa/messages">

<c:set var="form" value="${phiz:currentForm(pageContext)}"/>

<tiles:insert definition="logMain">
<tiles:put name="submenu" type="string" value="CSAMessgeLog"/>
<tiles:put name="pageTitle" type="string">
	<bean:message key="label.message.page.name" bundle="logBundle"/>
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

       <%-- row2 --%>
    <tiles:insert definition="filterTextField" flush="false">
            <tiles:put name="label" value="label.passport"/>
            <tiles:put name="bundle" value="logBundle"/>
            <tiles:put name="mandatory" value="false"/>
            <tiles:put name="size" value="16"/>
            <tiles:put name="maxlength" value="20"/>
            <tiles:put name="name" value="number"/>
            <tiles:put name="isDefault" value="Паспорт"/>
    </tiles:insert>

	<tiles:insert definition="filterEntryField" flush="false">
			<tiles:put name="label" value="label.system"/>
			<tiles:put name="bundle" value="logBundle"/>
			<tiles:put name="mandatory" value="false"/>
			<tiles:put name="data">
				<html:select property="filter(system)" styleClass="select">
                    <html:option value="">Все</html:option>
                    <c:forEach var="system" items="${form.systemList}">
                        <html:option value="${system}">
                            <bean:message key="message.log.system.${system}" bundle="logBundle"/>
                        </html:option>
                    </c:forEach>
				</html:select>
			</tiles:put>
	</tiles:insert>

    <%-- row3 --%>
    <tiles:insert definition="filterDateField" flush="false">
        <tiles:put name="label" value="label.birthDay"/>
        <tiles:put name="bundle" value="personsBundle"/>
        <tiles:put name="mandatory" value="false"/>
        <tiles:put name="name" value="birthday"/>
    </tiles:insert>

    <tiles:insert definition="filter2TextField" flush="false">
        <tiles:put name="label" value="label.request"/>
        <tiles:put name="bundle" value="logBundle"/>
        <tiles:put name="name"   value="requestTag"/>
        <tiles:put name="size"   value="40"/>
        <tiles:put name="maxlength"  value="256"/>
        <tiles:put name="isDefault"><bean:message key="label.request.tag" bundle="logBundle"/></tiles:put>
        <tiles:put name="name2"   value="requestWord"/>
        <tiles:put name="size2"   value="40"/>
        <tiles:put name="maxlength2"  value="256"/>
        <tiles:put name="default2"><bean:message key="label.request.word" bundle="logBundle"/></tiles:put>
    </tiles:insert>

    <%-- row4 --%>
     <tiles:insert definition="filterTextField" flush="false">
        <tiles:put name="label" value="label.responce"/>
        <tiles:put name="bundle" value="logBundle"/>
        <tiles:put name="name"   value="responceWord"/>
        <tiles:put name="size"   value="40"/>
        <tiles:put name="maxlength"  value="256"/>
        <tiles:put name="isDefault"><bean:message key="label.responce.word" bundle="logBundle"/></tiles:put>
    </tiles:insert>

    <%-- row 5 --%>
    <tiles:insert definition="filterTextField" flush="false">
			<tiles:put name="label" value="label.requestresponce"/>
			<tiles:put name="bundle" value="logBundle"/>
			<tiles:put name="mandatory" value="false"/>
            <tiles:put name="isFastSearch" value="true"/>
            <tiles:put name="size" value="87"/>
            <tiles:put name="maxlength" value="100"/>
            <tiles:put name="isDefault"><bean:message key="label.text.word" bundle="logBundle"/></tiles:put>
			<tiles:put name="name" value="requestresponceWord"/>
	</tiles:insert>

    <tiles:insert definition="filterEntryField" flush="false">
        <tiles:put name="label" value="label.application"/>
        <tiles:put name="bundle" value="logBundle"/>
        <tiles:put name="mandatory" value="false"/>
        <tiles:put name="data">
            <html:select property="filter(application)" styleClass="select">
                <html:option value="">Все</html:option>
                <html:option value="CSABack"><bean:message key="user.log.application.CSABack" bundle="logBundle"/></html:option>
                <html:option value="CSAFront"><bean:message key="user.log.application.CSAFront" bundle="logBundle"/></html:option>
                <html:option value="CsaErmbListener"><bean:message key="user.log.application.CSAERMBListener" bundle="logBundle"/></html:option>
                <html:option value="PhizIA"><bean:message key="user.log.application.admin" bundle="logBundle"/></html:option>
                <html:option value="PhizIC"><bean:message key="user.log.application.client" bundle="logBundle"/></html:option>
                <c:forEach var="mAPI" items="${phiz:getMobileApiNames()}">
                    <html:option value="${mAPI}"><bean:message key="system.log.application.${mAPI}" bundle="logBundle"/></html:option>
                </c:forEach>
                <html:option value="atm"><bean:message key="system.log.application.atm" bundle="logBundle"/></html:option>
                <html:option value="socialApi"><bean:message key="system.log.application.social" bundle="logBundle"/></html:option>
            </html:select>
        </tiles:put>
	</tiles:insert>

    <tiles:insert definition="filterTextField" flush="false">
			<tiles:put name="label" value="label.department.code"/>
			<tiles:put name="bundle" value="logBundle"/>
			<tiles:put name="mandatory" value="false"/>
			<tiles:put name="name" value="departmentCode"/>
            <tiles:put name="isFastSearch" value="true"/>
	</tiles:insert>

    <tiles:insert definition="filterTextField" flush="false">
			<tiles:put name="label" value="label.promoterId"/>
			<tiles:put name="bundle" value="logBundle"/>
			<tiles:put name="mandatory" value="false"/>
			<tiles:put name="name" value="promoterId"/>
            <tiles:put name="maxlength" value="100"/>
            <tiles:put name="size" value="50"/>
            <tiles:put name="addClassLabel" value="mainJournal"/>
            <tiles:put name="addClassValue" value="mainJournal"/>
            <tiles:put name="isFastSearch" value="false"/>
	</tiles:insert>

    <tiles:insert definition="filterTextField" flush="false">
			<tiles:put name="label" value="label.mGUID"/>
			<tiles:put name="bundle" value="logBundle"/>
			<tiles:put name="mandatory" value="false"/>               
			<tiles:put name="name" value="mGUID"/>
            <tiles:put name="size" value="40"/>
            <tiles:put name="maxlength" value="32"/>
            <tiles:put name="addClassLabel" value="mainJournal"/>
            <tiles:put name="addClassValue" value="mainJournal"/>
	</tiles:insert>

    <tiles:insert definition="filterTextField" flush="false">
			<tiles:put name="label" value="label.ip.address"/>
			<tiles:put name="bundle" value="logBundle"/>
			<tiles:put name="mandatory" value="false"/>
			<tiles:put name="maxlength" value="15"/>
			<tiles:put name="name" value="ipAddress"/>
	</tiles:insert>

    <tiles:insert definition="filterEntryField" flush="false">
        <tiles:put name="label" value="label.request.result"/>
        <tiles:put name="bundle" value="logBundle"/>
        <tiles:put name="mandatory" value="false"/>
        <tiles:put name="data">
            <html:select property="filter(requestState)" styleClass="select">
                <html:option value="">Все</html:option>
                <html:option value="success">Успешно</html:option>
                <html:option value="fail">Неуспешно</html:option>
            </html:select>
        </tiles:put>
	</tiles:insert>

    <tiles:insert definition="filterTextField" flush="false">
        <tiles:put name="label" value="label.log.guest.phone.number"/>
        <tiles:put name="bundle" value="logBundle"/>
        <tiles:put name="mandatory" value="false"/>
        <tiles:put name="name" value="phoneNumber"/>
        <tiles:put name="size"   value="11"/>
        <tiles:put name="maxlength" value="11"/>
        <tiles:put name="addClassLabel" value="guestJournal"/>
        <tiles:put name="addClassValue" value="guestJournal"/>
    </tiles:insert>

    <tiles:insert definition="filterEntryField" flush="false">
        <tiles:put name="label" value="label.log.guest.type"/>
        <tiles:put name="bundle" value="logBundle"/>
        <tiles:put name="mandatory" value="false"/>
        <tiles:put name="isFastSearch" value="true"/>
        <tiles:put name="data">
            <html:checkbox property="filter(isGuestLog)" styleClass="logJournal" onclick="chooseGuestJournal();"/>
        </tiles:put>
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

<tiles:put name="additionalFilterButtons" type="string">
     <tiles:insert definition="commandButton" flush="false">
        <tiles:put name="commandKey" value="button.download"/>
        <tiles:put name="commandHelpKey" value="button.download"/>
        <tiles:put name="bundle" value="logBundle"/>
    </tiles:insert>
</tiles:put>

<tiles:put name="data" type="string">
    <jsp:include page="messageLogData.jsp" flush="false"/>
</tiles:put>
</tiles:insert>
</html:form>
