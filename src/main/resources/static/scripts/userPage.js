function showQuotes() {
    $('#picturesButton, #storiesButton, #moviesButton, #lifeGoalsButton').removeClass('active');
    $('#quotesButton').addClass('active');
    $('#pictures, #stories, #movies, #lifeGoals').css('display', 'none');
    $('#quotes').css('display', 'block');
}

function showPictures() {
    $('#quotesButton, #storiesButton, #moviesButton, #lifeGoalsButton').removeClass('active');
    $('#picturesButton').addClass('active');
    $('#quotes, #stories, #movies, #lifeGoals').css('display', 'none');
    $('#pictures').css('display', 'block');
}

function showStories() {
    $('#quotesButton, #picturesButton, #moviesButton, #lifeGoalsButton').removeClass('active');
    $('#storiesButton').addClass('active');
    $('#quotes, #pictures, #movies, #lifeGoals').css('display', 'none');
    $('#stories').css('display', 'block');
}

function showMovies() {
    $('#quotesButton, #picturesButton, #storiesButton, #lifeGoalsButton').removeClass('active');
    $('#moviesButton').addClass('active');
    $('#quotes, #pictures, #stories, #lifeGoals').css('display', 'none');
    $('#movies').css('display', 'block');
}