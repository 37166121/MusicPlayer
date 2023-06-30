# MusicPlayer 仿网易云播放器
使用网易云音乐开放平台接口 <a href="https://developer.music.163.com/st/developer/document?category=sandbox">文档中心</a>
FFmpeg 5.0

## Note

#### 2023年3月16日

> <p>看了一下网易云技术团队博客，他们使用的可视化方案<a href="https://mp.weixin.qq.com/s/Hx5pikOZJSkGDMH2VRnbyw">Android 音频可视化</a></p>
> <p>大致流程：获取数据源（自定义Visualizer） > 处理数据 > 绘制，获取数据源和处理数据比较复杂</p>
> <p>获取数据源大致流程：在播放进程 native 层中计算 FFT，通过 JNI 调用，把计算结果回调给Java 层，然后通过 AIDL 把 FFT 数据传递给主进程进行后续的数据处理和发送操作</p>
> <p>处理数据大致流程：计算索引频率 > 选择数据 > 计算分贝 > 将分贝转化为高度 > 平滑数据</p>
> <p>APP目前阶段只是拿到Android Visualizer的fft数据，然后间隔取样，最后平滑，得不到网易云的效果</p>

## example

<div align=center >
  <a href="#"><img style="width:200px" src="https://raw.githubusercontent.com/37166121/MusicPlayer/master/example/2023-3-10-1.png"><p>2023-3-10-1.png</p></a>
  <a href="#"><img style="width:200px" src="https://raw.githubusercontent.com/37166121/MusicPlayer/master/example/2023-3-10-2.png"><p>2023-3-10-2.png</p></a>
</div>