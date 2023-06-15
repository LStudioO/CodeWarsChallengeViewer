package com.feature.user.data.local

import com.feature.user.domain.model.User

internal interface UserDataSource {
    val user: User
}
