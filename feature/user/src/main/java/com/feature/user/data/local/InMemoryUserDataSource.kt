package com.feature.user.data.local

import com.feature.user.domain.model.User

internal class InMemoryUserDataSource : UserDataSource {
    override val user: User
        get() = User("colbydauph")
}
