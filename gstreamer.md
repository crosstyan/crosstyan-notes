# GStreamer

- <https://github.com/thaytan/gst-tutorial-lca2018/blob/master/gstreamer-tutorial-lca2k18.pdf>
- <https://www.youtube.com/watch?v=ZphadMGufY8>
- <https://elinux.org/images/3/39/Celinux-gst-tutorial.pdf>
- <https://riptutorial.com/gstreamer/example/28136/saving-application-generated-media-to-file>
- <https://www.youtube.com/watch?v=MCRKfXipAkU>
- <http://www.ulduzsoft.com/2016/06/qtmultimedia-ffmpeg-gstreamer-comparing-multimedia-frameworks/>
- <https://docs.opencv.org/3.4/d4/d15/group__videoio__flags__base.html#ga023786be1ee68a9105bf2e48c700294d>

| Enumerator                                   | Description
|----------------------------------------------|---------------------------------------------------------------------|
| CAP_VFW Python: cv.CAP_VFW                   | Video For Windows (platform native)
| CAP_V4L Python: cv.CAP_V4L                   | V4L/V4L2 capturing support via libv4l.
| CAP_V4L2 Python: cv.CAP_V4L2                 | Same as CAP_V4L.
| CAP_FIREWIRE Python: cv.CAP_FIREWIRE         | IEEE 1394 drivers.
| CAP_FIREWARE Python: cv.CAP_FIREWARE         | Same as CAP_FIREWIRE.
| CAP_IEEE1394 Python: cv.CAP_IEEE1394         | Same as CAP_FIREWIRE.
| CAP_DC1394 Python: cv.CAP_DC1394             | Same as CAP_FIREWIRE.
| CAP_CMU1394 Python: cv.CAP_CMU1394           | Same as CAP_FIREWIRE.
| CAP_QT Python: cv.CAP_QT                     | QuickTime.
| CAP_UNICAP Python: cv.CAP_UNICAP             | Unicap drivers.
| CAP_DSHOW Python: cv.CAP_DSHOW               | DirectShow (via videoInput)
| CAP_PVAPI Python: cv.CAP_PVAPI               | PvAPI, Prosilica GigE SDK.
| CAP_OPENNI Python: cv.CAP_OPENNI             | OpenNI (for Kinect)
| CAP_OPENNI_ASUS Python: cv.CAP_OPENNI_ASUS   | OpenNI (for Asus Xtion)
| CAP_ANDROID Python: cv.CAP_ANDROID           | Android - not used.
| CAP_XIAPI Python: cv.CAP_XIAPI               | XIMEA Camera API.
| CAP_AVFOUNDATION Python: cv.CAP_AVFOUNDATION | AVFoundation framework for iOS (OS X Lion will have the same API)
| CAP_GIGANETIX Python: cv.CAP_GIGANETIX       | Smartek Giganetix GigEVisionSDK.
| CAP_MSMF Python: cv.CAP_MSMF                 | Microsoft Media Foundation (via videoInput)
| CAP_WINRT Python: cv.CAP_WINRT               | Microsoft Windows Runtime using Media Foundation.
| CAP_INTELPERC Python: cv.CAP_INTELPERC       | Intel Perceptual Computing SDK.
| CAP_OPENNI2 Python: cv.CAP_OPENNI2           | OpenNI2 (for Kinect)
| CAP_OPENNI2_ASUS Python: cv.CAP_OPENNI2_ASUS | OpenNI2 (for Asus Xtion and Occipital Structure sensors)
| CAP_GPHOTO2 Python: cv.CAP_GPHOTO2           | gPhoto2 connection
| CAP_GSTREAMER Python: cv.CAP_GSTREAMER       | GStreamer.
| CAP_FFMPEG Python: cv.CAP_FFMPEG             | Open and record video file or stream using the FFMPEG library.
| CAP_IMAGES Python: cv.CAP_IMAGES             | OpenCV Image Sequence (e.g. img_%02d.jpg)
| CAP_ARAVIS Python: cv.CAP_ARAVIS             | Aravis SDK.
| CAP_OPENCV_MJPEG Python: cv.CAP_OPENCV_MJPEG | Built-in OpenCV MotionJPEG codec.
| CAP_INTEL_MFX Python: cv.CAP_INTEL_MFX       | Intel MediaSDK.
| CAP_XINE Python: cv.CAP_XINE                 | XINE engine (Linux)

Bins containing Elements, linked by Pads. Top level Bin is Pipeline.

Pad 源自电子元器件的「焊点」

Data flows in Buffers. Negotiate by Events.

GStreamer Core is not aware of the content of data. Media Agnostic

Element/Pad is not the same.

Request Pads

IC on the PCB.

Source pad links to sink pad, with common format.

## Caps

Video format description.

Media Type + Optional Fields = Structure

Fixed Cap.

Write caps between two !.
Use the capsfilter element and set the caps property on it.

## Type Find

gst-typefind-1.0

## ASCII Arts

- <https://asciiflow.com/>
- <https://textik.com/>
- <https://datatracker.ietf.org/doc/html/rfc8140>
- <http://wiki.c2.com/?UmlAsciiArt>
- <https://metacpan.org/pod/Graph::Easy>
- <https://stackoverflow.com/questions/123378/command-line-unix-ascii-based-charting-plotting-tool>
- <https://www.luismg.com/protocol/>
- <https://news.ycombinator.com/item?id=18267275>

## OpenCV

- <https://zhuanlan.zhihu.com/p/377407799>
