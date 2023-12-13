package com.dvg.themovieapp.movies.models

data class Movie(
    val name: String?,
    val ratings: String?,
    val synopsis: String?
){
    override fun toString(): String {
        return name!!
    }
}