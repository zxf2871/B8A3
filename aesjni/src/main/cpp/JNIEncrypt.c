#include <jni.h>
#include "aes.h"
#include "scandir.h"
#include "checksignature.h"
#include <string.h>
#include <android/log.h>

#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG,TAG ,__VA_ARGS__) // 定义LOGD类型
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO,TAG ,__VA_ARGS__) // 定义LOGI类型
#define LOGW(...) __android_log_print(ANDROID_LOG_WARN,TAG ,__VA_ARGS__) // 定义LOGW类型
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR,TAG ,__VA_ARGS__) // 定义LOGE类型
#define LOGF(...) __android_log_print(ANDROID_LOG_FATAL,TAG ,__VA_ARGS__) // 定义LOGF类型

#define CBC 1
#define ECB 1


// 获取数组的大小
# define NELEM(x) ((int) (sizeof(x) / sizeof((x)[0])))
// 指定要注册的类，对应完整的java类名
#define JNIREG_CLASS "com/aesjni/AESEncrypt"

const char *UNSIGNATURE = "UNSIGNATURE";


__attribute__((section (".mytext")))
JNIEXPORT jstring JNICALL encode(JNIEnv *env, jobject instance, jobject context, jstring str_) {

//    //先进行apk被 二次打包的校验
//    if (checkSignature(env, instance, context) != 1) {
//        char *str = UNSIGNATURE;
//        return (*env)->NewString(env, str, strlen(str));
//    }

    uint8_t AES_KEY[] = "1234567890abcdef";
    const char *in = (*env)->GetStringUTFChars(env, str_, JNI_FALSE);
    char *baseResult = AES_128_ECB_PKCS5Padding_Encrypt(in, AES_KEY);
    (*env)->ReleaseStringUTFChars(env, str_, in);
    return (*env)->NewStringUTF(env, baseResult);
}

__attribute__((section (".mytext")))
JNIEXPORT jstring JNICALL decode(JNIEnv *env, jobject instance, jobject context, jstring str_) {


//    //先进行apk被 二次打包的校验
//    if (checkSignature(env, instance, context) != 1) {
//        char *str = UNSIGNATURE;
//        return (*env)->NewString(env, str, strlen(str));
//    }

    uint8_t AES_KEY[] = "1234567890abcdef";
    const char *str = (*env)->GetStringUTFChars(env, str_, JNI_FALSE);
    char *desResult = AES_128_ECB_PKCS5Padding_Decrypt(str, AES_KEY);
    (*env)->ReleaseStringUTFChars(env, str_, str);
    return (*env)->NewStringUTF(env, desResult);
    //不用系统自带的方法NewStringUTF是因为如果desResult是乱码,会抛出异常
    //return charToJstring(env,desResult);
}


jstring charToJstring(JNIEnv *envPtr, char *src) {
    JNIEnv env = *envPtr;

    jsize len = strlen(src);
    jclass clsstring = env->FindClass(envPtr, "java/lang/String");
    jstring strencode = env->NewStringUTF(envPtr, "UTF-8");
    jmethodID mid = env->GetMethodID(envPtr, clsstring, "<init>",
                                     "([BLjava/lang/String;)V");
    jbyteArray barr = env->NewByteArray(envPtr, len);
    env->SetByteArrayRegion(envPtr, barr, 0, len, (jbyte *) src);

    return (jstring) env->NewObject(envPtr, clsstring, mid, barr, strencode);
}


/**
 * if rerurn 1 ,is check pass.
 */
JNIEXPORT jint JNICALL
check(JNIEnv *env, jobject instance, jobject con) {

    return checkSignature(env, instance, con);
}

JNIEXPORT jint JNICALL _scan(JNIEnv *env, jobject instance, jstring str_) {

    const char *in = (*env)->GetStringUTFChars(env, str_, JNI_FALSE);
    scan_dir(env, in);
    return 1;
}


// Java和JNI函数的绑定表
static JNINativeMethod method_table[] = {
        {"checkSignature", "(Ljava/lang/Object;)I",                                    (void *) check},
        {"decode",         "(Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/String;", (void *) decode},
        {"encode",         "(Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/String;", (void *) encode},
        {"scanDir",        "(Ljava/lang/String;)V",                                    (void *) _scan},
};

// 注册native方法到java中
static int registerNativeMethods(JNIEnv *env, const char *className,
                                 JNINativeMethod *gMethods, int numMethods) {
    jclass clazz;
    clazz = (*env)->FindClass(env, className);
    if (clazz == NULL) {
        return JNI_FALSE;
    }
    if ((*env)->RegisterNatives(env, clazz, gMethods, numMethods) < 0) {
        return JNI_FALSE;
    }

    return JNI_TRUE;
}

int register_ndk_load(JNIEnv *env) {
    // 调用注册方法
    return registerNativeMethods(env, JNIREG_CLASS,
                                 method_table, NELEM(method_table));
}

JNIEXPORT jint JNI_OnLoad(JavaVM *vm, void *reserved) {
    JNIEnv *env = NULL;
    jint result = -1;
    if ((*vm)->GetEnv(vm, (void **) &env, JNI_VERSION_1_4) != JNI_OK) {
        return result;
    }
    register_ndk_load(env);

    // 返回jni的版本
    return JNI_VERSION_1_4;
}

