package com.feature.user.data.local.data_source

import com.feature.user.domain.model.User

internal class InMemoryUserDataSource : UserDataSource {
    override val user: User
        get() = User("colbydauph")
}
