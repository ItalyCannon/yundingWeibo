function load_home1() {
    window.location.href = "/home/index.html"
}

function load_home2() {
    window.location.href = "/collection/index.html";
}

function load_home3() {
    window.location.href = "/praise/index.html";
}

function load_home4() {
    window.location.href = "/commentReceive/index.html";
}

function show_list() {
    window.location.href = "/list/index.html";
}

function loadDetail() {
    window.location.href = "/detail/index.html"
}

function load_attention() {
    window.location.href = "/attention/index.html"
}

function load_fans() {
    window.location.href = "/fans/index.html"
}

var allCollection;
var allPraise;

var PageCode = 1;
var totalPage = 1;
var toRecordFirstCommentIds = 0;
var topFloor = [];
var floorRecord = 0;
var childrenListGlobalArr = [];

function showWeibo() {
    if (PageCode > totalPage) {
        return;
    }
    var url = '/ShowMyWeiboServlet?pc=' + PageCode;
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
                    '<img src="' + data.beanList[i].profilePicture + '" alt="" class="portrait" onclick="showOtherUserInfo(' + data.beanList[i].userId + ');seeMore()" style="cursor: pointer">' +
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
                    '<p class="bottom3_text" id="' + data.beanList[i].weiboId + 'commentNum">' + data.beanList[i].commentNum + '</p>' +
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
                    '<i class="iconfont expression" style="cursor: pointer">&#xe614;</i>' +
                    '<i class="iconfont image" style="cursor: pointer">&#xe72f;</i>' +
                    '<div class="check"></div>' +
                    '<p class="word">同时转发到我的微博</p>' +
                    '<input type="submit" name="" value="发布" class="submit" onclick="addComment(' + data.beanList[i].weiboId + ')" style="cursor: pointer">' +
                    '</div>' +
                    '</div>';
                //评论主体
                for (var j = 0; j < data.beanList[i].comments.length; ++j) {
                    floorRecord++;
                    messageIds[toRecordFirstCommentIds] = data.beanList[i].comments[j].commentId;

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
                    // 楼层相关，此功能已砍
                    // childrenList.topFloor = childrenList.length;
                    // topFloor[floorRecord] = childrenList.length;
                    // var childrenListWithParentCommentId = childrenList;
                    // childrenListWithParentCommentId.parentCommentId = data.beanList[i].comments[j].commentId;
                    // childrenListGlobalArr.push(childrenListWithParentCommentId);

                    console.log(topFloor);

                    function showAt(nickname) {
                        if (nickname === undefined) {
                            return "";
                        }
                        return ' @' + nickname;
                    }

                    function showReplyFloor(parentFloor) {
                        if (parentFloor == 0) {
                            return "";
                        }
                        // return " 回复" + parentFloor + "#";
                        return "";
                    }

                    console.log(childrenList);
                    //回复部分
                    for (var b = 0; b < childrenList.length; ++b) {
                        reply += '<div class="option_1_option" id="' + childrenList[b].commentId + 'replyCommentAAA">' +
                            '<img src="' + childrenList[b].profilePicture + '" alt="img" class="head_img">' +
                            '<div class="message">' +
                            '<p class="nickname"><span>' + childrenList[b].nickname + showReplyFloor(childrenList[b].parentFloor) + showAt(childrenList[b].praentNickname) + ' ：</span>' + childrenList[b].commentContent + '</p>'
                            + '<p class="floor">' + "" + '</p>' +
                            '<p class="date">' + new Date(childrenList[b].commentTime).toLocaleString() + '</p>' +
                            '<p class="reply" onclick="showReplySReplyTextArea(' + childrenList[b].commentId + ', ' + data.beanList[i].comments[j].weiboId + ',' + childrenList[b].parentFloor + ')" style="cursor: pointer">回复</p>' +
                            '<p class="string"></p>' +
                            '<p class="like" onclick="likeComment(' + childrenList[b].commentId + ',' + childrenList[b].commentPraise
                            + ')" id="' + childrenList[b].commentId + 'commentPraiseNum" style="cursor: pointer"><i class="iconfont">&#xe60c;</i> ' + childrenList[b].commentPraise + '</p>' +
                            '</div>' +
                            '<div id="' + childrenList[b].commentId + 'replySreply' + '"></div>' + '</div>';
                    }
                    //reply是二级或更高级的评论
                    replys[toRecordFirstCommentIds] = reply;
                    reply = '';
                    toRecordFirstCommentIds++;
                }
                //message是一级评论，即直接评论的微博
                messages[i] = message;

                //ids存的是weiboId
                ids[i] = data.beanList[i].weiboId;
            }
            for (var n = 0; n < data.beanList.length; ++n) {
                var weiboId = data.beanList[n].weiboId;
                for (var m = 0; m < allPraise.length; ++m) {
                    if (allPraise[m].weiboId == weiboId) {
                        document.getElementById(weiboId + "aaaa").style.color = "#224584";
                    }
                }
            }

            //给微博加上一级评论
            for (var k = 0; k < messages.length; ++k) {
                document.getElementById(ids[k] + "ff").innerHTML = messages[k];
            }

            //给一级评论加上更高级的评论
            // console.log(messageIds);
            for (k = 0; k < messageIds.length; ++k) {
                if (replys[k] === undefined || replys[k] == null) {
                    continue;
                }
                var elementById = document.getElementById(messageIds[k] + "options");
                elementById.innerHTML = replys[k]
                    + ' <div class="option_bottom" style="width:749px;height: 20px;background-color:#F2F2F5;"></div>';

            }
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

