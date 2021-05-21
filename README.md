**1.****系统分析**

**1.1**  **问题描述**

随着数码相机的普及，越来越多的⼈拥有⼤量的数字相片。人们迫切需要一款优秀、便捷的电子图片管理程序。

 

**1.2**  **系统功能分析**

**1.2.1****必须实现的功能**

• 目录树功能

• 图片选择功能（包括单张和多张图片的选择，选中方式有两种：鼠标框中和ctrl+鼠标右键）

• 图片预览功能（支持jpg, jpeg, bmp, gif, png 等格式）

• 图片放大缩小功能

• 图片删除功能

• 图片复制功能

• 图片粘贴功能

• 图片重命名功能（多选状态下，图片命名由“用户命名”+ 递增的序号组成）

• 取消选中功能

• 右键菜单功能

**1.2.2****额外实现功能**

• 添加额外右键菜单及菜单快捷键

**1.3**  **开发平台及工具介绍**

**1.3.1****开发平台**

Windows10平台

**1.3.2****开发工具**

1.Eclipse

Eclipse 是一个开放[源代码](https://baike.baidu.com/item/源代码/3969)的、基于[Java](https://baike.baidu.com/item/Java/85979)的可扩展开发平台。就其本身而言，它只是一个框架和一组服务，用于通过插件组件构建开发环境。幸运的是，Eclipse 附带了一个标准的插件集，包括Java[开发工具](https://baike.baidu.com/item/开发工具)（Java Development Kit，[JDK](https://baike.baidu.com/item/JDK/1011)）

2.IDEA

IDEA 全称 IntelliJ IDEA，是java编程语言开发的集成环境。IntelliJ在业界被公认为最好的java开发工具，尤其在智能代码助手、代码自动提示、重构、JavaEE支持、各类版本工具([git](https://baike.baidu.com/item/git/12647237)、[svn](https://baike.baidu.com/item/svn/3311103)等)、JUnit、CVS整合、代码分析、 创新的GUI设计等方面的功能可以说是超常的。IDEA是[JetBrains](https://baike.baidu.com/item/JetBrains/7502758)公司的产品，这家公司总部位于[捷克共和国](https://baike.baidu.com/item/捷克共和国/418555)的首都[布拉格](https://baike.baidu.com/item/布拉格/632)，开发人员以严谨著称的东欧程序员为主。它的旗舰版本还支持HTML，CSS，PHP，MySQL，Python等。免费版只支持Java, Kotlin等少数语言。

3.SceneBuilder

  JavaFX Scene Builder是一种可视布局工具，允许用户快速设计JavaFX应用程序用户界面，而无需编码。用户可以将UI组件拖放到工作区，修改其属性，应用样式表，并且它们正在创建的布局的FXML代码将在后台自动生成。它的结果是一个FXML文件，然后可以通过绑定到应用程序的逻辑与Java项目组合

**2****系统设计**

**2.1****系统总体结构设计**

本程序采用MVC模式，进行整体项目的构建

**2.1.1 MVC****模式简介**

模型-视图-控制器模式，也称为MVC模式（Model View Controller）。用一种业务逻辑、数据、界面显示分离的方法组织代码，将业务逻辑聚集到一个部件里面，在改进和个性化定制界面及用户交互的同时，不需要重新编写业务逻辑。MVC被独特的发展起来用于映射传统的输入、处理和输出功能在一个逻辑的图形化用户界面的结构中。它把软件系统分为三个基本部分：

①模型（Model）：负责存储系统的中心数据。

②视图（View）：将信息显示给用户（可以定义多个视图）。

③控制器（Controller）：处理用户输入的信息。负责从视图读取数据，控制用户输入，并向模型发送数据，是应用程序中处理用户交互的部分。负责管理与处理用户交互。

**![img](file:///C:/Users/Platina/AppData/Local/Temp/msohtmlclip1/01/clip_image002.jpg)**

**2.1.2** **项目结构设计**

① modules包

·Data 用于存放程序之中会反复用到的数据结构

·ImageNode 模型对VBox类进行拓展，包含图片显示和图片名字显示等

·MyContextMenu 类定义了右键菜单，根据参数类型创建对应的右键菜单

·Popus 类用于创建弹窗

·Slide 类用于处理幻灯片播放界面，加载必要的数据

② layout 包

借助SceneBuilder进行界面构建，自动生成fxml文件

·MainUI.fxml 主界面设计，大致框架设计

·SlideUI.fxml 幻灯片播放界面设计，设置事件方法

③ controller 包

·MainLayoutController MainUI.fxml的控制器，初始化主界面数据，进行一些布局属性绑定，添加事件监听等

·SlideLayoutController SlideUI.fxml的控制器，设计幻灯片播放界面的按钮的点击事件，进行一些事件预处理

④ handler 包

为精简结构，对各类事件的监控事件进行分类封装，增强程序可读性

·DeleteEventHandler 封装了删除操作过程中使用的函数，以及弹窗的信息

·FlowPaneEventHandler 封装了流式布局的监听事件

·ImageNodeEventHandler

·LoadlmageNode

·PasteEventHandler

·RenameEventHandler

·TreeViewListener

⑤ main 包

程序入口，加载主界面以及进行数据初始化和布局处理。

 

**2.2** **系统各个类及类之间的关系设计**

**![img](file:///C:/Users/Platina/AppData/Local/Temp/msohtmlclip1/01/clip_image004.jpg)**

 

**2.3** **数据储存的设计**

仅采用ArrayList来存储目录之中的图片节点和复制选中的文件，以及采用一些数组结构，未使用其它复杂存储结构

**2.4** **界面设计**

使用SceneBuilder设计主界面，细节完善在函数之中完成

**![img](file:///C:/Users/Platina/AppData/Local/Temp/msohtmlclip1/01/clip_image006.gif)**

 

**3** **系统实现**

**3.1.1** **目录树加载**

对节点展开事件进行监听，如果展开的节点下面的子节点还未添加更深一层的子节点，则调用addSubItem 函数为该节点下面的子节点添加更深一层的子节点。进行判断是否已经加载可以避免大部分重复加载，提高程序运行速率；同时在节点展开后才开始加载更深一层的子节点，达到每次只加载两层子目录的需求，不进行全盘扫描添加，节省了加载目录时间，大大提升了用户体验。

**![img](file:///C:/Users/Platina/AppData/Local/Temp/msohtmlclip1/01/clip_image008.gif)**

代码片段![img](file:///C:/Users/Platina/AppData/Local/Temp/msohtmlclip1/01/clip_image010.gif)

思维导图

**3.1.2** **加载图片节点**

当切换目录树选中节点时，记录当前节点，初始化数据，判断当前加载图片任务是否在运行，如果在运行就取消任务。没有任务或者任务被取消了以及任务完成时进入加载图片过程。遍历当前节点对应目录下的文件，是图片文件则用它创建图片节点，在Platform.runLater之中添加图片节点到预览区域的流式布局。任务完成后再更新数据提示区域。

![img](file:///C:/Users/Platina/AppData/Local/Temp/msohtmlclip1/01/clip_image012.gif)

代码片段

![img](file:///C:/Users/Platina/AppData/Local/Temp/msohtmlclip1/01/clip_image014.gif)

思维导图

**3.1.3** **右键菜单创建**

根据参数判断是否是在图片节点上新建右键菜单，根据参数创建对应菜单。在图片节点上添加删除、重命名、复制等右键菜单，同时设置这些菜单点击事件，并添加常用快捷键，空白处同理设置全选及粘贴等右键菜单。

![img](file:///C:/Users/Platina/AppData/Local/Temp/msohtmlclip1/01/clip_image016.gif)

代码片段

![img](file:///C:/Users/Platina/AppData/Local/Temp/msohtmlclip1/01/clip_image018.gif)

思维导图

**3.1.4** **删除功能实现**

点击删除后，弹出确认窗口，确认后使用Files.delete方法尝试进行删除，删除失败则显示错误信息弹窗，成功则提示删除成功

![img](file:///C:/Users/Platina/AppData/Local/Temp/msohtmlclip1/01/clip_image020.gif)

代码片段

 

**3.1.5** **复制功能实现**

因为采用了Data 类专门存放常用数据的这种模式，所以复制功能实现较为简单，只需要将选中节点数组的内容复制到复制数组即可。首先判断是否已经选中图片，是则进行复制操作，复制成功弹窗提示，否则提示未选中图片

![img](file:///C:/Users/Platina/AppData/Local/Temp/msohtmlclip1/01/clip_image022.gif)

代码片段

![img](file:///C:/Users/Platina/AppData/Local/Temp/msohtmlclip1/01/clip_image024.gif)

思维导图

**3.1.6** **重命名功能**

进行正式重命名之前进行两次判断，首先判断是否已经选中图片，未选择直接提示未选中图片。选中图片再判断选中图片数量，确定是进行单个图片重命名还是多个图片重命名，分别进入不同界面。再判断输入合法性，输入合法之后，尝试进行重命名，成功则显示成功提示，失败则显示错误弹窗，结束重命名过程。

**![img](file:///C:/Users/Platina/AppData/Local/Temp/msohtmlclip1/01/clip_image026.jpg)**

代码片段

![img](file:///C:/Users/Platina/AppData/Local/Temp/msohtmlclip1/01/clip_image028.gif)

思维导图

**3.1.7** **图片选中功能实现**

·鼠标多选操作方式，在“图片预览”区域按住鼠标左键，拖动鼠标形成一个矩形区域，位于该矩形区域中缩略图被选中。

对流式布局添加监听事件，按下鼠标是记录初始坐标，初始化选择矩形，进行拖拽后，进行坐标计算比较，判断图片节点是否与选中矩形相交，是则添加入选中图片列表，并进行相应处理

![img](file:///C:/Users/Platina/AppData/Local/Temp/msohtmlclip1/01/clip_image030.gif)

代码片段

![img](file:///C:/Users/Platina/AppData/Local/Temp/msohtmlclip1/01/clip_image032.gif)

思维导图

·鼠标+键盘多选操作方式，按住键盘的“ctrl”键，鼠标左键点击缩略图，在放开“ctrl”键之前所点击的图片全部被选中。

![img](file:///C:/Users/Platina/AppData/Local/Temp/msohtmlclip1/01/clip_image034.gif)

代码片段

![img](file:///C:/Users/Platina/AppData/Local/Temp/msohtmlclip1/01/clip_image036.gif)

思维导图

**3.1.8** **粘贴功能实现**

粘贴功能实现主要是需要解决名字重复的问题。这里我们按照Windows资源管理器的处理方式进行处理，即先加“– 副本“，后面“– 副本(2)、– 副本(3)“依次类推。先进行判断复制和粘贴是不是在同一个目录，是则先加“– 副本“，否则不加，后面判断是否重复，再进行循环处理，直到该文件名不存在，再使用缓存输入输出流进行复制文件。复制成功则提示成功，否则显示错误弹窗。

![img](file:///C:/Users/Platina/AppData/Local/Temp/msohtmlclip1/01/clip_image038.gif)

代码片段

![img](file:///C:/Users/Platina/AppData/Local/Temp/msohtmlclip1/01/clip_image040.gif)

思维导图

**3.1.9** **幻灯片播放界面**

利用KeyFrame设置事件，借助时间轴进行动画播放。使用SceneBuilder设计界面，在控制器类SlideLayoutController 里面初始化一列按钮的图标以及点击事件。设计了循环播放，当到目录最后时，下一次播放第一张图片。另外进行了一些细节的优化，巧妙利用数组进行循环添加图标和悬浮提示，优化代码结构，精简重复代码。以及设置在播放幻灯片过程之中缩放按钮不可选中，保证播放过程的美观性。

![img](file:///C:/Users/Platina/AppData/Local/Temp/msohtmlclip1/01/clip_image042.gif)

![img](file:///C:/Users/Platina/AppData/Local/Temp/msohtmlclip1/01/clip_image044.gif)

代码片段

**4** **系统测试**

**4.1** **图片文件预览界面功能测试**

**4.1.1** **目录树显示及目录图片文件缩略图加载**

**![img](file:///C:/Users/Platina/AppData/Local/Temp/msohtmlclip1/01/clip_image046.gif)**

支持规定的图片格式：.jpg、.gif、.png、.jpeg、.bmp

目录树实现且操作正常、缩略图包含图片、文件名且保持比例、点击目录显示缩略图及个数

缩略图单选多选，鼠标拖拽矩形选中缩略图，在右下角显示选中图片个数。

测试完毕，功能实现

 

**4.1.2** **缩略图删除功能测试**

![img](file:///C:/Users/Platina/AppData/Local/Temp/msohtmlclip1/01/clip_image048.gif)

确认删除缩略图弹窗

![img](file:///C:/Users/Platina/AppData/Local/Temp/msohtmlclip1/01/clip_image050.gif)

错误弹窗

![img](file:///C:/Users/Platina/AppData/Local/Temp/msohtmlclip1/01/clip_image052.gif)

删除成功弹窗

测试完成，测试结果与预期结果相符，在删除时出现确认弹窗，删除失败显示错误弹窗，删除成功显示成功提示。删除功能测试完毕。

 

**4.1.3** **缩略图复制粘贴功能测试**

![img](file:///C:/Users/Platina/AppData/Local/Temp/msohtmlclip1/01/clip_image054.gif)

复制成功提示

 

![img](file:///C:/Users/Platina/AppData/Local/Temp/msohtmlclip1/01/clip_image056.gif)

粘贴失败错误提示

 

![img](file:///C:/Users/Platina/AppData/Local/Temp/msohtmlclip1/01/clip_image058.gif)

粘贴成功提示

测试完成，测试结果与预期结果相符，在复制成功出现成功提示弹窗，粘贴失败显示错误弹窗，粘贴成功显示成功提示。复制粘贴功能测试完毕。

 

 

**4.1.4** **缩略图单个重命名**

![img](file:///C:/Users/Platina/AppData/Local/Temp/msohtmlclip1/01/clip_image060.gif)

单个文件重命名弹窗

![img](file:///C:/Users/Platina/AppData/Local/Temp/msohtmlclip1/01/clip_image062.gif)

非法输入提示

![img](file:///C:/Users/Platina/AppData/Local/Temp/msohtmlclip1/01/clip_image064.gif)

重命名失败错误弹窗

![img](file:///C:/Users/Platina/AppData/Local/Temp/msohtmlclip1/01/clip_image066.gif)

重命名成功提示弹窗

测试完成，测试结果与预期结果相符，在选中单个文件时进行重命名出现弹窗，非法输入提示非法输入，要求重新输入。重命名失败显示错误弹窗，重命名成功显示成功提示。单个文件重命名功能测试完毕。

**4.1.5** **缩略图多个重命名**

![img](file:///C:/Users/Platina/AppData/Local/Temp/msohtmlclip1/01/clip_image068.gif)

多个文件重命名弹窗

![img](file:///C:/Users/Platina/AppData/Local/Temp/msohtmlclip1/01/clip_image070.gif)

非法输入提示

![img](file:///C:/Users/Platina/AppData/Local/Temp/msohtmlclip1/01/clip_image072.gif)

重命名失败错误弹窗

![img](file:///C:/Users/Platina/AppData/Local/Temp/msohtmlclip1/01/clip_image074.gif)

重命名成功提示弹窗

测试完成，测试结果与预期结果相符，在选中多个文件时进行重命名出现弹窗，非法输入提示非法输入，要求重新输入。重命名失败显示错误弹窗，重命名成功显示成功提示。多个文件重命名功能测试完毕。

**4.2** **幻灯片播放界面功能测试**

**4.2.1** **图片展示的图片切换及放大缩小**

**![img](file:///C:/Users/Platina/AppData/Local/Temp/msohtmlclip1/01/clip_image076.gif)**

第一张图片

![img](file:///C:/Users/Platina/AppData/Local/Temp/msohtmlclip1/01/clip_image078.gif)

图片切换

![img](file:///C:/Users/Platina/AppData/Local/Temp/msohtmlclip1/01/clip_image080.gif)

图片放大

![img](file:///C:/Users/Platina/AppData/Local/Temp/msohtmlclip1/01/clip_image082.gif)

图片缩小

测试完成，测试结果与预期结果相符，切换至第一张图片时出现提示，可以切换图片，可以对图片进行缩放。功能测试完毕。

**4.2.1** **幻灯片播放**

**![img](file:///C:/Users/Platina/AppData/Local/Temp/msohtmlclip1/01/clip_image084.gif)**

幻灯片播放界面

测试完成，测试结果与预期结果相符。点击按钮进行幻灯片播放时屏蔽缩放按钮，按照预定1.5s一张图片的顺序进行播放，幻灯片播放功能测试完毕。

 

**4.3** **额外功能测试**

**4.3.1** **额外右键菜单及快捷键的添加**

![img](file:///C:/Users/Platina/AppData/Local/Temp/msohtmlclip1/01/clip_image086.jpg)

图片节点右键菜单

![img](file:///C:/Users/Platina/AppData/Local/Temp/msohtmlclip1/01/clip_image088.jpg)

空白处右键菜单

测试完成，测试结果与预期结果相符。在图片节点或者空白处处右键出现菜单，同时后面附带快捷键指示。功能测试完毕。

**5** **系统运行界面**

![img](file:///C:/Users/Platina/AppData/Local/Temp/msohtmlclip1/01/clip_image090.gif)

主界面

![img](file:///C:/Users/Platina/AppData/Local/Temp/msohtmlclip1/01/clip_image092.gif)

幻灯片播放界面