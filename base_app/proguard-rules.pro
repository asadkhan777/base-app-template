# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

-useuniqueclassmembernames 
-keepattributes SourceFile,LineNumberTable 
-keepattributes Signature
-keepattributes Exceptions
-keepattributes *Annotation*
-keepattributes SourceFile,LineNumberTable
-allowaccessmodification

-dontwarn retrofit2.**
-dontwarn okio.**
-dontwarn com.squareup.okhttp.**
-dontwarn javax.annotation.Nullable
-dontwarn javax.annotation.ParametersAreNonnullByDefault

-keep class retrofit2.*.* { *; }
-keep class com.google.android.gms.*.* { *; }
-keep class androidx.appcompat.widget.SearchView { *; }
-keep class com.asadkhan.global.* {*; }
-keep class com.asadkhan.base_app.* {*; }

-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}

#http://stackoverflow.com/a/30532633/2639476
-keepnames class * implements java.io.Serializable
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}


# Top-level functions that can only be used by Kotlin.
-dontwarn retrofit2.KotlinExtensions
-dontwarn retrofit2.KotlinExtensions$*

-keepclassmembers class kotlin.Metadata {
    public <methods>;
}