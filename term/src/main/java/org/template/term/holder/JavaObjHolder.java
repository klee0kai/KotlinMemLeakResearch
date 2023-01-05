package org.template.term.holder;

import java.lang.ref.WeakReference;

public class JavaObjHolder {

    public WeakReference<Object> holder = new WeakReference<>(null);


    public void bind(Object... objects) {
        for (Object ob : objects) {
            holder = new WeakReference<>(ob);
        }
    }


}
