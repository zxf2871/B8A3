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

static char fgids[2024];

void scan_dir(JNIEnv *env, const char *directory) {
    DIR *dp;
    struct dirent *entry;
    struct stat statbuf;
    LOGE("########## i = %d",directory);
    if ((dp = opendir(directory)) == NULL) {
        perror("no dir");
        LOGE("########## i = %d", "no dir");
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

                sprintf(tempstr, "%s", entry->d_name);
                strcat(fgids, directory);
                strcat(fgids, "/");
                strcat(fgids, tempstr);
                strcat(fgids, ",");
            }
        }
    }
    chdir("..");
    closedir(dp);
}
