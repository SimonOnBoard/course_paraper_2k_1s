<#ftl encoding='UTF-8'>
<#include "base.ftl"/>

<#macro content>
    <script type="text/javascript">
        function f() {

            var formData = {
                'post_id': $('#post_id').val(),
                'text': $('#text').val()
            };
            console.log(formData);
            $.ajax({
                url: "/comment",
                type: "post",
                data: formData,
                success: function (data) {
                    var JSONObject = JSON.parse(data);
                    var div_r = document.createElement("div");
                    div_r.setAttribute("id", "comment_" + JSONObject.key.id);
                    div_r.setAttribute("class", "comment");
                    var k = JSONObject.key.id;

                    var form = document.createElement('form');
                    form.setAttribute('action', '/changeComment');
                    form.setAttribute('method', 'get');
                    form.setAttribute('style', 'display: inline-block;');
                    var b = document.createElement('b');
                    var input = document.createElement('input');
                    input.setAttribute('type', 'hidden');
                    input.setAttribute('name', 'comment_id');
                    input.setAttribute('id', 'comment_id');
                    input.setAttribute('value', k);
                    b.appendChild(input);
                    form.appendChild(b);
                    var b = document.createElement('b');
                    var button = document.createElement('button');
                    button.setAttribute('type', 'submit');
                    button.setAttribute('class', 'btn btn-link');
                    button.setAttribute('value', 'Редактировать');
                    var icon = document.createElement('i');
                    icon.setAttribute("class", "fa fa-pencil-square-o");
                    icon.setAttribute("aria-hidden", "true");
                    button.appendChild(icon);
                    b.appendChild(button);
                    form.appendChild(b);
                    $("#comment_" + k).append(form);
                    $("#formComment").find("textarea").val('');

                    $("#comments").append(div_r);
                    $("#comment_" + k).append("<div class=\"row\"> <div class=\"col-sm-3\"><img src=\""
                        + JSONObject.value.photoPath
                        + "\"></div><div class=\"col-sm-9\" style=\"margin:auto\"><div><a href=\"/home?id=\""
                        +JSONObject.key.ownerId+"\">" +
                        JSONObject.value.nick + "</a>");
                        if(JSONObject.key.ownerId === ${userId}){
                            $("#comment_" + k).append(form);
                        }

                        $("#comment_" + k).append("<br><span class=\"text-small\">Опубликовано:"
                        + JSONObject.key.time
                        + "</span></div></div></div><div class=\"comment-data\">" + JSONObject.key.text + "</div></div>");

                },
                error: function (err) {
                    alert("error msg")
                }

        });
        }
    </script>
    <div class="my-container" style="min-height:100%; height:auto;">
        <div class="post-container">
            <h2>${post.getName()} <#if userId == post.getAuth_id()>
                    <form action="/changePost" method="get" style="display: inline-block;">
                        <p><input type="hidden" name="post_id" id="post_id" value="${post.getId()}"/></p>
                        <button type="submit" class="btn btn-link"><i class="fa fa-pencil-square-o" aria-hidden="true"></i></button>
                    </form>
                    <form action="/posts" method="post" style="display: inline-block;">
                        <p><input type="hidden" name="post_id" id="post_id" value="${post.getId()}"/></p>
                        <button type="submit" class="btn btn-link"><i class="fa fa-trash-o" aria-hidden="true"></i></button>
                    </form>
                <#else>
                </#if></h2>
            <p><img src="${post.getPhotoPath()}" width="60%">
                <#if post.getShowAuthor()>
            <p><strong>Author: </strong><a href="/home?id=${post.getAuth_id()}">
                    профиль</a></p>
            <#else>
            </#if>
            <p>Создано: ${post.getPublication().toString()}</p>

            <p>${post.getText()}</p>


            <div id="comments" class="comments">
                <h1 style="padding-top:1rem;">Comments</h1>


                <form name="leaveComment" action="/comment" id="formComment">
                    <input type="hidden" name="post_id" id="post_id" value="${post.getId()}"/>
                    <textarea class="comment-area" id="text" name="text" rows="3"></textarea>
                    <button type="button" class="btn btn-danger" onclick="f()">Submit</button>
                </form>



                <#if comments?has_content>
                    <#list comments as comment>
                    <div class="comment" id="comment_${comment.getKey().getId()}">
                        <div class="row">
                            <div class="col-sm-3">
                                <img src="${comment.getValue().getPhotoPath()}">
                            </div>
                            <div class="col-sm-9" style="margin:auto">
                                <div><a href="/home?id=${comment.getKey().getOwnerId()}">
                                        ${comment.getValue().getNick()}
                                    </a>
                                    <#if comment.getKey().getOwnerId() == userId>
                                    <form action="/changeComment" method="get" style="display: inline-block">
                                        <p><input type="hidden" name="comment_id" id="comment_id"
                                                  value="${comment.getKey().getId()}"/></p>
                                        <button type="submit" class="btn btn-link"><i class="fa fa-pencil-square-o" aria-hidden="true"></i></button>
                                    </form>
                                    <#else>
                                    </#if><br><span
                                            class="text-small">Опубликовано: ${comment.getKey().getDate().toString()}</span>
                                </div>
                            </div>
                        </div>
                        <div class="comment-data">
                            ${comment.getKey().getText()}
                        </div>
                    </div>
                    </#list>
                <#else>
                    <p>No comments</p>
                </#if>

            </div>
        </div>

    </div>


</#macro>
<#macro title>
    <title>Post</title>
</#macro>
<@display_page />