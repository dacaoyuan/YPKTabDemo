package com.ypk.tablayout

import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ypk.library.interfac.OnTabClickListener
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tabTextList: MutableList<String> = ArrayList<String>() as MutableList<String>

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


        val tabTextList2: MutableList<String> = ArrayList<String>()

        tabTextList2.add("全部");
        tabTextList2.add("待发货");
        tabTextList2.add("待收货");
        tabTextList2.add("待评价");
        mYPKTabLayoutView2.setTabTextList(tabTextList2);
        mYPKTabLayoutView2.setTabTextStyle(Typeface.BOLD)
        mYPKTabLayoutView2.setSelectTab(1)//设置默认选中第二tab

        val tabTextList3: MutableList<String> = ArrayList<String>()
        tabTextList3.add("首页");
        tabTextList3.add("关注");
        mYPKTabLayoutView3.setTabTextList(tabTextList3);

        val tabTextList4: MutableList<String> = ArrayList<String>()
        tabTextList4.add("Java1");
        tabTextList4.add("Java2");
        tabTextList4.add("Java3");
        tabTextList4.add("Java4");
        mYPKTabLayoutView4.setTabTextList(tabTextList4);
    }


}
