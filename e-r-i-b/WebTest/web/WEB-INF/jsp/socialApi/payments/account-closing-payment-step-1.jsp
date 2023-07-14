<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:html>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/jquery-1.4.2.js"></script>
    <head>
        <title>Закрытие вкладов</title>
    </head>

    <script type="text/javascript">

    </script>

    <body>
    <h1>Закрытие вкладов</h1>

    <html:form action="/mobileApi/accountClosingPayment" show="true">
        <c:set var="form"        value="${phiz:currentForm(pageContext)}"/>
        <c:set var="initialData" value="${form.response.initialData.accountClosingPayment}"/>
        <c:set var="targets"     value="${initialData.targetMap}"/>
        <c:set var="standartCourse" value="${initialData.standartCourse}"/>

        <c:if test="${not empty targets}">
            <script type="text/javascript">
                $(document).ready(function()
                {
                    var fromResource = document.getElementsByName('fromResource')[0];
                    fromResource.setAttribute('onchange', 'onChangeFromResource(this);');
                    onChangeFromResource(fromResource);
                });

                var targets =
                {
                    targets : [],

                    add : function(key, name, comment, date, amount)
                    {
                        var target     = {};
                        target.account = key;
                        target.name    = name;
                        target.date    = date;
                        target.comment = comment;
                        target.amount  = amount;

                        this.targets.push(target);
                    },

                    findByKey : function(key)
                    {
                        if (key == null)
                        {
                            return null;
                        }

                        for (var i=0; i<this.targets.length; i++)
                        {
                            if (this.targets[i].account == key)
                            {
                                return this.targets[i];
                            }
                        }

                        return null;
                    }
                }

                function onChangeFromResource(element)
                {
                    if (element.selectedIndex == -1)
                    {
                        return false;
                    }

                    var options = element.options;
                    var target  = targets.findByKey(options[element.selectedIndex].value);

                    var name    = document.getElementById('name');
                    var comment = document.getElementById('comment');
                    var date    = document.getElementById('date');
                    var amount  = document.getElementById('amount');

                    if (target == null)
                    {
                        name.value    = null;
                        date.value    = null;
                        amount.value  = null;
                        comment.value = null;

                        return false;
                    }

                    name.value    = target.name;
                    date.value    = target.date;
                    amount.value  = target.amount;
                    comment.value = target.comment;
                }

                <c:if test="${not empty targets}">
                    <c:forEach var="target" items="${targets.target}">
                        targets.add('${target.fromResource}',
                                    '${target.name.stringType.value}',
                                    '${target.comment.stringType.value}',
                                    '${target.date.stringType.value}',
                                    '${target.amount.stringType.value}');
                    </c:forEach>
                </c:if>
            </script>
        </c:if>

        <tiles:insert definition="mobile" flush="false">
            <tiles:put name="address" value="/private/payments/payment.do"/>
            <tiles:put name="formName" value="AccountClosingPayment"/>
            <tiles:put name="operation" value="save"/>

            <tiles:put name="data">
                <tiles:insert page="fields-table.jsp" flush="false">
                    <tiles:put name="data">
                        <tiles:insert page="field.jsp" flush="false">
                            <tiles:put name="field" beanName="initialData" beanProperty="closingDate"/>
                        </tiles:insert>
                         <tiles:insert page="field.jsp" flush="false">
                            <tiles:put name="field" beanName="initialData" beanProperty="documentNumber"/>
                        </tiles:insert>
                        <tiles:insert page="field.jsp" flush="false">
                            <tiles:put name="field" beanName="initialData" beanProperty="documentDate"/>
                        </tiles:insert>
                        <tiles:insert page="field.jsp" flush="false">
                            <tiles:put name="field" beanName="initialData" beanProperty="fromResource"/>
                        </tiles:insert>
                        <tiles:insert page="field.jsp" flush="false">
                            <tiles:put name="field" beanName="initialData" beanProperty="toResource"/>
                        </tiles:insert>
                         <tiles:insert page="field.jsp" flush="false">
                            <tiles:put name="field" beanName="initialData" beanProperty="chargeOffAmount"/>
                        </tiles:insert>
                        <tiles:insert page="field.jsp" flush="false">
                            <tiles:put name="field" beanName="initialData" beanProperty="course"/>
                        </tiles:insert>
                        <c:if test="${not empty standartCourse}">
                            <tiles:insert page="field.jsp" flush="false">
                                <tiles:put name="field" beanName="initialData" beanProperty="standartCourse"/>
                            </tiles:insert>
                            <tiles:insert page="field.jsp" flush="false">
                                <tiles:put name="field" beanName="initialData" beanProperty="gain"/>
                            </tiles:insert>
                        </c:if>
                        <tr><td>operationCode</td><td><html:text styleId="operationCode" property="operationCode"/></td></tr>

                        <c:if test="${not empty targets}">
                            <tr><td colspan="13"><b>Поля цели</b></td></tr>

                            <tr>
                                <td>
                                    <label for="name">name</label>
                                </td>
                                <td>
                                    <input type="text" id="name" readonly="readonly"/>
                                </td>
                            </tr>

                            <tr>
                                <td>
                                    <label for="comment">comment</label>
                                </td>
                                <td>
                                    <input type="text" id="comment" readonly="readonly"/>
                                </td>
                            </tr>

                            <tr>
                                <td>
                                    <label for="date">date</label>
                                </td>
                                <td>
                                    <input type="text" id="date" readonly="readonly"/>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <label for="amount">amount</label>
                                </td>
                                <td>
                                    <input type="text" id="amount" readonly="readonly"/>
                                </td>
                            </tr>
                        </c:if>
                    </tiles:put>
                </tiles:insert>
            </tiles:put>
        </tiles:insert>
    </html:form>

    </body>
</html:html>
