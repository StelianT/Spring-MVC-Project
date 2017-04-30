function like(elem, type) {
    var entityId = elem.value;
    $.ajax({
        type: 'POST',
        url: '/' + type + '/like/' + entityId,
        success: addNewLike(entityId, type)
    });
}

function addNewLike(entityId, type) {
    var currentUsername = $('#loggedInUser').val();
    var currentUserId = $('#loggedInUserId').val();

    var aTag = document.createElement('a');
    aTag.setAttribute('href',"/user/" + currentUserId);

    var isOnlyOneLike = !($('#' + entityId).children.length > 0);

    var newDiv = document.createElement("div");
    var newContent = document.createTextNode(currentUsername);
    newDiv.appendChild(newContent);
    $(newDiv).addClass(currentUserId);
    $(newDiv).addClass("list-group-item");
    aTag.appendChild(newDiv);
    $('#' + entityId).append(aTag);

    $('#id' + entityId).removeClass("disabled");

    if (isOnlyOneLike) {
        $('#id' + entityId).attr('isOnlyOneLike', 'true');
    } else {
        $('#id' + entityId).attr('isOnlyOneLike', 'false');
    }

    $('button[value=' + entityId + ']').text('Unlike').attr('onclick', 'unlike(this, \'' + type + '\')');
}

function unlike(elem, type) {
    var entityId = elem.value;
    $.ajax({
        type: 'POST',
        url: '/' + type + '/unlike/' + entityId,
        success: removeLike(entityId, type)
    });
}

function removeLike(entityId, type) {
    var currentUserId = $('#loggedInUserId').val();
    $('#id' + entityId + ' ' + '.' + currentUserId).remove();
    $('button[value=' + entityId + ']').text('Like').attr('onclick', 'like(this, \'' + type + '\')');

    var isOnlyOneLike = $('#id' + entityId).attr('isOnlyOneLike');
    if (isOnlyOneLike === "true") {
        $('#id' + entityId).addClass("disabled");
    }
}

function onLikedByHover(elem) {
    var entityId = elem.getAttribute('value');
    document.getElementById(entityId).style.position = 'absolute';
    document.getElementById(entityId).style.zIndex = '1';
    document.getElementById(entityId).style.display = 'block';
}

function onLikedByOut(elem) {
    var entityId = elem.getAttribute('value');
    document.getElementById(entityId).style.display = 'none';
}