var profile = '';
var userInfo;

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
            $("#nickname").html(user.nickname);
            $("#signature").html(user.signature);
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

function addReply(commentId, textArea, weiboId, parentFloor) {
    var isFirstReply = 0;
    var topFloor = -1;

    var commentIdToAdjust = commentId < 0 ? -commentId : commentId;
    // 楼层相关，此功能已砍
    // try {
    //     childrenListGlobalArr.forEach(function (value) {
    //         if (value.parentCommentId != undefined && value.parentCommentId != null && value.parentCommentId == commentIdToAdjust) {
    //             value.topFloor++;
    //             topFloor = value.topFloor;
    //             throw "";
    //         }
    //
    //         value.forEach(function (commentValue) {
    //             if (commentValue.commentId !== undefined && commentValue.commentId != null && commentValue.commentId == commentIdToAdjust) {
    //                 value.topFloor++;
    //                 topFloor = value.topFloor;
    //                 throw "";
    //             }
    //         });
    //     });
    // } catch (e) {
    //
    // }

    var inCommentId = commentId;
    if (inCommentId < 0) {
        inCommentId = -commentId;
    }
    var content = $("#" + commentId + textArea).val();
    if (content === undefined || content == null) {
        return;
    }
    if (commentId < 0) {
        commentId = -commentId;
        isFirstReply = 1;
    }

    var reply = {};
    reply.commentContent = content;
    reply.floor = topFloor;
    var comment = {};
    comment.commentId = commentId;
    comment.weiboId = weiboId;
    comment.parentFloor = parentFloor;
    $.ajax({
        url: '/AddCommentServlet?type=reply',
        type: 'get',
        dataType: 'json',
        data: {
            reply: JSON.stringify(reply),
            comment: JSON.stringify(comment)
        },
        success: function (text) {
            comment = eval(text);
        },
        async: false
    });

    // 楼层相关，此功能已砍
    // //把新加入的评论放到childrenListGlobalArr里
    // try {
    //     childrenListGlobalArr.forEach(function (value) {
    //         if (value.parentCommentId != undefined && value.parentCommentId != null && value.parentCommentId == commentIdToAdjust) {
    //             value.push(comment);
    //             throw "";
    //         }
    //
    //         value.forEach(function (commentValue) {
    //             if (commentValue.commentId !== undefined && commentValue.commentId != null && commentValue.commentId == commentIdToAdjust) {
    //                 value.push(comment);
    //             }
    //             throw "";
    //         });
    //     });
    // } catch (e) {
    //
    // }

    // console.log(comment);
    //隐藏输入框
    var elementById1 = document.getElementById(commentId + "replyTextArea");
    if (elementById1 !== undefined && elementById1 != null && elementById1.innerHTML != "") {
        elementById1.innerHTML = "";
    }
    var elementByIdaaa = document.getElementById(commentId + "replySreply");
    if (elementByIdaaa !== undefined && elementByIdaaa != null && elementByIdaaa.innerHTML != null && elementByIdaaa.innerHTML != "") {
        elementByIdaaa.innerHTML = ""
    }

    function showAt(nickname) {
        if (isFirstReply) {
            return "";
        }

        if (nickname === undefined) {
            return "";
        }
        return ' @' + nickname;
    }

    function showReplyFloor(parentFloor) {
        if (parentFloor == 0) {
            return "";
        }
        // return " 回复" + parentFloor + "#";
        return "";
    }

    // console.log(comment);
    var toReplySReply = '<div class="option_1_option" id="' + comment.commentId + 'replyCommentAAA">' +
        '<img src="' + userInfo.profilePicture + '" alt="img" class="head_img">' +
        '<div class="message">' +
        '<p class="nickname"><span>' + userInfo.nickname + showReplyFloor(comment.parentFloor) + showAt(comment.parentNickname) + ' ：</span>' + content + '</p>' +
        '<p class="floor">' + "" + '</p>' +
        '<p class="date">' + new Date().toLocaleString() + '</p>' +
        '<p class="reply" onclick="showReplySReplyTextArea(' + comment.commentId + ', ' + comment.weiboId + ',' + parentFloor + ')" style="cursor: pointer">回复</p>' +
        '<p class="string"></p>' +
        '<p class="like" onclick="likeComment(' + comment.commentId + ',' + 0
        + ')" id="' + comment.commentId + 'commentPraiseNum" style="cursor: pointer"><i class="iconfont">&#xe60c;</i> ' + 0 + '</p>' +
        '</div>' +
        '<div id="' + comment.commentId + 'replySreply' + '"></div>' + '</div>';

    var firstReply = document.getElementById(inCommentId + "replyCommentAAA");
    if (firstReply != null) {
        var parentElement = firstReply.parentElement;
        var parentComment = parentElement.innerHTML;
        parentElement.innerHTML = toReplySReply + parentComment;
    } else {
        var elementById = document.getElementById(inCommentId + "options");
        var innerHTML = elementById.innerHTML;
        elementById.innerHTML = toReplySReply + innerHTML;
    }

    //微博的评论数加一
    var $1 = $("#" + weiboId + "commentNum");
    var html = $1.html();
    var never = Number(html);
    $1.html(never + 1);
}

