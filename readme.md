# Kotlin GC Research

[English Version](./readme_en.md)

Исследование работы Garbage Collector на java и на kotlin.
Одинаковый тест для java и для kotlin проходит 
[по-разному](https://github.com/klee0kai/KotlinMemLeakResearch/actions/runs/3860669356/jobs/6581146165).
Актуально для [kotlin 1.8.0](https://github.com/klee0kai/KotlinMemLeakResearch/actions/runs/3865810249/jobs/6589550437)

В исследовании рассмотрен вызов `System.gc()` 
после вызова метода с переменным кол-вом параметров. 

## Тестирование 

В тестах создается обьект и вызывается метод с переменным кол-вом параметров `holder.bind(weakRef.get())`.
Далее проверяется, что все созданный обьект корректно собирается сборщиком, так как мы не используем сильных ссылок к нему.  

Для java следующий тест, выполняется корректно, 
все созданные обьекты без сильных ссылок правильно собираются.

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

Для kotlin, же тест не срабатывает. 

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

Данную проблему можно исправить, вынесев вызов метода  `holder.bind(weakRef.get())` внутрь другого метода. 
Выносим вызов в `fun JavaObjHolder.bindSingle(obj: Any)`

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

Однако, совсем неожиданным, является исправление дополнительным обьявлением переменной перед сборкой мусора.

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

## Refs 

 - [Issue KT-55818](https://youtrack.jetbrains.com/issue/KT-55818)


## License

```
Copyright (c) 2022 Andrey Kuzubov
```

