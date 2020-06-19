
![](https://img.shields.io/badge/release-v1.0.2-brightgreen.svg)


<div align=center>

![](https://img-blog.csdnimg.cn/20200613111419278.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2RhX2Nhb3l1YW4=,size_16,color_FFFFFF,t_70)

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

	  implementation 'com.github.dacaoyuan:YPKTabDemo:1.0.2'

}

```


## 使用方法
一：在xml布局中添加：
```
 <com.ypk.library.view.YPKTabLayoutView
        android:id="@+id/mYPKTabLayoutView"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_margin="10dp"
        app:arcControlX="30"
        app:tabTextColor="#fff"
        app:tabTextSize="15sp"
        app:view_bg_corners="0dp" />

```


二：activity中：
```

val tabTextList: MutableList<String> = ArrayList<String>()

        tabTextList.add("推荐学习");
        tabTextList.add("企业学院");
        tabTextList.add("我的关注");
        mYPKTabLayoutView.setTabTextList(tabTextList);

        mYPKTabLayoutView.addTabSelectedListener { tabPosition ->
            val makeText =
                Toast.makeText(
                    this@MainActivity,
                    "点击了第" + tabPosition + "项",
                    Toast.LENGTH_SHORT
                )
            makeText.setGravity(Gravity.CENTER, 0, 0);
            makeText.show();
        }

```

### 属性说明：
属性     | 说明
-------- | -----
tabTextColor| tab的文字颜色
tabTextSize| tab文字大小（单位：sp）
tab_view_bg| 控件背景色
select_tab_color| 选中后tab的背景色
arcControlX| 值越大，曲线弧度越大（单位：px）
view_bg_corners| 控件的圆角大小（单位：dp）



## 优化计划
   ...

