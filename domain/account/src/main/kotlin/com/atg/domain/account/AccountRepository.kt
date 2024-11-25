package com.atg.domain.account

interface AccountRepository {
    var balance: Map<String, Float>
    var operationCount: Int
}