package com.asadkhan.global.network

public data class BaseResult<T>(
  val count: Int, val next: String?, val previous: String?, val data: List<T>?
)
