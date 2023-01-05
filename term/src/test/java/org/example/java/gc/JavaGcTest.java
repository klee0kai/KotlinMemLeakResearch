package org.example.java.gc;

import org.junit.jupiter.api.Test;
import org.template.term.holder.JavaObjHolder;
import org.template.term.model.SomeJavaObject;

import java.lang.ref.WeakReference;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class JavaGcTest {

    @Test
    void javaObjHolderGcTest() {
        // GIVEN
        WeakReference<SomeJavaObject> weakRef = new WeakReference<>(new SomeJavaObject());
        JavaObjHolder holder = new JavaObjHolder();

        // WHEN
        holder.bind(weakRef.get());
        System.gc();

        // THEN
        assertNull(weakRef.get());
    }

}
