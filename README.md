
![](https://img.shields.io/badge/release-v1.0.0-brightgreen.svg)
![](https://img.shields.io/badge/API-18%2B-orange.svg)


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

	  implementation 'com.github.dacaoyuan:YPKTabDemo:1.0.0'

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
        app:view_bg_corners="0"
        app:arcControlX="30" />

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


## 优化计划
   ...

