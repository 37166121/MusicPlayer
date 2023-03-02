package com.aliyunm.musicplayer.listener

interface ArrayListItemChangeListener<T> {

    fun event()

    fun add(element : T)

    fun add(index : Int, element : T)

    fun addAll(elements: Collection<T>)

    fun addAll(index: Int, elements: Collection<T>)

    fun remove(element : T)

    fun removeAt(index : Int)

    fun removeAll(elements: Collection<T>)

    fun clear()
}