package com.example.foodorderingapplication.ui.restaurantlisting

import com.example.foodorderingapplication.model.entity.category.Category


interface IItemOnClick {
    fun onClick(category: Category)
}