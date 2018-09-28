-keep class br.com.concrete.desafio.data.model.dto.** { *; }
-keep class br.com.concrete.desafio.data.model.dto.*$Companion { *; }
-keep class br.com.concrete.desafio.data.model.** { *; }
-keep class br.com.concrete.desafio.data.model.*$Companion { *; }

-dontnote kotlin.jvm.internal.DefaultConstructorMarker
-dontnote kotlin.reflect.jvm.internal.ReflectionFactoryImpl
-dontnote kotlin.internal.jdk8.JDK8PlatformImplementations
-dontnote kotlin.internal.JRE8PlatformImplementations
-dontnote kotlin.internal.JRE7PlatformImplementations
-dontnote android.net.http.*
-dontnote org.apache.http.**

# Gson
-keepattributes Signature
-keepattributes *Annotation*
-dontwarn sun.misc.**
-dontnote sun.misc.Unsafe
-keep class com.google.gson.examples.android.model.** { *; }
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer

# OkHttp
-dontwarn okio.**
-dontwarn javax.annotation.Nullable
-dontwarn javax.annotation.ParametersAreNonnullByDefault
-dontwarn javax.annotation.concurrent.GuardedBy
-dontwarn org.conscrypt.Conscrypt
-dontwarn org.conscrypt.OpenSSLProvider
-dontwarn org.conscrypt.OpenSSLProvider

# Retrofit
-dontnote retrofit2.Platform
-dontwarn retrofit2.Platform$Java8
-keepattributes Signature
-keepattributes Exceptions

# EventBus
-keepattributes *Annotation*
-keepclassmembers class * {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}