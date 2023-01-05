package org.example.kotlin.gc

import org.example.kotlin.gc.holder.ObjHolder
import org.example.kotlin.gc.holder.bindSingle
import org.example.kotlin.gc.module.SomeKotlinObject
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.template.term.holder.JavaObjHolder
import org.template.term.model.SomeJavaObject
import java.lang.ref.WeakReference

class KotlinGcTests {

    @Test
    fun objHolderGcTest() {
        val weakRef = WeakReference(SomeKotlinObject())
        val objHolder = ObjHolder()

        assertNotNull(weakRef.get())
        objHolder.bind(weakRef.get()!!)
        assertNotNull(objHolder.holder.get())

        System.gc()

        assertNull(weakRef.get())
    }


    @Test
    fun javaObjHolderGcTest() {
        val weakRef = WeakReference(SomeKotlinObject())
        val objHolder = JavaObjHolder()

        assertNotNull(weakRef.get())
        objHolder.bind(weakRef.get()!!)
        assertNotNull(objHolder.holder.get())

        System.gc()

        assertNull(weakRef.get())
    }

    @Test
    fun javaObjHolder2GcTest() {
        val weakRef = WeakReference(SomeJavaObject())
        val objHolder = JavaObjHolder()

        assertNotNull(weakRef.get())
        objHolder.bind(weakRef.get()!!)
        assertNotNull(objHolder.holder.get())

        System.gc()

        assertNull(weakRef.get())
    }


    @Test
    fun javaObjHolderSepBindGcTest() {
        val weakRef = WeakReference(SomeJavaObject())
        val objHolder = JavaObjHolder()

        assertNotNull(weakRef.get())
        objHolder.bindSingle(weakRef.get()!!)
        assertNotNull(objHolder.holder.get())

        System.gc()

        assertNull(weakRef.get())
    }

}

