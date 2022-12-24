package com.chalo.assignments.omdbcarousal.home.repository.models

data class Media(
    val Title: String,
    val Year: String,
    val Rated: String,
    val Released: String,
    val Runtime: String,
    val Genre: String,
    val Director: String,
    val Writer: String,
    val Actors: String,
    val Plot: String,
    val Language: String,
    val Country: String,
    val Awards: String,
    val Poster: String,
    val Ratings: List<Rating>,
    val Metascore: String,
    val imdbRating: String,
    val imdbVotes: String,
    val imdbID: String,
    val Type: String,
    val DVD: String,
    val BoxOffice: String,
    val Production: String,
    val Website: String,
    val Response: String
){

    fun getSynopsis(): String{
        var synopsis = "Title : "
        synopsis += Title + "\n"
        synopsis += "Year : $Year\n"
        synopsis += "ImdbID : $imdbID\n"
        synopsis += "Type : $Type\n"
        return synopsis
    }

    fun getTitleYear(): String{
        return "$Title\n($Year)"
    }
}
