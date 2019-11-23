<#ftl encoding='UTF-8'>
<#include "base.ftl"/>

<#macro content>
    <div id="comments">
        <#if comments?has_content>
            <#list comments as comment>
                <div id="comment_${comment.getId()}">
                    <p>
                        ${comment.getText()}
                    </p>
                    <p>On data : ${comment.getDate().toString()}</p>
                    <a href="/showPost?id=${comment.getPostId()}">
                        Просмотреть запись
                    </a>
                </div>
                <form action="/changeComment" method="get">
                    <p><input type="hidden" name="comment_id" id="comment_id" value="${comment.getId()}"/></p>
                    <p><input type="submit" value="Редактировать"/></p>
                </form>
            </#list>
        <#else>
            <p>No comments here</p>
        </#if>
    </div>
</#macro>
<#macro title>
    <title>Comments</title>
</#macro>
<@display_page />