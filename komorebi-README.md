# komorebi 中文用户指南

**该指南并非[原 README](https://github.com/LGUG2Z/komorebi/blob/master/README.md) 的逐字翻译, 我做了一些改编使得中文使用者更易于理解, 同时去掉了部分与使用无关的内容.**

*This guide is not a word by word translation of [original README](https://github.com/LGUG2Z/komorebi/blob/master/README.md). Adaptations are made to make it more readable for Chinese user, and omit some section like contribution. (I assume one who has ability to contribute can read original English README)*

`komorebi` 是又一个 Windows 平铺式窗口管理器, 受 [bspwm](https://github.com/baskerville/bspwm) 所启发.

## 架构

`komorebi` 和 Linux 上的 [bspwm](https://github.com/baskerville/bspwm) 以及
macOS 中的 [yabai](https://github.com/koekeishiya/yabai) 一样, 遵循如此架构设计

```txt
     PROCESS                SOCKET
ahk  -------->  komorebic  <------>  komorebi
```

- `komorebi` 只会回应 [WinEvents](https://docs.microsoft.com/en-us/windows/win32/winauto/event-constants) 和专用 socket 中的消息.
- `komorebic` 是一个往 `komorebi` 的专用 socket 中写入消息的命令行程序
- `komorebi` 和 `komorebic` 都不会直接处理用户的键鼠输入. 需要一个第三方程序来将用户的输入转换为 `komorebic` 的命令. 在此以 AutoHotKey 为例, 但是用其他程序 (如[Python 的 keyboard 库](https://github.com/boppreh/keyboard)) 来调用 `komorebic` 也是完全有可能的.

## 设计

`komorebi` 是 [yatta](https://github.com/LGUG2Z/yatta) 的继任者. `komorebi`最初的设计目标
便是支持其他平台上已经成为平铺式窗口管理器的标准的复杂特性.

- `komorebi` 管理着一串物理监视器 (monitor) 列表.
- 监视器 (monitor) 在此便就是个包含着一个或多个虚拟工作空间 (workspace) 的长方形玩意儿
- 工作空间 (workspace) 包含着一串窗口容器 (containers) 列表
- 窗口容器 (container) 在此就是一个长方形玩意儿, 一个或多个应用程序可以在此显示其窗口界面.

也就是说

- 每个物理显示器都可以有它自己的一套工作空间
- 工作空间仅可以感知到其下的窗口容器, 而非窗口容器下的独立的应用程序窗口
- 每个应用程序窗口都必然属于一个窗口容器, 即使该容器仅包含一个应用程序窗口
- 多个属于同一窗口容器的应用程序窗口会被堆叠 (stacked) 并且可以循环切换

## 如何使用

该教程希望读者对以下概念有着基本掌握

- [PowerShell](https://docs.microsoft.com/en-us/PowerShell/)
- [环境变量](https://zh.wikipedia.org/wiki/%E7%8E%AF%E5%A2%83%E5%8F%98%E9%87%8F)
- [AutoHotkey](https://www.autohotkey.com/)
- [平铺式窗口管理器](https://en.wikipedia.org/wiki/Tiling_window_manager)

如果有不熟悉的概念, 仍然可以往下阅读, 但掌握以上概念可以拥有更好的阅读体验.

### 安装

#### GitHub Release

你可以在 [GitHub Release](https://github.com/LGUG2Z/komorebi/releases) 找到预编译的二进制包. 当然在下载之后你得手动将 `komorebi.exe` 和 `komorebic.exe` 放到你的 `PATH` 环境变量之中. (你可以在 PowerShell 终端中输入 `$Env:Path.split(";")` 来查看当前 `PATH` 环境变量所包含的路径)

你可以用 `setx` 命令或者在 PowerShell 中输入 `SystemPropertiesAdvanced.exe` 打开的 `系统属性` 窗口中点击 `环境变量...` 来设置添加里面有 `komorebic.exe` 和 `komorebi.exe` 的目录. 具体操作请百度/Google, 或参考[如何设置或更改 PATH 系统变量？](https://www.java.com/zh-CN/download/help/path_zh-cn.html), 这里不再赘述.

#### Scoop

如果你使用 [Scoop](https://scoop.sh/) 包管理器的话, 你可以用以下命令来从 GitHub Release 中下载安装 komorebi

```PowerShell
scoop bucket add komorebi https://github.com/LGUG2Z/komorebi-bucket
scoop install komorebi
```

如果使用这种方式安装 komorebi, 那么其二进制文件会自行加入到你的 `PATH` 环境变量中, 并且会提示你使用示例配置文件来开始使用

#### 源码编译

如果你更喜欢从源码编译, 你得有 [Rust Windows 10 开发环境](https://rustup.rs/) 还有 `x86_64-pc-windows-msvc` 工具链, 还得确保你安装了 Build Tools for Visual Studio 2019.

你可以 clone 这个仓库并使用以下命令来编译安装 `komorebi` 和 `komorebic`

```PowerShell
cargo install --path komorebi --locked
cargo install --path komorebic --locked
```

[rustup](https://rustup.rs/) 会自动将编译后的文件添加至 `PATH` 环境变量之中, 无需手动添加环境变量.

### 运行

一旦你将 `komorebi` 和 `komorebic` 添加至 `PATH` 环境变量之中, 你就可以在 PowerShell 终端运行

```PowerShell
komorebic start
```

此时应该会返回

```PowerShell
Start-Process komorebi -WindowStyle hidden
```

这意味着 `kmoorebi` 现在已经在后台正常运行, 管理你的所有窗口, 并监听来自 `komorebic` 的命令. 你可以用

```PowerShell
komorebic stop
```

来停止正在运行的 `komorebi` 进程.

### 配置

#### 直接使用 `komorebic` 进行配置

正如在[之前](#架构)所提到的, 该项目不会直接管理你的键盘绑定, 你需要一个类似 [sxhkd](https://github.com/baskerville/sxhkd) 之类的玩意来辅助管理热键. 官方钦定的管理软件是 [AutoHotkey](https://www.autohotkey.com/), 当然没人拦你选别的.

你可以通过在 PowerShell 直接执行 `komorebic` 来改变 `komorebi` 的状态.

下面是可能的命令列表

```txt
start                         以背景进程运行 komorebi
stop                          停止 komorebi 进程并恢复所有窗口
state                         显示当前窗口管理器的状态
log                           使用 tail 参看 komorebi 的日志
focus                         改变焦点 (后接方向)
move                          移动窗口 (同一工作空间中)
stack                         堆叠窗口
resize                        改变当前窗口大小
unstack                       撤销堆叠
cycle-stack                   循环堆叠中的窗口
move-to-monitor               移动窗口到监视器 (焦点会切换到目标监视器)
move-to-workspace             移动窗口到工作空间 (焦点会切换到目标工作空间)
send-to-monitor               移动窗口到监视器 (焦点保留在当前监视器)
send-to-workspace             移动窗口到工作空间 (焦点保留在当前工作空间)
focus-monitor                 切换聚焦监视器
focus-workspace               切换聚焦工作空间
new-workspace                 新建工作空间
adjust-container-padding      调整窗口容器间距
adjust-workspace-padding      调整工作空间间距
change-layout                 改变工作空间布局
flip-layout                   反转工作空间 (仅能在 BSP 布局使用)
promote                       将当前聚焦窗口移动至最前
retile                        强制工作空间重排
ensure-workspaces             创建指定数量的工作空间
container-padding             指定窗口容器间距
workspace-padding             指定工作空间间距
workspace-layout              指定工作空间布局
workspace-tiling              指定工作空间平铺/浮动
workspace-name                指定工作空间名称
toggle-pause                  暂停 komorebi
toggle-tiling                 切换当前工作空间平铺/浮动
toggle-float                  切换当前窗口平铺/浮动
toggle-monocle                切换单窗口布局
toggle-maximize               切换当前窗口最大化
restore-windows               还原所有窗口
manage                        强制捕捉当前窗口
unmanage                      还原强制捕捉的窗口
reload-configuration          重新加载 ~/komorebi.ahk
watch-configuration           检测 ~/kmorebi.ahk 内容变化自动加载
float-rule                    设立窗口浮动规则
manage-rule                   设立强制捕捉规则
workspace-rule                设立窗口与工作空间关联规则
identify-tray-application     设立任务栏程序标识规则
focus-follows-mouse           设置是否焦点跟随鼠标
toggle-focus-follows-mouse    切换焦点跟随鼠标
ahk-library                   生成 AHK 辅助函数
help                          显示本消息
```

#### 使用 AutoHotKey 辅助配置

一旦 `komorebi` 开始运行，你就可以用 AutoHotKey 执行 `komorebi.sample.ahk` 设置默认的按键绑定（该文件包含了能帮助你构建你自己的配置文件的注释）

你也可以在 [这里](https://github.com/LGUG2Z/komorebi/blob/master/komorebi.sample.ahk) 找到一份 AutoHotKey v1 的示例配置文件.

如果你已经安装了 AutoHotKey 并且有 `komorebi.ahk` 在你的 `UserProfile` 环境变量指向目录（你可以在PowerShell运行 `$Env:UserProfile` 来查看该目录所在位置），`komorebi` 会在启动时自动装载它

komorebi 还搭载了暂时的 [AutoHotkey v2](https://www.autohotkey.com/v2/) 支持，如果有 `komorebi.ahk2` 存在于 `UserProfile` 环境变量所指向目录之中, 以及 AutoHotkey v2 版本的 `AutoHotKey64.exe` 存在于 `PATH` 环境变量. 那么 `komorebi` 就会尝试在启动时加载它. 你可 以[在这里](https://gist.github.com/crosstyan/dafacc0778dabf693ce9236c57b201cd)找到一份 AutoHotkey v2 的示例配置文件

如果 `komorebi.ahk2` 和 `komorebi.ahk` 两者都存在，只有 `komorebi.ahk` 的配置文件会被载入，也就是说 AutoHotkey v1 的配置文件的优先级会更高.

## 常见新手问题

### 浮动窗口

有时你不想让一个窗口被平铺，你可以用

```PowerShell
komorebic toggle-float
```

来切换当前聚焦的窗口是否被平铺。但是当你想设定规则来使某一类窗口永远不平铺时，你需要 `float-rule`

```PowerShell
 komorebic.exe float-rule title "Control Panel"
# komorebic.exe float-rule exe [EXE 可执行文件名]
# komorebic.exe float-rule class [窗口类名]
# komorebic.exe float-rule title [窗口标题]
```

### 未被捕获的窗口

虽然 `komorebi` 可以捕获大部分窗口，但对于某些国产软件（像是 QQ，TIM或者是微信）它仍然需要用户手动指定规则才能正确捕获

```PowerShell
komorebic.exe manage-rule exe TIM.exe
# komorebic.exe manage-rule exe [EXE 可执行文件名]
# komorebic.exe manage-rule class [窗口类名]
# komorebic.exe manage-rule title [窗口标题]
```

### 任务栏通知区域应用

如果你在关闭应用程序窗口时出现[关闭窗口后仍留下空白的情况](https://github.com/LGUG2Z/komorebi/issues/6), 那么可能是因为该应用程序启用了「关闭时最小化到任务栏」(如各类聊天软件如QQ, Telegram, Discord 或是微信; 音乐软件如网易云, Spotify). `komorebi` 需要用户手动指定规则来正确地处理这样的应用程序

```PowerShell
komorebic.exe identify-tray-application exe Discord.exe
# komorebic.exe identify-tray-application exe [EXE 可执行文件名]
# komorebic.exe identify-tray-application class [窗口类名]
# komorebic.exe identify-tray-application title [窗口标题]
```

## 日志和除错

`komorebi` 的日志会放在 `~/komorebi.log` (`~` 是 `$env:USERPROFILE` 的缩写). 该文件永远不会被复写或是[轮替](https://zh.wikipedia.org/wiki/%E6%97%A5%E5%BF%97%E8%BD%AE%E6%9B%BF), 所以该文件会一直增长直到用户手动删除.

### 恢复窗口

理论上来说当运行 `komorebic stop` 或者 `Ctrl-C` 信号发送给 `komorebi` 的时候, `komorebi` 会恢复所有隐藏的窗口. 但如果有某些窗口被隐藏了且无法被自动恢复, 运行 `komorebic restore-windows` 会让 `komorebi` 读取存放所有被`komorebi`管理的窗口的窗口句柄的文件 `~/komorebi.hwnd.json`, 不管 `komorebi` 主进程是否正在运行.

### 恐慌和死锁

**恐慌 (panic) 在此指的是严重错误, 下译为「恐慌」.**

如果 `komorebi` 停止响应, 原因很可能是恐慌或者是死锁. 如果是恐慌的话程序的日志会有记录; 但若是死锁的话日志不会有任何错误记录, 但是整个进程却停止响应了.

若你觉得你遭遇了死锁, 你可以用 `--features deadlock_detection` 来编译 `komorebi` 然后尝试重现此问题. 该特性会每隔五秒检查是否发生了死锁, 若有死锁发生则会在日志中写入相关信息, 你可以拿着它去开 issue.
