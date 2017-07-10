//
// Created by Administrator on 2017/7/5.
//
#include <jni.h>
#include <string.h>
#include <stdio.h>
#include <android/log.h>
#include <dirent.h>
#include <sys/stat.h>
#include <unistd.h>
#include "scandir.h"


#define TAG "scandir-jni" // 这个是自定义的LOG的标识
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG,TAG ,__VA_ARGS__) // 定义LOGD类型
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO,TAG ,__VA_ARGS__) // 定义LOGI类型
#define LOGW(...) __android_log_print(ANDROID_LOG_WARN,TAG ,__VA_ARGS__) // 定义LOGW类型
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR,TAG ,__VA_ARGS__) // 定义LOGE类型
#define LOGF(...) __android_log_print(ANDROID_LOG_FATAL,TAG ,__VA_ARGS__) // 定义LOGF类型

static char fgids[2];

jclass ccClass;
jmethodID context_s_id;
jstring str_arg;


void scan_dir(JNIEnv *env, const char *directory) {
    DIR *dp;
    struct dirent *entry;
    struct stat statbuf;
//    LOGE("directory: %s",directory);
    if ((dp = opendir(directory)) == NULL) {
        perror("no dir");
        LOGE("########## i = %s", "no dir");
        return;
    }
    chdir(directory);
    while ((entry = readdir(dp)) != NULL) {
        stat(entry->d_name, &statbuf);
        if (S_ISDIR(statbuf.st_mode)) {
            if ((strcmp(entry->d_name, ".") != 0) && (strcmp(entry->d_name, "..") != 0) &&
                (entry->d_name[0] != '.')) {
                scan_dir(env, entry->d_name);
            }
        } else {
            if (strstr(entry->d_name, ".") != 0) {
                char tempstr[224];
//                callBack(env, entry->d_name);


//                jstring str_arg = (*env)->NewStringUTF(env,"check sign fail");

                if (ccClass == NULL) {
                    ccClass = (*env)->FindClass(env, "com/aesjni/AESEncrypt");
                }

                if (context_s_id == NULL) {
                    context_s_id = (*env)->GetStaticMethodID(env, ccClass, "scanCallBack",
                                                             "(Ljava/lang/String;)V");
                }
                str_arg = (*env)->NewStringUTF(env, entry->d_name);

                if (context_s_id != NULL) {
                    (*env)->CallStaticVoidMethod(env, ccClass, context_s_id, str_arg);
                }
                (*env)->ReleaseStringUTFChars(env, str_arg, entry->d_name);
                (*env)->DeleteLocalRef(env,str_arg);



//                sprintf(tempstr, "%s", entry->d_name);
//                LOGE("file: %s",entry->d_name);

//                strcat(fgids, directory);
//                strcat(fgids, "/");
//                strcat(fgids, tempstr);
//                strcat(fgids, ",");
            }
        }
    }
    chdir("..");
    closedir(dp);
}

void callBack(JNIEnv *env, char *fileName) {

    //校验不成退出
//    jclass s = (*env)->FindClass(env, "com/aesjni/AESEncrypt");
//    jmethodID id = (*env)->GetStaticMethodID(env, s, "scanCallBack", "(Ljava/lang/String;)V");
//    if (id != NULL) {
//        (*env)->CallStaticVoidMethod(env, id, fileName);
//    }

//    jstring str_arg = (*env)->NewStringUTF(env,"check sign fail");
//    jclass ccClass = (*env)->FindClass(env, "com/aesjni/AESEncrypt");
////    jmethodID context_s_id = (*env)->GetStaticMethodID(env, ccClass, "scanCallBack", "(Ljava/lang/String;)V");
////    (*env)->CallStaticVoidMethod(env, ccClass, context_s_id,"");


}
