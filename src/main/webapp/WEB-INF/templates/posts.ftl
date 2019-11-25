<#ftl encoding='UTF-8'>
<#include "base.ftl"/>
<#macro content>


    <div class="feed-container">
        <div class="feed">
            <h1 style="margin-bottom:2rem;">Your posts</h1>

            <#if posts?has_content>
                <#list posts as post>
                    <div class="row">
                        <div class="col-sm-6" id="post_${post.getId()}">
                            <div class="post" style="width:95%; margin-right:5%">
                                <p>
                                    <img src="${post.getPhotoPath()}" height="100"> &nbsp; <span
                                            class="big">${post.getName()}</span>
                                <form action="/changePost/" method="get" style="display:inline-block">
                                    <input type="hidden" name="post_id" id="post_id" value="${post.getId()}"/>
                                    <button type="submit" class="btn btn-link"><i class="fa fa-pencil-square-o" aria-hidden="true"></i></button>
                                </form>
                                <form action="/posts" method="post" style="display:inline-block">
                                    <input type="hidden" name="post_id" id="post_id" value="${post.getId()}"/>
                                    <button type="submit" class="btn btn-link"><i class="fa fa-trash" aria-hidden="true"></i></button>
                                </form>
                                </p>

                                <p class="post-text">${post.getText()}</p>
                                <p>${post.getCategory().toString()}</p>
                                <p>${post.getPublication().toString()}</p>
                                <p style="padding-top:2rem;"><
                                    <a href="/showPost?id=${post.getId()}">
                                        Просмотреть запись</a>
                                <p>
                                    <
                            </div>
                        </div>
                    </div>

                </#list>
            <#else>
                <p>No posts made, dude. <a href="/newPost">Go make one</a></p>
            </#if>

        </div>
    </div>


</#macro>
<#macro title>
    <title>Posts</title>
</#macro>
<@display_page />