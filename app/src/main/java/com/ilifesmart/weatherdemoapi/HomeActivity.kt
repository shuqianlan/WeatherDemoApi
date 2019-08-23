package com.ilifesmart.weatherdemoapi

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ilifesmart.weatherdemoapi.adapters.KotlinDataAdapter
import com.ilifesmart.weatherdemoapi.base.BaseViewModelActivity
import com.ilifesmart.weatherdemoapi.databeans.HomeChapters
import com.ilifesmart.weatherdemoapi.databinding.HomeChaptersLayoutBinding
import com.ilifesmart.weatherdemoapi.viewmodels.HomeViewModel
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.content_home.*

class HomeActivity : BaseViewModelActivity<HomeViewModel>() {

    override fun getLayoutResId() = R.layout.activity_home

    private val datas = mutableListOf<HomeChapters.Data>()

    override fun providerVMClass(): Class<HomeViewModel> {
        return HomeViewModel::class.java
    }

    override fun initView() {
        home_articles.adapter = KotlinDataAdapter.Builder<HomeChapters.Data, HomeChaptersLayoutBinding>().apply {
            setLayoutId(R.layout.home_chapters_layout)
            setData(datas)
            bindWithData{
                holder, itemData ->
                (holder.binder as? HomeChaptersLayoutBinding)?.let {
                    holder.binder.bean = itemData
                }
            }
            isSupportRefreshStatus(true)
        }.build()

        home_articles.addOnScrollListener(object :RecyclerView.OnScrollListener() {
            private var isScrolled = false // 限制滑动时加载.
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (isScrolled) {
                    (recyclerView.layoutManager as? LinearLayoutManager) ?.let {
                        var lastItem = it.findLastCompletelyVisibleItemPosition()
                        var itemCount = it.itemCount

                        println("lastItem: $lastItem itemCount:$itemCount")
                        if (itemCount-1 == lastItem) {
                            loadDatas()
                        }
                    }
                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                isScrolled = (newState in listOf<Int>(RecyclerView.SCROLL_STATE_DRAGGING, RecyclerView.SCROLL_STATE_SETTLING))
            }
        })

        toolbar.title = ""
        setSupportActionBar(toolbar)
    }

    override fun initData() {
        viewModel.chapters.observe(this, Observer() {
            datas.addAll(it)
            println("datas::::$datas")
            home_articles.adapter?.notifyDataSetChanged()
        })
    }

    private fun loadDatas() {
        if (viewModel is HomeViewModel) {
            viewModel.loadDatas()
        }
    }

}
