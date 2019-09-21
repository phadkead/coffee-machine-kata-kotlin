package com.drink

sealed class OrderResult {
    data class Success(val order: PreparedOrder) : OrderResult()
    data class Failure(val message: String) : OrderResult()
}