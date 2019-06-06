package com.lambdaschool.dogsinitial.model

import java.io.Serializable

class MessageDetail : Serializable
{
    var text: String? = null
    var priority: Int = 0
    var isSecret: Boolean = false

    constructor()
    {
    }

    constructor(text: String, priority: Int, secret: Boolean)
    {
        this.text = text
        this.priority = priority
        this.isSecret = secret
    }
}
