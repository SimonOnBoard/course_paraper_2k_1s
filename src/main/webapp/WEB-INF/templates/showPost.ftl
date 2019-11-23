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
                    form.setAttribute('action','/changeComment');
                    form.setAttribute('method','get');
                    var p = document.createElement('p');
                    var input = document.createElement('input');
                    input.setAttribute('type','hidden');
                    input.setAttribute('name','comment_id');
                    input.setAttribute('id','comment_id');
                    input.setAttribute('value',k);
                    p.appendChild(input);
                    form.appendChild(p);
                    var p = document.createElement('p');
                    var input = document.createElement('input');
                    input.setAttribute('type','submit');
                    input.setAttribute('value','Редактировать');
                    p.appendChild(input);
                    form.appendChild(p);
                    $("#comment_" + k).append(form);
                },
                error: function (err) {
                    alert("error msg")
                }
            });
        }
    </script>
    <img src="${post.getPhotoPath()}" width="200"/>
    <p>${post.getName()}</p>
    <p>${post.getText()}</p>
    <p>${post.getPublication().toString()}</p>
    <div id="comments">
        <#if comments?has_content>
        <#list comments as comment>
            <div id="comment_${comment.getKey().getId()}">
                <#if comment.getValue().getPhotoPath()??>
                    <img src="${comment.getValue().getPhotoPath()}" width="30"/>
                <#else>
                    No photo((
                </#if>

                <a href="/home?id=${comment.getKey().getOwnerId()}">
                    ${comment.getValue().getNick()}
                </a>
                <p>
                    ${comment.getKey().getText()}
                </p>
                <p>On data : ${comment.getKey().getDate().toString()}</p>
                <#if comment.getKey().getOwnerId() == userId>
                    <form action="/changeComment" method="get">
                        <p><input type="hidden" name="comment_id" id="comment_id" value="${comment.getKey().getId()}"/></p>
                        <p><input type="submit" value="Редактировать"/></p>
                    </form>
                    <#else>
                </#if>
            </div>
        </#list>
            <#else>
            <p>No comments here</p>
        </#if>
    </div>
    <form action="/comment">
        <p><input type="hidden" name="post_id" id="post_id" value="${post.getId()}"/></p>
        <p><textarea rows="10" cols="45" name="text" id="text"></textarea></p>
        <p><input type="button" value="Отправить" onclick="f()"/></p>
    </form>
</#macro>
<#macro title>
    <title>Post</title>
</#macro>
<@display_page />