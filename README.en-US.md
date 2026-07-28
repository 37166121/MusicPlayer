# MusicPlayer NetEase Cloud Music Clone
Uses NetEase Cloud Music Open Platform API <a href="https://developer.music.163.com/st/developer/document?category=sandbox">Documentation Center</a>
FFmpeg 5.0

## Note

#### March 16, 2023

> <p>Checked the NetEase technical team's blog regarding their visualization solution: <a href="https://mp.weixin.qq.com/s/Hx5pikOZJSkGDMH2VRnbyw">Android Audio Visualization</a></p>
> <p>General workflow: Obtain data source (custom Visualizer) > Process data > Render. Obtaining the data source and processing the data are relatively complex.</p>
> <p>General workflow for obtaining data source: Calculate FFT in the native layer of the playback process, invoke via JNI to callback the calculation results to the Java layer, and then pass the FFT data to the main process via AIDL for subsequent data processing and transmission.</p>
> <p>General workflow for processing data: Calculate index frequency > Select data > Calculate decibels > Convert decibels to height > Smooth data.</p>
> <p>The current stage of the APP only obtains the FFT data from the Android Visualizer, then samples at intervals and finally smooths it; it cannot achieve the same effect as NetEase Cloud Music.</p>

## example

<div align=center >
  <a href="#"><img style="width:200px" src="https://raw.githubusercontent.com/37166121/MusicPlayer/master/example/2023-3-10-1.png"><p>2023-3-10-1.png</p></a>
  <a href="#"><img style="width:200px" src="https://raw.githubusercontent.com/37166121/MusicPlayer/master/example/2023-3-10-2.png"><p>2023-3-10-2.png</p></a>
</div>
