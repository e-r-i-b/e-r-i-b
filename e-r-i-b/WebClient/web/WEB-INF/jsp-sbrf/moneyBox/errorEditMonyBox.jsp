<%--
  User: usachev
  Date: 21.03.15
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<script type="text/javascript">
    removeAllMessages('warningsViewMoneyBox');
    removeAllErrors('errorsViewMoneyBox');
    win.close(moneyBoxWinId);
    <phiz:messages id="errorMessage" field="stub" message="sessionError" showSessionMessages="true">
        addMessage('${errorMessage}', 'errorsViewMoneyBox', true);
    </phiz:messages>
    win.open(moneyBoxViewWinId);
</script>