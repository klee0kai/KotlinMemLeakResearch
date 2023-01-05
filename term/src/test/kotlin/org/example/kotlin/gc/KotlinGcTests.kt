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
        // GIVEN
        val weakRef = WeakReference(SomeKotlinObject())
        val objHolder = ObjHolder()

        // WHEN
        objHolder.bind(weakRef.get()!!)
        System.gc()

        // THEN
        assertNull(weakRef.get())
    }


    @Test
    fun javaObjHolderGcTest() {
        // GIVEN
        val weakRef = WeakReference(SomeKotlinObject())
        val objHolder = JavaObjHolder()

        // WHEN
        objHolder.bind(weakRef.get()!!)
        System.gc()

        // THEN
        assertNull(weakRef.get())
    }

    @Test
    fun javaObjHolder2GcTest() {
        // GIVEN
        val weakRef = WeakReference(SomeJavaObject())
        val objHolder = JavaObjHolder()

        // WHEN
        objHolder.bind(weakRef.get()!!)
        System.gc()

        // THEN
        assertNull(weakRef.get())
    }


    @Test
    fun javaObjHolderSepBindGcTest() {
        // GIVEN
        val weakRef = WeakReference(SomeJavaObject())
        val objHolder = JavaObjHolder()

        // WHEN
        objHolder.bindSingle(weakRef.get()!!)
        System.gc()

        // THEN
        assertNull(weakRef.get())
    }

    @Test
    fun javaObjHolderAdditionalValBindGcTest() {
        // GIVEN
        val weakRef = WeakReference(SomeJavaObject())
        val objHolder = JavaObjHolder()

        // WHEN
        objHolder.bind(weakRef.get()!!)
        val i = 0
        System.gc()

        // THEN
        assertNull(weakRef.get())
    }

}

