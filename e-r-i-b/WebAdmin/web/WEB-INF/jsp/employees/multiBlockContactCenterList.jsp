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


        <!-- ������  -->
        <tiles:put name="filter" type="string">
            <tiles:insert definition="filterTextField" flush="false">
                <tiles:put name="label" value="login.employee"/>
                <tiles:put name="bundle" value="employeesBundle"/>
                <tiles:put name="mandatory" value="false"/>
                <tiles:put name="name" value="fio"/>
                <tiles:put name="size"   value="50"/>
                <tiles:put name="maxlength"  value="255"/>
                <tiles:put name="isDefault" value="������� ��� ��������"/>
            </tiles:insert>
            <tiles:insert definition="filterEntryField" flush="false">
                <tiles:put name="label" value="label.area"/>
                <tiles:put name="bundle" value="contactCenterAreaBundle"/>
                <tiles:put name="data">
                    <html:select property="filters(area_id)" styleClass="select">
                        <html:option value="">���</html:option>
                        <c:forEach var="area" items="${form.contactCenterAreas}">
                            <html:option value="${area.uuid}">
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
                //���������� ������ ��� ������
                switchFilter(this);
                </c:if>
                function selectEmployee()
                {
                    if(!checkOneSelection("selectedIds", '����������, �������� ���� ������'))
                        return;

                    var result = new Array();
                    var selectRow = $('[name=selectedIds]:checked').parents('tr:first');
                    var data = selectRow.find('.listItem');
                    result['loginId'] = $(data[1]).text();
                    result['FIO'] = $(data[3]).text();

                    window.opener.setEmployeeInfo(result);
                    window.close();
                }
            </script>

            <tiles:insert definition="tableTemplate" flush="false">
                <tiles:put name="id" value="EmplyeesList"/>
                <tiles:put name="text" value="������ �����������"/>
                <tiles:put name="grid">
                    <sl:collection id="employee" bundle="contactCenterAreaBundle" property="data" model="list" selectType="checkbox" selectName="selectedIds" selectBean="employee" selectProperty="id">
                        <sl:collectionItem title="label.listId"     name="employee" property="externalId" hidden="true"/>
                        <sl:collectionItem title="label.listId"     name="employee" property="id"/>
                        <sl:collectionItem title="label.FIO"        name="employee" property="name"/>
                        <sl:collectionItem title="label.department" name="employee" property="department"/>
                        <sl:collectionItem title="label.area"       name="employee" property="area"/>
                    </sl:collection>
                </tiles:put>


                <tiles:put name="isEmpty" value="${empty form.data}"/>
                <tiles:put name="emptyMessage">
                    <c:choose>
                        <c:when test="${form.fromStart}">
                            ��� ������ ����������� � ������� ������������ ������. ����� �� ���������������, � ����� ������� ������� �������� ������ � ������� �� ������ �����������.
                        </c:when>
                        <c:otherwise>
                            �� ������� �� ������ ����������, <br/>���������������� ��������� �������!
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
