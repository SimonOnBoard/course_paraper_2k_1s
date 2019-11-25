<#ftl encoding='UTF-8'>
<#include "base.ftl"/>
<#macro content>
    <div class="feed-container">
        <div class="feed">
            <h1 style="margin-bottom:2rem;">Your comments</h1>
            <#if comments?has_content>
                <#list comments as comment>
                    <div class="row">
                        <div class="col-sm-6" id="comment_${comment.getId()}">
                            <div class="post" style="width:95%; margin-right:5%">

                                <p class="post-text">${comment.getText()}</p>
                                <p class="post-text">On date: ${comment.getDate().toString()}</p>
                                <p class="post-text"><a href="/showPost?id=${comment.getPostId()}">
                                        Просмотреть запись
                                    </a>

                                    <form action="/changeComment" method="get" style="display:inline-block">
                                <p><input type="hidden" name="comment_id" id="comment_id" value="${comment.getId()}"/></p>
                                <button type="submit" class="btn btn-link"><i class="fa fa-pencil-square-o" aria-hidden="true"></i></button>
                                </form>

                                </p>
                            </div>
                        </div>
                    </div>

                </#list>
            <#else>
                <p>No comments here</p>
            </#if>

        </div>
    </div>


</#macro>
<#macro title>
    <title>Comments</title>
</#macro>
<@display_page />