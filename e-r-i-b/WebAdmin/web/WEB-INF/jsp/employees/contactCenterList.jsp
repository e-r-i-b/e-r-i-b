<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@taglib uri="http://struts.application-servers.com/layout" prefix="sl"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>

<html:form action="/contact/center/dictionary/employee">

<c:set var="form" value="${phiz:currentForm(pageContext)}"/>

<tiles:insert definition="dictionary">
  <tiles:put name="pageTitle" type="string">
    <bean:message key="list.title" bundle="employeesBundle"/>
  </tiles:put>


  <!-- фильтр  -->
  <tiles:put name="filter" type="string">
      <tiles:insert definition="filterTextField" flush="false">
          <tiles:put name="label" value="login.employee"/>
          <tiles:put name="bundle" value="employeesBundle"/>
          <tiles:put name="mandatory" value="false"/>
          <tiles:put name="name" value="fio"/>
          <tiles:put name="size"   value="50"/>
          <tiles:put name="maxlength"  value="255"/>
          <tiles:put name="isDefault" value="Фамилия Имя Отчество"/>
      </tiles:insert>
      <tiles:insert definition="filterEntryField" flush="false">
          <tiles:put name="label" value="label.area"/>
          <tiles:put name="bundle" value="contactCenterAreaBundle"/>
          <tiles:put name="data">
              <html:select property="filters(area_id)" styleClass="select">
                  <html:option value="">Все</html:option>
                  <c:forEach var="area" items="${form.contactCenterAreas}">
                      <html:option value="${area.id}">
                          <c:out value="${area.name}"/>
                      </html:option>
                  </c:forEach>
              </html:select>
          </tiles:put>
      </tiles:insert>

	  <tiles:insert definition="filterTextField" flush="false">
		    <tiles:put name="label" value="label.filterId"/>
		    <tiles:put name="bundle" value="employeesBundle"/>
		    <tiles:put name="name" value="id"/>
            <tiles:put name="maxlength" value="22"/>
            <tiles:put name="size" value="14"/>
	  </tiles:insert>
  </tiles:put>



  <tiles:put name="data" type="string">
	<script type="text/javascript">
        <c:if test="${form.fromStart}">
           //показываем фильтр при старте
           switchFilter(this);
        </c:if>
        function selectEmployee()
        {
            if(!checkOneSelection("selectedIds", 'Пожалуйста, выберите одну запись'))
                return;

            var result = new Array();
            var selectRow = $('[name=selectedIds]:checked').parents('[class^=ListLine]');
            result['loginId'] = selectRow.find('#loginId').val();
            result['FIO'] = selectRow.find('#fullName').text();

            window.opener.setEmployeeInfo(result);
            window.close();
        }
	</script>

	<tiles:insert definition="tableTemplate" flush="false">
		<tiles:put name="id" value="EmplyeesList"/>
		<tiles:put name="grid">
			<sl:collection id="item" bundle="contactCenterAreaBundle" property="data" model="list" selectType="checkbox" selectName="selectedIds" selectBean="employee" selectProperty="id">
				<c:set var="employee"       value="${item[1]}" />
				<c:set var="department"     value="${item[2]}" />
                <c:set var="areaName"     value="${item[0]}" />
				<c:set var="login"        value="${employee.login}"/>

				<sl:collectionItem title="label.listId" name="employee" property="id"/>
				<sl:collectionItem title="label.FIO" >
                    <span id="fullName">${employee.surName} ${employee.firstName} ${employee.patrName}</span>
                    <input type="hidden" id="loginId" value="${login.id}"/>
                </sl:collectionItem>
                <sl:collectionItem title="label.department"><c:out value="${department.fullName}"/></sl:collectionItem>
                <sl:collectionItem title="label.area"><c:out value="${areaName}"/></sl:collectionItem>
			</sl:collection>
		</tiles:put>


		<tiles:put name="isEmpty" value="${empty form.data}"/>
		<tiles:put name="emptyMessage">
            <c:choose>
                <c:when test="${form.fromStart}">
                    Для поиска сотрудников в системе предусмотрен фильтр. Чтобы им воспользоваться, в полях фильтра задайте критерии поиска и нажмите на кнопку «Применить».
                </c:when>
                <c:otherwise>
                    Не найдено ни одного сотрудника, <br/>соответствующего заданному фильтру!
                </c:otherwise>
            </c:choose>
        </tiles:put>
        <tiles:put name="buttons">
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.assign"/>
                <tiles:put name="commandHelpKey" value="button.assign.help"/>
                <tiles:put name="image" value=""/>
                <tiles:put name="bundle" value="mailBundle"/>
                <tiles:put name="onclick" value="selectEmployee();"/>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
  </tiles:put>
</tiles:insert>
</html:form>
