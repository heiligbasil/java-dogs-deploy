package com.lambdaschool.javadogsdeploy

import com.lambdaschool.javadogsdeploy.model.Dog

interface CheckDog {
    fun test(d: Dog): Boolean

    companion object {
        inline operator fun invoke(crossinline op: (d: Dog) -> Boolean) =
                object  : CheckDog {
                    override fun test(d: Dog): Boolean = op(d)
                }
    }
}