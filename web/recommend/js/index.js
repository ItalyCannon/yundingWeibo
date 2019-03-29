function load_home1() {
    window.location.href = "/home"
}

function load_home2() {
    window.location.href = "/collection";
}

function load_home3() {
    window.location.href = "/praise";
}

function load_home4() {
    window.location.href = "/commentReceive";
}

function show_list() {
    window.location.href = "/list";
}

function loadDetail() {
    window.location.href = "/detail"
}

function load_attention() {
    window.location.href = "/attention"
}

function load_fans() {
    window.location.href = "/fans"
}

function load_space() {
    window.location.href = "/space"
}


var img = document.querySelector(".banner_img");
var right = document.querySelector(".arrow_right");
var left = document.querySelector(".arrow_left");
right.onclick = function () {
    right_img();
}
left.onclick = function () {
    left_img();
}

function right_img() {
    index++;
    if (index > 5) {
        index = 1;
    }
    showplay();
    var newleft;
    if (img.style.left == "-3740px") {
        newleft = -748;
    } else {
        newleft = parseInt(img.style.left) - 748;
    }
    img.style.left = newleft + "px";
}

function left_img() {
    index--;
    if (index < 0) {
        index = 4;
    }
    showplay();
    var newleft;
    if (img.style.left == "0px") {
        newleft = -2992;
    } else {
        newleft = parseInt(img.style.left) + 748;
    }
    img.style.left = newleft + "px";
}

//点击切换效果

// setInterval("right_img()",1000);
var i = null;

function play() {
    i = setInterval(function () {
        right_img();
    }, 3000);
}

play();

var banner = document.querySelector(".banner");
banner.onmouseover = function () {
    clearInterval(i);
}
banner.onmouseleave = function () {
    play();
}

//自动轮播效果

function click1() {
    var newleft2 = parseInt(img.style.left);
    newleft2 = -748;
    img.style.left = newleft2 + "px";
    index = 1;
    showplay();
}

function click2() {
    var newleft2 = parseInt(img.style.left);
    newleft2 = -1496;
    img.style.left = newleft2 + "px";
    index = 2;
    showplay();
}

function click3() {
    var newleft2 = parseInt(img.style.left);
    newleft2 = -2244;
    img.style.left = newleft2 + "px";
    index = 3;
    showplay();
}

function click4() {
    var newleft2 = parseInt(img.style.left);
    newleft2 = -2992;
    img.style.left = newleft2 + "px";
    index = 4;
    showplay();
}

function click5() {
    var newleft2 = parseInt(img.style.left);
    newleft2 = -3740;
    img.style.left = newleft2 + "px";
    index = 5;
    showplay();
}

//点击圆点切换效果

var index = 1;
var dots = document.getElementsByTagName("input");

function showplay() {
    for (var s = 0, len = dots.length; s < len; s++) {
        dots[s].className = "";
    }
    dots[index].className = "on";
}

//圆点跟随循环效果


var profile = '';
var userInfo;
var allAttention;
var allCollection;
var allPraise;

var PageCode = 1;
var totalPage = 1;

