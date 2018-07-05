#include <jni.h>
#include <string>
//#include <iostream>
//#include <stdlib.h>
//自定义的.h文件不能使用<>引用
#include "Hello.h"
#include <android/log.h>
//457542916
using namespace std;
extern "C"


//int func() {
//    return 10 + 20;
//}
#define LOGE(...) ((void)__android_log_print(ANDROID_LOG_ERROR, "我的c++", __VA_ARGS__))
JNIEXPORT jstring JNICALL
Java_com_kimonik_mypublicmodule_HomeActivity_stringFromJNI(JNIEnv *env, jobject instance) {
    string hello = "Hello from C++";
    string s = "mmmmm";
    string h = to_string(30);
    Hello hello1;
    string a = hello1.add("10", "9");
    hello = hello + s + h + a;
    char* c=new char[strlen(hello.c_str())+1];
//     char* c;
//    strcpy(c, hello.c_str());
    LOGE("[%s] decode %s jpeg images, spend time: %d ms\n", c, c, 200/1000);
    __android_log_print(ANDROID_LOG_ERROR,"我的c++",c);
    return env->NewStringUTF(hello.c_str());
}