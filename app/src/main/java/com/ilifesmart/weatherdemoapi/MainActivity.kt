package com.ilifesmart.weatherdemoapi

import androidx.lifecycle.Observer
import com.ilifesmart.weatherdemoapi.adapters.KotlinDataAdapter
import com.ilifesmart.weatherdemoapi.beans.Data
import com.ilifesmart.weatherdemoapi.databinding.ArticleItemBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseViewModelActivity<MainDemoViewModel>() {
    private var datas = mutableListOf<Data>()

    override fun providerVMClass(): Class<MainDemoViewModel> {
        return MainDemoViewModel::class.java
    }

    override fun getLayoutResId(): Int = R.layout.activity_main

    override fun initView() {
        recyclerView.adapter = creatAdapter()
    }

    override fun initData() {
        viewModel.getArticls().observe(this, Observer {
            datas.addAll(it)
            recyclerView.adapter?.notifyDataSetChanged()
        })
    }

    private fun creatAdapter() = with(KotlinDataAdapter.Builder<Data, ArticleItemBinding>()) {
        setLayoutId(R.layout.article_item)
        .setData(datas)
        .bindWithData { holder, itemData ->
            (holder.binder as? ArticleItemBinding)?.let {
                it.bean = itemData
            }
        }.onItemClick { _, itemData ->
            toast(itemData.name)
        }.build()
    }
}