function showWeibo() {
    if (PageCode > totalPage) {
        return;
    }
    var url = '/ShowAllWeiboServlet?pc=' + PageCode;
    $.ajax({
        url: url,
        type: 'get',
        dataType: 'json',
        data: {},
        success: function (text) {
            flushAllCollections();
            var data = eval(text);
            //获得总页数
            totalPage = getTotalPage(data.totalRecord, data.pageSize);

            var html = noApplicationRecord.innerHTML;
            var messages = [];
            var ids = [];
            var replys = [];
            var messageIds = [];
            console.log(data);
            for (var i = 0; i < data.beanList.length; i++) {
                html +=
                    '<div class="part_1"' + ' id="' + data.beanList[i].weiboId + 'main' + '"' + '>' +
                    '<img src="' + data.beanList[i].profilePicture + '" alt="" class="portrait">' +
                    '<div class="name">' +
                    '<p class="nickname">' + data.beanList[i].nickname + '</p>' +
                    '<p class="date">' + new Date(data.beanList[i].createTime).toLocaleString() + '</p>' +
                    '</div>' +
                    '<p class="text">' + data.beanList[i].weiboContent + '<span>' + '' + '</span></p>' +
                    '<div id="imgs" class="imgs"' + imgHeight(data.beanList[i].photo) + '>' +
                    picture(data.beanList[i].photo) +
                    '</div>' +
                    '<div class="bottom">' +
                    '<div class="bottom_part bottom1" onclick="collection(' + data.beanList[i].weiboId + ')" style="cursor: pointer;">' +
                    '<i class="iconfont">&#xe665;</i>' +
                    '<p class="bottom1_text" id="' + data.beanList[i].weiboId + 'c' + '">' + collectionCondition(data.beanList[i].weiboId) + '</p>' +
                    '</div>' +
                    '<div class="bottom_part bottom2" onclick="repost(' + data.beanList[i].weiboId + ')" style="cursor: pointer;">' +
                    '<i class="iconfont">&#xe64d;</i>' +
                    '<p class="bottom2_text">转发</p>' +
                    '</div>' +
                    '<div class="bottom_part bottom3" id="' + data.beanList[i].weiboId + 's"' + ' onclick="showComment('
                    + data.beanList[i].weiboId + ')"' + ' style="cursor: pointer;">' +
                    '<i class="iconfont">&#xe643;</i>' +
                    '<p class="bottom3_text">' + data.beanList[i].commentNum + '</p>' +
                    '</div>' +
                    '<div class="bottom_part bottom4" onclick="praise(' + data.beanList[i].weiboId + ')" style="cursor: pointer;">' +
                    '<i class="iconfont" id="' + data.beanList[i].weiboId + 'aaaa' + '">&#xe60c;</i>' +
                    '<p class="bottom4_text" id="' + data.beanList[i].weiboId + 'p' + '">' + data.beanList[i].praiseNum + '</p>' +
                    '</div>' +
                    '</div>' + '<div id="' + data.beanList[i].weiboId + 'cc' + '" style="display: none;">0</div>'
                    + '<div id="' + data.beanList[i].weiboId + 'ff' + '"  style="display:none;" class="comment"></div>'
                    + '</div>';

                //先把一条微博的轮廓搭起来，然后再往里加评论的部分
                noApplicationRecord.innerHTML = html;
                var message = '';
                var reply = '';
                //评论框
                message += '<div class="write">' +
                    '<img src="' + profile + '" alt="img" class="head_img">' +
                    '<textarea name="" class="text" id="' + data.beanList[i].weiboId + 'addcomment' + '">' + '' + '</textarea>' +
                    '<div class="option">' +
                    '<i class="iconfont expression">&#xe614;</i>' +
                    '<i class="iconfont image">&#xe72f;</i>' +
                    '<div class="check"></div>' +
                    '<p class="word">同时转发到我的微博</p>' +
                    '<input type="submit" name="" value="发布" class="submit" onclick="addComment(' + data.beanList[i].weiboId + ')" style="cursor: pointer">' +
                    '</div>' +
                    '</div>';
                //评论主体
                for (var j = 0; j < data.beanList[i].comments.length; ++j) {
                    message += '<div class="option_1" id="' + data.beanList[i].comments[j].commentId + 'comment">' +
                        '<img src="' + data.beanList[i].comments[j].profilePicture + '" alt="img" class="head_img">' +
                        '<div class="message">' +
                        '<p class="nickname"><span>' + data.beanList[i].comments[j].nickname + '：' + '</span>'
                        + data.beanList[i].comments[j].commentContent + '</p>' +
                        '<p class="date">' + new Date(data.beanList[i].comments[j].commentTime).toLocaleString() + '</p>' +
                        '<p class="reply" onclick="showReplyTextArea(' + data.beanList[i].comments[j].commentId + ','
                        + data.beanList[i].comments[j].weiboId + ')" style="cursor: pointer">回复</p>' +
                        '<p class="string"></p>' +
                        '<p class="like" style="cursor: pointer" onclick="likeComment(' + data.beanList[i].comments[j].commentId + ', '
                        + data.beanList[i].comments[j].commentPraise + ')" id="'
                        + data.beanList[i].comments[j].commentId + 'commentPraiseNum"><i class="iconfont">&#xe60c;</i> '
                        + data.beanList[i].comments[j].commentPraise + '</p>' +
                        '</div>' + '<div id="' + data.beanList[i].comments[j].commentId + "replyTextArea" + '"></div>'
                        + '<div id="' + data.beanList[i].comments[j].commentId + 'options"></div>' +
                        '</div>';

                    var children = data.beanList[i].comments[j].children;

                    if (!children || !children.length || children.length == null) {
                        continue;
                    }

                    var childrenList = toList(children);

                    function compare(property) {
                        return function (a, b) {
                            var value1 = a[property];
                            var value2 = b[property];
                            value1 = new Date(value1).getTime();
                            value2 = new Date(value2).getTime();
                            return value2 - value1;
                        }
                    }

                    childrenList.sort(compare('commentTime'));

                    function showAt(nickname) {
                        if (nickname === undefined) {
                            return "";
                        }
                        return ' @' + nickname;
                    }

                    console.log(childrenList);
                    //回复部分
                    for (var b = 0; b < childrenList.length; ++b) {
                        reply += '<div class="option_1_option">' +
                            '<img src="' + childrenList[b].profilePicture + '" alt="img" class="head_img">' +
                            '<div class="message">' +
                            '<p class="nickname"><span>' + childrenList[b].nickname + showAt(childrenList[b].praentNickname) + ' ：</span>' + childrenList[b].commentContent + '</p>' +
                            '<p class="date">' + new Date(childrenList[b].commentTime).toLocaleString() + '</p>' +
                            '<p class="reply" onclick="showReplySReplyTextArea(' + childrenList[b].commentId + ', ' + data.beanList[i].comments[j].weiboId + ')" style="cursor: pointer">回复</p>' +
                            '<p class="string"></p>' +
                            '<p class="like" onclick="likeComment(' + childrenList[b].commentId + ',' + childrenList[b].commentPraise
                            + ')" id="' + childrenList[b].commentId + 'commentPraiseNum" style="cursor: pointer"><i class="iconfont">&#xe60c;</i> ' + childrenList[b].commentPraise + '</p>' +
                            '</div>' +
                            '<div id="' + childrenList[b].commentId + 'replySreply' + '"></div>' + '</div>';
                    }
                    //reply是二级或更高级的评论
                    replys[j] = reply;
                    reply = '';
                    messageIds[j] = data.beanList[i].comments[j].commentId;
                }
                //message是一级评论，即直接评论的微博
                messages[i] = message;

                //ids存的是weiboId
                ids[i] = data.beanList[i].weiboId;
            }

            //ids存的是本页所有微博的id，用于给每个微博盒子加上一级评论
            // console.log(ids);
            //replys里存的是二级以及二级以上的评论的标签字符串
            // console.log(replys);
            //messages里存的是所有微博下的一级评论的标签字符串
            // console.log(messages);
            //messageIds存的是一条微博下的所有一级评论的id
            // console.log(messageIds);
            //把已点赞的微博的大拇指变成红色
            for (var n = 0; n < data.beanList.length; ++n) {
                var weiboId = data.beanList[n].weiboId;
                for (var m = 0; m < allPraise.length; ++m) {
                    if (allPraise[m].weiboId == weiboId) {
                        document.getElementById(weiboId + "aaaa").style.color = "red";
                    }
                }
            }

            // //给微博加上一级评论
            // for (var k = 0; k < messages.length; ++k) {
            //     document.getElementById(ids[k] + "ff").innerHTML = messages[k];
            // }
            //
            // //给一级评论加上更高级的评论
            // for (k = 0; k < messageIds.length; ++k) {
            //     var elementById = document.getElementById(messageIds[k] + "options");
            //     elementById.innerHTML = replys[k];
            // }
        },
        async: false
    });
    PageCode++;
}

