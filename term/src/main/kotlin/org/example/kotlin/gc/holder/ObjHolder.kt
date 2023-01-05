package org.example.kotlin.gc.holder

import org.template.term.holder.JavaObjHolder
import java.lang.ref.WeakReference

class ObjHolder {

    var holder = WeakReference<Any?>(null)
    fun bind(vararg objs: Any) {
        for (obj in objs) {
            holder = WeakReference(obj)
        }
    }
}


// simple vararg GC fix
fun JavaObjHolder.bindSingle(obj: Any) {
    bind(obj)
}