<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/persons/segmentCodeType/list">
    <tiles:insert definition="dictionary">
        <tiles:put name="pageTitle" type="string"><bean:message bundle="personsBundle" key="form.segment.list.pageTitle"/></tiles:put>
        <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

        <tiles:put name="data" type="string">
            <tiles:insert definition="tableTemplate" flush="false">
                <tiles:put name="id" value="segmentCodeTypeList"/>
                <tiles:put name="data">
                    <tr>
                        <th style="width:20px">
                            <input type="checkbox" name="isSelectAll" onclick="switchSelection('isSelectAll','segments');">
                        </th>
                        <th>
                            <bean:message bundle="personsBundle" key="form.segment.list.name"/>
                        </th>
                    </tr>
                    <logic:iterate id="segment" name="ListSegmentCodeTypesForm" property="data">
                        <tr>
                            <td valign="top">
                                <input type="checkbox" name="segments" value="${segment}">
                            </td>
                            <td valign="top">
                                <bean:message bundle="personsBundle" key="segment.${segment}"/>
                            </td>
                        </tr>
                    </logic:iterate>


                    <tiles:put name="buttons">
                        <tiles:insert definition="clientButton" flush="false">
                            <tiles:put name="commandTextKey"    value="button.cancel"/>
                            <tiles:put name="commandHelpKey"    value="button.cancel"/>
                            <tiles:put name="bundle"            value="commonBundle"/>
                            <tiles:put name="onclick"           value="window.close();"/>
                        </tiles:insert>
                        <script type="text/javascript">
                            function selectSegment()
                            {
                                checkIfOneItem("segments");
                                if (!checkSelection("segments", '<bean:message bundle="personsBundle" key="form.segment.list.required"/>'))
                                    return false;

                                var ids = document.getElementsByName("segments");
                                var result = new Array();
                                var resultCount = 0;
                                for (var i = 0; i < ids.length; i++)
                                {
                                    if (ids.item(i).checked)
                                    {
                                        var r = ids.item(i).parentNode.parentNode;
                                        var a = new Array(2);
                                        a['code'] = trim(ids.item(i).value);
                                        a['name'] = trim(getElementTextContent(r.cells[1]));
                                        result[resultCount++] = a;
                                    }
                                }
                                result['size'] = resultCount;
                                if (resultCount == 0)
                                {
                                    alert('<bean:message bundle="personsBundle" key="form.segment.list.required"/>');
                                    return false;
                                }

                                var message = window.opener.setSegmentCodeTypeInfo(result);
                                if (isNotEmpty(message))
                                {
                                    alert(message);
                                    return false;
                                }
                                window.close();
                                return true;
                            }
                        </script>
                        <tiles:insert definition="clientButton" flush="false">
                            <tiles:put name="commandTextKey"    value="account.button.select"/>
                            <tiles:put name="commandHelpKey"    value="account.button.select.help"/>
                            <tiles:put name="bundle"            value="pfpProductBundle"/>
                            <tiles:put name="onclick"           value="selectSegment();"/>
                        </tiles:insert>
                    </tiles:put>
                    <tiles:put name="isEmpty" value="${empty form.data}"/>
                    <tiles:put name="emptyMessage"><bean:message bundle="personsBundle" key="form.segment.list.empty"/></tiles:put>
                </tiles:put>
            </tiles:insert>
        </tiles:put>

    </tiles:insert>
</html:form>