function getTotalPage(totalRecord, pageSize) {
    if (totalRecord <= pageSize) {
        return 1;
    }
    return Math.ceil(totalRecord / pageSize);
}

function toList(comment) {
    var list = [];
    for (var t = 0; t < comment.length; ++t) {
        addTree(comment[t], list);
    }
    return list;
}

function addTree(parent, list) {
    var children = parent.children;
    if (children !== undefined && children.length !== undefined && children.length > 0) {
        for (var ch = 0; ch < children.length; ++ch) {
            children[ch].praentNickname = parent.nickname;
            addTree(children[ch], list);
        }
    }
    parent.children = null;
    list.push(parent);
}


function baseInfo() {
    $.ajax({
        url: '/BasicUserInfoServlet',
        type: 'get',
        dataType: 'json',
        data: {},
        success: function (text) {
            user = eval(text);
            userInfo = user;
            profile = user.profilePicture;
            $("#profilePicture").attr('src', user.profilePicture);
            $("#profile1").attr('src', user.profilePicture);
            $("#nicknameLeft").html(user.nickname);
            $("#subscribeNum").html(user.subscribeNum);
            $("#fansNum").html(user.fansNum);
            $("#weiboNum").html(user.weiboNum);
        },
        async: false
    });
}


function imgHeight(photo) {
    var pic = 'style="height: ';
    if (photo.length === 0) {
        return pic + '0"';
    }
    if (photo.length >= 1 && photo.length <= 3) {
        return pic + '136px"'
    }
    if (photo.length >= 4 && photo.length <= 7) {
        return pic + '280px"';
    }
    if (photo.length >= 8) {
        return pic + '420px"';
    }
}

