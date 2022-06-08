package com.example.foodorderingapplication.ui.order

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodorderingapplication.R
import com.example.foodorderingapplication.model.entity.order.Order
import java.text.SimpleDateFormat

class OrderRecyclerViewAdapter :
    RecyclerView.Adapter<OrderRecyclerViewAdapter.OrderListViewHolder>() {
    var myOrderList = ArrayList<Order>()

    class OrderListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var orderImageView: ImageView = view.findViewById(R.id.ordersImageView)
        private var orderRestaurantName: TextView =
            view.findViewById(R.id.ordersItemRestaurantNameTextView)
        private var orderDate: TextView = view.findViewById(R.id.ordersItemDateTextView)
        private var orderMealName: TextView = view.findViewById(R.id.ordersItemFoodNameTextView)
        private var orderPrice: TextView = view.findViewById(R.id.orderPriceTextView)

        @SuppressLint("SimpleDateFormat")
        fun setItem(order: Order) {
            Glide.with(orderImageView.context)
                .load(order.meal.image)
                .into(orderImageView)
            orderRestaurantName.text = order.restaurant.name
            orderMealName.text = order.meal.name
            orderDate.text = SimpleDateFormat("dd/MM/yyyy").format(order.createdDate).toString()
            orderPrice.text = order.meal.price.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderListViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_myorders, parent, false)
        return OrderListViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderListViewHolder, position: Int) {
        val item = myOrderList[position]
        holder.setItem(item)

    }

    @SuppressLint("NotifyDataSetChanged")
    fun setOrderList(list: ArrayList<Order>) {
        this.myOrderList = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = myOrderList.size
}