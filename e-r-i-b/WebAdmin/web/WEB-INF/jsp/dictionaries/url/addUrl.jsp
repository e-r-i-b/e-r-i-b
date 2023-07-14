<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<tiles:importAttribute/>

<html:form action="/whitelist/add" onsubmit="return setEmptyAction(event);">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="dictionary">
        <tiles:put name="data" type="string">
            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="name" ><bean:message bundle="whiteListUrlBundle" key="label.edit.title"/></tiles:put>
                <tiles:put name="description" ><bean:message bundle="whiteListUrlBundle" key="label.edit.description"/></tiles:put>
                <tiles:put name="data">
                    <script type="text/javascript">
                        doOnLoad(function() {
                            init();
                        });
                        var  currMask = null;
                        function init()
                        {
                             currMask = window.opener.getCurrMask();
                             if(currMask == null) return;
                             $('#urlMask').val(currMask.mask);
                             $('#urlMaskId').val(currMask.id);
                        }
                        function mask(id, mask)
                        {
                            this.id = id;
                            this.mask = mask;
                        }
                        function sendMask()
                        {
                            var urlMask = trim($('#urlMask').val());
                            if (urlMask == '')
                            {
                                alert('<bean:message bundle="whiteListUrlBundle" key="label.edit.empty.mask"/>');
                                return;
                            }

                            if(!/^[-\dA-Za-zÀ-ÿà-ÿ:_\./]{1,256}$/.test(urlMask))
                            {
                                alert('<bean:message bundle="whiteListUrlBundle" key="label.edit.incorrect.mask"/>');
                                return;
                            }
                            var res = window.opener.addOrUpdateMask(new mask(currMask.id, urlMask), ${param['update']});
                            if (res)
                                window.close();
                            else
                                alert('<bean:message bundle="whiteListUrlBundle" key="label.edit.mask.error"/>');

                        }

                        function cancel()
                        {
                            window.close();
                        }
                    </script>

                    <input type="hidden" id="conditionId"/>
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="whiteListUrlBundle" key="label.edit.field.name"/>
                        </tiles:put>
                        <tiles:put name="description">
                            <bean:message bundle="whiteListUrlBundle" key="label.edit.field.hint"/>
                        </tiles:put>
                        <tiles:put name="data">
                            <input type="text" id="urlMask" size="20" maxlength="100"/>
                            <input type="hidden" id="urlMaskId"/>
                        </tiles:put>
                    </tiles:insert>

                </tiles:put>

                <tiles:put name="buttons">
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.save"/>
                        <tiles:put name="commandHelpKey" value="button.save"/>
                        <tiles:put name="bundle" value="whiteListUrlBundle"/>
                        <tiles:put name="isDefault" value="true"/>
                        <tiles:put name="onclick">sendMask();</tiles:put>
                    </tiles:insert>
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.cancel"/>
                        <tiles:put name="commandHelpKey" value="button.cancel"/>
                        <tiles:put name="bundle" value="whiteListUrlBundle"/>
                        <tiles:put name="onclick">cancel();</tiles:put>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>