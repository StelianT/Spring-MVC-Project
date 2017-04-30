function likeQuote(elem) {
    var storyId = elem.value;
    $.ajax({
        type: 'POST',
        url: '/stories/like/' + storyId,
        success: addNewLike(storyId)
    });
}

function addNewLike(storyId) {
    var currentUsername = $('#loggedInUser').val();
    var currentUserId = $('#loggedInUserId').val();

    var aTag = document.createElement('a');
    aTag.setAttribute('href',"/user/" + currentUserId);

    var isOnlyOneLike = !($('#' + storyId).children.length > 0);

    var newDiv = document.createElement("div");
    var newContent = document.createTextNode(currentUsername);
    newDiv.appendChild(newContent);
    $(newDiv).addClass(currentUserId);
    $(newDiv).addClass("list-group-item");
    aTag.appendChild(newDiv);
    $('#' + storyId).append(aTag);

    $('#id' + storyId).removeClass("disabled");

    if (isOnlyOneLike) {
        $('#id' + storyId).attr('isOnlyOneLike', 'true');
    } else {
        $('#id' + storyId).attr('isOnlyOneLike', 'false');
    }

    $('button[value=' + storyId + ']').text('Unlike').attr('onclick', 'unlike(this)');
}

function unlike(elem) {
    var storyId = elem.value;
    $.ajax({
        type: 'POST',
        url: '/stories/unlike/' + storyId,
        success: removeLike(storyId)
    });
}

function removeLike(storyId) {
    var currentUserId = $('#loggedInUserId').val();
    $('#id' + storyId + ' ' + '.' + currentUserId).remove();
    $('button[value=' + storyId + ']').text('Like').attr('onclick', 'likeQuote(this)');

    var isOnlyOneLike = $('#id' + storyId).attr('isOnlyOneLike');
    if (isOnlyOneLike === "true") {
        $('#id' + storyId).addClass("disabled");
    }
}

function onLikedByHover(elem) {
    var storyId = elem.getAttribute('value');
    document.getElementById(storyId).style.position = 'absolute';
    document.getElementById(storyId).style.zIndex = '1';
    document.getElementById(storyId).style.display = 'block';
}

function onLikedByOut(elem) {
    var storyId = elem.getAttribute('value');
    document.getElementById(storyId).style.display = 'none';
}