function showReplyTextArea(commentId, weiboId) {
    var textArea = document.getElementById(commentId + "replyTextArea");
    commentId = -commentId;
    //如果是一级评论就写上这个标记
    var i = -1;
    if (textArea.innerHTML == "") {
        textArea.innerHTML = '<div class="option_1_option">' +
            '<textarea class="OptionTextarea" id="' + commentId + 'replyTextAreaInput"></textarea>' +
            '<input type="submit" name="" value="回复" class="OptionReply" onclick="addReply(' + commentId + ',' + '\'replyTextAreaInput\'' + ',' + weiboId + ',' + i + ')">' +
            '</div>';
    } else {
        textArea.innerHTML = "";
    }
}

function showReplySReplyTextArea(commentId, weiboId, parentFloor) {
    var textArea = document.getElementById(commentId + "replySreply");
    if (textArea.innerHTML == "") {
        textArea.innerHTML = '<div class="option_1_option_1">' +
            '<textarea class="OptionTextarea"  id="' + commentId + 'replySreplyInput"></textarea>' +
            '<input type="submit" name="" value="回复" class="OptionReply" onclick="addReply(' + commentId + ',' + '\'replySreplyInput\'' + ',' + weiboId + ',' + parentFloor + ')">' +
            '</div>';
    } else {
        textArea.innerHTML = "";
    }
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
    } else {
        $("#" + weiboId + "ff").css("display", "none");
        cc.html(0);
    }
}