function flushAllCollections() {
    $.ajax({
        url: '/CollectionServlet?type=show',
        type: 'get',
        dataType: 'json',
        data: {},
        success: function (text) {
            allCollection = eval(text);
        },
        async: false
    });
}

function picture(photo) {
    var img = '';
    if (photo.length === 0) {
        return img;
    }

    for (var i = 0; i < photo.length; ++i) {
        img +=
            '<img src="' + photo[i] + '" alt="img" class="middle_down_img middle_down_img' + (i + 1) + '">'
    }
    return img;
}

function showReplyTextArea(commentId, weiboId) {
    var textArea = document.getElementById(commentId + "replyTextArea");
    if (textArea.innerHTML == "") {
        textArea.innerHTML = '<div class="option_1_option">' +
            '<textarea class="OptionTextarea" id="' + commentId + 'replyTextAreaInput"></textarea>' +
            '<input type="submit" name="" value="回复" class="optionReply" onclick="addReply(' + commentId + ',' + '\'replyTextAreaInput\'' + ',' + weiboId + ')">' +
            '</div>';
    } else {
        textArea.innerHTML = "";
    }
}

function showReplySReplyTextArea(commentId, weiboId) {
    var textArea = document.getElementById(commentId + "replySreply");
    if (textArea.innerHTML == "") {
        textArea.innerHTML = '<div class="option_1_option_1">' +
            '<textarea class="OptionTextarea"  id="' + commentId + 'replySreplyInput"></textarea>' +
            '<input type="submit" name="" value="回复" class="OptionReply" onclick="addReply(' + commentId + ',' + '\'replySreplyInput\'' + ',' + weiboId + ')">' +
            '</div>';
    } else {
        textArea.innerHTML = "";
    }
}

function repost(weiboId) {
    $.ajax({
        url: '/RepostServlet?type=add',
        type: 'get',
        dataType: 'json',
        data: {
            weibo: weiboId
        },
        success: {},
        async: false
    });
    alert("转发成功!");
}

function collectionCondition(weiboId) {
    for (var q = 0; q < allCollection.length; ++q) {
        if (allCollection[q].weiboId === weiboId) {
            return "已收藏";
        }
    }
    return "收藏";
}

function showComment(weiboId) {
    var cc = $("#" + weiboId + "cc");
    if (cc.html() == 0) {
        $("#" + weiboId + "ff").css("display", "block");
        cc.html(1);
    }
}

function praise(weiboId) {
    var flag = 0;
    $.ajax({
        url: '/PraiseServlet?type=weibo',
        type: 'get',
        dataType: 'json',
        data: {
            weibo: weiboId
        },
        success: function (text) {
            if (text != null) {
                alert(text);
                flag++;
                return;
            }
        },
        async: false
    });

    if (flag === 0) {
        document.getElementById(weiboId + "aaaa").style.color = "red";
        var praiseNum = document.getElementById(weiboId + 'p');
        praiseNum.innerHTML = parseInt(praiseNum.innerHTML) + 1;
    }
}

function collection(weiboId) {
    var url;
    var collention = document.getElementById(weiboId + "c");
    if (collectionCondition(weiboId) === "收藏") {
        url = "/CollectionServlet?type=add";
        collention.innerHTML = "已收藏";
        allCollection.push({"weiboId": weiboId});
    } else {
        url = "/CollectionServlet?type=delete";
        collention.innerHTML = "收藏";
        for (var b = 0; b < allCollection.length; ++b) {
            if (allCollection[b].weiboId === weiboId) {
                allCollection.splice(b, 1);
                break;
            }
        }
    }
    $.ajax({
        url: url,
        type: 'get',
        dataType: 'json',
        data: {
            weibo: weiboId
        },
        success: function (text) {
            alert(text);
        },
        async: false
    })
}

$(function () {
    var totalHeight = 0; //定义一个总高度变量
    function ata() { //loa动态加载数据
        totalHeight = parseFloat($(window).height()) + parseFloat($(window).scrollTop()); //浏览器的高度加上滚动条的高度
        if ($(document).height() <= totalHeight) { //当文档的高度小于或者等于总的高度时，开始动态加载数据
            showWeibo();
        }
    }

    $(window).scroll(function () {
        ata();
    })
});


