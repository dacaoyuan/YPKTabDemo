
![](https://img.shields.io/badge/release-v1.2.4-brightgreen.svg)


<div align=center>

![](https://img-blog.csdnimg.cn/6df7515ff2324960b4317b99faa9af92.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZGFfY2FveXVhbg==,size_13,color_FFFFFF,t_70,g_se,x_16)

</div>


## 远程依赖
Add it in your root build.gradle at the end of repositories:

```
allprojects {

	repositories {
		...
		maven { url 'https://jitpack.io' }
	}

}

```

Add the dependency
```
dependencies {

	  implementation 'com.github.dacaoyuan:YPKTabDemo:1.2.4'

}

```


## 使用方法
一：在xml布局中添加：

kotlin
```
 <com.ypk.library.view.YPKTabLayoutView
        android:id="@+id/mYPKTabLayoutView"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_margin="10dp"
        app:arcControlX="30"
        app:tabTextColor="#fff"
        app:tabSelectTextColor="#fff"
        app:tabTextSize="15sp"
        app:view_bg_corners="0dp" />

```

java：
```
  <com.ypk.library.view.YPKTabLayoutViewJava
        android:id="@+id/mYPKTabLayoutView4"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_margin="10dp"
        app:arcControlX="30"
        app:show_indicator_select="true"
        app:tabSelectTextColor="@color/colorAccent"
        app:tabTextColor="@color/text_33"
        app:tab_indicator_height="1dp"
        app:tab_indicator_spacing="5dp" />

```


二：activity中：
```

val tabTextList: MutableList<String> = ArrayList<String>()

        tabTextList.add("推荐学习");
        tabTextList.add("企业学院");
        tabTextList.add("我的关注");
        mYPKTabLayoutView.setTabTextList(tabTextList);

       mYPKTabLayoutView.addTabSelectedListener(object : OnTabClickListener {
                   override fun tabSelectedListener(tabPosition: Int) {
                       val makeText =
                           Toast.makeText(
                               this@MainActivity,
                               "点击了第" + tabPosition + "项",
                               Toast.LENGTH_SHORT
                           )
                       makeText.setGravity(Gravity.CENTER, 0, 0);
                       makeText.show();
                   }
         })

```

### 属性说明：
属性     | 说明
-------- | -----
tabTextColor| tab的文字颜色
tabSelectTextColor| tab的文字选中颜色
tabTextSize| tab文字大小（单位：sp）
tab_view_bg| 控件背景色
select_tab_color| 选中后tab的背景色
arcControlX| 值越大，曲线弧度越大
view_bg_corners| 控件的圆角大小（单位：dp）
show_indicator_select| 是否只有选中才显示指示线
show_indicator| 是否显示指示线
tab_indicator_height| 指示线的高度（单位：dp）
tab_indicator_width| 指示线的宽度（单位：dp）
tab_indicator_spacing| 指示线的与文字的间隔（单位：dp）




## 版本更新说明：
### 1.2.4
1：添加了指示线的宽度设置

### 1.2.3
1：为了兼容有些项目没有使用kotlin语言，完善了java实现类，同时支持kotlin和java。

### 1.2.1
1：新增了指示器显示。

### 1.2.0
1：特别说明自1.2.0版本开始，代码已改为kotlin语言。


### 1.0.6
1：新增了设置默认选中tab项的功能

### 1.0.5
1：支持了tab文本字体样式
2：支持了文本选中颜色设置

## 实现思路详解
csdn地址：https://blog.csdn.net/da_caoyuan/article/details/106332632