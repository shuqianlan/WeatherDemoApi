package com.ilifesmart.weatherdemoapi.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.ilifesmart.weatherdemoapi.databinding.LoadingItemHolderBinding

/*
* 若itemHolder的xml中，只有一个variable，则无需调用bindWithData
*
* */
class KotlinDataAdapter<T,R:ViewDataBinding> private constructor(): RecyclerView.Adapter<DataHolder>() {
    private var layoutId:Int?=null
    private var datas:List<T>?=null
    private var addBindView: ((DataHolder, T) -> Unit)?=null
    private var itemClick: ((View, T) -> Unit)?=null
    private var isSupportRefreshStatus = false

    private val NORMAL = 0  // 正常态
    private var REFRESH = 1 // 上拉刷新态

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataHolder {
        val binder = if (viewType == REFRESH) {
            println("===========================> ")
            DataBindingUtil.inflate<LoadingItemHolderBinding>(LayoutInflater.from(parent.context), com.ilifesmart.weatherdemoapi.R.layout.loading_item_holder, parent, false)
        } else {
            DataBindingUtil.inflate<R>(LayoutInflater.from(parent.context), layoutId!!, parent, false)
        }

        return DataHolder(binder!!)
    }

    override fun getItemCount(): Int {
        return datas?.size?:0 + 1
    }

    override fun onBindViewHolder(holder: DataHolder, position: Int) {
        if (position != datas!!.size-1) {
            addBindView?.invoke(holder, datas!!.get(position)) ?: holder.binder.setVariable(1, datas!!.get(position))
            holder.itemView.setOnClickListener {
                itemClick?.invoke(holder.itemView, datas!!.get(position))
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (isSupportRefreshStatus && datas!!.size-1 == position) {
            println("====================")
            return REFRESH
        } else {
            return NORMAL
        }
    }

    class Builder<B,S:ViewDataBinding> {
        private var adapter:KotlinDataAdapter<B,S> = KotlinDataAdapter()

        fun setData(datas:List<B>): Builder<B,S> {
            adapter.datas = datas
            return this
        }

        fun setLayoutId(layoutId:Int):Builder<B,S> {
            adapter.layoutId = layoutId
            return this
        }

        fun bindWithData(itemBind:((holder:DataHolder, itemData:B) -> Unit)): Builder<B,S> {
            adapter.addBindView = itemBind
            return this
        }

        fun onItemClick(itemClick:((itemView:View, itemData:B) -> Unit)): Builder<B,S> {
            adapter.itemClick = itemClick
            return this
        }

        fun isSupportRefreshStatus(bool:Boolean) : Builder<B,S> {
            adapter.isSupportRefreshStatus = bool
            return this
        }

        fun build() :KotlinDataAdapter<B,S> {
            return adapter
        }
    }
}