function getPraise() {
    $.ajax({
        url: '/MyPraiseServlet?c=aa',
        type: 'get',
        dataType: 'json',
        data: {},
        success: function (text) {
            allPraise = eval(text);
        },
        async: false
    });
}

function addWeibo() {
    var weibo = {};
    var weiboContent = $("#weiboContent").val();
    if (weiboContent == undefined || weiboContent == null || weiboContent == '') {
        alert("微博内容不能为空");
        return;
    }
    weibo.weiboContent = weiboContent;
    $.ajax({
        url: '/AddWeiboServlet',
        type: 'get',
        dataType: 'json',
        data: {
            weibo: JSON.stringify(weibo)
        },
        success: function (text) {
        },
        async: false
    });
    window.location.href = "/home"

}

function addComment(weiboId) {
    var comment = {commentContent: ''};
    var commentContent = $("#" + weiboId + "addcomment").val();
    if (commentContent == undefined || commentContent == null || commentContent == '') {
        alert("评论内容不能为空");
        return;
    }
    comment.commentContent = commentContent;
    var weibo = {weiboId: -1};
    weibo.weiboId = weiboId;
    $.ajax({
        url: '/AddCommentServlet?type=comment',
        type: 'get',
        dataType: 'json',
        data: {
            comment: JSON.stringify(comment),
            weibo: JSON.stringify(weibo)
        },
        success: function (text) {
        },
        async: false
    });
    window.location.href = "/home";
}

function addReply(commentId, textArea, weiboId) {
    var content = $("#" + commentId + textArea).val();
    if (content === undefined || content == null) {
        return;
    }
    var reply = {};
    reply.commentContent = content;
    var comment = {};
    comment.commentId = commentId;
    comment.weiboId = weiboId;
    $.ajax({
        url: '/AddCommentServlet?type=reply',
        type: 'get',
        dataType: 'json',
        data: {
            reply: JSON.stringify(reply),
            comment: JSON.stringify(comment)
        },
        success: function (text) {
            allCollection = eval(text);
        },
        async: false
    });
}


function likeComment(commentId, praise) {
    var comment = {};
    comment.commentId = commentId;
    console.log(comment);
    $.ajax({
        url: '/PraiseServlet?type=comment',
        type: 'get',
        dataType: 'json',
        data: {
            comment: JSON.stringify(comment)
        },
        success: function (text) {

        },
        async: false
    });
    praise++;
    var html = '<i class="iconfont">&#xe60c;</i> ' + praise;

    $("#" + commentId + "commentPraiseNum").html(html);
}

function getAttention() {
    $.ajax({
        url: '/ShowAttentionServlet?type=1',
        type: 'get',
        dataType: 'json',
        data: {},
        success: function (text) {
            allAttention = eval(text);
        },
        async: false
    })
}

function attentionCondition(userId) {
    for (var i = 0; i < allAttention.length; ++i) {
        if (userId == allAttention[i].userId) {
            return "已关注";
        }
        if (userId == userInfo.userId) {
            return "";
        }
    }
    return "+关注";
}

function attention(userId) {
    var url;
    var flag;
    var val = $("#" + userId + "attention");
    var condition = attentionCondition(userId);
    if (condition == "+关注") {
        url = "/AddAttentionServlet";
        flag = 1;
    } else if (condition == "已关注") {
        url = "/RemoveAttentionServlet";
        flag = 0;
    } else {
        return;
    }

    $.ajax({
        url: url,
        type: 'get',
        // dataType: 'json',
        data: {
            userId: userId
        },
        success: function () {
            if (flag == 0) {
                val.html("+关注");
            } else if (flag == 1) {
                val.html("已关注");
            } else {

            }
        },
        async: false
    })
}

function change() {
    $.ajax({
        url: "/BackgroundServlet",
        type: "get",
        datatype: "json",
        data: {},
        success: function (data) {
            var text = eval(data);
            var content = document.getElementById("content");
            // alert(content.style.backgroundImage);
            $(".content").css("background-image", "url(" + '"' + text.img + '"' + ")");
            // var content = $("#content").attr("class");
        }

    })
}


//换轮播图导致发布按钮的样式丢失
window.onload = function () {
    baseInfo();
    change();
    getPraise();
    getAttention();
    showWeibo();
};
