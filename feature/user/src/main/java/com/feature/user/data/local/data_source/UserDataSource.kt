package com.feature.user.data.local.data_source

import com.feature.user.domain.model.User

internal interface UserDataSource {
    val user: User
}
