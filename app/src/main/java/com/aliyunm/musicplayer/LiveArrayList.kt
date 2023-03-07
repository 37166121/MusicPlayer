package com.aliyunm.musicplayer

import androidx.lifecycle.MutableLiveData
import com.aliyunm.musicplayer.listener.ArrayListItemChangeListener

class LiveArrayList<T> : ArrayList<T> {

    val event : MutableLiveData<Int> = MutableLiveData()
    val add : MutableLiveData<T?> = MutableLiveData()
    val addAll : MutableLiveData<Collection<T>> = MutableLiveData()
    val remove : MutableLiveData<T?> = MutableLiveData()
    val removeAt : MutableLiveData<Int> = MutableLiveData()
    val removeAll : MutableLiveData<Collection<T>> = MutableLiveData()
    val clear : MutableLiveData<Boolean> = MutableLiveData()

    private var listener : ArrayListItemChangeListener<T>? = null

    constructor() : this(null)

    constructor(collection : Collection<T>) : this(null, collection)

    constructor(arrayList : ArrayList<T>) : this(null, arrayList)

    constructor(listener : ArrayListItemChangeListener<T>?) : this(listener, arrayListOf())

    constructor(listener : ArrayListItemChangeListener<T>?, collection : Collection<T>) : super(collection) {
        this.listener = listener
    }

    fun setListener(listener : ArrayListItemChangeListener<T>) {
        this.listener = listener
    }

    override fun add(element: T): Boolean {
        add.postValue(element)
        listener?.add(element)
        val result = super.add(element)
        listener?.event()
        event.postValue(this.size)
        return result
    }

    override fun add(index: Int, element: T) {
        listener?.add(index, element)
        add.postValue(element)
        super.add(index, element)
        listener?.event()
        event.postValue(this.size)
    }

    override fun addAll(elements: Collection<T>): Boolean {
        listener?.addAll(elements)
        addAll.postValue(elements)
        val result = super.addAll(elements)
        listener?.event()
        event.postValue(this.size)
        return result
    }

    override fun addAll(index: Int, elements: Collection<T>): Boolean {
        listener?.addAll(index, elements)
        addAll.postValue(elements)
        val result = super.addAll(index, elements)
        listener?.event()
        event.postValue(this.size)
        return result
    }

    override fun remove(element: T): Boolean {
        listener?.remove(element)
        remove.postValue(element)
        val result = super.remove(element)
        listener?.event()
        event.postValue(this.size)
        return result
    }

    override fun removeAt(index: Int): T {
        listener?.removeAt(index)
        removeAt.postValue(index)
        val result = super.removeAt(index)
        listener?.event()
        event.postValue(this.size)
        return result
    }

    override fun removeAll(elements: Collection<T>): Boolean {
        listener?.removeAll(elements)
        removeAll.postValue(elements)
        val result = super.removeAll(elements)
        listener?.event()
        event.postValue(this.size)
        return result
    }

    override fun clear() {
        listener?.clear()
        clear.postValue(true)
        super.clear()
        listener?.event()
        event.postValue(this.size)
    }
}