function praise(weiboId) {
    var data = {};
    $.ajax({
        url: '/PraiseServlet?type=weibo',
        type: 'get',
        dataType: 'json',
        data: {
            weibo: weiboId
        },
        success: function (text) {
            data = eval(text)
        },
        async: false
    });

    var praiseNum = document.getElementById(weiboId + 'p');
    if (data.msg === 1) {
        document.getElementById(weiboId + "aaaa").style.color = "#224584";

        praiseNum.innerHTML = parseInt(praiseNum.innerHTML) + 1;
        return;
    }

    if (data.msg === 0) {
        document.getElementById(weiboId + "aaaa").style.color = "";

        praiseNum.innerHTML = parseInt(praiseNum.innerHTML) - 1;
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
            comment = eval(text);
        },
        async: false
    });
    //微博的评论数加一
    var $1 = $("#" + weiboId + "commentNum");
    var html = $1.html();
    var never = Number(html);
    $1.html(never + 1);

    var toReply = '<div class="option_1" id="' + comment.commentId + 'comment">' +
        '<img src="' + userInfo.profilePicture + '" alt="img" class="head_img">' +
        '<div class="message">' +
        '<p class="nickname"><span>' + userInfo.nickname + '：' + '</span>'
        + commentContent + '</p>' +
        '<p class="date">' + new Date().toLocaleString() + '</p>' +
        '<p class="reply" onclick="showReplyTextArea(' + comment.commentId + ','
        + comment.weiboId + ')" style="cursor: pointer">回复</p>' +
        '<p class="string"></p>' +
        '<p class="like" style="cursor: pointer" onclick="likeComment(' + comment.commentId + ', '
        + 0 + ')" id="'
        + comment.commentId + 'commentPraiseNum"><i class="iconfont">&#xe60c;</i> '
        + 0 + '</p>' +
        '</div>' + '<div id="' + comment.commentId + "replyTextArea" + '"></div>'
        + '<div id="' + comment.commentId + 'options"></div>' +
        '</div>';
    var elementById = document.getElementById(weiboId + "ff");
    var innerHTML1 = elementById.innerHTML;
    var number = innerHTML1.indexOf("<div class=\"option_1\"");
    var botton = ' <div class="option_bottom" style="width:749px;height: 20px;background-color:#F2F2F5;"></div>';
    if (number != -1) {
        var s = innerHTML1.substring(0, number);
        var end = innerHTML1.substring(number);
        elementById.innerHTML = s + toReply + end + botton;
    } else {
        elementById.innerHTML += toReply;
        elementById.innerHTML += botton;
    }
}

function likeComment(commentId, praise) {
    var comment = {};
    comment.commentId = commentId;
    // console.log(comment);
    var data = -1;
    $.ajax({
        url: '/PraiseServlet?type=comment',
        type: 'get',
        dataType: 'json',
        data: {
            comment: JSON.stringify(comment)
        },
        success: function (text) {
            data = eval(text);
        },
        async: false
    });


    if (data.msg == 1) {
        praise++;
    } else {
        praise--;
    }


    var html = '<i class="iconfont">&#xe60c;</i> ' + praise;
    var $1 = $("#" + commentId + "commentPraiseNum");
    $1.html(html);
    $1.removeAttr("onclick");
    $1.attr("onclick", 'likeComment(' + commentId + ',' + praise + ')');
}
function getInfo() {
    $.ajax({
        url: '/UserDetailsServlet',
        type: 'get',
        dataType: 'json',
        data: {},
        success: function (text) {
            var data = eval(text);
            $("#location").html('<i class="iconfont">&#xe60c;</i> &nbsp;' + data.location);
            $("#school").html('<i class="iconfont">&#xe60c;</i> &nbsp;' + data.undergraduateSchool);
            var formatBirthday = data.formatBirthday;
            var split = formatBirthday.split("-");
            $("#birthday").html('<i class="iconfont">&#xe60c;</i> &nbsp;' + split[0] + '年' + split[1] + '月' + split[2] + '日');
        }
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

function change() {
    $.ajax({
        url: "/BackgroundServlet",
        type: "get",
        datatype: "json",
        data: {},
        success: function (data) {
            var text = eval(data);
            // var background = document.getElementById("background");
            // alert(content.style.backgroundImage);
            $(".background").css("background-image", "url(" + '"' + text.img + '"' + ")");
            // var content = $("#content").attr("class");
        }

    })
}

$(function () {
    $("#return").click(function () {
        $("body,html").animate({
            scrollTop: 0
        }, 1000);
    });
});

function logout() {
    $.ajax({
        url: "/QuitServlet",
        type: "get",
        datatype: "json",
        data: {},
        success: function (text) {
            var data = eval(text);
            var message = data.msg;

            if (message == "退出成功") {
                window.location.href = "/login";
                return;
            }
            alert(message);
        }
    })
}

window.onload = function () {
    baseInfo();
    change();
    getPraise();
    showWeibo();
    getInfo();
};
