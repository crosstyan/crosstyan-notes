# Cheat Sheet

old hci tools has been deprecated. See the section *Deprecated BlueZ tools* of [Bluetooth](https://wiki.archlinux.org/title/bluetooth)

`bluetoothctl` is your friend.

```bash
btmon -w /tmp/dump
```

```text
$ bluetoothctl
[bluetooth]# menu scan
[bluetooth]# clear
[bluetooth]# transport le
[bluetooth]# duplicated-data on
[bluetooth]# back
[bluetooth]# scan on
```

Use [Wireshark](https://www.wireshark.org/) to decode the Bluetooth HCI protocol.

Constants are defined in [hci.h](https://github.com/bluez/bluez/blob/838741b884d9c43ab0099c54605462af252bbc5b/lib/hci.h) and its [Python Binding](https://github.com/pybluez/pybluez/blob/5096047f90a1f6a74ceb250aef6243e144170f92/bluez/btmodule.c). (They are just actual implementations of Bluetooth HCI protocol, including the magic numbers)

- EVT_LE_META_EVENT	0x3E
- HCI_EVENT_PKT		0x04

Want more information? See Core Specification (CS), Core Specification Supplement (CSS) in [Specifications and Test Documents List](https://www.bluetooth.com/specifications/specs/) from Bluetooth, See also [Assigned Number](https://www.bluetooth.com/specifications/assigned-numbers/) for magic numbers.

- [HCI Interface](https://software-dl.ti.com/simplelink/esd/simplelink_cc13x2_sdk/1.60.00.29_new/exports/docs/ble5stack/vendor_specific_guide/BLE_Vendor_Specific_HCI_Guide/hci_interface.html)

## Eddystone

[Eddystone Protocol Specification](https://github.com/google/eddystone/blob/master/protocol-specification.md)

[EDDYSTONE DATA FORMAT](https://www.global-tag.com/wp-content/uploads/2019/04/Beacony_Parameter_Appendix_Eddistone_Data_Format.pdf)

The first AD Type(Advertising Data Type) i.e. Flag can be omited.

> The Flags data type shall
be included when any of the Flag bits are non-zero and the advertising packet
is connectable, otherwise the Flags data type may be omitted.

[Beacon Simulator](https://play.google.com/store/apps/details?id=net.alea.beaconsimulator&hl=zh&gl=US) can be used
to simulate a Eddystone or iBeacon device.

- [EddyStone基本功能涵蓋完整　Beacon應用開發一把罩 | 新通訊](https://telegra.ph/EddyStone%E5%9F%BA%E6%9C%AC%E5%8A%9F%E8%83%BD%E6%B6%B5%E8%93%8B%E5%AE%8C%E6%95%B4-Beacon%E6%87%89%E7%94%A8%E9%96%8B%E7%99%BC%E4%B8%80%E6%8A%8A%E7%BD%A9--%E6%96%B0%E9%80%9A%E8%A8%8A-06-08) (See [Original Post](https://www.2cm.com.tw/2cm/zh-tw/tech/F52E9B8BE48B418AAEDCD38191B21938))
