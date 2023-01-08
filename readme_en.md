# Kotlin GC Research

[Русская версия](./readme.md)

Garbage rollector work research for java and kotlin.
Run same test for java, kotlin have 
[different results]((https://github.com/klee0kai/KotlinMemLeakResearch/actions/runs/3860669356/jobs/6581146165).).

The research examined the call to `System.gc()`
after calling a method with a variable number of parameters.

## Testing

In tests, an object is created and a method with a variable number of parameters `holder.bind(weakRef.get())` is called.
Next, it is checked that the entire created object is correctly collected by the collector, since we do not use strong references to
him.

For java, the following test runs correctly, 
all created objects without strong references are collected correctly.

``` java
✅ @Test
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
```

For kotlin, same test fails.

```kotlin
❌ @Test
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
```

This problem can be fixed by moving the call to the `holder.bind(weakRef.get())` method inside another method.
Move the call to `fun JavaObjHolder.bindSingle(obj: Any)`

```kotlin
✅ @Test
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
```

However, quite unexpectedly, the fix is an additional declaration of a variable before garbage collection.

```kotlin
✅ @Test
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
```

## License

```
Copyright (c) 2022 Andrey Kuzubov
```

