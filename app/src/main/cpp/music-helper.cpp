#include <jni.h>
#include <iostream>
#include <libavformat/avformat.h>
#include <libavcodec/avcodec.h>
#include <libswresample/swresample.h>

using namespace std;
extern "C"
JNIEXPORT jstring JNICALL
Java_com_aliyunm_musicplayer_helper_MusicFileHelper_decode(JNIEnv *env, jobject thiz,
                                                           jbyteArray file) {
    
}