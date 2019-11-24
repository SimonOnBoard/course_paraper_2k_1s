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
                    var k = JSONObject.key.id;
                    $("#comments").append(div_r);
                    $("#comment_" + k).append("<img width=" + "\"30\"" +
                        " src=" + "\"" + JSONObject.value.photoPath + "\"" + " />");
                    $("#comment_" + k).append("<a href=" + "\"/home?id=" + JSONObject.key.ownerId + "\" " + ">" +
                        JSONObject.value.nick + "</a>");
                    $("#comment_" + k).append("<p>" + JSONObject.key.text + "</p>");
                    $("#comment_" + k).append("<p>" + "On data :" + JSONObject.key.time + "</p>");
                    var form = document.createElement('form');
                    form.setAttribute('action', '/changeComment');
                    form.setAttribute('method', 'get');
                    var p = document.createElement('p');
                    var input = document.createElement('input');
                    input.setAttribute('type', 'hidden');
                    input.setAttribute('name', 'comment_id');
                    input.setAttribute('id', 'comment_id');
                    input.setAttribute('value', k);
                    p.appendChild(input);
                    form.appendChild(p);
                    var p = document.createElement('p');
                    var input = document.createElement('input');
                    input.setAttribute('type', 'submit');
                    input.setAttribute('value', 'Редактировать');
                    p.appendChild(input);
                    form.appendChild(p);
                    $("#comment_" + k).append(form);
                    $("#formComment").find("textarea").val('');
                },
                error: function (err) {
                    alert("error msg")
                }
            });
        }
    </script>
    <div class="my-container" style="min-height:100%; height:auto;">
        <div class="post-container">
            <h2>${post.getName()}</h2>
            <p><img src="${post.getPhotoPath()}" width="60%">
                <#if post.getShowAuthor()>
            <p><strong>Author: </strong><a href="/home?id=${post.getAuth_id()}">
                    Просмотреть профиль автора</a></p>
            <#else>
            </#if>
            <p>${post.getPublication().toString()}</p>

            <p>${post.getText()}</p>
            <#if userId == post.getAuth_id()>
                <form action="/changePost/" method="get">
                    <p><input type="hidden" name="post_id" id="post_id" value="${post.getId()}"/></p>
                    <button type="submit" class="btn btn-danger">Edit</button>
                </form>
                <form action="/posts" method="post">
                    <p><input type="hidden" name="post_id" id="post_id" value="${post.getId()}"/></p>
                    <button type="submit" class="btn btn-danger">Delete</button>
                </form>
                <#else>
            </#if>

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
                                    </a> <br><span
                                            class="text-small">Опубликовано: ${comment.getKey().getDate().toString()}</span>
                                </div>
                            </div>
                        </div>
                        <div class="comment-data">
                            ${comment.getKey().getText()}
                            <#if comment.getKey().getOwnerId() == userId>
                                <form action="/changeComment" method="get">
                                    <p><input type="hidden" name="comment_id" id="comment_id"
                                              value="${comment.getKey().getId()}"/></p>
                                    <button type="submit" class="btn btn-danger">Edit</button>
                                </form>
                            <#else>
                            </#if>
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