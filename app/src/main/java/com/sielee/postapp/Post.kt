package com.sielee.postapp

import java.io.Serializable

data class Post(

    var userId: Int = 0,
    var title: String="",
    var body: String ="",

) :Serializable{
    var id: Int=0
}