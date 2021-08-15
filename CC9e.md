# CC9e convert to A3

You can convert your CC9e to A3 (by `abl.edf`)

The bootloader will be **locked** after converting, I mean **LOCKED**. THINK TWICE. Thanks to @jerryyin for testing.

Attention! After switching to the official firmware Android 11, you can't flash back to Android 10 because of rollback protection. If you do you'll get a brick!

1. Download an **fastboot** ROM from **A3**, you can find one in [here](https://xiaomifirmwareupdater.com/archive/miui/laurel/). Decompress it.
2. Download an **fastboot** ROM from **CC9e**, you can find one in [here](https://xiaomifirmwareupdater.com/archive/firmware/laurus/). Decompress it and find the `abl.edf` there. You can download `abl.edf` from [here](https://yinqihao-my.sharepoint.com/personal/1915195935_yinqihao_onmicrosoft_com/_layouts/15/onedrive.aspx?id=%2Fpersonal%2F1915195935%5Fyinqihao%5Fonmicrosoft%5Fcom%2FDocuments%2Fcc9e%5Fedl%5Ffile&originalPath=aHR0cHM6Ly95aW5xaWhhby1teS5zaGFyZXBvaW50LmNvbS86ZjovZy9wZXJzb25hbC8xOTE1MTk1OTM1X3lpbnFpaGFvX29ubWljcm9zb2Z0X2NvbS9FclY3aUI4dFFsZEtzYUdxZk8yTGJxa0I4Z01KSEdUMGJFcDM1SnhMdWZhODZnP3J0aW1lPVlpQ010MlphMlVn)
3. Override the `abl.edf` and `prog_firehost_ddr.elf` in A3 folder by the one from CC9e.
4. Enter **fastboot** mode of your CC9e
5. Run `fastboot oem edl` in your terminal.
6. Use MiFlash tool from [here](https://yinqihao-my.sharepoint.com/personal/1915195935_yinqihao_onmicrosoft_com/_layouts/15/onedrive.aspx?id=%2Fpersonal%2F1915195935%5Fyinqihao%5Fonmicrosoft%5Fcom%2FDocuments%2Fcc9e%5Fedl%5Ffile&originalPath=aHR0cHM6Ly95aW5xaWhhby1teS5zaGFyZXBvaW50LmNvbS86ZjovZy9wZXJzb25hbC8xOTE1MTk1OTM1X3lpbnFpaGFvX29ubWljcm9zb2Z0X2NvbS9FclY3aUI4dFFsZEtzYUdxZk8yTGJxa0I4Z01KSEdUMGJFcDM1SnhMdWZhODZnP3J0aW1lPVlpQ010MlphMlVn) and flash it.
7. Reboot and hope it won't be a brick.

References

- [Mi A3 to CC9e (Russian)](https://4pda.to/forum/index.php?showtopic=962408&st=12740#entry104144712)
- [Anti-Rollback explained](https://www.xda-developers.com/xiaomi-anti-rollback-protection-brick-phone/)
- [Tutorial on Flashing Android 11 from A3 on CC9e and entering 9008 (Chinese)](https://www.coolapk.com/feed/28624511?shareKey=NjIzNmNiY2U4NDkxNjEwZmIzOGQ~&shareUid=3547471&shareFrom=com.coolapk.market_11.3)
- [Exploiting Qualcomm EDL Programmers (1): Gaining Access & PBL Internals](https://alephsecurity.com/2018/01/22/qualcomm-edl-1/)
- [openpst/sahara](https://github.com/openpst/sahara)
- [bkerler/edl](https://github.com/bkerler/edl)
- [关于高通 9008 刷机的研究](https://blog.omitol.com/2015/09/01/about-qcom-edl-dload-study/)
- [Verified Boot](https://source.android.com/security/verifiedboot)
- [Android Verified Boot 2.0](https://android.googlesource.com/platform/external/avb/+/master/README.md)
