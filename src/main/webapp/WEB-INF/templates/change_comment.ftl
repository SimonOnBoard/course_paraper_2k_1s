<#ftl encoding='UTF-8'>
<#include "base.ftl"/>

<#macro content>
    <form action="/changeComment" method="post">
        <input type="hidden" name="comment_id" id="comment_id" value="${comment.getId()}"/>
        <input type="hidden" name="path" id="path" value="${path}"/>
        <p><textarea rows="10" cols="45" name="text" id="text">${comment.getText()}</textarea></p>
        <p><input type="submit" value="Сохранить"/></p>
    </form>
</#macro>
<#macro title>
    <title>CommentChange</title>
</#macro>
<@display_page />